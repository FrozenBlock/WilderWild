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
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public final class FireflyBottleRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		FrozenRecipeProvider.colorWithDyes(
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
			WWItems.FIREFLY_BOTTLE,
			"firefly_bottle",
			RecipeCategory.MISC
		);

		fireflyBottle(provider, WWItems.BLACK_FIREFLY_BOTTLE, Items.BLACK_DYE, exporter);
		fireflyBottle(provider, WWItems.BLUE_FIREFLY_BOTTLE, Items.BLUE_DYE, exporter);
		fireflyBottle(provider, WWItems.BROWN_FIREFLY_BOTTLE, Items.BROWN_DYE, exporter);
		fireflyBottle(provider, WWItems.CYAN_FIREFLY_BOTTLE, Items.CYAN_DYE, exporter);
		fireflyBottle(provider, WWItems.GRAY_FIREFLY_BOTTLE, Items.GRAY_DYE, exporter);
		fireflyBottle(provider, WWItems.GREEN_FIREFLY_BOTTLE, Items.GREEN_DYE, exporter);
		fireflyBottle(provider, WWItems.LIGHT_BLUE_FIREFLY_BOTTLE, Items.LIGHT_BLUE_DYE, exporter);
		fireflyBottle(provider, WWItems.LIGHT_GRAY_FIREFLY_BOTTLE, Items.LIGHT_GRAY_DYE, exporter);
		fireflyBottle(provider, WWItems.LIME_FIREFLY_BOTTLE, Items.LIME_DYE, exporter);
		fireflyBottle(provider, WWItems.MAGENTA_FIREFLY_BOTTLE, Items.MAGENTA_DYE, exporter);
		fireflyBottle(provider, WWItems.ORANGE_FIREFLY_BOTTLE, Items.ORANGE_DYE, exporter);
		fireflyBottle(provider, WWItems.PINK_FIREFLY_BOTTLE, Items.PINK_DYE, exporter);
		fireflyBottle(provider, WWItems.PURPLE_FIREFLY_BOTTLE, Items.PURPLE_DYE, exporter);
		fireflyBottle(provider, WWItems.RED_FIREFLY_BOTTLE, Items.RED_DYE, exporter);
		fireflyBottle(provider, WWItems.WHITE_FIREFLY_BOTTLE, Items.WHITE_DYE, exporter);
		fireflyBottle(provider, WWItems.YELLOW_FIREFLY_BOTTLE, Items.YELLOW_DYE, exporter);
	}

	public static void fireflyBottle(RecipeProvider provider, ItemLike coloredBottle, ItemLike dye, RecipeOutput recipeOutput) {
		provider.shapeless(RecipeCategory.MISC, coloredBottle, 1)
			.requires(dye)
			.requires(WWItems.FIREFLY_BOTTLE)
			.group("firefly_bottle")
			.unlockedBy("has_" + RecipeProvider.getItemName(dye), provider.has(dye))
			.save(recipeOutput);
	}

}
