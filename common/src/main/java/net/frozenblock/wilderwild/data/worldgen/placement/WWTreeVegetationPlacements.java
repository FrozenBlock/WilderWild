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

package net.frozenblock.wilderwild.data.worldgen.placement;

import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ConfigSelectorFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeVegetationFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public final class WWTreeVegetationPlacements {
	// PLAINS
	public static final FrozenLibPlacedFeature TREE_PLAINS = register("tree_plains");
	public static final FrozenLibPlacedFeature TREE_FLOWER_FIELD = register("tree_flower_field");
	public static final FrozenLibPlacedFeature TREE_SNOWY = register("tree_snowy");

	// FOREST
	public static final FrozenLibPlacedFeature BIRCH_AND_OAK_ORIGINAL = register("birch_and_oak_original");
	public static final FrozenLibPlacedFeature BIRCH_AND_OAK = register("birch_and_oak");
	public static final FrozenLibPlacedFeature TREE_SPARSE_FOREST = register("tree_sparse_forest");
	public static final FrozenLibPlacedFeature TREE_MIXED_FOREST = register("tree_mixed_forest");
	public static final FrozenLibPlacedFeature TREE_RAINFOREST = register("tree_rainforest");
	public static final FrozenLibPlacedFeature TREE_FLOWER_FOREST = register("tree_flower_forest");
	public static final FrozenLibPlacedFeature TREE_DYING_FOREST = register("tree_dying_forest");
	public static final FrozenLibPlacedFeature TREE_DYING_MIXED_FOREST = register("tree_dying_mixed_forest");
	public static final FrozenLibPlacedFeature TREE_SNOWY_DYING_FOREST = register("tree_snowy_dying_forest");
	public static final FrozenLibPlacedFeature TREE_SNOWY_DYING_MIXED_FOREST = register("tree_snowy_dying_mixed_forest");
	public static final FrozenLibPlacedFeature TREE_PARCHED_FOREST = register("tree_parched_forest");
	public static final FrozenLibPlacedFeature TREE_ARID_FOREST = register("tree_arid_forest");

	// BIRCH FOREST
	public static final FrozenLibPlacedFeature TREE_BIRCH = register("tree_birch");
	public static final FrozenLibPlacedFeature TREE_BIRCH_TALL = register("tree_birch_tall");
	public static final FrozenLibPlacedFeature TREE_SEMI_BIRCH_AND_OAK = register("tree_semi_birch_and_oak");
	public static final FrozenLibPlacedFeature TREE_BIRCH_JUNGLE = register("tree_birch_jungle");
	public static final FrozenLibPlacedFeature TREE_SPARSE_BIRCH_JUNGLE = register("tree_sparse_birch_jungle");
	public static final FrozenLibPlacedFeature TREE_BIRCH_TAIGA = register("tree_birch_taiga");
	public static final FrozenLibPlacedFeature TREE_OLD_GROWTH_BIRCH_TAIGA = register("tree_old_growth_birch_taiga");

	// DARK FOREST
	public static final FrozenLibPlacedFeature DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenLibPlacedFeature OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenLibPlacedFeature DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenLibPlacedFeature TREE_DARK_TAIGA = register("tree_dark_taiga");

	// PALE GARDEN
	public static final FrozenLibPlacedFeature TREE_PALE_GARDEN = register("tree_pale_garden");

	// JUNGLE
	public static final FrozenLibPlacedFeature TREE_JUNGLE = register("tree_jungle");
	public static final FrozenLibPlacedFeature TREE_SPARSE_JUNGLE = register("tree_sparse_jungle");
	public static final FrozenLibPlacedFeature TREE_BAMBOO_JUNGLE = register("tree_bamboo_jungle");
	public static final FrozenLibPlacedFeature PALM_JUNGLE = register("palm_jungle");

	// SAVANNA
	public static final FrozenLibPlacedFeature TREE_SAVANNA = register("tree_savanna");
	public static final FrozenLibPlacedFeature TREE_SAVANNA_BAOBAB = register("tree_savanna_baobab");
	public static final FrozenLibPlacedFeature TREE_SAVANNA_BAOBAB_VANILLA = register("tree_savanna_baobab_vanilla");
	public static final FrozenLibPlacedFeature TREE_WINDSWEPT_SAVANNA = register("tree_windswept_savanna");
	public static final FrozenLibPlacedFeature TREE_ARID_SAVANNA = register("tree_arid_savanna");

	// DESERT
	public static final FrozenLibPlacedFeature PALM = register("palm");
	public static final FrozenLibPlacedFeature PALM_RARE = register("palm_rare");
	public static final FrozenLibPlacedFeature PALMS_OASIS = register("palms_oasis");
	public static final FrozenLibPlacedFeature PALMS_WARM_BEACH = register("palms_warm_beach");

	// BADLANDS
	public static final FrozenLibPlacedFeature TREE_WOODED_BADLANDS = register("tree_wooded_badlands");

	// SWAMP
	public static final FrozenLibPlacedFeature TREE_SWAMP = register("tree_swamp");
	public static final FrozenLibPlacedFeature TREE_SWAMP_SURFACE_WILLOW = register("tree_swamp_surface_willow");
	public static final FrozenLibPlacedFeature TREE_SWAMP_WATER_SHALLOW = register("tree_swamp_water_shallow");
	public static final FrozenLibPlacedFeature TREE_SWAMP_WATER = register("tree_swamp_water");
	public static final FrozenLibPlacedFeature MANGROVE = register("mangrove");
	public static final FrozenLibPlacedFeature TREE_CYPRESS_WETLANDS = register("tree_cypress_wetlands");
	public static final FrozenLibPlacedFeature TREE_CYPRESS_WETLANDS_WATER = register("tree_cypress_wetlands_water");

	// CHERRY GROVE
	public static final FrozenLibPlacedFeature CHERRY = register("cherry");

	// MAPLE FOREST
	public static final FrozenLibPlacedFeature MAPLE = register("maple");

	// DAPPLED FOREST
	public static final FrozenLibPlacedFeature POPLAR = register("poplar");

	// MEADOW
	public static final FrozenLibPlacedFeature TREE_MEADOW = register("tree_meadow");

	// GROVE
	public static final FrozenLibPlacedFeature TREE_GROVE = register("tree_grove");

	// TAIGA
	public static final FrozenLibPlacedFeature TREE_TAIGA = register("tree_taiga");
	public static final FrozenLibPlacedFeature TREE_TAIGA_NO_LITTER = register("tree_taiga_no_litter");
	public static final FrozenLibPlacedFeature SHORT_SPRUCE = register("short_spruce");
	public static final FrozenLibPlacedFeature SHORT_SPRUCE_SPARSE_LEAF_LITTER = register("short_spruce_sparse_leaf_litter");
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE = register("short_mega_spruce");
	public static final FrozenLibPlacedFeature SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow");
	public static final FrozenLibPlacedFeature TREE_OLD_GROWTH_PINE_TAIGA = register("tree_old_growth_pine_taiga");
	public static final FrozenLibPlacedFeature TREE_OLD_GROWTH_SPRUCE_TAIGA = register("tree_old_growth_spruce_taiga");
	public static final FrozenLibPlacedFeature TREE_OLD_GROWTH_SNOWY_PINE_TAIGA = register("tree_old_growth_snowy_pine_taiga");
	public static final FrozenLibPlacedFeature TREE_WINDSWEPT_HILLS = register("tree_windswept_hills");
	public static final FrozenLibPlacedFeature TREE_WINDSWEPT_FOREST = register("tree_windswept_forest");
	public static final FrozenLibPlacedFeature TREE_TEMPERATE_RAINFOREST = register("tree_temperate_rainforest");

	// BUSH
	public static final FrozenLibPlacedFeature BIG_BUSH_FOREST = register("big_bush_forest");
	public static final FrozenLibPlacedFeature BIG_BUSH_FOREST_COMMON = register("big_bush_forest_common");
	public static final FrozenLibPlacedFeature BIG_BUSH = register("big_bush");
	public static final FrozenLibPlacedFeature BIG_BUSH_WATER = register("big_bush_water");
	public static final FrozenLibPlacedFeature BIG_BUSH_ON_SAND = register("big_bush_on_sand");

	// FALLEN
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_OAK_AND_BIRCH = register("mossy_fallen_oak_and_birch");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_BIRCH_DARK_FOREST = register("fallen_oak_and_birch_dark_forest");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH = register("fallen_birch");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_AND_OAK = register("fallen_birch_and_oa");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_AND_OAK_SPARSE = register("fallen_birch_and_oak_sparse");
	public static final FrozenLibPlacedFeature FALLEN_BIRCH_AND_SPRUCE = register("fallen_birch_and_spruce");
	public static final FrozenLibPlacedFeature FALLEN_OAK_AND_BIRCH_AND_SPRUCE = register("fallen_oak_and_birch_and_spruce");
	public static final FrozenLibPlacedFeature MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE = register("mossy_fallen_oak_and_birch_and_spruce");
	public static final FrozenLibPlacedFeature FALLEN_SPRUCE_AND_OAK = register("fallen_spruce_and_oak");
	public static final FrozenLibPlacedFeature FALLEN_SPRUCE = register("fallen_spruce");
	public static final FrozenLibPlacedFeature CLEAN_FALLEN_SPRUCE = register("clean_fallen_spruce");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_SPRUCE = register("large_fallen_spruce");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_SPRUCE_COMMON = register("large_fallen_spruce_common");
	public static final FrozenLibPlacedFeature CLEAN_LARGE_FALLEN_SPRUCE = register("clean_large_fallen_spruce");
	public static final FrozenLibPlacedFeature CLEAN_LARGE_FALLEN_SPRUCE_COMMON = register("clean_large_fallen_spruce_common");
	public static final FrozenLibPlacedFeature FALLEN_DARK_OAK = register("fallen_dark_oak");
	public static final FrozenLibPlacedFeature FALLEN_DARK_OAK_COMMON = register("fallen_dark_oak_common");
	public static final FrozenLibPlacedFeature FALLEN_PALE_OAK = register("fallen_pale_oak");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_JUNGLE = register("large_fallen_jungle");
	public static final FrozenLibPlacedFeature LARGE_FALLEN_JUNGLE_COMMON = register("large_fallen_jungle_common");
	public static final FrozenLibPlacedFeature FALLEN_ACACIA_AND_OAK = register("fallen_acacia_and_oak");
	public static final FrozenLibPlacedFeature FALLEN_WILLOW_AND_OAK = register("fallen_willow_and_oak");
	public static final FrozenLibPlacedFeature FALLEN_CYPRESS_AND_OAK = register("fallen_cypress_and_oak");
	public static final FrozenLibPlacedFeature FALLEN_MANGROVE = register("fallen_mangrove");
	public static final FrozenLibPlacedFeature FALLEN_PALM = register("fallen_palm");
	public static final FrozenLibPlacedFeature FALLEN_PALM_RARE = register("fallen_palm_rare");
	public static final FrozenLibPlacedFeature FALLEN_PALM_AND_JUNGLE_AND_OAK = register("fallen_palm_and_jungle_and_oak");
	public static final FrozenLibPlacedFeature FALLEN_CHERRY = register("fallen_cherry");
	public static final FrozenLibPlacedFeature FALLEN_MAPLE = register("fallen_maple");
	public static final FrozenLibPlacedFeature FALLEN_POPLAR = register("fallen_poplar");

	// SNAPPED
	public static final FrozenLibPlacedFeature SNAPPED_OAK = register("snapped_oak");
	public static final FrozenLibPlacedFeature SNAPPED_OAK_CLEARING = register("snapped_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH = register("snapped_birch");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_CLEARING = register("snapped_birch_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK = register("snapped_birch_and_oak");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_CLEARING = register("snapped_birch_and_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_SPRUCE = register("snapped_birch_and_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_SPRUCE_CLEARING = register("snapped_birch_and_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING = register("snapped_birch_and_oak_and_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_BIRCH_AND_JUNGLE = register("snapped_birch_and_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE = register("snapped_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_CLEARING = register("snapped_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW = register("snapped_spruce_on_snow");
	public static final FrozenLibPlacedFeature SNAPPED_SPRUCE_ON_SNOW_CLEARING = register("snapped_spruce_on_snow_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE = register("snapped_large_spruce");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_COMMON = register("snapped_large_spruce_common");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_CLEARING = register("snapped_large_spruce_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW = register("snapped_large_spruce_on_snow");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING = register("snapped_large_spruce_on_snow_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_DARK_OAK = register("snapped_dark_oak");
	public static final FrozenLibPlacedFeature SNAPPED_DARK_OAK_CLEARING = register("snapped_dark_oak_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_PALE_OAK = register("snapped_pale_oak");
	public static final FrozenLibPlacedFeature SNAPPED_JUNGLE = register("snapped_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_LARGE_JUNGLE = register("snapped_large_jungle");
	public static final FrozenLibPlacedFeature SNAPPED_ACACIA = register("snapped_acacia");
	public static final FrozenLibPlacedFeature SNAPPED_ACACIA_AND_OAK = register("snapped_acacia_and_oak");
	public static final FrozenLibPlacedFeature SNAPPED_CYPRESS = register("snapped_cypress");
	public static final FrozenLibPlacedFeature SNAPPED_CHERRY = register("snapped_cherry");
	public static final FrozenLibPlacedFeature SNAPPED_MAPLE = register("snapped_maple");
	public static final FrozenLibPlacedFeature SNAPPED_MAPLE_CLEARING = register("snapped_maple_clearing");
	public static final FrozenLibPlacedFeature SNAPPED_POPLAR = register("snapped_poplar");
	public static final FrozenLibPlacedFeature SNAPPED_POPLAR_CLEARING = register("snapped_poplar_clearing");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		final PlacementFilter generateTree = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TREE));
		final PlacementFilter generatePalm = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALM));

		// PLAINS
		TREE_PLAINS.makeAndSetHolder(WWTreeVegetationFeatures.TREE_PLAINS,
			PlacementUtils.countExtra(1, 0.1F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		TREE_FLOWER_FIELD.makeAndSetHolder(WWTreeVegetationFeatures.TREE_FLOWER_FIELD,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.25F, 1))
		);

		TREE_SNOWY.makeAndSetHolder(WWTreeFeatures.SHORT_SPRUCE.getHolder(),
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);

		// FOREST
		BIRCH_AND_OAK_ORIGINAL.makeAndSetHolder(WWTreeVegetationFeatures.BIRCH_AND_OAK_ORIGINAL,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(11, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		BIRCH_AND_OAK.makeAndSetHolder(WWTreeVegetationFeatures.BIRCH_AND_OAK,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_SPARSE_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.BIRCH_AND_OAK_CALM,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 2))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_MIXED_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_MIXED_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(14, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_RAINFOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_RAINFOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(12, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_FLOWER_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_FLOWER_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_DYING_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_DYING_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(5, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_DYING_MIXED_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_DYING_MIXED_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_SNOWY_DYING_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SNOWY_DYING_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(3, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SNOWY_DYING_MIXED_FOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(6, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_PARCHED_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.PARCHED_FOREST_TREES,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(4, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_ARID_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.ARID_FOREST_TREES,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		// BIRCH FOREST
		TREE_BIRCH.makeAndSetHolder(WWTreeVegetationFeatures.TREES_BIRCH,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_BIRCH_TALL.makeAndSetHolder(WWTreeVegetationFeatures.TREES_BIRCH_TALL,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_SEMI_BIRCH_AND_OAK.makeAndSetHolder(WWTreeVegetationFeatures.TREES_SEMI_BIRCH_AND_OAK,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(9, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_BIRCH_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.BTREE_BIRCH_JUNGLE,
			VegetationPlacements.treePlacement(CountPlacement.of(29))
		);

		TREE_SPARSE_BIRCH_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SPARSE_BIRCH_JUNGLE,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
		);

		TREE_BIRCH_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_BIRCH_TAIGA,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_OLD_GROWTH_BIRCH_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_OLD_GROWTH_BIRCH_TAIGA,
			VegetationPlacements.treePlacementBase(CountPlacement.of(3))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		// DARK FOREST
		DARK_FOREST_VEGETATION.makeAndSetHolder(WWTreeVegetationFeatures.DARK_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(16))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(WWTreeVegetationFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(UniformInt.of(30, 32)))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(WWTreeVegetationFeatures.DARK_BIRCH_FOREST_VEGETATION.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(14))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_DARK_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_DARK_TAIGA.getHolder(),
			VegetationPlacements.treePlacementBase(CountPlacement.of(14))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		// PALE GARDEN
		TREE_PALE_GARDEN.makeAndSetHolder(WWTreeVegetationFeatures.TREE_PALE_GARDEN,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(18, 0.1F, 2))
		);

		// JUNGLE
		TREE_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.TREE_JUNGLE,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(50, 0.1F, 1))
		);

		TREE_SPARSE_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SPARSE_JUNGLE,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		TREE_BAMBOO_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.TREE_BAMBOO_JUNGLE,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(30, 0.1F, 1))
		);

		PALM_JUNGLE.makeAndSetHolder(WWTreeVegetationFeatures.PALM_JUNGLE,
			generatePalm,
			PlacementUtils.countExtra(8, 0.5F, 2),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		// SAVANNA
		TREE_SAVANNA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SAVANNA,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		TREE_SAVANNA_BAOBAB.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SAVANNA_BAOBAB,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		TREE_SAVANNA_BAOBAB_VANILLA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SAVANNA_BAOBAB_VANILLA,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);

		TREE_WINDSWEPT_SAVANNA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_WINDSWEPT_SAVANNA,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);

		TREE_ARID_SAVANNA.makeAndSetHolder(
			new ConfigSelectorFeature(
				configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALM),
				WWTreeVegetationFeatures.TREE_ARID_SAVANNA_PALM.asInlinePlaced(),
				WWTreeVegetationFeatures.TREE_ARID_SAVANNA.asInlinePlaced()
			),
			VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(12))
		);

		// DESERT
		PALM.makeAndSetHolder(WWTreeVegetationFeatures.PALM,
			generatePalm,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PALM_RARE.makeAndSetHolder(WWTreeVegetationFeatures.PALM_OASIS,
			generatePalm,
			RarityFilter.onAverageOnceEvery(52),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PALMS_OASIS.makeAndSetHolder(WWTreeVegetationFeatures.PALM_OASIS,
			generatePalm,
			RarityFilter.onAverageOnceEvery(14),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		PALMS_WARM_BEACH.makeAndSetHolder(WWTreeVegetationFeatures.PALM_OASIS,
			generatePalm,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		// BADLANDS
		TREE_WOODED_BADLANDS.makeAndSetHolder(WWTreeVegetationFeatures.TREE_WOODED_BADLANDS,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(6, 0.1F, 1))
		);

		// SWAMP
		TREE_SWAMP.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SWAMP,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING))
		);

		TREE_SWAMP_SURFACE_WILLOW.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SWAMP_SURFACE_WILLOW,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(1),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING.get())
		);

		TREE_SWAMP_WATER_SHALLOW.makeAndSetHolder(WWTreeVegetationFeatures.TREE_SWAMP_WATER_SHALLOW,
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(2),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING.get())
		);

		TREE_SWAMP_WATER.makeAndSetHolder(WWTreeFeatures.TALL_WILLOW.getHolder(),
			PlacementUtils.countExtra(2, 0.1F, 1),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(4),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesFluids(Direction.UP, Fluids.WATER),
					BlockPredicate.matchesFluids(Fluids.WATER)
				)
			),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.WILLOW_SAPLING.get())
		);

		MANGROVE.makeAndSetHolder(WWTreeVegetationFeatures.MANGROVE,
			CountPlacement.of(25),
			InSquarePlacement.spread(),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE),
			BiomeFilter.biome()
		);

		TREE_CYPRESS_WETLANDS.makeAndSetHolder(WWTreeVegetationFeatures.TREE_CYPRESS_WETLANDS,
			CountPlacement.of(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(WWBlocks.CYPRESS_SAPLING.get())
		);

		TREE_CYPRESS_WETLANDS_WATER.makeAndSetHolder(WWTreeVegetationFeatures.TREE_CYPRESS_WETLANDS_WATER,
			CountPlacement.of(UniformInt.of(5, 10)),
			SurfaceWaterDepthFilter.forMaxDepth(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			PlacementUtils.filteredByBlockSurvival(Blocks.MANGROVE_PROPAGULE)
		);

		// CHERRY GROVE
		CHERRY.makeAndSetHolder(WWTreeVegetationFeatures.CHERRY,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 3))
		);

		// MAPLE FOREST
		MAPLE.makeAndSetHolder(WWTreeVegetationFeatures.MAPLE_LEAF_LITTER,
			PlacementUtils.countExtra(6, 0.1F, 2),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.MAPLE_SAPLING.yellow().get()),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		// DAPPLED FOREST
		POPLAR.makeAndSetHolder(WWTreeVegetationFeatures.POPLAR,
			PlacementUtils.countExtra(10, 0.1F, 3),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.POPLAR_SAPLING),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		// MEADOW
		TREE_MEADOW.makeAndSetHolder(WWTreeVegetationFeatures.TREE_MEADOW,
			VegetationPlacements.treePlacement(RarityFilter.onAverageOnceEvery(100))
		);

		// GROVE
		TREE_GROVE.makeAndSetHolder(WWTreeVegetationFeatures.TREE_GROVE,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);

		// TAIGA
		TREE_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_TAIGA_NO_LITTER.makeAndSetHolder(WWTreeVegetationFeatures.TREE_TAIGA_NO_LITTER,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		SHORT_SPRUCE.makeAndSetHolder(WWTreeFeatures.SHORT_SPRUCE.getHolder(),
			generateTree,
			PlacementUtils.countExtra(5, 0.1F, 1),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		SHORT_SPRUCE_SPARSE_LEAF_LITTER.makeAndSetHolder(WWTreeFeatures.SHORT_SPRUCE.getLitterVariantHolder(),
			generateTree,
			PlacementUtils.countExtra(0, 0.2F, 1),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING),
			WWPlacementUtils.TREE_CLEARING_FILTER,
			BiomeFilter.biome()
		);

		SHORT_MEGA_SPRUCE.makeAndSetHolder(WWTreeVegetationFeatures.SHORT_MEGA_SPRUCE,
			generateTree,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(WWTreeVegetationFeatures.SHORT_MEGA_SPRUCE_ON_SNOW,
			generateTree,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		TREE_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_OLD_GROWTH_PINE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_OLD_GROWTH_SPRUCE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(10, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(WWTreeVegetationFeatures.TREE_OLD_GROWTH_SNOWY_PINE_TAIGA,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(8, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		TREE_WINDSWEPT_HILLS.makeAndSetHolder(WWTreeVegetationFeatures.TREE_WINDSWEPT_HILLS,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);

		TREE_WINDSWEPT_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_WINDSWEPT_HILLS,
			VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);

		TREE_TEMPERATE_RAINFOREST.makeAndSetHolder(WWTreeVegetationFeatures.TREE_TEMPERATE_RAINFOREST,
			VegetationPlacements.treePlacementBase(PlacementUtils.countExtra(13, 0.1F, 1))
				.add(WWPlacementUtils.TREE_CLEARING_FILTER).build()
		);

		// BUSH
		BIG_BUSH_FOREST.makeAndSetHolder(WWTreeVegetationFeatures.BIG_BUSH,
			generateTree,
			PlacementUtils.countExtra(1, 0.2F, 1),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.BIG_BUSH_CLEARING_FILTER
		);

		BIG_BUSH_FOREST_COMMON.makeAndSetHolder(WWTreeVegetationFeatures.BIG_BUSH,
			generateTree,
			PlacementUtils.countExtra(5, 0.2F, 1),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.BIG_BUSH_CLEARING_FILTER
		);

		BIG_BUSH.makeAndSetHolder(WWTreeVegetationFeatures.BIG_BUSH,
			generateTree,
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome(),
			WWPlacementUtils.BIG_BUSH_CLEARING_FILTER
		);

		BIG_BUSH_WATER.makeAndSetHolder(WWTreeVegetationFeatures.BIG_BUSH,
			PlacementUtils.countExtra(1, 0.2F, 1),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BIG_BUSH_ON_SAND.makeAndSetHolder(WWTreeFeatures.LARGE_BUSH,
			generateTree,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			VegetationPlacements.TREE_THRESHOLD,
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.SANDSTONE)), 8),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.matchesBlocks(
					Direction.DOWN,
					Blocks.RED_SAND,
					Blocks.SAND,
					Blocks.GRASS_BLOCK
				)
			),
			BiomeFilter.biome()
		);

		// FALLEN
		registerFallen(MOSSY_FALLEN_OAK_AND_BIRCH, WWTreeVegetationFeatures.MOSSY_FALLEN_OAK_AND_BIRCH, CountPlacement.of(2));
		registerFallen(FALLEN_OAK_AND_BIRCH_DARK_FOREST, WWTreeVegetationFeatures.FALLEN_OAK_AND_BIRCH_DARK_FOREST, RarityFilter.onAverageOnceEvery(10));
		registerFallenDirect(FALLEN_BIRCH, WWTreeFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(6));
		registerFallen(FALLEN_BIRCH_AND_OAK, WWTreeVegetationFeatures.FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(7));
		registerFallen(FALLEN_BIRCH_AND_OAK_SPARSE, WWTreeVegetationFeatures.FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(10));
		registerFallen(FALLEN_BIRCH_AND_SPRUCE, WWTreeVegetationFeatures.FALLEN_BIRCH_AND_SPRUCE, RarityFilter.onAverageOnceEvery(7));
		registerFallen(FALLEN_OAK_AND_BIRCH_AND_SPRUCE, WWTreeVegetationFeatures.FALLEN_OAK_AND_BIRCH_AND_SPRUCE, RarityFilter.onAverageOnceEvery(8));
		registerFallen(MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE, WWTreeVegetationFeatures.MOSSY_FALLEN_OAK_AND_BIRCH_AND_SPRUCE, CountPlacement.of(2));
		registerFallen(FALLEN_SPRUCE_AND_OAK, WWTreeVegetationFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilter.onAverageOnceEvery(7));
		registerFallenDirect(FALLEN_SPRUCE, WWTreeFeatures.FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(6));
		registerFallenDirect(CLEAN_FALLEN_SPRUCE, WWTreeFeatures.CLEAN_FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(6));
		registerFallenDirect(CLEAN_LARGE_FALLEN_SPRUCE, WWTreeFeatures.CLEAN_LARGE_FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(25));
		registerFallenDirect(CLEAN_LARGE_FALLEN_SPRUCE_COMMON, WWTreeFeatures.CLEAN_LARGE_FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(8));
		registerFallenDirect(LARGE_FALLEN_SPRUCE, WWTreeFeatures.LARGE_FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(17));
		registerFallenDirect(LARGE_FALLEN_SPRUCE_COMMON, WWTreeFeatures.LARGE_FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(9));
		registerFallenDirect(FALLEN_DARK_OAK, WWTreeFeatures.LARGE_FALLEN_DARK_OAK, RarityFilter.onAverageOnceEvery(10));
		registerFallenDirect(FALLEN_DARK_OAK_COMMON, WWTreeFeatures.LARGE_FALLEN_DARK_OAK, RarityFilter.onAverageOnceEvery(5));
		registerFallenDirect(FALLEN_PALE_OAK, WWTreeFeatures.LARGE_FALLEN_PALE_OAK, RarityFilter.onAverageOnceEvery(4));
		registerFallenDirect(LARGE_FALLEN_JUNGLE, WWTreeFeatures.LARGE_FALLEN_JUNGLE, RarityFilter.onAverageOnceEvery(2));
		registerFallenDirect(LARGE_FALLEN_JUNGLE_COMMON, WWTreeFeatures.LARGE_FALLEN_JUNGLE, RarityFilter.onAverageOnceEvery(5));
		registerFallen(FALLEN_ACACIA_AND_OAK, WWTreeVegetationFeatures.FALLEN_ACACIA_AND_OAK, RarityFilter.onAverageOnceEvery(29));
		FALLEN_WILLOW_AND_OAK.makeAndSetHolder(
			Holder.direct(
				new ConfigSelectorFeature(
					configPredicates.getOrThrow(WWConfigPredicates.GENERATE_WILLOW),
					PlacementUtils.inlinePlaced(
						WWTreeVegetationFeatures.FALLEN_WILLOW_AND_OAK.getHolder(),
						RarityFilter.onAverageOnceEvery(4),
						InSquarePlacement.spread(),
						PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
					),
					PlacementUtils.inlinePlaced(
						WWTreeFeatures.MOSSY_FALLEN_OAK.getHolder(),
						RarityFilter.onAverageOnceEvery(4),
						InSquarePlacement.spread(),
						PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
						WWTreePlacements.fallenTreePlacementFilter()
					)
				)
			),
			BiomeFilter.biome()
		);
		registerFallen(FALLEN_CYPRESS_AND_OAK, WWTreeVegetationFeatures.FALLEN_CYPRESS_AND_OAK, RarityFilter.onAverageOnceEvery(6));
		registerFallenDirect(FALLEN_MANGROVE, WWTreeFeatures.FALLEN_MANGROVE, RarityFilter.onAverageOnceEvery(1));
		registerFallenDirect(FALLEN_PALM, WWTreeFeatures.FALLEN_PALM, RarityFilter.onAverageOnceEvery(60), generatePalm);
		registerFallenDirect(FALLEN_PALM_RARE, WWTreeFeatures.FALLEN_PALM, RarityFilter.onAverageOnceEvery(135), generatePalm);
		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(
			Holder.direct(
				new ConfigSelectorFeature(
					configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALM),
					WWTreeVegetationFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK.asInlinePlaced(),
					WWTreeVegetationFeatures.FALLEN_JUNGLE_AND_OAK.asInlinePlaced()
				)
			),
			RarityFilter.onAverageOnceEvery(25),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);
		registerFallen(FALLEN_CHERRY, WWTreeVegetationFeatures.FALLEN_CHERRY, RarityFilter.onAverageOnceEvery(5));
		registerFallenDirect(FALLEN_MAPLE, WWTreeFeatures.FALLEN_MAPLE, RarityFilter.onAverageOnceEvery(9));
		registerFallenDirect(FALLEN_POPLAR, WWTreeFeatures.FALLEN_POPLAR, RarityFilter.onAverageOnceEvery(9));

		// SNAPPED
		registerSnappedVariantsDirect(
			context,
			SNAPPED_OAK,
			SNAPPED_OAK_CLEARING,
			WWTreeFeatures.SNAPPED_OAK,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3),
			Blocks.OAK_SAPLING
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_BIRCH,
			SNAPPED_BIRCH_CLEARING,
			WWTreeFeatures.SNAPPED_BIRCH,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3),
			Blocks.BIRCH_SAPLING
		);

		registerSnappedVariants(
			context,
			SNAPPED_BIRCH_AND_OAK,
			SNAPPED_BIRCH_AND_OAK_CLEARING,
			WWTreeVegetationFeatures.SNAPPED_BIRCH_AND_OAK,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedVariants(
			context,
			SNAPPED_BIRCH_AND_SPRUCE,
			SNAPPED_BIRCH_AND_SPRUCE_CLEARING,
			WWTreeVegetationFeatures.SNAPPED_BIRCH_AND_SPRUCE,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedVariants(
			context,
			SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING,
			WWTreeVegetationFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnapped(
			context,
			SNAPPED_BIRCH_AND_JUNGLE,
			WWTreeVegetationFeatures.SNAPPED_BIRCH_AND_JUNGLE,
			RarityFilter.onAverageOnceEvery(52),
			false
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_SPRUCE,
			SNAPPED_SPRUCE_CLEARING,
			WWTreeFeatures.SNAPPED_SPRUCE,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3),
			Blocks.SPRUCE_SAPLING
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_SPRUCE_ON_SNOW,
			SNAPPED_SPRUCE_ON_SNOW_CLEARING,
			WWTreePlacements.SNAPPED_SPRUCE_ON_SNOW,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedDirect(
			context,
			SNAPPED_LARGE_SPRUCE,
			WWTreePlacements.LARGE_SNAPPED_SPRUCE,
			RarityFilter.onAverageOnceEvery(52),
			false
		);
		registerSnappedVariantsDirect(
			context,
			SNAPPED_LARGE_SPRUCE_COMMON,
			SNAPPED_LARGE_SPRUCE_CLEARING,
			WWTreePlacements.LARGE_SNAPPED_SPRUCE,
			RarityFilter.onAverageOnceEvery(5),
			RarityFilter.onAverageOnceEvery(4)
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_LARGE_SPRUCE_ON_SNOW,
			SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING,
			WWTreePlacements.LARGE_SNAPPED_SPRUCE_ON_SNOW,
			RarityFilter.onAverageOnceEvery(52),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_DARK_OAK,
			SNAPPED_DARK_OAK_CLEARING,
			WWTreePlacements.LARGE_SNAPPED_DARK_OAK,
			RarityFilter.onAverageOnceEvery(48),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedDirect(
			context,
			SNAPPED_PALE_OAK,
			WWTreePlacements.LARGE_SNAPPED_PALE_OAK,
			RarityFilter.onAverageOnceEvery(5),
			false
		);

		registerSnappedDirect(
			context,
			SNAPPED_JUNGLE,
			WWTreePlacements.SNAPPED_JUNGLE,
			RarityFilter.onAverageOnceEvery(52),
			false
		);
		registerSnappedDirect(
			context,
			SNAPPED_LARGE_JUNGLE,
			WWTreePlacements.LARGE_SNAPPED_JUNGLE,
			RarityFilter.onAverageOnceEvery(52),
			false
		);

		registerSnappedDirect(
			context,
			SNAPPED_ACACIA,
			WWTreePlacements.SNAPPED_ACACIA,
			RarityFilter.onAverageOnceEvery(52),
			false
		);
		registerSnapped(
			context,
			SNAPPED_ACACIA_AND_OAK,
			WWTreeVegetationFeatures.SNAPPED_ACACIA_AND_OAK,
			RarityFilter.onAverageOnceEvery(52),
			false
		);

		registerSnappedDirect(
			context,
			SNAPPED_CYPRESS,
			WWTreePlacements.SNAPPED_CYPRESS,
			RarityFilter.onAverageOnceEvery(52),
			false
		);

		registerSnappedDirect(
			context,
			SNAPPED_CHERRY,
			WWTreePlacements.MOSSY_FALLEN_CHERRY,
			RarityFilter.onAverageOnceEvery(58),
			false
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_MAPLE,
			SNAPPED_MAPLE_CLEARING,
			WWTreePlacements.SNAPPED_MAPLE,
			RarityFilter.onAverageOnceEvery(32),
			RarityFilter.onAverageOnceEvery(3)
		);

		registerSnappedVariantsDirect(
			context,
			SNAPPED_POPLAR,
			SNAPPED_POPLAR_CLEARING,
			WWTreePlacements.SNAPPED_POPLAR,
			RarityFilter.onAverageOnceEvery(36),
			RarityFilter.onAverageOnceEvery(3)
		);
	}

	// FALLEN
	private static void registerFallen(
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE VEGETATION FEATURE BRO");

		placement.makeAndSetHolder(
			feature,
			count,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);
	}

	private static void registerFallenDirect(
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree_vegetation/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE FEATURE BRO");

		placement.makeAndSetHolder(
			feature,
			count,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			WWTreePlacements.fallenTreePlacementFilter(),
			BiomeFilter.biome()
		);
	}

	private static void registerFallenDirect(
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementFilter preFilter
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree_vegetation/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE FEATURE BRO");

		placement.makeAndSetHolder(
			feature,
			preFilter,
			count,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			WWTreePlacements.fallenTreePlacementFilter(),
			BiomeFilter.biome()
		);
	}

	// SNAPPED
	private static void registerSnapped(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		boolean clearing
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE VEGETATION FEATURE BRO");

		final List<PlacementModifier> placementModifiers = snappedPlacementModifiers(context, count, null, clearing);
		placement.makeAndSetHolder(feature, placementModifiers);
	}

	private static void registerSnappedVariants(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibPlacedFeature clearingPlacement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementModifier clearingCount
	) {
		registerSnapped(context, placement, feature, count, false);
		registerSnapped(context, clearingPlacement, feature, clearingCount, true);
	}

	private static void registerSnappedDirect(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		Block saplingBlock,
		boolean clearing
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree_vegetation/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE FEATURE BRO");

		final List<PlacementModifier> placementModifiers = snappedPlacementModifiers(context, count, saplingBlock, clearing);
		placement.makeAndSetHolder(feature, placementModifiers);
	}

	private static void registerSnappedDirect(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibPlacedFeature feature,
		PlacementModifier count,
		boolean clearing
	) {
		if (feature.getKey().identifier().getPath().startsWith("tree_vegetation/")) throw new IllegalArgumentException("REGISTER THIS WITH A TREE FEATURE BRO");

		final List<PlacementModifier> placementModifiers = snappedPlacementModifiers(context, count, null, clearing);
		placement.makeAndSetHolder(new SequenceFeature(feature.asHolderSet()), placementModifiers);
	}

	private static void registerSnappedVariantsDirect(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibPlacedFeature clearingPlacement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementModifier clearingCount,
		Block saplingBlock
	) {
		registerSnappedDirect(context, placement, feature, count, saplingBlock, false);
		registerSnappedDirect(context, clearingPlacement, feature, clearingCount, saplingBlock, true);
	}

	private static void registerSnappedVariantsDirect(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibPlacedFeature clearingPlacement,
		FrozenLibPlacedFeature feature,
		PlacementModifier count,
		PlacementModifier clearingCount
	) {
		registerSnappedDirect(context, placement, feature, count, false);
		registerSnappedDirect(context, clearingPlacement, feature, clearingCount, true);
	}

	private static List<PlacementModifier> snappedPlacementModifiers(
		BootstrapContext<PlacedFeature> context,
		PlacementModifier count,
		@Nullable Block saplingBlock,
		boolean clearing
	) {
		final ImmutableList.Builder<PlacementModifier> placementModifiers = VegetationPlacements.treePlacementBase(count);
		if (saplingBlock != null) placementModifiers.add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(saplingBlock)));
		if (clearing) placementModifiers.add(WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED);

		final List<PlacementModifier> finalModifiers = new ArrayList<>(placementModifiers.build());
		finalModifiers.addFirst(
			ConfigPredicate.placementFilter(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_SNAPPED_TREE))
		);
		return finalModifiers;
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("tree_vegetation/" + name));
	}

	private WWTreeVegetationPlacements() {}
}
