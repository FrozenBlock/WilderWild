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
import org.jetbrains.annotations.NotNull;

public class TubeWormsFeature extends Feature<NoneFeatureConfiguration> {

	public TubeWormsFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		WorldGenLevel worldGenLevel = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		RandomSource random = featurePlaceContext.random();
		int k = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
		BlockPos blockPos2 = new BlockPos(blockPos.getX(), k, blockPos.getZ());
		if (worldGenLevel.getBlockState(blockPos2).is(Blocks.WATER)) {
			BlockState blockState = WWBlocks.TUBE_WORMS.defaultBlockState();
			BlockPos.MutableBlockPos mutableBlockPos = blockPos2.mutable();
			BlockPos.MutableBlockPos mutablePlacePos = blockPos2.mutable();
			if (blockState.canSurvive(worldGenLevel, mutableBlockPos)) {
				if (random.nextFloat() <= 0.5F && worldGenLevel.getBlockState(mutableBlockPos.move(Direction.UP)).is(Blocks.WATER)) {
					if (random.nextFloat() <= 0.25F && worldGenLevel.getBlockState(mutableBlockPos.move(Direction.UP)).is(Blocks.WATER)) {
						worldGenLevel.setBlock(mutablePlacePos, blockState.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.BOTTOM), Block.UPDATE_CLIENTS);
						worldGenLevel.setBlock(mutablePlacePos.move(Direction.UP), blockState.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.MIDDLE), Block.UPDATE_CLIENTS);
						worldGenLevel.setBlock(mutablePlacePos.move(Direction.UP), blockState.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.TOP), Block.UPDATE_CLIENTS);
						return true;
					}
					worldGenLevel.setBlock(mutablePlacePos, blockState.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.BOTTOM), Block.UPDATE_CLIENTS);
					worldGenLevel.setBlock(mutablePlacePos.move(Direction.UP), blockState.setValue(TubeWormsBlock.TUBE_WORMS_PART, TubeWormsPart.TOP), Block.UPDATE_CLIENTS);
					return true;
				}
				worldGenLevel.setBlock(mutablePlacePos, blockState, Block.UPDATE_CLIENTS);
				return true;
			}
		}
		return false;
	}
}
