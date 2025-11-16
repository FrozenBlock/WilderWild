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

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		planksFromHollowed(provider, WWItemTags.HOLLOWED_ACACIA_LOGS, Items.ACACIA_PLANKS, output, "acacia");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, output, "acacia");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, output, "acacia");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_BAOBAB_LOGS, WWBlocks.BAOBAB_PLANKS, output, "baobab");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_BAOBAB_LOG, WWBlocks.BAOBAB_WOOD, output, "baobab");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD, output, "baobab");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_BIRCH_LOGS, Items.BIRCH_PLANKS, output, "birch");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, output, "birch");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, output, "birch");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_CHERRY_LOGS, Items.CHERRY_PLANKS, output, "cherry");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, output, "cherry");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, output, "cherry");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_CYPRESS_LOGS, WWBlocks.CYPRESS_PLANKS, output, "cypress");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_CYPRESS_LOG, WWBlocks.CYPRESS_WOOD, output, "cypress");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_WOOD, output, "cypress");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_DARK_OAK_LOGS, Items.DARK_OAK_PLANKS, output, "dark_oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, output, "dark_oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, output, "dark_oak");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_JUNGLE_LOGS, Items.JUNGLE_PLANKS, output, "jungle");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, output, "jungle");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, output, "jungle");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_MANGROVE_LOGS, Items.MANGROVE_PLANKS, output, "mangrove");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, output, "mangrove");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, output, "mangrove");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_MAPLE_LOGS, WWBlocks.MAPLE_PLANKS, output, "maple");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_MAPLE_LOG, WWBlocks.MAPLE_WOOD, output, "maple");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_WOOD, output, "maple");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_OAK_LOGS, Items.OAK_PLANKS, output, "oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_OAK_LOG, Items.OAK_WOOD, output, "oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, output, "oak");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_PALM_LOGS, WWBlocks.PALM_PLANKS, output, "palm");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_PALM_LOG, WWBlocks.PALM_WOOD, output, "palm");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG, WWBlocks.STRIPPED_PALM_WOOD, output, "palm");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_SPRUCE_LOGS, Items.SPRUCE_PLANKS, output, "spruce");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, output, "spruce");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, output, "spruce");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_PALE_OAK_LOGS, Items.PALE_OAK_PLANKS, output, "pale_oak");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_PALE_OAK_LOG, Items.PALE_OAK_WOOD, output, "pale_oak");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG, Items.STRIPPED_PALE_OAK_WOOD, output, "pale_oak");

		planksFromHollowedStem(provider, WWItemTags.HOLLOWED_CRIMSON_STEMS, Items.CRIMSON_PLANKS, output, "crimson");
		hyphaeFromHollowed(provider, WWBlocks.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, output, "crimson");
		strippedHyphaeFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, output, "crimson");

		planksFromHollowedStem(provider, WWItemTags.HOLLOWED_WARPED_STEMS, Items.WARPED_PLANKS, output, "warped");
		hyphaeFromHollowed(provider, WWBlocks.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, output, "warped");
		strippedHyphaeFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, output, "warped");

		planksFromHollowed(provider, WWItemTags.HOLLOWED_WILLOW_LOGS, WWBlocks.WILLOW_PLANKS, output, "willow");
		woodFromHollowed(provider, WWBlocks.HOLLOWED_WILLOW_LOG, WWBlocks.WILLOW_WOOD, output, "willow");
		strippedWoodFromHollowed(provider, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_WOOD, output, "willow");

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
