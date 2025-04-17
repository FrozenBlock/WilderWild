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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class TrunkPlacerHelper {

	public static @NotNull BlockState getLogBlockState(
		@NotNull LevelSimulatedReader level,
		@NotNull BlockStateProvider blockStateProvider,
		@NotNull BlockPos branchPos,
		@NotNull Direction direction,
		@NotNull RandomSource random
	) {
		BlockState state = blockStateProvider.getState(random, branchPos).trySetValue(BlockStateProperties.AXIS, direction.getAxis());
		if (state.hasProperty(BlockStateProperties.WATERLOGGED)) state = state.setValue(BlockStateProperties.WATERLOGGED, isWaterAt(level, branchPos));
		return state;
	}

	public static boolean isWaterAt(@NotNull LevelSimulatedReader level, @NotNull BlockPos blockpos) {
		return level.isFluidAtPosition(blockpos, fluidState -> fluidState.is(FluidTags.WATER));
	}

}
