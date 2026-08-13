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

package net.frozenblock.wilderwild.entity.ai.crab;

import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.registry.WWMemoryModuleTypes;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import org.jetbrains.annotations.Contract;

public final class CrabHeal {

	private CrabHeal() {}

	public static BehaviorControl<Crab> create() {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.present(WWMemoryModuleTypes.IS_UNDERGROUND.get()),
			instance.present(MemoryModuleType.DIG_COOLDOWN),
			instance.registered(WWMemoryModuleTypes.HEAL_COOLDOWN_TICKS.get())
		).apply(instance, (underground, digCooldown, healCooldown) -> (level, body, timestamp) -> {
			final Brain<Crab> brain = body.getBrain();
			if (brain.getMemory(WWMemoryModuleTypes.HEAL_COOLDOWN_TICKS.get()).isPresent()) {
				final int cooldownTicks = brain.getMemory(WWMemoryModuleTypes.HEAL_COOLDOWN_TICKS.get()).get();
				if (cooldownTicks > 0) {
					healCooldown.setWithExpiry(cooldownTicks - 1, 5L);
					return true;
				}
			}
			healCooldown.setWithExpiry(20, 5L);
			body.heal(0.05F);
			return true;
		}));
	}
}
