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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class SpongeBudBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	public static final float BONEMEAL_SUCCESS_CHANCE = 0.65F;
	public static final int MAX_AGE = 2;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<SpongeBudBlock> CODEC = simpleCodec(SpongeBudBlock::new);
	protected static final VoxelShape NORTH_WALL_SHAPE = Block.box(0D, 0D, 13D, 16D, 16D, 16D);
	protected static final VoxelShape SOUTH_WALL_SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 3D);
	protected static final VoxelShape WEST_WALL_SHAPE = Block.box(13D, 0D, 0D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_WALL_SHAPE = Block.box(0D, 0D, 0D, 3D, 16D, 16D);
	protected static final VoxelShape FLOOR_SHAPE = Block.box(0D, 0D, 0D, 16D, 3D, 16D);
	protected static final VoxelShape CEILING_SHAPE = Block.box(0D, 13D, 0D, 16D, 16D, 16D);

	public SpongeBudBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any()
			.setValue(FACING, Direction.NORTH)
			.setValue(WATERLOGGED, false)
			.setValue(FACE, AttachFace.WALL)
			.setValue(AGE, 0)
		);
	}

	@NotNull
	@Override
	protected MapCodec<? extends SpongeBudBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public ItemInteractionResult useItemOn(
		@NotNull ItemStack stack,
		@NotNull BlockState state,
		@NotNull Level level,
		@NotNull BlockPos pos,
		@NotNull Player player,
		@NotNull InteractionHand hand,
		@NotNull BlockHitResult hit
	) {
		if (stack.is(Items.SHEARS) && onShear(level, pos, state, player)) {
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.useItemOn(stack, state, level, pos, player, hand, hit);
		}
	}

	public static boolean onShear(Level level, BlockPos pos, @NotNull BlockState state, @Nullable Entity entity) {
		int age = state.getValue(AGE);
		if (age > 0) {
			popResource(level, pos, new ItemStack(state.getBlock()));
			level.setBlockAndUpdate(pos, state.setValue(AGE, age - 1));
			level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
			level.gameEvent(entity, GameEvent.SHEAR, pos);
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(FACE, FACING, AGE, WATERLOGGED);
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(AGE) < MAX_AGE || super.canBeReplaced(state, context);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState insideState = context.getLevel().getBlockState(context.getClickedPos());
		if (insideState.is(this)) {
			return insideState.setValue(AGE, Math.min(MAX_AGE, insideState.getValue(AGE) + 1));
		}
		boolean waterlogged = insideState.hasProperty(BlockStateProperties.WATERLOGGED) ? insideState.getValue(BlockStateProperties.WATERLOGGED) : false;
		if (!waterlogged) {
			waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
		}
		for (Direction direction : context.getNearestLookingDirections()) {
			BlockState blockState;
			if (direction.getAxis() == Direction.Axis.Y) {
				blockState = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, waterlogged);
			} else {
				blockState = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite()).setValue(WATERLOGGED, waterlogged);
			}

			if (blockState.canSurvive(context.getLevel(), context.getClickedPos())) {
				return blockState;
			}
		}
		return null;
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return switch (state.getValue(FACE)) {
			case FLOOR -> FLOOR_SHAPE;
			case WALL -> switch (state.getValue(FACING)) {
				case EAST -> EAST_WALL_SHAPE;
				case WEST -> WEST_WALL_SHAPE;
				case SOUTH -> SOUTH_WALL_SHAPE;
				default -> NORTH_WALL_SHAPE;
			};
			case CEILING -> CEILING_SHAPE;
		};
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return state.getValue(AGE) < MAX_AGE;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return random.nextFloat() < BONEMEAL_SUCCESS_CHANCE;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
	}
}
