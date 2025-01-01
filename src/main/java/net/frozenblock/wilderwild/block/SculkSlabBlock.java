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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class SculkSlabBlock extends SlabBlock implements SculkBehaviour {
	public static final MapCodec<SculkSlabBlock> CODEC = simpleCodec(SculkSlabBlock::new);
	private static final IntProvider EXPERIENCE = ConstantInt.of(1);

	public SculkSlabBlock(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	public MapCodec<? extends SculkSlabBlock> codec() {
		return CODEC;
	}

	@Override
	public void spawnAfterBreak(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull ItemStack stack, boolean dropExperience) {
		super.spawnAfterBreak(state, level, pos, stack, dropExperience);
		if (dropExperience) {
			this.tryDropExperience(level, pos, stack, EXPERIENCE);
		}
	}

	@Override
	public int attemptUseCharge(@NotNull SculkSpreader.ChargeCursor cursor, @NotNull LevelAccessor level, @NotNull BlockPos catalystPos, @NotNull RandomSource random, @NotNull SculkSpreader spreader, boolean shouldConvertToBlock) {
		BlockPos cursorPos = cursor.getPos();
		BlockState blockState = level.getBlockState(cursor.getPos());
		if ((blockState.isFaceSturdy(level, cursorPos, Direction.UP) || blockState.isFaceSturdy(level, cursorPos, Direction.DOWN)) && Blocks.SCULK instanceof SculkBlock sculkBlock) {
			return sculkBlock.attemptUseCharge(cursor, level, catalystPos, random, spreader, shouldConvertToBlock);
		}
		int i = cursor.getCharge();
		if (i != 0 && random.nextInt(spreader.chargeDecayRate()) == 0) {
			return random.nextInt(spreader.additionalDecayRate()) != 0 ? i : i - (cursorPos.closerThan(catalystPos, spreader.noGrowthRadius()) ? 1 : SculkBlock.getDecayPenalty(spreader, cursorPos, catalystPos, i));
		}
		return i;
	}
}
