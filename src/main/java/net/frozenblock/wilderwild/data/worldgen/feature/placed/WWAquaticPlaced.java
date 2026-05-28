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

import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWAquaticConfigured;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public final class WWAquaticPlaced {
	public static final FrozenLibPlacedFeature PATCH_CATTAIL = register("cattail");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_UNCOMMON = register("cattail_uncommon");
	public static final FrozenLibPlacedFeature PATCH_CATTAIL_COMMON = register("cattail_common");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_COMMON = register("patch_barnacles_common");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_STRUCTURE = register("patch_barnacles_structure");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES = register("patch_barnacles");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_SPARSE = register("patch_barnacles_sparse");
	public static final FrozenLibPlacedFeature PATCH_BARNACLES_RARE = register("patch_barnacles_rare");
	public static final FrozenLibPlacedFeature PATCH_FLOWERING_WATERLILY = register("patch_flowering_waterlily");
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
	public static final FrozenLibPlacedFeature OCEAN_MOSS = register("ocean_moss");
	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER = register("auburn_moss_underwater");
	public static final FrozenLibPlacedFeature AUBURN_MOSS_UNDERWATER_RARE = register("auburn_moss_underwater_rare");
	public static final FrozenLibPlacedFeature AUBURN_CREEPING_MOSS_UNDERWATER = register("auburn_creeping_moss_underwater");

	public static void registerAquaticPlaced(BootstrapContext<PlacedFeature> entries) {
		WWConstants.logWithModId("Registering WWAquaticPlaced for", true);
		final HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		final PlacementFilter cattailConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.CATTAIL_GENERATION, true).asPlacementFilter();
		PATCH_CATTAIL.makeAndSetHolder(WWAquaticConfigured.CATTAIL,
			cattailConfigPredicate,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		PATCH_CATTAIL_UNCOMMON.makeAndSetHolder(WWAquaticConfigured.CATTAIL_SMALL,
			cattailConfigPredicate,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		PATCH_CATTAIL_COMMON.makeAndSetHolder(WWAquaticConfigured.CATTAIL_SMALL,
			cattailConfigPredicate,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		final PlacementFilter barnaclesConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.BARNACLES_GENERATION, true).asPlacementFilter();
		PATCH_BARNACLES_COMMON.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES,
			barnaclesConfigPredicate,
			CountPlacement.of(UniformInt.of(1, 2)),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(30),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
		);

		PATCH_BARNACLES_STRUCTURE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES_STRUCTURE,
			barnaclesConfigPredicate,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(42),
			RandomOffsetPlacement.ofTriangle(8, 8),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
		);

		PATCH_BARNACLES.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES,
			barnaclesConfigPredicate,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
		);

		PATCH_BARNACLES_SPARSE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES,
			barnaclesConfigPredicate,
			RarityFilter.onAverageOnceEvery(18),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
		);

		PATCH_BARNACLES_RARE.makeAndSetHolder(WWAquaticConfigured.PATCH_BARNACLES,
			barnaclesConfigPredicate,
			RarityFilter.onAverageOnceEvery(24),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			RandomOffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
		);

		final PlacementFilter flowerConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.FLOWER_GENERATION, true).asPlacementFilter();
		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(WWAquaticConfigured.FLOWERING_WATERLILY,
			flowerConfigPredicate,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(19),
			RandomOffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		final PlacementFilter algaeConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.ALGAE_GENERATION, true).asPlacementFilter();
		PATCH_ALGAE.makeAndSetHolder(WWAquaticConfigured.PATCH_ALGAE,
			algaeConfigPredicate,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WWAquaticConfigured.PATCH_ALGAE_SMALL,
			algaeConfigPredicate,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		final PlacementFilter planktonConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.PLANKTON_GENERATION, true).asPlacementFilter();
		PATCH_PLANKTON.makeAndSetHolder(WWAquaticConfigured.PATCH_PLANKTON,
			planktonConfigPredicate,
			RarityFilter.onAverageOnceEvery(30),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		final PlacementFilter seagrassConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.SEAGRASS_GENERATION, true).asPlacementFilter();
		SEAGRASS_MEADOW.makeAndSetHolder(WWAquaticConfigured.SEAGRASS_MEADOW,
			seagrassConfigPredicate,
			CountPlacement.of(98),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BiomeFilter.biome()
		);

		final PlacementFilter spongeBudConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.SPONGE_BUD_GENERATION, true).asPlacementFilter();
		SPONGE_BUDS.makeAndSetHolder(WWAquaticConfigured.SPONGE_BUD,
			spongeBudConfigPredicate,
			CountPlacement.of(UniformInt.of(0, 3)),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		SPONGE_BUDS_RARE.makeAndSetHolder(WWAquaticConfigured.SPONGE_BUD,
			spongeBudConfigPredicate,
			CountPlacement.of(UniformInt.of(0, 1)),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		final PlacementFilter seaAnemoneConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.SEA_ANEMONE_GENERATION, true).asPlacementFilter();
		PATCH_SEA_ANEMONE.makeAndSetHolder(WWAquaticConfigured.SEA_ANEMONE,
			seaAnemoneConfigPredicate,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(12, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_ANEMONE.defaultBlockState(), BlockPos.ZERO),
					BlockPredicate.not(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SEA_ANEMONE_FEATURE_CANNOT_PLACE))
				)
			)
		);

		PATCH_SEA_ANEMONE_SPARSE.makeAndSetHolder(WWAquaticConfigured.SEA_ANEMONE,
			seaAnemoneConfigPredicate,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(12, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_ANEMONE.defaultBlockState(), BlockPos.ZERO),
					BlockPredicate.not(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SEA_ANEMONE_FEATURE_CANNOT_PLACE))
				)
			)
		);

		PATCH_SEA_ANEMONE_RARE.makeAndSetHolder(WWAquaticConfigured.SEA_ANEMONE,
			seaAnemoneConfigPredicate,
			RarityFilter.onAverageOnceEvery(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(12, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_ANEMONE.defaultBlockState(), BlockPos.ZERO),
					BlockPredicate.not(BlockPredicate.matchesTag(Direction.DOWN.getUnitVec3i(), WWBlockTags.SEA_ANEMONE_FEATURE_CANNOT_PLACE))
				)
			)
		);

		final PlacementFilter seaWhipConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.SEA_WHIP_GENERATION, true).asPlacementFilter();
		PATCH_SEA_WHIP.makeAndSetHolder(WWAquaticConfigured.SEA_WHIP,
			seaWhipConfigPredicate,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(7),
			RandomOffsetPlacement.ofTriangle(10, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_WHIP.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_SEA_WHIP_SPARSE.makeAndSetHolder(WWAquaticConfigured.SEA_WHIP,
			seaWhipConfigPredicate,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(4),
			RandomOffsetPlacement.ofTriangle(12, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_WHIP.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		PATCH_SEA_WHIP_RARE.makeAndSetHolder(WWAquaticConfigured.SEA_WHIP,
			seaWhipConfigPredicate,
			RarityFilter.onAverageOnceEvery(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(4),
			RandomOffsetPlacement.ofTriangle(12, 3),
			HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.matchesBlocks(Blocks.WATER),
					BlockPredicate.wouldSurvive(WWBlocks.SEA_WHIP.defaultBlockState(), BlockPos.ZERO)
				)
			)
		);

		final PlacementFilter tubeWormsConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.TUBE_WORMS_GENERATION, true).asPlacementFilter();
		PATCH_TUBE_WORMS.makeAndSetHolder(WWAquaticConfigured.TUBE_WORMS,
			tubeWormsConfigPredicate,
			RarityFilter.onAverageOnceEvery(22),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(3, 4)
		);

		PATCH_TUBE_WORMS_RARE.makeAndSetHolder(WWAquaticConfigured.TUBE_WORMS,
			tubeWormsConfigPredicate,
			RarityFilter.onAverageOnceEvery(42),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			RandomOffsetPlacement.ofTriangle(3, 4)
		);

		final PlacementFilter hydrothermalVentConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.HYDROTHERMAL_VENT_GENERATION, true).asPlacementFilter();
		HYDROTHERMAL_VENT.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT,
			hydrothermalVentConfigPredicate,
			RarityFilter.onAverageOnceEvery(48),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		HYDROTHERMAL_VENT_RARE.makeAndSetHolder(WWAquaticConfigured.HYDROTHERMAL_VENT,
			hydrothermalVentConfigPredicate,
			RarityFilter.onAverageOnceEvery(72),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_TOP_SOLID,
			BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER)),
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, -256, -9),
			BiomeFilter.biome()
		);

		final PlacementFilter oceanMossConfigPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.OCEAN_MOSS_GENERATION, true).asPlacementFilter();
		OCEAN_MOSS.makeAndSetHolder(WWAquaticConfigured.OCEAN_MOSS,
			oceanMossConfigPredicate,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		final PlacementFilter oceanAuburnMossPredicate = ConfigPredicate.equalTo(WWWorldgenConfig.OCEAN_AUBURN_MOSS_GENERATION, true).asPlacementFilter();
		AUBURN_MOSS_UNDERWATER.makeAndSetHolder(WWAquaticConfigured.AUBURN_MOSS_UNDERWATER,
			oceanAuburnMossPredicate,
			RarityFilter.onAverageOnceEvery(22),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		AUBURN_MOSS_UNDERWATER_RARE.makeAndSetHolder(WWAquaticConfigured.AUBURN_MOSS_UNDERWATER,
			oceanAuburnMossPredicate,
			RarityFilter.onAverageOnceEvery(52),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome()
		);

		AUBURN_CREEPING_MOSS_UNDERWATER.makeAndSetHolder(WWAquaticConfigured.AUBURN_CREEPING_MOSS_PATCH_UNDERWATER,
			oceanAuburnMossPredicate,
			RarityFilter.onAverageOnceEvery(14),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
			BiomeFilter.biome(),
			CountPlacement.of(38),
			RandomOffsetPlacement.ofTriangle(6, 4),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)
		);
	}

}
