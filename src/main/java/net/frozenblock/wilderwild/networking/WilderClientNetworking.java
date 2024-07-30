/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.networking.packet.WilderJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WilderLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WilderScorchingFirePlacePacket;
import net.frozenblock.wilderwild.networking.packet.WilderSensorHiccupPacket;
import net.frozenblock.wilderwild.networking.packet.WilderStoneChestLidPacket;
import net.frozenblock.wilderwild.networking.packet.WilderWindPacket;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.wind.WilderClientWindManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class WilderClientNetworking {

	public static void registerPacketReceivers() {
		receiveWindExtensionSyncPacket();
		receiveSensorHiccupPacket();
		receiveJellyfishStingPacket();
		receiveLightningStrikePacket();
		receiveStoneChestLidPacket();
		receiveScorchingFirePlacePacket();
	}

	public static void receiveWindExtensionSyncPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderWindPacket.PACKET_TYPE, (packet, ctx) -> {
			Vec3 cloudPos = packet.cloudPos();
			WilderClientWindManager.cloudX = cloudPos.x();
			WilderClientWindManager.cloudY = cloudPos.y();
			WilderClientWindManager.cloudZ = cloudPos.z();
		});
	}

	public static void receiveSensorHiccupPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderSensorHiccupPacket.PACKET_TYPE, (packet, ctx) -> {
			ClientLevel clientLevel = ctx.client().level;
			clientLevel.addParticle(
				ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, WilderSensorHiccupPacket.PARTICLE_COLOR),
				packet.x(),
				packet.y(),
				packet.z(),
				1D,
				1D,
				1D
			);
		});
	}

	public static void receiveJellyfishStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderJellyfishStingPacket.PACKET_TYPE, (packet, ctx) -> {
			Player player = Minecraft.getInstance().player;
			ClientLevel clientLevel = ctx.client().level;
			clientLevel.playSound(
				player,
				player.getX(),
				player.getY(),
				player.getZ(),
				RegisterSounds.ENTITY_JELLYFISH_STING,
				SoundSource.NEUTRAL,
				1F,
				packet.isBaby() ? Jellyfish.STING_PITCH_BABY : Jellyfish.STING_PITCH
			);
		});
	}

	public static void receiveLightningStrikePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderLightningStrikePacket.PACKET_TYPE, (packet, ctx) -> {
			BlockState blockState = Block.stateById(packet.blockStateId());
			Minecraft minecraft = Minecraft.getInstance();
			ClientLevel clientLevel = ctx.client().level;
			if (!blockState.isAir()) {
				RandomSource random = clientLevel.getRandom();
				if (EntityConfig.get().lightning.lightningBlockParticles) {
					lightningBlockParticles(packet.tickCount(), packet.x(), packet.y(), packet.z(), blockState, random, minecraft.particleEngine);
				}
				if (EntityConfig.get().lightning.lightningSmokeParticles) {
					lightningSmokeParticles(packet.tickCount(), packet.x(), packet.y(), packet.z(), blockState, random, minecraft.particleEngine);
				}
			}
		});
	}

	public static void receiveStoneChestLidPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderStoneChestLidPacket.PACKET_TYPE, (packet, ctx) -> {
			ClientLevel clientLevel = ctx.client().level;
			if (clientLevel.getBlockEntity(packet.pos()) instanceof StoneChestBlockEntity stoneChestBlockEntity) {
				stoneChestBlockEntity.openProgress = packet.openProgress();
				stoneChestBlockEntity.highestLidPoint = packet.highestLidPoint();
				stoneChestBlockEntity.cooldownTicks = packet.cooldownTicks();
				stoneChestBlockEntity.stillLidTicks = packet.stillLidTicks();
				stoneChestBlockEntity.closing = packet.closing();
			}
		});
	}

	public static void receiveScorchingFirePlacePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WilderScorchingFirePlacePacket.PACKET_TYPE, (packet, ctx) -> {
			ClientLevel clientLevel = ctx.client().level;
			RandomSource randomSource = clientLevel.random;
			BlockPos pos = packet.pos();
			for (int particles = 0; particles < 10; ++particles) {
				clientLevel.addParticle(
					ParticleTypes.LARGE_SMOKE,
					(double)pos.getX() + randomSource.nextDouble(),
					(double)pos.getY() + randomSource.nextDouble(),
					(double)pos.getZ() + randomSource.nextDouble(),
					randomSource.nextGaussian() * 0.02D,
					randomSource.nextGaussian() * 0.1D,
					randomSource.nextGaussian() * 0.02D
				);
			}

			clientLevel.playLocalSound(
				pos,
				RegisterSounds.BLOCK_FIRE_IGNITE,
				SoundSource.BLOCKS,
				1.0F,
				(randomSource.nextFloat() - randomSource.nextFloat()) * 0.2F + 1.0F,
				true
			);
		});
	}

	private static void lightningBlockParticles(int tickCount, double x, double y, double z, @NotNull BlockState blockState, @NotNull RandomSource random, @NotNull ParticleEngine particleEngine) {
		if (blockState.is(WilderBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)) {
			return;
		}
		boolean first = tickCount == 0;
		double calmDownAge = Math.max(1, tickCount - 6D);
		Vec3 origin = new Vec3(x, y, z);
		int particles = first ? random.nextInt(25, 40) : random.nextInt(5, 15);
		double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, blockState);
		if (blockState.is(Blocks.WATER)) {
			particleOptions = ParticleTypes.SPLASH;
		} else if (blockState.is(Blocks.LAVA)) {
			particleOptions = ParticleTypes.LAVA;
		}
		double speedMultiplier = first ? 1.5D : 1D;
		double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 0.4D, angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D)));
			double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D);
			double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D);

			Particle blockParticle = particleEngine.createParticle(particleOptions, x + dirX, y, z + dirZ, 0D, 0D, 0D);
			if (blockParticle != null) {
				blockParticle.xd = ((dirX * 0.8D) / calmDownAge) * speedMultiplier;
				blockParticle.yd = ((0.4D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
				blockParticle.zd = ((dirZ * 0.8D) / calmDownAge) * speedMultiplier;
			}

			if (random.nextBoolean()) {
				Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D) / calmDownAge) * speedMultiplier;
					particle2.yd = ((0.5D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D) / calmDownAge) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

	private static void lightningSmokeParticles(int tickCount, double x, double y, double z, @NotNull BlockState blockState, @NotNull RandomSource random, @NotNull ParticleEngine particleEngine) {
		if (blockState.is(WilderBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)) {
			return;
		}
		boolean first = tickCount == 0;
		Vec3 origin = new Vec3(x, y, z);
		int particles = random.nextInt(2, 15);
		double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		double speedMultiplier = first ? 1.5D : 1D;
		double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(origin, 0.4D, angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D)));
			double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;
			double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;

			if (random.nextBoolean()) {
				Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D)) * speedMultiplier;
					particle2.yd = ((0.5D / (double) tickCount) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D)) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

}
