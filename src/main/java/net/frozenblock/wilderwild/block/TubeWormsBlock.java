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
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Yes, I know this could easily extend TallSeagrassBlock.
// I'm just being cautious here, in case another mod changes something with that class which will cause unintentional behavior with Sea Anemones.
// Totaly not a copy-pasted message from SeaAnemoneBlock.
// - AViewFromTheTop
public class TubeWormsBlock extends DoublePlantBlock implements LiquidBlockContainer {
	private static final VoxelShape SHAPE = Block.box(2D, 0D, 2D, 14D, 16D, 14D);
	public static final MapCodec<TubeWormsBlock> CODEC = simpleCodec(TubeWormsBlock::new);

	public TubeWormsBlock(@NotNull Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull MapCodec<? extends TubeWormsBlock> codec() {
		return CODEC;
	}

	@Override
	protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
		return SHAPE;
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState blockState, BlockGetter blockGetter, BlockPos blockPos) {
		return blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP) && !blockState.is(Blocks.MAGMA_BLOCK);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		BlockState blockState = super.getStateForPlacement(blockPlaceContext);
		if (blockState != null) {
			Level level = blockPlaceContext.getLevel();
			BlockPos blockPos = blockPlaceContext.getClickedPos();
			if (this.isValidWaterToReplace(level, blockPos) && this.isValidWaterToReplace(level, blockPos.above())) return blockState;
		}

		return null;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		if (blockState.getValue(HALF) == DoubleBlockHalf.UPPER) {
			BlockState belowState = levelReader.getBlockState(blockPos.below());
			return belowState.is(this) && belowState.getValue(HALF) == DoubleBlockHalf.LOWER;
		} else {
			return super.canSurvive(blockState, levelReader, blockPos) && this.isValidWaterToSurvive(levelReader, blockPos);
		}
	}

	@Override
	public boolean canPlaceLiquid(Player player, BlockGetter blockGetter, BlockPos blockPos, BlockState blockState, Fluid fluid) {
		return false;
	}

	@Override
	public boolean placeLiquid(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, FluidState fluidState) {
		return false;
	}

	@Override
	protected @NotNull FluidState getFluidState(@NotNull BlockState blockState) {
		return Fluids.WATER.getSource(false);
	}

	private boolean isValidWaterToReplace(@NotNull LevelReader levelReader, BlockPos blockPos) {
		BlockState blockState = levelReader.getBlockState(blockPos);
		FluidState fluidState = blockState.getFluidState();
		return (blockState.is(Blocks.WATER) || (blockState.canBeReplaced() && fluidState.is(FluidTags.WATER))) && fluidState.getAmount() == 8;
	}

	private boolean isValidWaterToSurvive(@NotNull LevelReader levelReader, BlockPos blockPos) {
		FluidState fluidState = levelReader.getFluidState(blockPos);
		return fluidState.is(FluidTags.WATER) && fluidState.getAmount() == 8;
	}
}
