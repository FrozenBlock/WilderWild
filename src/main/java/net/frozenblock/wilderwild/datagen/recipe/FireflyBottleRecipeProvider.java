/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.datagen.recipe;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.recipe.v1.ingredient.DefaultCustomIngredients;
import net.frozenblock.lib.recipe.api.ShapelessRecipeBuilderExtension;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;

public final class FireflyBottleRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		colorFireflyBottlesWithDyes(
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
				 WWConstants.string("black"),
				 WWConstants.string("blue"),
				 WWConstants.string("brown"),
				 WWConstants.string("cyan"),
				 WWConstants.string("gray"),
				 WWConstants.string("green"),
				 WWConstants.string("light_blue"),
				 WWConstants.string("light_gray"),
				 WWConstants.string("lime"),
				 WWConstants.string("magenta"),
				 WWConstants.string("orange"),
				 WWConstants.string("pink"),
				 WWConstants.string("purple"),
				 WWConstants.string("red"),
				 WWConstants.string("white"),
				 WWConstants.string("yellow")
			)
		);

		fireflyBottle(WWConstants.string("black"), Items.BLACK_DYE, exporter);
		fireflyBottle(WWConstants.string("blue"), Items.BLUE_DYE, exporter);
		fireflyBottle(WWConstants.string("brown"), Items.BROWN_DYE, exporter);
		fireflyBottle(WWConstants.string("cyan"), Items.CYAN_DYE, exporter);
		fireflyBottle(WWConstants.string("gray"), Items.GRAY_DYE, exporter);
		fireflyBottle(WWConstants.string("green"), Items.GREEN_DYE, exporter);
		fireflyBottle(WWConstants.string("light_blue"), Items.LIGHT_BLUE_DYE, exporter);
		fireflyBottle(WWConstants.string("light_gray"), Items.LIGHT_GRAY_DYE, exporter);
		fireflyBottle(WWConstants.string("lime"), Items.LIME_DYE, exporter);
		fireflyBottle(WWConstants.string("magenta"), Items.MAGENTA_DYE, exporter);
		fireflyBottle(WWConstants.string("orange"), Items.ORANGE_DYE, exporter);
		fireflyBottle(WWConstants.string("pink"), Items.PINK_DYE, exporter);
		fireflyBottle(WWConstants.string("purple"), Items.PURPLE_DYE, exporter);
		fireflyBottle(WWConstants.string("red"), Items.RED_DYE, exporter);
		fireflyBottle(WWConstants.string("white"), Items.WHITE_DYE, exporter);
		fireflyBottle(WWConstants.string("yellow"), Items.YELLOW_DYE, exporter);
	}

	private static void colorFireflyBottlesWithDyes(
		RecipeOutput recipeOutput, @NotNull List<Item> dyes, List<String> fireflyColors
	) {
		for(int i = 0; i < dyes.size(); ++i) {
			Item dye = dyes.get(i);
			String outputColor = fireflyColors.get(i);
			CompoundTag variantTag = new CompoundTag();
			variantTag.putString("FireflyBottleVariantTag", outputColor);

			List<Ingredient> possibleIngredients = new ArrayList<>();

			for (String fireflyColor : fireflyColors) {
				if (fireflyColor.equals(outputColor)) continue;

				CompoundTag ingredientColorTag = new CompoundTag();
				ingredientColorTag.putString("FireflyBottleVariantTag", fireflyColor);

				possibleIngredients.add(DefaultCustomIngredients.components(
					Ingredient.of(WWItems.FIREFLY_BOTTLE),
					DataComponentPatch.builder()
						.set(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.of(ingredientColorTag))
						.build()
				));
			}

			Ingredient input = DefaultCustomIngredients.any(possibleIngredients.toArray(new Ingredient[0]));
			ResourceLocation colorLocation = ResourceLocation.parse(outputColor);

			((ShapelessRecipeBuilderExtension) ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
				.requires(dye)
				.requires(input)
				.group("firefly_bottle")
				.unlockedBy("has_needed_dye", RecipeProvider.has(dye))
			).frozenLib$patch(
				DataComponentPatch.builder()
					.set(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.of(variantTag))
					.build()
			).save(
				recipeOutput,
				WWConstants.id("dye_" + colorLocation.getPath() + "_firefly_bottle")
			);
		}
	}

	private static void fireflyBottle(@NotNull String fireflyColor, Item dye, RecipeOutput exporter) {
		CompoundTag defaultColorTag = new CompoundTag();
		defaultColorTag.putString("FireflyBottleVariantTag", WWConstants.string("on"));

		CompoundTag variantTag = new CompoundTag();
		variantTag.putString("FireflyBottleVariantTag", fireflyColor);
		ResourceLocation colorLocation = ResourceLocation.parse(fireflyColor);

		((ShapelessRecipeBuilderExtension) ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.requires(dye)
			.requires(DefaultCustomIngredients.components(
				Ingredient.of(WWItems.FIREFLY_BOTTLE),
				DataComponentPatch.builder()
					.set(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.of(defaultColorTag))
					.build()
			))
			.unlockedBy("has_firefly_bottle", RecipeProvider.has(WWItems.FIREFLY_BOTTLE))
		).frozenLib$patch(
			DataComponentPatch.builder()
				.set(WWDataComponents.BOTTLE_ENTITY_DATA, CustomData.of(variantTag))
				.build()
			).save(exporter, WWConstants.id(colorLocation.getPath() + "_firefly_bottle"));
	}

}
