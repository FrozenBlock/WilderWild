/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.impl.feature;

import com.mojang.serialization.Codec;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class HydrothermalVentFeature extends Feature<NoneFeatureConfiguration> {
	private static final IntProvider HEIGHT_PROVIDER = BiasedToBottomInt.of(0, 3);
	private static final int MAX_CONNECT_TO_FLOOR_DIST = 8;

	public HydrothermalVentFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		LevelAccessor levelAccessor = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		RandomSource randomSource = featurePlaceContext.random();

		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int height = HEIGHT_PROVIDER.sample(randomSource);
		mutableBlockPos.move(Direction.UP, height);

		if (isValidWaterToReplaceAt(levelAccessor, mutableBlockPos)) {
			levelAccessor.setBlock(mutableBlockPos, WWBlocks.GEYSER.defaultBlockState(), Block.UPDATE_CLIENTS);
			levelAccessor.setBlock(mutableBlockPos.move(Direction.DOWN), Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
			mutableBlockPos.move(Direction.UP);
		} else {
			return false;
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (randomSource.nextFloat() <= 0.25F) {
				mutableBlockPos.move(direction);
				if (isValidBlockToReplaceAt(levelAccessor, mutableBlockPos)) {
					levelAccessor.setBlock(mutableBlockPos, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
				mutableBlockPos.move(direction.getOpposite());
			}
		}

		for (int i = 0; i < Math.max(height - 1, 1); i++) {
			blockPos = mutableBlockPos.move(Direction.DOWN).immutable();
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				mutableBlockPos.move(direction);
				if (isValidBlockToReplaceAt(levelAccessor, mutableBlockPos)) {
					levelAccessor.setBlock(mutableBlockPos, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
				mutableBlockPos.move(direction.getOpposite());
				if (isValidBlockToReplaceAt(levelAccessor, mutableBlockPos)) {
					levelAccessor.setBlock(mutableBlockPos, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		}

		mutableBlockPos.setWithOffset(blockPos, Direction.DOWN);
		blockPos = mutableBlockPos.immutable();

		for (int i = 0; i < MAX_CONNECT_TO_FLOOR_DIST; i++) {
			mutableBlockPos.setWithOffset(blockPos, 0, -i, 0);
			if (levelAccessor.getBlockState(mutableBlockPos).is(WWBlocks.GABBRO)) {
				levelAccessor.setBlock(mutableBlockPos, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
			}
		}

		mutableBlockPos.set(blockPos);
		for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-1, 0 ,-1), blockPos.offset(1, 0 ,1))) {
			for (int i = 0; i < MAX_CONNECT_TO_FLOOR_DIST; i++) {
				mutableBlockPos.setWithOffset(pos, 0, -i, 0);
				if (isValidWaterToReplaceAt(levelAccessor, mutableBlockPos)) {
					levelAccessor.setBlock(mutableBlockPos, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);
				} else {
					break;
				}
			}
		}

		return true;
	}

	protected static boolean isValidWaterToReplaceAt(@NotNull LevelAccessor levelAccessor, BlockPos pos) {
		BlockState blockState = levelAccessor.getBlockState(pos);
		FluidState fluidState = blockState.getFluidState();
		return isReplaceableOrWater(blockState, fluidState);
	}

	protected static boolean isValidBlockToReplaceAt(@NotNull LevelAccessor levelAccessor, BlockPos pos) {
		BlockState blockState = levelAccessor.getBlockState(pos);
		FluidState fluidState = blockState.getFluidState();
		return blockState.is(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE) || isReplaceableOrWater(blockState, fluidState);
	}

	protected static boolean isReplaceableOrWater(BlockState blockState, @NotNull FluidState fluidState) {
		return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8 && blockState.canBeReplaced();
	}

}
