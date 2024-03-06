/*
 * Copyright 2023-2024 FrozenBlock
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
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
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

@SuppressWarnings("deprecation")
public class HangingTendrilBlock extends BaseEntityBlock implements SimpleWaterloggedBlock, SculkBehaviour {
	public static final int ACTIVE_TICKS = 60;
	public static final EnumProperty<SculkSensorPhase> PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final BooleanProperty TWITCHING = RegisterProperties.TWITCHING;
	public static final BooleanProperty WRINGING_OUT = RegisterProperties.WRINGING_OUT;
	public static final MapCodec<HangingTendrilBlock> CODEC = simpleCodec(HangingTendrilBlock::new);
	protected static final VoxelShape OUTLINE_SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 16.0D, 11.0D);

	public HangingTendrilBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(PHASE, SculkSensorPhase.INACTIVE).setValue(WATERLOGGED, false).setValue(TWITCHING, false).setValue(WRINGING_OUT, false).setValue(POWER, 0));
	}

	public static void deactivate(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos, state.setValue(PHASE, SculkSensorPhase.INACTIVE).setValue(POWER, 0), 3);
		if (!state.getValue(WATERLOGGED)) {
			level.playSound(null, pos, RegisterSounds.BLOCK_HANGING_TENDRIL_CLICKING_STOP, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.2F + 0.8F);
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
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE) {
			deactivate(level, pos, state);
		}
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
		if (level.isClientSide() || state.is(oldState.getBlock())) {
			return;
		} else if (state.getValue(POWER) > 0 && !level.getBlockTicks().hasScheduledTick(pos, this)) {
			level.setBlock(pos, state.setValue(POWER, 0), 18);
		}
		level.scheduleTick(pos, state.getBlock(), 1);
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (state.is(newState.getBlock())) {
			return;
		}
		if (SculkSensorBlock.getPhase(state) == SculkSensorPhase.ACTIVE) {
			SculkSensorBlock.updateNeighbours(level, pos, state);
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new HangingTendrilBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.serverTick(worldx, pos, statex)
		) : createTickerHelper(type, RegisterBlockEntities.HANGING_TENDRIL, (worldx, pos, statex, blockEntity) ->
			blockEntity.clientTick(statex));
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return BlockConfig.get().billboardTendrils ? RenderShape.INVISIBLE : RenderShape.MODEL;
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

	public int getActiveTicks() {
		return ACTIVE_TICKS;
	}

	public void activate(@Nullable Entity entity, @NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull GameEvent gameEvent, int power, int frequency) {
		world.setBlock(pos, state.setValue(PHASE, SculkSensorPhase.ACTIVE).setValue(POWER, power), 3);
		world.scheduleTick(pos, state.getBlock(), this.getActiveTicks());
		boolean tendrilsCarryEvents = BlockConfig.get().tendrilsCarryEvents;
		SculkSensorBlock.updateNeighbours(world, pos, state);
		SculkSensorBlock.tryResonateVibration(tendrilsCarryEvents ? entity : null, world, pos, frequency);
		world.gameEvent(tendrilsCarryEvents ? entity : null, tendrilsCarryEvents ? gameEvent : GameEvent.SCULK_SENSOR_TENDRILS_CLICKING, pos);
		if (!state.getValue(WATERLOGGED)) {
			world.playSound(
				null,
				pos.getX() + 0.5D,
				pos.getY() + 0.5D,
				pos.getZ() + 0.5,
				RegisterSounds.BLOCK_HANGING_TENDRIL_CLICKING,
				SoundSource.BLOCKS,
				1.0F,
				world.random.nextFloat() * 0.2F + 0.8F
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
		this.tryDropExperience(level, pos, stack, ConstantInt.of(1));
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (SculkSensorBlock.canActivate(state) && !state.getValue(WRINGING_OUT)) {
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
							level.getRandom().nextFloat() * 0.1F + 0.9F
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
	public int attemptUseCharge(SculkSpreader.@NotNull ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreadManager, boolean shouldConvertToBlock) {
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
