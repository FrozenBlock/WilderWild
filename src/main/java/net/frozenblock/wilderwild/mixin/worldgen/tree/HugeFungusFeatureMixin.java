/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.worldgen.tree;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.HugeFungusConfiguration;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(HugeFungusFeature.class)
public class HugeFungusFeatureMixin {

	/**
	 * Marks the fungus as having a thick stem if it's originally true or if it meets the conditions for a planted thick fungus.
	 *
	 * @param original if the fungus is a naturally generated thick stem
	 * @param context  the context behind the huge fungus growth
	 * @param newPos   the new center point of the huge fungus
	 * @return if the fungus should have a thick stem
	 * @see #wilderWild$placeUpdateCenter(BlockPos, LocalRef)
	 */
	@ModifyVariable(method = "place", at = @At(value = "STORE"), ordinal = 0)
	public boolean wilderWild$placeThickener(
		boolean original, FeaturePlaceContext<HugeFungusConfiguration> context,
		@Share("wilderWild$newPos") LocalRef<BlockPos> newPos
	) {
		newPos.set(context.origin());
		if (original) return true;
		if (context.config().planted && WWBlockConfig.get().thickBigFungusGrowth) {
			Level level = (Level) context.level();
			BlockPos pos = context.origin();
			Block fungus = level.getBlockState(pos).getBlock();
			if (wilderWild$canGrowThickFungus(level, pos, fungus)) {
				newPos.set(pos);
				wilderWild$clearFungi(level, pos);
				return true;
			} else {
				for (Direction direction : Direction.Plane.HORIZONTAL) {
					BlockPos adjacentPos = pos.relative(direction);
					if (level.getBlockState(adjacentPos).is(fungus)) {
						if (wilderWild$canGrowThickFungus(level, adjacentPos, fungus)) {
							newPos.set(adjacentPos);
							wilderWild$clearFungi(level, adjacentPos);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Replaces the center position of the huge fungus with newPos when thickBigFungusGrowth is enabled
	 *
	 * @param blockPos original position
	 * @param newPos   new position, potentially modified by @wilderWild$placeThickener
	 * @return center position of the huge fungus
	 * @see #wilderWild$placeThickener(boolean, FeaturePlaceContext, LocalRef)
	 */
	@ModifyVariable(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		),
		ordinal = 1
	)
	public BlockPos wilderWild$placeUpdateCenter(BlockPos blockPos, @Share("wilderWild$newPos") LocalRef<BlockPos> newPos) {
		return WWBlockConfig.get().thickBigFungusGrowth ? newPos.get() : blockPos;
	}

	/**
	 * Returns if the blocks on all horizontal sides of this are the same fungus.
	 *
	 * @param pos    position of the center fungus
	 * @param fungus this block's type
	 * @return if the blocks on all horizontal sides match the center block
	 */
	@Unique
	private boolean wilderWild$canGrowThickFungus(Level level, BlockPos pos, Block fungus) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!level.getBlockState(pos.relative(direction)).is(fungus)) return false;
		}
		return true;
	}

	/**
	 * Sets the given block and each horizontally adjacent block to air.
	 * Should be used only after using{@link #wilderWild$canGrowThickFungus(Level, BlockPos, Block) canGrowThickFungus}.
	 *
	 * @param pos position of the center fungus.
	 */
	@Unique
	private void wilderWild$clearFungi(Level level, BlockPos pos) {
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			level.setBlock(pos.relative(direction), Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
		}
	}

	/**
	 * Gives access to the variable bl in{@link HugeFungusFeature#placeStem(WorldGenLevel, RandomSource, HugeFungusConfiguration, BlockPos, int, boolean) placeStem},
	 * stored in {@code isCorner}.
	 *
	 * @param original
	 * @param isCorner
	 * @return
	 * @see #wilderWild$placeStemShouldPreserve(boolean, WorldGenLevel, RandomSource, LocalRef, LocalRef)
	 */
	@ModifyVariable(method = "placeStem", at = @At(value = "STORE"), ordinal = 1)
	public boolean wilderWild$placeStemIsCorner(
		boolean original, @Share("wilderWild$isCorner") LocalRef<Boolean> isCorner
	) {
		isCorner.set(original);
		return original;
	}

	/**
	 * If the position is in the corner, it has a 90% chance of not breaking nor placing anything.
	 * Else, it behaves as normal.
	 *
	 * @return if the block position should get preserved
	 * @see #wilderWild$placeStemIsCorner(boolean, LocalRef)
	 * @see #wilderWild$placeStemAttemptPlace(WorldGenLevel, BlockPos, BlockState, int, Operation, LocalRef)
	 */
	@ModifyExpressionValue(
		method = "placeStem",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;isAir()Z")
	)
	public boolean wilderWild$placeStemShouldPreserve(
		boolean original, WorldGenLevel level, RandomSource random,
		@Share("wilderWild$isCorner") LocalRef<Boolean> isCorner, @Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		shouldPlace.set(true);
		if (WWBlockConfig.get().thickBigFungusGrowth) {
			if (isCorner.get()) {
				if (random.nextFloat() < 0.1F) {
					// mark for destruction and place corner block
					return false;
				} else {
					// don't place corner block
					shouldPlace.set(false);
					return true;
				}
			}
		}
		return original;
	}

	/**
	 * Prevents {@link net.minecraft.world.level.LevelWriter#setBlock(BlockPos, BlockState, int) setBlock} from running if {@code shouldPlace} is false.
	 *
	 * @see #wilderWild$placeStemShouldPreserve(boolean, WorldGenLevel, RandomSource, LocalRef, LocalRef)
	 */
	@WrapOperation(
		method = "placeStem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	public boolean wilderWild$placeStemAttemptPlace(
		WorldGenLevel instance, BlockPos pos, BlockState blockState, int flag, Operation<Boolean> original,
		@Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		if (!shouldPlace.get()) {
			return false;
		}
		return original.call(instance, pos, blockState, flag);
	}
}
