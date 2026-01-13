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

package net.frozenblock.wilderwild.registry;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public final class WWCreativeInventorySorting {

	public static void init() {
		// BAOBAB (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(Items.MANGROVE_BUTTON, WWBlocks.BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_WOOD, WWBlocks.BAOBAB_PLANKS);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_PLANKS, WWBlocks.BAOBAB_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_STAIRS, WWBlocks.BAOBAB_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_SLAB, WWBlocks.BAOBAB_FENCE);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE_GATE);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_DOOR);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_DOOR, WWBlocks.BAOBAB_TRAPDOOR);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_BUTTON);
		// BAOBAB (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(Items.MANGROVE_LOG, WWBlocks.BAOBAB_LOG);
		insertAfterInNaturalBlocks(Items.MANGROVE_LEAVES, WWBlocks.BAOBAB_LEAVES);
		// BAOBAB (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(Items.MANGROVE_HANGING_SIGN, WWItems.BAOBAB_SIGN);
		insertAfterInFunctionalBlocks(WWItems.BAOBAB_SIGN, WWItems.BAOBAB_HANGING_SIGN);
		insertAfterInFunctionalBlocks(Items.MANGROVE_SHELF, WWBlocks.BAOBAB_SHELF);
		// BAOBAB (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(Items.MANGROVE_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		insertAfterInToolsAndUtilities(WWItems.BAOBAB_BOAT, WWItems.BAOBAB_CHEST_BOAT);
		// BAOBAB NUT
		insertAfterInFoodAndDrinks(Items.ENCHANTED_GOLDEN_APPLE, WWItems.BAOBAB_NUT);
		insertAfterInNaturalBlocks(Items.MANGROVE_PROPAGULE, WWItems.BAOBAB_NUT);

		// WILLOW (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWBlocks.BAOBAB_BUTTON, WWBlocks.WILLOW_LOG);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_LOG, WWBlocks.WILLOW_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_WOOD, WWBlocks.WILLOW_PLANKS);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_PLANKS, WWBlocks.WILLOW_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_STAIRS, WWBlocks.WILLOW_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_SLAB, WWBlocks.WILLOW_FENCE);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_FENCE, WWBlocks.WILLOW_FENCE_GATE);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_FENCE_GATE, WWBlocks.WILLOW_DOOR);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_DOOR, WWBlocks.WILLOW_TRAPDOOR);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_TRAPDOOR, WWBlocks.WILLOW_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_BUTTON);
		// WILLOW (NATURAL_BLOCKS)
		insertAfterInNaturalBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.WILLOW_LOG);
		insertAfterInNaturalBlocks(WWBlocks.BAOBAB_LEAVES, WWBlocks.WILLOW_LEAVES);
		insertAfterInNaturalBlocks(WWItems.BAOBAB_NUT, WWBlocks.WILLOW_SAPLING);
		// WILLOW (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.BAOBAB_HANGING_SIGN, WWItems.WILLOW_SIGN);
		insertAfterInFunctionalBlocks(WWItems.WILLOW_SIGN, WWItems.WILLOW_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWBlocks.BAOBAB_SHELF, WWBlocks.WILLOW_SHELF);
		// WILLOW (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWItems.BAOBAB_CHEST_BOAT, WWItems.WILLOW_BOAT);
		insertAfterInToolsAndUtilities(WWItems.WILLOW_BOAT, WWItems.WILLOW_CHEST_BOAT);

		// CYPRESS (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWBlocks.WILLOW_BUTTON, WWBlocks.CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.CYPRESS_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_WOOD, WWBlocks.CYPRESS_PLANKS);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_PLANKS, WWBlocks.CYPRESS_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_STAIRS, WWBlocks.CYPRESS_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_SLAB, WWBlocks.CYPRESS_FENCE);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_FENCE, WWBlocks.CYPRESS_FENCE_GATE);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_DOOR);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_DOOR, WWBlocks.CYPRESS_TRAPDOOR);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_BUTTON);
		// CYPRESS (NATURAL_BLOCKS)
		insertAfterInNaturalBlocks(WWBlocks.WILLOW_LOG, WWBlocks.CYPRESS_LOG);
		insertAfterInNaturalBlocks(WWBlocks.WILLOW_LEAVES, WWBlocks.CYPRESS_LEAVES);
		insertAfterInNaturalBlocks(WWBlocks.WILLOW_SAPLING, WWBlocks.CYPRESS_SAPLING);
		// CYPRESS (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.WILLOW_HANGING_SIGN, WWItems.CYPRESS_SIGN);
		insertAfterInFunctionalBlocks(WWItems.CYPRESS_SIGN, WWItems.CYPRESS_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWBlocks.WILLOW_SHELF, WWBlocks.CYPRESS_SHELF);
		// CYPRESS (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWItems.WILLOW_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		insertAfterInToolsAndUtilities(WWItems.CYPRESS_BOAT, WWItems.CYPRESS_CHEST_BOAT);

		// PALM (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWBlocks.CYPRESS_BUTTON, WWBlocks.PALM_LOG);
		insertAfterInBuildingBlocks(WWBlocks.PALM_LOG, WWBlocks.PALM_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_PALM_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_WOOD, WWBlocks.PALM_PLANKS);
		insertAfterInBuildingBlocks(WWBlocks.PALM_PLANKS, WWBlocks.PALM_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.PALM_STAIRS, WWBlocks.PALM_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.PALM_SLAB, WWBlocks.PALM_FENCE);
		insertAfterInBuildingBlocks(WWBlocks.PALM_FENCE, WWBlocks.PALM_FENCE_GATE);
		insertAfterInBuildingBlocks(WWBlocks.PALM_FENCE_GATE, WWBlocks.PALM_DOOR);
		insertAfterInBuildingBlocks(WWBlocks.PALM_DOOR, WWBlocks.PALM_TRAPDOOR);
		insertAfterInBuildingBlocks(WWBlocks.PALM_TRAPDOOR, WWBlocks.PALM_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWBlocks.PALM_PRESSURE_PLATE, WWBlocks.PALM_BUTTON);
		// PALM (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.PALM_LOG);
		insertAfterInNaturalBlocks(WWBlocks.CYPRESS_LEAVES, WWBlocks.PALM_FRONDS);
		// PALM (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.CYPRESS_HANGING_SIGN, WWItems.PALM_SIGN);
		insertAfterInFunctionalBlocks(WWItems.PALM_SIGN, WWItems.PALM_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWBlocks.CYPRESS_SHELF, WWBlocks.PALM_SHELF);
		// PALM (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(WWItems.CYPRESS_CHEST_BOAT, WWItems.PALM_BOAT);
		insertAfterInToolsAndUtilities(WWItems.PALM_BOAT, WWItems.PALM_CHEST_BOAT);
		// COCONUT
		insertAfterInCombat(Items.BLUE_EGG, WWItems.COCONUT);
		insertAfterInNaturalBlocks(WWBlocks.CYPRESS_SAPLING, WWItems.COCONUT);
		insertAfterInFoodAndDrinks(WWItems.BAOBAB_NUT, WWItems.SPLIT_COCONUT);

		// MAPLE (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(Items.CHERRY_BUTTON, WWBlocks.MAPLE_LOG);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_LOG, WWBlocks.MAPLE_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_WOOD);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_WOOD, WWBlocks.MAPLE_PLANKS);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_PLANKS, WWBlocks.MAPLE_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_STAIRS, WWBlocks.MAPLE_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_SLAB, WWBlocks.MAPLE_FENCE);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_FENCE, WWBlocks.MAPLE_FENCE_GATE);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_FENCE_GATE, WWBlocks.MAPLE_DOOR);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_DOOR, WWBlocks.MAPLE_TRAPDOOR);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_TRAPDOOR, WWBlocks.MAPLE_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWBlocks.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_BUTTON);
		// MAPLE (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(Items.CHERRY_LOG, WWBlocks.MAPLE_LOG);
		insertAfterInNaturalBlocks(Items.CHERRY_SAPLING, WWBlocks.YELLOW_MAPLE_SAPLING);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_MAPLE_SAPLING, WWBlocks.ORANGE_MAPLE_SAPLING);
		insertAfterInNaturalBlocks(WWBlocks.ORANGE_MAPLE_SAPLING, WWBlocks.RED_MAPLE_SAPLING);
		insertAfterInNaturalBlocks(Blocks.CHERRY_LEAVES, WWBlocks.YELLOW_MAPLE_LEAVES);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_MAPLE_LEAVES, WWBlocks.ORANGE_MAPLE_LEAVES);
		insertAfterInNaturalBlocks(WWBlocks.ORANGE_MAPLE_LEAVES, WWBlocks.RED_MAPLE_LEAVES);
		// MAPLE (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(Items.CHERRY_HANGING_SIGN, WWItems.MAPLE_SIGN);
		insertAfterInFunctionalBlocks(WWItems.MAPLE_SIGN, WWItems.MAPLE_HANGING_SIGN);
		insertAfterInFunctionalBlocks(Items.CHERRY_SHELF, WWBlocks.MAPLE_SHELF);
		// MAPLE (TOOLS AND UTILITIES)
		insertAfterInToolsAndUtilities(Items.CHERRY_CHEST_BOAT, WWItems.MAPLE_BOAT);
		insertAfterInToolsAndUtilities(WWItems.MAPLE_BOAT, WWItems.MAPLE_CHEST_BOAT);

		// HOLLOWED LOGS
		insertAfterInBuildingAndNaturalBlocks(Items.OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
		insertAfterInBuildingBlocks(Items.STRIPPED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);

		insertAfterInBuildingAndNaturalBlocks(Items.WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);
		insertAfterInBuildingBlocks(Items.STRIPPED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		insertAfterInBuildingAndNaturalBlocks(Items.MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		insertBeforeInBuildingBlocks(WWBlocks.BAOBAB_WOOD, WWBlocks.HOLLOWED_BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		insertAfterInNaturalBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG);

		insertBeforeInBuildingBlocks(WWBlocks.WILLOW_WOOD, WWBlocks.HOLLOWED_WILLOW_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		insertAfterInNaturalBlocks(WWBlocks.WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG);

		insertBeforeInBuildingBlocks(WWBlocks.CYPRESS_WOOD, WWBlocks.HOLLOWED_CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		insertAfterInNaturalBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG);

		insertBeforeInBuildingBlocks(WWBlocks.PALM_WOOD, WWBlocks.HOLLOWED_PALM_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		insertAfterInNaturalBlocks(WWBlocks.PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG);

		insertBeforeInBuildingBlocks(WWBlocks.MAPLE_WOOD, WWBlocks.HOLLOWED_MAPLE_LOG);
		insertAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		insertAfterInNaturalBlocks(WWBlocks.MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG);

		// LEAF LITTERS
		insertAfterInNaturalBlocks(Items.LEAF_LITTER, WWBlocks.SPRUCE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.SPRUCE_LEAF_LITTER, WWBlocks.BIRCH_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.BIRCH_LEAF_LITTER, WWBlocks.JUNGLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.JUNGLE_LEAF_LITTER, WWBlocks.ACACIA_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.ACACIA_LEAF_LITTER, WWBlocks.DARK_OAK_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.DARK_OAK_LEAF_LITTER, WWBlocks.MANGROVE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.MANGROVE_LEAF_LITTER, WWBlocks.BAOBAB_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.BAOBAB_LEAF_LITTER, WWBlocks.WILLOW_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.WILLOW_LEAF_LITTER, WWBlocks.CYPRESS_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.CYPRESS_LEAF_LITTER, WWBlocks.PALM_FROND_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.PALM_FROND_LITTER, WWBlocks.CHERRY_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.CHERRY_LEAF_LITTER, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, WWBlocks.RED_MAPLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.RED_MAPLE_LEAF_LITTER, WWBlocks.PALE_OAK_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWBlocks.PALE_OAK_LEAF_LITTER, WWBlocks.AZALEA_LEAF_LITTER);

		// SMALL FLOWERS
		insertAfterInNaturalBlocks(Items.DANDELION, WWBlocks.SEEDING_DANDELION);
		insertAfterInNaturalBlocks(Items.CORNFLOWER, WWBlocks.CARNATION);
		insertAfterInNaturalBlocks(WWBlocks.CARNATION, WWBlocks.MARIGOLD);
		insertAfterInNaturalBlocks(WWBlocks.MARIGOLD, WWBlocks.PASQUEFLOWER);
		insertBeforeInNaturalBlocks(Items.TORCHFLOWER, WWBlocks.RED_HIBISCUS);
		insertAfterInNaturalBlocks(WWBlocks.RED_HIBISCUS, WWBlocks.YELLOW_HIBISCUS);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_HIBISCUS, WWBlocks.WHITE_HIBISCUS);
		insertAfterInNaturalBlocks(WWBlocks.WHITE_HIBISCUS, WWBlocks.PINK_HIBISCUS);
		insertAfterInNaturalBlocks(WWBlocks.PINK_HIBISCUS, WWBlocks.PURPLE_HIBISCUS);
		insertAfterInNaturalBlocks(Items.WILDFLOWERS, WWBlocks.PHLOX);
		insertAfterInNaturalBlocks(WWBlocks.PHLOX, WWBlocks.LANTANAS);
		// TALL FLOWERS
		insertAfterInNaturalBlocks(Items.PEONY, WWBlocks.DATURA);
		insertAfterInNaturalBlocks(WWBlocks.DATURA, WWBlocks.MILKWEED);
		insertBeforeInIngredients(Items.INK_SAC, WWItems.MILKWEED_POD);
		insertAfterInNaturalBlocks(WWBlocks.MILKWEED, WWBlocks.CATTAIL);

		// PLANTS
		insertBeforeInNaturalBlocks(Items.GLOW_LICHEN, WWBlocks.POLLEN);
		insertAfterInNaturalBlocks(Items.CACTUS, WWItems.PRICKLY_PEAR);
		insertAfterInFoodAndDrinks(Items.SWEET_BERRIES, WWItems.PRICKLY_PEAR);
		insertAfterInFoodAndDrinks(WWItems.PRICKLY_PEAR, WWItems.PEELED_PRICKLY_PEAR);
		insertAfterInNaturalBlocks(WWItems.PRICKLY_PEAR, WWBlocks.TUMBLEWEED_PLANT);
		insertAfterInNaturalBlocks(WWBlocks.TUMBLEWEED_PLANT, WWBlocks.TUMBLEWEED);
		insertAfterInNaturalBlocks(WWBlocks.TUMBLEWEED, WWBlocks.SHRUB);
		insertBeforeInNaturalBlocks(Items.LILY_PAD, WWBlocks.BARNACLES);
		insertAfterInNaturalBlocks(WWBlocks.BARNACLES, WWItems.ALGAE);
		insertAfterInNaturalBlocks(WWItems.ALGAE, WWItems.PLANKTON);
		insertAfterInNaturalBlocks(Items.SEAGRASS, WWBlocks.SEA_WHIP);
		insertAfterInNaturalBlocks(WWBlocks.SEA_WHIP, WWBlocks.TUBE_WORMS);
		insertBeforeInNaturalBlocks(Items.SEA_PICKLE, WWBlocks.SEA_ANEMONE);
		insertAfterInNaturalBlocks(Items.LILY_PAD, WWItems.FLOWERING_LILY_PAD);

		insertAfterInNaturalBlocks(Items.FERN, WWBlocks.CLOVERS);
		insertAfterInNaturalBlocks(WWBlocks.CLOVERS, WWBlocks.FROZEN_SHORT_GRASS);
		insertAfterInNaturalBlocks(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_FERN);
		insertAfterInNaturalBlocks(Items.BUSH, WWBlocks.FROZEN_BUSH);
		insertAfterInNaturalBlocks(Items.LARGE_FERN, WWBlocks.FROZEN_TALL_GRASS);
		insertAfterInNaturalBlocks(WWBlocks.FROZEN_TALL_GRASS, WWBlocks.FROZEN_LARGE_FERN);

		insertBeforeInNaturalBlocks(Items.CRIMSON_ROOTS, WWBlocks.MYCELIUM_GROWTH);

		insertAfterInNaturalBlocks(Items.MOSS_CARPET, WWBlocks.AUBURN_MOSS_BLOCK);
		insertAfterInNaturalBlocks(WWBlocks.AUBURN_MOSS_BLOCK, WWBlocks.AUBURN_MOSS_CARPET);
		insertAfterInNaturalBlocks(WWBlocks.AUBURN_MOSS_CARPET, WWBlocks.AUBURN_CREEPING_MOSS);

		// SHELF FUNGI
		insertAfterInNaturalBlocks(Items.WARPED_FUNGUS, WWBlocks.BROWN_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWBlocks.BROWN_SHELF_FUNGI, WWBlocks.RED_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWBlocks.RED_SHELF_FUNGI, WWBlocks.CRIMSON_SHELF_FUNGI);
		insertAfterInNaturalBlocks(WWBlocks.CRIMSON_SHELF_FUNGI, WWBlocks.WARPED_SHELF_FUNGI);

		// PALE MUSHROOMS
		insertAfterInNaturalBlocks(Items.RED_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM_BLOCK);
		insertAfterInNaturalBlocks(Items.RED_MUSHROOM, WWBlocks.PALE_MUSHROOM);
		insertAfterInNaturalBlocks(WWBlocks.RED_SHELF_FUNGI, WWBlocks.PALE_SHELF_FUNGI);

		// SPONGE
		insertAfterInNaturalBlocks(Items.WET_SPONGE, WWBlocks.SPONGE_BUD);

		// EGGS
		insertBeforeInNaturalBlocks(Items.SNIFFER_EGG, WWBlocks.OSTRICH_EGG);
		insertAfterInNaturalBlocks(WWBlocks.OSTRICH_EGG, WWBlocks.PENGUIN_EGG);

		// MESOGLEA
		insertBeforeInNaturalBlocks(Items.SPONGE, WWBlocks.PEARLESCENT_BLUE_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.PEARLESCENT_BLUE_MESOGLEA, WWBlocks.PEARLESCENT_PURPLE_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA, WWBlocks.BLUE_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.BLUE_MESOGLEA, WWBlocks.PINK_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.PINK_MESOGLEA, WWBlocks.RED_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.RED_MESOGLEA, WWBlocks.YELLOW_MESOGLEA);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_MESOGLEA, WWBlocks.LIME_MESOGLEA);

		// NEMATOCYST
		insertBeforeInNaturalBlocks(Items.SPONGE, WWBlocks.PEARLESCENT_BLUE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.PEARLESCENT_BLUE_NEMATOCYST, WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST, WWBlocks.BLUE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.BLUE_NEMATOCYST, WWBlocks.PINK_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.PINK_NEMATOCYST, WWBlocks.RED_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.RED_NEMATOCYST, WWBlocks.YELLOW_NEMATOCYST);
		insertAfterInNaturalBlocks(WWBlocks.YELLOW_NEMATOCYST, WWBlocks.LIME_NEMATOCYST);

		// FROGLIGHT
		insertAfterInNaturalAndFunctionalBlocks(Items.PEARLESCENT_FROGLIGHT, WWBlocks.OCHRE_FROGLIGHT_GOOP);
		insertAfterInNaturalAndFunctionalBlocks(WWBlocks.OCHRE_FROGLIGHT_GOOP, WWBlocks.VERDANT_FROGLIGHT_GOOP);
		insertAfterInNaturalAndFunctionalBlocks(WWBlocks.VERDANT_FROGLIGHT_GOOP, WWBlocks.PEARLESCENT_FROGLIGHT_GOOP);

		// ICE
		insertAfterInNaturalBlocks(Items.ICE, WWBlocks.FRAGILE_ICE);
		insertAfterInNaturalBlocks(Items.BLUE_ICE, WWBlocks.ICICLE);

		// MUD BRICKS
		insertAfterInBuildingBlocks(Items.MUD_BRICKS, WWBlocks.CRACKED_MUD_BRICKS);
		insertAfterInBuildingBlocks(Items.MUD_BRICK_WALL, WWBlocks.CHISELED_MUD_BRICKS);
		insertAfterInBuildingBlocks(WWBlocks.CHISELED_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICKS);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_WALL);

		// SCULK
		insertInBuildingBlocks(Items.SCULK);
		insertAfterInBuildingBlocks(Items.SCULK, WWBlocks.SCULK_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.SCULK_STAIRS, WWBlocks.SCULK_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.SCULK_SLAB, WWBlocks.SCULK_WALL);
		insertAfterInBuildingBlocks(WWBlocks.SCULK_WALL, WWBlocks.OSSEOUS_SCULK);
		insertAfterInNaturalBlocks(Items.SCULK, WWBlocks.OSSEOUS_SCULK);
		insertAfterInNaturalBlocks(Items.SCULK_SENSOR, WWBlocks.HANGING_TENDRIL);

		// SCORCHED SAND
		insertAfterInNaturalBlocks(Items.SAND, WWBlocks.SCORCHED_SAND);
		insertAfterInNaturalBlocks(Items.RED_SAND, WWBlocks.SCORCHED_RED_SAND);

		// STORAGE
		insertAfterInFunctionalBlocks(Items.CHEST, WWBlocks.STONE_CHEST);
		insertAfterInFunctionalBlocks(Items.SOUL_LANTERN, WWItems.DISPLAY_LANTERN);

		// FUNCTIONAL BLOCK ENTITIES
		insertBeforeInNaturalBlocks(Items.BEE_NEST, WWBlocks.TERMITE_MOUND);
		insertBeforeInRedstoneBlocks(Items.SCULK_SENSOR, WWBlocks.GEYSER);

		// MISC
		insertAfterInFunctionalBlocks(Items.TINTED_GLASS, WWItems.ECHO_GLASS);
		insertBeforeInBuildingBlocks(Items.GLASS, WWBlocks.NULL_BLOCK);

		// FIREFLY
		insertBeforeInSpawnEggs(Items.FOX_SPAWN_EGG, WWItems.FIREFLY_SPAWN_EGG);
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			final ItemStack stack = new ItemStack(WWItems.FIREFLY_BOTTLE);
			stack.setCount(1);
			stack.set(
				WWDataComponents.FIREFLY_COLOR,
				entries.getContext()
					.holders()
					.lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR)
					.getOrThrow(FireflyColors.DEFAULT)
			);
			entries.insertAfter(Items.MILK_BUCKET, ImmutableList.of(stack), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		});

		// BUTTERFLY
		insertAfterInSpawnEggs(Items.BEE_SPAWN_EGG, WWItems.BUTTERFLY_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWItems.FIREFLY_BOTTLE, WWItems.BUTTERFLY_BOTTLE);

		// JELLYFISH
		insertAfterInSpawnEggs(Items.GLOW_SQUID_SPAWN_EGG, WWItems.JELLYFISH_SPAWN_EGG);
		insertAfterInToolsAndUtilities(Items.AXOLOTL_BUCKET, WWItems.JELLYFISH_BUCKET);

		// CRAB
		insertAfterInSpawnEggs(Items.COD_SPAWN_EGG, WWItems.CRAB_SPAWN_EGG);
		insertAfterInToolsAndUtilities(WWItems.JELLYFISH_BUCKET, WWItems.CRAB_BUCKET);
		insertBeforeInFoodAndDrinks(Items.COD, WWItems.CRAB_CLAW);
		insertAfterInFoodAndDrinks(WWItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW);

		// OSTRICH
		insertAfterInSpawnEggs(Items.MULE_SPAWN_EGG, WWItems.OSTRICH_SPAWN_EGG);

		// ZOMBIE OSTRICH
		insertAfterInSpawnEggs(Items.ZOMBIE_NAUTILUS_SPAWN_EGG, WWItems.ZOMBIE_OSTRICH_SPAWN_EGG);

		// SCORCHED
		insertBeforeInSpawnEggs(Items.SPIDER_SPAWN_EGG, WWItems.SCORCHED_SPAWN_EGG);
		insertAfterInIngredients(Items.SPIDER_EYE, WWItems.SCORCHED_EYE);
		insertAfterInIngredients(Items.FERMENTED_SPIDER_EYE, WWItems.FERMENTED_SCORCHED_EYE);

		// MOOBLOOM
		insertBeforeInSpawnEggs(Items.MOOSHROOM_SPAWN_EGG, WWItems.MOOBLOOM_SPAWN_EGG);

		// PENGUIN
		insertAfterInSpawnEggs(Items.PANDA_SPAWN_EGG, WWItems.PENGUIN_SPAWN_EGG);

		// GABBRO
		insertAfterInNaturalAndFunctionalBlocks(Items.MAGMA_BLOCK, WWBlocks.GEYSER);
		insertBeforeInNaturalBlocks(WWBlocks.GEYSER, WWBlocks.GABBRO);

		insertBeforeInBuildingBlocks(Items.BRICKS, WWBlocks.GABBRO);
		// TT
		insertAfterInBuildingBlocks(WWBlocks.GABBRO, WWBlocks.GABBRO_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_STAIRS, WWBlocks.GABBRO_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_SLAB, WWBlocks.GABBRO_WALL);
		// BACK TO WW
		insertAfterInBuildingBlocks(FrozenBools.HAS_TRAILIERTALES ? WWBlocks.GABBRO_WALL : WWBlocks.GABBRO, WWBlocks.POLISHED_GABBRO);
		insertAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_WALL);
		insertAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_WALL, WWBlocks.GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_BRICKS, WWBlocks.CRACKED_GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWBlocks.CRACKED_GABBRO_BRICKS, WWBlocks.GABBRO_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_WALL);
		insertAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_WALL, WWBlocks.CHISELED_GABBRO_BRICKS);
		// TT
		insertAfterInBuildingBlocks(WWBlocks.CHISELED_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_WALL);
	}

	private static void insertInBuildingBlocks(ItemLike item) {
		FrozenCreativeTabs.insert(item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertBeforeInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void insertBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInBuildingAndNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void insertAfterInNaturalAndFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void insertBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void insertInToolsAndUtilities(ItemLike item) {
		FrozenCreativeTabs.insert(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void insertBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertAfterInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void insertBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void insertAfterInCombat(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void insertBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void insertAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.insertAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	@SafeVarargs
	private static void addInstrumentBefore(
		Item comparedItem,
		Item instrument,
		TagKey<Instrument> tagKey,
		ResourceKey<CreativeModeTab>... tabs
	) {
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}
}
