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

import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;

public class Scorched extends Spider {

	public Scorched(EntityType<? extends Spider> entityType, Level level) {
		super(entityType, level);
		//this.setPathfindingMalus(PathType.WATER, -1F);
		this.setPathfindingMalus(PathType.LAVA, 0F);
		this.setPathfindingMalus(PathType.DANGER_FIRE, 0F);
		this.setPathfindingMalus(PathType.DAMAGE_FIRE, 0F);
	}

	@Override
	protected void registerGoals() {
		//this.goalSelector.addGoal(1, new FloatGoal(this));
		this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Armadillo.class, 6.0F, 1.0, 1.2,
			(livingEntity) -> !((Armadillo)livingEntity).isScared()));
		this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1D, true));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
	}

	@Override
	public void tick() {
		super.tick();
		this.floatScorched();
	}

	private void floatScorched() {
		if (this.isInLava()) {
			CollisionContext collisionContext = CollisionContext.of(this);
			if (collisionContext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
				this.setOnGround(true);
			} else {
				this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0D, 0.05D, 0D));
			}
		}
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.isInLava() ? SoundEvents.STRIDER_STEP_LAVA : SoundEvents.STRIDER_STEP, 1F, 1F);
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
	public boolean canStandOnFluid(@NotNull FluidState fluidState) {
		return fluidState.is(FluidTags.LAVA);
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	public float getWalkTargetValue(BlockPos pos, @NotNull LevelReader level) {
        return 0F;
	}

	@Override
	public boolean checkSpawnRules(LevelAccessor level, MobSpawnType reason) {
		return true;
	}

	public static boolean isDarkEnoughToSpawn(@NotNull ServerLevelAccessor level, BlockPos pos, @NotNull RandomSource random) {
		int skyBrightness = level.getBrightness(LightLayer.SKY, pos);
		if (skyBrightness > random.nextInt(32) || skyBrightness > 7) {
			return false;
		} else {
			int i = 11;
			if (level.getBrightness(LightLayer.BLOCK, pos) > i) {
				return false;
			} else {
				int j = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
				return j <= i;
			}
		}
	}

	public static boolean checkScorchedSpawnRules(EntityType<? extends Scorched> type, @NotNull ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getDifficulty() == Difficulty.PEACEFUL) return false;
		if (MobSpawnType.ignoresLightRequirements(spawnType) || isDarkEnoughToSpawn(level, pos, random)) {
			BlockPos blockPos = pos.below();
			BlockState state = level.getBlockState(blockPos);
			return spawnType == MobSpawnType.SPAWNER || (state.isValidSpawn(level, blockPos, type) || state.is(WilderBlockTags.SCORCHED_ALWAYS_SPAWNABLE));
		}
		return false;
	}


}
