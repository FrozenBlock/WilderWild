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
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWTreePlaced;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Direction;
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
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS = PoplarCollection.NAMES.map(name -> register(name + "_poplars"));
	public static final FrozenLibFeature POPLARS = register("poplars");
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_NO_BEES = PoplarCollection.NAMES.map(name -> register(name + "_poplars_no_bees"));
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLARS_BEES_SAPLING = PoplarCollection.NAMES.map(name -> register(name + "_poplars_bees_sapling"));
	public static final FrozenLibFeature POPLARS_BEES_SAPLING = register("poplars_bees_sapling");

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
					WWTreePlaced.FALLEN_SPRUCE_CHECKED.asWeightedPlacedFeature(0.4F),
					WWTreePlaced.FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.3F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_MIXED.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED.asWeightedPlacedFeature(0.15F),
					WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.asWeightedPlacedFeature(0.15F),
					WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.15F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FALLEN_SPRUCE_CHECKED.asWeightedPlacedFeature(0.6F),
					WWTreePlaced.FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.asHolderSet()));

		FALLEN_CHERRY.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FALLEN_CHERRY_CHECKED.asWeightedPlacedFeature(0.6F),
					WWTreePlaced.MOSSY_FALLEN_CHERRY_CHECKED.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlaced.FALLEN_CHERRY_CHECKED.getHolder()
			)
		);

		FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.asHolderSet()));

		CLEAN_FALLEN_SPRUCE.makeAndSetHolder(
			new SimpleRandomSelectorFeature(WWTreePlaced.CLEAN_FALLEN_SPRUCE_CHECKED.asHolderSet())
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.asHolderSet()));

		FALLEN_SWAMP_TREES_WILLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.asWeightedPlacedFeature(0.75F),
					WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		DECORATED_LARGE_FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.asHolderSet()));

		CLEAN_LARGE_FALLEN_SPRUCE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.asHolderSet()));

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.FALLEN_SPRUCE_CHECKED.asWeightedPlacedFeature(0.55F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.35F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.FALLEN_OAK_CHECKED.asWeightedPlacedFeature(0.35F)),
				WWTreePlaced.FALLEN_CYPRESS_CHECKED.getHolder()
			)
		);

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.FALLEN_ACACIA_CHECKED.asWeightedPlacedFeature(0.7F)),
				WWTreePlaced.FALLEN_OAK_NO_MOSS_CHECKED.getHolder()
			)
		);

		FALLEN_LARGE_JUNGLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_FALLEN_JUNGLE_CHECKED.asHolderSet()));

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FALLEN_PALM_CHECKED.asWeightedPlacedFeature(0.135F),
					WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.asWeightedPlacedFeature(0.25F)),
				WWTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_PALE_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_FALLEN_PALE_OAK_CHECKED.asHolderSet()));

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FALLEN_BIRCH_CHECKED.asWeightedPlacedFeature(0.135F),
					WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_MANGROVE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.FALLEN_MANGROVE_CHECKED.asHolderSet()));

		FALLEN_DARK_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_FALLEN_DARK_OAK_CHECKED.asHolderSet()));

		FALLEN_MAPLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.FALLEN_MAPLE_CHECKED.asHolderSet()));

		FALLEN_POPLAR.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.FALLEN_POPLAR_CHECKED.asHolderSet()));

		TREES_PLAINS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.BIG_BUSH_CHECKED.asWeightedPlacedFeature(0.35F),
					WWTreePlaced.LARGE_BUSH_CHECKED.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlaced.LARGE_BUSH_CHECKED.getHolder()
			)
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FANCY_OAK_BEES_025.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.FANCY_DYING_OAK_BEES_025.asWeightedPlacedFeature(0.09F),
					WWTreePlaced.BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.MEDIUM_BIRCH_BEES_025.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.LARGE_BUSH_CHECKED.asWeightedPlacedFeature(0.5F),
					WWTreePlaced.BIG_BUSH_CHECKED.asWeightedPlacedFeature(0.3F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.169F)
				),
				WWTreeConfigured.OAK_BEES_0004.asInlinePlaced()
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
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.055F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.04F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.115F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.055F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.04F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.115F)
				),
				WWTreePlaced.OAK_BEES_0004.getLitterVariantHolder()
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

		TREES_DYING_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_DYING_MIXED_FOREST_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_DYING_MIXED_FOREST_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_DYING_MIXED_FOREST_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(TREES_DYING_MIXED_FOREST_NO_LITTER.getHolder())
			)
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.getHolder())
			)
		);

		TREES_BIRCH.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_BIRCH_TALL_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_BIRCH_TALL_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_BIRCH_TALL.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_BIRCH_TALL_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_BIRCH_TALL_NO_LITTER.getHolder())
			)
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SHORT_BIRCH_BEES_0004.asWeightedPlacedFeature(0.2F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.035F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.05F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.063F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.195F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.095F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		MIXED_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.33F),
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.29F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.12F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.23F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.325F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		MIXED_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SPRUCE_SHORT_CHECKED.litterAsWeightedPlacedFeature(0.33F),
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.29F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.086F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.12F),
					WWTreePlaced.DYING_FANCY_OAK_BEES_0004.litterAsWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.23F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.325F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
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
					WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.6F)
				),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.MEDIUM_BIRCH.asWeightedPlacedFeature(0.19F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		RAINFOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
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
					WWTreePlaced.MEDIUM_BIRCH.litterAsWeightedPlacedFeature(0.19F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getLitterVariantHolder()
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

		BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		BIRCH_TAIGA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_TAIGA_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(BIRCH_TAIGA_TREES_NO_LITTER.getHolder())
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
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
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.BIRCH_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.BIRCH_CHECKED.getLitterVariantHolder()
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

		BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		BIRCH_JUNGLE_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_JUNGLE_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				BIRCH_JUNGLE_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		SPARSE_BIRCH_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.185F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
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
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.185F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
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

		OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
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
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.15F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
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
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.15F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.015F)
				),
				WWTreePlaced.DARK_OAK_CHECKED.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
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
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.015F)
				),
				WWTreePlaced.DARK_OAK_CHECKED.getHolder()
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
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.075F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getLitterVariantHolder()
			)
		);

		TREES_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREES_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		SHORT_TREES_TAIGA.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.asHolderSet()));

		SHORT_MEGA_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.43333334F),
					WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.125F),
					WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED.asWeightedPlacedFeature(0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.43333334F),
					WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.125F),
					WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW.asWeightedPlacedFeature(0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
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

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREES_OLD_GROWTH_PINE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.075F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.33333334F),
					WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.SPRUCE_SHORT_CHECKED.asWeightedPlacedFeature(0.0255F),
					WWTreePlaced.FUNGUS_PINE_CHECKED.asWeightedPlacedFeature(0.18333334F),
					WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.asWeightedPlacedFeature(0.255F)
				),
				WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TREES_GROVE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.FUNGUS_PINE_ON_SNOW.asWeightedPlacedFeature(0.33333334F)),
				WWTreePlaced.SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.asWeightedPlacedFeature(0.666F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SPRUCE_CHECKED.litterAsWeightedPlacedFeature(0.666F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
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
				List.of(WWTreePlaced.FANCY_OAK_BEES.asWeightedPlacedFeature(0.5F)),
				WWTreePlaced.SUPER_BIRCH_BEES.getHolder()
			)
		);

		SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.asWeightedPlacedFeature(0.035F)
				),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		SAVANNA_TREES_BAOBAB_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlaced.BAOBAB.litterAsWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.litterAsWeightedPlacedFeature(0.035F)
				),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlaced.BAOBAB_TALL.asWeightedPlacedFeature(0.035F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_CHECKED)
			)
		);

		WINDSWEPT_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getLitterVariantHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WINDSWEPT_SAVANNA_TREES_LEAF_LITTER.asWeightedPlacedFeature(	0.75F)),
				WINDSWEPT_SAVANNA_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		ARID_SAVANNA_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.08F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.08F)
				),
				WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder()
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
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.08F),
					WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.asWeightedPlacedFeature(0.052F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		ARID_SAVANNA_TREES_PALM_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.08F),
					WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.asWeightedPlacedFeature(0.052F)
				),
				WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.getHolder()
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
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.59F),
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.186F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.155F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.37F),
					WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.01F),
					WWTreePlaced.SHORT_BIRCH.asWeightedPlacedFeature(0.155F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		PARCHED_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.59F),
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.186F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.02F),
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.155F),
					WWTreePlaced.ACACIA_CHECKED_LEAF_LITTER.asWeightedPlacedFeature(0.37F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.01F),
					WWTreePlaced.SHORT_BIRCH.litterAsWeightedPlacedFeature(0.155F)
				),
				WWTreePlaced.OAK_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.DYING_OAK_CHECKED.asWeightedPlacedFeature(0.7085F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.asWeightedPlacedFeature(0.175F),
					WWTreePlaced.DYING_SHORT_BIRCH.asWeightedPlacedFeature(0.38F),
					WWTreePlaced.DYING_BIRCH.asWeightedPlacedFeature(0.2325F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		ARID_FOREST_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.7085F),
					WWTreePlaced.FANCY_DYING_OAK_CHECKED.litterAsWeightedPlacedFeature(0.175F),
					WWTreePlaced.DYING_SHORT_BIRCH.litterAsWeightedPlacedFeature(0.38F),
					WWTreePlaced.DYING_BIRCH.litterAsWeightedPlacedFeature(0.2325F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.CYPRESS.asWeightedPlacedFeature(0.37F),
					WWTreePlaced.SHORT_CYPRESS.asWeightedPlacedFeature(0.25F),
					WWTreePlaced.SWAMP_CYPRESS.asWeightedPlacedFeature(0.81F),
					WWTreePlaced.OAK_CHECKED.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.CYPRESS.asWeightedPlacedFeature(0.4F),
					WWTreePlaced.SHORT_CYPRESS.asWeightedPlacedFeature(0.15F),
					WWTreePlaced.SWAMP_CYPRESS.asWeightedPlacedFeature(0.81F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.SWAMP_CYPRESS.asWeightedPlacedFeature(0.85F)),
				WWTreePlaced.SWAMP_CYPRESS.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.asWeightedPlacedFeature(0.095F),
					WWTreePlaced.LARGE_BUSH_CHECKED.asWeightedPlacedFeature(0.4F),
					WWTreePlaced.SHORT_OAK_CHECKED.asWeightedPlacedFeature(0.67F),
					WWTreePlaced.JUNIPER.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.JUNIPER.getHolder()
			)
		);

		WOODED_BADLANDS_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.OAK_NO_FUNGI_CHECKED.litterAsWeightedPlacedFeature(0.095F),
					WWTreePlaced.LARGE_BUSH_CHECKED.asWeightedPlacedFeature(0.4F),
					WWTreePlaced.SHORT_OAK_CHECKED.litterAsWeightedPlacedFeature(0.67F),
					WWTreePlaced.JUNIPER.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.JUNIPER.getLitterVariantHolder()
			)
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WOODED_BADLANDS_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.4F)),
				WOODED_BADLANDS_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SWAMP_TREES_NO_LITTER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SWAMP_OAK_CHECKED.asHolderSet()));

		SWAMP_TREES_LEAF_LITTER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SWAMP_OAK_CHECKED.litterAsHolderSet()));

		SWAMP_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(SWAMP_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				SWAMP_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.WILLOW_CHECKED.asWeightedPlacedFeature(0.75F),
					WWTreePlaced.WILLOW_TALL_CHECKED.asWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.SWAMP_OAK_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_SURFACE_WILLOW_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.WILLOW_CHECKED.litterAsWeightedPlacedFeature(0.75F),
					WWTreePlaced.WILLOW_TALL_CHECKED.litterAsWeightedPlacedFeature(0.2F)
				),
				WWTreePlaced.SWAMP_OAK_CHECKED.getLitterVariantHolder()
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
				List.of(WWTreePlaced.WILLOW_TALL_CHECKED.asWeightedPlacedFeature(0.75F)),
				WWTreePlaced.WILLOW_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_WATER.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.WILLOW_TALLER_CHECKED.asHolderSet()));

		LARGE_BUSHES_ON_SAND.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_BUSH_ON_SAND.asHolderSet()));

		BIG_BUSHES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.BIG_BUSH_CHECKED.asWeightedPlacedFeature(0.3F),
					WWTreePlaced.LARGE_BUSH_CHECKED.asWeightedPlacedFeature(0.6F)
				),
				WWTreePlaced.LARGE_BUSH_CHECKED.getHolder()
			)
		);

		PALMS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.TALL_PALM_CHECKED.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		PALMS_JUNGLE_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.TALL_WINE_PALM_CHECKED_DIRT.asWeightedPlacedFeature(0.25F),
					WWTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT.asWeightedPlacedFeature(0.7F),
					WWTreePlaced.TALL_PALM_CHECKED_DIRT.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlaced.PALM_CHECKED_DIRT.getHolder()
			)
		);

		PALMS_JUNGLE_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.TALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.25F),
					WWTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.7F),
					WWTreePlaced.TALL_PALM_CHECKED_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlaced.PALM_CHECKED_DIRT_LEAF_LITTER.getHolder()
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
					WWTreePlaced.TALL_PALM_CHECKED.asWeightedPlacedFeature(0.5F),
					WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.asWeightedPlacedFeature(0.1F),
					WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.asWeightedPlacedFeature(0.37F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		BAMBOO_JUNGLE_TREES_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.asWeightedPlacedFeature(0.7F)
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
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.7F)
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
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		SPARSE_JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.FANCY_OAK_CHECKED.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		JUNGLE_TREES_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.FANCY_OAK_CHECKED.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getLitterVariantHolder()
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
				List.of(WWTreePlaced.TALL_MANGROVE_CHECKED.asWeightedPlacedFeature(0.85F)),
				WWTreePlaced.MANGROVE_CHECKED.getHolder()
			)
		);

		MANGROVE_VEGETATION_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.TALL_MANGROVE_CHECKED.litterAsWeightedPlacedFeature(0.85F)),
				WWTreePlaced.MANGROVE_CHECKED.getLitterVariantHolder()
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
					WWTreePlaced.CHERRY_CHECKED.asWeightedPlacedFeature(0.025F),
					WWTreePlaced.DYING_CHERRY_CHECKED.asWeightedPlacedFeature(0.0785F),
					WWTreePlaced.TALL_CHERRY_BEES_CHECKED.asWeightedPlacedFeature(0.37F),
					WWTreePlaced.TALL_DYING_CHERRY_CHECKED.asWeightedPlacedFeature(0.0785F)
				),
				WWTreePlaced.CHERRY_BEES_CHECKED.getHolder()
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
									WWTreePlaced.MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.025F),
									WWTreePlaced.FULL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.15F),
									WWTreePlaced.TALL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
									WWTreePlaced.DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
									WWTreePlaced.TALL_MAPLE_BEES_CHECKED.pick(color).asWeightedPlacedFeature(0.37F),
									WWTreePlaced.TALL_DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
									WWTreePlaced.MAPLE_BEES_CHECKED.pick(color).asWeightedPlacedFeature(0.37F),
									WWTreePlaced.SHORT_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.2F),
									WWTreePlaced.BIG_BUSH_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.4F)
								),
								WWTreePlaced.MAPLE_BEES_CHECKED.pick(color).getHolder()
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
				WWTreePlaced.MAPLE_BEES_CHECKED.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLES_BEES_SAPLING, MapleCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlaced.MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.025F),
						WWTreePlaced.FULL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.15F),
						WWTreePlaced.TALL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlaced.DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.TALL_MAPLE_BEES_CHECKED.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlaced.TALL_DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.SHORT_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.2F),
						WWTreePlaced.BIG_BUSH_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.4F)
					),
					WWTreePlaced.MAPLE_BEES_CHECKED.pick(color).getHolder()
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
				WWTreePlaced.MAPLE_BEES_CHECKED.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLES_NO_BEES, MapleCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlaced.MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.025F),
						WWTreePlaced.FULL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.15F),
						WWTreePlaced.TALL_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlaced.DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.TALL_DYING_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.SHORT_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.2F),
						WWTreePlaced.BIG_BUSH_MAPLE_CHECKED.pick(color).asWeightedPlacedFeature(0.4F)
					),
					WWTreePlaced.MAPLE_CHECKED.pick(color).getHolder()
				)
			);
		});

		// POPLAR
		PoplarCollection.zipApply(COLORED_POPLARS, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlaced.POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlaced.TALL_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlaced.DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.TALL_POPLAR_BEES_CHECKED.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlaced.TALL_DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlaced.POPLAR_BEES_CHECKED.pick(color).getHolder()
				)
			);
		});

		POPLARS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLARS.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLARS.orange().asWeightedPlacedFeature(0.45F)
				),
				COLORED_POPLARS.yellow().asInlinePlaced()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLARS_BEES_SAPLING, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlaced.POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlaced.TALL_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlaced.DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.TALL_POPLAR_BEES_CHECKED.pick(color).asWeightedPlacedFeature(0.37F),
						WWTreePlaced.TALL_DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlaced.POPLAR_BEES_CHECKED.pick(color).getHolder()
				)
			);
		});

		POPLARS_BEES_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLARS_BEES_SAPLING.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLARS_BEES_SAPLING.orange().asWeightedPlacedFeature(0.45F)
				),
				WWTreePlaced.POPLAR_BEES_CHECKED.yellow().getHolder()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLARS_NO_BEES, PoplarCollection.DYE_COLORS, (feature, color) -> {
			feature.makeAndSetHolder(
				new RandomSelectorFeature(
					List.of(
						WWTreePlaced.POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.45F),
						WWTreePlaced.TALL_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.25F),
						WWTreePlaced.DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.0785F),
						WWTreePlaced.TALL_DYING_POPLAR_CHECKED.pick(color).asWeightedPlacedFeature(0.1F)
					),
					WWTreePlaced.POPLAR_CHECKED.pick(color).getHolder()
				)
			);
		});

		PALE_OAKS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.TALL_PALE_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_TALL_PALE_OAK_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.COBWEB_TALL_PALE_OAK_CHECKED.asWeightedPlacedFeature(0.018F),
					WWTreePlaced.COBWEB_FANCY_PALE_OAK_CHECKED.asWeightedPlacedFeature(0.018F)
				),
				WWTreePlaced.PALE_OAK_CHECKED.getHolder()
			)
		);

		PALE_OAKS_CREAKING.makeAndSetHolder(

			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.TALL_PALE_OAK_CREAKING_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.FANCY_TALL_PALE_OAK_CREAKING_CHECKED.asWeightedPlacedFeature(0.075F),
					WWTreePlaced.COBWEB_TALL_PALE_OAK_CREAKING_CHECKED.asWeightedPlacedFeature(0.018F),
					WWTreePlaced.COBWEB_FANCY_PALE_OAK_CREAKING_CHECKED.asWeightedPlacedFeature(0.018F)
				),
				WWTreePlaced.PALE_OAK_CREAKING_CHECKED.getHolder()
			)
		);

		TREES_PALE_GARDEN.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					PALE_OAKS_CREAKING.asWeightedPlacedFeature(0.1F),
					PALE_OAKS.asWeightedPlacedFeature(0.9F)
				),
				WWTreePlaced.PALE_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCHES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.asHolderSet()));

		SNAPPED_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_OAK_CHECKED.asHolderSet()));

		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.SNAPPED_BIRCH_CHECKED.asWeightedPlacedFeature(0.3F)),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_SPRUCES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_SPRUCE_CHECKED.asHolderSet()));

		SNAPPED_SPRUCES_ON_SNOW.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_SPRUCE_ON_SNOW.asHolderSet()));

		SNAPPED_LARGE_SPRUCES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_SNAPPED_SPRUCE_CHECKED.asHolderSet()));

		SNAPPED_LARGE_SPRUCES_ON_SNOW.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.asHolderSet()));

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlaced.SNAPPED_BIRCH_CHECKED.asWeightedPlacedFeature(0.15F),
					WWTreePlaced.SNAPPED_SPRUCE_CHECKED.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.SNAPPED_BIRCH_CHECKED.asWeightedPlacedFeature(0.5F)),
				WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_CYPRESSES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_CYPRESS_CHECKED.asHolderSet()));

		SNAPPED_JUNGLES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_JUNGLE_CHECKED.asHolderSet()));

		SNAPPED_LARGE_JUNGLES.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_SNAPPED_JUNGLE_CHECKED.asHolderSet()));

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.SNAPPED_BIRCH_CHECKED.asWeightedPlacedFeature(0.35F)),
				WWTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_ACACIAS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_ACACIA_CHECKED.asHolderSet()));

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlaced.SNAPPED_OAK_CHECKED.asWeightedPlacedFeature(0.3F)),
				WWTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()
			)
		);

		SNAPPED_CHERRY.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_CHERRY_CHECKED.asHolderSet()));

		SNAPPED_DARK_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_SNAPPED_DARK_OAK_CHECKED.asHolderSet()));

		SNAPPED_MAPLE.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_MAPLE_CHECKED.asHolderSet()));

		SNAPPED_POPLAR.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.SNAPPED_POPLAR_CHECKED.asHolderSet()));

		SNAPPED_PALE_OAKS.makeAndSetHolder(new SimpleRandomSelectorFeature(WWTreePlaced.LARGE_SNAPPED_PALE_OAK_CHECKED.asHolderSet()));

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

		SEEDING_DANDELION.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.SEEDING_DANDELION.get())));

		CARNATION.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.CARNATION.get())));

		MARIGOLD.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.MARIGOLD.get())));

		PINK_TULIP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.PINK_TULIP)));

		ALLIUM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.ALLIUM)));

		DATURA.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.DATURA.get())));

		ROSE_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.ROSE_BUSH)));

		PEONY.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.PEONY)));

		LILAC.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.LILAC)));

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

		MILKWEED.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.MILKWEED.get())));

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

		PALE_MUSHROOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.PALE_MUSHROOM.get())));

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
						new SimpleBlockFeature(BlockStateProvider.simple(Blocks.LILAC)),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.MILKWEED.get())),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.simple(Blocks.ROSE_BUSH)),
						CountPlacement.of(9),
						OffsetPlacement.ofTriangle(7, 3),
						BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(BlockStateProvider.simple(Blocks.PEONY)),
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
		MOSS_CARPET.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.MOSS_CARPET)));

		FROZEN_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.FROZEN_BUSH.get())));

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

		FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS.get())));

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

		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.FROZEN_SHORT_GRASS.get())));

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
					BlockColumnFeature.layer(BiasedToBottomInt.of(3, 5), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
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
					BlockColumnFeature.layer(BiasedToBottomInt.of(4, 5), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
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
					BlockColumnFeature.layer(BiasedToBottomInt.of(2, 6), BlockStateProvider.simple(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.simple(Blocks.CACTUS_FLOWER)
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

		TALL_GRASS_SWAMP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(Blocks.TALL_GRASS)));

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

		MYCELIUM_GROWTH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.MYCELIUM_GROWTH.get())));

		POLLEN.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.POLLEN.get(),
				10,
				true,
				true,
				true,
				0.5F,
				blocks.getOrThrow(WWBlockTags.POLLEN_FEATURE_PLACEABLE)
			)
		);

		TERMITE_MOUND.makeAndSetHolder(
			new ColumnWithDiskFeature(
				BlockStateProvider.simple(WWBlocks.TERMITE_MOUND.get().defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true)),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				blocks.getOrThrow(WWBlockTags.TERMITE_DISK_REPLACEABLE),
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.COARSE_DIRT.defaultBlockState(), 2)
						.add(Blocks.PACKED_MUD.defaultBlockState(), 1)
						.build()
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
