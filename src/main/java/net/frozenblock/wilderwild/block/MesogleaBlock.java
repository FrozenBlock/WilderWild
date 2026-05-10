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
import net.frozenblock.lib.block.api.waterlike.WaterLikeBlock;
import net.frozenblock.lib.block.impl.waterlike.BubbleColumnDirection;
import net.frozenblock.lib.block.impl.waterlike.WaterLikeType;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWWaterLikeTypes;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MesogleaBlock extends HalfTransparentBlock implements WaterLikeBlock {
	public static final MapCodec<MesogleaBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.BOOL.fieldOf("pearlescent").forGetter(MesogleaBlock::isPearlescent),
		ColorRGBA.CODEC.fieldOf("water_like_color").forGetter(MesogleaBlock::waterLikeColor),
		ParticleTypes.CODEC.fieldOf("drip_particle").forGetter(MesogleaBlock::dripParticle),
		ParticleTypes.CODEC.fieldOf("bubble_particle").forGetter(MesogleaBlock::bubbleParticle),
		ParticleTypes.CODEC.fieldOf("splash_particle").forGetter(MesogleaBlock::splashParticle),
		ParticleTypes.CODEC.fieldOf("bubble_column_up_particle").forGetter(mesogleaBlock -> mesogleaBlock.bubbleColumnUpParticle().orElseThrow()),
		ParticleTypes.CODEC.fieldOf("current_down_particle").forGetter(mesogleaBlock -> mesogleaBlock.currentDownParticle().orElseThrow()),
		propertiesCodec()
	).apply(instance, MesogleaBlock::new));
	public static final double ITEM_SLOWDOWN = 0.999D;
	public static final double ITEM_VERTICAL_BOOST = 0.025D;
	public static final Vec3 ITEM_SLOWDOWN_VEC3 = new Vec3(ITEM_SLOWDOWN, ITEM_SLOWDOWN, ITEM_SLOWDOWN);
	public static final double BOAT_MAX_VERTICAL_SPEED = 0.175D;
	public static final double BOAT_VERTICAL_BOOST = 0.05D;
	public static final double BOAT_VERTICAL_SLOWDOWN_SCALE_WHEN_FALLING = 0.125D;
	public static final int DRIP_PARTICLE_CHANCE = 50;
	public static final int LIGHT_DAMPENING = 2;
	private final boolean pearlescent;
	private final ColorRGBA waterLikeColor;
	private final ParticleOptions dripParticle;
	private final ParticleOptions bubbleParticle;
	private final ParticleOptions bubbleColumnUpParticle;
	private final ParticleOptions currentDownParticle;
	private final ParticleOptions splashParticle;

	public MesogleaBlock(
		boolean pearlescent,
		ColorRGBA waterLikeColor,
		ParticleOptions dripParticle,
		ParticleOptions bubbleParticle,
		ParticleOptions bubbleColumnUpParticle,
		ParticleOptions currentDownParticle,
		ParticleOptions splashParticle,
		Properties properties
	) {
		super(properties);
		this.registerDefaultState(this.defaultBlockState().setValue(BUBBLE_COLUMN_DIRECTION, BubbleColumnDirection.NONE));
		this.pearlescent = pearlescent;
		this.waterLikeColor = waterLikeColor;
		this.dripParticle = dripParticle;
		this.bubbleParticle = bubbleParticle;
		this.bubbleColumnUpParticle = bubbleColumnUpParticle;
		this.currentDownParticle = currentDownParticle;
		this.splashParticle = splashParticle;
	}

	@Override
	protected MapCodec<? extends MesogleaBlock> codec() {
		return CODEC;
	}

	public boolean isPearlescent() {
		return this.pearlescent;
	}

	@Override
	public ResourceKey<WaterLikeType> myWaterLikeType() {
		return WWWaterLikeTypes.MESOGLEA;
	}

	@Override
	public ColorRGBA waterLikeColor() {
		return this.waterLikeColor;
	}

	@Override
	public float waterFogDistance() {
		return 0.5F;
	}

	@Override
	public ParticleOptions dripParticle() {
		return this.dripParticle;
	}

	@Override
	public int dripParticleChance() {
		return DRIP_PARTICLE_CHANCE;
	}

	@Override
	public ParticleOptions bubbleParticle() {
		return this.bubbleParticle;
	}

	@Override
	public ParticleOptions splashParticle() {
		return this.splashParticle;
	}

	@Override
	public boolean supportsBubbleColumns() {
		return WWBlockConfig.MESOGLEA_BUBBLE_COLUMNS.get();
	}

	@Override
	public Optional<ParticleOptions> bubbleColumnUpParticle() {
		return Optional.of(this.bubbleColumnUpParticle);
	}

	@Override
	public Optional<ParticleOptions> currentDownParticle() {
		return Optional.of(this.currentDownParticle);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier effectApplier, boolean isPrecise) {
		if (this.isPearlescent() && !WaterLikeBlock.hasBubbleColumn(state)) {
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

		this.tryEntityInsideAsBubbleColumn(state, level, pos, entity, effectApplier, isPrecise);
	}

	@Override
	public boolean canEvaporateOnPlace() {
		return true;
	}

	@Override
	public Optional<SoundEvent> evaporateSound() {
		return Optional.of(WWSounds.BLOCK_MESOGLEA_EVAPORATE);
	}

	@Override
	protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState replacingState, boolean movedByPiston) {
		this.tryEvaporateOnPlace(state, level, pos, replacingState, movedByPiston);
	}

	@Override
	public Optional<TagKey<EntityType<?>>> entityTypesThatStayWithinMe(BlockState state) {
		return Optional.of(WWEntityTypeTags.STAYS_IN_MESOGLEA);
	}

	@Override
	public boolean canWithinEntityTypesExitFromTop(BlockState state) {
		return true;
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return this.getCollisionShapeForWaterLike(state, level, pos, context);
	}

	@Override
	protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter level, BlockPos pos) {
		return Shapes.block();
	}

	@Override
	protected int getLightDampening(BlockState state) {
		return LIGHT_DAMPENING;
	}

	@Override
	@Nullable
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return this.getPlacementStateForWaterLike(context, this.defaultBlockState());
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
		this.onUpdateShapeForWaterLike(this, state, level, ticks, pos, direction, neighborPos, neighborState, random);
		return super.updateShape(state, level, ticks, pos, direction, neighborPos, neighborState, random);
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, @Nullable Orientation orientation, boolean movedByPiston) {
		this.neighborChangedForWaterLike(this, state, level, pos, neighborBlock, orientation, movedByPiston);
	}

	@Override
	public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		this.tickForWaterLike(state, level, pos, random);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);
		this.animateTickForWaterLike(state, level, pos, random);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return this.getFluidStateForWaterLike();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(BUBBLE_COLUMN_DIRECTION);
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
