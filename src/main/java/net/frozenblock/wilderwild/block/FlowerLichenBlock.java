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
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceSpreadeableBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class FlowerLichenBlock extends MultifaceSpreadeableBlock {
	public static final MapCodec<FlowerLichenBlock> CODEC = simpleCodec(FlowerLichenBlock::new);
	private final MultifaceSpreader spreader = new MultifaceSpreader(new FlowerLichenSpreaderConfig());

	public FlowerLichenBlock(@NotNull Properties settings) {
		super(settings);
	}

	public static boolean canAttachToNoWater(@NotNull BlockGetter level, @NotNull Direction direction, @NotNull BlockPos pos, @NotNull BlockState state) {
		return Block.isFaceFull(state.getBlockSupportShape(level, pos), direction.getOpposite())
			|| Block.isFaceFull(state.getCollisionShape(level, pos), direction.getOpposite())
			&& !level.getBlockState(pos).is(Blocks.WATER);
	}

	@NotNull
	@Override
	public MapCodec<? extends FlowerLichenBlock> codec() {
		return CODEC;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		boolean bl = false;
		if (level.getBlockState(pos).is(Blocks.WATER)) {
			return false;
		}
		for (Direction direction : DIRECTIONS) {
			if (hasFace(state, direction)) {
				BlockPos blockPos = pos.relative(direction);
				if (!canAttachToNoWater(level, direction, blockPos, level.getBlockState(blockPos))) {
					return false;
				}
				bl = true;
			}
		}
		return bl;
	}

	@Override
	public boolean canBeReplaced(@NotNull BlockState state, @NotNull BlockPlaceContext context) {
		return !context.getItemInHand().is(state.getBlock().asItem()) || super.canBeReplaced(state, context);
	}

	@Override
	public boolean isValidStateForPlacement(BlockGetter view, @NotNull BlockState state, BlockPos pos, Direction dir) {
		if (!state.getFluidState().isEmpty()) return false;
		return super.isValidStateForPlacement(view, state, pos, dir);
	}

	@Override
	@NotNull
	public MultifaceSpreader getSpreader() {
		return this.spreader;
	}

	public class FlowerLichenSpreaderConfig extends MultifaceSpreader.DefaultSpreaderConfig {
		public FlowerLichenSpreaderConfig() {
			super(FlowerLichenBlock.this);
		}

		@Override
		public boolean stateCanBeReplaced(BlockGetter view, BlockPos posA, BlockPos posB, Direction dir, @NotNull BlockState state) {
			return state.getFluidState().isEmpty() && super.stateCanBeReplaced(view, posA, posB, dir, state);
		}
	}
}
