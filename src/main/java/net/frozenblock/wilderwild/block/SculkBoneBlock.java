package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.noise.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.NewProperties;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;

public class SculkBoneBlock extends PillarBlock implements SculkSpreadable {

    public SculkBoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HEIGHT_LEFT, 4).with(AXIS, Direction.Axis.Y));
    }

    public static final IntProperty HEIGHT_LEFT = NewProperties.PILLAR_HEIGHT_LEFT;

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(1) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl) {
                Direction direction = world.getBlockState(blockPos).get(AXIS).getType().random(EasyNoiseSampler.simpleRandom);
                if (direction==Direction.DOWN) { direction=Direction.UP; }
                int pillarHeight = world.getBlockState(blockPos).get(SculkBoneBlock.HEIGHT_LEFT);
                BlockPos topPos = getTop(world, blockPos, pillarHeight, direction);
                if (topPos != null) {
                    pillarHeight = world.getBlockState(topPos).get(SculkBoneBlock.HEIGHT_LEFT);
                    direction = world.getBlockState(topPos).get(AXIS).getType().random(EasyNoiseSampler.simpleRandom);
                    if (direction==Direction.DOWN) { direction=Direction.UP; }
                    if (world.getBlockState(topPos.offset(direction)).isAir() || world.getBlockState(topPos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {
                        BlockState blockState = RegisterBlocks.SCULK_BONE.getDefaultState().with(SculkBoneBlock.HEIGHT_LEFT, pillarHeight-1);
                        if (EasyNoiseSampler.atomicRandom.nextInt(26)==0) {
                            blockState = RegisterBlocks.SCULK_BONE.getDefaultState().with(SculkBoneBlock.HEIGHT_LEFT, pillarHeight-1).with(AXIS, Direction.Axis.pickRandomAxis(EasyNoiseSampler.simpleRandom));
                        }
                        if (pillarHeight==1) {
                            blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState();
                            if (random.nextInt(11) == 0) {
                                blockState = Blocks.SCULK_CATALYST.getDefaultState();
                            }
                        }
                        world.setBlockState(topPos.offset(direction), blockState, 3);
                        world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return Math.max(0, i - 1);
                    }
                }
            }
        } return i;
    }

    public static BlockPos getTop(WorldAccess world, BlockPos pos, int max, Direction direction) {
        for (int i=0; i<max; i++) {
            Block block = world.getBlockState(pos).getBlock();
            if (block!=RegisterBlocks.SCULK_BONE) { return null; }
            if (world.getBlockState(pos.offset(direction)).isAir() || world.getBlockState(pos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {return pos;}
            pos=pos.offset(direction);
        } return null;
    }

    public int getDecay(int oldDecay) {
        return 1;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(HEIGHT_LEFT).add(Properties.AXIS); }

}
