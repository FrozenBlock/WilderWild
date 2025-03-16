/*
 * Copyright 2023-2025 FrozenBlock
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

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public final class WWBiomeTags {
	public static final TagKey<Biome> STRAYS_CAN_SPAWN_UNDERGROUND = bind("entity/spawn/underground_stray");
	public static final TagKey<Biome> HAS_BUTTERFLY = bind("entity/spawn/butterfly");
	public static final TagKey<Biome> HAS_COMMON_BUTTERFLY = bind("entity/spawn/common_butterfly");
	public static final TagKey<Biome> HAS_FIREFLY = bind("entity/spawn/firefly");
	public static final TagKey<Biome> HAS_JELLYFISH = bind("entity/spawn/jellyfish");
	public static final TagKey<Biome> JELLYFISH_SPECIAL_SPAWN = bind("entity/spawn/jellyfish_special_spawn");
	public static final TagKey<Biome> HAS_MOOBLOOM = bind("entity/spawn/moobloom");
	public static final TagKey<Biome> HAS_CRAB = bind("entity/spawn/crab");
	public static final TagKey<Biome> HAS_OSTRICH = bind("entity/spawn/ostrich");
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
	public static final TagKey<Biome> MOOBLOOM_DANDELION = bind("entity/variant/moobloom_dandelion");
	public static final TagKey<Biome> MOOBLOOM_POPPY = bind("entity/variant/moobloom_poppy");
	public static final TagKey<Biome> MOOBLOOM_AZURE_BLUET = bind("entity/variant/moobloom_azure_bluet");
	public static final TagKey<Biome> MOOBLOOM_RED_TULIP = bind("entity/variant/moobloom_red_tulip");
	public static final TagKey<Biome> MOOBLOOM_ORANGE_TULIP = bind("entity/variant/moobloom_orange_tulip");
	public static final TagKey<Biome> MOOBLOOM_WHITE_TULIP = bind("entity/variant/moobloom_white_tulip");
	public static final TagKey<Biome> MOOBLOOM_PINK_TULIP = bind("entity/variant/moobloom_pink_tulip");
	public static final TagKey<Biome> MOOBLOOM_OXEYE_DAISY = bind("entity/variant/moobloom_oxeye_daisy");
	public static final TagKey<Biome> MOOBLOOM_CORNFLOWER = bind("entity/variant/moobloom_cornflower");
	public static final TagKey<Biome> MOOBLOOM_LILY_OF_THE_VALLEY = bind("entity/variant/moobloom_lily_of_the_valley");
	public static final TagKey<Biome> MOOBLOOM_ALLIUM = bind("entity/variant/moobloom_allium");
	public static final TagKey<Biome> MOOBLOOM_BLUE_ORCHID = bind("entity/variant/moobloom_blue_orchid");
	public static final TagKey<Biome> MOOBLOOM_CARNATION = bind("entity/variant/moobloom_carnation");
	public static final TagKey<Biome> MOOBLOOM_MARIGOLD = bind("entity/variant/moobloom_marigold");
	public static final TagKey<Biome> MOOBLOOM_PASQUEFLOWER = bind("entity/variant/moobloom_pasqueflower");

	public static final TagKey<Biome> PRODUCES_BARNACLES_FROM_BONEMEAL = bind("produces_barnacles_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SPONGE_BUDS_FROM_BONEMEAL = bind("produces_sponge_buds_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SEA_ANEMONE_FROM_BONEMEAL = bind("produces_sea_anemone_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_SEA_WHIPS_FROM_BONEMEAL = bind("produces_sea_whips_from_bonemeal");
	public static final TagKey<Biome> PRODUCES_TUBE_WORMS_FROM_BONEMEAL = bind("produces_tube_worms_from_bonemeal");

	public static final TagKey<Biome> GROVE = bind("grove");
	public static final TagKey<Biome> NORMAL_SAVANNA = bind("normal_savanna");
	public static final TagKey<Biome> WINDSWEPT_SAVANNA = bind("windswept_savanna");
	public static final TagKey<Biome> SNOWY_PLAINS = bind("snowy_plains");
	public static final TagKey<Biome> WINDSWEPT_HILLS = bind("windswept_hills");
	public static final TagKey<Biome> WINDSWEPT_FOREST = bind("windswept_forest");
	public static final TagKey<Biome> RAINFOREST = bind("rainforest");

	public static final TagKey<Biome> DARK_FOREST = bind("dark_forest");
	public static final TagKey<Biome> PALE_GARDEN = bind("pale_garden");
	public static final TagKey<Biome> MEADOW = bind("meadow");

	public static final TagKey<Biome> BETA_BEACH_GRAVEL = bind("beta_beach/gravel");
	public static final TagKey<Biome> BETA_BEACH_SAND = bind("beta_beach/sand");
	public static final TagKey<Biome> BETA_BEACH_MULTI_LAYER_SAND = bind("beta_beach/multi_layer_sand");

	public static final TagKey<Biome> BELOW_SURFACE_SNOW = bind("below_surface_snow");

	public static final TagKey<Biome> LUKEWARM_WATER = bind("water_color/lukewarm");
	public static final TagKey<Biome> HOT_WATER = bind("water_color/hot");
	public static final TagKey<Biome> SNOWY_WATER = bind("water_color/snowy");
	public static final TagKey<Biome> FROZEN_WATER = bind("water_color/frozen");

	public static final TagKey<Biome> HAS_FROZEN_FOREST_GRASS = bind("feature/has_frozen_forest_grass");
	public static final TagKey<Biome> HAS_FROZEN_NORMAL_GRASS = bind("feature/has_frozen_normal_grass");
	public static final TagKey<Biome> HAS_FROZEN_PLAIN_GRASS = bind("feature/has_frozen_plain_grass");
	public static final TagKey<Biome> HAS_FROZEN_TAIGA_GRASS = bind("feature/has_frozen_taiga_grass");
	public static final TagKey<Biome> HAS_FROZEN_TAIGA_2_GRASS = bind("feature/has_frozen_taiga_2_grass");
	public static final TagKey<Biome> HAS_FROZEN_PLAIN_TALL_GRASS = bind("feature/has_frozen_plain_tall_grass");
	public static final TagKey<Biome> HAS_FROZEN_LARGE_FERNS = bind("feature/has_frozen_large_ferns");

	public static final TagKey<Biome> HAS_SURFACE_ICICLES = bind("feature/has_surface_icicles");
	public static final TagKey<Biome> HAS_SURFACE_FRAGILE_ICE = bind("feature/has_surface_fragile_ice");
	public static final TagKey<Biome> HAS_TUMBLEWEED_PLANT = bind("feature/has_tumbleweed_plant");
	public static final TagKey<Biome> NON_FROZEN_PLAINS = bind("non_frozen_plains");
	public static final TagKey<Biome> SWAMP_TREES = bind("feature/swamp_trees");
	public static final TagKey<Biome> SHORT_TAIGA = bind("feature/short_taiga");
	public static final TagKey<Biome> TALL_PINE_TAIGA = bind("feature/tall_pine_taiga");
	public static final TagKey<Biome> TALL_SPRUCE_TAIGA = bind("feature/tall_spruce_taiga");
	public static final TagKey<Biome> HAS_FALLEN_BIRCH_TREES = bind("feature/has_fallen_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_CHERRY_TREES = bind("feature/has_fallen_cherry_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_BIRCH_TREES = bind("feature/has_fallen_oak_and_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_SPRUCE_TREES = bind("feature/has_fallen_oak_and_spruce_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_CYPRESS_TREES = bind("feature/has_fallen_oak_and_cypress_trees");
	public static final TagKey<Biome> HAS_MOSSY_FALLEN_MIXED_TREES = bind("feature/has_mossy_fallen_mixed_trees");
	public static final TagKey<Biome> HAS_MOSSY_FALLEN_OAK_AND_BIRCH = bind("feature/has_mossy_fallen_oak_and_birch");
	public static final TagKey<Biome> HAS_FALLEN_ACACIA_AND_OAK = bind("feature/has_fallen_acacia_and_oak");
	public static final TagKey<Biome> HAS_FALLEN_PALM = bind("feature/has_fallen_palm");
	public static final TagKey<Biome> HAS_FALLEN_PALM_RARE = bind("feature/has_fallen_palm_rare");
	public static final TagKey<Biome> HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK = bind("feature/has_fallen_palm_and_jungle_and_oak");
	public static final TagKey<Biome> HAS_FALLEN_LARGE_JUNGLE = bind("feature/has_fallen_large_jungle");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_LARGE_JUNGLE = bind("feature/has_common_fallen_large_jungle");
	public static final TagKey<Biome> HAS_FALLEN_DARK_OAK = bind("feature/has_fallen_dark_oak");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_DARK_OAK = bind("feature/has_common_fallen_dark_oak");
	public static final TagKey<Biome> HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST = bind("feature/has_fallen_birch_and_oak_dark_forest");
	public static final TagKey<Biome> HAS_FALLEN_PALE_OAK = bind("feature/has_fallen_pale_oak");
	public static final TagKey<Biome> HAS_FALLEN_SPRUCE_TREES = bind("feature/has_fallen_spruce_trees");
	public static final TagKey<Biome> HAS_CLEAN_FALLEN_SPRUCE_TREES = bind("feature/has_clean_fallen_spruce_trees");
	public static final TagKey<Biome> HAS_FALLEN_LARGE_SPRUCE = bind("feature/has_fallen_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_FALLEN_LARGE_SPRUCE = bind("feature/has_common_fallen_large_spruce");
	public static final TagKey<Biome> HAS_CLEAN_FALLEN_LARGE_SPRUCE = bind("feature/has_clean_fallen_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE = bind("feature/has_common_clean_fallen_large_spruce");
	public static final TagKey<Biome> HAS_FALLEN_SWAMP_TREES = bind("feature/has_fallen_swamp_trees");
	public static final TagKey<Biome> HAS_FALLEN_MANGROVE_TREES = bind("feature/has_fallen_mangrove_trees");
	public static final TagKey<Biome> HAS_FALLEN_MAPLE_TREES = bind("feature/has_fallen_maple_trees");
	public static final TagKey<Biome> HAS_FALLEN_CRIMSON_FUNGI = bind("feature/has_fallen_crimson_fungi");
	public static final TagKey<Biome> HAS_FALLEN_WARPED_FUNGI = bind("feature/has_fallen_warped_fungi");
	public static final TagKey<Biome> BAMBOO_JUNGLE_TREES = bind("feature/bamboo_jungle_trees");
	public static final TagKey<Biome> SPARSE_JUNGLE_TREES = bind("feature/sparse_jungle_trees");
	public static final TagKey<Biome> JUNGLE_TREES = bind("feature/jungle_trees");
	public static final TagKey<Biome> MANGROVE_TREES = bind("feature/mangrove_trees");
	public static final TagKey<Biome> CHERRY_TREES = bind("feature/cherry_trees");
	public static final TagKey<Biome> FOREST_GRASS = bind("feature/forest_grass");
	public static final TagKey<Biome> PLAINS_GRASS = bind("feature/plains_grass");
	public static final TagKey<Biome> HAS_SPONGE_BUD = bind("feature/has_sponge_bud");
	public static final TagKey<Biome> HAS_SPONGE_BUD_RARE = bind("feature/has_sponge_bud_rare");
	public static final TagKey<Biome> HAS_HUGE_RED_MUSHROOM = bind("feature/has_huge_red_mushroom");
	public static final TagKey<Biome> HAS_HUGE_BROWN_MUSHROOM = bind("feature/has_huge_brown_mushroom");
	public static final TagKey<Biome> HAS_BIG_MUSHROOMS = bind("feature/has_big_mushrooms");
	public static final TagKey<Biome> HAS_COMMON_BROWN_MUSHROOM = bind("feature/has_common_brown_mushroom");
	public static final TagKey<Biome> HAS_COMMON_RED_MUSHROOM = bind("feature/has_common_red_mushroom");
	public static final TagKey<Biome> HAS_PALE_MUSHROOM = bind("feature/has_pale_mushroom");
	public static final TagKey<Biome> HAS_HUGE_PALE_MUSHROOM = bind("feature/has_huge_pale_mushroom");
	public static final TagKey<Biome> HAS_SWAMP_MUSHROOM = bind("feature/has_swamp_mushroom");
	public static final TagKey<Biome> HAS_BIG_MUSHROOM_PATCH = bind("feature/has_big_mushroom_patch");
	public static final TagKey<Biome> HAS_DATURA = bind("feature/has_datura");
	public static final TagKey<Biome> HAS_ROSE_BUSH = bind("feature/has_rose_bush");
	public static final TagKey<Biome> HAS_PEONY = bind("feature/has_peony");
	public static final TagKey<Biome> HAS_LILAC = bind("feature/has_lilac");
	public static final TagKey<Biome> HAS_CARNATION = bind("feature/has_carnation");
	public static final TagKey<Biome> HAS_MARIGOLD = bind("feature/has_marigold");
	public static final TagKey<Biome> HAS_MARIGOLD_SPARSE = bind("feature/has_marigold_sparse");
	public static final TagKey<Biome> HAS_EYEBLOSSOM = bind("feature/has_eyeblossom");
	public static final TagKey<Biome> HAS_PINK_TULIP_UNCOMMON = bind("feature/has_pink_tulip_uncommon");
	public static final TagKey<Biome> HAS_ALLIUM_UNCOMMON = bind("feature/has_allium_uncommon");
	public static final TagKey<Biome> HAS_CATTAIL = bind("feature/has_cattail");
	public static final TagKey<Biome> HAS_CATTAIL_UNCOMMON = bind("feature/has_cattail_uncommon");
	public static final TagKey<Biome> HAS_CATTAIL_COMMON = bind("feature/has_cattail_common");
	public static final TagKey<Biome> HAS_CATTAIL_MUD = bind("feature/has_cattail_mud");
	public static final TagKey<Biome> HAS_BARNACLES_COMMON = bind("feature/has_barnacles_common");
	public static final TagKey<Biome> HAS_BARNACLES_STRUCTURE = bind("feature/has_barnacles_structure");
	public static final TagKey<Biome> HAS_BARNACLES = bind("feature/has_barnacles");
	public static final TagKey<Biome> HAS_BARNACLES_SPARSE = bind("feature/has_barnacles_sparse");
	public static final TagKey<Biome> HAS_BARNACLES_RARE = bind("feature/has_barnacles_rare");
	public static final TagKey<Biome> HAS_SEA_ANEMONE = bind("feature/has_sea_anemone");
	public static final TagKey<Biome> HAS_SEA_ANEMONE_SPARSE = bind("feature/has_sea_anemone_sparse");
	public static final TagKey<Biome> HAS_SEA_ANEMONE_RARE = bind("feature/has_sea_anemone_rare");
	public static final TagKey<Biome> HAS_SEA_WHIP = bind("feature/has_sea_whip");
	public static final TagKey<Biome> HAS_SEA_WHIP_SPARSE = bind("feature/has_sea_whip_sparse");
	public static final TagKey<Biome> HAS_SEA_WHIP_RARE = bind("feature/has_sea_whip_rare");
	public static final TagKey<Biome> HAS_TUBE_WORMS = bind("feature/has_tube_worms");
	public static final TagKey<Biome> HAS_TUBE_WORMS_RARE = bind("feature/has_tube_worms_rare");
	public static final TagKey<Biome> HAS_SEEDING_DANDELION = bind("feature/has_seeding_dandelion");
	public static final TagKey<Biome> HAS_COMMON_SEEDING_DANDELION = bind("feature/has_common_seeding_dandelion");
	public static final TagKey<Biome> HAS_RARE_SEEDING_DANDELION = bind("feature/has_rare_seeding_dandelion");
	public static final TagKey<Biome> HAS_VERY_RARE_SEEDING_DANDELION = bind("feature/has_very_rare_seeding_dandelion");
	public static final TagKey<Biome> HAS_PHLOX = bind("feature/has_phlox");
	public static final TagKey<Biome> HAS_PHLOX_SPARSE = bind("feature/has_phlox_sparse");
	public static final TagKey<Biome> HAS_LANTANAS = bind("feature/has_lantanas");
	public static final TagKey<Biome> HAS_LANTANAS_SPARSE = bind("feature/has_lantanas_sparse");
	public static final TagKey<Biome> HAS_WILDFLOWERS = bind("feature/has_wildflowers");
	public static final TagKey<Biome> HAS_WILDFLOWERS_SPARSE = bind("feature/has_wildflowers_sparse");
	public static final TagKey<Biome> HAS_WILDFLOWERS_AND_PHLOX = bind("feature/has_wildflowers_and_phlox");
	public static final TagKey<Biome> HAS_WILDFLOWERS_AND_PHLOX_SPARSE = bind("feature/has_wildflowers_and_phlox_sparse");
	public static final TagKey<Biome> HAS_WILDFLOWERS_AND_LANTANAS = bind("feature/has_wildflowers_and_lantanas");
	public static final TagKey<Biome> HAS_LANTANAS_AND_PHLOX = bind("feature/has_lantanas_and_phlox");
	public static final TagKey<Biome> HAS_LANTANAS_AND_PHLOX_SPARSE = bind("feature/has_lantanas_and_phlox_sparse");
	public static final TagKey<Biome> HAS_CLOVERS = bind("feature/has_clovers");
	public static final TagKey<Biome> HAS_CLOVERS_SPARSE = bind("feature/has_clovers_sparse");
	public static final TagKey<Biome> HAS_MILKWEED = bind("feature/has_milkweed");
	public static final TagKey<Biome> HAS_SNOWY_PLAINS_FLOWERS = bind("feature/has_snowy_plains_flowers");
	public static final TagKey<Biome> HAS_CHERRY_FLOWERS = bind("feature/has_cherry_flowers");
	public static final TagKey<Biome> HAS_SUNFLOWER_PLAINS_FLOWERS = bind("feature/has_sunflower_plains_flowers");
	public static final TagKey<Biome> HAS_PALMS = bind("feature/has_palms");
	public static final TagKey<Biome> HAS_WARM_BEACH_PALMS = bind("feature/has_warm_beach_palms");
	public static final TagKey<Biome> HAS_SHORT_SPRUCE = bind("feature/has_short_spruce");
	public static final TagKey<Biome> HAS_SHORT_MEGA_SPRUCE = bind("feature/has_short_mega_spruce");
	public static final TagKey<Biome> HAS_SHORT_MEGA_SPRUCE_SNOWY = bind("feature/has_short_mega_spruce_snowy");
	public static final TagKey<Biome> HAS_BIG_COARSE_SHRUB = bind("feature/has_big_coarse_shrub");
	public static final TagKey<Biome> HAS_SNAPPED_OAK = bind("feature/has_snapped_oak");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH = bind("feature/has_snapped_birch");
	public static final TagKey<Biome> HAS_SNAPPED_SPRUCE = bind("feature/has_snapped_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_SPRUCE_SNOWY = bind("feature/has_snapped_spruce_snowy");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_SPRUCE = bind("feature/has_snapped_large_spruce");
	public static final TagKey<Biome> HAS_COMMON_SNAPPED_LARGE_SPRUCE = bind("feature/has_common_snapped_large_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_SPRUCE_SNOWY = bind("feature/has_snapped_large_spruce_snowy");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_OAK = bind("feature/has_snapped_birch_and_oak");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = bind("feature/has_snapped_birch_and_oak_and_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_SPRUCE = bind("feature/has_snapped_birch_and_spruce");
	public static final TagKey<Biome> HAS_SNAPPED_CYPRESS = bind("feature/has_snapped_cypress");
	public static final TagKey<Biome> HAS_SNAPPED_JUNGLE = bind("feature/has_snapped_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_LARGE_JUNGLE = bind("feature/has_snapped_large_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_BIRCH_AND_JUNGLE = bind("feature/has_snapped_birch_and_jungle");
	public static final TagKey<Biome> HAS_SNAPPED_ACACIA = bind("feature/has_snapped_acacia");
	public static final TagKey<Biome> HAS_SNAPPED_ACACIA_AND_OAK = bind("feature/has_snapped_acacia_and_oak");
	public static final TagKey<Biome> HAS_SNAPPED_CHERRY = bind("feature/has_snapped_cherry");
	public static final TagKey<Biome> HAS_SNAPPED_DARK_OAK = bind("feature/has_snapped_dark_oak");
	public static final TagKey<Biome> HAS_SNAPPED_PALE_OAK = bind("feature/has_snapped_pale_oak");
	public static final TagKey<Biome> HAS_SNAPPED_MAPLE = bind("feature/has_snapped_maple");
	public static final TagKey<Biome> HAS_SNAPPED_CRIMSON_FUNGI = bind("feature/has_snapped_crimson_fungi");
	public static final TagKey<Biome> HAS_SNAPPED_WARPED_FUNGI = bind("feature/has_snapped_warped_fungi");
	public static final TagKey<Biome> HAS_POLLEN = bind("feature/has_pollen");
	public static final TagKey<Biome> HAS_FIELD_FLOWERS = bind("feature/has_field_flowers");
	public static final TagKey<Biome> HAS_CRIMSON_SHELF_FUNGI = bind("feature/has_crimson_shelf_fungi");
	public static final TagKey<Biome> HAS_WARPED_SHELF_FUNGI = bind("feature/has_warped_shelf_fungi");
	public static final TagKey<Biome> HAS_CRIMSON_SHELF_FUNGI_RARE = bind("feature/has_crimson_shelf_fungi_rare");
	public static final TagKey<Biome> HAS_WARPED_SHELF_FUNGI_RARE = bind("feature/has_warped_shelf_fungi_rare");
	public static final TagKey<Biome> HAS_RAINFOREST_MUSHROOM = bind("feature/has_rainforest_mushroom");
	public static final TagKey<Biome> HAS_MIXED_MUSHROOM = bind("feature/has_mixed_mushroom");
	public static final TagKey<Biome> HAS_HIBISCUS = bind("feature/has_hibiscus");
	public static final TagKey<Biome> HAS_FLOWERING_WATER_LILY = bind("feature/has_flowering_water_lily");
	public static final TagKey<Biome> HAS_BERRY_PATCH = bind("feature/has_berry_patch");
	public static final TagKey<Biome> HAS_BUSH = bind("feature/has_bush");
	public static final TagKey<Biome> HAS_FOREST_SHRUB = bind("feature/has_forest_shrub");
	public static final TagKey<Biome> HAS_SHRUB = bind("feature/has_shrub");
	public static final TagKey<Biome> HAS_GENERIC_FLOWERS = bind("feature/has_generic_flowers");
	public static final TagKey<Biome> HAS_GENERIC_FLOWERS_NO_CARNATIONS = bind("feature/has_generic_flowers_no_carnations");
	public static final TagKey<Biome> HAS_PLAINS_FLOWERS = bind("feature/has_plains_flowers");
	public static final TagKey<Biome> HAS_TUNDRA_FLOWERS = bind("feature/has_tundra_flowers");
	public static final TagKey<Biome> HAS_BIRCH_FLOWERS = bind("feature/has_birch_flowers");
	public static final TagKey<Biome> HAS_CYPRESS_FLOWERS = bind("feature/has_cypress_flowers");
	public static final TagKey<Biome> HAS_RARE_MILKWEED = bind("feature/has_rare_milkweed");
	public static final TagKey<Biome> HAS_MYCELIUM_GROWTH = bind("feature/has_mycelium_growth");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS = bind("feature/has_large_fern_and_grass");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS_RARE = bind("feature/has_large_fern_and_grass_rare");
	public static final TagKey<Biome> HAS_NEW_RARE_GRASS = bind("feature/has_new_rare_grass");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_TALL_GRASS = bind("feature/has_flower_field_tall_grass");
	public static final TagKey<Biome> HAS_SWAMP_FERN = bind("feature/has_swamp_fern");
	public static final TagKey<Biome> HAS_DENSE_FERN = bind("feature/has_dense_fern");
	public static final TagKey<Biome> HAS_SWAMP_TALL_GRASS = bind("feature/has_swamp_tall_grass");
	public static final TagKey<Biome> HAS_DENSE_TALL_GRASS = bind("feature/has_dense_tall_grass");
	public static final TagKey<Biome> HAS_SPARSE_JUNGLE_FLOWERS = bind("feature/has_sparse_jungle_flowers");
	public static final TagKey<Biome> HAS_JUNGLE_FLOWERS = bind("feature/has_jungle_flowers");
	public static final TagKey<Biome> HAS_JUNGLE_BUSH = bind("feature/has_jungle_bush");
	public static final TagKey<Biome> HAS_SPARSE_BUSH = bind("feature/has_sparse_bush");
	public static final TagKey<Biome> HAS_ARID_BUSH = bind("feature/has_arid_bush");
	public static final TagKey<Biome> HAS_FLOWER_FIELD_BUSH = bind("feature/has_flower_field_bush");
	public static final TagKey<Biome> HAS_RAINFOREST_BUSH = bind("feature/has_rainforest_bush");
	public static final TagKey<Biome> HAS_BADLANDS_SAND_BUSH = bind("feature/has_badlands_sand_bush");
	public static final TagKey<Biome> HAS_BADLANDS_TERRACOTTA_BUSH = bind("feature/has_badlands_terracotta_bush");
	public static final TagKey<Biome> HAS_WOODED_BADLANDS_BUSH = bind("feature/has_wooded_badlands_bush");
	public static final TagKey<Biome> HAS_BADLANDS_RARE_SAND_BUSH = bind("feature/has_badlands_rare_sand_bush");
	public static final TagKey<Biome> HAS_DESERT_BUSH = bind("feature/has_desert_bush");
	public static final TagKey<Biome> HAS_OASIS_BUSH = bind("feature/has_oasis_bush");
	public static final TagKey<Biome> HAS_TALL_CACTUS = bind("feature/has_tall_cactus");
	public static final TagKey<Biome> HAS_PRICKLY_PEAR = bind("feature/has_prickly_pear");
	public static final TagKey<Biome> HAS_RARE_PRICKLY_PEAR = bind("feature/has_rare_prickly_pear");
	public static final TagKey<Biome> HAS_TALL_BADLANDS_CACTUS = bind("feature/has_tall_badlands_cactus");
	public static final TagKey<Biome> HAS_COMMON_PUMPKIN = bind("feature/has_common_pumpkin");
	public static final TagKey<Biome> HAS_MOSS_PILE = bind("feature/has_moss_pile");
	public static final TagKey<Biome> HAS_STONE_PILE_COMMON = bind("feature/has_stone_pile_common");
	public static final TagKey<Biome> HAS_STONE_PILE = bind("feature/has_stone_pile");
	public static final TagKey<Biome> HAS_STONE_PILE_RARE = bind("feature/has_stone_pile_rare");
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
	public static final TagKey<Biome> HAS_BIRCH_CLEARING_FLOWERS = bind("feature/has_birch_clearing_flowers");
	public static final TagKey<Biome> HAS_FOREST_CLEARING_FLOWERS = bind("feature/has_forest_clearing_flowers");
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
	public static final TagKey<Biome> HAS_NETHER_GEYSER = bind("feature/has_nether_geyser");
	public static final TagKey<Biome> HAS_NETHER_LAVA_GEYSER = bind("feature/has_nether_lava_geyser");
	public static final TagKey<Biome> HAS_HYDROTHERMAL_VENT = bind("feature/has_hydrothermal_vent");
	public static final TagKey<Biome> HAS_HYDROTHERMAL_VENT_RARE = bind("feature/has_hydrothermal_vent_rare");
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
	public static final TagKey<Biome> HAS_CREEPING_RED_MOSS = bind("feature/has_creeping_red_moss");
	public static final TagKey<Biome> HAS_RED_MOSS = bind("feature/has_red_moss");
	public static final TagKey<Biome> HAS_CREEPING_RED_MOSS_UNDERWATER = bind("feature/has_creeping_red_moss_underwater");
	public static final TagKey<Biome> HAS_RED_MOSS_UNDERWATER = bind("feature/has_red_moss_underwater");
	public static final TagKey<Biome> HAS_RED_MOSS_UNDERWATER_RARE = bind("feature/has_red_moss_underwater_rare");
	public static final TagKey<Biome> HAS_ALGAE_SMALL = bind("feature/has_algae_small");
	public static final TagKey<Biome> HAS_ALGAE = bind("feature/has_algae");
	public static final TagKey<Biome> HAS_PLANKTON = bind("feature/has_plankton");
	public static final TagKey<Biome> HAS_MEADOW_SEAGRASS = bind("feature/has_meadow_seagrass");
	public static final TagKey<Biome> HAS_OCEAN_MOSS = bind("feature/has_ocean_moss");
	public static final TagKey<Biome> HAS_WATER_POOLS = bind("feature/has_water_pools");
	public static final TagKey<Biome> HAS_WATER_SHRUBS = bind("feature/has_water_shrubs");
	public static final TagKey<Biome> HAS_WATER_GRASS = bind("feature/has_water_grass");
	public static final TagKey<Biome> HAS_RARE_COARSE = bind("feature/has_rare_coarse");
	public static final TagKey<Biome> HAS_RARE_GRAVEL = bind("feature/has_rare_gravel");
	public static final TagKey<Biome> HAS_RARE_STONE = bind("feature/has_rare_stone");
	public static final TagKey<Biome> HAS_CLAY_PATH = bind("feature/has_clay_path");
	public static final TagKey<Biome> WILDER_WILD_BIOMES = bind("wilder_wild_biomes");

	public static final TagKey<Biome> HAS_FOREST_MUSIC = bind("music_pool/has_forest_music");
	public static final TagKey<Biome> HAS_FLOWER_FOREST_MUSIC = bind("music_pool/has_flower_forest_music");
	public static final TagKey<Biome> HAS_SNOWY_MUSIC = bind("music_pool/has_snowy_music");
	public static final TagKey<Biome> HAS_LUSH_MUSIC = bind("music_pool/has_lush_music");
	public static final TagKey<Biome> HAS_DRIPSTONE_MUSIC = bind("music_pool/has_dripstone_music");
	public static final TagKey<Biome> HAS_CHERRY_MUSIC = bind("music_pool/has_cherry_music");
	public static final TagKey<Biome> HAS_GROVE_MUSIC = bind("music_pool/has_grove_music");
	public static final TagKey<Biome> HAS_JUNGLE_MUSIC = bind("music_pool/has_jungle_music");
	public static final TagKey<Biome> HAS_BAMBOO_JUNGLE_MUSIC = bind("music_pool/has_bamboo_jungle_music");
	public static final TagKey<Biome> HAS_SPARSE_JUNGLE_MUSIC = bind("music_pool/has_sparse_jungle_music");
	public static final TagKey<Biome> HAS_FROZEN_MUSIC = bind("music_pool/has_frozen_music");
	public static final TagKey<Biome> HAS_MAGMATIC_MUSIC = bind("music_pool/has_magmatic_music");
	public static final TagKey<Biome> HAS_MESOGLEA_MUSIC = bind("music_pool/has_mesoglea_music");
	public static final TagKey<Biome> HAS_MAPLE_MUSIC = bind("music_pool/has_maple_music");
	public static final TagKey<Biome> HAS_DYING_MUSIC = bind("music_pool/has_dying_music");
	public static final TagKey<Biome> HAS_SNOWY_DYING_MUSIC = bind("music_pool/has_snowy_dying_music");

	private WWBiomeTags() {
		throw new UnsupportedOperationException("WilderBiomeTags contains only static declarations.");
	}

	@NotNull
	private static TagKey<Biome> bind(@NotNull String path) {
		return TagKey.create(Registries.BIOME, WWConstants.id(path));
	}
}
