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
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.block.termite.TermiteManager;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TermiteMoundBlock extends BaseEntityBlock {
	public static final MapCodec<TermiteMoundBlock> CODEC = simpleCodec(TermiteMoundBlock::new);
	public static final int MIN_PLACEMENT_TICK_DELAY = 40;
	public static final int MAX_PLACEMENT_TICK_DELAY = 200;
	public static final int MIN_TICK_DELAY = 90;
	public static final int MAX_TICK_DELAY = 150;
	public static final int MIN_AWAKE_LIGHT_LEVEL = 7;

	public TermiteMoundBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(
			this.stateDefinition.any()
				.setValue(WWBlockStateProperties.NATURAL, false)
				.setValue(WWBlockStateProperties.TERMITES_AWAKE, false)
				.setValue(WWBlockStateProperties.CAN_SPAWN_TERMITE, false)
		);
	}

	@NotNull
	@Override
	protected MapCodec<? extends TermiteMoundBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new TermiteMoundBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WWBlockStateProperties.NATURAL, WWBlockStateProperties.TERMITES_AWAKE, WWBlockStateProperties.CAN_SPAWN_TERMITE);
	}

	@Override
	@NotNull
	public BlockState updateShape(
		@NotNull BlockState state,
		@NotNull Direction direction,
		@NotNull BlockState neighborState,
		@NotNull LevelAccessor level,
		@NotNull BlockPos currentPos,
		@NotNull BlockPos neighborPos
	) {
		if (!TermiteManager.isStateSafeForTermites(neighborState)) {
			state = state.setValue(WWBlockStateProperties.TERMITES_AWAKE, false)
				.setValue(WWBlockStateProperties.CAN_SPAWN_TERMITE, false);
		}
		level.scheduleTick(currentPos, this, level.getRandom().nextInt(MIN_PLACEMENT_TICK_DELAY, MAX_PLACEMENT_TICK_DELAY));
		return state;
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
		level.scheduleTick(pos, this, level.random.nextInt(MIN_PLACEMENT_TICK_DELAY, MAX_PLACEMENT_TICK_DELAY));
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean movedByPiston) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof TermiteMoundBlockEntity termiteMoundBlockEntity) {
				termiteMoundBlockEntity.termiteManager.clearTermites(level);
			}
		}
		super.onRemove(state, level, pos, newState, movedByPiston);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		BlockState evaluatedState = this.evaluateMoundBlockStateAtPosition(state, level, pos);
		if (evaluatedState != state) {
			level.setBlockAndUpdate(pos, evaluatedState);
		}
		level.scheduleTick(pos, this, random.nextInt(MIN_TICK_DELAY, MAX_TICK_DELAY));
	}

	public BlockState evaluateMoundBlockStateAtPosition(@NotNull BlockState moundState, Level level, BlockPos pos) {
		boolean areTermitesSafe = TermiteManager.areTermitesSafe(level, pos);
		boolean canAwaken = canTermitesWaken(level, pos) && areTermitesSafe;
		if (canAwaken != moundState.getValue(WWBlockStateProperties.TERMITES_AWAKE)) {
			moundState =  moundState.setValue(WWBlockStateProperties.TERMITES_AWAKE, canAwaken);
		}
		if (areTermitesSafe != moundState.getValue(WWBlockStateProperties.CAN_SPAWN_TERMITE)) {
			moundState = moundState.setValue(WWBlockStateProperties.CAN_SPAWN_TERMITE, areTermitesSafe);
		}
		return moundState;
	}

	public static boolean canTermitesWaken(@NotNull Level level, @NotNull BlockPos pos) {
		return !shouldTermitesSleep(level, getLightLevel(level, pos));
	}

	public static boolean shouldTermitesSleep(@NotNull Level level, int light) {
		return level.isNight() && light < MIN_AWAKE_LIGHT_LEVEL;
	}

	public static int getLightLevel(@NotNull Level level, @NotNull BlockPos blockPos) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.setWithOffset(blockPos, direction);
			int newLight = !level.isRaining() ? level.getMaxLocalRawBrightness(mutableBlockPos) : level.getBrightness(LightLayer.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
		}
		return finalLight;
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ?
			createTickerHelper(type, WWBlockEntityTypes.TERMITE_MOUND, (worldx, pos, statex, blockEntity) ->
				blockEntity.tickServer(
					worldx,
					pos,
					statex.getValue(WWBlockStateProperties.NATURAL),
					statex.getValue(WWBlockStateProperties.TERMITES_AWAKE),
					statex.getValue(WWBlockStateProperties.CAN_SPAWN_TERMITE)
				)
			)
			: createTickerHelper(type, WWBlockEntityTypes.TERMITE_MOUND, (worldx, pos, statex, blockEntity) -> blockEntity.tickClient());
	}
}
