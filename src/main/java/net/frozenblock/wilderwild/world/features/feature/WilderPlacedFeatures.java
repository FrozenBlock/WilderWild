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

package net.frozenblock.wilderwild.world.features.feature;

import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import static net.frozenblock.wilderwild.world.features.feature.WilderPlacementUtils.register;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import static net.minecraft.data.worldgen.placement.AquaticPlacements.seagrassPlacement;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.TREE_THRESHOLD;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.treePlacement;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import org.jetbrains.annotations.NotNull;

public final class WilderPlacedFeatures {
	//FALLEN TREES
	public static final FrozenPlacedFeature FALLEN_TREES_MIXED_PLACED = register("fallen_trees_mixed_placed");
	public static final FrozenPlacedFeature MOSSY_FALLEN_TREES_MIXED_PLACED = register("mossy_fallen_trees_mixed_placed");
	public static final FrozenPlacedFeature MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED = register("mossy_fallen_trees_oak_and_birch_placed");
	public static final FrozenPlacedFeature FALLEN_BIRCH_AND_SPRUCE_PLACED = register("fallen_birch_and_spruce_placed");
	public static final FrozenPlacedFeature FALLEN_OAK_PLACED_SWAMP = register("fallen_oak_placed_swamp");
	public static final FrozenPlacedFeature FALLEN_OAK_AND_SPRUCE_PLACED = register("fallen_oak_and_spruce_placed");
	public static final FrozenPlacedFeature FALLEN_OAK_AND_BIRCH_PLACED = register("fallen_oak_and_birch_placed");
	public static final FrozenPlacedFeature FALLEN_OAK_AND_CYPRESS_PLACED = register("fallen_oak_and_cypress_placed");
	public static final FrozenPlacedFeature FALLEN_BIRCH_PLACED = register("fallen_birch_placed");
	public static final FrozenPlacedFeature FALLEN_CHERRY_PLACED = register("fallen_cherry_placed");
	public static final FrozenPlacedFeature FALLEN_SPRUCE_PLACED = register("fallen_spruce_placed");
	public static final FrozenPlacedFeature CLEAN_FALLEN_SPRUCE_PLACED = register("clean_fallen_spruce_placed");
	public static final FrozenPlacedFeature CLEAN_FALLEN_LARGE_SPRUCE_PLACED = register("clean_fallen_large_spruce_placed");
	public static final FrozenPlacedFeature CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED = register("clean_fallen_large_spruce_common_placed");
	public static final FrozenPlacedFeature DECORATED_FALLEN_LARGE_SPRUCE_PLACED = register("decorated_fallen_large_spruce_placed");
	public static final FrozenPlacedFeature DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED = register("decorated_fallen_large_spruce_common_placed");
	public static final FrozenPlacedFeature FALLEN_OAK_AND_BIRCH_PLACED_2 = register("fallen_oak_and_birch_placed_2");
	public static final FrozenPlacedFeature FALLEN_ACACIA_AND_OAK_PLACED = register("fallen_acacia_and_oak_placed");
	public static final FrozenPlacedFeature FALLEN_PALM_PLACED = register("fallen_palm_placed");
	public static final FrozenPlacedFeature FALLEN_PALM_PLACED_RARE = register("fallen_palm_placed_rare");
	public static final FrozenPlacedFeature FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED = register("fallen_palm_and_jungle_and_oak_placed");
	public static final FrozenPlacedFeature LARGE_FALLEN_JUNGLE_PLACED = register("large_fallen_jungle_placed");
	public static final FrozenPlacedFeature LARGE_FALLEN_JUNGLE_COMMON_PLACED = register("large_fallen_jungle_common_placed");
	public static final FrozenPlacedFeature FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED = register("fallen_birch_and_oak_dark_forest_placed");
	public static final FrozenPlacedFeature FALLEN_DARK_OAK_PLACED = register("fallen_dark_oak_placed");
	public static final FrozenPlacedFeature FALLEN_DARK_OAK_COMMON_PLACED = register("fallen_dark_oak_common_placed");
	public static final FrozenPlacedFeature FALLEN_MANGROVE_PLACED = register("fallen_mangrove_placed");
	//TREES
	public static final FrozenPlacedFeature TREES_PLAINS = register("trees_plains");
	public static final FrozenPlacedFeature SHRUBS_FOREST = register("shrubs_forest");
	public static final FrozenPlacedFeature SHRUBS = register("shrubs");
	public static final FrozenPlacedFeature SHRUBS_WATER = register("shrubs_water");
	public static final FrozenPlacedFeature TREES_FLOWER_FIELD = register("trees_flower_field");
	public static final FrozenPlacedFeature TREES_BIRCH_AND_OAK = register("trees_birch_and_oak");
	public static final FrozenPlacedFeature TREES_DYING_FOREST = register("trees_dying_forest");
	public static final FrozenPlacedFeature TREES_SNOWY_DYING_FOREST = register("trees_snowy_dying_forest");
	public static final FrozenPlacedFeature TREES_DYING_MIXED_FOREST = register("trees_dying_mixed_forest");
	public static final FrozenPlacedFeature TREES_SNOWY_DYING_MIXED_FOREST = register("trees_snowy_dying_mixed_forest");
	public static final FrozenPlacedFeature TREES_BIRCH_AND_OAK_ORIGINAL = register("trees_birch_and_oak_original");
	public static final FrozenPlacedFeature TREES_SEMI_BIRCH_AND_OAK = register("trees_semi_birch_and_oak");
	public static final FrozenPlacedFeature TREES_FLOWER_FOREST = register("trees_flower_forest");
	public static final FrozenPlacedFeature DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenPlacedFeature OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenPlacedFeature DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenPlacedFeature DARK_TAIGA_VEGETATION = register("dark_taiga_vegetation");
	public static final FrozenPlacedFeature TREES_BIRCH = register("trees_birch");
	public static final FrozenPlacedFeature BIRCH_TALL = register("birch_tall");
	public static final FrozenPlacedFeature SPRUCE_PLACED = register("spruce_placed");
	public static final FrozenPlacedFeature SHORT_SPRUCE_PLACED = register("short_spruce_placed");
	public static final FrozenPlacedFeature SHORT_SPRUCE_RARE_PLACED = register("short_spruce_rare_placed");
	public static final FrozenPlacedFeature SHORT_MEGA_SPRUCE_PLACED = register("short_mega_spruce_placed");
	public static final FrozenPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW_PLACED = register("short_mega_spruce_on_snow_placed");
	public static final FrozenPlacedFeature TREES_OLD_GROWTH_PINE_TAIGA = register("trees_old_growth_pine_taiga");
	public static final FrozenPlacedFeature TREES_OLD_GROWTH_SPRUCE_TAIGA1 = register("trees_old_growth_spruce_taiga");
	public static final FrozenPlacedFeature TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenPlacedFeature TREES_SNOWY = register("trees_snowy");
	public static final FrozenPlacedFeature TREES_GROVE = register("trees_grove");
	public static final FrozenPlacedFeature TREES_WINDSWEPT_HILLS = register("trees_windswept_hills");
	public static final FrozenPlacedFeature TREES_WINDSWEPT_FOREST = register("trees_windswept_forest");
	public static final FrozenPlacedFeature TREES_MEADOW = register("trees_meadow");
	public static final FrozenPlacedFeature WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees");
	public static final FrozenPlacedFeature SAVANNA_TREES = register("savanna_trees");
	public static final FrozenPlacedFeature ARID_SAVANNA_TREES = register("arid_savanna_trees");
	public static final FrozenPlacedFeature WOODED_BADLANDS_TREES = register("wooded_badlands_trees");
	public static final FrozenPlacedFeature TREES_SWAMP = register("trees_swamp");
	public static final FrozenPlacedFeature MIXED_TREES = register("mixed_trees");
	public static final FrozenPlacedFeature TEMPERATE_RAINFOREST_TREES = register("temperate_rainforest_trees");
	public static final FrozenPlacedFeature RAINFOREST_TREES = register("rainforest_trees");
	public static final FrozenPlacedFeature BIRCH_TAIGA_TREES = register("birch_taiga_trees");
	public static final FrozenPlacedFeature OLD_GROWTH_BIRCH_TAIGA_TREES = register("old_growth_birch_taiga_trees");
	public static final FrozenPlacedFeature PARCHED_FOREST_TREES = register("parched_forest_trees");
	public static final FrozenPlacedFeature ARID_FOREST_TREES = register("arid_forest_trees");
	public static final FrozenPlacedFeature BIRCH_JUNGLE_TREES = register("birch_jungle_trees");
	public static final FrozenPlacedFeature SPARSE_BIRCH_JUNGLE_TREES = register("sparse_birch_jungle_trees");
	public static final FrozenPlacedFeature CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees");
	public static final FrozenPlacedFeature CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water");
	public static final FrozenPlacedFeature BIG_SHRUB = register("big_shrub");
	public static final FrozenPlacedFeature PALM = register("palm_placed");
	public static final FrozenPlacedFeature PALM_JUNGLE = register("palm_jungle");
	public static final FrozenPlacedFeature PALMS_OASIS = register("palms_oasis");
	public static final FrozenPlacedFeature PALM_RARE = register("palm_rare");
	public static final FrozenPlacedFeature PALMS_WARM_BEACH = register("palms_warm_beach");
	public static final FrozenPlacedFeature CHERRY_TREES = register("cherry_trees");
	public static final FrozenPlacedFeature SNAPPED_OAK_PLACED = register("snapped_oak");
	public static final FrozenPlacedFeature SNAPPED_OAK_CLEARING_PLACED = register("snapped_oak_clearing");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_PLACED = register("snapped_birch");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_CLEARING_PLACED = register("snapped_birch_clearing");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_OAK_PLACED = register("snapped_birch_and_oak");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED = register("snapped_birch_and_oak_clearing");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_PLACED = register("snapped_spruce");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_CLEARING_PLACED = register("snapped_spruce_clearing");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_ON_SNOW_PLACED = register("snapped_spruce_on_snow");
	public static final FrozenPlacedFeature SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED = register("snapped_spruce_on_snow_clearing");
	public static final FrozenPlacedFeature SNAPPED_LARGE_SPRUCE_PLACED = register("snapped_large_spruce");
	public static final FrozenPlacedFeature SNAPPED_LARGE_SPRUCE_COMMON_PLACED = register("common_snapped_large_spruce");
	public static final FrozenPlacedFeature SNAPPED_LARGE_SPRUCE_CLEARING_PLACED = register("snapped_large_spruce_clearing");
	public static final FrozenPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED = register("snapped_large_spruce_on_snow");
	public static final FrozenPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED = register("snapped_large_spruce_on_snow_clearing");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED = register("snapped_birch_and_oak_and_spruce_clearing");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_SPRUCE_PLACED = register("snapped_birch_and_spruce");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED = register("snapped_birch_and_spruce_clearing");
	public static final FrozenPlacedFeature SNAPPED_CYPRESS_PLACED = register("snapped_cypress");
	public static final FrozenPlacedFeature SNAPPED_JUNGLE_PLACED = register("snapped_jungle");
	public static final FrozenPlacedFeature SNAPPED_LARGE_JUNGLE_PLACED = register("snapped_large_jungle");
	public static final FrozenPlacedFeature SNAPPED_BIRCH_AND_JUNGLE_PLACED = register("snapped_birch_and_jungle");
	public static final FrozenPlacedFeature SNAPPED_ACACIA_PLACED = register("snapped_acacia");
	public static final FrozenPlacedFeature SNAPPED_ACACIA_AND_OAK_PLACED = register("snapped_acacia_and_oak");
	public static final FrozenPlacedFeature SNAPPED_CHERRY_PLACED = register("snapped_cherry");
	public static final FrozenPlacedFeature SNAPPED_DARK_OAK_PLACED = register("snapped_dark_oak");
	public static final FrozenPlacedFeature SNAPPED_DARK_OAK_CLEARING_PLACED = register("snapped_dark_oak_clearing");
	//MUSHROOMS
	public static final FrozenPlacedFeature BROWN_SHELF_FUNGUS_PLACED = register("brown_shelf_fungus_placed");
	public static final FrozenPlacedFeature RED_SHELF_FUNGUS_PLACED = register("red_shelf_fungus_placed");
	public static final FrozenPlacedFeature BROWN_MUSHROOM_PLACED = register("brown_mushroom_placed");
	public static final FrozenPlacedFeature RED_MUSHROOM_PLACED = register("red_mushroom_placed");
	public static final FrozenPlacedFeature DARK_FOREST_MUSHROOM_PLACED = register("dark_forest_mushroom_placed");
	public static final FrozenPlacedFeature HUGE_RED_MUSHROOM_PLACED = register("huge_red_mushroom_placed");
	public static final FrozenPlacedFeature HUGE_BROWN_MUSHROOM_PLACED = register("huge_brown_mushroom_placed");
	public static final FrozenPlacedFeature HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp");
	public static final FrozenPlacedFeature MUSHROOM_PLACED = register("mushroom_placed");
	public static final FrozenPlacedFeature MIXED_MUSHROOMS_PLACED = register("mixed_mushroom_placed");
	public static final FrozenPlacedFeature RAINFOREST_MUSHROOMS_PLACED = register("rainforest_mushroom_placed");
	//GRASS AND FERNS
	public static final FrozenPlacedFeature OASIS_GRASS_PLACED = register("oasis_grass_placed");
	public static final FrozenPlacedFeature OASIS_BUSH_PLACED = register("oasis_bush_placed");
	public static final FrozenPlacedFeature DEAD_BUSH_PLACED = register("dead_bush_placed");
	public static final FrozenPlacedFeature DEAD_BUSH_AND_BUSH_PLACED = register("dead_bush_and_bush_placed");
	public static final FrozenPlacedFeature BUSH_AND_DEAD_BUSH_PLACED = register("bush_and_dead_bush_placed");
	public static final FrozenPlacedFeature JUNGLE_BUSH_PLACED = register("jungle_bush_placed");
	public static final FrozenPlacedFeature SPARSE_BUSH_PLACED = register("sparse_bush_placed");
	public static final FrozenPlacedFeature FLOWER_FIELD_BUSH_PLACED = register("flower_field_bush_placed");
	public static final FrozenPlacedFeature GENERIC_BUSH_PLACED = register("bush_placed");
	public static final FrozenPlacedFeature DESERT_BUSH_PLACED = register("desert_bush_placed");
	public static final FrozenPlacedFeature BADLANDS_BUSH_SAND_PLACED = register("badlands_bush_sand_placed");
	public static final FrozenPlacedFeature BADLANDS_BUSH_RARE_SAND_PLACED = register("badlands_bush_rare_sand_placed");
	public static final FrozenPlacedFeature BADLANDS_BUSH_TERRACOTTA_PLACED = register("badlands_bush_terracotta_placed");
	public static final FrozenPlacedFeature WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED = register("wooded_badlands_bush_terracotta_placed");
	public static final FrozenPlacedFeature ARID_BUSH_PLACED = register("arid_bush_placed");
	public static final FrozenPlacedFeature OASIS_CACTUS_PLACED = register("oasis_cactus_placed");
	public static final FrozenPlacedFeature TALL_CACTUS_PLACED = register("tall_cactus_placed");
	public static final FrozenPlacedFeature BADLANDS_TALL_CACTUS_PLACED = register("badlands_tall_cactus_placed");
	public static final FrozenPlacedFeature ARID_CACTUS_PLACED = register("arid_cactus_placed");
	public static final FrozenPlacedFeature GRASS_PLACED = register("grass_placed");
	public static final FrozenPlacedFeature GRASS_PLAINS_PLACED = register("grass_plains_placed");
	public static final FrozenPlacedFeature RARE_GRASS_PLACED = register("rare_grass_placed");
	public static final FrozenPlacedFeature TALL_GRASS = register("tall_grass");
	public static final FrozenPlacedFeature TALL_GRASS_PLAINS = register("tall_grass_plains");
	public static final FrozenPlacedFeature DENSE_TALL_GRASS_PLACED = register("dense_tall_grass_placed");
	public static final FrozenPlacedFeature DENSE_FERN_PLACED = register("dense_fern_placed");
	public static final FrozenPlacedFeature SEAGRASS_CYPRESS = register("seagrass_cypress");
	public static final FrozenPlacedFeature LARGE_FERN_AND_GRASS = register("large_fern_and_grass");
	public static final FrozenPlacedFeature LARGE_FERN_AND_GRASS_RARE = register("large_fern_and_grass_rare");
	public static final FrozenPlacedFeature TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");
	public static final FrozenPlacedFeature FLOWER_FIELD_GRASS_PLACED = register("flower_field_grass_placed");
	public static final FrozenPlacedFeature PATCH_TALL_GRASS_FLOWER_FIELD = register("patch_tall_grass_flower_field");
	//FLOWERS
	public static final FrozenPlacedFeature SEEDING_DANDELION = register("seeding_dandelion");
	public static final FrozenPlacedFeature COMMON_SEEDING_DANDELION = register("common_seeding_dandelion");
	public static final FrozenPlacedFeature RARE_SEEDING_DANDELION = register("rare_seeding_dandelion");
	public static final FrozenPlacedFeature CARNATION = register("carnation");
	public static final FrozenPlacedFeature DATURA = register("datura");
	public static final FrozenPlacedFeature COMMON_DATURA = register("common_datura");
	public static final FrozenPlacedFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenPlacedFeature FLOWER_MEADOW = register("flower_meadow");
	public static final FrozenPlacedFeature DENSE_FLOWER_PLACED = register("dense_flower_placed");
	public static final FrozenPlacedFeature FLOWER_FOREST_FLOWERS = register("flower_forest_flowers");
	public static final FrozenPlacedFeature CYPRESS_WETLANDS_FLOWERS = register("cypress_wetlands_flowers");
	public static final FrozenPlacedFeature MILKWEED = register("milkweed");
	public static final FrozenPlacedFeature MILKWEED_RARE = register("milkweed_rare");
	public static final FrozenPlacedFeature GLORY_OF_THE_SNOW = register("glory_of_the_snow");
	public static final FrozenPlacedFeature GLORY_OF_THE_SNOW_JUNGLE = register("glory_of_the_snow_jungle");
	public static final FrozenPlacedFeature GLORY_OF_THE_SNOW_SPARSE_JUNGLE = register("glory_of_the_snow_sparse_jungle");
	public static final FrozenPlacedFeature FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenPlacedFeature FLOWER_TEMPERATE_RAINFOREST = register("flower_temperate_rainforest");
	public static final FrozenPlacedFeature FLOWER_TEMPERATE_RAINFOREST_VANILLA = register("flower_temperate_rainforest_vanilla");
	public static final FrozenPlacedFeature FLOWER_RAINFOREST = register("flower_rainforest");
	public static final FrozenPlacedFeature FLOWER_RAINFOREST_VANILLA = register("flower_rainforest_vanilla");
	public static final FrozenPlacedFeature FLOWER_JUNGLE = register("flower_jungle");
	public static final FrozenPlacedFeature FLOWER_SUNFLOWER_PLAINS = register("flower_sunflower_plains");
	public static final FrozenPlacedFeature FLOWER_BIRCH_CLEARING = register("flower_birch_clearing");
	public static final FrozenPlacedFeature FLOWER_FOREST_CLEARING = register("flower_forest_clearing");
	public static final FrozenPlacedFeature FLOWER_SPARSE_JUNGLE = register("flower_sparse_jungle");
	public static final FrozenPlacedFeature FLOWER_CHERRY = register("flower_cherry");
	public static final FrozenPlacedFeature MOSS_CARPET = register("moss_carpet");
	public static final FrozenPlacedFeature TALL_FLOWER_FIELD_FLOWERS = register("tall_flower_field_flowers");
	//VEGETATION
	public static final FrozenPlacedFeature POLLEN_PLACED = register("pollen");
	public static final FrozenPlacedFeature PATCH_CATTAIL = register("cattail");
	public static final FrozenPlacedFeature PATCH_CATTAIL_UNCOMMON = register("cattail_uncommon");
	public static final FrozenPlacedFeature PATCH_CATTAIL_COMMON = register("cattail_common");
	public static final FrozenPlacedFeature PATCH_CATTAIL_MUD = register("cattail_mud");
	public static final FrozenPlacedFeature PATCH_CATTAIL_MUD_UNCOMMON = register("cattail_mud_uncommon");
	public static final FrozenPlacedFeature PATCH_CATTAIL_MUD_COMMON = register("cattail_mud_common");
	public static final FrozenPlacedFeature PATCH_FLOWERING_WATERLILY = register("patch_flowering_waterlily");
	public static final FrozenPlacedFeature PATCH_ALGAE = register("patch_algae");
	public static final FrozenPlacedFeature PATCH_ALGAE_SMALL = register("patch_algae_small");
	public static final FrozenPlacedFeature PATCH_BERRY_FOREST = register("patch_berry_forest");
	public static final FrozenPlacedFeature TERMITE_PLACED = register("termite_placed");
	public static final FrozenPlacedFeature TUMBLEWEED = register("tumbleweed");
	public static final FrozenPlacedFeature PRICKLY_PEAR = register("prickly_pear");
	public static final FrozenPlacedFeature PRICKLY_PEAR_RARE = register("prickly_pear_rare");
	public static final FrozenPlacedFeature JELLYFISH_CAVES_BLUE_MESOGLEA = register("blue_mesoglea");
	public static final FrozenPlacedFeature JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = register("upside_down_blue_mesoglea");
	public static final FrozenPlacedFeature JELLYFISH_CAVES_PURPLE_MESOGLEA = register("purple_mesoglea");
	public static final FrozenPlacedFeature JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = register("upside_down_purple_mesoglea");
	public static final FrozenPlacedFeature NEMATOCYST_BLUE = register("nematocyst_blue");
	public static final FrozenPlacedFeature NEMATOCYST_PURPLE = register("nematocyst_purple");
	public static final FrozenPlacedFeature MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple");
	public static final FrozenPlacedFeature MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue");
	public static final FrozenPlacedFeature LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple");
	public static final FrozenPlacedFeature LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue");
	public static final FrozenPlacedFeature UPSIDE_DOWN_MAGMA = register("upside_down_magma");
	public static final FrozenPlacedFeature SMALL_SPONGES = register("small_sponges");
	public static final FrozenPlacedFeature SMALL_SPONGES_RARE = register("small_sponges_rare");
	public static final FrozenPlacedFeature PATCH_MELON = register("patch_melon");
	public static final FrozenPlacedFeature PATCH_PUMPKIN_COMMON = register("patch_pumpkin_common");

