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

import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FrozenTallGrassBlock extends TallGrassBlock {

	public FrozenTallGrassBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return super.mayPlaceOn(state, level, pos) || state.is(Blocks.SNOW_BLOCK);
	}

	@Override
	public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
		final DoublePlantBlock doublePlantBlock = (state.is(WWBlocks.FROZEN_FERN) ? WWBlocks.FROZEN_LARGE_FERN : WWBlocks.FROZEN_TALL_GRASS);
		if (!doublePlantBlock.defaultBlockState().canSurvive(level, pos) || !level.isEmptyBlock(pos.above())) return;
		DoublePlantBlock.placeAt(level, doublePlantBlock.defaultBlockState(), pos, UPDATE_CLIENTS);
	}
}
