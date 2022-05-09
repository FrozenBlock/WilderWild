package net.frozenblock.wilderwild.block;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;

public class HollowedLogBlock extends PillarBlock implements Waterloggable {
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Y_SHAPE;
    protected static final VoxelShape Z_SHAPE;
    protected static final VoxelShape RAYCAST_SHAPE;

    public HollowedLogBlock(Settings settings) {
        super(settings);
        this.setDefaultState( this.getDefaultState().with(WATERLOGGED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(AXIS)) {
            default -> X_SHAPE;
            case Y -> Y_SHAPE;
            case Z -> Z_SHAPE;
        };
    }

    public VoxelShape getRaycastShape(BlockState state, BlockView world, BlockPos pos) {
        return RAYCAST_SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState blockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean isTranslucent(BlockState state, BlockView world, BlockPos pos) {
        return !(Boolean)state.get(WATERLOGGED);
    }

    static {
        RAYCAST_SHAPE = VoxelShapes.fullCube();
        X_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0 , 16, 16, 3), Block.createCuboidShape(0, 13, 0, 16 ,16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
        Y_SHAPE = VoxelShapes.union(Block.createCuboidShape(0, 0, 0 , 16, 16, 3), Block.createCuboidShape(0, 0, 0, 3 ,16, 16), Block.createCuboidShape(0, 0, 13, 16, 16, 16), Block.createCuboidShape(13, 0, 0, 16, 16, 16));
        Z_SHAPE = VoxelShapes.union(Block.createCuboidShape(13, 0, 0 , 16, 16, 16), Block.createCuboidShape(0, 0, 0, 3 ,16, 16), Block.createCuboidShape(0, 13, 0, 16, 16, 16), Block.createCuboidShape(0, 0, 0, 16, 3, 16));
        WATERLOGGED = Properties.WATERLOGGED;
    }

}
