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
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
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
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class Ostrich extends AbstractHorse implements PlayerRideableJumping, Saddleable {
	private static final EntityDataAccessor<Float> BEAK_ANIM_PROGRESS = SynchedEntityData.defineId(Ostrich.class, EntityDataSerializers.FLOAT);
	private int beakCooldown;
	public float targetBeakAnimProgress;
	private float prevBeakAnimProgress;
	private boolean isAttacking;

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
		this.entityData.define(BEAK_ANIM_PROGRESS, 0F);
	}

	@Override
	public void tick() {
		this.prevBeakAnimProgress = this.getBeakAnimProgress();
		super.tick();
		/*
		if (this.isDashing() && this.dashCooldown < 50 && (this.onGround() || this.isInLiquid() || this.isPassenger())) {
			this.setDashing(false);
		}
		 */

		if (this.beakCooldown > 0) {
			this.beakCooldown -= 1;
			if (this.beakCooldown == 0) {
				this.level().playSound(null, this.blockPosition(), SoundEvents.CAMEL_DASH_READY, SoundSource.NEUTRAL, 1.0F, 1.0F);
				this.targetBeakAnimProgress = 0F;
			}
		}

		if (this.refuseToMove()) {
			this.clampHeadRotationToBody(this, 0F);
		}

		if (!this.level().isClientSide) {
			float beakAnimProgress = this.getBeakAnimProgress();
			this.setBeakAnimProgress(beakAnimProgress + ((this.targetBeakAnimProgress - beakAnimProgress) * 0.3F));
		}

		this.getBeakPos();
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
		this.beakCooldown = 100;
		this.isAttacking = true;
		this.hasImpulse = true;
	}

	@Override
	public void handleStartJump(int jumpPower) {
		WilderSharedConstants.log("OSTRICH POWER " + jumpPower, true);
		this.beakCooldown = 100;
		this.playSound(SoundEvents.CAMEL_DASH, 1.0F, this.getVoicePitch());
		this.gameEvent(GameEvent.ENTITY_ACTION);
		this.targetBeakAnimProgress = 0.5F;
	}

	@Override
	public void handleStopJump() {
	}

	@Override
	public int getJumpCooldown() {
		return this.beakCooldown;
	}

	public boolean refuseToMove() {
		return false;
	}

	@Override
	public float getRiddenSpeed(@NotNull Player player) {
		float f = player.isSprinting() && this.getJumpCooldown() == 0 ? 0.1F : 0.0F;
		return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) + f;
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
		if (this.isSaddled() && this.beakCooldown <= 0) {
			super.onPlayerJump(jumpPower);
		}
	}

	@Override
	public boolean isPushable() {
		return super.isPushable();
	}

	public boolean isAttacking() {
		return this.isAttacking;
	}

	public void setBeakAnimProgress(float progress) {
		this.entityData.set(BEAK_ANIM_PROGRESS, progress);
	}

	private float getBeakAnimProgress() {
		return this.entityData.get(BEAK_ANIM_PROGRESS);
	}

	public float getBeakAnimProgress(float delta) {
		return Mth.lerp(delta, this.prevBeakAnimProgress, this.getBeakAnimProgress());
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
		Vec3 rotPos = AdvancedMath.rotateAboutX(Vec3.ZERO, 1.125D, this.getBeakAnimProgress() * 180D);
		Vec3 beakPos = headBasePos.add(0, rotPos.x(), 0).add(lookOrientation.scale(rotPos.z()));

		if (this.level() instanceof ServerLevel serverLevel) {
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, headBasePos.x, headBasePos.y, headBasePos.z, 1, 0, 0, 0, 0);
			serverLevel.sendParticles(FrozenParticleTypes.DEBUG_POS, beakPos.x, beakPos.y, beakPos.z, 1, 0, 0, 0, 0);
		}
		return beakPos;
	}

	public boolean canGetHeadStuck() {
		if (!this.onGround()) {
			return false;
		}

		Vec3 beakVec = this.getBeakPos();
		BlockPos beakPos = BlockPos.containing(beakVec);

		return this.onGround()
			&& this.level().getBlockState(beakPos).is(BlockTags.MINEABLE_WITH_SHOVEL);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag compound) {
		super.addAdditionalSaveData(compound);
		compound.putInt("BeakCooldown", this.beakCooldown);
		compound.putFloat("TargetBeakAnimProgress", this.targetBeakAnimProgress);
		compound.putFloat("BeakAnimProgress", this.getBeakAnimProgress());
		compound.putBoolean("IsAttacking", this.isAttacking());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag compound) {
		super.readAdditionalSaveData(compound);
		this.beakCooldown = compound.getInt("BeakCooldown");
		this.targetBeakAnimProgress = compound.getFloat("TargetBeakAnimProgress");
		this.setBeakAnimProgress(compound.getFloat("BeakAnimProgress"));
		this.isAttacking = compound.getBoolean("IsAttacking");
	}
}
