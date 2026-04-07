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

package net.frozenblock.wilderwild.datagen.recipe;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.registry.WilderWildRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.ColorCollection;

public final class FireflyBottleRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output, HolderLookup.RegistryLookup<FireflyColor> registryLookup) {
		final List<Identifier> colorIds = ColorCollection.make(color -> WWConstants.id(color.getName())).asList();
		colorFireflyBottlesWithDyes(provider, output, colorIds, registryLookup);

		final List<Item> dyes = Items.DYE.asList();
		for(int dyeIndex = 0; dyeIndex < dyes.size(); dyeIndex++) {
			final Item dye = dyes.get(dyeIndex);
			final Identifier outputColor = colorIds.get(dyeIndex);
			fireflyBottle(provider, outputColor, dye, output, registryLookup);
		}
	}

	private static void colorFireflyBottlesWithDyes(
		RecipeProvider provider,
		RecipeOutput output,
		List<Identifier> fireflyColors,
		HolderLookup.RegistryLookup<FireflyColor> registryLookup
	) {
		final List<Item> dyes = Items.DYE.asList();
		for(int dyeIndex = 0; dyeIndex < dyes.size(); dyeIndex++) {
			final Item dye = dyes.get(dyeIndex);
			final Identifier outputColor = fireflyColors.get(dyeIndex);

			final List<Ingredient> possibleIngredients = new ArrayList<>();
			for (Identifier fireflyColor : fireflyColors) {
				if (fireflyColor.equals(outputColor)) continue;

				possibleIngredients.add(DefaultCustomIngredients.components(
					Ingredient.of(WWItems.FIREFLY_BOTTLE),
					DataComponentPatch.builder()
						.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
						.build()
				));
			}

			final Ingredient input = DefaultCustomIngredients.any(possibleIngredients.toArray(new Ingredient[0]));

			provider.shapeless(
				RecipeCategory.MISC,
				new ItemStackTemplate(
					WWItems.FIREFLY_BOTTLE,
					DataComponentPatch.builder()
						.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, outputColor)))
						.build()
				)
			)
			.requires(dye)
			.requires(input)
			.group("firefly_bottle")
			.unlockedBy("has_needed_dye", provider.has(dye))
			.save(
				output,
				WWConstants.string("dye_" + outputColor.getPath() + "_firefly_bottle")
			);
		}
	}

	private static void fireflyBottle(
		RecipeProvider provider,
		Identifier fireflyColor,
		Item dye,
		RecipeOutput output,
		HolderLookup.RegistryLookup<FireflyColor> registryLookup
	) {
		provider.shapeless(
			RecipeCategory.MISC,
			new ItemStackTemplate(
				WWItems.FIREFLY_BOTTLE,
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
					.build()
			)
		)
		.group("firefly_bottle")
		.requires(dye)
		.requires(
			DefaultCustomIngredients.components(
				Ingredient.of(WWItems.FIREFLY_BOTTLE),
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, WWConstants.id("on"))))
					.build()
			)
		)
		.unlockedBy("has_firefly_bottle", provider.has(WWItems.FIREFLY_BOTTLE))
		.save(output, WWConstants.string(fireflyColor.getPath() + "_firefly_bottle"));
	}

}
