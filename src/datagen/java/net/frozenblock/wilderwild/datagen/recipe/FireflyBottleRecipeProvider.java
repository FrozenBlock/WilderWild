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
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class FireflyBottleRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {

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
				RegisterItems.BLACK_FIREFLY_BOTTLE,
				RegisterItems.BLUE_FIREFLY_BOTTLE,
				RegisterItems.BROWN_FIREFLY_BOTTLE,
				RegisterItems.CYAN_FIREFLY_BOTTLE,
				RegisterItems.GRAY_FIREFLY_BOTTLE,
				RegisterItems.GREEN_FIREFLY_BOTTLE,
				RegisterItems.LIGHT_BLUE_FIREFLY_BOTTLE,
				RegisterItems.LIGHT_GRAY_FIREFLY_BOTTLE,
				RegisterItems.LIME_FIREFLY_BOTTLE,
				RegisterItems.MAGENTA_FIREFLY_BOTTLE,
				RegisterItems.ORANGE_FIREFLY_BOTTLE,
				RegisterItems.PINK_FIREFLY_BOTTLE,
				RegisterItems.PURPLE_FIREFLY_BOTTLE,
				RegisterItems.RED_FIREFLY_BOTTLE,
				RegisterItems.WHITE_FIREFLY_BOTTLE,
				RegisterItems.YELLOW_FIREFLY_BOTTLE
			),
			"firefly_bottle",
			RecipeCategory.MISC,
			WilderSharedConstants.MOD_ID
		);

		fireflyBottle(RegisterItems.BLACK_FIREFLY_BOTTLE, Items.BLACK_DYE, exporter);
		fireflyBottle(RegisterItems.BLUE_FIREFLY_BOTTLE, Items.BLUE_DYE, exporter);
		fireflyBottle(RegisterItems.BROWN_FIREFLY_BOTTLE, Items.BROWN_DYE, exporter);
		fireflyBottle(RegisterItems.CYAN_FIREFLY_BOTTLE, Items.CYAN_DYE, exporter);
		fireflyBottle(RegisterItems.GRAY_FIREFLY_BOTTLE, Items.GRAY_DYE, exporter);
		fireflyBottle(RegisterItems.GREEN_FIREFLY_BOTTLE, Items.GREEN_DYE, exporter);
		fireflyBottle(RegisterItems.LIGHT_BLUE_FIREFLY_BOTTLE, Items.LIGHT_BLUE_DYE, exporter);
		fireflyBottle(RegisterItems.LIGHT_GRAY_FIREFLY_BOTTLE, Items.LIGHT_GRAY_DYE, exporter);
		fireflyBottle(RegisterItems.LIME_FIREFLY_BOTTLE, Items.LIME_DYE, exporter);
		fireflyBottle(RegisterItems.MAGENTA_FIREFLY_BOTTLE, Items.MAGENTA_DYE, exporter);
		fireflyBottle(RegisterItems.ORANGE_FIREFLY_BOTTLE, Items.ORANGE_DYE, exporter);
		fireflyBottle(RegisterItems.PINK_FIREFLY_BOTTLE, Items.PINK_DYE, exporter);
		fireflyBottle(RegisterItems.PURPLE_FIREFLY_BOTTLE, Items.PURPLE_DYE, exporter);
		fireflyBottle(RegisterItems.RED_FIREFLY_BOTTLE, Items.RED_DYE, exporter);
		fireflyBottle(RegisterItems.WHITE_FIREFLY_BOTTLE, Items.WHITE_DYE, exporter);
		fireflyBottle(RegisterItems.YELLOW_FIREFLY_BOTTLE, Items.YELLOW_DYE, exporter);
	}

	public static void fireflyBottle(ItemLike coloredBottle, ItemLike dye, RecipeOutput recipeOutput) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, coloredBottle, 1)
			.requires(dye)
			.requires(RegisterItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.unlockedBy("has_" + RecipeProvider.getItemName(dye), RecipeProvider.has(dye))
			.save(recipeOutput);
	}

}
