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

package net.frozenblock.wilderwild.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public final class WWFood {
	public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).build();
	public static final FoodProperties CRAB_CLAW = new FoodProperties.Builder().nutrition(1).saturationMod(0.1F).build();
	public static final FoodProperties COOKED_CRAB_CLAW = new FoodProperties.Builder().nutrition(4).saturationMod(0.4F).build();
	public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();
	public static final FoodProperties SPLIT_COCONUT = new FoodProperties.Builder().nutrition(2).saturationMod(0.4F).build();
	public static final FoodProperties SCORCHED_EYE = new FoodProperties.Builder()
		.nutrition(3)
		.saturationMod(0.8F)
		.effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 1F)
		.build();

	private WWFood() {
		throw new UnsupportedOperationException("WWFood contains only static declarations.");
	}

}
