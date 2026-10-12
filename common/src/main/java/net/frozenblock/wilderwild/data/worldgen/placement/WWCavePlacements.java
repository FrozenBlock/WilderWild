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

import java.util.List;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.features.WWCaveFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
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
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.Unmodifiable;

public final class WWCavePlacements {
	// MESOGLEA CAVES
	public static final FrozenLibPlacedFeature ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibPlacedFeature POOL_STONE_MESOGLEA_CAVES = register("pool_stone_mesoglea_caves");
	public static final FrozenLibPlacedFeature PATHS_MESOGLEA = register("paths_mesoglea");
	// PEARLESCENT BLUE
	public static final FrozenLibPlacedFeature PATCH_MESOGLEA = register("patch_mesoglea");
	public static final FrozenLibPlacedFeature PATCH_MESOGLEA_CEILING = register("patch_mesoglea_ceiling");
	public static final FrozenLibPlacedFeature MEDIUM_MESOGLEA = register("medium_mesoglea");
	public static final FrozenLibPlacedFeature LARGE_MESOGLEA = register("large_mesoglea");
	public static final FrozenLibPlacedFeature NEMATOCYST = register("nematocyst");

	// MAGMATIC CAVES
	public static final FrozenLibPlacedFeature POOL_LAVA_GABBRO = register("pool_lava_gabbro");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_ON_GABBRO = register("patch_magma_on_gabbro");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_ON_MAGMA = register("patch_magma_on_magma");
	public static final FrozenLibPlacedFeature PATCH_MAGMA_CEILING = register("patch_magma_ceiling");
	public static final FrozenLibPlacedFeature SPRING_LAVA_OVERWORLD_EXTRA = register("spring_lava_overworld_extra");
	public static final FrozenLibPlacedFeature LAKE_LAVA_EXTRA = register("lake_lava_extra");
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

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(WWCaveFeatures.ORE_CALCITE,
			modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64)))
		);

		POOL_STONE_MESOGLEA_CAVES.makeAndSetHolder(WWCaveFeatures.POOL_STONE,
			CountPlacement.of(60),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PATHS_MESOGLEA.makeAndSetHolder(WWCaveFeatures.PATHS_MESOGLEA,
			CountPlacement.of(30),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		registerVegetationPatch(context, PATCH_MESOGLEA, WWCaveFeatures.PATCH_MESOGLEA, 10, 12, false);
		registerVegetationPatch(context, PATCH_MESOGLEA_CEILING, WWCaveFeatures.PATCH_MESOGLEA_CEILING, 18, 4, true);

		MEDIUM_MESOGLEA.makeAndSetHolder(WWCaveFeatures.MEDIUM_MESOGLEA,
			CountPlacement.of(UniformInt.of(12, 26)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		LARGE_MESOGLEA.makeAndSetHolder(WWCaveFeatures.LARGE_MESOGLEA,
			CountPlacement.of(UniformInt.of(2, 10)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		NEMATOCYST.makeAndSetHolder(WWCaveFeatures.NEMATOCYST,
			CountPlacement.of(ConstantInt.of(198)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		// MAGMATIC CAVES
		POOL_LAVA_GABBRO.makeAndSetHolder(WWCaveFeatures.POOL_LAVA_GABBRO,
			CountPlacement.of(4),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(5), VerticalAnchor.aboveBottom(60)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PATCH_MAGMA_ON_GABBRO.makeAndSetHolder(WWCaveFeatures.PATCH_MAGMA,
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PATCH_MAGMA_ON_MAGMA.makeAndSetHolder(WWCaveFeatures.PATCH_MAGMA,
			CountPlacement.of(28),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		PATCH_MAGMA_CEILING.makeAndSetHolder(WWCaveFeatures.PATCH_MAGMA_CEILING,
			CountPlacement.of(24),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get()), BlockPredicate.ONLY_IN_AIR_PREDICATE, 4),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		SPRING_LAVA_OVERWORLD_EXTRA.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.SPRING_LAVA_OVERWORLD),
			CountPlacement.of(UniformInt.of(144, 200)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		LAKE_LAVA_EXTRA.makeAndSetHolder(features.getOrThrow(MiscOverworldFeatures.LAKE_LAVA),
			CountPlacement.of(UniformInt.of(0, 8)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
		);

		FOSSIL_DIAMONDS_MAGMATIC_CAVES.makeAndSetHolder(features.getOrThrow(CaveFeatures.FOSSIL_DIAMONDS),
			RarityFilter.onAverageOnceEvery(20),
			InSquarePlacement.spread(),
			HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(-24)),
			BiomeFilter.biome()
		);

		registerVegetationPatch(context, PATCH_GABBRO, WWCaveFeatures.PATCH_GABBRO, 32, 12, false);
		registerVegetationPatch(context, PATCH_GABBRO_CEILING, WWCaveFeatures.PATCH_GABBRO_CEILING, 32, 6, true);

		GEOTHERMAL_VENT_LAVA.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
			CountPlacement.of(UniformInt.of(64, 72)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					BlockPredicate.matchesBlocks(Direction.UP, Blocks.LAVA)
				),
				BlockPredicate.replaceable(),
				12
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_UP.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
			CountPlacement.of(UniformInt.of(42, 64)),
			InSquarePlacement.spread(),
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
			),
			BiomeFilter.biome()
		);

		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_DOWN,
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

		registerGeothermalVentHorizontal(context, GEOTHERMAL_VENT_NORTH_SOUTH, WWCaveFeatures.GEOTHERMAL_VENT_NORTH_SOUTH, 128, 196);
		registerGeothermalVentHorizontal(context, GEOTHERMAL_VENT_EAST_WEST, WWCaveFeatures.GEOTHERMAL_VENT_EAST_WEST, 128, 196);

		registerVegetationPatch(context, GEOTHERMAL_VENT_STACK, WWCaveFeatures.GEOTHERMAL_VENT_STACK, 18, 12, false);
		registerVegetationPatch(context, GEOTHERMAL_VENT_STACK_CEILING, WWCaveFeatures.GEOTHERMAL_VENT_STACK_CEILING, 18, 6, true);
	}

	private static void registerVegetationPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int count,
		int searchSteps,
		boolean ceiling
	) {
		placement.makeAndSetHolder(
			feature,
			CountPlacement.of(count),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(
				ceiling ? Direction.UP : Direction.DOWN,
				BlockPredicate.solid(),
				BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
				searchSteps
			),
			OffsetPlacement.vertical(ConstantInt.of(ceiling ? -1 : 1)),
			BiomeFilter.biome()
		);
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

	@Unmodifiable
	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier modifier) {
		return List.of(countModifier, InSquarePlacement.spread(), modifier, BiomeFilter.biome());
	}

	@Unmodifiable
	private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier modifier) {
		return modifiers(CountPlacement.of(count), modifier);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("cave/" + name));
	}

	private WWCavePlacements() {}
}
