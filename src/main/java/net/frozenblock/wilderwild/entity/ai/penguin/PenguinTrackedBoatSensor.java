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
import org.jetbrains.annotations.NotNull;

public class PenguinTrackedBoatSensor extends Sensor<LivingEntity> {

	@Override
	@NotNull
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(WWMemoryModuleTypes.TRACKED_BOAT);
	}

	@Override
	protected void doTick(@NotNull ServerLevel level, @NotNull LivingEntity entity) {
		Brain<?> brain = entity.getBrain();

		if (entity.isPassenger()) {
			brain.eraseMemory(WWMemoryModuleTypes.TRACKED_BOAT);
			return;
		}

		AABB aABB = entity.getBoundingBox().inflate(16D, 16D, 16D);
		List<Boat> boats = level.getEntitiesOfClass(
			Boat.class,
			aABB,
			boat -> !boat.isSpectator()
				&& boat.isAlive()
				&& boat.getControllingPassenger() instanceof Player
				&& entity.hasLineOfSight(boat)
		);
		boats.sort(Comparator.comparingDouble(entity::distanceToSqr));

		List<Boat> temptingBoats = new ArrayList<>();
		boats.forEach(boat -> {
			if (boat.getControllingPassenger() instanceof Player player) {
				if (player.isHolding(PenguinAi.getTemptations())) {
					temptingBoats.add(boat);
				}
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
