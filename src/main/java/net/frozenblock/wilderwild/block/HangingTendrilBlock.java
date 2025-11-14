/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkSensorBlock;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class HangingTendrilBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, SculkBehaviour {
	public static final int ACTIVE_TICKS = 60;
	public static final int TWITCH_MIN_TICKS = 5;
	public static final int TWITCH_MAX_TICKS = 12;
	public static final int MAX_STORED_XP = 900;
	public static final int RING_OUT_TICKS = 5;
	public static final EnumProperty<SculkSensorPhase> PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TWITCHING = WWBlockStateProperties.TWITCHING;
	public static final BooleanProperty WRINGING_OUT = WWBlockStateProperties.WRINGING_OUT;
	public static final MapCodec<HangingTendrilBlock> CODEC = simpleCodec(HangingTendrilBlock::new);
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(5D, 0D, 5D, 11D, 16D, 11D);

	public HangingTendrilBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(PHASE, SculkSensorPhase.INACTIVE)
			.setValue(WATERLOGGED, false)
			.setValue(TWITCHING, false)
			.setValue(WRINGING_OUT, false)
			.setValue(POWER, 0)
		);
	}

	public static void deactivate(Level level, BlockPos pos, BlockState state, RandomSource random) {
		level.setBlockAndUpdate(pos, state.setValue(PHASE, SculkSensorPhase.INACTIVE).setValue(POWER, 0));
		if (!state.getValue(WATERLOGGED)) level.playSound(null, pos, WWSounds.BLOCK_HANGING_TENDRIL_CLICKING_STOP, SoundSource.BLOCKS, 1F, random.nextFloat() * 0.2F + 0.8F);
		SculkSensorBlock.updateNeighbours(level, pos, state);
	}

	public static boolean shouldHavePogLighting(BlockState state) {
		return SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE || state.getValue(WRINGING_OUT);
	}

	public static boolean canActivate(BlockState state) {
		return SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE;
	}

	@Override
	protected MapCodec<? extends HangingTendrilBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		final BlockPos blockPos = pos.above();
		final BlockState blockState = level.getBlockState(blockPos);
		return blockState.isFaceSturdy(level, blockPos, Direction.DOWN);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockPos blockPos = context.getClickedPos();
		final FluidState fluidState = context.getLevel().getFluidState(blockPos);
		return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
			return;
		}
		if (SculkSensorBlock.getPhase(state) != SculkSensorPhase.INACTIVE) return;
		if (!(level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity hangingTendril)) return;
		level.setBlockAndUpdate(pos, state.setValue(TWITCHING, true));
		hangingTendril.ticksToStopTwitching = random.nextIntBetweenInclusive(TWITCH_MIN_TICKS, TWITCH_MAX_TICKS);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		if (!state.canSurvive(level, pos)) scheduledTickAccess.scheduleTick(pos, this, 1);
		if (state.getValue(WATERLOGGED)) scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!state.canSurvive(level, pos)) level.destroyBlock(pos, true);
	}

	@Override
	public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		if (level.isClientSide() || state.is(oldState.getBlock())) return;
		if (state.getValue(POWER) > 0 && !level.getBlockTicks().hasScheduledTick(pos, this)) {
			level.setBlock(pos, state.setValue(POWER, 0), 18);
		}
		level.scheduleTick(pos, state.getBlock(), 1);
	}

	@Override
	protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean bl) {
		if (SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE) updateNeighbours(level, pos, state);
	}

	public static void updateNeighbours(Level level, BlockPos pos, BlockState state) {
		Block block = state.getBlock();
		level.updateNeighborsAt(pos, block);
		level.updateNeighborsAt(pos.below(), block);
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new HangingTendrilBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		return !level.isClientSide() ? createTickerHelper(type, WWBlockEntityTypes.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.serverTick(worldx, pos, statex)
		) : createTickerHelper(type, WWBlockEntityTypes.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.clientTick(worldx ,statex));
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return WWBlockConfig.Client.BILLBOARD_TENDRILS ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState state) {
		return OUTLINE_SHAPE;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return OUTLINE_SHAPE;
	}

	public void activate(
		@Nullable Entity entity,
		Level level,
		BlockPos pos,
		BlockState state,
		Holder<GameEvent> gameEvent,
		int power,
		int frequency
	) {
		level.setBlockAndUpdate(pos, state.setValue(PHASE, SculkSensorPhase.ACTIVE).setValue(POWER, power));
		final boolean tendrilsCarryEvents = WWBlockConfig.get().sculk.tendrilsCarryEvents;
		SculkSensorBlock.updateNeighbours(level, pos, state);
		SculkSensorBlock.tryResonateVibration(tendrilsCarryEvents ? entity : null, level, pos, frequency);
		level.gameEvent(tendrilsCarryEvents ? entity : null, tendrilsCarryEvents ? gameEvent : GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (state.getValue(WATERLOGGED)) return;
		level.playSound(
			null,
			pos,
			WWSounds.BLOCK_HANGING_TENDRIL_CLICKING,
			SoundSource.BLOCKS,
			1F,
			level.random.nextFloat() * 0.2F + 0.8F
		);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(PHASE, POWER, WATERLOGGED, TWITCHING, WRINGING_OUT);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		if (!(level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity hangingTendril)) return 0;
		return SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE ? hangingTendril.getLastVibrationFrequency() : 0;
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return true;
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean bl) {
		super.spawnAfterBreak(state, level, pos, stack, bl);
		if (!(level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity hangingTendril)) return;
		if (hangingTendril.getStoredXP() > 0) this.popExperience(level, pos, hangingTendril.getStoredXP());
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!SculkSensorBlock.canActivate(state) || state.getValue(WRINGING_OUT)) return InteractionResult.PASS;
		if (level.isClientSide()) return InteractionResult.SUCCESS;
		if (!(level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity hangingTendril)) return InteractionResult.PASS;
		if (hangingTendril.getStoredXP() <= 0) return InteractionResult.PASS;

		level.setBlockAndUpdate(pos, state.setValue(WRINGING_OUT, true));
		level.playSound(null, pos, WWSounds.BLOCK_HANGING_TENDRIL_WRING, SoundSource.BLOCKS, 1F, level.getRandom().nextFloat() * 0.1F + 0.9F);
		hangingTendril.ringOutTicksLeft = RING_OUT_TICKS;
		return InteractionResult.SUCCESS;
	}

	@Override
	public int attemptUseCharge(
		SculkSpreader.ChargeCursor cursor,
		LevelAccessor level,
		BlockPos catalystPos,
		RandomSource random,
		SculkSpreader spreadManager,
		boolean shouldConvertToBlock
	) {
		if (!(level.getBlockEntity(cursor.getPos()) instanceof HangingTendrilBlockEntity tendrilEntity)) return cursor.getCharge();
		final int storedXP = tendrilEntity.getStoredXP();
		if (storedXP >= MAX_STORED_XP) return cursor.getCharge();
		final int xpSwapAmount = Math.min(cursor.getCharge(), 2);
		tendrilEntity.setStoredXP(storedXP + xpSwapAmount);
		return cursor.getCharge() - xpSwapAmount;
	}
}
