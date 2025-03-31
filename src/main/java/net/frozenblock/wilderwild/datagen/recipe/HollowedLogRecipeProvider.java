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
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class HollowedLogRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput exporter) {
		planksFromHollowed(provider, WWItemTags.HOLLOWED_ACACIA_LOGS, Items.ACACIA_PLANKS, exporter, "acacia");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, exporter, "acacia");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, exporter, "acacia");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_BAOBAB_LOGS, WWBlocks.BAOBAB_PLANKS, exporter, "baobab");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.BAOBAB_WOOD, exporter, "baobab");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD, exporter, "baobab");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_BIRCH_LOGS, Items.BIRCH_PLANKS, exporter, "birch");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, exporter, "birch");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, exporter, "birch");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_CHERRY_LOGS, Items.CHERRY_PLANKS, exporter, "cherry");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, exporter, "cherry");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, exporter, "cherry");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_CYPRESS_LOGS, WWBlocks.CYPRESS_PLANKS, exporter, "cypress");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.CYPRESS_WOOD, exporter, "cypress");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_WOOD, exporter, "cypress");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_DARK_OAK_LOGS, Items.DARK_OAK_PLANKS, exporter, "dark_oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, exporter, "dark_oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, exporter, "dark_oak");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_JUNGLE_LOGS, Items.JUNGLE_PLANKS, exporter, "jungle");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, exporter, "jungle");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, exporter, "jungle");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_MANGROVE_LOGS, Items.MANGROVE_PLANKS, exporter, "mangrove");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, exporter, "mangrove");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, exporter, "mangrove");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_MAPLE_LOGS, WWBlocks.MAPLE_PLANKS, exporter, "maple");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.MAPLE_WOOD, exporter, "maple");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_WOOD, exporter, "maple");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_OAK_LOGS, Items.OAK_PLANKS, exporter, "oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_OAK_LOG, Items.OAK_WOOD, exporter, "oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, exporter, "oak");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_PALM_LOGS, WWBlocks.PALM_PLANKS, exporter, "palm");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.PALM_WOOD, exporter, "palm");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_WOOD, exporter, "palm");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_SPRUCE_LOGS, Items.SPRUCE_PLANKS, exporter, "spruce");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, exporter, "spruce");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, exporter, "spruce");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_PALE_OAK_LOGS, Items.PALE_OAK_PLANKS, exporter, "pale_oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_PALE_OAK_LOG, Items.PALE_OAK_WOOD, exporter, "pale_oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG, Items.STRIPPED_PALE_OAK_WOOD, exporter, "pale_oak");

		planksFromHollowedStem(provider, WWItemTags.HOLLOWED_CRIMSON_STEMS, Items.CRIMSON_PLANKS, exporter, "crimson");
		hyphaeFromHollowed(provider, WWBlocks.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, exporter, "crimson");
		strippedHyphaeFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, exporter, "crimson");

		planksFromHollowedStem(provider, WWItemTags.HOLLOWED_WARPED_STEMS, Items.WARPED_PLANKS, exporter, "warped");
		hyphaeFromHollowed(provider, WWBlocks.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, exporter, "warped");
		strippedHyphaeFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, exporter, "warped");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_WILLOW_LOGS, WWBlocks.WILLOW_PLANKS, exporter, "willow");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.WILLOW_WOOD, exporter, "willow");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_WOOD, exporter, "willow");

	}

	public static void planksFromHollowed(RecipeProvider provider, TagKey<Item> input, ItemLike output, RecipeOutput exporter, String name) {
		provider.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_log", provider.has(input))
			.save(exporter, WWConstants.string(name + "_planks_from_hollowed"));
	}

	public static void woodFromHollowed(RecipeProvider provider, ItemLike hollowedLog, ItemLike output, RecipeOutput exporter, String name) {
		provider.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedLog))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_log", provider.has(hollowedLog))
		.save(exporter, WWConstants.string(name + "_wood_from_hollowed"));
	}

	public static void strippedWoodFromHollowed(RecipeProvider provider, ItemLike hollowedLog, ItemLike output, RecipeOutput exporter, String name) {
		woodFromHollowed(provider, hollowedLog, output, exporter, "stripped_" + name);
	}

	public static void planksFromHollowedStem(RecipeProvider provider, TagKey<Item> input, ItemLike output, RecipeOutput exporter, String name) {
		provider.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_stem", provider.has(input))
			.save(exporter, WWConstants.string(name + "_planks_from_hollowed"));
	}

	public static void hyphaeFromHollowed(RecipeProvider provider, ItemLike hollowedStem, ItemLike output, RecipeOutput exporter, String name) {
		provider.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedStem))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_stem", provider.has(hollowedStem))
			.save(exporter, WWConstants.string(name + "_hyphae_from_hollowed"));
	}

	public static void strippedHyphaeFromHollowed(RecipeProvider provider, ItemLike hollowedStem, ItemLike output, RecipeOutput exporter, String name) {
		woodFromHollowed(provider, hollowedStem, output, exporter, "stripped_" + name);
	}
}
