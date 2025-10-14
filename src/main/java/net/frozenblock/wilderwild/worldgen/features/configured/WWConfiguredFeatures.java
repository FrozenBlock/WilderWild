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

package net.frozenblock.wilderwild.worldgen.features.configured;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ComboFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils;
import net.frozenblock.wilderwild.worldgen.features.placed.WWTreePlaced;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.ShelfFungiFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
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
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

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
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> BIG_COARSE_BUSHES = WWFeatureUtils.register("big_coarse_bushes");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> BUSHES = WWFeatureUtils.register("bushes");
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
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_DARK_OAK_LEAF_LITTER = WWFeatureUtils.register("patch_dark_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_PALE_OAK_LEAF_LITTER = WWFeatureUtils.register("patch_pale_oak_leaf_litter");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_SPRUCE_LEAF_LITTER = WWFeatureUtils.register("patch_spruce_leaf_litter");

	// FLOWERS
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> CLOVERS = WWFeatureUtils.register("clovers");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PHLOX = WWFeatureUtils.register("phlox");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> LANTANAS = WWFeatureUtils.register("lantanas");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> WILDFLOWERS = WWFeatureUtils.register("wildflowers");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WILDFLOWERS_AND_PHLOX = WWFeatureUtils.register("wildflowers_and_phlox");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> WILDFLOWERS_AND_LANTANAS = WWFeatureUtils.register("wildflowers_and_lantanas");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> LANTANAS_AND_PHLOX = WWFeatureUtils.register("lantanas_and_phlox");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> SEEDING_DANDELION = WWFeatureUtils.register("seeding_dandelion");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> CARNATION = WWFeatureUtils.register("carnation");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MARIGOLD = WWFeatureUtils.register("marigold");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MARIGOLD_SPARSE = WWFeatureUtils.register("marigold_sparse");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> EYEBLOSSOM = WWFeatureUtils.register("eyeblossom");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PINK_TULIP_UNCOMMON = WWFeatureUtils.register("pink_tulip_uncommon");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> ALLIUM_UNCOMMON = WWFeatureUtils.register("allium_uncommon");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> DATURA = WWFeatureUtils.register("datura");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> ROSE_BUSH = WWFeatureUtils.register("rose_bush");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PEONY = WWFeatureUtils.register("peony");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> LILAC = WWFeatureUtils.register("lilac");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_GENERIC = WWFeatureUtils.register("flower_generic");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_GENERIC_NO_CARNATION = WWFeatureUtils.register("flower_generic_no_carnation");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_PLAINS = WWFeatureUtils.register("flower_plains");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_SNOWY_PLAINS = WWFeatureUtils.register("flower_snowy_plains");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_TUNDRA = WWFeatureUtils.register("flower_tundra");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_BIRCH = WWFeatureUtils.register("flower_birch");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_MEADOW = WWFeatureUtils.register("flower_meadow");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MILKWEED = WWFeatureUtils.register("milkweed");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> HIBISCUS = WWFeatureUtils.register("hibiscus");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> HIBISCUS_JUNGLE = WWFeatureUtils.register("hibiscus_jungle");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_FLOWER_FIELD = WWFeatureUtils.register("flower_flower_field");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MOSS_CARPET = WWFeatureUtils.register("moss_carpet");

	public static final WeightedList<BlockState> FLOWERS_CHERRY_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.POPPY.defaultBlockState(), 9)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 5)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_CYPRESS_WETLANDS = WWFeatureUtils.register("flowers_cypress_wetlands");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_CYPRESS_WETLANDS = WWFeatureUtils.register("tall_flowers_cypress_wetlands");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_TEMPERATE_RAINFOREST = WWFeatureUtils.register("flowers_temperate_rainforest");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_TEMPERATE_RAINFOREST = WWFeatureUtils.register("tall_flowers_temperate_rainforest");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("flowers_temperate_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flowers_temperate_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_PALE_MUSHROOM = WWFeatureUtils.register("patch_pale_mushroom");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> HUGE_PALE_MUSHROOMS = WWFeatureUtils.register("huge_pale_mushrooms");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MUSHROOMS_DARK_FOREST = WWFeatureUtils.register("mushroom_dark_forest");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_RAINFOREST = WWFeatureUtils.register("flowers_rainforest");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_RAINFOREST = WWFeatureUtils.register("tall_flowers_rainforest");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_RAINFOREST_VANILLA = WWFeatureUtils.register("flowers_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flowers_rainforest_vanilla");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_JUNGLE = WWFeatureUtils.register("flowers_jungle");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_FLOWERS_JUNGLE = WWFeatureUtils.register("tall_flowers_jungle");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration> TALL_FLOWER_FLOWER_FIELD = WWFeatureUtils.register("tall_flower_flower_field");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_CHERRY = WWFeatureUtils.register("flowers_cherry");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_SUNFLOWER_PLAINS = WWFeatureUtils.register("flowers_sunflower_plains");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_BIRCH_CLEARING = WWFeatureUtils.register("flowers_birch_clearing");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWERS_FOREST_CLEARING = WWFeatureUtils.register("flowers_forest_clearing");

	// VEGETATION
	public static final WeightedList<BlockState> OASIS_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
		.build();

	public static final WeightedList<BlockState> OASIS_SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final WeightedList<BlockState> JUNGLE_SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final WeightedList<BlockState> SPARSE_JUNGLE_SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 3)
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

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_FROZEN_BUSH = WWFeatureUtils.register("patch_frozen_bush");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_TAIGA_FROZEN_GRASS = WWFeatureUtils.register("patch_taiga_frozen_grass");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_FROZEN_GRASS = WWFeatureUtils.register("patch_frozen_grass");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_FROZEN_LARGE_FERN = WWFeatureUtils.register("patch_frozen_large_fern");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_FROZEN_TALL_GRASS = WWFeatureUtils.register("patch_frozen_tall_grass");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SINGLE_PIECE_OF_FROZEN_GRASS = WWFeatureUtils.register("single_piece_of_frozen_grass");

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> OASIS_GRASS = WWFeatureUtils.register("oasis_grass");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> OASIS_SHRUB = WWFeatureUtils.register("oasis_shrub");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> JUNGLE_SHRUB = WWFeatureUtils.register("jungle_shrub");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> SPARSE_SHRUB = WWFeatureUtils.register("sparse_shrub");

	public static final WeightedList<BlockState> FLOWER_FIELD_SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final WeightedList<BlockState> SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FLOWER_FIELD_SHRUB = WWFeatureUtils.register("flower_field_shrub");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> GENERIC_SHRUB = WWFeatureUtils.register("generic_shrub");

	public static final WeightedList<BlockState> DESERT_SHRUB_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.SHRUB.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 4)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> DESERT_SHRUB = WWFeatureUtils.register("desert_shrub");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> BADLANDS_SHRUB_SAND = WWFeatureUtils.register("badlands_shrub_sand");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> BADLANDS_SHRUB_TERRACOTTA = WWFeatureUtils.register("badlands_shrub_terracotta");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> WOODED_BADLANDS_SHRUB_TERRACOTTA = WWFeatureUtils.register("wooded_badlands_shrub_terracotta");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> WOODED_BADLANDS_SHRUB_DIRT = WWFeatureUtils.register("wooded_badlands_shrub_dirt");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_CACTUS_OASIS = WWFeatureUtils.register("patch_cactus_oasis");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_CACTUS_TALL = WWFeatureUtils.register("patch_cactus_tall");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PATCH_CACTUS_TALL_BADLANDS = WWFeatureUtils.register("patch_cactus_tall_badlands");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> DRY_GRASS_SPARSE_ON_SAND = WWFeatureUtils.register("dry_grass_sparse_on_sand");

	public static final WeightedList<BlockState> PRICKLY_PEAR_POOL = WeightedList.<BlockState>builder()
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 2)
		.add(Blocks.CACTUS.defaultBlockState(), 3)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> PRICKLY_PEAR = WWFeatureUtils.register("prickly_pear");

	public static final WeightedList<BlockState> LARGE_FERN_AND_GRASS_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> LARGE_FERN_AND_GRASS = WWFeatureUtils.register("large_fern_and_grass");

	public static final WeightedList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = WeightedList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> LARGE_FERN_AND_GRASS_2 = WWFeatureUtils.register("large_fern_and_grass_2");

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
	public static final WeightedList<BlockState> SWAMP_FERN_POOL = WeightedList.<BlockState>builder()
		.add(Blocks.FERN.defaultBlockState(), 4)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();


	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TALL_GRASS_AND_GRASS_WATER = WWFeatureUtils.register("tall_grass_and_grass_water");

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> SWAMP_TALL_GRASS = WWFeatureUtils.register("swamp_tall_grass");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> SWAMP_FERN = WWFeatureUtils.register("swamp_fern");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> FERN_AND_GRASS = WWFeatureUtils.register("fern_and_grass");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> GRASS_AND_FERN = WWFeatureUtils.register("grass_and_fern");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> MYCELIUM_GROWTH = WWFeatureUtils.register("mycelium_growth");
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

	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration> TUMBLEWEED = WWFeatureUtils.register("tumbleweed");

	private WWConfiguredFeatures() {
		throw new UnsupportedOperationException("WWConfiguredFeatures contains only static declarations.");
	}

	public static void registerConfiguredFeatures(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
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
					new WeightedPlacedFeature(WWTreePlaced.BUSH_CHECKED.getHolder(), 0.35F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.BIG_BUSH_CHECKED.getHolder()
			)
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_BEES_025.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.FANCY_DYING_OAK_BEES_025.asWeightedPlacedFeature(0.09F),
					WWTreePlaced.BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.5F),
					new WeightedPlacedFeature(WWTreePlaced.BUSH_CHECKED.getHolder(), 0.3F),
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
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.55F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.255F),
				WWTreePlaced.BIRCH_CHECKED.asWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.04F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.6F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.522F),
				WWTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.018F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.0766F),
				WWTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.035F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.asWeightedPlacedFeature(0.222F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.095F),
				WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.045F),
				WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.24F)),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
				WWTreePlaced.DARK_OAK_CHECKED.asWeightedPlacedFeature(0.55F),
				WWTreePlaced.DYING_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.255F),
				WWTreePlaced.BIRCH_CHECKED.litterAsWeightedPlacedFeature(0.1F),
				WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.04F),
				WWTreePlaced.TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.6F),
				WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.522F),
				WWTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.018F),
				WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.0766F),
				WWTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.035F),
				WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.litterAsWeightedPlacedFeature(0.222F),
				WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.095F),
				WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.045F),
				WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.24F)),
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
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_COARSE_GRASS_CHECKED.getHolder(), 0.4F),
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
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_COARSE_GRASS_CHECKED.getHolder(), 0.4F),
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

		BIG_COARSE_BUSHES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.BIG_BUSH_COARSE_CHECKED.getHolder()))
		);

		BUSHES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.BUSH_CHECKED.getHolder(), 0.3F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_BUSH_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.BIG_BUSH_CHECKED.getHolder()
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
				PlacementUtils.inlinePlaced(PALMS_JUNGLE.getHolder())
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
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE))
			)
		);

		BAMBOO_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.7F)
				),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE))
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
								new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
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
								new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
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
								new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
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
					new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		RED_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
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
					new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder()
			)
		);

		RED_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
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

		PATCH_DARK_OAK_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(
							VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.DARK_OAK_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
						)
					)
				)
			)
		);

		PATCH_PALE_OAK_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(
							VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.PALE_OAK_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
						)
					)
				)
			)
		);

		PATCH_SPRUCE_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(
							VegetationFeatures.segmentedBlockPatchBuilder(WWBlocks.SPRUCE_LEAF_LITTER, 1, 3, LeafLitterBlock.AMOUNT, LeafLitterBlock.FACING)
						)
					)
				)
			)
		);

		// FLOWERS

		WeightedList.Builder<BlockState> cloverStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				cloverStates.add(WWBlocks.CLOVERS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		CLOVERS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				32,
				6,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(cloverStates))
				)
			)
		);

		WeightedList.Builder<BlockState> phloxStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				phloxStates.add(WWBlocks.PHLOX.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		PHLOX.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				30,
				6,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(phloxStates))
				)
			)
		);

		WeightedList.Builder<BlockState> lantanasStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				lantanasStates.add(WWBlocks.LANTANAS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		LANTANAS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				30,
				6,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(lantanasStates))
				)
			)
		);

		WeightedList.Builder<BlockState> wildflowerStates = WeightedList.builder();
		for (int i = 1; i <= 4; i++) {
			for (Direction direction : Direction.Plane.HORIZONTAL) {
				wildflowerStates.add(Blocks.WILDFLOWERS.defaultBlockState().setValue(FlowerBedBlock.AMOUNT, i).setValue(FlowerBedBlock.FACING, direction), 1);
			}
		}
		WILDFLOWERS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				28,
				6,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(wildflowerStates))
				)
			)
		);

		WILDFLOWERS_AND_PHLOX.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(PHLOX.getHolder()), 0.3F)
				),
				PlacementUtils.inlinePlaced(WILDFLOWERS.getHolder())
			)
		);

		WILDFLOWERS_AND_LANTANAS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LANTANAS.getHolder()), 0.3F)
				),
				PlacementUtils.inlinePlaced(WILDFLOWERS.getHolder())
			)
		);

		LANTANAS_AND_PHLOX.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(LANTANAS.getHolder()), 0.375F)
				),
				PlacementUtils.inlinePlaced(PHLOX.getHolder())
			)
		);

		SEEDING_DANDELION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.SEEDING_DANDELION))
				)
			)
		);

		CARNATION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.CARNATION))
				)
			)
		);

		MARIGOLD.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MARIGOLD))
				)
			)
		);

		MARIGOLD_SPARSE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MARIGOLD))
				)
			)
		);

		EYEBLOSSOM.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.CLOSED_EYEBLOSSOM), true)
				)
			)
		);

		PINK_TULIP_UNCOMMON.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PINK_TULIP))
				)
			)
		);

		ALLIUM_UNCOMMON.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM))
				)
			)
		);


		DATURA.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.DATURA))
				)
			)
		);

		ROSE_BUSH.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH))
				)
			)
		);

		PEONY.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY))
				)
			)
		);

		LILAC.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC))
				)
			)
		);

		FLOWER_GENERIC.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_GENERIC_NO_CARNATION.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_SNOWY_PLAINS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				32,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_TUNDRA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				42,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_BIRCH.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWER_MEADOW.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				20,
				8,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		MILKWEED.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				20,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED))
				)
			)
		);

		Holder<PlacedFeature> hibiscusNoise = PlacementUtils.onlyWhenEmpty(
			Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
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
			)
		);

		HIBISCUS.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				hibiscusNoise
			)
		);

		HIBISCUS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				52,
				hibiscusNoise
			)
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				64,
				6,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		MOSS_CARPET.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				25,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MOSS_CARPET)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		FLOWERS_CYPRESS_WETLANDS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_CYPRESS_WETLANDS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		PATCH_PALE_MUSHROOM.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simplePatchConfiguration(
				Feature.SIMPLE_BLOCK,
				new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.PALE_MUSHROOM))
			)
		);

		HUGE_PALE_MUSHROOMS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.HUGE_PALE_MUSHROOM.getHolder()), 0F)
					),
				PlacementUtils.inlinePlaced(WWTreeConfigured.HUGE_PALE_MUSHROOM.getHolder())
			)
		);

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				50,
				4,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				36,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWERS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				8,
				6,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_SUNFLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				38,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_BIRCH_CLEARING.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		FLOWERS_FOREST_CLEARING.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
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
				)
			)
		);

		TALL_FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)),
							List.of(),
							9
						)
					)
				)
			)
		);

		FLOWERS_CHERRY.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_CHERRY_POOL))
				)
			)
		);

		PATCH_FROZEN_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				24,
				5,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FROZEN_BUSH))
				)
			)
		);

		PATCH_TAIGA_FROZEN_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(FROZEN_VEGETATION_TAIGA_POOL)
					)
				)
			)
		);

		PATCH_FROZEN_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS)
					)
				)
			)
		);

		PATCH_FROZEN_LARGE_FERN.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(FROZEN_LARGE_FERN_POOL)))
		);

		PATCH_FROZEN_TALL_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(FROZEN_TALL_GRASS_POOL)))
		);

		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS.defaultBlockState()))
		);

		OASIS_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				35,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_GRASS_POOL))
				)
			)
		);

		OASIS_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				23,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_SHRUB_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						// TODO: Maybe remove sand again
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON)
					)
				)
			)
		);

		JUNGLE_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(JUNGLE_SHRUB_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
				)
			)
		);

		SPARSE_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SPARSE_JUNGLE_SHRUB_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
				)
			)
		);

		FLOWER_FIELD_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWER_FIELD_SHRUB_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
				)
			)
		);

		GENERIC_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SHRUB_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
				)
			)
		);

		DESERT_SHRUB.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_SHRUB_POOL))
				)
			)
		);

		BADLANDS_SHRUB_SAND.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(DESERT_SHRUB_POOL)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		BADLANDS_SHRUB_TERRACOTTA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				6,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(DESERT_SHRUB_POOL)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.BADLANDS_TERRACOTTA)
						)
					)
				)
			)
		);

		WOODED_BADLANDS_SHRUB_TERRACOTTA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_SHRUB_POOL)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.BADLANDS_TERRACOTTA)
						)
					)
				)
			)
		);

		WOODED_BADLANDS_SHRUB_DIRT.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				15,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_SHRUB_POOL)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.DIRT)
						)
					)
				)
			)
		);

		PATCH_CACTUS_OASIS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
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
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		PATCH_CACTUS_TALL.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
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
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		PATCH_CACTUS_TALL_BADLANDS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
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
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		DRY_GRASS_SPARSE_ON_SAND.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(
							WeightedList.<BlockState>builder().add(Blocks.SHORT_DRY_GRASS.defaultBlockState(), 1).add(Blocks.TALL_DRY_GRASS.defaultBlockState(), 1)
						)
					),
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), BlockTags.SAND)
					)
				)
			)
		);

		PRICKLY_PEAR.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				15,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(PRICKLY_PEAR_POOL))
				)
			)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				36,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL))
				)
			)
		);

		LARGE_FERN_AND_GRASS_2.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				36,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL_2))
				)
			)
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				16,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(TALL_GRASS_AND_GRASS_POOL))
				)
			)
		);

		SWAMP_TALL_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				18,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS))
				)
			)
		);

		SWAMP_FERN.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				24,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SWAMP_FERN_POOL))
				)
			)
		);

		FERN_AND_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				32,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FERN_AND_GRASS_POOL))
				)
			)
		);

		GRASS_AND_FERN.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				32,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL))
				)
			)
		);

		MYCELIUM_GROWTH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MYCELIUM_GROWTH))
				)
			)
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

		TUMBLEWEED.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(TUMBLEWEED_PLANT_POOL))
				)
			)
		);
	}
}
