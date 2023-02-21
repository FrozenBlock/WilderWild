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
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
	private static final BlockState placeState = Blocks.SNOW.defaultBlockState();

	public SnowBlanketFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutablePlacementPos = new BlockPos.MutableBlockPos();
		int posX = pos.getX();
		int posZ = pos.getZ();
		for(int i = 0; i < 16; i++) {
			int x = posX + i;
			for(int j = 0; j < 16; j++) {
				int z = posZ + j;
				mutablePos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z), z);
				mutablePlacementPos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
				if (!mutablePos.equals(mutablePlacementPos)) {
					Holder<Biome> biomeHolder = level.getBiome(mutablePos);
					if (biomeHolder.equals(level.getBiome(mutablePlacementPos))) {
						placeSnowAtPosOneBiome(level, mutablePos, mutablePlacementPos, biomeHolder);
					} else {
						placeSnowAtPos(level, mutablePos, mutablePlacementPos);
					}
				}
			}
		}
		return true;
	}

	private static void placeSnowAtPosOneBiome(WorldGenLevel level, BlockPos.MutableBlockPos motionBlockingPos, BlockPos.MutableBlockPos belowLeavesPos, Holder<Biome> biomeHolder) {
		if (!biomeHolder.is(WilderBiomeTags.NO_SNOW_BLANKET)) {
			int lowestY = belowLeavesPos.getY() - 1;
			while (motionBlockingPos.getY() > lowestY) {
				placeSnowLayerOneBiome(level, motionBlockingPos, biomeHolder);
				motionBlockingPos.move(Direction.DOWN);
			}
		}
	}

	private static boolean placeSnowLayerOneBiome(WorldGenLevel level, BlockPos.MutableBlockPos pos, Holder<Biome> biomeHolder) {
		Biome biome = biomeHolder.value();
		if (biome.shouldSnow(level, pos) && level.getBlockState(pos).isAir() && placeState.canSurvive(level, pos)) {
			level.setBlock(pos, placeState, 3);
			BlockState belowState = level.getBlockState(pos.move(Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(pos, belowState.setValue(BlockStateProperties.SNOWY, true), 3);
			}
			pos.move(Direction.UP);
			return true;
		}
		return false;
	}

	private static void placeSnowAtPos(WorldGenLevel level, BlockPos.MutableBlockPos motionBlockingPos, BlockPos.MutableBlockPos belowLeavesPos) {
		int lowestY = belowLeavesPos.getY() - 1;
		while (motionBlockingPos.getY() > lowestY) {
			placeSnowLayer(level, motionBlockingPos);
			motionBlockingPos.move(Direction.DOWN);
		}
	}

	private static boolean placeSnowLayer(WorldGenLevel level, BlockPos.MutableBlockPos pos) {
		Holder<Biome> biomeHolder = level.getBiome(pos);
		Biome biome = biomeHolder.value();
		if (!biomeHolder.is(WilderBiomeTags.NO_SNOW_BLANKET) && biome.shouldSnow(level, pos) && level.getBlockState(pos).isAir() && placeState.canSurvive(level, pos)) {
			level.setBlock(pos, placeState, 3);
			BlockState belowState = level.getBlockState(pos.move(Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(pos, belowState.setValue(BlockStateProperties.SNOWY, true), 3);
			}
			pos.move(Direction.UP);
			return true;
		}
		return false;
	}

}
