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
import net.frozenblock.wilderwild.block.SmallSpongeBlock;
import net.frozenblock.wilderwild.world.impl.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

public class SmallSpongeFeature extends Feature<SmallSpongeFeatureConfig> {

	public SmallSpongeFeature(@NotNull Codec<SmallSpongeFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(@NotNull WorldGenLevel level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull SmallSpongeFeatureConfig config, @NotNull List<Direction> directions) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();

		for (Direction direction : directions) {
			BlockState blockState = level.getBlockState(mutableBlockPos.setWithOffset(pos, direction));
			if (blockState.is(config.canPlaceOn)) {
				BlockState blockState2 = config.sponge.getStateForPlacement(state, level, pos, direction);
				if (blockState2 == null) {
					return false;
				}

				if (blockState2.getValue(SmallSpongeBlock.WATERLOGGED)) {
					level.setBlock(pos, blockState2, 3);
					level.getChunk(pos).markPosForPostprocessing(pos);
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isAirOrWater(@NotNull BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SmallSpongeFeatureConfig> context) {
		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();
		RandomSource randomSource = context.random();
		SmallSpongeFeatureConfig config = context.config();
		if (!isAirOrWater(worldGenLevel.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = config.shuffleDirections(randomSource);
			if (generate(worldGenLevel, blockPos, worldGenLevel.getBlockState(blockPos), config, list)) {
				return true;
			} else {
				BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();

				for (Direction direction : list) {
					mutableBlockPos.set(blockPos);
					List<Direction> list2 = config.shuffleDirections(randomSource, direction.getOpposite());

					for (int i = 0; i < config.searchRange; ++i) {
						mutableBlockPos.setWithOffset(blockPos, direction);
						BlockState blockState = worldGenLevel.getBlockState(mutableBlockPos);
						if (!isAirOrWater(blockState) && !blockState.is(config.sponge)) {
							break;
						}

						if (generate(worldGenLevel, mutableBlockPos, blockState, config, list2)) {
							return true;
						}
					}
				}
				return false;
			}
		}
	}
}

