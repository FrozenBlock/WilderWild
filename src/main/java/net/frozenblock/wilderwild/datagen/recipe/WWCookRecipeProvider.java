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
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;

public final class WWCookRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.CRAB_CLAW), RecipeCategory.FOOD, WWItems.COOKED_CRAB_CLAW, 0.35F, 200)
			.unlockedBy("has_crab_claw", provider.has(WWItems.CRAB_CLAW))
			.save(exporter);
		cookRecipes(provider, exporter, "smoking", RecipeSerializer.SMOKING_RECIPE, SmokingRecipe::new, 100);
		cookRecipes(provider, exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING_RECIPE, CampfireCookingRecipe::new, 600);
	}

	private static <T extends AbstractCookingRecipe> void cookRecipes(
		RecipeProvider provider, RecipeOutput exporter, String cooker, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> recipe, int cookingTime
	) {
		simpleCookingRecipe(provider, exporter, cooker, serializer, recipe, cookingTime, WWItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW, 0.35F);
	}

	private static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
		RecipeProvider provider,
		RecipeOutput exporter,
		String cooker,
		RecipeSerializer<T> serializer,
		AbstractCookingRecipe.Factory<T> recipe,
		int cookingTime,
		ItemLike input,
		ItemLike output,
		float experience
	) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(input), RecipeCategory.FOOD, output, experience, cookingTime, serializer, recipe)
			.unlockedBy(RecipeProvider.getHasName(input), provider.has(input))
			.save(exporter, WWConstants.string(RecipeProvider.getItemName(output) + "_from_" + cooker));
	}
}
