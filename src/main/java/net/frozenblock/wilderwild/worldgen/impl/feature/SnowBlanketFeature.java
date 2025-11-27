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
import java.util.Optional;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.block.impl.SnowyBlockUtils;
import net.frozenblock.wilderwild.tag.WWBlockTags;
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

public class SnowBlanketFeature extends Feature<NoneFeatureConfiguration> {

	public SnowBlanketFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private static boolean placeSnowAtPos(
		WorldGenLevel level,
		BlockPos.MutableBlockPos topPos,
		BlockPos.MutableBlockPos bottomPos,
		BlockPos.MutableBlockPos mutable,
		Optional<Holder<Biome>> optionalBiomeHolder
	) {
		if (placeSnowLayer(level, bottomPos, mutable, optionalBiomeHolder, true) == SnowGenerationState.CANCEL) return false;
		bottomPos.move(Direction.UP);

		while (bottomPos.getY() <= topPos.getY()) {
			placeSnowLayer(level, bottomPos, mutable, optionalBiomeHolder, false);
			bottomPos.move(Direction.UP);
		}
		return true;
	}

	private static SnowGenerationState placeSnowLayer(
		WorldGenLevel level,
		BlockPos.MutableBlockPos mutable1,
		BlockPos.MutableBlockPos mutable2,
		Optional<Holder<Biome>> optionalBiomeHolder,
		boolean cancelIfCantSnow
	) {
		final Biome biome = optionalBiomeHolder.orElse(level.getBiome(mutable1)).value();
		if (cancelIfCantSnow && biome.warmEnoughToRain(mutable1, level.getSeaLevel())) return SnowGenerationState.CANCEL;

		if (!biome.shouldSnow(level, mutable1)) return SnowGenerationState.CONTINUE;

		final BlockState replacingState = SnowyBlockUtils.replaceWithWorldgenSnowyEquivalent(level, level.getBlockState(mutable1), mutable1);
		if (SnowloggingUtils.canSnowlog(replacingState) && !SnowloggingUtils.isSnowlogged(replacingState)) {
			level.setBlock(mutable1, replacingState.setValue(SnowloggingUtils.SNOW_LAYERS, 1), Block.UPDATE_CLIENTS);
		} else {
			level.setBlock(mutable1, Blocks.SNOW.defaultBlockState(), Block.UPDATE_CLIENTS);
		}

		final BlockState belowState = level.getBlockState(mutable2.setWithOffset(mutable1, Direction.DOWN));
		if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
			level.setBlock(mutable2, belowState.setValue(BlockStateProperties.SNOWY, true), Block.UPDATE_CLIENTS);
		}

		return SnowGenerationState.CONTINUE;
	}

	private static int findLowestHeightForSnow(WorldGenLevel level, int x, int z) {
		int bottomHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(x, bottomHeight, z);

		BlockState state;
		while (
			((state = level.getBlockState(mutable.move(Direction.DOWN))).is(WWBlockTags.SNOW_GENERATION_CAN_SEARCH_THROUGH) || state.isAir())
				&& state.getFluidState().isEmpty()
		) {
			bottomHeight -= 1;
		}

		return bottomHeight;
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		final BlockPos origin = context.origin();
		final WorldGenLevel level = context.level();
		final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos mutableSnowPos = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos mutableSnowPos2 = new BlockPos.MutableBlockPos();
		final BlockPos.MutableBlockPos mutableIcePos = new BlockPos.MutableBlockPos();
		final int posX = origin.getX();
		final int posZ = origin.getZ();

		boolean returnValue = false;
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				final int x = posX + i;
				final int z = posZ + j;
				final int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				final int bottomHeight = findLowestHeightForSnow(level, x, z);

				if (height > bottomHeight) {
					mutable.set(x, height, z);
					mutableSnowPos.set(x, bottomHeight, z);
					final Holder<Biome> biomeHolder = level.getBiome(mutable);
					final Holder<Biome> lowerBiomeHolder = level.getBiome(mutableSnowPos);

					mutableIcePos.setWithOffset(mutableSnowPos, Direction.DOWN);
					if (lowerBiomeHolder.value().shouldFreeze(level, mutableIcePos, false)) {
						level.setBlock(mutableIcePos, Blocks.ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
					}

					final Optional<Holder<Biome>> optionalBiomeHolder = biomeHolder.equals(lowerBiomeHolder) ? Optional.of(biomeHolder) : Optional.empty();
					returnValue = placeSnowAtPos(level, mutable, mutableSnowPos, mutableSnowPos2, optionalBiomeHolder) || returnValue;
				}
			}
		}
		return returnValue;
	}

	private enum SnowGenerationState {
		CANCEL,
		CONTINUE;
	}
}
