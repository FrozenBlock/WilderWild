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

package net.frozenblock.wilderwild.references;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.references.BlockItemId;
import net.minecraft.resources.Identifier;

public final class WWBlockItemIds {
	// MUD
	public static final BlockItemId CHISELED_MUD_BRICKS = create("chiseled_mud_bricks");
	public static final BlockItemId CRACKED_MUD_BRICKS = create("cracked_mud_bricks");
	public static final BlockItemId MOSSY_MUD_BRICKS = create("mossy_mud_bricks");
	public static final BlockItemId MOSSY_MUD_BRICK_STAIRS = create("mossy_mud_brick_stairs");
	public static final BlockItemId MOSSY_MUD_BRICK_SLAB = create("mossy_mud_brick_slab");
	public static final BlockItemId MOSSY_MUD_BRICK_WALL = create("mossy_mud_brick_wall");

	// SAND
	public static final BlockItemId SCORCHED_SAND = create("scorched_sand");
	public static final BlockItemId SCORCHED_RED_SAND = create("scorched_red_sand");

	// SAPLINGS
	public static final BlockItemId BAOBAB_NUT = create("baobab_nut");
	public static final BlockItemId WILLOW_SAPLING = create("willow_sapling");
	public static final BlockItemId CYPRESS_SAPLING = create("cypress_sapling");
	public static final BlockItemId COCONUT = create("coconut");
	public static final BlockItemId YELLOW_MAPLE_SAPLING = create("yellow_maple_sapling");
	public static final BlockItemId ORANGE_MAPLE_SAPLING = create("orange_maple_sapling");
	public static final BlockItemId RED_MAPLE_SAPLING = create("red_maple_sapling");

	// LEAVES
	public static final BlockItemId BAOBAB_LEAVES = create("baobab_leaves");
	public static final BlockItemId WILLOW_LEAVES = create("willow_leaves");
	public static final BlockItemId CYPRESS_LEAVES = create("cypress_leaves");
	public static final BlockItemId PALM_FRONDS = create("palm_fronds");
	public static final BlockItemId YELLOW_MAPLE_LEAVES = create("yellow_maple_leaves");
	public static final BlockItemId ORANGE_MAPLE_LEAVES = create("orange_maple_leaves");
	public static final BlockItemId RED_MAPLE_LEAVES = create("red_maple_leaves");

	// HOLLOWED LOGS
	public static final BlockItemId HOLLOWED_OAK_LOG = create("hollowed_oak_log");
	public static final BlockItemId HOLLOWED_SPRUCE_LOG = create("hollowed_spruce_log");
	public static final BlockItemId HOLLOWED_BIRCH_LOG = create("hollowed_birch_log");
	public static final BlockItemId HOLLOWED_JUNGLE_LOG = create("hollowed_jungle_log");
	public static final BlockItemId HOLLOWED_ACACIA_LOG = create("hollowed_acacia_log");
	public static final BlockItemId HOLLOWED_DARK_OAK_LOG = create("hollowed_dark_oak_log");
	public static final BlockItemId HOLLOWED_MANGROVE_LOG = create("hollowed_mangrove_log");
	public static final BlockItemId HOLLOWED_CHERRY_LOG = create("hollowed_cherry_log");
	public static final BlockItemId HOLLOWED_PALE_OAK_LOG = create("hollowed_pale_oak_log");
	public static final BlockItemId HOLLOWED_CRIMSON_STEM = create("hollowed_crimson_stem");
	public static final BlockItemId HOLLOWED_WARPED_STEM = create("hollowed_warped_stem");
	public static final BlockItemId HOLLOWED_BAOBAB_LOG = create("hollowed_baobab_log");
	public static final BlockItemId HOLLOWED_WILLOW_LOG = create("hollowed_willow_log");
	public static final BlockItemId HOLLOWED_CYPRESS_LOG = create("hollowed_cypress_log");
	public static final BlockItemId HOLLOWED_PALM_LOG = create("hollowed_palm_log");
	public static final BlockItemId HOLLOWED_MAPLE_LOG = create("hollowed_maple_log");

