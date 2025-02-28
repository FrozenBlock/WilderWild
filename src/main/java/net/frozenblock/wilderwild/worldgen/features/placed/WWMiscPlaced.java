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

package net.frozenblock.wilderwild.worldgen.features.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils;
import net.frozenblock.wilderwild.worldgen.features.configured.WWMiscConfigured;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class WWMiscPlaced {
	public static final FrozenLibPlacedFeature MYCELIUM_GROWTH_BONEMEAL = WWPlacementUtils.register("mycelium_growth_bonemeal");

	public static final FrozenLibPlacedFeature COARSE_PATH_RARE = WWPlacementUtils.register("coarse_dirt_path_rare");
	public static final FrozenLibPlacedFeature GRAVEL_PATH_RARE = WWPlacementUtils.register("gravel_path_rare");
	public static final FrozenLibPlacedFeature STONE_PATH_RARE = WWPlacementUtils.register("stone_path_rare");
	public static final FrozenLibPlacedFeature COARSE_PATH_CLEARING = WWPlacementUtils.register("coarse_dirt_path_clearing");
	public static final FrozenLibPlacedFeature GRAVEL_PATH_CLEARING = WWPlacementUtils.register("gravel_path_clearing");
	public static final FrozenLibPlacedFeature ROOTED_DIRT_PATH_CLEARING = WWPlacementUtils.register("rooted_dirt_path_clearing");

	// SWAMP
	public static final FrozenLibPlacedFeature DISK_MUD = WWPlacementUtils.register("disk_mud");
	public static final FrozenLibPlacedFeature MUD_PATH = WWPlacementUtils.register("mud_path");
	public static final FrozenLibPlacedFeature MUD_TRANSITION = WWPlacementUtils.register("mud_transition");

	// TAIGA
	public static final FrozenLibPlacedFeature COARSE_PATH = WWPlacementUtils.register("coarse_dirt_path");
	public static final FrozenLibPlacedFeature COARSE_PATH_5 = WWPlacementUtils.register("coarse_dirt_path_5");
	public static final FrozenLibPlacedFeature FOREST_ROCK_TAIGA = WWPlacementUtils.register("forest_rock_taiga");

	// CYPRESS WETLANDS
	public static final FrozenLibPlacedFeature UNDER_WATER_SAND_PATH = WWPlacementUtils.register("under_water_sand_path");
	public static final FrozenLibPlacedFeature UNDER_WATER_GRAVEL_PATH = WWPlacementUtils.register("under_water_gravel_path");
	public static final FrozenLibPlacedFeature UNDER_WATER_CLAY_PATH = WWPlacementUtils.register("under_water_clay_path");

	// BEACH AND RIVER
	public static final FrozenLibPlacedFeature UNDER_WATER_CLAY_PATH_BEACH = WWPlacementUtils.register("under_water_clay_path_beach");
	public static final FrozenLibPlacedFeature UNDER_WATER_GRAVEL_PATH_RIVER = WWPlacementUtils.register("under_water_gravel_path_river");
	public static final FrozenLibPlacedFeature STONE_TRANSITION = WWPlacementUtils.register("stone_transition");
	public static final FrozenLibPlacedFeature SMALL_SAND_TRANSITION = WWPlacementUtils.register("small_sand_transition");
	public static final FrozenLibPlacedFeature BETA_BEACH_SAND_TRANSITION = WWPlacementUtils.register("beta_beach_sand_transition");
	public static final FrozenLibPlacedFeature BETA_BEACH_GRAVEL_TRANSITION = WWPlacementUtils.register("beta_beach_gravel_transition");
	public static final FrozenLibPlacedFeature SMALL_GRAVEL_TRANSITION = WWPlacementUtils.register("small_gravel_transition");
	public static final FrozenLibPlacedFeature RIVER_POOL = WWPlacementUtils.register("river_pool");
	public static final FrozenLibPlacedFeature SMALL_RIVER_POOL = WWPlacementUtils.register("small_river_pool");

	// SAVANNA
	public static final FrozenLibPlacedFeature PACKED_MUD_PATH = WWPlacementUtils.register("packed_mud_path");

	// JUNGLE
	public static final FrozenLibPlacedFeature MOSS_PATH = WWPlacementUtils.register("moss_path");

	// DESERT
	public static final FrozenLibPlacedFeature ORE_PACKED_MUD = WWPlacementUtils.register("ore_packed_mud");
	public static final FrozenLibPlacedFeature SANDSTONE_PATH = WWPlacementUtils.register("sandstone_path");
	public static final FrozenLibPlacedFeature SCORCHED_SAND = WWPlacementUtils.register("scorched_sand");
	public static final FrozenLibPlacedFeature SCORCHED_SAND_HUGE = WWPlacementUtils.register("scorched_sand_huge");
	public static final FrozenLibPlacedFeature SAND_TRANSITION = WWPlacementUtils.register("sand_transition");

	// BADLANDS
	public static final FrozenLibPlacedFeature COARSE_DIRT_PATH_SMALL = WWPlacementUtils.register("coarse_dirt_path_small");
	public static final FrozenLibPlacedFeature PACKED_MUD_PATH_BADLANDS = WWPlacementUtils.register("packed_mud_path_badlands");
	public static final FrozenLibPlacedFeature SCORCHED_RED_SAND = WWPlacementUtils.register("scorched_red_sand");
	public static final FrozenLibPlacedFeature SCORCHED_RED_SAND_HUGE = WWPlacementUtils.register("scorched_red_sand_huge");
	public static final FrozenLibPlacedFeature RED_SAND_TRANSITION = WWPlacementUtils.register("red_sand_transition");

	// OASIS
	public static final FrozenLibPlacedFeature GRASS_PATH = WWPlacementUtils.register("grass_path");
	public static final FrozenLibPlacedFeature MOSS_PATH_OASIS = WWPlacementUtils.register("moss_path_oasis");
	public static final FrozenLibPlacedFeature DESERT_WELL = WWPlacementUtils.register("desert_well");

	// BIRCH TAIGA
	public static final FrozenLibPlacedFeature COARSE_PATH_10 = WWPlacementUtils.register("coarse_dirt_path_10");

	// ARID SAVANNA
	public static final FrozenLibPlacedFeature GRASS_PATH_RARE = WWPlacementUtils.register("grass_path_rare");
	public static final FrozenLibPlacedFeature ARID_COARSE_PATH = WWPlacementUtils.register("arid_coarse_dirt_path");

	// OLD GROWTH SNOWY TAIGA
	public static final FrozenLibPlacedFeature SNOW_PILE = WWPlacementUtils.register("snow_pile");

	// TEMPERATE RAINFOREST & RAINFOREST
	public static final FrozenLibPlacedFeature MOSS_PILE = WWPlacementUtils.register("moss_pile");
	public static final FrozenLibPlacedFeature BASIN_PODZOL = WWPlacementUtils.register("basin_podzol");
	public static final FrozenLibPlacedFeature BASIN_MOSS = WWPlacementUtils.register("basin_moss");
	public static final FrozenLibPlacedFeature MOSS_LAKE = WWPlacementUtils.register("moss_lake");
	public static final FrozenLibPlacedFeature MOSS_LAKE_RARE = WWPlacementUtils.register("moss_lake_rare");

	// MANGROVE SWAMP
	public static final FrozenLibPlacedFeature MUD_PILE = WWPlacementUtils.register("mud_pile");
	public static final FrozenLibPlacedFeature BASIN_MUD = WWPlacementUtils.register("basin_mud");
	public static final FrozenLibPlacedFeature MUD_LAKE = WWPlacementUtils.register("mud_lake");

	// DYING FOREST
	public static final FrozenLibPlacedFeature COARSE_DIRT_DISK_AND_PILE = WWPlacementUtils.register("coarse_dirt_disk_and_pile");
	public static final FrozenLibPlacedFeature COARSE_DIRT_DISK_AND_PILE_RARE = WWPlacementUtils.register("coarse_dirt_disk_and_pile_rare");
	public static final FrozenLibPlacedFeature STONE_DISK_AND_PILE_COMMON = WWPlacementUtils.register("stone_disk_and_pile_common");
	public static final FrozenLibPlacedFeature STONE_DISK_AND_PILE = WWPlacementUtils.register("stone_disk_and_pile");
	public static final FrozenLibPlacedFeature STONE_DISK_AND_PILE_RARE = WWPlacementUtils.register("stone_disk_and_pile_rare");
	public static final FrozenLibPlacedFeature COARSE_TRANSITION_DISK = WWPlacementUtils.register("coarse_dirt_transition_disk");

	// SNOW
	public static final FrozenLibPlacedFeature SNOW_BLANKET = WWPlacementUtils.register("snow_blanket");
	public static final FrozenLibPlacedFeature SNOW_AND_ICE_TRANSITION = WWPlacementUtils.register("snow_and_freeze_transition");

	private WWMiscPlaced() {
		throw new UnsupportedOperationException("WilderMiscPlaced contains only static declarations.");
	}

	public static void registerMiscPlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.logWithModId("Registering WilderMiscPlaced for", true);

		MYCELIUM_GROWTH_BONEMEAL.makeAndSetHolder(WWMiscConfigured.SINGLE_MYCELIUM_GROWTH.getHolder(),
			PlacementUtils.isEmpty()
		);

		COARSE_PATH_RARE.makeAndSetHolder(WWMiscConfigured.COARSE_DIRT_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		GRAVEL_PATH_RARE.makeAndSetHolder(WWMiscConfigured.GRAVEL_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		STONE_PATH_RARE.makeAndSetHolder(WWMiscConfigured.STONE_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(72),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		COARSE_PATH_CLEARING.makeAndSetHolder(WWMiscConfigured.COARSE_DIRT_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		GRAVEL_PATH_CLEARING.makeAndSetHolder(WWMiscConfigured.GRAVEL_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		ROOTED_DIRT_PATH_CLEARING.makeAndSetHolder(WWMiscConfigured.ROOTED_DIRT_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			WWPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// SWAMP

		DISK_MUD.makeAndSetHolder(WWMiscConfigured.DISK_MUD.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)),
			BiomeFilter.biome()
		);

		MUD_PATH.makeAndSetHolder(WWMiscConfigured.MUD_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		MUD_TRANSITION.makeAndSetHolder(WWMiscConfigured.MUD_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.MUD_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// TAIGA

		COARSE_PATH.makeAndSetHolder(WWMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		COARSE_PATH_5.makeAndSetHolder(WWMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		FOREST_ROCK_TAIGA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.FOREST_ROCK),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// CYPRESS WETLANDS

		UNDER_WATER_SAND_PATH.makeAndSetHolder(WWMiscConfigured.UNDER_WATER_SAND_PATH.getHolder(),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		UNDER_WATER_GRAVEL_PATH.makeAndSetHolder(WWMiscConfigured.UNDER_WATER_GRAVEL_PATH.getHolder(),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		UNDER_WATER_CLAY_PATH.makeAndSetHolder(WWMiscConfigured.UNDER_WATER_CLAY_PATH.getHolder(),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// BEACH AND RIVER

		UNDER_WATER_CLAY_PATH_BEACH.makeAndSetHolder(WWMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		UNDER_WATER_GRAVEL_PATH_RIVER.makeAndSetHolder(WWMiscConfigured.UNDER_WATER_GRAVEL_PATH_RIVER.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		STONE_TRANSITION.makeAndSetHolder(WWMiscConfigured.STONE_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.STONE_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		SMALL_SAND_TRANSITION.makeAndSetHolder(WWMiscConfigured.SMALL_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		BETA_BEACH_SAND_TRANSITION.makeAndSetHolder(WWMiscConfigured.BETA_BEACH_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.SAND_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		BETA_BEACH_GRAVEL_TRANSITION.makeAndSetHolder(WWMiscConfigured.SMALL_GRAVEL_TRANSITION_DISK.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		SMALL_GRAVEL_TRANSITION.makeAndSetHolder(WWMiscConfigured.SMALL_GRAVEL_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		RIVER_POOL.makeAndSetHolder(WWMiscConfigured.RIVER_POOL.getHolder(),
			CountPlacement.of(20), RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(64)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		SMALL_RIVER_POOL.makeAndSetHolder(WWMiscConfigured.SMALL_RIVER_POOL.getHolder(),
			CountPlacement.of(8), RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(65), VerticalAnchor.absolute(72)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		// SAVANNA

		PACKED_MUD_PATH.makeAndSetHolder(WWMiscConfigured.PACKED_MUD_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// JUNGLE

		MOSS_PATH.makeAndSetHolder(WWMiscConfigured.MOSS_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// DESERT

		ORE_PACKED_MUD.makeAndSetHolder(WWMiscConfigured.ORE_PACKED_MUD.getHolder(),
			modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250)))
		);

		SANDSTONE_PATH.makeAndSetHolder(WWMiscConfigured.SANDSTONE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		SCORCHED_SAND.makeAndSetHolder(WWMiscConfigured.SCORCHED_SAND_DISK.getHolder(),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);

		SCORCHED_SAND_HUGE.makeAndSetHolder(WWMiscConfigured.SCORCHED_SAND_DISK_HUGE.getHolder(),
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);

		SAND_TRANSITION.makeAndSetHolder(WWMiscConfigured.SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// BADLANDS

		COARSE_DIRT_PATH_SMALL.makeAndSetHolder(WWMiscConfigured.COARSE_DIRT_PATH_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		PACKED_MUD_PATH_BADLANDS.makeAndSetHolder(WWMiscConfigured.PACKED_MUD_PATH_BADLANDS.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		SCORCHED_RED_SAND.makeAndSetHolder(WWMiscConfigured.SCORCHED_RED_SAND_DISK.getHolder(),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);

		SCORCHED_RED_SAND_HUGE.makeAndSetHolder(WWMiscConfigured.SCORCHED_RED_SAND_DISK_HUGE.getHolder(),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);

		RED_SAND_TRANSITION.makeAndSetHolder(WWMiscConfigured.RED_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWBlockTags.RED_SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// OASIS

		GRASS_PATH.makeAndSetHolder(WWMiscConfigured.GRASS_PATH.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		MOSS_PATH_OASIS.makeAndSetHolder(WWMiscConfigured.MOSS_PATH_OASIS.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		DESERT_WELL.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.DESERT_WELL),
			RarityFilter.onAverageOnceEvery(1000),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// BIRCH TAIGA

		COARSE_PATH_10.makeAndSetHolder(WWMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// ARID SAVANNA

		GRASS_PATH_RARE.makeAndSetHolder(WWMiscConfigured.GRASS_PATH.getHolder(),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		ARID_COARSE_PATH.makeAndSetHolder(WWMiscConfigured.ARID_COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BiomeFilter.biome()
		);

		// OLD GROWTH SNOWY TAIGA

		SNOW_PILE.makeAndSetHolder(WWMiscConfigured.SNOW.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING),
			BiomeFilter.biome()
		);

		// TEMPERATE RAINFOREST & RAINFOREST

		MOSS_PILE.makeAndSetHolder(WWMiscConfigured.MOSS_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BASIN_PODZOL.makeAndSetHolder(WWMiscConfigured.BASIN_PODZOL.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		BASIN_MOSS.makeAndSetHolder(WWMiscConfigured.BASIN_MOSS.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MOSS_LAKE.makeAndSetHolder(WWMiscConfigured.MOSS_LAKE.getHolder(),
			RarityFilter.onAverageOnceEvery(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		MOSS_LAKE_RARE.makeAndSetHolder(WWMiscConfigured.MOSS_LAKE.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		// MANGROVE SWAMP

		MUD_PILE.makeAndSetHolder(WWMiscConfigured.MUD_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BASIN_MUD.makeAndSetHolder(WWMiscConfigured.BASIN_MUD.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MUD_LAKE.makeAndSetHolder(WWMiscConfigured.MUD_LAKE.getHolder(),
			RarityFilter.onAverageOnceEvery(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		// DYING FOREST

		COARSE_DIRT_DISK_AND_PILE.makeAndSetHolder(WWMiscConfigured.COARSE_DIRT_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		COARSE_DIRT_DISK_AND_PILE_RARE.makeAndSetHolder(WWMiscConfigured.COARSE_DIRT_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(35),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		STONE_DISK_AND_PILE_COMMON.makeAndSetHolder(WWMiscConfigured.STONE_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		STONE_DISK_AND_PILE.makeAndSetHolder(WWMiscConfigured.STONE_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		STONE_DISK_AND_PILE_RARE.makeAndSetHolder(WWMiscConfigured.STONE_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(18),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);

		COARSE_TRANSITION_DISK.makeAndSetHolder(WWMiscConfigured.COARSE_TRANSITION_DISK.getHolder(),
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.COARSE_DIRT)),
			BiomeFilter.biome()
		);

		// SNOW

		SNOW_BLANKET.makeAndSetHolder(WWMiscConfigured.SNOW_BLANKET.getHolder(),
			BiomeFilter.biome()
		);

		SNOW_AND_ICE_TRANSITION.makeAndSetHolder(WWMiscConfigured.SNOW_AND_ICE_TRANSITION_DISK.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			BiomeFilter.biome()
		);
	}

	@Contract("_, _ -> new")
	private static @Unmodifiable List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
		return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
	}

	@Contract("_, _ -> new")
	private static @Unmodifiable List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
		return modifiers(CountPlacement.of(count), heightModifier);
	}

}
