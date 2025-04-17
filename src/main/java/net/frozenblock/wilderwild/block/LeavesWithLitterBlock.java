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

package net.frozenblock.wilderwild.block;

import com.mojang.serialization.MapCodec;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class LeavesWithLitterBlock extends LeavesBlock {
	public static final MapCodec<LeavesWithLitterBlock> CODEC = simpleCodec(LeavesWithLitterBlock::new);

	public LeavesWithLitterBlock(Properties properties) {
		super(properties);
	}

	@Override
	public @NotNull MapCodec<? extends LeavesWithLitterBlock> codec() {
		return CODEC;
	}

	@Override
	protected boolean isRandomlyTicking(@NotNull BlockState blockState) {
		return !blockState.getValue(PERSISTENT);
	}

	@Override
	protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
		FallingLeafUtil.onRandomTick(blockState, serverLevel, blockPos, randomSource);
		super.randomTick(blockState, serverLevel, blockPos, randomSource);
	}
}
