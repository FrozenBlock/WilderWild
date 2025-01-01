/*
 * Copyright 2023-2025 FrozenBlock
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

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public final class WWPotions {
	public static final Reference<Potion> REACH = register("reach", new Potion("reach", new MobEffectInstance(WWMobEffects.REACH_BOOST, 3600)));
	public static final Reference<Potion> LONG_REACH = register("long_reach", new Potion("reach", new MobEffectInstance(WWMobEffects.REACH_BOOST, 9600)));
	public static final Reference<Potion> STRONG_REACH = register("strong_reach", new Potion("reach", new MobEffectInstance(WWMobEffects.REACH_BOOST, 2700, 1)));
	public static final Holder<Potion> SCORCHING = register("scorching", new Potion("scorching", new MobEffectInstance(WWMobEffects.SCORCHING, 2700)));

	private WWPotions() {
		throw new UnsupportedOperationException("WWPotions contains only static declarations.");
	}

	public static void init() {
		WWConstants.logWithModId("Registering Potions for", WWConstants.UNSTABLE_LOGGING);

		FabricBrewingRecipeRegistryBuilder.BUILD.register(boringBuilder -> {
			if (boringBuilder instanceof FabricBrewingRecipeRegistryBuilder builder) {
				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(WWItems.CRAB_CLAW), REACH);
				builder.registerPotionRecipe(REACH, Ingredient.of(Items.REDSTONE), LONG_REACH);
				builder.registerPotionRecipe(REACH, Ingredient.of(Items.GLOWSTONE_DUST), STRONG_REACH);

				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(WWItems.FERMENTED_SCORCHED_EYE), SCORCHING);
			}
		});
	}

	private static @NotNull Reference<Potion> register(String key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, WWConstants.id(key), potion);
	}
}
