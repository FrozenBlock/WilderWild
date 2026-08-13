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

package net.frozenblock.wilderwild.entity.ai.penguin;

import net.frozenblock.wilderwild.registry.WWAttachmentTypes;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;

public final class PenguinBoostBoat {
	private static final double MAX_DISTANCE = 3D;
	private static final int BOOST_TICKS = 100;

	private PenguinBoostBoat() {}

	public static OneShot<LivingEntity> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(WWMemoryModuleTypes.TRACKED_BOAT.get())
		).apply(instance, (trackedBoat) -> (level, body, timestamp) -> {
			final AbstractBoat boat = instance.get(trackedBoat);
			if (body.distanceTo(boat) < MAX_DISTANCE) {
				boostForTicks(boat, BOOST_TICKS);
				return true;
			}
			return true;
		}));
	}

	public static void boostForTicks(AbstractBoat boat, int ticks) {
		WWAttachmentTypes.BOAT_BOOST_TICKS.set(boat, Math.max(WWAttachmentTypes.BOAT_BOOST_TICKS.getAttachedOrElse(boat, 0), ticks));
	}

	public static boolean isBoosted(AbstractBoat boat) {
		return WWAttachmentTypes.BOAT_BOOST_TICKS.getAttachedOrElse(boat, 0) > 0;
	}
}
