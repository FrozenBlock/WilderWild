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

import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.packs.VanillaRecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class WWNaturalRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		RecipeProvider.oneToOneConversionRecipe(exporter, Items.BLUE_DYE, RegisterBlocks.BLUE_GLORY_OF_THE_SNOW, "blue_dye");
		RecipeProvider.oneToOneConversionRecipe(exporter, Items.PINK_DYE, RegisterBlocks.PINK_GLORY_OF_THE_SNOW, "pink_dye");
		RecipeProvider.oneToOneConversionRecipe(exporter, Items.PURPLE_DYE, RegisterBlocks.PURPLE_GLORY_OF_THE_SNOW, "purple_dye");
		RecipeProvider.oneToOneConversionRecipe(exporter, Items.WHITE_DYE, RegisterBlocks.WHITE_GLORY_OF_THE_SNOW, "white_dye");

		RecipeProvider.oneToOneConversionRecipe(exporter, Items.LIGHT_GRAY_DYE, RegisterBlocks.DATURA, "light_gray_dye");

		RecipeProvider.oneToOneConversionRecipe(exporter, Items.ORANGE_DYE, RegisterBlocks.MILKWEED, "orange_dye", 2);

		RecipeProvider.oneToOneConversionRecipe(exporter, Items.PURPLE_DYE, RegisterBlocks.CARNATION, "orange_dye");

		RecipeProvider.oneToOneConversionRecipe(exporter, Items.WHITE_DYE, RegisterItems.SPLIT_COCONUT, "white_dye");
		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.BOWL, 2)
			.requires(RegisterItems.SPLIT_COCONUT, 2)
			.group("bowl")
			.unlockedBy(RecipeProvider.getHasName(RegisterItems.SPLIT_COCONUT), RecipeProvider.has(RegisterItems.SPLIT_COCONUT))
			.save(exporter, RecipeProvider.getConversionRecipeName(Items.BOWL, RegisterItems.SPLIT_COCONUT));

		ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, RegisterItems.PEELED_PRICKLY_PEAR, 1)
			.requires(RegisterItems.PRICKLY_PEAR)
			.unlockedBy(RecipeProvider.getHasName(RegisterItems.PRICKLY_PEAR), RecipeProvider.has(RegisterItems.PRICKLY_PEAR))
			.save(exporter);

		VanillaRecipeProvider.stonecutterResultFromBase(exporter, RecipeCategory.MISC, RegisterItems.COCONUT, RegisterItems.SPLIT_COCONUT, 2);

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', RegisterItems.MILKWEED_POD)
			.group("string")
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.unlockedBy(RecipeProvider.getHasName(RegisterItems.MILKWEED_POD), RecipeProvider.has(RegisterItems.MILKWEED_POD))
			.save(exporter, RecipeProvider.getConversionRecipeName(Items.STRING, RegisterItems.MILKWEED_POD));

		ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', Ingredient.of(RegisterBlocks.CATTAIL))
			.group("string")
			.pattern("##")
			.pattern("##")
			.unlockedBy(RecipeProvider.getHasName(RegisterBlocks.CATTAIL), RecipeProvider.has(RegisterBlocks.CATTAIL))
			.save(exporter, RecipeProvider.getConversionRecipeName(Items.STRING, RegisterBlocks.CATTAIL));
	}

}
