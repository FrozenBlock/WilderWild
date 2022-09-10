package net.frozenblock.wilderwild.mixin.server;

import net.frozenblock.lib.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
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

import java.util.Iterator;

@Mixin(value = SculkBlock.class, priority = 69420)
public class SculkBlockMixin {

    @Shadow
    private static boolean canPlaceGrowth(LevelAccessor world, BlockPos pos) {
        return false;
    }

    @Shadow
    private BlockState getRandomGrowthState(LevelAccessor world, BlockPos pos, RandomSource random, boolean randomize) {
        return null;
    }

    private static final int HEIGHT_MULTIPLIER = 20; //The higher, the less short pillars you'll see.
    private static final int MAX_HEIGHT = 15; //The rarest and absolute tallest height of pillars
    private static final double RANDOMNESS = 0.9; //The higher, the more random. The lower, the more gradual the heights change.

    private static final double OSSEOUS_SCULK_AREA_SIZE = 0.09; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
    private static final double OSSEOUS_SCULK_THRESHOLD = 0.15; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.
    private static final double OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow. (CEILINGS IN WORLDGEN ONLY)

    private boolean worldgen = false;
    private SculkSpreader.ChargeCursor cursor = null;


    @Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;canPlaceGrowth(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)Z"))
    private boolean newWorldgenCharge(LevelAccessor levelAccessor, BlockPos blockPos) {
        return canPlaceGrowth(levelAccessor, blockPos, this.worldgen);
    }