	// STRIPPED HOLLOWED LOGS
	public static final BlockItemId STRIPPED_HOLLOWED_OAK_LOG = create("stripped_hollowed_oak_log");
	public static final BlockItemId STRIPPED_HOLLOWED_SPRUCE_LOG = create("stripped_hollowed_spruce_log");
	public static final BlockItemId STRIPPED_HOLLOWED_BIRCH_LOG = create("stripped_hollowed_birch_log");
	public static final BlockItemId STRIPPED_HOLLOWED_JUNGLE_LOG = create("stripped_hollowed_jungle_log");
	public static final BlockItemId STRIPPED_HOLLOWED_ACACIA_LOG = create("stripped_hollowed_acacia_log");
	public static final BlockItemId STRIPPED_HOLLOWED_DARK_OAK_LOG = create("stripped_hollowed_dark_oak_log");
	public static final BlockItemId STRIPPED_HOLLOWED_MANGROVE_LOG = create("stripped_hollowed_mangrove_log");
	public static final BlockItemId STRIPPED_HOLLOWED_CHERRY_LOG = create("stripped_hollowed_cherry_log");
	public static final BlockItemId STRIPPED_HOLLOWED_PALE_OAK_LOG = create("stripped_hollowed_pale_oak_log");
	public static final BlockItemId STRIPPED_HOLLOWED_CRIMSON_STEM = create("stripped_hollowed_crimson_stem");
	public static final BlockItemId STRIPPED_HOLLOWED_WARPED_STEM = create("stripped_hollowed_warped_stem");

	// LEAF LITTER
	public static final BlockItemId ACACIA_LEAF_LITTER = create("acacia_leaf_litter");
	public static final BlockItemId AZALEA_LEAF_LITTER = create("azalea_leaf_litter");
	public static final BlockItemId BAOBAB_LEAF_LITTER = create("baobab_leaf_litter");
	public static final BlockItemId BIRCH_LEAF_LITTER = create("birch_leaf_litter");
	public static final BlockItemId CHERRY_LEAF_LITTER = create("cherry_leaf_litter");
	public static final BlockItemId CYPRESS_LEAF_LITTER = create("cypress_leaf_litter");
	public static final BlockItemId DARK_OAK_LEAF_LITTER = create("dark_oak_leaf_litter");
	public static final BlockItemId JUNGLE_LEAF_LITTER = create("jungle_leaf_litter");
	public static final BlockItemId MANGROVE_LEAF_LITTER = create("mangrove_leaf_litter");
	public static final BlockItemId PALE_OAK_LEAF_LITTER = create("pale_oak_leaf_litter");
	public static final BlockItemId PALM_FROND_LITTER = create("palm_frond_litter");
	public static final BlockItemId SPRUCE_LEAF_LITTER = create("spruce_leaf_litter");
	public static final BlockItemId WILLOW_LEAF_LITTER = create("willow_leaf_litter");
	public static final BlockItemId YELLOW_MAPLE_LEAF_LITTER = create("yellow_maple_leaf_litter");
	public static final BlockItemId ORANGE_MAPLE_LEAF_LITTER = create("orange_maple_leaf_litter");
	public static final BlockItemId RED_MAPLE_LEAF_LITTER = create("red_maple_leaf_litter");

	// SCULK
	public static final BlockItemId SCULK_STAIRS = create("sculk_stairs");
	public static final BlockItemId SCULK_SLAB = create("sculk_slab");
	public static final BlockItemId SCULK_WALL = create("sculk_wall");
	public static final BlockItemId OSSEOUS_SCULK = create("osseous_sculk");
	public static final BlockItemId HANGING_TENDRIL = create("hanging_tendril");
	public static final BlockItemId ECHO_GLASS = create("echo_glass");

