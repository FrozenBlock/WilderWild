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

package net.frozenblock.wilderwild.worldgen.impl.util;

import java.util.function.Consumer;
import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import org.jetbrains.annotations.NotNull;

public class IcicleUtils {

	public static boolean isEmptyOrWater(@NotNull LevelAccessor levelAccessor, BlockPos blockPos) {
		return levelAccessor.isStateAtPosition(blockPos, DripstoneUtils::isEmptyOrWater);
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

	public static boolean growIcicle(@NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull Direction direction, int i, boolean merge) {
		if (isIcicleBase(levelAccessor.getBlockState(blockPos.relative(direction.getOpposite())))) {
			BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
			buildBaseToTipColumn(direction, i, merge, blockState -> {
				if (blockState.is(WWBlocks.ICICLE)) {
					blockState = blockState.setValue(IcicleBlock.WATERLOGGED, levelAccessor.isWaterAt(mutableBlockPos));
				}

				levelAccessor.setBlock(mutableBlockPos, blockState, Block.UPDATE_CLIENTS);
				mutableBlockPos.move(direction);
			});
			return true;
		}
		return false;
	}

	public static boolean placeIceBlockIfPossible(@NotNull LevelAccessor levelAccessor, BlockPos blockPos) {
		BlockState blockState = levelAccessor.getBlockState(blockPos);
		if (blockState.is(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE)) {
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

	public static boolean isIcicleBase(@NotNull BlockState blockState) {
		return blockState.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) || blockState.is(BlockTags.ICE) || blockState.is(WWBlockTags.CAVE_ICE_REPLACEABLE);
	}

	public static boolean growIcicleOnRandomTick(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
		BlockPos belowPos = blockPos.below();
		BlockState belowState = serverLevel.getBlockState(belowPos);
		if (belowState.isAir()) {
			return IcicleUtils.growIcicle(serverLevel, belowPos, Direction.DOWN, 1, false);
		}
		return false;
	}

	public static boolean spreadIcicleOnRandomTick(@NotNull ServerLevel serverLevel, @NotNull BlockPos blockPos) {
		BlockState blockState = serverLevel.getBlockState(blockPos);
		if (IcicleBlock.canSpreadTo(blockState)) {
			return growIcicleOnRandomTick(serverLevel, blockPos);
		}
		return false;
	}
}
