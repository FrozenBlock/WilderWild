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

package net.frozenblock.wilderwild.entity.ai.firefly;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class FireflyLeaderSensor extends Sensor<Firefly> {
	private static final double FIREFLY_SWARM_LEADER_RANGE = 6D;

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.NEAREST_LIVING_ENTITIES, WWMemoryModuleTypes.IS_SWARM_LEADER, WWMemoryModuleTypes.SWARM_LEADER_TRACKER);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull Firefly firefly) {
		Brain<Firefly> brain = firefly.getBrain();
		if (!firefly.isSwarmLeader()) {
			List<LivingEntity> entities = new ArrayList<>(brain.getMemory(MemoryModuleType.NEAREST_LIVING_ENTITIES).orElse(ImmutableList.of()))
				.stream()
				.filter(livingEntity -> livingEntity.isAlive() && !livingEntity.isSpectator() && firefly.distanceTo(livingEntity) <= FIREFLY_SWARM_LEADER_RANGE)
				.filter(livingEntity -> livingEntity instanceof Firefly otherFirefly && otherFirefly.isSwarmLeader())
				.sorted(Comparator.comparingDouble(firefly::distanceToSqr))
				.toList();

			if (!entities.isEmpty()) {
				brain.setMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER, new EntityTracker(entities.getFirst(), true));
				return;
			}
		}
		brain.eraseMemory(WWMemoryModuleTypes.SWARM_LEADER_TRACKER);
	}
}
