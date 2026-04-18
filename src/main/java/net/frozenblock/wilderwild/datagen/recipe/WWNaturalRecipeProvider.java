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
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;

public final class WWNaturalRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		provider.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.LANTANAS, "orange_dye");

		provider.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PHLOX, "purple_dye");

		provider.oneToOneConversionRecipe(Items.DYE.red(), WWItems.RED_HIBISCUS, "red_dye");
		provider.oneToOneConversionRecipe(Items.DYE.yellow(), WWItems.YELLOW_HIBISCUS, "yellow_dye");
		provider.oneToOneConversionRecipe(Items.DYE.white(), WWItems.WHITE_HIBISCUS, "white_dye");
		provider.oneToOneConversionRecipe(Items.DYE.pink(), WWItems.PINK_HIBISCUS, "pink_dye");
		provider.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PURPLE_HIBISCUS, "purple_dye");

		provider.oneToOneConversionRecipe(Items.DYE.lightGray(), WWItems.DATURA, "light_gray_dye", 2);

		provider.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.MILKWEED, "orange_dye", 2);

		provider.oneToOneConversionRecipe(Items.DYE.magenta(), WWItems.CARNATION, "magenta_dye");

		provider.oneToOneConversionRecipe(Items.DYE.orange(), WWItems.MARIGOLD, "orange_dye");

		provider.oneToOneConversionRecipe(Items.DYE.purple(), WWItems.PASQUEFLOWER, "purple_dye");

		provider.oneToOneConversionRecipe(Items.DYE.white(), WWItems.SPLIT_COCONUT, "white_dye");
		provider.shapeless(RecipeCategory.MISC, Items.BOWL, 2)
			.requires(WWItems.SPLIT_COCONUT, 2)
			.group("bowl")
			.unlockedBy(RecipeProvider.getHasName(WWItems.SPLIT_COCONUT), provider.has(WWItems.SPLIT_COCONUT))
			.save(output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.BOWL, WWItems.SPLIT_COCONUT)));

		provider.shapeless(RecipeCategory.FOOD, WWItems.PEELED_PRICKLY_PEAR, 1)
			.requires(WWItems.PRICKLY_PEAR)
			.unlockedBy(RecipeProvider.getHasName(WWItems.PRICKLY_PEAR), provider.has(WWItems.PRICKLY_PEAR))
			.save(output);

		provider.stonecutterResultFromBase(RecipeCategory.MISC, WWItems.SPLIT_COCONUT, WWItems.COCONUT, 2);

		provider.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', WWItems.MILKWEED_POD)
			.group("string")
			.pattern("###")
			.pattern("###")
			.pattern("###")
			.unlockedBy(RecipeProvider.getHasName(WWItems.MILKWEED_POD), provider.has(WWItems.MILKWEED_POD))
			.save(output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.STRING, WWItems.MILKWEED_POD)));

		provider.shaped(RecipeCategory.MISC, Items.STRING)
			.define('#', Ingredient.of(WWItems.CATTAIL))
			.group("string")
			.pattern("##")
			.pattern("##")
			.unlockedBy(RecipeProvider.getHasName(WWItems.CATTAIL), provider.has(WWItems.CATTAIL))
			.save(output, WWConstants.string(RecipeProvider.getConversionRecipeName(Items.STRING, WWItems.CATTAIL)));

		provider.carpet(WWItems.AUBURN_MOSS_CARPET, WWItems.AUBURN_MOSS_BLOCK);

		provider.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, Blocks.ICE, WWItems.ICICLE);
	}

}
