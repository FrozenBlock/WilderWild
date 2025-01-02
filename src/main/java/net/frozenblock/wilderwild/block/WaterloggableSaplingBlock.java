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
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterloggableSaplingBlock extends SaplingBlock implements SimpleWaterloggedBlock {
	public static final int WATER_SEARCH_RANGE = 3;
	private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<WaterloggableSaplingBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		TreeGrower.CODEC.fieldOf("tree").forGetter((waterloggableSaplingBlock) -> waterloggableSaplingBlock.treeGrower),
		propertiesCodec()
	).apply(instance, WaterloggableSaplingBlock::new));

	public WaterloggableSaplingBlock(@NotNull TreeGrower grower, @NotNull Properties settings) {
		super(grower, settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(WATERLOGGED, false));
	}

	@NotNull
	@Override
	public MapCodec<? extends WaterloggableSaplingBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(WATERLOGGED);
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState floor, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return super.mayPlaceOn(floor, level, pos) || floor.is(Blocks.CLAY) || floor.is(Blocks.MUD) || floor.is(Blocks.SAND);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockPos.MutableBlockPos mutableBlockPos = pos.mutable();
		boolean canSkip = false;
		for (int i = 0; i < WATER_SEARCH_RANGE + 1; i++) {
			if (!canSkip) {
				if (level.getBlockState(mutableBlockPos.move(Direction.UP)).getFluidState().is(FluidTags.WATER)) {
					if (i == WATER_SEARCH_RANGE) {
						return false;
					}
				} else {
					canSkip = true;
				}
			}
		}
		return super.canSurvive(state, level, pos);
	}

	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx) {
		FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
		boolean bl = fluidState.getType() == Fluids.WATER;
		return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(WATERLOGGED, bl);
	}

	@NotNull
	@Override
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return direction == Direction.UP && !state.canSurvive(level, currentPos) ?
			Blocks.AIR.defaultBlockState()
			: super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@NotNull
	@Override
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}
}
