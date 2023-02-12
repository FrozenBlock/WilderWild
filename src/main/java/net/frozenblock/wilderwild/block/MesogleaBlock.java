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
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock implements SimpleWaterloggedBlock {
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public MesogleaBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false));
	}

	@Override
	public void entityInside(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		if (state.getValue(WATERLOGGED)) {
			if (entity instanceof ItemEntity item) {
				item.makeStuckInBlock(state, new Vec3(0.999D, 0.999D, 0.999D));
				item.setDeltaMovement(item.getDeltaMovement().add(0, 0.025, 0));
			}
			if (entity instanceof Boat boat) {
				Vec3 deltaMove = boat.getDeltaMovement();
				if (boat.isUnderWater()) {
					boat.setDeltaMovement(deltaMove.x, Math.min(0.175, deltaMove.y + 0.05), deltaMove.z);
				} else if (deltaMove.y < 0) {
					boat.setDeltaMovement(deltaMove.x, deltaMove.y * 0.5, deltaMove.z);
				}
			}
		}
	}

	@Override
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		if (collisionContext instanceof EntityCollisionContext entityCollisionContext && entityCollisionContext.getEntity() != null) {
			if (blockState.getValue(WATERLOGGED)) {
				VoxelShape shape = Shapes.empty();
				if (entityCollisionContext.getEntity() instanceof Jellyfish jellyfish) {
					BlockState jellyfishState = jellyfish.getFeetBlockState();
					if (jellyfish.isInWater() || (jellyfishState.getBlock() instanceof MesogleaBlock && jellyfishState.getValue(BlockStateProperties.WATERLOGGED))) {
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
			ParticleOptions particle = blockState.is(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA) ? RegisterParticles.BLUE_PEARLESCENT_HANGING_MESOGLEA :
					blockState.is(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA) ? RegisterParticles.PURPLE_PEARLESCENT_HANGING_MESOGLEA :
							blockState.is(RegisterBlocks.YELLOW_MESOGLEA) ? RegisterParticles.YELLOW_HANGING_MESOGLEA :
									blockState.is(RegisterBlocks.BLUE_MESOGLEA) ? RegisterParticles.BLUE_HANGING_MESOGLEA :
											blockState.is(RegisterBlocks.LIME_MESOGLEA) ? RegisterParticles.LIME_HANGING_MESOGLEA :
													blockState.is(RegisterBlocks.PINK_MESOGLEA) ? RegisterParticles.PINK_HANGING_MESOGLEA :
															RegisterParticles.RED_HANGING_MESOGLEA;
			level.addParticle(particle, blockPos.getX() + randomSource.nextDouble(), blockPos.getY(), blockPos.getZ() + randomSource.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public int getLightBlock(BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos) {
		return blockState.getValue(WATERLOGGED) ? 2 : 5;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
		FluidState fluidState = blockPlaceContext.getLevel().getFluidState(blockPlaceContext.getClickedPos());
		return Objects.requireNonNull(super.getStateForPlacement(blockPlaceContext)).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
	}

	@Override
	public BlockState updateShape(BlockState blockState, @NotNull Direction direction, @NotNull BlockState blockState2, @NotNull LevelAccessor levelAccessor, @NotNull BlockPos blockPos, @NotNull BlockPos blockPos2) {
		if (blockState.getValue(WATERLOGGED)) {
			levelAccessor.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
		}
		return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
	}

	@Override
	public FluidState getFluidState(BlockState blockState) {
		if (blockState.getValue(WATERLOGGED)) {
			return Fluids.WATER.getSource(false);
		}
		return super.getFluidState(blockState);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED);
	}

	@Override
	public boolean skipRendering(@NotNull BlockState blockState, BlockState blockState2, @NotNull Direction direction) {
		boolean isThisWaterlogged = blockState.getValue(WATERLOGGED);
		return blockState2.is(this) && (isThisWaterlogged == blockState2.getValue(WATERLOGGED) || isThisWaterlogged);
	}
}
