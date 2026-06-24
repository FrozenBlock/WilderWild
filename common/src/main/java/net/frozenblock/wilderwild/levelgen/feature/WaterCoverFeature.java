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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record WaterCoverFeature(
	BlockStateProvider blockStateProvider,
	IntProvider radius
) implements Feature {
	public static final MapCodec<WaterCoverFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("state").forGetter(WaterCoverFeature::blockStateProvider),
		IntProviders.CODEC.fieldOf("radius").forGetter(WaterCoverFeature::radius)
	).apply(instance, WaterCoverFeature::new));

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		boolean generated = false;
		final BlockPos heightmapPos = origin.atY(level.getHeight(Types.MOTION_BLOCKING_NO_LEAVES, origin.getX(), origin.getZ()));
		final int y = heightmapPos.getY();
		final int radius = this.radius.sample(random);

		//DISK
		final BlockPos.MutableBlockPos diskPos = heightmapPos.mutable();
		final int bx = heightmapPos.getX();
		final int bz = heightmapPos.getZ();
		for (int x = bx - radius; x <= bx + radius; x++) {
			for (int z = bz - radius; z <= bz + radius; z++) {
				double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)));
				if (distance >= radius * radius) continue;

				diskPos.set(x, y, z);
				final boolean fade = !diskPos.closerThan(heightmapPos, radius * 0.8D);
				boolean generatedThisRound = false;

				if (level.getBlockState(diskPos.move(Direction.DOWN)).is(Blocks.WATER) && level.getBlockState(diskPos.move(Direction.UP)).isAir()) {
					if (random.nextFloat() > 0.2F) {
						generatedThisRound = true;
						if (fade) {
							if (random.nextBoolean()) {
								level.setBlock(diskPos, this.blockStateProvider.getState(level, random, diskPos), Block.UPDATE_CLIENTS);
								generated = true;
							}
						} else {
							level.setBlock(diskPos, this.blockStateProvider.getState(level, random, diskPos), Block.UPDATE_CLIENTS);
							generated = true;
						}
					}
				} else {
					for (int aY = 0; aY < 3; aY++) {
						diskPos.set(x, y + aY, z);
						if (!level.getBlockState(diskPos.move(Direction.DOWN)).is(Blocks.WATER) || !level.getBlockState(diskPos.move(Direction.UP)).isAir()) continue;
						generatedThisRound = true;
						generated = placeBlock(level, this.blockStateProvider, random, diskPos, fade) || generated;
					}
				}

				if (!generatedThisRound) {
					for (int aY = -3; aY < 0; aY++) {
						diskPos.set(x, y + aY, z);
						if (!level.getBlockState(diskPos.move(Direction.DOWN)).is(Blocks.WATER) || !level.getBlockState(diskPos.move(Direction.UP)).isAir()) continue;
						generated = placeBlock(level, this.blockStateProvider, random, diskPos, fade) || generated;
					}
				}
			}
		}
		return generated;
	}

	private static boolean placeBlock(
		WorldGenLevel level,
		BlockStateProvider blockState,
		RandomSource random,
		BlockPos.MutableBlockPos diskPos,
		boolean fade
	) {
		if (random.nextFloat() > 0.2F) {
			if (fade) {
				if (random.nextFloat() > 0.5F) {
					level.setBlock(diskPos, blockState.getState(level, random, diskPos), Block.UPDATE_CLIENTS);
					return true;
				}
			} else {
				level.setBlock(diskPos, blockState.getState(level, random, diskPos), Block.UPDATE_CLIENTS);
				return true;
			}
		}
		return false;
	}
}
