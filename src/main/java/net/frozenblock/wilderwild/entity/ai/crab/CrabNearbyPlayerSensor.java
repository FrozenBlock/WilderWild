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

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class CrabNearbyPlayerSensor extends Sensor<Crab> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_PLAYERS, WWMemoryModuleTypes.IS_PLAYER_NEARBY);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Crab crab) {
		final Brain<?> brain = crab.getBrain();
		if (brain.hasMemoryValue(MemoryModuleType.NEAREST_PLAYERS) && !brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).get().isEmpty()) {
			brain.setMemory(WWMemoryModuleTypes.IS_PLAYER_NEARBY, true);
		} else {
			brain.eraseMemory(WWMemoryModuleTypes.IS_PLAYER_NEARBY);
		}
	}

}
