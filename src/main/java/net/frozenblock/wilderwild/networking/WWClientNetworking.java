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

package net.frozenblock.wilderwild.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.frozenblock.lib.math.api.AdvancedMath;
import net.frozenblock.wilderwild.block.entity.StoneChestBlockEntity;
import net.frozenblock.wilderwild.block.impl.FallingLeafUtil;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.networking.packet.WWJellyfishStingPacket;
import net.frozenblock.wilderwild.networking.packet.WWLeavesExplosionParticlePacket;
import net.frozenblock.wilderwild.networking.packet.WWLightningStrikePacket;
import net.frozenblock.wilderwild.networking.packet.WWScorchingFirePlacePacket;
import net.frozenblock.wilderwild.networking.packet.WWStoneChestLidPacket;
import net.frozenblock.wilderwild.networking.packet.WWWindPacket;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.wind.WWClientWindManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class WWClientNetworking {

	public static void registerPacketReceivers() {
		receiveWindExtensionSyncPacket();
		receiveJellyfishStingPacket();
		receiveLightningStrikePacket();
		receiveStoneChestLidPacket();
		receiveScorchingFirePlacePacket();
		receiveLeavesExplosionPacket();
	}

	public static void receiveWindExtensionSyncPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWWindPacket.PACKET_TYPE, (packet, ctx) -> {
			WWClientWindManager.cloudX = packet.cloudX();
			WWClientWindManager.cloudZ = packet.cloudZ();
		});
	}

	public static void receiveJellyfishStingPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWJellyfishStingPacket.PACKET_TYPE, (packet, ctx) -> {
			final Player player = Minecraft.getInstance().player;
			final ClientLevel level = ctx.client().level;
			level.playSound(
				player,
				player.getX(),
				player.getY(),
				player.getZ(),
				WWSounds.ENTITY_JELLYFISH_STING,
				SoundSource.NEUTRAL,
				1F,
				packet.isBaby() ? Jellyfish.STING_PITCH_BABY : Jellyfish.STING_PITCH
			);
		});
	}

	public static void receiveLightningStrikePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWLightningStrikePacket.PACKET_TYPE, (packet, ctx) -> {
			final BlockState state = Block.stateById(packet.blockStateId());
			if (state.isAir()) return;

			final Minecraft minecraft = Minecraft.getInstance();
			final ClientLevel level = ctx.client().level;
			final RandomSource random = level.getRandom();

			WWEntityConfig.LightningConfig lightningConfig = WWEntityConfig.get().lightning;
			if (lightningConfig.lightningBlockParticles) {
				lightningBlockParticles(packet.tickCount(), packet.x(), packet.y(), packet.z(), state, random, minecraft.particleEngine);
			}
			if (lightningConfig.lightningSmokeParticles) {
				lightningSmokeParticles(packet.tickCount(), packet.x(), packet.y(), packet.z(), state, random, minecraft.particleEngine);
			}
		});
	}

	public static void receiveStoneChestLidPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWStoneChestLidPacket.PACKET_TYPE, (packet, ctx) -> {
			final ClientLevel level = ctx.client().level;
			if (!(level.getBlockEntity(packet.pos()) instanceof StoneChestBlockEntity stoneChestBlockEntity)) return;

			stoneChestBlockEntity.prevOpenProgress = packet.prevOpenProgress();
			stoneChestBlockEntity.openProgress = packet.openProgress();
			stoneChestBlockEntity.highestLidPoint = packet.highestLidPoint();
			stoneChestBlockEntity.cooldownTicks = packet.cooldownTicks();
			stoneChestBlockEntity.stillLidTicks = packet.stillLidTicks();
			stoneChestBlockEntity.closing = packet.closing();
		});
	}

	public static void receiveScorchingFirePlacePacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWScorchingFirePlacePacket.PACKET_TYPE, (packet, ctx) -> {
			final ClientLevel level = ctx.client().level;
			final RandomSource random = level.random;
			final BlockPos pos = packet.pos();
			for (int particles = 0; particles < 10; ++particles) {
				level.addParticle(
					ParticleTypes.LARGE_SMOKE,
					(double)pos.getX() + random.nextDouble(),
					(double)pos.getY() + random.nextDouble(),
					(double)pos.getZ() + random.nextDouble(),
					random.nextGaussian() * 0.04D,
					random.nextGaussian() * 0.05D,
					random.nextGaussian() * 0.04D
				);
			}

			level.playLocalSound(
				pos,
				WWSounds.BLOCK_FIRE_IGNITE,
				SoundSource.BLOCKS,
				0.5F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1F,
				true
			);
		});
	}

	private static void lightningBlockParticles(int tickCount, double x, double y, double z, BlockState state, RandomSource random, ParticleEngine particleEngine) {
		if (state.is(WWBlockTags.NO_LIGHTNING_BLOCK_PARTICLES)) return;

		final boolean first = tickCount == 0;
		final double calmDownAge = Math.max(1, tickCount - 6D);
		final Vec3 origin = new Vec3(x, y, z);
		final int particles = first ? random.nextInt(25, 40) : random.nextInt(5, 15);
		final double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		ParticleOptions particleOptions = new BlockParticleOption(ParticleTypes.BLOCK, state);
		if (state.is(Blocks.WATER)) {
			particleOptions = ParticleTypes.SPLASH;
		} else if (state.is(Blocks.LAVA)) {
			particleOptions = ParticleTypes.LAVA;
		}
		final double speedMultiplier = first ? 1.5D : 1D;
		final double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(
				origin,
				0.4D,
				angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D))
			);
			final double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D);
			final double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D);

			final Particle blockParticle = particleEngine.createParticle(particleOptions, x + dirX, y, z + dirZ, 0D, 0D, 0D);
			if (blockParticle != null) {
				blockParticle.xd = ((dirX * 0.8D) / calmDownAge) * speedMultiplier;
				blockParticle.yd = ((0.4D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
				blockParticle.zd = ((dirZ * 0.8D) / calmDownAge) * speedMultiplier;
			}

			if (random.nextBoolean()) {
				final Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D) / calmDownAge) * speedMultiplier;
					particle2.yd = ((0.5D / calmDownAge) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D) / calmDownAge) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

	private static void lightningSmokeParticles(
		int tickCount,
		double x,
		double y,
		double z,
		BlockState state,
		RandomSource random,
		ParticleEngine particleEngine
	) {
		if (state.is(WWBlockTags.NO_LIGHTNING_SMOKE_PARTICLES)) return;

		final boolean first = tickCount == 0;
		final Vec3 origin = new Vec3(x, y, z);
		final int particles = random.nextInt(2, 15);
		final double rotAngle = 360D / (double) particles;
		double angle = random.nextDouble() * 360D;
		final double speedMultiplier = first ? 1.5D : 1D;
		final double speedMultiplierY = first ? 1.13D : 1D;

		for (int a = 0; a < particles; a++) {
			Vec3 offsetPos = AdvancedMath.rotateAboutXZ(
				origin,
				0.4D,
				angle + (((random.nextDouble() * rotAngle) * 0.35D) * (random.nextBoolean() ? 1D : -1D))
			);
			final double dirX = (offsetPos.x - origin.x) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;
			final double dirZ = (offsetPos.z - origin.z) * ((random.nextFloat() * 0.6D) + 0.4D) / (double) tickCount;

			if (random.nextBoolean()) {
				final Particle particle2 = particleEngine.createParticle(ParticleTypes.LARGE_SMOKE, x + dirX * 0.3D, y, z + dirZ * 0.3D, 0D, 0D, 0D);
				if (particle2 != null) {
					particle2.xd = ((dirX * 0.2D)) * speedMultiplier;
					particle2.yd = ((0.5D / (double) tickCount) * ((random.nextFloat() * 0.4D) + 0.7D)) * speedMultiplierY;
					particle2.zd = ((dirZ * 0.2D)) * speedMultiplier;
				}
			}

			angle += rotAngle;
		}
	}

	public static void receiveLeavesExplosionPacket() {
		ClientPlayNetworking.registerGlobalReceiver(WWLeavesExplosionParticlePacket.PACKET_TYPE, (packet, ctx) -> {
			final ClientLevel level = ctx.client().level;
			FallingLeafUtil.clientSpawnExplosionParticlesFromPacket(level, packet);
		});
	}
}
