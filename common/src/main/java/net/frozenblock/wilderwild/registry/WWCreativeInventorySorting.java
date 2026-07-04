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

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.lib.item.api.FrozenCreativeTabs;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

public final class WWCreativeInventorySorting {

	public static void init() {
		// BAOBAB (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(Items.MANGROVE_BUTTON, WWItems.BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_LOG, WWItems.BAOBAB_WOOD);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_WOOD, WWItems.STRIPPED_BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_BAOBAB_LOG, WWItems.STRIPPED_BAOBAB_WOOD);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_BAOBAB_WOOD, WWItems.BAOBAB_PLANKS);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_PLANKS, WWItems.BAOBAB_STAIRS);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_STAIRS, WWItems.BAOBAB_SLAB);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_SLAB, WWItems.BAOBAB_FENCE);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_FENCE, WWItems.BAOBAB_FENCE_GATE);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_FENCE_GATE, WWItems.BAOBAB_DOOR);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_DOOR, WWItems.BAOBAB_TRAPDOOR);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_TRAPDOOR, WWItems.BAOBAB_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWItems.BAOBAB_PRESSURE_PLATE, WWItems.BAOBAB_BUTTON);
		// BAOBAB (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(Items.MANGROVE_LOG, WWItems.BAOBAB_LOG);
		insertAfterInNaturalBlocks(Items.MANGROVE_LEAVES, WWItems.BAOBAB_LEAVES);
		// BAOBAB (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(Items.MANGROVE_HANGING_SIGN, WWItems.BAOBAB_SIGN);
		insertAfterInFunctionalBlocks(WWItems.BAOBAB_SIGN, WWItems.BAOBAB_HANGING_SIGN);
		insertAfterInFunctionalBlocks(Items.MANGROVE_SHELF, WWItems.BAOBAB_SHELF);
		// BAOBAB (TOOLS AND UTILITIES)
		// TODO BAOBAB_BOAT
		// TODO BAOBAB_CHEST_BOAT
		// BAOBAB NUT
		insertAfterInFoodAndDrinks(Items.ENCHANTED_GOLDEN_APPLE, WWItems.BAOBAB_NUT);
		insertAfterInNaturalBlocks(Items.MANGROVE_PROPAGULE, WWItems.BAOBAB_NUT);

		// WILLOW (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWItems.BAOBAB_BUTTON, WWItems.WILLOW_LOG);
		insertAfterInBuildingBlocks(WWItems.WILLOW_LOG, WWItems.WILLOW_WOOD);
		insertAfterInBuildingBlocks(WWItems.WILLOW_WOOD, WWItems.STRIPPED_WILLOW_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_WILLOW_LOG, WWItems.STRIPPED_WILLOW_WOOD);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_WILLOW_WOOD, WWItems.WILLOW_PLANKS);
		insertAfterInBuildingBlocks(WWItems.WILLOW_PLANKS, WWItems.WILLOW_STAIRS);
		insertAfterInBuildingBlocks(WWItems.WILLOW_STAIRS, WWItems.WILLOW_SLAB);
		insertAfterInBuildingBlocks(WWItems.WILLOW_SLAB, WWItems.WILLOW_FENCE);
		insertAfterInBuildingBlocks(WWItems.WILLOW_FENCE, WWItems.WILLOW_FENCE_GATE);
		insertAfterInBuildingBlocks(WWItems.WILLOW_FENCE_GATE, WWItems.WILLOW_DOOR);
		insertAfterInBuildingBlocks(WWItems.WILLOW_DOOR, WWItems.WILLOW_TRAPDOOR);
		insertAfterInBuildingBlocks(WWItems.WILLOW_TRAPDOOR, WWItems.WILLOW_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWItems.WILLOW_PRESSURE_PLATE, WWItems.WILLOW_BUTTON);
		// WILLOW (NATURAL_BLOCKS)
		insertAfterInNaturalBlocks(WWItems.BAOBAB_LOG, WWItems.WILLOW_LOG);
		insertAfterInNaturalBlocks(WWItems.BAOBAB_LEAVES, WWItems.WILLOW_LEAVES);
		insertAfterInNaturalBlocks(WWItems.BAOBAB_NUT, WWItems.WILLOW_SAPLING);
		// WILLOW (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.BAOBAB_HANGING_SIGN, WWItems.WILLOW_SIGN);
		insertAfterInFunctionalBlocks(WWItems.WILLOW_SIGN, WWItems.WILLOW_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWItems.BAOBAB_SHELF, WWItems.WILLOW_SHELF);
		// WILLOW (TOOLS AND UTILITIES)
		// TODO WILLOW_BOAT
		// TODO WILLOW_CHEST_BOAT

