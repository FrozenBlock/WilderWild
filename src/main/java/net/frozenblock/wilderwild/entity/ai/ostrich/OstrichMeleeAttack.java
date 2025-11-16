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

public class OstrichMeleeAttack {

	@Contract("_ -> new")
	public static OneShot<Ostrich> create(int cooldownBetweenAttacks) {
		return BehaviorBuilder.create(instance -> instance.group(
			instance.registered(MemoryModuleType.LOOK_TARGET),
			instance.present(MemoryModuleType.ATTACK_TARGET),
			instance.absent(MemoryModuleType.ATTACK_COOLING_DOWN),
			instance.present(MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES))
		.apply(instance, (lookTarget, attackTarget, attackCoolingDown, nearestEntities) -> (level, ostrich, l) -> {
			final LivingEntity target = instance.get(attackTarget);
			if (!isHoldingUsableProjectileWeapon(ostrich)
				&& ostrich.isWithinMeleeAttackRange(target)
				&& instance.get(nearestEntities).contains(target)
			) {
				lookTarget.set(new EntityTracker(target, true));
				ostrich.swing(InteractionHand.MAIN_HAND);
				attackCoolingDown.setWithExpiry(true, cooldownBetweenAttacks);
				return true;
			}
			return false;
		}));
	}

	private static boolean isHoldingUsableProjectileWeapon(Ostrich ostrich) {
		return ostrich.isHolding(stack -> {
			final Item item = stack.getItem();
			return item instanceof ProjectileWeaponItem projectileWeaponItem && ostrich.canFireProjectileWeapon(projectileWeaponItem);
		});
	}
}
