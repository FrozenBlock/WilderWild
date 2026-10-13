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

package net.frozenblock.wilderwild.data.worldgen.feature.configured;

import java.util.List;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnWithDiskFeature;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.LeafLitterStateProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShrubBlock;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreePlacements;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WWConfiguredFeatures {
	private static final NormalNoise SINGLE_OCTAVE_NOISE = NormalNoise.builder()
		.setBaseOctave(0)
		.setBaseAmplitude(1D)
		.setNormalize(false)
		.setOctaveCount(1)
		.setAmplitudeModifier(0, 1D)
		.build();

	// FALLEN TREES
	public static final FrozenLibFeature FALLEN_TREES_MIXED = register("fallen_trees_mixed");
	public static final FrozenLibFeature MOSSY_FALLEN_TREES_MIXED = register("mossy_fallen_trees_mixed");
	public static final FrozenLibFeature MOSSY_FALLEN_TREES_OAK_AND_BIRCH = register("mossy_fallen_trees_oak_and_birch");
	public static final FrozenLibFeature FALLEN_BIRCH_AND_SPRUCE = register("fallen_birch_and_spruce");
	public static final FrozenLibFeature FALLEN_BIRCH = register("fallen_birch");
	public static final FrozenLibFeature FALLEN_CHERRY = register("fallen_cherry");
	public static final FrozenLibFeature FALLEN_SPRUCE = register("fallen_spruce");
	public static final FrozenLibFeature CLEAN_FALLEN_SPRUCE = register("clean_fallen_spruce");
	public static final FrozenLibFeature FALLEN_SWAMP_TREES = register("fallen_swamp_trees");
	public static final FrozenLibFeature FALLEN_SWAMP_TREES_WILLOW = register("fallen_swamp_trees_willow");
	public static final FrozenLibFeature DECORATED_LARGE_FALLEN_SPRUCE = register("decorated_large_fallen_spruce");
	public static final FrozenLibFeature CLEAN_LARGE_FALLEN_SPRUCE = register("clean_large_fallen_spruce");
	public static final FrozenLibFeature FALLEN_SPRUCE_AND_OAK = register("fallen_spruce_and_oak");
	public static final FrozenLibFeature FALLEN_BIRCH_AND_OAK = register("fallen_birch_and_oak");
	public static final FrozenLibFeature FALLEN_CYPRESS_AND_OAK = register("fallen_cypress_and_oak");
	public static final FrozenLibFeature FALLEN_ACACIA_AND_OAK = register("fallen_acacia_and_oak");
	public static final FrozenLibFeature FALLEN_LARGE_JUNGLE = register("fallen_large_jungle");
	public static final FrozenLibFeature FALLEN_PALM_AND_JUNGLE_AND_OAK = register("fallen_palm_and_jungle_and_oak");
	public static final FrozenLibFeature FALLEN_JUNGLE_AND_OAK = register("fallen_jungle_and_oak");
	public static final FrozenLibFeature FALLEN_OAK_AND_BIRCH_DARK_FOREST = register("fallen_oak_and_birch_dark_forest");
	public static final FrozenLibFeature FALLEN_MANGROVE = register("fallen_mangrove");
	public static final FrozenLibFeature FALLEN_DARK_OAKS = register("fallen_dark_oaks");
	public static final FrozenLibFeature FALLEN_MAPLE = register("fallen_maple");
	public static final FrozenLibFeature FALLEN_POPLAR = register("fallen_poplar");
	public static final FrozenLibFeature FALLEN_PALE_OAKS = register("fallen_pale_oaks");

	// TREES
	public static final FrozenLibFeature TREES_PLAINS = register("trees_plains");
	public static final FrozenLibFeature TREES_FLOWER_FIELD = register("trees_flower_field");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER = register("trees_birch_and_oak_original_no_litter");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER = register("trees_birch_and_oak_original_leaf_litter");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_ORIGINAL = register("trees_birch_and_oak_original");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_NO_LITTER = register("trees_birch_and_oak_no_litter");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_LEAF_LITTER = register("trees_birch_and_oak_leaf_litter");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK = register("trees_birch_and_oak");
	public static final FrozenLibFeature TREES_BIRCH_AND_OAK_CALM = register("trees_birch_and_oak_calm");
	public static final FrozenLibFeature TREES_DYING_FOREST = register("trees_dying_forest");
	public static final FrozenLibFeature TREES_SNOWY_DYING_FOREST = register("trees_snowy_dying_forest");
	public static final FrozenLibFeature TREES_DYING_MIXED_FOREST_NO_LITTER = register("trees_dying_mixed_forest_no_litter");
	public static final FrozenLibFeature TREES_DYING_MIXED_FOREST_LEAF_LITTER = register("trees_dying_mixed_forest_leaf_litter");
	public static final FrozenLibFeature TREES_DYING_MIXED_FOREST = register("trees_dying_mixed_forest");
	public static final FrozenLibFeature TREES_SNOWY_DYING_MIXED_FOREST = register("trees_snowy_dying_mixed_forest");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK_NO_LITTER = register("trees_semi_birch_and_oak_no_litter");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER = register("trees_semi_birch_and_oak_leaf_litter");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK = register("trees_semi_birch_and_oak");
	public static final FrozenLibFeature TREES_BIRCH = register("trees_birch");
	public static final FrozenLibFeature TREES_BIRCH_TALL_NO_LITTER = register("trees_birch_tall_no_litter");
	public static final FrozenLibFeature TREES_BIRCH_TALL_LEAF_LITTER = register("trees_birch_tall_leaf_litter");
	public static final FrozenLibFeature TREES_BIRCH_TALL = register("trees_birch_tall");
	public static final FrozenLibFeature TREES_FLOWER_FOREST = register("trees_flower_forest");
	public static final FrozenLibFeature MIXED_TREES_NO_LITTER = register("mixed_trees_no_litter");
	public static final FrozenLibFeature MIXED_TREES_LEAF_LITTER = register("mixed_trees_leaf_litter");
	public static final FrozenLibFeature MIXED_TREES = register("mixed_trees");
	public static final FrozenLibFeature TEMPERATE_RAINFOREST_TREES_NO_LITTER = register("temperate_rainforest_trees_no_litter");
	public static final FrozenLibFeature TEMPERATE_RAINFOREST_TREES_LEAF_LITTER = register("temperate_rainforest_trees_leaf_litter");
	public static final FrozenLibFeature TEMPERATE_RAINFOREST_TREES = register("temperate_rainforest_trees");
	public static final FrozenLibFeature RAINFOREST_TREES_NO_LITTER = register("rainforest_trees_no_litter");
	public static final FrozenLibFeature RAINFOREST_TREES_LEAF_LITTER = register("rainforest_trees_leaf_litter");
	public static final FrozenLibFeature RAINFOREST_TREES = register("rainforest_trees");
	public static final FrozenLibFeature BIRCH_TAIGA_TREES_NO_LITTER = register("birch_taiga_trees_no_litter");
	public static final FrozenLibFeature BIRCH_TAIGA_TREES_LEAF_LITTER = register("birch_taiga_trees_leaf_litter");
	public static final FrozenLibFeature BIRCH_TAIGA_TREES = register("birch_taiga_trees");
	public static final FrozenLibFeature OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER = register("old_growth_birch_taiga_trees_no_litter");
	public static final FrozenLibFeature OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER = register("old_growth_birch_taiga_trees_leaf_litter");
	public static final FrozenLibFeature OLD_GROWTH_BIRCH_TAIGA_TREES = register("old_growth_birch_taiga_trees");
	public static final FrozenLibFeature BIRCH_JUNGLE_TREES_NO_LITTER = register("birch_jungle_trees_no_litter");
	public static final FrozenLibFeature BIRCH_JUNGLE_TREES_LEAF_LITTER = register("birch_jungle_trees_leaf_litter");
	public static final FrozenLibFeature BIRCH_JUNGLE_TREES = register("birch_jungle_trees");
	public static final FrozenLibFeature SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER = register("sparse_birch_jungle_trees_no_litter");
	public static final FrozenLibFeature SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER = register("sparse_birch_jungle_trees_leaf_litter");
	public static final FrozenLibFeature SPARSE_BIRCH_JUNGLE_TREES = register("sparse_birch_jungle_trees");
	public static final FrozenLibFeature DARK_FOREST_VEGETATION_NO_LITTER = register("dark_forest_vegetation_no_litter");
	public static final FrozenLibFeature DARK_FOREST_VEGETATION_LEAF_LITTER = register("dark_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER = register("old_growth_dark_forest_vegetation_no_litter");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER = register("old_growth_dark_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION_NO_LITTER = register("dark_birch_forest_vegetation_no_litter");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER = register("dark_birch_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenLibFeature DARK_TAIGA_VEGETATION_NO_LITTER = register("dark_taiga_vegetation_no_litter");
	public static final FrozenLibFeature DARK_TAIGA_VEGETATION_LEAF_LITTER = register("dark_taiga_vegetation_leaf_litter");
	public static final FrozenLibFeature DARK_TAIGA_VEGETATION = register("dark_taiga_vegetation");
	public static final FrozenLibFeature TREES_TAIGA_NO_LITTER = register("trees_taiga_no_litter");
	public static final FrozenLibFeature TREES_TAIGA_LEAF_LITTER = register("trees_taiga_leaf_litter");
	public static final FrozenLibFeature TREES_TAIGA = register("trees_taiga");
	public static final FrozenLibFeature SHORT_TREES_TAIGA = register("short_trees_taiga");
	public static final FrozenLibFeature SHORT_TREES_TAIGA_LEAF_LITTER = register("short_trees_taiga_leaf_litter");
	public static final FrozenLibFeature SHORT_MEGA_SPRUCE = register("short_mega_spruce_configured");
	public static final FrozenLibFeature SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow_configured");
	public static final FrozenLibFeature TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER = register("trees_old_growth_pine_taiga_no_litter");
	public static final FrozenLibFeature TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER = register("trees_old_growth_pine_taiga_leaf_litter");
	public static final FrozenLibFeature TREES_OLD_GROWTH_PINE_TAIGA = register("trees_old_growth_pine_taiga");
	public static final FrozenLibFeature TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER = register("trees_old_growth_spruce_taiga_no_litter");
	public static final FrozenLibFeature TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER = register("trees_old_growth_spruce_taiga_leaf_litter");
	public static final FrozenLibFeature TREES_OLD_GROWTH_SPRUCE_TAIGA = register("trees_old_growth_spruce_taiga");
	public static final FrozenLibFeature TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenLibFeature TREES_GROVE = register("trees_grove");
	public static final FrozenLibFeature TREES_WINDSWEPT_HILLS_NO_LITTER = register("trees_windswept_hills_no_litter");
	public static final FrozenLibFeature TREES_WINDSWEPT_HILLS_LEAF_LITTER = register("trees_windswept_hills_leaf_litter");
	public static final FrozenLibFeature TREES_WINDSWEPT_HILLS = register("trees_windswept_hills");
	public static final FrozenLibFeature MEADOW_TREES = register("meadow_trees");
	public static final FrozenLibFeature SAVANNA_TREES_NO_LITTER = register("savanna_trees_no_litter");
	public static final FrozenLibFeature SAVANNA_TREES_LEAF_LITTER = register("savanna_trees_leaf_litter");
	public static final FrozenLibFeature SAVANNA_TREES = register("savanna_trees");
	public static final FrozenLibFeature SAVANNA_TREES_BAOBAB_NO_LITTER = register("savanna_trees_baobab_no_litter");
	public static final FrozenLibFeature SAVANNA_TREES_BAOBAB_LEAF_LITTER = register("savanna_trees_baobab_leaf_liter");
	public static final FrozenLibFeature SAVANNA_TREES_BAOBAB = register("savanna_trees_baobab");
	public static final FrozenLibFeature SAVANNA_TREES_BAOBAB_VANILLA = register("savanna_trees_baobab_vanilla");
	public static final FrozenLibFeature WINDSWEPT_SAVANNA_TREES_NO_LITTER = register("windswept_savanna_trees_no_litter");
	public static final FrozenLibFeature WINDSWEPT_SAVANNA_TREES_LEAF_LITTER = register("windswept_savanna_trees_leaf_litter");
	public static final FrozenLibFeature WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees");
	public static final FrozenLibFeature ARID_SAVANNA_TREES_NO_LITTER = register("arid_savanna_trees_no_litter");
	public static final FrozenLibFeature ARID_SAVANNA_TREES_LEAF_LITTER = register("arid_savanna_trees_leaf_litter");
	public static final FrozenLibFeature ARID_SAVANNA_TREES = register("arid_savanna_trees");
	public static final FrozenLibFeature ARID_SAVANNA_TREES_PALM_NO_LITTER = register("arid_savanna_trees_palm_no_litter");
	public static final FrozenLibFeature ARID_SAVANNA_TREES_PALM_LEAF_LITTER = register("arid_savanna_trees_palm_leaf_litter");
	public static final FrozenLibFeature ARID_SAVANNA_TREES_PALM = register("arid_savanna_trees_palm");
	public static final FrozenLibFeature PARCHED_FOREST_TREES_NO_LITTER = register("parched_forest_trees_no_litter");
	public static final FrozenLibFeature PARCHED_FOREST_TREES_LEAF_LITTER = register("parched_forest_trees_leaf_litter");
	public static final FrozenLibFeature PARCHED_FOREST_TREES = register("parched_forest_trees");
	public static final FrozenLibFeature ARID_FOREST_TREES_NO_LITTER = register("arid_forest_trees_no_litter");
	public static final FrozenLibFeature ARID_FOREST_TREES_LEAF_LITTER = register("arid_forest_trees_leaf_litter");
	public static final FrozenLibFeature ARID_FOREST_TREES = register("arid_forest_trees");
	public static final FrozenLibFeature CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees");
	public static final FrozenLibFeature CYPRESS_WETLANDS_TREES_SAPLING = register("cypress_wetlands_trees_sapling");
	public static final FrozenLibFeature CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water");
	public static final FrozenLibFeature WOODED_BADLANDS_TREES_NO_LITTER = register("wooded_badlands_trees_no_litter");
	public static final FrozenLibFeature WOODED_BADLANDS_TREES_LEAF_LITTER = register("wooded_badlands_trees_leaf_litter");
	public static final FrozenLibFeature WOODED_BADLANDS_TREES = register("wooded_badlands_trees");
	public static final FrozenLibFeature SWAMP_TREES_NO_LITTER = register("swamp_trees_no_litter");
	public static final FrozenLibFeature SWAMP_TREES_LEAF_LITTER = register("swamp_trees_leaf_litter");
	public static final FrozenLibFeature SWAMP_TREES = register("swamp_trees");
	public static final FrozenLibFeature SWAMP_TREES_SURFACE_WILLOW_NO_LITTER = register("swamp_trees_surface_willow_no_litter");
	public static final FrozenLibFeature SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER = register("swamp_trees_surface_willow_leaf_litter");
	public static final FrozenLibFeature SWAMP_TREES_SURFACE_WILLOW = register("swamp_trees_surface_willow");
	public static final FrozenLibFeature SWAMP_TREES_WATER_SHALLOW = register("swamp_trees_water_shallow");
	public static final FrozenLibFeature SWAMP_TREES_WATER = register("swamp_trees_water");
	public static final FrozenLibFeature LARGE_BUSHES_ON_SAND = register("large_bushes_on_sand");
	public static final FrozenLibFeature BIG_BUSHES = register("big_bushes");
	public static final FrozenLibFeature PALMS = register("palms");
	public static final FrozenLibFeature PALMS_JUNGLE_NO_LITTER = register("palms_jungle_no_litter");
	public static final FrozenLibFeature PALMS_JUNGLE_LEAF_LITTER = register("palms_jungle_leaf_litter");
	public static final FrozenLibFeature PALMS_JUNGLE = register("palms_jungle");
	public static final FrozenLibFeature PALMS_OASIS = register("palms_oasis");
	public static final FrozenLibFeature BAMBOO_JUNGLE_TREES_NO_LITTER = register("bamboo_jungle_trees_no_litter");
	public static final FrozenLibFeature BAMBOO_JUNGLE_TREES_LEAF_LITTER = register("bamboo_jungle_trees_leaf_litter");
	public static final FrozenLibFeature BAMBOO_JUNGLE_TREES = register("bamboo_jungle_trees");
	public static final FrozenLibFeature JUNGLE_TREES_NO_LITTER = register("jungle_trees_no_litter");
	public static final FrozenLibFeature JUNGLE_TREES_LEAF_LITTER = register("jungle_trees_leaf_litter");
	public static final FrozenLibFeature JUNGLE_TREES = register("jungle_trees");
	public static final FrozenLibFeature SPARSE_JUNGLE_TREES_NO_LITTER = register("sparse_jungle_trees_no_litter");
	public static final FrozenLibFeature SPARSE_JUNGLE_TREES_LEAF_LITTER = register("sparse_jungle_trees_leaf_litter");
	public static final FrozenLibFeature SPARSE_JUNGLE_TREES = register("sparse_jungle_trees");
	public static final FrozenLibFeature MANGROVE_VEGETATION_NO_LITTER = register("mangrove_vegetation_no_litter");
	public static final FrozenLibFeature MANGROVE_VEGETATION_LEAF_LITTER = register("mangrove_vegetation_leaf_litter");
	public static final FrozenLibFeature MANGROVE_VEGETATION = register("mangrove_vegetation");
	public static final FrozenLibFeature CHERRIES = register("cherries");

	// MAPLE
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLES = MapleCollection.NAMES.map(name -> register(name + "_maples"));
	public static final FrozenLibFeature MAPLES = register("maples");
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLES_NO_BEES = MapleCollection.NAMES.map(name -> register(name + "_maples_no_bees"));
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLES_BEES_SAPLING = MapleCollection.NAMES.map(name -> register(name + "_maples_bees_sapling"));
	public static final FrozenLibFeature MAPLES_BEES_SAPLING = register("maples_bees_sapling");

	// POPLAR
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_NO_LITTER = PoplarCollection.NAMES.map(name -> register(name + "_poplars_no_litter"));
	public static final FrozenLibFeature POPLARS_NO_LITTER = register("poplars_no_litter");
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_LEAF_LITTER = PoplarCollection.NAMES.map(name -> register(name + "_poplars_leaf_litter"));
	public static final FrozenLibFeature POPLARS_LEAF_LITTER = register("poplars_leaf_litter");
	public static final FrozenLibFeature POPLARS = register("poplars");
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_NO_BEES_SAPLING = PoplarCollection.NAMES.map(name -> register(name + "_poplars_no_bees_sapling"));
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_BEES_SAPLING = PoplarCollection.NAMES.map(name -> register(name + "_poplars_bees_sapling"));

	public static final FrozenLibFeature PALE_OAKS = register("pale_oaks");
	public static final FrozenLibFeature PALE_OAKS_CREAKING = register("pale_oaks_creaking");
	public static final FrozenLibFeature TREES_PALE_GARDEN = register("trees_pale_garden");
	public static final FrozenLibFeature SNAPPED_BIRCHES = register("snapped_birches");
	public static final FrozenLibFeature SNAPPED_OAKS = register("snapped_oaks");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_OAK = register("snapped_birch_and_oak");
	public static final FrozenLibFeature SNAPPED_SPRUCES = register("snapped_spruces");
	public static final FrozenLibFeature SNAPPED_LARGE_SPRUCES = register("snapped_large_spruces");
	public static final FrozenLibFeature SNAPPED_SPRUCES_ON_SNOW = register("snapped_spruces_on_snow");
	public static final FrozenLibFeature SNAPPED_LARGE_SPRUCES_ON_SNOW = register("snapped_large_spruces_on_snow");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_SPRUCE = register("snapped_birch_and_spruce");
	public static final FrozenLibFeature SNAPPED_CYPRESSES = register("snapped_cypresses");
	public static final FrozenLibFeature SNAPPED_JUNGLES = register("snapped_jungles");
	public static final FrozenLibFeature SNAPPED_LARGE_JUNGLES = register("snapped_large_jungles");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_JUNGLE = register("snapped_birch_and_jungle");
	public static final FrozenLibFeature SNAPPED_ACACIAS = register("snapped_acacias");
	public static final FrozenLibFeature SNAPPED_ACACIA_AND_OAK = register("snapped_acacia_and_oak");
	public static final FrozenLibFeature SNAPPED_CHERRY = register("snapped_cherry");
	public static final FrozenLibFeature SNAPPED_DARK_OAKS = register("snapped_dark_oaks");
	public static final FrozenLibFeature SNAPPED_MAPLE = register("snapped_maple");
	public static final FrozenLibFeature SNAPPED_POPLAR = register("snapped_poplar");
	public static final FrozenLibFeature SNAPPED_PALE_OAKS = register("snapped_pale_oaks");

	// LEAF LITTERS
	public static final FrozenLibFeature DARK_OAK_LEAF_LITTER_SINGLE = register("dark_oak_leaf_litter_single");
	public static final FrozenLibFeature PALE_OAK_LEAF_LITTER_SINGLE = register("pale_oak_leaf_litter_single");
	public static final FrozenLibFeature SPRUCE_LEAF_LITTER_SINGLE = register("spruce_leaf_litter_single");

	// FLOWERS
	public static final FrozenLibFeature CLOVER = register("clover");
	public static final FrozenLibFeature PHLOX = register("phlox");
	public static final FrozenLibFeature LANTANAS = register("lantanas");
	public static final FrozenLibFeature WILDFLOWERS = register("wildflowers");
	public static final FrozenLibFeature WILDFLOWERS_AND_PHLOX = register("wildflowers_and_phlox");
	public static final FrozenLibFeature WILDFLOWERS_AND_LANTANAS = register("wildflowers_and_lantanas");
	public static final FrozenLibFeature LANTANAS_AND_PHLOX = register("lantanas_and_phlox");
	public static final FrozenLibFeature SEEDING_DANDELION = register("seeding_dandelion");
	public static final FrozenLibFeature CARNATION = register("carnation");
	public static final FrozenLibFeature MARIGOLD = register("marigold");
	public static final FrozenLibFeature PINK_TULIP = register("pink_tulip");
	public static final FrozenLibFeature ALLIUM = register("allium");
	public static final FrozenLibFeature DATURA = register("datura");
	public static final FrozenLibFeature ROSE_BUSH = register("rose_bush");
	public static final FrozenLibFeature PEONY = register("peony");
	public static final FrozenLibFeature LILAC = register("lilac");
	public static final FrozenLibFeature FLOWER_GENERIC = register("flower_generic");
	public static final FrozenLibFeature FLOWER_GENERIC_NO_CARNATION = register("flower_generic_no_carnation");
	public static final FrozenLibFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenLibFeature FLOWER_SNOWY_PLAINS = register("flower_snowy_plains");
	public static final FrozenLibFeature FLOWER_TUNDRA = register("flower_tundra");
	public static final FrozenLibFeature FLOWER_DAPPLED_FOREST = register("flower_dappled_forest");
	public static final FrozenLibFeature FLOWER_BIRCH = register("flower_birch");
	public static final FrozenLibFeature FLOWER_MEADOW = register("flower_meadow");
	public static final FrozenLibFeature MILKWEED = register("milkweed");
	public static final FrozenLibFeature HIBISCUS = register("hibiscus");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenLibFeature FLOWER_CYPRESS_WETLANDS = register("flower_cypress_wetlands");
	public static final FrozenLibFeature TALL_FLOWER_CYPRESS_WETLANDS = register("tall_flower_cypress_wetlands");
	public static final FrozenLibFeature FLOWER_TEMPERATE_RAINFOREST = register("flower_temperate_rainforest");
	public static final FrozenLibFeature TALL_FLOWER_TEMPERATE_RAINFOREST = register("tall_flower_temperate_rainforest");
	public static final FrozenLibFeature FLOWER_TEMPERATE_RAINFOREST_VANILLA = register("flower_temperate_rainforest_vanilla");
	public static final FrozenLibFeature TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA = register("tall_flower_temperate_rainforest_vanilla");
	public static final FrozenLibFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibFeature MUSHROOMS_DARK_FOREST = register("mushroom_dark_forest");
	public static final FrozenLibFeature FLOWER_RAINFOREST = register("flower_rainforest");
	public static final FrozenLibFeature TALL_FLOWER_RAINFOREST = register("tall_flower_rainforest");
	public static final FrozenLibFeature FLOWER_RAINFOREST_VANILLA = register("flower_rainforest_vanilla");
	public static final FrozenLibFeature TALL_FLOWER_RAINFOREST_VANILLA = register("tall_flower_rainforest_vanilla");
	public static final FrozenLibFeature FLOWER_JUNGLE = register("flower_jungle");
	public static final FrozenLibFeature TALL_FLOWER_JUNGLE = register("tall_flower_jungle");
	public static final FrozenLibFeature TALL_FLOWER_FLOWER_FIELD = register("tall_flower_flower_field");
	public static final FrozenLibFeature FLOWER_CHERRY = register("flower_cherry");
	public static final FrozenLibFeature FLOWER_SUNFLOWER_PLAINS = register("flower_sunflower_plains");
	public static final FrozenLibFeature FLOWER_FOREST_CLEARING = register("flower_forest_clearing");

	// VEGETATION
	public static final FrozenLibFeature MOSS_CARPET = register("moss_carpet");
	public static final FrozenLibFeature FROZEN_BUSH = register("frozen_bush");
	public static final FrozenLibFeature TAIGA_FROZEN_GRASS = register("taiga_frozen_grass");
	public static final FrozenLibFeature FROZEN_GRASS = register("frozen_grass");
	public static final FrozenLibFeature FROZEN_LARGE_FERN = register("frozen_large_fern");
	public static final FrozenLibFeature FROZEN_TALL_GRASS = register("frozen_tall_grass");
	public static final FrozenLibFeature SINGLE_PIECE_OF_FROZEN_GRASS = register("single_piece_of_frozen_grass");
	public static final FrozenLibFeature GRASS_OASIS = register("grass_oasis");
	public static final FrozenLibFeature SHRUB_OASIS = register("shrub_oasis");
	public static final FrozenLibFeature SHRUB_JUNGLE = register("shrub_jungle");
	public static final FrozenLibFeature SHRUB_SPARSE = register("shrub_sparse");
	public static final FrozenLibFeature SHRUB_FLOWER_FIELD = register("shrub_flower_field");
	public static final FrozenLibFeature SHRUB_GENERIC = register("shrub_generic");
	public static final FrozenLibFeature SHRUB_DESERT = register("shrub_desert");
	public static final FrozenLibFeature CACTUS_OASIS = register("cactus_oasis");
	public static final FrozenLibFeature CACTUS_TALL = register("cactus_tall");
	public static final FrozenLibFeature CACTUS_TALL_BADLANDS = register("cactus_tall_badlands");
	public static final FrozenLibFeature PRICKLY_PEAR = register("prickly_pear");
	public static final FrozenLibFeature LARGE_FERN_AND_GRASS = register("large_fern_and_grass");
	public static final FrozenLibFeature LARGE_FERN_AND_GRASS_2 = register("large_fern_and_grass_2");
	public static final FrozenLibFeature TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");
	public static final FrozenLibFeature TALL_GRASS_SWAMP = register("tall_grass_swamp");
	public static final FrozenLibFeature FERN_SWAMP = register("fern_swamp");
	public static final FrozenLibFeature FERN_AND_GRASS = register("fern_and_grass");
	public static final FrozenLibFeature GRASS_AND_FERN = register("grass_and_fern");
	public static final FrozenLibFeature MYCELIUM_GROWTH = register("mycelium_growth");
	public static final FrozenLibFeature POLLEN = register("pollen");
	public static final FrozenLibFeature TERMITE_MOUND = register("termite_mound");
	public static final FrozenLibFeature TUMBLEWEED = register("tumbleweed");

	public static void registerConfiguredFeatures(BootstrapContext<Feature> entries) {
		WWConstants.logWithModId("Registering WWConfiguredFeatures for", true);
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		FALLEN_TREES_MIXED.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_SPRUCE.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.3F)
				),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_MIXED.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MOSSY_FALLEN_SPRUCE.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.MOSSY_FALLEN_BIRCH.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.MOSSY_FALLEN_BIRCH.asWeightedPlacedFeature(0.15F)
				),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_SPRUCE.asWeightedPlacedFeature(0.6F),
					WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.FALLEN_SPRUCE.getHolder()
			)
		);

		FALLEN_BIRCH.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.FALLEN_BIRCH.asHolderSet()));

		FALLEN_CHERRY.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_CHERRY.asWeightedPlacedFeature(0.6F),
					WWTreePlacements.MOSSY_FALLEN_CHERRY.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.FALLEN_CHERRY.getHolder()
			)
		);

		FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.FALLEN_SPRUCE.asHolderSet()));

		CLEAN_FALLEN_SPRUCE.makeAndSetHolder(
			new SimpleRandomSelectorFeature(WWTreePlacements.CLEAN_FALLEN_SPRUCE.asHolderSet())
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.MOSSY_FALLEN_OAK.asHolderSet()));

		FALLEN_SWAMP_TREES_WILLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MOSSY_FALLEN_WILLOW.asWeightedPlacedFeature(0.75F),
					WWTreePlacements.MOSSY_FALLEN_WILLOW.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		DECORATED_LARGE_FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.DECORATED_LARGE_FALLEN_SPRUCE.asHolderSet()));

		CLEAN_LARGE_FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.CLEAN_LARGE_FALLEN_SPRUCE.asHolderSet()));

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_SPRUCE.asWeightedPlacedFeature(0.55F)),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_OAK.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.FALLEN_CYPRESS.getHolder()
			)
		);

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_ACACIA.asWeightedPlacedFeature(0.7F)),
				WWTreePlacements.FALLEN_OAK_NO_MOSS.getHolder()
			)
		);

		FALLEN_LARGE_JUNGLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_FALLEN_JUNGLE.asHolderSet()));

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_PALM.asWeightedPlacedFeature(0.135F),
					WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.FALLEN_JUNGLE.getHolder()
			)
		);

		FALLEN_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)),
				WWTreePlacements.FALLEN_JUNGLE.getHolder()
			)
		);

		FALLEN_PALE_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_FALLEN_PALE_OAK.asHolderSet()));

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.135F),
					WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_MANGROVE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.FALLEN_MANGROVE.asHolderSet()));

		FALLEN_DARK_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_FALLEN_DARK_OAK.asHolderSet()));

		FALLEN_MAPLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.FALLEN_MAPLE.asHolderSet()));

		FALLEN_POPLAR.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.FALLEN_POPLAR.asHolderSet()));

		TREES_PLAINS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.BIG_BUSH.asWeightedPlacedFeature(0.35F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlacements.LARGE_BUSH.getHolder()
			)
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK_BEES_025.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.FANCY_DYING_OAK_BEES_025.asWeightedPlacedFeature(0.09F),
					WWTreePlacements.BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.5F),
					WWTreePlacements.BIG_BUSH.asWeightedPlacedFeature(0.3F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.169F)
				),
				WWTreePlacements.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_002), 0.1F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_002)
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002_LEAF_LITTER), 0.1F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002_LEAF_LITTER)
			)
		);

		TREES_BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREES_BIRCH_AND_OAK_ORIGINAL_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_BIRCH_AND_OAK_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.055F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.115F)
				),
				WWTreePlacements.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.055F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.115F)
				),
				WWTreePlacements.OAK_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_BIRCH_AND_OAK_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREES_BIRCH_AND_OAK_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_BIRCH_AND_OAK_CALM.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.355F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.05F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.055F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F)
				),
				WWTreePlacements.OAK_BEES_0004.getHolder()
			)
		);

		TREES_DYING_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlacements.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlacements.SMALL_FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlacements.SMALL_FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DEAD_OAK.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlacements.OAK.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlacements.DEAD_OAK_BRANCHES.getLitterVariantHolder()
			)
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DEAD_BIRCH.asWeightedPlacedFeature(0.22F),
					WWTreePlacements.DEAD_MEDIUM_BIRCH.asWeightedPlacedFeature(0.32F),
					WWTreePlacements.FANCY_SEMI_DEAD_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_DEAD_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SMALL_FANCY_SEMI_DEAD_OAK.asWeightedPlacedFeature(0.0433F),
					WWTreePlacements.SMALL_FANCY_DEAD_OAK.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.085F),
					WWTreePlacements.DEAD_OAK.asWeightedPlacedFeature(0.483F)
				),
				WWTreePlacements.DEAD_OAK_BRANCHES.getHolder()
			)
		);

		TREES_DYING_MIXED_FOREST_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.33F),
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlacements.FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlacements.SMALL_FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlacements.SMALL_FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DEAD_OAK.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlacements.OAK.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlacements.DEAD_OAK_BRANCHES.getLitterVariantHolder()
			)
		);

		TREES_DYING_MIXED_FOREST_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.33F),
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.25F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DEAD_BIRCH.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DEAD_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlacements.SMALL_FANCY_SEMI_DEAD_OAK.litterAsWeightedPlacedFeature(0.0433F),
					WWTreePlacements.SMALL_FANCY_DEAD_OAK.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DEAD_OAK.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlacements.OAK.litterAsWeightedPlacedFeature(0.033F)
				),
				WWTreePlacements.DEAD_OAK_BRANCHES.getLitterVariantHolder()
			)
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_DYING_MIXED_FOREST_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(TREES_DYING_MIXED_FOREST_NO_LITTER.getHolder())
			)
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.13F),
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DEAD_BIRCH.asWeightedPlacedFeature(0.22F),
					WWTreePlacements.DEAD_MEDIUM_BIRCH.asWeightedPlacedFeature(0.32F),
					WWTreePlacements.FANCY_SEMI_DEAD_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_DEAD_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SMALL_FANCY_SEMI_DEAD_OAK.asWeightedPlacedFeature(0.0433F),
					WWTreePlacements.SMALL_FANCY_DEAD_OAK.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.085F),
					WWTreePlacements.DEAD_OAK.asWeightedPlacedFeature(0.483F)
				),
				WWTreePlacements.DEAD_OAK_BRANCHES.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.06F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.13F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.14F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.025F)
				),
				WWTreePlacements.OAK_BEES_0004.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.06F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.13F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.14F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.025F)
				),
				WWTreePlacements.OAK_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.getHolder())
			)
		);

		TREES_BIRCH.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.012F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.035F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.BIRCH_BEES_0004.asWeightedPlacedFeature(0.05F)
				),
				WWTreePlacements.MEDIUM_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.002F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.001F),
					WWTreePlacements.DYING_SUPER_BIRCH.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.BIRCH_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.177F)
				),
				WWTreePlacements.SUPER_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.002F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.001F),
					WWTreePlacements.DYING_SUPER_BIRCH.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.177F)
				),
				WWTreePlacements.SUPER_BIRCH_BEES_0004.getLitterVariantHolder()
			)
		);

		TREES_BIRCH_TALL.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_BIRCH_TALL_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_BIRCH_TALL_NO_LITTER.getHolder())
			)
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.035F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.05F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.063F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.195F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.095F)
				),
				WWTreePlacements.OAK_BEES_0004.getHolder()
			)
		);

		MIXED_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.33F),
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.29F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.12F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.23F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.325F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		MIXED_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.33F),
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.29F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.12F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.23F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.325F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		MIXED_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(MIXED_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.25F)),
				MIXED_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		TEMPERATE_RAINFOREST_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.042F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.021F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.041F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.05F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.09F),
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.72F),
					WWTreePlacements.SHORT_MEGA_SPRUCE.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlacements.DYING_FUNGUS_PINE.getHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.042F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.021F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.041F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.05F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.09F),
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.4F),
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.72F),
					WWTreePlacements.SHORT_MEGA_SPRUCE.litterAsWeightedPlacedFeature(0.6F)
				),
				WWTreePlacements.DYING_FUNGUS_PINE.getLitterVariantHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TEMPERATE_RAINFOREST_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.2F)),
				TEMPERATE_RAINFOREST_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		RAINFOREST_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK.asWeightedPlacedFeature(0.085F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.12F),
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.27F),
					WWTreePlacements.OLD_DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.022F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.052F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.120F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.098F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.BIRCH.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.19F)
				),
				WWTreePlacements.DYING_OAK.getHolder()
			)
		);

		RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK.litterAsWeightedPlacedFeature(0.085F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.12F),
					WWTreePlacements.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.27F),
					WWTreePlacements.OLD_DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.15F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.022F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.052F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.120F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.098F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.37F),
					WWTreePlacements.BIRCH.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.19F)
				),
				WWTreePlacements.DYING_OAK.getLitterVariantHolder()
			)
		);

		RAINFOREST_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(RAINFOREST_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				RAINFOREST_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		BIRCH_TAIGA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.39F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.155F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F)
				),
				WWTreePlacements.SHORT_BIRCH.getHolder()
			)
		);

		BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.39F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.37F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F)
				),
				WWTreePlacements.SHORT_BIRCH.getLitterVariantHolder()
			)
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_TAIGA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(BIRCH_TAIGA_TREES_NO_LITTER.getHolder())
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.39F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.155F),
					WWTreePlacements.DYING_SUPER_BIRCH.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.BIRCH.asWeightedPlacedFeature(0.355F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.BIRCH.getHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.39F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlacements.DYING_SUPER_BIRCH.litterAsWeightedPlacedFeature(0.37F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.BIRCH.litterAsWeightedPlacedFeature(0.355F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.BIRCH.getLitterVariantHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		BIRCH_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.049F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.069F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.049F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.079F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.119F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
					WWTreePlacements.MEGA_JUNGLE.asWeightedPlacedFeature(0.165F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.069F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.079F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.119F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
					WWTreePlacements.MEGA_JUNGLE.litterAsWeightedPlacedFeature(0.165F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				BIRCH_JUNGLE_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.07F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.055F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.089F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.asWeightedPlacedFeature(0.049F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.059F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.069F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.07F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.055F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.089F),
					WWTreePlacements.MEDIUM_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.049F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.059F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.069F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.05F)),
				PlacementUtils.inlinePlaced(SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		DARK_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.55F),
					WWTreePlacements.DYING_DARK_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.015F),
					WWTreePlacements.TALL_DARK_OAK.asWeightedPlacedFeature(0.32F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_TALL_DARK_OAK.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.027F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.012F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.185F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
					WWTreePlacements.DARK_OAK.litterAsWeightedPlacedFeature(0.55F),
					WWTreePlacements.DYING_DARK_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.32F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.027F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.012F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.185F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(DARK_FOREST_VEGETATION_LEAF_LITTER.asWeightedPlacedFeature(0.175F)),
				DARK_FOREST_VEGETATION_NO_LITTER.asInlinePlaced()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
					WWTreePlacements.TALL_DARK_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.175F),
					WWTreePlacements.COBWEB_TALL_DARK_OAK.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.COBWEB_FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_TALL_DARK_OAK.asWeightedPlacedFeature(0.0355F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.45F),
					WWTreePlacements.DYING_DARK_OAK.asWeightedPlacedFeature(0.1465F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.24F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.05F),
					WWTreePlacements.BIRCH.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_DARK_OAK.asWeightedPlacedFeature(0.1465F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
					WWTreePlacements.TALL_DARK_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.175F),
					WWTreePlacements.COBWEB_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.COBWEB_FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.0355F),
					WWTreePlacements.DARK_OAK.litterAsWeightedPlacedFeature(0.45F),
					WWTreePlacements.DYING_DARK_OAK.litterAsWeightedPlacedFeature(0.1465F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.24F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.05F),
					WWTreePlacements.BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_DARK_OAK.litterAsWeightedPlacedFeature(0.1465F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.asWeightedPlacedFeature(0.3F)),
				OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER.asInlinePlaced()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.235F),
					WWTreePlacements.DYING_DARK_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.35F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.015F),
					WWTreePlacements.MEDIUM_BIRCH.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.asWeightedPlacedFeature(0.015F),
					WWTreePlacements.TALL_DARK_OAK.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.095F),
					WWTreePlacements.DYING_TALL_DARK_OAK.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.027F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.012F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.15F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.235F),
					WWTreePlacements.DYING_DARK_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.35F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.4F),
					WWTreePlacements.DYING_MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.015F),
					WWTreePlacements.TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.15F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.095F),
					WWTreePlacements.DYING_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.027F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.012F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.15F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				DARK_BIRCH_FOREST_VEGETATION_NO_LITTER.asInlinePlaced()
			)
		);

		DARK_TAIGA_VEGETATION_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.155F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.19F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.235F),
					WWTreePlacements.DYING_DARK_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.12F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.004F),
					WWTreePlacements.BIRCH.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.005F),
					WWTreePlacements.TALL_DARK_OAK.asWeightedPlacedFeature(0.2F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.08F),
					WWTreePlacements.DYING_TALL_DARK_OAK.asWeightedPlacedFeature(0.024F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.031F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.015F)
				),
				WWTreePlacements.DARK_OAK.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.SHORT_SPRUCE.litterAsWeightedPlacedFeature(0.19F),
					WWTreePlacements.DARK_OAK.asWeightedPlacedFeature(0.235F),
					WWTreePlacements.DYING_DARK_OAK.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.12F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.004F),
					WWTreePlacements.BIRCH.litterAsWeightedPlacedFeature(0.1F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.005F),
					WWTreePlacements.TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlacements.FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.08F),
					WWTreePlacements.DYING_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.024F),
					WWTreePlacements.DYING_FANCY_TALL_DARK_OAK.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.031F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.015F)
				),
				WWTreePlacements.DARK_OAK.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(DARK_TAIGA_VEGETATION_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				DARK_TAIGA_VEGETATION_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_TAIGA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.33333334F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.075F)
				),
				WWTreePlacements.SPRUCE.getHolder()
			)
		);

		TREES_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.075F)
				),
				WWTreePlacements.SPRUCE.getLitterVariantHolder()
			)
		);

		TREES_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREES_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		SHORT_TREES_TAIGA.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SHORT_SPRUCE.asHolderSet()));

		SHORT_TREES_TAIGA_LEAF_LITTER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SHORT_SPRUCE.litterAsHolderSet()));

		SHORT_MEGA_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_MEGA_FUNGUS_SPRUCE.asWeightedPlacedFeature(0.43333334F),
					WWTreePlacements.SHORT_MEGA_DYING_FUNGUS_SPRUCE.asWeightedPlacedFeature(0.125F),
					WWTreePlacements.SHORT_MEGA_DYING_SPRUCE.asWeightedPlacedFeature(0.125F)
				),
				WWTreePlacements.SHORT_MEGA_SPRUCE.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.43333334F),
					WWTreePlacements.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.125F),
					WWTreePlacements.SHORT_MEGA_DYING_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.125F)
				),
				WWTreePlacements.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MEGA_FUNGUS_SPRUCE.asWeightedPlacedFeature(0.025641026F),
					WWTreePlacements.DYING_MEGA_FUNGUS_PINE.asWeightedPlacedFeature(0.028F),
					WWTreePlacements.MEGA_FUNGUS_PINE.asWeightedPlacedFeature(0.30769232F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.045F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.SPRUCE.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MEGA_FUNGUS_SPRUCE.litterAsWeightedPlacedFeature(0.025641026F),
					WWTreePlacements.DYING_MEGA_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.028F),
					WWTreePlacements.MEGA_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.30769232F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.045F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.SPRUCE.getLitterVariantHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MEGA_FUNGUS_SPRUCE.asWeightedPlacedFeature(0.33333334F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.SPRUCE.getHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MEGA_FUNGUS_SPRUCE.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlacements.DYING_FUNGUS_PINE.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlacements.FUNGUS_PINE.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.SPRUCE.getLitterVariantHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MEGA_FUNGUS_PINE.asWeightedPlacedFeature(0.33333334F),
					WWTreePlacements.DYING_FUNGUS_PINE.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.SHORT_SPRUCE.asWeightedPlacedFeature(0.0255F),
					WWTreePlacements.FUNGUS_PINE.asWeightedPlacedFeature(0.18333334F),
					WWTreePlacements.MEGA_FUNGUS_SPRUCE.asWeightedPlacedFeature(0.255F)
				),
				WWTreePlacements.MEGA_FUNGUS_PINE.getHolder()
			)
		);

		TREES_GROVE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FUNGUS_PINE_ON_SNOW.asWeightedPlacedFeature(0.33333334F)),
				WWTreePlacements.SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.asWeightedPlacedFeature(0.666F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SPRUCE.litterAsWeightedPlacedFeature(0.666F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_WINDSWEPT_HILLS_LEAF_LITTER.asWeightedPlacedFeature(0.3F)),
				TREES_WINDSWEPT_HILLS_NO_LITTER.asInlinePlaced()
			)
		);

		MEADOW_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FANCY_OAK_BEES.asWeightedPlacedFeature(0.5F)),
				WWTreePlacements.SUPER_BIRCH_BEES.getHolder()
			)
		);

		SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		SAVANNA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SAVANNA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				SAVANNA_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SAVANNA_TREES_BAOBAB_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.asWeightedPlacedFeature(0.035F)
				),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		SAVANNA_TREES_BAOBAB_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlacements.BAOBAB.litterAsWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.litterAsWeightedPlacedFeature(0.035F)
				),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		SAVANNA_TREES_BAOBAB.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SAVANNA_TREES_BAOBAB_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				SAVANNA_TREES_BAOBAB_NO_LITTER.asInlinePlaced()
			)
		);

		SAVANNA_TREES_BAOBAB_VANILLA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.asWeightedPlacedFeature(0.035F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_CHECKED)
			)
		);

		WINDSWEPT_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				WINDSWEPT_SAVANNA_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		ARID_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.064F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.064F)
				),
				WWTreePlacements.ACACIA_LEAF_LITTER.getHolder()
			)
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(ARID_SAVANNA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				ARID_SAVANNA_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		ARID_SAVANNA_TREES_PALM_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.08F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.052F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_PALM_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.08F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.052F)
				),
				WWTreePlacements.ACACIA_LEAF_LITTER.getHolder()
			)
		);

		ARID_SAVANNA_TREES_PALM.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(ARID_SAVANNA_TREES_PALM_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				ARID_SAVANNA_TREES_PALM_NO_LITTER.asInlinePlaced()
			)
		);

		PARCHED_FOREST_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.59F),
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.186F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.155F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.37F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlacements.SHORT_BIRCH.asWeightedPlacedFeature(0.155F)
				),
				WWTreePlacements.OAK.getHolder()
			)
		);

		PARCHED_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.59F),
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.186F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlacements.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.155F)
				),
				WWTreePlacements.OAK.getLitterVariantHolder()
			)
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(PARCHED_FOREST_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.2F)),
				PARCHED_FOREST_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		ARID_FOREST_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DYING_OAK.asWeightedPlacedFeature(0.7085F),
					WWTreePlacements.FANCY_DYING_OAK.asWeightedPlacedFeature(0.175F),
					WWTreePlacements.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.38F),
					WWTreePlacements.DYING_BIRCH.asWeightedPlacedFeature(0.2325F)
				),
				WWTreePlacements.DYING_OAK.getHolder()
			)
		);

		ARID_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.DYING_OAK.litterAsWeightedPlacedFeature(0.7085F),
					WWTreePlacements.FANCY_DYING_OAK.litterAsWeightedPlacedFeature(0.175F),
					WWTreePlacements.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.38F),
					WWTreePlacements.DYING_BIRCH.litterAsWeightedPlacedFeature(0.2325F)
				),
				WWTreePlacements.DYING_OAK.getLitterVariantHolder()
			)
		);

		ARID_FOREST_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(ARID_FOREST_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.4F)),
				ARID_FOREST_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.CYPRESS.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.SHORT_CYPRESS.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.SWAMP_CYPRESS.asWeightedPlacedFeature(0.81F),
					WWTreePlacements.OAK.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.CYPRESS.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_CYPRESS.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.SWAMP_CYPRESS.asWeightedPlacedFeature(0.81F)
				),
				WWTreePlacements.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SWAMP_CYPRESS.asWeightedPlacedFeature(0.85F)),
				WWTreePlacements.SWAMP_CYPRESS.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.095F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.67F),
					WWTreePlacements.JUNIPER.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlacements.JUNIPER.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.095F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.67F),
					WWTreePlacements.JUNIPER.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlacements.JUNIPER.getLitterVariantHolder()
			)
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WOODED_BADLANDS_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.4F)),
				WOODED_BADLANDS_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SWAMP_TREES_NO_LITTER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SWAMP_OAK.asHolderSet()));

		SWAMP_TREES_LEAF_LITTER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SWAMP_OAK.litterAsHolderSet()));

		SWAMP_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SWAMP_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				SWAMP_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_WILLOW.asWeightedPlacedFeature(0.75F),
					WWTreePlacements.WILLOW.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlacements.SWAMP_OAK.getHolder()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SHORT_WILLOW.litterAsWeightedPlacedFeature(0.75F),
					WWTreePlacements.WILLOW.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlacements.SWAMP_OAK.getLitterVariantHolder()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER.asWeightedPlacedFeature(	0.1F)),
				SWAMP_TREES_SURFACE_WILLOW_NO_LITTER.asInlinePlaced()
			)
		);

		SWAMP_TREES_WATER_SHALLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.WILLOW.asWeightedPlacedFeature(0.75F)),
				WWTreePlacements.SHORT_WILLOW.getHolder()
			)
		);

		SWAMP_TREES_WATER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.TALL_WILLOW.asHolderSet()));

		LARGE_BUSHES_ON_SAND.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_BUSH_ON_SAND.asHolderSet()));

		BIG_BUSHES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.BIG_BUSH.asWeightedPlacedFeature(0.3F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlacements.LARGE_BUSH.getHolder()
			)
		);

		PALMS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.TALL_PALM.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM.getHolder()
			)
		);

		PALMS_JUNGLE_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM_DIRT.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.SHORT_WINDMILL_PALM_DIRT.asWeightedPlacedFeature(0.7F),
					WWTreePlacements.TALL_PALM_DIRT.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM_DIRT.getHolder()
			)
		);

		PALMS_JUNGLE_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.SHORT_WINDMILL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.7F),
					WWTreePlacements.TALL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM_DIRT_LEAF_LITTER.getHolder()
			)
		);

		PALMS_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(PALMS_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(	0.075F)),
				PALMS_JUNGLE_NO_LITTER.asInlinePlaced()
			)
		);

		PALMS_OASIS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_PALM.asWeightedPlacedFeature(0.5F),
					WWTreePlacements.TALL_WINDMILL_PALM.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.37F)
				),
				WWTreePlacements.PALM.getHolder()
			)
		);

		BAMBOO_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlacements.MEGA_JUNGLE.asWeightedPlacedFeature(0.7F)
				),
				PlacementUtils.inlinePlaced(
					features.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
					CountPlacement.of(32),
					OffsetPlacement.ofTriangle(7, 3),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.not(
								BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.PODZOL)
							)
						)
					)
				)
			)
		);

		BAMBOO_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlacements.MEGA_JUNGLE.litterAsWeightedPlacedFeature(0.7F)
				),
				PlacementUtils.inlinePlaced(
					features.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
					CountPlacement.of(32),
					OffsetPlacement.ofTriangle(7, 3),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.not(
								BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.PODZOL)
							)
						)
					)
				)
			)
		);

		BAMBOO_JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BAMBOO_JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				BAMBOO_JUNGLE_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SPARSE_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		SPARSE_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		SPARSE_JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SPARSE_JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(	0.05F)),
				PlacementUtils.inlinePlaced(SPARSE_JUNGLE_TREES_NO_LITTER.getHolder())
			)
		);

		JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlacements.MEGA_JUNGLE.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlacements.MEGA_JUNGLE.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				JUNGLE_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		MANGROVE_VEGETATION_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.TALL_MANGROVE.asWeightedPlacedFeature(0.85F)),
				WWTreePlacements.MANGROVE.getHolder()
			)
		);

		MANGROVE_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.TALL_MANGROVE.litterAsWeightedPlacedFeature(0.85F)),
				WWTreePlacements.MANGROVE.getLitterVariantHolder()
			)
		);

		MANGROVE_VEGETATION.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(MANGROVE_VEGETATION_LEAF_LITTER.asWeightedPlacedFeature(0.2F)),
				MANGROVE_VEGETATION_NO_LITTER.asInlinePlaced()
			)
		);

		CHERRIES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.CHERRY.asWeightedPlacedFeature(0.025F),
					WWTreePlacements.DYING_CHERRY.asWeightedPlacedFeature(0.0785F),
					WWTreePlacements.TALL_CHERRY_BEES_025.asWeightedPlacedFeature(0.37F),
					WWTreePlacements.TALL_DYING_CHERRY.asWeightedPlacedFeature(0.0785F)
				),
				WWTreePlacements.CHERRY_BEES_025.getHolder()
			)
		);

		// MAPLE
		MapleCollection.zipApply(COLORED_MAPLES, MapleCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new SequenceFeature(
					HolderSet.direct(
						PlacementUtils.inlinePlaced(
							new RandomSelectorFeature(
								List.of(
									WWTreePlacements.MAPLE.pick(color).asWeightedPlacedFeature(0.025F),
									WWTreePlacements.FULL_MAPLE.pick(color).asWeightedPlacedFeature(0.15F),
									WWTreePlacements.TALL_MAPLE.pick(color).asWeightedPlacedFeature(0.25F),
									WWTreePlacements.DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
									WWTreePlacements.TALL_MAPLE_BEES_0004.pick(color).asWeightedPlacedFeature(0.37F),
									WWTreePlacements.TALL_DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
									WWTreePlacements.MAPLE_BEES_0004.pick(color).asWeightedPlacedFeature(0.37F),
									WWTreePlacements.SHORT_MAPLE.pick(color).asWeightedPlacedFeature(0.2F),
									WWTreePlacements.BIG_BUSH_MAPLE.pick(color).asWeightedPlacedFeature(0.4F)
								),
								WWTreePlacements.MAPLE_BEES_0004.pick(color).getHolder()
							)
						),
						WWMiscConfigured.MAPLE_LEAF_LITTER.pick(color).asInlinePlaced()
					)
				)
			);
		});

		MAPLES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_MAPLES.yellow().asWeightedPlacedFeature(0.4F),
					COLORED_MAPLES.orange().asWeightedPlacedFeature(0.55F),
					COLORED_MAPLES.red().asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.MAPLE_BEES_0004.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLES_BEES_SAPLING, MapleCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.MAPLE.pick(color).asWeightedPlacedFeature(0.025F),
						WWTreePlacements.FULL_MAPLE.pick(color).asWeightedPlacedFeature(0.15F),
						WWTreePlacements.TALL_MAPLE.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_MAPLE_BEES_0004.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlacements.TALL_DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.SHORT_MAPLE.pick(color).asWeightedPlacedFeature(0.2F),
						WWTreePlacements.BIG_BUSH_MAPLE.pick(color).asWeightedPlacedFeature(0.4F)
					),
					WWTreePlacements.MAPLE_BEES_0004.pick(color).getHolder()
				)
			);
		});

		MAPLES_BEES_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_MAPLES_BEES_SAPLING.yellow().asWeightedPlacedFeature(0.4F),
					COLORED_MAPLES_BEES_SAPLING.orange().asWeightedPlacedFeature(0.55F),
					COLORED_MAPLES_BEES_SAPLING.red().asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.MAPLE_BEES_0004.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLES_NO_BEES, MapleCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.MAPLE.pick(color).asWeightedPlacedFeature(0.025F),
						WWTreePlacements.FULL_MAPLE.pick(color).asWeightedPlacedFeature(0.15F),
						WWTreePlacements.TALL_MAPLE.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_DYING_MAPLE.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.SHORT_MAPLE.pick(color).asWeightedPlacedFeature(0.2F),
						WWTreePlacements.BIG_BUSH_MAPLE.pick(color).asWeightedPlacedFeature(0.4F)
					),
					WWTreePlacements.MAPLE.pick(color).getHolder()
				)
			);
		});

		// POPLAR
		PoplarCollection.zipApply(COLORED_POPLARS_NO_LITTER, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.POPLAR.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlacements.TALL_POPLAR.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_POPLAR_BEES_0002.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlacements.TALL_DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlacements.POPLAR_BEES_0002.pick(color).getHolder()
				)
			);
		});

		POPLARS_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLARS_NO_LITTER.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLARS_NO_LITTER.orange().asWeightedPlacedFeature(0.45F)
				),
				COLORED_POPLARS_NO_LITTER.yellow().asInlinePlaced()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLARS_LEAF_LITTER, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.POPLAR.pick(color).litterAsWeightedPlacedFeature(0.45F),
						WWTreePlacements.TALL_POPLAR.pick(color).litterAsWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_POPLAR.pick(color).litterAsWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_POPLAR_BEES_0002.pick(color).litterAsWeightedPlacedFeature(0.37F),
						WWTreePlacements.TALL_DYING_POPLAR.pick(color).litterAsWeightedPlacedFeature(0.1F)
					),
					WWTreePlacements.POPLAR_BEES_0002.pick(color).getLitterVariantHolder()
				)
			);
		});

		POPLARS_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLARS_LEAF_LITTER.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLARS_LEAF_LITTER.orange().asWeightedPlacedFeature(0.45F)
				),
				COLORED_POPLARS_LEAF_LITTER.yellow().asInlinePlaced()
			)
		);

		POPLARS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(POPLARS_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				POPLARS_NO_LITTER.asInlinePlaced()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLARS_BEES_SAPLING, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.POPLAR.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlacements.TALL_POPLAR.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_POPLAR_BEES_0002.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlacements.TALL_DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlacements.POPLAR_BEES_0002.pick(color).getHolder()
				)
			);
		});

		PoplarCollection.zipApply(COLORED_POPLARS_NO_BEES_SAPLING, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlacements.POPLAR.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlacements.TALL_POPLAR.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlacements.DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlacements.TALL_DYING_POPLAR.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlacements.POPLAR.pick(color).getHolder()
				)
			);
		});

		PALE_OAKS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_PALE_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_TALL_PALE_OAK.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.COBWEB_TALL_PALE_OAK.asWeightedPlacedFeature(0.018F),
					WWTreePlacements.COBWEB_FANCY_PALE_OAK.asWeightedPlacedFeature(0.018F)
				),
				WWTreePlacements.PALE_OAK.getHolder()
			)
		);

		PALE_OAKS_CREAKING.makeAndSetHolder(

			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_PALE_OAK_CREAKING.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.FANCY_TALL_PALE_OAK_CREAKING.asWeightedPlacedFeature(0.075F),
					WWTreePlacements.COBWEB_TALL_PALE_OAK_CREAKING.asWeightedPlacedFeature(0.018F),
					WWTreePlacements.COBWEB_FANCY_PALE_OAK_CREAKING.asWeightedPlacedFeature(0.018F)
				),
				WWTreePlacements.PALE_OAK_CREAKING.getHolder()
			)
		);

		TREES_PALE_GARDEN.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					PALE_OAKS_CREAKING.asWeightedPlacedFeature(0.1F),
					PALE_OAKS.asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.PALE_OAK.getHolder()
			)
		);

		SNAPPED_BIRCHES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_BIRCH.asHolderSet()));

		SNAPPED_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_OAK.asHolderSet()));

		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.3F)),
				WWTreePlacements.SNAPPED_OAK.getHolder()
			)
		);

		SNAPPED_SPRUCES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_SPRUCE.asHolderSet()));

		SNAPPED_SPRUCES_ON_SNOW.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_SPRUCE_ON_SNOW.asHolderSet()));

		SNAPPED_LARGE_SPRUCES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_SNAPPED_SPRUCE.asHolderSet()));

		SNAPPED_LARGE_SPRUCES_ON_SNOW.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_SNAPPED_SPRUCE_ON_SNOW.asHolderSet()));

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.SNAPPED_SPRUCE.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.SNAPPED_OAK.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.5F)),
				WWTreePlacements.SNAPPED_SPRUCE.getHolder()
			)
		);

		SNAPPED_CYPRESSES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_CYPRESS.asHolderSet()));

		SNAPPED_JUNGLES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_JUNGLE.asHolderSet()));

		SNAPPED_LARGE_JUNGLES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_SNAPPED_JUNGLE.asHolderSet()));

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.SNAPPED_JUNGLE.getHolder()
			)
		);

		SNAPPED_ACACIAS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_ACACIA.asHolderSet()));

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_OAK.asWeightedPlacedFeature(0.3F)),
				WWTreePlacements.SNAPPED_ACACIA.getHolder()
			)
		);

		SNAPPED_CHERRY.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_CHERRY.asHolderSet()));

		SNAPPED_DARK_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_SNAPPED_DARK_OAK.asHolderSet()));

		SNAPPED_MAPLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_MAPLE.asHolderSet()));

		SNAPPED_POPLAR.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.SNAPPED_POPLAR.asHolderSet()));

		SNAPPED_PALE_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlacements.LARGE_SNAPPED_PALE_OAK.asHolderSet()));

		// LEAF LITTERS
		DARK_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.DARK_OAK_LEAF_LITTER.get(), 3))
		);

		PALE_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.PALE_OAK_LEAF_LITTER.get(), 3))
		);

		SPRUCE_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.SPRUCE_LEAF_LITTER.get(), 3))
		);

		// FLOWERS
		CLOVER.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(WWBlocks.CLOVERS.get())));

		PHLOX.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(WWBlocks.PHLOX.get())));

		LANTANAS.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(WWBlocks.LANTANAS.get())));

		WILDFLOWERS.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(Blocks.WILDFLOWERS)));

		WILDFLOWERS_AND_PHLOX.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					PHLOX.asWeightedPlacedFeature(
						0.3F,
						CountPlacement.of(30),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					)
				),
				WILDFLOWERS.asInlinePlaced(
					CountPlacement.of(30),
					OffsetPlacement.ofTriangle(6, 2),
					BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
				)
			)
		);

		WILDFLOWERS_AND_LANTANAS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					LANTANAS.asWeightedPlacedFeature(
						0.3F,
						CountPlacement.of(30),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					)
				),
				WILDFLOWERS.asInlinePlaced(
					CountPlacement.of(30),
					OffsetPlacement.ofTriangle(6, 2),
					BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
				)
			)
		);

		LANTANAS_AND_PHLOX.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					LANTANAS.asWeightedPlacedFeature(
						0.375F,
						CountPlacement.of(30),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					)
				),
				PHLOX.asInlinePlaced()
			)
		);

		SEEDING_DANDELION.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.SEEDING_DANDELION.get())));

		CARNATION.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.CARNATION.get())));

		MARIGOLD.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MARIGOLD.get())));

		PINK_TULIP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.PINK_TULIP)));

		ALLIUM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.ALLIUM)));

		DATURA.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.DATURA.get())));

		ROSE_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.ROSE_BUSH)));

		PEONY.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.PEONY)));

		LILAC.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.LILAC)));

		FLOWER_GENERIC.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.048833334F,
					List.of(
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_GENERIC_NO_CARNATION.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
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

		FLOWER_PLAINS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
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
						WWBlocks.CARNATION.get().defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState()
					)
				)
			)
		);

		FLOWER_SNOWY_PLAINS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.048833334F,
					List.of(
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState()
					)
				)
			)
		);

		FLOWER_TUNDRA.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.048833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState()
					)
				)
			)
		);

		FLOWER_DAPPLED_FOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.048833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState()
					)
				)
			)
		);

		FLOWER_BIRCH.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.048833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState()
					)
				)
			)
		);

		FLOWER_MEADOW.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.007833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		MILKWEED.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MILKWEED.get())));

		HIBISCUS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					1234L,
					SINGLE_OCTAVE_NOISE,
					0.088833334F,
					List.of(
						WWBlocks.RED_HIBISCUS.get().defaultBlockState(),
						WWBlocks.RED_HIBISCUS.get().defaultBlockState(),
						WWBlocks.YELLOW_HIBISCUS.get().defaultBlockState(),
						WWBlocks.WHITE_HIBISCUS.get().defaultBlockState(),
						WWBlocks.PINK_HIBISCUS.get().defaultBlockState(),
						WWBlocks.PURPLE_HIBISCUS.get().defaultBlockState(),
						WWBlocks.PURPLE_HIBISCUS.get().defaultBlockState()
					)
				)
			)
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(2345L,
					SINGLE_OCTAVE_NOISE, 0.016F,
					List.of(
						Blocks.DANDELION.defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						WWBlocks.PASQUEFLOWER.get().defaultBlockState()
					)
				)
			)
		);

		FLOWER_CYPRESS_WETLANDS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.043833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.WHITE_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.AZURE_BLUET.defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_CYPRESS_WETLANDS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.043833334F,
					List.of(
						WWBlocks.DATURA.get().defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.023833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.023833334F,
					List.of(
						WWBlocks.DATURA.get().defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
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

		TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.023833334F,
					List.of(
						Blocks.ROSE_BUSH.defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		PALE_MUSHROOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.PALE_MUSHROOM.get())));

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5234L,
					SINGLE_OCTAVE_NOISE,
					0.020833334F,
					List.of(
						Blocks.RED_MUSHROOM.defaultBlockState(),
						Blocks.BROWN_MUSHROOM.defaultBlockState()
					)
				)
			)
		);

		FLOWER_RAINFOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.034833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.OXEYE_DAISY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_RAINFOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.034833334F,
					List.of(
						WWBlocks.DATURA.get().defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.LILAC.defaultBlockState()
					)
				)
			)
		);

		FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
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

		TALL_FLOWER_RAINFOREST_VANILLA.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.034833334F,
					List.of(
						Blocks.ROSE_BUSH.defaultBlockState(),
						Blocks.LILAC.defaultBlockState(),
						Blocks.PEONY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_JUNGLE.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					1234L,
					SINGLE_OCTAVE_NOISE,
					0.054833334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_JUNGLE.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					1234L,
					SINGLE_OCTAVE_NOISE,
					0.054833334F,
					List.of(
						WWBlocks.DATURA.get().defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.ROSE_BUSH.defaultBlockState()
					)
				)
			)
		);

		FLOWER_SUNFLOWER_PLAINS.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.054833334F,
					List.of(
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						WWBlocks.MARIGOLD.get().defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState()
					)
				)
			)
		);

		FLOWER_FOREST_CLEARING.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5050L,
					SINGLE_OCTAVE_NOISE,
					0.054833334F,
					List.of(
						Blocks.WHITE_TULIP.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.SUNFLOWER.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.ORANGE_TULIP.defaultBlockState(),
						Blocks.RED_TULIP.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.PINK_TULIP.defaultBlockState(),
						WWBlocks.MILKWEED.get().defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.LILAC.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.PEONY.defaultBlockState(),
						Blocks.CORNFLOWER.defaultBlockState()
					)
				)
			)
		);

		TALL_FLOWER_FLOWER_FIELD.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.LILAC)),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MILKWEED.get())),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.ROSE_BUSH)),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.PEONY)),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					)
				)
			)
		);

		FLOWER_CHERRY.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.POPPY.defaultBlockState(), 9)
						.add(Blocks.PINK_TULIP.defaultBlockState(), 5)
						.build()
				)
			)
		);

		// VEGETATION
		MOSS_CARPET.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.MOSS_CARPET)));

		FROZEN_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_BUSH.get())));

		TAIGA_FROZEN_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_SHORT_GRASS.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_FERN.get().defaultBlockState(), 4)
						.build()
				)
			)
		);

		FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));

		FROZEN_LARGE_FERN.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_LARGE_FERN.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_FERN.get().defaultBlockState(), 2)
						.build()
				)
			)
		);

		FROZEN_TALL_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_TALL_GRASS.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_SHORT_GRASS.get().defaultBlockState(), 2)
						.build()
				)
			)
		);

		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));

		GRASS_OASIS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
						.build()
				)
			)
		);

		SHRUB_OASIS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_JUNGLE.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 5)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 5)
						.build()
				)
			)
		);

		SHRUB_SPARSE.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_FLOWER_FIELD.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 4)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 4)
						.build()
				)
			)
		);

		SHRUB_GENERIC.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_DESERT.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 3)
						.build()
				)
			)
		);

		CACTUS_OASIS.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(3, 5), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(4, 5), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL_BADLANDS.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(2, 6), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		PRICKLY_PEAR.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 2)
					.add(Blocks.CACTUS.defaultBlockState(), 3)
					.build()
				)
			)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
						.build()
				)
			)
		);

		LARGE_FERN_AND_GRASS_2.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 1)
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 4)
						.build()
				)
			)
		);

		TALL_GRASS_SWAMP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.TALL_GRASS)));

		FERN_SWAMP.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.FERN.defaultBlockState(), 4)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		FERN_AND_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
						.add(Blocks.FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		GRASS_AND_FERN.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 11)
						.add(Blocks.FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		MYCELIUM_GROWTH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MYCELIUM_GROWTH.get())));

		POLLEN.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.POLLEN.get(),
				10,
				true,
				true,
				true,
				0.5F,
				blocks.getOrThrow(WWFeatureBlockTags.POLLEN_PLACEABLE)
			)
		);

		TERMITE_MOUND.makeAndSetHolder(
			new ColumnWithDiskFeature(
				BlockStateProvider.holderOf(WWBlocks.TERMITE_MOUND.get().defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true)),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				blocks.getOrThrow(WWFeatureBlockTags.TERMITE_DISK_REPLACEABLE),
				Holder.direct(
					new WeightedStateProvider(
						WeightedList.<BlockState>builder()
							.add(Blocks.COARSE_DIRT.defaultBlockState(), 2)
							.add(Blocks.PACKED_MUD.defaultBlockState(), 1)
							.build()
					)
				)
			)
		);

		TUMBLEWEED.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1)
						.build()
				)
			)
		);
	}
}
