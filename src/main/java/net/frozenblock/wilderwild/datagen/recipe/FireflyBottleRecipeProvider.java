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
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.registry.WWDataComponents;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
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
				FireflyColor.BLACK,
				FireflyColor.BLUE,
				FireflyColor.BROWN,
				FireflyColor.CYAN,
				FireflyColor.GRAY,
				FireflyColor.GREEN,
				FireflyColor.LIGHT_BLUE,
				FireflyColor.LIGHT_GRAY,
				FireflyColor.LIME,
				FireflyColor.MAGENTA,
				FireflyColor.ORANGE,
				FireflyColor.PINK,
				FireflyColor.PURPLE,
				FireflyColor.RED,
				FireflyColor.WHITE,
				FireflyColor.YELLOW
			)
		);

		fireflyBottle(FireflyColor.BLACK, Items.BLACK_DYE, exporter);
		fireflyBottle(FireflyColor.BLUE, Items.BLUE_DYE, exporter);
		fireflyBottle(FireflyColor.BROWN, Items.BROWN_DYE, exporter);
		fireflyBottle(FireflyColor.CYAN, Items.CYAN_DYE, exporter);
		fireflyBottle(FireflyColor.GRAY, Items.GRAY_DYE, exporter);
		fireflyBottle(FireflyColor.GREEN, Items.GREEN_DYE, exporter);
		fireflyBottle(FireflyColor.LIGHT_BLUE, Items.LIGHT_BLUE_DYE, exporter);
		fireflyBottle(FireflyColor.LIGHT_GRAY, Items.LIGHT_GRAY_DYE, exporter);
		fireflyBottle(FireflyColor.LIME, Items.LIME_DYE, exporter);
		fireflyBottle(FireflyColor.MAGENTA, Items.MAGENTA_DYE, exporter);
		fireflyBottle(FireflyColor.ORANGE, Items.ORANGE_DYE, exporter);
		fireflyBottle(FireflyColor.PINK, Items.PINK_DYE, exporter);
		fireflyBottle(FireflyColor.PURPLE, Items.PURPLE_DYE, exporter);
		fireflyBottle(FireflyColor.RED, Items.RED_DYE, exporter);
		fireflyBottle(FireflyColor.WHITE, Items.WHITE_DYE, exporter);
		fireflyBottle(FireflyColor.YELLOW, Items.YELLOW_DYE, exporter);
	}

	private static void colorFireflyBottlesWithDyes(
		RecipeOutput recipeOutput, @NotNull List<Item> dyes, List<FireflyColor> fireflyColors
	) {
		for(int i = 0; i < dyes.size(); ++i) {
			Item dye = dyes.get(i);
			FireflyColor outputColor = fireflyColors.get(i);
			CompoundTag variantTag = new CompoundTag();
			variantTag.putString("FireflyBottleVariantTag", outputColor.getSerializedName());

			((ShapelessRecipeBuilderExtension) ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
				.requires(dye)
				.requires(
					Ingredient.of(
						fireflyColors.stream()
							.filter(ingredientColor -> !ingredientColor.equals(outputColor))
							.map(fireflyColor -> {
								ItemStack itemStack = new ItemStack(WWItems.FIREFLY_BOTTLE);

								CustomData.update(
									WWDataComponents.FIREFLY_COLOR,
									itemStack,
									tag -> tag.putString("FireflyBottleVariantTag", fireflyColor.getSerializedName())
								);
								return itemStack;
							})
					)
				)
				.group("firefly_bottle")
				.unlockedBy("has_needed_dye", RecipeProvider.has(dye))
			).frozenLib$patch(
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, CustomData.of(variantTag))
					.build()
			).save(
				recipeOutput,
				WWConstants.id("dye_" + outputColor.key().getPath() + "_firefly_bottle")
			);
		}
	}

	private static void fireflyBottle(@NotNull FireflyColor fireflyColor, Item dye, RecipeOutput exporter) {
		CompoundTag defaultColorTag = new CompoundTag();
		defaultColorTag.putString("FireflyBottleVariantTag", FireflyColor.ON.getSerializedName());

		CompoundTag variantTag = new CompoundTag();
		variantTag.putString("FireflyBottleVariantTag", fireflyColor.getSerializedName());

		((ShapelessRecipeBuilderExtension) ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, WWItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.requires(dye)
			.requires(DefaultCustomIngredients.components(
				Ingredient.of(WWItems.FIREFLY_BOTTLE),
				DataComponentPatch.builder()
					.set(WWDataComponents.FIREFLY_COLOR, CustomData.of(defaultColorTag))
					.build()
			))
			.unlockedBy("has_firefly_bottle", RecipeProvider.has(WWItems.FIREFLY_BOTTLE))
		).frozenLib$patch(
			DataComponentPatch.builder()
				.set(WWDataComponents.FIREFLY_COLOR, CustomData.of(variantTag))
				.build()
			).save(exporter, WWConstants.id(fireflyColor.key().getPath() + "_firefly_bottle"));
	}

}
