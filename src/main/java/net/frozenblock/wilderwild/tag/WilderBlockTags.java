/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public final class WilderBlockTags {
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
	public static final TagKey<Block> HOLLOWED_WARPED_STEMS = bind("hollowed_warped_stems");

	public static final TagKey<Block> HOLLOWED_BAOBAB_LOGS = bind("hollowed_baobab_logs");
	public static final TagKey<Block> HOLLOWED_CYPRESS_LOGS = bind("hollowed_cypress_logs");
	public static final TagKey<Block> HOLLOWED_PALM_LOGS = bind("hollowed_palm_logs");
	public static final TagKey<Block> HOLLOWED_MAPLE_LOGS = bind("hollowed_maple_logs");

	public static final TagKey<Block> NO_LIGHTNING_BLOCK_PARTICLES = bind("no_lightning_block_particles");
	public static final TagKey<Block> NO_LIGHTNING_SMOKE_PARTICLES = bind("no_lightning_smoke_particles");
	public static final TagKey<Block> CRAB_CAN_HIDE = bind("crab_can_hide");
	public static final TagKey<Block> OSTRICH_BEAK_BURYABLE = bind("ostrich_beak_buryable");
	public static final TagKey<Block> GEYSER_CAN_PASS_THROUGH = bind("geyser_can_pass_through");
	public static final TagKey<Block> GEYSER_CANNOT_PASS_THROUGH = bind("geyser_cannot_pass_through");
	public static final TagKey<Block> ANCIENT_CITY_BLOCKS = bind("ancient_city_blocks");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE_WORLDGEN = bind("sculk_slab_replaceable_worldgen");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE_WORLDGEN = bind("sculk_stair_replaceable_worldgen");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE_WORLDGEN = bind("sculk_wall_replaceable_worldgen");
	public static final TagKey<Block> SCULK_SLAB_REPLACEABLE = bind("sculk_slab_replaceable");
	public static final TagKey<Block> SCULK_STAIR_REPLACEABLE = bind("sculk_stair_replaceable");
	public static final TagKey<Block> SCULK_WALL_REPLACEABLE = bind("sculk_wall_replaceable");
	public static final TagKey<Block> ANCIENT_HORN_NON_COLLIDE = bind("ancient_horn_vibration_non_collide");
	public static final TagKey<Block> BAOBAB_LOGS = bind("baobab_logs");
	public static final TagKey<Block> CYPRESS_LOGS = bind("cypress_logs");
	public static final TagKey<Block> PALM_LOGS = bind("palm_logs");
	public static final TagKey<Block> MAPLE_LOGS = bind("maple_logs");
	public static final TagKey<Block> KILLS_TERMITE = bind("kills_termite");
	public static final TagKey<Block> TERMITE_BREAKABLE = bind("termite_breakable");
	public static final TagKey<Block> BLOCKS_TERMITE = bind("blocks_termite");
	public static final TagKey<Block> FIREFLY_HIDEABLE_BLOCKS = bind("firefly_hideable_blocks");
	public static final TagKey<Block> PACKED_MUD_REPLACEABLE = bind("packed_mud_replaceable");
	public static final TagKey<Block> BUSH_MAY_PLACE_ON = bind("bush_may_place_on");
	public static final TagKey<Block> SAND_POOL_REPLACEABLE = bind("sand_pool_replaceable");
	public static final TagKey<Block> RIVER_POOL_REPLACEABLE = bind("river_pool_replaceable");
	public static final TagKey<Block> SMALL_SPONGE_GROWS_ON = bind("small_sponge_grows_on");
	public static final TagKey<Block> FALLEN_TREE_PLACEABLE = bind("fallen_tree_placeable");
	public static final TagKey<Block> BASIN_REPLACEABLE = bind("basin_replaceable");
	public static final TagKey<Block> SPLITS_COCONUT = bind("splits_coconut");
	public static final TagKey<Block> STOPS_TUMBLEWEED = bind("stops_tumbleweed");
	public static final TagKey<Block> CATTAIL_PLACEABLE = bind("cattail_placeable");
	public static final TagKey<Block> CATTAIL_MUD_PLACEABLE = bind("cattail_mud_placeable");
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
	public static final TagKey<Block> STONE_PATH_REPLACEABLE = bind("stone_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_REPLACEABLE = bind("packed_mud_path_replaceable");
	public static final TagKey<Block> MOSS_PATH_REPLACEABLE = bind("moss_path_replaceable");
	public static final TagKey<Block> SANDSTONE_PATH_REPLACEABLE = bind("sandstone_path_replaceable");
	public static final TagKey<Block> SMALL_COARSE_DIRT_PATH_REPLACEABLE = bind("small_coarse_dirt_path_replaceable");
	public static final TagKey<Block> PACKED_MUD_PATH_BADLANDS_REPLACEABLE = bind("packed_mud_path_badlands_replaceable");
	public static final TagKey<Block> POLLEN_FEATURE_PLACEABLE = bind("pollen_feature_placeable");
	public static final TagKey<Block> TERMITE_DISC_REPLACEABLE = bind("termite_disc_replaceable");
	public static final TagKey<Block> TERMITE_DISC_BLOCKS = bind("termite_disc_blocks");
	public static final TagKey<Block> BLUE_NEMATOCYST_FEATURE_PLACEABLE = bind("blue_nematocyst_feature_placeable");
	public static final TagKey<Block> PURPLE_NEMATOCYST_FEATURE_PLACEABLE = bind("purple_nematocyst_feature_placeable");
	public static final TagKey<Block> SHELF_FUNGUS_FEATURE_PLACEABLE = bind("shelf_fungus_feature_placeable");
	public static final TagKey<Block> SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("scorched_sand_feature_inner_replaceable");
	public static final TagKey<Block> SCORCHED_SAND_FEATURE_REPLACEABLE = bind("scorched_sand_feature_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE = bind("red_scorched_sand_feature_inner_replaceable");
	public static final TagKey<Block> RED_SCORCHED_SAND_FEATURE_REPLACEABLE = bind("red_scorched_sand_feature_replaceable");
	public static final TagKey<Block> PACKED_ICE_REPLACEABLE = bind("packed_ice_replaceable");
	public static final TagKey<Block> MESOGLEA_PATH_REPLACEABLE = bind("mesoglea_path_replaceable");
	public static final TagKey<Block> MAGMA_REPLACEABLE = bind("magma_replaceable");
	public static final TagKey<Block> NETHER_GEYSER_REPLACEABLE = bind("nether_geyser_replaceable");
	public static final TagKey<Block> OASIS_PATH_REPLACEABLE = bind("oasis_path_replaceable");
	public static final TagKey<Block> COARSE_DIRT_DISK_REPLACEABLE = bind("coarse_dirt_disk_replaceable");
	public static final TagKey<Block> MESOGLEA = bind("mesoglea");
	public static final TagKey<Block> NEMATOCYSTS = bind("nematocysts");

	private WilderBlockTags() {
		throw new UnsupportedOperationException("WilderBlockTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Block> bind(@NotNull String path) {
		return TagKey.create(Registries.BLOCK, WilderConstants.id(path));
	}
}
