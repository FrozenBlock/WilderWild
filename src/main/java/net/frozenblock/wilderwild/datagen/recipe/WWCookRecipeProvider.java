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

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ItemLike;

public final class WWCookRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.CRAB_CLAW), RecipeCategory.FOOD, CookingBookCategory.FOOD, WWItems.COOKED_CRAB_CLAW, 0.35F, 200)
			.unlockedBy("has_crab_claw", provider.has(WWItems.CRAB_CLAW))
			.save(output);
		cookRecipes(provider, output, "smoking", SmokingRecipe::new, 100);
		cookRecipes(provider, output, "campfire_cooking", CampfireCookingRecipe::new, 600);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ACACIA_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.ACACIA_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_acacia_leaves", provider.has(Items.ACACIA_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.AZALEA_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.AZALEA_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_azalea_leaves", provider.has(Items.AZALEA_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.BAOBAB_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.BAOBAB_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_baobab_leaves", provider.has(WWItems.BAOBAB_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BIRCH_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.BIRCH_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_birch_leaves", provider.has(Items.BIRCH_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CHERRY_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.CHERRY_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_cherry_leaves", provider.has(Items.CHERRY_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.CYPRESS_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.CYPRESS_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_cypress_leaves", provider.has(WWItems.CYPRESS_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.DARK_OAK_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.DARK_OAK_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_dark_oak_leaves", provider.has(Items.DARK_OAK_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.JUNGLE_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.JUNGLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_jungle_leaves", provider.has(Items.JUNGLE_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MANGROVE_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.MANGROVE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_mangrove_leaves", provider.has(Items.MANGROVE_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.PALE_OAK_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.PALE_OAK_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_pale_oak_leaves", provider.has(Items.PALE_OAK_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.PALM_FRONDS), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.PALM_FROND_LITTER, 0.1F, 200)
			.unlockedBy("has_palm_fronds", provider.has(WWItems.PALM_FRONDS))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SPRUCE_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.SPRUCE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_spruce_leaves", provider.has(Items.SPRUCE_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.WILLOW_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.WILLOW_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_willow_leaves", provider.has(WWItems.WILLOW_LEAVES))
			.save(output);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.YELLOW_MAPLE_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.YELLOW_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_yellow_maple_leaves", provider.has(WWItems.YELLOW_MAPLE_LEAVES))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.ORANGE_MAPLE_LEAF_LITTER), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.ORANGE_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_orange_maple_leaves", provider.has(WWItems.ORANGE_MAPLE_LEAF_LITTER))
			.save(output);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWItems.RED_MAPLE_LEAVES), RecipeCategory.MISC, CookingBookCategory.BLOCKS, WWItems.RED_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_red_maple_leaves", provider.has(WWItems.RED_MAPLE_LEAVES))
			.save(output);
	}

	private static <T extends AbstractCookingRecipe> void cookRecipes(
		RecipeProvider provider, RecipeOutput exporter, String source, AbstractCookingRecipe.Factory<T> factory, int cookingTime
	) {
		simpleCookingRecipe(provider, exporter, source, factory, cookingTime, WWItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW, 0.35F);
	}

	private static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
		RecipeProvider provider,
		RecipeOutput exporter,
		String source,
		AbstractCookingRecipe.Factory<T> factory,
		int cookingTime,
		ItemLike base,
		ItemLike result,
		float experience
	) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(base), RecipeCategory.FOOD, CookingBookCategory.FOOD, result, experience, cookingTime, factory)
			.unlockedBy(RecipeProvider.getHasName(base), provider.has(base))
			.save(exporter, WWConstants.string(RecipeProvider.getItemName(result) + "_from_" + source));
	}
}
