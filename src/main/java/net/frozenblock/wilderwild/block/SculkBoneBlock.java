package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.noise.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.NewProperties;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.AbstractRandom;
import net.minecraft.world.WorldAccess;

public class SculkBoneBlock extends PillarBlock implements SculkSpreadable {

    public SculkBoneBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HEIGHT_LEFT, 0).with(AXIS, Direction.Axis.Y).with(UPSIDEDOWN, false).with(TOTAL_HEIGHT, 0));
    }

    public static Direction getDir(Direction.Axis axis, boolean UpsideDown) {
        if (axis.isHorizontal()) {
            if (axis == Direction.Axis.X) {
                if (Math.random()>0.5) { return Direction.EAST; }
                return Direction.WEST;
            }
            if (axis == Direction.Axis.Z) {
                if (Math.random()>0.5) { return Direction.NORTH; }
                return Direction.SOUTH;
            }
        } if (UpsideDown) {return Direction.DOWN; }
        return Direction.UP;
    }

    public static final IntProperty HEIGHT_LEFT = NewProperties.PILLAR_HEIGHT_LEFT;
    public static final BooleanProperty UPSIDEDOWN = NewProperties.UPSIDE_DOWN;
    public static final IntProperty TOTAL_HEIGHT = NewProperties.TOTAL_HEIGHT;

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, AbstractRandom random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(1) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl) {
                int pillarHeight = world.getBlockState(blockPos).get(SculkBoneBlock.HEIGHT_LEFT);
                BlockPos topPos = getTop(world, blockPos, pillarHeight);
                if (topPos != null) {
                    BlockState state = world.getBlockState(topPos);
                    pillarHeight = state.get(SculkBoneBlock.HEIGHT_LEFT);
                    Direction direction = getDir(state.get(AXIS),state.get(UPSIDEDOWN));
                    if (world.getBlockState(topPos.offset(direction)).isAir() || world.getBlockState(topPos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {
                        BlockState blockState = RegisterBlocks.SCULK_BONE.getDefaultState().with(SculkBoneBlock.HEIGHT_LEFT, Math.max(0,pillarHeight-1));
                        if (EasyNoiseSampler.atomicRandom.nextInt(28)==0) {
                            blockState = RegisterBlocks.SCULK_BONE.getDefaultState().with(SculkBoneBlock.HEIGHT_LEFT,  Math.max(0,pillarHeight-1)).with(AXIS, Direction.Axis.pickRandomAxis(EasyNoiseSampler.simpleRandom));
                        }
                        if (pillarHeight==1 && !state.get(UPSIDEDOWN) && state.get(TOTAL_HEIGHT)>0) {
                            if (EasyNoiseSampler.simpleRandom.nextInt(Math.max(1,state.get(TOTAL_HEIGHT)/2))<=1) {
                                blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState();
                                if (random.nextInt(11) == 0) {
                                    blockState = Blocks.SCULK_CATALYST.getDefaultState();
                                }
                            }
                        }
                        if (blockState.getBlock()==RegisterBlocks.SCULK_BONE) {
                            blockState=blockState.with(TOTAL_HEIGHT, state.get(TOTAL_HEIGHT));
                            if (state.get(UPSIDEDOWN)) { blockState=blockState.with(UPSIDEDOWN, true); }
                        }
                        world.setBlockState(topPos.offset(direction), blockState, 3);
                        world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        return Math.max(0, i - 1);
                    }
                }
            }
        } return i;
    }

    public static BlockPos getTop(WorldAccess world, BlockPos pos, int max) {
        for (int i=0; i<max; i++) {
            Block block = world.getBlockState(pos).getBlock();
            if (block!=RegisterBlocks.SCULK_BONE) { return null; }
            Direction direction = getDir(world.getBlockState(pos).get(AXIS), world.getBlockState(pos).get(UPSIDEDOWN));
            if (world.getBlockState(pos.offset(direction)).isAir() || world.getBlockState(pos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {return pos;}
            pos=pos.offset(direction);
        } return null;
    }

    public int getDecay(int oldDecay) {
        return 1;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) { builder.add(HEIGHT_LEFT).add(Properties.AXIS).add(UPSIDEDOWN).add(TOTAL_HEIGHT); }

}
