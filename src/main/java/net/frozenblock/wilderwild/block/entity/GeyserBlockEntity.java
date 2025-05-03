/*
 * Copyright 2025 FrozenBlock
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

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.advancement.GeyserPushMobTrigger;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.impl.GeyserParticleHandler;
import net.frozenblock.wilderwild.block.state.properties.GeyserStage;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.registry.WWCriteria;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class GeyserBlockEntity extends BlockEntity {
	private static final WindDisturbanceLogic<GeyserBlockEntity> DUMMY_WIND_LOGIC = new WindDisturbanceLogic<>((source, level1, windOrigin, affectedArea, windTarget) -> WindDisturbance.DUMMY_RESULT);
	private static final TargetingConditions TARGETING_CONDITIONS = TargetingConditions.forNonCombat().ignoreInvisibilityTesting().ignoreLineOfSight().range(32D);
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
	public static final double EFFECTIVE_ADDITIONAL_WIND_INTENSITY = 0.5D;
	public static final double BASE_WIND_INTENSITY = 0.5D;
	public static final int FIRE_TICKS_MAX = 260;
	private boolean hasRunFirstCheck = false;
	private int ticksUntilNextEvent;
	private float eruptionProgress;

	public GeyserBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(WWBlockEntityTypes.GEYSER, pos, state);
	}

	@Override
	public boolean triggerEvent(int eventId, int data) {
		if (eventId == 1 && this.level.isClientSide) {
			this.eruptionProgress = 0F;
			this.ticksUntilNextEvent = data;
			return true;
		}
		return super.triggerEvent(eventId, data);
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, RandomSource random) {
		GeyserType geyserType = state.getValue(GeyserBlock.GEYSER_TYPE);
		GeyserStage geyserStage = state.getValue(GeyserBlock.GEYSER_STAGE);
		Direction direction = state.getValue(GeyserBlock.FACING);
		boolean natural = state.getValue(GeyserBlock.NATURAL);

		if (!this.hasRunFirstCheck) {
			level.scheduleTick(pos, this.getBlockState().getBlock(), level.random.nextInt(TICK_DELAY_START_MIN, TICK_DELAY_START_MAX));
			this.hasRunFirstCheck = true;
		} else if (GeyserBlock.isActive(geyserType)) {
			if (geyserType == GeyserType.HYDROTHERMAL_VENT) {
				this.eruptionProgress = 1F;
				this.handleEruption(level, pos, geyserType, direction, natural);
				this.setActive(level, pos, state, random);
			} else if (geyserStage == GeyserStage.ERUPTING) {
				if (this.eruptionProgress == 0F) {
					this.ticksUntilNextEvent = natural ? random.nextInt(MIN_ERUPTION_TICKS, MAX_ERUPTION_TICKS) : ERUPTION_TICKS_UNNATURAL;
					level.playSound(null, pos, geyserType.getEruptionSound(), SoundSource.BLOCKS, 0.7F, 0.9F + (random.nextFloat() * 0.2F));
					this.level.blockEvent(pos, state.getBlock(), 1, this.ticksUntilNextEvent);
				}
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
				this.handleEruption(level, pos, geyserType, direction, natural);
			}
			this.ticksUntilNextEvent -= 1;
			if (this.ticksUntilNextEvent <= 0) {
				this.advanceStage(level, pos, state, geyserStage, natural, random);
			}
		} else {
			this.setDormant(level, pos, state, random);
		}
	}

	public static boolean canEruptionPassThrough(LevelAccessor level, BlockPos pos, @NotNull BlockState state, @NotNull Direction direction) {
		return !((state.isFaceSturdy(level, pos, direction.getOpposite(), SupportType.CENTER)
			&& !state.is(WWBlockTags.GEYSER_CAN_PASS_THROUGH))
			|| state.is(WWBlockTags.GEYSER_CANNOT_PASS_THROUGH));
	}

	@NotNull
	@Contract("_, _ -> new")
	private static AABB aabb(@NotNull BlockPos startPos, @NotNull BlockPos endPos) {
		return new AABB(
			Math.min(startPos.getX(), endPos.getX()),
			Math.min(startPos.getY(), endPos.getY()),
			Math.min(startPos.getZ(), endPos.getZ()),
			Math.max(startPos.getX(), endPos.getX()) + 1D,
			Math.max(startPos.getY(), endPos.getY()) + 1D,
			Math.max(startPos.getZ(), endPos.getZ()) + 1D
		);
	}

	private void handleEruption(Level level, @NotNull BlockPos pos, GeyserType geyserType, Direction direction, boolean natural) {
		BlockPos maxEndPos = pos.relative(direction, (int) ERUPTION_DISTANCE);
		Optional<BlockPos> cutoffPos = Optional.empty();
		Optional<BlockPos> damageCutoffPos = Optional.empty();
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		boolean vent = geyserType == GeyserType.HYDROTHERMAL_VENT;
		for (int i = 0; i < (vent ? VENT_DISTANCE_IN_BLOCKS : ERUPTION_DISTANCE_IN_BLOCKS); i++) {
			if (level.hasChunkAt(mutablePos.move(direction))) {
				BlockState state = level.getBlockState(mutablePos);
				if (!canEruptionPassThrough(level, mutablePos, state, direction)) break;

				boolean mismatchesAir = geyserType == GeyserType.AIR && !state.getFluidState().isEmpty();
				boolean mismatchesWater = geyserType.isWater() && !state.getFluidState().is(Fluids.WATER);
				boolean mismatchesLava = geyserType == GeyserType.LAVA && !state.getFluidState().is(Fluids.LAVA);
				if (mismatchesAir || mismatchesWater || mismatchesLava) {
					if (cutoffPos.isEmpty()) cutoffPos = Optional.of(mutablePos.immutable());
				}
				if (geyserType == GeyserType.LAVA && state.getFluidState().is(FluidTags.WATER)) {
					if (damageCutoffPos.isEmpty()) damageCutoffPos = Optional.of(mutablePos.immutable());
				}
			}
		}
		AABB eruption = aabb(pos, mutablePos.immutable());
		mutablePos.move(direction.getOpposite());

		AABB effectiveEruption = cutoffPos.map(blockPos ->
				aabb(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> aabb(pos, mutablePos.immutable()));
		AABB damagingEruption = damageCutoffPos.map(blockPos ->
				aabb(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> aabb(pos, mutablePos.immutable()));

		AABB maxPossibleEruptionBox = getPossibleEruptionBoundingBox(pos, maxEndPos);
		List<Entity> entities = level.getEntities(
			EntityTypeTest.forClass(Entity.class),
			maxPossibleEruptionBox,
			EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS)
		);
		Vec3 geyserStartPos = Vec3.atCenterOf(pos);

		WindDisturbance<GeyserBlockEntity> effectiveWindDisturbance = new WindDisturbance<GeyserBlockEntity>(
			Optional.of(this),
			geyserStartPos,
			effectiveEruption.inflate(0.5D).move(direction.step().mul(0.5F)),
			WindDisturbanceLogic.getWindDisturbanceLogic(FrozenLibIntegration.GEYSER_EFFECTIVE_WIND_DISTURBANCE).orElse(DUMMY_WIND_LOGIC)
		);
		WindDisturbance<GeyserBlockEntity> baseWindDisturbance = new WindDisturbance<GeyserBlockEntity>(
			Optional.of(this),
			geyserStartPos,
			eruption.inflate(0.5D).move(direction.step().mul(0.5F)),
			WindDisturbanceLogic.getWindDisturbanceLogic(FrozenLibIntegration.GEYSER_BASE_WIND_DISTURBANCE).orElse(DUMMY_WIND_LOGIC)
		);

		if (!vent) {
			if (level instanceof ServerLevel serverLevel) {
				WindManager windManager = WindManager.getWindManager(serverLevel);
				windManager.addWindDisturbance(effectiveWindDisturbance);
				windManager.addWindDisturbance(baseWindDisturbance);
			} else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
				addWindDisturbanceToClient(effectiveWindDisturbance);
				addWindDisturbanceToClient(baseWindDisturbance);
			}
		}

		Vec3 movement = Vec3.atLowerCornerOf(direction.getNormal());
		for (Entity entity : entities) {
			AABB boundingBox = entity.getBoundingBox();
			if (eruption.intersects(boundingBox)) {
				boolean applyMovement = true;
				if (entity instanceof Player player) {
					if (!player.getAbilities().flying) {
						if (direction == Direction.UP) {
							Vec3 lastImpactPos = player.currentImpulseImpactPos;
							Vec3 playerPos = player.position();
							player.currentImpulseImpactPos = new Vec3(
								playerPos.x(),
								lastImpactPos != null ? Math.min(lastImpactPos.y(), playerPos.y()) : playerPos.y(),
								playerPos.z()
							);
							player.setIgnoreFallDamageFromCurrentImpulse(true);
						}
					} else {
						applyMovement = false;
					}
				}
				double eruptionDistance = vent ? VENT_DISTANCE : ERUPTION_DISTANCE;

				if (applyMovement) {
					double intensity = (eruptionDistance - Math.min(entity.position().distanceTo(geyserStartPos), eruptionDistance)) / eruptionDistance;
					double pushIntensity = (effectiveEruption.intersects(boundingBox) && !vent ? EFFECTIVE_PUSH_INTENSITY : INEFFECTIVE_PUSH_INTENSITY) * (entity.getType().is(WWEntityTags.GEYSER_PUSHES_FURTHER) ? 1.5D : 1D);
					double overallIntensity = intensity * pushIntensity;
					Vec3 deltaMovement = entity.getDeltaMovement().add(movement.scale(overallIntensity));
					entity.setDeltaMovement(deltaMovement);
				}
				if (damagingEruption.intersects(boundingBox)) {
					if (level instanceof ServerLevel serverLevel) {
						for (ServerPlayer serverPlayer :serverLevel.getPlayers(serverPlayerx -> serverPlayerx.distanceToSqr(geyserStartPos) < GeyserPushMobTrigger.TRIGGER_DISTANCE_FROM_PLAYER)) {
							WWCriteria.GEYSER_PUSH_MOB_TRIGGER.trigger(serverPlayer, entity, !natural, geyserType);
						}
					}

					double damageIntensity = Math.max((eruptionDistance - Math.min(entity.position().distanceTo(geyserStartPos), eruptionDistance)) / eruptionDistance, eruptionDistance * 0.1D);
					if (geyserType == GeyserType.LAVA) {
						if (!entity.fireImmune()) {
							entity.igniteForTicks((int) (FIRE_TICKS_MAX * damageIntensity));
							entity.hurt(level.damageSources().inFire(), 1F);
						}
					}
				}
			}
		}

		for (BlockPos blockPos : BlockPos.betweenClosed(pos, mutablePos.immutable())) {
			if (maxPossibleEruptionBox.contains(Vec3.atCenterOf(blockPos)) && level.hasChunkAt(blockPos)) {
				BlockState state = level.getBlockState(blockPos);

				if (geyserType == GeyserType.LAVA) {
					if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(BlockStateProperties.LIT)) {
						level.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.LIT, true));
					}

					if ((state.is(BlockTags.CANDLES) || state.is(BlockTags.CANDLE_CAKES)) && state.hasProperty(BlockStateProperties.LIT)) {
						level.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.LIT, true));
					}
				} else {
					if (state.is(BlockTags.FIRE)) {
						if (!level.isClientSide()) level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
						level.removeBlock(blockPos, false);
					}

					if (state.is(BlockTags.CAMPFIRES) && state.hasProperty(BlockStateProperties.LIT)) {
						level.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.LIT, false));
					}

					if ((state.is(BlockTags.CANDLES) || state.is(BlockTags.CANDLE_CAKES)) && state.hasProperty(BlockStateProperties.LIT)) {
						level.setBlockAndUpdate(blockPos, state.setValue(BlockStateProperties.LIT, false));
					}
				}
			}
		}
	}

	@NotNull
	private AABB getPossibleEruptionBoundingBox(@NotNull BlockPos pos, @NotNull BlockPos maxEndPos) {
		double xDifference = maxEndPos.getX() - pos.getX();
		double yDifference = maxEndPos.getY() - pos.getY();
		double zDifference = maxEndPos.getZ() - pos.getZ();
		double endX = pos.getX() + (xDifference * this.eruptionProgress);
		double endY = pos.getY() + (yDifference * this.eruptionProgress);
		double endZ = pos.getZ() + (zDifference * this.eruptionProgress);

        return new AABB(
			Math.min(pos.getX(), endX),
			Math.min(pos.getY(), endY),
			Math.min(pos.getZ(), endZ),
			Math.max(pos.getX(), endX) + 1D,
			Math.max(pos.getY(), endY) + 1D,
			Math.max(pos.getZ(), endZ) + 1D
		);
	}

	public void advanceStage(Level level, BlockPos pos, @NotNull BlockState state, GeyserStage geyserStage, boolean natural, RandomSource random) {
		if (geyserStage == GeyserStage.ERUPTING || !natural) {
			this.eruptionProgress = 0F;
			this.setStageAndCooldown(level, pos, state, GeyserStage.DORMANT, random);
		} else if (geyserStage == GeyserStage.DORMANT) {
			this.setStageAndCooldown(level, pos, state, GeyserStage.ACTIVE, random);
		} else if (geyserStage == GeyserStage.ACTIVE) {
			if (!canErupt(level, pos, natural, random)) return;
			this.setStageAndCooldown(level, pos, state, GeyserStage.ERUPTING, random);
		}
	}

	private boolean canErupt(Level level, BlockPos pos, boolean natural, RandomSource random) {
		if (natural) {
			Vec3 gesyerCenter = Vec3.atCenterOf(pos);
			Player player = level.getNearestPlayer(TARGETING_CONDITIONS, gesyerCenter.x(), gesyerCenter.y(), gesyerCenter.z());
			if (player != null) {
				double distance = player.distanceToSqr(gesyerCenter);
				if (Math.sqrt(distance) <= 48) return random.nextInt(((int) (distance * 1.5D)) + 5) == 0;
			}
			return false;
		}
		return true;
	}

	public void setDormant(Level level, BlockPos pos, BlockState state, RandomSource random) {
		this.setStageAndCooldown(level, pos, state, GeyserStage.DORMANT, random);
	}

	public void setActive(Level level, BlockPos pos, BlockState state, RandomSource random) {
		this.setStageAndCooldown(level, pos, state, GeyserStage.ACTIVE, random);
	}

	public void setStageAndCooldown(@NotNull Level level, BlockPos pos, @NotNull BlockState state, GeyserStage geyserStage, RandomSource random) {
		level.setBlockAndUpdate(pos, state.setValue(GeyserBlock.GEYSER_STAGE, geyserStage));
		if (geyserStage == GeyserStage.ACTIVE) {
			this.ticksUntilNextEvent = random.nextInt(MIN_ACTIVE_TICKS, MAX_ACTIVE_TICKS);
		} else if (geyserStage != GeyserStage.ERUPTING) { // Eruption duration is set in serverTick to work with Redstone properly
			this.ticksUntilNextEvent = random.nextInt(MIN_DORMANT_TICKS, MAX_DORMANT_TICKS);
		}
	}

	@Environment(EnvType.CLIENT)
	public void tickClient(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, RandomSource random) {
		GeyserType geyserType = state.getValue(GeyserBlock.GEYSER_TYPE);
		GeyserStage geyserStage = state.getValue(GeyserBlock.GEYSER_STAGE);
		Direction direction = state.getValue(GeyserBlock.FACING);
		boolean natural = state.getValue(GeyserBlock.NATURAL);
		if (GeyserBlock.isActive(geyserType)) {
			if (geyserStage == GeyserStage.ERUPTING || geyserType == GeyserType.HYDROTHERMAL_VENT) {
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
				this.handleEruption(level, pos, geyserType, direction, natural);
				GeyserParticleHandler.spawnEruptionParticles(level, pos, geyserType, direction, random);
			}
		}
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putBoolean("HasRunFirstCheck", this.hasRunFirstCheck);
		tag.putInt("TicksUntilNextEvent", this.ticksUntilNextEvent);
		tag.putFloat("EruptionProgress", this.eruptionProgress);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.hasRunFirstCheck = tag.getBoolean("HasRunFirstCheck");
		this.ticksUntilNextEvent = tag.getInt("TicksUntilNextEvent");
		this.eruptionProgress = tag.getFloat("EruptionProgress");
	}

	@Environment(EnvType.CLIENT)
	private static void addWindDisturbanceToClient(@NotNull WindDisturbance windDisturbance) {
		ClientWindManager.addWindDisturbance(windDisturbance);
	}

}
