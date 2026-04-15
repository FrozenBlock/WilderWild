/*
 * Copyright 2025-2026 FrozenBlock
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

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.frozenblock.lib.block.api.shape.FrozenShapes;
import net.frozenblock.wilderwild.block.state.properties.BubbleDirection;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.boat.Boat;
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
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock {
	public static final MapCodec<MesogleaBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
		Codec.BOOL.fieldOf("pearlescent").forGetter(MesogleaBlock::isPearlescent),
		ColorRGBA.CODEC.fieldOf("water_fog_color").forGetter(MesogleaBlock::getWaterFogColorOverride),
		ParticleTypes.CODEC.fieldOf("drip_particle").forGetter(mesogleaBlock -> mesogleaBlock.dripParticle),
		ParticleTypes.CODEC.fieldOf("bubble_particle").forGetter(mesogleaBlock -> mesogleaBlock.bubbleParticle),
		ParticleTypes.CODEC.fieldOf("bubble_column_up_particle").forGetter(mesogleaBlock -> mesogleaBlock.bubbleColumnUpParticle),
		ParticleTypes.CODEC.fieldOf("current_down_particle").forGetter(mesogleaBlock -> mesogleaBlock.currentDownParticle),
		ParticleTypes.CODEC.fieldOf("splash_particle").forGetter(mesogleaBlock -> mesogleaBlock.splashParticle),
		propertiesCodec()
	).apply(instance, MesogleaBlock::new));
	public static final float JELLYFISH_COLLISION_FROM_SIDE = 0.25F;
	public static final double ITEM_SLOWDOWN = 0.999D;
	public static final double ITEM_VERTICAL_BOOST = 0.025D;
	public static final Vec3 ITEM_SLOWDOWN_VEC3 = new Vec3(ITEM_SLOWDOWN, ITEM_SLOWDOWN, ITEM_SLOWDOWN);
	public static final double BOAT_MAX_VERTICAL_SPEED = 0.175D;
	public static final double BOAT_VERTICAL_BOOST = 0.05D;
	public static final double BOAT_VERTICAL_SLOWDOWN_SCALE_WHEN_FALLING = 0.125D;
	public static final int DRIP_PARTICLE_CHANCE = 50;
	public static final int LIGHT_DAMPENING = 2;
	public static final int AMBIENT_WHIRLPOOL_SOUND_CHANCE = 200;
	public static final int TICK_DELAY = 5;
	public static final EnumProperty<BubbleDirection> BUBBLE_DIRECTION = WWBlockStateProperties.BUBBLE_DIRECTION;
	private final boolean pearlescent;
	private final ColorRGBA waterFogColor;
	private final ParticleOptions dripParticle;
	private final ParticleOptions bubbleParticle;
	private final ParticleOptions bubbleColumnUpParticle;
	private final ParticleOptions currentDownParticle;
	private final ParticleOptions splashParticle;

	public MesogleaBlock(
		boolean pearlescent,
		ColorRGBA waterFogColor,
		ParticleOptions dripParticle,
		ParticleOptions bubbleParticle,
		ParticleOptions bubbleColumnUpParticle,
		ParticleOptions currentDownParticle,
		ParticleOptions splashParticle,
		Properties properties
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

	public static boolean isMesoglea(BlockState state) {
		return state.hasProperty(BUBBLE_DIRECTION) && state.getBlock() instanceof MesogleaBlock;
	}

	public static boolean isColumnSupportingMesoglea(BlockState state) {
		return isMesoglea(state) && WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get();
	}

	public static boolean hasBubbleColumn(BlockState state) {
		return isColumnSupportingMesoglea(state) && state.getValue(BUBBLE_DIRECTION) != BubbleDirection.NONE;
	}

	public static boolean isDraggingDown(BlockState state) {
		return isColumnSupportingMesoglea(state) && state.getValue(BUBBLE_DIRECTION) == BubbleDirection.DOWN;
	}

	public static Optional<Direction> getDragDirection(BlockState state) {
		return isColumnSupportingMesoglea(state) ? state.getValue(BUBBLE_DIRECTION).direction : Optional.empty();
	}

	public static boolean canColumnSurvive(LevelReader level, BlockPos pos) {
		if (!WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) return false;
		final BlockState belowState = level.getBlockState(pos.below());
		return belowState.is(Blocks.BUBBLE_COLUMN)
			|| belowState.is(BlockTags.ENABLES_BUBBLE_COLUMN_DRAG_DOWN)
			|| belowState.is(BlockTags.ENABLES_BUBBLE_COLUMN_PUSH_UP)
			|| hasBubbleColumn(belowState);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState belowState) {
		updateColumn(level, pos, level.getBlockState(pos), belowState);
	}

	public static void updateColumn(LevelAccessor level, BlockPos pos, BlockState occupyState, BlockState belowState) {
		if (!canOccupy(occupyState)) return;
		level.setBlock(pos, getColumnState(occupyState, belowState), UPDATE_CLIENTS);
		final BlockPos.MutableBlockPos mutable = pos.mutable().move(Direction.UP);
		BlockState mutableState;
		while (true) {
			mutableState = level.getBlockState(mutable);
			if (canOccupy(mutableState)) {
				if (!level.setBlock(mutable, getColumnState(mutableState, belowState), UPDATE_CLIENTS)) return;
				mutable.move(Direction.UP);
			} else {
				BubbleColumnBlock.updateColumn(Blocks.BUBBLE_COLUMN, level, mutable, level.getBlockState(mutable.immutable().below()));
				return;
			}
		}
	}

	private static BlockState getColumnState(BlockState occupyState, BlockState belowState) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) {
			if (belowState.is(Blocks.BUBBLE_COLUMN)) return occupyState.setValue(BUBBLE_DIRECTION, belowState.getValue(BlockStateProperties.DRAG) ? BubbleDirection.DOWN : BubbleDirection.UP);
			if (belowState.is(BlockTags.ENABLES_BUBBLE_COLUMN_PUSH_UP)) return occupyState.setValue(BUBBLE_DIRECTION, BubbleDirection.UP);
			if (belowState.is(BlockTags.ENABLES_BUBBLE_COLUMN_DRAG_DOWN)) return occupyState.setValue(BUBBLE_DIRECTION, BubbleDirection.DOWN);
		}
		return occupyState.setValue(BUBBLE_DIRECTION, BubbleDirection.NONE);
	}

	private static boolean canOccupy(BlockState state) {
		return isColumnSupportingMesoglea(state) && state.getFluidState().isFull() && state.getFluidState().isSource();
	}

	@Override
	protected MapCodec<? extends MesogleaBlock> codec() {
		return CODEC;
	}

	public boolean isPearlescent() {
		return this.pearlescent;
	}

	public ColorRGBA getWaterFogColorOverride() {
		return this.waterFogColor;
	}

	public ParticleOptions getDripParticle() {
		return this.dripParticle;
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
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState replacingState, boolean movedByPiston) {
		if (!level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos)) return;
		level.destroyBlock(pos, false);
		level.levelEvent(LevelEvent.PARTICLES_WATER_EVAPORATING, pos, 0);
		level.playSound(null, pos, WWSounds.BLOCK_MESOGLEA_EVAPORATE, SoundSource.BLOCKS, 1F, (1F + level.getRandom().nextFloat() * 0.2F) * 0.7F);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		final Optional<Direction> dragDirection = getDragDirection(state);
		if (this.isPearlescent()) {
			if (dragDirection.isEmpty() || !WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) {
				if (entity instanceof ItemEntity item) {
					item.makeStuckInBlock(state, ITEM_SLOWDOWN_VEC3);
					item.addDeltaMovement(new Vec3 (0D, ITEM_VERTICAL_BOOST, 0D));
				}
				if (entity instanceof Boat boat) {
					final Vec3 deltaMovement = boat.getDeltaMovement();
					if (boat.isUnderWater() && deltaMovement.y < BOAT_MAX_VERTICAL_SPEED) {
						boat.setDeltaMovement(deltaMovement.x, Math.min(BOAT_MAX_VERTICAL_SPEED, deltaMovement.y + BOAT_VERTICAL_BOOST), deltaMovement.z);
					} else if (deltaMovement.y < 0) {
						boat.setDeltaMovement(deltaMovement.x, deltaMovement.y * BOAT_VERTICAL_SLOWDOWN_SCALE_WHEN_FALLING, deltaMovement.z);
					}
				}
			}
		}

		if (!isPrecise) return;
		if (dragDirection.isEmpty() || !WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) return;

		final BlockState aboveState = level.getBlockState(pos.above());
		if (!aboveState.isAir()) {
			entity.onInsideBubbleColumn(dragDirection.get() == Direction.DOWN);
			return;
		}

		entity.onAboveBubbleColumn(dragDirection.get() == Direction.DOWN, pos);
		if (!(level instanceof ServerLevel serverLevel)) return;
		for (int i = 0; i < 2; ++i) {
			serverLevel.sendParticles(
				this.getSplashParticle(),
				pos.getX() + level.getRandom().nextDouble(),
				pos.getY() + 1D,
				pos.getZ() + level.getRandom().nextDouble(),
				1,
				0D,
				0D,
				0D,
				1D
			);
			serverLevel.sendParticles(
				this.getBubbleParticle(),
				pos.getX() + level.getRandom().nextDouble(),
				pos.getY() + 1D,
				pos.getZ() + level.getRandom().nextDouble(),
				1,
				0D, 0.01D, 0D,
				0.2
			);
		}
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		VoxelShape shape = Shapes.empty();
		if (!(context instanceof EntityCollisionContext entityCollisionContext)) return shape;
		if (entityCollisionContext.getEntity() == null) return shape;

		final Entity entity = entityCollisionContext.getEntity();
		if (entity == null || !entity.is(WWEntityTypeTags.STAYS_IN_MESOGLEA) || entity.isPassenger() || entity.isDescending()) return shape;
		if (entity instanceof Mob mob && mob.isLeashed()) return shape;

		if (entity.isInWater() || (entity.getInBlockState().getBlock() instanceof MesogleaBlock)) {
			for (Direction direction : Direction.values()) {
				if (direction == Direction.UP || level.getFluidState(pos.relative(direction)).is(FluidTags.WATER)) continue;
				shape = Shapes.or(shape, FrozenShapes.makePlaneFromDirection(direction, JELLYFISH_COLLISION_FROM_SIDE));
			}
		}

		return shape;
	}

	@Override
	protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.block();
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		final Optional<Direction> optionalDragDirection = getDragDirection(state);
		if (optionalDragDirection.isEmpty()) return;

		final double x = pos.getX();
		final double y = pos.getY();
		final double z = pos.getZ();

		final Direction dragDirection = optionalDragDirection.get();
		if (dragDirection == Direction.DOWN) {
			level.addAlwaysVisibleParticle(this.getCurrentDownParticle(), x + 0.5D, y + 0.8D, z, 0D, 0D, 0D);
			if (random.nextInt(AMBIENT_WHIRLPOOL_SOUND_CHANCE) == 0) {
				level.playLocalSound(
					x, y, z,
					SoundEvents.BUBBLE_COLUMN_WHIRLPOOL_AMBIENT,
					SoundSource.BLOCKS,
					0.2F + random.nextFloat() * 0.2F,
					0.9F + random.nextFloat() * 0.15F,
					false
				);
			}
		} else if (dragDirection == Direction.UP) {
			level.addAlwaysVisibleParticle(
				this.getBubbleColumnUpParticle(),
				x + 0.5D, y, z + 0.5D,
				0D,
				0.04D,
				0D
			);
			level.addAlwaysVisibleParticle(
				this.getBubbleColumnUpParticle(),
				x + random.nextDouble(), y + random.nextDouble(), z + random.nextDouble(),
				0D,
				0.04D,
				0D
			);
			if (random.nextInt(AMBIENT_WHIRLPOOL_SOUND_CHANCE) == 0) {
				level.playLocalSound(
					x, y, z,
					SoundEvents.BUBBLE_COLUMN_UPWARDS_AMBIENT,
					SoundSource.BLOCKS,
					0.2F + random.nextFloat() * 0.2F,
					0.9F + random.nextFloat() * 0.15F,
					false
				);
			}
		}
	}

	@Override
	protected int getLightDampening(BlockState state) {
		return LIGHT_DAMPENING;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		final BlockState state = context.getLevel().getBlockState(context.getClickedPos());
		return this.defaultBlockState()
			.setValue(
				BUBBLE_DIRECTION,
				state.is(Blocks.BUBBLE_COLUMN) && WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()
					? state.getValue(BlockStateProperties.DRAG)
						? BubbleDirection.DOWN
						: BubbleDirection.UP
					: BubbleDirection.NONE
			);
	}

	@Override
	protected BlockState updateShape(
		BlockState state,
		LevelReader level,
		ScheduledTickAccess ticks,
		BlockPos pos,
		Direction direction,
		BlockPos neighborPos,
		BlockState neighborState,
		RandomSource random
	) {
		ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		updateColumn: {
			if (!WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) break updateColumn;
			scheduleColumnTick:{
				if (!hasBubbleColumn(state)) break scheduleColumnTick;
				if (!(!canColumnSurvive(level, pos) || direction.getAxis().isVertical() && !hasBubbleColumn(neighborState) && canOccupy(neighborState))) break scheduleColumnTick;
				ticks.scheduleTick(pos, this, TICK_DELAY);
			}
			if (direction == Direction.DOWN && neighborState.is(Blocks.BUBBLE_COLUMN)) ticks.scheduleTick(pos, this, TICK_DELAY);
		}
		return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		if (WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) level.scheduleTick(pos, this, TICK_DELAY);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get()) return;
		updateColumn(level, pos, state, level.getBlockState(pos.below()));
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return Fluids.WATER.getSource(false);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BUBBLE_DIRECTION);
	}

	@Override
	public boolean skipRendering(BlockState state, BlockState neighborState, Direction direction) {
		return neighborState.is(this);
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return WWBlockConfig.MESOGLEA_RENDERS_AS_FLUID.get() ? RenderShape.INVISIBLE : RenderShape.MODEL;
	}
}
