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
import net.frozenblock.wilderwild.block.entity.GeyserBlockEntity;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GeyserBlock extends BaseEntityBlock {
	public static final MapCodec<GeyserBlock> CODEC = simpleCodec(GeyserBlock::new);
	public static final int MIN_PLACEMENT_TICK_DELAY = 5;
	public static final int MAX_PLACEMENT_TICK_DELAY = 20;

	public GeyserBlock(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	protected MapCodec<? extends GeyserBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new GeyserBlockEntity(pos, state);
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(RegisterProperties.ACTIVE);
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (direction.equals(Direction.UP)) {
			boolean isSafe = canBeActive(level, neighborPos);
			if (isSafe != state.getValue(RegisterProperties.ACTIVE)) {
				state = state.setValue(RegisterProperties.ACTIVE, isSafe);
			}
		}
		return state;
	}

	@Override
	public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean isMoving) {
		level.scheduleTick(pos, this, level.random.nextInt(MIN_PLACEMENT_TICK_DELAY, MAX_PLACEMENT_TICK_DELAY));
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		super.onRemove(state, level, pos, newState, isMoving);
	}

	public static boolean canBeActive(@NotNull LevelAccessor level, @NotNull BlockPos otherBlockPos) {
		BlockState state = level.getBlockState(otherBlockPos);
		return state.is(Blocks.WATER) || state.is(Blocks.LAVA) || state.isAir();
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		boolean canBeActive = canBeActive(level, pos.above());
		if (canBeActive != state.getValue(RegisterProperties.ACTIVE)) {
			level.setBlock(pos, state.setValue(RegisterProperties.ACTIVE, canBeActive), UPDATE_ALL);
		}
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		if (blockState.getValue(RegisterProperties.ACTIVE)) {
			level.addAlwaysVisibleParticle(
				ParticleTypes.WHITE_SMOKE,
				true,
				(double) blockPos.getX() + 0.5D + randomSource.nextDouble() / 3D * (randomSource.nextBoolean() ? 1D : -1D),
				blockPos.getY() + 1D,
				(double) blockPos.getZ() + 0.5D + randomSource.nextDouble() / 3D * (randomSource.nextBoolean() ? 1D : -1D),
				0D,
				(randomSource.nextFloat() * 0.004D) + 0.003D,
				0D
			);
		}
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickServer(worldx, pos, statex.getValue(RegisterProperties.ACTIVE)))
			: createTickerHelper(type, RegisterBlockEntities.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickClient(worldx, pos, statex.getValue(RegisterProperties.ACTIVE)));
	}

	@Override
	protected boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}
}
