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
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AuburnCreepingMossBlock extends MultifaceSpreadeableBlock implements BonemealableBlock, SimpleWaterloggedBlock {
	public static final MapCodec<AuburnCreepingMossBlock> CODEC = simpleCodec(AuburnCreepingMossBlock::new);
	private final MultifaceSpreader spreader = new MultifaceSpreader(new MultifaceSpreader.DefaultSpreaderConfig(this));

	public AuburnCreepingMossBlock(@NotNull Properties settings) {
		super(settings);
		this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
	}

	@NotNull
	@Override
	public MapCodec<? extends AuburnCreepingMossBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
	}

	@Override
	public boolean propagatesSkylightDown(@NotNull BlockState blockState) {
		return blockState.getFluidState().isEmpty();
	}

	@Override
	@NotNull
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader levelReader, BlockPos blockPos, BlockState blockState) {
		return Direction.stream().anyMatch(direction -> this.spreader.canSpreadInAnyDirection(blockState, levelReader, blockPos, direction.getOpposite()));
	}

	@Override
	public boolean isBonemealSuccess(Level level, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		return true;
	}

	@Override
	public void performBonemeal(ServerLevel serverLevel, RandomSource randomSource, BlockPos blockPos, BlockState blockState) {
		this.spreader.spreadFromRandomFaceTowardRandomDirection(blockState, serverLevel, blockPos, randomSource);
	}
}
