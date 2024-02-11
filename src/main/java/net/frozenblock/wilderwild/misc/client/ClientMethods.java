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

package net.frozenblock.wilderwild.misc.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.sound.api.instances.RestrictedMovingSound;
import net.frozenblock.lib.sound.api.predicate.SoundPredicate;
import net.frozenblock.wilderwild.block.GeyserBlock;
import net.frozenblock.wilderwild.block.entity.TermiteMoundBlockEntity;
import net.frozenblock.wilderwild.block.impl.GeyserType;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.resources.sounds.EntityBoundSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ClientMethods {
	private ClientMethods() {
		throw new UnsupportedOperationException("ClientMethods contains only static declarations.");
	}

	public static void playClientEnderManSound(@NotNull EnderMan enderMan) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && enderMan.isAlive()) {
			client.getSoundManager().play(new RestrictedMovingSound<>(enderMan, SoundEvents.ENDERMAN_STARE, SoundSource.HOSTILE, 2.5F, 1F, SoundPredicate.notSilentAndAlive(), true));
		}
	}

	public static void playClientPlayerSoundIfSamePlayer(@NotNull SoundEvent sound, float volume, float pitch, @NotNull Entity compareTo) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null && compareTo == client.player) {
			client.getSoundManager().play(new EntityBoundSoundInstance(sound, SoundSource.PLAYERS, volume, pitch, client.player, client.level.random.nextLong()));
		}
	}

	public static void addTermiteSound(TermiteMoundBlockEntity mound, int termiteID, boolean eating) {
		Minecraft client = Minecraft.getInstance();
		if (client.level != null) {
			if (eating) {
				client.getSoundManager().play(new TermiteSoundInstance<>(mound, termiteID, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_GNAW, SoundSource.BLOCKS, 0.2F, 1F, true));
			} else {
				client.getSoundManager().play(new TermiteSoundInstance<>(mound, termiteID, RegisterSounds.BLOCK_TERMITE_MOUND_TERMITE_IDLE, SoundSource.BLOCKS, 0.2F, 1F, false));
			}
		}
	}

	public static void spawnEruptionParticles(@NotNull Level level, BlockPos blockPos, GeyserType geyserType, Direction direction, RandomSource random) {
		Minecraft client = Minecraft.getInstance();
		ParticleEngine particleEngine = client.particleEngine;
		if (geyserType == GeyserType.WATER) {
			if (random.nextFloat() <= 0.8F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 1.7D, 4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.15D));
					level.addParticle(
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
			if (random.nextFloat() <= 0.9F) {
				int count = random.nextInt(1, 5);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.8D, 1.4D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addParticle(
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
		}
		if (geyserType == GeyserType.LAVA) {
			if (random.nextFloat() <= 0.8F) {
				int count = random.nextInt(1, 4);
				for (int i = 0; i < count; i++) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.2D, 0.9D);
					particleVelocity = particleVelocity.add(GeyserBlock.getVelocityFromDistance(blockPos, direction, particlePos, random, 0.275D));
					level.addParticle(
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
			int count = random.nextInt(1, 5);
			for (int i = 0; i < count; i++) {
				if (random.nextFloat() <= 0.9F) {
					Vec3 particlePos = GeyserBlock.getParticlePos(blockPos, direction, random);
					Vec3 particleVelocity = GeyserBlock.getParticleVelocity(direction, random, 0.1D, 0.6D);
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
			int lavaCount = random.nextInt(1, 3);
			for (int i = 0; i < lavaCount; i++) {
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
}
