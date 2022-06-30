package net.frozenblock.wilderwild.mixin;

import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.OsseousSculkBlock;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WildBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.Fluids;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {

    @Shadow
    private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.getMaxDistance();
        float f = MathHelper.square((float) Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float) i);
        int j = MathHelper.square(24 - i);
        float g = Math.min(1.0F, f / (float) j);
        return Math.max(1, (int) ((float) charge * g * 0.5F));
    }

    private static final int HEIGHT_MULTIPLIER = 20; //The higher, the less short pillars you'll see.
    private static final int MAX_HEIGHT = 15; //The rarest and absolute tallest height of pillars
    private static final double RANDOMNESS = 0.9; //The higher, the more random. The lower, the more gradual the heights change.

    private static final double OSSEOUS_SCULK_AREA_SIZE = 0.09; //The smaller, the larger the area pillars can grow, but the larger the gaps between them.
    private static final double OSSEOUS_SCULK_THRESHOLD = 0.15; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow.
    private static final double OSSEOUS_SCULK_WORLD_GEN_THRESHOLD = 0.16; //The higher, the harder it is for pillars to appear. If set to 1 or higher, they'll never grow. (CEILINGS IN WORLDGEN ONLY)

    @Inject(at = @At("HEAD"), method = "spread", cancellable = true)
    public void spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock, CallbackInfoReturnable<Integer> info) {
        int i = cursor.getCharge();
        boolean isWorldGen = spreadManager.isWorldGen();
        if (world.getServer() != null) {
            if (world.getServer().getOverworld().getSeed() != EasyNoiseSampler.seed) {
                EasyNoiseSampler.setSeed(world.getServer().getOverworld().getSeed());
            }
        }
        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl && shouldNotDecay(world, blockPos, isWorldGen)) {
                int j = spreadManager.getExtraBlockChance();
                if (random.nextInt(j) < i) {
                    BlockPos blockPos2 = blockPos.up();
                    BlockState blockState = this.getExtraBlockState(world, blockPos2, random, spreadManager.isWorldGen());

                    BlockState stateDown = world.getBlockState(blockPos.down());
                    Block blockDown = stateDown.getBlock();
                    if ((stateDown.isAir() || blockDown == Blocks.WATER || blockDown == Blocks.LAVA || blockDown == Blocks.SCULK_VEIN)) {
                        if (canPlaceOsseousSculk(blockPos, isWorldGen, world)) {
                            int pillarHeight = (int) MathHelper.clamp(EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, blockPos.down(), RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
                            blockState = RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).with(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1).with(OsseousSculkBlock.UPSIDEDOWN, true);
                        } else {
                            blockState = RegisterBlocks.HANGING_TENDRIL.getDefaultState();
                            WilderWild.log("Chose Hanging Tendril", WilderWild.DEV_LOGGING);
                            if (isWorldGen && Math.random() > 0.6) {
                                j = 0;
                            }
                        }
                        blockPos2 = blockPos.down();
                    }

                    if (isWorldGen && blockState.isOf(RegisterBlocks.OSSEOUS_SCULK)) {
                        j = -2;
                    }

                    world.setBlockState(blockPos2, blockState, 3);

                    if (isWorldGen && world.getBlockState(blockPos2).getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                        int amount = Math.max(0, blockState.get(OsseousSculkBlock.HEIGHT_LEFT) - random.nextInt(1));
                        for (int a = 0; a < amount; a++) {
                            OsseousSculkBlock.worldGenSpread(blockPos2, world, random);
                        }
                    }

                    world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                info.setReturnValue(Math.max(0, i - j));
                info.cancel();
            } else {
                info.setReturnValue(random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i)));
                info.cancel();
            }
        } else {
            info.setReturnValue(i);
            info.cancel();
        }
    }

    private static boolean ancientCityOrPillarNearby(WorldAccess world, BlockPos pos) {
        int i = 0;
        Iterator<BlockPos> var4 = BlockPos.iterate(pos.add(-2, -2, -2), pos.add(2, 2, 2)).iterator();
        do {
            if (!var4.hasNext()) {
                return false;
            }
            BlockPos blockPos = var4.next();
            BlockState blockState2 = world.getBlockState(blockPos);
            boolean osseousIsPresent = blockState2.isOf(RegisterBlocks.OSSEOUS_SCULK);
            if (blockState2.isIn(WildBlockTags.ANCIENT_CITY_BLOCKS) || (osseousIsPresent && (blockPos.getX() != pos.getX() && blockPos.getZ() != pos.getZ()))) {
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

    private static boolean canPlaceOsseousSculk(BlockPos pos, boolean worldGen, WorldAccess world) {
        if (worldGen) {
            if (!ancientCityOrPillarNearby(world, pos)) {
                return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_WORLD_GEN_THRESHOLD;
            }
            return false;
        }
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinXoro, pos, OSSEOUS_SCULK_AREA_SIZE, true, true) > OSSEOUS_SCULK_THRESHOLD;
    }

    private BlockState getExtraBlockState(WorldAccess world, BlockPos pos, Random random, boolean allowShrieker) {
        BlockState blockState = Blocks.SCULK_SENSOR.getDefaultState();
        boolean decided = false;
        if (random.nextInt(11) == 0) {
            blockState = Blocks.SCULK_SHRIEKER.getDefaultState().with(SculkShriekerBlock.CAN_SUMMON, allowShrieker);
            decided = true;
        }
        if (canPlaceOsseousSculk(pos, allowShrieker, world) && blockState.isOf(Blocks.SCULK_SENSOR)) {
            int pillarHeight = (int) MathHelper.clamp(EasyNoiseSampler.samplePositive(EasyNoiseSampler.perlinXoro, pos, RANDOMNESS, false, false) * HEIGHT_MULTIPLIER, 2, MAX_HEIGHT);
            blockState = RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(OsseousSculkBlock.HEIGHT_LEFT, pillarHeight).with(OsseousSculkBlock.TOTAL_HEIGHT, pillarHeight + 1);
            decided = true;
        }
        return blockState.contains(Properties.WATERLOGGED) && !world.getFluidState(pos).isEmpty() ? blockState.with(Properties.WATERLOGGED, true) : blockState;
    }


    private static boolean shouldNotDecay(WorldAccess world, BlockPos pos, boolean isWorldGen) {
        BlockState blockState = world.getBlockState(pos.up());
        BlockState blockState1 = world.getBlockState(pos.down());
        if (((blockState.isAir()) || (blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER))) || ((isWorldGen || canPlaceOsseousSculk(pos, isWorldGen, world)) && ((blockState1.isAir()) || (blockState1.isOf(Blocks.WATER) && blockState1.getFluidState().isOf(Fluids.WATER))))) {
            int i = 0;
            Iterator<BlockPos> var4 = BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4)).iterator();

            do {
                if (!var4.hasNext()) {
                    return true;
                }

                BlockPos blockPos = var4.next();
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER)) {
                    ++i;
                }
            } while (i <= 2);

        }
        return false;
    }
}
