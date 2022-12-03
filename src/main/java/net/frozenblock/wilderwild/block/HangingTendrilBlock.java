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
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSpreader;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
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
	public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = level.getBlockState(blockPos);
		return blockState.isFaceSturdy(level, blockPos, Direction.DOWN);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockPos blockPos = ctx.getClickedPos();
		FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
		return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	public void randomTick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		} else if (getPhase(state) == SculkSensorPhase.INACTIVE) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
				level.setBlockAndUpdate(pos, state.setValue(TWITCHING, true));
				wigglyTendril.ticksToStopTwitching = random.nextIntBetweenInclusive(5, 12);
			}
		}
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (direction == Direction.UP && !canSurvive(state, level, currentPos)) {
			level.destroyBlock(currentPos, true);
		}
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@NotNull
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (getPhase(state) != SculkSensorPhase.ACTIVE) {
			if (getPhase(state) == SculkSensorPhase.COOLDOWN) {
				level.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.INACTIVE), 3);
			}
		} else if (!isInactive(state)) {
			setCooldown(level, pos, state);
		}
	}

	@Override
	public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
		if (!level.isClientSide() && !state.is(oldState.getBlock())) {
			level.scheduleTick(new BlockPos(pos), state.getBlock(), 1);
		}
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (getPhase(state) == SculkSensorPhase.ACTIVE) {
				updateNeighbors(level, pos);
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

	private static void updateNeighbors(Level level, BlockPos pos) {
		level.updateNeighborsAt(pos, RegisterBlocks.HANGING_TENDRIL);
		level.updateNeighborsAt(pos.relative(Direction.UP.getOpposite()), RegisterBlocks.HANGING_TENDRIL);
	}

	@Override
	@Nullable
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HangingTendrilBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> GameEventListener getListener(@NotNull ServerLevel level, @NotNull T blockEntity) {
		return blockEntity instanceof HangingTendrilBlockEntity tendril ? tendril.getEventListener() : null;
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) -> blockEntity.serverTick(worldx, pos, statex)) : null;
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.MODEL;
	}

	@Override
	@NotNull
	public VoxelShape getOcclusionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return OUTLINE_SHAPE;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	public static SculkSensorPhase getPhase(BlockState state) {
		return state.getValue(HANGING_TENDRIL_PHASE);
	}

	public static boolean isInactive(BlockState state) {
		return getPhase(state) == SculkSensorPhase.INACTIVE;
	}

	public static void setCooldown(Level level, BlockPos pos, BlockState state) {
		level.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.COOLDOWN), 3);
		level.scheduleTick(pos, state.getBlock(), 1);
		if (!(Boolean) state.getValue(WATERLOGGED)) {
			level.playSound(null, pos, SoundEvents.SCULK_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 1.0F);
		}
		updateNeighbors(level, pos);
	}

	public static void setActive(@Nullable Entity entity, Level level, BlockPos pos, BlockState state, GameEvent event, int power) {
		level.setBlock(pos, state.setValue(HANGING_TENDRIL_PHASE, SculkSensorPhase.ACTIVE), 3);
		level.scheduleTick(pos, state.getBlock(), 60);
		updateNeighbors(level, pos);
		level.gameEvent(entity, event, pos);
		if (!state.getValue(WATERLOGGED)) {
			level.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.SCULK_CLICKING, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 1.0F);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
		builder.add(HANGING_TENDRIL_PHASE, WATERLOGGED, TWITCHING, WRINGING_OUT);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, Level level, @NotNull BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof HangingTendrilBlockEntity hangingEntity) {
			return getPhase(state) == SculkSensorPhase.ACTIVE ? hangingEntity.getLastVibrationFrequency() : 0;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
		return true;
	}

	@Override
	public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
		return true;
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean bl) {
		super.spawnAfterBreak(state, level, pos, stack, bl);
		this.tryDropExperience(level, pos, stack, ConstantInt.of(1));
	}

	public static boolean shouldHavePogLighting(BlockState state) {
		return getPhase(state) == SculkSensorPhase.ACTIVE || state.getValue(WRINGING_OUT);
	}

    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (isInactive(state) && !state.getValue(WRINGING_OUT)) {
            if (level.isClientSide) {
                return InteractionResult.SUCCESS;
            } else {
                if (level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity tendrilEntity) {
                    if (tendrilEntity.storedXP > 0) {
                        level.setBlockAndUpdate(pos, state.setValue(WRINGING_OUT, true));
                        level.playSound(null,
                                pos,
                                RegisterSounds.BLOCK_HANGING_TENDRIL_WRING,
                                SoundSource.BLOCKS,
                                1F,
                                level.random.nextFloat() * 0.1F + 0.9F
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
    public int attemptUseCharge(SculkSpreader.ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
        if (level.getBlockEntity(cursor.getPos()) instanceof HangingTendrilBlockEntity tendrilEntity) {
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