	// MESOGLEA
	public static final BlockItemId PEARLESCENT_BLUE_MESOGLEA = create("pearlescent_blue_mesoglea");
	public static final BlockItemId PEARLESCENT_PURPLE_MESOGLEA = create("pearlescent_purple_mesoglea");
	public static final BlockItemId YELLOW_MESOGLEA = create("yellow_mesoglea");
	public static final BlockItemId BLUE_MESOGLEA = create("blue_mesoglea");
	public static final BlockItemId LIME_MESOGLEA = create("lime_mesoglea");
	public static final BlockItemId RED_MESOGLEA = create("red_mesoglea");
	public static final BlockItemId PINK_MESOGLEA = create("pink_mesoglea");

	// NEMATOCYST
	public static final BlockItemId PEARLESCENT_BLUE_NEMATOCYST = create("pearlescent_blue_nematocyst");
	public static final BlockItemId PEARLESCENT_PURPLE_NEMATOCYST = create("pearlescent_purple_nematocyst");
	public static final BlockItemId YELLOW_NEMATOCYST = create("yellow_nematocyst");
	public static final BlockItemId BLUE_NEMATOCYST = create("blue_nematocyst");
	public static final BlockItemId LIME_NEMATOCYST = create("lime_nematocyst");
	public static final BlockItemId RED_NEMATOCYST = create("red_nematocyst");
	public static final BlockItemId PINK_NEMATOCYST = create("pink_nematocyst");

	// MISC
	public static final BlockItemId TERMITE_MOUND = create("termite_mound");
	public static final BlockItemId STONE_CHEST = create("stone_chest");
	public static final BlockItemId NULL_BLOCK = create("null_block");
	public static final BlockItemId DISPLAY_LANTERN = create("display_lantern");

	// FLOWERS
	public static final BlockItemId SEEDING_DANDELION = create("seeding_dandelion");
	public static final BlockItemId CARNATION = create("carnation");
	public static final BlockItemId MARIGOLD = create("marigold");
	public static final BlockItemId PASQUEFLOWER = create("pasqueflower");
	public static final BlockItemId RED_HIBISCUS = create("red_hibiscus");
	public static final BlockItemId YELLOW_HIBISCUS = create("yellow_hibiscus");
	public static final BlockItemId WHITE_HIBISCUS = create("white_hibiscus");
	public static final BlockItemId PINK_HIBISCUS = create("pink_hibiscus");
	public static final BlockItemId PURPLE_HIBISCUS = create("purple_hibiscus");

	// FLOWERBEDS
	public static final BlockItemId PHLOX = create("phlox");
	public static final BlockItemId LANTANAS = create("lantanas");
	public static final BlockItemId CLOVERS = create("clovers");

	// TALL FLOWERS
	public static final BlockItemId DATURA = create("datura");
	public static final BlockItemId MILKWEED = create("milkweed");

	// VEGETATION
	public static final BlockItemId POLLEN = create("pollen");
	public static final BlockItemId PRICKLY_PEAR = create("prickly_pear");
	public static final BlockItemId SHRUB = create("shrub");
	public static final BlockItemId TUMBLEWEED_PLANT = create("tumbleweed_plant");
	public static final BlockItemId TUMBLEWEED = create("tumbleweed");
	public static final BlockItemId FROZEN_SHORT_GRASS = create("frozen_short_grass");
	public static final BlockItemId FROZEN_TALL_GRASS = create("frozen_tall_grass");
	public static final BlockItemId FROZEN_FERN = create("frozen_fern");
	public static final BlockItemId FROZEN_LARGE_FERN = create("frozen_large_fern");
	public static final BlockItemId FROZEN_BUSH = create("frozen_bush");
	public static final BlockItemId MYCELIUM_GROWTH = create("mycelium_growth");

	// MUSHROOMS
	public static final BlockItemId BROWN_SHELF_FUNGI = create("brown_shelf_fungi");
	public static final BlockItemId RED_SHELF_FUNGI = create("red_shelf_fungi");
	public static final BlockItemId CRIMSON_SHELF_FUNGI = create("crimson_shelf_fungi");
	public static final BlockItemId WARPED_SHELF_FUNGI = create("warped_shelf_fungi");

