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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.lib.item.api.registry.PotionBrewingRegistry;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.lib.platform.api.registry.DeferredRegister;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

public final class WWPotions {
	private static final DeferredRegister<Potion> REGISTER = DeferredRegister.create(
		Registries.POTION,
		WWConstants.MOD_ID
	);

	public static final DeferredHolder<Potion, Potion> REACH = REGISTER.register("reach",
		() -> new Potion("reach",
			new MobEffectInstance(WWMobEffects.REACH_BOOST.asHolder(), 3600)
		)
	);
	public static final DeferredHolder<Potion, Potion> LONG_REACH = REGISTER.register("long_reach",
		() -> new Potion(
			"reach",
			new MobEffectInstance(WWMobEffects.REACH_BOOST.asHolder(), 9600)
		)
	);
	public static final DeferredHolder<Potion, Potion> STRONG_REACH = REGISTER.register("strong_reach",
		() -> new Potion(
			"reach",
			new MobEffectInstance(WWMobEffects.REACH_BOOST.asHolder(), 2700, 1)
		)
	);
	public static final DeferredHolder<Potion, Potion> SCORCHING = REGISTER.register("scorching",
		() -> new Potion(
			"scorching",
			new MobEffectInstance(WWMobEffects.SCORCHING.asHolder(), 2700)
		)
	);

	static {
		REGISTER.register();
	}

	// TODO: fabric & neo or 26.3's datadriven stuff
	public static void init() {
		PotionBrewingRegistry.BUILD.register(builder -> {
			builder.frozenLib$registerPotionRecipe(Potions.AWKWARD, Ingredient.of(WWItems.CRAB_CLAW), REACH.asHolder());
			builder.frozenLib$registerPotionRecipe(REACH.asHolder(), Ingredient.of(Items.REDSTONE), LONG_REACH.asHolder());
			builder.frozenLib$registerPotionRecipe(REACH.asHolder(), Ingredient.of(Items.GLOWSTONE_DUST), STRONG_REACH.asHolder());

			builder.frozenLib$registerPotionRecipe(Potions.AWKWARD, Ingredient.of(WWItems.FERMENTED_SCORCHED_EYE), SCORCHING.asHolder());
		});
	}

	private WWPotions() {}
}
