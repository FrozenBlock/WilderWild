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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.wilderwild.block.state.properties.BubbleDirection;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWEntityTags;
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
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock {
	public static final float JELLYFISH_COLLISION_FROM_SIDE = 0.25F;
	public static final double ITEM_SLOWDOWN = 0.999D;
	public static final double ITEM_VERTICAL_BOOST = 0.025D;
	public static final Vec3 ITEM_SLOWDOWN_VEC3 = new Vec3(ITEM_SLOWDOWN, ITEM_SLOWDOWN, ITEM_SLOWDOWN);
	public static final double BOAT_MAX_VERTICAL_SPEED = 0.175D;
	public static final double BOAT_VERTICAL_BOOST = 0.05D;
	public static final double BOAT_VERTICAL_SLOWDOWN_SCALE_WHEN_FALLING = 0.125D;
	public static final int DRIP_PARTICLE_CHANCE = 50;
	public static final int LIGHT_BLOCK = 2;
	public static final int AMBIENT_WHIRLPOOL_SOUND_CHANCE = 200;
	public static final int TICK_DELAY = 5;
	public static final EnumProperty<BubbleDirection> BUBBLE_DIRECTION = WWBlockStateProperties.BUBBLE_DIRECTION;
	public static final MapCodec<MesogleaBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Codec.BOOL.fieldOf("pearlescent").forGetter(MesogleaBlock::isPearlescent),
		Codec.INT.fieldOf("water_fog_color").forGetter(MesogleaBlock::getWaterFogColorOverride),
		ParticleTypes.CODEC.fieldOf("drip_particle").forGetter(mesogleaBlock -> mesogleaBlock.dripParticle),
		ParticleTypes.CODEC.fieldOf("bubble_particle").forGetter(mesogleaBlock -> mesogleaBlock.bubbleParticle),
		ParticleTypes.CODEC.fieldOf("bubble_column_up_particle").forGetter(mesogleaBlock -> mesogleaBlock.bubbleColumnUpParticle),
		ParticleTypes.CODEC.fieldOf("current_down_particle").forGetter(mesogleaBlock -> mesogleaBlock.currentDownParticle),
		ParticleTypes.CODEC.fieldOf("splash_particle").forGetter(mesogleaBlock -> mesogleaBlock.splashParticle),
		propertiesCodec()
	).apply(instance, MesogleaBlock::new));
	private final boolean pearlescent;
	private final int waterFogColor;
	private final ParticleOptions dripParticle;
	private final ParticleOptions bubbleParticle;
	private final ParticleOptions bubbleColumnUpParticle;
	private final ParticleOptions currentDownParticle;
	private final ParticleOptions splashParticle;

	public MesogleaBlock(
		boolean pearlescent,
		int waterFogColor,
		ParticleOptions dripParticle,
		ParticleOptions bubbleParticle,
		ParticleOptions bubbleColumnUpParticle,
		ParticleOptions currentDownParticle,
		ParticleOptions splashParticle,
		@NotNull Properties properties
	) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(BUBBLE_DIRECTION, BubbleDirection.NONE));
		this.pearlescent = pearlescent;
		this.waterFogColor = waterFogColor;
		this.dripParticle = dripParticle;
		this.bubbleParticle = bubbleParticle;
		this.bubbleColumnUpParticle = bubbleColumnUpParticle;
		this.currentDownParticle = currentDownParticle;
		this.splashParticle = splashParticle;
	}

	public static boolean isMesoglea(@NotNull BlockState blockState) {
		return blockState.hasProperty(BUBBLE_DIRECTION) && blockState.getBlock() instanceof MesogleaBlock;
	}

	public static boolean isColumnSupportingMesoglea(BlockState blockState) {
		return isMesoglea(blockState) && WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS;
	}

	public static boolean hasBubbleColumn(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) && blockState.getValue(BUBBLE_DIRECTION) != BubbleDirection.NONE;
	}

	public static boolean isDraggingDown(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) && blockState.getValue(BUBBLE_DIRECTION) == BubbleDirection.DOWN;
	}

	public static Optional<Direction> getDragDirection(BlockState blockState) {
		return isColumnSupportingMesoglea(blockState) ? blockState.getValue(BUBBLE_DIRECTION).direction : Optional.empty();
	}

	public static boolean canColumnSurvive(@NotNull LevelReader level, @NotNull BlockPos pos) {
		BlockState blockState = level.getBlockState(pos.below());
		return WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS && (
			blockState.is(Blocks.BUBBLE_COLUMN) || blockState.is(Blocks.MAGMA_BLOCK) || blockState.is(Blocks.SOUL_SAND) || hasBubbleColumn(blockState)
		);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState state) {
		updateColumn(level, pos, level.getBlockState(pos), state);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState mesoglea, BlockState state) {
		if (canExistIn(mesoglea)) {
			level.setBlock(pos, getColumnState(mesoglea, state), UPDATE_CLIENTS);
			BlockPos.MutableBlockPos mutableBlockPos = pos.mutable().move(Direction.UP);
			BlockState mutableState;
			while (true) {
				mutableState = level.getBlockState(mutableBlockPos);
				if (canExistIn(mutableState)) {
					if (!level.setBlock(mutableBlockPos, getColumnState(mutableState, state), UPDATE_CLIENTS)) {
						return;
					}
					mutableBlockPos.move(Direction.UP);
				} else {
					BubbleColumnBlock.updateColumn(level, mutableBlockPos, state);
					return;
				}
			}
		}
	}

	@NotNull
	private static BlockState getColumnState(@NotNull BlockState mesogleaState, @NotNull BlockState blockState) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
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

	@NotNull
	@Override
	protected MapCodec<? extends MesogleaBlock> codec() {
		return CODEC;
	}

	public boolean isPearlescent() {
		return this.pearlescent;
	}

	public int getWaterFogColorOverride() {
		return this.waterFogColor;
	}

	public ParticleOptions getBubbleParticle() {
		return this.bubbleParticle;
	}

	public ParticleOptions getBubbleColumnUpParticle() {
		return this.bubbleColumnUpParticle;
	}

	public ParticleOptions getSplashParticle() {
		return this.splashParticle;
	}

	public ParticleOptions getCurrentDownParticle() {
		return this.currentDownParticle;
	}

	@Override
	protected void onPlace(BlockState blockState, @NotNull Level level, BlockPos blockPos, BlockState blockState2, boolean bl) {
		if (level.dimensionType().ultraWarm()) {
			level.destroyBlock(blockPos, false);
			level.levelEvent(LevelEvent.PARTICLES_WATER_EVAPORATING, blockPos, 0);
			level.playSound(null, blockPos, WWSounds.BLOCK_MESOGLEA_EVAPORATE, SoundSource.BLOCKS, 1F, (1F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
		}
	}

	@Override
	public void entityInside(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Entity entity) {
		Optional<Direction> dragDirection = getDragDirection(state);
		if (this.isPearlescent()) {
			if (dragDirection.isEmpty() || !WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
				if (entity instanceof ItemEntity item) {
					item.makeStuckInBlock(state, ITEM_SLOWDOWN_VEC3);
					item.addDeltaMovement(new Vec3 (0D, ITEM_VERTICAL_BOOST, 0D));
				}
				if (entity instanceof Boat boat) {
					Vec3 deltaMovement = boat.getDeltaMovement();
					if (boat.isUnderWater() && deltaMovement.y < BOAT_MAX_VERTICAL_SPEED) {
						boat.setDeltaMovement(deltaMovement.x, Math.min(BOAT_MAX_VERTICAL_SPEED, deltaMovement.y + BOAT_VERTICAL_BOOST), deltaMovement.z);
					} else if (deltaMovement.y < 0) {
						boat.setDeltaMovement(deltaMovement.x, deltaMovement.y * BOAT_VERTICAL_SLOWDOWN_SCALE_WHEN_FALLING, deltaMovement.z);
					}
				}
			}
		}

		if (dragDirection.isPresent() && WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			BlockState blockState = level.getBlockState(pos.above());
			if (blockState.isAir()) {
				entity.onAboveBubbleCol(dragDirection.get() == Direction.DOWN);
				if (level instanceof ServerLevel serverLevel) {
					for (int i = 0; i < 2; ++i) {
						serverLevel.sendParticles(
							this.getSplashParticle(),
							pos.getX() + level.random.nextDouble(),
							pos.getY() + 1D,
							pos.getZ() + level.random.nextDouble(),
							1,
							0D,
							0D,
							0D,
							1D
						);
						serverLevel.sendParticles(
							this.getBubbleParticle(),
							pos.getX() + level.random.nextDouble(),
							pos.getY() + 1D,
							pos.getZ() + level.random.nextDouble(),
							1,
							0D,
							0.01D,
							0D,
							0.2
						);
					}
				}
			} else {
				entity.onInsideBubbleColumn(dragDirection.get() == Direction.DOWN);
			}
		}
	}

	@Override
	@NotNull
	public VoxelShape getCollisionShape(@NotNull BlockState blockState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull CollisionContext collisionContext) {
		VoxelShape shape = Shapes.empty();
		if (collisionContext instanceof EntityCollisionContext entityCollisionContext) {
			if (entityCollisionContext.getEntity() != null) {
				Entity entity = entityCollisionContext.getEntity();
				if (entity != null && entity.getType().is(WWEntityTags.STAYS_IN_MESOGLEA) && !entity.isPassenger() && !entity.isDescending()) {
					if (entity instanceof Mob mob && mob.isLeashed()) {
						return shape;
					}
					BlockState insideState = entity.getBlockStateOn();
					if (entity.isInWater() || (insideState.getBlock() instanceof MesogleaBlock)) {
						for (Direction direction : Direction.values()) {
							if (direction != Direction.UP && !blockGetter.getFluidState(blockPos.relative(direction)).is(FluidTags.WATER)) {
								shape = Shapes.or(shape, FrozenShapes.makePlaneFromDirection(direction, JELLYFISH_COLLISION_FROM_SIDE));
							}
						}
					}
				}
				return shape;
			}
		}
		return shape;
	}

	@Override
	public void animateTick(@NotNull BlockState blockState, @NotNull Level level, @NotNull BlockPos blockPos, @NotNull RandomSource randomSource) {
		super.animateTick(blockState, level, blockPos, randomSource);
		double d = blockPos.getX();
		double e = blockPos.getY();
		double f = blockPos.getZ();
		if (blockState.getBlock() instanceof MesogleaBlock) {
			if (randomSource.nextInt(0, DRIP_PARTICLE_CHANCE) == 0
				&& level.getFluidState(blockPos.below()).isEmpty()
				&& level.getBlockState(blockPos.below()).isAir()
			) {
				level.addParticle(this.dripParticle, d + randomSource.nextDouble(), e, f + randomSource.nextDouble(), 0D, 0D, 0D);
			}
		}
		Optional<Direction> dragDirection = getDragDirection(blockState);
		if (dragDirection.isPresent()) {
			if (dragDirection.get() == Direction.DOWN) {
				level.addAlwaysVisibleParticle(this.getCurrentDownParticle(), d + 0.5D, e + 0.8D, f, 0D, 0D, 0D);
				if (randomSource.nextInt(AMBIENT_WHIRLPOOL_SOUND_CHANCE) == 0) {
					level.playLocalSound(
						d,
						e,
						f,
						SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
						SoundSource.BLOCKS,
						0.2F + randomSource.nextFloat() * 0.2F,
						0.9F + randomSource.nextFloat() * 0.15F,
						false
					);
				}
			} else if (dragDirection.get() == Direction.UP) {
				level.addAlwaysVisibleParticle(
					this.getBubbleColumnUpParticle(),
					d + 0.5D,
					e,
					f + 0.5D,
					0D,
					0.04D,
					0D
				);
				level.addAlwaysVisibleParticle(
					this.getBubbleColumnUpParticle(),
					d + randomSource.nextDouble(),
					e + randomSource.nextDouble(),
					f + randomSource.nextDouble(),
					0D,
					0.04D,
					0D
				);
				if (randomSource.nextInt(AMBIENT_WHIRLPOOL_SOUND_CHANCE) == 0) {
					level.playLocalSound(
						d,
						e,
						f,
						SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
						SoundSource.BLOCKS,
						0.2F + randomSource.nextFloat() * 0.2F,
						0.9F + randomSource.nextFloat() * 0.15F,
						false
					);
				}
			}
		}
	}

	@Override
	public int getLightBlock(@NotNull BlockState blockState) {
		return LIGHT_BLOCK;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext blockPlaceContext) {
		BlockState blockState = blockPlaceContext.getLevel().getBlockState(blockPlaceContext.getClickedPos());
		return this.defaultBlockState()
			.setValue(
				BUBBLE_DIRECTION,
				blockState.is(Blocks.BUBBLE_COLUMN)
					&& WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS
					? blockState.getValue(BlockStateProperties.DRAG)
					? BubbleDirection.DOWN
					: BubbleDirection.UP
					: BubbleDirection.NONE
			);
	}

	@Override
	protected @NotNull BlockState updateShape(
		BlockState blockState,
		LevelReader levelReader,
		@NotNull ScheduledTickAccess scheduledTickAccess,
		BlockPos blockPos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource randomSource
	) {
		scheduledTickAccess.scheduleTick(blockPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelReader));
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			if (hasBubbleColumn(blockState)) {
				if (!canColumnSurvive(levelReader, blockPos) || direction == Direction.DOWN || direction == Direction.UP && !hasBubbleColumn(neighborState) && canExistIn(neighborState)) {
					scheduledTickAccess.scheduleTick(blockPos, this, TICK_DELAY);
				}
			}
			if (direction == Direction.DOWN && neighborState.is(Blocks.BUBBLE_COLUMN)) {
				scheduledTickAccess.scheduleTick(blockPos, this, TICK_DELAY);
			}
		}
		return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, neighborPos, neighborState, randomSource);
	}

	@Override
	protected void neighborChanged(BlockState blockState, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			level.scheduleTick(pos, this, TICK_DELAY);
		}
	}

	@Override
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS) {
			updateColumn(level, pos, state, level.getBlockState(pos.below()));
			BubbleColumnBlock.updateColumn(level, pos.above(), state);
		}
	}

	@Override
	@NotNull
	public FluidState getFluidState(@NotNull BlockState blockState) {
		return Fluids.WATER.getSource(false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(BUBBLE_DIRECTION);
	}

	@Override
	public boolean skipRendering(@NotNull BlockState blockState, @NotNull BlockState blockState2, @NotNull Direction direction) {
		return blockState2.is(this);
	}

	@Override
	@NotNull
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return WWBlockConfig.Client.MESOGLEA_FLUID ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}
}
