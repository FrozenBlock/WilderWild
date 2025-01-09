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

package net.frozenblock.wilderwild.mixin.sculk;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.Iterator;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.impl.SlabWallStairSculkBehavior;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = SculkBlock.class, priority = 69420)
public abstract class SculkBlockMixin {

	/**
	 * The maximum height of an Osseous Sculk pillar.
	 * <p>
	 * An Osseous Sculk pillar cannot grow further than this height.
	 */
	@Unique
	private static final int WILDERWILD$MAX_HEIGHT = 15;

	/**
	 * Decides how commonly Osseous Sculk pillars will grow during worldgen.
	 * <p>
	 * If set to 1 or higher, the pillars will never grow.
	 */
	@Unique
	private static final double WILDERWILD$OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16D;

	@Unique
	private volatile boolean wilderWild$canPlaceOsseousSculk;

	@Inject(method = "canPlaceGrowth", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$canPlaceGrowth(LevelAccessor level, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) {
			info.setReturnValue(false);
		}
	}

	@Unique
	private static boolean wilderWild$ancientCityOrPillarNearby(LevelAccessor level, @NotNull BlockPos pos) {
		int i = 0;
		Iterator<BlockPos> var4 = BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2)).iterator();
		do {
			if (!var4.hasNext()) {
				return false;
			}
			BlockPos blockPos = var4.next();
			BlockState blockState2 = level.getBlockState(blockPos);
			if (blockState2.is(WWBlockTags.ANCIENT_CITY_BLOCKS) || (blockState2.is(WWBlocks.OSSEOUS_SCULK) && (blockPos.getX() != pos.getX() && blockPos.getZ() != pos.getZ()))) {
				++i;
			}
			if (i >= 3) {
				return true;
			}
		} while (true);
	}

	@Unique
	private static boolean wilderWild$canPlaceOsseousSculk(BlockPos pos, boolean worldGen, LevelAccessor level) {
		if (worldGen) {
			if (!wilderWild$ancientCityOrPillarNearby(level, pos)) {
				return level.getRandom().nextFloat() <= WILDERWILD$OSSEOUS_SCULK_WORLD_GEN_THRESHOLD;
			}
			return false;
		}
		return level.getRandom().nextInt(0, 7) == 3;
	}

	@ModifyExpressionValue(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"
		)
	)
	private boolean wilderWild$newWorldgenCharge(
		boolean original,
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos blockPos,
		RandomSource random,
		SculkSpreader spreader,
		boolean bl,
		@Share("wilderWild$placingBelow") LocalBooleanRef placingBelow
	) {
		return this.wilderWild$canPlaceGrowth(level, cursor.getPos(), spreader.isWorldGeneration(), placingBelow) || original;
	}

	@Inject(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;"
		)
	)
	private void wilderWild$getPlacementState(
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos catalystPos,
		RandomSource random,
		@NotNull SculkSpreader spreader,
		boolean shouldConvertBlocks,
		CallbackInfoReturnable<Integer> info,
		@Local(ordinal = 0) int chargeAmount,
		@Local(ordinal = 1) BlockPos chargePos,
		@Local(ordinal = 1) int growthSpawnCost,
		@Local(ordinal = 2) BlockPos aboveChargePos,
		@Share("wilderWild$placementPos") LocalRef<BlockPos> placementPos,
		@Share("wilderWild$placementState") LocalRef<BlockState> placementState,
		@Share("wilderWild$placingBelow") LocalBooleanRef placingBelow,
		@Share("wilderWild$additionalGrowthCost") LocalRef<Integer> additionalGrowthCost,
		@Share("wilderWild$canPlace") LocalBooleanRef canPlace
	) {
		placementPos.set(null);
		placementState.set(null);
		additionalGrowthCost.set(null);
		boolean isWorldgen = spreader.isWorldGeneration();

		if (placingBelow.get()) {
			BlockPos belowCharge = chargePos.below();
			if (this.wilderWild$canPlaceOsseousSculk) {
				int pillarHeight = level.getRandom().nextInt(4, WILDERWILD$MAX_HEIGHT);
				placementState.set(WWBlocks.OSSEOUS_SCULK.defaultBlockState()
					.setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight)
					.setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1)
					.setValue(OsseousSculkBlock.FACING, Direction.DOWN)
				);
			} else {
				placementState.set(WWBlocks.HANGING_TENDRIL.defaultBlockState());
			}
			placementPos.set(belowCharge);
			additionalGrowthCost.set(1);
		}

		if (isWorldgen && placementState.get() != null && placementState.get().is(WWBlocks.OSSEOUS_SCULK)) {
			additionalGrowthCost.set(growthSpawnCost);
		}

		BlockState chargePosState = level.getBlockState(chargePos);
		if ((isWorldgen && chargePosState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) || chargePosState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE)) {
			placementState.set(WWBlocks.SCULK_STAIRS.withPropertiesOf(chargePosState));
		} else if ((isWorldgen && chargePosState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) || chargePosState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE)) {
			placementState.set(WWBlocks.SCULK_SLAB.withPropertiesOf(chargePosState));
		} else if ((isWorldgen && chargePosState.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) || chargePosState.is(WWBlockTags.SCULK_WALL_REPLACEABLE)) {
			placementState.set(WWBlocks.SCULK_WALL.withPropertiesOf(chargePosState));
		}

		canPlace.set(placementState.get() != null && placementPos.get() != null);
	}

	@ModifyArgs(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"
		)
	)
	private void wilderWild$newPlace(
		Args args,
		@Share("wilderWild$placementPos") LocalRef<BlockPos> placementPos,
		@Share("wilderWild$placementState") LocalRef<BlockState> placementState,
		@Share("wilderWild$canPlace") LocalBooleanRef canPlace,
		@Share("wilderWild$placedPos") LocalRef<BlockPos> placedPos,
		@Share("wilderWild$placedState") LocalRef<BlockState> placedState
	) {
		if (placementPos.get() == null || placementState.get() == null) {
			return;
		}
		if (canPlace.get()) {
			args.set(0, placementPos.get());
			args.set(1, placementState.get());
		}
		placedPos.set(args.get(0));
		placedState.set(args.get(1));
	}

	@ModifyArgs(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
		)
	)
	private void wilderWild$newSounds(
		Args args,
		@Share("wilderWild$placedPos") LocalRef<BlockPos> placedPos,
		@Share("wilderWild$placedState") LocalRef<BlockState> placedState
	) {
		if (placedPos.get() == null || placedState.get() == null) {
			return;
		}
		args.set(1, placedPos.get());
		SoundType soundType = placedState.get().getSoundType();
		args.set(2, soundType.getPlaceSound());
		args.set(4, soundType.getVolume());
		args.set(5, soundType.getPitch());
	}

	@Inject(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V",
			shift = At.Shift.AFTER
		)
	)
	private void wilderWild$handlePlacement(
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos blockPos,
		RandomSource random,
		SculkSpreader spreader,
		boolean shouldConvertBlocks,
		CallbackInfoReturnable<Integer> info,
		@Share("wilderWild$placedPos") LocalRef<BlockPos> placedPos,
		@Share("wilderWild$placedState") LocalRef<BlockState> placedState
	) {
		if (placedPos.get() == null || placedState.get() == null) {
			return;
		}
		if (spreader.isWorldGeneration() && placedState.get().getBlock() instanceof OsseousSculkBlock osseousSculkBlock) {
			int growthAmount = placedState.get().getValue(OsseousSculkBlock.HEIGHT_LEFT);
			for (int a = 0; a < growthAmount; a++) {
				osseousSculkBlock.attemptUseCharge(cursor, level, placedPos.get(), random, spreader, shouldConvertBlocks);
			}
		} else if (placedState.get().is(WWBlocks.SCULK_STAIRS) || placedState.get().is(WWBlocks.SCULK_SLAB) || placedState.get().is(WWBlocks.SCULK_WALL)) {
			SlabWallStairSculkBehavior.clearSculkVeins(level, placedPos.get());
		}
		placedPos.set(null);
		placedState.set(null);
	}

	@ModifyExpressionValue(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Ljava/lang/Math;max(II)I"
		)
	)
	private int wilderWild$newReturnValue(
		int original,
		@Share("wilderWild$additionalGrowthCost") LocalRef<Integer> additionalGrowthCost
	) {
		Integer additional = additionalGrowthCost.get();
		return additional != null ? original + additional : original;
	}

	@Inject(
		method = "getRandomGrowthState",
		at = @At(
			value = "RETURN",
			shift = At.Shift.BEFORE
		),
		cancellable = true
	)
	private void wilderWild$getRandomGrowthState(
		LevelAccessor level, BlockPos pos, RandomSource random, boolean randomize, CallbackInfoReturnable<BlockState> info,
		@Local(ordinal = 0) BlockState blockState
	) {
		if (this.wilderWild$canPlaceOsseousSculk && !blockState.is(Blocks.SCULK_SHRIEKER)) {
			int pillarHeight = level.getRandom().nextInt(4, WILDERWILD$MAX_HEIGHT);
			blockState = WWBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
			info.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !level.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState);
		}
	}

	@Unique
	private boolean wilderWild$canPlaceGrowth(@NotNull LevelAccessor level, @NotNull BlockPos pos, boolean isWorldGen, LocalBooleanRef placingBelow) {
		this.wilderWild$canPlaceOsseousSculk = wilderWild$canPlaceOsseousSculk(pos, isWorldGen, level);
		if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)) {
			BlockState blockState = level.getBlockState(pos.below());
			Block block = blockState.getBlock();
			if ((blockState.isAir() || block == Blocks.WATER || (this.wilderWild$canPlaceOsseousSculk && block == Blocks.LAVA) || block == Blocks.SCULK_VEIN) && level.getRandom().nextFloat() >= 0.75F) {
				placingBelow.set(true);
				return true;
			}
		}
		placingBelow.set(false);
		return false;
	}

}
