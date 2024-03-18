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

package net.frozenblock.wilderwild.world.impl.features;

import com.mojang.serialization.Codec;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import org.jetbrains.annotations.NotNull;

public class NematocystFeature extends MultifaceGrowthFeature {

	public NematocystFeature(@NotNull Codec<MultifaceGrowthConfiguration> codec) {
		super(codec);
	}

	public static boolean placeGrowthIfPossible(@NotNull WorldGenLevel level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull MultifaceGrowthConfiguration config, @NotNull RandomSource random, @NotNull List<Direction> directions) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (Direction direction : directions) {
			BlockState blockState = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
			if (blockState.is(config.canBePlacedOn)) {
				BlockState blockState2 = config.placeBlock.getStateForPlacement(state, level, pos, direction);
				if (blockState2 == null) {
					return false;
				}

				level.setBlock(pos, blockState2, 3);
				level.getChunk(pos).markPosForPostprocessing(pos);
				var optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState2, level, pos, direction, random, true);
				for (int i = 0; i < random.nextInt(2) + 3; ++i) {
					if (optional.isPresent()) {
						var spreadPos = optional.get();
						optional = config.placeBlock.getSpreader().spreadFromFaceTowardRandomDirection(blockState2, level, spreadPos.pos(), spreadPos.face(), random, true);
					} else {
						break;
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<MultifaceGrowthConfiguration> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		MultifaceGrowthConfiguration multifaceGrowthConfiguration = context.config();
		if (!isAirOrWater(worldGenLevel.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = multifaceGrowthConfiguration.getShuffledDirections(randomSource);
			if (placeGrowthIfPossible(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), multifaceGrowthConfiguration, randomSource, list)) {
				return true;
			} else {
				BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

				for (Direction direction : list) {
					mutableBlockPos.set(blockPos);
					List<Direction> list2 = multifaceGrowthConfiguration.getShuffledDirectionsExcept(randomSource, direction.getOpposite());

					for (int i = 0; i < multifaceGrowthConfiguration.searchRange; ++i) {
						mutableBlockPos.setWithOffset(blockPos, direction);
						BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
						if (!isAirOrWater(blockState) && !blockState.is(multifaceGrowthConfiguration.placeBlock)) {
							break;
						}

						if (placeGrowthIfPossible(worldGenLevel, mutableBlockPos, blockState, multifaceGrowthConfiguration, randomSource, list2)) {
							return true;
						}
					}
				}
				return false;
			}
		}
	}
}

