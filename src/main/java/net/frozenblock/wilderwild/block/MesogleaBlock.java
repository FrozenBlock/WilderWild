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

import java.util.Objects;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public final ParticleOptions dripParticle;
	public final boolean pearlescent;

	public MesogleaBlock(@NotNull Properties properties, @NotNull ParticleOptions dripParticle, boolean pearlescent) {
		super(properties.pushReaction(PushReaction.DESTROY));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
		this.dripParticle = dripParticle;
		this.pearlescent = pearlescent;
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (this.pearlescent) {
			if (state.getValue(WATERLOGGED)) {
				if (entity instanceof ItemEntity item) {
					item.makeStuckInBlock(state, new Vec3(0.999D, 0.999D, 0.999D));
					item.setDeltaMovement(item.getDeltaMovement().add(0, 0.025, 0));
				}
				if (entity instanceof Boat boat) {
					Vec3 deltaMovement = boat.getDeltaMovement();
					if (boat.isUnderWater() && deltaMovement.y < 0.175) {
						boat.setDeltaMovement(deltaMovement.x, Math.min(0.175, deltaMovement.y + 0.05), deltaMovement.z);
					} else if (deltaMovement.y < 0) {
						boat.setDeltaMovement(deltaMovement.x, deltaMovement.y * 0.125, deltaMovement.z);
					}
				}
			}
		}
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		if (collisionContext instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() != null) {
			if (blockState.getValue(WATERLOGGED)) {
				VoxelShape shape = Shapes.empty();
				Entity entity = entityCollisionContext.getEntity();
				if (entity != null && entity.getType().is(WilderEntityTags.STAYS_IN_MESOGLEA) && !entity.isPassenger() && !entity.isDescending()) {
					if (entity instanceof Mob mob && mob.isLeashed()) {
						return shape;
					}
					BlockState insideState = entity.getFeetBlockState();
					if (entity.isInWater() || (insideState.getBlock() instanceof MesogleaBlock && insideState.getValue(BlockStateProperties.WATERLOGGED))) {
						for (Direction direction : Direction.values()) {
							if (direction != Direction.UP && !blockGetter.getFluidState(blockPos.relative(direction)).is(FluidTags.WATER)) {
								shape = Shapes.or(shape, FrozenShapes.makePlaneFromDirection(direction, 0.25F));
							}
						}
					}
				}
				return shape;
			}
		}
		return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		super.animateTick(blockState, level, blockPos, randomSource);
		if (randomSource.nextInt(0, 50) == 0 && (blockState.getValue(WATERLOGGED) || level.getFluidState(blockPos.above()).is(FluidTags.WATER)) && level.getFluidState(blockPos.below()).isEmpty() && level.getBlockState(blockPos.below()).isAir()) {
			level.addParticle(this.dripParticle, blockPos.getX() + randomSource.nextDouble(), blockPos.getY(), blockPos.getZ() + randomSource.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightBlock(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return blockState.getValue(WATERLOGGED) ? 2 : 5;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
		return Objects.requireNonNull(super.getStateForPlacement(blockPlaceContext)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	@NotNull
	public BlockState updateShape(@NotNull BlockState blockState, @NotNull Direction direction, @NotNull BlockState blockState2, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState blockState) {
		if (blockState.getValue(WATERLOGGED)) {
			return Fluids.WATER.getSource(false);
		}
		return super.getFluidState(blockState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean skipRendering(@NotNull BlockState blockState, @NotNull BlockState blockState2, @NotNull Direction direction) {
		boolean isThisWaterlogged = blockState.getValue(WATERLOGGED);
		return blockState2.is(this) && (isThisWaterlogged == blockState2.getValue(WATERLOGGED) || isThisWaterlogged);
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) && BlockConfig.get().mesogleaLiquid ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}
}