	public static final BlockItemId PALE_MUSHROOM_BLOCK = create("pale_mushroom_block");
	public static final BlockItemId PALE_MUSHROOM = create("pale_mushroom");
	public static final BlockItemId PALE_SHELF_FUNGI = create("pale_shelf_fungi");

	// MOSS
	public static final BlockItemId AUBURN_MOSS_BLOCK = create("auburn_moss_block");
	public static final BlockItemId AUBURN_MOSS_CARPET = create("auburn_moss_carpet");
	public static final BlockItemId AUBURN_CREEPING_MOSS = create("auburn_creeping_moss");

	// AQUATIC
	public static final BlockItemId CATTAIL = create("cattail");
	public static final BlockItemId FLOWERING_LILY_PAD = create("flowering_lily_pad");
	public static final BlockItemId ALGAE = create("algae");
	public static final BlockItemId PLANKTON = create("plankton");
	public static final BlockItemId SPONGE_BUD = create("sponge_bud");
	public static final BlockItemId BARNACLES = create("barnacles");
	public static final BlockItemId SEA_ANEMONE = create("sea_anemone");
	public static final BlockItemId SEA_WHIP = create("sea_whip");
	public static final BlockItemId TUBE_WORMS = create("tube_worms");

	// EGGS
	public static final BlockItemId OSTRICH_EGG = create("ostrich_egg");
	public static final BlockItemId PENGUIN_EGG = create("penguin_egg");

	// GABBRO
	public static final BlockItemId GABBRO = create("gabbro");
	public static final BlockItemId GABBRO_STAIRS = create("gabbro_stairs");
	public static final BlockItemId GABBRO_SLAB = create("gabbro_slab");
	public static final BlockItemId GABBRO_WALL = create("gabbro_wall");

	public static final BlockItemId GEYSER = create("geyser");

	public static final BlockItemId POLISHED_GABBRO = create("polished_gabbro");
	public static final BlockItemId POLISHED_GABBRO_STAIRS = create("polished_gabbro_stairs");
	public static final BlockItemId POLISHED_GABBRO_SLAB = create("polished_gabbro_slab");
	public static final BlockItemId POLISHED_GABBRO_WALL = create("polished_gabbro_wall");

	public static final BlockItemId GABBRO_BRICKS = create("gabbro_bricks");
	public static final BlockItemId GABBRO_BRICK_STAIRS = create("gabbro_brick_stairs");
	public static final BlockItemId GABBRO_BRICK_SLAB = create("gabbro_brick_slab");
	public static final BlockItemId GABBRO_BRICK_WALL = create("gabbro_brick_wall");
	public static final BlockItemId CRACKED_GABBRO_BRICKS = create("cracked_gabbro_bricks");
	public static final BlockItemId CHISELED_GABBRO_BRICKS = create("chiseled_gabbro_bricks");

	public static final BlockItemId MOSSY_GABBRO_BRICKS = create("mossy_gabbro_bricks");
	public static final BlockItemId MOSSY_GABBRO_BRICK_STAIRS = create("mossy_gabbro_brick_stairs");
	public static final BlockItemId MOSSY_GABBRO_BRICK_SLAB = create("mossy_gabbro_brick_slab");
	public static final BlockItemId MOSSY_GABBRO_BRICK_WALL = create("mossy_gabbro_brick_wall");

