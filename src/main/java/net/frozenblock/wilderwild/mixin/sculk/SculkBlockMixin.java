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

package net.frozenblock.wilderwild.mixin.sculk;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import java.util.Iterator;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.impl.SlabWallStairSculkBehavior;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = SculkBlock.class, priority = 69420)
public class SculkBlockMixin {

	@Unique
	private static final int WILDERWILD$OSSEOUS_GROWTH_ATTEMPTS_MIN = 2;
	@Unique
	private static final int WILDERWILD$OSSEOUS_GROWTH_ATTEMPTS_MAX = 15;
	@Unique
	private static final int WILDERWILD$OSSEOUS_SCULK_WORLDGEN_CHANCE = 5;
	@Unique
	private static final int WILDERWILD$OSSEOUS_SCULK_CHANCE = 7;
	@Unique
	private static final int WILDERWILD$OSSEOUS_SCULK_WORLDGEN_CANCELLING_BLOCK_COUNT = 3;
	@Unique
	private static final float WILDERWILD$BELOW_GROWTH_CHANCE = 0.75F;
	@Unique
	private volatile boolean wilderWild$canPlaceOsseousSculk;

	@Inject(method = "canPlaceGrowth", at = @At("HEAD"), cancellable = true)
	private static void wilderWild$canPlaceGrowth(LevelAccessor level, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
		if (!level.getBlockState(pos).isFaceSturdy(level, pos, Direction.UP)) info.setReturnValue(false);
	}

