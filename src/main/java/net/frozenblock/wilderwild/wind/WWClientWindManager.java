/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.wind;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.api.ClientWindManagerExtension;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import static net.frozenblock.lib.wind.client.impl.ClientWindManager.*;
import net.frozenblock.lib.worldgen.heightmap.api.FrozenHeightmaps;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.particle.options.WindParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWClientWindManager implements ClientWindManagerExtension {
	public static double prevCloudX;
	public static double prevCloudY;
	public static double prevCloudZ;
	public static double cloudX;
	public static double cloudY;
	public static double cloudZ;

	public static double getCloudX(float partialTick) {
		return Mth.lerp(partialTick, prevCloudX, cloudX);
	}

	public static double getCloudY(float partialTick) {
		return Mth.lerp(partialTick, prevCloudY, cloudY);
	}

	public static double getCloudZ(float partialTick) {
		return Mth.lerp(partialTick, prevCloudZ, cloudZ);
	}

	@Override
	public void clientTick() {
		prevCloudX = cloudX;
		prevCloudY = cloudY;
		prevCloudZ = cloudZ;

		cloudX += (laggedWindX * 0.007D);
		cloudY += (laggedWindY * 0.01D);
		cloudZ += (laggedWindZ * 0.007D);

		Minecraft minecraft = Minecraft.getInstance();
		ClientLevel level = minecraft.level;
		if (level != null) {
			BlockPos pos = minecraft.gameRenderer.getMainCamera().getBlockPosition();
			this.animateTick(level, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	public void animateTick(@NotNull ClientLevel level, int posX, int posY, int posZ) {
		RandomSource randomSource = level.random;
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		if (WWAmbienceAndMiscConfig.WIND_PARTICLES) {
			for (int i = 0; i < WWAmbienceAndMiscConfig.WIND_PARTICLE_SPAWN_ATTEMPTS; ++i) {
				this.spawnAmbientWindParticles(level, posX, posY, posZ, 48, randomSource, mutableBlockPos);
			}
		}
		if (WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLES) {
			for (int i = 0; i < WWAmbienceAndMiscConfig.WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS; ++i) {
				this.spawnDisturbanceWindParticles(level, posX, posY, posZ, 48, randomSource, mutableBlockPos);
			}
		}
	}

	public void spawnAmbientWindParticles(
		@NotNull ClientLevel level, int posX, int posY, int posZ, int range, @NotNull RandomSource random, @NotNull BlockPos.MutableBlockPos blockPos
	) {
		int highestPossibleY = posY + range;
		int i = posX + random.nextIntBetweenInclusive(-range, range);
		int j = posY;
		int k = posZ + random.nextIntBetweenInclusive(-range, range);
		blockPos.set(i, j, k);

		blockPos.set(level.getHeightmapPos(FrozenHeightmaps.MOTION_BLOCKING_NO_LEAVES_SYNCED, blockPos));
		int heightmapY = blockPos.getY();
		if (heightmapY > highestPossibleY) {
			return;
		} else if (heightmapY < posY - range) {
			j += random.nextInt(range) - random.nextInt(range);
		} else {
			double differenceInPoses = highestPossibleY - heightmapY;
			if (random.nextDouble() < (differenceInPoses / (range * 2D))) {
				j = random.nextIntBetweenInclusive(heightmapY, highestPossibleY);
			} else {
				return;
			}
		}
		blockPos.set(i, j, k);

		Vec3 wind = ClientWindManager.getWindMovement(level, Vec3.atCenterOf(blockPos), 1D, 2D, 2D);
		double horizontalWind = wind.horizontalDistance();
		if (random.nextDouble() < (horizontalWind * WWAmbienceAndMiscConfig.getWindParticleFrequency())) {
			level.addParticle(
				new WindParticleOptions((int) (10D + (horizontalWind * 30D)), wind.x * 0.01D, wind.y * 0.0015D, wind.z * 0.01D),
				i,
				j,
				k,
				0D,
				0D,
				0D
			);
		}
	}

	public void spawnDisturbanceWindParticles(
		@NotNull ClientLevel level, int posX, int posY, int posZ, int range, @NotNull RandomSource random, @NotNull BlockPos.MutableBlockPos blockPos
	) {
		int i = posX + random.nextIntBetweenInclusive(-range, range);
		int j = posY + random.nextIntBetweenInclusive(-range, range);
		int k = posZ + random.nextIntBetweenInclusive(-range, range);
		blockPos.set(i, j, k);
		if (ClientWindManager.getWindDisturbances().stream().noneMatch(windDisturbance -> windDisturbance.affectedArea.contains(i, j, k))) {
			return;
		}

		BlockState blockState = level.getBlockState(blockPos);

		if (!blockState.isCollisionShapeFullBlock(level, blockPos)) {
			Vec3 wind = ClientWindManager.getWindMovement(level, Vec3.atCenterOf(blockPos), 1D, 1000D, 1000D).scale(0.001D);
			double windLength = wind.length();
			if (random.nextDouble() < ((wind.length() - 0.001D) * WWAmbienceAndMiscConfig.getWindDisturbanceParticleFrequency())) {
				level.addParticle(
					new WindParticleOptions((int) (10D + (windLength * 30D)), wind.x * 0.01D, wind.y * 0.003D, wind.z * 0.01D),
					i,
					j,
					k,
					0D,
					0D,
					0D
				);
			}
		}
	}

	public static boolean shouldUseWind() {
		return WWAmbienceAndMiscConfig.CLOUD_MOVEMENT && ClientWindManager.shouldUseWind();
	}

	@Override
	public void baseTick() {
	}

	@Override
	public void receiveSyncPacket(@NotNull FriendlyByteBuf byteBuf, @NotNull Minecraft minecraft) {
		double cloudX = byteBuf.readDouble();
		double cloudY = byteBuf.readDouble();
		double cloudZ = byteBuf.readDouble();

		minecraft.execute(() -> {
			if (minecraft.level != null) {
				WWClientWindManager.cloudX = cloudX;
				WWClientWindManager.cloudY = cloudY;
				WWClientWindManager.cloudZ = cloudZ;
			}
		});
	}
}