		// CYPRESS (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWItems.WILLOW_BUTTON, WWItems.CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_LOG, WWItems.CYPRESS_WOOD);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_WOOD, WWItems.STRIPPED_CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_CYPRESS_LOG, WWItems.STRIPPED_CYPRESS_WOOD);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_CYPRESS_WOOD, WWItems.CYPRESS_PLANKS);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_PLANKS, WWItems.CYPRESS_STAIRS);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_STAIRS, WWItems.CYPRESS_SLAB);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_SLAB, WWItems.CYPRESS_FENCE);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_FENCE, WWItems.CYPRESS_FENCE_GATE);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_FENCE_GATE, WWItems.CYPRESS_DOOR);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_DOOR, WWItems.CYPRESS_TRAPDOOR);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_TRAPDOOR, WWItems.CYPRESS_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWItems.CYPRESS_PRESSURE_PLATE, WWItems.CYPRESS_BUTTON);
		// CYPRESS (NATURAL_BLOCKS)
		insertAfterInNaturalBlocks(WWItems.WILLOW_LOG, WWItems.CYPRESS_LOG);
		insertAfterInNaturalBlocks(WWItems.WILLOW_LEAVES, WWItems.CYPRESS_LEAVES);
		insertAfterInNaturalBlocks(WWItems.WILLOW_SAPLING, WWItems.CYPRESS_SAPLING);
		// CYPRESS (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.WILLOW_HANGING_SIGN, WWItems.CYPRESS_SIGN);
		insertAfterInFunctionalBlocks(WWItems.CYPRESS_SIGN, WWItems.CYPRESS_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWItems.WILLOW_SHELF, WWItems.CYPRESS_SHELF);
		// CYPRESS (TOOLS AND UTILITIES)
		// TODO CYPRESS_BOAT
		// TODO CYPRESS_CHEST_BOAT

