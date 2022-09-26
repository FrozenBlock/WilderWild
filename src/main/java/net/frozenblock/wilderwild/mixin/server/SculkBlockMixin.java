package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.WilderWild;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(value = SculkBlock.class, priority = 69420)
public class SculkBlockMixin {

    @Shadow
    private static boolean canPlaceGrowth(LevelAccessor level, BlockPos pos) {
        return false;
    }

    @Shadow
    private BlockState getRandomGrowthState(LevelAccessor level, BlockPos pos, RandomSource random, boolean randomize) {
        return null;
    }

    private static final int HEIGHT_MULTIPLIER = 20; //The higher, the less short pillars you'll see.
    private static final int MAX_HEIGHT = 15; //The rarest and absolute tallest height of pillars
    private static final double RANDOMNESS = 0.9; //The higher, the more random. The lower, the more gradual the heights change.

    private static final double OSSEOUS_SCULK_AREA_SIZE = 0.09; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
    private static final double OSSEOUS_SCULK_THRESHOLD = 0.15; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.
    private static final double OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow. (CEILINGS IN WORLDGEN ONLY)

    @Inject(at = @At("HEAD"), method = "attemptUseCharge")
    public void setSeed(SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread, CallbackInfoReturnable<Integer> info) {
        if (level.getServer() != null) {
            if (level.getServer().overworld().getSeed() != EasyNoiseSampler.seed) {
                EasyNoiseSampler.setSeed(level.getServer().overworld().getSeed());
            }
        }
    }

    @Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean newWorldgenCharge(LevelAccessor levelAccessor, BlockPos blockPos, SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos pos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread) {
        return canPlaceGrowth(levelAccessor, blockPos, sculkChargeHandler.isWorldGeneration());
    }

    @Inject(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void newSculkSpread(SculkSpreader.ChargeCursor charge, LevelAccessor level, BlockPos catalystPos, RandomSource random, SculkSpreader sculkChargeHandler, boolean spread, CallbackInfoReturnable<Integer> cir, int chargeAmount, BlockPos chargePos, boolean bl, int growthSpawnCost, BlockPos aboveChargePos) {
        BlockState growthState = null;
        boolean isWorldGen = sculkChargeHandler.isWorldGeneration();
        boolean canReturn = false;

        BlockState stateDown = level.getBlockState(chargePos.below());
        Block blockDown = stateDown.getBlock();
        if (stateDown.isAir() || blockDown == Blocks.WATER || blockDown == Blocks.LAVA || blockDown == Blocks.SCULK_VEIN) {
            if (canPlaceOsseousSculk(chargePos, isWorldGen, level)) {
                int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, chargePos.below(), RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
                canReturn = true;
                growthState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).setValue(OsseousSculkBlock.UPSIDEDOWN, true);
            } else {
                if (level.getBlockState(chargePos.below().below()).getBlock() == Blocks.AIR && level.getBlockState(chargePos.below().below().below()).getBlock() == Blocks.AIR) {
                    canReturn = true;
                    growthState = RegisterBlocks.HANGING_TENDRIL.defaultBlockState();
                    WilderWild.log("Chose Hanging Tendril", WilderWild.DEV_LOGGING);
                    if (isWorldGen && Math.random() > 0.6) {
                        growthSpawnCost = 0;
                    }
                }
            }
            aboveChargePos = chargePos.below();
        }

        if (isWorldGen && growthState != null && growthState.is(RegisterBlocks.OSSEOUS_SCULK)) {
            growthSpawnCost = -2;
        }

        if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_STAIRS.defaultBlockState();
        }
        if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_SLAB.defaultBlockState();
        }
        if (level.getBlockState(chargePos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) || level.getBlockState(chargePos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
            canReturn = true;
            growthState = RegisterBlocks.SCULK_WALL.defaultBlockState();
        }

        if (canReturn && growthState != null) {
            level.setBlock(aboveChargePos, growthState, 3);

            if (isWorldGen && level.getBlockState(aboveChargePos).getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                int growthAmount = Math.max(0, growthState.getValue(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(2));
                for (int a = 0; a < growthAmount; a++) {
                    OsseousSculkBlock.worldGenSpread(aboveChargePos, level, random);
                }
            }
            cir.setReturnValue(Math.max(0, chargeAmount - growthSpawnCost));
            level.playSound(null, aboveChargePos, growthState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Inject(method = "getRandomGrowthState", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private void getRandomGrowthState(LevelAccessor level, BlockPos pos, RandomSource random, boolean randomize, CallbackInfoReturnable<BlockState> cir, BlockState blockState) {
        if (canPlaceOsseousSculk(pos, randomize, level) && !blockState.is(Blocks.SCULK_SHRIEKER)) {
            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.samplePositive(EasyNoiseSampler.perlinXoro, pos, RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
            cir.setReturnValue(blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !level.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState);
        }
    }

    private static boolean ancientCityOrPillarNearby(LevelAccessor level, BlockPos pos) {
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
                if (!osseousIsPresent) {
                    WilderWild.log("Ancient City Detected While Growing Osseous Sculk @ " + blockPos, WilderWild.DEV_LOGGING);
                }
                return true;
            }
        } while (true);
    }

    private static boolean canPlaceOsseousSculk(BlockPos pos, boolean worldGen, LevelAccessor level) {
        if (worldGen) {
            if (!ancientCityOrPillarNearby(level, pos)) {
                return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_WORLD_GEN_THRESHOLD;
            }
            return false;
        }
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_THRESHOLD;
    }

    private static boolean canPlaceGrowth(LevelAccessor level, BlockPos pos, boolean isWorldGen) {
        BlockState blockState = level.getBlockState(pos.above());
        BlockState blockState1 = level.getBlockState(pos.below());
        if ((isWorldGen || canPlaceOsseousSculk(pos, isWorldGen, level)) && (blockState1.isAir() || (blockState1.is(Blocks.WATER) && blockState1.getFluidState().is(Fluids.WATER)))) {
            int nearbyGrowths = 0;

            for (BlockPos blockPos2 : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 2, 4))) {
                BlockState blockState2 = level.getBlockState(blockPos2);
                if (blockState2.is(Blocks.SCULK_SENSOR) || blockState2.is(Blocks.SCULK_SHRIEKER)) {
                    ++nearbyGrowths;
                }

                if (nearbyGrowths > 2) {
                    return false;
                }
            }

            return true;

        } else {
            return canPlaceGrowth(level, pos);
        }
    }
}
