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

package net.frozenblock.wilderwild.worldgen.features.configured;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ComboFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils;
import net.frozenblock.wilderwild.worldgen.features.placed.WWTreePlaced;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.ShelfFungiFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBedBlock;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WWConfiguredFeatures {
	// FALLEN TREES
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_TREES_MIXED = WWFeatureUtils.register("fallen_trees_mixed");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MOSSY_FALLEN_TREES_MIXED = WWFeatureUtils.register("mossy_fallen_trees_mixed");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MOSSY_FALLEN_TREES_OAK_AND_BIRCH = WWFeatureUtils.register("mossy_fallen_trees_oak_and_birch");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_BIRCH_AND_SPRUCE = WWFeatureUtils.register("fallen_birch_and_spruce");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_BIRCH = WWFeatureUtils.register("fallen_birch");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_CHERRY = WWFeatureUtils.register("fallen_cherry");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_SPRUCE = WWFeatureUtils.register("fallen_spruce");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> CLEAN_FALLEN_SPRUCE = WWFeatureUtils.register("clean_fallen_spruce");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_SWAMP_TREES = WWFeatureUtils.register("fallen_swamp_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_SWAMP_TREES_WILLOW = WWFeatureUtils.register("fallen_swamp_trees_willow");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> DECORATED_LARGE_FALLEN_SPRUCE = WWFeatureUtils.register("decorated_large_fallen_spruce");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> CLEAN_LARGE_FALLEN_SPRUCE = WWFeatureUtils.register("clean_large_fallen_spruce");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_SPRUCE_AND_OAK = WWFeatureUtils.register("fallen_spruce_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_BIRCH_AND_OAK = WWFeatureUtils.register("fallen_birch_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_CYPRESS_AND_OAK = WWFeatureUtils.register("fallen_cypress_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_ACACIA_AND_OAK = WWFeatureUtils.register("fallen_acacia_and_oak");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_LARGE_JUNGLE = WWFeatureUtils.register("fallen_large_jungle");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_PALM_AND_JUNGLE_AND_OAK = WWFeatureUtils.register("fallen_palm_and_jungle_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_JUNGLE_AND_OAK = WWFeatureUtils.register("fallen_jungle_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> FALLEN_OAK_AND_BIRCH_DARK_FOREST = WWFeatureUtils.register("fallen_oak_and_birch_dark_forest");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_MANGROVE = WWFeatureUtils.register("fallen_mangrove");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_DARK_OAKS = WWFeatureUtils.register("fallen_dark_oaks");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_MAPLE = WWFeatureUtils.register("fallen_maple");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> FALLEN_PALE_OAKS = WWFeatureUtils.register("fallen_pale_oaks");

	// TREES
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_PLAINS = WWFeatureUtils.register("trees_plains");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_FLOWER_FIELD = WWFeatureUtils.register("trees_flower_field");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER = WWFeatureUtils.register("trees_birch_and_oak_original_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER = WWFeatureUtils.register("trees_birch_and_oak_original_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_ORIGINAL = WWFeatureUtils.register("trees_birch_and_oak_original");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_NO_LITTER = WWFeatureUtils.register("trees_birch_and_oak_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_LEAF_LITTER = WWFeatureUtils.register("trees_birch_and_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK = WWFeatureUtils.register("trees_birch_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_AND_OAK_CALM = WWFeatureUtils.register("trees_birch_and_oak_calm");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_DYING_FOREST = WWFeatureUtils.register("trees_dying_forest");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_SNOWY_DYING_FOREST = WWFeatureUtils.register("trees_snowy_dying_forest");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_DYING_MIXED_FOREST_NO_LITTER = WWFeatureUtils.register("trees_dying_mixed_forest_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_DYING_MIXED_FOREST_LEAF_LITTER = WWFeatureUtils.register("trees_dying_mixed_forest_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_DYING_MIXED_FOREST = WWFeatureUtils.register("trees_dying_mixed_forest");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_SNOWY_DYING_MIXED_FOREST = WWFeatureUtils.register("trees_snowy_dying_mixed_forest");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_SEMI_BIRCH_AND_OAK_NO_LITTER = WWFeatureUtils.register("trees_semi_birch_and_oak_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER = WWFeatureUtils.register("trees_semi_birch_and_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_SEMI_BIRCH_AND_OAK = WWFeatureUtils.register("trees_semi_birch_and_oak");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH = WWFeatureUtils.register("trees_birch");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_TALL_NO_LITTER = WWFeatureUtils.register("trees_birch_tall_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_TALL_LEAF_LITTER = WWFeatureUtils.register("trees_birch_tall_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_BIRCH_TALL = WWFeatureUtils.register("trees_birch_tall");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_FLOWER_FOREST = WWFeatureUtils.register("trees_flower_forest");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MIXED_TREES_NO_LITTER = WWFeatureUtils.register("mixed_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MIXED_TREES_LEAF_LITTER = WWFeatureUtils.register("mixed_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MIXED_TREES = WWFeatureUtils.register("mixed_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TEMPERATE_RAINFOREST_TREES_NO_LITTER = WWFeatureUtils.register("temperate_rainforest_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TEMPERATE_RAINFOREST_TREES_LEAF_LITTER = WWFeatureUtils.register("temperate_rainforest_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TEMPERATE_RAINFOREST_TREES = WWFeatureUtils.register("temperate_rainforest_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> RAINFOREST_TREES_NO_LITTER = WWFeatureUtils.register("rainforest_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> RAINFOREST_TREES_LEAF_LITTER = WWFeatureUtils.register("rainforest_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> RAINFOREST_TREES = WWFeatureUtils.register("rainforest_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_TAIGA_TREES_NO_LITTER = WWFeatureUtils.register("birch_taiga_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_TAIGA_TREES_LEAF_LITTER = WWFeatureUtils.register("birch_taiga_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_TAIGA_TREES = WWFeatureUtils.register("birch_taiga_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER = WWFeatureUtils.register("old_growth_birch_taiga_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER = WWFeatureUtils.register("old_growth_birch_taiga_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_BIRCH_TAIGA_TREES = WWFeatureUtils.register("old_growth_birch_taiga_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_JUNGLE_TREES_NO_LITTER = WWFeatureUtils.register("birch_jungle_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_JUNGLE_TREES_LEAF_LITTER = WWFeatureUtils.register("birch_jungle_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIRCH_JUNGLE_TREES = WWFeatureUtils.register("birch_jungle_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER = WWFeatureUtils.register("sparse_birch_jungle_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER = WWFeatureUtils.register("sparse_birch_jungle_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_BIRCH_JUNGLE_TREES = WWFeatureUtils.register("sparse_birch_jungle_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_FOREST_VEGETATION_NO_LITTER = WWFeatureUtils.register("dark_forest_vegetation_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_FOREST_VEGETATION_LEAF_LITTER = WWFeatureUtils.register("dark_forest_vegetation_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_FOREST_VEGETATION = WWFeatureUtils.register("dark_forest_vegetation");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER = WWFeatureUtils.register("old_growth_dark_forest_vegetation_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER = WWFeatureUtils.register("old_growth_dark_forest_vegetation_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> OLD_GROWTH_DARK_FOREST_VEGETATION = WWFeatureUtils.register("old_growth_dark_forest_vegetation");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_BIRCH_FOREST_VEGETATION_NO_LITTER = WWFeatureUtils.register("dark_birch_forest_vegetation_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER = WWFeatureUtils.register("dark_birch_forest_vegetation_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_BIRCH_FOREST_VEGETATION = WWFeatureUtils.register("dark_birch_forest_vegetation");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_TAIGA_VEGETATION_NO_LITTER = WWFeatureUtils.register("dark_taiga_vegetation_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_TAIGA_VEGETATION_LEAF_LITTER = WWFeatureUtils.register("dark_taiga_vegetation_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> DARK_TAIGA_VEGETATION = WWFeatureUtils.register("dark_taiga_vegetation");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_TAIGA_NO_LITTER = WWFeatureUtils.register("trees_taiga_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_TAIGA_LEAF_LITTER = WWFeatureUtils.register("trees_taiga_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_TAIGA = WWFeatureUtils.register("trees_taiga");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SHORT_TREES_TAIGA = WWFeatureUtils.register("short_trees_taiga");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SHORT_MEGA_SPRUCE = WWFeatureUtils.register("short_mega_spruce_configured");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SHORT_MEGA_SPRUCE_ON_SNOW = WWFeatureUtils.register("short_mega_spruce_on_snow_configured");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER = WWFeatureUtils.register("trees_old_growth_pine_taiga_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER = WWFeatureUtils.register("trees_old_growth_pine_taiga_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_PINE_TAIGA = WWFeatureUtils.register("trees_old_growth_pine_taiga");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER = WWFeatureUtils.register("trees_old_growth_spruce_taiga_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER = WWFeatureUtils.register("trees_old_growth_spruce_taiga_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_SPRUCE_TAIGA = WWFeatureUtils.register("trees_old_growth_spruce_taiga");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = WWFeatureUtils.register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_GROVE = WWFeatureUtils.register("trees_grove");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_WINDSWEPT_HILLS_NO_LITTER = WWFeatureUtils.register("trees_windswept_hills_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_WINDSWEPT_HILLS_LEAF_LITTER = WWFeatureUtils.register("trees_windswept_hills_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_WINDSWEPT_HILLS = WWFeatureUtils.register("trees_windswept_hills");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MEADOW_TREES = WWFeatureUtils.register("meadow_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_NO_LITTER = WWFeatureUtils.register("savanna_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_LEAF_LITTER = WWFeatureUtils.register("savanna_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES = WWFeatureUtils.register("savanna_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_BAOBAB_NO_LITTER = WWFeatureUtils.register("savanna_trees_baobab_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_BAOBAB_LEAF_LITTER = WWFeatureUtils.register("savanna_trees_baobab_leaf_liter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_BAOBAB = WWFeatureUtils.register("savanna_trees_baobab");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SAVANNA_TREES_BAOBAB_VANILLA = WWFeatureUtils.register("savanna_trees_baobab_vanilla");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WINDSWEPT_SAVANNA_TREES_NO_LITTER = WWFeatureUtils.register("windswept_savanna_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WINDSWEPT_SAVANNA_TREES_LEAF_LITTER = WWFeatureUtils.register("windswept_savanna_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WINDSWEPT_SAVANNA_TREES = WWFeatureUtils.register("windswept_savanna_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES_NO_LITTER = WWFeatureUtils.register("arid_savanna_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES_LEAF_LITTER = WWFeatureUtils.register("arid_savanna_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES = WWFeatureUtils.register("arid_savanna_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES_PALM_NO_LITTER = WWFeatureUtils.register("arid_savanna_trees_palm_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES_PALM_LEAF_LITTER = WWFeatureUtils.register("arid_savanna_trees_palm_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_SAVANNA_TREES_PALM = WWFeatureUtils.register("arid_savanna_trees_palm");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PARCHED_FOREST_TREES_NO_LITTER = WWFeatureUtils.register("parched_forest_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PARCHED_FOREST_TREES_LEAF_LITTER = WWFeatureUtils.register("parched_forest_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PARCHED_FOREST_TREES = WWFeatureUtils.register("parched_forest_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_FOREST_TREES_NO_LITTER = WWFeatureUtils.register("arid_forest_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_FOREST_TREES_LEAF_LITTER = WWFeatureUtils.register("arid_forest_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ARID_FOREST_TREES = WWFeatureUtils.register("arid_forest_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> CYPRESS_WETLANDS_TREES = WWFeatureUtils.register("cypress_wetlands_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> CYPRESS_WETLANDS_TREES_SAPLING = WWFeatureUtils.register("cypress_wetlands_trees_sapling");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> CYPRESS_WETLANDS_TREES_WATER = WWFeatureUtils.register("cypress_wetlands_trees_water");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WOODED_BADLANDS_TREES_NO_LITTER = WWFeatureUtils.register("wooded_badlands_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WOODED_BADLANDS_TREES_LEAF_LITTER = WWFeatureUtils.register("wooded_badlands_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WOODED_BADLANDS_TREES = WWFeatureUtils.register("wooded_badlands_trees");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SWAMP_TREES_NO_LITTER = WWFeatureUtils.register("swamp_trees_no_litter");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SWAMP_TREES_LEAF_LITTER = WWFeatureUtils.register("swamp_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SWAMP_TREES = WWFeatureUtils.register("swamp_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SWAMP_TREES_SURFACE_WILLOW_NO_LITTER = WWFeatureUtils.register("swamp_trees_surface_willow_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER = WWFeatureUtils.register("swamp_trees_surface_willow_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SWAMP_TREES_SURFACE_WILLOW = WWFeatureUtils.register("swamp_trees_surface_willow");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SWAMP_TREES_WATER_SHALLOW = WWFeatureUtils.register("swamp_trees_water_shallow");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SWAMP_TREES_WATER = WWFeatureUtils.register("swamp_trees_water");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> LARGE_BUSHES_ON_SAND = WWFeatureUtils.register("large_bushes_on_sand");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BIG_BUSHES = WWFeatureUtils.register("big_bushes");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALMS = WWFeatureUtils.register("palms");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALMS_JUNGLE_NO_LITTER = WWFeatureUtils.register("palms_jungle_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALMS_JUNGLE_LEAF_LITTER = WWFeatureUtils.register("palms_jungle_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALMS_JUNGLE = WWFeatureUtils.register("palms_jungle");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALMS_OASIS = WWFeatureUtils.register("palms_oasis");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BAMBOO_JUNGLE_TREES_NO_LITTER = WWFeatureUtils.register("bamboo_jungle_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BAMBOO_JUNGLE_TREES_LEAF_LITTER = WWFeatureUtils.register("bamboo_jungle_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BAMBOO_JUNGLE_TREES = WWFeatureUtils.register("bamboo_jungle_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> JUNGLE_TREES_NO_LITTER = WWFeatureUtils.register("jungle_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> JUNGLE_TREES_LEAF_LITTER = WWFeatureUtils.register("jungle_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> JUNGLE_TREES = WWFeatureUtils.register("jungle_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_JUNGLE_TREES_NO_LITTER = WWFeatureUtils.register("sparse_jungle_trees_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_JUNGLE_TREES_LEAF_LITTER = WWFeatureUtils.register("sparse_jungle_trees_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SPARSE_JUNGLE_TREES = WWFeatureUtils.register("sparse_jungle_trees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MANGROVE_VEGETATION_NO_LITTER = WWFeatureUtils.register("mangrove_vegetation_no_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MANGROVE_VEGETATION_LEAF_LITTER = WWFeatureUtils.register("mangrove_vegetation_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MANGROVE_VEGETATION = WWFeatureUtils.register("mangrove_vegetation");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> CHERRIES = WWFeatureUtils.register("cherries");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig> YELLOW_MAPLES = WWFeatureUtils.register("yellow_maples");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig> ORANGE_MAPLES = WWFeatureUtils.register("orange_maples");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig> RED_MAPLES = WWFeatureUtils.register("red_maples");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MAPLES = WWFeatureUtils.register("maples");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> YELLOW_MAPLES_NO_BEES = WWFeatureUtils.register("yellow_maples_no_bees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ORANGE_MAPLES_NO_BEES = WWFeatureUtils.register("orange_maples_no_bees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> RED_MAPLES_NO_BEES = WWFeatureUtils.register("red_maples_no_bees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> YELLOW_MAPLES_BEES_SAPLING = WWFeatureUtils.register("yellow_maples_bees_sapling");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ORANGE_MAPLES_BEES_SAPLING = WWFeatureUtils.register("orange_maples_bees_sapling");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> RED_MAPLES_BEES_SAPLING = WWFeatureUtils.register("red_maples_bees_sapling");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MAPLES_BEES_SAPLING = WWFeatureUtils.register("maples_bees_sapling");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> MAPLES_NO_BEES = WWFeatureUtils.register("maples_no_bees");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALE_OAKS = WWFeatureUtils.register("pale_oaks");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> PALE_OAKS_CREAKING = WWFeatureUtils.register("pale_oaks_creaking");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> TREES_PALE_GARDEN = WWFeatureUtils.register("trees_pale_garden");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_BIRCHES = WWFeatureUtils.register("snapped_birches");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_OAKS = WWFeatureUtils.register("snapped_oaks");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SNAPPED_BIRCH_AND_OAK = WWFeatureUtils.register("snapped_birch_and_oak");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_SPRUCES = WWFeatureUtils.register("snapped_spruces");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_LARGE_SPRUCES = WWFeatureUtils.register("snapped_large_spruces");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_SPRUCES_ON_SNOW = WWFeatureUtils.register("snapped_spruces_on_snow");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_LARGE_SPRUCES_ON_SNOW = WWFeatureUtils.register("snapped_large_spruces_on_snow");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = WWFeatureUtils.register("snapped_birch_and_oak_and_spruce");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SNAPPED_BIRCH_AND_SPRUCE = WWFeatureUtils.register("snapped_birch_and_spruce");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_CYPRESSES = WWFeatureUtils.register("snapped_cypresses");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_JUNGLES = WWFeatureUtils.register("snapped_jungles");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_LARGE_JUNGLES = WWFeatureUtils.register("snapped_large_jungles");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SNAPPED_BIRCH_AND_JUNGLE = WWFeatureUtils.register("snapped_birch_and_jungle");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_ACACIAS = WWFeatureUtils.register("snapped_acacias");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> SNAPPED_ACACIA_AND_OAK = WWFeatureUtils.register("snapped_acacia_and_oak");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_CHERRY = WWFeatureUtils.register("snapped_cherry");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_DARK_OAKS = WWFeatureUtils.register("snapped_dark_oaks");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_MAPLE = WWFeatureUtils.register("snapped_maple");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> SNAPPED_PALE_OAKS = WWFeatureUtils.register("snapped_pale_oaks");

	// LEAF LITTERS
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> DARK_OAK_LEAF_LITTER_SINGLE = WWFeatureUtils.register("dark_oak_leaf_litter_single");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PALE_OAK_LEAF_LITTER_SINGLE = WWFeatureUtils.register("pale_oak_leaf_litter_single");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SPRUCE_LEAF_LITTER_SINGLE = WWFeatureUtils.register("spruce_leaf_litter_single");

	// FLOWERS
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> CLOVER = WWFeatureUtils.register("clover");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PHLOX = WWFeatureUtils.register("phlox");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> LANTANAS = WWFeatureUtils.register("lantanas");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> WILDFLOWERS = WWFeatureUtils.register("wildflowers");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WILDFLOWERS_AND_PHLOX = WWFeatureUtils.register("wildflowers_and_phlox");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WILDFLOWERS_AND_LANTANAS = WWFeatureUtils.register("wildflowers_and_lantanas");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> LANTANAS_AND_PHLOX = WWFeatureUtils.register("lantanas_and_phlox");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SEEDING_DANDELION = WWFeatureUtils.register("seeding_dandelion");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> CARNATION = WWFeatureUtils.register("carnation");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MARIGOLD = WWFeatureUtils.register("marigold");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> EYEBLOSSOM = WWFeatureUtils.register("eyeblossom");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PINK_TULIP = WWFeatureUtils.register("pink_tulip");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> ALLIUM = WWFeatureUtils.register("allium");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> DATURA = WWFeatureUtils.register("datura");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> ROSE_BUSH = WWFeatureUtils.register("rose_bush");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PEONY = WWFeatureUtils.register("peony");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> LILAC = WWFeatureUtils.register("lilac");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_GENERIC = WWFeatureUtils.register("flower_generic");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_GENERIC_NO_CARNATION = WWFeatureUtils.register("flower_generic_no_carnation");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_PLAINS = WWFeatureUtils.register("flower_plains");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_SNOWY_PLAINS = WWFeatureUtils.register("flower_snowy_plains");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_TUNDRA = WWFeatureUtils.register("flower_tundra");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_BIRCH = WWFeatureUtils.register("flower_birch");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_MEADOW = WWFeatureUtils.register("flower_meadow");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MILKWEED = WWFeatureUtils.register("milkweed");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MILKWEED_SWAMP = WWFeatureUtils.register("milkweed_swamp");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> HIBISCUS = WWFeatureUtils.register("hibiscus");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> HIBISCUS_JUNGLE = WWFeatureUtils.register("hibiscus_jungle");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_FLOWER_FIELD = WWFeatureUtils.register("flower_flower_field");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MOSS_CARPET = WWFeatureUtils.register("moss_carpet");

	public static final WeightedList<BlockState> FLOWERS_CHERRY_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.POPPY.defaultBlockState(), 9)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 5)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_CYPRESS_WETLANDS = WWFeatureUtils.register("flower_cypress_wetlands");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_CYPRESS_WETLANDS = WWFeatureUtils.register("tall_flower_cypress_wetlands");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_TEMPERATE_RAINFOREST = WWFeatureUtils.register("flower_temperate_rainforest");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_TEMPERATE_RAINFOREST = WWFeatureUtils.register("tall_flower_temperate_rainforest");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("flower_temperate_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flower_temperate_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PALE_MUSHROOM = WWFeatureUtils.register("pale_mushroom");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> HUGE_PALE_MUSHROOMS = WWFeatureUtils.register("huge_pale_mushrooms");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MUSHROOMS_DARK_FOREST = WWFeatureUtils.register("mushroom_dark_forest");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_RAINFOREST = WWFeatureUtils.register("flower_rainforest");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_RAINFOREST = WWFeatureUtils.register("tall_flower_rainforest");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_RAINFOREST_VANILLA = WWFeatureUtils.register("flower_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flower_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_JUNGLE = WWFeatureUtils.register("flower_jungle");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_FLOWER_JUNGLE = WWFeatureUtils.register("tall_flower_jungle");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> TALL_FLOWER_FLOWER_FIELD = WWFeatureUtils.register("tall_flower_flower_field");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_CHERRY = WWFeatureUtils.register("flower_cherry");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_SUNFLOWER_PLAINS = WWFeatureUtils.register("flower_sunflower_plains");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_BIRCH_CLEARING = WWFeatureUtils.register("flower_birch_clearing");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWER_FOREST_CLEARING = WWFeatureUtils.register("flower_forest_clearing");

	// VEGETATION
	public static final WeightedList<BlockState> GRASS_OASIS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
		.build();

	public static final WeightedList<BlockState> SHRUB_OASIS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
		.build();

	public static final WeightedList<BlockState> SHRUB_JUNGLE_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 5)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 5)
		.build();

	public static final WeightedList<BlockState> SHRUB_SPARSE_JUNGLE_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
		.build();

	public static final WeightedList<BlockState> FROZEN_VEGETATION_TAIGA_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.FROZEN_SHORT_GRASS.defaultBlockState(), 1)
		.add(WWBlocks.FROZEN_FERN.defaultBlockState(), 4)
		.build();

	public static final WeightedList<BlockState> FROZEN_LARGE_FERN_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.FROZEN_LARGE_FERN.defaultBlockState(), 1)
		.add(WWBlocks.FROZEN_FERN.defaultBlockState(), 2)
		.build();
	public static final WeightedList<BlockState> FROZEN_TALL_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.FROZEN_TALL_GRASS.defaultBlockState(), 1)
		.add(WWBlocks.FROZEN_SHORT_GRASS.defaultBlockState(), 2)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FROZEN_BUSH = WWFeatureUtils.register("frozen_bush");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TAIGA_FROZEN_GRASS = WWFeatureUtils.register("taiga_frozen_grass");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FROZEN_GRASS = WWFeatureUtils.register("frozen_grass");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FROZEN_LARGE_FERN = WWFeatureUtils.register("frozen_large_fern");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FROZEN_TALL_GRASS = WWFeatureUtils.register("frozen_tall_grass");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SINGLE_PIECE_OF_FROZEN_GRASS = WWFeatureUtils.register("single_piece_of_frozen_grass");

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GRASS_OASIS = WWFeatureUtils.register("grass_oasis");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_OASIS = WWFeatureUtils.register("shrub_oasis");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_JUNGLE = WWFeatureUtils.register("shrub_jungle");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_SPARSE = WWFeatureUtils.register("shrub_sparse");

	public static final WeightedList<BlockState> SHRUB_FLOWER_FIELD_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 4)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 4)
		.build();

	public static final WeightedList<BlockState> SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 2)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_FLOWER_FIELD = WWFeatureUtils.register("shrub_flower_field");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_GENERIC = WWFeatureUtils.register("shrub_generic");

	public static final WeightedList<BlockState> SHRUB_DESERT_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(ShrubBlock.AGE, 2), 3)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SHRUB_DESERT = WWFeatureUtils.register("shrub_desert");
	public static final FrozenLibConfiguredFeature<BlockColumnConfiguration> CACTUS_OASIS = WWFeatureUtils.register("cactus_oasis");
	public static final FrozenLibConfiguredFeature<BlockColumnConfiguration> CACTUS_TALL = WWFeatureUtils.register("cactus_tall");
	public static final FrozenLibConfiguredFeature<BlockColumnConfiguration> CACTUS_TALL_BADLANDS = WWFeatureUtils.register("cactus_tall_badlands");

	public static final WeightedList<BlockState> PRICKLY_PEAR_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 2)
		.add(Blocks.CACTUS.defaultBlockState(), 3)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> PRICKLY_PEAR = WWFeatureUtils.register("prickly_pear");

	public static final WeightedList<BlockState> LARGE_FERN_AND_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> LARGE_FERN_AND_GRASS = WWFeatureUtils.register("large_fern_and_grass");

	public static final WeightedList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> LARGE_FERN_AND_GRASS_2 = WWFeatureUtils.register("large_fern_and_grass_2");

	public static final WeightedList<BlockState> FERN_AND_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();
	public static final WeightedList<BlockState> GRASS_AND_FERN_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 11)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();
	public static final WeightedList<BlockState> TALL_GRASS_AND_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 1)
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 4)
		.build();
	public static final WeightedList<BlockState> FERN_SWAMP_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.FERN.defaultBlockState(), 4)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_GRASS_AND_GRASS_WATER = WWFeatureUtils.register("tall_grass_and_grass_water");

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TALL_GRASS_SWAMP = WWFeatureUtils.register("tall_grass_swamp");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FERN_SWAMP = WWFeatureUtils.register("fern_swamp");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FERN_AND_GRASS = WWFeatureUtils.register("fern_and_grass");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GRASS_AND_FERN = WWFeatureUtils.register("grass_and_fern");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> MYCELIUM_GROWTH = WWFeatureUtils.register("mycelium_growth");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> POLLEN = WWFeatureUtils.register("pollen");
	public static final FrozenLibConfiguredFeature<ShelfFungiFeatureConfig> CRIMSON_SHELF_FUNGI = WWFeatureUtils.register("crimson_shelf_fungi");
	public static final FrozenLibConfiguredFeature<ShelfFungiFeatureConfig> WARPED_SHELF_FUNGI = WWFeatureUtils.register("warped_shelf_fungi");
	public static final FrozenLibConfiguredFeature<ColumnWithDiskFeatureConfig> TERMITE_MOUND = WWFeatureUtils.register("termite_mound");

	public static final WeightedList<BlockState> TUMBLEWEED_PLANT_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1)
		.build();

	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> TUMBLEWEED = WWFeatureUtils.register("tumbleweed");

	private WWConfiguredFeatures() {
		throw new UnsupportedOperationException("WWConfiguredFeatures contains only static declarations.");
	}

	public static void registerConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.logWithModId("Registering WWConfiguredFeatures for", true);

		FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.3F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.1F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.15F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.6F),
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder()))
		);

		FALLEN_CHERRY.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_CHERRY_CHECKED.getHolder(), 0.6F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_CHERRY_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.FALLEN_CHERRY_CHECKED.getHolder()
			)
		);

		FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		CLEAN_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.CLEAN_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()))
		);

		FALLEN_SWAMP_TREES_WILLOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.getHolder(), 0.75F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.getHolder(), 0.2F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		DECORATED_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		CLEAN_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.55F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_OAK_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.FALLEN_CYPRESS_CHECKED.getHolder()
			)
		);

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_ACACIA_CHECKED.getHolder(), 0.7F)),
				WWTreePlaced.FALLEN_OAK_NO_MOSS_CHECKED.getHolder()
			)
		);

		FALLEN_LARGE_JUNGLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_FALLEN_JUNGLE_CHECKED.getHolder()))
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_PALM_CHECKED.getHolder(), 0.135F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_JUNGLE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)),
				WWTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_PALE_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_FALLEN_PALE_OAK_CHECKED.getHolder()))
		);

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.135F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_MANGROVE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_MANGROVE_CHECKED.getHolder()))
		);

		FALLEN_DARK_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_FALLEN_DARK_OAK_CHECKED.getHolder()))
		);

		FALLEN_MAPLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_MAPLE_CHECKED.getHolder()))
		);

		TREES_PLAINS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.35F),
					new WeightedPlacedFeature(WWTreePlaced.LARGE_BUSH_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.LARGE_BUSH_CHECKED.getHolder()
			)
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_BEES_025.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.FANCY_DYING_OAK_BEES_025.asWeightedPlacedFeature(0.09F),
					WWTreePlaced.BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(WWTreePlaced.LARGE_BUSH_CHECKED.getHolder(), 0.5F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.3F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.169F)
				),
				PlacementUtils.inlinePlaced(WWTreeConfigured.OAK_BEES_0004.getHolder())
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_002), 0.1F)),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_002)
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002_LEAF_LITTER), 0.1F)),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002_LEAF_LITTER)
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER.getHolder())
			)
		);

		TREES_BIRCH_AND_OAK_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F),
				WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.26F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.055F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.04F),
				WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.115F)),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.04F),
				WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.26F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.055F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.04F),
				WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.115F)),
				WWTreePlaced.OAK_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_BIRCH_AND_OAK_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(TREES_BIRCH_AND_OAK_NO_LITTER.getHolder())
			)
		);

		TREES_BIRCH_AND_OAK_CALM.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.355F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.05F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.055F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlaced.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlaced.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlaced.OAK_CHECKED.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.DEAD_BIRCH.asWeightedPlacedFeature(0.22F),
					WWTreePlaced.DEAD_MEDIUM_BIRCH.asWeightedPlacedFeature(0.32F),
					WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.0433F),
					WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.085F),
					WWTreePlaced.DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.483F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_DYING_MIXED_FOREST_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.33F),
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.25F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlaced.OAK_CHECKED.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_DYING_MIXED_FOREST_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.33F),
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.25F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.DEAD_OAK_CHECKED.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlaced.OAK_CHECKED.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_DYING_MIXED_FOREST_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(TREES_DYING_MIXED_FOREST_NO_LITTER.getHolder())
			)
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.13F),
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.25F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.DEAD_BIRCH.asWeightedPlacedFeature(0.22F),
					WWTreePlaced.DEAD_MEDIUM_BIRCH.asWeightedPlacedFeature(0.32F),
					WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.0433F),
					WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.085F),
					WWTreePlaced.DEAD_OAK_CHECKED.asWeightedPlacedFeature(0.483F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.06F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.13F),
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.14F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.045F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.025F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.13F),
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.14F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.025F)
				),
				WWTreePlaced.OAK_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.getHolder())
			)
		);

		TREES_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.012F),
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.035F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.BIRCH_BEES_0004.asWeightedPlacedFeature(0.05F)
				),
				WWTreePlaced.MEDIUM_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.002F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.001F),
					WWTreePlaced.DYING_SUPER_BIRCH.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.BIRCH_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.177F)
				),
				WWTreePlaced.SUPER_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.002F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.001F),
					WWTreePlaced.DYING_SUPER_BIRCH.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.177F)
				),
				WWTreePlaced.SUPER_BIRCH_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_BIRCH_TALL.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_BIRCH_TALL_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(TREES_BIRCH_TALL_NO_LITTER.getHolder())
			)
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.035F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.05F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.063F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.205F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.095F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		MIXED_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.33F),
				WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.29F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.12F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.025F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.23F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.325F)),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		MIXED_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.33F),
				WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.29F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.12F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.025F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
				WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.23F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.325F)),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		MIXED_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							MIXED_TREES_LEAF_LITTER.getHolder()
						),
						0.25F
					)
				),
				PlacementUtils.inlinePlaced(
					MIXED_TREES_NO_LITTER.getHolder()
				)
			)
		);

		TEMPERATE_RAINFOREST_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.042F),
				WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.021F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.041F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.05F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.025F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.09F),
				WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.4F),
				WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.72F),
				WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.asWeightedPlacedFeature(0.6F)),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.042F),
				WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.021F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.041F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.05F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.025F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.09F),
				WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.4F),
				WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.72F),
				WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.6F)),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getLitterVariantHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							TEMPERATE_RAINFOREST_TREES_LEAF_LITTER.getHolder()
						),
						0.2F
					)
				),
				PlacementUtils.inlinePlaced(
					TEMPERATE_RAINFOREST_TREES_NO_LITTER.getHolder()
				)
			)
		);

		RAINFOREST_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.OAK_CHECKED.asWeightedPlacedFeature(0.085F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.12F),
				WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.27F),
				WWTreePlaced.OLD_DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.15F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.022F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.052F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.120F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.098F),
				WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.37F),
				WWTreePlaced.BIRCH_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.19F)),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.OAK_CHECKED.litterAsWeightedPlacedFeature(0.085F),
				WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.12F),
				WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.27F),
				WWTreePlaced.OLD_DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.15F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.022F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.052F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.120F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.098F),
				WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.37F),
				WWTreePlaced.BIRCH_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.19F)),
				WWTreePlaced.DYING_OAK_CHECKED.getLitterVariantHolder()
			)
		);

		RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(RAINFOREST_TREES_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(RAINFOREST_TREES_NO_LITTER.getHolder())
			)
		);

		BIRCH_TAIGA_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.39F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.155F),
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.37F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F)
				),
				WWTreePlaced.SHORT_BIRCH.getHolder()
			)
		);

		BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.39F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.37F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F)
				),
				WWTreePlaced.SHORT_BIRCH.getLitterVariantHolder()
			)
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(BIRCH_TAIGA_TREES_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(BIRCH_TAIGA_TREES_NO_LITTER.getHolder())
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.39F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.155F),
				WWTreePlaced.DYING_SUPER_BIRCH.asWeightedPlacedFeature(0.37F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.BIRCH_CHECKED.asWeightedPlacedFeature(0.355F),
				WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.1F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.1F)),
				WWTreePlaced.BIRCH_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.39F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlaced.DYING_SUPER_BIRCH.litterAsWeightedPlacedFeature(0.37F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.BIRCH_CHECKED.litterAsWeightedPlacedFeature(0.355F),
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.1F)),
				WWTreePlaced.BIRCH_CHECKED.getLitterVariantHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER.getHolder())
			)
		);

		BIRCH_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.049F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.069F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.049F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.079F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.119F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.asWeightedPlacedFeature(0.165F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.069F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.079F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.119F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.165F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
			)
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(BIRCH_JUNGLE_TREES_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(BIRCH_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.07F),
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.055F),
					WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.089F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.049F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.059F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.069F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.055F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.089F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.059F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.069F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER.getHolder()),
						0.05F
					)
				),
				PlacementUtils.inlinePlaced(SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		DARK_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.55F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.015F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.32F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.027F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.012F),
				WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.185F)),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
				WWTreePlaced.DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.55F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.2F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.015F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.32F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.027F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.012F),
				WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.185F)),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(DARK_FOREST_VEGETATION_LEAF_LITTER.getHolder()),
						0.175F
					)
				),
				PlacementUtils.inlinePlaced(DARK_FOREST_VEGETATION_NO_LITTER.getHolder())
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
					WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.175F),
					WWTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
					WWTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.0355F),
					WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.45F),
					WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.1465F),
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.24F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.05F),
					WWTreePlaced.BIRCH_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
					WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.1465F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
					WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.175F),
					WWTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.0355F),
					WWTreePlaced.DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.45F),
					WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1465F),
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.24F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.05F),
					WWTreePlaced.BIRCH_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1465F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.getHolder()),
						0.3F
					)
				),
				PlacementUtils.inlinePlaced(OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER.getHolder())
			)
		);

		DARK_BIRCH_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.235F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.35F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.015F),
				WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.4F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.015F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.15F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.095F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.027F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.012F),
				WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.15F)),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.235F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.35F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.015F),
				WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.4F),
				WWTreePlaced.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.015F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.15F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.095F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.027F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.012F),
				WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.15F)),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(DARK_BIRCH_FOREST_VEGETATION_NO_LITTER.getHolder())
			)
		);

		DARK_TAIGA_VEGETATION_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.155F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.045F),
				WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.19F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.235F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.12F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.004F),
				WWTreePlaced.BIRCH_CHECKED.asWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.005F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.2F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.08F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.024F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.031F),
				WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.015F)),
				WWTreePlaced.DARK_OAK_CHECKED.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.155F),
				WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.045F),
				WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.19F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.235F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.075F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.12F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.004F),
				WWTreePlaced.BIRCH_CHECKED.litterAsWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.005F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.2F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.08F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.024F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.031F),
				WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.015F)),
				WWTreePlaced.DARK_OAK_CHECKED.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(DARK_TAIGA_VEGETATION_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(DARK_TAIGA_VEGETATION_NO_LITTER.getHolder())
			)
		);

		TREES_TAIGA_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_TAIGA_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.075F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_TAIGA_LEAF_LITTER.getHolder()),
						0.15F
					)
				),
				PlacementUtils.inlinePlaced(TREES_TAIGA_NO_LITTER.getHolder())
			)
		);

		SHORT_TREES_TAIGA.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder()))
		);

		SHORT_MEGA_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.43333334F),
					WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.125F),
					WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED.asWeightedPlacedFeature(0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.43333334F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.125F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW.getHolder(), 0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.025641026F),
					WWTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.028F),
					WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.30769232F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.045F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.025641026F),
					WWTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.028F),
					WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.30769232F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.getHolder())
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.getHolder())
			)
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.0255F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.18333334F),
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.255F)
				),
				WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TREES_GROVE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_ON_SNOW.getHolder(), 0.33333334F)),
				WWTreePlaced.SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.666F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.666F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(TREES_WINDSWEPT_HILLS_LEAF_LITTER.getHolder()),
						0.3F
					)
				),
				PlacementUtils.inlinePlaced(TREES_WINDSWEPT_HILLS_NO_LITTER.getHolder())
			)
		);

		MEADOW_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(WWTreePlaced.FANCY_OAK_BEES.asWeightedPlacedFeature(0.5F)),
				WWTreePlaced.SUPER_BIRCH_BEES.getHolder()
			)
		);

		SAVANNA_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
			)
		);

		SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SAVANNA_TREES_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(SAVANNA_TREES_NO_LITTER.getHolder())
			)
		);

		SAVANNA_TREES_BAOBAB_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlaced.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.asWeightedPlacedFeature(0.035F)
				),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		SAVANNA_TREES_BAOBAB_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.8F),
					WWTreePlaced.BAOBAB.litterAsWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.litterAsWeightedPlacedFeature(0.035F)
				),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
			)
		);

		SAVANNA_TREES_BAOBAB.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SAVANNA_TREES_BAOBAB_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(SAVANNA_TREES_BAOBAB_NO_LITTER.getHolder())
			)
		);

		SAVANNA_TREES_BAOBAB_VANILLA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlaced.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.asWeightedPlacedFeature(0.035F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_CHECKED)
			)
		);

		WINDSWEPT_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.getHolder()),
						0.75F
					)
				),
				PlacementUtils.inlinePlaced(WINDSWEPT_SAVANNA_TREES_NO_LITTER.getHolder())
			)
		);

		ARID_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.08F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.08F)
				),
				WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder()
			)
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(ARID_SAVANNA_TREES_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(ARID_SAVANNA_TREES_NO_LITTER.getHolder())
			)
		);

		ARID_SAVANNA_TREES_PALM_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.08F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.getHolder(), 0.052F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_PALM_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.08F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.getHolder(), 0.052F)
				),
				WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder()
			)
		);

		ARID_SAVANNA_TREES_PALM.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(ARID_SAVANNA_TREES_PALM_LEAF_LITTER.getHolder()),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(
					ARID_SAVANNA_TREES_PALM_NO_LITTER.getHolder()
				)
			)
		);

		PARCHED_FOREST_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.59F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.186F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
				WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.155F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.37F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
				WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.155F)),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		PARCHED_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.59F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.186F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
				WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.155F),
				new WeightedPlacedFeature(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder(), 0.37F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.01F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
				WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.155F)),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
			)
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							PARCHED_FOREST_TREES_LEAF_LITTER.getHolder()
						),
						0.2F
					)
				),
				PlacementUtils.inlinePlaced(
					PARCHED_FOREST_TREES_NO_LITTER.getHolder()
				)
			)
		);

		ARID_FOREST_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.7085F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.175F),
				WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.38F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.2325F)),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		ARID_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.7085F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.175F),
				WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.38F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.2325F)),
				WWTreePlaced.DYING_OAK_CHECKED.getLitterVariantHolder()
			)
		);

		ARID_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(ARID_FOREST_TREES_LEAF_LITTER.getHolder()),
						0.4F
					)
				),
				PlacementUtils.inlinePlaced(ARID_FOREST_TREES_NO_LITTER.getHolder())
			)
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CYPRESS.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_CYPRESS.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F),
					WWTreePlaced.OAK_CHECKED.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CYPRESS.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_CYPRESS.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.85F)),
				WWTreePlaced.SWAMP_CYPRESS.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.095F),
					new WeightedPlacedFeature(WWTreePlaced.LARGE_BUSH_CHECKED.getHolder(), 0.4F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.67F),
					WWTreePlaced.JUNIPER.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.JUNIPER.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.095F),
					new WeightedPlacedFeature(WWTreePlaced.LARGE_BUSH_CHECKED.getHolder(), 0.4F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.67F),
					WWTreePlaced.JUNIPER.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.JUNIPER.getLitterVariantHolder()
			)
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(WOODED_BADLANDS_TREES_LEAF_LITTER.getHolder()),
						0.4F
					)
				),
				PlacementUtils.inlinePlaced(
					WOODED_BADLANDS_TREES_NO_LITTER.getHolder()
				)
			)
		);

		SWAMP_TREES_NO_LITTER.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SWAMP_OAK_CHECKED.getHolder()))
		);

		SWAMP_TREES_LEAF_LITTER.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SWAMP_OAK_CHECKED.getLitterVariantHolder()))
		);

		SWAMP_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SWAMP_TREES_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(SWAMP_TREES_NO_LITTER.getHolder())
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.WILLOW_CHECKED.asWeightedPlacedFeature(0.75F),
					WWTreePlaced.WILLOW_TALL_CHECKED.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.SWAMP_OAK_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.WILLOW_CHECKED.litterAsWeightedPlacedFeature(0.75F),
					WWTreePlaced.WILLOW_TALL_CHECKED.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.SWAMP_OAK_CHECKED.getLitterVariantHolder()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(SWAMP_TREES_SURFACE_WILLOW_NO_LITTER.getHolder())
			)
		);

		SWAMP_TREES_WATER_SHALLOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.WILLOW_TALL_CHECKED.asWeightedPlacedFeature(0.75F)
				),
				WWTreePlaced.WILLOW_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_WATER.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(WWTreePlaced.WILLOW_TALLER_CHECKED.getHolder())
			)
		);

		LARGE_BUSHES_ON_SAND.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_BUSH_ON_SAND.getHolder()))
		);

		BIG_BUSHES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.3F),
					new WeightedPlacedFeature(WWTreePlaced.LARGE_BUSH_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.LARGE_BUSH_CHECKED.getHolder()
			)
		);

		PALMS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		PALMS_JUNGLE_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.7F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED_DIRT.getHolder(), 0.4F)
				),
				WWTreePlaced.PALM_CHECKED_DIRT.getHolder()
			)
		);

		PALMS_JUNGLE_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.getHolder(), 0.7F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED_DIRT_LEAF_LITTER.getHolder(), 0.4F)
				),
				WWTreePlaced.PALM_CHECKED_DIRT_LEAF_LITTER.getHolder()
			)
		);

		PALMS_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(PALMS_JUNGLE_LEAF_LITTER.getHolder()),
						0.075F
					)
				),
				PlacementUtils.inlinePlaced(PALMS_JUNGLE_NO_LITTER.getHolder())
			)
		);

		PALMS_OASIS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.5F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.getHolder(), 0.37F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		BAMBOO_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.asWeightedPlacedFeature(0.7F)
				),
				PlacementUtils.inlinePlaced(
					configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
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
				)
			)
		);

		BAMBOO_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.7F)
				),
				PlacementUtils.inlinePlaced(
					configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
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
				)
			)
		);

		BAMBOO_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(BAMBOO_JUNGLE_TREES_LEAF_LITTER.getHolder()),
						0.75F
					)
				),
				PlacementUtils.inlinePlaced(BAMBOO_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		SPARSE_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		SPARSE_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
			)
		);

		SPARSE_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(SPARSE_JUNGLE_TREES_LEAF_LITTER.getHolder()),
						0.05F
					)
				),
				PlacementUtils.inlinePlaced(SPARSE_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		JUNGLE_TREES_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
			)
		);

		JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(JUNGLE_TREES_LEAF_LITTER.getHolder()),
						0.1F
					)
				),
				PlacementUtils.inlinePlaced(
					JUNGLE_TREES_NO_LITTER.getHolder()
				)
			)
		);

		MANGROVE_VEGETATION_NO_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.TALL_MANGROVE_CHECKED.asWeightedPlacedFeature(0.85F)
				),
				WWTreePlaced.MANGROVE_CHECKED.getHolder()
			)
		);

		MANGROVE_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.TALL_MANGROVE_CHECKED.litterAsWeightedPlacedFeature(0.85F)
				),
				WWTreePlaced.MANGROVE_CHECKED.getLitterVariantHolder()
			)
		);

		MANGROVE_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(MANGROVE_VEGETATION_LEAF_LITTER.getHolder()),
						0.2F
					)
				),
				PlacementUtils.inlinePlaced(MANGROVE_VEGETATION_NO_LITTER.getHolder())
			)
		);

		CHERRIES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CHERRY_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_CHERRY_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_CHERRY_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_CHERRY_CHECKED.getHolder(), 0.0785F)
				),
				WWTreePlaced.CHERRY_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES.makeAndSetHolder(
			FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.FULL_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.YELLOW_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		ORANGE_MAPLES.makeAndSetHolder(
			FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.FULL_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.ORANGE_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		RED_MAPLES.makeAndSetHolder(
			FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.FULL_RED_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_RED_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.RED_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		MAPLES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		RED_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_RED_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES_BEES_SAPLING.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES_BEES_SAPLING.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES_BEES_SAPLING.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder()
			)
		);

		RED_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FULL_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_RED_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.RED_MAPLE_CHECKED.getHolder()
			)
		);

		MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES_NO_BEES.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES_NO_BEES.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES_NO_BEES.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder()
			)
		);

		PALE_OAKS.makeAndSetHolder(
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALE_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_PALE_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_TALL_PALE_OAK_CHECKED.getHolder(), 0.018F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_FANCY_PALE_OAK_CHECKED.getHolder(), 0.018F)
				),
				WWTreePlaced.PALE_OAK_CHECKED.getHolder()
			)
		);

		PALE_OAKS_CREAKING.makeAndSetHolder(
			Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALE_OAK_CREAKING_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_PALE_OAK_CREAKING_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_TALL_PALE_OAK_CREAKING_CHECKED.getHolder(), 0.018F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_FANCY_PALE_OAK_CREAKING_CHECKED.getHolder(), 0.018F)
				),
				WWTreePlaced.PALE_OAK_CREAKING_CHECKED.getHolder()
			)
		);

		TREES_PALE_GARDEN.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(PALE_OAKS_CREAKING.getHolder()),
						0.1F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(PALE_OAKS.getHolder()),
						0.9F
					)
				),
				WWTreePlaced.PALE_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCHES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder()))
		);

		SNAPPED_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.3F)),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_SPRUCES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()))
		);

		SNAPPED_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_SPRUCE_ON_SNOW.getHolder()))
		);

		SNAPPED_LARGE_SPRUCES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_SPRUCE_CHECKED.getHolder()))
		);

		SNAPPED_LARGE_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.5F)),
				WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_CYPRESSES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_CYPRESS_CHECKED.getHolder()))
		);

		SNAPPED_JUNGLES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()))
		);

		SNAPPED_LARGE_JUNGLES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_JUNGLE_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_ACACIAS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()))
		);

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder(), 0.3F)),
				WWTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()
			)
		);

		SNAPPED_CHERRY.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_CHERRY_CHECKED.getHolder()))
		);

		SNAPPED_DARK_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_DARK_OAK_CHECKED.getHolder()))
		);

		SNAPPED_MAPLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_MAPLE_CHECKED.getHolder()))
		);

		SNAPPED_PALE_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_PALE_OAK_CHECKED.getHolder()))
		);

		// LEAF LITTERS
		DARK_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.DARK_OAK_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
				)
			)
		);

		PALE_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.PALE_OAK_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
				)
			)
		);

		SPRUCE_LEAF_LITTER_SINGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.SPRUCE_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
				)
			)
		);

		// FLOWERS
		final WeightedList.Builder<BlockState> cloverStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				cloverStates.add(WWBlocks.CLOVERS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		CLOVER.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(cloverStates))
		);

		final WeightedList.Builder<BlockState> phloxStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				phloxStates.add(WWBlocks.PHLOX.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		PHLOX.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(phloxStates))
		);

		final WeightedList.Builder<BlockState> lantanasStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				lantanasStates.add(WWBlocks.LANTANAS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		LANTANAS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(lantanasStates))
		);

		WeightedList.Builder<BlockState> wildflowerStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				wildflowerStates.add(Blocks.WILDFLOWERS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		WILDFLOWERS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(wildflowerStates))
		);

		WILDFLOWERS_AND_PHLOX.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							PHLOX.getHolder(),
							CountPlacement.of(30),
							RandomOffsetPlacement.ofTriangle(6, 2),
							BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
						),
						0.3F
					)
				),
				PlacementUtils.inlinePlaced(
					WILDFLOWERS.getHolder(),
					CountPlacement.of(30),
					RandomOffsetPlacement.ofTriangle(6, 2),
					BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
				)
			)
		);

		WILDFLOWERS_AND_LANTANAS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							LANTANAS.getHolder(),
							CountPlacement.of(30),
							RandomOffsetPlacement.ofTriangle(6, 2),
							BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
						),
						0.3F
					)
				),
				PlacementUtils.inlinePlaced(
					WILDFLOWERS.getHolder(),
					CountPlacement.of(30),
					RandomOffsetPlacement.ofTriangle(6, 2),
					BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
				)
			)
		);

		LANTANAS_AND_PHLOX.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							LANTANAS.getHolder(),
							CountPlacement.of(30),
							RandomOffsetPlacement.ofTriangle(6, 2),
							BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
						),
						0.375F
					)
				),
				PlacementUtils.inlinePlaced(
					PHLOX.getHolder()
				)
			)
		);

		SEEDING_DANDELION.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.SEEDING_DANDELION))
		);

		CARNATION.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.CARNATION))
		);

		MARIGOLD.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MARIGOLD))
		);

		EYEBLOSSOM.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CLOSED_EYEBLOSSOM), true)
		);

		PINK_TULIP.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PINK_TULIP))
		);

		ALLIUM.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM))
		);

		DATURA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.DATURA))
		);

		ROSE_BUSH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH))
		);

		PEONY.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY))
		);

		LILAC.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC))
		);

		FLOWER_GENERIC.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_GENERIC_NO_CARNATION.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_PLAINS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState()
					)
				)
			)
		);

		FLOWER_SNOWY_PLAINS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState()
					)
				)
			)
		);

		FLOWER_TUNDRA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState()
					)
				)
			)
		);

		FLOWER_BIRCH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState()
					)
				)
			)
		);

		FLOWER_MEADOW.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.007833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		MILKWEED.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED))
		);

		MILKWEED_SWAMP.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED))
		);

		final SimpleBlockConfiguration hibiscusNoise = new SimpleBlockConfiguration(
			new NoiseProvider(
				1234L,
				new NormalNoise.NoiseParameters(0, 1D),
				0.088833334F,
				List.of(
					WWBlocks.RED_HIBISCUS.defaultBlockState(),
					WWBlocks.RED_HIBISCUS.defaultBlockState(),
					WWBlocks.YELLOW_HIBISCUS.defaultBlockState(),
					WWBlocks.WHITE_HIBISCUS.defaultBlockState(),
					WWBlocks.PINK_HIBISCUS.defaultBlockState(),
					WWBlocks.PURPLE_HIBISCUS.defaultBlockState(),
					WWBlocks.PURPLE_HIBISCUS.defaultBlockState()
				)
			)
		);

		HIBISCUS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			hibiscusNoise
		);

		HIBISCUS_JUNGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			hibiscusNoise
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(2345L,
					new NormalNoise.NoiseParameters(0, 1F), 0.016F,
					List.of(
						Blocks.DANDELION.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.defaultBlockState()
					)
				)
			)
		);

		MOSS_CARPET.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MOSS_CARPET))
		);

		FLOWER_CYPRESS_WETLANDS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.043833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_CYPRESS_WETLANDS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.043833334F,
					List.of(
						WWBlocks.DATURA.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.023833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.023833334F,
					List.of(
						WWBlocks.DATURA.defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.023833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.023833334F,
					List.of(
						Blocks.ROSE_BUSH.defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		PALE_MUSHROOM.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.PALE_MUSHROOM))
		);

		HUGE_PALE_MUSHROOMS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.HUGE_PALE_MUSHROOM.getHolder()), 0F)
				),
				PlacementUtils.inlinePlaced(WWTreeConfigured.HUGE_PALE_MUSHROOM.getHolder())
			)
		);

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5234L,
					new NormalNoise.NoiseParameters(0, 1.0),
					0.020833334F,
					List.of(
						Blocks.RED_MUSHROOM.defaultBlockState(),
						Blocks.BROWN_MUSHROOM.defaultBlockState()
					)
				)
			)
		);

		FLOWER_RAINFOREST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.034833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_RAINFOREST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.034833334F,
					List.of(
						WWBlocks.DATURA.defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.034833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.034833334F,
					List.of(
						Blocks.ROSE_BUSH.defaultBlockState(),
						Blocks.LILAC.defaultBlockState(),
						Blocks.PEONY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_JUNGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					1234L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.054833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_JUNGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					1234L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.054833334F,
					List.of(
						WWBlocks.DATURA.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState()
					)
				)
			)
		);

		FLOWER_SUNFLOWER_PLAINS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.054833334F,
					List.of(
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						WWBlocks.MARIGOLD.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_BIRCH_CLEARING.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.054833334F,
					List.of(
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState()
					)
				)
			)
		);

		FLOWER_FOREST_CLEARING.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.054833334F,
					List.of(
						Blocks.WHITE_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.defaultBlockState(),
						Blocks.SUNFLOWER.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						WWBlocks.MILKWEED.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.LILAC.defaultBlockState(),
						WWBlocks.CARNATION.defaultBlockState(),
						Blocks.PEONY.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)),
						CountPlacement.of(9),
						RandomOffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED)),
						CountPlacement.of(9),
						RandomOffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)),
						CountPlacement.of(9),
						RandomOffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						Feature.SIMPLE_BLOCK,
						new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)),
						CountPlacement.of(9),
						RandomOffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					)
				)
			)
		);

		FLOWER_CHERRY.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_CHERRY_POOL))
		);

		FROZEN_BUSH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FROZEN_BUSH))
		);

		TAIGA_FROZEN_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(FROZEN_VEGETATION_TAIGA_POOL)
			)
		);

		FROZEN_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS)
			)
		);

		FROZEN_LARGE_FERN.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(FROZEN_LARGE_FERN_POOL))
		);

		FROZEN_TALL_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(FROZEN_TALL_GRASS_POOL))
		);

		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS.defaultBlockState()))
		);

		GRASS_OASIS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_OASIS_POOL))
		);

		SHRUB_OASIS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_OASIS_POOL))
		);

		SHRUB_JUNGLE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_JUNGLE_POOL))
		);

		SHRUB_SPARSE.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_SPARSE_JUNGLE_POOL))
		);

		SHRUB_FLOWER_FIELD.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_FLOWER_FIELD_POOL))
		);

		SHRUB_GENERIC.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_POOL))
		);

		SHRUB_DESERT.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_DESERT_POOL))
		);

		CACTUS_OASIS.makeAndSetHolder(Feature.BLOCK_COLUMN,
			new BlockColumnConfiguration(
				List.of(
					BlockColumnConfiguration.layer(BiasedToBottomInt.of(3, 5), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnConfiguration.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL.makeAndSetHolder(Feature.BLOCK_COLUMN,
			new BlockColumnConfiguration(
				List.of(
					BlockColumnConfiguration.layer(BiasedToBottomInt.of(4, 5), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnConfiguration.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL_BADLANDS.makeAndSetHolder(Feature.BLOCK_COLUMN,
			new BlockColumnConfiguration(
				List.of(
					BlockColumnConfiguration.layer(BiasedToBottomInt.of(2, 6), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnConfiguration.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		PRICKLY_PEAR.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(PRICKLY_PEAR_POOL))
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL))
		);

		LARGE_FERN_AND_GRASS_2.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL_2))
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(TALL_GRASS_AND_GRASS_POOL))
		);

		TALL_GRASS_SWAMP.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS))
		);

		FERN_SWAMP.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(FERN_SWAMP_POOL))
		);

		FERN_AND_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(FERN_AND_GRASS_POOL))
		);

		GRASS_AND_FERN.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL))
		);

		MYCELIUM_GROWTH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MYCELIUM_GROWTH))
		);

		POLLEN.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.POLLEN,
				10,
				true,
				true,
				true,
				0.5F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.POLLEN_FEATURE_PLACEABLE
				)
			)
		);

		CRIMSON_SHELF_FUNGI.makeAndSetHolder(WWFeatures.SHELF_FUNGI_FEATURE,
			new ShelfFungiFeatureConfig(
				WWBlocks.CRIMSON_SHELF_FUNGI,
				12,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER
				)
			)
		);

		WARPED_SHELF_FUNGI.makeAndSetHolder(WWFeatures.SHELF_FUNGI_FEATURE,
			new ShelfFungiFeatureConfig(
				WWBlocks.WARPED_SHELF_FUNGI,
				12,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER
				)
			)
		);

		TERMITE_MOUND.makeAndSetHolder(FrozenLibFeatures.COLUMN_WITH_DISK_FEATURE,
			new ColumnWithDiskFeatureConfig(
				WWBlocks.TERMITE_MOUND.defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.TERMITE_DISK_REPLACEABLE
				),
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.TERMITE_DISK_BLOCKS
				)
			)
		);

		TUMBLEWEED.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(new WeightedStateProvider(TUMBLEWEED_PLANT_POOL))
		);
	}
}
