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

package net.frozenblock.wilderwild.worldgen.impl.util;

import java.util.function.Consumer;
import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import org.jetbrains.annotations.NotNull;

public class IcicleUtils {
	public static double getIcicleHeight(double d, double e, double f, double g) {
		if (d < g) {
			d = g;
		}

		double i = d / e * 0.384D;
		double j = 0.75D * Math.pow(i, 1.3333333333333333D);
		double k = Math.pow(i, 0.6666666666666666D);
		double l = 0.3333333333333333D * Math.log(i);
		double m = f * (j - k - l);
		m = Math.max(m, 0D);
		return m / 0.384D * e;
	}

	public static boolean isCircleMostlyEmbeddedInStone(WorldGenLevel worldGenLevel, BlockPos blockPos, int i) {
		if (isEmptyOrWaterOrLava(worldGenLevel, blockPos)) {
			return false;
		} else {
			float g = 6F / (float)i;

			for (float h = 0F; h < (float) (Math.PI * 2F); h += g) {
				int j = (int)(Mth.cos(h) * (float)i);
				int k = (int)(Mth.sin(h) * (float)i);
				if (isEmptyOrWaterOrLava(worldGenLevel, blockPos.offset(j, 0, k))) {
					return false;
				}
			}

			return true;
		}
	}

	public static boolean isEmptyOrWater(@NotNull LevelAccessor levelAccessor, BlockPos blockPos) {
		return levelAccessor.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWater);
	}

	public static boolean isEmptyOrWaterOrLava(@NotNull LevelAccessor levelAccessor, BlockPos blockPos) {
		return levelAccessor.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWaterOrLava);
	}

	public static void buildBaseToTipColumn(Direction direction, int length, boolean merge, Consumer<BlockState> consumer) {
		if (length >= 3) {
			consumer.accept(createIcicle(direction, DripstoneThickness.BASE));

			for (int j = 0; j < length - 3; j++) {
				consumer.accept(createIcicle(direction, DripstoneThickness.MIDDLE));
			}
		}

		if (length >= 2) {
			consumer.accept(createIcicle(direction, DripstoneThickness.FRUSTUM));
		}

		if (length >= 1) {
			consumer.accept(createIcicle(direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
		}
	}

	public static void growIcicle(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull Direction direction, int i, boolean merge) {
		if (isIcicleBase(levelAccessor.getBlockState(blockPos.relative(direction.getOpposite())))) {
			BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
			buildBaseToTipColumn(direction, i, merge, blockState -> {
				if (blockState.is(WWBlocks.ICICLE)) {
					blockState = blockState.setValue(IcicleBlock.WATERLOGGED, levelAccessor.isWaterAt(mutableBlockPos));
				}

				levelAccessor.setBlock(mutableBlockPos, blockState, Block.UPDATE_CLIENTS);
				mutableBlockPos.move(direction);
			});
		}
	}

	public static boolean placeIceBlockIfPossible(@NotNull LevelAccessor levelAccessor, BlockPos blockPos) {
		BlockState blockState = levelAccessor.getBlockState(blockPos);
		if (blockState.is(WWBlockTags.CAVE_ICE_REPLACEABLE)) {
			levelAccessor.setBlock(blockPos, WWBlocks.FRAGILE_ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
			return true;
		} else {
			return false;
		}
	}

	private static @NotNull BlockState createIcicle(Direction direction, DripstoneThickness icicleThickness) {
		return WWBlocks.ICICLE
			.defaultBlockState()
			.setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)
			.setValue(PointedDripstoneBlock.THICKNESS, icicleThickness);
	}

	public static boolean isIcicleBaseOrLava(BlockState blockState) {
		return isIcicleBase(blockState) || blockState.is(Blocks.LAVA);
	}

	public static boolean isIcicleBase(@NotNull BlockState blockState) {
		return blockState.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) || blockState.is(BlockTags.ICE) || blockState.is(WWBlockTags.CAVE_ICE_REPLACEABLE);
	}
}
