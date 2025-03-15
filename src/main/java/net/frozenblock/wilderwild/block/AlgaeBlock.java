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
import java.util.Iterator;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
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

public class AlgaeBlock extends Block implements BonemealableBlock {
	public static final MapCodec<AlgaeBlock> CODEC = simpleCodec(AlgaeBlock::new);
	public static final double ENTITY_SLOWDOWN = 0.8D;
	protected static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16D, 1D, 16D);

	public AlgaeBlock(@NotNull BlockBehaviour.Properties settings) {
		super(settings);
	}

	@NotNull
	@Override
	protected MapCodec<? extends AlgaeBlock> codec() {
		return CODEC;
	}

	@Override
	@NotNull
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
		return SHAPE;
	}

	@Override
	public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
		FluidState belowFluidState = level.getFluidState(pos.below());
		FluidState thisFluidState = level.getFluidState(pos);
		return belowFluidState.getType() == Fluids.WATER && thisFluidState.getType() == Fluids.EMPTY;
	}

	@Override
	protected @NotNull BlockState updateShape(
		@NotNull BlockState blockState,
		LevelReader levelReader,
		ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource randomSource
	) {
		return !this.canSurvive(blockState, levelReader, blockPos)
			? Blocks.AIR.defaultBlockState()
			: super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!this.canSurvive(state, level, pos)) {
			level.destroyBlock(pos, false);
		}
	}

	@Override
	public void entityInside(
	@NotNull BlockState state,
	@NotNull Level level,
	@NotNull BlockPos pos,
	@NotNull Entity entity,
	InsideBlockEffectApplier insideBlockEffectApplier
	) {
		if (entity.getY() <= pos.getY() + SHAPE.max(Direction.Axis.Y)) {
			if (entity.getType().equals(EntityType.FALLING_BLOCK)) {
				level.destroyBlock(pos, false);
			}
			if (!entity.getType().is(WWEntityTags.CAN_SWIM_IN_ALGAE)) {
				if (entity instanceof Player player && player.getAbilities().flying) return;
				entity.resetFallDistance();
				entity.setDeltaMovement(entity.getDeltaMovement().scale(ENTITY_SLOWDOWN));
			}
		}
	}

	@Override
	public boolean isValidBonemealTarget(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			mutableBlockPos.setWithOffset(pos, direction);
			if (level.getBlockState(mutableBlockPos).isAir() && this.canSurvive(this.defaultBlockState(), level, mutableBlockPos)) {
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
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(random)) {
			mutableBlockPos.setWithOffset(pos, direction);
			if (level.getBlockState(mutableBlockPos).isAir() && this.canSurvive(this.defaultBlockState(), level, mutableBlockPos)) {
				level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, mutableBlockPos, 0);
				level.setBlockAndUpdate(mutableBlockPos, this.defaultBlockState());
				return;
			}
		}
	}

	public static boolean hasNearbyAlgae(@NotNull LevelAccessor level, @NotNull BlockPos blockPos, int distance, int threshold) {
		Iterator<BlockPos> posesToCheck = BlockPos.betweenClosed(blockPos.offset(-distance, -distance, -distance), blockPos.offset(distance, distance, distance)).iterator();
		int count = 0;
		do {
			if (!posesToCheck.hasNext()) return false;
			if (level.getBlockState(posesToCheck.next()).is(WWBlocks.ALGAE)) count = count + 1;
		} while (count < threshold);
		return true;
	}
}
