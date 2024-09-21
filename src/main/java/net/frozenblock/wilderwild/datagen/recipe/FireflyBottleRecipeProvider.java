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
import net.frozenblock.lib.recipe.api.FrozenRecipeProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import java.util.function.Consumer;

public final class FireflyBottleRecipeProvider {

	static void buildRecipes(Consumer<FinishedRecipe> exporter) {
		FrozenRecipeProvider.colorWithDyes(
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
				WWItems.BLACK_FIREFLY_BOTTLE,
				WWItems.BLUE_FIREFLY_BOTTLE,
				WWItems.BROWN_FIREFLY_BOTTLE,
				WWItems.CYAN_FIREFLY_BOTTLE,
				WWItems.GRAY_FIREFLY_BOTTLE,
				WWItems.GREEN_FIREFLY_BOTTLE,
				WWItems.LIGHT_BLUE_FIREFLY_BOTTLE,
				WWItems.LIGHT_GRAY_FIREFLY_BOTTLE,
				WWItems.LIME_FIREFLY_BOTTLE,
				WWItems.MAGENTA_FIREFLY_BOTTLE,
				WWItems.ORANGE_FIREFLY_BOTTLE,
				WWItems.PINK_FIREFLY_BOTTLE,
				WWItems.PURPLE_FIREFLY_BOTTLE,
				WWItems.RED_FIREFLY_BOTTLE,
				WWItems.WHITE_FIREFLY_BOTTLE,
				WWItems.YELLOW_FIREFLY_BOTTLE
			),
			"firefly_bottle",
			RecipeCategory.MISC,
			WWConstants.MOD_ID
		);

		fireflyBottle(WWItems.BLACK_FIREFLY_BOTTLE, Items.BLACK_DYE, exporter);
		fireflyBottle(WWItems.BLUE_FIREFLY_BOTTLE, Items.BLUE_DYE, exporter);
		fireflyBottle(WWItems.BROWN_FIREFLY_BOTTLE, Items.BROWN_DYE, exporter);
		fireflyBottle(WWItems.CYAN_FIREFLY_BOTTLE, Items.CYAN_DYE, exporter);
		fireflyBottle(WWItems.GRAY_FIREFLY_BOTTLE, Items.GRAY_DYE, exporter);
		fireflyBottle(WWItems.GREEN_FIREFLY_BOTTLE, Items.GREEN_DYE, exporter);
		fireflyBottle(WWItems.LIGHT_BLUE_FIREFLY_BOTTLE, Items.LIGHT_BLUE_DYE, exporter);
		fireflyBottle(WWItems.LIGHT_GRAY_FIREFLY_BOTTLE, Items.LIGHT_GRAY_DYE, exporter);
		fireflyBottle(WWItems.LIME_FIREFLY_BOTTLE, Items.LIME_DYE, exporter);
		fireflyBottle(WWItems.MAGENTA_FIREFLY_BOTTLE, Items.MAGENTA_DYE, exporter);
		fireflyBottle(WWItems.ORANGE_FIREFLY_BOTTLE, Items.ORANGE_DYE, exporter);
		fireflyBottle(WWItems.PINK_FIREFLY_BOTTLE, Items.PINK_DYE, exporter);
		fireflyBottle(WWItems.PURPLE_FIREFLY_BOTTLE, Items.PURPLE_DYE, exporter);
		fireflyBottle(WWItems.RED_FIREFLY_BOTTLE, Items.RED_DYE, exporter);
		fireflyBottle(WWItems.WHITE_FIREFLY_BOTTLE, Items.WHITE_DYE, exporter);
		fireflyBottle(WWItems.YELLOW_FIREFLY_BOTTLE, Items.YELLOW_DYE, exporter);
	}

	public static void fireflyBottle(ItemLike coloredBottle, ItemLike dye, Consumer<FinishedRecipe> recipeOutput) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, coloredBottle, 1)
			.requires(dye)
			.requires(WWItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.unlockedBy("has_" + RecipeProvider.getItemName(dye), RecipeProvider.has(dye))
			.save(recipeOutput);
	}

}
