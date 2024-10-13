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
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.world.level.block.LevelEvent;
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

@SuppressWarnings("deprecation")
public class WilderBushBlock extends BushBlock implements BonemealableBlock {
	public static final int GROWTH_CHANCE = 7;
	public static final float ALMOST_FULLY_GROWN_GROWTH_CHANCE = 0.75F;
	public static final float BONEMEAL_SUCCESS_CHANCE = 0.65F;
	public static final float ALMOST_FULLY_GROWN_BONEMEAL_SUCCESS_CHANCE = 0.45F;
	public static final int MAX_AGE = 2;
	public static final int ALMOST_MAX_AGE = 1;
	public static final int MIN_AGE = 0;
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;
	private static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final MapCodec<WilderBushBlock> CODEC = simpleCodec(WilderBushBlock::new);

	public WilderBushBlock(@NotNull BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, MIN_AGE).setValue(HALF, DoubleBlockHalf.LOWER));
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == MAX_AGE;
	}

	public static boolean isAlmostFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == ALMOST_MAX_AGE;
	}

	public static boolean isMinimumAge(@NotNull BlockState state) {
		return state.getValue(AGE) == MIN_AGE;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(HALF) == DoubleBlockHalf.LOWER;
	}

	private static void preventDropFromBottomPart(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
		BlockPos blockPos;
		BlockState blockState;
		DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
		if (doubleBlockHalf == DoubleBlockHalf.UPPER
			&& (blockState = level.getBlockState(blockPos = pos.below())).is(state.getBlock())
			&& blockState.getValue(HALF) == DoubleBlockHalf.LOWER
		) {
			BlockState setState = blockState.hasProperty(BlockStateProperties.WATERLOGGED) &&
				blockState.getValue(BlockStateProperties.WATERLOGGED) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();

			if (setState.isAir() && setState.getFluidState().isEmpty() && SnowloggingUtils.isSnowlogged(state)) {
				setState = SnowloggingUtils.getSnowEquivalent(state);
			}

			level.setBlock(blockPos, setState, 35);
			level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
		}
	}

	@NotNull
	@Override
	protected MapCodec<? extends WilderBushBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE, HALF);
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		if (isFullyGrown(state)) {
			DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
			if (!(direction.getAxis() != Direction.Axis.Y || doubleBlockHalf == DoubleBlockHalf.LOWER != (direction == Direction.UP) || neighborState.is(this) && neighborState.getValue(HALF) != doubleBlockHalf)) {
				return Blocks.AIR.defaultBlockState();
			}
			if (doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canSurvive(level, pos)) {
				return Blocks.AIR.defaultBlockState();
			}
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}


	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.randomTick(state, level, pos, random);
		if (!isFullyGrown(state) && random.nextInt(GROWTH_CHANCE) == 0 && level.getRawBrightness(pos, 0) >= 9) {
			if (isAlmostFullyGrown(state) && random.nextFloat() < ALMOST_FULLY_GROWN_GROWTH_CHANCE) {
				return;
			}
			this.grow(level, state, pos);
		}
	}

	public void grow(@NotNull Level level, @NotNull BlockState state, @NotNull BlockPos pos) {
		BlockState setState = state.cycle(AGE);
		if (isAlmostFullyGrown(state)) {
			BlockPos above = pos.above();
			if (level.getBlockState(above).isAir()) {
				level.setBlock(pos, setState, UPDATE_CLIENTS);
				level.setBlock(above, this.defaultBlockState().setValue(AGE, setState.getValue(AGE)).setValue(HALF, DoubleBlockHalf.UPPER), UPDATE_CLIENTS);
			}
		} else {
			level.setBlock(pos, setState, UPDATE_CLIENTS);
		}
	}

	@Override
	@NotNull
	public ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (stack.is(Items.SHEARS) && onShear(level, pos, state, player)) {
			stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
			return ItemInteractionResult.sidedSuccess(level.isClientSide);
		}
		return super.useItemOn(stack, state, level, pos, player, hand, hit);
	}

	public boolean onShear(Level level, BlockPos pos, @NotNull BlockState state, @Nullable Entity entity) {
		if (!isMinimumAge(state)) {
			if (!level.isClientSide) {
				level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1F, 1F);
				if (isFullyGrown(state)) {
					ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.75D, pos.getZ() + 0.5D, new ItemStack(WWBlocks.BUSH));
					level.addFreshEntity(itemEntity);
				}
				state = this.setAgeOnBothHalves(state, level, pos, state.getValue(AGE) - 1, true);
				level.gameEvent(entity, GameEvent.SHEAR, pos);
				this.removeTopHalfIfYoung(state, level, pos);
			}
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected boolean mayPlaceOn(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos) {
		return state.is(WWBlockTags.BUSH_MAY_PLACE_ON) || super.mayPlaceOn(state, level, pos);
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
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return isMinimumAge(state) || (isAlmostFullyGrown(state) && isLower(state) && level.getBlockState(pos.above()).isAir()) || isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isAlmostFullyGrown(state) ? level.getRandom().nextFloat() < BONEMEAL_SUCCESS_CHANCE : level.getRandom().nextFloat() < ALMOST_FULLY_GROWN_BONEMEAL_SUCCESS_CHANCE;
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
	@NotNull
	public BlockState playerWillDestroy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
		if (!level.isClientSide && !SnowloggingUtils.isSnowlogged(state)) {
			boolean creative = player.isCreative();
			boolean canContinue = true;
			if (!creative) {
				if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
					BlockPos belowPos = pos.below();
					BlockState belowState = level.getBlockState(belowPos);
					if (SnowloggingUtils.isSnowlogged(belowState)) {
						Block.dropResources(state.setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), level, pos, null, player, player.getMainHandItem());
						canContinue = false;
					}
				}
			}

			if (canContinue && isFullyGrown(state)) {
				if (creative) {
					try {
						preventDropFromBottomPart(level, pos, state, player);
					} catch (IllegalArgumentException e) {
						Block.dropResources(state, level, pos, null, player, player.getMainHandItem());
					}
				} else {
					Block.dropResources(state, level, pos, null, player, player.getMainHandItem());
				}
			}
		}
		return super.playerWillDestroy(level, pos, state, player);
	}

	@Override
	public void playerDestroy(@NotNull Level level, @NotNull Player player, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable BlockEntity blockEntity, @NotNull ItemStack stack) {
		if (SnowloggingUtils.isSnowlogged(state)) {
			BlockState snowEquivalent = SnowloggingUtils.getSnowEquivalent(state);
			if (player.hasCorrectToolForDrops(snowEquivalent)) {
				super.playerDestroy(level, player, pos, snowEquivalent, blockEntity, stack);
			}
		} else {
			super.playerDestroy(level, player, pos, isFullyGrown(state) ? Blocks.AIR.defaultBlockState() : state, blockEntity, stack);
		}
	}

	@NotNull
	public BlockState setAgeOnBothHalves(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age, boolean particles) {
		BlockState setState = state.setValue(BlockStateProperties.AGE_2, age);
		level.setBlockAndUpdate(pos, setState);
		BlockPos movedPos = state.getValue(HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this)) {
			level.setBlockAndUpdate(movedPos, secondState.setValue(BlockStateProperties.AGE_2, age));
			if (particles) {
				level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, movedPos, Block.getId(secondState));
			}
		}
		if (particles) {
			level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
		}
		return setState;
	}

	public void removeTopHalfIfYoung(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		if (state.is(this) && !isLower(state) && !isFullyGrown(state)) {
			level.setBlock(pos, level.getFluidState(pos).createLegacyBlock(), UPDATE_ALL);
			return;
		}
		BlockPos movedPos = pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this) && !isLower(secondState) && !isFullyGrown(secondState)) {
			level.setBlock(movedPos, level.getFluidState(movedPos).createLegacyBlock(), UPDATE_ALL);
		}
	}
}
