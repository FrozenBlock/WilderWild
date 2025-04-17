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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public final class WWFood {
	public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).saturationModifier(0.2F).build();
	public static final FoodProperties CRAB_CLAW = new FoodProperties.Builder().nutrition(1).saturationModifier(0.1F).build();
	public static final FoodProperties COOKED_CRAB_CLAW = new FoodProperties.Builder().nutrition(4).saturationModifier(0.4F).build();
	public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationModifier(0.3F).build();
	public static final FoodProperties SPLIT_COCONUT = new FoodProperties.Builder().nutrition(2).saturationModifier(0.4F).build();
	public static final FoodProperties SCORCHED_EYE = new FoodProperties.Builder()
		.nutrition(3)
		.saturationModifier(0.8F)
		.effect(new MobEffectInstance(MobEffects.POISON, 200, 0), 1F)
		.build();

	private WWFood() {
		throw new UnsupportedOperationException("WWFood contains only static declarations.");
	}

}
