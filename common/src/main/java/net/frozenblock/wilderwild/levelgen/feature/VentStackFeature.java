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
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.material.FluidState;

public record VentStackFeature(
	Holder<BlockStateProvider> ventBlock,
	Holder<BlockStateProvider> baseBlock,
	Holder<BlockStateProvider> innerBlock,
	CaveSurface surface,
	IntProvider height,
	HolderSet<Block> replaceable
) implements Feature {
	public static final MapCodec<VentStackFeature> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		BlockStateProvider.CODEC.fieldOf("vent_block").forGetter(VentStackFeature::ventBlock),
		BlockStateProvider.CODEC.fieldOf("base_block").forGetter(VentStackFeature::baseBlock),
		BlockStateProvider.CODEC.fieldOf("inner_block").forGetter(VentStackFeature::innerBlock),
		CaveSurface.CODEC.fieldOf("surface").forGetter(VentStackFeature::surface),
		IntProviders.NON_NEGATIVE_CODEC.fieldOf("height").forGetter(VentStackFeature::height),
		RegistryCodecs.holderSet(Registries.BLOCK).fieldOf("replaceable").forGetter(VentStackFeature::replaceable)
	).apply(instance, VentStackFeature::new));
	private static final int MAX_CONNECT_TO_SURFACE_DIST = 8;

	@Override
	public MapCodec<VentStackFeature> codec() {
		return CODEC;
	}

	@Override
	public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos origin) {
		final Predicate<BlockState> replaceableTag = state -> !state.is(BlockTags.FEATURES_CANNOT_REPLACE);
		final Predicate<BlockState> validBlockToReplace = this::isValidBlockToReplace;
		final Direction direction = this.surface.getDirection();
		final Direction oppositeDirection = direction.getOpposite();

		final BlockPos.MutableBlockPos mutable = origin.mutable();
		final int height = this.height.sample(random);
		mutable.move(oppositeDirection, height);

		if (isAirOrWaterAndReplaceable(level.getBlockState(mutable))) {
			final BlockState ventState = this.ventBlock.value().getOptionalState(level, random, mutable);
			if (ventState == null) return false;
			this.setBlock(level, mutable, ventState);

			final BlockState innerState = this.innerBlock.value().getOptionalState(level, random, mutable);
			if (innerState != null) {
				this.safeSetBlock(level, mutable.move(direction), innerState, replaceableTag);
				mutable.move(oppositeDirection);
			}
		} else {
			return false;
		}

		for (Direction horizontal : Direction.Plane.HORIZONTAL) {
			if (random.nextFloat() > 0.25F) continue;

			mutable.move(horizontal);
			final BlockState baseState = this.baseBlock.value().getOptionalState(level, random, mutable);
			if (baseState != null) this.safeSetBlock(level, mutable, baseState, validBlockToReplace);
			mutable.move(horizontal.getOpposite());
		}

		for (int i = 0; i < Math.max(height - 1, 1); i++) {
			origin = mutable.move(direction).immutable();
			for (Direction horizontal : Direction.Plane.HORIZONTAL) {
				mutable.move(horizontal);
				final BlockState baseState = this.baseBlock.value().getOptionalState(level, random, mutable);
				if (baseState != null) this.safeSetBlock(level, mutable, baseState, validBlockToReplace);

				mutable.move(horizontal.getOpposite());
				final BlockState innerState = this.innerBlock.value().getOptionalState(level, random, mutable);
				if (innerState != null) this.safeSetBlock(level, mutable, innerState, validBlockToReplace);
			}
		}

		mutable.setWithOffset(origin, direction);
		origin = mutable.immutable();

		boolean cancelConnectionIteration = false;
		for (int i = 0; i < MAX_CONNECT_TO_SURFACE_DIST; i++) {
			if (cancelConnectionIteration) break;

			for (BlockPos pos : BlockPos.betweenClosed(origin.offset(-1, 0 ,-1), origin.offset(1, 0 ,1))) {
				mutable.setWithOffset(pos, 0, this.surface.getY() * i, 0);

				BlockState state = this.baseBlock.value().getOptionalState(level, random, mutable);
				if (mutable.getX() == origin.getX() && mutable.getZ() == origin.getZ()) {
					final BlockState innerState = this.innerBlock.value().getOptionalState(level, random, mutable);
					if (innerState != null) state = innerState;
				}

				if (!isAirOrWaterAndReplaceable(level.getBlockState(mutable))) cancelConnectionIteration = true;

				if (state != null) this.safeSetBlock(level, mutable, state, validBlockToReplace);
			}
		}

		return true;
	}

	private boolean isValidBlockToReplace(BlockState blockState) {
		return blockState.is(this.replaceable) || isAirOrWaterAndReplaceable(blockState);
	}

	private static boolean isAirOrWaterAndReplaceable(BlockState state) {
		final FluidState fluidState = state.getFluidState();
		return state.isAir() || (fluidState.is(FluidTags.WATER) && fluidState.isFull() && state.canBeReplaced());
	}
}
