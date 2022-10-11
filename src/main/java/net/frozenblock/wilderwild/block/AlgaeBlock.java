package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.Util;
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
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class AlgaeBlock extends Block implements BonemealableBlock {

    /**
     * The multiplier that is applied when an entity collides with the algae.
     */
    private static final float ENTITY_SLOWING_MULTIPLIER = 0.8F;

    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 1, 16);

    public AlgaeBlock(final BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getShape(
            final @NotNull BlockState state,
            final @NotNull BlockGetter level,
            final @NotNull BlockPos pos,
            final @NotNull CollisionContext context
    ) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(
            final @NotNull BlockState state,
            final @NotNull LevelReader level,
            final BlockPos pos
    ) {
        return canLayAt(level, pos.below());
    }

    @Override
    public BlockState updateShape(
            final @NotNull BlockState state,
            final @NotNull Direction direction,
            final @NotNull BlockState neighborState,
            final @NotNull LevelAccessor level,
            final @NotNull BlockPos pos,
            final @NotNull BlockPos neighborPos
    ) {
        return !this.canSurvive(state, level, pos)
                ? Blocks.AIR.defaultBlockState()
                : super.updateShape(state, direction, neighborState, level, pos,
                neighborPos);
    }

    @Override
    public void tick(
            final @NotNull BlockState state,
            final @NotNull ServerLevel level,
            final @NotNull BlockPos pos, final
            @NotNull RandomSource random
    ) {
        if (!this.canSurvive(state, level, pos)) {
            level.destroyBlock(pos, false);
        }
    }

    @Override
    public void entityInside(
            final @NotNull BlockState state,
            final @NotNull Level level,
            final @NotNull BlockPos pos,
            final Entity entity
    ) {
        if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
            level.destroyBlock(pos, false);
        }
        if (!entity.getType().is(WilderEntityTags.CAN_SWIM_IN_ALGAE)) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(
                    ENTITY_SLOWING_MULTIPLIER, ENTITY_SLOWING_MULTIPLIER,
                    ENTITY_SLOWING_MULTIPLIER));
        }
    }

    private static boolean canLayAt(final BlockGetter level,
                                    final BlockPos pos) {
        FluidState fluidState = level.getFluidState(pos);
        FluidState fluidState2 = level.getFluidState(pos.above());
        return fluidState.getType() == Fluids.WATER &&
                fluidState2.getType() == Fluids.EMPTY;
    }

    @Override
    public boolean isValidBonemealTarget(
            final @NotNull BlockGetter level,
            final @NotNull BlockPos pos,
            final @NotNull BlockState state,
            final boolean isClient
    ) {
        if (!isClient) {
            for (Direction offset : shuffleOffsets(
                    ((LevelAccessor) level).getRandom())) {
                BlockPos blockPos = pos.relative(offset);
                if (level.getBlockState(blockPos).isAir() && state.getBlock()
                        .canSurvive(state, (LevelReader) level, blockPos)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(
            final @NotNull Level level,
            final @NotNull RandomSource random,
            final @NotNull BlockPos pos,
            final @NotNull BlockState state
    ) {
        return true;
    }

    @Override
    public void performBonemeal(
            final @NotNull ServerLevel level,
            final @NotNull RandomSource random,
            final @NotNull BlockPos pos,
            final @NotNull BlockState state
    ) {
        WilderWild.log("Algae Bonemealed @ " + pos, WilderWild.DEV_LOGGING);
        for (Direction offset : shuffleOffsets(level.getRandom())) {
            BlockPos blockPos = pos.relative(offset);
            BlockPos below = blockPos.below();
            if (level.getBlockState(below).getFluidState()
                    .isSourceOfType(Fluids.WATER)) {
                level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH,
                        blockPos, 0);
                level.setBlockAndUpdate(blockPos, state);
            }
        }
    }

    private static final List<Direction> OFFSETS = new ArrayList<>() {{
        add(Direction.EAST);
        add(Direction.NORTH);
        add(Direction.SOUTH);
        add(Direction.WEST);
    }};

    private static List<Direction> shuffleOffsets(final RandomSource random) {
        return Util.toShuffledList(OFFSETS.stream(), random);
    }
}
