package net.frozenblock.wilderwild.registry;

import net.minecraft.world.food.FoodProperties;

public final class RegisterFood {
	private RegisterFood() {
		throw new UnsupportedOperationException("RegisterFood contains only static declarations.");
	}

	public static final FoodProperties BAOBAB_NUT = new FoodProperties.Builder().nutrition(1).saturationMod(0.2F).build();
	public static final FoodProperties PRICKLY_PEAR = new FoodProperties.Builder().nutrition(2).saturationMod(0.3F).build();

}
