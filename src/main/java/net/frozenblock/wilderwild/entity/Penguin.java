/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import java.util.Arrays;
import java.util.List;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.penguin.PenguinAi;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Unit;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;

public class Penguin extends Animal {
	private static final List<String> VALID_LINUX_NAMES = ImmutableList.of("Linux", "Tux", "Treetrain", "Treetrain1");
	public static final double BOAT_BOOST_SPEED = 1.7D;
	public AnimationState layDownAnimationState = new AnimationState();
	public AnimationState standUpAnimationState = new AnimationState();
	public AnimationState callAnimationState = new AnimationState();
	private float prevWadeProgress;
	private float wadeProgress;
	private float prevSlideProgress;
	private float slideProgress;

	public Penguin(EntityType<? extends Animal> entityType, Level level) {
		super(entityType, level);
		this.moveControl = new SmoothSwimmingMoveControl(this, 85, 30, 0.4F, 0.2F, true);
		this.lookControl = new SmoothSwimmingLookControl(this, 20);
	}

	@Override
	protected Brain.@NotNull Provider<Penguin> brainProvider() {
		return PenguinAi.brainProvider();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Penguin> getBrain() {
		return (Brain<Penguin>) super.getBrain();
	}

	@NotNull
	@Override
	@SuppressWarnings("unchecked")
	public Brain<Penguin> makeBrain(@NotNull Dynamic<?> dynamic) {
		return (Brain<Penguin>) PenguinAi.makeBrain(this, this.brainProvider().makeBrain(dynamic));
	}

	@NotNull
	public static AttributeSupplier.Builder createAttributes() {
		return Animal.createAnimalAttributes().add(Attributes.MAX_HEALTH, 12D)
			.add(Attributes.MOVEMENT_SPEED, 1D)
			.add(Attributes.STEP_HEIGHT, 1D)
			.add(Attributes.ATTACK_DAMAGE, 2D)
			.add(Attributes.SAFE_FALL_DISTANCE, 5D)
			.add(Attributes.WATER_MOVEMENT_EFFICIENCY, 0.5D);
	}

	public static boolean checkPenguinSpawnRules(
		EntityType<? extends Penguin> entityType,
		LevelAccessor level,
		EntitySpawnReason spawnReason,
		BlockPos pos,
		RandomSource random
	) {
		if (!WWEntityConfig.get().penguin.spawnPenguins) return false;
		return level.getBlockState(pos.below()).is(WWBlockTags.PENGUINS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
	}

	@Override
	protected @NotNull PathNavigation createNavigation(Level level) {
		return new AmphibiousPathNavigation(this, level);
	}

	@Override
	public void updateSwimming() {
		if (this.isSwimming()) {
			this.setSwimming(this.isInWater() && !this.isPassenger());
		} else {
			this.setSwimming(this.isUnderWater() && !this.isPassenger() && this.level().getFluidState(this.blockPosition()).is(FluidTags.WATER));
		}
	}

	@Override
	public void setSwimming(boolean bl) {
		super.setSwimming(bl);
		if (bl) this.setPose(Pose.STANDING);
	}

	@Override
	public void calculateEntityAnimation(boolean includeHeight) {
		super.calculateEntityAnimation(this.isSwimming() || includeHeight);
	}

	@Override
	public boolean isVisuallySwimming() {
		return this.isSwimming() && this.isUnderWater();
	}

	public boolean isTouchingWaterOrSwimming() {
		return this.isInWater() || this.isVisuallySwimming();
	}

	@Override
	public boolean isFood(@NotNull ItemStack itemStack) {
		return itemStack.is(WWItemTags.PENGUIN_FOOD);
	}

	public boolean hasAttackTarget() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET);
	}

	@Override
	public void spawnChildFromBreeding(@NotNull ServerLevel level, @NotNull Animal mate) {
		this.finalizeSpawnChildFromBreeding(level, mate, null);
		this.getBrain().setMemory(MemoryModuleType.IS_PREGNANT, Unit.INSTANCE);
	}

	public boolean isPregnant() {
		return this.getBrain().hasMemoryValue(MemoryModuleType.IS_PREGNANT);
	}

	public void revokePregnancy() {
		this.getBrain().eraseMemory(MemoryModuleType.IS_PREGNANT);
	}

	@Nullable
	@Override
	public Penguin getBreedOffspring(@NotNull ServerLevel level, @NotNull AgeableMob otherParent) {
		return WWEntityTypes.PENGUIN.create(level, EntitySpawnReason.BREEDING);
	}

	@Override
	public void tick() {
		super.tick();
		this.prevWadeProgress = this.wadeProgress;
		this.wadeProgress += ((this.isTouchingWaterOrSwimming() && !this.isUnderWater() ? 1F : 0F) - this.wadeProgress) * 0.175F;
		this.prevSlideProgress = this.slideProgress;
		this.slideProgress += ((this.hasPose(Pose.SLIDING) ? 1F : 0F) - this.slideProgress) * 0.175F;
	}

