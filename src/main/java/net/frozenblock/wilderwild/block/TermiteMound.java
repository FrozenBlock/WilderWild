/*
 * Copyright 2022-2023 FrozenBlock
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

import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.entity.TermiteManager;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
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

public class TermiteMound extends BaseEntityBlock {

    public TermiteMound(Properties settings) {
        super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(RegisterProperties.NATURAL, false).setValue(RegisterProperties.TERMITES_AWAKE, false).setValue(RegisterProperties.CAN_SPAWN_TERMITE, false));
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new TermiteMoundBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RegisterProperties.NATURAL, RegisterProperties.TERMITES_AWAKE, RegisterProperties.CAN_SPAWN_TERMITE);
    }

	@Override
	@NotNull
	public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		boolean isSafe = TermiteManager.isPosSafeForTermites(level, neighborPos, neighborState);
		if (isSafe != state.getValue(RegisterProperties.TERMITES_AWAKE)) {
			state.setValue(RegisterProperties.TERMITES_AWAKE, isSafe);
		}
		if (isSafe != state.getValue(RegisterProperties.CAN_SPAWN_TERMITE)) {
			state.setValue(RegisterProperties.CAN_SPAWN_TERMITE, isSafe);
		}
		return state;
	}

	@Override
	public void onPlace(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
		level.scheduleTick(pos, this, level.random.nextInt(40, 200));
	}

	@Override
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, BlockState newState, boolean isMoving) {
		if (!state.is(newState.getBlock())) {
			if (level.getBlockEntity(pos) instanceof TermiteMoundBlockEntity termiteMoundBlockEntity) {
				termiteMoundBlockEntity.termiteManager.clearTermites(level);
			}
		}
		super.onRemove(state, level, pos, newState, isMoving);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		boolean areTermitesSafe = TermiteManager.areTermitesSafe(level, pos);
		boolean canAwaken = canTermitesWaken(level, pos) && areTermitesSafe;
		if (canAwaken != state.getValue(RegisterProperties.TERMITES_AWAKE)) {
			level.setBlock(pos, state.setValue(RegisterProperties.TERMITES_AWAKE, canAwaken), 3);
		}
		if (areTermitesSafe != state.getValue(RegisterProperties.CAN_SPAWN_TERMITE)) {
			level.setBlock(pos, state.setValue(RegisterProperties.CAN_SPAWN_TERMITE, areTermitesSafe), 3);
		}
		level.scheduleTick(pos, this, random.nextInt(90, 150));
	}

	public static boolean canTermitesWaken(@NotNull Level level, @NotNull BlockPos pos) {
		return !shouldTermitesSleep(level, getLightLevel(level, pos));
	}

	public static boolean shouldTermitesSleep(Level level, int light) {
		return level.isNight() && light < 7;
	}

	public static int getLightLevel(Level level, BlockPos blockPos) {
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		int finalLight = 0;
		for (Direction direction : Direction.values()) {
			mutableBlockPos.move(direction);
			int newLight = !level.isRaining() ? level.getMaxLocalRawBrightness(mutableBlockPos) : level.getBrightness(LightLayer.BLOCK, mutableBlockPos);
			finalLight = Math.max(finalLight, newLight);
			mutableBlockPos.move(direction, -1);
		}
		return finalLight;
	}

	@Override
	@NotNull
    public RenderShape getRenderShape(@NotNull BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.TERMITE_MOUND, (worldx, pos, statex, blockEntity) -> blockEntity.tick(worldx, pos, statex.getValue(RegisterProperties.NATURAL), statex.getValue(RegisterProperties.TERMITES_AWAKE), statex.getValue(RegisterProperties.CAN_SPAWN_TERMITE))) : null;
    }
}
