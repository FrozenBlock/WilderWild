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

import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public final class RegisterPotions {

	public static final Reference<Potion> REACH = register("reach", new Potion(new MobEffectInstance(RegisterMobEffects.REACH_BOOST, 3600)));
	public static final Reference<Potion> LONG_REACH = register("long_reach", new Potion("reach", new MobEffectInstance(RegisterMobEffects.REACH_BOOST, 9600)));
	public static final Reference<Potion> STRONG_REACH = register("strong_reach", new Potion("reach", new MobEffectInstance(RegisterMobEffects.REACH_BOOST, 2700, 1)));
	public static final Holder<Potion> SCORCHING = register("scorching", new Potion("scorching", new MobEffectInstance(RegisterMobEffects.SCORCHING, 2700)));

	private RegisterPotions() {
		throw new UnsupportedOperationException("RegisterPotions contains only static declarations.");
	}

	public static void init() {
		WilderConstants.logWithModId("Registering Potions for", WilderConstants.UNSTABLE_LOGGING);

		FabricBrewingRecipeRegistryBuilder.BUILD.register(boringBuilder -> {
			if (boringBuilder instanceof FabricBrewingRecipeRegistryBuilder builder) {
				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(RegisterItems.CRAB_CLAW), REACH);
				builder.registerPotionRecipe(REACH, Ingredient.of(Items.REDSTONE), LONG_REACH);
				builder.registerPotionRecipe(REACH, Ingredient.of(Items.GLOWSTONE_DUST), STRONG_REACH);

				builder.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(RegisterItems.FERMENTED_SCORCHED_EYE), SCORCHING);
			}
		});
	}

	private static @NotNull Reference<Potion> register(String key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, WilderConstants.id(key), potion);
	}

	private static @NotNull Reference<Potion> register(ResourceKey<Potion> key, Potion potion) {
		return Registry.registerForHolder(BuiltInRegistries.POTION, key, potion);
	}
}
