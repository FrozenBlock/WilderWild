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

public class NewSnowAndFreezeFeature extends Feature<NoneFeatureConfiguration> {

    public NewSnowAndFreezeFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutablePlacementPos = new BlockPos.MutableBlockPos();
		boolean returnValue = false;
		int posX = pos.getX();
		int posZ = pos.getZ();
		for(int i = 0; i < 16; i++) {
			int x = posX + i;
			for(int j = 0; j < 16; j++) {
				int z = posZ + j;
				mutablePos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z), z);
				mutablePlacementPos.set(x, level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z), z);
				if (placeSnowAndIceAtPos(level, mutablePos, mutablePlacementPos)) {
					returnValue = true;
				}
			}
		}
		return returnValue;
	}

	private static boolean placeSnowAndIceAtPos(WorldGenLevel level, BlockPos.MutableBlockPos motionBlockingPos, BlockPos.MutableBlockPos belowLeavesPos) {
		boolean returnValue = false;
		Biome biome = level.getBiome(motionBlockingPos).value();
		int lowestY = belowLeavesPos.getY() - 1;
		while (motionBlockingPos.getY() > lowestY) {
			if (placeSnowLayerOrIce(level, motionBlockingPos, biome)) {
				returnValue = true;
			}
		}

		return returnValue;
	}

	private static boolean placeSnowLayerOrIce(WorldGenLevel level, BlockPos.MutableBlockPos pos, Biome biome) {
		if (biome.shouldFreeze(level, pos.move(Direction.DOWN), false)) {
			level.setBlock(pos, Blocks.ICE.defaultBlockState(), 2);
		} else if (biome.shouldSnow(level, pos.move(Direction.UP))) {
			level.setBlock(pos, Blocks.SNOW.defaultBlockState(), 2);
			BlockState belowState = level.getBlockState(pos.move(Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(pos, belowState.setValue(BlockStateProperties.SNOWY, true), 2);
			}
			pos.move(Direction.DOWN);
			return true;
		}
		return false;
	}

}
