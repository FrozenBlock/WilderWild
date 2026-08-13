/*
 * Copyright 2025-2026 FrozenBlock
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

public class FireflyLeaderSensor extends Sensor<Firefly> {
	private static final double NON_LEADER_MAX_DISTANCE = 6D;

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.NEARBY_FIREFLIES.get(), WWMemoryModuleTypes.IS_SWARM_LEADER.get(), WWMemoryModuleTypes.SWARM_LEADER_TRACKER.get());
	}

	@Override
	protected void doTick(ServerLevel level, Firefly body) {
		final Brain<Firefly> brain = body.getBrain();
		if (!body.hasHome()) {
			if (!body.isSwarmLeader()) {
				final List<Firefly> leaderFireflies = FireflyAi.getNearbyFirefliesInRank(body, true);

				if (!leaderFireflies.isEmpty()) {
					brain.setMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER.get(), new EntityTracker(leaderFireflies.getFirst(), true));
					return;
				}
				FireflyAi.setSwarmLeader(body);
			} else {
				final List<Firefly> nonLeaderFirefliesCloseBy = FireflyAi.getNearbyFirefliesInRank(body, false)
					.stream().filter(otherFirefly -> otherFirefly.distanceTo(body) <= NON_LEADER_MAX_DISTANCE)
					.toList();
				final List<Firefly> leaderFireflies = FireflyAi.getNearbyFirefliesInRank(body, true);

				if (nonLeaderFirefliesCloseBy.isEmpty() && !leaderFireflies.isEmpty()) brain.eraseMemory(WWMemoryModuleTypes.IS_SWARM_LEADER.get());
			}
		}
		brain.eraseMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER.get());
	}
}
