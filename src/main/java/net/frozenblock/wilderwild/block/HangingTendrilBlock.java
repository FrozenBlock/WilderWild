/*
 * Copyright 2023-2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import org.jetbrains.annotations.NotNull;
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

	public HangingTendrilBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(PHASE, SculkSensorPhase.INACTIVE)
			.setValue(WATERLOGGED, false)
			.setValue(TWITCHING, false)
			.setValue(WRINGING_OUT, false)
			.setValue(POWER, 0)
		);
	}

	public static void deactivate(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, RandomSource random) {
		level.setBlockAndUpdate(pos, state.setValue(PHASE, SculkSensorPhase.INACTIVE).setValue(POWER, 0));
		if (!state.getValue(WATERLOGGED)) {
			level.playSound(null, pos, WWSounds.BLOCK_HANGING_TENDRIL_CLICKING_STOP, SoundSource.BLOCKS, 1F, random.nextFloat() * 0.2F + 0.8F);
		}

		SculkSensorBlock.updateNeighbours(level, pos, state);
	}

	public static boolean shouldHavePogLighting(BlockState state) {
		return SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE || state.getValue(WRINGING_OUT);
	}

	public static boolean canActivate(@NotNull BlockState state) {
		return SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE;
	}

	@NotNull
	@Override
	protected MapCodec<? extends HangingTendrilBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockPos blockPos = pos.above();
		BlockState blockState = level.getBlockState(blockPos);
		return blockState.isFaceSturdy(level, blockPos, Direction.DOWN);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		BlockPos blockPos = ctx.getClickedPos();
		FluidState fluidState = ctx.getLevel().getFluidState(blockPos);
		return this.defaultBlockState().setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		} else if (SculkSensorBlock.getPhase(state) == SculkSensorPhase.INACTIVE) {
			BlockEntity entity = level.getBlockEntity(pos);
			if (entity instanceof HangingTendrilBlockEntity wigglyTendril) {
				level.setBlockAndUpdate(pos, state.setValue(TWITCHING, true));
				wigglyTendril.ticksToStopTwitching = random.nextIntBetweenInclusive(TWITCH_MIN_TICKS, TWITCH_MAX_TICKS);
			}
		}
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (!state.canSurvive(level, currentPos)) {
			level.scheduleTick(currentPos, this, 1);
		}
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!state.canSurvive(level, pos)) {
			level.destroyBlock(pos, true);
		}
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		if (level.isClientSide() || state.is(oldState.getBlock())) return;
		if (state.getValue(POWER) > 0 && !level.getBlockTicks().hasScheduledTick(pos, this)) {
			level.setBlock(pos, state.setValue(POWER, 0), 18);
		}
		level.scheduleTick(pos, state.getBlock(), 1);
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			if (SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE) {
				updateNeighbours(level, pos, state);
			}

			super.onRemove(state, level, pos, newState, movedByPiston);
		}
	}

	public static void updateNeighbours(@NotNull Level level, BlockPos pos, @NotNull BlockState state) {
		Block block = state.getBlock();
		level.updateNeighborsAt(pos, block);
		level.updateNeighborsAt(pos.below(), block);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HangingTendrilBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, WWBlockEntityTypes.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.serverTick(worldx, pos, statex)
		) : createTickerHelper(type, WWBlockEntityTypes.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.clientTick(worldx ,statex));
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return WWBlockConfig.Client.BILLBOARD_TENDRILS ? RenderShape.INVISIBLE : RenderShape.MODEL;
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

	public void activate(
		@Nullable Entity entity,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull BlockState state,
		@NotNull Holder<GameEvent> gameEvent,
		int power,
		int frequency
	) {
		level.setBlockAndUpdate(pos, state.setValue(PHASE, SculkSensorPhase.ACTIVE).setValue(POWER, power));
		boolean tendrilsCarryEvents = WWBlockConfig.get().sculk.tendrilsCarryEvents;
		SculkSensorBlock.updateNeighbours(level, pos, state);
		SculkSensorBlock.tryResonateVibration(tendrilsCarryEvents ? entity : null, level, pos, frequency);
		level.gameEvent(tendrilsCarryEvents ? entity : null, tendrilsCarryEvents ? gameEvent : GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (!state.getValue(WATERLOGGED)) {
			level.playSound(
				null,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5D,
				WWSounds.BLOCK_HANGING_TENDRIL_CLICKING,
				SoundSource.BLOCKS,
				1F,
				level.random.nextFloat() * 0.2F + 0.8F
			);
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
		builder.add(PHASE, POWER, WATERLOGGED, TWITCHING, WRINGING_OUT);
	}

	@Override
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		BlockEntity blockEntity = level.getBlockEntity(pos);
		if (blockEntity instanceof HangingTendrilBlockEntity hangingEntity) {
			return SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE ? hangingEntity.getLastVibrationFrequency() : 0;
		} else {
			return 0;
		}
	}

	@Override
	public boolean isPathfindable(@NotNull BlockState state, @NotNull PathComputationType type) {
		return true;
	}

	@Override
	public boolean useShapeForLightOcclusion(@NotNull BlockState state) {
		return true;
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean bl) {
		super.spawnAfterBreak(state, level, pos, stack, bl);
		if (level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity hangingTendrilBlockEntity) {
			if (hangingTendrilBlockEntity.getStoredXP() > 0) {
				this.popExperience(level, pos, hangingTendrilBlockEntity.getStoredXP());
			}
		}
	}

	@Override
	@NotNull
	public InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
		if (SculkSensorBlock.canActivate(state) && !state.getValue(WRINGING_OUT)) {
			if (level.isClientSide) {
				return InteractionResult.SUCCESS;
			} else {
				if (level.getBlockEntity(pos) instanceof HangingTendrilBlockEntity tendrilEntity) {
					if (tendrilEntity.getStoredXP() > 0) {
						level.setBlockAndUpdate(pos, state.setValue(WRINGING_OUT, true));
						level.playSound(null,
							pos,
							WWSounds.BLOCK_HANGING_TENDRIL_WRING,
							SoundSource.BLOCKS,
							1F,
							level.getRandom().nextFloat() * 0.1F + 0.9F
						);
						tendrilEntity.ringOutTicksLeft = RING_OUT_TICKS;
						return InteractionResult.SUCCESS;
					}
				}
			}
		}
		return InteractionResult.PASS;
	}

	@Override
	public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
		if (level.getBlockEntity(cursor.getPos()) instanceof HangingTendrilBlockEntity tendrilEntity) {
			int storedXP = tendrilEntity.getStoredXP();
			if (storedXP < MAX_STORED_XP) {
				int xpSwapAmount = Math.min(cursor.getCharge(), 2);
				tendrilEntity.setStoredXP(storedXP + xpSwapAmount);
				return cursor.getCharge() - xpSwapAmount;
			}
		}
		return cursor.getCharge();
	}
}
