/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.item.impl;

import lombok.experimental.UtilityClass;
import net.frozenblock.wilderwild.registry.WWEnchantmentEffectComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.jetbrains.annotations.ApiStatus;

@UtilityClass
@ApiStatus.Internal
public final class WWEnchantmentHelper {

	public static float modifyHarpoonGrapplingTime(ServerLevel serverLevel, ItemStack weapon, Entity harpoon, float time) {
		final MutableFloat modifiedTime = new MutableFloat(time);
		EnchantmentHelper.runIterationOnItem(
			weapon,
			(enchantment, level) -> enchantment.value().modifyEntityFilteredValue(
				WWEnchantmentEffectComponents.HARPOON_GRAPPLING_TIME.get(),
				serverLevel,
				level,
				weapon,
				harpoon,
				modifiedTime
			)
		);

		return Math.max(0F, modifiedTime.floatValue());
	}

	public static float modifyHarpoonGrappleStrength(ServerLevel serverLevel, ItemStack weapon, Entity harpoon, float strength) {
		final MutableFloat modifiedStrength = new MutableFloat(strength);
		EnchantmentHelper.runIterationOnItem(
			weapon,
			(enchantment, level) -> enchantment.value().modifyEntityFilteredValue(
				WWEnchantmentEffectComponents.HARPOON_GRAPPLE_STRENGTH.get(),
				serverLevel,
				level,
				weapon,
				harpoon,
				modifiedStrength
			)
		);

		return Math.max(0F, modifiedStrength.floatValue());
	}

	public static float modifyHarpoonGrappleMaxCount(ServerLevel serverLevel, ItemStack weapon, Entity harpoon, int maxGrappleCount) {
		final MutableFloat modifiedGrappleCount = new MutableFloat(maxGrappleCount);
		EnchantmentHelper.runIterationOnItem(
			weapon,
			(enchantment, level) -> enchantment.value().modifyEntityFilteredValue(
				WWEnchantmentEffectComponents.HARPOON_GRAPPLE_MAX_COUNT.get(),
				serverLevel,
				level,
				weapon,
				harpoon,
				modifiedGrappleCount
			)
		);

		return Math.max(0, Mth.floor(modifiedGrappleCount.floatValue()));
	}
}
