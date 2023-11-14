/*
 * Copyright 2023 FrozenBlock
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
import java.util.Iterator;
import java.util.Optional;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.block.sculk_behavior.SlabWallStairSculkBehavior;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
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
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(value = SculkBlock.class, priority = 69420)
public abstract class SculkBlockMixin {

	/**
	 * The height multiplier of the Osseous Sculk pillars.
	 * <p>
	 * At higher values, most Osseous Sculk pillars will be taller.
	 */
	@Unique
	private static final int WILDERWILD$HEIGHT_MULTIPLIER = 20;

	/**
	 * The maximum height of an Osseous Sculk pillar.
	 * <p>
	 * An Osseous Sculk pillar cannot grow further than this height.
	 */
	@Unique
	private static final int WILDERWILD$MAX_HEIGHT = 15;

	/**
	 * The randomness that the Osseous Sculk pillars will grow.
	 * <p>
	 * At lower values, the heights of the Osseous Sculk pillars will grow more gradually.
	 */
	@Unique
	private static final double WILDERWILD$RANDOMNESS = 0.9;

	/**
	 * The radius that the Osseous Sculk pillars can grow in.
	 * <p>
	 * At larger values, the area the pillars can grow in will increase,
	 * but the distance between the pillars will also increase.
	 */
	@Unique
	private static final double WILDERWILD$OSSEOUS_SCULK_AREA_SIZE = 0.09;

	/**
	 * Decides how commonly Osseous Sculk pillars will grow during worldgen.
	 * <p>
	 * If set to 1 or higher, the pillars will never grow.
	 */
	@Unique
	private static final double WILDERWILD$OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16;

	@Unique
	private boolean wilderWild$isPlacingBelow;

	@Unique
	private boolean wilderWild$canPlaceOsseousSculk;
	@Unique
	private BlockPos wilderWild$placementPos = null;
	@Unique
	private BlockState wilderWild$placementState = null;
	@Unique
	private Optional<Integer> wilderWild$additionalGrowthCost = Optional.empty();
	@Unique
	private boolean wilderWild$canPlace;
	@Unique
	private BlockPos wilderWild$placedPos;
	@Unique
	private BlockState wilderWild$placedState;

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
			if (blockState2.is(WilderBlockTags.ANCIENT_CITY_BLOCKS) || (blockState2.is(RegisterBlocks.OSSEOUS_SCULK) && (blockPos.getX() != pos.getX() && blockPos.getZ() != pos.getZ()))) {
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
				return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, WILDERWILD$OSSEOUS_SCULK_AREA_SIZE, true, true) > WILDERWILD$OSSEOUS_SCULK_WORLD_GEN_THRESHOLD;
			}
			return false;
		}
		return level.getRandom().nextInt(0, 7) == 3;
	}

	@ModifyExpressionValue(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"))
	private boolean wilderWild$newWorldgenCharge(boolean original, SculkSpreader.@NotNull ChargeCursor chargeCursor, LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, @NotNull SculkSpreader sculkSpreader, boolean bl) {
		return this.wilderWild$canPlaceGrowth(levelAccessor, chargeCursor.getPos(), sculkSpreader.isWorldGeneration()) || original;
	}

	@Inject(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;"), locals = LocalCapture.CAPTURE_FAILHARD)
	private void wilderWild$getPlacementState(SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos catalystPos, RandomSource random, @NotNull SculkSpreader sculkChargeHandler, boolean shouldConvertBlocks, CallbackInfoReturnable<Integer> cir, int chargeAmount, BlockPos chargePos, int growthSpawnCost, BlockPos aboveChargePos) {
		this.wilderWild$placementState = null;
		this.wilderWild$placementPos = null;
		this.wilderWild$additionalGrowthCost = Optional.empty();
		boolean isWorldgen = sculkChargeHandler.isWorldGeneration();

		if (this.wilderWild$isPlacingBelow) {
			BlockPos belowCharge = chargePos.below();
			if (this.wilderWild$canPlaceOsseousSculk) {
				int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, belowCharge, WILDERWILD$RANDOMNESS, false, false) * WILDERWILD$HEIGHT_MULTIPLIER, 2, WILDERWILD$MAX_HEIGHT);
				this.wilderWild$placementState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).setValue(OsseousSculkBlock.FACING, Direction.DOWN);
			} else {
				this.wilderWild$placementState = RegisterBlocks.HANGING_TENDRIL.defaultBlockState();
			}
			this.wilderWild$placementPos = belowCharge;
			this.wilderWild$additionalGrowthCost = Optional.of(1);
		}

		if (isWorldgen && this.wilderWild$placementState != null && this.wilderWild$placementState.is(RegisterBlocks.OSSEOUS_SCULK)) {
			this.wilderWild$additionalGrowthCost = Optional.of(growthSpawnCost + 2);
		}

		BlockState chargePosState = level.getBlockState(chargePos);
		if ((isWorldgen && chargePosState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) || chargePosState.is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
			this.wilderWild$placementState = RegisterBlocks.SCULK_STAIRS.withPropertiesOf(chargePosState);
		} else if ((isWorldgen && chargePosState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) || chargePosState.is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
			this.wilderWild$placementState = RegisterBlocks.SCULK_SLAB.withPropertiesOf(chargePosState);
		} else if ((isWorldgen && chargePosState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) || chargePosState.is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
			this.wilderWild$placementState = RegisterBlocks.SCULK_WALL.withPropertiesOf(chargePosState);
		}

		this.wilderWild$canPlace = this.wilderWild$placementState != null && this.wilderWild$placementPos != null;
	}

	@ModifyArgs(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
	private void wilderWild$newPlace(Args args) {
		if (this.wilderWild$placementPos == null || this.wilderWild$placementState == null) {
			return;
		}
		if (this.wilderWild$canPlace) {
			args.set(0, this.wilderWild$placementPos);
			args.set(1, this.wilderWild$placementState);
		}
		this.wilderWild$placedPos = args.get(0);
		this.wilderWild$placedState = args.get(1);
	}

	@ModifyArgs(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
	private void wilderWild$newSounds(Args args) {
		if (this.wilderWild$placedPos == null || this.wilderWild$placedState == null) {
			return;
		}
		args.set(1, this.wilderWild$placedPos);
		SoundType soundType = this.wilderWild$placedState.getSoundType();
		args.set(2, soundType.getPlaceSound());
		args.set(4, soundType.getVolume());
		args.set(5, soundType.getPitch());
	}

	@Inject(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/LevelAccessor;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", shift = At.Shift.AFTER))
	private void wilderWild$handlePlacement(SculkSpreader.ChargeCursor chargeCursor, LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, SculkSpreader sculkSpreader, boolean bl, CallbackInfoReturnable<Integer> cir) {
		if (this.wilderWild$placedState == null || this.wilderWild$placedPos == null) {
			return;
		}
		if (sculkSpreader.isWorldGeneration() && this.wilderWild$placedState.getBlock() instanceof OsseousSculkBlock osseousSculkBlock) {
			int growthAmount = Math.max(0, this.wilderWild$placedState.getValue(OsseousSculkBlock.HEIGHT_LEFT));
			for (int a = 0; a < growthAmount; a++) {
				osseousSculkBlock.worldGenSpread(this.wilderWild$placedPos, levelAccessor, randomSource);
			}
		} else if (this.wilderWild$placedState.is(RegisterBlocks.SCULK_STAIRS) || this.wilderWild$placedState.is(RegisterBlocks.SCULK_SLAB) || this.wilderWild$placedState.is(RegisterBlocks.SCULK_WALL)) {
			SlabWallStairSculkBehavior.clearSculkVeins(levelAccessor, this.wilderWild$placedPos);
		}
		this.wilderWild$placedPos = null;
		this.wilderWild$placedState = null;
	}

	@ModifyExpressionValue(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(II)I"))
	private int wilderWild$newReturnValue(int original) {
		return this.wilderWild$additionalGrowthCost.map(integer -> original + integer).orElse(original);
	}

	@Inject(method = "getRandomGrowthState", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	private void wilderWild$getRandomGrowthState(LevelAccessor level, BlockPos pos, RandomSource random, boolean randomize, CallbackInfoReturnable<BlockState> info, BlockState blockState) {
		if (this.wilderWild$canPlaceOsseousSculk && !blockState.is(Blocks.SCULK_SHRIEKER)) {
			int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sampleAbs(EasyNoiseSampler.perlinXoro, pos, WILDERWILD$RANDOMNESS, false, false) * WILDERWILD$HEIGHT_MULTIPLIER, 2, WILDERWILD$MAX_HEIGHT);
			blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
			info.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !level.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState);
		}
	}

	@Unique
	private boolean wilderWild$canPlaceGrowth(@NotNull LevelAccessor level, @NotNull BlockPos pos, boolean isWorldGen) {
		this.wilderWild$canPlaceOsseousSculk = wilderWild$canPlaceOsseousSculk(pos, isWorldGen, level);
		if (level.getBlockState(pos).isFaceSturdy(level, pos, Direction.DOWN)) {
			BlockState blockState = level.getBlockState(pos.below());
			Block block = blockState.getBlock();
			if ((blockState.isAir() || block == Blocks.WATER || (this.wilderWild$canPlaceOsseousSculk && block == Blocks.LAVA) || block == Blocks.SCULK_VEIN) && level.getRandom().nextFloat() >= 0.75F) {
				this.wilderWild$isPlacingBelow = true;
				return true;
			}
		}
		this.wilderWild$isPlacingBelow = false;
		return false;
	}

}
