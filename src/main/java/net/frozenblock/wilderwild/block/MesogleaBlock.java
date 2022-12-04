package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public MesogleaBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (state.getValue(WATERLOGGED)) {
            if (entity instanceof ItemEntity item) {
                item.makeStuckInBlock(state, new Vec3(0.999D, 0.999D, 0.999D));
                item.setDeltaMovement(item.getDeltaMovement().add(0, 0.025, 0));
            } //else if (!entity.getType().is(WilderEntityTags.CAN_SWIM_IN_MESOGLEA)) {
            //entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.9, 0.9, 0.9));
            //}
            if (entity instanceof Boat boat) {
                Vec3 deltaMove = boat.getDeltaMovement();
                if (boat.isUnderWater()) {
                    boat.setDeltaMovement(deltaMove.x, Math.min(0.175, deltaMove.y + 0.05), deltaMove.z);
                } else if (deltaMove.y < 0) {
                    boat.setDeltaMovement(deltaMove.x, deltaMove.y * 0.5, deltaMove.z);
                }
            }
        }
    }

    @Override
    public VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (collisionContext instanceof EntityCollisionContext && ((EntityCollisionContext) collisionContext).getEntity() != null) {
            return blockState.getValue(WATERLOGGED) ? Shapes.empty() : super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
        }
        return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        super.animateTick(blockState, level, blockPos, randomSource);
        if (randomSource.nextInt(0, 50) == 0 && (blockState.getValue(WATERLOGGED) || level.getFluidState(blockPos.above()).is(FluidTags.WATER)) && level.getFluidState(blockPos.below()).isEmpty() && level.getBlockState(blockPos.below()).isAir()) {
            ParticleOptions particle = blockState.is(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA) ? RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA :
                    blockState.is(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA) ? RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA :
                            blockState.is(RegisterBlocks.YELLOW_MESOGLEA) ? RegisterParticles.YELLOW_HANGING_MESOGLEA :
                                    blockState.is(RegisterBlocks.BLUE_MESOGLEA) ? RegisterParticles.BLUE_HANGING_MESOGLEA :
                                            blockState.is(RegisterBlocks.LIME_MESOGLEA) ? RegisterParticles.LIME_HANGING_MESOGLEA :
                                                    blockState.is(RegisterBlocks.PINK_MESOGLEA) ? RegisterParticles.PINK_HANGING_MESOGLEA :
                                                            RegisterParticles.RED_HANGING_MESOGLEA;
            level.addParticle(particle, blockPos.getX() + randomSource.nextDouble(), blockPos.getY(), blockPos.getZ() + randomSource.nextDouble(), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    public int getLightBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
        return blockState.getValue(WATERLOGGED) ? 2 : 5;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return super.getStateForPlacement(blockPlaceContext).setValue(WATERLOGGED, bl);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        if (blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }
        return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
    }

    @Override
    public FluidState getFluidState(BlockState blockState) {
        if (blockState.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public boolean skipRendering(BlockState blockState, BlockState blockState2, Direction direction) {
        if (blockState2.is(this)) {
            return true;
        }
        return super.skipRendering(blockState, blockState2, direction);
    }
}