	private WilderPlacedFeatures() {
		throw new UnsupportedOperationException("WilderPlacedFeatures contains only static declarations.");
	}

	public static void registerPlacedFeatures(@NotNull BootstrapContext<PlacedFeature> entries) {

		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);

		WilderSharedConstants.logWithModId("Registering WilderPlacedFeatures for ", true);

		// FALLEN TREES

		FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_TREES_MIXED.getHolder(),
			RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WilderConfiguredFeatures.MOSSY_FALLEN_TREES_MIXED.getHolder(),
			CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH.getHolder(),
			CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_BIRCH_AND_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_OAK_PLACED_SWAMP.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_OAK_SWAMP.getHolder(),
			RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_OAK_AND_CYPRESS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_CYPRESS_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_BIRCH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_BIRCH.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_CHERRY_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_CHERRY.getHolder(),
			RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		CLEAN_FALLEN_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.CLEAN_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WilderConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WilderConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED_2.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_ACACIA_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(29), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED.makeAndSetHolder(WilderTreeConfigured.FALLEN_PALM.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WilderBlockTags.FALLEN_TREE_PLACEABLE)),
			RarityFilter.onAverageOnceEvery(60), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED_RARE.makeAndSetHolder(WilderTreeConfigured.FALLEN_PALM.getHolder(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WilderBlockTags.FALLEN_TREE_PLACEABLE)),
			RarityFilter.onAverageOnceEvery(135), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_LARGE_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(25), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_COMMON_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_LARGE_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_OAK_AND_BIRCH_DARK_FOREST.getHolder(),
			RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_DARK_OAKS.getHolder(),
			RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_COMMON_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_DARK_OAKS.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		FALLEN_MANGROVE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FALLEN_MANGROVE.getHolder(),
			RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		// TREES

		TREES_PLAINS.makeAndSetHolder(WilderConfiguredFeatures.TREES_PLAINS.getHolder(),
			PlacementUtils.countExtra(1, 0.1F, 1), RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), BiomeFilter.biome()
		);

		SHRUBS_FOREST.makeAndSetHolder(WilderConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1), InSquarePlacement.spread(), TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), BiomeFilter.biome(),
			WilderPlacementUtils.SHRUB_CLEARING_FILTER
		);

		SHRUBS.makeAndSetHolder(WilderConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1), RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), BiomeFilter.biome(),
			WilderPlacementUtils.SHRUB_CLEARING_FILTER
		);

		SHRUBS_WATER.makeAndSetHolder(WilderConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1), RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(), TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), BiomeFilter.biome(),
			WilderPlacementUtils.SHRUB_CLEARING_FILTER
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(WilderConfiguredFeatures.TREES_FLOWER_FIELD.getHolder(),
			PlacementUtils.countExtra(0, 0.25F, 1), InSquarePlacement.spread(), TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING), BiomeFilter.biome()
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(WilderConfiguredFeatures.TREES_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_DYING_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_DYING_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_SNOWY_DYING_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_DYING_MIXED_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_SNOWY_DYING_MIXED_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TREES_BIRCH_AND_OAK),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(WilderConfiguredFeatures.TREES_SEMI_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(11, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_FLOWER_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(WilderConfiguredFeatures.DARK_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(16), InSquarePlacement.spread(), TREE_THRESHOLD, WilderPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(WilderConfiguredFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(17), InSquarePlacement.spread(), TREE_THRESHOLD, WilderPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(WilderConfiguredFeatures.DARK_BIRCH_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(14), InSquarePlacement.spread(), TREE_THRESHOLD, WilderPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(WilderConfiguredFeatures.DARK_TAIGA_VEGETATION.getHolder(),
			CountPlacement.of(14), InSquarePlacement.spread(), TREE_THRESHOLD, WilderPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome()
		);

		TREES_BIRCH.makeAndSetHolder(WilderConfiguredFeatures.TREES_BIRCH.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		BIRCH_TALL.makeAndSetHolder(WilderConfiguredFeatures.TREES_BIRCH_TALL.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.TREES_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		SHORT_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SHORT_TREES_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(5, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		SHORT_SPRUCE_RARE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SHORT_TREES_TAIGA.getHolder(),
			treePlacement(PlacementUtils.countExtra(5, 0.1F, 1))
		);

		SHORT_MEGA_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SHORT_MEGA_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(WilderConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA1.makeAndSetHolder(WilderConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(WilderConfiguredFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TREES_SNOWY.makeAndSetHolder(WilderTreeConfigured.SPRUCE_SHORT.getHolder(),
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);

		TREES_GROVE.makeAndSetHolder(WilderConfiguredFeatures.TREES_GROVE.getHolder(),
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS.getHolder(),
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);

		TREES_WINDSWEPT_FOREST.makeAndSetHolder(WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS.getHolder(),
			treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);

		TREES_MEADOW.makeAndSetHolder(WilderConfiguredFeatures.MEADOW_TREES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(100))
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		SAVANNA_TREES.makeAndSetHolder(WilderConfiguredFeatures.SAVANNA_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(WilderConfiguredFeatures.ARID_SAVANNA_TREES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(WilderConfiguredFeatures.WOODED_BADLANDS_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(7, 0.1F, 1))
		);

		TREES_SWAMP.makeAndSetHolder(WilderTreeConfigured.SWAMP_TREE.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(3), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		MIXED_TREES.makeAndSetHolder(WilderConfiguredFeatures.MIXED_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(14, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(WilderConfiguredFeatures.TEMPERATE_RAINFOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(13, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		RAINFOREST_TREES.makeAndSetHolder(WilderConfiguredFeatures.RAINFOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(WilderConfiguredFeatures.BIRCH_TAIGA_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(WilderConfiguredFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(WilderConfiguredFeatures.PARCHED_FOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 1))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		ARID_FOREST_TREES.makeAndSetHolder(WilderConfiguredFeatures.ARID_FOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER
				).build()
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(WilderConfiguredFeatures.BIRCH_JUNGLE_TREES.getHolder(),
			treePlacement(CountPlacement.of(29))
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(WilderConfiguredFeatures.SPARSE_BIRCH_JUNGLE_TREES.getHolder(),
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1f, 1))
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES.getHolder(),
			CountPlacement.of(28), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), PlacementUtils.filteredByBlockSurvival(RegisterBlocks.CYPRESS_SAPLING)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER.getHolder(),
			CountPlacement.of(UniformInt.of(5, 10)), SurfaceWaterDepthFilter.forMaxDepth(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		BIG_SHRUB.makeAndSetHolder(WilderConfiguredFeatures.BIG_COARSE_SHRUBS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		PALM.makeAndSetHolder(WilderConfiguredFeatures.PALMS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(4))
		);

		PALM_JUNGLE.makeAndSetHolder(WilderConfiguredFeatures.PALMS_JUNGLE.getHolder(),
			treePlacement(PlacementUtils.countExtra(8, 0.5F, 2))
		);

		PALMS_OASIS.makeAndSetHolder(WilderConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(3))
		);

		PALM_RARE.makeAndSetHolder(WilderConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		PALMS_WARM_BEACH.makeAndSetHolder(WilderConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(6))
		);

		CHERRY_TREES.makeAndSetHolder(WilderConfiguredFeatures.CHERRIES.getHolder(),
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 3))
		);

		SNAPPED_BIRCH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCHES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCHES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_OAKS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_OAK_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_OAKS.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_BIRCH_AND_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_OAK.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_SPRUCES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_LARGE_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(4))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		SNAPPED_CYPRESS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_CYPRESSES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_JUNGLE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_JUNGLES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_JUNGLE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_LARGE_JUNGLES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_JUNGLE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_BIRCH_AND_JUNGLE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_ACACIAS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_ACACIA_AND_OAK.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_CHERRY_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_CHERRY.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(58))
		);

		SNAPPED_DARK_OAK_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_DARK_OAKS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(48))
		);

		SNAPPED_DARK_OAK_CLEARING_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SNAPPED_DARK_OAKS.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(
					WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
				).build()
		);

		// MUSHROOMS

		BROWN_SHELF_FUNGUS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED.getHolder(),
			RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome()
		);

		RED_SHELF_FUNGUS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.RED_SHELF_FUNGUS_CONFIGURED.getHolder(),
			RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome()
		);

		BROWN_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_BROWN_MUSHROOM),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		RED_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_RED_MUSHROOM),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		DARK_FOREST_MUSHROOM_PLACED.makeAndSetHolder(WilderConfiguredFeatures.MUSHROOMS_DARK_FOREST.getHolder(),
			RarityFilter.onAverageOnceEvery(4), CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		HUGE_RED_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		HUGE_BROWN_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		HUGE_MUSHROOMS_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		MIXED_MUSHROOMS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(75), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		RAINFOREST_MUSHROOMS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		// GRASS AND FERNS

		OASIS_GRASS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.OASIS_GRASS.getHolder(),
			CountPlacement.of(19), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		OASIS_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.OASIS_BUSH.getHolder(),
			CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		DEAD_BUSH_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_DEAD_BUSH),
			CountPlacement.of(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(Blocks.DEAD_BUSH), BiomeFilter.biome()
		);

		DEAD_BUSH_AND_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.DEAD_BUSH_AND_BUSH.getHolder(),
			CountPlacement.of(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		BUSH_AND_DEAD_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.BUSH_AND_DEAD_BUSH.getHolder(),
			CountPlacement.of(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		JUNGLE_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.JUNGLE_BUSH.getHolder(),
			CountPlacement.of(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		SPARSE_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.SPARSE_BUSH.getHolder(),
			CountPlacement.of(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		FLOWER_FIELD_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FLOWER_FIELD_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		GENERIC_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.GENERIC_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		DESERT_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.DESERT_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		BADLANDS_BUSH_SAND_PLACED.makeAndSetHolder(WilderConfiguredFeatures.BADLANDS_BUSH_SAND.getHolder(),
			RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		BADLANDS_BUSH_RARE_SAND_PLACED.makeAndSetHolder(WilderConfiguredFeatures.BADLANDS_BUSH_SAND.getHolder(),
			RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		BADLANDS_BUSH_TERRACOTTA_PLACED.makeAndSetHolder(WilderConfiguredFeatures.BADLANDS_BUSH_TERRACOTTA.getHolder(),
			RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED.makeAndSetHolder(WilderConfiguredFeatures.WOODED_BADLANDS_BUSH_TERRACOTTA.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		ARID_BUSH_PLACED.makeAndSetHolder(WilderConfiguredFeatures.DESERT_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, PlacementUtils.filteredByBlockSurvival(RegisterBlocks.BUSH), BiomeFilter.biome()
		);

		OASIS_CACTUS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.PATCH_CACTUS_OASIS.getHolder(),
			RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		TALL_CACTUS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.PATCH_CACTUS_TALL.getHolder(),
			RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		BADLANDS_TALL_CACTUS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.PATCH_CACTUS_TALL_BADLANDS.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		ARID_CACTUS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_CACTUS),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		GRASS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.FERN_AND_GRASS.getHolder(),
			CountPlacement.of(20), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		GRASS_PLAINS_PLACED.makeAndSetHolder(WilderConfiguredFeatures.GRASS_AND_FERN.getHolder(),
			CountPlacement.of(15), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RARE_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE),
			CountPlacement.of(8), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS_PLAINS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DENSE_TALL_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DENSE_FERN_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_LARGE_FERN),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SEAGRASS_CYPRESS.makeAndSetHolder(configuredFeatures.getOrThrow(AquaticFeatures.SEAGRASS_MID),
			seagrassPlacement(56)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(WilderConfiguredFeatures.LARGE_FERN_AND_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LARGE_FERN_AND_GRASS_RARE.makeAndSetHolder(WilderConfiguredFeatures.LARGE_FERN_AND_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(WilderConfiguredFeatures.TALL_GRASS_AND_GRASS_WATER.getHolder(),
			RarityFilter.onAverageOnceEvery(2), CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_FIELD_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE),
			CountPlacement.of(15), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_TALL_GRASS_FLOWER_FIELD.makeAndSetHolder(WilderConfiguredFeatures.LARGE_FERN_AND_GRASS_2.getHolder(),
			NoiseThresholdCountPlacement.of(-0.8, 0, 7), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		// FLOWERS

		SEEDING_DANDELION.makeAndSetHolder(WilderConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		COMMON_SEEDING_DANDELION.makeAndSetHolder(WilderConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		RARE_SEEDING_DANDELION.makeAndSetHolder(WilderConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		CARNATION.makeAndSetHolder(WilderConfiguredFeatures.CARNATION.getHolder(),
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		DATURA.makeAndSetHolder(WilderConfiguredFeatures.DATURA.getHolder(),
			RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		COMMON_DATURA.makeAndSetHolder(WilderConfiguredFeatures.DATURA.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_PLAINS.makeAndSetHolder(WilderConfiguredFeatures.FLOWER_PLAINS.getHolder(),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_MEADOW.makeAndSetHolder(WilderConfiguredFeatures.FLOWER_MEADOW.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		DENSE_FLOWER_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FLOWER_DEFAULT),
			CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_FOREST_FLOWERS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FOREST_FLOWERS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)), BiomeFilter.biome()
		);

		CYPRESS_WETLANDS_FLOWERS.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_CYPRESS_WETLANDS.getHolder(),
			CountPlacement.of(UniformInt.of(1, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		MILKWEED.makeAndSetHolder(WilderConfiguredFeatures.MILKWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		MILKWEED_RARE.makeAndSetHolder(WilderConfiguredFeatures.MILKWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		GLORY_OF_THE_SNOW.makeAndSetHolder(WilderConfiguredFeatures.GLORY_OF_THE_SNOW.getHolder(),
			RarityFilter.onAverageOnceEvery(11), CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		GLORY_OF_THE_SNOW_JUNGLE.makeAndSetHolder(WilderConfiguredFeatures.GLORY_OF_THE_SNOW_JUNGLE.getHolder(),
			CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		GLORY_OF_THE_SNOW_SPARSE_JUNGLE.makeAndSetHolder(WilderConfiguredFeatures.GLORY_OF_THE_SNOW_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(WilderConfiguredFeatures.FLOWER_FLOWER_FIELD.getHolder(),
			CountPlacement.of(UniformInt.of(1, 4)), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_TEMPERATE_RAINFOREST.getHolder(),
			CountPlacement.of(2), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_TEMPERATE_RAINFOREST_VANILLA.getHolder(),
			CountPlacement.of(2), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_RAINFOREST.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_RAINFOREST.getHolder(),
			CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_RAINFOREST_VANILLA.getHolder(),
			CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_JUNGLE.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_JUNGLE.getHolder(),
			CountPlacement.of(20), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_CHERRY.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_CHERRY.getHolder(),
			CountPlacement.of(UniformInt.of(3, 8)), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_SUNFLOWER_PLAINS.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_SUNFLOWER_PLAINS.getHolder(),
			CountPlacement.of(UniformInt.of(8, 14)), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		FLOWER_BIRCH_CLEARING.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_BIRCH_CLEARING.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(),
			WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
		);

		FLOWER_FOREST_CLEARING.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_FOREST_CLEARING.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(),
			WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED
		);

		FLOWER_SPARSE_JUNGLE.makeAndSetHolder(WilderConfiguredFeatures.FLOWERS_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		MOSS_CARPET.makeAndSetHolder(WilderConfiguredFeatures.MOSS_CARPET.getHolder(),
			RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		TALL_FLOWER_FIELD_FLOWERS.makeAndSetHolder(WilderConfiguredFeatures.TALL_FLOWER_FLOWER_FIELD.getHolder(),
			RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 4), 0, 4)), BiomeFilter.biome()
		);

		// VEGETATION

		POLLEN_PLACED.makeAndSetHolder(WilderConfiguredFeatures.POLLEN_CONFIGURED.getHolder(),
			RarityFilter.onAverageOnceEvery(1), CountPlacement.of(2), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_CATTAIL.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
		);

		PATCH_CATTAIL_UNCOMMON.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
		);

		PATCH_CATTAIL_COMMON.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL_SMALL.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome()
		);

		PATCH_CATTAIL_MUD.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL_MUD.getHolder(),
			RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_CATTAIL_MUD_UNCOMMON.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL_MUD_SMALL.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)), RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_CATTAIL_MUD_COMMON.makeAndSetHolder(WilderConfiguredFeatures.CATTAIL_MUD_SMALL.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(WilderConfiguredFeatures.PATCH_FLOWERING_WATERLILY.getHolder(),
			CountPlacement.of(1), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_ALGAE.makeAndSetHolder(WilderConfiguredFeatures.PATCH_ALGAE.getHolder(),
			RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WilderConfiguredFeatures.PATCH_ALGAE_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PATCH_BERRY_FOREST.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_BERRY_BUSH),
			RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		TERMITE_PLACED.makeAndSetHolder(WilderConfiguredFeatures.TERMITE_CONFIGURED.getHolder(),
			RarityFilter.onAverageOnceEvery(45), CountPlacement.of(1),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome()
		);

		TUMBLEWEED.makeAndSetHolder(WilderConfiguredFeatures.TUMBLEWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(9), CountPlacement.of(1),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PRICKLY_PEAR.makeAndSetHolder(WilderConfiguredFeatures.PRICKLY_PEAR.getHolder(),
			RarityFilter.onAverageOnceEvery(7), CountPlacement.of(1),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		PRICKLY_PEAR_RARE.makeAndSetHolder(WilderConfiguredFeatures.PRICKLY_PEAR.getHolder(),
			RarityFilter.onAverageOnceEvery(9), CountPlacement.of(1),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome()
		);

		JELLYFISH_CAVES_BLUE_MESOGLEA.makeAndSetHolder(WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA.makeAndSetHolder(WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		JELLYFISH_CAVES_PURPLE_MESOGLEA.makeAndSetHolder(WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA.makeAndSetHolder(WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		NEMATOCYST_BLUE.makeAndSetHolder(WilderConfiguredFeatures.NEMATOCYST_BLUE.getHolder(),
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(WilderConfiguredFeatures.NEMATOCYST_PURPLE.getHolder(),
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WilderConfiguredFeatures.MESOGLEA_CLUSTER_PURPLE.getHolder(),
			CountPlacement.of(UniformInt.of(9, 15)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(WilderConfiguredFeatures.MESOGLEA_CLUSTER_BLUE.getHolder(),
			CountPlacement.of(UniformInt.of(6, 13)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(WilderConfiguredFeatures.LARGE_MESOGLEA_PURPLE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(WilderConfiguredFeatures.LARGE_MESOGLEA_BLUE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(WilderConfiguredFeatures.UPSIDE_DOWN_MAGMA.getHolder(),
			CountPlacement.of(72),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 4),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		SMALL_SPONGES.makeAndSetHolder(WilderConfiguredFeatures.SMALL_SPONGE.getHolder(),
			CountPlacement.of(UniformInt.of(0, 3)),
			RarityFilter.onAverageOnceEvery(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SMALL_SPONGES_RARE.makeAndSetHolder(WilderConfiguredFeatures.SMALL_SPONGE.getHolder(),
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PATCH_MELON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_MELON),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_PUMPKIN_COMMON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_PUMPKIN),
			RarityFilter.onAverageOnceEvery(12),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);
	}

}
