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

import java.util.Iterator;
import java.util.List;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlgaeBlock extends Block implements BonemealableBlock {
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16, 1.0, 16);

	public AlgaeBlock(@NotNull BlockBehaviour.Properties settings) {
		super(settings);
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		FluidState fluidState = level.getFluidState(pos.below());
		FluidState fluidState2 = level.getFluidState(pos);
		return fluidState.getType() == Fluids.WATER && fluidState2.getType() == Fluids.EMPTY;
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		return !this.canSurvive(state, level, pos)
			? Blocks.AIR.defaultBlockState()
			: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, false);
		}
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
			level.destroyBlock(pos, false);
		}
		if (!entity.getType().is(WilderEntityTags.CAN_SWIM_IN_ALGAE)) {
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(0.8, 0.8, 0.8));
		}
	}

	@NotNull
	public static List<Direction> shuffleOffsets(@NotNull RandomSource random) {
		return Util.toShuffledList(Direction.Plane.HORIZONTAL.stream(), random);
	}

	@Nullable
	private BlockPos bonemealPos = null;

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
		for (Direction offset : AlgaeBlock.shuffleOffsets(AdvancedMath.random())) {
			BlockPos blockPos = pos.relative(offset);
			if (level.getBlockState(blockPos).isAir() && this.canSurvive(this.defaultBlockState(), level, blockPos.below())) {
				this.bonemealPos = blockPos;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		return true;
	}

	@Override
	public void performBonemeal(@NotNull ServerLevel level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (this.bonemealPos != null) {
			level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, this.bonemealPos, 0);
			level.setBlockAndUpdate(this.bonemealPos, this.defaultBlockState());
		}
		this.bonemealPos = null;
	}

	public boolean hasAmountNearby(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, int x, int neededAmount) {
		Iterator<BlockPos> iterator = BlockPos.betweenClosed(blockPos.offset(-x, -x, -x), blockPos.offset(x, x, x)).iterator();
		int count = 0;
		BlockPos.MutableBlockPos mutableBlockPos = blockPos.mutable();
		do {
			if (!iterator.hasNext()) {
				return false;
			}
			mutableBlockPos.set(iterator.next());
			if (level.getBlockState(mutableBlockPos).is(this)) {
				count = count + 1;
			}
		} while (count < neededAmount);
		return true;
	}
}
