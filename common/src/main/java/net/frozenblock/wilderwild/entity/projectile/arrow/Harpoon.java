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
	private static final EntityDataAccessor<Integer> HOOKED_ENTITY = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.INT);
	private @Nullable Entity attachedTo;
	private @Nullable Entity hookedIn;
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

		if (!(this.getOrSetAttachedTo() instanceof LivingEntity attachedTo)) return;

		if (this.level().isClientSide() || !this.shouldStopHarpooning(attachedTo)) {
			if (this.hookedIn != null) {
				if (!this.hookedIn.isRemoved() && this.hookedIn.canInteractWithLevel() && this.hookedIn.level().dimension() == this.level().dimension()) {
					this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.7D), this.hookedIn.getZ());
				} else {
					this.setHookedIn(null);
				}
			}
		}

		this.attachedTo = EntityReference.getLivingEntity(WWAttachmentTypes.HARPOON_ATTACHED_TO.get(this), this.level());
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
		// TOOD: sounds and particles for string breaking
	}

	public static boolean isEmptyCrossbow(ItemStack itemStack) {
		return itemStack.is(Items.CROSSBOW) && itemStack.getOrDefault(DataComponents.CHARGED_PROJECTILES, ChargedProjectiles.EMPTY).isEmpty();
	}

	@Override
    protected double getDefaultGravity() {
        return this.isInWater() ? 0.01D : 0.075D;
    }

    public int retrieve(ItemStack crossbow) {
		if (!(this.getOrSetAttachedTo() instanceof LivingEntity attachedTo)) return 0;

		if (this.level().isClientSide() || this.shouldStopHarpooning(attachedTo)) return 0;

		int dmg = 0;
		pullEntityOrAttachedTo: {
			if (this.hookedIn != null) {
				this.pullEntity(this.hookedIn);
				// TODO: criteria for reeling hooked entity
				//CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)owner, crossbow, this, Collections.emptyList());
			} else if (this.isInGround()) {
				this.pullEntity(attachedTo);
				// TODO: criteria for using as grappling hook
				//CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)owner, crossbow, this, Collections.emptyList());
			} else {
				break pullEntityOrAttachedTo;
			}

			this.level().broadcastEntityEvent(this, EntityEvent.FISHING_ROD_REEL_IN);
			dmg = 3;
		}

		this.discard();
		return dmg;
    }

	@Override
	public void handleEntityEvent(@EntityEvent.Value byte id) {
		if (id == EntityEvent.FISHING_ROD_REEL_IN) {
			if (this.hookedIn != null) {
				this.pullEntity(this.hookedIn);
			} else if (this.isInGround() && this.getOrSetAttachedTo() != null) {
				this.pullEntity(this.getOrSetAttachedTo());
			}
		}

		super.handleEntityEvent(id);
	}

	protected void pullEntity(Entity entity) {
		final Entity attachedTo = this.getOrSetAttachedTo();
		if (attachedTo == null || !entity.canSimulateMovement()) return;

		final boolean reverse = entity == attachedTo;

		float attachedToXRot = attachedTo.getViewXRot(1F);
		if (attachedToXRot > 0F) attachedToXRot /= 2F;
		final float attachedToYHeight = (-(attachedToXRot / 90F) * 0.5F) + 0.5F;

		final Vec3 delta = new Vec3(attachedTo.getX() - this.getX(), attachedTo.getY(attachedToYHeight) - this.getY(), attachedTo.getZ() - this.getZ())
			.scale(!reverse ? 1D : -1D);
		final double distance = delta.length();
		final Vec3 reelDelta = delta.normalize().scale(Math.min(distance * 1.5D, 16D)).scale(!reverse ? 0.2D : 0.1D);
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
	protected boolean tryPickup(final Player player) {
		return super.tryPickup(player) || this.isNoPhysics() && this.ownedBy(player) && player.getInventory().add(this.getPickupItem());
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

	private void updateOwnerInfo(@Nullable Harpoon harpoon) {
		if (!(this.getOwner() instanceof LivingEntity owner)) return;

		if (harpoon == null) {
			WWAttachmentTypes.HARPOON.remove(owner);
			return;
		}

		WWAttachmentTypes.HARPOON_ATTACHED_TO.set(this, EntityReference.of(owner));
		WWAttachmentTypes.HARPOON.set(owner, EntityReference.of(harpoon));
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
