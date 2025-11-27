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
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatureUtils;
import net.frozenblock.wilderwild.block.CattailBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.CattailFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class CattailFeature extends Feature<CattailFeatureConfig> {

	public CattailFeature(Codec<CattailFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<CattailFeatureConfig> context) {
		final RandomSource random = context.random();
		final WorldGenLevel level = context.level();
		final BlockPos origin = context.origin();
		final CattailFeatureConfig config = context.config();
		final int originX = origin.getX();
		final int originZ = origin.getZ();
		final int maxHeight = level.getMaxBuildHeight() - 1;
		final BlockPos.MutableBlockPos bottomBlockPos = origin.mutable();
		final BlockPos.MutableBlockPos topBlockPos = origin.mutable();
		final TagKey<Block> placeableBlocks = config.canBePlacedOn();
		final int width = config.width();

		boolean generated = false;
		final int placementAttempts = config.placementAttempts().sample(random);
		for (int l = 0; l < placementAttempts; l++) {
			final int newX = originX + random.nextIntBetweenInclusive(-width, width);
			final int newZ = originZ + random.nextIntBetweenInclusive(-width, width);
			final int oceanFloorY = level.getHeight(Types.OCEAN_FLOOR, newX, newZ);
			if (oceanFloorY >= maxHeight - 1) continue;

			final BlockState bottomState = level.getBlockState(bottomBlockPos.set(newX, oceanFloorY, newZ));
			final boolean bottomStateIsWater = bottomState.is(Blocks.WATER);
			final BlockState topState = level.getBlockState(topBlockPos.setWithOffset(bottomBlockPos, Direction.UP));
			if (!(bottomState.isAir() || bottomStateIsWater) || !topState.isAir()) continue;

			final BlockState bottomPlaceState = WWBlocks.CATTAIL.defaultBlockState()
				.setValue(CattailBlock.WATERLOGGED, bottomStateIsWater)
				.setValue(CattailBlock.SWAYING, bottomStateIsWater);
			if (!bottomPlaceState.canSurvive(level, bottomBlockPos)) continue;
			if (!(bottomStateIsWater || FrozenLibFeatureUtils.isWaterNearby(level, bottomBlockPos, 2))) continue;
			if (!level.getBlockState(bottomBlockPos.move(Direction.DOWN)).is(placeableBlocks)) continue;

			final BlockState topPlaceState = WWBlocks.CATTAIL.defaultBlockState()
				.setValue(CattailBlock.HALF, DoubleBlockHalf.UPPER)
				.setValue(CattailBlock.SWAYING, bottomStateIsWater);

			level.setBlock(bottomBlockPos.move(Direction.UP), bottomPlaceState, Block.UPDATE_CLIENTS);
			if (topPlaceState.canSurvive(level, topBlockPos)) level.setBlock(topBlockPos, topPlaceState, Block.UPDATE_CLIENTS);
			generated = true;
		}
		return generated;
	}

}
