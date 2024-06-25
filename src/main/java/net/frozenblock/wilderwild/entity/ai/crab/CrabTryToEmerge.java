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

package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.RegisterMemoryModuleTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class CrabTryToEmerge {

	@Contract(" -> new")
	public static @NotNull BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.absent(MemoryModuleType.IS_EMERGING),
			instance.present(RegisterMemoryModuleTypes.IS_UNDERGROUND),
			instance.registered(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(MemoryModuleType.NEAREST_PLAYERS)
		).apply(instance, (isEmerging, underground, digCooldown, players) -> (world, crab, l) -> {
			if (crab.canEmerge() || !crab.canHideOnGround()) {
				if (crab.getBrain().checkMemory(MemoryModuleType.DIG_COOLDOWN, MemoryStatus.VALUE_ABSENT) ||
					(crab.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).isPresent() &&
						crab.getBrain().getMemory(MemoryModuleType.NEAREST_PLAYERS).get().stream().anyMatch(player -> player.distanceTo(crab) < CrabAi.UNDERGROUND_PLAYER_RANGE))
				) {
					isEmerging.setWithExpiry(Unit.INSTANCE, Crab.EMERGE_LENGTH_IN_TICKS);
					digCooldown.setWithExpiry(Unit.INSTANCE, CrabAi.getRandomDigCooldown(crab));
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
