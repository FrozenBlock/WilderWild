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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableMap;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinStandUp<E extends Penguin> extends Behavior<E> {

	public PenguinStandUp(int duration) {
		super(
			ImmutableMap.of(
				WWMemoryModuleTypes.LAYING_DOWN, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.VALUE_ABSENT
			),
			duration
		);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return true;
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		boolean swimming = penguin.isSwimming();
		penguin.setPose(swimming ? Pose.SWIMMING : Pose.EMERGING);
		if (!swimming && penguin.onGround()) {
			penguin.stopInPlace();
		}
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		if (penguin.hasPose(Pose.EMERGING)) {
			boolean swimming = penguin.isSwimming();
			penguin.setPose(swimming ? Pose.SWIMMING : Pose.STANDING);
		}
	}
}