	@Unique
	private static boolean wilderWild$ancientCityOrPillarNearby(LevelAccessor level, BlockPos pos) {
		int foundCancellingBlocks = 0;
		final Iterator<BlockPos> poses = BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2)).iterator();
		while (true) {
			if (!poses.hasNext()) return false;
			final BlockState state = level.getBlockState(poses.next());
			if (state.is(WWBlockTags.ANCIENT_CITY_BLOCKS)) ++foundCancellingBlocks;
			if (foundCancellingBlocks >= WILDERWILD$OSSEOUS_SCULK_WORLDGEN_CANCELLING_BLOCK_COUNT) return true;
		}
	}

	@Unique
	private static boolean wilderWild$canPlaceOsseousSculk(BlockPos pos, boolean worldGen, LevelAccessor level) {
		if (!WWBlockConfig.OSSEOUS_SCULK_GENERATION) return false;
		if (!worldGen) return level.getRandom().nextInt(0, WILDERWILD$OSSEOUS_SCULK_CHANCE) == 0;
		if (!wilderWild$ancientCityOrPillarNearby(level, pos)) return level.getRandom().nextInt(0, WILDERWILD$OSSEOUS_SCULK_WORLDGEN_CHANCE) == 0;
		return false;
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
		BlockPos pos,
		RandomSource random,
		SculkSpreader spreader,
		boolean bl,
		@Share("wilderWild$placingBelow") LocalBooleanRef placingBelow
	) {
		return this.wilderWild$canPlaceGrowthBelow(level, cursor.getPos(), spreader.isWorldGeneration(), placingBelow) || original;
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
		SculkSpreader spreader,
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
		final boolean isWorldgen = spreader.isWorldGeneration();

		if (placingBelow.get()) {
			final BlockPos belowCharge = chargePos.below();
			BlockState newPlacementState = null;
			if (this.wilderWild$canPlaceOsseousSculk) {
				newPlacementState = WWBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.FACING, Direction.DOWN);
			} else if (WWBlockConfig.HANGING_TENDRIL_GENERATION) {
				newPlacementState = WWBlocks.HANGING_TENDRIL.defaultBlockState();
			}

			if (newPlacementState != null) {
				placementState.set(newPlacementState);
				placementPos.set(belowCharge);
				additionalGrowthCost.set(isWorldgen ? 3 : 1);
			}
		}

		if (WWBlockConfig.SCULK_BUILDING_BLOCKS_GENERATION) {
			final BlockState chargeState = level.getBlockState(chargePos);
			if ((isWorldgen && chargeState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) || chargeState.is(WWBlockTags.SCULK_STAIR_REPLACEABLE)) {
				placementState.set(WWBlocks.SCULK_STAIRS.withPropertiesOf(chargeState));
			} else if ((isWorldgen && chargeState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) || chargeState.is(WWBlockTags.SCULK_SLAB_REPLACEABLE)) {
				placementState.set(WWBlocks.SCULK_SLAB.withPropertiesOf(chargeState));
			} else if ((isWorldgen && chargeState.is(WWBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) || chargeState.is(WWBlockTags.SCULK_WALL_REPLACEABLE)) {
				placementState.set(WWBlocks.SCULK_WALL.withPropertiesOf(chargeState));
			}
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
		if (placementPos.get() == null || placementState.get() == null) return;
		if (canPlace.get()) {
			args.set(0, placementPos.get());
			args.set(1, placementState.get());
		}
		placedPos.set(args.get(0));
		placedState.set(args.get(1));
	}

	@WrapOperation(
		method = "attemptUseCharge",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/LevelAccessor;playSound(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"
		)
	)
	private void wilderWild$newSounds(
		LevelAccessor instance, Entity entity, BlockPos pos, SoundEvent soundEvent, SoundSource soundSource, float volume, float pitch,
		Operation<Void> original, @Share("wilderWild$placedPos") LocalRef<BlockPos> placedPos, @Share("wilderWild$placedState") LocalRef<BlockState> placedState
	) {
		if (placedPos.get() != null && placedState.get()!= null) {
			final SoundType soundType = placedState.get().getSoundType();
			pos = placedPos.get();
			soundEvent = soundType.getPlaceSound();
			volume = soundType.getVolume();
			pitch = soundType.getPitch() * 0.8F;
		}
		original.call(instance, entity, pos, soundEvent, soundSource, volume, pitch);
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
		BlockPos pos,
		RandomSource random,
		SculkSpreader spreader,
		boolean shouldConvertBlocks,
		CallbackInfoReturnable<Integer> info,
		@Share("wilderWild$placedPos") LocalRef<BlockPos> placedPos,
		@Share("wilderWild$placedState") LocalRef<BlockState> placedState
	) {
		if (placedPos.get() == null || placedState.get() == null) return;

		if (spreader.isWorldGeneration() && placedState.get().getBlock() instanceof OsseousSculkBlock osseousSculkBlock) {
			final int growthAttempts = random.nextInt(WILDERWILD$OSSEOUS_GROWTH_ATTEMPTS_MIN, WILDERWILD$OSSEOUS_GROWTH_ATTEMPTS_MAX);
			for (int a = 0; a < growthAttempts; a++) {
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
		final Integer additional = additionalGrowthCost.get();
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
		@Local(ordinal = 0) BlockState state
	) {
		if (!this.wilderWild$canPlaceOsseousSculk || state.is(Blocks.SCULK_SHRIEKER)) return;
		state = WWBlocks.OSSEOUS_SCULK.defaultBlockState();
		info.setReturnValue(
			state.hasProperty(BlockStateProperties.WATERLOGGED) && !level.getFluidState(pos).isEmpty()
				? state.setValue(BlockStateProperties.WATERLOGGED, true)
				: state
		);
	}

	@Unique
	private boolean wilderWild$canPlaceGrowthBelow(LevelAccessor level, BlockPos pos, boolean isWorldGen, LocalBooleanRef placingBelow) {
		if (!WWBlockConfig.OSSEOUS_SCULK_GENERATION && !WWBlockConfig.HANGING_TENDRIL_GENERATION) return false;
		this.wilderWild$canPlaceOsseousSculk = wilderWild$canPlaceOsseousSculk(pos, isWorldGen, level);

		if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)) {
			final BlockState state = level.getBlockState(pos.below());

			final boolean isBlockStateReplaceable = state.canBeReplaced()
				|| (this.wilderWild$canPlaceOsseousSculk && state.is(Blocks.LAVA))
				|| state.is(Blocks.SCULK_VEIN);

			final boolean canPlaceBelow = this.wilderWild$canPlaceOsseousSculk ? isBlockStateReplaceable
				: WWBlockConfig.HANGING_TENDRIL_GENERATION && isBlockStateReplaceable;

			if (canPlaceBelow && level.getRandom().nextFloat() >= WILDERWILD$BELOW_GROWTH_CHANCE) {
				placingBelow.set(true);
				return true;
			}
		}

		placingBelow.set(false);
		return false;
	}

}
