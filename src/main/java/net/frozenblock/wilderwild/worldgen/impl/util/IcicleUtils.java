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

public class IcicleUtils {

	public static boolean isEmptyOrWater(LevelAccessor level, BlockPos pos) {
		return level.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
	}

	public static void buildBaseToTipColumn(Direction direction, int length, boolean merge, Consumer<BlockState> consumer) {
		if (length >= 3) {
			consumer.accept(createIcicle(direction, DripstoneThickness.BASE));
			for (int j = 0; j < length - 3; j++) consumer.accept(createIcicle(direction, DripstoneThickness.MIDDLE));
		}
		if (length >= 2) consumer.accept(createIcicle(direction, DripstoneThickness.FRUSTUM));
		if (length >= 1) consumer.accept(createIcicle(direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP));
	}

	public static boolean growIcicle(LevelAccessor level, BlockPos pos, Direction direction, int i, boolean merge) {
		if (!isIcicleBase(level.getBlockState(pos.relative(direction.getOpposite())))) return false;

		BlockPos.MutableBlockPos mutable = pos.mutable();
		buildBaseToTipColumn(direction, i, merge, state -> {
			if (state.is(WWBlocks.ICICLE)) state = state.setValue(IcicleBlock.WATERLOGGED, level.isWaterAt(mutable));

			level.setBlock(mutable, state, Block.UPDATE_CLIENTS);
			mutable.move(direction);
		});
		return true;
	}

	public static boolean placeIceBlockIfPossible(LevelAccessor level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (!state.is(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE)) return false;
		level.setBlock(pos, WWBlocks.FRAGILE_ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
		return true;
	}

	private static BlockState createIcicle(Direction direction, DripstoneThickness thickness) {
		return WWBlocks.ICICLE
			.defaultBlockState()
			.setValue(PointedDripstoneBlock.TIP_DIRECTION, direction)
			.setValue(PointedDripstoneBlock.THICKNESS, thickness);
	}

	public static boolean isIcicleBase(BlockState state) {
		return state.is(WWBlockTags.ICICLE_GROWS_WHEN_UNDER) || state.is(BlockTags.ICE) || state.is(WWBlockTags.CAVE_ICE_REPLACEABLE);
	}

	public static boolean growIcicleOnRandomTick(ServerLevel level, BlockPos pos) {
		final BlockPos belowPos = pos.below();
		final BlockState belowState = level.getBlockState(belowPos);
		if (belowState.isAir()) return IcicleUtils.growIcicle(level, belowPos, Direction.DOWN, 1, false);
		return false;
	}

	public static boolean spreadIcicleOnRandomTick(ServerLevel level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (IcicleBlock.canSpreadTo(state)) return growIcicleOnRandomTick(level, pos);
		return false;
	}
}
