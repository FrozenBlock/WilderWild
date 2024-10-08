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

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

public final class WWNaturalRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		oneToOneConversionRecipe(provider, exporter, Items.BLUE_DYE, WWBlocks.BLUE_GIANT_GLORY_OF_THE_SNOW, "blue_dye");
		oneToOneConversionRecipe(provider, exporter, Items.PINK_DYE, WWBlocks.PINK_GIANT_GLORY_OF_THE_SNOW, "pink_dye");
		oneToOneConversionRecipe(provider, exporter, Items.PURPLE_DYE, WWBlocks.VIOLET_BEAUTY_GLORY_OF_THE_SNOW, "purple_dye");
		oneToOneConversionRecipe(provider, exporter, Items.WHITE_DYE, WWBlocks.ALBA_GLORY_OF_THE_SNOW, "white_dye");

		oneToOneConversionRecipe(provider, exporter, Items.LIGHT_GRAY_DYE, WWBlocks.DATURA, "light_gray_dye", 2);

		oneToOneConversionRecipe(provider, exporter, Items.ORANGE_DYE, WWBlocks.MILKWEED, "orange_dye", 2);

		oneToOneConversionRecipe(provider, exporter, Items.PURPLE_DYE, WWBlocks.CARNATION, "purple_Dye");

		oneToOneConversionRecipe(provider, exporter, Items.ORANGE_DYE, WWBlocks.MARIGOLD, "orange_dye");

		oneToOneConversionRecipe(provider, exporter, Items.WHITE_DYE, WWItems.SPLIT_COCONUT, "white_dye");
		provider.shapeless(RecipeCategory.MISC, Items.BOWL, 2)
			.requires(WWItems.SPLIT_COCONUT, 2)
			.group("bowl")
			.unlockedBy(RecipeProvider.getHasName(WWItems.SPLIT_COCONUT), provider.has(WWItems.SPLIT_COCONUT))
			.save(exporter, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.BOWL, WWItems.SPLIT_COCONUT)));

		provider.shapeless(RecipeCategory.MISC, WWItems.PEELED_PRICKLY_PEAR, 1)
			.requires(WWItems.PRICKLY_PEAR)
			.unlockedBy(RecipeProvider.getHasName(WWItems.PRICKLY_PEAR), provider.has(WWItems.PRICKLY_PEAR))
			.save(exporter);

		WWRecipeProvider.stonecutterResultFromBase(provider, exporter, RecipeCategory.MISC, WWItems.SPLIT_COCONUT, WWItems.COCONUT, 2);

		provider.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', WWItems.MILKWEED_POD)
			.group("string")
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.unlockedBy(RecipeProvider.getHasName(WWItems.MILKWEED_POD), provider.has(WWItems.MILKWEED_POD))
			.save(exporter, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.STRING, WWItems.MILKWEED_POD)));

		provider.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', Ingredient.of(WWBlocks.CATTAIL))
			.group("string")
			.pattern("##")
			.pattern("##")
			.unlockedBy(RecipeProvider.getHasName(WWBlocks.CATTAIL), provider.has(WWBlocks.CATTAIL))
			.save(exporter, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.STRING, WWBlocks.CATTAIL)));
	}

	private static void oneToOneConversionRecipe(RecipeProvider provider, RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, @Nullable String group) {
		oneToOneConversionRecipe(provider, recipeOutput, result, ingredient, group, 1);
	}

	private static void oneToOneConversionRecipe(RecipeProvider provider, RecipeOutput recipeOutput, ItemLike result, ItemLike ingredient, @Nullable String group, int resultCount) {
		provider.shapeless(RecipeCategory.MISC, result, resultCount)
			.requires(ingredient)
			.group(group)
			.unlockedBy(RecipeProvider.getHasName(ingredient), provider.has(ingredient))
			.save(recipeOutput, (RecipeProvider.getConversionRecipeName(result, ingredient)));
	}

}
