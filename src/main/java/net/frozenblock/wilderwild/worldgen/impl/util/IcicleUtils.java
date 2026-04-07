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

package net.frozenblock.wilderwild.worldgen.impl.util;

import net.frozenblock.wilderwild.block.IcicleBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;

public class IcicleUtils {

	public static boolean growIcicleOnRandomTick(ServerLevel level, BlockPos pos) {
		final BlockPos belowPos = pos.below();
		final BlockState belowState = level.getBlockState(belowPos);
		if (!belowState.isAir()) return false;
		SpeleothemUtils.growSpeleothem(
			level,
			belowPos,
			Direction.DOWN,
			1,
			false,
			WWBlocks.FRAGILE_ICE,
			WWBlocks.ICICLE,
			level.registryAccess().getOrThrow(WWBlockTags.ICICLE_REPLACEABLE)
		);
		return level.getBlockState(belowPos).is(WWBlocks.ICICLE);
	}

	public static boolean spreadIcicleOnRandomTick(ServerLevel level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		if (IcicleBlock.canSpreadTo(state)) return growIcicleOnRandomTick(level, pos);
		return false;
	}
}
