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
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWConfiguredFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import static net.minecraft.data.worldgen.placement.AquaticPlacements.seagrassPlacement;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;

public final class WWPlacedFeatures {
	// MUSHROOMS
	public static final FrozenLibPlacedFeature BROWN_MUSHROOM = register("brown_mushroom");
	public static final FrozenLibPlacedFeature RED_MUSHROOM = register("red_mushroom");
	public static final FrozenLibPlacedFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibPlacedFeature HUGE_PALE_MUSHROOM = register("huge_pale_mushroom");
	public static final FrozenLibPlacedFeature MUSHROOMS_DARK_FOREST = register("mushrooms_dark_forest");
	public static final FrozenLibPlacedFeature HUGE_RED_MUSHROOM = register("huge_red_mushroom");
	public static final FrozenLibPlacedFeature HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS = register("huge_mushrooms");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOMS_RARE = register("huge_mushrooms_rare");
	public static final FrozenLibPlacedFeature MIXED_MUSHROOM = register("mixed_mushroom");
	public static final FrozenLibPlacedFeature RAINFOREST_MUSHROOM = register("rainforest_mushroom");

	// GRASS AND FERNS
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_PLAIN = register("patch_grass_frozen_plain");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_FOREST = register("patch_grass_frozen_forest");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_NORMAL = register("patch_grass_frozen_normal");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA_2 = register("patch_grass_frozen_taiga_2");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_TAIGA = register("patch_grass_frozen_taiga");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FROZEN_BONEMEAL = register("grass_frozen_bonemeal");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS_2 = register("patch_frozen_tall_grass2");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS = register("patch_frozen_tall_grass");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_LARGE_FERN = register("patch_frozen_large_fern");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_BUSH = register("patch_frozen_bush");
	public static final FrozenLibPlacedFeature PATCH_GRASS_OASIS = register("patch_grass_oasis");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_OASIS = register("patch_shrub_oasis");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_JUNGLE = register("patch_shrub_jungle");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_SPARSE = register("patch_shrub_sparse");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_FLOWER_FIELD = register("patch_shrub_flower_field");
	public static final FrozenLibPlacedFeature PATCH_SHRUB = register("patch_shrub");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_DESERT = register("patch_shrub_desert");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND = register("patch_shrub_badlands_sand");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND_RARE = register("patch_shrub_badlands_sand_rare");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_TERRACOTTA = register("patch_shrub_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA = register("patch_shrub_wooded_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_DIRT = register("patch_shrub_wooded_badlands_dirt");
	public static final FrozenLibPlacedFeature PATCH_BUSH_ARID = register("patch_bush_arid");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_OASIS = register("patch_cactus_oasis");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL = register("patch_cactus_tall");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands");
	public static final FrozenLibPlacedFeature PATCH_ARID_CACTUS = register("patch_arid_cactus");
	public static final FrozenLibPlacedFeature PATCH_MYCELIUM_GROWTH = register("patch_mycelium_growth");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS = register("patch_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS_LIGHT = register("patch_fern_and_grass_light");
	public static final FrozenLibPlacedFeature PATCH_GRASS_BIRCH = register("patch_grass_birch");
	public static final FrozenLibPlacedFeature PATCH_FERN_SWAMP = register("patch_fern_swamp");
	public static final FrozenLibPlacedFeature PATCH_RARE_GRASS = register("patch_rare_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS = register("tall_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS_LIGHT = register("tall_grass_light");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_SWAMP = register("patch_tall_grass_swamp");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_DENSE = register("patch_tall_grass_dense");
	public static final FrozenLibPlacedFeature PATCH_FERN_DENSE = register("patch_fern_dense");
	public static final FrozenLibPlacedFeature SEAGRASS_CYPRESS = register("seagrass_cypress");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS = register("patch_large_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS_RARE = register("patch_large_fern_and_grass_rare");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_AND_GRASS_WATER = register("patch_tall_grass_and_grass_water");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FLOWER_FIELD = register("patch_grass_flower_field");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_FLOWER_FIELD = register("patch_tall_grass_flower_field");

	// FLOWERS
	public static final FrozenLibPlacedFeature PATCH_CLOVER = register("patch_clover");
	public static final FrozenLibPlacedFeature PATCH_CLOVER_SPARSE = register("patch_clover_sparse");

	public static final FrozenLibPlacedFeature MOSS_CARPET = register("moss_carpet");
	// VEGETATION
	public static final FrozenLibPlacedFeature POLLEN = register("pollen");
	public static final FrozenLibPlacedFeature PATCH_BERRY_FOREST = register("patch_berry_forest");
	public static final FrozenLibPlacedFeature TERMITE_MOUND = register("termite_mound");
	public static final FrozenLibPlacedFeature PATCH_TUMBLEWEED = register("patch_tumbleweed");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR = register("patch_prickly_pear");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR_RARE = register("patch_prickly_pear_rare");
	public static final FrozenLibPlacedFeature PATCH_MELON = register("patch_melon");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_COMMON = register("patch_pumpkin_common");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_UNCOMMON = register("patch_pumpkin_uncommon");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_DESERT = register("patch_dry_grass_desert");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BADLANDS = register("patch_dry_grass_badlands");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BEACH = register("patch_dry_grass_beach");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BETA_BEACH = register("patch_dry_grass_beta_beach");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER = register("patch_firefly_bush_near_water");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP = register("patch_firefly_bush_near_water_swamp");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_SWAMP = register("patch_firefly_bush_swamp");
	public static final FrozenLibPlacedFeature PATCH_LEAF_LITTER = register("patch_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_UNCOMMON_LEAF_LITTER = register("patch_uncommon_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_DARK_OAK_LEAF_LITTER = register("patch_dark_oak_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_PALE_OAK_LEAF_LITTER = register("patch_pale_oak_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_SPRUCE_LEAF_LITTER = register("patch_spruce_leaf_litter");

	public static void registerPlacedFeatures(BootstrapContext<PlacedFeature> entries) {
		WWConstants.logWithModId("Registering WWPlacedFeatures for ", true);
		final HolderGetter<Feature> configuredFeatures = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		// MUSHROOMS
		BROWN_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.BROWN_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		RED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.RED_MUSHROOM),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PALE_MUSHROOM.makeAndSetHolder(WWConfiguredFeatures.PALE_MUSHROOM,
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HUGE_PALE_MUSHROOM.makeAndSetHolder(WWTreeFeatures.HUGE_PALE_MUSHROOM,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(WWConfiguredFeatures.MUSHROOMS_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(4),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(50),
			OffsetPlacement.ofTriangle(4, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HUGE_RED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_BROWN_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM),
			RarityFilter.onAverageOnceEvery(90),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_MUSHROOMS_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_MUSHROOMS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		HUGE_MUSHROOMS_RARE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(20),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		MIXED_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(75),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		RAINFOREST_MUSHROOM.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		// GRASS AND FERNS
		PATCH_GRASS_FROZEN_PLAIN.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 1, 3),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_FOREST.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_NORMAL.makeAndSetHolder(WWConfiguredFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_TAIGA_2.makeAndSetHolder(WWConfiguredFeatures.TAIGA_FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_TAIGA.makeAndSetHolder(WWConfiguredFeatures.TAIGA_FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FROZEN_BONEMEAL.makeAndSetHolder(WWConfiguredFeatures.SINGLE_PIECE_OF_FROZEN_GRASS,
			PlacementUtils.isEmpty()
		);

		PATCH_FROZEN_TALL_GRASS_2.makeAndSetHolder(WWConfiguredFeatures.FROZEN_TALL_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 0, 3),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_TALL_GRASS.makeAndSetHolder(WWConfiguredFeatures.FROZEN_TALL_GRASS,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_LARGE_FERN.makeAndSetHolder(WWConfiguredFeatures.FROZEN_LARGE_FERN,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_BUSH.makeAndSetHolder(WWConfiguredFeatures.FROZEN_BUSH,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			OffsetPlacement.ofTriangle(5, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_OASIS.makeAndSetHolder(WWConfiguredFeatures.GRASS_OASIS,
			CountPlacement.of(19),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(35),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SHRUB_OASIS.makeAndSetHolder(WWConfiguredFeatures.SHRUB_OASIS,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(23),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_JUNGLE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_JUNGLE,
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(23),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_SPARSE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_SPARSE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.SHRUB_FLOWER_FIELD,
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(18),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB.makeAndSetHolder(WWConfiguredFeatures.SHRUB_GENERIC,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
				)
			)
		);

		PATCH_SHRUB_DESERT.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(11),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SHRUB_BADLANDS_SAND.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(8),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_SHRUB_BADLANDS_SAND_RARE.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(8),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_SHRUB_BADLANDS_TERRACOTTA.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(9),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(6),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.BADLANDS_TERRACOTTA)
				)
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(10),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.BADLANDS_TERRACOTTA)
				)
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_DIRT.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(15),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.DIRT)
				)
			)
		);

		PATCH_BUSH_ARID.makeAndSetHolder(WWConfiguredFeatures.SHRUB_DESERT,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get()),
			BiomeFilter.biome(),
			CountPlacement.of(4),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_CACTUS_OASIS.makeAndSetHolder(WWConfiguredFeatures.CACTUS_OASIS,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_CACTUS_TALL.makeAndSetHolder(WWConfiguredFeatures.CACTUS_TALL,
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(8),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_CACTUS_TALL_BADLANDS.makeAndSetHolder(WWConfiguredFeatures.CACTUS_TALL_BADLANDS,
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_ARID_CACTUS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.CACTUS),
			RarityFilter.onAverageOnceEvery(7),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(10),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.CACTUS)
				)
			)
		);

		PATCH_MYCELIUM_GROWTH.makeAndSetHolder(WWConfiguredFeatures.MYCELIUM_GROWTH,
			CountPlacement.of(3), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_AND_GRASS.makeAndSetHolder(WWConfiguredFeatures.FERN_AND_GRASS,
			CountPlacement.of(20),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_AND_GRASS_LIGHT.makeAndSetHolder(WWConfiguredFeatures.FERN_AND_GRASS,
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_BIRCH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS),
			CountPlacement.of(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_SWAMP.makeAndSetHolder(WWConfiguredFeatures.FERN_SWAMP,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(24),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_RARE_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
			CountPlacement.of(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.not(
						BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.PODZOL)
					)
				)
			)
		);

		TALL_GRASS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		TALL_GRASS_LIGHT.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			RarityFilter.onAverageOnceEvery(5),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_SWAMP.makeAndSetHolder(WWConfiguredFeatures.TALL_GRASS_SWAMP,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(18),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_DENSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.TALL_GRASS),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FERN_DENSE.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LARGE_FERN),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		SEAGRASS_CYPRESS.makeAndSetHolder(configuredFeatures.getOrThrow(AquaticFeatures.SEAGRASS_MID),
			seagrassPlacement(56)
		);

		PATCH_LARGE_FERN_AND_GRASS.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_LARGE_FERN_AND_GRASS_RARE.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS,
			RarityFilter.onAverageOnceEvery(4),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(WWConfiguredFeatures.TALL_GRASS_AND_GRASS_WATER,
			RarityFilter.onAverageOnceEvery(2),
			CountPlacement.of(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(16),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_GRASS_FLOWER_FIELD.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
			CountPlacement.of(15),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.not(
						BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.PODZOL)
					)
				)
			)
		);

		PATCH_TALL_GRASS_FLOWER_FIELD.makeAndSetHolder(WWConfiguredFeatures.LARGE_FERN_AND_GRASS_2,
			NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
			RarityFilter.onAverageOnceEvery(16),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(36),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		// FLOWERS
		PATCH_CLOVER.makeAndSetHolder(WWConfiguredFeatures.CLOVER,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_CLOVER_SPARSE.makeAndSetHolder(WWConfiguredFeatures.CLOVER,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(6, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		MOSS_CARPET.makeAndSetHolder(WWConfiguredFeatures.MOSS_CARPET,
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(25),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING)
				)
			)
		);

		// VEGETATION
		POLLEN.makeAndSetHolder(WWConfiguredFeatures.POLLEN,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome()
		);

		PATCH_BERRY_FOREST.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.BERRY_BUSH),
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		TERMITE_MOUND.makeAndSetHolder(WWConfiguredFeatures.TERMITE_MOUND,
			RarityFilter.onAverageOnceEvery(45),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
			BiomeFilter.biome()
		);

		PATCH_TUMBLEWEED.makeAndSetHolder(WWConfiguredFeatures.TUMBLEWEED,
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(12),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PRICKLY_PEAR.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR,
			RarityFilter.onAverageOnceEvery(7),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(15),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PRICKLY_PEAR_RARE.makeAndSetHolder(WWConfiguredFeatures.PRICKLY_PEAR,
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(15),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_MELON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.MELON),
			RarityFilter.onAverageOnceEvery(64),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.replaceable(),
					BlockPredicate.noFluid(),
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_PUMPKIN_COMMON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PUMPKIN),
			RarityFilter.onAverageOnceEvery(12),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_PUMPKIN_UNCOMMON.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.PUMPKIN),
			RarityFilter.onAverageOnceEvery(24),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_DRY_GRASS_BADLANDS.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(6),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_DRY_GRASS_DESERT.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_DRY_GRASS_BEACH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.SAND)
				)
			)
		);

		final BlockPredicate sandNearby = BlockPredicate.allOf(
			BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.SAND),
			BlockPredicate.matchesBlocks(new Vec3i(-1, -1, 0), List.of(Blocks.SAND)),
			BlockPredicate.matchesBlocks(new Vec3i(0, -1, -1), List.of(Blocks.SAND)),
			BlockPredicate.matchesBlocks(new Vec3i(1, -1, 0), List.of(Blocks.SAND)),
			BlockPredicate.matchesBlocks(new Vec3i(0, -1, 1), List.of(Blocks.SAND))
		);

		PATCH_DRY_GRASS_BETA_BEACH.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.DRY_GRASS),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BlockPredicateFilter.forPredicate(sandNearby),
			BiomeFilter.biome(),
			CountPlacement.of(28),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.SAND)
				)
			)
		);

		PATCH_FIREFLY_BUSH_NEAR_WATER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
			CountPlacement.of(20),
			OffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(3),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
			CountPlacement.of(20),
			OffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FIREFLY_BUSH_SWAMP.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			RarityFilter.onAverageOnceEvery(8),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(20),
			OffsetPlacement.ofTriangle(4, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		final BlockPredicateFilter leafLitterPlacementPredicate = BlockPredicateFilter.forPredicate(
			BlockPredicate.not(
				BlockPredicate.anyOf(
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.SAND),
					BlockPredicate.matchesTag(Direction.DOWN, BlockTags.TERRACOTTA),
					BlockPredicate.matchesBlocks(
						Direction.DOWN,
						Blocks.GRAVEL,
						Blocks.SUSPICIOUS_GRAVEL,
						Blocks.CLAY
					),
					BlockPredicate.not(BlockPredicate.matchesFluids(Direction.DOWN, Fluids.EMPTY))
				)
			)
		);
		PATCH_LEAF_LITTER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPlacementPredicate,
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_UNCOMMON_LEAF_LITTER.makeAndSetHolder(configuredFeatures.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(1),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPlacementPredicate,
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
		);

		PATCH_DARK_OAK_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.DARK_OAK_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPlacementPredicate,
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_PALE_OAK_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.PALE_OAK_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPlacementPredicate,
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_SPRUCE_LEAF_LITTER.makeAndSetHolder(WWConfiguredFeatures.SPRUCE_LEAF_LITTER_SINGLE,
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			leafLitterPlacementPredicate,
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
	}

}
