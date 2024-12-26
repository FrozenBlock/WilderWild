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

package net.frozenblock.wilderwild.entity.ai.penguin;

import java.util.Map;
import net.frozenblock.wilderwild.entity.Penguin;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.NotNull;

public class PenguinSearchingForWater<E extends Penguin> extends Behavior<E> {

	public PenguinSearchingForWater() {
		super(
			Map.of(
				MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT,
				WWMemoryModuleTypes.SEARCHING_FOR_WATER, MemoryStatus.REGISTERED,
				MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT
			)
		);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return !penguin.isSwimming();
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		penguin.getBrain().setMemoryWithExpiry(WWMemoryModuleTypes.SEARCHING_FOR_WATER, Unit.INSTANCE, 400L);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		penguin.getBrain().eraseMemory(WWMemoryModuleTypes.SEARCHING_FOR_WATER);
		Brain<Penguin> brain = penguin.getBrain();

		if (penguin.hasPose(Pose.SLIDING)) penguin.setPose(Pose.STANDING);

		if (!penguin.isSwimming()) {
			brain.setMemory(WWMemoryModuleTypes.IDLE_TIME, 1200);
			brain.setMemory(WWMemoryModuleTypes.RISING_TO_STAND_UP, Unit.INSTANCE);
		}

	}
}