	// BAOBAB
	public static final BlockItemId BAOBAB_PLANKS = create("baobab_planks");
	public static final BlockItemId BAOBAB_STAIRS = create("baobab_stairs");
	public static final BlockItemId BAOBAB_FENCE_GATE = create("baobab_fence_gate");
	public static final BlockItemId BAOBAB_SLAB = create("baobab_slab");
	public static final BlockItemId BAOBAB_PRESSURE_PLATE = create("baobab_pressure_plate");
	public static final BlockItemId BAOBAB_BUTTON = create("baobab_button");
	public static final BlockItemId BAOBAB_DOOR = create("baobab_door");
	public static final BlockItemId BAOBAB_TRAPDOOR = create("baobab_trapdoor");
	public static final BlockItemId BAOBAB_FENCE = create("baobab_fence");
	public static final BlockItemId BAOBAB_LOG = create("baobab_log");
	public static final BlockItemId STRIPPED_BAOBAB_LOG = create("stripped_baobab_log"	);
	public static final BlockItemId STRIPPED_HOLLOWED_BAOBAB_LOG = create("stripped_hollowed_baobab_log");
	public static final BlockItemId BAOBAB_WOOD = create("baobab_wood");
	public static final BlockItemId STRIPPED_BAOBAB_WOOD = create("stripped_baobab_wood");
	public static final BlockItemId BAOBAB_SIGN = create("baobab_sign");
	public static final BlockItemId BAOBAB_HANGING_SIGN = create("baobab_hanging_sign");
	public static final BlockItemId BAOBAB_SHELF = create("baobab_shelf");

	// WILLOW
	public static final BlockItemId WILLOW_PLANKS = create("willow_planks");
	public static final BlockItemId WILLOW_STAIRS = create("willow_stairs");
	public static final BlockItemId WILLOW_FENCE_GATE = create("willow_fence_gate");
	public static final BlockItemId WILLOW_SLAB = create("willow_slab");
	public static final BlockItemId WILLOW_PRESSURE_PLATE = create("willow_pressure_plate");
	public static final BlockItemId WILLOW_BUTTON = create("willow_button");
	public static final BlockItemId WILLOW_DOOR = create("willow_door");
	public static final BlockItemId WILLOW_TRAPDOOR = create("willow_trapdoor");
	public static final BlockItemId WILLOW_FENCE = create("willow_fence");
	public static final BlockItemId WILLOW_LOG = create("willow_log");
	public static final BlockItemId STRIPPED_WILLOW_LOG = create("stripped_willow_log"	);
	public static final BlockItemId STRIPPED_HOLLOWED_WILLOW_LOG = create("stripped_hollowed_willow_log");
	public static final BlockItemId WILLOW_WOOD = create("willow_wood");
	public static final BlockItemId STRIPPED_WILLOW_WOOD = create("stripped_willow_wood");
	public static final BlockItemId WILLOW_SIGN = create("willow_sign");
	public static final BlockItemId WILLOW_HANGING_SIGN = create("willow_hanging_sign");
	public static final BlockItemId WILLOW_SHELF = create("willow_shelf");

	// CYPRESS
	public static final BlockItemId CYPRESS_PLANKS = create("cypress_planks");
	public static final BlockItemId CYPRESS_STAIRS = create("cypress_stairs");
	public static final BlockItemId CYPRESS_FENCE_GATE = create("cypress_fence_gate");
	public static final BlockItemId CYPRESS_SLAB = create("cypress_slab");
	public static final BlockItemId CYPRESS_PRESSURE_PLATE = create("cypress_pressure_plate");
	public static final BlockItemId CYPRESS_BUTTON = create("cypress_button");
	public static final BlockItemId CYPRESS_DOOR = create("cypress_door");
	public static final BlockItemId CYPRESS_TRAPDOOR = create("cypress_trapdoor");
	public static final BlockItemId CYPRESS_FENCE = create("cypress_fence");
	public static final BlockItemId CYPRESS_LOG = create("cypress_log");
	public static final BlockItemId STRIPPED_CYPRESS_LOG = create("stripped_cypress_log"	);
	public static final BlockItemId STRIPPED_HOLLOWED_CYPRESS_LOG = create("stripped_hollowed_cypress_log");
	public static final BlockItemId CYPRESS_WOOD = create("cypress_wood");
	public static final BlockItemId STRIPPED_CYPRESS_WOOD = create("stripped_cypress_wood");
	public static final BlockItemId CYPRESS_SIGN = create("cypress_sign");
	public static final BlockItemId CYPRESS_HANGING_SIGN = create("cypress_hanging_sign");
	public static final BlockItemId CYPRESS_SHELF = create("cypress_shelf");

