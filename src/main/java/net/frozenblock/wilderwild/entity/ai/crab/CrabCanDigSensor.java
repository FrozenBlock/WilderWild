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

package net.frozenblock.wilderwild.entity.ai.crab;

import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;

public class CrabCanDigSensor extends Sensor<Crab> {

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Set.of(WWMemoryModuleTypes.CAN_DIG);
	}

	@Override
	protected void doTick(ServerLevel level, Crab crab) {
		final Brain<?> brain = crab.getBrain();
		if (crab.canHideOnGround()) {
			brain.setMemory(WWMemoryModuleTypes.CAN_DIG, true);
		} else {
			brain.eraseMemory(WWMemoryModuleTypes.CAN_DIG);
		}
	}

}
