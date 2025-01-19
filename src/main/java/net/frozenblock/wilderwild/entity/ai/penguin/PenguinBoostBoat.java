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

import net.frozenblock.wilderwild.entity.impl.BoatBoostInterface;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.vehicle.Boat;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class PenguinBoostBoat {
	private static final double MAX_DISTANCE = 3D;
	private static final int BOOST_TICKS = 200;

	@Contract(" -> new")
	public static @NotNull OneShot<LivingEntity> create() {
		return BehaviorBuilder.create(
			instance -> instance.group(instance.present(WWMemoryModuleTypes.TRACKED_BOAT))
				.apply(
					instance,
					(trackedBoat) -> (serverLevel, livingEntity, l) -> {
						Boat boat = instance.get(trackedBoat);
						if (boat instanceof BoatBoostInterface boatBoostInterface) {
							if (livingEntity.distanceTo(boat) < MAX_DISTANCE) {
								boatBoostInterface.wilderWild$boostBoatForTicks(BOOST_TICKS);
								return true;
							}
						}
						return true;
					}
				)
		);
	}
}
