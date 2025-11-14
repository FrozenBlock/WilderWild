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

package net.frozenblock.wilderwild.block.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.particle.options.WindParticleOptions;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.state.properties.GeyserType;
import net.frozenblock.wilderwild.registry.WWParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ParticleStatus;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class GeyserParticleHandler {
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
	public static final int VENT_MIN_PARTICLE_SPAWN_WIDTH = -5;
	public static final int VENT_MAX_PARTICLE_SPAWN_WIDTH = 5;
	public static final int VENT_MIN_PARTICLE_SPAWN_HEIGHT = -3;
	public static final int VENT_MAX_PARTICLE_SPAWN_HEIGHT = 7;
	public static final int VENT_PARTICLE_SPAWN_ATTEMPTS = 6;

	public static void spawnDormantParticles(Level level, BlockPos pos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (!geyserType.isWater() || random.nextFloat() > DORMANT_BUBBLE_CHANCE) return;
		final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
		final Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, DORMANT_BUBBLE_MIN_VELOCITY, DORMANT_BUBBLE_MAX_VELOCITY);
		level.addParticle(
			ParticleTypes.BUBBLE,
			particlePos.x, particlePos.y, particlePos.z,
			particleVelocity.x, particleVelocity.y, particleVelocity.z
		);
	}

	public static void spawnActiveParticles(Level level, BlockPos pos, GeyserType geyserType, Direction direction, RandomSource random) {
		if (geyserType.isWater()) {
			if (random.nextFloat() > ACTIVE_BUBBLE_CHANCE) return;
			int count = random.nextInt(MIN_ACTIVE_BUBBLES, MAX_ACTIVE_BUBBLES);
			for (int i = 0; i < count; i++) {
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_BUBBLE_MIN_VELOCITY_OFFSET, ACTIVE_BUBBLE_MAX_VELOCITY_OFFSET);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, ACTIVE_BUBBLE_RANDOM_VELOCITY));
				level.addParticle(
					ParticleTypes.BUBBLE,
					particlePos.x,
					particlePos.y,
					particlePos.z,
					particleVelocity.x,
					particleVelocity.y,
					particleVelocity.z
				);
			}
			return;
		}

		if (random.nextFloat() <= ACTIVE_DUST_CHANCE) {
			final int count = random.nextInt(MIN_ACTIVE_DUST, MAX_ACTIVE_DUST);
			for (int i = 0; i < count; i++) {
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_DUST_MIN_VELOCITY, ACTIVE_DUST_MAX_VELOCITY);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, ACTIVE_DUST_RANDOM_VELOCITY));
				level.addParticle(
					ParticleTypes.DUST_PLUME,
					particlePos.x, particlePos.y, particlePos.z,
					particleVelocity.x, particleVelocity.y, particleVelocity.z
				);
			}
		}


		if (geyserType == GeyserType.LAVA) {
			if (random.nextFloat() <= ACTIVE_LAVA_CHANCE) {
				int count = random.nextInt(MIN_ACTIVE_LAVA, MAX_ACTIVE_LAVA);
				for (int i = 0; i < count; i++) {
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_LAVA_MIN_VELOCITY, ACTIVE_LAVA_MAX_VELOCITY);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, ACTIVE_LAVA_RANDOM_VELOCITY));
					level.addParticle(
						ParticleTypes.LARGE_SMOKE,
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
			if (random.nextFloat() <= ACTIVE_FLAME_CHANCE) {
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, ACTIVE_FLAME_MIN_VELOCITY, ACTIVE_FLAME_MAX_VELOCITY);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, ACTIVE_FLAME_RANDOM_VELOCITY));
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
	}

	@Environment(EnvType.CLIENT)
	public static void spawnEruptionParticles(Level level, BlockPos pos, GeyserType geyserType, Direction direction, RandomSource random) {
		final Minecraft client = Minecraft.getInstance();
		final ParticleStatus particleStatus = client.options.particles().get();
		final ParticleEngine particleEngine = client.particleEngine;
		final boolean vent = geyserType == GeyserType.HYDROTHERMAL_VENT;
		if (geyserType.isWater()) {
			if (random.nextFloat() <= 0.4F && (!vent || random.nextBoolean())) { // Bubble
				final int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 1.7D, 4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.15D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.BUBBLE,
						!vent && random.nextBoolean(),
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
			if (vent && random.nextFloat() <= 0.3F) { // Smoke
				final int count = random.nextInt(1, 2);
				for (int i = 0; i < count; i++) {
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.05D, 0.15D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.055D));
					level.addParticle(
						ParticleTypes.LARGE_SMOKE,
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
		} else {
			if (random.nextFloat() <= 0.5F) { // Dust Plume
				final int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.8D, 1.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.275D));
					level.addParticle(
						ParticleTypes.DUST_PLUME,
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
		}
		if (geyserType == GeyserType.LAVA) {
			if (random.nextFloat() <= 0.7F) { // Large Smoke
				final int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.9D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.275D));
					level.addParticle(
						ParticleTypes.LARGE_SMOKE,
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
			final int count = random.nextInt(1, 5);
			for (int i = 0; i < count; i++) {
				if (random.nextFloat() <= 0.9F) { // Flame
					final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.1D, 0.6D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.225D));
					level.addAlwaysVisibleParticle(
						ParticleTypes.FLAME,
						false,
						particlePos.x, particlePos.y, particlePos.z,
						particleVelocity.x, particleVelocity.y, particleVelocity.z
					);
				}
			}
			int lavaCount = random.nextInt(1, 3);
			for (int i = 0; i < lavaCount; i++) { // Lava
				if (particleStatus == ParticleStatus.DECREASED && random.nextBoolean()) {
					break;
				} else if (particleStatus == ParticleStatus.MINIMAL && random.nextFloat() <= 0.675F) {
					break;
				}
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.6D, 0.8D);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.2D));
				final Particle lavaParticle = particleEngine.createParticle(
					ParticleTypes.LAVA,
					particlePos.x, particlePos.y, particlePos.z,
					particleVelocity.x, particleVelocity.y, particleVelocity.z
				);
				if (lavaParticle != null) {
					lavaParticle.xd = particleVelocity.x;
					lavaParticle.yd = particleVelocity.y;
					lavaParticle.zd = particleVelocity.z;
				}
			}
		} else if (!vent) {
			final int windCount = random.nextInt(0, 2);
			for (int i = 0; i < windCount; i++) { // WIND
				if (particleStatus == ParticleStatus.DECREASED && random.nextBoolean()) {
					break;
				} else if (particleStatus == ParticleStatus.MINIMAL && random.nextFloat() <= 0.675F) {
					break;
				}
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.6D, 0.8D);
				particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(pos, direction, particlePos, random, 0.2D));
				particleEngine.createParticle(
					new WindParticleOptions(12, particleVelocity, WindParticleOptions.ParticleLength.SMALL),
					particlePos.x, particlePos.y, particlePos.z,
					0, 0, 0
				);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	public static void spawnBaseGeyserParticles(Level level, BlockPos pos, Direction direction, RandomSource random, boolean vent) {
		final Minecraft client = Minecraft.getInstance();
		final ParticleStatus particleStatus = client.options.particles().get();
		if (particleStatus == ParticleStatus.MINIMAL) return;
		final ParticleEngine particleEngine = client.particleEngine;
		final float chance = particleStatus == ParticleStatus.DECREASED ? 0.3F : 1F;

		final int count = vent ? random.nextInt(2, 5) : random.nextInt(0, 3);
		for (int i = 0; i < count; i++) {
			if (random.nextFloat() <= chance) {
				final Vec3 particlePos = GeyserBlock.getParticlePos(pos, direction, random);
				final Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.001D, 0.005D);
				Particle particle = particleEngine.createParticle(
					vent ? ParticleTypes.LARGE_SMOKE : ParticleTypes.WHITE_SMOKE,
					particlePos.x, particlePos.y, particlePos.z,
					particleVelocity.x, particleVelocity.y, particleVelocity.z
				);
				if (particle != null) {
					particle.xd = particleVelocity.x;
					particle.yd = particleVelocity.y;
					particle.zd = particleVelocity.z;
				}
			}
		}

		if (vent) {
			final BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
			for (int l = 0; l < VENT_PARTICLE_SPAWN_ATTEMPTS; ++l) {
				mutable.setWithOffset(
					pos,
					Mth.nextInt(random, VENT_MIN_PARTICLE_SPAWN_WIDTH, VENT_MAX_PARTICLE_SPAWN_WIDTH),
					Mth.nextInt(random, VENT_MIN_PARTICLE_SPAWN_HEIGHT, VENT_MAX_PARTICLE_SPAWN_HEIGHT),
					Mth.nextInt(random, VENT_MIN_PARTICLE_SPAWN_WIDTH, VENT_MAX_PARTICLE_SPAWN_WIDTH)
				);
				final BlockState state = level.getBlockState(mutable);
				if (state.is(Blocks.WATER) && state.getFluidState().getAmount() == FluidState.AMOUNT_FULL) {
					level.addParticle(
						WWParticleTypes.UNDERWATER_ASH,
						mutable.getX() + random.nextDouble(),
						mutable.getY() + random.nextDouble(),
						mutable.getZ() + random.nextDouble(),
						0D, 0D, 0D
					);
				}
			}
		}
	}

}
