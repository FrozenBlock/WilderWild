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

package net.frozenblock.wilderwild.block.entity;

import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.lib.wind.api.WindDisturbance;
import net.frozenblock.lib.wind.api.WindDisturbanceLogic;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.state.properties.GeyserStage;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.mod_compat.FrozenLibIntegration;
import net.frozenblock.wilderwild.particle.options.WindParticleOptions;
import net.frozenblock.wilderwild.registry.WWBlockEntityTypes;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.tag.WWEntityTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
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
	public static final double ERUPTION_DISTANCE = 6D;
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
	private int tickUntilNextEvent;
	private float eruptionProgress;

	public GeyserBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(WWBlockEntityTypes.GEYSER, pos, state);
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
			if (geyserStage == GeyserStage.ERUPTING) {
				if (this.eruptionProgress == 0F) {
					this.tickUntilNextEvent = natural ? random.nextInt(MIN_ERUPTION_TICKS, MAX_ERUPTION_TICKS) : ERUPTION_TICKS_UNNATURAL;
					level.playSound(null, pos, geyserType.getEruptionSound(), SoundSource.BLOCKS, 0.7F, 0.9F + (random.nextFloat() * 0.2F));
				}
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
				this.handleEruption(level, pos, geyserType, direction);
			}
			this.tickUntilNextEvent -= 1;
			if (this.tickUntilNextEvent <= 0) {
				this.advanceStage(level, pos, state, geyserStage, natural, random);
			}
		} else {
			this.setDormant(level, pos, state, random);
		}
		this.setChanged();
	}

	private static boolean canEruptionPassThrough(Level level, BlockPos pos, @NotNull BlockState state, @NotNull Direction direction) {
		return !((state.isFaceSturdy(level, pos, direction.getOpposite(), SupportType.CENTER) && !state.is(WWBlockTags.GEYSER_CAN_PASS_THROUGH))
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

	private void handleEruption(Level level, @NotNull BlockPos pos, GeyserType geyserType, Direction direction) {
		BlockPos maxEndPos = pos.relative(direction, (int) ERUPTION_DISTANCE);
		Optional<BlockPos> cutoffPos = Optional.empty();
		Optional<BlockPos> damageCutoffPos = Optional.empty();
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < 5; i++) {
			if (level.hasChunkAt(mutablePos.move(direction))) {
				BlockState state = level.getBlockState(mutablePos);
				if (!canEruptionPassThrough(level, mutablePos, state, direction)) {
					break;
				}
				boolean mismatchesAir = geyserType == GeyserType.AIR && !state.getFluidState().isEmpty();
				boolean mismatchesWater = geyserType == GeyserType.WATER && !state.getFluidState().is(Fluids.WATER);
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

		if (level instanceof ServerLevel serverLevel) {
			WindManager windManager = WindManager.getWindManager(serverLevel);
			windManager.addWindDisturbance(effectiveWindDisturbance);
			windManager.addWindDisturbance(baseWindDisturbance);
		} else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			addWindDisturbanceToClient(effectiveWindDisturbance);
			addWindDisturbanceToClient(baseWindDisturbance);
		}

		Vec3 movement = Vec3.atLowerCornerOf(direction.getUnitVec3i());
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
				if (applyMovement) {
					double intensity = (ERUPTION_DISTANCE - Math.min(entity.position().distanceTo(geyserStartPos), ERUPTION_DISTANCE)) / ERUPTION_DISTANCE;
					double pushIntensity = (effectiveEruption.intersects(boundingBox) ? EFFECTIVE_PUSH_INTENSITY : INEFFECTIVE_PUSH_INTENSITY) * (entity.getType().is(WWEntityTags.GEYSER_PUSHES_FURTHER) ? 1.5D : 1D);
					double overallIntensity = intensity * pushIntensity;
					Vec3 deltaMovement = entity.getDeltaMovement().add(movement.scale(overallIntensity));
					entity.setDeltaMovement(deltaMovement);
				}
				if (damagingEruption.intersects(boundingBox)) {
					double damageIntensity = Math.max((ERUPTION_DISTANCE - Math.min(entity.position().distanceTo(geyserStartPos), ERUPTION_DISTANCE)) / ERUPTION_DISTANCE, ERUPTION_DISTANCE * 0.1D);
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
						if (!level.isClientSide()) {
							level.levelEvent(null, LevelEvent.SOUND_EXTINGUISH_FIRE, pos, 0);
						}
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
			Player player = level.getNearestPlayer(gesyerCenter.x(), gesyerCenter.y(), gesyerCenter.z(), -1D, entity -> !entity.isSpectator() && entity.isAlive());
			if (player != null) {
				double distance = player.distanceToSqr(gesyerCenter);
				if (Math.sqrt(distance) <= 48) {
					return random.nextInt(((int) (distance * 1.5D)) + 5) != 0;
				}
			}
			return false;
		}
		return true;
	}

	public void setDormant(Level level, BlockPos pos, BlockState state, RandomSource random) {
		this.setStageAndCooldown(level, pos, state, GeyserStage.DORMANT, random);
	}

	public void setStageAndCooldown(@NotNull Level level, BlockPos pos, @NotNull BlockState state, GeyserStage geyserStage, RandomSource random) {
		level.setBlock(pos, state.setValue(GeyserBlock.GEYSER_STAGE, geyserStage), Block.UPDATE_ALL);
		if (geyserStage == GeyserStage.ACTIVE) {
			this.tickUntilNextEvent = random.nextInt(MIN_ACTIVE_TICKS, MAX_ACTIVE_TICKS);
		} else if (geyserStage != GeyserStage.ERUPTING) { // Eruption duration is set in serverTick to work with Redstone properly
			this.tickUntilNextEvent = random.nextInt(MIN_DORMANT_TICKS, MAX_DORMANT_TICKS);
		}
	}

	@Environment(EnvType.CLIENT)
	public void tickClient(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, RandomSource random) {
		GeyserType geyserType = state.getValue(GeyserBlock.GEYSER_TYPE);
		GeyserStage geyserStage = state.getValue(GeyserBlock.GEYSER_STAGE);
		Direction direction = state.getValue(GeyserBlock.FACING);
		if (GeyserBlock.isActive(geyserType)) {
			if (geyserStage == GeyserStage.ERUPTING) {
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + ERUPTION_PROGRESS_INTERVAL);
				this.handleEruption(level, pos, geyserType, direction);
				spawnEruptionParticles(level, pos, geyserType, direction, random);
			}
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@NotNull
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
		return this.saveWithoutMetadata(provider);
	}

	@Override
	protected void saveAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.saveAdditional(tag, provider);
		tag.putBoolean("HasRunFirstCheck", this.hasRunFirstCheck);
		tag.putInt("TicksUntilNextEvent", this.tickUntilNextEvent);
		tag.putFloat("EruptionProgress", this.eruptionProgress);
	}

	@Override
	public void loadAdditional(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.loadAdditional(tag, provider);
		this.hasRunFirstCheck = tag.getBoolean("HasRunFirstCheck");
		this.tickUntilNextEvent = tag.getInt("TicksUntilNextEvent");
		this.eruptionProgress = tag.getFloat("EruptionProgress");
	}

	@Environment(EnvType.CLIENT)
	private static void addWindDisturbanceToClient(@NotNull WindDisturbance windDisturbance) {
		ClientWindManager.addWindDisturbance(windDisturbance);
	}

	public static final float DORMANT_BUBBLE_CHANCE = 0.0195F;
	public static final double DORMANT_BUBBLE_MIN_VELOCITY = 0.09D;
	public static final double DORMANT_BUBBLE_MAX_VELOCITY = 0.12D;
	public static final float ACTIVE_BUBBLE_CHANCE = 0.1F;
	public static final int MIN_ACTIVE_BUBBLES = 1;
	public static final int MAX_ACTIVE_BUBBLES = 3;
	public static final double ACTIVE_BUBBLE_MIN_VELOCITY_OFFSET = 0.4D;
	public static final double ACTIVE_BUBBLE_MAX_VELOCITY_OFFSET = 0.8D;
	public static final double ACTIVE_BUBBLE_RANDOM_VELOCITY = 0.1D;
	public static final float ACTIVE_LAVA_CHANCE = 0.0575F;
	public static final int MIN_ACTIVE_LAVA = 1;
	public static final int MAX_ACTIVE_LAVA = 2;
	public static final double ACTIVE_LAVA_MIN_VELOCITY = 0.06D;
	public static final double ACTIVE_LAVA_MAX_VELOCITY = 0.1D;
	public static final double ACTIVE_LAVA_RANDOM_VELOCITY = 0.1D;
	public static final float ACTIVE_FLAME_CHANCE = 0.0875F;
	public static final double ACTIVE_FLAME_MIN_VELOCITY = 0.2D;
	public static final double ACTIVE_FLAME_MAX_VELOCITY = 0.4D;
	public static final double ACTIVE_FLAME_RANDOM_VELOCITY = 0.1D;
	public static final float ACTIVE_DUST_CHANCE = 0.185F;
	public static final int MIN_ACTIVE_DUST = 1;
	public static final int MAX_ACTIVE_DUST = 4;
	public static final double ACTIVE_DUST_MIN_VELOCITY = 0.06D;
	public static final double ACTIVE_DUST_MAX_VELOCITY = 0.4D;
	public static final double ACTIVE_DUST_RANDOM_VELOCITY = 0.4D;

	public static void spawnDormantParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType == GeyserType.WATER && random.nextFloat() <= DORMANT_BUBBLE_CHANCE) {
			Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
			Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, DORMANT_BUBBLE_MIN_VELOCITY, DORMANT_BUBBLE_MAX_VELOCITY);
			level.addParticle(
				ParticleTypes.BUBBLE,
				false,
				particlePos.x,
				particlePos.y,
				particlePos.z,
				particleVelocity.x,
				particleVelocity.y,
				particleVelocity.z
			);
		}
	}

	public static void spawnActiveParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= ACTIVE_BUBBLE_CHANCE) {
				int count = random.nextInt(MIN_ACTIVE_BUBBLES, MAX_ACTIVE_BUBBLES);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_BUBBLE_MIN_VELOCITY_OFFSET, ACTIVE_BUBBLE_MAX_VELOCITY_OFFSET);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, ACTIVE_BUBBLE_RANDOM_VELOCITY));
					level.addParticle(
						ParticleTypes.BUBBLE,
						false,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
		} else {
			if (geyserType == GeyserType.LAVA) {
				if (random.nextFloat() <= ACTIVE_LAVA_CHANCE) {
					int count = random.nextInt(MIN_ACTIVE_LAVA, MAX_ACTIVE_LAVA);
					for (int i = 0; i < count; i++) {
						Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
						Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_LAVA_MIN_VELOCITY, ACTIVE_LAVA_MAX_VELOCITY);
						particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, ACTIVE_LAVA_RANDOM_VELOCITY));
						level.addParticle(
							ParticleTypes.LARGE_SMOKE,
							false,
							particlePos.x,
							particlePos.y,
							particlePos.z,
							particleVelocity.x,
							particleVelocity.y,
							particleVelocity.z
						);
					}
				}
				if (random.nextFloat() <= ACTIVE_FLAME_CHANCE) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_FLAME_MIN_VELOCITY, ACTIVE_FLAME_MAX_VELOCITY);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, ACTIVE_FLAME_RANDOM_VELOCITY));
					level.addParticle(
						ParticleTypes.FLAME,
						true,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
			if (random.nextFloat() <= ACTIVE_DUST_CHANCE) {
				int count = random.nextInt(MIN_ACTIVE_DUST, MAX_ACTIVE_DUST);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_DUST_MIN_VELOCITY, ACTIVE_DUST_MAX_VELOCITY);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, ACTIVE_DUST_RANDOM_VELOCITY));
					level.addParticle(
						ParticleTypes.DUST_PLUME,
						false,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public static void spawnEruptionParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		Minecraft client = Minecraft.getInstance();
		ParticleStatus particleStatus = client.options.particles().get();
		ParticleEngine particleEngine = client.particleEngine;
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= 0.4F) { // Bubble
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 1.7D, 4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.15D));
					level.addParticle(
						ParticleTypes.BUBBLE,
						random.nextBoolean(),
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
		} else {
			if (random.nextFloat() <= 0.5F) { // Dust Plume
				int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.8D, 1.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addParticle(
						ParticleTypes.DUST_PLUME,
						false,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
		}
		if (geyserType == GeyserType.LAVA) {
			if (random.nextFloat() <= 0.7F) { // Large Smoke
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.9D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addParticle(
						ParticleTypes.LARGE_SMOKE,
						false,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
			int count = random.nextInt(1, 5);
			for (int i = 0; i < count; i++) {
				if (random.nextFloat() <= 0.9F) { // Flame
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.1D, 0.6D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.225D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.FLAME,
						false,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x,
						particleVelocity.y,
						particleVelocity.z
					);
				}
			}
			int lavaCount = random.nextInt(1, 3);
			for (int i = 0; i < lavaCount; i++) { // Lava
				if (particleStatus == ParticleStatus.DECREASED & random.nextBoolean()) {
					break;
				} else if (particleStatus == ParticleStatus.MINIMAL && random.nextFloat() <= 0.675F) {
					break;
				}
				Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.6D, 0.8D);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.2D));
				Particle lavaParticle = particleEngine.createParticle(
					ParticleTypes.LAVA,
					particlePos.x,
					particlePos.y,
					particlePos.z,
					particleVelocity.x,
					particleVelocity.y,
					particleVelocity.z
				);
				if (lavaParticle != null) {
					lavaParticle.xd = particleVelocity.x;
					lavaParticle.yd = particleVelocity.y;
					lavaParticle.zd = particleVelocity.z;
				}
			}
		} else {
			int windCount = random.nextInt(0, 2);
			for (int i = 0; i < windCount; i++) { // WIND
				if (particleStatus == ParticleStatus.DECREASED & random.nextBoolean()) {
					break;
				} else if (particleStatus == ParticleStatus.MINIMAL && random.nextFloat() <= 0.675F) {
					break;
				}
				Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.6D, 0.8D);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.2D));
				particleEngine.createParticle(
					new WindParticleOptions(12, particleVelocity),
					particlePos.x,
					particlePos.y,
					particlePos.z,
					0,
					0,
					0
				);
			}
		}
	}

}
