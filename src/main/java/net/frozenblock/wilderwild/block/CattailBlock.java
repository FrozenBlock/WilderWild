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

package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.block.api.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CattailBlock extends WaterloggableTallFlowerBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public CattailBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState blockState = super.getStateForPlacement(context);
		return blockState != null ?
			SnowloggingUtils.getSnowPlacementState(blockState, context) : null;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(WWBlockTags.CATTAIL_FEATURE_PLACEABLE) || blockState.is(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE);
	}
}
