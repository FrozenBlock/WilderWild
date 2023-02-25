/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.server.sculk;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = SculkBlock.class, priority = 69420)
public abstract class SculkBlockMixin {

    @Shadow
    private static boolean canPlaceGrowth(LevelAccessor level, BlockPos pos) {
		throw new AssertionError("Mixin injection failed - Wilder Wild SculkBlockMixin.");
    }

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
     * Decides how commonly Osseous Sculk pillars will grow.
     * <p>
     * If set to 1 or higher, the pillars will never grow.
     */
	@Unique
    private static final double WILDERWILD$OSSEOUS_SCULK_THRESHOLD = 0.15;

    /**
     * Decides how commonly Osseous Sculk pillars will grow during worldgen.
     * <p>
     * If set to 1 or higher, the pillars will never grow.
     * <p>
     * <STRONG>CEILINGS IN WORLDGEN ONLY</STRONG>
     */
	@Unique
    private static final double WILDERWILD$OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16;

	@Unique
	private boolean wilderWild$isPlacingBelow;

	@Unique
	private boolean wilderWild$canPlaceOsseousSculk;

    @Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean wilderWild$newWorldgenCharge(LevelAccessor levelAccessor, BlockPos blockPos, SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread) {
        return this.wilderWild$canPlaceGrowth(levelAccessor, blockPos, sculkChargeHandler.isWorldGeneration());
    }

    @Inject(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void wilderWild$newSculkSpread(SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread, CallbackInfoReturnable<Integer> info, int chargeAmount, BlockPos chargePos, boolean bl, int growthSpawnCost, BlockPos aboveChargePos) {
        BlockState growthState = null;
        boolean isWorldGen = sculkChargeHandler.isWorldGeneration();
        boolean canReturn = false;
		BlockPos newChargePos = new BlockPos(aboveChargePos.getX(), aboveChargePos.getY(), aboveChargePos.getZ());

        BlockState stateDown = level.getBlockState(chargePos.below());
        Block blockDown = stateDown.getBlock();
        if (this.wilderWild$isPlacingBelow && (stateDown.isAir() || blockDown == Blocks.WATER || blockDown == Blocks.LAVA || blockDown == Blocks.SCULK_VEIN)) {
            if (this.wilderWild$canPlaceOsseousSculk) {
                int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, chargePos.below(), WILDERWILD$RANDOMNESS, false, false) * WILDERWILD$HEIGHT_MULTIPLIER, 2, WILDERWILD$MAX_HEIGHT);
                canReturn = true;
                growthState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).setValue(OsseousSculkBlock.UPSIDEDOWN, true);
				newChargePos = chargePos.below();
			} else if (level.getBlockState(chargePos.below().below()).getBlock() == Blocks.AIR && level.getBlockState(chargePos.below().below().below()).getBlock() == Blocks.AIR) {
				canReturn = true;
				growthState = RegisterBlocks.HANGING_TENDRIL.defaultBlockState();
				if (isWorldGen && AdvancedMath.random().nextDouble() > 0.6) {
					growthSpawnCost = 0;
				}
				newChargePos = chargePos.below();
			}
        }

        if (isWorldGen && growthState != null && growthState.is(RegisterBlocks.OSSEOUS_SCULK)) {
            growthSpawnCost = -2;
        }

        if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_STAIRS.defaultBlockState();
        } else if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_SLAB.defaultBlockState();
        } else if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_WALL.defaultBlockState();
        }

        if (canReturn && growthState != null) {
            level.setBlock(newChargePos, growthState, 3);

            if (isWorldGen && level.getBlockState(newChargePos).getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                int growthAmount = Math.max(0, growthState.getValue(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(2));
                for (int a = 0; a < growthAmount; a++) {
                    OsseousSculkBlock.worldGenSpread(newChargePos, level, random);
                }
            }
            info.setReturnValue(Math.max(0, chargeAmount - growthSpawnCost));
            level.playSound(null, newChargePos, growthState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        } else {
			if (this.wilderWild$isPlacingBelow) {
				info.setReturnValue(chargeAmount);
			}
		}
    }

    @Inject(method = "getRandomGrowthState", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void wilderWild$getRandomGrowthState(LevelAccessor level, BlockPos pos, RandomSource random, boolean randomize, CallbackInfoReturnable<BlockState> info, BlockState blockState) {
        if (this.wilderWild$canPlaceOsseousSculk && !blockState.is(Blocks.SCULK_SHRIEKER)) {
            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.samplePositive(EasyNoiseSampler.perlinXoro, pos, WILDERWILD$RANDOMNESS, false, false) * WILDERWILD$HEIGHT_MULTIPLIER, 2, WILDERWILD$MAX_HEIGHT);
            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
            info.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !level.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState);
        }
    }

	@Unique
    private static boolean wilderWild$ancientCityOrPillarNearby(LevelAccessor level, BlockPos pos) {
        int i = 0;
        Iterator<BlockPos> var4 = BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2)).iterator();
        do {
            if (!var4.hasNext()) {
                return false;
            }
            BlockPos blockPos = var4.next();
            BlockState blockState2 = level.getBlockState(blockPos);
            boolean osseousIsPresent = blockState2.is(RegisterBlocks.OSSEOUS_SCULK);
            if (blockState2.is(WilderBlockTags.ANCIENT_CITY_BLOCKS) || (osseousIsPresent && (blockPos.getX() != pos.getX() && blockPos.getZ() != pos.getZ()))) {
                if (osseousIsPresent) {
                    ++i;
                }
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
        return level.getRandom().nextInt(0, 6) == 3;
    }

	@Unique
    private boolean wilderWild$canPlaceGrowth(LevelAccessor level, BlockPos pos, boolean isWorldGen) {
		BlockState blockState1 = level.getBlockState(pos.below());
		this.wilderWild$canPlaceOsseousSculk = wilderWild$canPlaceOsseousSculk(pos, isWorldGen, level);
		if ((blockState1.isAir() || (blockState1.is(Blocks.WATER) && blockState1.getFluidState().is(Fluids.WATER)))) {
			if (isWorldGen) {
				int nearbyGrowths = 0;

				for (BlockPos blockPos2 : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 2, 4))) {
					BlockState blockState2 = level.getBlockState(blockPos2);
					if (blockState2.is(Blocks.SCULK_SENSOR) || blockState2.is(Blocks.SCULK_SHRIEKER)) {
						++nearbyGrowths;
					}

					if (nearbyGrowths > 2) {
						this.wilderWild$isPlacingBelow = false;
						this.wilderWild$canPlaceOsseousSculk = false;
						return false;
					}
				}

				this.wilderWild$isPlacingBelow = true;
				return true;
			} else if (this.wilderWild$canPlaceOsseousSculk) {
				this.wilderWild$isPlacingBelow = true;
				return true;
			}
			this.wilderWild$isPlacingBelow = false;
		}
		return canPlaceGrowth(level, pos);
    }

}
