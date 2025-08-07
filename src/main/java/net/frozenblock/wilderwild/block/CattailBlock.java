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

import net.frozenblock.lib.block.api.WaterloggableTallFlowerBlock;
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CattailBlock extends WaterloggableTallFlowerBlock {
	public static final BooleanProperty SWAYING = WWBlockStateProperties.SWAYING;

	public CattailBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(SWAYING, false));
	}

	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState blockState = super.getStateForPlacement(context);
		if (blockState == null) return null;

		blockState = blockState.setValue(SWAYING, blockState.getValue(WATERLOGGED));
		return SnowloggingUtils.getSnowPlacementState(blockState, context);
	}

	@Override
	public void setPlacedBy(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull BlockState blockState, LivingEntity livingEntity, ItemStack itemStack) {
		BlockPos abovePos = blockPos.above();
		BlockState topState = copyWaterloggedFrom(level, abovePos, this.defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER))
			.setValue(SWAYING, blockState.getValue(WATERLOGGED));

		level.setBlock(abovePos, topState, UPDATE_ALL);
	}

	@Override
	public @NotNull BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		state = super.updateShape(state, direction, neighborState, world, pos, neighborPos);
		if (state == null || !state.is(this)) return state;

		state = state.setValue(
			SWAYING,
			state.getValue(HALF) == DoubleBlockHalf.UPPER ? world.getFluidState(pos.below()).is(FluidTags.WATER) : state.getValue(WATERLOGGED)
		);
		return state;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return super.mayPlaceOn(blockState, blockGetter, blockPos)
			|| blockState.is(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			|| blockState.is(WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(SWAYING);
	}
}
