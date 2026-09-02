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

package net.frozenblock.wilderwild.data.recipe;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
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
		cookRecipes("smoking", SmokingRecipe::new, 200, provider, output);
		cookRecipes("campfire_cooking", CampfireCookingRecipe::new, 600, provider, output);

		leafLitterSmelting(Items.ACACIA_LEAVES, WWItems.ACACIA_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.AZALEA_LEAVES, WWItems.AZALEA_LEAF_LITTER, provider, output);
		leafLitterSmelting(WWItems.BAOBAB_LEAVES, WWItems.BAOBAB_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.BIRCH_LEAVES, WWItems.BIRCH_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.CHERRY_LEAVES, WWItems.CHERRY_LEAF_LITTER, provider, output);
		leafLitterSmelting(WWItems.CYPRESS_LEAVES, WWItems.CYPRESS_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.DARK_OAK_LEAVES, WWItems.DARK_OAK_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.JUNGLE_LEAVES, WWItems.JUNGLE_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.MANGROVE_LEAVES, WWItems.MANGROVE_LEAF_LITTER, provider, output);
		leafLitterSmelting(Items.PALE_OAK_LEAVES, WWItems.PALE_OAK_LEAF_LITTER, provider, output);
		leafLitterSmelting(WWItems.PALM_FRONDS, WWItems.PALM_FROND_LITTER, provider, output);
		leafLitterSmelting(Items.SPRUCE_LEAVES, WWItems.SPRUCE_LEAF_LITTER, provider, output);
		leafLitterSmelting(WWItems.WILLOW_LEAVES, WWItems.WILLOW_LEAF_LITTER, provider, output);

		MapleCollection.zipApply(WWItems.MAPLE_LEAF_LITTER, WWItems.MAPLE_LEAVES,
			(leafLitter, leaves) -> leafLitterSmelting(leaves, leafLitter, provider, output)
		);
	}

	private static <T extends AbstractCookingRecipe> void cookRecipes(
		String source, AbstractCookingRecipe.Factory<T> factory, int cookingTime, RecipeProvider provider, RecipeOutput output
	) {
		simpleCookingRecipe(provider, output, source, factory, cookingTime, WWItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW, 0.35F);
	}

	private static <T extends AbstractCookingRecipe> void simpleCookingRecipe(
		RecipeProvider provider,
		RecipeOutput output,
		String source,
		AbstractCookingRecipe.Factory<T> factory,
		int cookingTime,
		ItemLike base,
		ItemLike result,
		float experience
	) {
		SimpleCookingRecipeBuilder.generic(Ingredient.of(base), RecipeCategory.FOOD, CookingBookCategory.FOOD, result, experience, cookingTime, factory)
			.unlockedBy(RecipeProvider.getHasName(base), provider.has(base))
			.save(output, WWConstants.string(RecipeProvider.getItemName(result) + "_from_" + source));
	}

	private static void leafLitterSmelting(ItemLike leafBlock, ItemLike leafLitter, RecipeProvider provider, RecipeOutput output) {
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(leafBlock), RecipeCategory.MISC, CookingBookCategory.BLOCKS, leafLitter, 0.1F, 200)
			.unlockedBy(RecipeProvider.getHasName(leafBlock), provider.has(leafBlock))
			.save(output);
	}
}
