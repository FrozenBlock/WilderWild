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

package net.frozenblock.wilderwild.data.worldgen.feature.placed;

import java.util.List;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWCaveConfigured;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.ReplaceablePredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.Unmodifiable;

public final class WWCavePlaced {
	// MESOGLEA CAVES
	public static final FrozenLibPlacedFeature ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibPlacedFeature MESOGLEA_CAVES_STONE_POOL = register("mesoglea_caves_stone_pool");
	public static final FrozenLibPlacedFeature BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenLibPlacedFeature PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenLibPlacedFeature MESOGLEA_PATHS = register("mesoglea_paths");
	public static final FrozenLibPlacedFeature DOWNWARD_BLUE_MESOGLEA = register("upside_down_blue_mesoglea");
	public static final FrozenLibPlacedFeature DOWNWARD_PURPLE_MESOGLEA = register("upside_down_purple_mesoglea");
	public static final FrozenLibPlacedFeature NEMATOCYST_BLUE = register("nematocyst_blue");
	public static final FrozenLibPlacedFeature NEMATOCYST_PURPLE = register("nematocyst_purple");
	public static final FrozenLibPlacedFeature MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple");
	public static final FrozenLibPlacedFeature MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibPlacedFeature GABBRO_LAVA_POOL = register("gabbro_lava_pool");
	public static final FrozenLibPlacedFeature GABBRO_MAGMA_PATH = register("gabbro_magma_path");
	public static final FrozenLibPlacedFeature LAVA_SPRING_EXTRA = register("lava_spring_extra");
	public static final FrozenLibPlacedFeature ORE_GABBRO = register("ore_gabbro");
	public static final FrozenLibPlacedFeature GABBRO_DISK = register("gabbro_disk");
	public static final FrozenLibPlacedFeature GABBRO_PILE = register("gabbro_pile");
	public static final FrozenLibPlacedFeature NETHER_GEOTHERMAL_VENT = register("nether_geothermal_vent");
	public static final FrozenLibPlacedFeature NETHER_LAVA_GEOTHERMAL_VENT = register("nether_lava_geothermal_vent");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_LAVA = register("geothermal_vent_lava");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_NORTH = register("geothermal_vent_north");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_EAST = register("geothermal_vent_east");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_SOUTH = register("geothermal_vent_south");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_WEST = register("geothermal_vent_west");
	public static final FrozenLibPlacedFeature DOWNWARDS_GEOTHERMAL_VENT_COLUMN = register("downwards_geothermal_vent_column");
	public static final FrozenLibPlacedFeature DOWNWARDS_GABBRO_COLUMN = register("downwards_gabbro_column");
	public static final FrozenLibPlacedFeature LAVA_LAKE_EXTRA = register("lava_lake_extra");
	public static final FrozenLibPlacedFeature FOSSIL_LAVA = register("fossil_lava");
	public static final FrozenLibPlacedFeature UPSIDE_DOWN_MAGMA = register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE_WG = register("decorative_icicle_cluster_surface_wg");
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE = register("decorative_icicle_cluster_surface");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PATCH_CEILING = register("ice_patch_ceiling");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PATCH = register("fragile_ice_patch");

