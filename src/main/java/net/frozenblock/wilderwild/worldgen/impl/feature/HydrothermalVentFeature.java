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

public class HydrothermalVentFeature extends Feature<NoneFeatureConfiguration> {
	private static final IntProvider HEIGHT_PROVIDER = BiasedToBottomInt.of(0, 3);
	private static final int MAX_CONNECT_TO_FLOOR_DIST = 8;

	public HydrothermalVentFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> featurePlaceContext) {
		final LevelAccessor level = featurePlaceContext.level();
		BlockPos blockPos = featurePlaceContext.origin();
		final RandomSource random = featurePlaceContext.random();

		final BlockPos.MutableBlockPos mutable = blockPos.mutable();
		final int height = HEIGHT_PROVIDER.sample(random);
		mutable.move(Direction.UP, height);

		if (isValidWaterToReplaceAt(level, mutable)) {
			level.setBlock(mutable, WWBlocks.GEYSER.defaultBlockState(), Block.UPDATE_CLIENTS);
			level.setBlock(mutable.move(Direction.DOWN), Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
			mutable.move(Direction.UP);
		} else {
			return false;
		}

		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (random.nextFloat() > 0.25F) continue;
			mutable.move(direction);
			if (isValidBlockToReplaceAt(level, mutable)) level.setBlock(mutable, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);
			mutable.move(direction.getOpposite());
		}

		for (int i = 0; i < Math.max(height - 1, 1); i++) {
			blockPos = mutable.move(Direction.DOWN).immutable();
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				mutable.move(direction);
				if (isValidBlockToReplaceAt(level, mutable)) level.setBlock(mutable, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);

				mutable.move(direction.getOpposite());
				if (isValidBlockToReplaceAt(level, mutable)) level.setBlock(mutable, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
			}
		}

		mutable.setWithOffset(blockPos, Direction.DOWN);
		blockPos = mutable.immutable();

		for (int i = 0; i < MAX_CONNECT_TO_FLOOR_DIST; i++) {
			mutable.setWithOffset(blockPos, 0, -i, 0);
			if (level.getBlockState(mutable).is(WWBlocks.GABBRO)) level.setBlock(mutable, Blocks.MAGMA_BLOCK.defaultBlockState(), Block.UPDATE_CLIENTS);
		}

		mutable.set(blockPos);
		for (BlockPos pos : BlockPos.betweenClosed(blockPos.offset(-1, 0 ,-1), blockPos.offset(1, 0 ,1))) {
			for (int i = 0; i < MAX_CONNECT_TO_FLOOR_DIST; i++) {
				mutable.setWithOffset(pos, 0, -i, 0);
				if (isValidWaterToReplaceAt(level, mutable)) {
					level.setBlock(mutable, WWBlocks.GABBRO.defaultBlockState(), Block.UPDATE_CLIENTS);
				} else {
					break;
				}
			}
		}

		return true;
	}

	protected static boolean isValidWaterToReplaceAt(LevelAccessor level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		final FluidState fluidState = state.getFluidState();
		return isReplaceableOrWater(state, fluidState);
	}

	protected static boolean isValidBlockToReplaceAt(LevelAccessor level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		final FluidState fluidState = state.getFluidState();
		return state.is(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE) || isReplaceableOrWater(state, fluidState);
	}

	protected static boolean isReplaceableOrWater(BlockState state, FluidState fluidState) {
		return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == FluidState.AMOUNT_FULL && state.canBeReplaced();
	}

}
