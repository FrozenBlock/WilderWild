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

package net.frozenblock.wilderwild.block.impl;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkBehaviour;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.SculkSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public interface StairsSlabWallSculkBehaviour extends SculkBehaviour {

	default boolean supportsSculkGrowths() {
		return true;
	}

	@Override
	default int attemptUseCharge(
		@NotNull SculkSpreader.ChargeCursor cursor,
		@NotNull LevelAccessor level,
		@NotNull BlockPos catalystPos,
		@NotNull RandomSource random,
		@NotNull SculkSpreader spreader,
		boolean shouldConvertToBlock
	) {
		final BlockPos pos = cursor.getPos();

		if (this.supportsSculkGrowths()) {
			final BlockState state = level.getBlockState(cursor.getPos());
			if ((state.isFaceSturdy(level, pos, Direction.UP) || state.isFaceSturdy(level, pos, Direction.DOWN)) && Blocks.SCULK instanceof SculkBlock sculkBlock) {
				return sculkBlock.attemptUseCharge(cursor, level, catalystPos, random, spreader, shouldConvertToBlock);
			}
		}

		final int charge = cursor.getCharge();
		if (charge != 0 && random.nextInt(spreader.chargeDecayRate()) == 0) {
			if (random.nextInt(spreader.additionalDecayRate()) != 0) return charge;
			return charge - (pos.closerThan(catalystPos, spreader.noGrowthRadius()) ? 1 : SculkBlock.getDecayPenalty(spreader, pos, catalystPos, charge));
		}
		return charge;
	}

}
