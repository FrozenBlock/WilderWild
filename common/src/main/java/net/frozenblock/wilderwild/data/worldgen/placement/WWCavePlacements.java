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

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import static net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil.*;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.features.WWCaveFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public final class WWCavePlacements {
	// MESOGLEA CAVES
	public static final FrozenLibPlacedFeature ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibPlacedFeature POOL_MESOGLEA_CAVES = register("pool_mesoglea_caves");
	public static final FrozenLibPlacedFeature PATHS_MESOGLEA = register("paths_mesoglea");
	public static final FrozenLibPlacedFeature PATCH_MESOGLEA = register("patch_mesoglea");
	public static final FrozenLibPlacedFeature PATCH_MESOGLEA_CEILING = register("patch_mesoglea_ceiling");
	public static final FrozenLibPlacedFeature MEDIUM_MESOGLEA = register("medium_mesoglea");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA = register("large_mesoglea");
	public static final FrozenLibPlacedFeature NEMATOCYST = register("nematocyst");

	// MAGMATIC CAVES
	public static final FrozenLibPlacedFeature PUDDLE_LAVA_GABBRO = register("puddle_lava_gabbro");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_ON_GABBRO = register("patch_magma_on_gabbro");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_ON_MAGMA = register("patch_magma_on_magma");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_CEILING = register("patch_magma_ceiling");
	public static final FrozenLibPlacedFeature SPRING_LAVA_MAGMA_OR_GABBRO = register("spring_lava_magma_or_gabbro");
	public static final FrozenLibPlacedFeature LAKE_LAVA_GABBRO = register("lake_lava_gabbro");
	public static final FrozenLibPlacedFeature FOSSIL_DIAMONDS_MAGMATIC_CAVES = register("fossil_diamonds_magmatic_caves");
	public static final FrozenLibPlacedFeature PATCH_GABBRO = register("patch_gabbro");
	public static final FrozenLibPlacedFeature PATCH_GABBRO_CEILING = register("patch_gabbro_ceiling");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_LAVA = register("geothermal_vent_lava");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_NORTH_SOUTH = register("geothermal_vent_north_south");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_EAST_WEST = register("geothermal_vent_east_west");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_STACK = register("geothermal_vent_stack");
	public static final FrozenLibPlacedFeature GEOTHERMAL_VENT_STACK_CEILING = register("geothermal_vent_stack_ceiling");

	// ICE CAVES
	public static final FrozenLibPlacedFeature PATCH_BRITTLE_ICE = register("patch_brittle_ice");
	public static final FrozenLibPlacedFeature PATCH_BRITTLE_ICE_CEILING = register("patch_brittle_ice_ceiling");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(WWCaveFeatures.ORE_CALCITE,
			modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		POOL_MESOGLEA_CAVES.makeAndSetHolder(WWCaveFeatures.POOL_MESOGLEA_CAVES,
			modifiersWithCount(
				60,
				HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
				OffsetPlacement.vertical(ConstantInt.of(1))
			)
		);

		PATHS_MESOGLEA.makeAndSetHolder(WWCaveFeatures.PATHS_MESOGLEA,
			modifiersWithCount(30, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		registerVegetationPatch(context, PATCH_MESOGLEA, WWCaveFeatures.PATCH_MESOGLEA, 10, 12, false);
		registerVegetationPatch(context, PATCH_MESOGLEA_CEILING, WWCaveFeatures.PATCH_MESOGLEA_CEILING, 18, 4, true);

		MEDIUM_MESOGLEA.makeAndSetHolder(WWCaveFeatures.MEDIUM_MESOGLEA,
			modifiers(CountPlacement.of(UniformInt.of(12, 26)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		LARGE_MESOGLEA.makeAndSetHolder(WWCaveFeatures.LARGE_MESOGLEA,
			CountPlacement.of(UniformInt.of(2, 10)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		NEMATOCYST.makeAndSetHolder(WWCaveFeatures.NEMATOCYST,
			modifiersWithCount(198, PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		// MAGMATIC CAVES
		PUDDLE_LAVA_GABBRO.makeAndSetHolder(WWCaveFeatures.PUDDLE_LAVA_GABBRO,
			modifiers(
				CountPlacement.of(UniformInt.of(1, 2)),
				HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(16), VerticalAnchor.aboveBottom(60)),
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
				OffsetPlacement.vertical(ConstantInt.of(1))
			)
		);

		registerVegetationPatch(
			context, PATCH_MAGMA_ON_GABBRO, WWCaveFeatures.PATCH_MAGMA, 8,
			BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4, false
		);
		registerVegetationPatch(
			context, PATCH_MAGMA_ON_MAGMA, WWCaveFeatures.PATCH_MAGMA, 28,
			BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4, false
		);
		registerVegetationPatch(
			context, PATCH_MAGMA_CEILING, WWCaveFeatures.PATCH_MAGMA_CEILING, 24,
			BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4, true
		);

		SPRING_LAVA_MAGMA_OR_GABBRO.makeAndSetHolder(WWCaveFeatures.SPRING_LAVA_MAGMA_OR_GABBRO,
			modifiers(CountPlacement.of(UniformInt.of(144, 200)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT)
		);

		LAKE_LAVA_GABBRO.makeAndSetHolder(WWCaveFeatures.LAKE_LAVA_GABBRO,
			modifiers(
				CountPlacement.of(96),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(
					Direction.DOWN,
					BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK, WWBlocks.GABBRO.get()),
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					12
				),
				OffsetPlacement.vertical(UniformInt.of(-2, 1))
			)
		);

		FOSSIL_DIAMONDS_MAGMATIC_CAVES.makeAndSetHolder(features.getOrThrow(CaveFeatures.FOSSIL_DIAMONDS),
			modifiers(
				RarityFilter.onAverageOnceEvery(20),
				HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(-24))
			)
		);

		registerVegetationPatch(context, PATCH_GABBRO, WWCaveFeatures.PATCH_GABBRO, 32, 12, false);
		registerVegetationPatch(context, PATCH_GABBRO_CEILING, WWCaveFeatures.PATCH_GABBRO_CEILING, 32, 6, true);

		GEOTHERMAL_VENT_LAVA.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
			modifiers(
				CountPlacement.of(UniformInt.of(64, 72)),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(
					Direction.DOWN,
					BlockPredicate.allOf(
						BlockPredicate.solid(),
						BlockPredicate.matchesBlocks(Direction.UP, Blocks.LAVA)
					),
					BlockPredicate.replaceable(),
					12
				)
			)
		);

		GEOTHERMAL_VENT_UP.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
			modifiers(
				CountPlacement.of(UniformInt.of(42, 64)),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(
					Direction.DOWN,
					BlockPredicate.anyOf(
						BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK),
						BlockPredicate.allOf(
							BlockPredicate.solid(),
							BlockPredicate.matchesBlocks(Direction.UP, Blocks.LAVA)
						)
					),
					BlockPredicate.replaceable(),
					12
				)
			)
		);

		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_DOWN,
			modifiers(
				CountPlacement.of(UniformInt.of(48, 64)),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(
					Direction.UP,
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK),
					BlockPredicate.replaceable(),
					12
				)
			)
		);

		registerGeothermalVentHorizontal(context, GEOTHERMAL_VENT_NORTH_SOUTH, WWCaveFeatures.GEOTHERMAL_VENT_NORTH_SOUTH, 128, 196);
		registerGeothermalVentHorizontal(context, GEOTHERMAL_VENT_EAST_WEST, WWCaveFeatures.GEOTHERMAL_VENT_EAST_WEST, 128, 196);

		final BlockPredicate geothermalVentStackSearch = BlockPredicate.anyOf(BlockPredicate.ONLY_IN_AIR_PREDICATE, BlockPredicate.matchesBlocks(Blocks.WATER, Blocks.LAVA));
		registerVegetationPatch(context, GEOTHERMAL_VENT_STACK, WWCaveFeatures.GEOTHERMAL_VENT_STACK, 18, geothermalVentStackSearch, 12, false);
		registerVegetationPatch(context, GEOTHERMAL_VENT_STACK_CEILING, WWCaveFeatures.GEOTHERMAL_VENT_STACK_CEILING, 18, geothermalVentStackSearch, 6, true);

		// ICE CAVES
		registerBrittleIcePatch(context, PATCH_BRITTLE_ICE, WWCaveFeatures.PATCH_BRITTLE_ICE, false);
		registerBrittleIcePatch(context, PATCH_BRITTLE_ICE_CEILING, WWCaveFeatures.PATCH_BRITTLE_ICE_CEILING, true);
	}

	private static void registerVegetationPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int count,
		BlockPredicate targetCondition,
		BlockPredicate allowedSearchCondition,
		int searchSteps,
		boolean ceiling
	) {
		placement.makeAndSetHolder(
			feature,
			modifiersWithCount(
				count,
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(
					ceiling ? Direction.UP : Direction.DOWN,
					targetCondition,
					allowedSearchCondition,
					searchSteps
				),
				OffsetPlacement.vertical(ConstantInt.of(ceiling ? -1 : 1))
			)
		);
	}

	private static void registerVegetationPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int count,
		BlockPredicate allowedSearchCondition,
		int searchSteps,
		boolean ceiling
	) {
		registerVegetationPatch(context, placement, feature, count, BlockPredicate.solid(), allowedSearchCondition, searchSteps, ceiling);
	}

	private static void registerVegetationPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int count,
		int searchSteps,
		boolean ceiling
	) {
		registerVegetationPatch(context, placement, feature, count, BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, searchSteps, ceiling);
	}

	private static void registerGeothermalVentHorizontal(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int minCount, int maxCount
	) {
		placement.makeAndSetHolder(
			feature,
			CountPlacement.of(UniformInt.of(minCount, maxCount)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);
	}

	private static void registerBrittleIcePatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		boolean ceiling
	) {
		placement.makeAndSetHolder(
			feature,
			modifiersWithFilter(
				context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_BRITTLE_ICE),
				CountPlacement.of(22),
				PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(ceiling ? Direction.UP : Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
				OffsetPlacement.vertical(ConstantInt.of(ceiling ? -1 : 1))
			)
		);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("cave/" + name));
	}

	private WWCavePlacements() {}
}
