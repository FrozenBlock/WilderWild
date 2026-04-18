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
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
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
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_ACACIA_LOGS.item(), Items.ACACIA_PLANKS, output, "acacia");
		woodFromHollowed(provider, WWItems.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, output, "acacia");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, output, "acacia");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_BAOBAB_LOGS.item(), WWItems.BAOBAB_PLANKS, output, "baobab");
		woodFromHollowed(provider, WWItems.HOLLOWED_BAOBAB_LOG, WWItems.BAOBAB_WOOD, output, "baobab");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_BAOBAB_LOG, WWItems.STRIPPED_BAOBAB_WOOD, output, "baobab");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_BIRCH_LOGS.item(), Items.BIRCH_PLANKS, output, "birch");
		woodFromHollowed(provider, WWItems.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, output, "birch");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, output, "birch");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_CHERRY_LOGS.item(), Items.CHERRY_PLANKS, output, "cherry");
		woodFromHollowed(provider, WWItems.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, output, "cherry");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, output, "cherry");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_CYPRESS_LOGS.item(), WWItems.CYPRESS_PLANKS, output, "cypress");
		woodFromHollowed(provider, WWItems.HOLLOWED_CYPRESS_LOG, WWItems.CYPRESS_WOOD, output, "cypress");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CYPRESS_LOG, WWItems.STRIPPED_CYPRESS_WOOD, output, "cypress");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_DARK_OAK_LOGS.item(), Items.DARK_OAK_PLANKS, output, "dark_oak");
		woodFromHollowed(provider, WWItems.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, output, "dark_oak");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, output, "dark_oak");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_JUNGLE_LOGS.item(), Items.JUNGLE_PLANKS, output, "jungle");
		woodFromHollowed(provider, WWItems.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, output, "jungle");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, output, "jungle");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_MANGROVE_LOGS.item(), Items.MANGROVE_PLANKS, output, "mangrove");
		woodFromHollowed(provider, WWItems.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, output, "mangrove");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, output, "mangrove");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_MAPLE_LOGS.item(), WWItems.MAPLE_PLANKS, output, "maple");
		woodFromHollowed(provider, WWItems.HOLLOWED_MAPLE_LOG, WWItems.MAPLE_WOOD, output, "maple");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_MAPLE_LOG, WWItems.STRIPPED_MAPLE_WOOD, output, "maple");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_OAK_LOGS.item(), Items.OAK_PLANKS, output, "oak");
		woodFromHollowed(provider, WWItems.HOLLOWED_OAK_LOG, Items.OAK_WOOD, output, "oak");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, output, "oak");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_PALM_LOGS.item(), WWItems.PALM_PLANKS, output, "palm");
		woodFromHollowed(provider, WWItems.HOLLOWED_PALM_LOG, WWItems.PALM_WOOD, output, "palm");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_PALM_LOG, WWItems.STRIPPED_PALM_WOOD, output, "palm");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_SPRUCE_LOGS.item(), Items.SPRUCE_PLANKS, output, "spruce");
		woodFromHollowed(provider, WWItems.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, output, "spruce");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, output, "spruce");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_PALE_OAK_LOGS.item(), Items.PALE_OAK_PLANKS, output, "pale_oak");
		woodFromHollowed(provider, WWItems.HOLLOWED_PALE_OAK_LOG, Items.PALE_OAK_WOOD, output, "pale_oak");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_PALE_OAK_LOG, Items.STRIPPED_PALE_OAK_WOOD, output, "pale_oak");

		planksFromHollowedStem(provider, WWBlockItemTags.HOLLOWED_CRIMSON_STEMS.item(), Items.CRIMSON_PLANKS, output, "crimson");
		hyphaeFromHollowed(provider, WWItems.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, output, "crimson");
		strippedHyphaeFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, output, "crimson");

		planksFromHollowedStem(provider, WWBlockItemTags.HOLLOWED_WARPED_STEMS.item(), Items.WARPED_PLANKS, output, "warped");
		hyphaeFromHollowed(provider, WWItems.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, output, "warped");
		strippedHyphaeFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, output, "warped");

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_WILLOW_LOGS.item(), WWItems.WILLOW_PLANKS, output, "willow");
		woodFromHollowed(provider, WWItems.HOLLOWED_WILLOW_LOG, WWItems.WILLOW_WOOD, output, "willow");
		strippedWoodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_WILLOW_LOG, WWItems.STRIPPED_WILLOW_WOOD, output, "willow");

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
