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
import net.frozenblock.wilderwild.block.state.properties.TubeWormsPart;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TubeWormsBlock extends VegetationBlock implements LiquidBlockContainer {
	public static final EnumProperty<TubeWormsPart> TUBE_WORMS_PART = WWBlockStateProperties.TUBE_WORMS_PART;
	private static final VoxelShape SHAPE = Block.box(2D, 0D, 2D, 14D, 16D, 14D);
	public static final MapCodec<TubeWormsBlock> CODEC = simpleCodec(TubeWormsBlock::new);

	public TubeWormsBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TUBE_WORMS_PART, TubeWormsPart.SINGLE));
	}

	@Override
	public MapCodec<TubeWormsBlock> codec() {
		return CODEC;
	}

	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
		return (state.isFaceSturdy(level, pos, Direction.UP) || state.is(this))
			&& !state.is(Blocks.MAGMA_BLOCK)
			&& !state.is(WWBlocks.GEYSER);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockState state = super.getStateForPlacement(context);
		if (state == null) return null;

		final Level level = context.getLevel();
		final BlockPos pos = context.getClickedPos();
		if (!this.isValidWaterToReplace(level, pos)) return null;

		final BlockState belowState = level.getBlockState(pos.below());
		if (belowState.is(this)) return state.setValue(TUBE_WORMS_PART, TubeWormsPart.TOP);
		return state;
	}

	@Override
	public boolean canSurvive(BlockState blockState, LevelReader level, BlockPos pos) {
		return super.canSurvive(blockState, level, pos) && this.isValidWaterToSurvive(level, pos);
	}

	@Override
	protected void tick(BlockState blockState, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!blockState.canSurvive(level, pos)) level.destroyBlock(pos, true);
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
		if (direction == Direction.DOWN && !state.canSurvive(level, pos)) scheduledTickAccess.scheduleTick(pos, this, 1);
		scheduledTickAccess.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

		if (direction == Direction.UP) {
			final TubeWormsPart tubeWormsPart = state.getValue(TUBE_WORMS_PART);
			final BlockState aboveState = level.getBlockState(pos.above());

			if (tubeWormsPart == TubeWormsPart.TOP) {
				if (aboveState.is(this)) return state.setValue(TUBE_WORMS_PART, TubeWormsPart.MIDDLE);
			} else if (tubeWormsPart == TubeWormsPart.SINGLE) {
				if (aboveState.is(this)) return state.setValue(TUBE_WORMS_PART, TubeWormsPart.BOTTOM);
			} else if (tubeWormsPart == TubeWormsPart.MIDDLE) {
				if (!aboveState.is(this)) return state.setValue(TUBE_WORMS_PART, TubeWormsPart.TOP);
			} if (tubeWormsPart == TubeWormsPart.BOTTOM) {
				if (!aboveState.is(this)) return state.setValue(TUBE_WORMS_PART, TubeWormsPart.SINGLE);
			}
		}
		return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(TUBE_WORMS_PART);
	}

	@Override
	public boolean canPlaceLiquid(@Nullable LivingEntity livingEntity, BlockGetter level, BlockPos pos, BlockState state, Fluid fluid) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor level, BlockPos pos, BlockState state, FluidState fluidState) {
		return false;
	}

	@Override
	protected FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getSource(false);
	}

	private boolean isValidWaterToReplace(LevelReader level, BlockPos pos) {
		final BlockState state = level.getBlockState(pos);
		final FluidState fluidState = state.getFluidState();
		return state.canBeReplaced() && fluidState.is(FluidTags.WATER) && fluidState.getAmount() == FluidState.AMOUNT_FULL;
	}

	private boolean isValidWaterToSurvive(LevelReader level, BlockPos pos) {
		final FluidState fluidState = level.getFluidState(pos);
		return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == FluidState.AMOUNT_FULL;
	}
}
