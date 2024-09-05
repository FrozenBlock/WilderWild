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
import java.util.OptionalInt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class PalmFrondsBlock extends LeavesBlock implements BonemealableBlock {
	public static final int DECAY_DISTANCE = 12;
	public static final MapCodec<PalmFrondsBlock> CODEC = simpleCodec(PalmFrondsBlock::new);
	public static final int BONEMEAL_DISTANCE = 1;

	public PalmFrondsBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(DISTANCE, DECAY_DISTANCE));
	}

	@NotNull
	private static BlockState updateFrondDistanceDistance(@NotNull BlockState state, @NotNull LevelAccessor level, @NotNull BlockPos pos) {
		int i = DECAY_DISTANCE;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		for (Direction direction : Direction.values()) {
			mutableBlockPos.setWithOffset(pos, direction);
			i = Math.min(i, getFrondDistanceAt(level.getBlockState(mutableBlockPos)) + 1);
			if (i == 1) {
				break;
			}
		}

		return state.setValue(DISTANCE, i);
	}

	private static int getFrondDistanceAt(BlockState neighbor) {
		return getOptionalFrondDistanceAt(neighbor).orElse(DECAY_DISTANCE);
	}

	@NotNull
	public static OptionalInt getOptionalFrondDistanceAt(@NotNull BlockState state) {
		if (state.is(BlockTags.LOGS)) return OptionalInt.of(0);
		if (!state.hasProperty(DISTANCE)) return OptionalInt.empty();

		Block block = state.getBlock();
		int distance = state.getValue(DISTANCE);
		if (!(block instanceof PalmFrondsBlock) && distance == LeavesBlock.DECAY_DISTANCE) {
			return OptionalInt.of(DECAY_DISTANCE);
		}
		return OptionalInt.of(distance);
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

	@Override
	public boolean isRandomlyTicking(@NotNull BlockState state) {
		return state.getValue(DISTANCE) == DECAY_DISTANCE && !state.getValue(PERSISTENT);
	}

	@Override
	protected boolean decaying(@NotNull BlockState state) {
		return !state.getValue(PERSISTENT) && state.getValue(DISTANCE) == DECAY_DISTANCE;
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		level.setBlock(pos, updateFrondDistanceDistance(state, level, pos), UPDATE_ALL);
	}

	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
		BlockState blockState = this.defaultBlockState()
			.setValue(PERSISTENT, Boolean.TRUE)
			.setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
		return updateFrondDistanceDistance(blockState, context.getLevel(), context.getClickedPos());
	}
}
