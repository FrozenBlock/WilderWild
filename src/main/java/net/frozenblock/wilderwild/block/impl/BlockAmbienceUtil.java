/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.block.impl;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.wind.api.WindManager;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class BlockAmbienceUtil {
	private static final int COMPLETELY_SURROUNDED_BY_LEAVES = 6;
	private static final int MOSTLY_SURROUNDED_BY_LEAVES = 4;

	public static void playLeavesAmbience(@NotNull BlockState state, Level level, BlockPos pos, @NotNull RandomSource random) {
		if (state.getOptionalValue(LeavesBlock.DISTANCE).orElse(0) >= 7) return;

		SoundEvent soundToPlay = null;
		double soundYOffset = 0D;

		float windStrength = horizontalWindStrength(level, pos);

		float baseVolume = 0.05F;
		float maxRandomVolume = 0.15F;
		float basePitch = getFromLerp(windStrength, 0.75F, 1.1F);
		float maxRandomPitch = getFromLerp(windStrength, 0.075F, 0.2F);

		if (state.is(WWBlockTags.AMBIENCE_WIND_FALLING_LEAVES)) {
			if (random.nextFloat() <= getFromLerp(windStrength, 0.0005F, 0.005F)) {
				if (!isBrightEnoughForWind(level, pos) || !hasNeighboringLeaves(level, pos, COMPLETELY_SURROUNDED_BY_LEAVES)) return;
				soundToPlay = windStrength > 0.75F ? WWSounds.AMBIENT_OVERWORLD_WIND_FALLING_LEAVES_FAST : WWSounds.AMBIENT_OVERWORLD_WIND_FALLING_LEAVES;

				baseVolume = getFromLerp(windStrength, 0.015F, 0.09F);
				maxRandomVolume = getFromLerp(windStrength, 0.005F, 0.15F);

				soundYOffset = -2D;
			}
		} else {
			if (random.nextFloat() <= windStrength * 0.005F) {
				if (!isBrightEnoughForWind(level, pos) || !hasNeighboringLeaves(level, pos, MOSTLY_SURROUNDED_BY_LEAVES)) return;
				soundToPlay = windStrength > 0.75F ? WWSounds.AMBIENT_OVERWORLD_WIND_LEAVES_FAST : WWSounds.AMBIENT_OVERWORLD_WIND_LEAVES;

				baseVolume = getFromLerp(windStrength, 0.0075F, 0.09F);
				maxRandomVolume = getFromLerp(windStrength, 0.005F, 0.15F);
			}
		}

		if (soundToPlay != null) {
			level.playLocalSound(
				pos.getX() + 0.5D,
				pos.getY() + soundYOffset + 0.5D,
				pos.getZ() + 0.5D,
				soundToPlay,
				SoundSource.AMBIENT,
				baseVolume + (random.nextFloat() * maxRandomVolume),
				basePitch + (random.nextFloat() * maxRandomPitch),
				false
			);
		}
	}

	public static float getFromLerp(float lerp, float min, float max) {
		return Mth.lerp(lerp, min, max);
	}

	public static float horizontalWindStrength(@NotNull Level level, @NotNull BlockPos pos) {
		float windLength = 0F;
		if (level instanceof ServerLevel serverLevel) {
			windLength = (float) WindManager.getWindManager(serverLevel).getWindMovement(pos).horizontalDistance();
		} else if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT && level.isClientSide) {
			windLength = (float) ClientWindManager.getWindMovement(level, pos).horizontalDistance();
		}
		return Mth.clamp(windLength * 1.25F, 0F, 1F);
	}

	public static boolean isBrightEnoughForWind(@NotNull Level level, BlockPos pos) {
		return level.getBrightness(LightLayer.SKY, pos) > 10;
	}

	public static boolean hasNeighboringLeaves(Level level, BlockPos pos, int requiredLeafCount) {
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();
		int countedLeaves = 0;

		for (Direction direction : Direction.values()) {
			mutablePos.setWithOffset(pos, direction);
			if (level.getBlockState(mutablePos).is(BlockTags.LEAVES)) countedLeaves += 1;
		}

		return countedLeaves >= requiredLeafCount;
	}

	public static boolean isDay(@NotNull Level level) {
		return !level.dimensionType().hasFixedTime() && Mth.cos(level.getSunAngle(1F)) >= 0F;
	}

	public static boolean isNight(@NotNull Level level) {
		return !level.dimensionType().hasFixedTime() && Mth.cos(level.getSunAngle(1F)) < 0F;
	}
}
