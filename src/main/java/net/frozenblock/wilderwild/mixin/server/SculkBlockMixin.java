package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

    @Shadow
    private static int getDecayPenalty(SculkSpreader spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.noGrowthRadius();
        float f = Mth.square((float) Math.sqrt(cursorPos.distSqr(catalystPos)) - (float) i);
        int j = Mth.square(24 - i);
        float g = Math.min(1.0F, f / (float) j);
        return Math.max(1, (int) ((float) charge * g * 0.5F));
    }

    @Shadow
    private static boolean canPlaceGrowth(LevelAccessor world, BlockPos pos) {
        return false;
    }

    private static final int HEIGHT_MULTIPLIER = 20; //The higher, the less short pillars you'll see.
    private static final int MAX_HEIGHT = 15; //The rarest and absolute tallest height of pillars
    private static final double RANDOMNESS = 0.9; //The higher, the more random. The lower, the more gradual the heights change.

    private static final double OSSEOUS_SCULK_AREA_SIZE = 0.09; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
    private static final double OSSEOUS_SCULK_THRESHOLD = 0.15; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.
    private static final double OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow. (CEILINGS IN WORLDGEN ONLY)

    @Inject(at = @At("HEAD"), method = "attemptUseCharge", cancellable = true)
    public void spread(SculkSpreader.ChargeCursor cursor, LevelAccessor world, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock, CallbackInfoReturnable<Integer> info) {
        int i = cursor.getCharge();
        boolean isWorldGen = spreadManager.isWorldGeneration();
        if (world.getServer() != null) {
            if (world.getServer().overworld().getSeed() != EasyNoiseSampler.seed) {
                EasyNoiseSampler.setSeed(world.getServer().overworld().getSeed());
            }
        }
        if (i != 0 && random.nextInt(spreadManager.chargeDecayRate()) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.closerThan(catalystPos, spreadManager.noGrowthRadius());
            if (!bl && canPlaceGrowth(world, blockPos, isWorldGen)) {
                int j = spreadManager.growthSpawnCost();
                if (random.nextInt(j) < i) {
                    BlockPos blockPos2 = blockPos.above();
                    BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGeneration());

                    BlockState stateDown = world.getBlockState(blockPos.below());
                    Block blockDown = stateDown.getBlock();
                    if ((stateDown.isAir() || blockDown == Blocks.WATER || blockDown == Blocks.LAVA || blockDown == Blocks.SCULK_VEIN)) {
                        if (canPlaceOsseousSculk(blockPos, isWorldGen, world)) {
                            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, blockPos.below(), RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
                            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).setValue(OsseousSculkBlock.UPSIDEDOWN, true);
                        } else {
                            if (world.getBlockState(blockPos.below().below()).getBlock() == Blocks.AIR && world.getBlockState(blockPos.below().below().below()).getBlock() == Blocks.AIR) {
                                blockState = RegisterBlocks.HANGING_TENDRIL.defaultBlockState();
                                WilderWild.log("Chose Hanging Tendril", WilderWild.DEV_LOGGING);
                                if (isWorldGen && Math.random() > 0.6) {
                                    j = 0;
                                }
                            } else {
                                blockState = stateDown;
                            }
                        }
                        blockPos2 = blockPos.below();
                    }

                    if (isWorldGen && blockState.is(RegisterBlocks.OSSEOUS_SCULK)) {
                        j = -2;
                    }

                    if (world.getBlockState(blockPos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN)) {
                        blockState = RegisterBlocks.SCULK_STAIRS.defaultBlockState();
                    }
                    if (world.getBlockState(blockPos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN)) {
                        blockState = RegisterBlocks.SCULK_SLAB.defaultBlockState();
                    }
                    if (world.getBlockState(blockPos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN)) {
                        blockState = RegisterBlocks.SCULK_WALL.defaultBlockState();
                    }

                    world.setBlock(blockPos2, blockState, 3);

                    if (isWorldGen && world.getBlockState(blockPos2).getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                        int amount = Math.max(0, blockState.getValue(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(2));
                        for (int a = 0; a < amount; a++) {
                            OsseousSculkBlock.worldGenSpread(blockPos2, world, random);
                        }
                    }

                    world.playSound(null, blockPos, blockState.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
                }

                info.setReturnValue(Math.max(0, i - j));
                info.cancel();
            } else {
                info.setReturnValue(random.nextInt(spreadManager.additionalDecayRate()) != 0 ? i : i - (bl ? 1 : getDecayPenalty(spreadManager, blockPos, catalystPos, i)));
                info.cancel();
            }
        } else {
            info.setReturnValue(i);
            info.cancel();
        }
    }

    private static boolean ancientCityOrPillarNearby(LevelAccessor world, BlockPos pos) {
        int i = 0;
        Iterator<BlockPos> var4 = BlockPos.betweenClosed(pos.offset(-2, -2, -2), pos.offset(2, 2, 2)).iterator();
        do {
            if (!var4.hasNext()) {
                return false;
            }
            BlockPos blockPos = var4.next();
            net.minecraft.world.level.block.state.BlockState blockState2 = world.getBlockState(blockPos);
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

    private static boolean canPlaceOsseousSculk(BlockPos pos, boolean worldGen, LevelAccessor world) {
        if (worldGen) {
            if (!ancientCityOrPillarNearby(world, pos)) {
                return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_WORLD_GEN_THRESHOLD;
            }
            return false;
        }
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_THRESHOLD;
    }

    private net.minecraft.world.level.block.state.BlockState getExtraBlockState(LevelAccessor world, BlockPos pos, RandomSource random, boolean allowShrieker) {
        net.minecraft.world.level.block.state.BlockState blockState = Blocks.SCULK_SENSOR.defaultBlockState();
        boolean decided = false;
        if (random.nextInt(11) == 0) {
            blockState = Blocks.SCULK_SHRIEKER.defaultBlockState().setValue(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
            decided = true;
        }
        if (canPlaceOsseousSculk(pos, allowShrieker, world) && blockState.is(Blocks.SCULK_SENSOR)) {
            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.samplePositive(EasyNoiseSampler.perlinXoro, pos, RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
            decided = true;
        }
        return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState;
    }


    private static boolean canPlaceGrowth(LevelAccessor world, BlockPos pos, boolean isWorldGen) {
        BlockState blockState = world.getBlockState(pos.above());
        BlockState blockState1 = world.getBlockState(pos.below());
        if (((isWorldGen || canPlaceOsseousSculk(pos, isWorldGen, world)) && ((blockState1.isAir()) || (blockState1.is(Blocks.WATER) && blockState1.getFluidState().is(Fluids.WATER))))) {
            int i = 0;
            Iterator<BlockPos> var4 = BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 2, 4)).iterator();

            do {
                if (!var4.hasNext()) {
                    return true;
                }

                BlockPos blockPos = var4.next();
                net.minecraft.world.level.block.state.BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.is(Blocks.SCULK_SENSOR) || blockState2.is(Blocks.SCULK_SHRIEKER)) {
                    ++i;
                }
            } while (i <= 2);

        }
        return canPlaceGrowth(world, pos);
    }
}
