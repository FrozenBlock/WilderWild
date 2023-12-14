/*
 * Copyright 2023 FrozenBlock
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

import net.frozenblock.wilderwild.networking.packet.WilderSeedParticlePacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MilkweedBlock extends TallFlowerBlock {
	private static final int MAX_AGE = 3;

	public MilkweedBlock(@NotNull Properties settings) {
		super(settings);
	}

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.AGE_3) == MAX_AGE;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
	}

	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.AGE_3);
	}

	@Override
	public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextFloat() > 0.83F && isLower(state) && !isFullyGrown(state)) {
			setAgeOnBothHalves(this, state, level, pos, state.getValue(BlockStateProperties.AGE_3) + 1);
		}
	}

	@Override
	@NotNull
	public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (isFullyGrown(state)) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (!level.isClientSide) {
				if (itemStack.is(Items.SHEARS)) {
					itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
					player.awardStat(Stats.ITEM_USED.get(Items.SHEARS));
					shear(level, pos, state, player);
				} else {
					level.playSound(null, player.getX(), player.getY(), player.getZ(), RegisterSounds.BLOCK_MILKWEED_RUSTLE, SoundSource.BLOCKS, 0.8F, 0.9F + (level.random.nextFloat() * 0.15F));
					WilderSeedParticlePacket.sendToAll(level, Vec3.atCenterOf(pos).add(0, 0.3, 0), level.random.nextIntBetweenInclusive(14, 28), true);
					setAgeOnBothHalves(this, state, level, pos, 0);
				}
			}
			return InteractionResult.SUCCESS;
		}
		return super.use(state, level, pos, player, hand, hit);
	}

	public static void shear(@NotNull Level level, BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
		ItemStack stack = new ItemStack(RegisterItems.MILKWEED_POD);
		stack.setCount(level.random.nextIntBetweenInclusive(2, 5));
		popResource(level, pos, stack);
		level.playSound(null, pos, SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
		level.gameEvent(player, GameEvent.SHEAR, pos);
		setAgeOnBothHalves(state.getBlock(), state, level, pos, 0);
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (isLower(state) && !isFullyGrown(state)) {
			setAgeOnBothHalves(this, state, level, pos, state.getValue(BlockStateProperties.AGE_3) + 1);
			return;
		}
		super.performBonemeal(level, random, pos, state);
	}

	public static void setAgeOnBothHalves(Block thisBlock, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age) {
		if (age > MAX_AGE) {
			return;
		}
		level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, age));
		BlockPos movedPos = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(thisBlock)) {
			level.setBlockAndUpdate(movedPos, secondState.setValue(BlockStateProperties.AGE_3, age));
		}
	}
}
