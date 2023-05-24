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

import net.frozenblock.wilderwild.misc.server.EasyPacket;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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

public class MilkweedBlock extends TallFlowerBlock {
	private static final int MAX_AGE = 3;

    public MilkweedBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BlockStateProperties.AGE_3);
    }

	public static boolean isFullyGrown(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.AGE_3) == MAX_AGE;
	}

	public static boolean isLower(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER;
	}

    @Override
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, RandomSource random) {
        if (random.nextFloat() > 0.83F && isLower(state) && !isFullyGrown(state)) {
				this.setAgeOnBothHalves(state, level, pos, state.getValue(BlockStateProperties.AGE_3) + 1);

        }
    }

    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
		if (isFullyGrown(state)) {
			ItemStack itemStack = player.getItemInHand(hand);
			if (!level.isClientSide) {
				if (itemStack.is(Items.SHEARS)) {
					ItemStack stack = new ItemStack(RegisterItems.MILKWEED_POD);
					stack.setCount(level.random.nextIntBetweenInclusive(2, 7));
					popResource(level, pos, stack);
					level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.GROWING_PLANT_CROP, SoundSource.BLOCKS, 1.0F, 1.0F);
					itemStack.hurtAndBreak(1, player, (playerx) -> playerx.broadcastBreakEvent(hand));
					level.gameEvent(player, GameEvent.SHEAR, pos);
				} else {
					EasyPacket.EasySeedPacket.createParticle(level, Vec3.atCenterOf(pos).add(0, 0.3, 0), level.random.nextIntBetweenInclusive(14, 28), true);
				}
				this.setAgeOnBothHalves(state, level, pos, 0);
			}
			return InteractionResult.SUCCESS;
		}
        return super.use(state, level, pos, player, hand, hit);
    }

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (isLower(state) && !isFullyGrown(state)) {
			this.setAgeOnBothHalves(state, level, pos, state.getValue(BlockStateProperties.AGE_3) + 1);
			return;
		}
		Block.popResource(level, pos, new ItemStack(this));
	}

	public void setAgeOnBothHalves(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int age) {
		if (age > MAX_AGE) return;
		level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.AGE_3, age));
		BlockPos movedPos = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos.above();
		BlockState secondState = level.getBlockState(movedPos);
		if (secondState.is(this)) {
			level.setBlockAndUpdate(movedPos, secondState.setValue(BlockStateProperties.AGE_3, age));
		}
	}
}
