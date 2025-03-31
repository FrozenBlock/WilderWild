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

package net.frozenblock.wilderwild.entity.ai.crab;

import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class CrabCanDigSensor extends Sensor<Crab> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(WWMemoryModuleTypes.CAN_DIG);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Crab crab) {
		Brain<?> brain = crab.getBrain();
		if (crab.canHideOnGround()) {
			brain.setMemory(WWMemoryModuleTypes.CAN_DIG, true);
		} else {
			brain.eraseMemory(WWMemoryModuleTypes.CAN_DIG);
		}
	}

}
