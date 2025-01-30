/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WWSpawnTypes {
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
}
