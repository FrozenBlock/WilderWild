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
import org.jetbrains.annotations.NotNull;

public class FrozenTallGrassBlock extends TallGrassBlock {

	public FrozenTallGrassBlock(@NotNull Properties settings) {
		super(settings);
	}

	@Override
	protected boolean mayPlaceOn(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos) || blockState.is(Blocks.SNOW_BLOCK);
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, @NotNull BlockState blockState) {
		DoublePlantBlock doublePlantBlock = (blockState.is(WWBlocks.FROZEN_FERN) ? WWBlocks.FROZEN_LARGE_FERN : WWBlocks.FROZEN_TALL_GRASS);
		if (doublePlantBlock.defaultBlockState().canSurvive(serverLevel, blockPos) && serverLevel.isEmptyBlock(blockPos.above())) {
			DoublePlantBlock.placeAt(serverLevel, doublePlantBlock.defaultBlockState(), blockPos, UPDATE_CLIENTS);
		}
	}
}
