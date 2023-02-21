/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.generation.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SnowBlanketFeature extends Feature<NoneFeatureConfiguration> {

	public SnowBlanketFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockState placeState = Blocks.SNOW.defaultBlockState();
		int posX = pos.getX();
		int posZ = pos.getZ();
		for(int i = 0; i < 16; i++) {
			int x = posX + i;
			for(int j = 0; j < 16; j++) {
				int z = posZ + j;
				if (level.getBlockState(mutablePos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z) - 1, z)).is(BlockTags.LEAVES)) {
					mutablePos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
					Holder<Biome> biomeHolder = level.getBiome(mutablePos);
					Biome biome = biomeHolder.value();
					if (!biomeHolder.is(WilderBiomeTags.NO_SNOW_BLANKET) && biome.shouldSnow(level, mutablePos) && level.getBlockState(mutablePos).isAir() && placeState.canSurvive(level, mutablePos)) {
						level.setBlock(mutablePos, placeState, 3);
						BlockState belowState = level.getBlockState(mutablePos.set(x, mutablePos.getY() - 1, z));
						if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
							level.setBlock(mutablePos, belowState.setValue(BlockStateProperties.SNOWY, true), 3);
						}
					}
				}
			}
		}
		return true;
	}

}