		// PALM (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(WWItems.CYPRESS_BUTTON, WWItems.PALM_LOG);
		insertAfterInBuildingBlocks(WWItems.PALM_LOG, WWItems.PALM_WOOD);
		insertAfterInBuildingBlocks(WWItems.PALM_WOOD, WWItems.STRIPPED_PALM_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_PALM_LOG, WWItems.STRIPPED_PALM_WOOD);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_PALM_WOOD, WWItems.PALM_PLANKS);
		insertAfterInBuildingBlocks(WWItems.PALM_PLANKS, WWItems.PALM_STAIRS);
		insertAfterInBuildingBlocks(WWItems.PALM_STAIRS, WWItems.PALM_SLAB);
		insertAfterInBuildingBlocks(WWItems.PALM_SLAB, WWItems.PALM_FENCE);
		insertAfterInBuildingBlocks(WWItems.PALM_FENCE, WWItems.PALM_FENCE_GATE);
		insertAfterInBuildingBlocks(WWItems.PALM_FENCE_GATE, WWItems.PALM_DOOR);
		insertAfterInBuildingBlocks(WWItems.PALM_DOOR, WWItems.PALM_TRAPDOOR);
		insertAfterInBuildingBlocks(WWItems.PALM_TRAPDOOR, WWItems.PALM_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWItems.PALM_PRESSURE_PLATE, WWItems.PALM_BUTTON);
		// PALM (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(WWItems.CYPRESS_LOG, WWItems.PALM_LOG);
		insertAfterInNaturalBlocks(WWItems.CYPRESS_LEAVES, WWItems.PALM_FRONDS);
		// PALM (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(WWItems.CYPRESS_HANGING_SIGN, WWItems.PALM_SIGN);
		insertAfterInFunctionalBlocks(WWItems.PALM_SIGN, WWItems.PALM_HANGING_SIGN);
		insertAfterInFunctionalBlocks(WWItems.CYPRESS_SHELF, WWItems.PALM_SHELF);
		// PALM (TOOLS AND UTILITIES)
		// TODO PALM_BOAT
		// TODO PALM_CHEST_BOAT
		// COCONUT
		// TODO COCONUT
		// TODO COCONUT
		insertAfterInFoodAndDrinks(WWItems.BAOBAB_NUT, WWItems.SPLIT_COCONUT);

		// MAPLE (BUILDING BLOCKS)
		insertAfterInBuildingBlocks(Items.CHERRY_BUTTON, WWItems.MAPLE_LOG);
		insertAfterInBuildingBlocks(WWItems.MAPLE_LOG, WWItems.MAPLE_WOOD);
		insertAfterInBuildingBlocks(WWItems.MAPLE_WOOD, WWItems.STRIPPED_MAPLE_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_MAPLE_LOG, WWItems.STRIPPED_MAPLE_WOOD);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_MAPLE_WOOD, WWItems.MAPLE_PLANKS);
		insertAfterInBuildingBlocks(WWItems.MAPLE_PLANKS, WWItems.MAPLE_STAIRS);
		insertAfterInBuildingBlocks(WWItems.MAPLE_STAIRS, WWItems.MAPLE_SLAB);
		insertAfterInBuildingBlocks(WWItems.MAPLE_SLAB, WWItems.MAPLE_FENCE);
		insertAfterInBuildingBlocks(WWItems.MAPLE_FENCE, WWItems.MAPLE_FENCE_GATE);
		insertAfterInBuildingBlocks(WWItems.MAPLE_FENCE_GATE, WWItems.MAPLE_DOOR);
		insertAfterInBuildingBlocks(WWItems.MAPLE_DOOR, WWItems.MAPLE_TRAPDOOR);
		insertAfterInBuildingBlocks(WWItems.MAPLE_TRAPDOOR, WWItems.MAPLE_PRESSURE_PLATE);
		insertAfterInBuildingBlocks(WWItems.MAPLE_PRESSURE_PLATE, WWItems.MAPLE_BUTTON);
		// MAPLE (NATURAL BLOCKS)
		insertAfterInNaturalBlocks(Items.CHERRY_LOG, WWItems.MAPLE_LOG);
		insertAfterInNaturalBlocks(Items.CHERRY_SAPLING, WWItems.YELLOW_MAPLE_SAPLING);
		insertAfterInNaturalBlocks(WWItems.YELLOW_MAPLE_SAPLING, WWItems.ORANGE_MAPLE_SAPLING);
		insertAfterInNaturalBlocks(WWItems.ORANGE_MAPLE_SAPLING, WWItems.RED_MAPLE_SAPLING);
		// TODO YELLOW_MAPLE_LEAVES
		// TODO ORANGE_MAPLE_LEAVES
		// TODO RED_MAPLE_LEAVES
		// MAPLE (FUNCTIONAL BLOCKS)
		insertAfterInFunctionalBlocks(Items.CHERRY_HANGING_SIGN, WWItems.MAPLE_SIGN);
		insertAfterInFunctionalBlocks(WWItems.MAPLE_SIGN, WWItems.MAPLE_HANGING_SIGN);
		insertAfterInFunctionalBlocks(Items.CHERRY_SHELF, WWItems.MAPLE_SHELF);
		// MAPLE (TOOLS AND UTILITIES)
		// TODO MAPLE_BOAT
		// TODO MAPLE_CHEST_BOAT

		// HOLLOWED LOGS
		insertAfterInBuildingAndNaturalBlocks(Items.OAK_LOG, WWItems.HOLLOWED_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_OAK_LOG, WWItems.STRIPPED_HOLLOWED_OAK_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.SPRUCE_LOG, WWItems.HOLLOWED_SPRUCE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_SPRUCE_LOG, WWItems.STRIPPED_HOLLOWED_SPRUCE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.BIRCH_LOG, WWItems.HOLLOWED_BIRCH_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_BIRCH_LOG, WWItems.STRIPPED_HOLLOWED_BIRCH_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.JUNGLE_LOG, WWItems.HOLLOWED_JUNGLE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_JUNGLE_LOG, WWItems.STRIPPED_HOLLOWED_JUNGLE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.ACACIA_LOG, WWItems.HOLLOWED_ACACIA_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_ACACIA_LOG, WWItems.STRIPPED_HOLLOWED_ACACIA_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.DARK_OAK_LOG, WWItems.HOLLOWED_DARK_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_DARK_OAK_LOG, WWItems.STRIPPED_HOLLOWED_DARK_OAK_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.CRIMSON_STEM, WWItems.HOLLOWED_CRIMSON_STEM);
		insertAfterInBuildingBlocks(Items.STRIPPED_CRIMSON_STEM, WWItems.STRIPPED_HOLLOWED_CRIMSON_STEM);

		insertAfterInBuildingAndNaturalBlocks(Items.WARPED_STEM, WWItems.HOLLOWED_WARPED_STEM);
		insertAfterInBuildingBlocks(Items.STRIPPED_WARPED_STEM, WWItems.STRIPPED_HOLLOWED_WARPED_STEM);

		insertAfterInBuildingAndNaturalBlocks(Items.MANGROVE_LOG, WWItems.HOLLOWED_MANGROVE_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_MANGROVE_LOG, WWItems.STRIPPED_HOLLOWED_MANGROVE_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.CHERRY_LOG, WWItems.HOLLOWED_CHERRY_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_CHERRY_LOG, WWItems.STRIPPED_HOLLOWED_CHERRY_LOG);

		insertAfterInBuildingAndNaturalBlocks(Items.PALE_OAK_LOG, WWItems.HOLLOWED_PALE_OAK_LOG);
		insertAfterInBuildingBlocks(Items.STRIPPED_PALE_OAK_LOG, WWItems.STRIPPED_HOLLOWED_PALE_OAK_LOG);

		insertBeforeInBuildingBlocks(WWItems.BAOBAB_WOOD, WWItems.HOLLOWED_BAOBAB_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_BAOBAB_LOG, WWItems.STRIPPED_HOLLOWED_BAOBAB_LOG);
		insertAfterInNaturalBlocks(WWItems.BAOBAB_LOG, WWItems.HOLLOWED_BAOBAB_LOG);

		insertBeforeInBuildingBlocks(WWItems.WILLOW_WOOD, WWItems.HOLLOWED_WILLOW_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_WILLOW_LOG, WWItems.STRIPPED_HOLLOWED_WILLOW_LOG);
		insertAfterInNaturalBlocks(WWItems.WILLOW_LOG, WWItems.HOLLOWED_WILLOW_LOG);

		insertBeforeInBuildingBlocks(WWItems.CYPRESS_WOOD, WWItems.HOLLOWED_CYPRESS_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_CYPRESS_LOG, WWItems.STRIPPED_HOLLOWED_CYPRESS_LOG);
		insertAfterInNaturalBlocks(WWItems.CYPRESS_LOG, WWItems.HOLLOWED_CYPRESS_LOG);

		insertBeforeInBuildingBlocks(WWItems.PALM_WOOD, WWItems.HOLLOWED_PALM_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_PALM_LOG, WWItems.STRIPPED_HOLLOWED_PALM_LOG);
		insertAfterInNaturalBlocks(WWItems.PALM_LOG, WWItems.HOLLOWED_PALM_LOG);

		insertBeforeInBuildingBlocks(WWItems.MAPLE_WOOD, WWItems.HOLLOWED_MAPLE_LOG);
		insertAfterInBuildingBlocks(WWItems.STRIPPED_MAPLE_LOG, WWItems.STRIPPED_HOLLOWED_MAPLE_LOG);
		insertAfterInNaturalBlocks(WWItems.MAPLE_LOG, WWItems.HOLLOWED_MAPLE_LOG);

		// LEAF LITTERS
		insertAfterInNaturalBlocks(Items.LEAF_LITTER, WWItems.SPRUCE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.SPRUCE_LEAF_LITTER, WWItems.BIRCH_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.BIRCH_LEAF_LITTER, WWItems.JUNGLE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.JUNGLE_LEAF_LITTER, WWItems.ACACIA_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.ACACIA_LEAF_LITTER, WWItems.DARK_OAK_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.DARK_OAK_LEAF_LITTER, WWItems.MANGROVE_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.MANGROVE_LEAF_LITTER, WWItems.BAOBAB_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.BAOBAB_LEAF_LITTER, WWItems.WILLOW_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.WILLOW_LEAF_LITTER, WWItems.CYPRESS_LEAF_LITTER);
		insertAfterInNaturalBlocks(WWItems.CYPRESS_LEAF_LITTER, WWItems.PALM_FROND_LITTER);
		insertAfterInNaturalBlocks(WWItems.PALM_FROND_LITTER, WWItems.CHERRY_LEAF_LITTER);
		// TODO YELLOW_MAPLE_LEAF_LITTER
		// TODO ORANGE_MAPLE_LEAF_LITTER
		// TODO RED_MAPLE_LEAF_LITTER
		// TODO PALE_OAK_LEAF_LITTER
		// TODO insertAfterInNaturalBlocks(WWItems.PALE_OAK_LEAF_LITTER, WWItems.AZALEA_LEAF_LITTER);

		// SMALL FLOWERS
		// TODO SEEDING_DANDELION
		insertAfterInNaturalBlocks(Items.CORNFLOWER, WWItems.CARNATION);
		insertAfterInNaturalBlocks(WWItems.CARNATION, WWItems.MARIGOLD);
		insertAfterInNaturalBlocks(WWItems.MARIGOLD, WWItems.PASQUEFLOWER);
		insertBeforeInNaturalBlocks(Items.TORCHFLOWER, WWItems.RED_HIBISCUS);
		insertAfterInNaturalBlocks(WWItems.RED_HIBISCUS, WWItems.YELLOW_HIBISCUS);
		insertAfterInNaturalBlocks(WWItems.YELLOW_HIBISCUS, WWItems.WHITE_HIBISCUS);
		insertAfterInNaturalBlocks(WWItems.WHITE_HIBISCUS, WWItems.PINK_HIBISCUS);
		insertAfterInNaturalBlocks(WWItems.PINK_HIBISCUS, WWItems.PURPLE_HIBISCUS);
		insertAfterInNaturalBlocks(Items.WILDFLOWERS, WWItems.PHLOX);
		insertAfterInNaturalBlocks(WWItems.PHLOX, WWItems.LANTANAS);
		// TALL FLOWERS
		insertAfterInNaturalBlocks(Items.PEONY, WWItems.DATURA);
		insertAfterInNaturalBlocks(WWItems.DATURA, WWItems.MILKWEED);
		// TODO MILKWEED_POD
		insertAfterInNaturalBlocks(WWItems.MILKWEED, WWItems.CATTAIL);

		// PLANTS
		// TODO POLLEN
		insertAfterInNaturalBlocks(Items.CACTUS, WWItems.PRICKLY_PEAR);
		insertAfterInFoodAndDrinks(Items.SWEET_BERRIES, WWItems.PRICKLY_PEAR);
		insertAfterInFoodAndDrinks(WWItems.PRICKLY_PEAR, WWItems.PEELED_PRICKLY_PEAR);
		// TODO TUMBLEWEED_PLANT
		// TODO TUMBLEWEED
		// TODO SHRUB
		insertBeforeInNaturalBlocks(Items.LILY_PAD, WWItems.BARNACLES);
		insertAfterInNaturalBlocks(WWItems.BARNACLES, WWItems.ALGAE);
		// TODO PLANKTON
		insertAfterInNaturalBlocks(Items.SEAGRASS, WWItems.SEA_WHIP);
		insertAfterInNaturalBlocks(WWItems.SEA_WHIP, WWItems.TUBE_WORMS);
		insertBeforeInNaturalBlocks(Items.SEA_PICKLE, WWItems.SEA_ANEMONE);
		insertAfterInNaturalBlocks(Items.LILY_PAD, WWItems.FLOWERING_LILY_PAD);

		insertAfterInNaturalBlocks(Items.FERN, WWItems.CLOVERS);
		insertAfterInNaturalBlocks(WWItems.CLOVERS, WWItems.FROZEN_SHORT_GRASS);
		insertAfterInNaturalBlocks(WWItems.FROZEN_SHORT_GRASS, WWItems.FROZEN_FERN);
		insertAfterInNaturalBlocks(Items.BUSH, WWItems.FROZEN_BUSH);
		insertAfterInNaturalBlocks(Items.LARGE_FERN, WWItems.FROZEN_TALL_GRASS);
		insertAfterInNaturalBlocks(WWItems.FROZEN_TALL_GRASS, WWItems.FROZEN_LARGE_FERN);

		insertBeforeInNaturalBlocks(Items.CRIMSON_ROOTS, WWItems.MYCELIUM_GROWTH);

		insertAfterInNaturalBlocks(Items.MOSS_CARPET, WWItems.AUBURN_MOSS_BLOCK);
		insertAfterInNaturalBlocks(WWItems.AUBURN_MOSS_BLOCK, WWItems.AUBURN_MOSS_CARPET);
		insertAfterInNaturalBlocks(WWItems.AUBURN_MOSS_CARPET, WWItems.AUBURN_CREEPING_MOSS);

		// SHELF FUNGI
		// TODO BROWN_SHELF_FUNGI
		// TODO RED_SHELF_FUNGI
		// TODO CRIMSON_SHELF_FUNGI
		// TODO WARPED_SHELF_FUNGI

		// PALE MUSHROOMS
		// TODO PALE_MUSHROOM_BLOCK
		// TODO PALE_MUSHROOM
		// TODO PALE_SHELF_FUNGI

		// SPONGE
		insertAfterInNaturalBlocks(Items.WET_SPONGE, WWItems.SPONGE_BUD);

		// EGGS
		// TODO OSTRICH_EGG
		// TODO PENGUIN_EGG

		// MESOGLEA
		// TODO PEARLESCENT_BLUE_MESOGLEA
		// TODO PEARLESCENT_PURPLE_MESOGLEA
		// TODO BLUE_MESOGLEA
		// TODO PINK_MESOGLEA
		// TODO RED_MESOGLEA
		// TODO YELLOW_MESOGLEA
		// TODO LIME_MESOGLEA

		// NEMATOCYST
		insertBeforeInNaturalBlocks(Items.SPONGE, WWItems.PEARLESCENT_BLUE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.PEARLESCENT_BLUE_NEMATOCYST, WWItems.PEARLESCENT_PURPLE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.PEARLESCENT_PURPLE_NEMATOCYST, WWItems.BLUE_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.BLUE_NEMATOCYST, WWItems.PINK_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.PINK_NEMATOCYST, WWItems.RED_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.RED_NEMATOCYST, WWItems.YELLOW_NEMATOCYST);
		insertAfterInNaturalBlocks(WWItems.YELLOW_NEMATOCYST, WWItems.LIME_NEMATOCYST);

		// FROGLIGHT
		insertAfterInNaturalAndFunctionalBlocks(Items.PEARLESCENT_FROGLIGHT, WWItems.OCHRE_FROGLIGHT_GOOP);
		insertAfterInNaturalAndFunctionalBlocks(WWItems.OCHRE_FROGLIGHT_GOOP, WWItems.VERDANT_FROGLIGHT_GOOP);
		insertAfterInNaturalAndFunctionalBlocks(WWItems.VERDANT_FROGLIGHT_GOOP, WWItems.PEARLESCENT_FROGLIGHT_GOOP);

		// ICE
		insertAfterInNaturalBlocks(Items.ICE, WWItems.FRAGILE_ICE);
		// TODO ICICLE

		// MUD BRICKS
		insertAfterInBuildingBlocks(Items.MUD_BRICKS, WWItems.CRACKED_MUD_BRICKS);
		insertAfterInBuildingBlocks(Items.MUD_BRICK_WALL, WWItems.CHISELED_MUD_BRICKS);
		insertAfterInBuildingBlocks(WWItems.CHISELED_MUD_BRICKS, WWItems.MOSSY_MUD_BRICKS);
		insertAfterInBuildingBlocks(WWItems.MOSSY_MUD_BRICKS, WWItems.MOSSY_MUD_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWItems.MOSSY_MUD_BRICK_STAIRS, WWItems.MOSSY_MUD_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWItems.MOSSY_MUD_BRICK_SLAB, WWItems.MOSSY_MUD_BRICK_WALL);

		// SCULK
		insertInBuildingBlocks(Items.SCULK);
		insertAfterInBuildingBlocks(Items.SCULK, WWItems.SCULK_STAIRS);
		insertAfterInBuildingBlocks(WWItems.SCULK_STAIRS, WWItems.SCULK_SLAB);
		insertAfterInBuildingBlocks(WWItems.SCULK_SLAB, WWItems.SCULK_WALL);
		insertAfterInBuildingBlocks(WWItems.SCULK_WALL, WWItems.OSSEOUS_SCULK);
		insertAfterInNaturalBlocks(Items.SCULK, WWItems.OSSEOUS_SCULK);
		// TODO HANGING_TENDRIL

		// SCORCHED SAND
		// TODO SCORCHED_SAND
		// TODO SCORCHED_RED_SAND

		// STORAGE
		// TODO STONE_CHEST
		// TODO DISPLAY_LANTERN

		// FUNCTIONAL BLOCK ENTITIES
		// TODO TERMITE_MOUND
		// TODO GEOTHERMAL_VENT

		// MISC
		insertAfterInFunctionalBlocks(Items.TINTED_GLASS, WWItems.ECHO_GLASS);
		insertAfterInBuildingBlocks(WWItems.OSSEOUS_SCULK, WWItems.NULL_BLOCK);

		// FIREFLY
		insertBeforeInSpawnEggs(Items.FOX_SPAWN_EGG, WWItems.FIREFLY_SPAWN_EGG);
		insertAfterInToolsAndUtilities(Items.MILK_BUCKET, WWItems.FIREFLY_BOTTLE);

		// BUTTERFLY
		// TODO BUTTERFLY_SPAWN_EGG
		// TODO BUTTERFLY_BOTTLE

		// JELLYFISH
		// TODO JELLYFISH_SPAWN_EGG
		// TODO JELLYFISH_BUCKET

		// CRAB
		// TODO CRAB_SPAWN_EGG
		// TODO CRAB_BUCKET
		// TODO CRAB_CLAW
		// TODO COOKED_CRAB_CLAW

		// OSTRICH
		// TODO OSTRICH_SPAWN_EGG

		// ZOMBIE OSTRICH
		// TODO ZOMBIE_OSTRICH_SPAWN_EGG

		// SCORCHED
		// TODO SCORCHED_SPAWN_EGG
		insertAfterInIngredients(Items.SPIDER_EYE, WWItems.SCORCHED_EYE);
		insertAfterInIngredients(Items.FERMENTED_SPIDER_EYE, WWItems.FERMENTED_SCORCHED_EYE);

		// MOOBLOOM
		// TODO MOOBLOOM_SPAWN_EGG

		// PENGUIN
		// TODO PENGUIN_SPAWN_EGG

		// GABBRO
		// TODO GEOTHERMAL_VENT
		// TODO GABBRO

		insertBeforeInBuildingBlocks(Items.BRICKS, WWItems.GABBRO);
		// TT
		insertAfterInBuildingBlocks(WWItems.GABBRO, WWItems.GABBRO_STAIRS);
		insertAfterInBuildingBlocks(WWItems.GABBRO_STAIRS, WWItems.GABBRO_SLAB);
		insertAfterInBuildingBlocks(WWItems.GABBRO_SLAB, WWItems.GABBRO_WALL);
		// BACK TO WW
		insertAfterInBuildingBlocks(FrozenBools.HAS_TRAILIERTALES ? WWItems.GABBRO_WALL : WWItems.GABBRO, WWItems.POLISHED_GABBRO);
		insertAfterInBuildingBlocks(WWItems.POLISHED_GABBRO, WWItems.POLISHED_GABBRO_STAIRS);
		insertAfterInBuildingBlocks(WWItems.POLISHED_GABBRO_STAIRS, WWItems.POLISHED_GABBRO_SLAB);
		insertAfterInBuildingBlocks(WWItems.POLISHED_GABBRO_SLAB, WWItems.POLISHED_GABBRO_WALL);
		insertAfterInBuildingBlocks(WWItems.POLISHED_GABBRO_WALL, WWItems.GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWItems.GABBRO_BRICKS, WWItems.CRACKED_GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWItems.CRACKED_GABBRO_BRICKS, WWItems.GABBRO_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWItems.GABBRO_BRICK_STAIRS, WWItems.GABBRO_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWItems.GABBRO_BRICK_SLAB, WWItems.GABBRO_BRICK_WALL);
		insertAfterInBuildingBlocks(WWItems.GABBRO_BRICK_WALL, WWItems.CHISELED_GABBRO_BRICKS);
		// TT
		insertAfterInBuildingBlocks(WWItems.CHISELED_GABBRO_BRICKS, WWItems.MOSSY_GABBRO_BRICKS);
		insertAfterInBuildingBlocks(WWItems.MOSSY_GABBRO_BRICKS, WWItems.MOSSY_GABBRO_BRICK_STAIRS);
		insertAfterInBuildingBlocks(WWItems.MOSSY_GABBRO_BRICK_STAIRS, WWItems.MOSSY_GABBRO_BRICK_SLAB);
		insertAfterInBuildingBlocks(WWItems.MOSSY_GABBRO_BRICK_SLAB, WWItems.MOSSY_GABBRO_BRICK_WALL);
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
