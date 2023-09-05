/*
 * Copyright 2023 FrozenBlock
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
import java.util.Optional;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.wilderwild.config.BlockConfig;
import net.frozenblock.wilderwild.misc.BubbleDirection;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.tag.WilderEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
	public static final EnumProperty<BubbleDirection> BUBBLE_DIRECTION = RegisterProperties.BUBBLE_DIRECTION;

	public final ParticleOptions dripParticle;
	public final boolean pearlescent;

	public MesogleaBlock(@NotNull Properties properties, @NotNull ParticleOptions dripParticle, boolean pearlescent) {
		super(properties.pushReaction(PushReaction.DESTROY));
		this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(BUBBLE_DIRECTION, BubbleDirection.NONE));
		this.dripParticle = dripParticle;
		this.pearlescent = pearlescent;
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		Optional<Direction> dragDirection = getDragDirection(state);
		if (this.pearlescent) {
			if (state.getValue(WATERLOGGED) && dragDirection.isEmpty()) {
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

		if (dragDirection.isPresent()) {
			BlockState blockState = level.getBlockState(pos.above());
			if (blockState.isAir()) {
				entity.onAboveBubbleCol(dragDirection.get() == Direction.DOWN);
				if (!level.isClientSide) {
					ServerLevel serverLevel = (ServerLevel)level;

					for(int i = 0; i < 2; ++i) {
						serverLevel.sendParticles(ParticleTypes.SPLASH, (double)pos.getX() + level.random.nextDouble(), pos.getY() + 1, (double)pos.getZ() + level.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
						serverLevel.sendParticles(ParticleTypes.BUBBLE, (double)pos.getX() + level.random.nextDouble(), pos.getY() + 1, (double)pos.getZ() + level.random.nextDouble(), 1, 0.0, 0.01, 0.0, 0.2);
					}
				}
			} else {
				entity.onInsideBubbleColumn(dragDirection.get() == Direction.DOWN);
			}
		}
	}

	public static boolean isMesoglea(BlockState blockState) {
		return blockState.hasProperty(BUBBLE_DIRECTION) && blockState.getBlock() instanceof MesogleaBlock;
	}

	public static boolean isColumnSupportingMesoglea(BlockState blockState) {
		return isMesoglea(blockState) && blockState.getValue(WATERLOGGED);
	}

	public static boolean hasBubbleColumn(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) && blockState.getValue(BUBBLE_DIRECTION) != BubbleDirection.NONE;
	}

	public static Optional<Direction> getDragDirection(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) ? blockState.getValue(BUBBLE_DIRECTION).direction : Optional.empty();
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		if (blockState.getValue(WATERLOGGED)) {
			VoxelShape shape = Shapes.empty();
			if (collisionContext instanceof EntityCollisionContext entityCollisionContext) {
				if (entityCollisionContext.getEntity() != null) {
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
							return shape;
						}
					}
				}
			}
			for (Direction direction : Direction.values()) {
				if (direction != Direction.UP && !blockGetter.getFluidState(blockPos.relative(direction)).is(FluidTags.WATER)) {
					shape = Shapes.or(shape, FrozenShapes.makePlaneFromDirection(direction, 0.05F));
				}
			}
			return shape;
		}
		return super.getCollisionShape(blockState, blockGetter, blockPos, collisionContext);
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		super.animateTick(blockState, level, blockPos, randomSource);
		double d = blockPos.getX();
		double e = blockPos.getY();
		double f = blockPos.getZ();
		if (randomSource.nextInt(0, 50) == 0 && (blockState.getValue(WATERLOGGED) || level.getFluidState(blockPos.above()).is(FluidTags.WATER)) && level.getFluidState(blockPos.below()).isEmpty() && level.getBlockState(blockPos.below()).isAir()) {
			level.addParticle(this.dripParticle, d + randomSource.nextDouble(), e, f + randomSource.nextDouble(), 0.0D, 0.0D, 0.0D);
		}
		Optional<Direction> dragDirection = getDragDirection(blockState);
		if (dragDirection.isPresent()) {
			if (dragDirection.get() == Direction.DOWN) {
				level.addAlwaysVisibleParticle(ParticleTypes.CURRENT_DOWN, d + 0.5, e + 0.8, f, 0.0, 0.0, 0.0);
				if (randomSource.nextInt(200) == 0) {
					level.playLocalSound(d, e, f, SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
				}
			} else if (dragDirection.get() == Direction.UP) {
				level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + 0.5, e, f + 0.5, 0.0, 0.04, 0.0);
				level.addAlwaysVisibleParticle(ParticleTypes.BUBBLE_COLUMN_UP, d + (double) randomSource.nextFloat(), e + (double) randomSource.nextFloat(), f + (double) randomSource.nextFloat(), 0.0, 0.04, 0.0);
				if (randomSource.nextInt(200) == 0) {
					level.playLocalSound(d, e, f, SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT, SoundSource.BLOCKS, 0.2F + randomSource.nextFloat() * 0.2F, 0.9F + randomSource.nextFloat() * 0.15F, false);
				}
			}
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
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction direction, @NotNull BlockState neighborState, @NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockPos neighborPos) {
		if (state.getValue(WATERLOGGED)) {
			level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			if (hasBubbleColumn(state)) {
				if (!canColumnSurvive(level, pos) || direction == Direction.DOWN || direction == Direction.UP && !hasBubbleColumn(neighborState) && canExistIn(neighborState)) {
					level.scheduleTick(pos, this, 5);
				}
			}
			if (neighborState.is(Blocks.BUBBLE_COLUMN)) {
				level.scheduleTick(pos, this, 5);
			}
		} else {
			state = state.setValue(BUBBLE_DIRECTION, BubbleDirection.NONE);
			level.setBlock(pos, state, 2);
		}
		return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}

	public static boolean canColumnSurvive(LevelReader level, BlockPos pos) {
		BlockState blockState = level.getBlockState(pos.below());
		return blockState.is(Blocks.BUBBLE_COLUMN) || blockState.is(Blocks.MAGMA_BLOCK) || blockState.is(Blocks.SOUL_SAND) || hasBubbleColumn(blockState);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		BubbleColumnBlock.updateColumn(level, pos.above(), state);
		updateColumn(level, pos, state, level.getBlockState(pos.below()));
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState blockState) {
		if (blockState.getValue(WATERLOGGED)) {
			return Fluids.WATER.getSource(false);
		}
		return super.getFluidState(blockState);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState state) {
		updateColumn(level, pos, level.getBlockState(pos), state);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState mesoglea, BlockState state) {
		if (canExistIn(mesoglea)) {
			level.setBlock(pos, getColumnState(mesoglea, state), 2);
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.UP);
			BlockState mutableState;
			while (canExistIn(mutableState = level.getBlockState(mutableBlockPos))) {
				if (!level.setBlock(mutableBlockPos, getColumnState(mutableState, state), 2)) {
					return;
				}
				mutableBlockPos.move(Direction.UP);
			}
		}
	}

	private static BlockState getColumnState(BlockState mesogleaState, BlockState blockState) {
		if (mesogleaState.getValue(WATERLOGGED)) {
			//Remember, blockState is for the block below.
			if (blockState.is(Blocks.BUBBLE_COLUMN)) {
				return mesogleaState.setValue(BUBBLE_DIRECTION, blockState.getValue(BlockStateProperties.DRAG) ? BubbleDirection.DOWN : BubbleDirection.UP);
			} else if (blockState.is(Blocks.SOUL_SAND)) {
				return mesogleaState.setValue(BUBBLE_DIRECTION, BubbleDirection.UP);
			} else if (blockState.is(Blocks.MAGMA_BLOCK)) {
				return mesogleaState.setValue(BUBBLE_DIRECTION, BubbleDirection.DOWN);
			}
		}
		return mesogleaState.setValue(BUBBLE_DIRECTION, BubbleDirection.NONE);
	}

	private static boolean canExistIn(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) && blockState.getFluidState().getAmount() >= 8 && blockState.getFluidState().isSource();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(WATERLOGGED, BUBBLE_DIRECTION);
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
