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
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;

public record SeagrassFeature(float probability) implements Feature {
	public static final MapCodec<SeagrassFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		com.mojang.serialization.Codec.FLOAT.fieldOf("probability").forGetter(SeagrassFeature::probability)
	).apply(instance, SeagrassFeature::new));

	@Override
	public MapCodec<? extends Feature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		final int x = random.nextInt(8) - random.nextInt(8);
		final int z = random.nextInt(8) - random.nextInt(8);
		final int y = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + x, origin.getZ() + z);
		final BlockPos grassPos = new BlockPos(origin.getX() + x, y, origin.getZ() + z);
		if (!level.getBlockState(grassPos).is(Blocks.WATER)) return false;

		final boolean isTall = random.nextDouble() < this.probability;
		final BlockState state = isTall ? Blocks.TALL_SEAGRASS.defaultBlockState() : Blocks.SEAGRASS.defaultBlockState();
		if (!state.canSurvive(level, grassPos)) return false;

		if (isTall) {
			final BlockState upperState = state.setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER);
			final BlockPos above = grassPos.above();
			if (!level.getBlockState(above).is(Blocks.WATER)) return false;
			level.setBlock(grassPos, state, 2);
			level.setBlock(above, upperState, 2);
		} else {
			level.setBlock(grassPos, state, 2);
		}
		return true;
	}
}
