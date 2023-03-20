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
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WaterloggableSaplingBlock extends SaplingBlock implements SimpleWaterloggedBlock {
    private static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterloggableSaplingBlock(AbstractTreeGrower generator, Properties settings) {
        super(generator, settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, 0).setValue(WATERLOGGED, false));
    }

	@Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, net.minecraft.world.level.block.state.BlockState> builder) {
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
		for (int i = 0; i < 3; i++) {
			if (!canSkip) {
				if (level.getBlockState(mutableBlockPos.move(Direction.UP)).getFluidState().is(FluidTags.WATER)) {
					if (i == 2) {
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
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        boolean bl = fluidState.getType() == Fluids.WATER;
        return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(WATERLOGGED, bl);
    }

	@Override
    public BlockState updateShape(BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return direction == Direction.UP && !state.canSurvive(level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

	@Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }
}