	@Contract("null->false")
	public boolean canTargetEntity(@Nullable Entity entity) {
		return entity instanceof LivingEntity livingEntity
			&& this.level() == livingEntity.level()
			&& !this.level().getDifficulty().equals(Difficulty.PEACEFUL)
			&& EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingEntity)
			&& !this.isAlliedTo(livingEntity)
			&& livingEntity.getType() != EntityType.ARMOR_STAND
			&& livingEntity.getType() != WWEntityTypes.PENGUIN
			&& !livingEntity.isInvulnerable()
			&& !livingEntity.isDeadOrDying()
			&& !livingEntity.isRemoved()
			&& this.level().getWorldBorder().isWithinBounds(livingEntity.getBoundingBox());
	}

	@Override
	public boolean killedEntity(ServerLevel level, LivingEntity entity, DamageSource source) {
		final boolean killed = super.killedEntity(level, entity, source);
		if (this.getBrain().isActive(Activity.FIGHT)) PenguinAi.addCallMemoryIfPenguinsClose(this);
		return killed;
	}

	public float getWadeProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevWadeProgress, this.wadeProgress);
	}

	public float getSlideProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevSlideProgress, this.slideProgress);
	}

	public boolean isSlidingOrSwimming() {
		return this.isSliding() || this.isSwimming();
	}

	public boolean isSliding() {
		return this.hasPose(Pose.SLIDING);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> entityDataAccessor) {
		if (DATA_POSE.equals(entityDataAccessor)) {
			switch (this.getPose()) {
				case SLIDING:
					this.layDownAnimationState.start(this.tickCount);
					this.standUpAnimationState.stop();
					this.callAnimationState.stop();
					break;
				case EMERGING:
					this.standUpAnimationState.start(this.tickCount);
					this.layDownAnimationState.stop();
					this.callAnimationState.stop();
					break;
				case ROARING:
					this.callAnimationState.start(this.tickCount);
					this.standUpAnimationState.stop();
					this.layDownAnimationState.stop();
					break;
				default:
					this.standUpAnimationState.stop();
					this.layDownAnimationState.stop();
					this.callAnimationState.stop();
			}
			this.refreshDimensions();
		}

		super.onSyncedDataUpdated(entityDataAccessor);
	}

	@Override
	public @NotNull EntityDimensions getDefaultDimensions(Pose pose) {
		EntityDimensions entityDimensions = super.getDefaultDimensions(pose);
		return this.isSliding() ? EntityDimensions.fixed(entityDimensions.width(), 0.5F) : entityDimensions;
	}

	@Override
	protected @Nullable SoundEvent getAmbientSound() {
		return this.isLinux() ? WWSounds.ENTITY_LINUX_IDLE : WWSounds.ENTITY_PENGUIN_IDLE;
	}

	@Override
	protected @Nullable SoundEvent getHurtSound(DamageSource source) {
		return this.isLinux() ? WWSounds.ENTITY_LINUX_HURT : WWSounds.ENTITY_PENGUIN_HURT;
	}

	@Override
	protected @Nullable SoundEvent getDeathSound() {
		return this.isLinux() ? WWSounds.ENTITY_LINUX_DEATH : WWSounds.ENTITY_PENGUIN_DEATH;
	}

	@Override
	public int getAmbientSoundInterval() {
		return 200;
	}

	@Override
	protected float nextStep() {
		if (this.isSliding()) return super.nextStep();
		if (this.isSwimming()) return this.moveDist + 1F;
		return this.moveDist + 0.4F;
	}

	@Override
	protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
		if (!this.isSliding()) {
			this.playSound(this.isLinux() ? WWSounds.ENTITY_LINUX_STEP : WWSounds.ENTITY_PENGUIN_STEP, 0.1F, 1F);
			return;
		}
		super.playStepSound(pos, state);
	}

	@Override
	protected void customServerAiStep(@NotNull ServerLevel level) {
		final ProfilerFiller profilerFiller = Profiler.get();
		profilerFiller.push("penguinBrain");
		this.getBrain().tick(level, this);
		profilerFiller.pop();
		profilerFiller.push("penguinActivityUpdate");
		PenguinAi.updateActivity(this);
		profilerFiller.pop();
		super.customServerAiStep(level);
	}

	@Override
	public void addAdditionalSaveData(@NotNull ValueOutput valueOutput) {
		super.addAdditionalSaveData(valueOutput);
		valueOutput.putString("EntityPose", this.getPose().name());
	}

	@Override
	public void readAdditionalSaveData(@NotNull ValueInput valueInput) {
		super.readAdditionalSaveData(valueInput);
		valueInput.getString("EntityPose").ifPresent(entityPose -> {
			if (Arrays.stream(Pose.values()).anyMatch(pose -> pose.name().equals(entityPose))) {
				this.setPose(Pose.valueOf(entityPose));
			}
		});
	}

	public boolean isLinux() {
		String string = ChatFormatting.stripFormatting(this.getName().getString());
		return VALID_LINUX_NAMES.stream().anyMatch(string::equalsIgnoreCase);
	}
}
