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
import net.frozenblock.wilderwild.item.impl.WWEnchantmentHelper;
import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWEnchantmentEffectComponents;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WWSoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.item.enchantment.EnchantmentHelper;
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
	private static final int GRAPPLE_BUFFER_TICKS = 5;
	private static final float GRAPPLE_TIME_IN_SECONDS = (float)REEL_IN_TICKS / 20F;
	private static final float DEFAULT_GRAPPLE_STRENGTH = 0.02F;
	private static final int IN_GROUND_TIME_BEFORE_TENSION_REEL = 10;
	private static final EntityDataAccessor<Integer> HOOKED_ENTITY = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Byte> GRAPPLE_TICKS = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Float> GRAPPLE_STRENGTH = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Byte> REMAINING_GRAPPLE_COUNT = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> PREVENT_ROPE_SNAP = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> PREVENT_GRAPPLE = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Boolean> FOIL = SynchedEntityData.defineId(Harpoon.class, EntityDataSerializers.BOOLEAN);
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
		this.setupEnchantmentSynchedData(harpoonItem);
	}

	public Harpoon(Level level, double x, double y, double z, ItemStack harpoonItem) {
		super(WWEntityTypes.HARPOON.get(), x, y, z, level, harpoonItem, harpoonItem);
		this.setupEnchantmentSynchedData(harpoonItem);
	}

	public Harpoon(Level level, LivingEntity owner, ItemStack pickupItemStack, @Nullable ItemStack firedFromWeapon) {
		super(WWEntityTypes.HARPOON.get(), owner, level, pickupItemStack, firedFromWeapon);
		this.setupEnchantmentSynchedData(pickupItemStack);
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder entityData) {
		super.defineSynchedData(entityData);
		entityData.define(HOOKED_ENTITY, 0);
		entityData.define(GRAPPLE_TICKS, (byte)REEL_IN_TICKS);
		entityData.define(GRAPPLE_STRENGTH, DEFAULT_GRAPPLE_STRENGTH);
		entityData.define(REMAINING_GRAPPLE_COUNT, (byte)1);
		entityData.define(PREVENT_ROPE_SNAP, false);
		entityData.define(PREVENT_GRAPPLE, false);
		entityData.define(FOIL, false);
	}

	private void setupEnchantmentSynchedData(ItemStack harpoonItem) {
		this.setGrappleTicks(this.getGrappleTimeInTicksFromItem(harpoonItem));
		this.setGrappleStrength(this.getGrappleStrengthFromItem(harpoonItem));
		this.setRemainingGrappleCount(this.getGrappleMaxCountFromItem(harpoonItem));
		this.setPreventRopeSnap(this.getPreventRopeSnapFromItem(harpoonItem));
		this.setPreventGrapple(this.getPreventGrappleFromItem(harpoonItem));
		this.setFoil(harpoonItem.hasFoil());
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
			if (this.reelInTicks != REEL_IN_TICKS_DEFAULT) this.discardAndPlaceItemBackInInventory(false);
			return;
		}

		if (this.level().isClientSide() || !this.shouldStopHarpooning(attachedTo)) {
			if (this.hookedIn != null) {
				if (!this.hookedIn.isRemoved() && this.hookedIn.canInteractWithLevel() && this.hookedIn.level().dimension() == this.level().dimension()) {
					this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.7D), this.hookedIn.getZ());
				} else {
					this.discardAndPlaceItemBackInInventory(false);
				}
			} else if (this.isInGround() && this.getPreventGrapple() && this.inGroundTime > IN_GROUND_TIME_BEFORE_TENSION_REEL) {
				this.discardAndPlaceItemBackInInventory(true);
				return;
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

		if (this.getHookedIn() != null) this.setHookedIn(null);
		if (this.getPreventRopeSnap()) {
			this.discardAndPlaceItemBackInInventory(true);
		} else {
			this.level().broadcastEntityEvent(this, EntityEvent.SHAKE);
			this.playRopeSound(
				WWSoundEvents.HARPOON_SNAP.asHolder(),
				1F,
				0.9F,
				1.1F
			);
		}
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

		if (this.hookedIn != null) {
			if (this.reelInTicks >= 0) return 0;

			this.reelInTicks = REEL_IN_TICKS;
			this.level().broadcastEntityEvent(this, EntityEvent.FISHING_ROD_REEL_IN);
			this.playRopeSound(
				WWSoundEvents.HARPOON_REEL_ENTITY.asHolder(),
				1F,
				0.9F,
				1.1F
			);
			if (crossbow != null && attachedTo instanceof ServerPlayer player) {
				// TODO: criteria for reeling hooked entity
				//CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player, crossbow, this, Collections.emptyList());
			}

			return 3;
		}

		if (this.reelInTicks >= GRAPPLE_BUFFER_TICKS) return 0;
		if (this.isInGround() && this.getRemainingGrappleCount() > 0 && !this.getPreventGrapple()) {
			this.reelInTicks = this.getGrappleTicks();
			final int previousRemainingGrappleCount = this.getRemainingGrappleCount();
			this.setRemainingGrappleCount(previousRemainingGrappleCount - 1);
			this.level().broadcastEntityEvent(this, EntityEvent.TENDRILS_SHIVER);
			this.playRopeSound(
				this.getGrappleTicks() > 6
					? previousRemainingGrappleCount > 1
						? WWSoundEvents.HARPOON_REEL_GRAPPLE.asHolder()
						: WWSoundEvents.HARPOON_REEL_GRAPPLE_WEAK.asHolder()
					: previousRemainingGrappleCount > 1
						? WWSoundEvents.HARPOON_REEL_GRAPPLE_SHORT.asHolder()
						: WWSoundEvents.HARPOON_REEL_GRAPPLE_WEAK_SHORT.asHolder(),
				1F,
				0.9F + ((previousRemainingGrappleCount - 1) * 0.2F),
				1.1F + ((previousRemainingGrappleCount - 1) * 0.2F)
			);
			if (crossbow != null && attachedTo instanceof ServerPlayer player) {
				// TODO: criteria for using as grappling hook
				//CriteriaTriggers.FISHING_ROD_HOOKED.trigger(player, crossbow, this, Collections.emptyList());
			}

			return 3;
		}

		this.discardAndPlaceItemBackInInventory(false);
		return 0;
    }

	public void discardAndPlaceItemBackInInventory(boolean automaticFromEnchantment) {
		if (this.level().isClientSide()) return;

		final Entity attachedTo = this.getOrSetAttachedTo();
		if (!(attachedTo instanceof Player) && attachedTo instanceof LivingEntity livingEntity) {
			this.playRopeSound(
				automaticFromEnchantment ? WWSoundEvents.HARPOON_REEL_AUTOMATIC.asHolder() : WWSoundEvents.HARPOON_REEL.asHolder(),
				0.75F,
				0.9F,
				1.1F
			);
			livingEntity.take(this, 1);
		} else if (attachedTo instanceof ServerPlayer player && (this.pickup == Pickup.ALLOWED || player.hasInfiniteMaterials())) {
			if (player.isDeadOrDying()) return;
			this.playRopeSound(
				automaticFromEnchantment ? WWSoundEvents.HARPOON_REEL_AUTOMATIC.asHolder() : WWSoundEvents.HARPOON_REEL.asHolder(),
				0.75F,
				0.9F,
				1.1F
			);
			player.take(this, 1);
			if (this.pickup == Pickup.ALLOWED) player.getInventory().placeItemBackInInventory(this.getPickupItem(), Prediction.SERVER_ONLY);
		}

		this.discard();
	}

	private void playRopeSound(Holder<SoundEvent> soundEvent, float volume, float minPitch, float maxPitch) {
		if (this.isSilent() || this.level().isClientSide()) return;

		final Entity attachedTo = this.getOrSetAttachedTo();
		if (attachedTo != null && attachedTo.isSilent()) return;

		final Entity sourceEntity = attachedTo != null ? attachedTo : this;
		final float pitch = minPitch + (this.getRandom().nextFloat() * (maxPitch - minPitch));
		this.level().playSound(
			null,
			sourceEntity.getX(),
			sourceEntity.getY(),
			sourceEntity.getZ(),
			soundEvent,
			sourceEntity.getSoundSource(),
			volume,
			pitch
		);
	}

	@Override
	public void handleEntityEvent(@EntityEvent.Value byte id) {
		if (id == EntityEvent.FISHING_ROD_REEL_IN) this.reelInTicks = REEL_IN_TICKS;
		if (id == EntityEvent.TENDRILS_SHIVER) this.reelInTicks = this.getGrappleTicks();
		if (id == EntityEvent.SHAKE) {
			final Entity attachedTo = this.attachedTo;
			if (attachedTo != null) {
				final Vec3 harpoonPos = this.getEyePosition();
				final Vec3 attachedToPos = attachedTo.getRopeHoldPosition(1F);
				final float xDifference = (float)(attachedToPos.x - harpoonPos.x);
				final float yDifference = (float)(attachedToPos.y - harpoonPos.y);
				final float zDifference = (float)(attachedToPos.z - harpoonPos.z);
				final float offsetFactor = Mth.invSqrt(xDifference * xDifference + zDifference * zDifference) * 0.05F / 2F;
				final float xOffset = zDifference * offsetFactor;
				final float zOffset = xDifference * offsetFactor;

				final int particleCount = this.random.nextIntBetweenInclusive(8, 20);
				for (int i = 0; i <= particleCount; i++) {
					final float progress = i / particleCount;
					final float x = xDifference * progress;
					final float y = yDifference > 0F
						? yDifference * progress * progress
						: yDifference - yDifference * (1F - progress) * (1F - progress);
					final float z = zDifference * progress;
					this.level().addParticle(
						new ItemParticleOption(ParticleTypes.ITEM, Items.LEAD),
						harpoonPos.x + x + xOffset,
						harpoonPos.y + y,
						harpoonPos.z + z + zOffset,
						0D,
						0D,
						0D
					);
				}
			}
		}

		super.handleEntityEvent(id);
	}

	private void tickReelIn() {
		if (this.reelInTicks == REEL_IN_TICKS_DEFAULT) return;

		if (this.attachedTo == null) {
			this.discardAndPlaceItemBackInInventory(false);
			return;
		}

		if (this.hookedIn != null) {
			this.pullEntity(this.hookedIn, false);
		} else if (this.isInGround() && this.getOrSetAttachedTo() != null && !this.getPreventGrapple()) {
			this.pullEntity(this.getOrSetAttachedTo(), true);
		} else {
			this.discardAndPlaceItemBackInInventory(false);
			return;
		}

		this.reelInTicks -= 1;
		if (this.reelInTicks <= 0) this.discardAndPlaceItemBackInInventory(false);
	}

	protected void pullEntity(Entity entity, boolean grapple) {
		final Entity attachedTo = this.getOrSetAttachedTo();
		if (attachedTo == null || !entity.canSimulateMovement()) return;

		final float attachedToYHeight;
		if (attachedTo instanceof Player) {
			float attachedToXRot = attachedTo.getViewXRot(1F);
			if (attachedToXRot > 0F) attachedToXRot /= 2F;
			attachedToYHeight = (-(attachedToXRot / 90F) * 0.5F) + 0.5F;
		} else {
			attachedToYHeight = 0.35F;
		}

		final Vec3 delta = new Vec3(attachedTo.getX() - this.getX(), attachedTo.getY(attachedToYHeight) - this.getY(), attachedTo.getZ() - this.getZ())
			.scale(!grapple ? 1D : -1D);
		final double distance = delta.length();
		Vec3 reelDelta = delta.normalize().scale(Math.min(distance * 1.5D, 12D)).scale(!grapple ? 0.04D : this.getGrappleStrength());
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
			this.discardAndPlaceItemBackInInventory(false);
			return;
		}

		this.setDealtDamage(true);
		this.setHookedIn(mob);
	}

	@Override
	public void addBubbleParticles(Vec3 position) {
		if (this.hookedIn != null) return;
		super.addBubbleParticles(position);
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

	private void setGrappleTicks(int grapplingTicks) {
		this.entityData.set(GRAPPLE_TICKS, (byte) Math.clamp(grapplingTicks, 0, 127));
	}

	public byte getGrappleTicks() {
		return this.entityData.get(GRAPPLE_TICKS);
	}

	private void setGrappleStrength(float grappleStrength) {
		this.entityData.set(GRAPPLE_STRENGTH, grappleStrength);
	}

	public float getGrappleStrength() {
		return this.entityData.get(GRAPPLE_STRENGTH);
	}

	private void setRemainingGrappleCount(int remainingGrappleCount) {
		this.entityData.set(REMAINING_GRAPPLE_COUNT, (byte) Math.clamp(remainingGrappleCount, 0, 127));
	}

	public byte getRemainingGrappleCount() {
		return this.entityData.get(REMAINING_GRAPPLE_COUNT);
	}

	private void setPreventRopeSnap(boolean preventRopeSnap) {
		this.entityData.set(PREVENT_ROPE_SNAP, preventRopeSnap);
	}

	public boolean getPreventRopeSnap() {
		return this.entityData.get(PREVENT_ROPE_SNAP);
	}

	private void setPreventGrapple(boolean preventGrapple) {
		this.entityData.set(PREVENT_GRAPPLE, preventGrapple);
	}

	public boolean getPreventGrapple() {
		return this.entityData.get(PREVENT_GRAPPLE);
	}

	private void setFoil(boolean foil) {
		this.entityData.set(FOIL, foil);
	}

	public boolean isFoil() {
		return this.entityData.get(FOIL);
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
		this.setupEnchantmentSynchedData(this.getPickupItemStackOrigin());
	}

	private byte getGrappleTimeInTicksFromItem(ItemStack harpoonItem) {
		return this.level() instanceof ServerLevel serverLevel
			? (byte)Math.clamp(Mth.floor(WWEnchantmentHelper.modifyHarpoonGrapplingTime(serverLevel, harpoonItem, this, GRAPPLE_TIME_IN_SECONDS) * 20F), 0, 127)
			: 0;
	}

	private float getGrappleStrengthFromItem(ItemStack harpoonItem) {
		return this.level() instanceof ServerLevel serverLevel
			? WWEnchantmentHelper.modifyHarpoonGrappleStrength(serverLevel, harpoonItem, this, DEFAULT_GRAPPLE_STRENGTH)
			: 0;
	}

	private byte getGrappleMaxCountFromItem(ItemStack harpoonItem) {
		return this.level() instanceof ServerLevel serverLevel
			? (byte)Math.clamp(WWEnchantmentHelper.modifyHarpoonGrappleMaxCount(serverLevel, harpoonItem, this, 1), 0, 127)
			: 0;
	}

	private boolean getPreventRopeSnapFromItem(ItemStack harpoonItem) {
		return this.level() instanceof ServerLevel && EnchantmentHelper.has(harpoonItem, WWEnchantmentEffectComponents.PREVENT_HARPOON_ROPE_SNAP.get());
	}

	private boolean getPreventGrappleFromItem(ItemStack harpoonItem) {
		return this.level() instanceof ServerLevel && EnchantmentHelper.has(harpoonItem, WWEnchantmentEffectComponents.PREVENT_HARPOON_GRAPPLE.get());
	}
}
