/*
 * Copyright 2025 FrozenBlock
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
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public final class WWNaturalRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		provider.oneToOneConversionRecipe(Items.ORANGE_DYE, WWBlocks.LANTANAS, "orange_dye");

		provider.oneToOneConversionRecipe(Items.PURPLE_DYE, WWBlocks.PHLOX, "purple_dye");

		provider.oneToOneConversionRecipe(Items.RED_DYE, WWBlocks.RED_HIBISCUS, "red_dye");
		provider.oneToOneConversionRecipe(Items.YELLOW_DYE, WWBlocks.YELLOW_HIBISCUS, "yellow_dye");
		provider.oneToOneConversionRecipe(Items.WHITE_DYE, WWBlocks.WHITE_HIBISCUS, "white_dye");
		provider.oneToOneConversionRecipe(Items.PINK_DYE, WWBlocks.PINK_HIBISCUS, "pink_dye");
		provider.oneToOneConversionRecipe(Items.PURPLE_DYE, WWBlocks.PURPLE_HIBISCUS, "purple_dye");

		provider.oneToOneConversionRecipe(Items.LIGHT_GRAY_DYE, WWBlocks.DATURA, "light_gray_dye", 2);

		provider.oneToOneConversionRecipe(Items.ORANGE_DYE, WWBlocks.MILKWEED, "orange_dye", 2);

		provider.oneToOneConversionRecipe(Items.MAGENTA_DYE, WWBlocks.CARNATION, "magenta_dye");

		provider.oneToOneConversionRecipe(Items.ORANGE_DYE, WWBlocks.MARIGOLD, "orange_dye");

		provider.oneToOneConversionRecipe(Items.PURPLE_DYE, WWBlocks.PASQUEFLOWER, "purple_dye");

		provider.oneToOneConversionRecipe(Items.WHITE_DYE, WWItems.SPLIT_COCONUT, "white_dye");
		provider.shapeless(RecipeCategory.MISC, Items.BOWL, 2)
			.requires(WWItems.SPLIT_COCONUT, 2)
			.group("bowl")
			.unlockedBy(RecipeProvider.getHasName(WWItems.SPLIT_COCONUT), provider.has(WWItems.SPLIT_COCONUT))
			.save(exporter, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.BOWL, WWItems.SPLIT_COCONUT)));

		provider.shapeless(RecipeCategory.FOOD, WWItems.PEELED_PRICKLY_PEAR, 1)
			.requires(WWItems.PRICKLY_PEAR)
			.unlockedBy(RecipeProvider.getHasName(WWItems.PRICKLY_PEAR), provider.has(WWItems.PRICKLY_PEAR))
			.save(exporter);

		provider.stonecutterResultFromBase(RecipeCategory.MISC, WWItems.SPLIT_COCONUT, WWItems.COCONUT, 2);

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

		provider.carpet(WWBlocks.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_MOSS_BLOCK);

		provider.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, Blocks.ICE, WWBlocks.ICICLE);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.ACACIA_LEAVES), RecipeCategory.MISC, WWBlocks.ACACIA_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_acacia_leaves", provider.has(Items.ACACIA_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.AZALEA_LEAVES), RecipeCategory.MISC, WWBlocks.AZALEA_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_azalea_leaves", provider.has(Items.AZALEA_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.BAOBAB_LEAVES), RecipeCategory.MISC, WWBlocks.BAOBAB_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_baobab_leaves", provider.has(WWBlocks.BAOBAB_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.BIRCH_LEAVES), RecipeCategory.MISC, WWBlocks.BIRCH_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_birch_leaves", provider.has(Items.BIRCH_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.CHERRY_LEAVES), RecipeCategory.MISC, WWBlocks.CHERRY_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_cherry_leaves", provider.has(Items.CHERRY_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.CYPRESS_LEAVES), RecipeCategory.MISC, WWBlocks.CYPRESS_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_cypress_leaves", provider.has(WWBlocks.CYPRESS_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.DARK_OAK_LEAVES), RecipeCategory.MISC, WWBlocks.DARK_OAK_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_dark_oak_leaves", provider.has(Items.DARK_OAK_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.JUNGLE_LEAVES), RecipeCategory.MISC, WWBlocks.JUNGLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_jungle_leaves", provider.has(Items.JUNGLE_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.MANGROVE_LEAVES), RecipeCategory.MISC, WWBlocks.MANGROVE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_mangrove_leaves", provider.has(Items.MANGROVE_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.PALE_OAK_LEAVES), RecipeCategory.MISC, WWBlocks.PALE_OAK_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_pale_oak_leaves", provider.has(Items.PALE_OAK_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.PALM_FRONDS), RecipeCategory.MISC, WWBlocks.PALM_FROND_LITTER, 0.1F, 200)
			.unlockedBy("has_palm_fronds", provider.has(WWBlocks.PALM_FRONDS))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Items.SPRUCE_LEAVES), RecipeCategory.MISC, WWBlocks.SPRUCE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_spruce_leaves", provider.has(Items.SPRUCE_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.WILLOW_LEAVES), RecipeCategory.MISC, WWBlocks.WILLOW_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_willow_leaves", provider.has(WWBlocks.WILLOW_LEAVES))
			.save(exporter);

		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.YELLOW_MAPLE_LEAVES), RecipeCategory.MISC, WWBlocks.YELLOW_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_yellow_maple_leaves", provider.has(WWBlocks.YELLOW_MAPLE_LEAVES))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.ORANGE_MAPLE_LEAF_LITTER), RecipeCategory.MISC, WWBlocks.ORANGE_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_orange_maple_leaves", provider.has(WWBlocks.ORANGE_MAPLE_LEAF_LITTER))
			.save(exporter);
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(WWBlocks.RED_MAPLE_LEAVES), RecipeCategory.MISC, WWBlocks.RED_MAPLE_LEAF_LITTER, 0.1F, 200)
			.unlockedBy("has_red_maple_leaves", provider.has(WWBlocks.RED_MAPLE_LEAVES))
			.save(exporter);
	}

}
