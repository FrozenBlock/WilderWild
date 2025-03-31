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

package net.frozenblock.wilderwild.datagen.recipe;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapelessRecipeBuilderExtension;
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
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public final class FireflyBottleRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter, HolderLookup.RegistryLookup<FireflyColor> registryLookup) {
		colorFireflyBottlesWithDyes(
			provider,
			exporter,
			ImmutableList.of(
				Items.BLACK_DYE,
				Items.BLUE_DYE,
				Items.BROWN_DYE,
				Items.CYAN_DYE,
				Items.GRAY_DYE,
				Items.GREEN_DYE,
				Items.LIGHT_BLUE_DYE,
				Items.LIGHT_GRAY_DYE,
				Items.LIME_DYE,
				Items.MAGENTA_DYE,
				Items.ORANGE_DYE,
				Items.PINK_DYE,
				Items.PURPLE_DYE,
				Items.RED_DYE,
				Items.WHITE_DYE,
				Items.YELLOW_DYE
			),
			ImmutableList.of(
				 WWConstants.id("black"),
				 WWConstants.id("blue"),
				 WWConstants.id("brown"),
				 WWConstants.id("cyan"),
				 WWConstants.id("gray"),
				 WWConstants.id("green"),
				 WWConstants.id("light_blue"),
				 WWConstants.id("light_gray"),
				 WWConstants.id("lime"),
				 WWConstants.id("magenta"),
				 WWConstants.id("orange"),
				 WWConstants.id("pink"),
				 WWConstants.id("purple"),
				 WWConstants.id("red"),
				 WWConstants.id("white"),
				 WWConstants.id("yellow")
			),
			registryLookup
		);

		fireflyBottle(provider, WWConstants.id("black"), Items.BLACK_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("blue"), Items.BLUE_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("brown"), Items.BROWN_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("cyan"), Items.CYAN_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("gray"), Items.GRAY_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("green"), Items.GREEN_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("light_blue"), Items.LIGHT_BLUE_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("light_gray"), Items.LIGHT_GRAY_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("lime"), Items.LIME_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("magenta"), Items.MAGENTA_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("orange"), Items.ORANGE_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("pink"), Items.PINK_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("purple"), Items.PURPLE_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("red"), Items.RED_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("white"), Items.WHITE_DYE, exporter, registryLookup);
		fireflyBottle(provider, WWConstants.id("yellow"), Items.YELLOW_DYE, exporter, registryLookup);
	}

	private static void colorFireflyBottlesWithDyes(
		RecipeProvider provider,
		RecipeOutput recipeOutput,
		@NotNull List<Item> dyes,
		List<ResourceLocation> fireflyColors,
		HolderLookup.RegistryLookup<FireflyColor> registryLookup
	) {
		for(int i = 0; i < dyes.size(); ++i) {
			Item dye = dyes.get(i);
			ResourceLocation outputColor = fireflyColors.get(i);

			List<Ingredient> possibleIngredients = new ArrayList<>();

			for (ResourceLocation fireflyColor : fireflyColors) {
				if (fireflyColor.equals(outputColor)) continue;

				possibleIngredients.add(DefaultCustomIngredients.components(
					Ingredient.of(WWItems.FIREFLY_BOTTLE),
					DataComponentPatch.builder()
						.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
						.build()
				));
			}

			Ingredient input = DefaultCustomIngredients.any(possibleIngredients.toArray(new Ingredient[0]));

			((ShapelessRecipeBuilderExtension) provider.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
				.requires(dye)
				.requires(input)
				.group("firefly_bottle")
				.unlockedBy("has_needed_dye", provider.has(dye))
			).frozenLib$patch(
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, outputColor)))
					.build()
			).save(
				recipeOutput,
				WWConstants.string("dye_" + outputColor.getPath() + "_firefly_bottle")
			);
		}
	}

	private static void fireflyBottle(
		@NotNull RecipeProvider provider,
		@NotNull ResourceLocation fireflyColor,
		Item dye,
		RecipeOutput exporter,
		HolderLookup.@NotNull RegistryLookup<FireflyColor> registryLookup
	) {
		((ShapelessRecipeBuilderExtension) provider.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.requires(dye)
			.requires(DefaultCustomIngredients.components(
				Ingredient.of(WWItems.FIREFLY_BOTTLE),
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, WWConstants.id("on"))))
					.build()
			))
			.unlockedBy("has_firefly_bottle", provider.has(WWItems.FIREFLY_BOTTLE))
		).frozenLib$patch(
			DataComponentPatch.builder()
				.set(WWDataComponents.FIREFLY_COLOR, registryLookup.getOrThrow(ResourceKey.create(WilderWildRegistries.FIREFLY_COLOR, fireflyColor)))
				.build()
			).save(exporter, WWConstants.string(fireflyColor.getPath() + "_firefly_bottle"));
	}

}
