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

import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.frozenblock.lib.item.api.recipe.RecipeExportNamespaceFix;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.registry.WWBlockFamilies;
import net.frozenblock.wilderwild.registry.WWItems;
import net.frozenblock.wilderwild.tag.WWBlockItemTags;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.TagKey;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;

public final class WWWoodSetRecipeProvider extends FabricRecipeProvider {

	public WWWoodSetRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				RecipeExportNamespaceFix.setCurrentGeneratingModId(WWConstants.MOD_ID);

				// BAOBAB
				this.generateRecipes(WWBlockFamilies.BAOBAB_PLANKS, FeatureFlags.VANILLA_SET);
				this.planksFromLogs(WWItems.BAOBAB_PLANKS, WWBlockItemTags.BAOBAB_LOGS.item(), 4);
				this.woodFromLogs(WWItems.BAOBAB_WOOD, WWItems.BAOBAB_LOG);
				this.woodFromLogs(WWItems.STRIPPED_BAOBAB_WOOD, WWItems.STRIPPED_BAOBAB_LOG);
				this.woodenBoat(WWItems.BAOBAB_BOAT, WWItems.BAOBAB_PLANKS);
				this.chestBoat(WWItems.BAOBAB_CHEST_BOAT, WWItems.BAOBAB_BOAT);
				this.shelf(WWItems.BAOBAB_SHELF, WWItems.STRIPPED_BAOBAB_LOG);
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_BAOBAB_LOGS.item(), WWItems.BAOBAB_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_BAOBAB_LOG, WWItems.BAOBAB_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_BAOBAB_LOG, WWItems.STRIPPED_BAOBAB_WOOD);

				// WILLOW
				this.generateRecipes(WWBlockFamilies.WILLOW_PLANKS, FeatureFlags.VANILLA_SET);
				this.planksFromLogs(WWItems.WILLOW_PLANKS, WWBlockItemTags.WILLOW_LOGS.item(), 4);
				this.woodFromLogs(WWItems.WILLOW_WOOD, WWItems.WILLOW_LOG);
				this.woodFromLogs(WWItems.STRIPPED_WILLOW_WOOD, WWItems.STRIPPED_WILLOW_LOG);
				this.woodenBoat(WWItems.WILLOW_BOAT, WWItems.WILLOW_PLANKS);
				this.chestBoat(WWItems.WILLOW_CHEST_BOAT, WWItems.WILLOW_BOAT);
				this.shelf(WWItems.WILLOW_SHELF, WWItems.STRIPPED_WILLOW_LOG);
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_WILLOW_LOGS.item(), WWItems.WILLOW_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_WILLOW_LOG, WWItems.WILLOW_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_WILLOW_LOG, WWItems.STRIPPED_WILLOW_WOOD);

				// CYPRESS
				this.generateRecipes(WWBlockFamilies.CYPRESS_PLANKS, FeatureFlags.VANILLA_SET);
				this.planksFromLogs(WWItems.CYPRESS_PLANKS, WWBlockItemTags.CYPRESS_LOGS.item(), 4);
				this.woodFromLogs(WWItems.CYPRESS_WOOD, WWItems.CYPRESS_LOG);
				this.woodFromLogs(WWItems.STRIPPED_CYPRESS_WOOD, WWItems.STRIPPED_CYPRESS_LOG);
				this.woodenBoat(WWItems.CYPRESS_BOAT, WWItems.CYPRESS_PLANKS);
				this.chestBoat(WWItems.CYPRESS_CHEST_BOAT, WWItems.CYPRESS_BOAT);
				this.shelf(WWItems.CYPRESS_SHELF, WWItems.STRIPPED_CYPRESS_LOG);
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_CYPRESS_LOGS.item(), WWItems.CYPRESS_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_CYPRESS_LOG, WWItems.CYPRESS_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_CYPRESS_LOG, WWItems.STRIPPED_CYPRESS_WOOD);

				// PALM
				this.generateRecipes(WWBlockFamilies.PALM_PLANKS, FeatureFlags.VANILLA_SET);
				this.planksFromLogs(WWItems.PALM_PLANKS, WWBlockItemTags.PALM_LOGS.item(), 4);
				this.woodFromLogs(WWItems.PALM_WOOD, WWItems.PALM_LOG);
				this.woodFromLogs(WWItems.STRIPPED_PALM_WOOD, WWItems.STRIPPED_PALM_LOG);
				this.woodenBoat(WWItems.PALM_BOAT, WWItems.PALM_PLANKS);
				this.chestBoat(WWItems.PALM_CHEST_BOAT, WWItems.PALM_BOAT);
				this.shelf(WWItems.PALM_SHELF, WWItems.STRIPPED_PALM_LOG);
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_PALM_LOGS.item(), WWItems.PALM_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_PALM_LOG, WWItems.PALM_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_PALM_LOG, WWItems.STRIPPED_PALM_WOOD);

				// MAPLE
				this.generateRecipes(WWBlockFamilies.MAPLE_PLANKS, FeatureFlags.VANILLA_SET);
				this.planksFromLogs(WWItems.MAPLE_PLANKS, WWBlockItemTags.MAPLE_LOGS.item(), 4);
				this.woodFromLogs(WWItems.MAPLE_WOOD, WWItems.MAPLE_LOG);
				this.woodFromLogs(WWItems.STRIPPED_MAPLE_WOOD, WWItems.STRIPPED_MAPLE_LOG);
				this.woodenBoat(WWItems.MAPLE_BOAT, WWItems.MAPLE_PLANKS);
				this.chestBoat(WWItems.MAPLE_CHEST_BOAT, WWItems.MAPLE_BOAT);
				this.shelf(WWItems.MAPLE_SHELF, WWItems.STRIPPED_MAPLE_LOG);
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_MAPLE_LOGS.item(), WWItems.MAPLE_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_MAPLE_LOG, WWItems.MAPLE_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_MAPLE_LOG, WWItems.STRIPPED_MAPLE_WOOD);

				// ACACIA
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_ACACIA_LOGS.item(), Items.ACACIA_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_ACACIA_LOG, Items.ACACIA_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_ACACIA_LOG, Items.STRIPPED_ACACIA_WOOD);

				// BIRCH
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_BIRCH_LOGS.item(), Items.BIRCH_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_BIRCH_LOG, Items.BIRCH_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_BIRCH_LOG, Items.STRIPPED_BIRCH_WOOD);

				// CHERRY
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_CHERRY_LOGS.item(), Items.CHERRY_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_CHERRY_LOG, Items.CHERRY_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_CHERRY_LOG, Items.STRIPPED_CHERRY_WOOD);

				// DARK OAK
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_DARK_OAK_LOGS.item(), Items.DARK_OAK_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_DARK_OAK_LOG, Items.DARK_OAK_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_DARK_OAK_LOG, Items.STRIPPED_DARK_OAK_WOOD);

				// JUNGLE
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_JUNGLE_LOGS.item(), Items.JUNGLE_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_JUNGLE_LOG, Items.JUNGLE_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_JUNGLE_LOG, Items.STRIPPED_JUNGLE_WOOD);

				// MANGROVE
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_MANGROVE_LOGS.item(), Items.MANGROVE_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_MANGROVE_LOG, Items.MANGROVE_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_MANGROVE_LOG, Items.STRIPPED_MANGROVE_WOOD);

				// OAK
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_OAK_LOGS.item(), Items.OAK_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_OAK_LOG, Items.OAK_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_OAK_LOG, Items.STRIPPED_OAK_WOOD);

				// SPRUCE
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_SPRUCE_LOGS.item(), Items.SPRUCE_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_SPRUCE_LOG, Items.SPRUCE_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_SPRUCE_LOG, Items.STRIPPED_SPRUCE_WOOD);

				// PALE OAK
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_PALE_OAK_LOGS.item(), Items.PALE_OAK_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_PALE_OAK_LOG, Items.PALE_OAK_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_PALE_OAK_LOG, Items.STRIPPED_PALE_OAK_WOOD);

				// POPLAR
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_POPLAR_LOGS.item(), Items.POPLAR_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_POPLAR_LOG, Items.POPLAR_WOOD);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_POPLAR_LOG, Items.STRIPPED_POPLAR_WOOD);

				// CRIMSON
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_CRIMSON_STEMS.item(), Items.CRIMSON_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_CRIMSON_STEM, Items.CRIMSON_HYPHAE);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_CRIMSON_STEM, Items.STRIPPED_CRIMSON_HYPHAE);

				// WARPED
				this.planksFromHollowed(WWBlockItemTags.HOLLOWED_WARPED_STEMS.item(), Items.WARPED_PLANKS);
				this.woodFromHollowed(WWItems.HOLLOWED_WARPED_STEM, Items.WARPED_HYPHAE);
				this.woodFromHollowed(WWItems.STRIPPED_HOLLOWED_WARPED_STEM, Items.STRIPPED_WARPED_HYPHAE);

				// LEAF LITTER
				this.leafLitterSmelting(Items.ACACIA_LEAVES, WWItems.ACACIA_LEAF_LITTER);
				this.leafLitterSmelting(Items.AZALEA_LEAVES, WWItems.AZALEA_LEAF_LITTER);
				this.leafLitterSmelting(WWItems.BAOBAB_LEAVES, WWItems.BAOBAB_LEAF_LITTER);
				this.leafLitterSmelting(Items.BIRCH_LEAVES, WWItems.BIRCH_LEAF_LITTER);
				this.leafLitterSmelting(Items.CHERRY_LEAVES, WWItems.CHERRY_LEAF_LITTER);
				this.leafLitterSmelting(WWItems.CYPRESS_LEAVES, WWItems.CYPRESS_LEAF_LITTER);
				this.leafLitterSmelting(Items.DARK_OAK_LEAVES, WWItems.DARK_OAK_LEAF_LITTER);
				this.leafLitterSmelting(Items.JUNGLE_LEAVES, WWItems.JUNGLE_LEAF_LITTER);
				this.leafLitterSmelting(Items.MANGROVE_LEAVES, WWItems.MANGROVE_LEAF_LITTER);
				this.leafLitterSmelting(Items.PALE_OAK_LEAVES, WWItems.PALE_OAK_LEAF_LITTER);
				this.leafLitterSmelting(WWItems.PALM_FRONDS, WWItems.PALM_FROND_LITTER);
				this.leafLitterSmelting(Items.SPRUCE_LEAVES, WWItems.SPRUCE_LEAF_LITTER);
				this.leafLitterSmelting(WWItems.WILLOW_LEAVES, WWItems.WILLOW_LEAF_LITTER);

				MapleCollection.zipApply(WWItems.MAPLE_LEAF_LITTER, WWItems.MAPLE_LEAVES,
					(leafLitter, leaves) -> this.leafLitterSmelting(leaves, leafLitter)
				);

				RecipeExportNamespaceFix.clearCurrentGeneratingModId();
			}

			void planksFromHollowed(TagKey<Item> input, ItemLike planks) {
				this.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 2)
					.group("planks")
					.requires(input)
					.unlockedBy("has_hollowed_log", this.has(input))
					.save(this.output, getItemName(planks) + "_from_hollowed");
			}

			void woodFromHollowed(ItemLike hollowedLog, ItemLike wood) {
				this.shaped(RecipeCategory.BUILDING_BLOCKS, wood, 2)
					.group("bark")
					.define('#', Ingredient.of(hollowedLog))
					.pattern("##")
					.pattern("##")
					.unlockedBy(getHasName(hollowedLog), this.has(hollowedLog))
					.save(this.output, WWConstants.string(getItemName(wood) + "_from_" + getItemName(hollowedLog)));
			}

			void leafLitterSmelting(ItemLike leavesBlock, ItemLike leafLitter) {
				SimpleCookingRecipeBuilder.smelting(Ingredient.of(leavesBlock), RecipeCategory.MISC, CookingBookCategory.BLOCKS, leafLitter, 0.1F, 200)
					.unlockedBy(getHasName(leavesBlock), this.has(leavesBlock))
					.save(output);
			}
		};
	}

	@Override
	public String getName() {
		return "Wilder Wild Wood Set Recipes";
	}
}
