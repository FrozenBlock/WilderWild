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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import java.util.List;
import net.frozenblock.wilderwild.block.ShelfFungiBlock;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.ShelfFungiFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import org.jetbrains.annotations.NotNull;

public class ShelfFungiFeature extends Feature<ShelfFungiFeatureConfig> {

	public ShelfFungiFeature(@NotNull Codec<ShelfFungiFeatureConfig> codec) {
		super(codec);
	}

	public static boolean generate(@NotNull WorldGenLevel level, @NotNull BlockPos pos, @NotNull ShelfFungiFeatureConfig config, @NotNull RandomSource random, @NotNull List<Direction> directions) {
		MutableBlockPos mutable = pos.mutable();

		Direction placementDirection = null;
		for (Direction direction : Direction.values()) {
			BlockState blockState = level.getBlockState(mutable.setWithOffset(pos, direction));
			if (blockState.is(config.canPlaceOn)) {
				if (direction.getAxis() == Direction.Axis.Y) {
					placementDirection = Direction.Plane.HORIZONTAL.getRandomDirection(random);
				} else {
					placementDirection = direction.getOpposite();
				}
				break;
			}
		}

		if (placementDirection != null) {
			level.setBlock(pos, config.fungus.defaultBlockState()
				.setValue(ShelfFungiBlock.FACING, placementDirection)
				.setValue(ShelfFungiBlock.FACE, ShelfFungiBlock.getFace(placementDirection.getOpposite()))
				.setValue(ShelfFungiBlock.STAGE, random.nextInt(3) + 1), Block.UPDATE_ALL);
			level.getChunk(pos).markPosForPostprocessing(pos);
			return true;
		}

		return false;
	}

	private static boolean isAirOrWater(@NotNull BlockState state) {
		return state.isAir() || state.is(Blocks.WATER);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<ShelfFungiFeatureConfig> context) {
		WorldGenLevel structureWorldAccess = context.level();
		RandomSource abstractRandom = context.random();
		BlockPos blockPos = context.origin().above(abstractRandom.nextInt(0, 4));
		ShelfFungiFeatureConfig shelfFungusFeatureConfig = context.config();
		if (!isAirOrWater(structureWorldAccess.getBlockState(blockPos))) {
			return false;
		} else {
			List<Direction> list = shelfFungusFeatureConfig.shuffleDirections(abstractRandom);
			if (generate(structureWorldAccess, blockPos, shelfFungusFeatureConfig, abstractRandom, list)) {
				return true;
			} else {
				MutableBlockPos mutable = blockPos.mutable();

				for (Direction direction : list) {
					mutable.set(blockPos);
					List<Direction> list2 = shelfFungusFeatureConfig.shuffleDirections(abstractRandom, direction.getOpposite());

					for (int i = 0; i < shelfFungusFeatureConfig.searchRange; ++i) {
						mutable.setWithOffset(blockPos, direction);
						BlockState blockState = structureWorldAccess.getBlockState(mutable);
						if (!isAirOrWater(blockState) && !blockState.is(shelfFungusFeatureConfig.fungus)) {
							break;
						}

						if (generate(structureWorldAccess, mutable, shelfFungusFeatureConfig, abstractRandom, list2)) {
							return true;
						}
					}
				}
				return false;
			}
		}
	}
}
