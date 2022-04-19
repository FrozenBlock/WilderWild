package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.noise.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;

public class SculkBoneBlock extends PillarBlock implements SculkSpreadable {

    public SculkBoneBlock(Settings settings) { super(settings); }

    public static final int heightMultiplier = 23; //The higher, the less short pillars you'll see.
    public static final int maxHeight = 15; //The rarest and absolute tallest height of pillars
    public static final double randomness = 0.8; //The higher, the more random. The lower, the more gradual the heights change.

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(1) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl) {
                double pillarHeight = MathHelper.clamp(EasyNoiseSampler.samplePerlinSimplePositive(blockPos, randomness, true, false) * heightMultiplier,0, maxHeight);
                if (getHeight(world, blockPos) < pillarHeight && (world.getBlockState(blockPos.up()).isAir() || world.getBlockState(blockPos.up()).getBlock()==Blocks.SCULK_VEIN)) {
                    BlockState blockState = RegisterBlocks.SCULK_BONE.getDefaultState();
                    BlockPos top = getTop(world, blockPos);
                    if (top!=null) {
                        if (getHeight(world, top)+1>=pillarHeight) {
                            blockState=RegisterBlocks.SCULK_ECHOER.getDefaultState();
                        }
                        world.setBlockState(top.up(), blockState, 3);
                        world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return Math.max(0, i - 1);
                    }
                }
            }
            return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(i));
        } else {
            return i;
        }
    }

    public static BlockPos getTop(WorldAccess world, BlockPos pos) {
        for (int i=0; i<13; i++) {
            Block block = world.getBlockState(pos).getBlock();
            if (block!=RegisterBlocks.SCULK_BONE) { return null; }
            if (world.getBlockState(pos.up()).isAir() || world.getBlockState(pos.up()).getBlock() == Blocks.SCULK_VEIN) {return pos;}
            pos=pos.up();
        } return null;
    }

    public static int getHeight(WorldAccess world, BlockPos pos) {
        for (int i=0; i<13; i++) {
            pos=pos.down();
            if (world.getBlockState(pos).getBlock() == Blocks.SCULK) {return i;}
        } return 99;
    }

    public int getDecay(int oldDecay) {
        return 1;
    }

}
