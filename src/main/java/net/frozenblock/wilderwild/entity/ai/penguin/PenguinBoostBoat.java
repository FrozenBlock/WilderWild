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
	private static final int BOOST_TICKS = 100;

	@Contract(" -> new")
	public static @NotNull OneShot<LivingEntity> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(WWMemoryModuleTypes.TRACKED_BOAT)
			).apply(
				instance,
			(trackedBoat) -> (serverLevel, livingEntity, l) -> {
					final Boat boat = instance.get(trackedBoat);
					if (boat instanceof BoatBoostInterface boatBoostInterface && livingEntity.distanceTo(boat) < MAX_DISTANCE) {
						boatBoostInterface.wilderWild$boostBoatForTicks(BOOST_TICKS);
						return true;
					}
					return true;
				}
			)
		);
	}
}
