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
			),
			400
		);
	}

	@Override
	protected boolean canStillUse(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		return !penguin.isSwimming();
	}

	@Override
	protected void start(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		Brain<Penguin> brain = penguin.getBrain();
		brain.setMemoryWithExpiry(WWMemoryModuleTypes.SEARCHING_FOR_WATER, Unit.INSTANCE, 400L);
		brain.setMemory(WWMemoryModuleTypes.LAYING_DOWN, Unit.INSTANCE);
		penguin.setPose(Pose.SLIDING);
	}

	@Override
	protected void stop(@NotNull ServerLevel level, @NotNull E penguin, long gameTime) {
		Brain<Penguin> brain = penguin.getBrain();
		brain.eraseMemory(WWMemoryModuleTypes.SEARCHING_FOR_WATER);
		brain.eraseMemory(WWMemoryModuleTypes.LAYING_DOWN);

		if (!penguin.isTouchingWaterOrSwimming()) {
			brain.setMemory(WWMemoryModuleTypes.IDLE_TIME, 400);
			brain.setMemoryWithExpiry(WWMemoryModuleTypes.STANDING_UP, Unit.INSTANCE, PenguinAi.STAND_UP_DURATION);
		} else {
			brain.setMemory(WWMemoryModuleTypes.DIVE_TICKS, 400);
		}
	}
}
