/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.entity.ai.penguin;

import java.util.function.Function;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PenguinLongJump<E extends Penguin> extends LongJumpToRandomPos<E> {

	public PenguinLongJump(UniformInt uniformInt, int i, int j, float f, Function<E, SoundEvent> function) {
		super(uniformInt, i, j, f, function, PenguinLongJump::penguinAcceptableLandingSpot);
	}

	public static <E extends Mob> boolean penguinAcceptableLandingSpot(@NotNull E mob, @NotNull BlockPos blockPos) {
		Level level = mob.level();
		BlockPos blockPos2 = blockPos.below();
		BlockState belowState = level.getBlockState(blockPos2);
		return Math.sqrt(mob.distanceToSqr(Vec3.atCenterOf(blockPos))) > 6D && (belowState.isSolidRender() || belowState.getFluidState().is(FluidTags.WATER)
			&& mob.getPathfindingMalus(WalkNodeEvaluator.getPathTypeStatic(mob, blockPos)) == 0F);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel serverLevel, @NotNull E mob) {
		boolean bl = mob.isInWater() && !mob.isInLava() && !serverLevel.getBlockState(mob.blockPosition()).is(Blocks.HONEY_BLOCK);
		if (!bl) {
			mob.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.timeBetweenLongJumps.sample(serverLevel.random) / 2);
		}

		return bl;
	}

	@Override
	protected boolean canStillUse(ServerLevel serverLevel, E mob, long l) {
		boolean bl = this.initialPosition.isPresent()
			&& this.initialPosition.get().equals(mob.position())
			&& this.findJumpTries > 0
			&& (this.chosenJump != null || !this.jumpCandidates.isEmpty());
		if (!bl && mob.getBrain().getMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isEmpty()) {
			mob.getBrain().setMemory(MemoryModuleType.LONG_JUMP_COOLDOWN_TICKS, this.timeBetweenLongJumps.sample(serverLevel.random) / 2);
			mob.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
		}

		return bl;
	}

}
