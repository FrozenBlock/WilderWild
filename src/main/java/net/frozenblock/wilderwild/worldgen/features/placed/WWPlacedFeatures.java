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
import net.minecraft.core.Vec3i;
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
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;

public final class WWPlacedFeatures {
	// FALLEN TREES
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
	public static final FrozenLibPlacedFeature FALLEN_PALE_OAK_PLACED = register("fallen_pale_oak_placed");
	public static final FrozenLibPlacedFeature FALLEN_MANGROVE_PLACED = register("fallen_mangrove_placed");
	public static final FrozenLibPlacedFeature FALLEN_MAPLE_PLACED = register("fallen_maple_placed");
	// TREES
	public static final FrozenLibPlacedFeature TREES_PLAINS = register("trees_plains");
	public static final FrozenLibPlacedFeature BIG_BUSHES_FOREST = register("big_bushes_forest");
	public static final FrozenLibPlacedFeature BIG_BUSHES_FOREST_COMMON = register("big_bushes_forest_common");
	public static final FrozenLibPlacedFeature BIG_BUSHES = register("big_bushes");
	public static final FrozenLibPlacedFeature BIG_BUSHES_WATER = register("big_bushes_water");
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
	public static final FrozenLibPlacedFeature SPRUCE_PLACED_NO_LITTER = register("spruce_placed_no_litter");
	public static final FrozenLibPlacedFeature SHORT_SPRUCE_PLACED = register("short_spruce_placed");
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
	public static final FrozenLibPlacedFeature BIG_COARSE_BUSHES = register("big_coarse_bushes");
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
	public static final FrozenLibPlacedFeature TREES_PALE_GARDEN = register("trees_pale_garden");
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
	public static final FrozenLibPlacedFeature SNAPPED_PALE_OAK_PLACED = register("snapped_pale_oak");
	// MUSHROOMS
	public static final FrozenLibPlacedFeature CRIMSON_SHELF_FUNGI = register("crimson_shelf_fungi");
	public static final FrozenLibPlacedFeature WARPED_SHELF_FUNGI = register("warped_shelf_fungi");
	public static final FrozenLibPlacedFeature CRIMSON_SHELF_FUNGI_RARE = register("crimson_shelf_fungi_rare");
	public static final FrozenLibPlacedFeature WARPED_SHELF_FUNGI_RARE = register("warped_shelf_fungi_rare");
	public static final FrozenLibPlacedFeature BROWN_MUSHROOM = register("brown_mushroom");
	public static final FrozenLibPlacedFeature RED_MUSHROOM = register("red_mushroom");
	public static final FrozenLibPlacedFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibPlacedFeature HUGE_PALE_MUSHROOM = register("huge_pale_mushroom");
	public static final FrozenLibPlacedFeature MUSHROOMS_DARK_FOREST = register("mushrooms_dark_forest");
	public static final FrozenLibPlacedFeature HUGE_RED_MUSHROOM = register("huge_red_mushroom");
	public static final FrozenLibPlacedFeature HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS = register("huge_mushrooms");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS_RARE = register("huge_mushrooms_rare");
	public static final FrozenLibPlacedFeature MIXED_MUSHROOM = register("mixed_mushroom");
	public static final FrozenLibPlacedFeature RAINFOREST_MUSHROOM = register("rainforest_mushroom");
	// GRASS AND FERNS
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_PLAIN = register("patch_grass_frozen_plain");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_FOREST = register("patch_grass_frozen_forest");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_NORMAL = register("patch_grass_frozen_normal");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA_2 = register("patch_grass_frozen_taiga_2");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA = register("patch_grass_frozen_taiga");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_BONEMEAL = register("grass_frozen_bonemeal");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS_2 = register("patch_frozen_tall_grass2");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS = register("patch_frozen_tall_grass");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_LARGE_FERN = register("patch_frozen_large_fern");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_BUSH = register("patch_frozen_bush");
	public static final FrozenLibPlacedFeature PATCH_GRASS_OASIS = register("patch_grass_oasis");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_OASIS = register("patch_shrub_oasis");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_JUNGLE = register("patch_shrub_jungle");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_SPARSE = register("patch_shrub_sparse");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_FLOWER_FIELD = register("patch_shrub_flower_field");
	public static final FrozenLibPlacedFeature PATCH_SHRUB = register("patch_shrub");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_DESERT = register("patch_shrub_desert");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND = register("patch_shrub_badlands_sand");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND_RARE = register("patch_shrub_badlands_sand_rare");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_TERRACOTTA = register("patch_shrub_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA = register("patch_shrub_wooded_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_DIRT = register("patch_shrub_wooded_badlands_dirt");
	public static final FrozenLibPlacedFeature PATCH_BUSH_ARID = register("patch_bush_arid");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_OASIS = register("patch_cactus_oasis");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL = register("patch_cactus_tall");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands");
	public static final FrozenLibPlacedFeature PATCH_ARID_CACTUS = register("patch_arid_cactus");
	public static final FrozenLibPlacedFeature PATCH_MYCELIUM_GROWTH = register("patch_mycelium_growth");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS = register("patch_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS_LIGHT = register("patch_fern_and_grass_light");
	public static final FrozenLibPlacedFeature PATCH_GRASS_BIRCH = register("patch_grass_birch");
	public static final FrozenLibPlacedFeature PATCH_FERN_SWAMP = register("patch_fern_swamp");
	public static final FrozenLibPlacedFeature PATCH_GRASS_PLAINS = register("patch_grass_plains");
	public static final FrozenLibPlacedFeature PATCH_RARE_GRASS = register("patch_rare_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS = register("tall_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS_LIGHT = register("tall_grass_light");
	public static final FrozenLibPlacedFeature TALL_GRASS_PLAINS = register("tall_grass_plains");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_SWAMP = register("patch_tall_grass_swamp");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_DENSE = register("patch_tall_grass_dense");
	public static final FrozenLibPlacedFeature PATCH_FERN_DENSE = register("patch_fern_dense");
	public static final FrozenLibPlacedFeature SEAGRASS_CYPRESS = register("seagrass_cypress");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS = register("patch_large_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS_RARE = register("patch_large_fern_and_grass_rare");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_AND_GRASS_WATER = register("patch_tall_grass_and_grass_water");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FLOWER_FIELD = register("patch_grass_flower_field");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_FLOWER_FIELD = register("patch_tall_grass_flower_field");
	// FLOWERS
	public static final FrozenLibPlacedFeature PATCH_CLOVER = register("patch_clover");
	public static final FrozenLibPlacedFeature PATCH_CLOVER_SPARSE = register("patch_clover_sparse");
	public static final FrozenLibPlacedFeature PATCH_PHLOX = register("patch_phlox");
	public static final FrozenLibPlacedFeature PATCH_PHLOX_SPARSE = register("patch_phlox_sparse");
	public static final FrozenLibPlacedFeature PATCH_LANTANAS = register("patch_lantanas");
	public static final FrozenLibPlacedFeature PATCH_LANTANAS_SPARSE = register("patch_lantanas_sparse");
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS = register("patch_wildflowers");
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS_SPARSE = register("patch_wildflowers_sparse");
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS_AND_PHLOX = register("patch_wildflowers_and_phlox");
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS_AND_PHLOX_SPARSE = register("patch_wildflowers_and_phlox_sparse");
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS_AND_LANTANAS = register("patch_wildflowers_and_lantanas");
	public static final FrozenLibPlacedFeature PATCH_LANTANAS_AND_PHLOX = register("patch_lantanas_and_phlox");
	public static final FrozenLibPlacedFeature PATCH_LANTANAS_AND_PHLOX_SPARSE = register("patch_lantanas_and_phlox_sparse");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION = register("patch_seeding_dandelion");
	public static final FrozenLibPlacedFeature PATCH_COMMON_SEEDING_DANDELION = register("patch_common_seeding_dandelion");
	public static final FrozenLibPlacedFeature PATCH_RARE_SEEDING_DANDELION = register("patch_rare_seeding_dandelion");
	public static final FrozenLibPlacedFeature PATCH_VERY_RARE_SEEDING_DANDELION = register("patch_very_rare_seeding_dandelion");
	public static final FrozenLibPlacedFeature CARNATION = register("carnation");
	public static final FrozenLibPlacedFeature MARIGOLD = register("marigold");
	public static final FrozenLibPlacedFeature MARIGOLD_SPARSE = register("marigold_sparse");
	public static final FrozenLibPlacedFeature EYEBLOSSOM = register("eyeblossom");
	public static final FrozenLibPlacedFeature PINK_TULIP_UNCOMMON = register("pink_tulip_uncommon");
	public static final FrozenLibPlacedFeature ALLIUM_UNCOMMON = register("allium_uncommon");
	public static final FrozenLibPlacedFeature DATURA = register("datura");
	public static final FrozenLibPlacedFeature COMMON_DATURA = register("common_datura");
	public static final FrozenLibPlacedFeature ROSE_BUSH = register("rose_bush");
	public static final FrozenLibPlacedFeature ROSE_BUSH_SPARSE = register("rose_bush_sparse");
	public static final FrozenLibPlacedFeature PEONY = register("peony");
	public static final FrozenLibPlacedFeature PEONY_SPARSE = register("peony_sparse");
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
	public static final FrozenLibPlacedFeature MILKWEED_SWAMP = register("milkweed_swamp");
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
	public static final FrozenLibPlacedFeature FLOWER_FOREST_CLEARING = register("flower_forest_clearing");
	public static final FrozenLibPlacedFeature FLOWER_SPARSE_JUNGLE = register("flower_sparse_jungle");
	public static final FrozenLibPlacedFeature FLOWER_CHERRY = register("flower_cherry");
	public static final FrozenLibPlacedFeature MOSS_CARPET = register("moss_carpet");
	public static final FrozenLibPlacedFeature TALL_FLOWER_FLOWER_FIELD = register("tall_flower_flower_field");
	// VEGETATION
	public static final FrozenLibPlacedFeature POLLEN = register("pollen");
	public static final FrozenLibPlacedFeature PATCH_BERRY_FOREST = register("patch_berry_forest");
	public static final FrozenLibPlacedFeature TERMITE_MOUND = register("termite_mound");
	public static final FrozenLibPlacedFeature PATCH_TUMBLEWEED = register("patch_tumbleweed");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR = register("patch_prickly_pear");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR_RARE = register("patch_prickly_pear_rare");
	public static final FrozenLibPlacedFeature PATCH_MELON = register("patch_melon");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_COMMON = register("patch_pumpkin_common");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_DESERT = register("patch_dry_grass_desert");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BADLANDS = register("patch_dry_grass_badlands");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BEACH = register("patch_dry_grass_beach");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BETA_BEACH = register("patch_dry_grass_beta_beach");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER = register("patch_firefly_bush_near_water");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP = register("patch_firefly_bush_near_water_swamp");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_SWAMP = register("patch_firefly_bush_swamp");
	public static final FrozenLibPlacedFeature PATCH_LEAF_LITTER = register("patch_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_UNCOMMON_LEAF_LITTER = register("patch_uncommon_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_DARK_OAK_LEAF_LITTER = register("patch_dark_oak_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_PALE_OAK_LEAF_LITTER = register("patch_pale_oak_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_SPRUCE_LEAF_LITTER = register("patch_spruce_leaf_litter");

	private WWPlacedFeatures() {
		throw new UnsupportedOperationException("WWPlacedFeatures contains only static declarations.");
	}

	public static void registerPlacedFeatures(BootstrapContext<PlacedFeature> entries) {

		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);

		WWConstants.logWithModId("Registering WWPlacedFeatures for ", true);

		// FALLEN TREES
		FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_TREES_MIXED,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_MIXED_PLACED.makeAndSetHolder(WWConfiguredFeatures.MOSSY_FALLEN_TREES_MIXED,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_SPRUCE,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SWAMP_TREES,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SWAMP_TREES_WILLOW.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SWAMP_TREES_WILLOW,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SPRUCE_AND_OAK,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_OAK,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_CYPRESS_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_CYPRESS_AND_OAK,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_PLACED.makeAndSetHolder(
			WWConfiguredFeatures.FALLEN_BIRCH,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_CHERRY_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_CHERRY,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.CLEAN_LARGE_FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.DECORATED_LARGE_FALLEN_SPRUCE,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_OAK_AND_BIRCH_PLACED_2.makeAndSetHolder(WWConfiguredFeatures.FALLEN_BIRCH_AND_OAK,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_ACACIA_AND_OAK,
			RarityFilter.onAverageOnceEvery(29),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM,
			RarityFilter.onAverageOnceEvery(60),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.FALLEN_TREE_PLACEABLE)),
			BiomeFilter.biome()
		);

		FALLEN_PALM_PLACED_RARE.makeAndSetHolder(WWTreeConfigured.FALLEN_PALM,
			RarityFilter.onAverageOnceEvery(135),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.FALLEN_TREE_PLACEABLE)),
			BiomeFilter.biome()
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK,
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_JUNGLE_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_JUNGLE_AND_OAK,
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_LARGE_JUNGLE,
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		LARGE_FALLEN_JUNGLE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_LARGE_JUNGLE,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_OAK_AND_BIRCH_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_DARK_OAKS,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_DARK_OAK_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_DARK_OAKS,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_PALE_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_PALE_OAKS,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_MANGROVE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_MANGROVE,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		FALLEN_MAPLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.FALLEN_MAPLE,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		// TREES
		TREES_PLAINS.makeAndSetHolder(WWConfiguredFeatures.TREES_PLAINS,
			PlacementUtils.countExtra(1, 0.1F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BIG_BUSHES_FOREST.makeAndSetHolder(WWConfiguredFeatures.BIG_BUSHES,
			PlacementUtils.countExtra(1, 0.2F, 1),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		BIG_BUSHES_FOREST_COMMON.makeAndSetHolder(WWConfiguredFeatures.BIG_BUSHES.getHolder(),
			PlacementUtils.countExtra(5, 0.2F, 1),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		BIG_BUSHES.makeAndSetHolder(WWConfiguredFeatures.BIG_BUSHES,
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		BIG_BUSHES_WATER.makeAndSetHolder(WWConfiguredFeatures.BIG_BUSHES,
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.SHRUB_CLEARING_FILTER
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.TREES_FLOWER_FIELD,
			PlacementUtils.countExtra(0, 0.25F, 1),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_AND_OAK,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_DYING_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_DYING_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(5, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_SNOWY_DYING_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_DYING_MIXED_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_SNOWY_DYING_MIXED_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_AND_OAK_ORIGINAL,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(11, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(WWConfiguredFeatures.TREES_SEMI_BIRCH_AND_OAK,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SPARSE_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_AND_OAK_CALM,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 2))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_FLOWER_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(16))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(UniformInt.of(30, 32)))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_BIRCH_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(14))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.DARK_TAIGA_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(14))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_BIRCH.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_BIRCH_TALL.makeAndSetHolder(WWConfiguredFeatures.TREES_BIRCH_TALL,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.TREES_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SPRUCE_PLACED_NO_LITTER.makeAndSetHolder(WWConfiguredFeatures.TREES_TAIGA_NO_LITTER,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SHORT_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_TREES_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(5, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SHORT_MEGA_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_MEGA_SPRUCE,
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SHORT_MEGA_SPRUCE_ON_SNOW,
			treePlacement(RarityFilter.onAverageOnceEvery(9))
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA1.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREES_SNOWY.makeAndSetHolder(WWTreeConfigured.SPRUCE_SHORT.getHolder(),
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);

		TREES_GROVE.makeAndSetHolder(WWConfiguredFeatures.TREES_GROVE,
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(WWConfiguredFeatures.TREES_WINDSWEPT_HILLS,
			treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);

		TREES_WINDSWEPT_FOREST.makeAndSetHolder(WWConfiguredFeatures.TREES_WINDSWEPT_HILLS,
			treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);

		TREES_MEADOW.makeAndSetHolder(WWConfiguredFeatures.MEADOW_TREES,
			treePlacement(RarityFilter.onAverageOnceEvery(100))
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.WINDSWEPT_SAVANNA_TREES,
			treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES,
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		SAVANNA_TREES_BAOBAB.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES_BAOBAB,
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		SAVANNA_TREES_BAOBAB_VANILLA.makeAndSetHolder(WWConfiguredFeatures.SAVANNA_TREES_BAOBAB_VANILLA,
			treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(WWConfiguredFeatures.ARID_SAVANNA_TREES,
			treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		ARID_SAVANNA_TREES_PALM.makeAndSetHolder(WWConfiguredFeatures.ARID_SAVANNA_TREES_PALM,
			treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(WWConfiguredFeatures.WOODED_BADLANDS_TREES,
			treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))
		);

		TREES_SWAMP.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO))
		);

		TREES_SWAMP_SURFACE_WILLOW.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_SURFACE_WILLOW,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(1),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		TREES_SWAMP_WATER_SHALLOW.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_WATER_SHALLOW,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		TREES_SWAMP_WATER.makeAndSetHolder(WWConfiguredFeatures.SWAMP_TREES_WATER,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(4),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesFluids(Direction.UP.getUnitVec3i(), Fluids.WATER),
					BlockPredicate.matchesFluids(Fluids.WATER)
				)
			),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING)
		);

		MIXED_TREES.makeAndSetHolder(WWConfiguredFeatures.MIXED_TREES,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(14, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.TEMPERATE_RAINFOREST_TREES,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(13, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		RAINFOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.RAINFOREST_TREES,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(WWConfiguredFeatures.BIRCH_TAIGA_TREES,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(WWConfiguredFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.PARCHED_FOREST_TREES,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		ARID_FOREST_TREES.makeAndSetHolder(WWConfiguredFeatures.ARID_FOREST_TREES,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(WWConfiguredFeatures.BIRCH_JUNGLE_TREES,
			treePlacement(CountPlacement.of(29))
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(WWConfiguredFeatures.SPARSE_BIRCH_JUNGLE_TREES,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(WWConfiguredFeatures.CYPRESS_WETLANDS_TREES,
			CountPlacement.of(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(WWConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER,
			CountPlacement.of(UniformInt.of(5, 10)),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		BIG_COARSE_BUSHES.makeAndSetHolder(WWConfiguredFeatures.LARGE_BUSHES_ON_SAND,
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		PALM.makeAndSetHolder(WWConfiguredFeatures.PALMS,
			treePlacement(RarityFilter.onAverageOnceEvery(4))
		);

		PALM_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.PALMS_JUNGLE,
			treePlacement(PlacementUtils.countExtra(8, 0.5F, 2))
		);

		PALMS_OASIS.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS,
			treePlacement(RarityFilter.onAverageOnceEvery(3))
		);

		PALM_RARE.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		PALMS_WARM_BEACH.makeAndSetHolder(WWConfiguredFeatures.PALMS_OASIS,
			treePlacement(RarityFilter.onAverageOnceEvery(6))
		);

		BAMBOO_VEGETATION.makeAndSetHolder(WWConfiguredFeatures.BAMBOO_JUNGLE_TREES,
			treePlacement(PlacementUtils.countExtra(30, 0.1F, 1))
		);

		TREES_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.SPARSE_JUNGLE_TREES,
			treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		TREES_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.JUNGLE_TREES,
			treePlacement(PlacementUtils.countExtra(50, 0.1F, 1))
		);

		TREES_MANGROVE.makeAndSetHolder(WWConfiguredFeatures.MANGROVE_VEGETATION,
			CountPlacement.of(25),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE),
			BiomeFilter.biome()
		);

		CHERRY_TREES.makeAndSetHolder(WWConfiguredFeatures.CHERRIES,
			treePlacement(PlacementUtils.countExtra(10, 0.1F, 3))
		);

		MAPLE_TREES.makeAndSetHolder(WWConfiguredFeatures.MAPLES,
			PlacementUtils.countExtra(6, 0.1F, 2),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.YELLOW_MAPLE_SAPLING),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		TREES_PALE_GARDEN.makeAndSetHolder(WWConfiguredFeatures.TREES_PALE_GARDEN,
			PlacementUtils.countExtra(18, 0.1F, 2),
			InSquarePlacement.spread(),
			TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.PALE_OAK_SAPLING),
			BiomeFilter.biome()
		);

		SNAPPED_BIRCH_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCHES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCHES,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_OAKS,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_OAKS,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_SPRUCES_ON_SNOW,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_LARGE_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_COMMON_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES,
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(4))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_SPRUCES_ON_SNOW,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_BIRCH_AND_SPRUCE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_SPRUCE,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_CYPRESS_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_CYPRESSES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_JUNGLES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_LARGE_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_LARGE_JUNGLES,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_BIRCH_AND_JUNGLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_BIRCH_AND_JUNGLE,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_ACACIAS,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_ACACIA_AND_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_ACACIA_AND_OAK,
			treePlacement(RarityFilter.onAverageOnceEvery(52))
		);

		SNAPPED_CHERRY_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_CHERRY,
			treePlacement(RarityFilter.onAverageOnceEvery(58))
		);

		SNAPPED_DARK_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_DARK_OAKS,
			treePlacement(RarityFilter.onAverageOnceEvery(48))
		);

		SNAPPED_DARK_OAK_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_DARK_OAKS,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_MAPLE_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_MAPLE,
			treePlacement(RarityFilter.onAverageOnceEvery(32))
		);

		SNAPPED_MAPLE_CLEARING_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_MAPLE,
			VegetationPlacements.treePlacementBase(RarityFilter.onAverageOnceEvery(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED).build()
		);

		SNAPPED_PALE_OAK_PLACED.makeAndSetHolder(WWConfiguredFeatures.SNAPPED_PALE_OAKS,
			treePlacement(RarityFilter.onAverageOnceEvery(5))
		);

		// MUSHROOMS
		BROWN_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.BROWN_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		CRIMSON_SHELF_FUNGI.makeAndSetHolder(WWConfiguredFeatures.CRIMSON_SHELF_FUNGI,
			CountPlacement.of(230),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		WARPED_SHELF_FUNGI.makeAndSetHolder(WWConfiguredFeatures.WARPED_SHELF_FUNGI,
			CountPlacement.of(230),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		CRIMSON_SHELF_FUNGI_RARE.makeAndSetHolder(WWConfiguredFeatures.CRIMSON_SHELF_FUNGI,
			CountPlacement.of(40),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		WARPED_SHELF_FUNGI_RARE.makeAndSetHolder(WWConfiguredFeatures.WARPED_SHELF_FUNGI,
			CountPlacement.of(40),
			InSquarePlacement.spread(),
			HeightRangePlacement.triangle(VerticalAnchor.BOTTOM, VerticalAnchor.TOP),
			BiomeFilter.biome()
		);

		RED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.RED_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PALE_MUSHROOM.makeAndSetHolder(WWConfiguredFeatures.PALE_MUSHROOM,
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HUGE_PALE_MUSHROOM.makeAndSetHolder(WWConfiguredFeatures.HUGE_PALE_MUSHROOMS,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(WWConfiguredFeatures.MUSHROOMS_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(4),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(50),
			RandomOffsetPlacement.ofTriangle(4, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HUGE_RED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_BROWN_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM),
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

		HUGE_MUSHROOMS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_MUSHROOMS_RARE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(20),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MIXED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(75),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RAINFOREST_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// GRASS AND FERNS
		PATCH_GRASS_FROZEN_PLAIN.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 1, 3),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_FOREST.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_NORMAL.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_TAIGA_2.makeAndSetHolder(WWConfiguredFeatures.TAIGA_FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TAIGA_FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_BONEMEAL.makeAndSetHolder(WWConfiguredFeatures.SINGLE_PIECE_OF_FROZEN_GRASS,
			PlacementUtils.isEmpty()
		);

		PATCH_FROZEN_TALL_GRASS_2.makeAndSetHolder(WWConfiguredFeatures.FROZEN_TALL_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 0, 3),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_TALL_GRASS.makeAndSetHolder(WWConfiguredFeatures.FROZEN_TALL_GRASS,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_LARGE_FERN.makeAndSetHolder(WWConfiguredFeatures.FROZEN_LARGE_FERN,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_BUSH.makeAndSetHolder(WWConfiguredFeatures.FROZEN_BUSH,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(5, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_OASIS.makeAndSetHolder(WWConfiguredFeatures.GRASS_OASIS,
			CountPlacement.of(19),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(35),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SHRUB_OASIS.makeAndSetHolder(WWConfiguredFeatures.SHRUB_OASIS,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(23),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					// TODO: Maybe remove sand again
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SUPPORTS_SHRUB)
				)
			)
		);

		PATCH_SHRUB_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_JUNGLE,
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(23),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_SPARSE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_SPARSE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.SHRUB_FLOWER_FIELD,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB.makeAndSetHolder(WWConfiguredFeatures.SHRUB_GENERIC,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_DESERT.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SHRUB_BADLANDS_SAND.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(8),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_SHRUB_BADLANDS_SAND_RARE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(8),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_SHRUB_BADLANDS_TERRACOTTA.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(6),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.BADLANDS_TERRACOTTA)
				)
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.BADLANDS_TERRACOTTA)
				)
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_DIRT.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(15),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.DIRT)
				)
			)
		);

		PATCH_BUSH_ARID.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_CACTUS_OASIS.makeAndSetHolder(WWConfiguredFeatures.CACTUS_OASIS,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_CACTUS_TALL.makeAndSetHolder(WWConfiguredFeatures.CACTUS_TALL,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(8),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_CACTUS_TALL_BADLANDS.makeAndSetHolder(WWConfiguredFeatures.CACTUS_TALL_BADLANDS,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_ARID_CACTUS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.CACTUS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_MYCELIUM_GROWTH.makeAndSetHolder(WWConfiguredFeatures.MYCELIUM_GROWTH,
			CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_AND_GRASS.makeAndSetHolder(WWConfiguredFeatures.FERN_AND_GRASS,
			CountPlacement.of(20),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_AND_GRASS_LIGHT.makeAndSetHolder(WWConfiguredFeatures.FERN_AND_GRASS,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_BIRCH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS),
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_SWAMP.makeAndSetHolder(WWConfiguredFeatures.FERN_SWAMP,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_PLAINS.makeAndSetHolder(WWConfiguredFeatures.GRASS_AND_FERN,
			CountPlacement.of(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_RARE_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.not(
						BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.PODZOL)
					)
				)
			)
		);

		TALL_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_GRASS_LIGHT.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_GRASS_PLAINS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_SWAMP.makeAndSetHolder(WWConfiguredFeatures.TALL_GRASS_SWAMP,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_DENSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_DENSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LARGE_FERN),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		SEAGRASS_CYPRESS.makeAndSetHolder(configuredFeatures.getOrThrow(AquaticFeatures.SEAGRASS_MID),
			seagrassPlacement(56)
		);

		PATCH_LARGE_FERN_AND_GRASS.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_LARGE_FERN_AND_GRASS_RARE.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(WWConfiguredFeatures.TALL_GRASS_AND_GRASS_WATER,
			RarityFilter.onAverageOnceEvery(2),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(16),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FLOWER_FIELD.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
			CountPlacement.of(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.not(
						BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.PODZOL)
					)
				)
			)
		);

		PATCH_TALL_GRASS_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS_2,
			NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		// FLOWERS
		PATCH_CLOVER.makeAndSetHolder(WWConfiguredFeatures.CLOVER,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_CLOVER_SPARSE.makeAndSetHolder(WWConfiguredFeatures.CLOVER,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PHLOX.makeAndSetHolder(WWConfiguredFeatures.PHLOX,
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.PHLOX,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_LANTANAS.makeAndSetHolder(WWConfiguredFeatures.LANTANAS,
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_LANTANAS_SPARSE.makeAndSetHolder(WWConfiguredFeatures.LANTANAS,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_WILDFLOWERS.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS,
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_WILDFLOWERS_SPARSE.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS,
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_WILDFLOWERS_AND_PHLOX.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_PHLOX,
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_WILDFLOWERS_AND_PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_PHLOX,
			CountPlacement.of(UniformInt.of(0, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_WILDFLOWERS_AND_LANTANAS.makeAndSetHolder(WWConfiguredFeatures.WILDFLOWERS_AND_LANTANAS,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_LANTANAS_AND_PHLOX.makeAndSetHolder(WWConfiguredFeatures.LANTANAS_AND_PHLOX,
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_LANTANAS_AND_PHLOX_SPARSE.makeAndSetHolder(WWConfiguredFeatures.LANTANAS_AND_PHLOX,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_COMMON_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_RARE_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(17),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_VERY_RARE_SEEDING_DANDELION.makeAndSetHolder(WWConfiguredFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		CARNATION.makeAndSetHolder(WWConfiguredFeatures.CARNATION,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MARIGOLD.makeAndSetHolder(WWConfiguredFeatures.MARIGOLD,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(40),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MARIGOLD_SPARSE.makeAndSetHolder(WWConfiguredFeatures.MARIGOLD,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		EYEBLOSSOM.makeAndSetHolder(WWConfiguredFeatures.EYEBLOSSOM,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PINK_TULIP_UNCOMMON.makeAndSetHolder(WWConfiguredFeatures.PINK_TULIP,
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		ALLIUM_UNCOMMON.makeAndSetHolder(WWConfiguredFeatures.ALLIUM,
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		DATURA.makeAndSetHolder(WWConfiguredFeatures.DATURA,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		COMMON_DATURA.makeAndSetHolder(WWConfiguredFeatures.DATURA,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		ROSE_BUSH.makeAndSetHolder(WWConfiguredFeatures.ROSE_BUSH,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(40),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		ROSE_BUSH_SPARSE.makeAndSetHolder(WWConfiguredFeatures.ROSE_BUSH,
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PEONY.makeAndSetHolder(WWConfiguredFeatures.PEONY,
			RarityFilter.onAverageOnceEvery(12),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PEONY_SPARSE.makeAndSetHolder(WWConfiguredFeatures.PEONY,
			RarityFilter.onAverageOnceEvery(18),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		LILAC.makeAndSetHolder(WWConfiguredFeatures.LILAC,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(40),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_GENERIC.makeAndSetHolder(WWConfiguredFeatures.FLOWER_GENERIC,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_GENERIC_NO_CARNATION.makeAndSetHolder(WWConfiguredFeatures.FLOWER_GENERIC_NO_CARNATION,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_PLAINS,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_SNOWY_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_SNOWY_PLAINS,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_TUNDRA.makeAndSetHolder(WWConfiguredFeatures.FLOWER_TUNDRA,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(42),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_BIRCH.makeAndSetHolder(WWConfiguredFeatures.FLOWER_BIRCH,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(48),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_MEADOW.makeAndSetHolder(WWConfiguredFeatures.FLOWER_MEADOW,
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(8, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		DENSE_FLOWER_PLACED.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FLOWER_DEFAULT),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		CYPRESS_WETLANDS_FLOWERS_SPARSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FOREST_FLOWERS),
			RarityFilter.onAverageOnceEvery(7),
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		CYPRESS_WETLANDS_FLOWERS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_CYPRESS_WETLANDS,
			CountPlacement.of(UniformInt.of(1, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		CYPRESS_WETLANDS_FLOWERS_TALL.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_CYPRESS_WETLANDS,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MILKWEED.makeAndSetHolder(WWConfiguredFeatures.MILKWEED,
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MILKWEED_RARE.makeAndSetHolder(WWConfiguredFeatures.MILKWEED,
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MILKWEED_SWAMP.makeAndSetHolder(WWConfiguredFeatures.MILKWEED_SWAMP,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HIBISCUS.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HIBISCUS_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS_JUNGLE,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(52),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HIBISCUS_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.HIBISCUS_JUNGLE,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(52),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_FLOWER_FOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FLOWER_FIELD,
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FLOWER_FIELD,
			CountPlacement.of(3),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWER_TEMPERATE_RAINFOREST,
			CountPlacement.of(2),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST,
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA,
			CountPlacement.of(2),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA,
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.FLOWER_RAINFOREST,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_FLOWER_RAINFOREST.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_RAINFOREST,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.FLOWER_RAINFOREST_VANILLA,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_RAINFOREST_VANILLA,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.FLOWER_JUNGLE,
			CountPlacement.of(5),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_FLOWER_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_JUNGLE,
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(8),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_CHERRY.makeAndSetHolder(WWConfiguredFeatures.FLOWER_CHERRY,
			CountPlacement.of(UniformInt.of(3, 7)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_SUNFLOWER_PLAINS.makeAndSetHolder(WWConfiguredFeatures.FLOWER_SUNFLOWER_PLAINS,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(38),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_FOREST_CLEARING.makeAndSetHolder(WWConfiguredFeatures.FLOWER_FOREST_CLEARING,
			CountPlacement.of(UniformInt.of(0, 1)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		FLOWER_SPARSE_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.FLOWER_JUNGLE,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			RandomOffsetPlacement.ofTriangle(8, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MOSS_CARPET.makeAndSetHolder(WWConfiguredFeatures.MOSS_CARPET,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(25),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		TALL_FLOWER_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.TALL_FLOWER_FLOWER_FIELD,
			RarityFilter.onAverageOnceEvery(3),
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 4), 0, 4)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// VEGETATION
		POLLEN.makeAndSetHolder(WWConfiguredFeatures.POLLEN,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_BERRY_FOREST.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.BERRY_BUSH),
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)
				)
			)
		);

		TERMITE_MOUND.makeAndSetHolder(WWConfiguredFeatures.TERMITE_MOUND,
			RarityFilter.onAverageOnceEvery(45),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
			BiomeFilter.biome()
		);

		PATCH_TUMBLEWEED.makeAndSetHolder(WWConfiguredFeatures.TUMBLEWEED,
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PRICKLY_PEAR.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR,
			RarityFilter.onAverageOnceEvery(7),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(15),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PRICKLY_PEAR_RARE.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR,
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(15),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_MELON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MELON),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_PUMPKIN_COMMON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PUMPKIN),
			RarityFilter.onAverageOnceEvery(12),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_DRY_GRASS_BADLANDS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_DRY_GRASS_DESERT.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_DRY_GRASS_BEACH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.SAND)
				)
			)
		);

		BlockPredicate sandNearby = BlockPredicate.allOf(
			BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.SAND),
			BlockPredicate.matchesBlocks(new Vec3i(-1, -1, 0), Blocks.SAND),
			BlockPredicate.matchesBlocks(new Vec3i(0, -1, -1), Blocks.SAND),
			BlockPredicate.matchesBlocks(new Vec3i(1, -1, 0), Blocks.SAND),
			BlockPredicate.matchesBlocks(new Vec3i(0, -1, 1), Blocks.SAND)
		);

		PATCH_DRY_GRASS_BETA_BEACH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BlockPredicateFilter.forPredicate(sandNearby),
			BiomeFilter.biome(),
			CountPlacement.of(28),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.SAND)
				)
			)
		);

		PATCH_FIREFLY_BUSH_NEAR_WATER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FIREFLY_BUSH_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(20),
			RandomOffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		BlockPredicateFilter leafLitterPredicate = BlockPredicateFilter.forPredicate(
			BlockPredicate.not(
				BlockPredicate.anyOf(
					BlockPredicate.matchesTag(
						Direction.DOWN.getUnitVec3i(),
						BlockTags.SAND
					),
					BlockPredicate.matchesTag(
						Direction.DOWN.getUnitVec3i(),
						BlockTags.TERRACOTTA
					),
					BlockPredicate.matchesBlocks(
						Direction.DOWN.getUnitVec3i(),
						Blocks.GRAVEL,
						Blocks.SUSPICIOUS_GRAVEL,
						Blocks.CLAY
					),
					BlockPredicate.not(
						BlockPredicate.noFluid(Direction.DOWN.getUnitVec3i())
					)
				)
			)
		);

		PATCH_LEAF_LITTER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPredicate,
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_UNCOMMON_LEAF_LITTER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPredicate,
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN.getUnitVec3i(), Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_DARK_OAK_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.DARK_OAK_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPredicate,
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PALE_OAK_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.PALE_OAK_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPredicate,
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SPRUCE_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.SPRUCE_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPredicate,
			CountPlacement.of(32),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
	}

}
