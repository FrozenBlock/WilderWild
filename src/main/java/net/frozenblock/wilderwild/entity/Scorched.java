/*
 * Copyright 2023-2024 FrozenBlock
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

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.ai.scorched.ScorchedNavigation;
import net.frozenblock.wilderwild.registry.WWMobEffects;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.IronGolem;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;
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
		this.setPathfindingMalus(BlockPathTypes.WATER, -1F);
		this.setPathfindingMalus(BlockPathTypes.LAVA, 0F);
		this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, 0F);
		this.setPathfindingMalus(BlockPathTypes.DAMAGE_FIRE, 0F);
	}

	@Override
	protected void registerGoals() {
		//this.goalSelector.addGoal(1, new FloatGoal(this));
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
		if (this.getBlockStateOn().is(Blocks.MAGMA_BLOCK) || this.getFeetBlockState().is(BlockTags.FIRE) || this.isInLava()) {
			this.addEffect(
				new MobEffectInstance(
					WWMobEffects.SCORCHING,
					200,
					0
				)
			);
		}
		this.scorchedInLava();
		this.checkInsideBlocks();
        float targetLavaAnimProgress = this.isInLava() ? 1F : 0F;
		this.prevLavaAnimProgress = this.lavaAnimProgress;
		this.lavaAnimProgress = this.lavaAnimProgress + (targetLavaAnimProgress - this.lavaAnimProgress) * 0.1F;
	}

	private void scorchedInLava() {
		if (this.isInLava()) {
			CollisionContext collisionContext = CollisionContext.of(this);
			if (
				collisionContext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true)
					&& !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)
			) {
				this.setOnGround(true);
			} else {
				this.addDeltaMovement(LAVA_FLOAT_VECTOR);
			}
		}
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.isInLava() ? WWSounds.ENTITY_SCORCHED_STEP_LAVA : WWSounds.ENTITY_SCORCHED_STEP, 0.15F, 1F);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return WWSounds.ENTITY_SCORCHED_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return WWSounds.ENTITY_SCORCHED_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return WWSounds.ENTITY_SCORCHED_DEATH;
	}

	@Override
	protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {
		this.checkInsideBlocks();
		if (this.isInLava()) {
			this.resetFallDistance();
		} else {
			super.checkFallDamage(y, onGround, state, pos);
		}
	}

	@Override
	protected int calculateFallDamage(float fallDistance, float damageMultiplier) {
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
		if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
			return false;
		}
		DimensionType dimensionType = level.dimensionType();
		int j = level.getLevel().isThundering() ? level.getBrightness(LightLayer.SKY, pos) - 10 : level.getBrightness(LightLayer.SKY, pos);
		return j <= dimensionType.monsterSpawnLightTest().sample(random);
	}

	public static boolean checkScorchedSpawnRules(EntityType<? extends Scorched> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
		if (!MobSpawnType.isSpawner(spawnType) && !WWEntityConfig.get().scorched.spawnScorched) return false;
		if (MobSpawnType.ignoresLightRequirements(spawnType) || Scorched.isDarkEnoughToSpawn(level, pos, random)) {
			return true;
		}
		return false;
	}

}
