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

package net.frozenblock.wilderwild.block.impl;

import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.NotNull;

public class SnowloggingUtils {
	public static final IntegerProperty SNOW_LAYERS = RegisterProperties.SNOW_LAYERS;
	public static final int MAX_LAYERS = 8;

	public static boolean supportsSnowlogging(@NotNull BlockState state) {
		return state.hasProperty(SNOW_LAYERS);
	}

	public static int getSnowLayers(@NotNull BlockState state) {
		return state.getValue(SNOW_LAYERS);
	}

	public static boolean isSnowlogged(BlockState state) {
		return supportsSnowlogging(state) && getSnowLayers(state) > 0;
	}

	@NotNull
	public static BlockState getSnowEquivalent(BlockState state) {
		return Blocks.SNOW.defaultBlockState().setValue(BlockStateProperties.LAYERS, Math.max(1, getSnowLayers(state)));
	}

	public static boolean canBeReplacedWithSnow(BlockState state, BlockPlaceContext context) {
		int layers;
		return (SnowloggingUtils.supportsSnowlogging(state) && context.getItemInHand().is(Blocks.SNOW.asItem())) &&
			Blocks.SNOW.canSurvive(Blocks.SNOW.defaultBlockState(), context.getLevel(), context.getClickedPos())
			&& ((layers = SnowloggingUtils.getSnowLayers(state)) <= 0 || (context.replacingClickedOnBlock() && context.getClickedFace() == Direction.UP && layers < MAX_LAYERS));
	}

	@NotNull
	public static BlockState onUpdateShape(BlockState state, LevelAccessor level, BlockPos pos) {
		if (isSnowlogged(state)) {
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (!Blocks.SNOW.canSurvive(snowEquivalent, level, pos)) {
				level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(snowEquivalent));
				state = state.setValue(SNOW_LAYERS, 0);
			}
		}
		return state;
	}

}
