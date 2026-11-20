/*
 * Copyright 2026 FrozenBlock
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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jspecify.annotations.Nullable;

public class MesogleaWallTorchBlock extends WallTorchBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public MesogleaWallTorchBlock(SimpleParticleType mesogleaBubbleParticle, BlockBehaviour.Properties properties) {
		super(mesogleaBubbleParticle, properties);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@Override
	protected boolean propagatesSkylightDown(BlockState state) {
		return state.getFluidState().isEmpty();
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
		MesogleaTorchBlock.tryEvaporateMesogleaTorchOnPlace(level, pos);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess ticks,
		BlockPos pos,
		Direction directionToNeighbour,
		BlockPos neighbourPos,
		BlockState neighbourState,
		RandomSource random
	) {
		if (state.getValue(WATERLOGGED)) ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		return super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
	}

	@Override
	public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockState placedState = super.getStateForPlacement(context);
		if (placedState == null) return null;

		final FluidState replacedFluidState = context.getLevel().getFluidState(context.getClickedPos());
		final boolean isWaterSource = replacedFluidState.isSourceOfType(Fluids.WATER);
		return placedState.setValue(WATERLOGGED, isWaterSource);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		if (random.nextFloat() > 0.1F) return;

		final Direction direction = state.getValue(FACING);
		final double x = pos.getX() + 0.5D;
		final double y = pos.getY() + 0.7D;
		final double z = pos.getZ() + 0.5D;
		Direction opposite = direction.getOpposite();
		level.addParticle(this.flameParticle, x + 0.27D * opposite.getStepX(), y + 0.22D, z + 0.27D * opposite.getStepZ(), 0D, 0D, 0D);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource() : super.getFluidState(state);
	}
}