	public static void registerCavePlaced(BootstrapContext<PlacedFeature> entries) {
		WWConstants.logWithModId("Registering WWCavePlaced for", true);
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(WWCaveConfigured.ORE_CALCITE,
			modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		MESOGLEA_CAVES_STONE_POOL.makeAndSetHolder(WWCaveConfigured.STONE_POOL,
			CountPlacement.of(60),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(WWCaveConfigured.BLUE_MESOGLEA_COLUMN,
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.matchesBlocks(Blocks.WATER), 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(WWCaveConfigured.PURPLE_MESOGLEA_COLUMN,
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.matchesBlocks(Blocks.WATER), 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MESOGLEA_PATHS.makeAndSetHolder(WWCaveConfigured.MESOGLEA_PATHS,
			CountPlacement.of(30),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			BiomeFilter.biome()
		);

		DOWNWARD_BLUE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.DOWNWARD_BLUE_MESOGLEA,
			CountPlacement.of(12),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		DOWNWARD_PURPLE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.DOWNWARD_PURPLE_MESOGLEA,
			CountPlacement.of(12),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		NEMATOCYST_BLUE.makeAndSetHolder(WWCaveConfigured.NEMATOCYST_BLUE,
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(WWCaveConfigured.NEMATOCYST_PURPLE,
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WWCaveConfigured.MESOGLEA_CLUSTER_PURPLE,
			CountPlacement.of(UniformInt.of(9, 15)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(WWCaveConfigured.MESOGLEA_CLUSTER_BLUE,
			CountPlacement.of(UniformInt.of(6, 13)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(WWCaveConfigured.LARGE_MESOGLEA_PURPLE,
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(WWCaveConfigured.LARGE_MESOGLEA_BLUE,
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		// MAGMATIC CAVES
		GABBRO_LAVA_POOL.makeAndSetHolder(WWCaveConfigured.GABBRO_LAVA_POOL,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		GABBRO_MAGMA_PATH.makeAndSetHolder(WWCaveConfigured.GABBRO_MAGMA_PATH,
			modifiersWithCount(72, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		LAVA_SPRING_EXTRA.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.SPRING_LAVA_OVERWORLD),
			CountPlacement.of(UniformInt.of(144, 200)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		ORE_GABBRO.makeAndSetHolder(WWCaveConfigured.ORE_GABBRO,
			modifiersWithCount(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		GABBRO_DISK.makeAndSetHolder(WWCaveConfigured.GABBRO_DISK,
			modifiersWithCount(48, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		GABBRO_PILE.makeAndSetHolder(WWCaveConfigured.GABBRO_PILE,
			CountPlacement.of(UniformInt.of(32, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		NETHER_GEOTHERMAL_VENT.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_UP,
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesTag(WWBlockTags.NETHER_GEOTHERMAL_VENT_REPLACEABLE),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		NETHER_LAVA_GEOTHERMAL_VENT.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_COLUMN,
			CountPlacement.of(UniformInt.of(8, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(WWBlockTags.NETHER_GEOTHERMAL_VENT_REPLACEABLE),
					SearchInDirectionBlockPredicate.hasLavaAbove(3)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_LAVA.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_UP,
			CountPlacement.of(UniformInt.of(64, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					SearchInDirectionBlockPredicate.hasLavaAbove(1)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_UP.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_UP,
			CountPlacement.of(UniformInt.of(64, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.anyOf(
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK),
					BlockPredicate.allOf(
						BlockPredicate.solid(),
						SearchInDirectionBlockPredicate.hasLavaAbove(1)
					)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_DOWN,
			CountPlacement.of(UniformInt.of(48, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.UP,
				BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_NORTH.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_NORTH,
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					new ReplaceablePredicate(Direction.NORTH.getUnitVec3i()),
					BlockPredicate.matchesBlocks(Direction.SOUTH, WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_EAST.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_EAST,
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					new ReplaceablePredicate(Direction.EAST.getUnitVec3i()),
					BlockPredicate.matchesBlocks(Direction.WEST, WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_SOUTH.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_SOUTH,
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					new ReplaceablePredicate(Direction.SOUTH.getUnitVec3i()),
					BlockPredicate.matchesBlocks(Direction.NORTH, WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_WEST.makeAndSetHolder(WWCaveConfigured.GEOTHERMAL_VENT_WEST,
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					new ReplaceablePredicate(Direction.WEST.getUnitVec3i()),
					BlockPredicate.matchesBlocks(Direction.EAST, WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		DOWNWARDS_GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_GEOTHERMAL_VENT_COLUMN,
			CountPlacement.of(UniformInt.of(8, 24)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK), BlockPredicate.replaceable(), 12),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_GABBRO_COLUMN,
			CountPlacement.of(UniformInt.of(72, 120)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		LAVA_LAKE_EXTRA.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.LAKE_LAVA),
			CountPlacement.of(UniformInt.of(0, 8)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FOSSIL_LAVA.makeAndSetHolder(features.getOrThrow(CaveFeatures.FOSSIL_DIAMONDS),
			RarityFilter.onAverageOnceEvery(20),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(-24)),
			BiomeFilter.biome()
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(WWCaveConfigured.UPSIDE_DOWN_MAGMA,
			CountPlacement.of(64),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 4),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		// FROZEN CAVES
		DECORATIVE_ICICLE_CLUSTER_SURFACE_WG.makeAndSetHolder(WWCaveConfigured.DECORATIVE_ICICLE_CLUSTER,
			CountPlacement.of(UniformInt.of(20, 30)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		DECORATIVE_ICICLE_CLUSTER_SURFACE.makeAndSetHolder(WWCaveConfigured.DECORATIVE_ICICLE_CLUSTER,
			CountPlacement.of(UniformInt.of(22, 30)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		FRAGILE_ICE_PATCH_CEILING.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PATCH_CEILING,
			CountPlacement.of(32),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PATCH,
			CountPlacement.of(32),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);
	}

	@Unmodifiable
	private static  List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier modifier) {
		return List.of(countModifier, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
	}

	@Unmodifiable
	private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier modifier) {
		return modifiers(CountPlacement.of(count), modifier);
	}
}
