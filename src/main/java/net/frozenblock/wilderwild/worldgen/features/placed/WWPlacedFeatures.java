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

package net.frozenblock.wilderwild.worldgen.features.placed;

import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils.register;
import net.frozenblock.wilderwild.worldgen.features.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.worldgen.features.configured.WWTreeConfigured;
import net.minecraft.core.BlockPos;
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
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public final class WWPlacedFeatures {
	//FALLEN TREES
	public static final FrozenLibPlacedFeature FALLEN_TREES_MIXED_PLACED = register("fallen_trees_mixed_placed");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_TREES_MIXED_PLACED = register("mossy_fallen_trees_mixed_placed");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED = register("mossy_fallen_trees_oak_and_birch_placed");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_AND_SPRUCE_PLACED = register("fallen_birch_and_spruce_placed");
	public static final FrozenLibPlacedFeature FALLEN_SWAMP_TREES = register("fallen_swamp_trees");
	public static final FrozenLibPlacedFeature FALLEN_SWAMP_TREES_WILLOW = register("fallen_swamp_trees_willow");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_SPRUCE_PLACED = register("fallen_oak_and_spruce_placed");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_BIRCH_PLACED = register("fallen_oak_and_birch_placed");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_CYPRESS_PLACED = register("fallen_oak_and_cypress_placed");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_PLACED = register("fallen_birch_placed");
	public static final FrozenLibPlacedFeature FALLEN_CHERRY_PLACED = register("fallen_cherry_placed");
	public static final FrozenLibPlacedFeature FALLEN_SPRUCE_PLACED = register("fallen_spruce_placed");
	public static final FrozenLibPlacedFeature CLEAN_FALLEN_SPRUCE_PLACED = register("clean_fallen_spruce_placed");
	public static final FrozenLibPlacedFeature CLEAN_FALLEN_LARGE_SPRUCE_PLACED = register("clean_fallen_large_spruce_placed");
	public static final FrozenLibPlacedFeature CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED = register("clean_fallen_large_spruce_common_placed");
	public static final FrozenLibPlacedFeature DECORATED_FALLEN_LARGE_SPRUCE_PLACED = register("decorated_fallen_large_spruce_placed");
	public static final FrozenLibPlacedFeature DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED = register("decorated_fallen_large_spruce_common_placed");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_BIRCH_PLACED_2 = register("fallen_oak_and_birch_placed_2");
	public static final FrozenLibPlacedFeature FALLEN_ACACIA_AND_OAK_PLACED = register("fallen_acacia_and_oak_placed");
	public static final FrozenLibPlacedFeature FALLEN_PALM_PLACED = register("fallen_palm_placed");
	public static final FrozenLibPlacedFeature FALLEN_PALM_PLACED_RARE = register("fallen_palm_placed_rare");
	public static final FrozenLibPlacedFeature FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED = register("fallen_palm_and_jungle_and_oak_placed");
	public static final FrozenLibPlacedFeature FALLEN_JUNGLE_AND_OAK_PLACED = register("fallen_jungle_and_oak_placed");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_JUNGLE_PLACED = register("large_fallen_jungle_placed");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_JUNGLE_COMMON_PLACED = register("large_fallen_jungle_common_placed");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED = register("fallen_birch_and_oak_dark_forest_placed");
	public static final FrozenLibPlacedFeature FALLEN_DARK_OAK_PLACED = register("fallen_dark_oak_placed");
	public static final FrozenLibPlacedFeature FALLEN_DARK_OAK_COMMON_PLACED = register("fallen_dark_oak_common_placed");
	public static final FrozenLibPlacedFeature FALLEN_MANGROVE_PLACED = register("fallen_mangrove_placed");
	public static final FrozenLibPlacedFeature FALLEN_MAPLE_PLACED = register("fallen_maple_placed");
	//TREES
	public static final FrozenLibPlacedFeature TREES_PLAINS = register("trees_plains");
	public static final FrozenLibPlacedFeature SHRUBS_FOREST = register("shrubs_forest");
	public static final FrozenLibPlacedFeature SHRUBS = register("shrubs");
	public static final FrozenLibPlacedFeature SHRUBS_WATER = register("shrubs_water");
	public static final FrozenLibPlacedFeature TREES_FLOWER_FIELD = register("trees_flower_field");
	public static final FrozenLibPlacedFeature TREES_BIRCH_AND_OAK = register("trees_birch_and_oak");
	public static final FrozenLibPlacedFeature TREES_DYING_FOREST = register("trees_dying_forest");
	public static final FrozenLibPlacedFeature TREES_SNOWY_DYING_FOREST = register("trees_snowy_dying_forest");
	public static final FrozenLibPlacedFeature TREES_DYING_MIXED_FOREST = register("trees_dying_mixed_forest");
	public static final FrozenLibPlacedFeature TREES_SNOWY_DYING_MIXED_FOREST = register("trees_snowy_dying_mixed_forest");
	public static final FrozenLibPlacedFeature TREES_BIRCH_AND_OAK_ORIGINAL = register("trees_birch_and_oak_original");
	public static final FrozenLibPlacedFeature TREES_SEMI_BIRCH_AND_OAK = register("trees_semi_birch_and_oak");
	public static final FrozenLibPlacedFeature TREES_SPARSE_FOREST = register("trees_sparse_forest");
	public static final FrozenLibPlacedFeature TREES_FLOWER_FOREST = register("trees_flower_forest");
	public static final FrozenLibPlacedFeature DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenLibPlacedFeature OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenLibPlacedFeature DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenLibPlacedFeature DARK_TAIGA_VEGETATION = register("dark_taiga_vegetation");
	public static final FrozenLibPlacedFeature TREES_BIRCH = register("trees_birch");
	public static final FrozenLibPlacedFeature TREES_BIRCH_TALL = register("trees_birch_tall");
	public static final FrozenLibPlacedFeature SPRUCE_PLACED = register("spruce_placed");
	public static final FrozenLibPlacedFeature SHORT_SPRUCE_PLACED = register("short_spruce_placed");
	public static final FrozenLibPlacedFeature SHORT_SPRUCE_RARE_PLACED = register("short_spruce_rare_placed");
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE_PLACED = register("short_mega_spruce_placed");
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW_PLACED = register("short_mega_spruce_on_snow_placed");
	public static final FrozenLibPlacedFeature TREES_OLD_GROWTH_PINE_TAIGA = register("trees_old_growth_pine_taiga");
	public static final FrozenLibPlacedFeature TREES_OLD_GROWTH_SPRUCE_TAIGA1 = register("trees_old_growth_spruce_taiga");
	public static final FrozenLibPlacedFeature TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenLibPlacedFeature TREES_SNOWY = register("trees_snowy");
	public static final FrozenLibPlacedFeature TREES_GROVE = register("trees_grove");
	public static final FrozenLibPlacedFeature TREES_WINDSWEPT_HILLS = register("trees_windswept_hills");
	public static final FrozenLibPlacedFeature TREES_WINDSWEPT_FOREST = register("trees_windswept_forest");
	public static final FrozenLibPlacedFeature TREES_MEADOW = register("trees_meadow");
	public static final FrozenLibPlacedFeature WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees");
	public static final FrozenLibPlacedFeature SAVANNA_TREES = register("savanna_trees");
	public static final FrozenLibPlacedFeature SAVANNA_TREES_BAOBAB = register("savanna_trees_baobab");
	public static final FrozenLibPlacedFeature SAVANNA_TREES_BAOBAB_VANILLA = register("savanna_trees_baobab_vanilla");
	public static final FrozenLibPlacedFeature ARID_SAVANNA_TREES = register("arid_savanna_trees");
	public static final FrozenLibPlacedFeature ARID_SAVANNA_TREES_PALM = register("arid_savanna_trees_palm");
	public static final FrozenLibPlacedFeature WOODED_BADLANDS_TREES = register("wooded_badlands_trees");
	public static final FrozenLibPlacedFeature TREES_SWAMP = register("trees_swamp");
	public static final FrozenLibPlacedFeature TREES_SWAMP_SURFACE_WILLOW = register("trees_swamp_surface_willow");
	public static final FrozenLibPlacedFeature TREES_SWAMP_WATER_SHALLOW = register("trees_swamp_water_shallow");
	public static final FrozenLibPlacedFeature TREES_SWAMP_WATER = register("trees_swamp_water");
	public static final FrozenLibPlacedFeature MIXED_TREES = register("mixed_trees");
	public static final FrozenLibPlacedFeature TEMPERATE_RAINFOREST_TREES = register("temperate_rainforest_trees");
	public static final FrozenLibPlacedFeature RAINFOREST_TREES = register("rainforest_trees");
	public static final FrozenLibPlacedFeature BIRCH_TAIGA_TREES = register("birch_taiga_trees");
	public static final FrozenLibPlacedFeature OLD_GROWTH_BIRCH_TAIGA_TREES = register("old_growth_birch_taiga_trees");
	public static final FrozenLibPlacedFeature PARCHED_FOREST_TREES = register("parched_forest_trees");
	public static final FrozenLibPlacedFeature ARID_FOREST_TREES = register("arid_forest_trees");
	public static final FrozenLibPlacedFeature BIRCH_JUNGLE_TREES = register("birch_jungle_trees");
	public static final FrozenLibPlacedFeature SPARSE_BIRCH_JUNGLE_TREES = register("sparse_birch_jungle_trees");
	public static final FrozenLibPlacedFeature CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees");
	public static final FrozenLibPlacedFeature CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water");
	public static final FrozenLibPlacedFeature BIG_SHRUB = register("big_shrub");
	public static final FrozenLibPlacedFeature PALM = register("palm_placed");
	public static final FrozenLibPlacedFeature PALM_JUNGLE = register("palm_jungle");
	public static final FrozenLibPlacedFeature PALMS_OASIS = register("palms_oasis");
	public static final FrozenLibPlacedFeature PALM_RARE = register("palm_rare");
	public static final FrozenLibPlacedFeature PALMS_WARM_BEACH = register("palms_warm_beach");
	public static final FrozenLibPlacedFeature BAMBOO_VEGETATION = register("bamboo_vegetation");
	public static final FrozenLibPlacedFeature TREES_SPARSE_JUNGLE = register("trees_sparse_jungle");
	public static final FrozenLibPlacedFeature TREES_JUNGLE = register("trees_jungle");
	public static final FrozenLibPlacedFeature TREES_MANGROVE = register("trees_mangrove");
	public static final FrozenLibPlacedFeature CHERRY_TREES = register("cherry_trees");
	public static final FrozenLibPlacedFeature MAPLE_TREES = register("maple_trees");
	public static final FrozenLibPlacedFeature SNAPPED_OAK_PLACED = register("snapped_oak");
	public static final FrozenLibPlacedFeature SNAPPED_OAK_CLEARING_PLACED = register("snapped_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_PLACED = register("snapped_birch");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_CLEARING_PLACED = register("snapped_birch_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_PLACED = register("snapped_birch_and_oak");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED = register("snapped_birch_and_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_PLACED = register("snapped_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_CLEARING_PLACED = register("snapped_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW_PLACED = register("snapped_spruce_on_snow");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED = register("snapped_spruce_on_snow_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_PLACED = register("snapped_large_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_COMMON_PLACED = register("common_snapped_large_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_CLEARING_PLACED = register("snapped_large_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED = register("snapped_large_spruce_on_snow");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED = register("snapped_large_spruce_on_snow_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED = register("snapped_birch_and_oak_and_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_SPRUCE_PLACED = register("snapped_birch_and_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED = register("snapped_birch_and_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_CYPRESS_PLACED = register("snapped_cypress");
	public static final FrozenLibPlacedFeature SNAPPED_JUNGLE_PLACED = register("snapped_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_JUNGLE_PLACED = register("snapped_large_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_JUNGLE_PLACED = register("snapped_birch_and_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_ACACIA_PLACED = register("snapped_acacia");
	public static final FrozenLibPlacedFeature SNAPPED_ACACIA_AND_OAK_PLACED = register("snapped_acacia_and_oak");
	public static final FrozenLibPlacedFeature SNAPPED_CHERRY_PLACED = register("snapped_cherry");
	public static final FrozenLibPlacedFeature SNAPPED_DARK_OAK_PLACED = register("snapped_dark_oak");
	public static final FrozenLibPlacedFeature SNAPPED_DARK_OAK_CLEARING_PLACED = register("snapped_dark_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_MAPLE_PLACED = register("snapped_maple");
	public static final FrozenLibPlacedFeature SNAPPED_MAPLE_CLEARING_PLACED = register("snapped_maple_clearing");
	//MUSHROOMS
	public static final FrozenLibPlacedFeature CRIMSON_SHELF_FUNGI = register("crimson_shelf_fungi");
	public static final FrozenLibPlacedFeature WARPED_SHELF_FUNGI = register("warped_shelf_fungi");
	public static final FrozenLibPlacedFeature CRIMSON_SHELF_FUNGI_RARE = register("crimson_shelf_fungi_rare");
	public static final FrozenLibPlacedFeature WARPED_SHELF_FUNGI_RARE = register("warped_shelf_fungi_rare");
	public static final FrozenLibPlacedFeature BROWN_MUSHROOM_PLACED = register("brown_mushroom_placed");
	public static final FrozenLibPlacedFeature RED_MUSHROOM_PLACED = register("red_mushroom_placed");
	public static final FrozenLibPlacedFeature DARK_FOREST_MUSHROOM_PLACED = register("dark_forest_mushroom_placed");
	public static final FrozenLibPlacedFeature HUGE_RED_MUSHROOM_PLACED = register("huge_red_mushroom_placed");
	public static final FrozenLibPlacedFeature HUGE_BROWN_MUSHROOM_PLACED = register("huge_brown_mushroom_placed");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp");
	public static final FrozenLibPlacedFeature MUSHROOM_PLACED = register("mushroom_placed");
	public static final FrozenLibPlacedFeature MIXED_MUSHROOMS_PLACED = register("mixed_mushroom_placed");
	public static final FrozenLibPlacedFeature RAINFOREST_MUSHROOMS_PLACED = register("rainforest_mushroom_placed");
	//GRASS AND FERNS
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_PLAIN = register("patch_grass_frozen_plain");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_FOREST = register("patch_grass_frozen_forest");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_NORMAL = register("patch_grass_frozen_normal");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA_2 = register("patch_grass_frozen_taiga_2");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA = register("patch_grass_frozen_taiga");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_BONEMEAL = register("grass_frozen_bonemeal");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS_2 = register("patch_frozen_tall_grass2");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS = register("patch_frozen_tall_grass");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_LARGE_FERN = register("patch_frozen_large_fern");
	public static final FrozenLibPlacedFeature OASIS_GRASS_PLACED = register("oasis_grass_placed");
	public static final FrozenLibPlacedFeature OASIS_BUSH_PLACED = register("oasis_bush_placed");
	public static final FrozenLibPlacedFeature JUNGLE_BUSH_PLACED = register("jungle_bush_placed");
	public static final FrozenLibPlacedFeature SPARSE_BUSH_PLACED = register("sparse_bush_placed");
	public static final FrozenLibPlacedFeature FLOWER_FIELD_BUSH_PLACED = register("flower_field_bush_placed");
	public static final FrozenLibPlacedFeature GENERIC_BUSH_PLACED = register("bush_placed");
	public static final FrozenLibPlacedFeature DESERT_BUSH_PLACED = register("desert_bush_placed");
	public static final FrozenLibPlacedFeature BADLANDS_BUSH_SAND_PLACED = register("badlands_bush_sand_placed");
	public static final FrozenLibPlacedFeature BADLANDS_BUSH_RARE_SAND_PLACED = register("badlands_bush_rare_sand_placed");
	public static final FrozenLibPlacedFeature BADLANDS_BUSH_TERRACOTTA_PLACED = register("badlands_bush_terracotta_placed");
	public static final FrozenLibPlacedFeature WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED = register("wooded_badlands_bush_terracotta_placed");
	public static final FrozenLibPlacedFeature WOODED_BADLANDS_BUSH_DIRT_PLACED = register("wooded_badlands_bush_dirt_placed");
	public static final FrozenLibPlacedFeature ARID_BUSH_PLACED = register("arid_bush_placed");
	public static final FrozenLibPlacedFeature OASIS_CACTUS_PLACED = register("oasis_cactus_placed");
	public static final FrozenLibPlacedFeature TALL_CACTUS_PLACED = register("tall_cactus_placed");
	public static final FrozenLibPlacedFeature BADLANDS_TALL_CACTUS_PLACED = register("badlands_tall_cactus_placed");
	public static final FrozenLibPlacedFeature ARID_CACTUS_PLACED = register("arid_cactus_placed");
	public static final FrozenLibPlacedFeature MYCELIUM_GROWTH_PLACED = register("mycelium_growth_placed");
	public static final FrozenLibPlacedFeature GRASS_PLACED = register("grass_placed");
	public static final FrozenLibPlacedFeature SWAMP_FERN = register("swamp_fern");
	public static final FrozenLibPlacedFeature GRASS_PLAINS_PLACED = register("grass_plains_placed");
	public static final FrozenLibPlacedFeature RARE_GRASS_PLACED = register("rare_grass_placed");
	public static final FrozenLibPlacedFeature TALL_GRASS = register("tall_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS_PLAINS = register("tall_grass_plains");
	public static final FrozenLibPlacedFeature SWAMP_TALL_GRASS_PLACED = register("tall_swamp_grass_placed");
	public static final FrozenLibPlacedFeature DENSE_TALL_GRASS_PLACED = register("dense_tall_grass_placed");
	public static final FrozenLibPlacedFeature DENSE_FERN_PLACED = register("dense_fern_placed");
	public static final FrozenLibPlacedFeature SEAGRASS_CYPRESS = register("seagrass_cypress");
	public static final FrozenLibPlacedFeature LARGE_FERN_AND_GRASS = register("large_fern_and_grass");
	public static final FrozenLibPlacedFeature LARGE_FERN_AND_GRASS_RARE = register("large_fern_and_grass_rare");
	public static final FrozenLibPlacedFeature TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");
	public static final FrozenLibPlacedFeature FLOWER_FIELD_GRASS_PLACED = register("flower_field_grass_placed");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_FLOWER_FIELD = register("patch_tall_grass_flower_field");
	//FLOWERS
	public static final FrozenLibPlacedFeature CLOVERS = register("clovers");
	public static final FrozenLibPlacedFeature CLOVERS_SPARSE = register("clovers_sparse");
	public static final FrozenLibPlacedFeature PHLOX = register("phlox");
	public static final FrozenLibPlacedFeature PHLOX_SPARSE = register("phlox_sparse");
	public static final FrozenLibPlacedFeature LANTANAS = register("lantanas");
	public static final FrozenLibPlacedFeature LANTANAS_SPARSE = register("lantanas_sparse");
	public static final FrozenLibPlacedFeature WILDFLOWERS = register("wildflowers");
	public static final FrozenLibPlacedFeature WILDFLOWERS_SPARSE = register("wildflowers_sparse");
	public static final FrozenLibPlacedFeature WILDFLOWERS_AND_PHLOX = register("wildflowers_and_phlox");
	public static final FrozenLibPlacedFeature WILDFLOWERS_AND_PHLOX_SPARSE = register("wildflowers_and_phlox_sparse");
	public static final FrozenLibPlacedFeature WILDFLOWERS_AND_LANTANAS = register("wildflowers_and_lantanas");
	public static final FrozenLibPlacedFeature LANTANAS_AND_PHLOX = register("lantanas_and_phlox");
	public static final FrozenLibPlacedFeature LANTANAS_AND_PHLOX_SPARSE = register("lantanas_and_phlox_sparse");
	public static final FrozenLibPlacedFeature SEEDING_DANDELION = register("seeding_dandelion");
	public static final FrozenLibPlacedFeature COMMON_SEEDING_DANDELION = register("common_seeding_dandelion");
	public static final FrozenLibPlacedFeature RARE_SEEDING_DANDELION = register("rare_seeding_dandelion");
	public static final FrozenLibPlacedFeature VERY_RARE_SEEDING_DANDELION = register("very_rare_seeding_dandelion");
	public static final FrozenLibPlacedFeature CARNATION = register("carnation");
	public static final FrozenLibPlacedFeature MARIGOLD = register("marigold");
	public static final FrozenLibPlacedFeature MARIGOLD_SPARSE = register("marigold_sparse");
	public static final FrozenLibPlacedFeature PINK_TULIP_UNCOMMON = register("pink_tulip_uncommon");
	public static final FrozenLibPlacedFeature ALLIUM_UNCOMMON = register("allium_uncommon");
	public static final FrozenLibPlacedFeature DATURA = register("datura");
	public static final FrozenLibPlacedFeature COMMON_DATURA = register("common_datura");
	public static final FrozenLibPlacedFeature ROSE_BUSH = register("rose_bush");
	public static final FrozenLibPlacedFeature PEONY = register("peony");
	public static final FrozenLibPlacedFeature LILAC = register("lilac");
	public static final FrozenLibPlacedFeature FLOWER_GENERIC = register("flower_generic");
	public static final FrozenLibPlacedFeature FLOWER_GENERIC_NO_CARNATION = register("flower_generic_no_carnation");
	public static final FrozenLibPlacedFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenLibPlacedFeature FLOWER_SNOWY_PLAINS = register("flower_snowy_plains");
	public static final FrozenLibPlacedFeature FLOWER_TUNDRA = register("flower_tundra");
	public static final FrozenLibPlacedFeature FLOWER_BIRCH = register("flower_birch");
	public static final FrozenLibPlacedFeature FLOWER_MEADOW = register("flower_meadow");
	public static final FrozenLibPlacedFeature DENSE_FLOWER_PLACED = register("dense_flower_placed");
	public static final FrozenLibPlacedFeature CYPRESS_WETLANDS_FLOWERS_SPARSE = register("cypress_wetlands_flowers_sparse");
	public static final FrozenLibPlacedFeature CYPRESS_WETLANDS_FLOWERS = register("cypress_wetlands_flowers");
	public static final FrozenLibPlacedFeature CYPRESS_WETLANDS_FLOWERS_TALL = register("cypress_wetlands_flowers_tall");
	public static final FrozenLibPlacedFeature MILKWEED = register("milkweed");
	public static final FrozenLibPlacedFeature MILKWEED_RARE = register("milkweed_rare");
	public static final FrozenLibPlacedFeature HIBISCUS = register("hibiscus");
	public static final FrozenLibPlacedFeature HIBISCUS_JUNGLE = register("hibiscus_jungle");
	public static final FrozenLibPlacedFeature HIBISCUS_SPARSE_JUNGLE = register("hibiscus_sparse_jungle");
	public static final FrozenLibPlacedFeature FLOWER_FLOWER_FOREST = register("flower_flower_forest");
	public static final FrozenLibPlacedFeature FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenLibPlacedFeature FLOWER_TEMPERATE_RAINFOREST = register("flower_temperate_rainforest");
	public static final FrozenLibPlacedFeature TALL_FLOWER_TEMPERATE_RAINFOREST = register("tall_flower_temperate_rainforest");
	public static final FrozenLibPlacedFeature FLOWER_TEMPERATE_RAINFOREST_VANILLA = register("flower_temperate_rainforest_vanilla");
	public static final FrozenLibPlacedFeature TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA = register("tall_flower_temperate_rainforest_vanilla");
	public static final FrozenLibPlacedFeature FLOWER_RAINFOREST = register("flower_rainforest");
	public static final FrozenLibPlacedFeature TALL_FLOWER_RAINFOREST = register("tall_flower_rainforest");
	public static final FrozenLibPlacedFeature FLOWER_RAINFOREST_VANILLA = register("flower_rainforest_vanilla");
	public static final FrozenLibPlacedFeature TALL_FLOWER_RAINFOREST_VANILLA = register("tall_flower_rainforest_vanilla");
	public static final FrozenLibPlacedFeature FLOWER_JUNGLE = register("flower_jungle");
	public static final FrozenLibPlacedFeature TALL_FLOWER_JUNGLE = register("tall_flower_jungle");
	public static final FrozenLibPlacedFeature FLOWER_SUNFLOWER_PLAINS = register("flower_sunflower_plains");
	public static final FrozenLibPlacedFeature FLOWER_BIRCH_CLEARING = register("flower_birch_clearing");
	public static final FrozenLibPlacedFeature FLOWER_FOREST_CLEARING = register("flower_forest_clearing");
	public static final FrozenLibPlacedFeature FLOWER_SPARSE_JUNGLE = register("flower_sparse_jungle");
	public static final FrozenLibPlacedFeature FLOWER_CHERRY = register("flower_cherry");
	public static final FrozenLibPlacedFeature MOSS_CARPET = register("moss_carpet");
	public static final FrozenLibPlacedFeature TALL_FLOWER_FIELD_FLOWERS = register("tall_flower_field_flowers");
	//VEGETATION
	public static final FrozenLibPlacedFeature POLLEN_PLACED = register("pollen");
	public static final FrozenLibPlacedFeature PATCH_BERRY_FOREST = register("patch_berry_forest");
	public static final FrozenLibPlacedFeature TERMITE_MOUND = register("termite_mound");
	public static final FrozenLibPlacedFeature TUMBLEWEED = register("tumbleweed");
	public static final FrozenLibPlacedFeature PRICKLY_PEAR = register("prickly_pear");
	public static final FrozenLibPlacedFeature PRICKLY_PEAR_RARE = register("prickly_pear_rare");
	public static final FrozenLibPlacedFeature PATCH_MELON = register("patch_melon");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_COMMON = register("patch_pumpkin_common");

	private WWPlacedFeatures() {
		throw new UnsupportedOperationException("WWPlacedFeatures contains only static declarations.");
	}

	public static void registerPlacedFeatures(@NotNull BootstrapContext<PlacedFeature> entries) {

		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);

		WWConstants.logWithModId("Registering WWPlacedFeatures for ", true);

		// FALLEN TREES

		FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_TREES_MIXED.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WWConfiguredFeatures.MOSSY_FALLEN_TREES_MIXED.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SWAMP_TREES.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SWAMP_TREES_WILLOW.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SWAMP_TREES_WILLOW.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SPRUCE_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_CYPRESS_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_CYPRESS_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_PLACED.makeAndSetHolder(
			WWConfiguredFeatures.FALLEN_BIRCH.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_CHERRY_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_CHERRY.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED_2.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_ACACIA_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(29),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM.getHolder(),
			RarityFilter.onAverageOnceEvery(60),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE)),
			BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED_RARE.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM.getHolder(),
			RarityFilter.onAverageOnceEvery(135),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.FALLEN_TREE_PLACEABLE)),
			BiomeFilter.biome()
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_JUNGLE_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_JUNGLE_AND_OAK.getHolder(),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_LARGE_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_LARGE_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_OAK_AND_BIRCH_DARK_FOREST.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_DARK_OAKS.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_DARK_OAKS.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_MANGROVE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_MANGROVE.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_MAPLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_MAPLE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		// TREES

		TREES_PLAINS.makeAndSetHolder(WWConfiguredFeatures.TREES_PLAINS.getHolder(),
			PlacementUtils.countExtra(1, 0.1F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		SHRUBS_FOREST.makeAndSetHolder(WWConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		SHRUBS.makeAndSetHolder(WWConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		SHRUBS_WATER.makeAndSetHolder(WWConfiguredFeatures.SHRUBS.getHolder(),
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.TREES_FLOWER_FIELD.getHolder(),
			PlacementUtils.countExtra(0, 0.25F, 1),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_DYING_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_DYING_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_SNOWY_DYING_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_DYING_MIXED_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_SNOWY_DYING_MIXED_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TREES_BIRCH_AND_OAK),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(WWConfiguredFeatures.TREES_SEMI_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(11, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SPARSE_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_AND_OAK_CALM.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 2))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_FLOWER_FOREST.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(16), InSquarePlacement.spread(),
			TREE_THRESHOLD,
			WWPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(17),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			WWPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_BIRCH_FOREST_VEGETATION.getHolder(),
			CountPlacement.of(14), InSquarePlacement.spread(),
			TREE_THRESHOLD,
			WWPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_TAIGA_VEGETATION.getHolder(),
			CountPlacement.of(14),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			WWPlacementUtils.TREE_CLEARING_FILTER,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		TREES_BIRCH.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_BIRCH_TALL.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_TALL.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.TREES_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SHORT_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_TREES_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(5, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SHORT_SPRUCE_RARE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_TREES_TAIGA.getHolder(),
			treePlacement(PlacementUtils.countExtra(5, 0.1F, 1))
		);

		SHORT_MEGA_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_MEGA_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA1.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY.makeAndSetHolder(WWTreeConfigured.SPRUCE_SHORT.getHolder(),
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);

		TREES_GROVE.makeAndSetHolder(WWConfiguredFeatures.TREES_GROVE.getHolder(),
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(WWConfiguredFeatures.TREES_WINDSWEPT_HILLS.getHolder(),
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);

		TREES_WINDSWEPT_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_WINDSWEPT_HILLS.getHolder(),
			treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);

		TREES_MEADOW.makeAndSetHolder(WWConfiguredFeatures.MEADOW_TREES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(100))
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.WINDSWEPT_SAVANNA_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		SAVANNA_TREES_BAOBAB.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES_BAOBAB.getHolder(),
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		SAVANNA_TREES_BAOBAB_VANILLA.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES_BAOBAB_VANILLA.getHolder(),
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.ARID_SAVANNA_TREES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		ARID_SAVANNA_TREES_PALM.makeAndSetHolder(WWConfiguredFeatures.ARID_SAVANNA_TREES_PALM.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(WWConfiguredFeatures.WOODED_BADLANDS_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))
		);

		TREES_SWAMP.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
		);

		TREES_SWAMP_SURFACE_WILLOW.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_SURFACE_WILLOW.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(1),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		TREES_SWAMP_WATER_SHALLOW.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_WATER_SHALLOW.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		TREES_SWAMP_WATER.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_WATER.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(4),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER),
					BlockPredicate.matchesFluids(Fluids.WATER)
				)
			),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		MIXED_TREES.makeAndSetHolder(WWConfiguredFeatures.MIXED_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(14, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.TEMPERATE_RAINFOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(13, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		RAINFOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.RAINFOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(WWConfiguredFeatures.BIRCH_TAIGA_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(WWConfiguredFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.PARCHED_FOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		ARID_FOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.ARID_FOREST_TREES.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(WWConfiguredFeatures.BIRCH_JUNGLE_TREES.getHolder(),
			treePlacement(CountPlacement.of(29))
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(WWConfiguredFeatures.SPARSE_BIRCH_JUNGLE_TREES.getHolder(),
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(WWConfiguredFeatures.CYPRESS_WETLANDS_TREES.getHolder(),
			CountPlacement.of(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(WWConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER.getHolder(),
			CountPlacement.of(UniformInt.of(5, 10)),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		BIG_SHRUB.makeAndSetHolder(WWConfiguredFeatures.BIG_COARSE_SHRUBS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		PALM.makeAndSetHolder(WWConfiguredFeatures.PALMS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(4))
		);

		PALM_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.PALMS_JUNGLE.getHolder(),
			treePlacement(PlacementUtils.countExtra(8, 0.5F, 2))
		);

		PALMS_OASIS.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(3))
		);

		PALM_RARE.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		PALMS_WARM_BEACH.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(6))
		);

		BAMBOO_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.BAMBOO_JUNGLE_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(30, 0.1F, 1))
		);

		TREES_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.SPARSE_JUNGLE_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		TREES_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.JUNGLE_TREES.getHolder(),
			treePlacement(PlacementUtils.countExtra(50, 0.1F, 1))
		);

		TREES_MANGROVE.makeAndSetHolder(WWConfiguredFeatures.MANGROVE_VEGETATION.getHolder(),
			CountPlacement.of(25),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE),
			BiomeFilter.biome()
		);

		CHERRY_TREES.makeAndSetHolder(WWConfiguredFeatures.CHERRIES.getHolder(),
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 3))
		);

		MAPLE_TREES.makeAndSetHolder(WWConfiguredFeatures.MAPLES.getHolder(),
			PlacementUtils.countExtra(6, 0.1F, 2),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		SNAPPED_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCHES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCHES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_OAKS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_OAKS.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(4))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_CYPRESS_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_CYPRESSES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_JUNGLES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_JUNGLES.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_JUNGLE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_ACACIAS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_ACACIA_AND_OAK.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_CHERRY_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_CHERRY.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(58))
		);

		SNAPPED_DARK_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_DARK_OAKS.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(48))
		);

		SNAPPED_DARK_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_DARK_OAKS.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_MAPLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_MAPLE.getHolder(),
			treePlacement(RarityFilter.onAverageOnceEvery(32))
		);

		SNAPPED_MAPLE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_MAPLE.getHolder(),
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		// MUSHROOMS

		BROWN_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_BROWN_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CRIMSON_SHELF_FUNGI.makeAndSetHolder(WWConfiguredFeatures.CRIMSON_SHELF_FUNGI.getHolder(),
			CountPlacement.of(230),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		WARPED_SHELF_FUNGI.makeAndSetHolder(WWConfiguredFeatures.WARPED_SHELF_FUNGI.getHolder(),
			CountPlacement.of(230),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		CRIMSON_SHELF_FUNGI_RARE.makeAndSetHolder(WWConfiguredFeatures.CRIMSON_SHELF_FUNGI.getHolder(),
			CountPlacement.of(40),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		WARPED_SHELF_FUNGI_RARE.makeAndSetHolder(WWConfiguredFeatures.WARPED_SHELF_FUNGI.getHolder(),
			CountPlacement.of(40),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		RED_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_RED_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DARK_FOREST_MUSHROOM_PLACED.makeAndSetHolder(WWConfiguredFeatures.MUSHROOMS_DARK_FOREST.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_RED_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_BROWN_MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_MUSHROOMS_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MUSHROOM_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MIXED_MUSHROOMS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(75),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RAINFOREST_MUSHROOMS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// GRASS AND FERNS

		PATCH_GRASS_FROZEN_PLAIN.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_GRASS.getHolder(),
			NoiseThresholdCountPlacement.of(-0.8D, 1, 3),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_GRASS_FROZEN_FOREST.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_GRASS_FROZEN_NORMAL.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_GRASS_FROZEN_TAIGA_2.makeAndSetHolder(WWConfiguredFeatures.PATCH_TAIGA_FROZEN_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_GRASS_FROZEN_TAIGA.makeAndSetHolder(WWConfiguredFeatures.PATCH_TAIGA_FROZEN_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_GRASS_FROZEN_BONEMEAL.makeAndSetHolder(WWConfiguredFeatures.SINGLE_PIECE_OF_FROZEN_GRASS.getHolder(),
			PlacementUtils.isEmpty()
		);

		PATCH_FROZEN_TALL_GRASS_2.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_TALL_GRASS.getHolder(),
			NoiseThresholdCountPlacement.of(-0.8D, 0, 3),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		PATCH_FROZEN_TALL_GRASS.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_TALL_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		PATCH_FROZEN_LARGE_FERN.makeAndSetHolder(WWConfiguredFeatures.PATCH_FROZEN_LARGE_FERN.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		OASIS_GRASS_PLACED.makeAndSetHolder(WWConfiguredFeatures.OASIS_GRASS.getHolder(),
			CountPlacement.of(19),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		OASIS_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.OASIS_BUSH.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		JUNGLE_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.JUNGLE_BUSH.getHolder(),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		SPARSE_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.SPARSE_BUSH.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		FLOWER_FIELD_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FIELD_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		GENERIC_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.GENERIC_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DESERT_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.DESERT_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		BADLANDS_BUSH_SAND_PLACED.makeAndSetHolder(WWConfiguredFeatures.BADLANDS_BUSH_SAND.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		BADLANDS_BUSH_RARE_SAND_PLACED.makeAndSetHolder(WWConfiguredFeatures.BADLANDS_BUSH_SAND.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		BADLANDS_BUSH_TERRACOTTA_PLACED.makeAndSetHolder(WWConfiguredFeatures.BADLANDS_BUSH_TERRACOTTA.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED.makeAndSetHolder(WWConfiguredFeatures.WOODED_BADLANDS_BUSH_TERRACOTTA.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		WOODED_BADLANDS_BUSH_DIRT_PLACED.makeAndSetHolder(WWConfiguredFeatures.WOODED_BADLANDS_BUSH_DIRT.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		ARID_BUSH_PLACED.makeAndSetHolder(WWConfiguredFeatures.DESERT_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.BUSH),
			BiomeFilter.biome()
		);

		OASIS_CACTUS_PLACED.makeAndSetHolder(WWConfiguredFeatures.PATCH_CACTUS_OASIS.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_CACTUS_PLACED.makeAndSetHolder(WWConfiguredFeatures.PATCH_CACTUS_TALL.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		BADLANDS_TALL_CACTUS_PLACED.makeAndSetHolder(WWConfiguredFeatures.PATCH_CACTUS_TALL_BADLANDS.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		ARID_CACTUS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_CACTUS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MYCELIUM_GROWTH_PLACED.makeAndSetHolder(WWConfiguredFeatures.MYCELIUM_GROWTH.getHolder(),
			CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		GRASS_PLACED.makeAndSetHolder(WWConfiguredFeatures.FERN_AND_GRASS.getHolder(),
			CountPlacement.of(20),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SWAMP_FERN.makeAndSetHolder(WWConfiguredFeatures.SWAMP_FERN.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		GRASS_PLAINS_PLACED.makeAndSetHolder(WWConfiguredFeatures.GRASS_AND_FERN.getHolder(),
			CountPlacement.of(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RARE_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS_PLAINS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SWAMP_TALL_GRASS_PLACED.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TALL_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DENSE_TALL_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_TALL_GRASS),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DENSE_FERN_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_LARGE_FERN),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SEAGRASS_CYPRESS.makeAndSetHolder(configuredFeatures.getOrThrow(AquaticFeatures.SEAGRASS_MID),
			seagrassPlacement(56)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LARGE_FERN_AND_GRASS_RARE.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(WWConfiguredFeatures.TALL_GRASS_AND_GRASS_WATER.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_FIELD_GRASS_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE),
			CountPlacement.of(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_TALL_GRASS_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS_2.getHolder(),
			NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// FLOWERS

		CLOVERS.makeAndSetHolder(WWConfiguredFeatures.CLOVERS.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CLOVERS_SPARSE.makeAndSetHolder(WWConfiguredFeatures.CLOVERS.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PHLOX.makeAndSetHolder(WWConfiguredFeatures.PHLOX.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.PHLOX.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LANTANAS.makeAndSetHolder(WWConfiguredFeatures.LANTANAS.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LANTANAS_SPARSE.makeAndSetHolder(WWConfiguredFeatures.LANTANAS.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		WILDFLOWERS.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		WILDFLOWERS_SPARSE.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		WILDFLOWERS_AND_PHLOX.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_PHLOX.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		WILDFLOWERS_AND_PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_PHLOX.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		WILDFLOWERS_AND_LANTANAS.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_LANTANAS.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LANTANAS_AND_PHLOX.makeAndSetHolder(WWConfiguredFeatures.LANTANAS_AND_PHLOX.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LANTANAS_AND_PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.LANTANAS_AND_PHLOX.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		COMMON_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RARE_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(17),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		VERY_RARE_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION.getHolder(),
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CARNATION.makeAndSetHolder(WWConfiguredFeatures.CARNATION.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MARIGOLD.makeAndSetHolder(WWConfiguredFeatures.MARIGOLD.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MARIGOLD_SPARSE.makeAndSetHolder(WWConfiguredFeatures.MARIGOLD_SPARSE.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PINK_TULIP_UNCOMMON.makeAndSetHolder(WWConfiguredFeatures.PINK_TULIP_UNCOMMON.getHolder(),
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		ALLIUM_UNCOMMON.makeAndSetHolder(WWConfiguredFeatures.ALLIUM_UNCOMMON.getHolder(),
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DATURA.makeAndSetHolder(WWConfiguredFeatures.DATURA.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		COMMON_DATURA.makeAndSetHolder(WWConfiguredFeatures.DATURA.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		ROSE_BUSH.makeAndSetHolder(WWConfiguredFeatures.ROSE_BUSH.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PEONY.makeAndSetHolder(WWConfiguredFeatures.PEONY.getHolder(),
			RarityFilter.onAverageOnceEvery(12),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		LILAC.makeAndSetHolder(WWConfiguredFeatures.LILAC.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_GENERIC.makeAndSetHolder(WWConfiguredFeatures.FLOWER_GENERIC.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_GENERIC_NO_CARNATION.makeAndSetHolder(WWConfiguredFeatures.FLOWER_GENERIC_NO_CARNATION.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_PLAINS.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_SNOWY_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_SNOWY_PLAINS.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_TUNDRA.makeAndSetHolder(WWConfiguredFeatures.FLOWER_TUNDRA.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_BIRCH.makeAndSetHolder(WWConfiguredFeatures.FLOWER_BIRCH.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_MEADOW.makeAndSetHolder(WWConfiguredFeatures.FLOWER_MEADOW.getHolder(),
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DENSE_FLOWER_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FLOWER_DEFAULT),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CYPRESS_WETLANDS_FLOWERS_SPARSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FOREST_FLOWERS),
			RarityFilter.onAverageOnceEvery(7),
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CYPRESS_WETLANDS_FLOWERS.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_CYPRESS_WETLANDS.getHolder(),
			CountPlacement.of(UniformInt.of(1, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CYPRESS_WETLANDS_FLOWERS_TALL.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_CYPRESS_WETLANDS.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MILKWEED.makeAndSetHolder(WWConfiguredFeatures.MILKWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MILKWEED_RARE.makeAndSetHolder(WWConfiguredFeatures.MILKWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HIBISCUS.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HIBISCUS_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HIBISCUS_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_FLOWER_FOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FLOWER_FIELD.getHolder(),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FLOWER_FIELD.getHolder(),
			CountPlacement.of(3),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_TEMPERATE_RAINFOREST.getHolder(),
			CountPlacement.of(2),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_TEMPERATE_RAINFOREST.getHolder(),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_TEMPERATE_RAINFOREST_VANILLA.getHolder(),
			CountPlacement.of(2),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_TEMPERATE_RAINFOREST_VANILLA.getHolder(),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_RAINFOREST.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_RAINFOREST.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_RAINFOREST_VANILLA.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_RAINFOREST_VANILLA.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_JUNGLE.getHolder(),
			CountPlacement.of(5),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWERS_JUNGLE.getHolder(),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_CHERRY.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_CHERRY.getHolder(),
			CountPlacement.of(UniformInt.of(3, 7)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_SUNFLOWER_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_SUNFLOWER_PLAINS.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FLOWER_BIRCH_CLEARING.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_BIRCH_CLEARING.getHolder(),
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome()
		);

		FLOWER_FOREST_CLEARING.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_FOREST_CLEARING.getHolder(),
			CountPlacement.of(UniformInt.of(0, 1)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome()
		);

		FLOWER_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.FLOWERS_JUNGLE.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MOSS_CARPET.makeAndSetHolder(WWConfiguredFeatures.MOSS_CARPET.getHolder(),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TALL_FLOWER_FIELD_FLOWERS.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_FLOWER_FIELD.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 4), 0, 4)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// VEGETATION

		POLLEN_PLACED.makeAndSetHolder(WWConfiguredFeatures.POLLEN.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_BERRY_FOREST.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_BERRY_BUSH),
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		TERMITE_MOUND.makeAndSetHolder(WWConfiguredFeatures.TERMITE_MOUND.getHolder(),
			RarityFilter.onAverageOnceEvery(45),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
			BiomeFilter.biome()
		);

		TUMBLEWEED.makeAndSetHolder(WWConfiguredFeatures.TUMBLEWEED.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PRICKLY_PEAR.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PRICKLY_PEAR_RARE.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
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
