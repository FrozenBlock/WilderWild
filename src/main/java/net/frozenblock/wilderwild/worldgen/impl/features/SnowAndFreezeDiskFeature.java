/*
 * Copyright 2023-2024 FrozenBlock
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
import net.frozenblock.wilderwild.worldgen.impl.features.config.SnowAndIceDiskFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class SnowAndFreezeDiskFeature extends Feature<SnowAndIceDiskFeatureConfig> {

	public SnowAndFreezeDiskFeature(@NotNull Codec<SnowAndIceDiskFeatureConfig> codec) {
		super(codec);
	}

	public static boolean canPlaceSnow(@NotNull LevelReader level, @NotNull BlockPos pos) {
		return pos.getY() >= level.getMinBuildHeight() && pos.getY() < level.getMaxBuildHeight() && level.getBrightness(LightLayer.BLOCK, pos) < 10 && (level.getBlockState(pos)).isAir() && Blocks.SNOW.defaultBlockState().canSurvive(level, pos);
	}

	public static boolean canPlaceIce(@NotNull LevelReader level, @NotNull BlockPos water) {
		if (water.getY() >= level.getMinBuildHeight() && water.getY() < level.getMaxBuildHeight() && level.getBrightness(LightLayer.BLOCK, water) < 10) {
			BlockState blockState = level.getBlockState(water);
			FluidState fluidState = level.getFluidState(water);
			return fluidState.getType() == Fluids.WATER && blockState.getBlock() instanceof LiquidBlock;
		}
		return false;
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<SnowAndIceDiskFeatureConfig> context) {
		boolean bl = false;
		BlockPos blockPos = context.origin();
		WorldGenLevel level = context.level();
		SnowAndIceDiskFeatureConfig config = context.config();
		BlockPos s = blockPos.atY(level.getHeight(Types.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ()));
		Biome biome = level.getBiome(s).value();
		boolean coldEnough = !biome.warmEnoughToRain(s);
		if (coldEnough) {
			RandomSource random = level.getRandom();
			int radius = config.radius.sample(random);
			//DISK
			BlockPos.MutableBlockPos mutableDisk = s.mutable();
			BlockPos.MutableBlockPos mutableDisk2 = new BlockPos.MutableBlockPos();
			int bx = s.getX();
			int bz = s.getZ();
			BlockState snowState = Blocks.SNOW.defaultBlockState();
			for (int x = bx - radius; x <= bx + radius; x++) {
				for (int z = bz - radius; z <= bz + radius; z++) {
					if (((bx - x) * (bx - x) + (bz - z) * (bz - z)) < radius * radius) {
						mutableDisk.set(x, level.getHeight(Types.MOTION_BLOCKING, x, z), z);
						BlockState state = level.getBlockState(mutableDisk);
						if (state.getBlock() != Blocks.SNOW) {
							boolean fade = !mutableDisk.closerThan(s, radius * config.fadeStartDistancePercent);
							if (random.nextFloat() < config.placementChance && ((!fade || random.nextFloat() > 0.5F) && canPlaceSnow(level, mutableDisk))) {
								BlockState belowState = level.getBlockState(mutableDisk2.set(mutableDisk).move(Direction.DOWN));
								if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
									level.setBlock(mutableDisk2, belowState.setValue(BlockStateProperties.SNOWY, true), 2);
								}
								level.setBlock(mutableDisk, snowState, 2);
								bl = true;
							}
						}
					}
				}
			}
		}

		if (coldEnough) {
			RandomSource random = level.getRandom();
			int radius = config.iceRadius.sample(random);
			//DISK
			BlockPos.MutableBlockPos mutableDisk = s.mutable();
			BlockPos.MutableBlockPos mutableDisk2 = new BlockPos.MutableBlockPos();
			int bx = s.getX();
			int bz = s.getZ();
			BlockState iceState = Blocks.ICE.defaultBlockState();
			for (int x = bx - radius; x <= bx + radius; x++) {
				for (int z = bz - radius; z <= bz + radius; z++) {
					if (((bx - x) * (bx - x) + (bz - z) * (bz - z)) < radius * radius) {
						mutableDisk.set(x, level.getHeight(Types.MOTION_BLOCKING, x, z), z);
						BlockState state = level.getBlockState(mutableDisk2.set(mutableDisk).move(Direction.DOWN));
						if (state.getBlock() != Blocks.ICE) {
							boolean fade = !mutableDisk.closerThan(s, radius * config.fadeStartDistancePercent);
							if (random.nextFloat() < config.placementChance && ((!fade || random.nextFloat() > 0.5F) && canPlaceIce(level, mutableDisk2))) {
								level.setBlock(mutableDisk2, iceState, 2);
							}
						}
					}
				}
			}
		}
		return bl;
	}

}
