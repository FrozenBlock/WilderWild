/*
 * Copyright 2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.entity.projectile.arrow;

import java.util.Collection;
import java.util.List;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Prediction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

public class Harpoon extends AbstractArrow {
	private static final double MAX_STRING_DISTANCE = 24D;
	private static final double MAX_STRING_DISTANCE_SQUARED = MAX_STRING_DISTANCE * MAX_STRING_DISTANCE;
	private static final int REEL_IN_TICKS_DEFAULT = -1;
	private static final int REEL_IN_TICKS = 5;
	private static final int REEL_IN_TICKS_BUFFER_BLOCK = 3;
	private static final EntityDataAccessor<Integer> HOOKED_ENTITY = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.INT);
	private @Nullable Entity attachedTo;
	private @Nullable Entity hookedIn;
	private int reelInTicks = REEL_IN_TICKS_DEFAULT;
	private boolean dealtDamage = false;

	public Harpoon(EntityType<? extends Harpoon> type, Level level) {
		super(type, level);
		this.baseDamage = 1D;
	}

	public Harpoon(Level level, LivingEntity owner, ItemStack harpoonItem) {
		super(WWEntityTypes.HARPOON.get(), owner, level, harpoonItem, null);
	}

	public Harpoon(Level level, double x, double y, double z, ItemStack harpoonItem) {
		super(WWEntityTypes.HARPOON.get(), x, y, z, level, harpoonItem, harpoonItem);
	}

	public Harpoon(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(WWEntityTypes.HARPOON.get(), owner, level, pickupItemStack, firedFromWeapon);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(HOOKED_ENTITY, 0);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		if (HOOKED_ENTITY.equals(accessor)) {
			final int id = this.getEntityData().get(HOOKED_ENTITY);
			this.hookedIn = id > 0 ? this.level().getEntity(id - 1) : null;
		}

		super.onSyncedDataUpdated(accessor);
	}

	@Override
	public void tick() {
		super.tick();

		if (!(this.getOrSetAttachedTo() instanceof LivingEntity attachedTo)) {
			if (this.reelInTicks != REEL_IN_TICKS_DEFAULT) this.discard();
			return;
		}

		if (this.level().isClientSide() || !this.shouldStopHarpooning(attachedTo)) {
			if (this.hookedIn != null) {
				if (!this.hookedIn.isRemoved() && this.hookedIn.canInteractWithLevel() && this.hookedIn.level().dimension() == this.level().dimension()) {
					this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.7D), this.hookedIn.getZ());
				} else {
					this.discard();
				}
			}
		}

		this.attachedTo = EntityReference.getLivingEntity(WWAttachmentTypes.HARPOON_ATTACHED_TO.get(this), this.level());
		this.tickReelIn();
	}

	private boolean shouldStopHarpooning(LivingEntity attachedTo) {
		if (attachedTo.canInteractWithLevel()) {
			final ItemStack selectedItem = attachedTo.getMainHandItem();
			final ItemStack selectedItemOffHand = attachedTo.getOffhandItem();
			final boolean mainHandIsHarpooning = isEmptyCrossbow(selectedItem);
			final boolean offHandIsHarpooning = isEmptyCrossbow(selectedItemOffHand);
			if ((mainHandIsHarpooning || offHandIsHarpooning) && this.distanceToSqr(attachedTo) <= MAX_STRING_DISTANCE_SQUARED) return false;
		}

		this.onHarpoonStringBroken(attachedTo);
		return true;
	}

	private void onHarpoonStringBroken(LivingEntity attachedTo) {
		WWAttachmentTypes.HARPOON_ATTACHED_TO.remove(this);
		WWAttachmentTypes.HARPOON.remove(attachedTo);

		// TODO: sounds and particles for string breaking

		if (this.getHookedIn() != null) this.discard();
	}

	public static boolean isEmptyCrossbow(ItemStack itemStack) {
		return itemStack.is(Items.CROSSBOW) && itemStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).isEmpty();
	}

	@Override
    protected double getDefaultGravity() {
        return this.isInWater() ? 0.01D : 0.075D;
    }

    public int retrieve(@Nullable ItemStack crossbow) {
		if (!(this.getOrSetAttachedTo() instanceof LivingEntity attachedTo)) return 0;

		if (this.level().isClientSide() || this.shouldStopHarpooning(attachedTo)) return 0;

		boolean pulled = false;
		pullEntityOrAttachedTo: {
			if (this.hookedIn != null) {
				if (this.reelInTicks >= 0) return 0;
				this.reelInTicks = REEL_IN_TICKS;
				if (crossbow != null && attachedTo instanceof ServerPlayer player) {
					// TODO: criteria for reeling hooked entity
					//CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player, crossbow, this, Collections.emptyList());
				}
			} else if (this.isInGround()) {
				if (this.reelInTicks >= REEL_IN_TICKS_BUFFER_BLOCK) return 0;
				this.reelInTicks = REEL_IN_TICKS;
				if (crossbow != null && attachedTo instanceof ServerPlayer player) {
					// TODO: criteria for using as grappling hook
					//CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player, crossbow, this, Collections.emptyList());
				}
			} else {
				break pullEntityOrAttachedTo;
			}

			this.level().broadcastEntityEvent(this, EntityEvent.FISHING_ROD_REEL_IN);
			pulled = true;
		}

		if (!pulled) {
			this.discard();
			final ItemStack pickupItem = this.getPickupItem();
			if (attachedTo instanceof ServerPlayer player && !pickupItem.has(DataComponents.INTANGIBLE_PROJECTILE)) {
				player.getInventory().placeItemBackInInventory(this.getPickupItem(), Prediction.SERVER_ONLY);
			}
		}

		return pulled ? 3 : 0;
    }

	@Override
	public void handleEntityEvent(@EntityEvent.Value byte id) {
		if (id == EntityEvent.FISHING_ROD_REEL_IN) this.reelInTicks = REEL_IN_TICKS;

		super.handleEntityEvent(id);
	}

	private void tickReelIn() {
		if (this.reelInTicks == REEL_IN_TICKS_DEFAULT) return;

		if (this.attachedTo == null) {
			this.discard();
			return;
		}

		if (this.hookedIn != null) {
			this.pullEntity(this.hookedIn);
		} else if (this.isInGround() && this.getOrSetAttachedTo() != null) {
			this.pullEntity(this.getOrSetAttachedTo());
		} else {
			this.discard();
			return;
		}

		this.reelInTicks -= 1;
		if (this.reelInTicks <= 0) this.discard();
	}

	protected void pullEntity(Entity entity) {
		final Entity attachedTo = this.getOrSetAttachedTo();
		if (attachedTo == null || !entity.canSimulateMovement()) return;

		final boolean reverse = entity == attachedTo;

		final float attachedToYHeight;
		if (attachedTo instanceof Player) {
			float attachedToXRot = attachedTo.getViewXRot(1F);
			if (attachedToXRot > 0F) attachedToXRot /= 2F;
			attachedToYHeight = (-(attachedToXRot / 90F) * 0.5F) + 0.5F;
		} else {
			attachedToYHeight = 0.35F;
		}

		final Vec3 delta = new Vec3(attachedTo.getX() - this.getX(), attachedTo.getY(attachedToYHeight) - this.getY(), attachedTo.getZ() - this.getZ())
			.scale(!reverse ? 1D : -1D);
		final double distance = delta.length();
		Vec3 reelDelta = delta.normalize().scale(Math.min(distance * 1.5D, 12D)).scale(!reverse ? 0.04D : 0.02D);
		if (reelDelta.y() > 0D) reelDelta = reelDelta.multiply(1D, 0.75D, 1D);

		entity.addDeltaMovement(reelDelta);
	}

	@Override
	protected @Nullable EntityHitResult findHitEntity(Vec3 from, Vec3 to) {
		return this.dealtDamage ? null : super.findHitEntity(from, to);
	}

	@Override
	protected Collection<EntityHitResult> findHitEntities(Vec3 from, Vec3 to) {
		final EntityHitResult hitResult = this.findHitEntity(from, to);
		return hitResult != null ? List.of(hitResult) : List.of();
	}

	@Override
	protected void doPostHurtEffects(LivingEntity mob) {
		super.doPostHurtEffects(mob);

		if (!mob.canInteractWithLevel()) {
			this.discard();
			return;
		}

		this.setDealtDamage(true);
		this.setHookedIn(mob);
	}

	public void setHookedIn(@Nullable Entity hookedIn) {
		this.hookedIn = hookedIn;
		this.getEntityData().set(HOOKED_ENTITY, hookedIn == null ? 0 : hookedIn.getId() + 1);
	}

	public @Nullable Entity getHookedIn() {
		return this.hookedIn;
	}

	public @Nullable Entity getAttachedTo() {
		return this.attachedTo;
	}

	public @Nullable Entity getOrSetAttachedTo() {
		if (this.attachedTo == null) this.attachedTo = EntityReference.getLivingEntity(WWAttachmentTypes.HARPOON_ATTACHED_TO.get(this), this.level());
		return this.attachedTo;
	}

	public void setDealtDamage(boolean dealtDamage) {
		this.dealtDamage = dealtDamage;
	}

	public boolean dealtDamage() {
		return this.dealtDamage;
	}

	public boolean reelingIn() {
		return this.reelInTicks >= 0;
	}

	@Override
	public void tickDespawn() {
		if (this.pickup != Pickup.ALLOWED || this.owner == null) super.tickDespawn();
	}

	@Override
	protected float getWaterInertia() {
		return 0.99F;
	}

	@Override
	public boolean isNoPhysics() {
		return this.hookedIn != null || super.isNoPhysics();
	}

	@Override
	protected boolean tryPickup(Player player) {
		if (this.getOrSetAttachedTo() != null) return false;
		return super.tryPickup(player) || this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
	}

	@Override
	protected ItemStack getDefaultPickupItem() {
		return new ItemStack(WWItems.HARPOON.get());
	}

	@Override
	public void playerTouch(Player player) {
		if (this.ownedBy(player) || this.getOwner() == null) super.playerTouch(player);
	}

	@Override
	public void remove(Entity.RemovalReason reason) {
		this.updateOwnerInfo(null);
		super.remove(reason);
	}

	@Override
	public void setOwner(@Nullable Entity owner) {
		super.setOwner(owner);
		this.updateOwnerInfo(this);
	}

	@Override
	public void applyOnProjectileSpawned(ServerLevel serverLevel, ItemStack pickupItemStack) {
		super.applyOnProjectileSpawned(serverLevel, pickupItemStack);

		if (!(this.getOwner() instanceof LivingEntity owner)) return;
		WWAttachmentTypes.HARPOON_ATTACHED_TO.set(this, EntityReference.of(owner));
		WWAttachmentTypes.HARPOON.set(owner, EntityReference.of(this));
	}

	private void updateOwnerInfo(@Nullable Harpoon harpoon) {
		if (!(this.getOwner() instanceof LivingEntity owner)) return;

		if (harpoon == null) {
			final EntityReference<Harpoon> harpoonReference = WWAttachmentTypes.HARPOON.get(owner);
			if (harpoonReference == null || harpoonReference.getEntity(this.level(), Harpoon.class) != this) return;
			WWAttachmentTypes.HARPOON.remove(owner);
		}
	}

	@Override
	protected void addAdditionalSaveData(ValueOutput output) {
		super.addAdditionalSaveData(output);
		output.putBoolean("DealtDamage", this.dealtDamage);
	}

	@Override
	protected void readAdditionalSaveData(ValueInput input) {
		super.readAdditionalSaveData(input);
		this.dealtDamage = input.getBooleanOr("DealtDamage", false);
		this.baseDamage = input.getDoubleOr("damage", 1D);
	}
}
