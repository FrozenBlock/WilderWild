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

import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
		float baseVolume = 0.05F;
		float maxRandomVolume = 0.15F;
		double soundYOffset = 0D;

		if (state.is(WWBlockTags.AMBIENCE_WIND_FALLING_LEAVES)) {
			if (random.nextFloat() <= 0.0025F) {
				if (!isBrightEnoughForWind(level, pos) || !hasNeighboringLeaves(level, pos, COMPLETELY_SURROUNDED_BY_LEAVES)) return;
				soundToPlay = WWSounds.AMBIENT_OVERWORLD_WIND_FALLING_LEAVES;
				soundYOffset = -2D;
			}
		} else {
			if (random.nextFloat() <= 0.002F) {
				if (!isBrightEnoughForWind(level, pos) || !hasNeighboringLeaves(level, pos, MOSTLY_SURROUNDED_BY_LEAVES)) return;
				soundToPlay = WWSounds.AMBIENT_OVERWORLD_WIND_LEAVES;
				baseVolume = 0.025F;
				maxRandomVolume = 0.075F;
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
				0.85F + (random.nextFloat() * 0.25F),
				false
			);
		}
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
