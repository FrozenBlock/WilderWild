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

package net.frozenblock.wilderwild.wind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.particle.options.WindParticleOptions;
import net.frozenblock.lib.wind.client.api.ClientWindManagerExtension;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.options.WindClusterSeedParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public final class WWClientWindManager implements ClientWindManagerExtension {
	public static double prevCloudX;
	public static double prevCloudZ;
	public static double cloudX;
	public static double cloudZ;

	public static double getCloudX(float partialTick) {
		return Mth.lerp(partialTick, prevCloudX, cloudX);
	}

	public static double getCloudZ(float partialTick) {
		return Mth.lerp(partialTick, prevCloudZ, cloudZ);
	}

	@Override
	public void clientTick() {
		prevCloudX = cloudX;
		prevCloudZ = cloudZ;

		cloudX += (ClientWindManager.laggedWindX * 0.007D);
		cloudZ += (ClientWindManager.laggedWindZ * 0.007D);

		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level != null) {
			BlockPos pos = minecraft.gameRenderer.getMainCamera().getBlockPosition();
			animateTick(level, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public static void animateTick(ClientLevel level, int posX, int posY, int posZ) {
		RandomSource randomSource = level.random;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		if (WWAmbienceAndMiscConfig.WIND_PARTICLES) {
			for (int i = 0; i < WWAmbienceAndMiscConfig.WIND_PARTICLE_SPAWN_ATTEMPTS; ++i) {
				spawnAmbientWindParticles(level, posX, posY, posZ, 48, randomSource, mutableBlockPos, true);
			}
		}
		if (WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLES) {
			for (int i = 0; i < WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS; ++i) {
				spawnDisturbanceWindParticles(level, posX, posY, posZ, 48, randomSource, mutableBlockPos);
			}
		}
	}

	public static void spawnAmbientWindParticles(
		ClientLevel level,
		int posX, int posY, int posZ,
		int range,
		RandomSource random,
		BlockPos.MutableBlockPos mutable,
		boolean allowAdditional
	) {
		final int highestPossibleY = posY + range;
		final int x = posX + random.nextIntBetweenInclusive(-range, range);
		int y = posY;
		final int z = posZ + random.nextIntBetweenInclusive(-range, range);
		mutable.set(x, y, z);

		mutable.set(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutable));
		final int heightmapY = mutable.getY();

		if (heightmapY > highestPossibleY) return;

		if (heightmapY < posY - range) {
			y += random.nextInt(range) - random.nextInt(range);
		} else {
			final double differenceInPoses = highestPossibleY - heightmapY;
			if (random.nextDouble() >= (differenceInPoses / (range * 2D))) return;
			y = random.nextIntBetweenInclusive(heightmapY, highestPossibleY);
		}
		mutable.set(x, y, z);

		final Vec3 wind = ClientWindManager.getWindMovement(level, Vec3.atCenterOf(mutable), 1D, 2D, 2D);
		final double horizontalWind = wind.horizontalDistance();
		if (random.nextDouble() >= (horizontalWind * WWAmbienceAndMiscConfig.getWindParticleFrequency())) return;

		spawnWindParticle(level, random.nextIntBetweenInclusive(10, 20), horizontalWind, wind, 0.0015D, x, y, z, random);

		if (!allowAdditional || !WWAmbienceAndMiscConfig.WIND_CLUSTERS) return;
		final int additionalSpawnAttempts = Math.min((int) (horizontalWind * 6D), WWAmbienceAndMiscConfig.WIND_CLUSTER_MAX_SPAWN_ATTEMPTS);
		if (additionalSpawnAttempts <= 0) return;

		level.addParticle(
			new WindClusterSeedParticleOptions(random.nextIntBetweenInclusive(10, 17), additionalSpawnAttempts),
			x + 0.5D, y + 0.5D, z + 0.5D,
			0D, 0D, 0D
		);
	}

	public static void spawnDisturbanceWindParticles(
		ClientLevel level, int posX, int posY, int posZ, int range, RandomSource random, BlockPos.MutableBlockPos blockPos
	) {
		int x = posX + random.nextIntBetweenInclusive(-range, range);
		int y = posY + random.nextIntBetweenInclusive(-range, range);
		int z = posZ + random.nextIntBetweenInclusive(-range, range);
		blockPos.set(x, y, z);
		if (ClientWindManager.getWindDisturbances().stream().noneMatch(windDisturbance -> windDisturbance.affectedArea.contains(x, y, z))) return;

		BlockState blockState = level.getBlockState(blockPos);
		if (blockState.isCollisionShapeFullBlock(level, blockPos)) return;

		Vec3 wind = ClientWindManager.getWindMovement(level, Vec3.atCenterOf(blockPos), 1D, 1000D, 1000D).scale(0.001D);
		double windLength = wind.length();
		if (random.nextDouble() >= ((wind.length() - 0.001D) * WWAmbienceAndMiscConfig.getWindDisturbanceParticleFrequency())) return;

		spawnWindParticle(level, 10, windLength, wind, 0.003D, x, y, z, random);
	}

	private static void spawnWindParticle(ClientLevel level, int minLifespan, double windStrength, Vec3 wind, double windYScale, int x, int y, int z, RandomSource random) {
		level.addParticle(
			new WindParticleOptions((int) (minLifespan + (windStrength * 30D)), wind.x * 0.01D, wind.y * windYScale, wind.z * 0.01D),
			x + random.triangle(0.5D, 0.3D), y + random.triangle(0.5D, 0.3D), z + random.triangle(0.5D, 0.3D),
			0D, 0D, 0D
		);
	}

	public static boolean shouldUseWind() {
		return WWAmbienceAndMiscConfig.CLOUD_MOVEMENT && ClientWindManager.shouldUseWind();
	}

	@Override
	public void baseTick() {
	}

	@Override
	public void receiveSyncPacket(FriendlyByteBuf byteBuf, Minecraft minecraft) {
		double cloudX = byteBuf.readDouble();
		double cloudZ = byteBuf.readDouble();

		minecraft.execute(() -> {
			if (minecraft.level != null) {
				WWClientWindManager.cloudX = cloudX;
				WWClientWindManager.cloudZ = cloudZ;
			}
		});
	}
}
