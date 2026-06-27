/*
 * Copyright 2025-2026 FrozenBlock
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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

public final class WWSpawnTypes {
	public static final SpawnPlacementType ON_GROUND_OR_IN_ALGAE = new SpawnPlacementType() {
		@Override
		public boolean isSpawnPositionOk(LevelReader level, BlockPos pos, @Nullable EntityType<?> type) {
			if (SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(level, pos, type)) return true;
			if (type == null || !level.getWorldBorder().isWithinBounds(pos)) return false;

			final BlockPos abovePos = pos.above();
			return level.getFluidState(pos).is(FluidTags.WATER) && level.getBlockState(abovePos).is(WWBlocks.ALGAE);
		}

		@Override
		public BlockPos adjustSpawnPosition(LevelReader level, BlockPos candidate) {
			return SpawnPlacementTypes.ON_GROUND.adjustSpawnPosition(level, candidate);
		}
	};

	public static final SpawnPlacementType ON_GROUND_OR_IN_LAVA = new SpawnPlacementType() {
		@Override
		public boolean isSpawnPositionOk(LevelReader level, BlockPos pos, @Nullable EntityType<?> type) {
			return SpawnPlacementTypes.ON_GROUND.isSpawnPositionOk(level, pos, type) || SpawnPlacementTypes.IN_LAVA.isSpawnPositionOk(level, pos, type);
		}

		@Override
		public BlockPos adjustSpawnPosition(LevelReader level, BlockPos candidate) {
			return SpawnPlacementTypes.ON_GROUND.adjustSpawnPosition(level, candidate);
		}
	};
}
