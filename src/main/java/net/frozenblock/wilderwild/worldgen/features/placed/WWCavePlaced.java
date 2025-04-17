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

package net.frozenblock.wilderwild.worldgen.features.placed;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.worldgen.feature.api.block_predicate.SearchInDirectionBlockPredicate;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWPlacementUtils.register;
import net.frozenblock.wilderwild.worldgen.features.configured.WWCaveConfigured;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

public final class WWCavePlaced {
	// MESOGLEA CAVES
	public static final FrozenLibPlacedFeature ORE_CALCITE = WWPlacementUtils.register("ore_calcite");
	public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);
	public static final FrozenLibPlacedFeature MESOGLEA_CAVES_STONE_POOL = WWPlacementUtils.register("mesoglea_caves_stone_pool");
	public static final FrozenLibPlacedFeature BLUE_MESOGLEA_COLUMN = WWPlacementUtils.register("blue_mesoglea_column");
	public static final FrozenLibPlacedFeature PURPLE_MESOGLEA_COLUMN = WWPlacementUtils.register("purple_mesoglea_column");
	public static final FrozenLibPlacedFeature MESOGLEA_PATHS = WWPlacementUtils.register("mesoglea_paths");
	public static final FrozenLibPlacedFeature BLUE_MESOGLEA = register("blue_mesoglea");
	public static final FrozenLibPlacedFeature DOWNWARD_BLUE_MESOGLEA = register("upside_down_blue_mesoglea");
	public static final FrozenLibPlacedFeature PURPLE_MESOGLEA = register("purple_mesoglea");
	public static final FrozenLibPlacedFeature DOWNWARD_PURLE_MESOGLEA = register("upside_down_purple_mesoglea");
	public static final FrozenLibPlacedFeature NEMATOCYST_BLUE = register("nematocyst_blue");
	public static final FrozenLibPlacedFeature NEMATOCYST_PURPLE = register("nematocyst_purple");
	public static final FrozenLibPlacedFeature MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple");
	public static final FrozenLibPlacedFeature MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibPlacedFeature MAGMA_LAVA_POOL = WWPlacementUtils.register("magma_lava_pool");
	public static final FrozenLibPlacedFeature MAGMA_PATH = WWPlacementUtils.register("magma_path");
	public static final FrozenLibPlacedFeature MAGMA_DISK = WWPlacementUtils.register("magma_disk");
	public static final FrozenLibPlacedFeature MAGMA_PILE = WWPlacementUtils.register("magma_pile");
	public static final FrozenLibPlacedFeature OBSIDIAN_DISK = WWPlacementUtils.register("obsidian_disk");
	public static final FrozenLibPlacedFeature LAVA_SPRING_EXTRA = WWPlacementUtils.register("lava_spring_extra");
	public static final FrozenLibPlacedFeature FIRE_PATCH_MAGMA = WWPlacementUtils.register("fire_patch_magma");
	public static final FrozenLibPlacedFeature ORE_GABBRO = WWPlacementUtils.register("ore_gabbro");
	public static final FrozenLibPlacedFeature GABBRO_DISK = WWPlacementUtils.register("gabbro_disk");
	public static final FrozenLibPlacedFeature GABBRO_PILE = WWPlacementUtils.register("gabbro_pile");
	public static final FrozenLibPlacedFeature NETHER_GEYSER = WWPlacementUtils.register("nether_geyser");
	public static final FrozenLibPlacedFeature NETHER_LAVA_GEYSER = WWPlacementUtils.register("nether_lava_geyser");
	public static final FrozenLibPlacedFeature GEYSER_LAVA = WWPlacementUtils.register("geyser_lava");
	public static final FrozenLibPlacedFeature GEYSER_UP = WWPlacementUtils.register("geyser_up");
	public static final FrozenLibPlacedFeature GEYSER_DOWN = WWPlacementUtils.register("geyser_down");
	public static final FrozenLibPlacedFeature GEYSER_NORTH = WWPlacementUtils.register("geyser_north");
	public static final FrozenLibPlacedFeature GEYSER_EAST = WWPlacementUtils.register("geyser_east");
	public static final FrozenLibPlacedFeature GEYSER_SOUTH = WWPlacementUtils.register("geyser_south");
	public static final FrozenLibPlacedFeature GEYSER_WEST = WWPlacementUtils.register("geyser_west");
	public static final FrozenLibPlacedFeature DOWNWARDS_GEYSER_COLUMN = WWPlacementUtils.register("downwards_geyser_column");
	public static final FrozenLibPlacedFeature DOWNWARDS_GABBRO_COLUMN = WWPlacementUtils.register("downwards_gabbro_column");
	public static final FrozenLibPlacedFeature LAVA_LAKE_EXTRA = WWPlacementUtils.register("lava_lake_extra");
	public static final FrozenLibPlacedFeature FOSSIL_LAVA = WWPlacementUtils.register("fossil_lava");
	public static final FrozenLibPlacedFeature UPSIDE_DOWN_MAGMA = register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenLibPlacedFeature ICICLE_CLUSTER = WWPlacementUtils.register("icicle_cluster");
	public static final FrozenLibPlacedFeature CAVE_ICICLES = WWPlacementUtils.register("cave_icicles");
	public static final FrozenLibPlacedFeature ICICLES_SURFACE_WG = WWPlacementUtils.register("icicles_surface_wg");
	public static final FrozenLibPlacedFeature ICICLES_SURFACE = WWPlacementUtils.register("icicles_surface");
	public static final FrozenLibPlacedFeature ICE_PATHS = WWPlacementUtils.register("ice_paths");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_DISK = WWPlacementUtils.register("fragile_ice_disk");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PILE = WWPlacementUtils.register("fragile_ice_pile");
	public static final FrozenLibPlacedFeature HANGING_PACKED_ICE = WWPlacementUtils.register("hanging_packed_ice");
	public static final FrozenLibPlacedFeature ICE_PATCH_CEILING = WWPlacementUtils.register("ice_patch_ceiling");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_COLUMN_PATCH = WWPlacementUtils.register("fragile_ice_column_patch");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PATCH = WWPlacementUtils.register("fragile_ice_patch");
	public static final FrozenLibPlacedFeature DIORITE_PATCH = WWPlacementUtils.register("diorite_patch");
	public static final FrozenLibPlacedFeature DIORITE_PATCH_CEILING = WWPlacementUtils.register("diorite_patch_ceiling");
	public static final FrozenLibPlacedFeature ORE_DIORITE_EXTRA = WWPlacementUtils.register("ore_diorite_extra");

	private WWCavePlaced() {
		throw new UnsupportedOperationException("WWCavePlaced contains only static declarations.");
	}

	public static void registerCavePlaced(@NotNull BootstrapContext<PlacedFeature> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.log("Registering WWCavePlaced.", true);

		// MESOGLEA CAVES

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

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(WWCaveConfigured.BLUE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(WWCaveConfigured.PURPLE_MESOGLEA_COLUMN.getHolder(),
			CountPlacement.of(7),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		MESOGLEA_PATHS.makeAndSetHolder(WWCaveConfigured.MESOGLEA_PATHS.getHolder(),
			CountPlacement.of(30),
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

		DOWNWARD_BLUE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.DOWNWARD_BLUE_MESOGLEA.getHolder(),
			CountPlacement.of(12),
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

		DOWNWARD_PURLE_MESOGLEA.makeAndSetHolder(WWCaveConfigured.DOWNWARD_PURPLE_MESOGLEA.getHolder(),
			CountPlacement.of(12),
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

		MAGMA_PATH.makeAndSetHolder(WWCaveConfigured.MAGMA_AND_GABBRO_PATH.getHolder(),
			modifiersWithCount(72, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		MAGMA_DISK.makeAndSetHolder(WWCaveConfigured.MAGMA_DISK.getHolder(),
			modifiersWithCount(48, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		OBSIDIAN_DISK.makeAndSetHolder(WWCaveConfigured.OBSIDIAN_DISK.getHolder(),
			modifiersWithCount(6, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
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

		ORE_GABBRO.makeAndSetHolder(WWCaveConfigured.ORE_GABBRO.getHolder(),
			modifiersWithCount(4, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		GABBRO_DISK.makeAndSetHolder(WWCaveConfigured.GABBRO_DISK.getHolder(),
			modifiersWithCount(32, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		GABBRO_PILE.makeAndSetHolder(WWCaveConfigured.GABBRO_PILE.getHolder(),
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
			BlockPredicateFilter.forPredicate(
				BlockPredicate.anyOf(
					BlockPredicate.noFluid(Direction.UP.getNormal()),
					SearchInDirectionBlockPredicate.hasLavaAbove(1)
				)
			),
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
					SearchInDirectionBlockPredicate.hasLavaAbove(3)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_LAVA.makeAndSetHolder(WWCaveConfigured.GEYSER_UP.getHolder(),
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

		GEYSER_UP.makeAndSetHolder(WWCaveConfigured.GEYSER_UP.getHolder(),
			CountPlacement.of(UniformInt.of(64, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.anyOf(
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO, Blocks.MAGMA_BLOCK),
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

		GEYSER_DOWN.makeAndSetHolder(WWCaveConfigured.GEYSER_DOWN.getHolder(),
			CountPlacement.of(UniformInt.of(48, 64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.UP,
				BlockPredicate.matchesBlocks(WWBlocks.GABBRO, Blocks.MAGMA_BLOCK),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_NORTH.makeAndSetHolder(WWCaveConfigured.GEYSER_NORTH.getHolder(),
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					BlockPredicate.replaceable(Direction.NORTH.getNormal()),
					BlockPredicate.matchesBlocks(Direction.SOUTH.getNormal(), WWBlocks.GABBRO, Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_EAST.makeAndSetHolder(WWCaveConfigured.GEYSER_EAST.getHolder(),
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					BlockPredicate.replaceable(Direction.EAST.getNormal()),
					BlockPredicate.matchesBlocks(Direction.WEST.getNormal(), WWBlocks.GABBRO, Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_SOUTH.makeAndSetHolder(WWCaveConfigured.GEYSER_SOUTH.getHolder(),
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					BlockPredicate.replaceable(Direction.SOUTH.getNormal()),
					BlockPredicate.matchesBlocks(Direction.NORTH.getNormal(), WWBlocks.GABBRO, Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		GEYSER_WEST.makeAndSetHolder(WWCaveConfigured.GEYSER_WEST.getHolder(),
			CountPlacement.of(UniformInt.of(96, 128)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					BlockPredicate.replaceable(Direction.WEST.getNormal()),
					BlockPredicate.matchesBlocks(Direction.EAST.getNormal(), WWBlocks.GABBRO, Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			),
			BiomeFilter.biome()
		);

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_GEYSER_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(8, 24)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.matchesBlocks(WWBlocks.GABBRO, Blocks.MAGMA_BLOCK), BlockPredicate.replaceable(), 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(WWCaveConfigured.DOWNWARDS_GABBRO_COLUMN.getHolder(),
			CountPlacement.of(UniformInt.of(72, 120)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.replaceable(), 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		LAVA_LAKE_EXTRA.makeAndSetHolder(configuredFeatures.getOrThrow(MiscOverworldFeatures.LAKE_LAVA),
			CountPlacement.of(UniformInt.of(0, 8)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FOSSIL_LAVA.makeAndSetHolder(configuredFeatures.getOrThrow(CaveFeatures.FOSSIL_DIAMONDS),
			RarityFilter.onAverageOnceEvery(20),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(-24)),
			BiomeFilter.biome()
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(WWCaveConfigured.UPSIDE_DOWN_MAGMA.getHolder(),
			CountPlacement.of(72),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 4),
			RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		// FROZEN CAVES

		ICICLE_CLUSTER.makeAndSetHolder(WWCaveConfigured.ICICLE_CLUSTER.getHolder(),
			CountPlacement.of(UniformInt.of(24, 48)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		CAVE_ICICLES.makeAndSetHolder(WWCaveConfigured.CAVE_ICICLE.getHolder(),
			CountPlacement.of(UniformInt.of(56, 192)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			CountPlacement.of(UniformInt.of(1, 5)),
			RandomOffsetPlacement.of(ClampedNormalInt.of(0F, 3F, -10, 10), ClampedNormalInt.of(0F, 0.6F, -2, 2)),
			BiomeFilter.biome()
		);

		ICICLES_SURFACE_WG.makeAndSetHolder(WWCaveConfigured.ICICLE.getHolder(),
			CountPlacement.of(UniformInt.of(20, 30)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(127)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.of(ClampedNormalInt.of(0F, 3F, -10, 10), ClampedNormalInt.of(0F, 0.6F, -2, 2)),
			BiomeFilter.biome()
		);

		ICICLES_SURFACE.makeAndSetHolder(WWCaveConfigured.ICICLE.getHolder(),
			CountPlacement.of(UniformInt.of(22, 30)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(127)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			RandomOffsetPlacement.of(ClampedNormalInt.of(0F, 3F, -10, 10), ClampedNormalInt.of(0F, 0.6F, -2, 2)),
			BiomeFilter.biome()
		);

		ICE_PATHS.makeAndSetHolder(WWCaveConfigured.ICE_PATHS.getHolder(),
			modifiersWithCount(72, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		FRAGILE_ICE_DISK.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_DISK.getHolder(),
			modifiersWithCount(38, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		FRAGILE_ICE_PILE.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PILE.getHolder(),
			CountPlacement.of(UniformInt.of(60, 80)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			BiomeFilter.biome()
		);

		HANGING_PACKED_ICE.makeAndSetHolder(WWCaveConfigured.HANGING_PACKED_ICE.getHolder(),
			CountPlacement.of(16),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		ICE_PATCH_CEILING.makeAndSetHolder(WWCaveConfigured.ICE_PATCH_CEILING.getHolder(),
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		FRAGILE_ICE_COLUMN_PATCH.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_COLUMN_PATCH.getHolder(),
			CountPlacement.of(12),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PATCH.getHolder(),
			CountPlacement.of(48),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		DIORITE_PATCH.makeAndSetHolder(WWCaveConfigured.DIORITE_PATCH.getHolder(),
			CountPlacement.of(16),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		DIORITE_PATCH_CEILING.makeAndSetHolder(WWCaveConfigured.DIORITE_PATCH_CEILING.getHolder(),
			CountPlacement.of(16),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			RandomOffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		ORE_DIORITE_EXTRA.makeAndSetHolder(configuredFeatures.getOrThrow(OreFeatures.ORE_DIORITE),
			modifiersWithCount(1, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(256)))
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
