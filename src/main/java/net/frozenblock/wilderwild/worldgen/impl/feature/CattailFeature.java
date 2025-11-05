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
import org.jetbrains.annotations.NotNull;

public class CattailFeature extends Feature<CattailFeatureConfig> {

	public CattailFeature(@NotNull Codec<CattailFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<CattailFeatureConfig> context) {
		final RandomSource random = context.random();
		final WorldGenLevel level = context.level();
		final BlockPos pos = context.origin();
		final CattailFeatureConfig config = context.config();
		final int posX = pos.getX();
		final int posZ = pos.getZ();
		final int maxHeight = level.getMaxBuildHeight() - 1;
		final BlockPos.MutableBlockPos bottomBlockPos = pos.mutable();
		final BlockPos.MutableBlockPos topBlockPos = pos.mutable();
		final TagKey<Block> placeableBlocks = config.canBePlacedOn();
		final int width = config.width();

		boolean generated = false;
		final int placementAttempts = config.placementAttempts().sample(random);
		for (int l = 0; l < placementAttempts; l++) {
			final int newX = posX + random.nextIntBetweenInclusive(-width, width);
			final int newZ = posZ + random.nextIntBetweenInclusive(-width, width);
			final int oceanFloorY = level.getHeight(Types.OCEAN_FLOOR, newX, newZ);
			if (oceanFloorY >= maxHeight - 1) continue;

			bottomBlockPos.set(newX, oceanFloorY, newZ);
			BlockState bottomState = level.getBlockState(bottomBlockPos);
			boolean bottomStateIsWater = bottomState.is(Blocks.WATER);
			BlockState bottomPlaceState = WWBlocks.CATTAIL.defaultBlockState();
			topBlockPos.setWithOffset(bottomBlockPos, Direction.UP);
			BlockState topState = level.getBlockState(topBlockPos);
			if ((bottomState.isAir() || bottomStateIsWater)
				&& topState.isAir()
				&& bottomPlaceState.canSurvive(level, bottomBlockPos)
				&& (bottomStateIsWater || FrozenLibFeatureUtils.isWaterNearby(level, bottomBlockPos, 2))
				&& level.getBlockState(bottomBlockPos.move(Direction.DOWN)).is(placeableBlocks)
			) {
				bottomPlaceState = bottomPlaceState
					.setValue(CattailBlock.WATERLOGGED, bottomStateIsWater)
					.setValue(CattailBlock.SWAYING, bottomStateIsWater);
				BlockState topPlaceState = WWBlocks.CATTAIL.defaultBlockState()
					.setValue(CattailBlock.HALF, DoubleBlockHalf.UPPER)
					.setValue(CattailBlock.SWAYING, bottomStateIsWater);

				level.setBlock(bottomBlockPos.move(Direction.UP), bottomPlaceState, Block.UPDATE_CLIENTS);
				if (topPlaceState.canSurvive(level, topBlockPos)) level.setBlock(topBlockPos, topPlaceState, Block.UPDATE_CLIENTS);
				generated = true;
			}
		}
		return generated;
	}

}
