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

package net.frozenblock.wilderwild.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.Nullable;

public final class WWSpawnTypes {
	public static final SpawnPlacementType ON_GROUND_OR_IN_ALGAE = new SpawnPlacementType() {
		@Override
		public boolean isSpawnPositionOk(LevelReader level, BlockPos pos, @Nullable EntityType<?> entityType) {
			if (SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(level, pos, entityType)) return true;

			if (entityType != null && level.getWorldBorder().isWithinBounds(pos)) {
				final BlockPos abovePos = pos.above();
				return level.getFluidState(pos).is(FluidTags.WATER) && level.getBlockState(abovePos).is(WWBlocks.ALGAE);
			}
			return false;
		}

		@Override
		public BlockPos adjustSpawnPosition(LevelReader level, BlockPos pos) {
			return SpawnPlacementTypes.ON_GROUND.adjustSpawnPosition(level, pos);
		}
	};

	public static final SpawnPlacementType SCORCHED = new SpawnPlacementType() {
		@Override
		public boolean isSpawnPositionOk(LevelReader level, BlockPos pos, @Nullable EntityType<?> entityType) {
			if (entityType != null && level.getWorldBorder().isWithinBounds(pos)) {
				final BlockPos belowPos = pos.below();
				final BlockState belowState = level.getBlockState(belowPos);
				return !belowState.isValidSpawn(level, belowPos, entityType) && !this.isSurfaceLavaOrMagmaOrGabbro(level, pos, belowState)
					? false
					: this.isValidEmptySpawnBlock(level, pos, entityType);
			} else {
				return false;
			}
		}

		private boolean isSurfaceLavaOrMagmaOrGabbro(LevelReader level, BlockPos blockPos, BlockState belowState) {
			final BlockState state = level.getBlockState(blockPos);
			return (belowState.getFluidState().is(FluidTags.LAVA) || belowState.is(Blocks.MAGMA_BLOCK) || belowState.is(WWBlocks.GABBRO))
				&& !state.getFluidState().is(FluidTags.LAVA)
				&& !(state.is(Blocks.MAGMA_BLOCK) || state.is(WWBlocks.GABBRO));
		}

		private boolean isValidEmptySpawnBlock(LevelReader level, BlockPos blockPos, EntityType<?> entityType) {
			final BlockState state = level.getBlockState(blockPos);
			boolean isSafeBurning = state.is(BlockTags.FIRE);
			return isSafeBurning || NaturalSpawner.isValidEmptySpawnBlock(level, blockPos, state, state.getFluidState(), entityType);
		}

		@Override
		public BlockPos adjustSpawnPosition(LevelReader level, BlockPos pos) {
			final BlockPos belowPos = pos.below();
			return level.getBlockState(belowPos).isPathfindable(PathComputationType.LAND) ? belowPos : pos;
		}
	};
}
