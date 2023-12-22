/*
 * Copyright 2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity;

import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.particle.api.FrozenParticleTypes;
import net.frozenblock.wilderwild.registry.RegisterDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;
import java.util.List;

public class Ostrich extends AbstractHorse implements PlayerRideableJumping, Saddleable {
	public static final int BEAK_COOLDOWN_TICKS = 40;
	public static final int BEAK_STUCK_TICKS = 100;

	private static final EntityDataAccessor<Float> TARGET_BEAK_ANIM_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> TARGET_PASSENGER_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Boolean> IS_ATTACKING = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.BOOLEAN);
	private static final EntityDataAccessor<Integer> BEAK_COOLDOWN = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Integer> STUCK_TICKS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.INT);
	private float prevPassengerProgress;
	private float passengerProgress;
	private float prevBeakAnimProgress;
	private float beakAnimProgress;

	public Ostrich(EntityType<? extends Ostrich> entityType, Level level) {
		super(entityType, level);
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D)
			.add(Attributes.MOVEMENT_SPEED, 0.225F)
			.add(Attributes.JUMP_STRENGTH, 1.0D)
			.add(Attributes.ATTACK_DAMAGE, 6.0D);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(TARGET_BEAK_ANIM_PROGRESS, 0F);
		this.entityData.define(TARGET_PASSENGER_PROGRESS, 0F);
		this.entityData.define(IS_ATTACKING, false);
		this.entityData.define(BEAK_COOLDOWN, 0);
		this.entityData.define(STUCK_TICKS, 0);
	}

	@Override
	public void tick() {
		this.prevBeakAnimProgress = this.getTargetBeakAnimProgress();
		super.tick();

		if (this.getBeakCooldown() > 0) {
			this.setBeakCooldown(this.getBeakCooldown() - 1);
			if (this.getBeakCooldown() == 0) {
				this.level().playSound(null, this.blockPosition(), SoundEvents.CAMEL_DASH_READY, SoundSource.NEUTRAL, 1.0F, 1.0F);
				this.setTargetBeakAnimProgress(0F);
			}
		}

		if (this.refuseToMove()) {
			this.clampHeadRotationToBody(this, 0F);
		}

		this.prevBeakAnimProgress = this.beakAnimProgress;
		this.beakAnimProgress = this.beakAnimProgress + ((this.getTargetBeakAnimProgress() - this.beakAnimProgress) * 0.3F);

		if (!this.level().isClientSide) {
			this.handleAttackAndStuck();

			if (this.getFirstPassenger() != null) {
				this.setTargetPassengerProgress(1F);
			} else {
				this.setTargetPassengerProgress(0F);
			}
		}
		this.prevPassengerProgress = this.passengerProgress;
		this.passengerProgress = this.passengerProgress + ((this.getTargetPassengerProgress() - this.passengerProgress) * 0.3F);

		this.getBeakPos();
	}

	private void handleAttackAndStuck() {
		if (this.isAttacking()) {
			Vec3 beakPos = this.getBeakPos();
			boolean hasAttacked = false;
			AABB attackBox = AABB.ofSize(beakPos, 0.3D, 0.3D, 0.3D);
			if (this.level() instanceof ServerLevel serverLevel) {
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.minY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.minY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.maxY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.minY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.maxY, attackBox.minZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.minY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.minX, attackBox.maxY, attackBox.maxZ, 1, 0, 0, 0, 0);
				serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, attackBox.maxX, attackBox.maxY, attackBox.maxZ, 1, 0, 0, 0, 0);
			}

			List<Entity> entities = this.level().getEntities(this, attackBox);
			float beakAverage = (this.beakAnimProgress + this.getTargetBeakAnimProgress()) * 0.5F;
			float beakDamage = beakAverage * 5F;
			for (Entity entity : entities) {
				if (!this.hasPassenger(entity) && !this.isAlliedTo(entity)) {
					if (this.getOwner() instanceof Player player) {
						hasAttacked = entity.hurt(this.damageSources().source(RegisterDamageTypes.OSTRICH_PROXY, player, this), beakDamage);
					} else {
						hasAttacked = entity.hurt(this.damageSources().source(RegisterDamageTypes.OSTRICH_PROXY, this.getOwner(), this), beakDamage);
					}
				}
				if (hasAttacked) {
					this.setTargetBeakAnimProgress(0F);
					this.setAttacking(false);
					return;
				}
			}

			if (this.canGetHeadStuck()) {
				this.setBeakCooldown(BEAK_STUCK_TICKS + BEAK_COOLDOWN_TICKS);
				this.setStuckTicks(BEAK_STUCK_TICKS);
				this.setAttacking(false);
				this.setTargetBeakAnimProgress(this.beakAnimProgress);
			} else if (this.getBeakAnimProgress(1F) >= this.getTargetBeakAnimProgress() - 0.025F) {
				this.setTargetBeakAnimProgress(0F);
				this.setAttacking(false);
			}

		} else if (this.getStuckTicks() > 0) {
			this.setStuckTicks(this.getStuckTicks() - 1);
			if (this.getStuckTicks() == 0 || !this.canGetHeadStuck()) {
				this.emergeBeak();
			}
		}
	}

	public void emergeBeak() {
		this.setStuckTicks(0);
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setTargetBeakAnimProgress(0F);
	}

	@Override
	public boolean canSprint() {
		return true;
	}

	@Override
	public void tickRidden(@NotNull Player player, @NotNull Vec3 travelVector) {
		Vec2 vec2 = this.getRiddenRotation(player);
		this.setRot(vec2.y, vec2.x);
		this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
		if (this.isControlledByLocalInstance()) {
			if (travelVector.z <= 0.0) {
				this.gallopSoundCounter = 0;
			}
			if (this.playerJumpPendingScale > 0.0F) {
				this.executeRidersJump(this.playerJumpPendingScale, travelVector);
			}
			this.playerJumpPendingScale = 0.0F;
		}
	}

	@Override
	public void executeRidersJump(float playerJumpPendingScale, @NotNull Vec3 travelVector) {
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setAttacking(true);
		this.hasImpulse = true;
	}

	@Override
	public void handleStartJump(int jumpPower) {
		this.setBeakCooldown(BEAK_COOLDOWN_TICKS);
		this.setAttacking(true);
		this.playSound(SoundEvents.CAMEL_DASH, 1.0F, this.getVoicePitch());
		this.gameEvent(GameEvent.ENTITY_ACTION);
		float powerPercent = ((float) jumpPower) * 0.01F;
		this.setTargetBeakAnimProgress(powerPercent);
	}

	@Override
	public void handleStopJump() {
	}

	@Override
	public int getJumpCooldown() {
		return this.getBeakCooldown();
	}

	public boolean refuseToMove() {
		return this.getStuckTicks() > 0;
	}

	@Override
	public float getRiddenSpeed(@NotNull Player player) {
		float additionalSpeed = player.isSprinting() && this.getJumpCooldown() == 0 ? 0.2F : 0.0F;
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) + additionalSpeed;
	}

	@Override
	public Vec2 getRiddenRotation(@NotNull LivingEntity entity) {
		return this.refuseToMove() ? new Vec2(this.getXRot(), this.getYRot()) : super.getRiddenRotation(entity);
	}

	@Override
	public Vec3 getRiddenInput(@NotNull Player player, @NotNull Vec3 travelVector) {
		return this.refuseToMove() ? Vec3.ZERO : super.getRiddenInput(player, travelVector);
	}

	@Override
	public boolean canJump() {
		return !this.refuseToMove() && super.canJump();
	}

	@Override
	public void onPlayerJump(int jumpPower) {
		if (this.isSaddled() && this.getBeakCooldown() <= 0) {
			super.onPlayerJump(jumpPower);
		}
	}

	@Override
	public boolean isPushable() {
		return !this.refuseToMove() && super.isPushable();
	}

	public void setTargetBeakAnimProgress(float progress) {
		this.entityData.set(TARGET_BEAK_ANIM_PROGRESS, progress);
	}

	private float getTargetBeakAnimProgress() {
		return this.entityData.get(TARGET_BEAK_ANIM_PROGRESS);
	}

	public float getBeakAnimProgress(float delta) {
		return Mth.lerp(delta, this.prevBeakAnimProgress, this.beakAnimProgress);
	}

	public void setTargetPassengerProgress(float progress) {
		this.entityData.set(TARGET_PASSENGER_PROGRESS, progress);
	}

	private float getTargetPassengerProgress() {
		return this.entityData.get(TARGET_PASSENGER_PROGRESS);
	}

	public float getTargetPassengerProgress(float delta) {
		return Mth.lerp(delta, this.prevPassengerProgress, this.passengerProgress);
	}

	private void setAttacking(boolean isAttacking) {
		this.entityData.set(IS_ATTACKING, isAttacking);
	}

	private boolean isAttacking() {
		return this.entityData.get(IS_ATTACKING);
	}

	private void setBeakCooldown(int cooldown) {
		this.entityData.set(BEAK_COOLDOWN, cooldown);
	}

	private int getBeakCooldown() {
		return this.entityData.get(BEAK_COOLDOWN);
	}

	private void setStuckTicks(int stuckTicks) {
		this.entityData.set(STUCK_TICKS, stuckTicks);
	}

	private int getStuckTicks() {
		return this.entityData.get(STUCK_TICKS);
	}

	private void clampHeadRotationToBody(@NotNull Entity entity, float maxYRot) {
		float f = entity.getYHeadRot();
		float g = Mth.wrapDegrees(this.yBodyRot - f);
		float h = Mth.clamp(Mth.wrapDegrees(this.yBodyRot - f), -maxYRot, maxYRot);
		float i = f + g - h;
		entity.setYHeadRot(i);
	}

	public Vec3 getBeakPos() {
		Vec3 currentPos = this.position().add(0D, 1.1875D, 0D);
		Vec3 lookOrientation = this.getLookAngle();
		lookOrientation = lookOrientation.subtract(0D, lookOrientation.y(), 0D);
		Vec3 headBasePos = currentPos.add(lookOrientation.scale(0.875D));
		Vec3 rotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 1.25D, this.beakAnimProgress * 180D);
		Vec3 beakPos = headBasePos.add(0, rotPos.x(), 0).add(lookOrientation.scale(rotPos.z()));

		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, headBasePos.x, headBasePos.y, headBasePos.z, 1, 0, 0, 0, 0);
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, beakPos.x, beakPos.y, beakPos.z, 1, 0, 0, 0, 0);
		}
		return beakPos;
	}

	public boolean canGetHeadStuck() {
		Vec3 beakVec = this.getBeakPos();
		BlockPos beakPos = BlockPos.containing(beakVec);

		return this.level().getBlockState(beakPos).is(BlockTags.MINEABLE_WITH_SHOVEL);
	}

	@Override
	public float getStandingEyeHeight(@NotNull Pose pose, @NotNull EntityDimensions dimensions) {
		return dimensions.height * 0.8F;
	}

	@Override
	public Vector3f getPassengerAttachmentPoint(@NotNull Entity entity, @NotNull EntityDimensions dimensions, float scale) {
		return new Vector3f(0.0F, this.getPassengersRidingOffsetY(dimensions, scale), 0);
	}

	@Override
	public float getPassengersRidingOffsetY(@NotNull EntityDimensions entityDimensions, float f) {
		return entityDimensions.height * 0.875F * f;
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("BeakCooldown", this.getBeakCooldown());
		compound.putFloat("TargetBeakAnimProgress", this.getTargetBeakAnimProgress());
		compound.putFloat("TargetPassengerProgress", this.getTargetPassengerProgress());
		compound.putBoolean("IsAttacking", this.isAttacking());
		compound.putInt("StuckTicks", this.getStuckTicks());
		compound.putFloat("BeakAnimProgress", this.beakAnimProgress);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.setBeakCooldown(compound.getInt("BeakCooldown"));
		this.setTargetBeakAnimProgress(compound.getFloat("TargetBeakAnimProgress"));
		this.setTargetPassengerProgress(compound.getFloat("TargetPassengerProgress"));
		this.setAttacking(compound.getBoolean("IsAttacking"));
		this.setStuckTicks(compound.getInt("StuckTicks"));
		this.beakAnimProgress = compound.getFloat("BeakAnimProgress");
	}
}