	// PALM
	public static final BlockItemId PALM_PLANKS = create("palm_planks");
	public static final BlockItemId PALM_STAIRS = create("palm_stairs");
	public static final BlockItemId PALM_FENCE_GATE = create("palm_fence_gate");
	public static final BlockItemId PALM_SLAB = create("palm_slab");
	public static final BlockItemId PALM_PRESSURE_PLATE = create("palm_pressure_plate");
	public static final BlockItemId PALM_BUTTON = create("palm_button");
	public static final BlockItemId PALM_DOOR = create("palm_door");
	public static final BlockItemId PALM_TRAPDOOR = create("palm_trapdoor");
	public static final BlockItemId PALM_FENCE = create("palm_fence");
	public static final BlockItemId PALM_LOG = create("palm_log");
	public static final BlockItemId STRIPPED_PALM_LOG = create("stripped_palm_log"	);
	public static final BlockItemId STRIPPED_HOLLOWED_PALM_LOG = create("stripped_hollowed_palm_log");
	public static final BlockItemId PALM_WOOD = create("palm_wood");
	public static final BlockItemId STRIPPED_PALM_WOOD = create("stripped_palm_wood");
	public static final BlockItemId PALM_SIGN = create("palm_sign");
	public static final BlockItemId PALM_HANGING_SIGN = create("palm_hanging_sign");
	public static final BlockItemId PALM_SHELF = create("palm_shelf");

	// MAPLE
	public static final BlockItemId MAPLE_PLANKS = create("maple_planks");
	public static final BlockItemId MAPLE_STAIRS = create("maple_stairs");
	public static final BlockItemId MAPLE_FENCE_GATE = create("maple_fence_gate");
	public static final BlockItemId MAPLE_SLAB = create("maple_slab");
	public static final BlockItemId MAPLE_PRESSURE_PLATE = create("maple_pressure_plate");
	public static final BlockItemId MAPLE_BUTTON = create("maple_button");
	public static final BlockItemId MAPLE_DOOR = create("maple_door");
	public static final BlockItemId MAPLE_TRAPDOOR = create("maple_trapdoor");
	public static final BlockItemId MAPLE_FENCE = create("maple_fence");
	public static final BlockItemId MAPLE_LOG = create("maple_log");
	public static final BlockItemId STRIPPED_MAPLE_LOG = create("stripped_maple_log"	);
	public static final BlockItemId STRIPPED_HOLLOWED_MAPLE_LOG = create("stripped_hollowed_maple_log");
	public static final BlockItemId MAPLE_WOOD = create("maple_wood");
	public static final BlockItemId STRIPPED_MAPLE_WOOD = create("stripped_maple_wood");
	public static final BlockItemId MAPLE_SIGN = create("maple_sign");
	public static final BlockItemId MAPLE_HANGING_SIGN = create("maple_hanging_sign");
	public static final BlockItemId MAPLE_SHELF = create("maple_shelf");

	// ICE
	public static final BlockItemId FRAGILE_ICE = create("fragile_ice");
	public static final BlockItemId ICICLE = create("icicle");

	// FROGLIGHT GOOP
	public static final BlockItemId OCHRE_FROGLIGHT_GOOP = create("ochre_froglight_goop");
	public static final BlockItemId VERDANT_FROGLIGHT_GOOP = create("verdant_froglight_goop");
	public static final BlockItemId PEARLESCENT_FROGLIGHT_GOOP = create("pearlescent_froglight_goop");

	private WWBlockItemIds() {
		throw new UnsupportedOperationException("WWBlockItemIds contains only static declarations.");
	}

	private static BlockItemId create(String name) {
		final Identifier id = WWConstants.id(name);
		return BlockItemId.create(id, id);
	}

	private static BlockItemId create(String blockName, String itemName) {
		return BlockItemId.create(WWConstants.id(blockName), WWConstants.id(itemName));
	}
}
