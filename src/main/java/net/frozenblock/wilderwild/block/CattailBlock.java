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

package net.frozenblock.wilderwild.block;

import net.frozenblock.lib.block.api.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CattailBlock extends WaterloggableTallFlowerBlock {

	public CattailBlock(@NotNull Properties settings) {
		super(settings);
	}

	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState blockState = super.getStateForPlacement(context);
		return blockState != null ? SnowloggingUtils.getSnowPlacementState(blockState, context) : null;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(WWBlockTags.CATTAIL_FEATURE_PLACEABLE) || blockState.is(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE);
	}
}
