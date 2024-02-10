/*
 * Copyright 2023-2024 FrozenBlock
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.impl.GeyserType;
import net.frozenblock.wilderwild.registry.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SupportType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class GeyserBlockEntity extends BlockEntity {
	private boolean hasRunFirstCheck = false;
	private int tickUntilNextEvent;
	private float eruptionProgress;
	private GeyserStage geyserStage = GeyserStage.DORMANT;

	public GeyserBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		super(RegisterBlockEntities.GEYSER, pos, state);
	}

	public void tickServer(@NotNull Level level, @NotNull BlockPos pos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (!this.hasRunFirstCheck) {
			level.scheduleTick(pos, this.getBlockState().getBlock(), level.random.nextInt(20, 100));
			this.hasRunFirstCheck = true;
		} else if (!GeyserBlock.isInactive(geyserType)) {
			if (this.geyserStage == GeyserStage.ERUPTING) {
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + 0.1F);
				this.handleEruption(level, pos, geyserType, direction);
			}
			this.tickUntilNextEvent -= 1;
			if (this.tickUntilNextEvent <= 0) {
				this.advanceStage(random);
				if (this.geyserStage == GeyserStage.ERUPTING) {
					level.playSound(null, pos, geyserType.getEruptionSound(), SoundSource.BLOCKS, 0.7F, 0.9F + (random.nextFloat() * 0.2F));
				}
			}
		} else {
			this.setDormant(random);
		}
		this.updateSync();
	}

	private void handleEruption(Level level, @NotNull BlockPos pos, GeyserType geyserType, Direction direction) {
		BlockPos maxEndPos = pos.relative(direction, 5);
		Optional<BlockPos> cutoffPos = Optional.empty();
		Optional<BlockPos> damageCutoffPos = Optional.empty();
		BlockPos.MutableBlockPos mutablePos = pos.mutable();
		for (int i = 0; i < 5; i++) {
			BlockState state = level.getBlockState(mutablePos.move(direction));
			if (state.isFaceSturdy(level, mutablePos, direction.getOpposite(), SupportType.CENTER)) {
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
		AABB eruption = AABB.encapsulatingFullBlocks(pos, mutablePos.immutable());
		AABB effectiveEruption = cutoffPos.map(blockPos ->
				AABB.encapsulatingFullBlocks(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> AABB.encapsulatingFullBlocks(pos, mutablePos.immutable().relative(direction.getOpposite())));
		AABB damagingEruption = damageCutoffPos.map(blockPos ->
				AABB.encapsulatingFullBlocks(pos, blockPos.immutable().relative(direction.getOpposite())))
			.orElseGet(() -> AABB.encapsulatingFullBlocks(pos, mutablePos.immutable().relative(direction.getOpposite())));

		List<Entity> entities = level.getEntities(
			EntityTypeTest.forClass(Entity.class),
			getPossibleEruptionBoundingBox(pos, maxEndPos),
			EntitySelector.ENTITY_STILL_ALIVE.and(EntitySelector.NO_SPECTATORS)
		);
		Vec3 geyserStartPos = Vec3.atCenterOf(pos);

		for (Entity entity : entities) {
			AABB boundingBox = entity.getBoundingBox();
			if (eruption.intersects(boundingBox)) {
				Vec3 movement = Vec3.atLowerCornerOf(direction.getNormal());
				double intensity = (5D - Math.min(entity.position().distanceTo(geyserStartPos), 5D)) / 5D;
				entity.addDeltaMovement(movement.scale(intensity * 0.1D));
				if (effectiveEruption.intersects(boundingBox)) {
					entity.addDeltaMovement(movement.scale(intensity * 0.3D));
				}
				if (damagingEruption.intersects(boundingBox)) {
					if (geyserType == GeyserType.LAVA) {
						if (!entity.fireImmune()) {
							entity.setRemainingFireTicks(entity.getRemainingFireTicks() + 1);
							if (entity.getRemainingFireTicks() == 0) {
								entity.igniteForTicks((int) (200 * intensity));
							}
							entity.hurt(level.damageSources().inFire(), 1F);
						}
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

	public void advanceStage(RandomSource random) {
		if (this.geyserStage == GeyserStage.DORMANT) {
			this.setStageAndCooldown(GeyserStage.ACTIVE, random);
		} else if (this.geyserStage == GeyserStage.ACTIVE) {
			this.setStageAndCooldown(GeyserStage.ERUPTING, random);
		} else if (this.geyserStage == GeyserStage.ERUPTING) {
			this.eruptionProgress = 0F;
			this.setStageAndCooldown(GeyserStage.DORMANT, random);
		}
	}

	public void setDormant(RandomSource random) {
		this.setStageAndCooldown(GeyserStage.DORMANT, random);
	}

	public void setStageAndCooldown(GeyserStage geyserStage, RandomSource random) {
		this.geyserStage = geyserStage;
		if (geyserStage == GeyserStage.ACTIVE) {
			this.tickUntilNextEvent = random.nextInt(100, 200);
		} else if (geyserStage == GeyserStage.ERUPTING) {
			this.tickUntilNextEvent = random.nextInt(20, 40);
		} else {
			this.tickUntilNextEvent = random.nextInt(200, 400);
		}
	}

	public void tickClient(@NotNull Level level, @NotNull BlockPos pos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (!GeyserBlock.isInactive(geyserType)) {
			if (this.geyserStage == GeyserStage.DORMANT) {
				spawnDormantParticles(level, pos, geyserType, direction, random);
			} else if (this.geyserStage == GeyserStage.ACTIVE) {
				spawnActiveParticles(level, pos, geyserType, direction, random);
			} else if (this.geyserStage == GeyserStage.ERUPTING) {
				this.eruptionProgress = Math.min(1F, this.eruptionProgress + 0.1F);
				this.handleEruption(level, pos, geyserType, direction);
				spawnEruptionParticles(level, pos, geyserType, direction, random);
			}
		}
	}

	public static void spawnDormantParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType == GeyserType.WATER && random.nextFloat() <= 0.0235F) {
			Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
			Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.09D, 0.12D);
			level.addAlwaysVisibleParticle(
				ParticleTypes.BUBBLE,
				true,
				particlePos.x,
				particlePos.y,
				particlePos.z,
				particleVelocity.x,
				particleVelocity.y,
				particleVelocity.z
			);
		}
		if (random.nextFloat() <= 0.0125F) {
			Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
			Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.03D, 0.05D);
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
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

	public static void spawnActiveParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= 0.1F) {
				int count = random.nextInt(1, 3);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.4D, 0.8D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.1D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.BUBBLE,
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
		} else {
			if (random.nextFloat() <= 0.0575F) {
				int count = random.nextInt(1, 2);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.06D, 0.1D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.1D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.LARGE_SMOKE,
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
			if (random.nextFloat() <= 0.185F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.06D, 0.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.1D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.DUST_PLUME,
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
			if (geyserType == GeyserType.LAVA) {
				if (random.nextFloat() <= 0.0875F) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.1D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.LAVA,
						true,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x * 10D,
						particleVelocity.y,
						particleVelocity.z * 10D
					);
				}
			}
		}
		if (random.nextFloat() <= 0.0375F) {
			Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
			Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.03D, 0.06D);
			particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.05D));
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
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

	public static void spawnEruptionParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= 0.8F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 1.7D, 4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.15D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.BUBBLE,
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
			if (random.nextFloat() <= 0.8F) {
				int count = random.nextInt(1, 6);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 4D, 6D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.SPLASH,
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
		} else {
			if (random.nextFloat() <= 0.8F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.9D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.LARGE_SMOKE,
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
			if (random.nextFloat() <= 0.9F) {
				int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.8D, 1.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.DUST_PLUME,
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
			if (random.nextFloat() <= 0.9F) {
				int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.4D, 0.7D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.CLOUD,
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
			if (geyserType == GeyserType.LAVA) {
				int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					if (random.nextFloat() <= 0.9F) {
						Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
						Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.1D, 0.8D);
						particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.225D));
						level.addAlwaysVisibleParticle(
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
				if (random.nextFloat() <= 0.2F) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.2D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.LAVA,
						true,
						particlePos.x,
						particlePos.y,
						particlePos.z,
						particleVelocity.x * 10D,
						particleVelocity.y,
						particleVelocity.z * 10D
					);
				}
			}
		}
		if (random.nextFloat() <= 0.2F) {
			Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
			Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.1D, 0.3D);
			particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.15D));
			level.addAlwaysVisibleParticle(
				ParticleTypes.CAMPFIRE_COSY_SMOKE,
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

	public void updateSync() {
		ClientboundBlockEntityDataPacket updatePacket = this.getUpdatePacket();
		if (updatePacket != null) {
			for (ServerPlayer player : PlayerLookup.tracking(this)) {
				player.connection.send(this.getUpdatePacket());
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
		tag.putString("GeyserStage", this.geyserStage.name());
	}

	@Override
	public void load(@NotNull CompoundTag tag, HolderLookup.Provider provider) {
		super.load(tag, provider);
		this.hasRunFirstCheck = tag.getBoolean("HasRunFirstCheck");
		this.tickUntilNextEvent = tag.getInt("TicksUntilNextEvent");
		this.eruptionProgress = tag.getFloat("EruptionProgress");
		if (tag.contains("GeyserStage") && (Arrays.stream(GeyserStage.values()).anyMatch(geyserStage -> geyserStage.name().equals(tag.getString("GeyserStage"))))) {
			this.geyserStage = GeyserStage.valueOf(tag.getString("GeyserStage"));
		}
	}

	public enum GeyserStage implements StringRepresentable {
		DORMANT("dormant"),
		ACTIVE("active"),
		ERUPTING("erupting");

		private final String name;

		GeyserStage(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		@Override
		@NotNull
		public String getSerializedName() {
			return this.name;
		}
	}
}
