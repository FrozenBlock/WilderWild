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

package net.frozenblock.wilderwild.mixin.sculk;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import java.util.List;
import net.frozenblock.lib.block.api.sculk.BooleanPropertySculkBehavior;
import net.frozenblock.wilderwild.block.impl.SlabWallStairSculkBehavior;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SculkVeinBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkSpreader.ChargeCursor.class)
public class SculkSpreaderChargeCursorMixin {

	@Unique
	private static boolean wilderWild$isReplaceableBuildingBlock(BlockState state, boolean worldgen) {
		if (WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION) {
			return worldgen
				? state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)
				: state.is(WWBlockTags.SCULK_STAIR_REPLACEABLE) || state.is(WWBlockTags.SCULK_WALL_REPLACEABLE) || state.is(WWBlockTags.SCULK_SLAB_REPLACEABLE);
		}
		return false;
	}

	@Inject(
		method = "isMovementUnobstructed",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/BlockPos;subtract(Lnet/minecraft/core/Vec3i;)Lnet/minecraft/core/BlockPos;",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private static void wilderWild$isMovementUnobstructed(LevelAccessor level, BlockPos startPos, BlockPos spreadPos, CallbackInfoReturnable<Boolean> info) {
		if (wilderWild$isReplaceableBuildingBlock(level.getBlockState(spreadPos), false)) {
			info.setReturnValue(true);
		}
	}

	@Inject(
		method = "getValidMovementPos",
		at = @At(value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private static void wilderWild$getValidMovementPos(
		LevelAccessor level, BlockPos pos, RandomSource random, CallbackInfoReturnable<BlockPos> info,
		@Local(ordinal = 0) BlockPos.MutableBlockPos mutable, @Local(ordinal = 1) BlockPos.MutableBlockPos mutable2
	) {
		BlockState state = level.getBlockState(mutable2);
		if (wilderWild$isReplaceableBuildingBlock(state, false) && isMovementUnobstructed(level, pos, mutable2)) {
			mutable.set(mutable2);
			if (SculkVeinBlock.hasSubstrateAccess(level, state, mutable2)) {
				info.cancel();
			}
			info.setReturnValue(mutable.equals(pos) ? null : mutable);
		}
	}

	@Unique
	private static boolean wilderWild$isMovementUnobstructedWorldgen(LevelAccessor level, @NotNull BlockPos fromPos, BlockPos toPos) {
		if (fromPos.distManhattan(toPos) == 1) return true;
		BlockState cheatState = level.getBlockState(toPos);

		boolean isSpreadableStoneChest = cheatState.is(WWBlocks.STONE_CHEST) && !cheatState.getValue(WWBlockStateProperties.HAS_SCULK);
		if (wilderWild$isReplaceableBuildingBlock(cheatState, true) || isSpreadableStoneChest) return true;

		BlockPos blockPos = toPos.subtract(fromPos);
		Direction direction = Direction.fromAxisAndDirection(Direction.Axis.X, blockPos.getX() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
		Direction direction2 = Direction.fromAxisAndDirection(Direction.Axis.Y, blockPos.getY() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
		Direction direction3 = Direction.fromAxisAndDirection(Direction.Axis.Z, blockPos.getZ() < 0 ? Direction.AxisDirection.NEGATIVE : Direction.AxisDirection.POSITIVE);
		if (blockPos.getX() == 0) return isUnobstructed(level, fromPos, direction2) || isUnobstructed(level, fromPos, direction3);
		if (blockPos.getY() == 0) return isUnobstructed(level, fromPos, direction) || isUnobstructed(level, fromPos, direction3);
		return isUnobstructed(level, fromPos, direction) || isUnobstructed(level, fromPos, direction2);
	}

	@Unique
	@NotNull
	private static BlockPos wilderWild$getValidMovementPosWorldgen(LevelAccessor level, @NotNull BlockPos pos, RandomSource random) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		BlockPos.MutableBlockPos mutableBlockPos2 = pos.mutable();
		for (Vec3i vec3i : getRandomizedNonCornerNeighbourOffsets(random)) {
			mutableBlockPos2.setWithOffset(pos, vec3i);
			BlockState blockState = level.getBlockState(mutableBlockPos2);
			boolean canReturn = false;
			BlockState state = level.getBlockState(mutableBlockPos2);
			if (wilderWild$isReplaceableBuildingBlock(state, true) && wilderWild$isMovementUnobstructedWorldgen(level, pos, mutableBlockPos2)) {
				mutableBlockPos.set(mutableBlockPos2);
				canReturn = true;
				if (SculkVeinBlock.hasSubstrateAccess(level, state, mutableBlockPos2)) {
					return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
				}
			}

			if (canReturn) {
				return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
			}
			if (!(blockState.getBlock() instanceof SculkBehaviour) || !isMovementUnobstructed(level, pos, mutableBlockPos2)) continue;
			mutableBlockPos.set(mutableBlockPos2);
			if (!SculkVeinBlock.hasSubstrateAccess(level, blockState, mutableBlockPos2)) continue;
			break;
		}
		return mutableBlockPos.equals(pos) ? null : mutableBlockPos;
	}

	//SHADOWS
	@Shadow
	private static boolean isMovementUnobstructed(LevelAccessor level, BlockPos sourcePos, BlockPos targetPos) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
	}

	@Shadow
	private static List<Vec3i> getRandomizedNonCornerNeighbourOffsets(RandomSource random) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
	}

	@Shadow
	private static boolean isUnobstructed(LevelAccessor level, BlockPos pos, Direction direction) {
		throw new AssertionError("Mixin injection failed - WilderWild SculkSpreaderChargeCursorMixin.");
	}

	@Inject(method = "update", at = @At("HEAD"))
	private void wilderWild$newSculkBehaviour(
		LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader spreader, boolean spread, CallbackInfo info,
		@Share("wilderWild$isWorldGen") LocalBooleanRef isWorldGen
	) {
		isWorldGen.set(spreader.isWorldGeneration());
	}

	@WrapOperation(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SculkSpreader$ChargeCursor;getBlockBehaviour(Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/level/block/SculkBehaviour;"
		)
	)
	private SculkBehaviour wilderWild$newSculkBehaviour(
		BlockState state, Operation<SculkBehaviour> operation,
		@Share("wilderWild$isWorldGen") LocalBooleanRef isWorldGen
	) {
		if (isWorldGen.get()) {
			if (wilderWild$isReplaceableBuildingBlock(state, true)) {
				return new SlabWallStairSculkBehavior();
			} else if (state.is(WWBlocks.STONE_CHEST)) {
				return new BooleanPropertySculkBehavior(WWBlockStateProperties.HAS_SCULK, true);
			}
		} else {
			if (wilderWild$isReplaceableBuildingBlock(state, false)) {
				return new SlabWallStairSculkBehavior();
			}
		}
		return operation.call(state);
	}

	@WrapOperation(
		method = "update",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SculkSpreader$ChargeCursor;getValidMovementPos(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)Lnet/minecraft/core/BlockPos;"
		)
	)
	private BlockPos wilderWild$newValidMovementPos(
		LevelAccessor levelAccessor, BlockPos blockPos, RandomSource random, Operation<BlockPos> operation,
		@Share("wilderWild$isWorldGen") LocalBooleanRef isWorldGen
	) {
		if (isWorldGen.get()) {
			return wilderWild$getValidMovementPosWorldgen(levelAccessor, blockPos, random);
		} else {
			return operation.call(levelAccessor, blockPos, random);
		}
	}

}
