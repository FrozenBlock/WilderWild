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

import lombok.experimental.UtilityClass;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.features.WWCaveFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWMiscFeatures;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWMiscPlacements {
	// TERMITE MOUND
	public static final FrozenLibPlacedFeature TERMITE_MOUND = register("termite_mound");

	// COARSE DIRT
	public static final FrozenLibPlacedFeature COARSE_DIRT_BLOB_AND_PATCH = register("coarse_dirt_blob_and_patch");
	public static final FrozenLibPlacedFeature COARSE_DIRT_BLOB_AND_PATCH_RARE = register("coarse_dirt_blob_and_patch_rare");

	// PODZOL
	public static final FrozenLibPlacedFeature PODZOL_BASIN = register("podzol_basin");

	// MUD
	public static final FrozenLibPlacedFeature MUD_BASIN = register("mud_basin");
	public static final FrozenLibPlacedFeature MUD_LAKE = register("mud_lake");
	public static final FrozenLibPlacedFeature MUD_TRANSITION = register("mud_transition");

	// CLAY
	public static final FrozenLibPlacedFeature DISK_CLAY_LARGE = register("disk_clay_large");
	public static final FrozenLibPlacedFeature DISK_CLAY_COMMON = register("disk_clay_common");

	// MOSS
	public static final FrozenLibPlacedFeature MOSS_BLOB = register("moss_blob");
	public static final FrozenLibPlacedFeature MOSS_BASIN = register("moss_basin");
	public static final FrozenLibPlacedFeature MOSS_LAKE = register("moss_lake");
	public static final FrozenLibPlacedFeature MOSS_LAKE_RARE = register("moss_lake_rare");

	// PALE MOSS
	public static final FrozenLibPlacedFeature PALE_MOSS_BLOB = register("pale_moss_blob");

	// GRAVEL
	public static final FrozenLibPlacedFeature GRAVEL_TRANSITION = register("gravel_transition");
	public static final FrozenLibPlacedFeature BETA_BEACH_GRAVEL_TRANSITION = register("beta_beach_gravel_transition");

	// STONE
	public static final FrozenLibPlacedFeature FOREST_ROCK_TAIGA_SPARSE = register("forest_rock_taiga_sparse");
	public static final FrozenLibPlacedFeature STONE_TRANSITION = register("stone_transition");

	// SAND
	public static final FrozenLibPlacedFeature DISK_SAND_SPARSE = register("disk_sand_sparse");
	public static final FrozenLibPlacedFeature SCORCHED_SAND = register("scorched_sand");
	public static final FrozenLibPlacedFeature SCORCHED_SAND_HUGE = register("scorched_sand_huge");
	public static final FrozenLibPlacedFeature SAND_TRANSITION = register("sand_transition");
	public static final FrozenLibPlacedFeature SMALL_SAND_TRANSITION = register("small_sand_transition");
	public static final FrozenLibPlacedFeature BETA_BEACH_SAND_TRANSITION = register("beta_beach_sand_transition");

	// OASIS
	public static final FrozenLibPlacedFeature LAKE_OASIS = register("lake_oasis");

	// RED SAND
	public static final FrozenLibPlacedFeature SCORCHED_RED_SAND = register("scorched_red_sand");
	public static final FrozenLibPlacedFeature SCORCHED_RED_SAND_HUGE = register("scorched_red_sand_huge");
	public static final FrozenLibPlacedFeature RED_SAND_TRANSITION = register("red_sand_transition");

	// SNOW
	public static final FrozenLibPlacedFeature SNOW_BLOB = register("snow_blob");
	public static final FrozenLibPlacedFeature SNOW_BLANKET = register("snow_blanket");
	public static final FrozenLibPlacedFeature SNOW_AND_ICE_TRANSITION = register("snow_and_ice_transition");

	// ICE
	public static final FrozenLibPlacedFeature BRITTLE_ICE_DISK_SURFACE = register("brittle_ice_disk_surface");
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE_WG = register("decorative_icicle_cluster_surface_wg");
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE = register("decorative_icicle_cluster_surface");

	// GEOTHERMAL VENT
	public static final FrozenLibPlacedFeature NETHER_GEOTHERMAL_VENT = register("nether_geothermal_vent");
	public static final FrozenLibPlacedFeature NETHER_LAVA_GEOTHERMAL_VENT = register("nether_lava_geothermal_vent");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// TERMITE MOUND
		TERMITE_MOUND.makeAndSetHolder(WWMiscFeatures.TERMITE_MOUND,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TERMITE)),
			RarityFilter.onAverageOnceEvery(45),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
			BiomeFilter.biome()
		);

		// COARSE DIRT
		COARSE_DIRT_BLOB_AND_PATCH.makeAndSetHolder(WWMiscFeatures.COARSE_DIRT_BLOB_AND_PATCH,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_COARSE_DIRT)),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		COARSE_DIRT_BLOB_AND_PATCH_RARE.makeAndSetHolder(WWMiscFeatures.COARSE_DIRT_BLOB_AND_PATCH,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_COARSE_DIRT)),
			RarityFilter.onAverageOnceEvery(35),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		// PODZOL
		PODZOL_BASIN.makeAndSetHolder(WWMiscFeatures.PODZOL_BASIN,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_BASIN)),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		// MUD
		MUD_BASIN.makeAndSetHolder(WWMiscFeatures.MUD_BASIN,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_BASIN)),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MUD_LAKE.makeAndSetHolder(WWMiscFeatures.MUD_LAKE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_LAKE)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		MUD_TRANSITION.makeAndSetHolder(WWMiscFeatures.MUD_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_MUD_TRANSITION)),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.MUD)),
			BiomeFilter.biome()
		);

		// CLAY
		DISK_CLAY_LARGE.makeAndSetHolder(WWMiscFeatures.DISK_CLAY_LARGE,
			CountPlacement.of(UniformInt.of(1, 2)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER))
		);

		DISK_CLAY_COMMON.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.DISK_CLAY),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER))
		);

		// MOSS
		MOSS_BLOB.makeAndSetHolder(WWMiscFeatures.MOSS_BLOB,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_MOSS)),
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		MOSS_BASIN.makeAndSetHolder(WWMiscFeatures.MOSS_BASIN,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_BASIN)),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MOSS_LAKE.makeAndSetHolder(WWMiscFeatures.MOSS_LAKE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_LAKE)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		MOSS_LAKE_RARE.makeAndSetHolder(WWMiscFeatures.MOSS_LAKE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_LAKE)),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING),
			BiomeFilter.biome()
		);

		// PALE MOSS
		PALE_MOSS_BLOB.makeAndSetHolder(WWMiscFeatures.PALE_MOSS_BLOB,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALE_MOSS)),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.PALE_OAK_SAPLING),
			BiomeFilter.biome()
		);

		// GRAVEL
		GRAVEL_TRANSITION.makeAndSetHolder(WWMiscFeatures.GRAVEL_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_GRAVEL_TRANSITION)),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		BETA_BEACH_GRAVEL_TRANSITION.makeAndSetHolder(WWMiscFeatures.GRAVEL_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_GRAVEL_TRANSITION)),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		// STONE
		FOREST_ROCK_TAIGA_SPARSE.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.FOREST_ROCK),
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TAIGA_BOULDER)),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		STONE_TRANSITION.makeAndSetHolder(WWMiscFeatures.STONE_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_STONE_TRANSITION)),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// SAND
		DISK_SAND_SPARSE.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.DISK_SAND),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesFluids(Fluids.WATER))
		);

		SCORCHED_SAND.makeAndSetHolder(WWMiscFeatures.SCORCHED_SAND,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SCORCHED_SAND)),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SCORCHED_SAND_HUGE.makeAndSetHolder(WWMiscFeatures.SCORCHED_SAND_HUGE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SCORCHED_SAND)),
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SAND_TRANSITION.makeAndSetHolder(WWMiscFeatures.SAND_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SAND_TRANSITION)),
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		SMALL_SAND_TRANSITION.makeAndSetHolder(WWMiscFeatures.SMALL_SAND_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SAND_TRANSITION)),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		BETA_BEACH_SAND_TRANSITION.makeAndSetHolder(WWMiscFeatures.BETA_BEACH_SAND_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SAND_TRANSITION)),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SAND_TRANSITION_PLACEABLE)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, 48, 66),
			BiomeFilter.biome()
		);

		// OASIS
		LAKE_OASIS.makeAndSetHolder(WWMiscFeatures.LAKE_OASIS,
			FrozenLibPlacementUtil.modifiers(
				CountPlacement.of(1),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)),
				OffsetPlacement.vertical(BiasedToBottomInt.of(-1, 0))
			)
		);

		// RED SAND
		SCORCHED_RED_SAND.makeAndSetHolder(WWMiscFeatures.SCORCHED_RED_SAND,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SCORCHED_RED_SAND)),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SCORCHED_RED_SAND_HUGE.makeAndSetHolder(WWMiscFeatures.SCORCHED_RED_SAND_HUGE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SCORCHED_RED_SAND)),
			RarityFilter.onAverageOnceEvery(226),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		RED_SAND_TRANSITION.makeAndSetHolder(WWMiscFeatures.RED_SAND_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_RED_SAND_TRANSITION)),
			CountPlacement.of(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SAND_TRANSITION_PLACEABLE)),
			BiomeFilter.biome()
		);

		// SNOW
		SNOW_BLOB.makeAndSetHolder(WWMiscFeatures.SNOW_BLOB,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SNOW_BLOB)),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			PlacementUtils.filteredByBlockSurvival(Blocks.SPRUCE_SAPLING),
			BiomeFilter.biome()
		);

		SNOW_BLANKET.makeAndSetHolder(WWMiscFeatures.SNOW_BLANKET,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SNOW_BELOW_TREES)),
			BiomeFilter.biome()
		);

		SNOW_AND_ICE_TRANSITION.makeAndSetHolder(WWMiscFeatures.SNOW_AND_ICE_TRANSITION,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SNOW_TRANSITION)),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		// ICE
		BRITTLE_ICE_DISK_SURFACE.makeAndSetHolder(WWMiscFeatures.BRITTLE_ICE_DISK_SURFACE,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_BRITTLE_ICE)),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome()
		);

		DECORATIVE_ICICLE_CLUSTER_SURFACE_WG.makeAndSetHolder(WWMiscFeatures.DECORATIVE_ICICLE_CLUSTER,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_DECORATIVE_ICICLE)),
			CountPlacement.of(UniformInt.of(20, 30)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		DECORATIVE_ICICLE_CLUSTER_SURFACE.makeAndSetHolder(WWMiscFeatures.DECORATIVE_ICICLE_CLUSTER,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_DECORATIVE_ICICLE)),
			CountPlacement.of(UniformInt.of(22, 30)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		// GEOTHERMAL VENT
		NETHER_GEOTHERMAL_VENT.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_NETHER_GEOTHERMAL_VENT)),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesTag(WWFeatureBlockTags.NETHER_GEOTHERMAL_VENT_REPLACEABLE),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		NETHER_LAVA_GEOTHERMAL_VENT.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_STACK,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_NETHER_GEOTHERMAL_VENT)),
			CountPlacement.of(UniformInt.of(8, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(WWFeatureBlockTags.NETHER_GEOTHERMAL_VENT_REPLACEABLE),
					SearchInDirectionBlockPredicate.hasLavaAbove(3)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("misc/" + name));
	}
}
