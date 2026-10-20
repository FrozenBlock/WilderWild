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
import net.minecraft.world.level.biome.Biome;

public final class WWBiomeTags {
	public static final TagKey<Biome> HAS_FIREFLY = bind("entity/spawn/firefly");
	public static final TagKey<Biome> HAS_BUTTERFLY = bind("entity/spawn/butterfly");
	public static final TagKey<Biome> BUTTERFLY_COMMON_SPAWN = bind("entity/spawn/butterfly_common_spawn");
	public static final TagKey<Biome> BUTTERFLY_VERY_RARE_SPAWN = bind("entity/spawn/butterfly_very_rare_spawn");
	public static final TagKey<Biome> HAS_JELLYFISH = bind("entity/spawn/jellyfish");
	public static final TagKey<Biome> JELLYFISH_COMMON_SPAWN = bind("entity/spawn/jellyfish_common_spawn");
	public static final TagKey<Biome> HAS_MOOBLOOM = bind("entity/spawn/moobloom");
	public static final TagKey<Biome> HAS_CRAB = bind("entity/spawn/crab");
	public static final TagKey<Biome> HAS_OSTRICH = bind("entity/spawn/ostrich");
	public static final TagKey<Biome> HAS_ZOMBIE_OSTRICH = bind("entity/spawn/zombie_ostrich");
	public static final TagKey<Biome> HAS_PENGUIN = bind("entity/spawn/penguin");
	public static final TagKey<Biome> HAS_TUMBLEWEED_ENTITY = bind("entity/spawn/tumbleweed");

	public static final TagKey<Biome> BUTTERFLY_MONARCH = bind("entity/variant/butterfly_monarch");
	public static final TagKey<Biome> BUTTERFLY_RED_LACEWING = bind("entity/variant/butterfly_red_lacewing");
	public static final TagKey<Biome> BUTTERFLY_MARBLED = bind("entity/variant/butterfly_marbled");
	public static final TagKey<Biome> BUTTERFLY_MORPHO_BLUE = bind("entity/variant/butterfly_morpho_blue");
	public static final TagKey<Biome> BUTTERFLY_GREEN_HAIRSTREAK = bind("entity/variant/butterfly_green_hairstreak");
	public static final TagKey<Biome> BUTTERFLY_CLOUDED_YELLOW = bind("entity/variant/butterfly_clouded_yellow");
	public static final TagKey<Biome> BUTTERFLY_DUSKWING = bind("entity/variant/butterfly_duskwing");
	public static final TagKey<Biome> BLUE_JELLYFISH = bind("entity/variant/blue_jellyfish");
	public static final TagKey<Biome> LIME_JELLYFISH = bind("entity/variant/lime_jellyfish");
	public static final TagKey<Biome> PINK_JELLYFISH = bind("entity/variant/pink_jellyfish");
	public static final TagKey<Biome> RED_JELLYFISH = bind("entity/variant/red_jellyfish");
	public static final TagKey<Biome> YELLOW_JELLYFISH = bind("entity/variant/yellow_jellyfish");
	public static final TagKey<Biome> PEARLESCENT_JELLYFISH = bind("entity/variant/pearlescent_jellyfish");

	public static final TagKey<Biome> WOLF_ASHEN = bind("entity/variant/wolf_ashen");
	public static final TagKey<Biome> WOLF_WOODS = bind("entity/variant/wolf_woods");
	public static final TagKey<Biome> WOLF_PALE = bind("entity/variant/wolf_pale");
	public static final TagKey<Biome> WOLF_BLACK = bind("entity/variant/wolf_black");
	public static final TagKey<Biome> WOLF_CHESTNUT = bind("entity/variant/wolf_chestnut");

