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

package net.frozenblock.wilderwild.data.worldgen.features;

import com.mojang.datafixers.util.Pair;
import java.util.List;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedTreeFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.impl.MapleCollection;
import net.frozenblock.wilderwild.block.impl.PoplarCollection;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWMiscConfigured;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreePlacements;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWTreeVegetationFeatures {
	// PLAINS
	public static final FrozenLibFeature TREE_PLAINS = register("tree_plains");
	public static final FrozenLibFeature TREE_FLOWER_FIELD = register("tree_flower_field");

	// FOREST
	public static final FrozenLibFeature BIRCH_AND_OAK_ORIGINAL_NO_LITTER = register("birch_and_oak_original_no_litter");
	public static final FrozenLibFeature BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER = register("birch_and_oak_original_leaf_litter");
	public static final FrozenLibFeature BIRCH_AND_OAK_ORIGINAL = register("birch_and_oak_original");
	public static final FrozenLibFeature BIRCH_AND_OAK_NO_LITTER = register("birch_and_oak_no_litter");
	public static final FrozenLibFeature BIRCH_AND_OAK_LEAF_LITTER = register("birch_and_oak_leaf_litter");
	public static final FrozenLibFeature BIRCH_AND_OAK = register("birch_and_oak");
	public static final FrozenLibFeature BIRCH_AND_OAK_CALM = register("birch_and_oak_calm");
	public static final FrozenLibFeature TREE_MIXED_FOREST_NO_LITTER = register("tree_mixed_forest_no_litter");
	public static final FrozenLibFeature TREE_MIXED_FOREST_LEAF_LITTER = register("tree_mixed_forest_leaf_litter");
	public static final FrozenLibFeature TREE_MIXED_FOREST = register("tree_mixed_forest");
	public static final FrozenLibFeature TREE_RAINFOREST_NO_LITTER = register("tree_rainforest_no_litter");
	public static final FrozenLibFeature TREE_RAINFOREST_LEAF_LITTER = register("tree_rainforest_leaf_litter");
	public static final FrozenLibFeature TREE_RAINFOREST = register("tree_rainforest");
	public static final FrozenLibFeature TREE_FLOWER_FOREST = register("tree_flower_forest");
	public static final FrozenLibFeature TREE_DYING_FOREST = register("tree_dying_forest");
	public static final FrozenLibFeature TREE_DYING_MIXED_FOREST_NO_LITTER = register("tree_dying_mixed_forest_no_litter");
	public static final FrozenLibFeature TREE_DYING_MIXED_FOREST_LEAF_LITTER = register("tree_dying_mixed_forest_leaf_litter");
	public static final FrozenLibFeature TREE_DYING_MIXED_FOREST = register("tree_dying_mixed_forest");
	public static final FrozenLibFeature TREE_SNOWY_DYING_FOREST = register("tree_snowy_dying_forest");
	public static final FrozenLibFeature TREE_SNOWY_DYING_MIXED_FOREST = register("tree_snowy_dying_mixed_forest");
	public static final FrozenLibFeature PARCHED_FOREST_TREES_NO_LITTER = register("tree_parched_forest_no_litter");
	public static final FrozenLibFeature PARCHED_FOREST_TREES_LEAF_LITTER = register("tree_parched_forest_leaf_litter");
	public static final FrozenLibFeature PARCHED_FOREST_TREES = register("tree_parched_forest");
	public static final FrozenLibFeature ARID_FOREST_TREES_NO_LITTER = register("tree_arid_forest_no_litter");
	public static final FrozenLibFeature ARID_FOREST_TREES_LEAF_LITTER = register("tree_arid_forest_leaf_litter");
	public static final FrozenLibFeature ARID_FOREST_TREES = register("tree_arid_forest");

	// BIRCH FOREST
	public static final FrozenLibFeature TREES_BIRCH = register("tree_birch");
	public static final FrozenLibFeature TREES_BIRCH_TALL_NO_LITTER = register("tree_birch_tall_no_litter");
	public static final FrozenLibFeature TREES_BIRCH_TALL_LEAF_LITTER = register("tree_birch_tall_leaf_litter");
	public static final FrozenLibFeature TREES_BIRCH_TALL = register("tree_birch_tall");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK_NO_LITTER = register("tree_semi_birch_and_oak_no_litter");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER = register("tree_semi_birch_and_oak_leaf_litter");
	public static final FrozenLibFeature TREES_SEMI_BIRCH_AND_OAK = register("tree_semi_birch_and_oak");
	public static final FrozenLibFeature TREE_BIRCH_JUNGLE_NO_LITTER = register("tree_birch_jungle_no_litter");
	public static final FrozenLibFeature TREE_BIRCH_JUNGLE_LEAF_LITTER = register("tree_birch_jungle_leaf_litter");
	public static final FrozenLibFeature BTREE_BIRCH_JUNGLE = register("btree_birch_jungle");
	public static final FrozenLibFeature TREE_SPARSE_BIRCH_JUNGLE_NO_LITTER = register("tree_sparse_birch_jungle_no_litter");
	public static final FrozenLibFeature TREE_SPARSE_BIRCH_JUNGLE_LEAF_LITTER = register("tree_sparse_birch_jungle_leaf_litter");
	public static final FrozenLibFeature TREE_SPARSE_BIRCH_JUNGLE = register("tree_sparse_birch_jungle");
	public static final FrozenLibFeature TREE_BIRCH_TAIGA_NO_LITTER = register("tree_birch_taiga_no_litter");
	public static final FrozenLibFeature TREE_BIRCH_TAIGA_LEAF_LITTER = register("tree_birch_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_BIRCH_TAIGA = register("tree_birch_taiga");
	public static final FrozenLibFeature TREE_OLD_GROWTH_BIRCH_TAIGA_NO_LITTER = register("tree_old_growth_birch_taiga_no_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_BIRCH_TAIGA_LEAF_LITTER = register("tree_old_growth_birch_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_BIRCH_TAIGA = register("tree_old_growth_birch_taiga");

	// DARK FOREST
	public static final FrozenLibFeature DARK_FOREST_VEGETATION_NO_LITTER = register("dark_forest_vegetation_no_litter");
	public static final FrozenLibFeature DARK_FOREST_VEGETATION_LEAF_LITTER = register("dark_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION_NO_LITTER = register("old_growth_dark_forest_vegetation_no_litter");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION_LEAF_LITTER = register("old_growth_dark_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION_NO_LITTER = register("dark_birch_forest_vegetation_no_litter");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION_LEAF_LITTER = register("dark_birch_forest_vegetation_leaf_litter");
	public static final FrozenLibFeature DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenLibFeature TREE_DARK_TAIGA_NO_LITTER = register("tree_dark_taiga_no_litter");
	public static final FrozenLibFeature TREE_DARK_TAIGA_LEAF_LITTER = register("tree_dark_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_DARK_TAIGA = register("tree_dark_taiga");

	// PALE GARDEN
	public static final FrozenLibFeature PALE_OAK = register("pale_oak");
	public static final FrozenLibFeature PALE_OAK_CREAKING = register("pale_oak_creaking");
	public static final FrozenLibFeature TREE_PALE_GARDEN = register("tree_pale_garden");

	// JUNGLE
	public static final FrozenLibFeature TREE_JUNGLE_NO_LITTER = register("tree_jungle_no_litter");
	public static final FrozenLibFeature TREE_JUNGLE_LEAF_LITTER = register("tree_jungle_leaf_litter");
	public static final FrozenLibFeature TREE_JUNGLE = register("tree_jungle");
	public static final FrozenLibFeature TREE_SPARSE_JUNGLE_NO_LITTER = register("tree_sparse_jungle_no_litter");
	public static final FrozenLibFeature TREE_SPARSE_JUNGLE_LEAF_LITTER = register("tree_sparse_jungle_leaf_litter");
	public static final FrozenLibFeature TREE_SPARSE_JUNGLE = register("tree_sparse_jungle");
	public static final FrozenLibFeature TREE_BAMBOO_JUNGLE_NO_LITTER = register("tree_bamboo_jungle_no_litter");
	public static final FrozenLibFeature TREE_BAMBOO_JUNGLE_LEAF_LITTER = register("tree_bamboo_jungle_leaf_litter");
	public static final FrozenLibFeature TREE_BAMBOO_JUNGLE = register("tree_bamboo_jungle");
	public static final FrozenLibFeature PALM_JUNGLE_NO_LITTER = register("palm_jungle_no_litter");
	public static final FrozenLibFeature PALM_JUNGLE_LEAF_LITTER = register("palm_jungle_leaf_litter");
	public static final FrozenLibFeature PALM_JUNGLE = register("palm_jungle");

	// SAVANNA
	public static final FrozenLibFeature TREE_SAVANNA_NO_LITTER = register("tree_savanna_no_litter");
	public static final FrozenLibFeature TREE_SAVANNA_LEAF_LITTER = register("tree_savanna_leaf_litter");
	public static final FrozenLibFeature TREE_SAVANNA = register("tree_savanna");
	public static final FrozenLibFeature TREE_SAVANNA_BAOBAB_NO_LITTER = register("tree_savanna_baobab_no_litter");
	public static final FrozenLibFeature TREE_SAVANNA_BAOBAB_LEAF_LITER = register("tree_savanna_baobab_leaf_liter");
	public static final FrozenLibFeature TREE_SAVANNA_BAOBAB = register("tree_savanna_baobab");
	public static final FrozenLibFeature TREE_SAVANNA_BAOBAB_VANILLA = register("tree_savanna_baobab_vanilla");
	public static final FrozenLibFeature TREE_WINDSWEPT_SAVANNA_NO_LITTER = register("tree_windswept_savanna_no_litter");
	public static final FrozenLibFeature TREE_WINDSWEPT_SAVANNA_LEAF_LITTER = register("tree_windswept_savanna_leaf_litter");
	public static final FrozenLibFeature TREE_WINDSWEPT_SAVANNA = register("tree_windswept_savanna");
	public static final FrozenLibFeature TREE_ARID_SAVANNA_NO_LITTER = register("tree_arid_savanna_no_litter");
	public static final FrozenLibFeature TREE_ARID_SAVANNA_LEAF_LITTER = register("tree_arid_savanna_leaf_litter");
	public static final FrozenLibFeature TREE_ARID_SAVANNA = register("tree_arid_savanna");
	public static final FrozenLibFeature TREE_ARID_SAVANNA_PALM_NO_LITTER = register("tree_arid_savanna_palm_no_litter");
	public static final FrozenLibFeature TREE_ARID_SAVANNA_PALM_LEAF_LITTER = register("tree_arid_savanna_palm_leaf_litter");
	public static final FrozenLibFeature TREE_ARID_SAVANNA_PALM = register("tree_arid_savanna_palm");

	// DESERT
	public static final FrozenLibFeature PALM = register("palm");
	public static final FrozenLibFeature PALM_OASIS = register("palm_oasis");

	// BADLANDS
	public static final FrozenLibFeature TREE_WOODED_BADLANDS_NO_LITTER = register("tree_wooded_badlands_no_litter");
	public static final FrozenLibFeature TREE_WOODED_BADLANDS_LEAF_LITTER = register("tree_wooded_badlands_leaf_litter");
	public static final FrozenLibFeature TREE_WOODED_BADLANDS = register("tree_wooded_badlands");

	// SWAMP
	public static final FrozenLibFeature TREE_SWAMP = register("tree_swamp");
	public static final FrozenLibFeature TREE_SWAMP_SURFACE_WILLOW_NO_LITTER = register("tree_swamp_surface_willow_no_litter");
	public static final FrozenLibFeature TREE_SWAMP_SURFACE_WILLOW_LEAF_LITTER = register("tree_swamp_surface_willow_leaf_litter");
	public static final FrozenLibFeature TREE_SWAMP_SURFACE_WILLOW = register("tree_swamp_surface_willow");
	public static final FrozenLibFeature TREE_SWAMP_WATER_SHALLOW = register("tree_swamp_water_shallow");
	public static final FrozenLibFeature MANGROVE_NO_LITTER = register("mangrove_no_litter");
	public static final FrozenLibFeature MANGROVE_LEAF_LITTER = register("mangrove_leaf_litter");
	public static final FrozenLibFeature MANGROVE = register("mangrove");
	public static final FrozenLibFeature TREE_CYPRESS_WETLANDS = register("tree_cypress_wetlands");
	public static final FrozenLibFeature TREE_CYPRESS_WETLANDS_WATER = register("tree_cypress_wetlands_water");
	public static final FrozenLibFeature TREE_CYPRESS_SAPLING = register("tree_cypress_sapling");

	// CHERRY GROVE
	public static final FrozenLibFeature CHERRY = register("cherry");

	// MAPLE FOREST
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLE = MapleCollection.NAMES.map(name -> register(name + "_maple"));
	public static final FrozenLibFeature MAPLE = register("maple");
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLE_NO_BEES = MapleCollection.NAMES.map(name -> register(name + "_maple_no_bees"));
	public static final MapleCollection<FrozenLibFeature> COLORED_MAPLE_BEES_SAPLING = MapleCollection.NAMES.map(name -> register(name + "_maple_bees_sapling"));
	public static final FrozenLibFeature MAPLE_BEES_SAPLING = register("maple_bees_sapling");

	// DAPPLED FOREST
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLAR_NO_LITTER = PoplarCollection.NAMES.map(name -> register(name + "_poplar_no_litter"));
	public static final FrozenLibFeature POPLAR_NO_LITTER = register("poplar_no_litter");
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLAR_LEAF_LITTER = PoplarCollection.NAMES.map(name -> register(name + "_poplar_leaf_litter"));
	public static final FrozenLibFeature POPLAR_LEAF_LITTER = register("poplar_leaf_litter");
	public static final FrozenLibFeature POPLAR = register("poplar");
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLAR_NO_BEES_SAPLING = PoplarCollection.NAMES.map(name -> register(name + "_poplar_no_bees_sapling"));
	public static final PoplarCollection<FrozenLibFeature> COLORED_POPLAR_BEES_SAPLING = PoplarCollection.NAMES.map(name -> register(name + "_poplar_bees_sapling"));

	// MEADOW
	public static final FrozenLibFeature TREE_MEADOW = register("tree_meadow");

	// GROVE
	public static final FrozenLibFeature TREE_GROVE = register("tree_grove");

	// TAIGA
	public static final FrozenLibFeature TREE_TAIGA_NO_LITTER = register("tree_taiga_no_litter");
	public static final FrozenLibFeature TREE_TAIGA_LEAF_LITTER = register("tree_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_TAIGA = register("tree_taiga");
	public static final FrozenLibFeature SHORT_MEGA_SPRUCE = register("short_mega_spruce_configured");
	public static final FrozenLibFeature SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow_configured");
	public static final FrozenLibFeature TREE_OLD_GROWTH_PINE_TAIGA_NO_LITTER = register("trees_old_growth_pine_taiga_no_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER = register("trees_old_growth_pine_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_PINE_TAIGA = register("tree_old_growth_pine_taiga");
	public static final FrozenLibFeature TREE_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER = register("tree_old_growth_spruce_taiga_no_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER = register("tree_old_growth_spruce_taiga_leaf_litter");
	public static final FrozenLibFeature TREE_OLD_GROWTH_SPRUCE_TAIGA = register("tree_old_growth_spruce_taiga");
	public static final FrozenLibFeature TREE_OLD_GROWTH_SNOWY_PINE_TAIGA = register("tree_old_growth_snowy_pine_taiga");
	public static final FrozenLibFeature TREE_WINDSWEPT_HILLS_NO_LITTER = register("tree_windswept_hills_no_litter");
	public static final FrozenLibFeature TREE_WINDSWEPT_HILLS_LEAF_LITTER = register("tree_windswept_hills_leaf_litter");
	public static final FrozenLibFeature TREE_WINDSWEPT_HILLS = register("tree_windswept_hills");
	public static final FrozenLibFeature TREE_TEMPERATE_RAINFOREST_NO_LITTER = register("tree_temperate_rainforest_no_litter");
	public static final FrozenLibFeature TREE_TEMPERATE_RAINFOREST_LEAF_LITTER = register("tree_temperate_rainforest_leaf_litter");
	public static final FrozenLibFeature TREE_TEMPERATE_RAINFOREST = register("tree_temperate_rainforest");

	// BUSH
	public static final FrozenLibFeature BIG_BUSH = register("big_bush");

	// FALLEN
	public static final FrozenLibFeature MOSSY_FALLEN_OAK_AND_BIRCH = register("mossy_fallen_oak_and_birch");
	public static final FrozenLibFeature FALLEN_OAK_AND_BIRCH_DARK_FOREST = register("fallen_oak_and_birch_dark_forest");
	public static final FrozenLibFeature FALLEN_BIRCH_AND_OAK = register("fallen_birch_and_oak");
	public static final FrozenLibFeature FALLEN_BIRCH_AND_SPRUCE = register("fallen_birch_and_spruce");
	public static final FrozenLibFeature FALLEN_SPRUCE_AND_OAK = register("fallen_spruce_and_oak");
	public static final FrozenLibFeature FALLEN_OAK_AND_BIRCH_AND_SPRUCE = register("fallen_oak_and_birch_and_spruce");
	public static final FrozenLibFeature MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE = register("mossy_fallen_oak_and_birch_and_spruce");
	public static final FrozenLibFeature FALLEN_JUNGLE_AND_OAK = register("fallen_jungle_and_oak");
	public static final FrozenLibFeature FALLEN_PALM_AND_JUNGLE_AND_OAK = register("fallen_palm_and_jungle_and_oak");
	public static final FrozenLibFeature FALLEN_ACACIA_AND_OAK = register("fallen_acacia_and_oak");
	public static final FrozenLibFeature FALLEN_CHERRY = register("fallen_cherry");
	public static final FrozenLibFeature FALLEN_WILLOW_AND_OAK = register("fallen_willow_and_oak");
	public static final FrozenLibFeature FALLEN_CYPRESS_AND_OAK = register("fallen_cypress_and_oak");

	// SNAPPED
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_OAK = register("snapped_birch_and_oak");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_SPRUCE = register("snapped_birch_and_spruce");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenLibFeature SNAPPED_BIRCH_AND_JUNGLE = register("snapped_birch_and_jungle");
	public static final FrozenLibFeature SNAPPED_ACACIA_AND_OAK = register("snapped_acacia_and_oak");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// PLAINS
		TREE_PLAINS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.04F),
					WWTreePlacements.DYING_FANCY_OAK_BEES_0004.asWeightedPlacedFeature(0.02F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.BIG_BUSH.asWeightedPlacedFeature(0.35F)
				),
				WWTreePlacements.LARGE_BUSH.getHolder()
			)
		);

		TREE_FLOWER_FIELD.makeAndSetHolder(
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

		// FOREST
		BIRCH_AND_OAK_ORIGINAL_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_002), 0.1F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_002)
			)
		);

		BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.BIRCH_BEES_0002_PLACED), 0.2F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_BEES_0002_LEAF_LITTER), 0.1F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_BEES_0002_LEAF_LITTER)
			)
		);

		BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_AND_OAK_ORIGINAL_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				BIRCH_AND_OAK_ORIGINAL_NO_LITTER.asInlinePlaced()
			)
		);

		registerNoLitterAndLitterFeature(
			BIRCH_AND_OAK_NO_LITTER, BIRCH_AND_OAK_LEAF_LITTER,
			WWTreePlacements.OAK_BEES_0004,
			List.of(
				Pair.of(WWTreePlacements.SHORT_BIRCH_BEES_0004, 0.2F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.04F),
				Pair.of(WWTreePlacements.FANCY_OAK_BEES_0004, 0.2F),
				Pair.of(WWTreePlacements.DYING_FANCY_OAK_BEES_0004, 0.055F),
				Pair.of(WWTreePlacements.DYING_OAK, 0.04F),
				Pair.of(WWTreePlacements.SHORT_OAK, 0.115F)
			)
		);

		BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(BIRCH_AND_OAK_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				BIRCH_AND_OAK_NO_LITTER.asInlinePlaced()
			)
		);

		BIRCH_AND_OAK_CALM.makeAndSetHolder(
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

		registerNoLitterAndLitterFeature(
			TREE_MIXED_FOREST_NO_LITTER, TREE_MIXED_FOREST_LEAF_LITTER,
			WWTreePlacements.OAK,
			List.of(
				Pair.of(WWTreePlacements.SHORT_SPRUCE, 0.33F),
				Pair.of(WWTreePlacements.SPRUCE, 0.29F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.086F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.02F),
				Pair.of(WWTreePlacements.FANCY_OAK_BEES_0004, 0.12F),
				Pair.of(WWTreePlacements.DYING_FANCY_OAK_BEES_0004, 0.025F),
				Pair.of(WWTreePlacements.DYING_OAK, 0.01F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.01F),
				Pair.of(WWTreePlacements.SHORT_OAK, 0.23F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.325F)
			)
		);

		TREE_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_MIXED_FOREST_LEAF_LITTER.asWeightedPlacedFeature(0.25F)),
				TREE_MIXED_FOREST_NO_LITTER.asInlinePlaced()
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_RAINFOREST_NO_LITTER, TREE_RAINFOREST_LEAF_LITTER,
			WWTreePlacements.DYING_OAK,
			List.of(
				Pair.of(WWTreePlacements.OAK, 0.085F),
				Pair.of(WWTreePlacements.DYING_FANCY_OAK_BEES_0004, 0.12F),
				Pair.of(WWTreePlacements.FANCY_OAK_BEES_0004, 0.27F),
				Pair.of(WWTreePlacements.OLD_DYING_FANCY_OAK_BEES_0004, 0.15F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.022F),
				Pair.of(WWTreePlacements.DYING_MEDIUM_BIRCH, 0.052F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.120F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.098F),
				Pair.of(WWTreePlacements.SHORT_OAK, 0.37F),
				Pair.of(WWTreePlacements.BIRCH, 0.02F),
				Pair.of(WWTreePlacements.MEDIUM_BIRCH, 0.19F)
			)
		);

		TREE_RAINFOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_RAINFOREST_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREE_RAINFOREST_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_FLOWER_FOREST.makeAndSetHolder(
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

		TREE_DYING_FOREST.makeAndSetHolder(
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

		TREE_DYING_MIXED_FOREST_NO_LITTER.makeAndSetHolder(
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

		TREE_DYING_MIXED_FOREST_LEAF_LITTER.makeAndSetHolder(
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

		TREE_DYING_MIXED_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_DYING_MIXED_FOREST_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(TREE_DYING_MIXED_FOREST_NO_LITTER.getHolder())
			)
		);

		TREE_SNOWY_DYING_FOREST.makeAndSetHolder(
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

		TREE_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(
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

		registerNoLitterAndLitterFeature(
			ARID_FOREST_TREES_NO_LITTER, ARID_FOREST_TREES_LEAF_LITTER,
			WWTreePlacements.DYING_OAK,
			List.of(
				Pair.of(WWTreePlacements.DYING_OAK, 0.7085F),
				Pair.of(WWTreePlacements.FANCY_DYING_OAK, 0.175F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.38F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.2325F)
			)
		);

		ARID_FOREST_TREES.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(ARID_FOREST_TREES_LEAF_LITTER.asWeightedPlacedFeature(0.4F)),
				ARID_FOREST_TREES_NO_LITTER.asInlinePlaced()
			)
		);

		// BIRCH FOREST
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

		registerNoLitterAndLitterFeature(
			TREES_BIRCH_TALL_NO_LITTER, TREES_BIRCH_TALL_LEAF_LITTER,
			WWTreePlacements.SUPER_BIRCH_BEES_0004,
			List.of(
				Pair.of(WWTreePlacements.SHORT_BIRCH_BEES_0004, 0.002F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.001F),
				Pair.of(WWTreePlacements.DYING_SUPER_BIRCH, 0.075F),
				Pair.of(WWTreePlacements.BIRCH_BEES_0004, 0.02F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.177F)
			)
		);

		TREES_BIRCH_TALL.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_BIRCH_TALL_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_BIRCH_TALL_NO_LITTER.getHolder())
			)
		);

		registerNoLitterAndLitterFeature(
			TREES_SEMI_BIRCH_AND_OAK_NO_LITTER, TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER,
			WWTreePlacements.OAK_BEES_0004,
			List.of(
				Pair.of(WWTreePlacements.SHORT_BIRCH_BEES_0004, 0.2F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.04F),
				Pair.of(WWTreePlacements.FANCY_OAK_BEES_0004, 0.06F),
				Pair.of(WWTreePlacements.DYING_FANCY_OAK_BEES_0004, 0.025F),
				Pair.of(WWTreePlacements.DYING_OAK, 0.04F),
				Pair.of(WWTreePlacements.SHORT_OAK, 0.13F),
				Pair.of(WWTreePlacements.MEDIUM_BIRCH, 0.14F),
				Pair.of(WWTreePlacements.DYING_MEDIUM_BIRCH, 0.045F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.1F),
				Pair.of(WWTreePlacements.MEDIUM_BIRCH_BEES_0004, 0.025F)
			)
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREES_SEMI_BIRCH_AND_OAK_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				PlacementUtils.inlinePlaced(TREES_SEMI_BIRCH_AND_OAK_NO_LITTER.getHolder())
			)
		);

		TREE_BIRCH_JUNGLE_NO_LITTER.makeAndSetHolder(
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

		TREE_BIRCH_JUNGLE_LEAF_LITTER.makeAndSetHolder(
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

		BTREE_BIRCH_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_BIRCH_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				TREE_BIRCH_JUNGLE_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_SPARSE_BIRCH_JUNGLE_NO_LITTER.makeAndSetHolder(
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

		TREE_SPARSE_BIRCH_JUNGLE_LEAF_LITTER.makeAndSetHolder(
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

		TREE_SPARSE_BIRCH_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_SPARSE_BIRCH_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(0.05F)),
				PlacementUtils.inlinePlaced(TREE_SPARSE_BIRCH_JUNGLE_NO_LITTER.getHolder())
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_BIRCH_TAIGA_NO_LITTER, TREE_BIRCH_TAIGA_LEAF_LITTER,
			WWTreePlacements.SHORT_BIRCH,
			List.of(
				Pair.of(WWTreePlacements.SPRUCE, 0.39F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.086F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.02F),
				Pair.of(WWTreePlacements.SHORT_SPRUCE, 0.155F),
				Pair.of(WWTreePlacements.MEDIUM_BIRCH, 0.37F),
				Pair.of(WWTreePlacements.DYING_MEDIUM_BIRCH, 0.01F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.01F)
			)
		);

		TREE_BIRCH_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_BIRCH_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				PlacementUtils.inlinePlaced(TREE_BIRCH_TAIGA_NO_LITTER.getHolder())
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_OLD_GROWTH_BIRCH_TAIGA_NO_LITTER, TREE_OLD_GROWTH_BIRCH_TAIGA_LEAF_LITTER,
			WWTreePlacements.BIRCH,
			List.of(
				Pair.of(WWTreePlacements.SPRUCE, 0.39F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.086F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.02F),
				Pair.of(WWTreePlacements.SHORT_SPRUCE, 0.155F),
				Pair.of(WWTreePlacements.DYING_SUPER_BIRCH, 0.37F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.01F),
				Pair.of(WWTreePlacements.DYING_MEDIUM_BIRCH, 0.01F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.01F),
				Pair.of(WWTreePlacements.BIRCH, 0.355F),
				Pair.of(WWTreePlacements.MEDIUM_BIRCH, 0.1F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.1F)
			)
		);

		TREE_OLD_GROWTH_BIRCH_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_OLD_GROWTH_BIRCH_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREE_OLD_GROWTH_BIRCH_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		// DARK FOREST
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

		registerNoLitterAndLitterFeature(
			TREE_DARK_TAIGA_NO_LITTER, TREE_DARK_TAIGA_LEAF_LITTER,
			WWTreePlacements.DARK_OAK,
			List.of(
				Pair.of(WWTreePlacements.SPRUCE, 0.155F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.086F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.045F),
				Pair.of(WWTreePlacements.SHORT_SPRUCE, 0.19F),
				Pair.of(WWTreePlacements.DARK_OAK, 0.235F),
				Pair.of(WWTreePlacements.DYING_DARK_OAK, 0.075F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.12F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.004F),
				Pair.of(WWTreePlacements.BIRCH, 0.1F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.005F),
				Pair.of(WWTreePlacements.TALL_DARK_OAK, 0.2F),
				Pair.of(WWTreePlacements.FANCY_TALL_DARK_OAK, 0.08F),
				Pair.of(WWTreePlacements.DYING_TALL_DARK_OAK, 0.024F),
				Pair.of(WWTreePlacements.DYING_FANCY_TALL_DARK_OAK, 0.01F),
				Pair.of(WWTreePlacements.DYING_OAK, 0.031F),
				Pair.of(WWTreePlacements.FANCY_OAK, 0.015F)
			)
		);

		TREE_DARK_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_DARK_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREE_DARK_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		// PALE GARDEN
		PALE_OAK.makeAndSetHolder(
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

		PALE_OAK_CREAKING.makeAndSetHolder(
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

		TREE_PALE_GARDEN.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					PALE_OAK_CREAKING.asWeightedPlacedFeature(0.1F),
					PALE_OAK.asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.PALE_OAK.getHolder()
			)
		);

		// JUNGLE
		TREE_JUNGLE_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlacements.MEGA_JUNGLE.asWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		TREE_JUNGLE_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					WWTreePlacements.MEGA_JUNGLE.litterAsWeightedPlacedFeature(0.33333334F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		TREE_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(0.1F)),
				TREE_JUNGLE_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_SPARSE_JUNGLE_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.asWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getHolder()
			)
		);

		TREE_SPARSE_JUNGLE_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FANCY_OAK.litterAsWeightedPlacedFeature(0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlacements.JUNGLE.getLitterVariantHolder()
			)
		);

		TREE_SPARSE_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_SPARSE_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(0.05F)),
				PlacementUtils.inlinePlaced(TREE_SPARSE_JUNGLE_NO_LITTER.getHolder())
			)
		);

		TREE_BAMBOO_JUNGLE_NO_LITTER.makeAndSetHolder(
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

		TREE_BAMBOO_JUNGLE_LEAF_LITTER.makeAndSetHolder(
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

		TREE_BAMBOO_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_BAMBOO_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				TREE_BAMBOO_JUNGLE_NO_LITTER.asInlinePlaced()
			)
		);

		PALM_JUNGLE_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM_DIRT.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.SHORT_WINDMILL_PALM_DIRT.asWeightedPlacedFeature(0.7F),
					WWTreePlacements.TALL_PALM_DIRT.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM_DIRT.getHolder()
			)
		);

		PALM_JUNGLE_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.25F),
					WWTreePlacements.SHORT_WINDMILL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.7F),
					WWTreePlacements.TALL_PALM_DIRT_LEAF_LITTER.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM_DIRT_LEAF_LITTER.getHolder()
			)
		);

		PALM_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(PALM_JUNGLE_LEAF_LITTER.asWeightedPlacedFeature(	0.075F)),
				PALM_JUNGLE_NO_LITTER.asInlinePlaced()
			)
		);

		// SAVANNA
		TREE_SAVANNA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		TREE_SAVANNA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		TREE_SAVANNA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_SAVANNA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREE_SAVANNA_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_SAVANNA_BAOBAB_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.asWeightedPlacedFeature(0.035F)
				),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		TREE_SAVANNA_BAOBAB_LEAF_LITER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlacements.BAOBAB.litterAsWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.litterAsWeightedPlacedFeature(0.035F)
				),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		TREE_SAVANNA_BAOBAB.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_SAVANNA_BAOBAB_LEAF_LITER.asWeightedPlacedFeature(0.5F)),
				TREE_SAVANNA_BAOBAB_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_SAVANNA_BAOBAB_VANILLA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.BAOBAB.asWeightedPlacedFeature(0.062F),
					WWTreePlacements.TALL_BAOBAB.asWeightedPlacedFeature(0.035F)
				),
				placedFeatures.getOrThrow(TreePlacements.OAK_CHECKED)
			)
		);

		TREE_WINDSWEPT_SAVANNA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getHolder()
			)
		);

		TREE_WINDSWEPT_SAVANNA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F)),
				WWTreePlacements.OAK_NO_FUNGI.getLitterVariantHolder()
			)
		);

		TREE_WINDSWEPT_SAVANNA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_WINDSWEPT_SAVANNA_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				TREE_WINDSWEPT_SAVANNA_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_ARID_SAVANNA_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.064F)),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		TREE_ARID_SAVANNA_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.064F)),
				WWTreePlacements.ACACIA_LEAF_LITTER.getHolder()
			)
		);

		TREE_ARID_SAVANNA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_ARID_SAVANNA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREE_ARID_SAVANNA_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_ARID_SAVANNA_PALM_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.08F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.052F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		TREE_ARID_SAVANNA_PALM_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.ACACIA_LEAF_LITTER.asWeightedPlacedFeature(0.8F),
					WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.08F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.052F)
				),
				WWTreePlacements.ACACIA_LEAF_LITTER.getHolder()
			)
		);

		TREE_ARID_SAVANNA_PALM.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_ARID_SAVANNA_PALM_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREE_ARID_SAVANNA_PALM_NO_LITTER.asInlinePlaced()
			)
		);

		// DESERT
		PALM.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_WINDMILL_PALM.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.TALL_PALM.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.PALM.getHolder()
			)
		);

		PALM_OASIS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.TALL_PALM.asWeightedPlacedFeature(0.5F),
					WWTreePlacements.TALL_WINDMILL_PALM.asWeightedPlacedFeature(0.1F),
					WWTreePlacements.SHORT_WINDMILL_PALM.asWeightedPlacedFeature(0.37F)
				),
				WWTreePlacements.PALM.getHolder()
			)
		);

		// BADLANDS
		TREE_WOODED_BADLANDS_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.asWeightedPlacedFeature(0.095F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_OAK.asWeightedPlacedFeature(0.67F)
				),
				WWTreePlacements.JUNIPER.getHolder()
			)
		);

		TREE_WOODED_BADLANDS_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.OAK_NO_FUNGI.litterAsWeightedPlacedFeature(0.095F),
					WWTreePlacements.LARGE_BUSH.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_OAK.litterAsWeightedPlacedFeature(0.67F)
				),
				WWTreePlacements.JUNIPER.getLitterVariantHolder()
			)
		);

		TREE_WOODED_BADLANDS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_WOODED_BADLANDS_LEAF_LITTER.asWeightedPlacedFeature(0.4F)),
				TREE_WOODED_BADLANDS_NO_LITTER.asInlinePlaced()
			)
		);

		// SWAMP
		TREE_SWAMP.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SWAMP_OAK.litterAsWeightedPlacedFeature(0.1F)),
				WWTreePlacements.SWAMP_OAK.getHolder()
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_SWAMP_SURFACE_WILLOW_NO_LITTER, TREE_SWAMP_SURFACE_WILLOW_LEAF_LITTER,
			WWTreePlacements.SWAMP_OAK,
			List.of(
				Pair.of(WWTreePlacements.SHORT_WILLOW, 0.75F),
				Pair.of(WWTreePlacements.WILLOW, 0.2F)
			)
		);

		TREE_SWAMP_SURFACE_WILLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_SWAMP_SURFACE_WILLOW_LEAF_LITTER.asWeightedPlacedFeature(	0.1F)),
				TREE_SWAMP_SURFACE_WILLOW_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_SWAMP_WATER_SHALLOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.WILLOW.asWeightedPlacedFeature(0.75F)),
				WWTreePlacements.SHORT_WILLOW.getHolder()
			)
		);

		registerNoLitterAndLitterFeature(
			MANGROVE_NO_LITTER, MANGROVE_LEAF_LITTER,
			WWTreePlacements.MANGROVE,
			List.of(Pair.of(WWTreePlacements.TALL_MANGROVE, 0.85F))
		);

		MANGROVE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(MANGROVE_LEAF_LITTER.asWeightedPlacedFeature(0.2F)),
				MANGROVE_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_CYPRESS_WETLANDS.makeAndSetHolder(
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

		TREE_CYPRESS_WETLANDS_WATER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SWAMP_CYPRESS.asWeightedPlacedFeature(0.85F)),
				WWTreePlacements.SWAMP_CYPRESS.getHolder()
			)
		);

		TREE_CYPRESS_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.CYPRESS.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.SHORT_CYPRESS.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.SWAMP_CYPRESS.asWeightedPlacedFeature(0.81F)
				),
				WWTreePlacements.FUNGUS_CYPRESS.getHolder()
			)
		);

		// CHERRY GROVE
		CHERRY.makeAndSetHolder(
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

		// MAPLE FOREST
		MapleCollection.zipApply(COLORED_MAPLE, MapleCollection.DYE_COLORS, (feature, color) -> {
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

		MAPLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_MAPLE.yellow().asWeightedPlacedFeature(0.4F),
					COLORED_MAPLE.orange().asWeightedPlacedFeature(0.55F),
					COLORED_MAPLE.red().asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.MAPLE_BEES_0004.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLE_BEES_SAPLING, MapleCollection.DYE_COLORS, (feature, color) -> {
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

		MAPLE_BEES_SAPLING.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_MAPLE_BEES_SAPLING.yellow().asWeightedPlacedFeature(0.4F),
					COLORED_MAPLE_BEES_SAPLING.orange().asWeightedPlacedFeature(0.55F),
					COLORED_MAPLE_BEES_SAPLING.red().asWeightedPlacedFeature(0.9F)
				),
				WWTreePlacements.MAPLE_BEES_0004.yellow().getHolder()
			)
		);

		MapleCollection.zipApply(COLORED_MAPLE_NO_BEES, MapleCollection.DYE_COLORS, (feature, color) -> {
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

		// DAPPLED FOREST
		PoplarCollection.zipApply(COLORED_POPLAR_NO_LITTER, PoplarCollection.DYE_COLORS, (feature, color) -> {
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

		POPLAR_NO_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLAR_NO_LITTER.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLAR_NO_LITTER.orange().asWeightedPlacedFeature(0.45F)
				),
				COLORED_POPLAR_NO_LITTER.yellow().asInlinePlaced()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLAR_LEAF_LITTER, PoplarCollection.DYE_COLORS, (feature, color) -> {
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

		POPLAR_LEAF_LITTER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					COLORED_POPLAR_LEAF_LITTER.red().asWeightedPlacedFeature(0.377F),
					COLORED_POPLAR_LEAF_LITTER.orange().asWeightedPlacedFeature(0.45F)
				),
				COLORED_POPLAR_LEAF_LITTER.yellow().asInlinePlaced()
			)
		);

		POPLAR.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(POPLAR_LEAF_LITTER.asWeightedPlacedFeature(0.75F)),
				POPLAR_NO_LITTER.asInlinePlaced()
			)
		);

		PoplarCollection.zipApply(COLORED_POPLAR_BEES_SAPLING, PoplarCollection.DYE_COLORS, (feature, color) -> {
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

		PoplarCollection.zipApply(COLORED_POPLAR_NO_BEES_SAPLING, PoplarCollection.DYE_COLORS, (feature, color) -> {
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

		// MEADOW
		TREE_MEADOW.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FANCY_OAK_BEES.asWeightedPlacedFeature(0.5F)),
				WWTreePlacements.SUPER_BIRCH_BEES.getHolder()
			)
		);

		// GROVE
		TREE_GROVE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FUNGUS_PINE_ON_SNOW.asWeightedPlacedFeature(0.33333334F)),
				WWTreePlacements.SPRUCE_ON_SNOW.getHolder()
			)
		);

		// TAIGA
		registerNoLitterAndLitterFeature(
			TREE_TAIGA_NO_LITTER, TREE_TAIGA_LEAF_LITTER,
			WWTreePlacements.SPRUCE,
			List.of(
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.33333334F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.075F)
			)
		);

		TREE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.15F)),
				TREE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

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

		registerNoLitterAndLitterFeature(
			TREE_OLD_GROWTH_PINE_TAIGA_NO_LITTER, TREE_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER,
			WWTreePlacements.SPRUCE,
			List.of(
				Pair.of(WWTreePlacements.MEGA_FUNGUS_SPRUCE, 0.025641026F),
				Pair.of(WWTreePlacements.DYING_MEGA_FUNGUS_PINE, 0.028F),
				Pair.of(WWTreePlacements.MEGA_FUNGUS_PINE, 0.30769232F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.045F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.33333334F)
			)
		);

		TREE_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_OLD_GROWTH_PINE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREE_OLD_GROWTH_PINE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER, TREE_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER,
			WWTreePlacements.SPRUCE,
			List.of(
				Pair.of(WWTreePlacements.MEGA_FUNGUS_SPRUCE, 0.33333334F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.075F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.33333334F)
			)
		);

		TREE_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_OLD_GROWTH_SPRUCE_TAIGA_LEAF_LITTER.asWeightedPlacedFeature(0.5F)),
				TREE_OLD_GROWTH_SPRUCE_TAIGA_NO_LITTER.asInlinePlaced()
			)
		);

		TREE_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(
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

		registerNoLitterAndLitterFeature(
			TREE_WINDSWEPT_HILLS_NO_LITTER, TREE_WINDSWEPT_HILLS_LEAF_LITTER,
			WWTreePlacements.OAK,
			List.of(
				Pair.of(WWTreePlacements.SPRUCE, 0.666F),
				Pair.of(WWTreePlacements.FANCY_DYING_OAK, 0.01F),
				Pair.of(WWTreePlacements.DYING_OAK, 0.02F),
				Pair.of(WWTreePlacements.FANCY_OAK, 0.1F)
			)
		);

		TREE_WINDSWEPT_HILLS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_WINDSWEPT_HILLS_LEAF_LITTER.asWeightedPlacedFeature(0.3F)),
				TREE_WINDSWEPT_HILLS_NO_LITTER.asInlinePlaced()
			)
		);

		registerNoLitterAndLitterFeature(
			TREE_TEMPERATE_RAINFOREST_NO_LITTER, TREE_TEMPERATE_RAINFOREST_LEAF_LITTER,
			WWTreePlacements.DYING_FUNGUS_PINE,
			List.of(
				Pair.of(WWTreePlacements.DYING_OAK, 0.045F),
				Pair.of(WWTreePlacements.DYING_FANCY_OAK_BEES_0004, 0.042F),
				Pair.of(WWTreePlacements.SHORT_OAK, 0.02F),
				Pair.of(WWTreePlacements.DYING_BIRCH, 0.021F),
				Pair.of(WWTreePlacements.DYING_MEDIUM_BIRCH, 0.041F),
				Pair.of(WWTreePlacements.DYING_SHORT_BIRCH, 0.05F),
				Pair.of(WWTreePlacements.SHORT_BIRCH, 0.025F),
				Pair.of(WWTreePlacements.FUNGUS_PINE, 0.09F),
				Pair.of(WWTreePlacements.SHORT_SPRUCE, 0.4F),
				Pair.of(WWTreePlacements.SPRUCE, 0.2F),
				Pair.of(WWTreePlacements.DYING_FUNGUS_PINE, 0.72F),
				Pair.of(WWTreePlacements.SHORT_MEGA_SPRUCE, 0.6F)
			)
		);

		TREE_TEMPERATE_RAINFOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(TREE_TEMPERATE_RAINFOREST_LEAF_LITTER.asWeightedPlacedFeature(0.2F)),
				TREE_TEMPERATE_RAINFOREST_NO_LITTER.asInlinePlaced()
			)
		);

		// BUSH
		BIG_BUSH.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.BIG_BUSH.asWeightedPlacedFeature(0.3F)),
				WWTreePlacements.LARGE_BUSH.getHolder()
			)
		);

		// FALLEN
		MOSSY_FALLEN_OAK_AND_BIRCH.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.MOSSY_FALLEN_BIRCH.asWeightedPlacedFeature(0.0225F)),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.135F),
					WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.24F)),
				WWTreePlacements.FALLEN_SPRUCE.getHolder()
			)
		);

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_SPRUCE.asWeightedPlacedFeature(0.55F)),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		FALLEN_OAK_AND_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_SPRUCE.asWeightedPlacedFeature(0.4F),
					WWTreePlacements.FALLEN_BIRCH.asWeightedPlacedFeature(0.3F)
				),
				WWTreePlacements.FALLEN_OAK.getHolder()
			)
		);

		MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.MOSSY_FALLEN_SPRUCE.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.MOSSY_FALLEN_BIRCH.asWeightedPlacedFeature(0.1F)
				),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		FALLEN_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)),
				WWTreePlacements.FALLEN_JUNGLE.getHolder()
			)
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_PALM.asWeightedPlacedFeature(0.135F),
					WWTreePlacements.MOSSY_FALLEN_OAK.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.FALLEN_JUNGLE.getHolder()
			)
		);

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_ACACIA.asWeightedPlacedFeature(0.7F)),
				WWTreePlacements.FALLEN_OAK_NO_MOSS.getHolder()
			)
		);

		FALLEN_CHERRY.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.FALLEN_CHERRY.asWeightedPlacedFeature(0.6F),
					WWTreePlacements.MOSSY_FALLEN_CHERRY.asWeightedPlacedFeature(0.4F)
				),
				WWTreePlacements.FALLEN_CHERRY.getHolder()
			)
		);

		FALLEN_WILLOW_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.MOSSY_FALLEN_WILLOW.asWeightedPlacedFeature(0.8F)),
				WWTreePlacements.MOSSY_FALLEN_OAK.getHolder()
			)
		);

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.FALLEN_OAK.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.FALLEN_CYPRESS.getHolder()
			)
		);

		// SNAPPED
		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.3F)),
				WWTreePlacements.SNAPPED_OAK.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.5F)),
				WWTreePlacements.SNAPPED_SPRUCE.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.15F),
					WWTreePlacements.SNAPPED_SPRUCE.asWeightedPlacedFeature(0.25F)
				),
				WWTreePlacements.SNAPPED_OAK.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_BIRCH.asWeightedPlacedFeature(0.35F)),
				WWTreePlacements.SNAPPED_JUNGLE.getHolder()
			)
		);

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(WWTreePlacements.SNAPPED_OAK.asWeightedPlacedFeature(0.3F)),
				WWTreePlacements.SNAPPED_ACACIA.getHolder()
			)
		);
	}

	private static void registerNoLitterAndLitterFeature(
		FrozenLibFeature noLitterFeature,
		FrozenLibFeature litterFeature,
		FrozenLibPlacedTreeFeature placementFallback,
		List<Pair<FrozenLibPlacedTreeFeature, Float>> trees
	) {
		noLitterFeature.makeAndSetHolder(
			new RandomSelectorFeature(
				trees.stream()
					.map(pair -> new WeightedPlacedFeature(pair.getFirst().getHolder(), pair.getSecond()))
					.toList(),
				placementFallback.getHolder()
			)
		);

		litterFeature.makeAndSetHolder(
			new RandomSelectorFeature(
				trees.stream()
					.map(pair -> new WeightedPlacedFeature(pair.getFirst().getLitterVariantHolder(), pair.getSecond()))
					.toList(),
				placementFallback.getLitterVariantHolder()
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("tree_vegetation/" + id));
	}

	private WWTreeVegetationFeatures() {}
}
