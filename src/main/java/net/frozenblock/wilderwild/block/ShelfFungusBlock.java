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
import net.frozenblock.wilderwild.block.impl.SnowloggingUtils;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
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
import net.minecraft.world.level.block.entity.BlockEntity;
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
public class ShelfFungusBlock extends FaceAttachedHorizontalDirectionalBlock implements SimpleWaterloggedBlock, BonemealableBlock {
	public static final int GROWTH_BRIGHTNESS_OFFSET = 2;
	public static final int MAX_STAGE = 4;
	public static final int MAX_AGE = 2;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	public static final IntegerProperty STAGE = RegisterProperties.FUNGUS_STAGE;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
	public static final MapCodec<ShelfFungusBlock> CODEC = simpleCodec(ShelfFungusBlock::new);
	protected static final VoxelShape NORTH_WALL_SHAPE = Block.box(0D, 0D, 13D, 16D, 16D, 16D);
	protected static final VoxelShape SOUTH_WALL_SHAPE = Block.box(0D, 0D, 0D, 16D, 16D, 3D);
	protected static final VoxelShape WEST_WALL_SHAPE = Block.box(13D, 0D, 0D, 16D, 16D, 16D);
	protected static final VoxelShape EAST_WALL_SHAPE = Block.box(0D, 0D, 0D, 3D, 16D, 16D);
	protected static final VoxelShape FLOOR_SHAPE = Block.box(0D, 0D, 0D, 16D, 3D, 16D);
	protected static final VoxelShape CEILING_SHAPE = Block.box(0D, 13D, 0D, 16D, 16D, 16D);

	public ShelfFungusBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().
			setValue(FACING, Direction.NORTH)
			.setValue(WATERLOGGED, false)
			.setValue(FACE, AttachFace.WALL)
			.setValue(STAGE, 1)
		);
	}

	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return super.isRandomlyTicking(state) || SnowloggingUtils.isSnowlogged(state);
	}

	@NotNull
	public static AttachFace getFace(@NotNull Direction direction) {
		if (direction.getAxis() == Direction.Axis.Y) {
			return direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR;
		}
		return AttachFace.WALL;
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(STAGE) == MAX_STAGE;
	}

	@NotNull
	@Override
	protected MapCodec<? extends ShelfFungusBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.is(Items.SHEARS) && shear(level, pos, state, player)) {
			stack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
	}

	public static boolean shear(Level level, BlockPos pos, @NotNull BlockState state, @Nullable Entity entity) {
		int stage = state.getValue(STAGE);
		if (stage > 1) {
			popResource(level, pos, new ItemStack(state.getBlock()));
			level.setBlockAndUpdate(pos, state.setValue(STAGE, stage - 1));
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
		builder.add(FACE, FACING, AGE, STAGE, WATERLOGGED);
		if (BlockConfig.get().snowlogging.snowlogging) {
			builder.add(SnowloggingUtils.SNOW_LAYERS);
		}
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.isSecondaryUseActive() && context.getItemInHand().is(this.asItem()) && state.getValue(STAGE) < MAX_STAGE || super.canBeReplaced(state, context);
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState insideState = context.getLevel().getBlockState(context.getClickedPos());
		if (insideState.is(this)) {
			return insideState.setValue(STAGE, Math.min(MAX_STAGE, insideState.getValue(STAGE) + 1));
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
				return SnowloggingUtils.getSnowPlacementState(blockState, context);
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

	public boolean isMaxAge(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextInt(level.getMaxLocalRawBrightness(pos) + GROWTH_BRIGHTNESS_OFFSET) == 1) {
			if (!isMaxAge(state)) {
				level.setBlock(pos, state.cycle(AGE), UPDATE_CLIENTS);
			} else if (!isFullyGrown(state)) {
				level.setBlock(pos, state.cycle(STAGE).setValue(AGE, 0), UPDATE_CLIENTS);
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos, state.cycle(STAGE).setValue(AGE, 0), UPDATE_CLIENTS);
	}

	@Override
	public void destroy(LevelAccessor level, BlockPos pos, BlockState state) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			super.destroy(level, pos, SnowloggingUtils.getSnowEquivalent(state));
		} else {
			super.destroy(level, pos, state);
		}
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (player.hasCorrectToolForDrops(snowEquivalent)) {
				super.playerDestroy(level, player, pos, snowEquivalent, blockEntity, stack);
			}
		} else {
			super.playerDestroy(level, player, pos, state, blockEntity, stack);
		}
	}
}
