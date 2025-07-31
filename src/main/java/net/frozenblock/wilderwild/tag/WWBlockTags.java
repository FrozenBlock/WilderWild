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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public final class WWBlockTags {
	public static final TagKey<Block> SOUND_GRASS = bind("sound/grass");
	public static final TagKey<Block> SOUND_FLOWER = bind("sound/flower");
	public static final TagKey<Block> SOUND_LEAVES = bind("sound/leaves");
	public static final TagKey<Block> SOUND_CONIFER_LEAVES = bind("sound/conifer_leaves");
	public static final TagKey<Block> SOUND_SAPLING = bind("sound/sapling");
	public static final TagKey<Block> SOUND_CACTUS = bind("sound/cactus");
	public static final TagKey<Block> SOUND_COARSE_DIRT = bind("sound/coarse_dirt");
	public static final TagKey<Block> SOUND_ICE = bind("sound/ice");
	public static final TagKey<Block> SOUND_FROSTED_ICE = bind("sound/frosted_ice");
	public static final TagKey<Block> SOUND_MUSHROOM = bind("sound/mushroom");
	public static final TagKey<Block> SOUND_MUSHROOM_BLOCK = bind("sound/mushroom_block");
	public static final TagKey<Block> SOUND_SANDSTONE = bind("sound/sandstone");
	public static final TagKey<Block> SOUND_LILY_PAD = bind("sound/lily_pad");
	public static final TagKey<Block> SOUND_MELON = bind("sound/melon");
	public static final TagKey<Block> SOUND_MELON_STEM = bind("sound/melon_stem");
	public static final TagKey<Block> SOUND_GRAVEL = bind("sound/gravel");
	public static final TagKey<Block> SOUND_CLAY = bind("sound/clay");
	public static final TagKey<Block> SOUND_DEAD_BUSH = bind("sound/dead_bush");
	public static final TagKey<Block> SOUND_PODZOL = bind("sound/podzol");
	public static final TagKey<Block> SOUND_REINFORCED_DEEPSLATE = bind("sound/reinforced_deepslate");
	public static final TagKey<Block> SOUND_SUGAR_CANE = bind("sound/sugar_cane");
	public static final TagKey<Block> SOUND_WITHER_ROSE = bind("sound/wither_rose");
	public static final TagKey<Block> SOUND_MAGMA_BLOCK = bind("sound/magma_block");
	public static final TagKey<Block> SOUND_COCONUT = bind("sound/coconut");
	public static final TagKey<Block> SOUND_AUBURN_MOSS = bind("sound/auburn_moss");
	public static final TagKey<Block> SOUND_AUBURN_MOSS_CARPET = bind("sound/auburn_moss_carpet");

	public static final TagKey<Block> SNOW_GENERATION_CAN_SEARCH_THROUGH = bind("snow_generation_can_search_through");

	public static final TagKey<Block> HOLLOWED_LOGS = bind("hollowed_logs");
	public static final TagKey<Block> HOLLOWED_LOGS_DONT_BURN = bind("hollowed_logs_dont_burn");
	public static final TagKey<Block> HOLLOWED_LOGS_THAT_BURN = bind("hollowed_logs_that_burn");

	public static final TagKey<Block> STRIPPED_HOLLOWED_LOGS = bind("stripped_hollowed_logs");
	public static final TagKey<Block> STRIPPED_HOLLOWED_LOGS_DONT_BURN = bind("stripped_hollowed_logs_dont_burn");
	public static final TagKey<Block> STRIPPED_HOLLOWED_LOGS_THAT_BURN = bind("stripped_hollowed_logs_that_burn");

	public static final TagKey<Block> HOLLOWED_ACACIA_LOGS = bind("hollowed_acacia_logs");
	public static final TagKey<Block> HOLLOWED_BIRCH_LOGS = bind("hollowed_birch_logs");
	public static final TagKey<Block> HOLLOWED_CHERRY_LOGS = bind("hollowed_cherry_logs");
	public static final TagKey<Block> HOLLOWED_CRIMSON_STEMS = bind("hollowed_crimson_stems");
	public static final TagKey<Block> HOLLOWED_DARK_OAK_LOGS = bind("hollowed_dark_oak_logs");
	public static final TagKey<Block> HOLLOWED_JUNGLE_LOGS = bind("hollowed_jungle_logs");
	public static final TagKey<Block> HOLLOWED_MANGROVE_LOGS = bind("hollowed_mangrove_logs");
	public static final TagKey<Block> HOLLOWED_OAK_LOGS = bind("hollowed_oak_logs");
	public static final TagKey<Block> HOLLOWED_SPRUCE_LOGS = bind("hollowed_spruce_logs");
	public static final TagKey<Block> HOLLOWED_PALE_OAK_LOGS = bind("hollowed_pale_oak_logs");
	public static final TagKey<Block> HOLLOWED_WARPED_STEMS = bind("hollowed_warped_stems");

	public static final TagKey<Block> HOLLOWED_BAOBAB_LOGS = bind("hollowed_baobab_logs");
	public static final TagKey<Block> HOLLOWED_WILLOW_LOGS = bind("hollowed_willow_logs");
	public static final TagKey<Block> HOLLOWED_CYPRESS_LOGS = bind("hollowed_cypress_logs");
	public static final TagKey<Block> HOLLOWED_PALM_LOGS = bind("hollowed_palm_logs");
	public static final TagKey<Block> HOLLOWED_MAPLE_LOGS = bind("hollowed_maple_logs");

	public static final TagKey<Block> MESOGLEA = bind("mesoglea");
	public static final TagKey<Block> NEMATOCYSTS = bind("nematocysts");

	public static final TagKey<Block> LEAF_LITTERS = bind("leaf_litters");
	public static final TagKey<Block> LEAF_LITTER_CANNOT_SURVIVE_ON = bind("leaf_litter_cannot_survive_on");
	public static final TagKey<Block> LEAF_LITTER_CAN_SURVIVE_ON = bind("leaf_litter_can_survive_on");

	public static final TagKey<Block> BAOBAB_LOGS = bind("baobab_logs");
	public static final TagKey<Block> WILLOW_LOGS = bind("willow_logs");
	public static final TagKey<Block> CYPRESS_LOGS = bind("cypress_logs");
	public static final TagKey<Block> PALM_LOGS = bind("palm_logs");
	public static final TagKey<Block> MAPLE_LOGS = bind("maple_logs");

	public static final TagKey<Block> WILLOW_ROOTS_CAN_GROW_THROUGH = bind("willow_roots_can_grow_through");

	public static final TagKey<Block> KILLS_TERMITE = bind("kills_termite");
	public static final TagKey<Block> BLOCKS_TERMITE = bind("blocks_termite");

	public static final TagKey<Block> ICICLE_FALLS_FROM = bind("icicle_falls_from");
	public static final TagKey<Block> ICICLE_GROWS_WHEN_UNDER = bind("icicle_grows_when_under");

	public static final TagKey<Block> ANCIENT_CITY_BLOCKS = bind("ancient_city_blocks");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE_WORLDGEN = bind("sculk_slab_replaceable_worldgen");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE_WORLDGEN = bind("sculk_stair_replaceable_worldgen");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE_WORLDGEN = bind("sculk_wall_replaceable_worldgen");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE = bind("sculk_slab_replaceable");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE = bind("sculk_stair_replaceable");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE = bind("sculk_wall_replaceable");

	public static final TagKey<Block> SPLITS_COCONUT = bind("splits_coconut");
	public static final TagKey<Block> STOPS_TUMBLEWEED = bind("stops_tumbleweed");

	public static final TagKey<Block> NO_LIGHTNING_BLOCK_PARTICLES = bind("no_lightning_block_particles");
	public static final TagKey<Block> NO_LIGHTNING_SMOKE_PARTICLES = bind("no_lightning_smoke_particles");
	public static final TagKey<Block> CRAB_HIDEABLE = bind("crab_hideable");
	public static final TagKey<Block> OSTRICH_BEAK_BURYABLE = bind("ostrich_beak_buryable");
	public static final TagKey<Block> GEYSER_CAN_PASS_THROUGH = bind("geyser_can_pass_through");
	public static final TagKey<Block> GEYSER_CANNOT_PASS_THROUGH = bind("geyser_cannot_pass_through");
	public static final TagKey<Block> FIREFLY_HIDEABLE_BLOCKS = bind("firefly_hideable_blocks");
	public static final TagKey<Block> PENGUIN_IGNORE_FRICTION = bind("penguin_ignore_friction");
	public static final TagKey<Block> PENGUINS_SPAWNABLE_ON = bind("penguins_spawnable_on");

	public static final TagKey<Block> MYCELIUM_GROWTH_REPLACEABLE = bind("mycelium_growth_replaceable");
	public static final TagKey<Block> RED_MOSS_REPLACEABLE = bind("red_moss_replaceable");

	public static final TagKey<Block> BUSH_MAY_PLACE_ON = bind("bush_may_place_on");

	public static final TagKey<Block> BUSH_MAY_PLACE_ON_FEATURE_NO_SAND = bind("feature/bush_may_place_on_no_sand");
	public static final TagKey<Block> PACKED_MUD_REPLACEABLE = bind("feature/packed_mud_replaceable");
	public static final TagKey<Block> SAND_POOL_REPLACEABLE = bind("feature/sand_pool_replaceable");
	public static final TagKey<Block> RIVER_POOL_REPLACEABLE = bind("feature/river_pool_replaceable");
	public static final TagKey<Block> SMALL_SPONGE_GROWS_ON = bind("feature/small_sponge_grows_on");
	public static final TagKey<Block> FALLEN_TREE_PLACEABLE = bind("feature/fallen_tree_placeable");
	public static final TagKey<Block> BASIN_REPLACEABLE = bind("feature/basin_replaceable");
	public static final TagKey<Block> HYDROTHERMAL_VENT_REPLACEABLE = bind("feature/hydrothermal_vent_replaceable");
	public static final TagKey<Block> CATTAIL_FEATURE_PLACEABLE = bind("feature/cattail_placeable");
	public static final TagKey<Block> CATTAIL_FEATURE_MUD_PLACEABLE = bind("feature/cattail_mud_placeable");
	public static final TagKey<Block> STONE_TRANSITION_REPLACEABLE = bind("feature/stone_transition_replaceable");
	public static final TagKey<Block> STONE_TRANSITION_PLACEABLE = bind("feature/stone_transition_placeable");
	public static final TagKey<Block> SMALL_SAND_TRANSITION_REPLACEABLE = bind("feature/small_sand_transition_replaceable");
	public static final TagKey<Block> GRAVEL_TRANSITION_REPLACEABLE = bind("feature/gravel_transition_replaceable");
	public static final TagKey<Block> GRAVEL_TRANSITION_PLACEABLE = bind("feature/gravel_transition_placeable");
	public static final TagKey<Block> SAND_TRANSITION_REPLACEABLE = bind("feature/sand_transition_replaceable");
	public static final TagKey<Block> SAND_TRANSITION_PLACEABLE = bind("feature/sand_transition_placeable");
	public static final TagKey<Block> RED_SAND_TRANSITION_REPLACEABLE = bind("feature/red_sand_transition_replaceable");
	public static final TagKey<Block> RED_SAND_TRANSITION_PLACEABLE = bind("feature/red_sand_transition_placeable");
	public static final TagKey<Block> MUD_TRANSITION_REPLACEABLE = bind("feature/mud_transition_replaceable");
	public static final TagKey<Block> MUD_TRANSITION_PLACEABLE = bind("feature/mud_transition_placeable");
	public static final TagKey<Block> MUD_PATH_REPLACEABLE = bind("feature/mud_path_replaceable");
	public static final TagKey<Block> COARSE_PATH_REPLACEABLE = bind("feature/coarse_path_replaceable");
	public static final TagKey<Block> COARSE_CLEARING_REPLACEABLE = bind("feature/coarse_clearing_replaceable");
	public static final TagKey<Block> ROOTED_DIRT_PATH_REPLACEABLE = bind("feature/rooted_dirt_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_SAND_PATH_REPLACEABLE = bind("feature/under_water_sand_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_GRAVEL_PATH_REPLACEABLE = bind("feature/under_water_gravel_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_CLAY_PATH_REPLACEABLE = bind("feature/under_water_clay_path_replaceable");
	public static final TagKey<Block> BEACH_CLAY_PATH_REPLACEABLE = bind("feature/beach_clay_path_replaceable");
	public static final TagKey<Block> RIVER_GRAVEL_PATH_REPLACEABLE = bind("feature/river_gravel_path_replaceable");
	public static final TagKey<Block> SAND_PATH_REPLACEABLE = bind("feature/sand_path_replaceable");
	public static final TagKey<Block> GRAVEL_PATH_REPLACEABLE = bind("feature/gravel_path_replaceable");
	public static final TagKey<Block> GRAVEL_CLEARING_REPLACEABLE = bind("feature/gravel_clearing_replaceable");
	public static final TagKey<Block> GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE = bind("feature/gravel_and_pale_moss_path_replaceable");
	public static final TagKey<Block> STONE_PATH_REPLACEABLE = bind("feature/stone_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_REPLACEABLE = bind("feature/packed_mud_path_replaceable");
	public static final TagKey<Block> MOSS_PATH_REPLACEABLE = bind("feature/moss_path_replaceable");
	public static final TagKey<Block> OCEAN_MOSS_REPLACEABLE = bind("feature/ocean_moss_replaceable");
	public static final TagKey<Block> SANDSTONE_PATH_REPLACEABLE = bind("feature/sandstone_path_replaceable");
	public static final TagKey<Block> SMALL_COARSE_DIRT_PATH_REPLACEABLE = bind("feature/small_coarse_dirt_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_BADLANDS_REPLACEABLE = bind("feature/packed_mud_path_badlands_replaceable");
	public static final TagKey<Block> POLLEN_FEATURE_PLACEABLE = bind("feature/pollen_feature_placeable");
	public static final TagKey<Block> AUBURN_CREEPING_MOSS_FEATURE_PLACEABLE = bind("feature/auburn_creeping_moss_feature_placeable");
	public static final TagKey<Block> BARNACLES_FEATURE_PLACEABLE = bind("feature/barnacles_feature_placeable");
	public static final TagKey<Block> BARNACLES_FEATURE_PLACEABLE_STRUCTURE = bind("feature/barnacles_feature_placeable_structure");
	public static final TagKey<Block> SEA_ANEMONE_FEATURE_CANNOT_PLACE = bind("feature/sea_anemone_feature_cannot_place");
	public static final TagKey<Block> TERMITE_DISK_REPLACEABLE = bind("feature/termite_disk_replaceable");
	public static final TagKey<Block> TERMITE_DISK_BLOCKS = bind("feature/termite_disk_blocks");
	public static final TagKey<Block> BLUE_NEMATOCYST_FEATURE_PLACEABLE = bind("feature/blue_nematocyst_feature_placeable");
	public static final TagKey<Block> PURPLE_NEMATOCYST_FEATURE_PLACEABLE = bind("feature/purple_nematocyst_feature_placeable");
	public static final TagKey<Block> SHELF_FUNGI_FEATURE_PLACEABLE = bind("feature/shelf_fungi_feature_placeable");
	public static final TagKey<Block> SHELF_FUNGI_FEATURE_PLACEABLE_NETHER = bind("feature/shelf_fungi_feature_placeable_nether");
	public static final TagKey<Block> SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("feature/scorched_sand_feature_inner_replaceable");
	public static final TagKey<Block> SCORCHED_SAND_FEATURE_REPLACEABLE = bind("feature/scorched_sand_feature_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("feature/red_scorched_sand_feature_inner_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_REPLACEABLE = bind("feature/red_scorched_sand_feature_replaceable");
	public static final TagKey<Block> DIORITE_ICE_REPLACEABLE = bind("feature/diorite_ice_replaceable");
	public static final TagKey<Block> CAVE_ICE_REPLACEABLE = bind("feature/cave_ice_replaceable");
	public static final TagKey<Block> CAVE_FRAGILE_ICE_REPLACEABLE = bind("feature/cave_fragile_ice_replaceable");
	public static final TagKey<Block> MESOGLEA_PATH_REPLACEABLE = bind("feature/mesoglea_path_replaceable");
	public static final TagKey<Block> MAGMA_REPLACEABLE = bind("feature/magma_replaceable");
	public static final TagKey<Block> NETHER_GEYSER_REPLACEABLE = bind("feature/nether_geyser_replaceable");
	public static final TagKey<Block> OASIS_PATH_REPLACEABLE = bind("feature/oasis_path_replaceable");
	public static final TagKey<Block> COARSE_DIRT_DISK_REPLACEABLE = bind("feature/coarse_dirt_disk_replaceable");

	private WWBlockTags() {
		throw new UnsupportedOperationException("WWBlockTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, WWConstants.id(path));
	}
}
