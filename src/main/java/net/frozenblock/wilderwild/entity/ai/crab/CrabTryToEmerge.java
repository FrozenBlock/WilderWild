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

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.Contract;

public class CrabTryToEmerge {

	@Contract(" -> new")
	public static BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.absent(MemoryModuleType.IS_EMERGING),
			instance.present(WWMemoryModuleTypes.IS_UNDERGROUND),
			instance.registered(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(MemoryModuleType.NEAREST_PLAYERS)
		).apply(instance, (isEmerging, underground, digCooldown, players) -> (level, crab, l) -> {
			final Brain<Crab> brain = crab.getBrain();
			if (crab.canEmerge() || !crab.canHideOnGround()) {
				if (brain.checkMemory(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT) ||
					(brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).isPresent() &&
						brain.getMemory(MemoryModuleType.NEAREST_PLAYERS).get().stream().anyMatch(player -> player.distanceTo(crab) < CrabAi.UNDERGROUND_PLAYER_RANGE))
				) {
					isEmerging.setWithExpiry(Unit.INSTANCE, Crab.EMERGE_LENGTH_IN_TICKS);
					underground.erase();
					return true;
				}
			} else {
				digCooldown.setWithExpiry(Unit.INSTANCE, 40L);
			}
			return false;
		}));
	}
}
