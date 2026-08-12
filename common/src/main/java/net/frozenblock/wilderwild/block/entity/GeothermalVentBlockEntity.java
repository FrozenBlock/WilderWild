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

package net.frozenblock.wilderwild.block.entity;

import com.google.common.base.Function;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.frozenblock.lib.platform.api.attachment.DataAttachmentTarget;
import net.frozenblock.lib.wind.BlowingHelper;
import net.frozenblock.lib.wind.disturbance.WindDisturbances;
import net.frozenblock.wilderwild.advancements.trigger.GeothermalVentPushMobTrigger;
import net.frozenblock.wilderwild.block.GeothermalVentBlock;
import net.frozenblock.wilderwild.block.impl.client.GeothermalVentParticleSpawner;
import net.frozenblock.wilderwild.block.state.properties.GeothermalVentStage;
import net.frozenblock.wilderwild.block.state.properties.GeothermalVentType;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.registry.WWWindDisturbances;
import net.frozenblock.wilderwild.tag.WWEntityTypeTags;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentBaseWindDisturbance;
import net.frozenblock.wilderwild.wind.disturbance.GeothermalVentEffectiveWindDisturbance;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class GeothermalVentBlockEntity extends BlockEntity {
	private static final Predicate<Entity> EFFECT_PREDICATE = EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS);
	public static final double ERUPTION_DISTANCE = 6D;
	public static final double VENT_DISTANCE = 3D;
	public static final int ERUPTION_DISTANCE_IN_BLOCKS = 5;
	public static final int VENT_DISTANCE_IN_BLOCKS = 2;
	public static final int MIN_ACTIVE_TICKS = 100;
	public static final int MAX_ACTIVE_TICKS = 200;
	public static final int MIN_DORMANT_TICKS = 400;
	public static final int MAX_DORMANT_TICKS = 900;
	public static final int MIN_ERUPTION_TICKS = 20;
	public static final int MAX_ERUPTION_TICKS = 40;
	public static final int ERUPTION_TICKS_UNNATURAL = 30;
	public static final float ERUPTION_PROGRESS_INTERVAL = 0.1F;
	public static final int TICK_DELAY_START_MIN = 20;
	public static final int TICK_DELAY_START_MAX = 100;
	public static final double EFFECTIVE_PUSH_INTENSITY = 0.4D;
	public static final double INEFFECTIVE_PUSH_INTENSITY = 0.2D;

	public static final int FIRE_TICKS_MAX = 260;
	private boolean hasRunFirstCheck = false;
	private int ticksUntilNextEvent;
	private float eruptionProgress;

	public GeothermalVentBlockEntity(BlockPos pos, BlockState state) {
		super(WWBlockEntityTypes.GEOTHERMAL_VENT.get(), pos, state);
	}

	@Override
	public boolean triggerEvent(int eventId, int data) {
		if (eventId == 1 && this.level.isClientSide()) {
			this.eruptionProgress = 0F;
			this.ticksUntilNextEvent = data;
			return true;
		}
		return super.triggerEvent(eventId, data);
	}

	public void tickServer(ServerLevel level, BlockPos pos, BlockState state, RandomSource random) {
		final GeothermalVentType geothermalVentType = state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE);
		final GeothermalVentStage geothermalVentStage = state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_STAGE);
		final Direction direction = state.getValue(GeothermalVentBlock.FACING);
		boolean natural = state.getValue(GeothermalVentBlock.NATURAL);

		if (!this.hasRunFirstCheck) {
			level.scheduleTick(pos, this.getBlockState().getBlock(), level.getRandom().nextInt(TICK_DELAY_START_MIN, TICK_DELAY_START_MAX));
			this.hasRunFirstCheck = true;
		} else if (GeothermalVentBlock.isActive(geothermalVentType)) {
			if (geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT) {
				this.eruptionProgress = 1F;
				this.tickEruption(level, pos, geothermalVentType, direction, natural);
				this.setActive(level, pos, state, random);
			} else if (geothermalVentStage == GeothermalVentStage.ERUPTING) {
				if (this.eruptionProgress == 0F) {
					this.ticksUntilNextEvent = natural ? random.nextInt(MIN_ERUPTION_TICKS, MAX_ERUPTION_TICKS) : ERUPTION_TICKS_UNNATURAL;
					level.playSound(null, pos, geothermalVentType.getEruptionSound().get(), SoundSource.BLOCKS, 0.7F, 0.9F + (random.nextFloat() * 0.2F));
					level.blockEvent(pos, state.getBlock(), 1, this.ticksUntilNextEvent);
				}
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
				this.tickEruption(level, pos, geothermalVentType, direction, natural);
			}
			this.ticksUntilNextEvent -= 1;
			if (this.ticksUntilNextEvent <= 0) this.advanceStage(level, pos, state, geothermalVentStage, natural, random);
		} else {
			this.setDormant(level, pos, state, random);
		}
	}

	private static AABB aabb(BlockPos startPos, BlockPos endPos) {
		return new AABB(
			Math.min(startPos.getX(), endPos.getX()),
			Math.min(startPos.getY(), endPos.getY()),
			Math.min(startPos.getZ(), endPos.getZ()),
			Math.max(startPos.getX(), endPos.getX()) + 1D,
			Math.max(startPos.getY(), endPos.getY()) + 1D,
			Math.max(startPos.getZ(), endPos.getZ()) + 1D
		);
	}

	/**
	 * @return the base, effective, damaging, and maximum possible areas of the eruption, along with the ending position of the scan.
	 */
	private EruptionAreas computeEruptionAreas(Level level, BlockPos pos, GeothermalVentType geothermalVentType, Direction direction) {
		final BlockPos maxEndPos = pos.relative(direction, (int) ERUPTION_DISTANCE);
		final boolean vent = geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT;

		Optional<BlockPos> cutoffPos = Optional.empty();
		Optional<BlockPos> damageCutoffPos = Optional.empty();
		final BlockPos.MutableBlockPos mutablePos = pos.mutable();

		for (int i = 0; i < (vent ? VENT_DISTANCE_IN_BLOCKS : ERUPTION_DISTANCE_IN_BLOCKS); i++) {
			if (!level.hasChunkAt(mutablePos.move(direction))) continue;

			final BlockState state = level.getBlockState(mutablePos);
			if (!BlowingHelper.canBlowingPassThrough(level, mutablePos, state, direction)) break;

			final boolean mismatchesAir = geothermalVentType == GeothermalVentType.AIR && !state.getFluidState().isEmpty();
			final boolean mismatchesWater = geothermalVentType.isWater() && !state.getFluidState().is(Fluids.WATER);
			final boolean mismatchesLava = geothermalVentType == GeothermalVentType.LAVA && !state.getFluidState().is(Fluids.LAVA);
			if (mismatchesAir || mismatchesWater || mismatchesLava) {
				if (cutoffPos.isEmpty()) cutoffPos = Optional.of(mutablePos.immutable());
			}
			if (geothermalVentType == GeothermalVentType.LAVA && state.getFluidState().is(FluidTags.WATER)) {
				if (damageCutoffPos.isEmpty()) damageCutoffPos = Optional.of(mutablePos.immutable());
			}
		}

		final AABB eruption = aabb(pos, mutablePos.immutable());
		mutablePos.move(direction.getOpposite());
		final BlockPos scanEndPos = mutablePos.immutable();

		final AABB effectiveEruption = cutoffPos.map(blockPos -> aabb(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> aabb(pos, scanEndPos));
		final AABB damagingEruption = damageCutoffPos.map(blockPos -> aabb(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> aabb(pos, scanEndPos));

		final AABB maxPossibleEruptionBoundingBox = getPossibleEruptionBoundingBox(pos, maxEndPos);

		return new EruptionAreas(eruption, effectiveEruption, damagingEruption, maxPossibleEruptionBoundingBox, scanEndPos);
	}

	public boolean isErupting() {
		final BlockState state = this.getBlockState();
		if (!state.hasProperty(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE) || !state.hasProperty(GeothermalVentBlock.GEOTHERMAL_VENT_STAGE)) return false;
		if (state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE) == GeothermalVentType.HYDROTHERMAL_VENT) return false;
		return state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_STAGE) == GeothermalVentStage.ERUPTING;
	}

	private AABB computeEruptionArea(Level level, Function<EruptionAreas, AABB> areaGetter) {
		final BlockState state = level.getBlockState(this.getBlockPos());
		if (!state.hasProperty(GeothermalVentBlock.FACING) || !state.hasProperty(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE)) return new AABB(this.getBlockPos());

		final Direction direction = state.getValue(GeothermalVentBlock.FACING);
		final GeothermalVentType geothermalVentType = state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE);
		final EruptionAreas areas = this.computeEruptionAreas(level, this.getBlockPos(), geothermalVentType, direction);
		return areaGetter.apply(areas).inflate(0.5D).move(direction.step().mul(0.5F));
	}

	public AABB computeEffectiveEruptionArea(Level level) {
		return this.computeEruptionArea(level, EruptionAreas::effectiveArea);
	}

	public AABB computeBaseEruptionArea(Level level) {
		return this.computeEruptionArea(level, EruptionAreas::baseArea);
	}

	private void tickEruption(Level level, BlockPos pos, GeothermalVentType geothermalVentType, Direction direction, boolean natural) {
		final boolean vent = geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT;
		final EruptionAreas areas = this.computeEruptionAreas(level, pos, geothermalVentType, direction);
		final AABB eruption = areas.baseArea();
		final AABB effectiveEruption = areas.effectiveArea();
		final AABB damagingEruption = areas.damagingArea();
		final AABB maxPossibleEruptionBox = areas.maxPossibleArea();

		final List<Entity> entities = level.getEntities(EntityTypeTest.forClass(Entity.class), maxPossibleEruptionBox, EFFECT_PREDICATE);
		final Vec3 ventStartPos = Vec3.atCenterOf(pos);

		if (!vent) {
			WindDisturbances.addIf(level, this, this::doesNotHaveEffectiveWindDisturbance, () -> GeothermalVentEffectiveWindDisturbance.INSTANCE);
			WindDisturbances.addIf(level, this, this::doesNotHaveBaseWindDisturbance, () -> GeothermalVentBaseWindDisturbance.INSTANCE);
		}

		final double eruptionDistance = vent ? VENT_DISTANCE : ERUPTION_DISTANCE;
		final Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
		for (Entity entity : entities) {
			final AABB boundingBox = entity.getBoundingBox();
			if (!eruption.intersects(boundingBox)) continue;

			boolean applyMovement = !entity.is(WWEntityTypeTags.GEOTHERMAL_VENT_CANNOT_PUSH);
			if (entity instanceof Player player) {
				if (player.getAbilities().flying) {
					applyMovement = false;
				} else if (direction == Direction.UP) {
					final Vec3 lastImpactPos = player.currentImpulseImpactPos;
					final Vec3 playerPos = player.position();
					player.setIgnoreFallDamageFromCurrentImpulse(
						true,
						new Vec3(
							playerPos.x(),
							lastImpactPos != null ? Math.min(lastImpactPos.y(), playerPos.y()) : playerPos.y(),
							playerPos.z()
						)
					);
				}
			} else if (entity instanceof AbstractArrow abstractArrow) {
				if (abstractArrow.isInGround()) applyMovement = false;
			}

			if (applyMovement) {
				final double intensity = (eruptionDistance - Math.min(entity.position().distanceTo(ventStartPos), eruptionDistance)) / eruptionDistance;
				final double pushIntensity = (effectiveEruption.intersects(boundingBox) && !vent ? EFFECTIVE_PUSH_INTENSITY : INEFFECTIVE_PUSH_INTENSITY) * (entity.is(WWEntityTypeTags.GEOTHERMAL_VENT_PUSHES_FURTHER) ? 1.5D : 1D);
				final double overallIntensity = intensity * pushIntensity;
				entity.addDeltaMovement(movement.scale(overallIntensity));
				entity.needsSync = true;
			}

			if (damagingEruption.intersects(boundingBox)) {
				if (applyMovement && level instanceof ServerLevel serverLevel) {
					for (ServerPlayer serverPlayer : serverLevel.getPlayers(player -> player.distanceToSqr(ventStartPos) < GeothermalVentPushMobTrigger.TRIGGER_DISTANCE_FROM_PLAYER)) {
						WWCriteria.GEOTHERMAL_VENT_PUSH_MOB_TRIGGER.get().trigger(serverPlayer, entity, !natural, geothermalVentType);
					}
				}

				final double damageIntensity = Math.max((eruptionDistance - Math.min(entity.position().distanceTo(ventStartPos), eruptionDistance)) / eruptionDistance, eruptionDistance * 0.1D);
				if (geothermalVentType == GeothermalVentType.LAVA && !entity.fireImmune()) {
					entity.igniteForTicks((int) (FIRE_TICKS_MAX * damageIntensity));
					entity.hurt(level.damageSources().inFire(), 1F);
				}
			}
		}

		for (BlockPos searchPos : BlockPos.betweenClosed(pos, areas.endPos())) {
			if (level.isClientSide() || !maxPossibleEruptionBox.contains(Vec3.atCenterOf(searchPos)) || !level.hasChunkAt(searchPos)) continue;

			final BlockState state = level.getBlockState(searchPos);
			if (geothermalVentType == GeothermalVentType.LAVA) {
				if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(BlockStateProperties.LIT)) {
					level.setBlockAndUpdate(searchPos, state.setValue(BlockStateProperties.LIT, true));
				}

				if ((state.is(BlockTags.CANDLES) || state.is(BlockTags.CANDLE_CAKES)) && state.hasProperty(BlockStateProperties.LIT)) {
					level.setBlockAndUpdate(searchPos, state.setValue(BlockStateProperties.LIT, true));
				}
			} else {
				if (state.is(BlockTags.FIRE)) {
					level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
					level.removeBlock(searchPos, false);
				}

				if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(BlockStateProperties.LIT)) {
					level.setBlockAndUpdate(searchPos, state.setValue(BlockStateProperties.LIT, false));
				}

				if ((state.is(BlockTags.CANDLES) || state.is(BlockTags.CANDLE_CAKES)) && state.hasProperty(BlockStateProperties.LIT)) {
					level.setBlockAndUpdate(searchPos, state.setValue(BlockStateProperties.LIT, false));
				}
			}
		}
	}

	private boolean doesNotHaveEffectiveWindDisturbance(DataAttachmentTarget source) {
		return WindDisturbances.noneMatch(source, WindDisturbances.type(WWWindDisturbances.GEOTHERMAL_VENT_EFFECTIVE.get()));
	}

	private boolean doesNotHaveBaseWindDisturbance(DataAttachmentTarget source) {
		return WindDisturbances.noneMatch(source, WindDisturbances.type(WWWindDisturbances.GEOTHERMAL_VENT_BASE.get()));
	}

	private AABB getPossibleEruptionBoundingBox(BlockPos pos, BlockPos maxEndPos) {
		final double xDifference = maxEndPos.getX() - pos.getX();
		final double yDifference = maxEndPos.getY() - pos.getY();
		final double zDifference = maxEndPos.getZ() - pos.getZ();
		final double endX = pos.getX() + (xDifference * this.eruptionProgress);
		final double endY = pos.getY() + (yDifference * this.eruptionProgress);
		final double endZ = pos.getZ() + (zDifference * this.eruptionProgress);

        return new AABB(
			Math.min(pos.getX(), endX),
			Math.min(pos.getY(), endY),
			Math.min(pos.getZ(), endZ),
			Math.max(pos.getX(), endX) + 1D,
			Math.max(pos.getY(), endY) + 1D,
			Math.max(pos.getZ(), endZ) + 1D
		);
	}

	public void advanceStage(Level level, BlockPos pos, BlockState state, GeothermalVentStage geothermalVentStage, boolean natural, RandomSource random) {
		if (geothermalVentStage == GeothermalVentStage.ERUPTING || !natural) {
			this.eruptionProgress = 0F;
			this.setStageAndCooldown(level, pos, state, GeothermalVentStage.DORMANT, random);
		} else if (geothermalVentStage == GeothermalVentStage.DORMANT) {
			this.setStageAndCooldown(level, pos, state, GeothermalVentStage.ACTIVE, random);
		} else if (geothermalVentStage == GeothermalVentStage.ACTIVE) {
			if (!canErupt(level, pos, natural, random)) return;
			this.setStageAndCooldown(level, pos, state, GeothermalVentStage.ERUPTING, random);
		}
	}

	private boolean canErupt(Level level, BlockPos pos, boolean natural, RandomSource random) {
		if (!natural) return true;

		final Vec3 center = Vec3.atCenterOf(pos);
		final Player player = level.getNearestPlayer(center.x(), center.y(), center.z(), -1D, entity -> !entity.isSpectator() && entity.isAlive());
		if (player != null) {
			final double distance = player.distanceToSqr(center);
			if (Math.sqrt(distance) <= 48) return random.nextInt(((int) (distance * 1.5D)) + 5) == 0;
		}
		return false;
	}

	public void setDormant(Level level, BlockPos pos, BlockState state, RandomSource random) {
		this.setStageAndCooldown(level, pos, state, GeothermalVentStage.DORMANT, random);
	}

	public void setActive(Level level, BlockPos pos, BlockState state, RandomSource random) {
		this.setStageAndCooldown(level, pos, state, GeothermalVentStage.ACTIVE, random);
	}

	public void setStageAndCooldown(Level level, BlockPos pos, BlockState state, GeothermalVentStage geothermalVentStage, RandomSource random) {
		level.setBlockAndUpdate(pos, state.setValue(GeothermalVentBlock.GEOTHERMAL_VENT_STAGE, geothermalVentStage));
		if (geothermalVentStage == GeothermalVentStage.ACTIVE) {
			this.ticksUntilNextEvent = random.nextInt(MIN_ACTIVE_TICKS, MAX_ACTIVE_TICKS);
		} else if (geothermalVentStage != GeothermalVentStage.ERUPTING) { // Eruption duration is set in serverTick to work with Redstone properly
			this.eruptionProgress = 0F;
			this.ticksUntilNextEvent = random.nextInt(MIN_DORMANT_TICKS, MAX_DORMANT_TICKS);
		}
	}

	@ClientOnly
	public void tickClient(Level level, BlockPos pos, BlockState state, RandomSource random) {
		final GeothermalVentType geothermalVentType = state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_TYPE);
		if (!GeothermalVentBlock.isActive(geothermalVentType)) return;

		final GeothermalVentStage geothermalVentStage = state.getValue(GeothermalVentBlock.GEOTHERMAL_VENT_STAGE);
		final Direction direction = state.getValue(GeothermalVentBlock.FACING);
		final boolean natural = state.getValue(GeothermalVentBlock.NATURAL);
		if (geothermalVentStage == GeothermalVentStage.ERUPTING || geothermalVentType == GeothermalVentType.HYDROTHERMAL_VENT) {
			this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
			this.tickEruption(level, pos, geothermalVentType, direction, natural);
			GeothermalVentParticleSpawner.spawnEruptionParticles(level, pos, geothermalVentType, direction, random);
		}
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		output.putBoolean("HasRunFirstCheck", this.hasRunFirstCheck);
		output.putInt("TicksUntilNextEvent", this.ticksUntilNextEvent);
		output.putFloat("EruptionProgress", this.eruptionProgress);
	}

	@Override
	public void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.hasRunFirstCheck = input.getBooleanOr("HasRunFirstCheck", false);
		this.ticksUntilNextEvent = input.getIntOr("TicksUntilNextEvent", 0);
		this.eruptionProgress = input.getFloatOr("EruptionProgress", 0F);
	}

	private record EruptionAreas(AABB baseArea, AABB effectiveArea, AABB damagingArea, AABB maxPossibleArea, BlockPos endPos) {}
}
