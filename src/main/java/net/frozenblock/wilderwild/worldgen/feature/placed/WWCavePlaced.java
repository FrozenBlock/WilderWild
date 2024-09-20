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

package net.frozenblock.wilderwild.worldgen.feature.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.feature.WWPlacementUtils;
import static net.frozenblock.wilderwild.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.worldgen.feature.configured.WWCaveConfigured;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
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

public final class WWCavePlaced {
	// MESOGLEA CAVES
	public static final FrozenPlacedFeature EXTRA_GLOW_LICHEN = WWPlacementUtils.register("extra_glow_lichen");
	public static final FrozenPlacedFeature STONE_POOL = WWPlacementUtils.register("stone_pool");
	public static final FrozenPlacedFeature ORE_CALCITE = WWPlacementUtils.register("ore_calcite");
	public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);
	public static final FrozenPlacedFeature MESOGLEA_CAVES_STONE_POOL = WWPlacementUtils.register("mesoglea_caves_stone_pool");
	public static final FrozenPlacedFeature MESOGLEA_PILLAR = WWPlacementUtils.register("blue_mesoglea_pillar");
	public static final FrozenPlacedFeature PURPLE_MESOGLEA_PILLAR = WWPlacementUtils.register("purple_mesoglea_pillar");
	public static final FrozenPlacedFeature BLUE_MESOGLEA_PATH = WWPlacementUtils.register("blue_mesoglea_path");
	public static final FrozenPlacedFeature PURPLE_MESOGLEA_PATH = WWPlacementUtils.register("purple_mesoglea_path");
	public static final FrozenPlacedFeature BLUE_MESOGLEA = register("blue_mesoglea");
	public static final FrozenPlacedFeature UPSIDE_DOWN_BLUE_MESOGLEA = register("upside_down_blue_mesoglea");
	public static final FrozenPlacedFeature PURPLE_MESOGLEA = register("purple_mesoglea");
	public static final FrozenPlacedFeature UPSIDE_DOWN_PURPLE_MESOGLEA = register("upside_down_purple_mesoglea");
	public static final FrozenPlacedFeature NEMATOCYST_BLUE = register("nematocyst_blue");
	public static final FrozenPlacedFeature NEMATOCYST_PURPLE = register("nematocyst_purple");
	public static final FrozenPlacedFeature MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple");
	public static final FrozenPlacedFeature MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue");
	public static final FrozenPlacedFeature LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple");
	public static final FrozenPlacedFeature LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenPlacedFeature MAGMA_LAVA_POOL = WWPlacementUtils.register("magma_lava_pool");
	public static final FrozenPlacedFeature MAGMA_PATH = WWPlacementUtils.register("magma_path");
	public static final FrozenPlacedFeature MAGMA_DISK = WWPlacementUtils.register("magma_disk");
	public static final FrozenPlacedFeature MAGMA_PILE = WWPlacementUtils.register("magma_pile");
	public static final FrozenPlacedFeature OBSIDIAN_DISK = WWPlacementUtils.register("obsidian_disk");
	public static final FrozenPlacedFeature LAVA_SPRING_EXTRA = WWPlacementUtils.register("lava_spring_extra");
	public static final FrozenPlacedFeature FIRE_PATCH_MAGMA = WWPlacementUtils.register("fire_patch_magma");
	public static final FrozenPlacedFeature BASALT_PILE = WWPlacementUtils.register("basalt_pile");
	public static final FrozenPlacedFeature GEYSER_PILE = WWPlacementUtils.register("geyser_pile");
	public static final FrozenPlacedFeature NETHER_GEYSER = WWPlacementUtils.register("nether_geyser");
	public static final FrozenPlacedFeature NETHER_LAVA_GEYSER = WWPlacementUtils.register("nether_lava_geyser");
	public static final FrozenPlacedFeature GEYSER_UP = WWPlacementUtils.register("geyser_up");
	public static final FrozenPlacedFeature GEYSER_DOWN = WWPlacementUtils.register("geyser_down");
	public static final FrozenPlacedFeature DOWNWARDS_GEYSER_COLUMN = WWPlacementUtils.register("downwards_geyser_column");
	public static final FrozenPlacedFeature DOWNWARDS_BASALT_COLUMN = WWPlacementUtils.register("downwards_basalt_column");
	public static final FrozenPlacedFeature BASALT_SPIKE = WWPlacementUtils.register("basalt_spike");
	public static final FrozenPlacedFeature LAVA_LAKE_EXTRA = WWPlacementUtils.register("lava_lake_extra");
	public static final FrozenPlacedFeature FOSSIL_LAVA = WWPlacementUtils.register("fossil_lava");
	public static final FrozenPlacedFeature UPSIDE_DOWN_MAGMA = register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenPlacedFeature PACKED_ICE_PATH = WWPlacementUtils.register("packed_ice_path");
	public static final FrozenPlacedFeature PACKED_ICE_DISK = WWPlacementUtils.register("packed_ice_disk");
	public static final FrozenPlacedFeature PACKED_ICE_COLUMN = WWPlacementUtils.register("packed_ice_column");
	public static final FrozenPlacedFeature DOWNWARDS_PACKED_ICE_COLUMN = WWPlacementUtils.register("downwards_packed_ice_column");
	public static final FrozenPlacedFeature PACKED_ICE_BIG_COLUMN = WWPlacementUtils.register("packed_ice_big_column");
	public static final FrozenPlacedFeature ICE_DISK = WWPlacementUtils.register("ice_disk");
	public static final FrozenPlacedFeature ICE_COLUMN = WWPlacementUtils.register("ice_column");
	public static final FrozenPlacedFeature DOWNWARDS_ICE_COLUMN = WWPlacementUtils.register("downwards_ice_column");
	public static final FrozenPlacedFeature ICE_PILE = WWPlacementUtils.register("ice_pile");
	public static final FrozenPlacedFeature ORE_ICE = WWPlacementUtils.register("ore_ice");
	public static final FrozenPlacedFeature SNOW_DISK_UPPER = WWPlacementUtils.register("snow_disk_upper");
	public static final FrozenPlacedFeature POWDER_SNOW_DISK_UPPER = WWPlacementUtils.register("powder_snow_disk_upper");
	public static final FrozenPlacedFeature SNOW_DISK_LOWER = WWPlacementUtils.register("snow_disk_lower");
	public static final FrozenPlacedFeature POWDER_SNOW_DISK_LOWER = WWPlacementUtils.register("powder_snow_disk_lower");

	private WWCavePlaced() {
		throw new UnsupportedOperationException("WilderCavePlaced contains only static declarations.");
	}

	public static void registerCavePlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.log("Registering WWCavePlaced.", true);

		// MESOGLEA CAVES

		EXTRA_GLOW_LICHEN.makeAndSetHolder(configuredFeatures.getOrThrow(CaveFeatures.GLOW_LICHEN),
			CountPlacement.of(UniformInt.of(104, 157)),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			InSquarePlacement.spread(),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13),
			BiomeFilter.biome()
		);

		STONE_POOL.makeAndSetHolder(WWCaveConfigured.STONE_POOL.getHolder(),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(108)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		ORE_CALCITE.makeAndSetHolder(WWCaveConfigured.ORE_CALCITE.getHolder(),
			modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		MESOGLEA_CAVES_STONE_POOL.makeAndSetHolder(WWCaveConfigured.STONE_POOL.getHolder(),
			CountPlacement.of(60),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MESOGLEA_PILLAR.makeAndSetHolder(WWCaveConfigured.BLUE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_PILLAR.makeAndSetHolder(WWCaveConfigured.PURPLE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		BLUE_MESOGLEA_PATH.makeAndSetHolder(WWCaveConfigured.BLUE_MESOGLEA_PATH.getHolder(),
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_PATH.makeAndSetHolder(WWCaveConfigured.PURPLE_MESOGLEA_PATH.getHolder(),
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			BiomeFilter.biome()
		);

		BLUE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.BLUE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		UPSIDE_DOWN_BLUE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.UPSIDE_DOWN_BLUE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.PURPLE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		UPSIDE_DOWN_PURPLE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.UPSIDE_DOWN_PURPLE_MESOGLEA.getHolder(),
			CountPlacement.of(9),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		NEMATOCYST_BLUE.makeAndSetHolder(WWCaveConfigured.NEMATOCYST_BLUE.getHolder(),
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(WWCaveConfigured.NEMATOCYST_PURPLE.getHolder(),
			CountPlacement.of(ConstantInt.of(99)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WWCaveConfigured.MESOGLEA_CLUSTER_PURPLE.getHolder(),
			CountPlacement.of(UniformInt.of(9, 15)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(WWCaveConfigured.MESOGLEA_CLUSTER_BLUE.getHolder(),
			CountPlacement.of(UniformInt.of(6, 13)), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(WWCaveConfigured.LARGE_MESOGLEA_PURPLE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(WWCaveConfigured.LARGE_MESOGLEA_BLUE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 5)), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome()
		);

		// MAGMATIC CAVES

		MAGMA_LAVA_POOL.makeAndSetHolder(WWCaveConfigured.MAGMA_LAVA_POOL.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MAGMA_PATH.makeAndSetHolder(WWCaveConfigured.MAGMA_AND_BASALT_PATH.getHolder(),
			modifiersWithCount(64, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		MAGMA_DISK.makeAndSetHolder(WWCaveConfigured.MAGMA_DISK.getHolder(),
			modifiersWithCount(48, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		OBSIDIAN_DISK.makeAndSetHolder(WWCaveConfigured.OBSIDIAN_DISK.getHolder(),
			modifiersWithCount(8, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		LAVA_SPRING_EXTRA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.SPRING_LAVA_OVERWORLD),
			CountPlacement.of(UniformInt.of(144, 200)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FIRE_PATCH_MAGMA.makeAndSetHolder(WWCaveConfigured.FIRE_PATCH_MAGMA.getHolder(),
			CountPlacement.of(UniformInt.of(80, 130)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		BASALT_PILE.makeAndSetHolder(WWCaveConfigured.BASALT_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(24, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		MAGMA_PILE.makeAndSetHolder(WWCaveConfigured.MAGMA_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(32, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		GEYSER_PILE.makeAndSetHolder(WWCaveConfigured.GEYSER_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(1, 4)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		NETHER_GEYSER.makeAndSetHolder(WWCaveConfigured.GEYSER_UP.getHolder(),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.matchesTag(WWBlockTags.NETHER_GEYSER_REPLACEABLE),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		NETHER_LAVA_GEYSER.makeAndSetHolder(WWCaveConfigured.UPWARDS_GEYSER_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(8, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.matchesTag(WWBlockTags.NETHER_GEYSER_REPLACEABLE),
					BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.LAVA),
					BlockPredicate.matchesFluids(Direction.UP.getNormal().above(), Fluids.LAVA),
					BlockPredicate.matchesFluids(Direction.UP.getNormal().above().above(), Fluids.LAVA)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_UP.makeAndSetHolder(WWCaveConfigured.GEYSER_UP.getHolder(),
			CountPlacement.of(UniformInt.of(32, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		GEYSER_DOWN.makeAndSetHolder(WWCaveConfigured.GEYSER_DOWN.getHolder(),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_GEYSER_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(8, 24)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_BASALT_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_BASALT_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(72, 120)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			BiomeFilter.biome()
		);

		BASALT_SPIKE.makeAndSetHolder(WWCaveConfigured.BASALT_SPIKE.getHolder(),
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

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(WWCaveConfigured.UPSIDE_DOWN_MAGMA.getHolder(),
			CountPlacement.of(72),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 4),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		// FROZEN CAVES

		PACKED_ICE_PATH.makeAndSetHolder(WWCaveConfigured.PACKED_ICE_PATH.getHolder(),
			modifiersWithCount(68, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		PACKED_ICE_DISK.makeAndSetHolder(WWCaveConfigured.PACKED_ICE_DISK.getHolder(),
			modifiersWithCount(42, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(WWCaveConfigured.PACKED_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(45, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_PACKED_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(40, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(WWCaveConfigured.PACKED_ICE_BIG_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(24, 44)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ICE_DISK.makeAndSetHolder(WWCaveConfigured.ICE_DISK.getHolder(),
			modifiersWithCount(32, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		ICE_COLUMN.makeAndSetHolder(WWCaveConfigured.ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(10, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		DOWNWARDS_ICE_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_ICE_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(10, 20)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ICE_PILE.makeAndSetHolder(WWCaveConfigured.ICE_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(60, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		ORE_ICE.makeAndSetHolder(WWCaveConfigured.ORE_ICE.getHolder(),
			modifiersWithCount(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		POWDER_SNOW_DISK_LOWER.makeAndSetHolder(WWCaveConfigured.POWDER_SNOW_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		SNOW_DISK_LOWER.makeAndSetHolder(WWCaveConfigured.SNOW_DISK.getHolder(),
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(48)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		POWDER_SNOW_DISK_UPPER.makeAndSetHolder(WWCaveConfigured.POWDER_SNOW_DISK.getHolder(),
			CountPlacement.of(13),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(48), VerticalAnchor.absolute(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		SNOW_DISK_UPPER.makeAndSetHolder(WWCaveConfigured.SNOW_DISK.getHolder(),
			CountPlacement.of(20),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(48), VerticalAnchor.absolute(256)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
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
