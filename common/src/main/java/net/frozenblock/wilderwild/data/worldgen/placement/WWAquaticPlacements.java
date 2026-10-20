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

import java.util.ArrayList;
import java.util.List;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import static net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil.modifiersWithFilter;
import static net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil.postModifiers;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.features.WWAquaticFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public final class WWAquaticPlacements {
	public static final FrozenLibPlacedFeature RIVER_POOL = register("river_pool");
	public static final FrozenLibPlacedFeature SMALL_RIVER_POOL = register("small_river_pool");

	public static final FrozenLibPlacedFeature PATCH_CATTAIL = register("patch_cattail");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_COMMON = register("patch_cattail_common");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_UNCOMMON = register("patch_cattail_uncommon");

	public static final FrozenLibPlacedFeature PATCH_BARNACLES = register("patch_barnacles");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_COMMON = register("patch_barnacles_common");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_SPARSE = register("patch_barnacles_sparse");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_RARE = register("patch_barnacles_rare");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_STRUCTURE = register("patch_barnacles_structure");

	public static final FrozenLibPlacedFeature PATCH_FLOWERING_WATER_LILY = register("patch_flowering_water_lily");

	public static final FrozenLibPlacedFeature PATCH_ALGAE = register("patch_algae");
	public static final FrozenLibPlacedFeature PATCH_ALGAE_SMALL = register("patch_algae_small");

	public static final FrozenLibPlacedFeature PATCH_PLANKTON = register("patch_plankton");

	public static final FrozenLibPlacedFeature SEAGRASS_MEADOW = register("seagrass_meadow");

	public static final FrozenLibPlacedFeature SPONGE_BUDS = register("sponge_buds");
	public static final FrozenLibPlacedFeature SPONGE_BUDS_RARE = register("sponge_buds_rare");

	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE = register("patch_sea_anemone");
	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE_SPARSE = register("patch_sea_anemone_sparse");
	public static final FrozenLibPlacedFeature PATCH_SEA_ANEMONE_RARE = register("patch_sea_anemone_rare");

	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP = register("patch_sea_whip");
	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP_SPARSE = register("patch_sea_whip_sparse");
	public static final FrozenLibPlacedFeature PATCH_SEA_WHIP_RARE = register("patch_sea_whip_rare");
	public static final FrozenLibPlacedFeature PATCH_TUBE_WORMS = register("patch_tube_worms");
	public static final FrozenLibPlacedFeature PATCH_TUBE_WORMS_RARE = register("patch_tube_worms_rare");

	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT = register("hydrothermal_vent");
	public static final FrozenLibPlacedFeature HYDROTHERMAL_VENT_RARE = register("hydrothermal_vent_rare");

	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER = register("auburn_moss_underwater");
	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER_RARE = register("auburn_moss_underwater_rare");
	public static final FrozenLibPlacedFeature AUBURN_CREEPING_MOSS_UNDERWATER = register("auburn_creeping_moss_underwater");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		final PlacementFilter generateRiverPool = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_RIVER_POOL));
		RIVER_POOL.makeAndSetHolder(WWAquaticFeatures.RIVER_POOL,
			generateRiverPool,
			CountPlacement.of(20),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(62), VerticalAnchor.absolute(64)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		SMALL_RIVER_POOL.makeAndSetHolder(WWAquaticFeatures.SMALL_RIVER_POOL,
			generateRiverPool,
			CountPlacement.of(8),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			HeightRangePlacement.uniform(VerticalAnchor.absolute(65), VerticalAnchor.absolute(72)),
			EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
			OffsetPlacement.vertical(ConstantInt.of(1)),
			BiomeFilter.biome()
		);

		registerCattail(context, PATCH_CATTAIL, 4, 24, 32, 6);
		registerCattail(context, PATCH_CATTAIL_COMMON, 2, 10, 20, 4);
		registerCattail(context, PATCH_CATTAIL_UNCOMMON, 3, 10, 20, 4);

		registerBarnacles(context, PATCH_BARNACLES, WWAquaticFeatures.BARNACLES, 18, 6, 3,
			RarityFilter.onAverageOnceEvery(8)
		);
		registerBarnacles(context, PATCH_BARNACLES_COMMON, WWAquaticFeatures.BARNACLES, 30, 6, 3,
			CountPlacement.of(UniformInt.of(1, 2)),
			RarityFilter.onAverageOnceEvery(3)
		);
		registerBarnacles(context, PATCH_BARNACLES_SPARSE, WWAquaticFeatures.BARNACLES, 18, 6, 3,
			RarityFilter.onAverageOnceEvery(18)
		);
		registerBarnacles(context, PATCH_BARNACLES_RARE, WWAquaticFeatures.BARNACLES, 18, 6, 3,
			RarityFilter.onAverageOnceEvery(24)
		);
		registerBarnacles(context, PATCH_BARNACLES_STRUCTURE, WWAquaticFeatures.BARNACLES_STRUCTURE, 42, 8, 8);

		PATCH_FLOWERING_WATER_LILY.makeAndSetHolder(WWAquaticFeatures.FLOWERING_WATER_LILY,
			postModifiers(
				modifiersWithFilter(
					configPredicates.getOrThrow(WWConfigPredicates.GENERATE_FLOWER),
					CountPlacement.of(1),
					PlacementUtils.HEIGHTMAP_WORLD_SURFACE
				),
				CountPlacement.of(19),
				OffsetPlacement.ofTriangle(7, 3),
				BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
			)
		);

		registerWaterCover(
			context,
			PATCH_ALGAE,
			WWAquaticFeatures.ALGAE,
			WWConfigPredicates.GENERATE_ALGAE,
			3,
			List.of(4, 5, 6, 7, 8, 9, 10)
		);
		registerWaterCover(
			context,
			PATCH_ALGAE_SMALL,
			WWAquaticFeatures.ALGAE,
			WWConfigPredicates.GENERATE_ALGAE,
			5,
			List.of(2, 3, 4, 5, 6)
		);

		registerWaterCover(
			context,
			PATCH_PLANKTON,
			WWAquaticFeatures.PLANKTON,
			WWConfigPredicates.GENERATE_PLANKTON,
			30,
			List.of(2, 3, 4)
		);

		SEAGRASS_MEADOW.makeAndSetHolder(WWAquaticFeatures.SEAGRASS_VERY_SHORT,
			modifiersWithFilter(
				configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SEAGRASS),
				CountPlacement.of(98),
				PlacementUtils.HEIGHTMAP_TOP_SOLID,
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
			)
		);

		registerSpongeBud(context, SPONGE_BUDS, CountPlacement.of(UniformInt.of(0, 3)));
		registerSpongeBud(context, SPONGE_BUDS_RARE, CountPlacement.of(UniformInt.of(0, 1)), RarityFilter.onAverageOnceEvery(2));

		registerSeaAnemone(context, PATCH_SEA_ANEMONE, 2);
		registerSeaAnemone(context, PATCH_SEA_ANEMONE_SPARSE, 9);
		registerSeaAnemone(context, PATCH_SEA_ANEMONE_RARE, 15);

		registerSeaWhip(context, PATCH_SEA_WHIP, 5, 7, 10, 3);
		registerSeaWhip(context, PATCH_SEA_WHIP_SPARSE, 9, 4, 12, 3);
		registerSeaWhip(context, PATCH_SEA_WHIP_RARE, 15, 4, 12, 3);

		registerTubeWorms(context, PATCH_TUBE_WORMS, 22);
		registerTubeWorms(context, PATCH_TUBE_WORMS_RARE, 42);

		registerHydrothermalVent(context, HYDROTHERMAL_VENT, 48);
		registerHydrothermalVent(context, HYDROTHERMAL_VENT_RARE, 72);

		registerAuburnMoss(context, AUBURN_MOSS_UNDERWATER, 22);
		registerAuburnMoss(context, AUBURN_MOSS_UNDERWATER_RARE, 52);

		AUBURN_CREEPING_MOSS_UNDERWATER.makeAndSetHolder(WWAquaticFeatures.AUBURN_CREEPING_MOSS_PATCH_UNDERWATER,
			postModifiers(
				modifiersWithFilter(
					configPredicates.getOrThrow(WWConfigPredicates.GENERATE_OCEAN_AUBURN_MOSS),
					RarityFilter.onAverageOnceEvery(14),
					PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
				),
				CountPlacement.of(38),
				OffsetPlacement.ofTriangle(6, 4),
				BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)
			)
		);
	}

	private static void registerCattail(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity,
		int minCount, int maxCount,
		int horizontalRange
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.CATTAIL,
			postModifiers(
				modifiersWithFilter(
					context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_CATTAIL),
					RarityFilter.onAverageOnceEvery(rarity),
					PlacementUtils.HEIGHTMAP_TOP_SOLID
				),
				CountPlacement.of(UniformInt.of(minCount, maxCount)),
				OffsetPlacement.horizontal(TrapezoidInt.triangle(horizontalRange)),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.CATTAIL_PLACEABLE))
			)
		);
	}

	private static void registerBarnacles(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		int count,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		final List<PlacementModifier> modifierList = new ArrayList<>(List.of(modifiers));
		modifierList.addFirst(ConfigPredicate.placementFilter(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_BARNACLES)));
		modifierList.add(InSquarePlacement.spread());
		modifierList.add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR);
		modifierList.add(BiomeFilter.biome());
		modifierList.add(CountPlacement.of(count));
		modifierList.add(OffsetPlacement.ofTriangle(xzRange, yRange));
		modifierList.add(BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)));

		placement.makeAndSetHolder(feature, modifierList);
	}

	private static void registerWaterCover(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		ResourceKey<ConfigPredicate> configPredicate,
		int rarity,
		List<Integer> radii
	) {
		final List<Holder<PlacedFeature>> placements = new ArrayList<>();
		radii.forEach(radius -> {
			placements.add(
				feature.asInlinePlaced(
					CountPlacement.of(Math.powExact(radius + radius + 1, 2)),
					OffsetPlacement.horizontal(TrapezoidInt.triangle(radius)),
					EnvironmentScanPlacement.scanningFor(
						Direction.DOWN,
						BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.WATER),
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						3
					)
				)
			);
		});

		placement.makeAndSetHolder(
			Holder.direct(new SimpleRandomSelectorFeature(HolderSet.direct(placements))),
			modifiersWithFilter(
				context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(configPredicate),
				RarityFilter.onAverageOnceEvery(rarity),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE
			)
		);
	}

	private static void registerSpongeBud(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		PlacementModifier... modifiers
	) {
		final List<PlacementModifier> modifierList = new ArrayList<>(List.of(modifiers));
		modifierList.addFirst(ConfigPredicate.placementFilter(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_SPONGE_BUD)));
		modifierList.add(InSquarePlacement.spread());
		modifierList.add(PlacementUtils.HEIGHTMAP_OCEAN_FLOOR);
		modifierList.add(BiomeFilter.biome());

		placement.makeAndSetHolder(WWAquaticFeatures.SPONGE_BUD, modifierList);
	}

	private static void registerSeaAnemone(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.SEA_ANEMONE,
			postModifiers(
				modifiersWithFilter(
					context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_SEA_ANEMONE),
					RarityFilter.onAverageOnceEvery(rarity),
					PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
				),
				CountPlacement.of(12),
				OffsetPlacement.ofTriangle(12, 3),
				HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.matchesBlocks(Blocks.WATER),
						BlockPredicate.wouldSurvive(WWBlocks.SEA_ANEMONE.get()),
						BlockPredicate.not(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWFeatureBlockTags.SEA_ANEMONE_CANNOT_PLACE))
					)
				)
			)
		);
	}

	private static void registerSeaWhip(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity,
		int count,
		int xzRange, int yRange
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.SEA_WHIP,
			postModifiers(
				modifiersWithFilter(
					context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_SEA_WHIP),
					RarityFilter.onAverageOnceEvery(rarity),
					PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
				),
				CountPlacement.of(count),
				OffsetPlacement.ofTriangle(xzRange, yRange),
				HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.matchesBlocks(Blocks.WATER),
						BlockPredicate.wouldSurvive(WWBlocks.SEA_WHIP.get())
					)
				)
			)
		);
	}

	private static void registerTubeWorms(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.TUBE_WORMS,
			postModifiers(
				modifiersWithFilter(
					context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_TUBE_WORMS),
					RarityFilter.onAverageOnceEvery(rarity),
					PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
				),
				CountPlacement.of(12),
				OffsetPlacement.ofTriangle(3, 4)
			)
		);
	}

	private static void registerHydrothermalVent(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.HYDROTHERMAL_VENT,
			modifiersWithFilter(
				context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_HYDROTHERMAL_VENT),
				RarityFilter.onAverageOnceEvery(rarity),
				PlacementUtils.HEIGHTMAP_TOP_SOLID,
				BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9)
			)
		);
	}

	private static void registerAuburnMoss(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		int rarity
	) {
		placement.makeAndSetHolder(WWAquaticFeatures.AUBURN_MOSS_UNDERWATER,
			modifiersWithFilter(
				context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(WWConfigPredicates.GENERATE_OCEAN_AUBURN_MOSS),
				RarityFilter.onAverageOnceEvery(rarity),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR
			)
		);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("aquatic/" + name));
	}

	private WWAquaticPlacements() {}
}
