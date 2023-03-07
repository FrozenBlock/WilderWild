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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class WWBushBlock extends BushBlock implements BonemealableBlock {
	public static final IntegerProperty AGE = BlockStateProperties.AGE_2;

	public WWBushBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AGE);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 2;
	}

	public static boolean isAlmostFullyGrown(@NotNull BlockState state) {
		return state.getValue(AGE) == 1;
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!isFullyGrown(state) && random.nextInt(5) == 0 && level.getRawBrightness(pos, 0) >= 9) {
			if (isAlmostFullyGrown(state) && random.nextFloat() < 0.65F) {
				return;
			}
			level.setBlock(pos, state.cycle(AGE), 2);
		}
	}

	@Override
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		ItemStack itemStack = player.getItemInHand(hand);
		if (itemStack.is(Items.SHEARS) && isFullyGrown(state)) {
			if (!level.isClientSide) {
				level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
				ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.75, pos.getZ() + 0.5,new ItemStack(RegisterBlocks.BUSH));
				level.addFreshEntity(itemEntity);
				level.levelEvent(2001, pos, Block.getId(state));
				level.setBlockAndUpdate(pos, state.setValue(AGE, 0));
				itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
				level.gameEvent(player, GameEvent.SHEAR, pos);
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
	public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		return !isFullyGrown(state);
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return !isAlmostFullyGrown(state) ? (double)level.random.nextFloat() < 0.65 : (double)level.random.nextFloat() < 0.45;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos, state.cycle(AGE), 2);
	}
}
