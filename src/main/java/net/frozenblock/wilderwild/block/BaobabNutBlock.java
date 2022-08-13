package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.sapling.BaobabSaplingGenerator;
import net.minecraft.block.*;
import net.minecraft.block.sapling.MangroveSaplingGenerator;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

public class BaobabNutBlock extends SaplingBlock {
    public static final IntProperty AGE;
    public static final int MAX_AGE = 2;
    private static final VoxelShape[] SHAPES;
    public static final BooleanProperty HANGING;

    public BaobabNutBlock(AbstractBlock.Settings settings) {
        super(new BaobabSaplingGenerator(), settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(STAGE, 0)).with(AGE, 0)).with(HANGING, false)));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{STAGE}).add(new Property[]{AGE}).add(new Property[]{HANGING});
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return super.canPlantOnTop(floor, world, pos) || floor.isOf(Blocks.CLAY);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        boolean bl = fluidState.getFluid() == Fluids.WATER;
        return (BlockState)((BlockState)super.getPlacementState(ctx).with(AGE, 2));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        VoxelShape voxelShape;
        if (!(Boolean)state.get(HANGING)) {
            voxelShape = SHAPES[4];
        } else {
            voxelShape = SHAPES[(Integer)state.get(AGE)];
        }

        return voxelShape.offset(vec3d.x, vec3d.y, vec3d.z);
    }

    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return isHanging(state) ? world.getBlockState(pos.up()).isOf(RegisterBlocks.BAOBAB_LEAVES) : super.canPlaceAt(state, world, pos);
    }


    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!isHanging(state)) {
            if (random.nextInt(7) == 0) {
                this.generate(world, pos, state, random);
            }

        } else {
            if (!isFullyGrown(state)) {
                world.setBlockState(pos, (BlockState)state.cycle(AGE), 2);
            }

        }
    }

    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return !isHanging(state) || !isFullyGrown(state);
    }

    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return isHanging(state) ? !isFullyGrown(state) : super.canGrow(world, random, pos, state);
    }

    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (isHanging(state) && !isFullyGrown(state)) {
            world.setBlockState(pos, (BlockState)state.cycle(AGE), 2);
        } else {
            super.grow(world, random, pos, state);
        }

    }

    private static boolean isHanging(BlockState state) {
        return (Boolean)state.get(HANGING);
    }

    private static boolean isFullyGrown(BlockState state) {
        return (Integer)state.get(AGE) == 2;
    }

    public static BlockState getDefaultHangingState() {
        return getHangingState(0);
    }

    public static BlockState getHangingState(int age) {
        return (BlockState)((BlockState) RegisterBlocks.BAOBAB_SAPLING.getDefaultState().with(HANGING, true)).with(AGE, age);
    }

    static {
        AGE = Properties.AGE_2;
        SHAPES = new VoxelShape[]{Block.createCuboidShape(7.0, 13.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(7.0, 10.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(7.0, 7.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(7.0, 3.0, 7.0, 9.0, 16.0, 9.0), Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 16.0, 9.0)};
        HANGING = Properties.HANGING;
    }
}