	public static final TagKey<Biome> PRODUCES_BARNACLES_FROM_BONEMEAL = bind("produces_barnacles_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SPONGE_BUDS_FROM_BONEMEAL = bind("produces_sponge_buds_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SEA_ANEMONE_FROM_BONEMEAL = bind("produces_sea_anemone_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SEA_WHIPS_FROM_BONEMEAL = bind("produces_sea_whips_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_TUBE_WORMS_FROM_BONEMEAL = bind("produces_tube_worms_from_bonemeal");

	public static final TagKey<Biome> BETA_BEACH_GRAVEL = bind("beta_beach/gravel");
	public static final TagKey<Biome> BETA_BEACH_SAND = bind("beta_beach/sand");
	public static final TagKey<Biome> BETA_BEACH_MULTI_LAYER_SAND = bind("beta_beach/multi_layer_sand");

	public static final TagKey<Biome> BELOW_SURFACE_SNOW = bind("below_surface_snow");

	public static final TagKey<Biome> LUKEWARM_WATER = bind("water_color/lukewarm");
	public static final TagKey<Biome> HOT_WATER = bind("water_color/hot");
	public static final TagKey<Biome> SNOWY_WATER = bind("water_color/snowy");
	public static final TagKey<Biome> FROZEN_WATER = bind("water_color/frozen");

	public static final TagKey<Biome> HAS_ABANDONED_CAMP_MAPLE_FOREST = bind("has_structure/abandoned_camp_maple_forest");

	public static final TagKey<Biome> HAS_FROZEN_FOREST_GRASS = bind("feature/has_frozen_forest_grass");
	public static final TagKey<Biome> HAS_FROZEN_NORMAL_GRASS = bind("feature/has_frozen_normal_grass");
	public static final TagKey<Biome> HAS_FROZEN_PLAIN_GRASS = bind("feature/has_frozen_plain_grass");
	public static final TagKey<Biome> HAS_FROZEN_TAIGA_GRASS = bind("feature/has_frozen_taiga_grass");
	public static final TagKey<Biome> HAS_FROZEN_TAIGA_2_GRASS = bind("feature/has_frozen_taiga_2_grass");
	public static final TagKey<Biome> HAS_FROZEN_PLAIN_TALL_GRASS = bind("feature/has_frozen_plain_tall_grass");
	public static final TagKey<Biome> HAS_FROZEN_LARGE_FERNS = bind("feature/has_frozen_large_ferns");
	public static final TagKey<Biome> HAS_FROZEN_BUSH = bind("feature/has_frozen_bush");

	public static final TagKey<Biome> HAS_LEAF_LITTER = bind("feature/has_leaf_litter");
	public static final TagKey<Biome> HAS_UNCOMMON_LEAF_LITTER = bind("feature/has_uncommon_leaf_litter");
	public static final TagKey<Biome> HAS_SPRUCE_LEAF_LITTER = bind("feature/has_spruce_leaf_litter");
	public static final TagKey<Biome> HAS_DARK_OAK_LEAF_LITTER = bind("feature/has_dark_oak_leaf_litter");
	public static final TagKey<Biome> HAS_PALE_OAK_LEAF_LITTER = bind("feature/has_pale_oak_leaf_litter");

	public static final TagKey<Biome> HAS_SURFACE_ICICLES = bind("feature/has_surface_icicles");
	public static final TagKey<Biome> HAS_SURFACE_BRITTLE_ICE = bind("feature/has_surface_brittle_ice");
	public static final TagKey<Biome> HAS_CAVE_BRITTLE_ICE_PATCH = bind("feature/has_cave_brittle_ice_patch");
	public static final TagKey<Biome> HAS_TUMBLEWEED_PLANT = bind("feature/has_tumbleweed_plant");

	public static final TagKey<Biome> FOREST_GRASS = bind("feature/forest_grass");
	public static final TagKey<Biome> FOREST_GRASS_LIGHT = bind("feature/forest_grass_light");
	public static final TagKey<Biome> FOREST_GRASS_BIRCH = bind("feature/forest_grass_birch");
	public static final TagKey<Biome> HAS_HUGE_RED_MUSHROOM = bind("feature/has_huge_red_mushroom");
	public static final TagKey<Biome> HAS_HUGE_BROWN_MUSHROOM = bind("feature/has_huge_brown_mushroom");
	public static final TagKey<Biome> HAS_HUGE_MUSHROOMS = bind("feature/has_huge_mushrooms");
	public static final TagKey<Biome> HAS_HUGE_MUSHROOMS_RARE = bind("feature/has_huge_mushrooms_rare");
	public static final TagKey<Biome> HAS_COMMON_BROWN_MUSHROOM = bind("feature/has_common_brown_mushroom");
	public static final TagKey<Biome> HAS_COMMON_RED_MUSHROOM = bind("feature/has_common_red_mushroom");
	public static final TagKey<Biome> HAS_PALE_MUSHROOM = bind("feature/has_pale_mushroom");
	public static final TagKey<Biome> HAS_HUGE_PALE_MUSHROOM = bind("feature/has_huge_pale_mushroom");
	public static final TagKey<Biome> HAS_SWAMP_MUSHROOM = bind("feature/has_swamp_mushroom");
	public static final TagKey<Biome> HAS_BIG_MUSHROOM_PATCH = bind("feature/has_big_mushroom_patch");

	public static final TagKey<Biome> HAS_CLOVERS = bind("feature/has_clovers");
	public static final TagKey<Biome> HAS_CLOVERS_SPARSE = bind("feature/has_clovers_sparse");

	public static final TagKey<Biome> HAS_POLLEN = bind("feature/has_pollen");
	public static final TagKey<Biome> HAS_RAINFOREST_MUSHROOM = bind("feature/has_rainforest_mushroom");
	public static final TagKey<Biome> HAS_MIXED_MUSHROOM = bind("feature/has_mixed_mushroom");

	public static final TagKey<Biome> HAS_BERRY_PATCH = bind("feature/has_berry_patch");
	public static final TagKey<Biome> HAS_SHRUB = bind("feature/has_shrub");

	public static final TagKey<Biome> HAS_MYCELIUM_GROWTH = bind("feature/has_mycelium_growth");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS = bind("feature/has_large_fern_and_grass");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS_RARE = bind("feature/has_large_fern_and_grass_rare");
	public static final TagKey<Biome> HAS_NEW_RARE_GRASS = bind("feature/has_new_rare_grass");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_TALL_GRASS = bind("feature/has_flower_field_tall_grass");
	public static final TagKey<Biome> HAS_SWAMP_FERN = bind("feature/has_swamp_fern");
	public static final TagKey<Biome> HAS_DENSE_FERN = bind("feature/has_dense_fern");
	public static final TagKey<Biome> HAS_SWAMP_TALL_GRASS = bind("feature/has_swamp_tall_grass");
	public static final TagKey<Biome> HAS_DENSE_TALL_GRASS = bind("feature/has_dense_tall_grass");
	public static final TagKey<Biome> HAS_JUNGLE_SHRUB = bind("feature/has_jungle_shrub");
	public static final TagKey<Biome> HAS_SPARSE_SHRUB = bind("feature/has_sparse_shrub");
	public static final TagKey<Biome> HAS_ARID_SHRUB = bind("feature/has_arid_shrub");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_SHRUB = bind("feature/has_flower_field_shrub");
	public static final TagKey<Biome> HAS_RAINFOREST_SHRUB = bind("feature/has_rainforest_shrub");
	public static final TagKey<Biome> HAS_BADLANDS_SAND_SHRUB = bind("feature/has_badlands_sand_shrub");
	public static final TagKey<Biome> HAS_BADLANDS_TERRACOTTA_SHRUB = bind("feature/has_badlands_terracotta_shrub");
	public static final TagKey<Biome> HAS_WOODED_BADLANDS_SHRUB = bind("feature/has_wooded_badlands_shrub");
	public static final TagKey<Biome> HAS_BADLANDS_RARE_SAND_SHRUB = bind("feature/has_badlands_rare_sand_shrub");
	public static final TagKey<Biome> HAS_DESERT_SHRUB = bind("feature/has_desert_shrub");
	public static final TagKey<Biome> HAS_OASIS_SHRUB = bind("feature/has_oasis_shrub");
	public static final TagKey<Biome> HAS_TALL_CACTUS = bind("feature/has_tall_cactus");
	public static final TagKey<Biome> HAS_PRICKLY_PEAR = bind("feature/has_prickly_pear");
	public static final TagKey<Biome> HAS_RARE_PRICKLY_PEAR = bind("feature/has_rare_prickly_pear");
	public static final TagKey<Biome> HAS_TALL_BADLANDS_CACTUS = bind("feature/has_tall_badlands_cactus");
	public static final TagKey<Biome> HAS_DRY_GRASS_BADLANDS = bind("feature/has_dry_grass_badlands");
	public static final TagKey<Biome> HAS_DRY_GRASS_DESERT = bind("feature/has_dry_grass_desert");
	public static final TagKey<Biome> HAS_DRY_GRASS_BEACH = bind("feature/has_dry_grass_beach");
	public static final TagKey<Biome> HAS_DRY_GRASS_BETA_BEACH = bind("feature/has_dry_grass_beta_beach");
	public static final TagKey<Biome> HAS_FIREFLY_BUSH = bind("feature/has_firefly_bush");
	public static final TagKey<Biome> HAS_FIREFLY_BUSH_SWAMP = bind("feature/has_firefly_bush_swamp");
	public static final TagKey<Biome> HAS_COMMON_PUMPKIN = bind("feature/has_common_pumpkin");
	public static final TagKey<Biome> HAS_UNCOMMON_PUMPKIN = bind("feature/has_uncommon_pumpkin");
	public static final TagKey<Biome> HAS_MOSS_PILE = bind("feature/has_moss_pile");
	public static final TagKey<Biome> HAS_STONE_PILE = bind("feature/has_stone_pile");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PILE_WITH_DISK = bind("feature/has_coarse_dirt_pile_with_disk");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PILE_WITH_DISK_RARE = bind("feature/has_coarse_dirt_pile_with_disk_rare");
	public static final TagKey<Biome> HAS_COARSE_DIRT_TRANSITION_DISK = bind("feature/has_coarse_dirt_transition_disk");
	public static final TagKey<Biome> HAS_PALE_MOSS_PILE = bind("feature/has_pale_moss_pile");
	public static final TagKey<Biome> HAS_GRAVEL_AND_PALE_MOSS_PATH = bind("feature/has_gravel_and_pale_moss_path");
	public static final TagKey<Biome> HAS_DECORATIVE_MUD = bind("feature/has_decorative_mud");
	public static final TagKey<Biome> HAS_PACKED_MUD_ORE = bind("feature/has_packed_mud_ore");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PATH = bind("feature/has_coarse_dirt_path");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PATH_SMALL = bind("feature/has_coarse_dirt_path_small");
	public static final TagKey<Biome> HAS_PACKED_MUD_PATH_BADLANDS = bind("feature/has_packed_mud_path_badlands");
	public static final TagKey<Biome> HAS_SANDSTONE_PATH = bind("feature/has_sandstone_path");
	public static final TagKey<Biome> HAS_COARSE_DIRT_CLEARING = bind("feature/has_coarse_dirt_clearing");
	public static final TagKey<Biome> HAS_GRAVEL_CLEARING = bind("feature/has_gravel_clearing");
	public static final TagKey<Biome> HAS_ROOTED_DIRT_CLEARING = bind("feature/has_rooted_dirt_clearing");
	public static final TagKey<Biome> HAS_SCORCHED_SAND = bind("feature/has_scorched_sand");
	public static final TagKey<Biome> HAS_SCORCHED_RED_SAND = bind("feature/has_scorched_red_sand");
	public static final TagKey<Biome> HAS_SMALL_SAND_TRANSITION = bind("feature/has_small_sand_transition");
	public static final TagKey<Biome> HAS_SAND_TRANSITION = bind("feature/has_sand_transition");
	public static final TagKey<Biome> HAS_RED_SAND_TRANSITION = bind("feature/has_red_sand_transition");
	public static final TagKey<Biome> HAS_STONE_TRANSITION = bind("feature/has_stone_transition");
	public static final TagKey<Biome> HAS_BETA_BEACH_SAND_TRANSITION = bind("feature/has_beta_beach_sand_transition");
	public static final TagKey<Biome> HAS_BETA_BEACH_GRAVEL_TRANSITION = bind("feature/has_beta_beach_gravel_transition");
	public static final TagKey<Biome> HAS_GRAVEL_TRANSITION = bind("feature/has_gravel_transition");
	public static final TagKey<Biome> HAS_MUD_TRANSITION = bind("feature/has_mud_transition");
	public static final TagKey<Biome> HAS_TERMITE_MOUND = bind("feature/has_termite_mound");
	public static final TagKey<Biome> HAS_NETHER_GEOTHERMAL_VENT = bind("feature/has_nether_geothermal_vent");
	public static final TagKey<Biome> HAS_NETHER_LAVA_GEOTHERMAL_VENT = bind("feature/has_nether_lava_geothermal_vent");

	public static final TagKey<Biome> HAS_TAIGA_FOREST_ROCK = bind("feature/has_taiga_forest_rock");
	public static final TagKey<Biome> HAS_SNOW_PILE = bind("feature/has_snow_pile");
	public static final TagKey<Biome> HAS_MOSS_PATH = bind("feature/has_moss_path");
	public static final TagKey<Biome> HAS_MOSS_LAKE = bind("feature/has_moss_lake");
	public static final TagKey<Biome> HAS_MOSS_LAKE_RARE = bind("feature/has_moss_lake_rare");
	public static final TagKey<Biome> HAS_MOSS_BASIN = bind("feature/has_moss_basin");
	public static final TagKey<Biome> HAS_PODZOL_BASIN = bind("feature/has_podzol_basin");
	public static final TagKey<Biome> HAS_MOSS_CARPET = bind("feature/has_moss_carpet");
	public static final TagKey<Biome> HAS_PACKED_MUD_PATH = bind("feature/has_packed_mud_path");
	public static final TagKey<Biome> HAS_MUD_BASIN = bind("feature/has_mud_basin");
	public static final TagKey<Biome> HAS_MUD_PILE = bind("feature/has_mud_pile");
	public static final TagKey<Biome> HAS_MUD_LAKE = bind("feature/has_mud_lake");
	public static final TagKey<Biome> HAS_CREEPING_AUBURN_MOSS = bind("feature/has_auburn_creeping_moss");
	public static final TagKey<Biome> HAS_AUBURN_MOSS = bind("feature/has_auburn_moss");

	public static final TagKey<Biome> HAS_WATER_GRASS = bind("feature/has_water_grass");
	public static final TagKey<Biome> HAS_RARE_COARSE = bind("feature/has_rare_coarse");
	public static final TagKey<Biome> HAS_RARE_GRAVEL = bind("feature/has_rare_gravel");
	public static final TagKey<Biome> HAS_RARE_STONE = bind("feature/has_rare_stone");
	public static final TagKey<Biome> HAS_CLAY_PATH = bind("feature/has_clay_path");

	public static final TagKey<Biome> WILDER_WILD_BIOMES = bind("wilder_wild_biomes");

	public static final TagKey<Biome> MUSIC_PITCH_SHIFT_DYING_FOREST = bind("music_pitch_shift_dying_forest");

	public static final TagKey<Biome> HAS_FOREST_MUSIC = bind("music_pool/has_forest_music");
	public static final TagKey<Biome> HAS_FLOWER_FOREST_MUSIC = bind("music_pool/has_flower_forest_music");
	public static final TagKey<Biome> HAS_DAPPLED_FOREST_MUSIC = bind("music_pool/has_dappled_forest_music");
	public static final TagKey<Biome> HAS_SNOWY_MUSIC = bind("music_pool/has_snowy_music");
	public static final TagKey<Biome> HAS_LUSH_CAVES_MUSIC = bind("music_pool/has_lush_caves_music");
	public static final TagKey<Biome> HAS_DRIPSTONE_CAVES_MUSIC = bind("music_pool/has_dripstone_caves_music");
	public static final TagKey<Biome> HAS_TAIGA_MUSIC = bind("music_pool/has_taiga_music");
	public static final TagKey<Biome> HAS_OLD_GROWTH_TAIGA_MUSIC = bind("music_pool/has_old_growth_taiga_music");
	public static final TagKey<Biome> HAS_CHERRY_GROVE_MUSIC = bind("music_pool/has_cherry_grove_music");
	public static final TagKey<Biome> HAS_GROVE_MUSIC = bind("music_pool/has_grove_music");
	public static final TagKey<Biome> HAS_JUNGLE_MUSIC = bind("music_pool/has_jungle_music");
	public static final TagKey<Biome> HAS_BAMBOO_JUNGLE_MUSIC = bind("music_pool/has_bamboo_jungle_music");
	public static final TagKey<Biome> HAS_SPARSE_JUNGLE_MUSIC = bind("music_pool/has_sparse_jungle_music");
	public static final TagKey<Biome> HAS_BADLANDS_MUSIC = bind("music_pool/has_badlands_music");
	public static final TagKey<Biome> HAS_DESERT_MUSIC = bind("music_pool/has_desert_music");
	public static final TagKey<Biome> HAS_OCEAN_MUSIC = bind("music_pool/has_ocean_music");
	public static final TagKey<Biome> HAS_FROZEN_OCEAN_MUSIC = bind("music_pool/has_frozen_ocean_music");
	public static final TagKey<Biome> HAS_WARM_OCEAN_MUSIC = bind("music_pool/has_warm_ocean_music");
	public static final TagKey<Biome> HAS_ICE_CAVES_MUSIC = bind("music_pool/has_ice_caves_music");
	public static final TagKey<Biome> HAS_MAGMATIC_CAVES_MUSIC = bind("music_pool/has_magmatic_caves_music");
	public static final TagKey<Biome> HAS_MESOGLEA_CAVES_MUSIC = bind("music_pool/has_mesoglea_caves_music");
	public static final TagKey<Biome> HAS_MAPLE_FOREST_MUSIC = bind("music_pool/has_maple_forest_music");
	public static final TagKey<Biome> HAS_DYING_FOREST_MUSIC = bind("music_pool/has_dying_forest_music");
	public static final TagKey<Biome> HAS_SNOWY_DYING_FOREST_MUSIC = bind("music_pool/has_snowy_dying_forest_music");

	private static TagKey<Biome> bind(String name) {
		return TagKey.create(Registries.BIOME, WWConstants.id(name));
	}

	private WWBiomeTags() {}
}
