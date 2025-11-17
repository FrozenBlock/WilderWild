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

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SeaWhipFeature extends Feature<NoneFeatureConfiguration> {
	private static final int SEARCH_RANGE = 6;

	public SeaWhipFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		final RandomSource random = featurePlaceContext.random();
		final WorldGenLevel level = featurePlaceContext.level();
		final BlockPos origin = featurePlaceContext.origin();
		final int x = random.nextInt(SEARCH_RANGE) - random.nextInt(SEARCH_RANGE);
		final int z = random.nextInt(SEARCH_RANGE) - random.nextInt(SEARCH_RANGE);
		final int heightmapY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + x, origin.getZ() + z);
		final BlockPos pos = new BlockPos(origin.getX() + x, heightmapY, origin.getZ() + z);

		boolean generated = false;
		if (level.getBlockState(pos).is(Blocks.WATER)) {
			final BlockState state = WWBlocks.SEA_WHIP.defaultBlockState();
			if (state.canSurvive(level, pos)) {
				level.setBlock(pos, state, Block.UPDATE_CLIENTS);
				generated = true;
			}
		}
		return generated;
	}
}
