/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.datagen.recipe;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWItemTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class HollowedLogRecipeProvider {

	static void buildRecipes(RecipeOutput exporter) {
		planksFromHollowed(WWItemTags.HOLLOWED_ACACIA_LOGS, Items.ACACIA_PLANKS, exporter, "acacia");
		woodFromHollowed(WWBlocks.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, exporter, "acacia");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, exporter, "acacia");

		planksFromHollowed(WWItemTags.HOLLOWED_BAOBAB_LOGS, WWBlocks.BAOBAB_PLANKS, exporter, "baobab");
		woodFromHollowed(WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.BAOBAB_WOOD, exporter, "baobab");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD, exporter, "baobab");

		planksFromHollowed(WWItemTags.HOLLOWED_BIRCH_LOGS, Items.BIRCH_PLANKS, exporter, "birch");
		woodFromHollowed(WWBlocks.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, exporter, "birch");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, exporter, "birch");

		planksFromHollowed(WWItemTags.HOLLOWED_CHERRY_LOGS, Items.CHERRY_PLANKS, exporter, "cherry");
		woodFromHollowed(WWBlocks.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, exporter, "cherry");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, exporter, "cherry");

		planksFromHollowed(WWItemTags.HOLLOWED_CYPRESS_LOGS, WWBlocks.CYPRESS_PLANKS, exporter, "cypress");
		woodFromHollowed(WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.CYPRESS_WOOD, exporter, "cypress");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_WOOD, exporter, "cypress");

		planksFromHollowed(WWItemTags.HOLLOWED_DARK_OAK_LOGS, Items.DARK_OAK_PLANKS, exporter, "dark_oak");
		woodFromHollowed(WWBlocks.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, exporter, "dark_oak");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, exporter, "dark_oak");

		planksFromHollowed(WWItemTags.HOLLOWED_JUNGLE_LOGS, Items.JUNGLE_PLANKS, exporter, "jungle");
		woodFromHollowed(WWBlocks.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, exporter, "jungle");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, exporter, "jungle");

		planksFromHollowed(WWItemTags.HOLLOWED_MANGROVE_LOGS, Items.MANGROVE_PLANKS, exporter, "mangrove");
		woodFromHollowed(WWBlocks.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, exporter, "mangrove");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, exporter, "mangrove");

		planksFromHollowed(WWItemTags.HOLLOWED_MAPLE_LOGS, WWBlocks.MAPLE_PLANKS, exporter, "maple");
		woodFromHollowed(WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.MAPLE_WOOD, exporter, "maple");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_WOOD, exporter, "maple");

		planksFromHollowed(WWItemTags.HOLLOWED_OAK_LOGS, Items.OAK_PLANKS, exporter, "oak");
		woodFromHollowed(WWBlocks.HOLLOWED_OAK_LOG, Items.OAK_WOOD, exporter, "oak");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, exporter, "oak");

		planksFromHollowed(WWItemTags.HOLLOWED_PALM_LOGS, WWBlocks.PALM_PLANKS, exporter, "palm");
		woodFromHollowed(WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.PALM_WOOD, exporter, "palm");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_WOOD, exporter, "palm");

		planksFromHollowed(WWItemTags.HOLLOWED_SPRUCE_LOGS, Items.SPRUCE_PLANKS, exporter, "spruce");
		woodFromHollowed(WWBlocks.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, exporter, "spruce");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, exporter, "spruce");

		planksFromHollowedStem(WWItemTags.HOLLOWED_CRIMSON_STEMS, Items.CRIMSON_PLANKS, exporter, "crimson");
		hyphaeFromHollowed(WWBlocks.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, exporter, "crimson");
		strippedHyphaeFromHollowed(WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, exporter, "crimson");

		planksFromHollowedStem(WWItemTags.HOLLOWED_WARPED_STEMS, Items.WARPED_PLANKS, exporter, "warped");
		hyphaeFromHollowed(WWBlocks.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, exporter, "warped");
		strippedHyphaeFromHollowed(WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, exporter, "warped");

		planksFromHollowed(WWItemTags.HOLLOWED_WILLOW_LOGS, WWBlocks.WILLOW_PLANKS, exporter, "willow");
		woodFromHollowed(WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.WILLOW_WOOD, exporter, "willow");
		strippedWoodFromHollowed(WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_WOOD, exporter, "willow");
	}

	public static void planksFromHollowed(TagKey<Item> input, ItemLike output, RecipeOutput exporter, String name) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_log", RecipeProvider.has(input))
			.save(exporter, WWConstants.id(name + "_planks_from_hollowed"));
	}

	public static void woodFromHollowed(ItemLike hollowedLog, ItemLike output, RecipeOutput exporter, String name) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedLog))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_log", RecipeProvider.has(hollowedLog))
		.save(exporter, WWConstants.id(name + "_wood_from_hollowed"));
	}

	public static void strippedWoodFromHollowed(ItemLike hollowedLog, ItemLike output, RecipeOutput exporter, String name) {
		woodFromHollowed(hollowedLog, output, exporter, "stripped_" + name);
	}

	public static void planksFromHollowedStem(TagKey<Item> input, ItemLike output, RecipeOutput exporter, String name) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_stem", RecipeProvider.has(input))
			.save(exporter, WWConstants.id(name + "_planks_from_hollowed"));
	}

	public static void hyphaeFromHollowed(ItemLike hollowedStem, ItemLike output, RecipeOutput exporter, String name) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedStem))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_stem", RecipeProvider.has(hollowedStem))
			.save(exporter, WWConstants.id(name + "_hyphae_from_hollowed"));
	}

	public static void strippedHyphaeFromHollowed(ItemLike hollowedStem, ItemLike output, RecipeOutput exporter, String name) {
		woodFromHollowed(hollowedStem, output, exporter, "stripped_" + name);
	}
}
