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

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderItemTags;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import java.util.function.Consumer;

public class HollowedLogRecipeProvider {

	static void buildRecipes(Consumer<FinishedRecipe> exporter) {
		planksFromHollowed(WilderItemTags.HOLLOWED_ACACIA_LOGS, Items.ACACIA_PLANKS, exporter, "acacia");
		woodFromHollowed(RegisterBlocks.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, exporter, "acacia");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, exporter, "acacia");

		planksFromHollowed(WilderItemTags.HOLLOWED_BAOBAB_LOGS, RegisterBlocks.BAOBAB_PLANKS, exporter, "baobab");
		woodFromHollowed(RegisterBlocks.HOLLOWED_BAOBAB_LOG, RegisterBlocks.BAOBAB_WOOD, exporter, "baobab");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG, RegisterBlocks.STRIPPED_BAOBAB_WOOD, exporter, "baobab");

		planksFromHollowed(WilderItemTags.HOLLOWED_BIRCH_LOGS, Items.BIRCH_PLANKS, exporter, "birch");
		woodFromHollowed(RegisterBlocks.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, exporter, "birch");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, exporter, "birch");

		planksFromHollowed(WilderItemTags.HOLLOWED_CHERRY_LOGS, Items.CHERRY_PLANKS, exporter, "cherry");
		woodFromHollowed(RegisterBlocks.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, exporter, "cherry");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, exporter, "cherry");

		planksFromHollowed(WilderItemTags.HOLLOWED_CYPRESS_LOGS, RegisterBlocks.CYPRESS_PLANKS, exporter, "cypress");
		woodFromHollowed(RegisterBlocks.HOLLOWED_CYPRESS_LOG, RegisterBlocks.CYPRESS_WOOD, exporter, "cypress");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG, RegisterBlocks.STRIPPED_CYPRESS_WOOD, exporter, "cypress");

		planksFromHollowed(WilderItemTags.HOLLOWED_DARK_OAK_LOGS, Items.DARK_OAK_PLANKS, exporter, "dark_oak");
		woodFromHollowed(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, exporter, "dark_oak");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, exporter, "dark_oak");

		planksFromHollowed(WilderItemTags.HOLLOWED_JUNGLE_LOGS, Items.JUNGLE_PLANKS, exporter, "jungle");
		woodFromHollowed(RegisterBlocks.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, exporter, "jungle");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, exporter, "jungle");

		planksFromHollowed(WilderItemTags.HOLLOWED_MANGROVE_LOGS, Items.MANGROVE_PLANKS, exporter, "mangrove");
		woodFromHollowed(RegisterBlocks.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, exporter, "mangrove");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, exporter, "mangrove");

		planksFromHollowed(WilderItemTags.HOLLOWED_OAK_LOGS, Items.OAK_PLANKS, exporter, "oak");
		woodFromHollowed(RegisterBlocks.HOLLOWED_OAK_LOG, Items.OAK_WOOD, exporter, "oak");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, exporter, "oak");

		planksFromHollowed(WilderItemTags.HOLLOWED_PALM_LOGS, RegisterBlocks.PALM_PLANKS, exporter, "palm");
		woodFromHollowed(RegisterBlocks.HOLLOWED_PALM_LOG, RegisterBlocks.PALM_WOOD, exporter, "palm");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_PALM_LOG, RegisterBlocks.STRIPPED_PALM_WOOD, exporter, "palm");

		planksFromHollowed(WilderItemTags.HOLLOWED_SPRUCE_LOGS, Items.SPRUCE_PLANKS, exporter, "spruce");
		woodFromHollowed(RegisterBlocks.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, exporter, "spruce");
		strippedWoodFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, exporter, "spruce");

		planksFromHollowedStem(WilderItemTags.HOLLOWED_CRIMSON_STEMS, Items.CRIMSON_PLANKS, exporter, "crimson");
		hyphaeFromHollowed(RegisterBlocks.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, exporter, "crimson");
		strippedHyphaeFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, exporter, "crimson");

		planksFromHollowedStem(WilderItemTags.HOLLOWED_WARPED_STEMS, Items.WARPED_PLANKS, exporter, "warped");
		hyphaeFromHollowed(RegisterBlocks.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, exporter, "warped");
		strippedHyphaeFromHollowed(RegisterBlocks.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, exporter, "warped");
	}

	public static void planksFromHollowed(TagKey<Item> input, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_log", RecipeProvider.has(input))
			.save(exporter, WilderSharedConstants.id(name + "_planks_from_hollowed"));
	}

	public static void woodFromHollowed(ItemLike hollowedLog, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedLog))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_log", RecipeProvider.has(hollowedLog))
		.save(exporter, WilderSharedConstants.id(name + "_wood_from_hollowed"));
	}

	public static void strippedWoodFromHollowed(ItemLike hollowedLog, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		woodFromHollowed(hollowedLog, output, exporter, "stripped_" + name);
	}

	public static void planksFromHollowedStem(TagKey<Item> input, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_stem", RecipeProvider.has(input))
			.save(exporter, WilderSharedConstants.id(name + "_planks_from_hollowed"));
	}

	public static void hyphaeFromHollowed(ItemLike hollowedStem, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, output, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedStem))
			.pattern("##")
			.pattern("##")
			.unlockedBy("has_stem", RecipeProvider.has(hollowedStem))
			.save(exporter, WilderSharedConstants.id(name + "_hyphae_from_hollowed"));
	}

	public static void strippedHyphaeFromHollowed(ItemLike hollowedStem, ItemLike output, Consumer<FinishedRecipe> exporter, String name) {
		woodFromHollowed(hollowedStem, output, exporter, "stripped_" + name);
	}
}
