package net.frozenblock.wilderwild.block;

import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class HollowedLogBlock extends PillarBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape north = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 3.0D);
    protected static final VoxelShape south = Block.createCuboidShape(0.0D, 0.0D, 13.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape east = Block.createCuboidShape(13.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape west = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 3.0D, 16.0D, 16.0D);
    protected static final VoxelShape up = Block.createCuboidShape(0.0D, 13.0D, 0.0D, 16.0D, 16.0D, 16.0D);
    protected static final VoxelShape down = Block.createCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape HITBOX_N;
    protected static final VoxelShape HITBOX_S;
    protected static final VoxelShape HITBOX_W;
    protected static final VoxelShape HITBOX_E;
    protected static final VoxelShape HITBOX_U;
    protected static final VoxelShape HITBOX_D;
    public static final IntProperty LEVEL;

    public static final DirectionProperty FACING;

    protected static final VoxelShape RAYCAST_SHAPE;

    // CLASS's BASE METHODS
    public HollowedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(FACING, Direction.UP).with(LEVEL, 0));
    }
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            default -> VoxelShapes.union(east, west, up, down);
            case WEST, EAST -> VoxelShapes.union(north, south, up, down);
            case UP, DOWN -> VoxelShapes.union(north, south, west, east);
        };
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        Direction.Axis axis = state.get(AXIS);
        world.setBlockState(pos, state.with(FACING, axisToDir(axis)));
        updateWaterCompatibility(world, state, pos);
        world.createAndScheduleBlockTick(pos, this, 1);
        super.onPlaced(world, pos, state, placer, itemStack);
    }

    private Direction axisToDir(Direction.Axis axis) {
        return switch (axis) {
            case X -> Direction.NORTH;
            case Y -> Direction.DOWN;
            default -> Direction.WEST;
        };
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        world.createAndScheduleBlockTick(pos, this, 1);
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }


    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        updateWaterCompatibility(world, world.getBlockState(pos), pos);
    }

    private void updateWaterCompatibility(WorldAccess world, BlockState state, BlockPos pos) {
        Direction dir = state.get(FACING);
        if(state.get(LEVEL) != 9) { // Check for not persistent
            Block getBlock = world.getBlockState(pos.offset(dir.getOpposite())).getBlock();
            Fluid getFluid = world.getFluidState(pos.offset(dir.getOpposite())).getFluid();
            if(dir != Direction.UP) { // Up can not transport water
                if(getBlock instanceof HollowedLogBlock) {
                    if(world.getBlockState(pos.offset(dir.getOpposite())).get(FACING) == dir
                            && world.getBlockState(pos.offset(dir.getOpposite())).get(WATERLOGGED)) {
                        if(world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) < 8) {
                            int waterlevel = world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) + 1;
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                        } else if(world.getBlockState(pos.offset(dir.getOpposite())).get(LEVEL) == 9) {
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), 1);
                        }
                    } else {
                        tryDrainFluid(world, pos, state);
                    }
                } else if(getFluid == Fluids.WATER || getFluid == Fluids.FLOWING_WATER) {
                        int waterlevel;
                        if (getBlock == Blocks.WATER) {
                            if(world.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) < 8) {
                                waterlevel = world.getBlockState(pos.offset(dir.getOpposite())).get(FluidBlock.LEVEL) + 1;
                                tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), waterlevel);
                            }
                        } else {
                            tryFillWithFluid(world, pos, state, Fluids.WATER.getDefaultState(), 1);
                        }
                } else {
                    tryDrainFluid(world, pos, state);
                }
            }
        }
    }

    private boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState, int level) {
        if (!(Boolean)state.get(Properties.WATERLOGGED) && fluidState.getFluid() == Fluids.WATER) {
            if (!world.isClient()) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, true).with(LEVEL, level), 3);
                world.createAndScheduleFluidTick(pos, fluidState.getFluid(), fluidState.getFluid().getTickRate(world));
            }

            return true;
        } else {
            return false;
        }
    }
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState defaultState) {
        return tryFillWithFluid(world, pos, state, defaultState, 9);
    }
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state, int level) {
        if (state.get(Properties.WATERLOGGED)) {
            int getLevel = world.getBlockState(pos).get(LEVEL);
            if(getLevel != 9) {
                world.setBlockState(pos, state.with(Properties.WATERLOGGED, false).with(LEVEL, level), 3);
                if (!state.canPlaceAt(world, pos)) {
                    world.breakBlock(pos, true);
                }
            }

            if(getLevel != 9) {
                return ItemStack.EMPTY;
            } else {
                return new ItemStack(Items.WATER_BUCKET);
            }
        } else {
            return ItemStack.EMPTY;
        }
    }
    public ItemStack tryDrainFluid(WorldAccess world, BlockPos pos, BlockState state) {
        return tryDrainFluid(world, pos, state, 0);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return Objects.requireNonNull(super.getPlacementState(ctx)).with(FACING, ctx.getSide());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED, FACING, LEVEL);
    }

    // RENDERING
    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }
    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !(Boolean)state.get(WATERLOGGED);
    }

    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    static {
        RAYCAST_SHAPE = VoxelShapes.fullCube();

        HITBOX_N = VoxelShapes.union(east, west, up, down, south);
        HITBOX_S = VoxelShapes.union(east, west, up, down, north);
        HITBOX_W = VoxelShapes.union(north, south, up, down, east);
        HITBOX_E = VoxelShapes.union(north, south, up, down, west);
        HITBOX_U = VoxelShapes.union(east, west, north, south, down);
        HITBOX_D = VoxelShapes.union(east, west, south, north, up);

        WATERLOGGED = Properties.WATERLOGGED;
        FACING = Properties.FACING;
        LEVEL = IntProperty.of("level", 0, 9);
    }

}