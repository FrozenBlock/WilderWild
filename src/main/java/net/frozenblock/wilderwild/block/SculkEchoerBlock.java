package net.frozenblock.wilderwild.block;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.frozenblock.wilderwild.block.entity.SculkEchoerBlockEntity;
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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SculkSensorBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SculkEchoerBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {

    public static final EnumProperty<SculkSensorPhase> SCULK_ECHOER_PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty UPSIDEDOWN = RegisterProperties.UPSIDE_DOWN;
	private static final Object2IntMap<GameEvent> VIBRATION_FREQUENCY_FOR_EVENT = SculkSensorBlock.VIBRATION_FREQUENCY_FOR_EVENT;
    private final int range;

    private static final VoxelShape OUTLINE = Shapes.block();
    private static final VoxelShape SHAPE = Shapes.or(Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(1.0D, 4.0D, 1.0D, 15.0D, 10.0D, 15.0D));
    private static final VoxelShape SHAPE_UPSIDEDOWN = Shapes.or(Block.box(0.0D, 12.0D, 0.0D, 16.0D, 16.0D, 16.0D), Block.box(1.0D, 6.0D, 1.0D, 15.0D, 12.0D, 15.0D));

    public SculkEchoerBlock(Properties settings, int range) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(SCULK_ECHOER_PHASE, SculkSensorPhase.INACTIVE).setValue(WATERLOGGED, false).setValue(UPSIDEDOWN, false));
        this.range = range;
    }

	@Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SCULK_ECHOER_PHASE, WATERLOGGED, UPSIDEDOWN);
    }

	@Override
    public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, world, pos, stack, dropExperience);
        if (dropExperience) {
            this.tryDropExperience(world, pos, stack, ConstantInt.of(5));
        }
    }

    @Override
    public void stepOn(Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Entity entity) {
        if (!level.isClientSide && isInactive(state) && entity.getType() != EntityType.WARDEN) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SculkEchoerBlockEntity sculkEchoerBlockEntity) {
                sculkEchoerBlockEntity.setLastVibrationFrequency(VIBRATION_FREQUENCY_FOR_EVENT.get(GameEvent.STEP));
            }

            setActive(entity, level, pos, state, 20);
        }

        super.stepOn(level, pos, state, entity);
    }

	@Override
    public void onPlace(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean notify) {
        if (!world.isClientSide && !state.is(oldState.getBlock())) {
            world.scheduleTick(new BlockPos(pos), state.getBlock(), 1);
        }
    }

	@Override
    public void onRemove(BlockState state, @NotNull Level world, @NotNull BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            if (getPhase(state) == SculkSensorPhase.ACTIVE) {
                updateNeighbors(world, pos);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

	@Override
    public void tick(@NotNull BlockState state, @NotNull ServerLevel world, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (getPhase(state) != SculkSensorPhase.ACTIVE) {
            if (getPhase(state) == SculkSensorPhase.COOLDOWN) {
                world.setBlock(pos, state.setValue(SCULK_ECHOER_PHASE, SculkSensorPhase.INACTIVE), Block.UPDATE_ALL);
            }
        } else {
            setCooldown(world, pos, state);
        }
    }

    @Override
	@NotNull
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

	@Override
	@NotNull
    public VoxelShape getCollisionShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(UPSIDEDOWN)) {
            return SHAPE_UPSIDEDOWN;
        }
        return SHAPE;
    }

    public VoxelShape getOcclusionShape(BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos) {
        if (state.getValue(UPSIDEDOWN)) {
            return SHAPE_UPSIDEDOWN;
        }
        return SHAPE;
    }

    @Override
    public boolean useShapeForLightOcclusion(@NotNull BlockState blockState) {
        return true;
    }

    @Override
    public VoxelShape getShape(@NotNull BlockState blockState, @NotNull BlockGetter blockView, @NotNull BlockPos blockPos, @NotNull CollisionContext shapeContext) {
        return OUTLINE;
    }

    public static SculkSensorPhase getPhase(BlockState state) {
        return state.getValue(SCULK_ECHOER_PHASE);
    }

    public static boolean isInactive(BlockState state) {
        return getPhase(state) == SculkSensorPhase.INACTIVE;
    }

    public static void setCooldown(Level world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(SCULK_ECHOER_PHASE, SculkSensorPhase.COOLDOWN), Block.UPDATE_ALL);
        world.scheduleTick(pos, state.getBlock(), 1);
        if (!state.getValue(WATERLOGGED)) {
            world.playSound(null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.7F);
            world.playSound(null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 0.5F, world.random.nextFloat() * 0.1F + 0.9F * 0.5F);
            world.playSound(null, (double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, SoundEvents.SCULK_BLOCK_SPREAD, SoundSource.BLOCKS, 0.25F, world.random.nextFloat() * 0.1F + 0.9F * 0.3F);
        }
        updateNeighbors(world, pos);
    }

    public static void setActive(@Nullable Entity entity, Level world, BlockPos pos, BlockState state, int bubbles) {
        boolean canRun = true;
        if (entity != null) {
            if (entity instanceof Warden) {
                canRun = false;
            }
        }
        BlockEntity entity1 = world.getBlockEntity(pos);
        if (entity1 instanceof SculkEchoerBlockEntity echoer) {
            echoer.echoBubblesLeft = bubbles;
            echoer.bigBubble = true;
        }
        if (canRun) {
            int additionalCooldown = state.getValue(WATERLOGGED) ? 75 : 45;
            world.setBlock(pos, state.setValue(SCULK_ECHOER_PHASE, SculkSensorPhase.ACTIVE), Block.UPDATE_ALL);
            world.scheduleTick(pos, state.getBlock(), bubbles + additionalCooldown);
            updateNeighbors(world, pos);
            if (!state.getValue(WATERLOGGED)) {
                world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, RegisterSounds.BLOCK_SCULK_ECHOER_ECHO, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
            }
        }
    }

	@Override
    public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
        return true;
    }

	@Override
    public int getAnalogOutputSignal(@NotNull BlockState state, Level world, @NotNull BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof SculkEchoerBlockEntity echoerEntity) {
            return getPhase(state) == SculkSensorPhase.ACTIVE ? echoerEntity.getLastVibrationFrequency() : 0;
        } else {
            return 0;
        }
    }

	@Override
    public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull PathComputationType type) {
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SculkEchoerBlockEntity(pos, state);
    }

	@Override
	@NotNull
    public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor world, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    private static void updateNeighbors(Level world, BlockPos pos) {
        world.updateNeighborsAt(pos, RegisterBlocks.SCULK_ECHOER);
        world.updateNeighborsAt(pos.relative(Direction.UP.getOpposite()), RegisterBlocks.SCULK_ECHOER);
    }

    public int getRange() {
        return this.range;
    }

	@Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getClickedPos();
        FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
        boolean isUpsideDown = ctx.getClickedFace() == Direction.DOWN;
        return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER).setValue(UPSIDEDOWN, isUpsideDown);
    }

	@Override
	@NotNull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

	@Override
    @Nullable
    public <T extends BlockEntity> GameEventListener getListener(@NotNull ServerLevel world, T blockEntity) {
        return blockEntity instanceof SculkEchoerBlockEntity ? ((SculkEchoerBlockEntity) blockEntity).getEventListener() : null;
    }

	@Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return !world.isClientSide ? createTickerHelper(type, RegisterBlockEntities.SCULK_ECHOER, (worldx, pos, statex, blockEntity) -> {
            blockEntity.tick(worldx, pos, statex);
        }) : null;
    }
}
