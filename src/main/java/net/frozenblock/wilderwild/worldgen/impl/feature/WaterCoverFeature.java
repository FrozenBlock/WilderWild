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
import net.frozenblock.wilderwild.worldgen.impl.feature.config.WaterCoverFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.jetbrains.annotations.NotNull;

public class WaterCoverFeature extends Feature<WaterCoverFeatureConfig> {

	public WaterCoverFeature(@NotNull Codec<WaterCoverFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<WaterCoverFeatureConfig> context) {
		boolean generated = false;
		BlockPos blockPos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos s = blockPos.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, blockPos.getX(), blockPos.getZ()));
		int y = s.getY();
		RandomSource random = level.getRandom();
		int radius = context.config().radius().sample(random);
		BlockStateProvider stateProvider = context.config().blockStateProvider();
		//DISK
		BlockPos.MutableBlockPos mutableDisk = s.mutable();
		int bx = s.getX();
		int bz = s.getZ();
		for (int x = bx - radius; x <= bx + radius; x++) {
			for (int z = bz - radius; z <= bz + radius; z++) {
				double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
				if (distance < radius * radius) {
					mutableDisk.set(x, y, z);
					boolean fade = !mutableDisk.closerThan(s, radius * 0.8D);
					boolean hasGeneratedThisRound = false;
					if (level.getBlockState(mutableDisk.move(Direction.DOWN)).is(Blocks.WATER) && level.getBlockState(mutableDisk.move(Direction.UP)).isAir()) {
						if (random.nextFloat() > 0.2F) {
							hasGeneratedThisRound = true;
							if (fade) {
								if (random.nextBoolean()) {
									level.setBlock(mutableDisk, stateProvider.getState(random, mutableDisk), Block.UPDATE_CLIENTS);
									generated = true;
								}
							} else {
								level.setBlock(mutableDisk, stateProvider.getState(random, mutableDisk), Block.UPDATE_CLIENTS);
								generated = true;
							}
						}
					} else {
						for (int aY = 0; aY < 3; aY++) {
							mutableDisk.set(x, y + aY, z);
							if (level.getBlockState(mutableDisk.move(Direction.DOWN)).is(Blocks.WATER) && level.getBlockState(mutableDisk.move(Direction.UP)).isAir()) {
								hasGeneratedThisRound = true;
								generated = placeBlock(level, stateProvider, random, mutableDisk, fade) || generated;
							}
						}
					}
					if (!hasGeneratedThisRound) {
						for (int aY = -3; aY < 0; aY++) {
							mutableDisk.set(x, y + aY, z);
							if (level.getBlockState(mutableDisk.move(Direction.DOWN)).is(Blocks.WATER) && level.getBlockState(mutableDisk.move(Direction.UP)).isAir()) {
								generated = placeBlock(level, stateProvider, random, mutableDisk, fade) || generated;
							}
						}
					}
				}
			}
		}
		return generated;
	}

	private boolean placeBlock(
		WorldGenLevel level,
		BlockStateProvider stateProvider,
		@NotNull RandomSource random,
		@NotNull BlockPos.MutableBlockPos mutableDisk,
		boolean fade
	) {
		if (random.nextFloat() > 0.2F) {
			if (fade) {
				if (random.nextFloat() > 0.5F) {
					level.setBlock(mutableDisk, stateProvider.getState(random, mutableDisk), Block.UPDATE_CLIENTS);
					return true;
				}
			} else {
				level.setBlock(mutableDisk, stateProvider.getState(random, mutableDisk), Block.UPDATE_CLIENTS);
				return true;
			}
		}
		return false;
	}
}
