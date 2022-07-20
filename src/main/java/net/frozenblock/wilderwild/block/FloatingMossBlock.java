package net.frozenblock.wilderwild.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class FloatingMossBlock extends Block {
    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16, 1.0, 16);

    public FloatingMossBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canLayAt(world, pos.below());
    }

    @Override
    public BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos
    ) {
        return !this.canSurvive(state, world, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, false);
        }
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            world.destroyBlock(pos, false);
        }

    }

    private static boolean canLayAt(BlockGetter world, BlockPos pos) {
        FluidState fluidState = world.getFluidState(pos);
        FluidState fluidState2 = world.getFluidState(pos.above());
        return fluidState.getType() == Fluids.WATER && fluidState2.getType() == Fluids.EMPTY;
    }

}
