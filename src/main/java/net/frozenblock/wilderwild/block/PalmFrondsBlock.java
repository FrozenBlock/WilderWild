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
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class PalmFrondsBlock extends LeavesBlock implements BonemealableBlock {
	public static final MapCodec<PalmFrondsBlock> CODEC = simpleCodec(PalmFrondsBlock::new);
	public static final int BONEMEAL_DISTANCE = CoconutBlock.VALID_FROND_DISTANCE;

	public PalmFrondsBlock(@NotNull Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	public MapCodec<? extends PalmFrondsBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		return level.getBlockState(pos.below()).isAir() && (state.getValue(DISTANCE) <= BONEMEAL_DISTANCE || state.getValue(PERSISTENT));
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		level.setBlock(pos.below(), CoconutBlock.getDefaultHangingState(), UPDATE_CLIENTS);
	}
}
