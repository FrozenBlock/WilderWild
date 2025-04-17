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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AuburnMossCarpetBlock extends CarpetBlock implements SimpleWaterloggedBlock {
	public static final MapCodec<AuburnMossCarpetBlock> CODEC = simpleCodec(AuburnMossCarpetBlock::new);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public AuburnMossCarpetBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@NotNull
	@Override
	public MapCodec<? extends AuburnMossCarpetBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		@NotNull Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource randomSource
	) {
		if (blockState.getValue(WATERLOGGED)) scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
	}

	@Override
	public boolean canSurvive(BlockState blockState, @NotNull LevelReader levelReader, @NotNull BlockPos blockPos) {
		boolean canSurvive = super.canSurvive(blockState, levelReader, blockPos);
		if (blockState.getValue(WATERLOGGED)) {
			return canSurvive && !levelReader.getFluidState(blockPos.below()).is(FluidTags.WATER);
		}
		return canSurvive;
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		BlockState state = super.getStateForPlacement(blockPlaceContext);
		if (state != null) return state.setValue(WATERLOGGED, blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos()).getType() == Fluids.WATER);
		return state;
	}

	@Override
	protected @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState blockState) {
		return blockState.getFluidState().isEmpty();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}
}
