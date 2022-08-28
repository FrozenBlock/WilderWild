package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.world.gen.sapling.BaobabSaplingGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

// DEEZ NUTS HAHAHHA GOTTEM
public class BaobabNutBlock extends SaplingBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
    public static final int MAX_AGE = 2;
    private static final VoxelShape[] SHAPES = new VoxelShape[]{
            Shapes.or(Block.box(7.0, 13.0, 7.0, 9.0, 16.0, 9.0), Block.box(5.0, 6.0, 5.0, 11.0, 13.0, 11.0)),
            Shapes.or(Block.box(7.0, 12.0, 7.0, 9.0, 16.0, 9.0), Block.box(4.0, 3.0, 4.0, 12.0, 12.0, 12.0)),
            Shapes.or(Block.box(7.0, 10.0, 7.0, 9.0, 16.0, 9.0), Block.box(4.0, 0.0, 4.0, 12.0, 10.0, 12.0)),
            Block.box(7.0, 3.0, 7.0, 9.0, 16.0, 9.0), Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D)
    };
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;

    public BaobabNutBlock(BlockBehaviour.Properties settings) {
        super(new BaobabSaplingGenerator(), settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(AGE, 0).setValue(HANGING, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
        builder.add(STAGE, AGE, HANGING);
    }

    protected boolean mayPlaceOn(net.minecraft.world.level.block.state.BlockState floor, BlockGetter world, BlockPos pos) {
        return super.mayPlaceOn(floor, world, pos) || floor.is(Blocks.CLAY);
    }

    @Nullable
    public net.minecraft.world.level.block.state.BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return super.getStateForPlacement(ctx).setValue(AGE, 2);
    }

    public VoxelShape getShape(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(world, pos);
        VoxelShape voxelShape;
        if (!state.getValue(HANGING)) {
            voxelShape = SHAPES[4];
        } else {
            voxelShape = SHAPES[state.getValue(AGE)];
        }

        return voxelShape.move(vec3d.x, vec3d.y, vec3d.z);
    }

    public boolean canSurvive(net.minecraft.world.level.block.state.BlockState state, LevelReader world, BlockPos pos) {
        return isHanging(state) ? world.getBlockState(pos.above()).is(RegisterBlocks.BAOBAB_LEAVES) : super.canSurvive(state, world, pos);
    }


    public void randomTick(net.minecraft.world.level.block.state.BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!isHanging(state)) {
            if (random.nextInt(7) == 0) {
                this.advanceTree(world, pos, state, random);
            }

        } else {
            if (!isFullyGrown(state)) {
                world.setBlock(pos, state.cycle(AGE), 2);
            }

        }
    }

    public boolean isValidBonemealTarget(BlockGetter world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, boolean isClient) {
        return !isHanging(state) || !isFullyGrown(state);
    }

    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        return isHanging(state) ? !isFullyGrown(state) : super.isBonemealSuccess(world, random, pos, state);
    }

    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        if (isHanging(state) && !isFullyGrown(state)) {
            world.setBlock(pos, state.cycle(AGE), 2);
        } else {
            super.performBonemeal(world, random, pos, state);
        }

    }

    private static boolean isHanging(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(HANGING);
    }

    private static boolean isFullyGrown(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(AGE) == 2;
    }

    public static net.minecraft.world.level.block.state.BlockState getDefaultHangingState() {
        return getHangingState(0);
    }

    public static net.minecraft.world.level.block.state.BlockState getHangingState(int age) {
        return RegisterBlocks.BAOBAB_NUT.defaultBlockState().setValue(HANGING, true).setValue(AGE, age);
    }
}
