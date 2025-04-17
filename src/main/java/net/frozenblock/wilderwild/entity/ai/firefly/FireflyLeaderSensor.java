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

package net.frozenblock.wilderwild.entity.ai.firefly;

import com.google.common.collect.ImmutableSet;
import java.util.List;
import java.util.Set;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;

public class FireflyLeaderSensor extends Sensor<Firefly> {
	private static final double NON_LEADER_MAX_DISTANCE = 6D;
	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.NEARBY_FIREFLIES, WWMemoryModuleTypes.IS_SWARM_LEADER, WWMemoryModuleTypes.SWARM_LEADER_TRACKER);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Firefly firefly) {
		Brain<Firefly> brain = firefly.getBrain();
		if (!firefly.hasHome()) {
			if (!firefly.isSwarmLeader()) {
				List<Firefly> leaderFireflies = FireflyAi.getNearbyFirefliesInRank(firefly, true);

				if (!leaderFireflies.isEmpty()) {
					brain.setMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER, new EntityTracker(leaderFireflies.getFirst(), true));
					return;
				} else {
					FireflyAi.setSwarmLeader(firefly);
				}
			} else {
				List<Firefly> nonLeaderFirefliesCloseBy = FireflyAi.getNearbyFirefliesInRank(firefly, false)
					.stream().filter(otherFirefly -> otherFirefly.distanceTo(firefly) <= NON_LEADER_MAX_DISTANCE)
					.toList();
				List<Firefly> leaderFireflies = FireflyAi.getNearbyFirefliesInRank(firefly, true);

				if (nonLeaderFirefliesCloseBy.isEmpty() && !leaderFireflies.isEmpty()) {
					brain.eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER);
				}
			}
		}
		brain.eraseMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER);
	}
}
