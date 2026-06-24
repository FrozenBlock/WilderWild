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

package net.frozenblock.wilderwild.levelgen.feature;

import com.mojang.serialization.MapCodec;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeatureUtil;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;

public record CattailFeature() implements Feature {
	public static final CattailFeature INSTANCE = new CattailFeature();
	public static final MapCodec<CattailFeature> CODEC = MapCodec.unit(INSTANCE);

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		final BlockPos aboveOrigin = origin.above();
		final BlockState bottomState = level.getBlockState(origin);
		final boolean bottomStateIsWater = bottomState.is(Blocks.WATER);
		final BlockState topState = level.getBlockState(aboveOrigin);
		if (!(bottomState.isAir() || bottomStateIsWater) || !topState.isAir()) return false;

		final BlockState bottomPlaceState = WWBlocks.CATTAIL.defaultBlockState()
			.setValue(CattailBlock.WATERLOGGED, bottomStateIsWater)
			.setValue(CattailBlock.SWAYING, bottomStateIsWater);
		if (!bottomPlaceState.canSurvive(level, origin)) return false;
		if (!(bottomStateIsWater || FrozenLibFeatureUtil.isWaterNearby(level, origin, 2))) return false;

		final BlockState topPlaceState = WWBlocks.CATTAIL.defaultBlockState()
			.setValue(CattailBlock.HALF, DoubleBlockHalf.UPPER)
			.setValue(CattailBlock.SWAYING, bottomStateIsWater);

		level.setBlock(origin, bottomPlaceState, Block.UPDATE_CLIENTS);
		if (topPlaceState.canSurvive(level, aboveOrigin)) level.setBlock(aboveOrigin, topPlaceState, Block.UPDATE_CLIENTS);

		return true;
	}
}
