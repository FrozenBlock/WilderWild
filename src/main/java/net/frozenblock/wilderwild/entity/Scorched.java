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

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.scorched.ScorchedNavigation;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class Scorched extends Spider {
	public static final Vec3 LAVA_FLOAT_VECTOR = new Vec3(0D, 0.085D, 0D);
	public static final int FALL_DAMAGE_RESISTANCE = 8;
    private float lavaAnimProgress;
	private float prevLavaAnimProgress;

	public Scorched(EntityType<? extends Spider> entityType, Level level) {
		super(entityType, level);
		this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.LAVA, 0F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, 0F);
		this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0F);
	}

	@Override
	protected void registerGoals() {
		//this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Armadillo.class, 6F, 1D, 1.2D,
			(livingEntity) -> !((Armadillo)livingEntity).isScared()));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1D, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8D));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@NotNull
	@Override
	protected PathNavigation createNavigation(Level level) {
		return new ScorchedNavigation(this, level);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getBlockStateOn().is(Blocks.MAGMA_BLOCK) || this.getInBlockState().is(BlockTags.FIRE) || this.isInLava()) {
			this.addEffect(
				new MobEffectInstance(
					WWMobEffects.SCORCHING,
					200,
					0
				)
			);
		}
		this.scorchedInLava();
		this.applyEffectsFromBlocks();
        float targetLavaAnimProgress = this.isInLava() ? 1F : 0F;
		this.prevLavaAnimProgress = this.lavaAnimProgress;
		this.lavaAnimProgress = this.lavaAnimProgress + (targetLavaAnimProgress - this.lavaAnimProgress) * 0.1F;
	}

	private void scorchedInLava() {
		if (this.isInLava()) {
			CollisionContext collisionContext = CollisionContext.of(this);
			if (
				collisionContext.isAbove(LiquidBlock.SHAPE_STABLE, this.blockPosition(), true)
					&& !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)
			) {
				this.setOnGround(true);
			} else {
				this.addDeltaMovement(LAVA_FLOAT_VECTOR);
			}
		}
	}

	@Override
	public boolean doHurtTarget(ServerLevel serverLevel, Entity entity) {
		boolean hurtTarget =  super.doHurtTarget(serverLevel, entity);
		if (hurtTarget) {
			entity.igniteForSeconds(this.hasEffect(WWMobEffects.SCORCHING) ? 4 : 3);
		}
		return hurtTarget;
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.isInLava() ? WWSounds.ENTITY_SCORCHED_STEP_LAVA : WWSounds.ENTITY_SCORCHED_STEP, 0.15F, 1F);
	}

	@Override
	protected @NotNull SoundEvent getAmbientSound() {
		return WWSounds.ENTITY_SCORCHED_AMBIENT;
	}

	@Override
	protected @NotNull SoundEvent getHurtSound(DamageSource damageSource) {
		return WWSounds.ENTITY_SCORCHED_HURT;
	}

	@Override
	protected @NotNull SoundEvent getDeathSound() {
		return WWSounds.ENTITY_SCORCHED_DEATH;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
		this.applyEffectsFromBlocks();
		if (this.isInLava()) {
			this.resetFallDistance();
		} else {
			super.checkFallDamage(y, onGround, state, pos);
		}
	}

	@Override
	protected int calculateFallDamage(double fallDistance, float damageMultiplier) {
		return super.calculateFallDamage(fallDistance, damageMultiplier) - FALL_DAMAGE_RESISTANCE;
	}

	@Override
	public boolean canStandOnFluid(@NotNull FluidState fluidState) {
		return fluidState.is(FluidTags.LAVA);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	public float getLavaAnimProgress(float partialTick) {
		return Mth.lerp(partialTick, this.prevLavaAnimProgress, this.lavaAnimProgress);
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, @NotNull LevelReader level) {
		BlockState state = level.getBlockState(pos);
		boolean prefers = state.getFluidState().is(FluidTags.LAVA) || state.is(Blocks.MAGMA_BLOCK);
		return prefers ? 1.5F : 1F;
	}

	@Override
	public boolean checkSpawnObstruction(@NotNull LevelReader level) {
		return level.isUnobstructed(this);
	}

	public static boolean isDarkEnoughToSpawn(@NotNull ServerLevelAccessor level, BlockPos pos, @NotNull RandomSource random) {
		if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) return false;
		DimensionType dimensionType = level.dimensionType();
		int skyLight = level.getLevel().isThundering() ? level.getBrightness(LightLayer.SKY, pos) - 10 : level.getBrightness(LightLayer.SKY, pos);
		return skyLight <= dimensionType.monsterSpawnLightTest().sample(random);
	}

	public static boolean checkScorchedSpawnRules(
		EntityType<? extends Scorched> type, @NotNull ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random
	) {
		if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
		if (!EntitySpawnReason.isSpawner(spawnReason) && !WWEntityConfig.get().scorched.spawnScorched) return false;
		return EntitySpawnReason.ignoresLightRequirements(spawnReason) || Scorched.isDarkEnoughToSpawn(level, pos, random);
	}

}
