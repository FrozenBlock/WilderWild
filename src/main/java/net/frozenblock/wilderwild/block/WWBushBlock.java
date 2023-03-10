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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class WWBushBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

	public WWBushBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.LOWER));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE, BlockStateProperties.DOUBLE_BLOCK_HALF);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 2;
	}

	public static boolean isAlmostFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 1;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!isFullyGrown(state) && random.nextInt(5) == 0 && level.getRawBrightness(pos, 0) >= 9) {
			if (isAlmostFullyGrown(state) && random.nextFloat() < 0.65F) {
				return;
			}
			this.grow(level, state, pos);
		}
	}

	public boolean grow(Level level, BlockState state, BlockPos pos) {
		state = state.cycle(AGE);
		if (isAlmostFullyGrown(state)) {
			BlockPos above = pos.above();
			if (level.getBlockState(above).isAir()) {
				level.setBlock(pos, state, 2);
				level.setBlock(above, state.setValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER), 2);
				return true;
			}
		} else {
			level.setBlock(pos, state, 2);
			return true;
		}
		return false;
	}

	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS) && isFullyGrown(state)) {
			if (!level.isClientSide) {
				level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
				ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5,new ItemStack(RegisterBlocks.BUSH));
				level.addFreshEntity(itemEntity);
				this.setAgeOnBothHalves(state, level, pos, 0, true);
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
		if (!isLower(state)) {
			BlockState otherState = level.getBlockState(blockPos);
			return otherState.is(this) && isLower(otherState) && isFullyGrown(otherState);
		}
		return this.mayPlaceOn(level.getBlockState(blockPos), level, blockPos);
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return state.getValue(AGE) == 0 || (isAlmostFullyGrown(state) && isLower(state) && level.getBlockState(pos.above()).isAir());
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isAlmostFullyGrown(state) ? (double)level.random.nextFloat() < 0.65 : (double)level.random.nextFloat() < 0.45;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		this.grow(level, state, pos);
	}

	public void setAgeOnBothHalves(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age, boolean particles) {
		level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_2, age));
		BlockPos movedPos = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
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
	}

	public void removeTopHalfIfYoung(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		if (state.is(this) && state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER && !isFullyGrown(state)) {
			level.setBlock(pos, level.getFluidState(pos).createLegacyBlock(), 3);
			return;
		}
		BlockPos movedPos = pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this) && secondState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER && !isFullyGrown(secondState)) {
			level.setBlock(movedPos, level.getFluidState(movedPos).createLegacyBlock(), 3);
		}
	}
}
