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
	public static final TagKey<Biome> MORE_FREQUENT_SUNKEN_SPAWNS = bind("entity/spawn/more_frequent_sunken_spawns");

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
	public static final TagKey<Biome> CORAL_VARIANT_SUNKEN = bind("entity/variant/coral_variant_sunken");

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





	public static final TagKey<Biome> LUKEWARM_WATER = bind("water_color/lukewarm");
	public static final TagKey<Biome> HOT_WATER = bind("water_color/hot");
	public static final TagKey<Biome> SNOWY_WATER = bind("water_color/snowy");
	public static final TagKey<Biome> FROZEN_WATER = bind("water_color/frozen");

	public static final TagKey<Biome> HAS_ABANDONED_CAMP_MAPLE_FOREST = bind("has_structure/abandoned_camp_maple_forest");

	public static final TagKey<Biome> HAS_SURFACE_ICICLES = bind("feature/has_surface_icicles");
	public static final TagKey<Biome> HAS_SURFACE_BRITTLE_ICE = bind("feature/has_surface_brittle_ice");
	public static final TagKey<Biome> HAS_CAVE_BRITTLE_ICE_PATCH = bind("feature/has_cave_brittle_ice_patch");

	// MATERIAL RULES
	public static final TagKey<Biome> BELOW_SURFACE_SNOW = bind("material_rule/below_surface_snow");
	public static final TagKey<Biome> BETA_BEACH_GRAVEL = bind("material_rule/gravel");
	public static final TagKey<Biome> BETA_BEACH_SAND = bind("material_rule/sand");
	public static final TagKey<Biome> BETA_BEACH_SAND_DEEP = bind("material_rule/beta_beach_sand_deep");
	public static final TagKey<Biome> COARSE_DIRT_PATH = bind("material_rule/coarse_dirt_path");
	public static final TagKey<Biome> COARSE_DIRT_PATH_PATCHY = bind("material_rule/coarse_dirt_path_patchy");
	public static final TagKey<Biome> MOSS_PATH_JUNGLE = bind("material_rule/moss_path_jungle");
	public static final TagKey<Biome> MOSS_PATH_OCEAN = bind("material_rule/moss_path_ocean");


	public static final TagKey<Biome> HAS_MOSS_PILE = bind("feature/has_moss_pile");
	public static final TagKey<Biome> HAS_STONE_PILE = bind("feature/has_stone_pile");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PILE_WITH_DISK = bind("feature/has_coarse_dirt_pile_with_disk");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PILE_WITH_DISK_RARE = bind("feature/has_coarse_dirt_pile_with_disk_rare");
	public static final TagKey<Biome> HAS_COARSE_DIRT_TRANSITION_DISK = bind("feature/has_coarse_dirt_transition_disk");
	public static final TagKey<Biome> HAS_PALE_MOSS_PILE = bind("feature/has_pale_moss_pile");
	public static final TagKey<Biome> HAS_GRAVEL_AND_PALE_MOSS_PATH = bind("feature/has_gravel_and_pale_moss_path");
	public static final TagKey<Biome> HAS_DECORATIVE_MUD = bind("feature/has_decorative_mud");
	public static final TagKey<Biome> HAS_PACKED_MUD_ORE = bind("feature/has_packed_mud_ore");

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
	public static final TagKey<Biome> HAS_NETHER_GEOTHERMAL_VENT = bind("feature/has_nether_geothermal_vent");
	public static final TagKey<Biome> HAS_NETHER_LAVA_GEOTHERMAL_VENT = bind("feature/has_nether_lava_geothermal_vent");

	public static final TagKey<Biome> HAS_TAIGA_FOREST_ROCK = bind("feature/has_taiga_forest_rock");
	public static final TagKey<Biome> HAS_SNOW_PILE = bind("feature/has_snow_pile");
	public static final TagKey<Biome> HAS_MOSS_LAKE = bind("feature/has_moss_lake");
	public static final TagKey<Biome> HAS_MOSS_LAKE_RARE = bind("feature/has_moss_lake_rare");
	public static final TagKey<Biome> HAS_MOSS_BASIN = bind("feature/has_moss_basin");
	public static final TagKey<Biome> HAS_PODZOL_BASIN = bind("feature/has_podzol_basin");
	public static final TagKey<Biome> HAS_PACKED_MUD_PATH = bind("feature/has_packed_mud_path");
	public static final TagKey<Biome> HAS_MUD_BASIN = bind("feature/has_mud_basin");
	public static final TagKey<Biome> HAS_MUD_PILE = bind("feature/has_mud_pile");
	public static final TagKey<Biome> HAS_MUD_LAKE = bind("feature/has_mud_lake");

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
