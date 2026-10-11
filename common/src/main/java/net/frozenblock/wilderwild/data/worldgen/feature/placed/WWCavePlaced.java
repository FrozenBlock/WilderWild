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
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWCaveConfigured;
import net.frozenblock.wilderwild.data.worldgen.features.WWCaveFeatures;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.Unmodifiable;

public final class WWCavePlaced {
	// MAGMATIC CAVES
	public static final FrozenLibPlacedFeature NETHER_GEOTHERMAL_VENT = register("nether_geothermal_vent");
	public static final FrozenLibPlacedFeature NETHER_LAVA_GEOTHERMAL_VENT = register("nether_lava_geothermal_vent");

	// FROZEN CAVES
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE_WG = register("decorative_icicle_cluster_surface_wg");
	public static final FrozenLibPlacedFeature DECORATIVE_ICICLE_CLUSTER_SURFACE = register("decorative_icicle_cluster_surface");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PATCH_CEILING = register("ice_patch_ceiling");
	public static final FrozenLibPlacedFeature FRAGILE_ICE_PATCH = register("fragile_ice_patch");

	public static void registerCavePlaced(BootstrapContext<PlacedFeature> entries) {
		WWConstants.logWithModId("Registering WWCavePlaced for", true);
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = entries.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);

		// MAGMATIC CAVES
		NETHER_GEOTHERMAL_VENT.makeAndSetHolder(WWCaveFeatures.GEOTHERMAL_VENT_UP,
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

		// FROZEN CAVES
		final PlacementFilter generateIcicle = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_DECORATIVE_ICICLE));
		DECORATIVE_ICICLE_CLUSTER_SURFACE_WG.makeAndSetHolder(WWCaveConfigured.DECORATIVE_ICICLE_CLUSTER,
			generateIcicle,
			CountPlacement.of(UniformInt.of(20, 30)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		DECORATIVE_ICICLE_CLUSTER_SURFACE.makeAndSetHolder(WWCaveConfigured.DECORATIVE_ICICLE_CLUSTER,
			generateIcicle,
			CountPlacement.of(UniformInt.of(22, 30)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			OffsetPlacement.horizontal(ClampedNormalInt.of(0F, 3F, -4, 4)),
			BiomeFilter.biome()
		);

		final PlacementFilter generateFragileIce = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_FRAGILE_ICE));
		FRAGILE_ICE_PATCH_CEILING.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PATCH_CEILING,
			generateFragileIce,
			CountPlacement.of(32),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(-1)),
			BiomeFilter.biome()
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(WWCaveConfigured.FRAGILE_ICE_PATCH,
			generateFragileIce,
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
