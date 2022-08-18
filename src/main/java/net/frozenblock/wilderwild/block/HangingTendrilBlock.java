package net.frozenblock.wilderwild.block;

import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HangingTendrilBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, SculkBehaviour {
    public static final EnumProperty<SculkSensorPhase> HANGING_TENDRIL_PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty TWITCHING = RegisterProperties.TWITCHING;
    public static final BooleanProperty WRINGING_OUT = RegisterProperties.WRINGING_OUT;
    protected static final VoxelShape OUTLINE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);
    private final int range;

    public HangingTendrilBlock(Properties settings, int range) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.INACTIVE).setValue(WATERLOGGED, false).setValue(TWITCHING, false).setValue(WRINGING_OUT, false));
        this.range = range;
    }

    public int getRange() {
        return this.range;
    }

    @Override
    public boolean canSurvive(net.minecraft.world.level.block.state.BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        net.minecraft.world.level.block.state.BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, Direction.DOWN);
    }

    @Override
    public net.minecraft.world.level.block.state.BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    @Override
    public boolean isRandomlyTicking(net.minecraft.world.level.block.state.BlockState state) {
        return true;
    }

    @Override
    public void randomTick(net.minecraft.world.level.block.state.BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos)) {
            world.destroyBlock(pos, true);
        } else if (getPhase(state) == SculkSensorPhase.INACTIVE) {
            BlockEntity entity = world.getBlockEntity(pos);
            if (entity != null) {
                if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                    world.setBlockAndUpdate(pos, state.setValue(TWITCHING, true));
                    wigglyTendril.ticksToStopTwitching = random.nextIntBetweenInclusive(5, 12);
                }
            }
        }
    }

    @Override
    public net.minecraft.world.level.block.state.BlockState updateShape(net.minecraft.world.level.block.state.BlockState state, Direction direction, net.minecraft.world.level.block.state.BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == Direction.UP && !canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void tick(net.minecraft.world.level.block.state.BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (getPhase(state) != SculkSensorPhase.ACTIVE) {
            if (getPhase(state) == SculkSensorPhase.COOLDOWN) {
                world.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.INACTIVE), 3);
            }
        } else if (!isInactive(state)) {
            setCooldown(world, pos, state);
        }
    }

    @Override
    public void onPlace(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState oldState, boolean notify) {
        if (!world.isClientSide() && !state.is(oldState.getBlock())) {
            world.scheduleTick(new BlockPos(pos), state.getBlock(), 1);
        }
    }

    @Override
    public void onRemove(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            if (getPhase(state) == SculkSensorPhase.ACTIVE) {
                updateNeighbors(world, pos);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    private static void updateNeighbors(Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, RegisterBlocks.HANGING_TENDRIL);
        world.updateNeighborsAt(pos.relative(Direction.UP.getOpposite()), RegisterBlocks.HANGING_TENDRIL);
    }

    @Override
    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        return new HangingTendrilBlockEntity(pos, state);
    }

    @Override
    @Nullable
    public <T extends BlockEntity> GameEventListener getListener(ServerLevel world, T blockEntity) {
        return blockEntity instanceof HangingTendrilBlockEntity tendril ? tendril.getEventListener() : null;
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, net.minecraft.world.level.block.state.BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide ? createTickerHelper(type, RegisterBlockEntities.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) -> {
            blockEntity.serverTick(worldx, pos, statex);
        }) : null;
    }

    @Override
    public RenderShape getRenderShape(net.minecraft.world.level.block.state.BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOcclusionShape(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos) {
        return OUTLINE_SHAPE;
    }

    @Override
    public VoxelShape getShape(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return OUTLINE_SHAPE;
    }

    public static SculkSensorPhase getPhase(net.minecraft.world.level.block.state.BlockState state) {
        return state.getValue(HANGING_TENDRIL_PHASE);
    }

    public static boolean isInactive(net.minecraft.world.level.block.state.BlockState state) {
        return getPhase(state) == SculkSensorPhase.INACTIVE;
    }

    public static void setCooldown(Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        world.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.COOLDOWN), 3);
        world.scheduleTick(pos, state.getBlock(), 1);
        if (!(Boolean) state.getValue(WATERLOGGED)) {
            world.playSound(null, pos, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 1.0F);
        }
        updateNeighbors(world, pos);
    }

    public static void setActive(@Nullable Entity entity, Level world, BlockPos pos, net.minecraft.world.level.block.state.BlockState state, GameEvent event, int power) {
        world.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.ACTIVE), 3);
        world.scheduleTick(pos, state.getBlock(), 60);
        updateNeighbors(world, pos);
        world.gameEvent(entity, event, pos);
        if (!state.getValue(WATERLOGGED)) {
            world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.2F + 1.0F);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
        builder.add(HANGING_TENDRIL_PHASE, WATERLOGGED, TWITCHING, WRINGING_OUT);
    }

    @Override
    public boolean hasAnalogOutputSignal(net.minecraft.world.level.block.state.BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof HangingTendrilBlockEntity hangingEntity) {
            return getPhase(state) == SculkSensorPhase.ACTIVE ? hangingEntity.getLastVibrationFrequency() : 0;
        } else {
            return 0;
        }
    }

    @Override
    public boolean isPathfindable(net.minecraft.world.level.block.state.BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
        return true;
    }

    @Override
    public boolean useShapeForLightOcclusion(net.minecraft.world.level.block.state.BlockState state) {
        return true;
    }

    @Override
    public void spawnAfterBreak(net.minecraft.world.level.block.state.BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, boolean bl) {
        super.spawnAfterBreak(state, world, pos, stack, bl);
        this.tryDropExperience(world, pos, stack, ConstantInt.of(1));
    }

    public static boolean shouldHavePogLighting(net.minecraft.world.level.block.state.BlockState state) {
        return getPhase(state) == SculkSensorPhase.ACTIVE || state.getValue(WRINGING_OUT);
    }

    public static HangingTendrilBlockEntity getEntity(Level world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                return wigglyTendril;
            }
        }
        return null;
    }

    public static HangingTendrilBlockEntity getEntity(LevelAccessor world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity != null) {
            if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
                return wigglyTendril;
            }
        }
        return null;
    }

    @Override
    public InteractionResult use(net.minecraft.world.level.block.state.BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (isInactive(state) && !state.getValue(WRINGING_OUT)) {
            if (world.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                HangingTendrilBlockEntity tendrilEntity = getEntity(world, pos);
                if (tendrilEntity != null) {
                    if (tendrilEntity.storedXP > 0) {
                        world.setBlockAndUpdate(pos, state.setValue(WRINGING_OUT, true));
                        world.playSound(
                                null,
                                pos,
                                RegisterSounds.BLOCK_HANGING_TENDRIL_WRING,
                                SoundSource.BLOCKS,
                                1f,
                                world.random.nextFloat() * 0.1F + 0.9F
                        );
                        tendrilEntity.ringOutTicksLeft = 5;
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return InteractionResult.PASS;
    }

    @Override
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, LevelAccessor world, BlockPos catalystPos, RandomSource random, SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        HangingTendrilBlockEntity tendrilEntity = getEntity(world, cursor.getPos());
        if (tendrilEntity != null) {
            if (tendrilEntity.storedXP < 900) {
                if (cursor.getCharge() > 1) {
                    tendrilEntity.storedXP = tendrilEntity.storedXP + 2;
                    return cursor.getCharge() - 2;
                } else {
                    ++tendrilEntity.storedXP;
                    return cursor.getCharge() - 1;
                }
            }
        }
        return cursor.getCharge();
    }
}
