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

package net.frozenblock.wilderwild.worldgen.impl.features;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class SnowBlanketFeature extends Feature<NoneFeatureConfiguration> {

	public SnowBlanketFeature(@NotNull Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private static boolean placeSnowAtPos(
			@NotNull WorldGenLevel level,
			@NotNull BlockPos.MutableBlockPos motionBlockingPos,
			@NotNull BlockPos.MutableBlockPos belowLeavesPos,
			@NotNull Holder<Biome> biomeHolder
	) {
		boolean returnValue = false;
		int lowestY = belowLeavesPos.getY() - 1;
		while (motionBlockingPos.getY() > lowestY) {
			if (placeSnowLayer(level, motionBlockingPos, biomeHolder)) {
				returnValue = true;
			}
			motionBlockingPos.move(Direction.DOWN);
		}
		return returnValue;
	}

	private static boolean placeSnowAtPos(
			@NotNull WorldGenLevel level,
			@NotNull BlockPos.MutableBlockPos motionBlockingPos,
			@NotNull BlockPos.MutableBlockPos belowLeavesPos
	) {
		boolean returnValue = false;
		int lowestY = belowLeavesPos.getY() - 1;
		while (motionBlockingPos.getY() > lowestY) {
			if (placeSnowLayer(level, motionBlockingPos)) {
				returnValue = true;
			}
			motionBlockingPos.move(Direction.DOWN);
		}
		return returnValue;
	}

	private static boolean placeSnowLayer(@NotNull WorldGenLevel level, @NotNull BlockPos.MutableBlockPos pos) {
		return placeSnowLayer(level, pos, level.getBiome(pos));
	}

	private static boolean placeSnowLayer(@NotNull WorldGenLevel level, @NotNull BlockPos.MutableBlockPos pos, @NotNull Holder<Biome> biomeHolder) {
		if (biomeHolder.value().shouldSnow(level, pos)) {
			BlockState replacingState = level.getBlockState(pos);
			if (SnowloggingUtils.canSnowlog(replacingState) && !SnowloggingUtils.isSnowlogged(replacingState)) {
				level.setBlock(pos, replacingState.setValue(SnowloggingUtils.SNOW_LAYERS, 1), Block.UPDATE_CLIENTS);
			} else {
				level.setBlock(pos, Blocks.SNOW.defaultBlockState(), Block.UPDATE_CLIENTS);
			}
			BlockState belowState = level.getBlockState(pos.move(Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(pos, belowState.setValue(BlockStateProperties.SNOWY, true), Block.UPDATE_CLIENTS);
			}
			pos.move(Direction.UP);
			return true;
		}
		return false;
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutablePlacementPos = new BlockPos.MutableBlockPos();
		boolean returnValue = false;
		int posX = pos.getX();
		int posZ = pos.getZ();
		for (int i = 0; i < 16; i++) {
			int x = posX + i;
			for (int j = 0; j < 16; j++) {
				int z = posZ + j;
				int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				int belowLeavesHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
				if (height > belowLeavesHeight) {
					mutablePos.set(x, height, z);
					mutablePlacementPos.set(x, belowLeavesHeight, z);
					Holder<Biome> biomeHolder = level.getBiome(mutablePos);
					Holder<Biome> lowerBiomeHolder = level.getBiome(mutablePlacementPos);
					if (lowerBiomeHolder.value().shouldFreeze(level, mutablePlacementPos.move(Direction.DOWN), false)) {
						level.setBlock(mutablePlacementPos, Blocks.ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
					}
					mutablePlacementPos.move(Direction.UP);
					if (biomeHolder.equals(lowerBiomeHolder)) {
						if (placeSnowAtPos(level, mutablePos, mutablePlacementPos, biomeHolder)) {
							returnValue = true;
						}
					} else {
						if (placeSnowAtPos(level, mutablePos, mutablePlacementPos)) {
							returnValue = true;
						}
					}
				}
			}
		}
		return returnValue;
	}
}
