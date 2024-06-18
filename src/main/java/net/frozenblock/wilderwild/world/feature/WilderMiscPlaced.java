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

package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.placementmodifier.LowerHeightmapPlacement;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
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

public final class WilderMiscPlaced {

	public static final FrozenPlacedFeature COARSE_PATH_RARE = WilderPlacementUtils.register("coarse_dirt_path_rare");
	public static final FrozenPlacedFeature GRAVEL_PATH_RARE = WilderPlacementUtils.register("gravel_path_rare");
	public static final FrozenPlacedFeature STONE_PATH_RARE = WilderPlacementUtils.register("stone_path_rare");
	public static final FrozenPlacedFeature COARSE_PATH_CLEARING = WilderPlacementUtils.register("coarse_dirt_path_clearing");
	public static final FrozenPlacedFeature GRAVEL_PATH_CLEARING = WilderPlacementUtils.register("gravel_path_clearing");
	public static final FrozenPlacedFeature ROOTED_DIRT_PATH_CLEARING = WilderPlacementUtils.register("rooted_dirt_path_clearing");
	// SWAMP
	public static final FrozenPlacedFeature DISK_MUD = WilderPlacementUtils.register("disk_mud");
	public static final FrozenPlacedFeature MUD_PATH = WilderPlacementUtils.register("mud_path");
	public static final FrozenPlacedFeature MUD_TRANSITION = WilderPlacementUtils.register("mud_transition");
	// TAIGA
	public static final FrozenPlacedFeature COARSE_PATH = WilderPlacementUtils.register("coarse_dirt_path");
	public static final FrozenPlacedFeature COARSE_PATH_5 = WilderPlacementUtils.register("coarse_dirt_path_5");
	public static final FrozenPlacedFeature FOREST_ROCK_TAIGA = WilderPlacementUtils.register("forest_rock_taiga");
	// CYPRESS WETLANDS
	public static final FrozenPlacedFeature UNDER_WATER_SAND_PATH = WilderPlacementUtils.register("under_water_sand_path");
	public static final FrozenPlacedFeature UNDER_WATER_GRAVEL_PATH = WilderPlacementUtils.register("under_water_gravel_path");
	public static final FrozenPlacedFeature UNDER_WATER_CLAY_PATH = WilderPlacementUtils.register("under_water_clay_path");
	// BEACH AND RIVER
	public static final FrozenPlacedFeature UNDER_WATER_CLAY_PATH_BEACH = WilderPlacementUtils.register("under_water_clay_path_beach");
	public static final FrozenPlacedFeature UNDER_WATER_GRAVEL_PATH_RIVER = WilderPlacementUtils.register("under_water_gravel_path_river");
	public static final FrozenPlacedFeature STONE_TRANSITION = WilderPlacementUtils.register("stone_transition");
	public static final FrozenPlacedFeature SMALL_SAND_TRANSITION = WilderPlacementUtils.register("small_sand_transition");
	public static final FrozenPlacedFeature BETA_BEACH_SAND_TRANSITION = WilderPlacementUtils.register("beta_beach_sand_transition");
	public static final FrozenPlacedFeature BETA_BEACH_GRAVEL_TRANSITION = WilderPlacementUtils.register("beta_beach_gravel_transition");
	public static final FrozenPlacedFeature SMALL_GRAVEL_TRANSITION = WilderPlacementUtils.register("small_gravel_transition");
	public static final FrozenPlacedFeature RIVER_POOL = WilderPlacementUtils.register("river_pool");
	public static final FrozenPlacedFeature SMALL_RIVER_POOL = WilderPlacementUtils.register("small_river_pool");
	// SAVANNA
	public static final FrozenPlacedFeature PACKED_MUD_PATH = WilderPlacementUtils.register("packed_mud_path");
	// JUNGLE
	public static final FrozenPlacedFeature MOSS_PATH = WilderPlacementUtils.register("moss_path");
	// DESERT
	public static final FrozenPlacedFeature ORE_PACKED_MUD = WilderPlacementUtils.register("ore_packed_mud");
	public static final FrozenPlacedFeature SANDSTONE_PATH = WilderPlacementUtils.register("sandstone_path");
	public static final FrozenPlacedFeature SCORCHED_SAND = WilderPlacementUtils.register("scorched_sand");
	public static final FrozenPlacedFeature SCORCHED_SAND_HUGE = WilderPlacementUtils.register("scorched_sand_huge");
	public static final FrozenPlacedFeature SAND_TRANSITION = WilderPlacementUtils.register("sand_transition");
	// BADLANDS
	public static final FrozenPlacedFeature COARSE_DIRT_PATH_SMALL = WilderPlacementUtils.register("coarse_dirt_path_small");
	public static final FrozenPlacedFeature PACKED_MUD_PATH_BADLANDS = WilderPlacementUtils.register("packed_mud_path_badlands");
	public static final FrozenPlacedFeature SCORCHED_RED_SAND = WilderPlacementUtils.register("scorched_red_sand");
	public static final FrozenPlacedFeature SCORCHED_RED_SAND_HUGE = WilderPlacementUtils.register("scorched_red_sand_huge");
	public static final FrozenPlacedFeature RED_SAND_TRANSITION = WilderPlacementUtils.register("red_sand_transition");
	// JELLYFISH CAVES
	public static final FrozenPlacedFeature EXTRA_GLOW_LICHEN = WilderPlacementUtils.register("extra_glow_lichen");
	public static final FrozenPlacedFeature STONE_POOL = WilderPlacementUtils.register("stone_pool");
	public static final FrozenPlacedFeature ORE_CALCITE = WilderPlacementUtils.register("ore_calcite");
	public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);
	public static final FrozenPlacedFeature JELLYFISH_STONE_POOL = WilderPlacementUtils.register("jellyfish_stone_pool");
	public static final FrozenPlacedFeature MESOGLEA_PILLAR = WilderPlacementUtils.register("blue_mesoglea_pillar");
	public static final FrozenPlacedFeature PURPLE_MESOGLEA_PILLAR = WilderPlacementUtils.register("purple_mesoglea_pillar");
	public static final FrozenPlacedFeature BLUE_MESOGLEA_PATH = WilderPlacementUtils.register("blue_mesoglea_path");
	public static final FrozenPlacedFeature PURPLE_MESOGLEA_PATH = WilderPlacementUtils.register("purple_mesoglea_path");
	// MAGMA CAVES
	public static final FrozenPlacedFeature MAGMA_LAVA_POOL = WilderPlacementUtils.register("magma_lava_pool");
	public static final FrozenPlacedFeature MAGMA_PATH = WilderPlacementUtils.register("magma_path");
	public static final FrozenPlacedFeature MAGMA_DISK = WilderPlacementUtils.register("magma_disk");
	public static final FrozenPlacedFeature MAGMA_PILE = WilderPlacementUtils.register("magma_pile");
	public static final FrozenPlacedFeature OBSIDIAN_DISK = WilderPlacementUtils.register("obsidian_disk");
	public static final FrozenPlacedFeature LAVA_SPRING_EXTRA = WilderPlacementUtils.register("lava_spring_extra");
	public static final FrozenPlacedFeature FIRE_PATCH_MAGMA = WilderPlacementUtils.register("fire_patch_magma");
	public static final FrozenPlacedFeature BASALT_PILE = WilderPlacementUtils.register("basalt_pile");
	public static final FrozenPlacedFeature GEYSER_PILE = WilderPlacementUtils.register("geyser_pile");
	public static final FrozenPlacedFeature NETHER_GEYSER = WilderPlacementUtils.register("nether_geyser");
	public static final FrozenPlacedFeature NETHER_LAVA_GEYSER = WilderPlacementUtils.register("nether_lava_geyser");
	public static final FrozenPlacedFeature GEYSER_UP = WilderPlacementUtils.register("geyser_up");
	public static final FrozenPlacedFeature GEYSER_DOWN = WilderPlacementUtils.register("geyser_down");
	public static final FrozenPlacedFeature DOWNWARDS_GEYSER_COLUMN = WilderPlacementUtils.register("downwards_geyser_column");
	public static final FrozenPlacedFeature DOWNWARDS_BASALT_COLUMN = WilderPlacementUtils.register("downwards_basalt_column");
	public static final FrozenPlacedFeature BASALT_SPIKE = WilderPlacementUtils.register("basalt_spike");
	public static final FrozenPlacedFeature LAVA_LAKE_EXTRA = WilderPlacementUtils.register("lava_lake_extra");
	public static final FrozenPlacedFeature FOSSIL_LAVA = WilderPlacementUtils.register("fossil_lava");
	// FROZEN CAVES
	public static final FrozenPlacedFeature PACKED_ICE_PATH = WilderPlacementUtils.register("packed_ice_path");
	public static final FrozenPlacedFeature PACKED_ICE_DISK = WilderPlacementUtils.register("packed_ice_disk");
	public static final FrozenPlacedFeature PACKED_ICE_COLUMN = WilderPlacementUtils.register("packed_ice_column");
	public static final FrozenPlacedFeature DOWNWARDS_PACKED_ICE_COLUMN = WilderPlacementUtils.register("downwards_packed_ice_column");
	public static final FrozenPlacedFeature PACKED_ICE_BIG_COLUMN = WilderPlacementUtils.register("packed_ice_big_column");
	public static final FrozenPlacedFeature ICE_DISK = WilderPlacementUtils.register("ice_disk");
	public static final FrozenPlacedFeature ICE_COLUMN = WilderPlacementUtils.register("ice_column");
	public static final FrozenPlacedFeature DOWNWARDS_ICE_COLUMN = WilderPlacementUtils.register("downwards_ice_column");
	public static final FrozenPlacedFeature ICE_PILE = WilderPlacementUtils.register("ice_pile");
	public static final FrozenPlacedFeature ORE_ICE = WilderPlacementUtils.register("ore_ice");
	public static final FrozenPlacedFeature SNOW_DISK_UPPER = WilderPlacementUtils.register("snow_disk_upper");
	public static final FrozenPlacedFeature POWDER_SNOW_DISK_UPPER = WilderPlacementUtils.register("powder_snow_disk_upper");
	public static final FrozenPlacedFeature SNOW_DISK_LOWER = WilderPlacementUtils.register("snow_disk_lower");
	public static final FrozenPlacedFeature POWDER_SNOW_DISK_LOWER = WilderPlacementUtils.register("powder_snow_disk_lower");
	// OASIS
	public static final FrozenPlacedFeature SAND_POOL = WilderPlacementUtils.register("sand_pool");
	public static final FrozenPlacedFeature MESSY_SAND_POOL = WilderPlacementUtils.register("messy_sand_pool");
	public static final FrozenPlacedFeature GRASS_PATH = WilderPlacementUtils.register("grass_path");
	public static final FrozenPlacedFeature MOSS_PATH_OASIS = WilderPlacementUtils.register("moss_path_oasis");
	public static final FrozenPlacedFeature DESERT_WELL = WilderPlacementUtils.register("desert_well");
	// BIRCH TAIGA
	public static final FrozenPlacedFeature COARSE_PATH_10 = WilderPlacementUtils.register("coarse_dirt_path_10");
	// ARID SAVANNA
	public static final FrozenPlacedFeature GRASS_PATH_RARE = WilderPlacementUtils.register("grass_path_rare");
	public static final FrozenPlacedFeature ARID_COARSE_PATH = WilderPlacementUtils.register("arid_coarse_dirt_path");
	// OLD GROWTH SNOWY TAIGA
	public static final FrozenPlacedFeature PILE_SNOW = WilderPlacementUtils.register("pile_snow");
	// TEMPERATE RAINFOREST & RAINFOREST
	public static final FrozenPlacedFeature MOSS_PILE = WilderPlacementUtils.register("moss_pile");
	public static final FrozenPlacedFeature BASIN_PODZOL = WilderPlacementUtils.register("basin_podzol");
	public static final FrozenPlacedFeature BASIN_MOSS = WilderPlacementUtils.register("basin_moss");
	public static final FrozenPlacedFeature MOSS_LAKE = WilderPlacementUtils.register("moss_lake");
	public static final FrozenPlacedFeature MOSS_LAKE_RARE = WilderPlacementUtils.register("moss_lake_rare");
	// MANGROVE SWAMP
	public static final FrozenPlacedFeature MUD_PILE = WilderPlacementUtils.register("mud_pile");
	public static final FrozenPlacedFeature BASIN_MUD = WilderPlacementUtils.register("basin_mud");
	public static final FrozenPlacedFeature MUD_LAKE = WilderPlacementUtils.register("mud_lake");
	// DYING FOREST
	public static final FrozenPlacedFeature COARSE_DIRT_DISK_AND_PILE = WilderPlacementUtils.register("coarse_dirt_disk_and_pile");
	// SNOW
	public static final FrozenPlacedFeature SNOW_BLANKET = WilderPlacementUtils.register("snow_blanket");
	public static final FrozenPlacedFeature SNOW_AND_ICE_TRANSITION = WilderPlacementUtils.register("snow_and_freeze_transition");
	public static final FrozenPlacedFeature SNOW_CARPET_RANDOM = WilderPlacementUtils.register("snow_carpet_random");

	private WilderMiscPlaced() {
		throw new UnsupportedOperationException("WilderMiscPlaced contains only static declarations.");
	}

	public static void registerMiscPlaced(@NotNull BootstapContext<PlacedFeature> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WilderSharedConstants.logWithModId("Registering WilderMiscPlaced for", true);

		COARSE_PATH_RARE.makeAndSetHolder(WilderMiscConfigured.COARSE_DIRT_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		GRAVEL_PATH_RARE.makeAndSetHolder(WilderMiscConfigured.GRAVEL_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		STONE_PATH_RARE.makeAndSetHolder(WilderMiscConfigured.STONE_PATH_RARE.getHolder(),
			RarityFilter.onAverageOnceEvery(72),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		COARSE_PATH_CLEARING.makeAndSetHolder(WilderMiscConfigured.COARSE_DIRT_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome()
		);

		GRAVEL_PATH_CLEARING.makeAndSetHolder(WilderMiscConfigured.GRAVEL_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome()
		);

		ROOTED_DIRT_PATH_CLEARING.makeAndSetHolder(WilderMiscConfigured.ROOTED_DIRT_PATH_CLEARING.getHolder(),
			CountPlacement.of(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			WilderPlacementUtils.TREE_CLEARING_FILTER_INVERTED,
			BiomeFilter.biome()
		);

		// SWAMP

		DISK_MUD.makeAndSetHolder(WilderMiscConfigured.DISK_MUD.getHolder(),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)),
			BiomeFilter.biome()
		);

		MUD_PATH.makeAndSetHolder(WilderMiscConfigured.MUD_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		MUD_TRANSITION.makeAndSetHolder(WilderMiscConfigured.MUD_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.MUD_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// TAIGA

		COARSE_PATH.makeAndSetHolder(WilderMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		COARSE_PATH_5.makeAndSetHolder(WilderMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		FOREST_ROCK_TAIGA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.FOREST_ROCK),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// CYPRESS WETLANDS

		UNDER_WATER_SAND_PATH.makeAndSetHolder(WilderMiscConfigured.UNDER_WATER_SAND_PATH.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			BiomeFilter.biome()
		);

		UNDER_WATER_GRAVEL_PATH.makeAndSetHolder(WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			BiomeFilter.biome()
		);

		UNDER_WATER_CLAY_PATH.makeAndSetHolder(WilderMiscConfigured.UNDER_WATER_CLAY_PATH.getHolder(),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			BiomeFilter.biome()
		);

		// BEACH AND RIVER

		UNDER_WATER_CLAY_PATH_BEACH.makeAndSetHolder(WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			BiomeFilter.biome()
		);

		UNDER_WATER_GRAVEL_PATH_RIVER.makeAndSetHolder(WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH_RIVER.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER)),
			BiomeFilter.biome()
		);

		STONE_TRANSITION.makeAndSetHolder(WilderMiscConfigured.STONE_TRANSITION_DISK.getHolder(),
			CountPlacement.of(6),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.STONE_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		SMALL_SAND_TRANSITION.makeAndSetHolder(WilderMiscConfigured.SMALL_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(6),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		BETA_BEACH_SAND_TRANSITION.makeAndSetHolder(WilderMiscConfigured.BETA_BEACH_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.SAND_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		BETA_BEACH_GRAVEL_TRANSITION.makeAndSetHolder(WilderMiscConfigured.SMALL_GRAVEL_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		SMALL_GRAVEL_TRANSITION.makeAndSetHolder(WilderMiscConfigured.SMALL_GRAVEL_TRANSITION_DISK.getHolder(),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		RIVER_POOL.makeAndSetHolder(WilderMiscConfigured.RIVER_POOL.getHolder(),
			CountPlacement.of(20), RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(64)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		SMALL_RIVER_POOL.makeAndSetHolder(WilderMiscConfigured.SMALL_RIVER_POOL.getHolder(),
			CountPlacement.of(8), RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(65), VerticalAnchor.absolute(72)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		// SAVANNA

		PACKED_MUD_PATH.makeAndSetHolder(WilderMiscConfigured.PACKED_MUD_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		// JUNGLE

		MOSS_PATH.makeAndSetHolder(WilderMiscConfigured.MOSS_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// DESERT

		ORE_PACKED_MUD.makeAndSetHolder(WilderMiscConfigured.ORE_PACKED_MUD.getHolder(),
			modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250)))
		);

		SANDSTONE_PATH.makeAndSetHolder(WilderMiscConfigured.SANDSTONE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		SCORCHED_SAND.makeAndSetHolder(WilderMiscConfigured.SCORCHED_SAND_DISK.getHolder(),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SCORCHED_SAND_HUGE.makeAndSetHolder(WilderMiscConfigured.SCORCHED_SAND_DISK_HUGE.getHolder(),
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SAND_TRANSITION.makeAndSetHolder(WilderMiscConfigured.SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// BADLANDS

		COARSE_DIRT_PATH_SMALL.makeAndSetHolder(WilderMiscConfigured.COARSE_DIRT_PATH_SMALL.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PACKED_MUD_PATH_BADLANDS.makeAndSetHolder(WilderMiscConfigured.PACKED_MUD_PATH_BADLANDS.getHolder(),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		SCORCHED_RED_SAND.makeAndSetHolder(WilderMiscConfigured.SCORCHED_RED_SAND_DISK.getHolder(),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		SCORCHED_RED_SAND_HUGE.makeAndSetHolder(WilderMiscConfigured.SCORCHED_RED_SAND_DISK_HUGE.getHolder(),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RED_SAND_TRANSITION.makeAndSetHolder(WilderMiscConfigured.RED_SAND_TRANSITION_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			LowerHeightmapPlacement.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WilderBlockTags.RED_SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// JELLYFISH CAVES

		EXTRA_GLOW_LICHEN.makeAndSetHolder(configuredFeatures.getOrThrow(CaveFeatures.GLOW_LICHEN),
			CountPlacement.of(UniformInt.of(104, 157)),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			InSquarePlacement.spread(),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
			BiomeFilter.biome()
		);

		STONE_POOL.makeAndSetHolder(WilderMiscConfigured.STONE_POOL.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(108)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		ORE_CALCITE.makeAndSetHolder(WilderMiscConfigured.ORE_CALCITE.getHolder(),
			modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		JELLYFISH_STONE_POOL.makeAndSetHolder(WilderMiscConfigured.STONE_POOL.getHolder(),
			CountPlacement.of(60),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MESOGLEA_PILLAR.makeAndSetHolder(WilderMiscConfigured.BLUE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_PILLAR.makeAndSetHolder(WilderMiscConfigured.PURPLE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		BLUE_MESOGLEA_PATH.makeAndSetHolder(WilderMiscConfigured.BLUE_MESOGLEA_PATH.getHolder(),
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_PATH.makeAndSetHolder(WilderMiscConfigured.PURPLE_MESOGLEA_PATH.getHolder(),
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			BiomeFilter.biome()
		);

		// MAGMA CAVES

		MAGMA_LAVA_POOL.makeAndSetHolder(WilderMiscConfigured.MAGMA_LAVA_POOL.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MAGMA_PATH.makeAndSetHolder(WilderMiscConfigured.MAGMA_AND_BASALT_PATH.getHolder(),
			modifiersWithCount(64, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		MAGMA_DISK.makeAndSetHolder(WilderMiscConfigured.MAGMA_DISK.getHolder(),
			modifiersWithCount(48, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		OBSIDIAN_DISK.makeAndSetHolder(WilderMiscConfigured.OBSIDIAN_DISK.getHolder(),
			modifiersWithCount(8, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		LAVA_SPRING_EXTRA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.SPRING_LAVA_OVERWORLD),
			CountPlacement.of(UniformInt.of(144, 200)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FIRE_PATCH_MAGMA.makeAndSetHolder(WilderMiscConfigured.FIRE_PATCH_MAGMA.getHolder(),
			CountPlacement.of(UniformInt.of(80, 130)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		BASALT_PILE.makeAndSetHolder(WilderMiscConfigured.BASALT_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(24, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		MAGMA_PILE.makeAndSetHolder(WilderMiscConfigured.MAGMA_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(32, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		GEYSER_PILE.makeAndSetHolder(WilderMiscConfigured.GEYSER_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 4)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		NETHER_GEYSER.makeAndSetHolder(WilderMiscConfigured.GEYSER_UP.getHolder(),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesTag(WilderBlockTags.NETHER_GEYSER_REPLACEABLE),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		NETHER_LAVA_GEYSER.makeAndSetHolder(WilderMiscConfigured.UPWARDS_GEYSER_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(8, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(WilderBlockTags.NETHER_GEYSER_REPLACEABLE),
					BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.LAVA),
					BlockPredicate.matchesFluids(Direction.UP.getNormal().above(), Fluids.LAVA),
					BlockPredicate.matchesFluids(Direction.UP.getNormal().above().above(), Fluids.LAVA)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_UP.makeAndSetHolder(WilderMiscConfigured.GEYSER_UP.getHolder(),
			CountPlacement.of(UniformInt.of(32, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		GEYSER_DOWN.makeAndSetHolder(WilderMiscConfigured.GEYSER_DOWN.getHolder(),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(WilderMiscConfigured.DOWNWARDS_GEYSER_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(8, 24)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_BASALT_COLUMN.makeAndSetHolder(WilderMiscConfigured.DOWNWARDS_BASALT_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(72, 120)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		BASALT_SPIKE.makeAndSetHolder(WilderMiscConfigured.BASALT_SPIKE.getHolder(),
			CountPlacement.of(UniformInt.of(16, 40)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		LAVA_LAKE_EXTRA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.LAKE_LAVA),
			CountPlacement.of(UniformInt.of(0, 8)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FOSSIL_LAVA.makeAndSetHolder(configuredFeatures.getOrThrow(CaveFeatures.FOSSIL_DIAMONDS),
			RarityFilter.onAverageOnceEvery(13),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(-24)),
			BiomeFilter.biome()
		);

		// FROZEN CAVES

		PACKED_ICE_PATH.makeAndSetHolder(WilderMiscConfigured.PACKED_ICE_PATH.getHolder(),
			modifiersWithCount(68, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		PACKED_ICE_DISK.makeAndSetHolder(WilderMiscConfigured.PACKED_ICE_DISK.getHolder(),
			modifiersWithCount(42, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(WilderMiscConfigured.PACKED_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(45, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(WilderMiscConfigured.DOWNWARDS_PACKED_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(40, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(WilderMiscConfigured.PACKED_ICE_BIG_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(24, 44)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ICE_DISK.makeAndSetHolder(WilderMiscConfigured.ICE_DISK.getHolder(),
			modifiersWithCount(32, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		ICE_COLUMN.makeAndSetHolder(WilderMiscConfigured.ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(10, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_ICE_COLUMN.makeAndSetHolder(WilderMiscConfigured.DOWNWARDS_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(10, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ICE_PILE.makeAndSetHolder(WilderMiscConfigured.ICE_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(60, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ORE_ICE.makeAndSetHolder(WilderMiscConfigured.ORE_ICE.getHolder(),
			modifiersWithCount(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		POWDER_SNOW_DISK_LOWER.makeAndSetHolder(WilderMiscConfigured.POWDER_SNOW_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		SNOW_DISK_LOWER.makeAndSetHolder(WilderMiscConfigured.SNOW_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		POWDER_SNOW_DISK_UPPER.makeAndSetHolder(WilderMiscConfigured.POWDER_SNOW_DISK.getHolder(),
			CountPlacement.of(13),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(48), VerticalAnchor.absolute(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		SNOW_DISK_UPPER.makeAndSetHolder(WilderMiscConfigured.SNOW_DISK.getHolder(),
			CountPlacement.of(20),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(48), VerticalAnchor.absolute(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		// OASIS

		SAND_POOL.makeAndSetHolder(WilderMiscConfigured.SAND_POOL.getHolder(),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(63), VerticalAnchor.aboveBottom(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MESSY_SAND_POOL.makeAndSetHolder(WilderMiscConfigured.MESSY_SAND_POOL.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(63), VerticalAnchor.aboveBottom(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		GRASS_PATH.makeAndSetHolder(WilderMiscConfigured.GRASS_PATH.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MOSS_PATH_OASIS.makeAndSetHolder(WilderMiscConfigured.MOSS_PATH_OASIS.getHolder(),
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		DESERT_WELL.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.DESERT_WELL),
			RarityFilter.onAverageOnceEvery(1000),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// BIRCH TAIGA

		COARSE_PATH_10.makeAndSetHolder(WilderMiscConfigured.COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// ARID SAVANNA

		GRASS_PATH_RARE.makeAndSetHolder(WilderMiscConfigured.GRASS_PATH.getHolder(),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		ARID_COARSE_PATH.makeAndSetHolder(WilderMiscConfigured.ARID_COARSE_PATH.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// OLD GROWTH SNOWY TAIGA

		PILE_SNOW.makeAndSetHolder(WilderMiscConfigured.SNOW.getHolder(),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING),
			BiomeFilter.biome()
		);

		// TEMPERATE RAINFOREST & RAINFOREST

		MOSS_PILE.makeAndSetHolder(WilderMiscConfigured.MOSS_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BASIN_PODZOL.makeAndSetHolder(WilderMiscConfigured.BASIN_PODZOL.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		BASIN_MOSS.makeAndSetHolder(WilderMiscConfigured.BASIN_MOSS.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MOSS_LAKE.makeAndSetHolder(WilderMiscConfigured.MOSS_LAKE.getHolder(),
			CountPlacement.of(1),
			RarityFilter.onAverageOnceEvery(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		MOSS_LAKE_RARE.makeAndSetHolder(WilderMiscConfigured.MOSS_LAKE.getHolder(),
			CountPlacement.of(1),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		// MANGROVE SWAMP

		MUD_PILE.makeAndSetHolder(WilderMiscConfigured.MUD_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		BASIN_MUD.makeAndSetHolder(WilderMiscConfigured.BASIN_MUD.getHolder(),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MUD_LAKE.makeAndSetHolder(WilderMiscConfigured.MUD_LAKE.getHolder(),
			CountPlacement.of(1),
			RarityFilter.onAverageOnceEvery(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		// DYING FOREST

		COARSE_DIRT_DISK_AND_PILE.makeAndSetHolder(WilderMiscConfigured.COARSE_DIRT_DISK_AND_PILE.getHolder(),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		// SNOW

		SNOW_BLANKET.makeAndSetHolder(WilderMiscConfigured.SNOW_BLANKET.getHolder(),
			CountPlacement.of(1),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		SNOW_AND_ICE_TRANSITION.makeAndSetHolder(WilderMiscConfigured.SNOW_AND_ICE_TRANSITION_DISK.getHolder(),
			CountPlacement.of(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		SNOW_CARPET_RANDOM.makeAndSetHolder(WilderMiscConfigured.SNOW_CARPET_RANDOM.getHolder(),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
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
