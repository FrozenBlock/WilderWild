/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.frozenblock.lib.item.api.ItemBlockStateTagUtils;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColors;
import net.frozenblock.wilderwild.tag.WWInstrumentTags;
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
import org.jetbrains.annotations.NotNull;

public class WWCreativeInventorySorting {

	public static void init() {
		// BAOBAB (BUILDING BLOCKS)
		addAfterInBuildingBlocks(Items.MANGROVE_BUTTON, WWBlocks.BAOBAB_LOG);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_WOOD, WWBlocks.STRIPPED_BAOBAB_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_BAOBAB_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_WOOD, WWBlocks.BAOBAB_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_PLANKS, WWBlocks.BAOBAB_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_STAIRS, WWBlocks.BAOBAB_SLAB);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_SLAB, WWBlocks.BAOBAB_FENCE);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE, WWBlocks.BAOBAB_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_FENCE_GATE, WWBlocks.BAOBAB_DOOR);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_DOOR, WWBlocks.BAOBAB_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_TRAPDOOR, WWBlocks.BAOBAB_PRESSURE_PLATE);
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_PRESSURE_PLATE, WWBlocks.BAOBAB_BUTTON);
		// BAOBAB (NATURAL BLOCKS)
		addAfterInNaturalBlocks(Items.MANGROVE_LOG, WWBlocks.BAOBAB_LOG);
		addAfterInNaturalBlocks(Items.MANGROVE_LEAVES, WWBlocks.BAOBAB_LEAVES);
		// BAOBAB (FUNCTIONAL BLOCKS)
		addAfterInFunctionalBlocks(Items.MANGROVE_HANGING_SIGN, WWItems.BAOBAB_SIGN);
		addAfterInFunctionalBlocks(WWItems.BAOBAB_SIGN, WWItems.BAOBAB_HANGING_SIGN);
		// BAOBAB (TOOLS AND UTILITIES)
		addAfterInToolsAndUtilities(Items.MANGROVE_CHEST_BOAT, WWItems.BAOBAB_BOAT);
		addAfterInToolsAndUtilities(WWItems.BAOBAB_BOAT, WWItems.BAOBAB_CHEST_BOAT);
		// BAOBAB NUT
		addAfterInFoodAndDrinks(Items.ENCHANTED_GOLDEN_APPLE, WWItems.BAOBAB_NUT);
		addAfterInNaturalBlocks(Items.MANGROVE_PROPAGULE, WWItems.BAOBAB_NUT);

		// WILLOW (BUILDING BLOCKS)
		addAfterInBuildingBlocks(WWBlocks.BAOBAB_BUTTON, WWBlocks.WILLOW_LOG);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_LOG, WWBlocks.WILLOW_WOOD);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_WOOD, WWBlocks.STRIPPED_WILLOW_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_WILLOW_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_WOOD, WWBlocks.WILLOW_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_PLANKS, WWBlocks.WILLOW_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_STAIRS, WWBlocks.WILLOW_SLAB);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_SLAB, WWBlocks.WILLOW_FENCE);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_FENCE, WWBlocks.WILLOW_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_FENCE_GATE, WWBlocks.WILLOW_DOOR);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_DOOR, WWBlocks.WILLOW_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_TRAPDOOR, WWBlocks.WILLOW_PRESSURE_PLATE);
		addAfterInBuildingBlocks(WWBlocks.WILLOW_PRESSURE_PLATE, WWBlocks.WILLOW_BUTTON);
		// WILLOW (NATURAL_BLOCKS)
		addAfterInNaturalBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.WILLOW_LOG);
		addAfterInNaturalBlocks(WWBlocks.BAOBAB_LEAVES, WWBlocks.WILLOW_LEAVES);
		addAfterInNaturalBlocks(WWItems.BAOBAB_NUT, WWBlocks.WILLOW_SAPLING);
		// WILLOW (FUNCTIONAL BLOCKS)
		addAfterInFunctionalBlocks(WWItems.BAOBAB_HANGING_SIGN, WWItems.WILLOW_SIGN);
		addAfterInFunctionalBlocks(WWItems.WILLOW_SIGN, WWItems.WILLOW_HANGING_SIGN);
		// WILLOW (TOOLS AND UTILITIES)
		addAfterInToolsAndUtilities(WWItems.BAOBAB_CHEST_BOAT, WWItems.WILLOW_BOAT);
		addAfterInToolsAndUtilities(WWItems.WILLOW_BOAT, WWItems.WILLOW_CHEST_BOAT);

		// CYPRESS (BUILDING BLOCKS)
		addAfterInBuildingBlocks(WWBlocks.WILLOW_BUTTON, WWBlocks.CYPRESS_LOG);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.CYPRESS_WOOD);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_WOOD, WWBlocks.STRIPPED_CYPRESS_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_CYPRESS_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_WOOD, WWBlocks.CYPRESS_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_PLANKS, WWBlocks.CYPRESS_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_STAIRS, WWBlocks.CYPRESS_SLAB);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_SLAB, WWBlocks.CYPRESS_FENCE);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_FENCE, WWBlocks.CYPRESS_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_FENCE_GATE, WWBlocks.CYPRESS_DOOR);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_DOOR, WWBlocks.CYPRESS_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_TRAPDOOR, WWBlocks.CYPRESS_PRESSURE_PLATE);
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_PRESSURE_PLATE, WWBlocks.CYPRESS_BUTTON);
		// CYPRESS (NATURAL_BLOCKS)
		addAfterInNaturalBlocks(WWBlocks.WILLOW_LOG, WWBlocks.CYPRESS_LOG);
		addAfterInNaturalBlocks(WWBlocks.WILLOW_LEAVES, WWBlocks.CYPRESS_LEAVES);
		addAfterInNaturalBlocks(WWBlocks.WILLOW_SAPLING, WWBlocks.CYPRESS_SAPLING);
		// CYPRESS (FUNCTIONAL BLOCKS)
		addAfterInFunctionalBlocks(WWItems.WILLOW_HANGING_SIGN, WWItems.CYPRESS_SIGN);
		addAfterInFunctionalBlocks(WWItems.CYPRESS_SIGN, WWItems.CYPRESS_HANGING_SIGN);
		// CYPRESS (TOOLS AND UTILITIES)
		addAfterInToolsAndUtilities(WWItems.WILLOW_CHEST_BOAT, WWItems.CYPRESS_BOAT);
		addAfterInToolsAndUtilities(WWItems.CYPRESS_BOAT, WWItems.CYPRESS_CHEST_BOAT);

		// PALM (BUILDING BLOCKS)
		addAfterInBuildingBlocks(WWBlocks.CYPRESS_BUTTON, WWBlocks.PALM_LOG);
		addAfterInBuildingBlocks(WWBlocks.PALM_LOG, WWBlocks.PALM_WOOD);
		addAfterInBuildingBlocks(WWBlocks.PALM_WOOD, WWBlocks.STRIPPED_PALM_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_PALM_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_WOOD, WWBlocks.PALM_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.PALM_PLANKS, WWBlocks.PALM_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.PALM_STAIRS, WWBlocks.PALM_SLAB);
		addAfterInBuildingBlocks(WWBlocks.PALM_SLAB, WWBlocks.PALM_FENCE);
		addAfterInBuildingBlocks(WWBlocks.PALM_FENCE, WWBlocks.PALM_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.PALM_FENCE_GATE, WWBlocks.PALM_DOOR);
		addAfterInBuildingBlocks(WWBlocks.PALM_DOOR, WWBlocks.PALM_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.PALM_TRAPDOOR, WWBlocks.PALM_PRESSURE_PLATE);
		addAfterInBuildingBlocks(WWBlocks.PALM_PRESSURE_PLATE, WWBlocks.PALM_BUTTON);
		// PALM (NATURAL BLOCKS)
		addAfterInNaturalBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.PALM_LOG);
		addAfterInNaturalBlocks(WWBlocks.CYPRESS_LEAVES, WWBlocks.PALM_FRONDS);
		// PALM (FUNCTIONAL BLOCKS)
		addAfterInFunctionalBlocks(WWItems.CYPRESS_HANGING_SIGN, WWItems.PALM_SIGN);
		addAfterInFunctionalBlocks(WWItems.PALM_SIGN, WWItems.PALM_HANGING_SIGN);
		// PALM (TOOLS AND UTILITIES)
		addAfterInToolsAndUtilities(WWItems.CYPRESS_CHEST_BOAT, WWItems.PALM_BOAT);
		addAfterInToolsAndUtilities(WWItems.PALM_BOAT, WWItems.PALM_CHEST_BOAT);
		// COCONUT
		addAfterInCombat(Items.EGG, WWItems.COCONUT);
		addAfterInNaturalBlocks(WWBlocks.CYPRESS_SAPLING, WWItems.COCONUT);
		addAfterInFoodAndDrinks(WWItems.BAOBAB_NUT, WWItems.SPLIT_COCONUT);

		// MAPLE (BUILDING BLOCKS)
		addAfterInBuildingBlocks(Items.CHERRY_BUTTON, WWBlocks.MAPLE_LOG);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_LOG, WWBlocks.MAPLE_WOOD);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_WOOD, WWBlocks.STRIPPED_MAPLE_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_MAPLE_WOOD);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_WOOD, WWBlocks.MAPLE_PLANKS);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_PLANKS, WWBlocks.MAPLE_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_STAIRS, WWBlocks.MAPLE_SLAB);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_SLAB, WWBlocks.MAPLE_FENCE);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_FENCE, WWBlocks.MAPLE_FENCE_GATE);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_FENCE_GATE, WWBlocks.MAPLE_DOOR);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_DOOR, WWBlocks.MAPLE_TRAPDOOR);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_TRAPDOOR, WWBlocks.MAPLE_PRESSURE_PLATE);
		addAfterInBuildingBlocks(WWBlocks.MAPLE_PRESSURE_PLATE, WWBlocks.MAPLE_BUTTON);
		// MAPLE (NATURAL BLOCKS)
		addAfterInNaturalBlocks(Items.CHERRY_LOG, WWBlocks.MAPLE_LOG);
		addAfterInNaturalBlocks(Items.CHERRY_SAPLING, WWBlocks.MAPLE_SAPLING);
		addAfterInNaturalBlocks(Blocks.CHERRY_LEAVES, WWBlocks.YELLOW_MAPLE_LEAVES);
		addAfterInNaturalBlocks(WWBlocks.YELLOW_MAPLE_LEAVES, WWBlocks.YELLOW_MAPLE_LEAF_LITTER);
		addAfterInNaturalBlocks(WWBlocks.YELLOW_MAPLE_LEAF_LITTER, WWBlocks.ORANGE_MAPLE_LEAVES);
		addAfterInNaturalBlocks(WWBlocks.ORANGE_MAPLE_LEAVES, WWBlocks.ORANGE_MAPLE_LEAF_LITTER);
		addAfterInNaturalBlocks(WWBlocks.ORANGE_MAPLE_LEAF_LITTER, WWBlocks.RED_MAPLE_LEAVES);
		addAfterInNaturalBlocks(WWBlocks.RED_MAPLE_LEAVES, WWBlocks.RED_MAPLE_LEAF_LITTER);
		// MAPLE (FUNCTIONAL BLOCKS)
		addAfterInFunctionalBlocks(Blocks.CHERRY_HANGING_SIGN, WWItems.MAPLE_SIGN);
		addAfterInFunctionalBlocks(WWItems.MAPLE_SIGN, WWItems.MAPLE_HANGING_SIGN);
		// MAPLE (TOOLS AND UTILITIES)
		addAfterInToolsAndUtilities(Items.CHERRY_CHEST_BOAT, WWItems.MAPLE_BOAT);
		addAfterInToolsAndUtilities(WWItems.MAPLE_BOAT, WWItems.MAPLE_CHEST_BOAT);

		// HOLLOWED LOGS
		addAfterInBuildingAndNaturalBlocks(Items.OAK_LOG, WWBlocks.HOLLOWED_OAK_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_OAK_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.SPRUCE_LOG, WWBlocks.HOLLOWED_SPRUCE_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_SPRUCE_LOG, WWBlocks.STRIPPED_HOLLOWED_SPRUCE_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.BIRCH_LOG, WWBlocks.HOLLOWED_BIRCH_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_BIRCH_LOG, WWBlocks.STRIPPED_HOLLOWED_BIRCH_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.JUNGLE_LOG, WWBlocks.HOLLOWED_JUNGLE_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_JUNGLE_LOG, WWBlocks.STRIPPED_HOLLOWED_JUNGLE_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.ACACIA_LOG, WWBlocks.HOLLOWED_ACACIA_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_ACACIA_LOG, WWBlocks.STRIPPED_HOLLOWED_ACACIA_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.DARK_OAK_LOG, WWBlocks.HOLLOWED_DARK_OAK_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_DARK_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.CRIMSON_STEM, WWBlocks.HOLLOWED_CRIMSON_STEM);
		addAfterInBuildingBlocks(Items.STRIPPED_CRIMSON_STEM, WWBlocks.STRIPPED_HOLLOWED_CRIMSON_STEM);

		addAfterInBuildingAndNaturalBlocks(Items.WARPED_STEM, WWBlocks.HOLLOWED_WARPED_STEM);
		addAfterInBuildingBlocks(Items.STRIPPED_WARPED_STEM, WWBlocks.STRIPPED_HOLLOWED_WARPED_STEM);

		addAfterInBuildingAndNaturalBlocks(Items.MANGROVE_LOG, WWBlocks.HOLLOWED_MANGROVE_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_MANGROVE_LOG, WWBlocks.STRIPPED_HOLLOWED_MANGROVE_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.CHERRY_LOG, WWBlocks.HOLLOWED_CHERRY_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_CHERRY_LOG, WWBlocks.STRIPPED_HOLLOWED_CHERRY_LOG);

		addAfterInBuildingAndNaturalBlocks(Items.PALE_OAK_LOG, WWBlocks.HOLLOWED_PALE_OAK_LOG);
		addAfterInBuildingBlocks(Items.STRIPPED_PALE_OAK_LOG, WWBlocks.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		addBeforeInBuildingBlocks(WWBlocks.BAOBAB_WOOD, WWBlocks.HOLLOWED_BAOBAB_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_BAOBAB_LOG, WWBlocks.STRIPPED_HOLLOWED_BAOBAB_LOG);
		addAfterInNaturalBlocks(WWBlocks.BAOBAB_LOG, WWBlocks.HOLLOWED_BAOBAB_LOG);

		addBeforeInBuildingBlocks(WWBlocks.WILLOW_WOOD, WWBlocks.HOLLOWED_WILLOW_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_WILLOW_LOG, WWBlocks.STRIPPED_HOLLOWED_WILLOW_LOG);
		addAfterInNaturalBlocks(WWBlocks.WILLOW_LOG, WWBlocks.HOLLOWED_WILLOW_LOG);

		addBeforeInBuildingBlocks(WWBlocks.CYPRESS_WOOD, WWBlocks.HOLLOWED_CYPRESS_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_CYPRESS_LOG, WWBlocks.STRIPPED_HOLLOWED_CYPRESS_LOG);
		addAfterInNaturalBlocks(WWBlocks.CYPRESS_LOG, WWBlocks.HOLLOWED_CYPRESS_LOG);

		addBeforeInBuildingBlocks(WWBlocks.PALM_WOOD, WWBlocks.HOLLOWED_PALM_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_PALM_LOG, WWBlocks.STRIPPED_HOLLOWED_PALM_LOG);
		addAfterInNaturalBlocks(WWBlocks.PALM_LOG, WWBlocks.HOLLOWED_PALM_LOG);

		addBeforeInBuildingBlocks(WWBlocks.MAPLE_WOOD, WWBlocks.HOLLOWED_MAPLE_LOG);
		addAfterInBuildingBlocks(WWBlocks.STRIPPED_MAPLE_LOG, WWBlocks.STRIPPED_HOLLOWED_MAPLE_LOG);
		addAfterInNaturalBlocks(WWBlocks.MAPLE_LOG, WWBlocks.HOLLOWED_MAPLE_LOG);

		// SMALL FLOWERS
		addAfterInNaturalBlocks(Items.DANDELION, WWBlocks.SEEDING_DANDELION);
		addAfterInNaturalBlocks(Items.CORNFLOWER, WWBlocks.CARNATION);
		addAfterInNaturalBlocks(WWBlocks.CARNATION, WWBlocks.MARIGOLD);
		addAfterInNaturalBlocks(WWBlocks.MARIGOLD, WWBlocks.PASQUEFLOWER);
		addBeforeInNaturalBlocks(Items.PINK_TULIP, WWBlocks.RED_HIBISCUS);
		addBeforeInNaturalBlocks(WWBlocks.RED_HIBISCUS, WWBlocks.YELLOW_HIBISCUS);
		addAfterInNaturalBlocks(WWBlocks.YELLOW_HIBISCUS, WWBlocks.WHITE_HIBISCUS);
		addAfterInNaturalBlocks(WWBlocks.WHITE_HIBISCUS, WWBlocks.PINK_HIBISCUS);
		addAfterInNaturalBlocks(WWBlocks.PINK_HIBISCUS, WWBlocks.PURPLE_HIBISCUS);
		addAfterInNaturalBlocks(Items.WILDFLOWERS, WWBlocks.PHLOX);
		addAfterInNaturalBlocks(WWBlocks.PHLOX, WWBlocks.LANTANAS);
		// TALL FLOWERS
		addAfterInNaturalBlocks(Items.PEONY, WWBlocks.DATURA);
		addAfterInNaturalBlocks(WWBlocks.DATURA, WWBlocks.MILKWEED);
		addBeforeInIngredients(Items.INK_SAC, WWItems.MILKWEED_POD);
		addAfterInNaturalBlocks(WWBlocks.MILKWEED, WWBlocks.CATTAIL);

		// PLANTS
		addAfterInNaturalBlocks(Items.GLOW_LICHEN, WWBlocks.POLLEN);
		addAfterInNaturalBlocks(Items.CACTUS, WWItems.PRICKLY_PEAR);
		addAfterInFoodAndDrinks(Items.SWEET_BERRIES, WWItems.PRICKLY_PEAR);
		addAfterInFoodAndDrinks(WWItems.PRICKLY_PEAR, WWItems.PEELED_PRICKLY_PEAR);
		addAfterInNaturalBlocks(WWItems.PRICKLY_PEAR, WWBlocks.TUMBLEWEED_PLANT);
		addAfterInNaturalBlocks(WWBlocks.TUMBLEWEED_PLANT, WWBlocks.TUMBLEWEED);
		addAfterInNaturalBlocks(WWBlocks.TUMBLEWEED, WWBlocks.SHRUB);
		addBeforeInNaturalBlocks(Items.LILY_PAD, WWItems.ALGAE);
		addAfterInNaturalBlocks(Items.LILY_PAD, WWItems.FLOWERING_LILY_PAD);

		addAfterInNaturalBlocks(Items.FERN, WWBlocks.FROZEN_SHORT_GRASS);
		addAfterInNaturalBlocks(WWBlocks.FROZEN_SHORT_GRASS, WWBlocks.FROZEN_FERN);
		addAfterInNaturalBlocks(WWBlocks.FROZEN_FERN, WWBlocks.FROZEN_BUSH);
		addAfterInNaturalBlocks(Items.LARGE_FERN, WWBlocks.FROZEN_TALL_GRASS);
		addAfterInNaturalBlocks(WWBlocks.FROZEN_TALL_GRASS, WWBlocks.FROZEN_LARGE_FERN);

		// MYCELIUM
		addAfterInNaturalBlocks(Items.FERN, WWBlocks.CLOVERS);
		addBeforeInNaturalBlocks(Items.DEAD_BUSH, WWBlocks.MYCELIUM_GROWTH);

		// SHELF FUNGI
		addAfterInNaturalBlocks(Items.RED_MUSHROOM, WWBlocks.BROWN_SHELF_FUNGI);
		addAfterInNaturalBlocks(WWBlocks.BROWN_SHELF_FUNGI, WWBlocks.RED_SHELF_FUNGI);
		addAfterInNaturalBlocks(Items.WARPED_FUNGUS, WWBlocks.CRIMSON_SHELF_FUNGI);
		addAfterInNaturalBlocks(WWBlocks.CRIMSON_SHELF_FUNGI, WWBlocks.WARPED_SHELF_FUNGI);

		// PALE MUSHROOMS
		addAfterInNaturalBlocks(Items.RED_MUSHROOM_BLOCK, WWBlocks.PALE_MUSHROOM_BLOCK);
		addAfterInNaturalBlocks(Items.RED_MUSHROOM, WWBlocks.PALE_MUSHROOM);
		addAfterInNaturalBlocks(WWBlocks.RED_SHELF_FUNGI, WWBlocks.PALE_SHELF_FUNGI);

		// SPONGE
		addAfterInNaturalBlocks(Items.WET_SPONGE, WWBlocks.SPONGE_BUD);

		// EGGS
		addBeforeInNaturalBlocks(Items.SNIFFER_EGG, WWBlocks.OSTRICH_EGG);
		addAfterInNaturalBlocks(WWBlocks.OSTRICH_EGG, WWBlocks.PENGUIN_EGG);

		// MESOGLEA
		addBeforeInNaturalBlocks(Items.SPONGE, WWBlocks.BLUE_PEARLESCENT_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.BLUE_PEARLESCENT_MESOGLEA, WWBlocks.PURPLE_PEARLESCENT_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA, WWBlocks.BLUE_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.BLUE_MESOGLEA, WWBlocks.PINK_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.PINK_MESOGLEA, WWBlocks.RED_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.RED_MESOGLEA, WWBlocks.YELLOW_MESOGLEA);
		addAfterInNaturalBlocks(WWBlocks.YELLOW_MESOGLEA, WWBlocks.LIME_MESOGLEA);

		// NEMATOCYST
		addBeforeInNaturalBlocks(Items.SPONGE, WWBlocks.BLUE_PEARLESCENT_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.BLUE_PEARLESCENT_NEMATOCYST, WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST, WWBlocks.BLUE_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.BLUE_NEMATOCYST, WWBlocks.PINK_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.PINK_NEMATOCYST, WWBlocks.RED_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.RED_NEMATOCYST, WWBlocks.YELLOW_NEMATOCYST);
		addAfterInNaturalBlocks(WWBlocks.YELLOW_NEMATOCYST, WWBlocks.LIME_NEMATOCYST);

		// MUD BRICKS
		addAfterInBuildingBlocks(Items.MUD_BRICKS, WWBlocks.CRACKED_MUD_BRICKS);
		addAfterInBuildingBlocks(Items.MUD_BRICK_WALL, WWBlocks.CHISELED_MUD_BRICKS);
		addAfterInBuildingBlocks(WWBlocks.CHISELED_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICKS);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICKS, WWBlocks.MOSSY_MUD_BRICK_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICK_STAIRS, WWBlocks.MOSSY_MUD_BRICK_SLAB);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_MUD_BRICK_SLAB, WWBlocks.MOSSY_MUD_BRICK_WALL);

		// SCULK
		addAfterInBuildingBlocks(Items.DEEPSLATE_TILE_WALL, WWBlocks.OSSEOUS_SCULK);
		addAfterInBuildingBlocks(WWBlocks.OSSEOUS_SCULK, WWBlocks.SCULK_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.SCULK_STAIRS, WWBlocks.SCULK_SLAB);
		addAfterInBuildingBlocks(WWBlocks.SCULK_SLAB, WWBlocks.SCULK_WALL);
		addAfterInNaturalBlocks(Items.SCULK, WWBlocks.OSSEOUS_SCULK);
		addAfterInNaturalBlocks(Items.SCULK_SENSOR, WWBlocks.HANGING_TENDRIL);

		// SCORCHED SAND
		addAfterInNaturalBlocks(Items.SAND, WWBlocks.SCORCHED_SAND);
		addAfterInNaturalBlocks(Items.RED_SAND, WWBlocks.SCORCHED_RED_SAND);

		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
			var second = new ItemStack(WWBlocks.SCORCHED_SAND);
			ItemBlockStateTagUtils.setProperty(second, WWBlockStateProperties.CRACKED, true);
			entries.addAfter(WWBlocks.SCORCHED_SAND, second);

			var secondRed = new ItemStack(WWBlocks.SCORCHED_RED_SAND);
			ItemBlockStateTagUtils.setProperty(secondRed, WWBlockStateProperties.CRACKED, true);
			entries.addAfter(WWBlocks.SCORCHED_RED_SAND, secondRed);
		});

		// STORAGE
		addAfterInFunctionalBlocks(Items.CHEST, WWBlocks.STONE_CHEST);
		addAfterInFunctionalBlocks(Items.SOUL_LANTERN, WWItems.DISPLAY_LANTERN);

		// FUNCTIONAL BLOCK ENTITIES
		addBeforeInNaturalBlocks(Items.BEE_NEST, WWBlocks.TERMITE_MOUND);
		addBeforeInRedstoneBlocks(Items.SCULK_SENSOR, WWBlocks.GEYSER);

		// MISC
		addAfterInFunctionalBlocks(Items.TINTED_GLASS, WWItems.ECHO_GLASS);
		addBeforeInBuildingBlocks(Items.GLASS, WWBlocks.NULL_BLOCK);

		// FIREFLY
		addAfterInSpawnEggs(Items.EVOKER_SPAWN_EGG, WWItems.FIREFLY_SPAWN_EGG);
		ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
			var stack = new ItemStack(WWItems.FIREFLY_BOTTLE);
			stack.setCount(1);
			stack.set(
				WWDataComponents.FIREFLY_COLOR,
				entries.getContext().holders().lookupOrThrow(WilderWildRegistries.FIREFLY_COLOR).getOrThrow(FireflyColors.DEFAULT)
			);
			entries.accept(stack);
		});

		// BUTTERFLY
		addAfterInSpawnEggs(Items.BREEZE_SPAWN_EGG, WWItems.BUTTERFLY_SPAWN_EGG);
		addInToolsAndUtilities(WWItems.BUTTERFLY_BOTTLE);

		// JELLYFISH
		addAfterInSpawnEggs(Items.HUSK_SPAWN_EGG, WWItems.JELLYFISH_SPAWN_EGG);
		addAfterInToolsAndUtilities(Items.AXOLOTL_BUCKET, WWItems.JELLYFISH_BUCKET);

		// CRAB
		addBeforeInSpawnEggs(Items.CREEPER_SPAWN_EGG, WWItems.CRAB_SPAWN_EGG);
		addAfterInToolsAndUtilities(WWItems.JELLYFISH_BUCKET, WWItems.CRAB_BUCKET);
		addBeforeInFoodAndDrinks(Items.COD, WWItems.CRAB_CLAW);
		addAfterInFoodAndDrinks(WWItems.CRAB_CLAW, WWItems.COOKED_CRAB_CLAW);

		// OSTRICH
		addAfterInSpawnEggs(Items.OCELOT_SPAWN_EGG, WWItems.OSTRICH_SPAWN_EGG);

		// SCORCHED
		addAfterInSpawnEggs(Items.SALMON_SPAWN_EGG, WWItems.SCORCHED_SPAWN_EGG);
		addAfterInIngredients(Items.SPIDER_EYE, WWItems.SCORCHED_EYE);
		addAfterInIngredients(Items.FERMENTED_SPIDER_EYE, WWItems.FERMENTED_SCORCHED_EYE);

		// MOOBLOOM
		addBeforeInSpawnEggs(Items.MOOSHROOM_SPAWN_EGG, WWItems.MOOBLOOM_SPAWN_EGG);

		// PENGUIN
		addBeforeInSpawnEggs(Items.PHANTOM_SPAWN_EGG, WWItems.PENGUIN_SPAWN_EGG);

		// HORNS
		addInstrumentBefore(Items.MUSIC_DISC_13, WWItems.COPPER_HORN, WWInstrumentTags.COPPER_HORNS, CreativeModeTabs.TOOLS_AND_UTILITIES);

		// GABBRO
		addAfterInNaturalAndFunctionalBlocks(Items.MAGMA_BLOCK, WWBlocks.GEYSER);
		addBeforeInNaturalBlocks(WWBlocks.GEYSER, WWBlocks.GABBRO);

		addBeforeInBuildingBlocks(Items.BRICKS, WWBlocks.GABBRO);
		addAfterInBuildingBlocks(WWBlocks.GABBRO, WWBlocks.GEYSER);
		// TT
		addAfterInBuildingBlocks(WWBlocks.GEYSER, WWBlocks.GABBRO_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_STAIRS, WWBlocks.GABBRO_SLAB);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_SLAB, WWBlocks.GABBRO_WALL);
		// BACK TO WW
		addAfterInBuildingBlocks(FrozenBools.HAS_TRAILIERTALES ? WWBlocks.GABBRO_WALL : WWBlocks.GEYSER, WWBlocks.POLISHED_GABBRO);
		addAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO, WWBlocks.POLISHED_GABBRO_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_STAIRS, WWBlocks.POLISHED_GABBRO_SLAB);
		addAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_SLAB, WWBlocks.POLISHED_GABBRO_WALL);
		addAfterInBuildingBlocks(WWBlocks.POLISHED_GABBRO_WALL, WWBlocks.GABBRO_BRICKS);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_BRICKS, WWBlocks.CRACKED_GABBRO_BRICKS);
		addAfterInBuildingBlocks(WWBlocks.CRACKED_GABBRO_BRICKS, WWBlocks.GABBRO_BRICK_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_STAIRS, WWBlocks.GABBRO_BRICK_SLAB);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_SLAB, WWBlocks.GABBRO_BRICK_WALL);
		addAfterInBuildingBlocks(WWBlocks.GABBRO_BRICK_WALL, WWBlocks.CHISELED_GABBRO_BRICKS);
		// TT
		addAfterInBuildingBlocks(WWBlocks.CHISELED_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICKS);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICKS, WWBlocks.MOSSY_GABBRO_BRICK_STAIRS);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICK_STAIRS, WWBlocks.MOSSY_GABBRO_BRICK_SLAB);
		addAfterInBuildingBlocks(WWBlocks.MOSSY_GABBRO_BRICK_SLAB, WWBlocks.MOSSY_GABBRO_BRICK_WALL);
	}

	private static void addBeforeInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void addAfterInBuildingBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS);
	}

	private static void addBeforeInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addAfterInNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addAfterInBuildingAndNaturalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.BUILDING_BLOCKS, CreativeModeTabs.NATURAL_BLOCKS);
	}

	private static void addAfterInNaturalAndFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.NATURAL_BLOCKS, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void addAfterInFunctionalBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.FUNCTIONAL_BLOCKS);
	}

	private static void addBeforeInRedstoneBlocks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.REDSTONE_BLOCKS);
	}

	private static void addInToolsAndUtilities(ItemLike item) {
		FrozenCreativeTabs.add(item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void addAfterInToolsAndUtilities(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.TOOLS_AND_UTILITIES);
	}

	private static void addBeforeInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}

	private static void addAfterInIngredients(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.INGREDIENTS);
	}


	private static void addBeforeInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void addAfterInFoodAndDrinks(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.FOOD_AND_DRINKS);
	}

	private static void addAfterInCombat(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.COMBAT);
	}

	private static void addBeforeInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addBefore(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	private static void addAfterInSpawnEggs(ItemLike comparedItem, ItemLike item) {
		FrozenCreativeTabs.addAfter(comparedItem, item, CreativeModeTabs.SPAWN_EGGS);
	}

	@SafeVarargs
	private static void addInstrumentBefore(
		@NotNull Item comparedItem,
		@NotNull Item instrument,
		@NotNull TagKey<Instrument> tagKey,
		@NotNull ResourceKey<CreativeModeTab>... tabs
	) {
		FrozenCreativeTabs.addInstrumentBefore(comparedItem, instrument, tagKey, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
	}
}
