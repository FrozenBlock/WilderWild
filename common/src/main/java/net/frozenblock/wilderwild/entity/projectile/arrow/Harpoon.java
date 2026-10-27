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
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

// TODO: data attachment that is literally just. the harpoon instance. and put it in the owner (thats how fishing hooks work apparently)
public class Harpoon extends AbstractArrow {
	private static final EntityDataAccessor<Integer> HOOKED_ENTITY = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.INT);
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

		if (!(this.getOwner() instanceof LivingEntity owner)) {
			this.discard();
			return;
		}

		if (this.level().isClientSide() || !this.shouldStopHarpooning(owner)) {
			if (this.hookedIn != null) {
				if (!this.hookedIn.isRemoved() && this.hookedIn.canInteractWithLevel() && this.hookedIn.level().dimension() == this.level().dimension()) {
					this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.7D), this.hookedIn.getZ());
				} else {
					this.setHookedEntity(null);
				}
			}
		}
	}

	private boolean shouldStopHarpooning(LivingEntity owner) {
		if (owner.canInteractWithLevel()) {
			final ItemStack selectedItem = owner.getMainHandItem();
			final ItemStack selectedItemOffHand = owner.getOffhandItem();
			boolean mainHandIsFishing = selectedItem.is(Items.CROSSBOW);
			boolean offHandIsFishing = selectedItemOffHand.is(Items.CROSSBOW);
			if ((mainHandIsFishing || offHandIsFishing) && this.distanceToSqr(owner) <= 1024D) {
				return false;
			}
		}

		this.discard();
		return true;
	}

	public float getAirHorizontalDrag() {
		return 0.7F;
	}

	@Override
    protected double getDefaultGravity() {
        return this.isInWater() ? super.getDefaultGravity() : 0.1D;
    }

    public int retrieve(ItemStack crossbow) {
		if (!(this.getOwner() instanceof LivingEntity owner)) return 0;

		if (this.level().isClientSide() || this.shouldStopHarpooning(owner)) return 0;

		int dmg = 0;
		if (this.hookedIn != null) {
			this.pullEntity(this.hookedIn);
			// TODO: criteria for reeling hooked entity
			//CriteriaTriggers.FISHING_ROD_HOOKED.trigger((ServerPlayer)owner, crossbow, this, Collections.emptyList());
			this.level().broadcastEntityEvent(this, EntityEvent.FISHING_ROD_REEL_IN);
			dmg = 3;
		}

		if (this.onGround()) dmg = 1;

		this.discard();
		return dmg;
    }

	@Override
	public void handleEntityEvent(@EntityEvent.Value byte id) {
		if (id == EntityEvent.FISHING_ROD_REEL_IN && this.hookedIn != null) this.pullEntity(this.hookedIn);

		super.handleEntityEvent(id);
	}

	protected void pullEntity(Entity entity) {
		final Entity owner = this.getOwner();
		if (owner == null || !entity.canSimulateMovement()) return;

		final Vec3 delta = new Vec3(owner.getX() - this.getX(), owner.getY() - this.getY(), owner.getZ() - this.getZ()).scale(0.35D);
		entity.addDeltaMovement(delta);
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
		this.setHookedEntity(mob);
	}

	public void setHookedEntity(@Nullable Entity hookedIn) {
		this.hookedIn = hookedIn;
		this.getEntityData().set(HOOKED_ENTITY, hookedIn == null ? 0 : hookedIn.getId() + 1);
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
