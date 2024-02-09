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
import net.frozenblock.wilderwild.block.impl.GeyserType;
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

	public GeyserBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.getStateDefinition().any().setValue(RegisterProperties.GEYSER_TYPE, GeyserType.NONE));
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
		builder.add(RegisterProperties.GEYSER_TYPE);
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (direction.equals(Direction.UP)) {
			GeyserType geyserType = getGeyserTypeForPos(level, currentPos);
			if (geyserType != state.getValue(RegisterProperties.GEYSER_TYPE)) {
				state = state.setValue(RegisterProperties.GEYSER_TYPE, geyserType);
			}
		}
		return state;
	}

	@Override
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		super.onRemove(state, level, pos, newState, isMoving);
	}

	public static GeyserType getGeyserTypeForPos(@NotNull LevelAccessor level, @NotNull BlockPos pos) {
		BlockPos abovePos = pos.above();
		BlockState state = level.getBlockState(abovePos);
		if (state.is(Blocks.WATER)) {
			return GeyserType.WATER;
		} else if (state.is(Blocks.LAVA)) {
			return GeyserType.LAVA;
		} else if (state.isAir()) {
			return GeyserType.AIR;
		}
		return GeyserType.NONE;
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		GeyserType geyserType = getGeyserTypeForPos(level, pos);
		if (geyserType != state.getValue(RegisterProperties.GEYSER_TYPE)) {
			level.setBlock(pos, state.setValue(RegisterProperties.GEYSER_TYPE, geyserType), UPDATE_ALL);
		}
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		GeyserType geyserType = blockState.getValue(RegisterProperties.GEYSER_TYPE);
		if (!isInactive(geyserType)) {
			int count = randomSource.nextInt(2, 5);
			for (int i = 0; i < count; i++) {
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
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState blockState) {
		return RenderShape.MODEL;
	}

	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return !level.isClientSide ? createTickerHelper(type, RegisterBlockEntities.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickServer(worldx, pos, statex.getValue(RegisterProperties.GEYSER_TYPE), worldx.random))
			: createTickerHelper(type, RegisterBlockEntities.GEYSER, (worldx, pos, statex, blockEntity) -> blockEntity.tickClient(worldx, pos, statex.getValue(RegisterProperties.GEYSER_TYPE), worldx.random));
	}

	@Override
	protected boolean isPathfindable(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, PathComputationType pathComputationType) {
		return false;
	}

	public static boolean isInactive(@NotNull BlockState state) {
		return isInactive(state.getValue(RegisterProperties.GEYSER_TYPE));
	}

	public static boolean isInactive(GeyserType geyserType) {
		return geyserType == GeyserType.NONE;
	}
}
