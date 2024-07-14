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
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class WilderBiomeTags {
	public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = bind("firefly_spawnable_during_day");
	public static final TagKey<Biome> FIREFLY_SPAWNABLE_CAVE = bind("firefly_spawnable_cave");
	public static final TagKey<Biome> FIREFLY_SPAWNABLE = bind("firefly_spawnable");
	public static final TagKey<Biome> ABANDONED_CABIN_HAS_STRUCTURE = bind("has_structure/abandoned_cabin");
	public static final TagKey<Biome> HAS_JELLYFISH = bind("has_jellyfish");
	public static final TagKey<Biome> PEARLESCENT_JELLYFISH = bind("pearlescent_jellyfish");
	public static final TagKey<Biome> JELLYFISH_SPECIAL_SPAWN = bind("jellyfish_special_spawn");
	public static final TagKey<Biome> HAS_CRAB = bind("has_crab");
	public static final TagKey<Biome> HAS_COMMON_CRAB = bind("has_common_crab");
	public static final TagKey<Biome> HAS_OSTRICH = bind("has_ostrich");
	public static final TagKey<Biome> HAS_TUMBLEWEED_ENTITY = bind("has_tumbleweed_entity");
	public static final TagKey<Biome> HAS_TUMBLEWEED_PLANT = bind("has_tumbleweed_plant");
	public static final TagKey<Biome> NO_POOLS = bind("no_pools");
	public static final TagKey<Biome> NON_FROZEN_PLAINS = bind("non_frozen_plains");
	public static final TagKey<Biome> SWAMP_TREES = bind("swamp_trees");
	public static final TagKey<Biome> SHORT_TAIGA = bind("short_taiga");
	public static final TagKey<Biome> TALL_PINE_TAIGA = bind("tall_pine_taiga");
	public static final TagKey<Biome> TALL_SPRUCE_TAIGA = bind("tall_spruce_taiga");
	public static final TagKey<Biome> GROVE = bind("grove");
	public static final TagKey<Biome> NORMAL_SAVANNA = bind("normal_savanna");
	public static final TagKey<Biome> WINDSWEPT_SAVANNA = bind("windswept_savanna");
	public static final TagKey<Biome> SNOWY_PLAINS = bind("snowy_plains");
	public static final TagKey<Biome> WINDSWEPT_HILLS = bind("windswept_hills");
	public static final TagKey<Biome> WINDSWEPT_FOREST = bind("windswept_forest");
	public static final TagKey<Biome> RAINFOREST = bind("rainforest");
	public static final TagKey<Biome> HAS_FALLEN_BIRCH_TREES = bind("has_fallen_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_CHERRY_TREES = bind("has_fallen_cherry_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_BIRCH_TREES = bind("has_fallen_oak_and_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_SPRUCE_TREES = bind("has_fallen_oak_and_spruce_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_CYPRESS_TREES = bind("has_fallen_oak_and_cypress_trees");
	public static final TagKey<Biome> HAS_MOSSY_FALLEN_MIXED_TREES = bind("has_mossy_fallen_mixed_trees");
	public static final TagKey<Biome> HAS_MOSSY_FALLEN_OAK_AND_BIRCH = bind("has_mossy_fallen_oak_and_birch");
	public static final TagKey<Biome> HAS_FALLEN_ACACIA_AND_OAK = bind("has_fallen_acacia_and_oak");
	public static final TagKey<Biome> HAS_FALLEN_PALM = bind("has_fallen_palm");
	public static final TagKey<Biome> HAS_FALLEN_PALM_RARE = bind("has_fallen_palm_rare");
	public static final TagKey<Biome> HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK = bind("has_fallen_palm_and_jungle_and_oak");
	public static final TagKey<Biome> HAS_FALLEN_LARGE_JUNGLE = bind("has_fallen_large_jungle");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_LARGE_JUNGLE = bind("has_common_fallen_large_jungle");
	public static final TagKey<Biome> HAS_FALLEN_DARK_OAK = bind("has_fallen_dark_oak");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_DARK_OAK = bind("has_common_fallen_dark_oak");
	public static final TagKey<Biome> HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST = bind("has_fallen_birch_and_oak_dark_forest");
	public static final TagKey<Biome> HAS_FALLEN_SPRUCE_TREES = bind("has_fallen_spruce_trees");
	public static final TagKey<Biome> HAS_CLEAN_FALLEN_SPRUCE_TREES = bind("has_clean_fallen_spruce_trees");
	public static final TagKey<Biome> HAS_FALLEN_LARGE_SPRUCE = bind("has_fallen_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_LARGE_SPRUCE = bind("has_common_fallen_large_spruce");
	public static final TagKey<Biome> HAS_CLEAN_FALLEN_LARGE_SPRUCE = bind("has_clean_fallen_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE = bind("has_common_clean_fallen_large_spruce");
	public static final TagKey<Biome> HAS_FALLEN_SWAMP_OAK_TREES = bind("has_fallen_swamp_oak_trees");
	public static final TagKey<Biome> HAS_FALLEN_MANGROVE_TREES = bind("has_fallen_mangrove_trees");
	public static final TagKey<Biome> CHERRY_TREES = bind("cherry_trees");
	public static final TagKey<Biome> DARK_FOREST = bind("dark_forest");
	public static final TagKey<Biome> MEADOW = bind("meadow");
	public static final TagKey<Biome> OAK_SAPLINGS_GROW_SWAMP_VARIANT = bind("oak_saplings_grow_swamp_variant");
	public static final TagKey<Biome> FOREST_GRASS = bind("forest_grass");
	public static final TagKey<Biome> PLAINS_GRASS = bind("plains_grass");
	public static final TagKey<Biome> HAS_SMALL_SPONGE = bind("has_small_sponge");
	public static final TagKey<Biome> HAS_SMALL_SPONGE_RARE = bind("has_small_sponge_rare");
	public static final TagKey<Biome> HAS_HUGE_RED_MUSHROOM = bind("has_huge_red_mushroom");
	public static final TagKey<Biome> HAS_HUGE_BROWN_MUSHROOM = bind("has_huge_brown_mushroom");
	public static final TagKey<Biome> HAS_BIG_MUSHROOMS = bind("has_big_mushrooms");
	public static final TagKey<Biome> HAS_COMMON_BROWN_MUSHROOM = bind("has_common_brown_mushroom");
	public static final TagKey<Biome> HAS_COMMON_RED_MUSHROOM = bind("has_common_red_mushroom");
	public static final TagKey<Biome> HAS_SWAMP_MUSHROOM = bind("has_swamp_mushroom");
	public static final TagKey<Biome> HAS_BIG_MUSHROOM_PATCH = bind("has_big_mushroom_patch");
	public static final TagKey<Biome> HAS_DATURA = bind("has_datura");
	public static final TagKey<Biome> HAS_CARNATION = bind("has_carnation");
	public static final TagKey<Biome> HAS_CATTAIL = bind("has_cattail");
	public static final TagKey<Biome> HAS_CATTAIL_UNCOMMON = bind("has_cattail_uncommon");
	public static final TagKey<Biome> HAS_CATTAIL_COMMON = bind("has_cattail_common");
	public static final TagKey<Biome> HAS_SEEDING_DANDELION = bind("has_seeding_dandelion");
	public static final TagKey<Biome> HAS_COMMON_SEEDING_DANDELION = bind("has_common_seeding_dandelion");
	public static final TagKey<Biome> HAS_RARE_SEEDING_DANDELION = bind("has_rare_seeding_dandelion");
	public static final TagKey<Biome> HAS_MILKWEED = bind("has_milkweed");
	public static final TagKey<Biome> CHERRY_FLOWERS = bind("cherry_flowers");
	public static final TagKey<Biome> HAS_SUNFLOWER_PLAINS_FLOWERS = bind("has_sunflower_plains_flowers");
	public static final TagKey<Biome> HAS_PALMS = bind("has_palms");
	public static final TagKey<Biome> HAS_WARM_BEACH_PALMS = bind("has_warm_beach_palms");
	public static final TagKey<Biome> HAS_SHORT_SPRUCE = bind("has_short_spruce");
	public static final TagKey<Biome> HAS_SHORT_MEGA_SPRUCE = bind("has_short_mega_spruce");
	public static final TagKey<Biome> HAS_SHORT_MEGA_SPRUCE_SNOWY = bind("has_short_mega_spruce_snowy");
	public static final TagKey<Biome> HAS_BIG_COARSE_SHRUB = bind("has_big_coarse_shrub");
	public static final TagKey<Biome> HAS_SNAPPED_OAK = bind("has_snapped_oak");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH = bind("has_snapped_birch");
	public static final TagKey<Biome> HAS_SNAPPED_SPRUCE = bind("has_snapped_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_SPRUCE_SNOWY = bind("has_snapped_spruce_snowy");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_SPRUCE = bind("has_snapped_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_SNAPPED_LARGE_SPRUCE = bind("has_common_snapped_large_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_SPRUCE_SNOWY = bind("has_snapped_large_spruce_snowy");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_OAK = bind("has_snapped_birch_and_oak");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = bind("has_snapped_birch_and_oak_and_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_SPRUCE = bind("has_snapped_birch_and_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_CYPRESS = bind("has_snapped_cypress");
	public static final TagKey<Biome> HAS_SNAPPED_JUNGLE = bind("has_snapped_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_JUNGLE = bind("has_snapped_large_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_JUNGLE = bind("has_snapped_birch_and_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_ACACIA = bind("has_snapped_acacia");
	public static final TagKey<Biome> HAS_SNAPPED_ACACIA_AND_OAK = bind("has_snapped_acacia_and_oak");
	public static final TagKey<Biome> HAS_SNAPPED_CHERRY = bind("has_snapped_cherry");
	public static final TagKey<Biome> HAS_SNAPPED_DARK_OAK = bind("has_snapped_dark_oak");
	public static final TagKey<Biome> HAS_POLLEN = bind("has_pollen");
	public static final TagKey<Biome> HAS_FIELD_FLOWERS = bind("has_field_flowers");
	public static final TagKey<Biome> HAS_RED_SHELF_FUNGUS = bind("has_red_shelf_fungus");
	public static final TagKey<Biome> HAS_BROWN_SHELF_FUNGUS = bind("has_brown_shelf_fungus");
	public static final TagKey<Biome> HAS_RAINFOREST_MUSHROOM = bind("has_rainforest_mushroom");
	public static final TagKey<Biome> HAS_MIXED_MUSHROOM = bind("has_mixed_mushroom");
	public static final TagKey<Biome> HAS_GLORY_OF_THE_SNOW = bind("has_glory_of_the_snow");
	public static final TagKey<Biome> HAS_FLOWERING_WATER_LILY = bind("has_flowering_water_lily");
	public static final TagKey<Biome> HAS_BERRY_PATCH = bind("has_berry_patch");
	public static final TagKey<Biome> HAS_BUSH = bind("has_bush");
	public static final TagKey<Biome> HAS_FOREST_SHRUB = bind("has_forest_shrub");
	public static final TagKey<Biome> HAS_SHRUB = bind("has_shrub");
	public static final TagKey<Biome> HAS_PLAINS_FLOWERS = bind("has_plains_flowers");
	public static final TagKey<Biome> HAS_CYPRESS_FLOWERS = bind("has_cypress_flowers");
	public static final TagKey<Biome> HAS_RARE_MILKWEED = bind("has_rare_milkweed");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS = bind("has_large_fern_and_grass");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS_RARE = bind("has_large_fern_and_grass_rare");
	public static final TagKey<Biome> HAS_NEW_RARE_GRASS = bind("has_new_rare_grass");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_TALL_GRASS = bind("has_flower_field_tall_grass");
	public static final TagKey<Biome> HAS_DENSE_FERN = bind("has_dense_fern");
	public static final TagKey<Biome> HAS_DENSE_TALL_GRASS = bind("has_dense_tall_grass");
	public static final TagKey<Biome> HAS_SPARSE_JUNGLE_FLOWERS = bind("has_sparse_jungle_flowers");
	public static final TagKey<Biome> HAS_JUNGLE_FLOWERS = bind("has_jungle_flowers");
	public static final TagKey<Biome> HAS_JUNGLE_BUSH = bind("has_jungle_bush");
	public static final TagKey<Biome> HAS_SPARSE_BUSH = bind("has_sparse_bush");
	public static final TagKey<Biome> HAS_ARID_BUSH = bind("has_arid_bush");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_BUSH = bind("has_flower_field_bush");
	public static final TagKey<Biome> HAS_RAINFOREST_BUSH = bind("has_rainforest_bush");
	public static final TagKey<Biome> HAS_BADLANDS_SAND_BUSH = bind("has_badlands_sand_bush");
	public static final TagKey<Biome> HAS_BADLANDS_TERRACOTTA_BUSH = bind("has_badlands_terracotta_bush");
	public static final TagKey<Biome> HAS_WOODED_BADLANDS_TERRACOTTA_BUSH = bind("has_wooded_badlands_terracotta_bush");
	public static final TagKey<Biome> HAS_BADLANDS_RARE_SAND_BUSH = bind("has_badlands_rare_sand_bush");
	public static final TagKey<Biome> HAS_DESERT_BUSH = bind("has_desert_bush");
	public static final TagKey<Biome> HAS_OASIS_BUSH = bind("has_oasis_bush");
	public static final TagKey<Biome> HAS_TALL_CACTUS = bind("has_tall_cactus");
	public static final TagKey<Biome> HAS_PRICKLY_PEAR = bind("has_prickly_pear");
	public static final TagKey<Biome> HAS_RARE_PRICKLY_PEAR = bind("has_rare_prickly_pear");
	public static final TagKey<Biome> HAS_TALL_BADLANDS_CACTUS = bind("has_tall_badlands_cactus");
	public static final TagKey<Biome> HAS_COMMON_PUMPKIN = bind("has_common_pumpkin");
	public static final TagKey<Biome> HAS_MOSS_PILE = bind("has_moss_pile");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PILE_WITH_DISK = bind("has_coarse_dirt_pile_with_disk");
	public static final TagKey<Biome> HAS_DECORATIVE_MUD = bind("has_decorative_mud");
	public static final TagKey<Biome> HAS_PACKED_MUD_ORE = bind("has_packed_mud_ore");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PATH = bind("has_coarse_dirt_path");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PATH_SMALL = bind("has_coarse_dirt_path_small");
	public static final TagKey<Biome> HAS_PACKED_MUD_PATH_BADLANDS = bind("has_packed_mud_path_badlands");
	public static final TagKey<Biome> HAS_SANDSTONE_PATH = bind("has_sandstone_path");
	public static final TagKey<Biome> HAS_COARSE_DIRT_CLEARING = bind("has_coarse_dirt_clearing");
	public static final TagKey<Biome> HAS_GRAVEL_CLEARING = bind("has_gravel_clearing");
	public static final TagKey<Biome> HAS_ROOTED_DIRT_CLEARING = bind("has_rooted_dirt_clearing");
	public static final TagKey<Biome> HAS_BIRCH_CLEARING_FLOWERS = bind("has_birch_clearing_flowers");
	public static final TagKey<Biome> HAS_FOREST_CLEARING_FLOWERS = bind("has_forest_clearing_flowers");
	public static final TagKey<Biome> HAS_SCORCHED_SAND = bind("has_scorched_sand");
	public static final TagKey<Biome> HAS_SCORCHED_RED_SAND = bind("has_scorched_red_sand");
	public static final TagKey<Biome> HAS_SMALL_SAND_TRANSITION = bind("has_small_sand_transition");
	public static final TagKey<Biome> HAS_SAND_TRANSITION = bind("has_sand_transition");
	public static final TagKey<Biome> HAS_RED_SAND_TRANSITION = bind("has_red_sand_transition");
	public static final TagKey<Biome> HAS_STONE_TRANSITION = bind("has_stone_transition");
	public static final TagKey<Biome> HAS_BETA_BEACH_SAND_TRANSITION = bind("has_beta_beach_sand_transition");
	public static final TagKey<Biome> HAS_BETA_BEACH_GRAVEL_TRANSITION = bind("has_beta_beach_gravel_transition");
	public static final TagKey<Biome> HAS_GRAVEL_TRANSITION = bind("has_gravel_transition");
	public static final TagKey<Biome> HAS_MUD_TRANSITION = bind("has_mud_transition");
	public static final TagKey<Biome> HAS_TERMITE_MOUND = bind("has_termite_mound");
	public static final TagKey<Biome> HAS_NETHER_GEYSER = bind("has_nether_geyser");
	public static final TagKey<Biome> HAS_NETHER_LAVA_GEYSER = bind("has_nether_lava_geyser");
	public static final TagKey<Biome> HAS_TAIGA_FOREST_ROCK = bind("has_taiga_forest_rock");
	public static final TagKey<Biome> HAS_MOSS_PATH = bind("has_moss_path");
	public static final TagKey<Biome> HAS_MOSS_LAKE = bind("has_moss_lake");
	public static final TagKey<Biome> HAS_MOSS_LAKE_RARE = bind("has_moss_lake_rare");
	public static final TagKey<Biome> HAS_MOSS_BASIN = bind("has_moss_basin");
	public static final TagKey<Biome> HAS_PODZOL_BASIN = bind("has_podzol_basin");
	public static final TagKey<Biome> HAS_MOSS_CARPET = bind("has_moss_carpet");
	public static final TagKey<Biome> HAS_PACKED_MUD_PATH = bind("has_packed_mud_path");
	public static final TagKey<Biome> HAS_MUD_BASIN = bind("has_mud_basin");
	public static final TagKey<Biome> HAS_MUD_PILE = bind("has_mud_pile");
	public static final TagKey<Biome> HAS_MUD_LAKE = bind("has_mud_lake");
	public static final TagKey<Biome> HAS_ALGAE_SMALL = bind("has_algae_small");
	public static final TagKey<Biome> HAS_ALGAE = bind("has_algae");
	public static final TagKey<Biome> HAS_WATER_POOLS = bind("has_water_pools");
	public static final TagKey<Biome> HAS_WATER_SHRUBS = bind("has_water_shrubs");
	public static final TagKey<Biome> HAS_WATER_GRASS = bind("has_water_grass");
	public static final TagKey<Biome> HAS_RARE_COARSE = bind("has_rare_coarse");
	public static final TagKey<Biome> HAS_RARE_GRAVEL = bind("has_rare_gravel");
	public static final TagKey<Biome> HAS_RARE_STONE = bind("has_rare_stone");
	public static final TagKey<Biome> HAS_CLAY_PATH = bind("has_clay_path");
	public static final TagKey<Biome> GRAVEL_BEACH = bind("gravel_beaches");
	public static final TagKey<Biome> SAND_BEACHES = bind("sand_beaches");
	public static final TagKey<Biome> MULTI_LAYER_SAND_BEACHES = bind("multi_layer_sand_beaches");
	public static final TagKey<Biome> BELOW_SURFACE_SNOW = bind("below_surface_snow");
	public static final TagKey<Biome> STRAYS_CAN_SPAWN_UNDERGROUND = bind("strays_can_spawn_underground");
	public static final TagKey<Biome> LUKEWARM_WATER = bind("lukewarm_water");
	public static final TagKey<Biome> HOT_WATER = bind("hot_water");
	public static final TagKey<Biome> SNOWY_WATER = bind("snowy_water");
	public static final TagKey<Biome> FROZEN_WATER = bind("frozen_water");
	public static final TagKey<Biome> WILDER_WILD_BIOMES = bind("wilder_wild_biomes");

	private WilderBiomeTags() {
		throw new UnsupportedOperationException("WilderBiomeTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, WilderConstants.id(path));
	}
}
