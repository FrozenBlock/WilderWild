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
 * You should have received a copy of the FrozenBlock Modding oasis License
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class WWSpawnTypes {
	public static final SpawnPlacementType ON_GROUND_OR_IN_ALGAE = new SpawnPlacementType() {
		@Override
		public boolean isSpawnPositionOk(LevelReader levelReader, BlockPos blockPos, @Nullable EntityType<?> entityType) {
			if (SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(levelReader, blockPos, entityType)) return true;

			if (entityType != null && levelReader.getWorldBorder().isWithinBounds(blockPos)) {
				BlockPos abovePos = blockPos.above();
				return levelReader.getFluidState(blockPos).is(FluidTags.WATER) && levelReader.getBlockState(abovePos).is(WWBlocks.ALGAE);
			}
			return false;
		}

		@Override
		public @NotNull BlockPos adjustSpawnPosition(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos) {
			return SpawnPlacementTypes.ON_GROUND.adjustSpawnPosition(levelReader, blockPos);
		}
	};

	public static final SpawnPlacementType SCORCHED = new SpawnPlacementType() {
		public boolean isSpawnPositionOk(LevelReader levelReader, BlockPos blockPos, @Nullable EntityType<?> entityType) {
			if (entityType != null && levelReader.getWorldBorder().isWithinBounds(blockPos)) {
				BlockPos belowPos = blockPos.below();
				BlockState belowState = levelReader.getBlockState(belowPos);
				return !belowState.isValidSpawn(levelReader, belowPos, entityType) && !this.isSurfaceLavaOrMagmaOrGabbro(levelReader, blockPos, belowState)
					? false
					: this.isValidEmptySpawnBlock(levelReader, blockPos, entityType);
			} else {
				return false;
			}
		}

		private boolean isSurfaceLavaOrMagmaOrGabbro(@NotNull LevelReader levelReader, BlockPos blockPos, @NotNull BlockState belowState) {
			BlockState blockState = levelReader.getBlockState(blockPos);
			return (belowState.getFluidState().is(FluidTags.LAVA) || belowState.is(Blocks.MAGMA_BLOCK) || belowState.is(WWBlocks.GABBRO))
				&& !blockState.getFluidState().is(FluidTags.LAVA)
				&& !(blockState.is(Blocks.MAGMA_BLOCK) || blockState.is(WWBlocks.GABBRO));
		}

		private boolean isValidEmptySpawnBlock(@NotNull LevelReader levelReader, BlockPos blockPos, EntityType<?> entityType) {
			BlockState blockState = levelReader.getBlockState(blockPos);
			boolean isSafeBurning = blockState.is(BlockTags.FIRE);
			return isSafeBurning || NaturalSpawner.isValidEmptySpawnBlock(levelReader, blockPos, blockState, blockState.getFluidState(), entityType);
		}

		public @NotNull BlockPos adjustSpawnPosition(@NotNull LevelReader levelReader, @NotNull BlockPos blockPos) {
			BlockPos belowPos = blockPos.below();
			return levelReader.getBlockState(belowPos).isPathfindable(PathComputationType.LAND) ? belowPos : blockPos;
		}
	};
}
