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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class BarnaclesBlock extends MultifaceSpreadeableBlock implements SimpleWaterloggedBlock {
	public static final MapCodec<BarnaclesBlock> CODEC = simpleCodec(BarnaclesBlock::new);
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	private final MultifaceSpreader spreader = new MultifaceSpreader(new MultifaceSpreader.DefaultSpreaderConfig(this));

	public BarnaclesBlock(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	public MapCodec<? extends BarnaclesBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
	}

	@Override
	protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
		if (blockState.getValue(WATERLOGGED))
			scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
	}

	@Override
	protected @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
		return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
	}

	@Override
	@NotNull
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

}
