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
import net.frozenblock.wilderwild.block.TubeWormsBlock;
import net.frozenblock.wilderwild.block.state.properties.TubeWormsPart;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class TubeWormsFeature extends Feature<NoneFeatureConfiguration> {

	public TubeWormsFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		final WorldGenLevel level = featurePlaceContext.level();
		final BlockPos origin = featurePlaceContext.origin();
		final RandomSource random = featurePlaceContext.random();
		final int heightmapY = level.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX(), origin.getZ());
		final BlockPos heightmapPos = new BlockPos(origin.getX(), heightmapY, origin.getZ());
		if (!level.getBlockState(heightmapPos).is(Blocks.WATER)) return false;

		final BlockState state = WWBlocks.TUBE_WORMS.defaultBlockState();
		final BlockPos.MutableBlockPos mutableBlockPos = heightmapPos.mutable();
		final BlockPos.MutableBlockPos mutablePlacePos = heightmapPos.mutable();
		if (!state.canSurvive(level, mutableBlockPos)) return false;

		if (random.nextFloat() <= 0.5F && level.getBlockState(mutableBlockPos.move(Direction.UP)).is(Blocks.WATER)) {
			if (random.nextFloat() <= 0.25F && level.getBlockState(mutableBlockPos.move(Direction.UP)).is(Blocks.WATER)) {
				level.setBlock(mutablePlacePos, state.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.BOTTOM), Block.UPDATE_CLIENTS);
				level.setBlock(mutablePlacePos.move(Direction.UP), state.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.MIDDLE), Block.UPDATE_CLIENTS);
				level.setBlock(mutablePlacePos.move(Direction.UP), state.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.TOP), Block.UPDATE_CLIENTS);
				return true;
			}
			level.setBlock(mutablePlacePos, state.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.BOTTOM), Block.UPDATE_CLIENTS);
			level.setBlock(mutablePlacePos.move(Direction.UP), state.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.TOP), Block.UPDATE_CLIENTS);
			return true;
		}
		level.setBlock(mutablePlacePos, state, Block.UPDATE_CLIENTS);
		return true;
	}
}