    @Redirect(method = "attemptUseCharge", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/SculkBlock;getRandomGrowthState(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState newGrowthState(SculkBlock instance, LevelAccessor levelAccessor, BlockPos blockPos, RandomSource randomSource, boolean bl) {
        return this.getRandomGrowthState(levelAccessor, blockPos, this.cursor.getPos(), randomSource, bl);
    }

    @Inject(at = @At("HEAD"), method = "attemptUseCharge")
    public void setValues(SculkSpreader.ChargeCursor cursor, LevelAccessor world, BlockPos catalystPos, RandomSource random, SculkSpreader sculkSpreader, boolean shouldConvertToBlock, CallbackInfoReturnable<Integer> info) {
        this.worldgen = sculkSpreader.isWorldGeneration();
        this.cursor = cursor;
        if (world.getServer() != null) {
            if (world.getServer().overworld().getSeed() != EasyNoiseSampler.seed) {
                EasyNoiseSampler.setSeed(world.getServer().overworld().getSeed());
            }
        }
    }

    @Inject(at = @At("RETURN"), method = "attemptUseCharge", cancellable = true)
    public void newSculkSpread(SculkSpreader.ChargeCursor cursor, LevelAccessor world, BlockPos catalystPos, RandomSource random, SculkSpreader sculkSpreader, boolean shouldConvertToBlock, CallbackInfoReturnable<Integer> info) {
        boolean isWorldGen = sculkSpreader.isWorldGeneration();
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(sculkSpreader.chargeDecayRate()) == 0) {
            BlockPos cursorPos = cursor.getPos();
            boolean bl = cursorPos.closerThan(catalystPos, sculkSpreader.noGrowthRadius());
            if (!bl && canPlaceGrowth(world, cursorPos, isWorldGen)) {
                int j = sculkSpreader.growthSpawnCost();
                if (random.nextInt(j) < i) {
                    boolean canReturn = false;
                    BlockPos blockPos2 = cursorPos.above();
                    BlockState blockState = this.getRandomGrowthState(world, blockPos2, cursorPos, random, sculkSpreader.isWorldGeneration());

                    BlockState stateDown = world.getBlockState(cursorPos.below());
                    Block blockDown = stateDown.getBlock();
                    if ((stateDown.isAir() || blockDown == Blocks.WATER || blockDown == Blocks.LAVA || blockDown == Blocks.SCULK_VEIN)) {
                        if (canPlaceOsseousSculk(cursorPos, isWorldGen, world)) {
                            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, cursorPos.below(), RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
                            canReturn = true;
                            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).setValue(OsseousSculkBlock.UPSIDEDOWN, true);
                        } else {
                            if (world.getBlockState(cursorPos.below().below()).getBlock() == Blocks.AIR && world.getBlockState(cursorPos.below().below().below()).getBlock() == Blocks.AIR) {
                                canReturn = true;
                                blockState = RegisterBlocks.HANGING_TENDRIL.defaultBlockState();
                                WilderWild.log("Chose Hanging Tendril", WilderWild.DEV_LOGGING);
                                if (isWorldGen && Math.random() > 0.6) {
                                    j = 0;
                                }
                            } else {
                                blockState = stateDown;
                            }
                        }
                        blockPos2 = cursorPos.below();
                    }

                    if (isWorldGen && blockState.is(RegisterBlocks.OSSEOUS_SCULK)) {
                        j = -2;
                    }

                    if (world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE_WORLDGEN) || world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_STAIR_REPLACEABLE)) {
                        canReturn = true;
                        blockState = RegisterBlocks.SCULK_STAIRS.defaultBlockState();
                    }
                    if (world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE_WORLDGEN) || world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_SLAB_REPLACEABLE)) {
                        canReturn = true;
                        blockState = RegisterBlocks.SCULK_SLAB.defaultBlockState();
                    }
                    if (world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE_WORLDGEN) ||  world.getBlockState(cursorPos).is(WilderBlockTags.SCULK_WALL_REPLACEABLE)) {
                        canReturn = true;
                        blockState = RegisterBlocks.SCULK_WALL.defaultBlockState();
                    }

                    if (canReturn) {
                        world.setBlock(blockPos2, blockState, 3);

                        if (isWorldGen && world.getBlockState(blockPos2).getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                            int amount = Math.max(0, blockState.getValue(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(2));
                            for (int a = 0; a < amount; a++) {
                                OsseousSculkBlock.worldGenSpread(blockPos2, world, random);
                            }
                        }

                        info.setReturnValue(Math.max(0, i - j));
                    }
                }
            }
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

    private BlockState getRandomGrowthState(LevelAccessor world, BlockPos pos, BlockPos cursorPos, RandomSource randomSource, boolean allowShrieker) {
        BlockState blockState = Blocks.SCULK_SENSOR.defaultBlockState();
        if (canPlaceOsseousSculk(pos, allowShrieker, world) && blockState.is(Blocks.SCULK_SENSOR)) {
            int pillarHeight = (int) Mth.clamp(EasyNoiseSampler.samplePositive(EasyNoiseSampler.perlinXoro, pos, RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
            blockState = RegisterBlocks.OSSEOUS_SCULK.defaultBlockState().setValue(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).setValue(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
            return blockState.hasProperty(BlockStateProperties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.setValue(BlockStateProperties.WATERLOGGED, true) : blockState;
        }
        return getRandomGrowthState(world, pos, randomSource, allowShrieker);
    }


    private static boolean canPlaceGrowth(LevelAccessor world, BlockPos pos, boolean isWorldGen) {
        BlockState blockState = world.getBlockState(pos.above());
        BlockState blockState1 = world.getBlockState(pos.below());
        if ((isWorldGen || canPlaceOsseousSculk(pos, isWorldGen, world)) && ((blockState1.isAir()) || (blockState1.is(Blocks.WATER) && blockState1.getFluidState().is(Fluids.WATER)))) {
            int i = 0;

            for (BlockPos blockPos2 : BlockPos.betweenClosed(pos.offset(-4, 0, -4), pos.offset(4, 2, 4))) {
                BlockState blockState2 = world.getBlockState(blockPos2);
                if (blockState2.is(Blocks.SCULK_SENSOR) || blockState2.is(Blocks.SCULK_SHRIEKER)) {
                    ++i;
                }

                if (i > 2) {
                    return false;
                }
            }

            return true;

        }
        return canPlaceGrowth(world, pos);
    }
}
