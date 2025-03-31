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

package net.frozenblock.wilderwild.entity.ai.ostrich;

import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class OstrichMeleeAttack {

	@Contract("_ -> new")
	public static @NotNull OneShot<Ostrich> create(int cooldownBetweenAttacks) {
		return BehaviorBuilder.create(instance -> instance.group(
				instance.registered(MemoryModuleType.LOOK_TARGET),
				instance.present(MemoryModuleType.ATTACK_TARGET),
				instance.absent(MemoryModuleType.ATTACK_COOLING_DOWN),
				instance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES)
			).apply(
				instance,
				(memoryAccessor, memoryAccessor2, memoryAccessor3, memoryAccessor4) -> (world, ostrich, l) -> {
					LivingEntity livingEntity = instance.get(memoryAccessor2);
					if (!isHoldingUsableProjectileWeapon(ostrich)
						&& ostrich.isWithinMeleeAttackRange(livingEntity)
						&& instance.get(memoryAccessor4).contains(livingEntity)) {
						memoryAccessor.set(new EntityTracker(livingEntity, true));
						ostrich.swing(InteractionHand.MAIN_HAND);
						memoryAccessor3.setWithExpiry(true, cooldownBetweenAttacks);
						return true;
					} else {
						return false;
					}
				}
			)
		);
	}

	private static boolean isHoldingUsableProjectileWeapon(@NotNull Ostrich ostrich) {
		return ostrich.isHolding(stack -> {
			Item item = stack.getItem();
			return item instanceof ProjectileWeaponItem && ostrich.canFireProjectileWeapon((ProjectileWeaponItem) item);
		});
	}
}
