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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TubeWormsBlock extends BushBlock implements LiquidBlockContainer {
	public static final EnumProperty<TubeWormsPart> TUBE_WORMS_PART = WWBlockStateProperties.TUBE_WORMS_PART;
	private static final VoxelShape SHAPE = Block.box(2D, 0D, 2D, 14D, 16D, 14D);
	public static final MapCodec<TubeWormsBlock> CODEC = simpleCodec(TubeWormsBlock::new);

	public TubeWormsBlock(@NotNull Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(TUBE_WORMS_PART, TubeWormsPart.SINGLE));
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
		return (blockState.isFaceSturdy(blockGetter, blockPos, Direction.UP) || blockState.is(this))
			&& !blockState.is(Blocks.MAGMA_BLOCK)
			&& !blockState.is(WWBlocks.GEYSER);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		BlockState blockState = super.getStateForPlacement(blockPlaceContext);
		if (blockState != null) {
			Level level = blockPlaceContext.getLevel();
			BlockPos blockPos = blockPlaceContext.getClickedPos();
			if (this.isValidWaterToReplace(level, blockPos)) {
				BlockState belowState = level.getBlockState(blockPos.below());
				if (belowState.is(this)) {
					return blockState.setValue(TUBE_WORMS_PART, TubeWormsPart.TOP);
				} else {
					return blockState;
				}
			}
		}

		return null;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState blockState, LevelReader levelReader, BlockPos blockPos) {
		return super.canSurvive(blockState, levelReader, blockPos) && this.isValidWaterToSurvive(levelReader, blockPos);
	}

	@Override
	protected void tick(@NotNull BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		if (!blockState.canSurvive(serverLevel, blockPos)) {
			serverLevel.destroyBlock(blockPos, true);
		}
	}

	@Override
	protected BlockState updateShape(
		BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos blockPos2,
		BlockState blockState2,
		RandomSource randomSource
	) {
		if (direction == Direction.DOWN && !blockState.canSurvive(levelReader, blockPos)) {
			scheduledTickAccess.scheduleTick(blockPos, this, 1);
		}
		scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));

		if (direction == Direction.UP) {
			TubeWormsPart tubeWormsPart = blockState.getValue(TUBE_WORMS_PART);
			BlockState aboveState = levelReader.getBlockState(blockPos.above());

			if (tubeWormsPart == TubeWormsPart.TOP) {
				if (aboveState.is(this)) return blockState.setValue(TUBE_WORMS_PART, TubeWormsPart.MIDDLE);
			} else if (tubeWormsPart == TubeWormsPart.SINGLE) {
				if (aboveState.is(this)) return blockState.setValue(TUBE_WORMS_PART, TubeWormsPart.BOTTOM);
			} else if (tubeWormsPart == TubeWormsPart.MIDDLE) {
				if (!aboveState.is(this)) return blockState.setValue(TUBE_WORMS_PART, TubeWormsPart.TOP);
			} if (tubeWormsPart == TubeWormsPart.BOTTOM) {
				if (!aboveState.is(this)) return blockState.setValue(TUBE_WORMS_PART, TubeWormsPart.SINGLE);
			}
		}
		return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(TUBE_WORMS_PART);
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
