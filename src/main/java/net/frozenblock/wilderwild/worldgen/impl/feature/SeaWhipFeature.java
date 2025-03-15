/*
 * Copyright 2023-2025 FrozenBlock
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
import org.jetbrains.annotations.NotNull;

public class SeaWhipFeature extends Feature<NoneFeatureConfiguration> {
	private static final int SEARCH_RANGE = 6;

	public SeaWhipFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		boolean generated = false;
		RandomSource randomSource = featurePlaceContext.random();
		WorldGenLevel worldGenLevel = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		int i = randomSource.nextInt(SEARCH_RANGE) - randomSource.nextInt(SEARCH_RANGE);
		int j = randomSource.nextInt(SEARCH_RANGE) - randomSource.nextInt(SEARCH_RANGE);
		int k = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX() + i, blockPos.getZ() + j);
		BlockPos blockPos2 = new BlockPos(blockPos.getX() + i, k, blockPos.getZ() + j);
		if (worldGenLevel.getBlockState(blockPos2).is(Blocks.WATER)) {
			BlockState blockState = WWBlocks.SEA_WHIP.defaultBlockState();
			if (blockState.canSurvive(worldGenLevel, blockPos2)) {
				worldGenLevel.setBlock(blockPos2, blockState, Block.UPDATE_CLIENTS);
				generated = true;
			}
		}
		return generated;
	}
}
