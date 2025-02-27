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
import org.jetbrains.annotations.NotNull;

public class SnowBlanketFeature extends Feature<NoneFeatureConfiguration> {

	public SnowBlanketFeature(@NotNull Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	private static boolean placeSnowAtPos(
		@NotNull WorldGenLevel level,
		@NotNull BlockPos.MutableBlockPos topPos,
		@NotNull BlockPos.MutableBlockPos bottomPos,
		@NotNull BlockPos.MutableBlockPos mutable,
		@NotNull Optional<Holder<Biome>> optionalBiomeHolder
	) {
		boolean returnValue = false;
		int lowestY = bottomPos.getY() - 1;
		while (topPos.getY() > lowestY) {
			if (placeSnowLayer(level, topPos, mutable, optionalBiomeHolder)) returnValue = true;
			topPos.move(Direction.DOWN);
		}
		return returnValue;
	}

	private static boolean placeSnowLayer(
		@NotNull WorldGenLevel level,
		@NotNull BlockPos.MutableBlockPos mutablePos,
		@NotNull BlockPos.MutableBlockPos mutablePos2,
		@NotNull Optional<Holder<Biome>> optionalBiomeHolder
	) {
		BlockPos pos = mutablePos.immutable();
		if (optionalBiomeHolder.orElse(level.getBiome(pos)).value().shouldSnow(level, pos)) {
			BlockState replacingState = SnowyBlockUtils.replaceWithWorldgenSnowyEquivalent(level, level.getBlockState(pos), pos);
			if (SnowloggingUtils.canSnowlog(replacingState) && !SnowloggingUtils.isSnowlogged(replacingState)) {
				level.setBlock(pos, replacingState.setValue(SnowloggingUtils.SNOW_LAYERS, 1), Block.UPDATE_CLIENTS);
			} else {
				level.setBlock(pos, Blocks.SNOW.defaultBlockState(), Block.UPDATE_CLIENTS);
			}

			BlockState belowState = level.getBlockState(mutablePos2.setWithOffset(pos, Direction.DOWN));
			if (belowState.hasProperty(BlockStateProperties.SNOWY)) {
				level.setBlock(mutablePos2, belowState.setValue(BlockStateProperties.SNOWY, true), Block.UPDATE_CLIENTS);
			}
			return true;
		}
		return false;
	}

	private static int findLowestHeightForSnow(@NotNull WorldGenLevel level, int x, int z) {
		int bottomHeight = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
		BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos().set(x, bottomHeight, z);

		BlockState state = level.getBlockState(pos.move(Direction.DOWN));
		while ((state.is(WWBlockTags.SNOW_GENERATION_CAN_SEARCH_THROUGH) || state.isAir()) && state.getFluidState().isEmpty()) bottomHeight -= 1;

		return bottomHeight;
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
		BlockPos pos = context.origin();
		WorldGenLevel level = context.level();
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableSnowPos = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableSnowPos2 = new BlockPos.MutableBlockPos();
		BlockPos.MutableBlockPos mutableIcePos = new BlockPos.MutableBlockPos();
		boolean returnValue = false;
		int posX = pos.getX();
		int posZ = pos.getZ();

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int x = posX + i;
				int z = posZ + j;
				int height = level.getHeight(Heightmap.Types.MOTION_BLOCKING, x, z);
				int bottomHeight = findLowestHeightForSnow(level, x, z);

				if (height > bottomHeight) {
					mutablePos.set(x, height, z);
					mutableSnowPos.set(x, bottomHeight, z);
					Holder<Biome> biomeHolder = level.getBiome(mutablePos);
					Holder<Biome> lowerBiomeHolder = level.getBiome(mutableSnowPos);

					mutableIcePos.setWithOffset(mutableSnowPos, Direction.DOWN);
					if (lowerBiomeHolder.value().shouldFreeze(level, mutableIcePos, false)) {
						level.setBlock(mutableIcePos, Blocks.ICE.defaultBlockState(), Block.UPDATE_CLIENTS);
					}

					Optional<Holder<Biome>> optionalBiomeHolder = biomeHolder.equals(lowerBiomeHolder) ? Optional.of(biomeHolder) : Optional.empty();
					returnValue = returnValue || placeSnowAtPos(level, mutablePos, mutableSnowPos, mutableSnowPos2, optionalBiomeHolder);
				}
			}
		}
		return returnValue;
	}
}
