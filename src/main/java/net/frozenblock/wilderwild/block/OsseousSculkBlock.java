package net.frozenblock.wilderwild.block;

import net.frozenblock.api.mathematics.EasyNoiseSampler;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class OsseousSculkBlock extends PillarBlock implements SculkSpreadable {

    public OsseousSculkBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(HEIGHT_LEFT, 0).with(AXIS, Direction.Axis.Y).with(UPSIDEDOWN, false).with(TOTAL_HEIGHT, 0));
    }

    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (dropExperience) {
            this.dropExperienceWhenMined(world, pos, stack, ConstantIntProvider.create(3));
        }
    }

    public static Direction getDir(Direction.Axis axis, boolean UpsideDown) {
        if (axis != null) {
            if (axis.isHorizontal()) {
                if (axis == Direction.Axis.X) {
                    return Math.random() > 0.5 ? Direction.EAST : Direction.WEST;
                }
                if (axis == Direction.Axis.Z) {
                    return Math.random() > 0.5 ? Direction.NORTH : Direction.SOUTH;
                }
            }
        }
        return UpsideDown ? Direction.DOWN : Direction.UP;
    }

    public static Direction.Axis getAxis(BlockPos pos) {
        return EasyNoiseSampler.sample(EasyNoiseSampler.perlinSimple, pos, 0.7, false, false) > 0 ? Direction.Axis.X : Direction.Axis.Z;
    }

    public static void convertToSculk(WorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state.isOf(RegisterBlocks.OSSEOUS_SCULK)) {
            Direction.Axis axis = state.get(AXIS);
            Direction dir = getDir(axis, state.get(UPSIDEDOWN));
            if (world.getBlockState(pos.offset(dir)).isOf(RegisterBlocks.OSSEOUS_SCULK)) {
                BlockPos newPos = pos.offset(dir);
                for (Direction direction : DIRECTIONS) {
                    BlockState stateReplace = world.getBlockState(newPos.offset(direction));
                    BlockState stateSetTo = null;
                    if (stateReplace.isOf(Blocks.SCULK_VEIN)) {
                        stateSetTo = stateReplace.with(MultifaceGrowthBlock.getProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace.isAir()) {
                        stateSetTo = Blocks.SCULK_VEIN.getDefaultState().with(MultifaceGrowthBlock.getProperty(direction.getOpposite()), true);
                    }
                    if (stateReplace == Blocks.WATER.getDefaultState()) {
                        stateSetTo = Blocks.SCULK_VEIN.getDefaultState().with(MultifaceGrowthBlock.getProperty(direction.getOpposite()), true).with(Properties.WATERLOGGED, true);
                    }
                    if (stateSetTo != null) {
                        world.setBlockState(newPos.offset(direction), stateSetTo, 3);
                    }
                }
                world.setBlockState(pos, Blocks.SCULK.getDefaultState(), 3);
            }
        }
    }

    public static final IntProperty HEIGHT_LEFT = RegisterProperties.PILLAR_HEIGHT_LEFT;
    public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
    public static final IntProperty TOTAL_HEIGHT = RegisterProperties.TOTAL_HEIGHT;

    @Override
    public int spread(SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock) {
        if (spreadManager.isWorldGen()) {
            worldGenSpread(cursor.getPos(), world, random);
            return cursor.getCharge();
        }
        int i = cursor.getCharge();
        int j = 1;
        if (i != 0 && random.nextInt(1) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl) {
                int pillarHeight = world.getBlockState(blockPos).get(OsseousSculkBlock.HEIGHT_LEFT);
                BlockPos topPos = getTop(world, blockPos, pillarHeight);
                if (topPos != null) {
                    BlockState state = world.getBlockState(topPos);
                    pillarHeight = state.get(OsseousSculkBlock.HEIGHT_LEFT);
                    Direction direction = getDir(state.get(AXIS), state.get(UPSIDEDOWN));
                    if (world.getBlockState(topPos.offset(direction)).isAir() || world.getBlockState(topPos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {
                        BlockState blockState = RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));

                        if (pillarHeight == 1 && !state.get(UPSIDEDOWN) && state.get(TOTAL_HEIGHT) > 0) {
                            if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.get(TOTAL_HEIGHT) / 2)) <= 1) {
                                blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState();
                                if (random.nextInt(11) == 0) {
                                    blockState = Blocks.SCULK_CATALYST.getDefaultState();
                                }
                            }
                        }
                        if (pillarHeight == 1 && state.get(UPSIDEDOWN) && state.get(TOTAL_HEIGHT) > 0) {
                            if (EasyNoiseSampler.localRandom.nextInt(3) <= 1) {
                                blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState().with(SculkEchoerBlock.UPSIDEDOWN, true);
                            }
                        }
                        if (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                            blockState = blockState.with(TOTAL_HEIGHT, state.get(TOTAL_HEIGHT));
                            if (state.get(UPSIDEDOWN)) {
                                blockState = blockState.with(UPSIDEDOWN, true);
                                if (direction == Direction.DOWN && Math.random() > 0.8) {
                                    Direction ribCage = getDir(getAxis(topPos), false);
                                    if (ISITSAFE(world.getBlockState(topPos.offset(ribCage)))) {
                                        world.setBlockState(topPos.offset(ribCage), RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(AXIS, getAxis(topPos)).with(TOTAL_HEIGHT, state.get(TOTAL_HEIGHT)).with(HEIGHT_LEFT, 0), 3);
                                        if (ISITSAFE(world.getBlockState(topPos.offset(ribCage).down()))) {
                                            if (Math.random() > 0.7) {
                                                world.setBlockState(topPos.offset(ribCage).down(), RegisterBlocks.HANGING_TENDRIL.getDefaultState(), 3);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        world.setBlockState(topPos.offset(direction), blockState, 3);
                        world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                        if (spreadManager.isWorldGen() && Math.random() > 0.2) {
                            j = 0;
                        }
                        BlockPos bottom = getBottom(world, topPos.offset(direction), state.get(TOTAL_HEIGHT));
                        if (bottom != null) {
                            BlockState bottomState = world.getBlockState(bottom);
                            if (bottomState.isOf(RegisterBlocks.OSSEOUS_SCULK)) {
                                int piece = bottomState.get(HEIGHT_LEFT);
                                int total = bottomState.get(TOTAL_HEIGHT);
                                if ((total) - piece <= total / 3) {
                                    convertToSculk(world, bottom);
                                }
                            }
                        }
                        return Math.max(0, i - j);
                    }
                }
            }
        }
        return i;
    }

    public static boolean ISITSAFE(BlockState state) {
        return state.isOf(Blocks.SCULK_VEIN) || state.isAir() || state.isOf(Blocks.WATER);
    }

    public static void worldGenSpread(BlockPos blockPos, WorldAccess world, Random random) {
        if (world.getBlockState(blockPos).isOf(RegisterBlocks.OSSEOUS_SCULK)) {
            int pillarHeight = world.getBlockState(blockPos).get(HEIGHT_LEFT);
            BlockPos topPos = getTop(world, blockPos, pillarHeight);
            if (topPos != null) {
                BlockState state = world.getBlockState(topPos);
                pillarHeight = state.get(HEIGHT_LEFT);
                Direction direction = getDir(state.get(AXIS), state.get(UPSIDEDOWN));
                if (world.getBlockState(topPos.offset(direction)).isAir() || world.getBlockState(topPos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {
                    BlockState blockState = RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(HEIGHT_LEFT, Math.max(0, pillarHeight - 1));

                    if (pillarHeight == 1 && !state.get(UPSIDEDOWN) && state.get(TOTAL_HEIGHT) > 0) {
                        if (EasyNoiseSampler.localRandom.nextInt(Math.max(1, state.get(TOTAL_HEIGHT) / 2)) <= 1) {
                            blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState();
                            if (random.nextInt(11) == 0) {
                                blockState = Blocks.SCULK_CATALYST.getDefaultState();
                            }
                        }
                    }
                    if (pillarHeight == 1 && state.get(UPSIDEDOWN) && state.get(TOTAL_HEIGHT) > 0) {
                        if (EasyNoiseSampler.localRandom.nextInt(3) <= 1) {
                            blockState = RegisterBlocks.SCULK_ECHOER.getDefaultState().with(SculkEchoerBlock.UPSIDEDOWN, true);
                        }
                    }
                    if (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK) {
                        blockState = blockState.with(TOTAL_HEIGHT, state.get(TOTAL_HEIGHT));
                        if (state.get(UPSIDEDOWN)) {
                            blockState = blockState.with(UPSIDEDOWN, true);
                            if (direction == Direction.DOWN && Math.random() > 0.9) {
                                Direction ribCage = getDir(getAxis(topPos), false);
                                if (ISITSAFE(world.getBlockState(topPos.offset(ribCage)))) {
                                    world.setBlockState(topPos.offset(ribCage), RegisterBlocks.OSSEOUS_SCULK.getDefaultState().with(AXIS, getAxis(topPos)).with(TOTAL_HEIGHT, state.get(TOTAL_HEIGHT)).with(HEIGHT_LEFT, 0), 3);
                                    if (ISITSAFE(world.getBlockState(topPos.offset(ribCage).down()))) {
                                        if (Math.random() > 0.66) {
                                            world.setBlockState(topPos.offset(ribCage).down(), RegisterBlocks.HANGING_TENDRIL.getDefaultState(), 3);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    world.setBlockState(topPos.offset(direction), blockState, 3);
                    world.playSound(null, blockPos, blockState.getSoundGroup().getPlaceSound(), SoundCategory.BLOCKS, 1.0F, 1.0F);

                    if (blockState.getBlock() == Blocks.SCULK_CATALYST || blockState.getBlock() == RegisterBlocks.SCULK_ECHOER || (blockState.getBlock() == RegisterBlocks.OSSEOUS_SCULK && blockState.get(HEIGHT_LEFT) == 0)) {
                        for (int i = 0; i < 4; i++) {
                            BlockPos bottom = getBottom(world, topPos, state.get(TOTAL_HEIGHT));
                            if (bottom != null) {
                                BlockState bottomState = world.getBlockState(bottom);
                                if (bottomState.isOf(RegisterBlocks.OSSEOUS_SCULK)) {
                                    int piece = bottomState.get(HEIGHT_LEFT);
                                    int total = bottomState.get(TOTAL_HEIGHT);
                                    if ((total) - piece <= total / 3) {
                                        convertToSculk(world, bottom);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static BlockPos getTop(WorldAccess world, BlockPos pos, int max) {
        for (int i = 0; i < max; i++) {
            Block block = world.getBlockState(pos).getBlock();
            if (block != RegisterBlocks.OSSEOUS_SCULK) {
                return null;
            }
            Direction direction = getDir(world.getBlockState(pos).get(AXIS), world.getBlockState(pos).get(UPSIDEDOWN));
            if (world.getBlockState(pos.offset(direction)).isAir() || world.getBlockState(pos.offset(direction)).getBlock() == Blocks.SCULK_VEIN) {
                return pos;
            }
            pos = pos.offset(direction);
        }
        return null;
    }

    public static BlockPos getBottom(WorldAccess world, BlockPos pos, int max) {
        for (int i = 0; i < max; i++) {
            Block block = world.getBlockState(pos).getBlock();
            if (block != RegisterBlocks.OSSEOUS_SCULK) {
                return null;
            }
            Direction direction = getDir(null, world.getBlockState(pos).get(UPSIDEDOWN)).getOpposite();
            if (world.getBlockState(pos.offset(direction)).isOf(Blocks.SCULK)) {
                return pos;
            }
            pos = pos.offset(direction);
        }
        return null;
    }

    public int getDecay(int oldDecay) {
        return 1;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HEIGHT_LEFT).add(Properties.AXIS).add(UPSIDEDOWN).add(TOTAL_HEIGHT);
    }

}
