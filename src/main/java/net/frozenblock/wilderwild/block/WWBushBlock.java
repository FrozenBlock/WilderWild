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

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WWBushBlock extends BushBlock implements BonemealableBlock {
	private static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	private static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

	public WWBushBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE, HALF);
	}

	@Override
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos neighborPos) {
		if (isFullyGrown(state)) {
			DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
			if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf)) {
				return Blocks.AIR.defaultBlockState();
			}
			if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, currentPos)) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 2;
	}

	public static boolean isAlmostFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 1;
	}

	public static boolean isMinimumAge(@NotNull BlockState state) {
		return state.getValue(AGE) == 0;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!isFullyGrown(state) && random.nextInt(5) == 0 && level.getRawBrightness(pos, 0) >= 9) {
			if (isAlmostFullyGrown(state) && random.nextFloat() < 0.75F) {
				return;
			}
			this.grow(level, state, pos);
		}
	}

	public boolean grow(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos) {
		BlockState setState = state.cycle(AGE);
		if (isAlmostFullyGrown(state)) {
			BlockPos above = pos.above();
			if (level.getBlockState(above).isAir()) {
				level.setBlock(pos, setState, 2);
				level.setBlock(above, setState.setValue(HALF, DoubleBlockHalf.UPPER), 2);
				return true;
			}
		} else {
			level.setBlock(pos, setState, 2);
			return true;
		}
		return false;
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS) && !isMinimumAge(state)) {
			if (!level.isClientSide) {
				level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
				if (isFullyGrown(state)) {
					ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5, new ItemStack(RegisterBlocks.BUSH));
					level.addFreshEntity(itemEntity);
				}
				state = this.setAgeOnBothHalves(state, level, pos, state.getValue(AGE) - 1, true);
				itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
				level.gameEvent(player, GameEvent.SHEAR, pos);
				this.removeTopHalfIfYoung(state, level, pos);
			}
			return InteractionResult.sidedSuccess(level.isClientSide);
		} else {
			return super.use(state, level, pos, player, hand, hit);
		}
	}

	@Override
	protected boolean mayPlaceOn(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(WilderBlockTags.BUSH_MAY_PLACE_ON) || super.mayPlaceOn(state, level, pos);
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockPos blockPos = pos.below();
		if (state.is(this) && !isLower(state)) {
			BlockState otherState = level.getBlockState(blockPos);
			return otherState.is(this) && isLower(otherState) && isFullyGrown(otherState) && isFullyGrown(state);
		}
		return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return isMinimumAge(state) || (isAlmostFullyGrown(state) && isLower(state) && level.getBlockState(pos.above()).isAir()) || isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isAlmostFullyGrown(state) ? level.random.nextFloat() < 0.65 : level.random.nextFloat() < 0.45;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (isFullyGrown(state)) {
			Block.popResource(level, pos, new ItemStack(this));
		} else {
			this.grow(level, state, pos);
		}
	}

	@Override
	public long getSeed(@NotNull BlockState state, @NotNull BlockPos pos) {
		try {
			return isFullyGrown(state) ? Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ()) : super.getSeed(state, pos);
		} catch (IllegalArgumentException e) {
			return super.getSeed(state, pos);
		}
	}

	@Override
	public void playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (isFullyGrown(state) && (!level.isClientSide)) {
				if (player.isCreative()) {
					try {
						preventCreativeDropFromBottomPart(level, pos, state, player);
					} catch (IllegalArgumentException e) {
						Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
					}
				} else {
					Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, player.getMainHandItem());
				}

		}
		super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack tool) {
		if (isFullyGrown(state)) {
			super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, tool);
		} else {
			super.playerDestroy(level, player, pos, state, blockEntity, tool);
		}
	}

	private static void preventCreativeDropFromBottomPart(Level level, BlockPos pos, BlockState state, Player player) {
		BlockPos blockPos;
		BlockState blockState;
		DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
		if (doubleBlockHalf == DoubleBlockHalf.UPPER
			&& (blockState = level.getBlockState(blockPos = pos.below())).is(state.getBlock())
			&& blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
			BlockState blockState2 = blockState.hasProperty(BlockStateProperties.WATERLOGGED) && Boolean.TRUE.equals(blockState.getValue(BlockStateProperties.WATERLOGGED)) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();

			level.setBlock(blockPos, blockState2, 35);
			level.levelEvent(player, 2001, blockPos, Block.getId(blockState));
		}
	}

	public BlockState setAgeOnBothHalves(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age, boolean particles) {
		BlockState setState = state.setValue(BlockStateProperties.AGE_2, age);
		level.setBlockAndUpdate(pos, setState);
		BlockPos movedPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this)) {
			level.setBlockAndUpdate(movedPos, secondState.setValue(BlockStateProperties.AGE_2, age));
			if (particles) {
				level.levelEvent(2001, movedPos, Block.getId(secondState));
			}
		}
		if (particles) {
			level.levelEvent(2001, pos, Block.getId(state));
		}
		return setState;
	}

	public void removeTopHalfIfYoung(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		if (state.is(this) && !isLower(state) && !isFullyGrown(state)) {
			level.setBlock(pos, level.getFluidState(pos).createLegacyBlock(), 3);
			return;
		}
		BlockPos movedPos = pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this) && !isLower(secondState) && !isFullyGrown(secondState)) {
			level.setBlock(movedPos, level.getFluidState(movedPos).createLegacyBlock(), 3);
		}
	}
}
