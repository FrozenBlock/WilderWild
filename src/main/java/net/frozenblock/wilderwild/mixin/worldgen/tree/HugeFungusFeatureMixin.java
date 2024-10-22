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

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
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
			}
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
		return false;
	}

	@ModifyVariable(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		),
		ordinal = 1
	)
	public BlockPos wilderWild$placeUpdateOrigin(BlockPos value, @Share("wilderWild$newPos") LocalRef<BlockPos> newPos) {
		return newPos.get();
	}

	@Unique
	private boolean wilderWild$canGrowThickFungus(Level level, BlockPos pos, Block fungus) {
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			if (!level.getBlockState(pos.relative(direction)).is(fungus)) return false;
		}
		return true;
	}

	@Unique
	private void wilderWild$clearFungi(Level level, BlockPos pos) {
		level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			level.setBlock(pos.relative(direction), Blocks.AIR.defaultBlockState(), Block.UPDATE_INVISIBLE);
		}
	}


	@ModifyVariable(method = "placeStem", at = @At(value = "STORE"), ordinal = 1)
	public boolean wilderWild$placeStemShouldPlace(
		boolean isCorner, @Share("wilderWild$isCorner") LocalRef<Boolean> isCornerRef
	) {
		isCornerRef.set(isCorner);
		return isCorner;
	}

	@WrapOperation(
		method = "placeStem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/WorldGenLevel;destroyBlock(Lnet/minecraft/core/BlockPos;Z)Z"
		)
	)
	public boolean wilderWild$placeStemPlantedA(
		WorldGenLevel instance, BlockPos pos, boolean drop, Operation<Boolean> original, @Local(argsOnly = true) RandomSource random,
		@Share("wilderWild$isCorner") LocalRef<Boolean> isCorner, @Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		if (WWBlockConfig.get().thickBigFungusGrowth) {
			if (!isCorner.get() || random.nextFloat() < 0.1F) {
				shouldPlace.set(true);
				return original.call(instance, pos, drop);
			}
			shouldPlace.set(false);
			return false;
		}
		return original.call(instance, pos, drop);
	}

	@WrapOperation(
		method = "placeStem",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	public boolean wilderWild$placeStemPlantedB(
		WorldGenLevel instance, BlockPos pos, BlockState blockState, int flag, Operation<Boolean> original,
		@Local(argsOnly = true) RandomSource random, @Share("wilderWild$shouldPlace") LocalRef<Boolean> shouldPlace
	) {
		if (shouldPlace.get()) {
			return original.call(instance, pos, blockState, flag);
		}
		return false;
	}
}
