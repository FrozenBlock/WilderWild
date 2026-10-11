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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class WWFeatureBlockTags {
	public static final TagKey<Block> SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND = bind("shrub_may_place_on_no_sand");

	// AQUATIC
	public static final TagKey<Block> BARNACLES_PLACEABLE_NATURAL = bind("barnacles_placeable_natural");
	public static final TagKey<Block> BARNACLES_PLACEABLE_UNNATURAL = bind("barnacles_placeable_unnatural");
	public static final TagKey<Block> BARNACLES_PLACEABLE = bind("barnacles_placeable");
	public static final TagKey<Block> CATTAIL_PLACEABLE = bind("cattail_placeable");
	public static final TagKey<Block> SEA_ANEMONE_CANNOT_PLACE = bind("sea_anemone_cannot_place");
	public static final TagKey<Block> SPONGE_BUD_PLACEABLE = bind("sponge_bud_placeable");
	public static final TagKey<Block> HYDROTHERMAL_VENT_REPLACEABLE = bind("hydrothermal_vent_replaceable");

	// CAVE
	// MESOGLEA CAVES
	public static final TagKey<Block> STONE_POOL_REPLACEABLE = bind("stone_pool_replaceable");
	public static final TagKey<Block> MESOGLEA_AND_NEMATOCYST_BASE_BLOCKS = bind("mesoglea_and_nematocyst_base_blocks");
	public static final TagKey<Block> NEMATOCYST_PLACEABLE_PEARLESCENT_BLUE = bind("nematocyst_placeable_pearlescent_blue");
	public static final TagKey<Block> NEMATOCYST_PLACEABLE_PEARLESCENT_PURPLE = bind("nematocyst_placeable_pearlescent_purple");
	// MAGMATIC CAVES
	public static final TagKey<Block> MAGMA_REPLACEABLE = bind("magma_replaceable");
	public static final TagKey<Block> GEOTHERMAL_VENT_STACK_REPLACEABLE = bind("geothermal_vent_stack_replaceable");

	public static final TagKey<Block> PACKED_MUD_REPLACEABLE = bind("packed_mud_replaceable");
	public static final TagKey<Block> SAND_POOL_REPLACEABLE = bind("sand_pool_replaceable");
	public static final TagKey<Block> RIVER_POOL_REPLACEABLE = bind("river_pool_replaceable");
	public static final TagKey<Block> FALLEN_TREE_PLACEABLE = bind("fallen_tree_placeable");
	public static final TagKey<Block> BASIN_REPLACEABLE = bind("basin_replaceable");

	public static final TagKey<Block> STONE_TRANSITION_REPLACEABLE = bind("stone_transition_replaceable");
	public static final TagKey<Block> STONE_TRANSITION_PLACEABLE = bind("stone_transition_placeable");
	public static final TagKey<Block> SMALL_SAND_TRANSITION_REPLACEABLE = bind("small_sand_transition_replaceable");
	public static final TagKey<Block> GRAVEL_TRANSITION_REPLACEABLE = bind("gravel_transition_replaceable");
	public static final TagKey<Block> GRAVEL_TRANSITION_PLACEABLE = bind("gravel_transition_placeable");
	public static final TagKey<Block> SAND_TRANSITION_REPLACEABLE = bind("sand_transition_replaceable");
	public static final TagKey<Block> SAND_TRANSITION_PLACEABLE = bind("sand_transition_placeable");
	public static final TagKey<Block> RED_SAND_TRANSITION_REPLACEABLE = bind("red_sand_transition_replaceable");
	public static final TagKey<Block> RED_SAND_TRANSITION_PLACEABLE = bind("red_sand_transition_placeable");
	public static final TagKey<Block> MUD_TRANSITION_REPLACEABLE = bind("mud_transition_replaceable");
	public static final TagKey<Block> MUD_TRANSITION_PLACEABLE = bind("mud_transition_placeable");
	public static final TagKey<Block> MUD_PATH_REPLACEABLE = bind("mud_path_replaceable");
	public static final TagKey<Block> COARSE_PATH_REPLACEABLE = bind("coarse_path_replaceable");
	public static final TagKey<Block> COARSE_CLEARING_REPLACEABLE = bind("coarse_clearing_replaceable");
	public static final TagKey<Block> ROOTED_DIRT_PATH_REPLACEABLE = bind("rooted_dirt_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_SAND_PATH_REPLACEABLE = bind("under_water_sand_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_GRAVEL_PATH_REPLACEABLE = bind("under_water_gravel_path_replaceable");
	public static final TagKey<Block> UNDER_WATER_CLAY_PATH_REPLACEABLE = bind("under_water_clay_path_replaceable");
	public static final TagKey<Block> BEACH_CLAY_PATH_REPLACEABLE = bind("beach_clay_path_replaceable");
	public static final TagKey<Block> RIVER_GRAVEL_PATH_REPLACEABLE = bind("river_gravel_path_replaceable");
	public static final TagKey<Block> SAND_PATH_REPLACEABLE = bind("sand_path_replaceable");
	public static final TagKey<Block> GRAVEL_PATH_REPLACEABLE = bind("gravel_path_replaceable");
	public static final TagKey<Block> GRAVEL_CLEARING_REPLACEABLE = bind("gravel_clearing_replaceable");
	public static final TagKey<Block> GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE = bind("gravel_and_pale_moss_path_replaceable");
	public static final TagKey<Block> STONE_PATH_REPLACEABLE = bind("stone_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_REPLACEABLE = bind("packed_mud_path_replaceable");
	public static final TagKey<Block> MOSS_PATH_REPLACEABLE = bind("moss_path_replaceable");
	public static final TagKey<Block> OCEAN_MOSS_REPLACEABLE = bind("ocean_moss_replaceable");
	public static final TagKey<Block> SANDSTONE_PATH_REPLACEABLE = bind("sandstone_path_replaceable");
	public static final TagKey<Block> SMALL_COARSE_DIRT_PATH_REPLACEABLE = bind("small_coarse_dirt_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_BADLANDS_REPLACEABLE = bind("packed_mud_path_badlands_replaceable");
	public static final TagKey<Block> POLLEN_PLACEABLE = bind("pollen_placeable");
	public static final TagKey<Block> AUBURN_MOSS_REPLACEABLE = bind("auburn_moss_replaceable");
	public static final TagKey<Block> AUBURN_CREEPING_MOSS_PLACEABLE = bind("auburn_creeping_moss_placeable");

	public static final TagKey<Block> TERMITE_DISK_REPLACEABLE = bind("termite_disk_replaceable");

	public static final TagKey<Block> SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("scorched_sand_inner_replaceable");
	public static final TagKey<Block> SCORCHED_SAND_FEATURE_REPLACEABLE = bind("scorched_sand_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("red_scorched_sand_inner_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_REPLACEABLE = bind("red_scorched_sand_replaceable");
	public static final TagKey<Block> DIORITE_ICE_REPLACEABLE = bind("diorite_ice_replaceable");
	public static final TagKey<Block> CAVE_FRAGILE_ICE_REPLACEABLE = bind("cave_fragile_ice_replaceable");
	public static final TagKey<Block> ICICLE_REPLACEABLE = bind("icicle_replaceable");
	public static final TagKey<Block> NETHER_GEOTHERMAL_VENT_REPLACEABLE = bind("nether_geothermal_vent_replaceable");
	public static final TagKey<Block> OASIS_PATH_REPLACEABLE = bind("oasis_path_replaceable");
	public static final TagKey<Block> COARSE_DIRT_DISK_REPLACEABLE = bind("coarse_dirt_disk_replaceable");

	private static TagKey<Block> bind(String name) {
		return TagKey.create(Registries.BLOCK, WWConstants.id("feature/" + name));
	}

	private WWFeatureBlockTags() {}
}
