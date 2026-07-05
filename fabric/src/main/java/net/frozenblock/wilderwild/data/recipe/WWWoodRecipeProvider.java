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
import net.frozenblock.wilderwild.registry.WWBlockFamilies;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

public final class WWWoodRecipeProvider {

	static void buildRecipes(RecipeProvider provider, RecipeOutput output) {
		provider.generateRecipes(WWBlockFamilies.BAOBAB_PLANKS, FeatureFlags.VANILLA_SET);
		provider.planksFromLogs(WWItems.BAOBAB_PLANKS, WWBlockItemTags.BAOBAB_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.BAOBAB_WOOD, WWItems.BAOBAB_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_BAOBAB_WOOD, WWItems.STRIPPED_BAOBAB_LOG);
		provider.woodenBoat(WWItems.BAOBAB_BOAT, WWItems.BAOBAB_PLANKS);
		provider.chestBoat(WWItems.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		provider.shelf(WWItems.BAOBAB_SHELF, WWItems.STRIPPED_BAOBAB_LOG);
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_BAOBAB_LOGS.item(), WWItems.BAOBAB_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_BAOBAB_LOG, WWItems.BAOBAB_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_BAOBAB_LOG, WWItems.STRIPPED_BAOBAB_WOOD, output);

		provider.generateRecipes(WWBlockFamilies.WILLOW_PLANKS, FeatureFlags.VANILLA_SET);
		provider.planksFromLogs(WWItems.WILLOW_PLANKS, WWBlockItemTags.WILLOW_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.WILLOW_WOOD, WWItems.WILLOW_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_WILLOW_WOOD, WWItems.STRIPPED_WILLOW_LOG);
		provider.woodenBoat(WWItems.WILLOW_BOAT, WWItems.WILLOW_PLANKS);
		provider.chestBoat(WWItems.WILLOW_CHEST_BOAT, WWItems.WILLOW_BOAT);
		provider.shelf(WWItems.WILLOW_SHELF, WWItems.STRIPPED_WILLOW_LOG);
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_WILLOW_LOGS.item(), WWItems.WILLOW_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_WILLOW_LOG, WWItems.WILLOW_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_WILLOW_LOG, WWItems.STRIPPED_WILLOW_WOOD, output);

		provider.generateRecipes(WWBlockFamilies.CYPRESS_PLANKS, FeatureFlags.VANILLA_SET);
		provider.planksFromLogs(WWItems.CYPRESS_PLANKS, WWBlockItemTags.CYPRESS_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.CYPRESS_WOOD, WWItems.CYPRESS_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_CYPRESS_WOOD, WWItems.STRIPPED_CYPRESS_LOG);
		provider.woodenBoat(WWItems.CYPRESS_BOAT, WWItems.CYPRESS_PLANKS);
		provider.chestBoat(WWItems.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		provider.shelf(WWItems.CYPRESS_SHELF, WWItems.STRIPPED_CYPRESS_LOG);
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_CYPRESS_LOGS.item(), WWItems.CYPRESS_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_CYPRESS_LOG, WWItems.CYPRESS_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CYPRESS_LOG, WWItems.STRIPPED_CYPRESS_WOOD, output);

		provider.generateRecipes(WWBlockFamilies.PALM_PLANKS, FeatureFlags.VANILLA_SET);
		provider.planksFromLogs(WWItems.PALM_PLANKS, WWBlockItemTags.PALM_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.PALM_WOOD, WWItems.PALM_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_PALM_WOOD, WWItems.STRIPPED_PALM_LOG);
		provider.woodenBoat(WWItems.PALM_BOAT, WWItems.PALM_PLANKS);
		provider.chestBoat(WWItems.PALM_CHEST_BOAT, WWItems.PALM_BOAT);
		provider.shelf(WWItems.PALM_SHELF, WWItems.STRIPPED_PALM_LOG);
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_PALM_LOGS.item(), WWItems.PALM_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_PALM_LOG, WWItems.PALM_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_PALM_LOG, WWItems.STRIPPED_PALM_WOOD, output);

		provider.generateRecipes(WWBlockFamilies.MAPLE_PLANKS, FeatureFlags.VANILLA_SET);
		provider.planksFromLogs(WWItems.MAPLE_PLANKS, WWBlockItemTags.MAPLE_LOGS.item(), 4);
		provider.woodFromLogs(WWItems.MAPLE_WOOD, WWItems.MAPLE_LOG);
		provider.woodFromLogs(WWItems.STRIPPED_MAPLE_WOOD, WWItems.STRIPPED_MAPLE_LOG);
		provider.woodenBoat(WWItems.MAPLE_BOAT, WWItems.MAPLE_PLANKS);
		provider.chestBoat(WWItems.MAPLE_CHEST_BOAT, WWItems.MAPLE_BOAT);
		provider.shelf(WWItems.MAPLE_SHELF, WWItems.STRIPPED_MAPLE_LOG);
		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_MAPLE_LOGS.item(), WWItems.MAPLE_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_MAPLE_LOG, WWItems.MAPLE_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_MAPLE_LOG, WWItems.STRIPPED_MAPLE_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_ACACIA_LOGS.item(), Items.ACACIA_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_BIRCH_LOGS.item(), Items.BIRCH_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_CHERRY_LOGS.item(), Items.CHERRY_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_DARK_OAK_LOGS.item(), Items.DARK_OAK_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_JUNGLE_LOGS.item(), Items.JUNGLE_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_MANGROVE_LOGS.item(), Items.MANGROVE_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_OAK_LOGS.item(), Items.OAK_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_OAK_LOG, Items.OAK_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_SPRUCE_LOGS.item(), Items.SPRUCE_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_PALE_OAK_LOGS.item(), Items.PALE_OAK_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_PALE_OAK_LOG, Items.PALE_OAK_WOOD, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_PALE_OAK_LOG, Items.STRIPPED_PALE_OAK_WOOD, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_CRIMSON_STEMS.item(), Items.CRIMSON_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE, output);

		planksFromHollowed(provider, WWBlockItemTags.HOLLOWED_WARPED_STEMS.item(), Items.WARPED_PLANKS, output);
		woodFromHollowed(provider, WWItems.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE, output);
		woodFromHollowed(provider, WWItems.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE, output);
	}

	public static void planksFromHollowed(RecipeProvider provider, TagKey<Item> input, ItemLike planks, RecipeOutput exporter) {
		provider.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 2)
			.group("planks")
			.requires(input)
			.unlockedBy("has_hollowed_log", provider.has(input))
			.save(exporter, RecipeProvider.getItemName(planks) + "_from_hollowed");
	}

	public static void woodFromHollowed(RecipeProvider provider, ItemLike hollowedLog, ItemLike wood, RecipeOutput exporter) {
		provider.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 2)
			.group("bark")
			.define('#', Ingredient.of(hollowedLog))
			.pattern("##")
			.pattern("##")
			.unlockedBy(RecipeProvider.getHasName(hollowedLog), provider.has(hollowedLog))
			.save(exporter, WWConstants.string(RecipeProvider.getItemName(wood) + "_from_" + RecipeProvider.getItemName(hollowedLog)));
	}

}
