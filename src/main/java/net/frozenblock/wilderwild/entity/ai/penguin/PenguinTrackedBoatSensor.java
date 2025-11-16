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

package net.frozenblock.wilderwild.entity.ai.penguin;

import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.AABB;

public class PenguinTrackedBoatSensor extends Sensor<LivingEntity> {

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.TRACKED_BOAT);
	}

	@Override
	protected void doTick(ServerLevel level, LivingEntity entity) {
		final Brain<?> brain = entity.getBrain();

		if (entity.isPassenger()) {
			brain.eraseMemory(WWMemoryModuleTypes.TRACKED_BOAT);
			return;
		}

		final AABB searchArea = entity.getBoundingBox().inflate(16D, 16D, 16D);
		final List<Boat> boats = level.getEntitiesOfClass(
			Boat.class,
			searchArea,
			boat -> !boat.isSpectator()
				&& boat.isAlive()
				&& boat.getControllingPassenger() instanceof Player
				&& entity.hasLineOfSight(boat)
		);
		boats.sort(Comparator.comparingDouble(entity::distanceToSqr));

		final List<Boat> temptingBoats = new ArrayList<>();
		boats.forEach(boat -> {
			if (boat.getControllingPassenger() instanceof Player player && player.isHolding(PenguinAi.getTemptations())) {
				temptingBoats.add(boat);
			}
		});
		temptingBoats.sort(Comparator.comparingDouble(entity::distanceToSqr));

		if (!temptingBoats.isEmpty()) {
			brain.setMemory(WWMemoryModuleTypes.TRACKED_BOAT, temptingBoats.getFirst());
		} else if (!boats.isEmpty()) {
			brain.setMemory(WWMemoryModuleTypes.TRACKED_BOAT, boats.getFirst());
		} else {
			brain.eraseMemory(WWMemoryModuleTypes.TRACKED_BOAT);
		}
	}
}
