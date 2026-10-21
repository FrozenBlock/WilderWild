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
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil;
import static net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacementUtil.postModifiers;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWPlacementUtils.register;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWVegetationFeatures;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public final class WWVegetationPlacements {
	// LEAF LITTER
	public static final FrozenLibPlacedFeature PATCH_LEAF_LITTER = register("patch_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_LEAF_LITTER_UNCOMMON = register("patch_leaf_litter_uncommon");
	public static final FrozenLibPlacedFeature PATCH_SPRUCE_LEAF_LITTER = register("patch_spruce_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_DARK_OAK_LEAF_LITTER = register("patch_dark_oak_leaf_litter");
	public static final FrozenLibPlacedFeature PATCH_PALE_OAK_LEAF_LITTER = register("patch_pale_oak_leaf_litter");

	// GRASS
	public static final FrozenLibPlacedFeature PATCH_GRASS_RARE = register("patch_grass_rare");
	public static final FrozenLibPlacedFeature PATCH_GRASS_FLOWER_FIELD = register("patch_grass_flower_field");
	public static final FrozenLibPlacedFeature PATCH_GRASS_BIRCH = register("patch_grass_birch");
	public static final FrozenLibPlacedFeature PATCH_GRASS_OASIS = register("patch_grass_oasis");
	public static final FrozenLibPlacedFeature TALL_GRASS = register("tall_grass");
	public static final FrozenLibPlacedFeature TALL_GRASS_LIGHT = register("tall_grass_light");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_DENSE = register("patch_tall_grass_dense");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_FLOWER_FIELD = register("patch_tall_grass_flower_field");
	public static final FrozenLibPlacedFeature PATCH_TALL_GRASS_SWAMP = register("patch_tall_grass_swamp");
	public static final FrozenLibPlacedFeature PATCH_GRASS_WATER = register("patch_grass_water");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS = register("patch_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_FERN_AND_GRASS_LIGHT = register("patch_fern_and_grass_light");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS = register("patch_large_fern_and_grass");
	public static final FrozenLibPlacedFeature PATCH_LARGE_FERN_AND_GRASS_RARE = register("patch_large_fern_and_grass_rare");
	public static final FrozenLibPlacedFeature PATCH_FERN_DENSE = register("patch_fern_dense");
	public static final FrozenLibPlacedFeature PATCH_FERN_SWAMP = register("patch_fern_swamp");
	public static final FrozenLibPlacedFeature PATCH_CLOVER = register("patch_clover");
	public static final FrozenLibPlacedFeature PATCH_CLOVER_SPARSE = register("patch_clover_sparse");

	// DRY GRASS
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_DESERT = register("patch_dry_grass_desert");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BADLANDS = register("patch_dry_grass_badlands");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BEACH = register("patch_dry_grass_beach");
	public static final FrozenLibPlacedFeature PATCH_DRY_GRASS_BETA_BEACH = register("patch_dry_grass_beta_beach");

	// FROZEN GRASS
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_BONEMEAL = register("grass_frozen_grass_bonemeal");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_PLAIN = register("patch_frozen_grass_plain");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_FOREST = register("patch_frozen_grass_forest");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_NORMAL = register("patch_frozen_grass_normal");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_TAIGA = register("patch_frozen_grass_taiga");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_GRASS_TAIGA_2 = register("patch_frozen_grass_taiga_2");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS = register("patch_frozen_tall_grass");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_TALL_GRASS_2 = register("patch_frozen_tall_grass2");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_BUSH = register("patch_frozen_bush");
	public static final FrozenLibPlacedFeature PATCH_FROZEN_LARGE_FERN = register("patch_frozen_large_fern");

	// FIREFLY BUSH
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER = register("patch_firefly_bush_near_water");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP = register("patch_firefly_bush_near_water_swamp");
	public static final FrozenLibPlacedFeature PATCH_FIREFLY_BUSH_SWAMP = register("patch_firefly_bush_swamp");

	// SHRUB
	public static final FrozenLibPlacedFeature PATCH_SHRUB = register("patch_shrub");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_SPARSE = register("patch_shrub_sparse");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_FLOWER_FIELD = register("patch_shrub_flower_field");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_JUNGLE = register("patch_shrub_jungle");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_OASIS = register("patch_shrub_oasis");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_DESERT = register("patch_shrub_desert");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_ARID = register("patch_shrub_arid");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND = register("patch_shrub_badlands_sand");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_SAND_RARE = register("patch_shrub_badlands_sand_rare");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_BADLANDS_TERRACOTTA = register("patch_shrub_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA = register("patch_shrub_wooded_badlands_terracotta");
	public static final FrozenLibPlacedFeature PATCH_SHRUB_WOODED_BADLANDS_DIRT = register("patch_shrub_wooded_badlands_dirt");

	// MUSHROOM
	public static final FrozenLibPlacedFeature BROWN_MUSHROOM = register("brown_mushroom");
	public static final FrozenLibPlacedFeature RED_MUSHROOM = register("red_mushroom");
	public static final FrozenLibPlacedFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibPlacedFeature HUGE_PALE_MUSHROOM = register("huge_pale_mushroom");
	public static final FrozenLibPlacedFeature PATCH_MUSHROOM_DARK_FOREST = register("big_patch_mushroom");
	public static final FrozenLibPlacedFeature HUGE_BROWN_MUSHROOM = register("huge_brown_mushroom");
	public static final FrozenLibPlacedFeature HUGE_RED_MUSHROOM = register("huge_red_mushroom");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOM = register("huge_mushroom");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOM_SWAMP = register("huge_mushroom_swamp");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOM_RARE = register("huge_mushroom_rare");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOM_MIXED_FOREST = register("huge_mushroom_mixed_forest");
	public static final FrozenLibPlacedFeature HUGE_MUSHROOM_RAINFOREST = register("huge_mushroom_rainforest");
	public static final FrozenLibPlacedFeature PATCH_MYCELIUM_GROWTH = register("patch_mycelium_growth");

	// CACTUS
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL = register("patch_cactus_tall");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_OASIS = register("patch_cactus_oasis");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_ARID = register("patch_cactus_arid");
	public static final FrozenLibPlacedFeature PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR = register("patch_prickly_pear");
	public static final FrozenLibPlacedFeature PATCH_PRICKLY_PEAR_RARE = register("patch_prickly_pear_rare");

	// TUMBLEWEED
	public static final FrozenLibPlacedFeature PATCH_TUMBLEWEED = register("patch_tumbleweed");

	// POLLEN
	public static final FrozenLibPlacedFeature POLLEN = register("pollen");

	// MOSS
	public static final FrozenLibPlacedFeature MOSS_CARPET = register("moss_carpet");

	// BERRY
	public static final FrozenLibPlacedFeature PATCH_BERRY_FOREST = register("patch_berry_forest");

	// MELON
	public static final FrozenLibPlacedFeature PATCH_MELON = register("patch_melon");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_COMMON = register("patch_pumpkin_common");
	public static final FrozenLibPlacedFeature PATCH_PUMPKIN_UNCOMMON = register("patch_pumpkin_uncommon");

	// MISC (move to misc features later)
	public static final FrozenLibPlacedFeature TERMITE_MOUND = register("termite_mound");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// LEAF LITTER
		registerLeafLitterPatch(
			PATCH_LEAF_LITTER,
			features.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(2),
			32, 7, 3,
			true
		);

		registerLeafLitterPatch(
			PATCH_LEAF_LITTER_UNCOMMON,
			features.getOrThrow(VegetationFeatures.LEAF_LITTER),
			CountPlacement.of(1),
			32, 7, 3,
			true
		);

		registerLeafLitterPatch(
			PATCH_SPRUCE_LEAF_LITTER,
			WWVegetationFeatures.SPRUCE_LEAF_LITTER_SINGLE.getHolder(),
			CountPlacement.of(2),
			32, 7, 3,
			false
		);

		registerLeafLitterPatch(
			PATCH_DARK_OAK_LEAF_LITTER,
			WWVegetationFeatures.DARK_OAK_LEAF_LITTER_SINGLE.getHolder(),
			CountPlacement.of(2),
			32, 7, 3,
			false
		);

		registerLeafLitterPatch(
			PATCH_PALE_OAK_LEAF_LITTER,
			WWVegetationFeatures.PALE_OAK_LEAF_LITTER_SINGLE.getHolder(),
			CountPlacement.of(2),
			32, 7, 3,
			false
		);

		// GRASS
		PATCH_GRASS_RARE.makeAndSetHolder(features.getOrThrow(VegetationFeatures.GRASS_JUNGLE),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(8), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
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
			)
		);

		PATCH_GRASS_FLOWER_FIELD.makeAndSetHolder(features.getOrThrow(VegetationFeatures.GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(15), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
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
			)
		);

		PATCH_GRASS_BIRCH.makeAndSetHolder(features.getOrThrow(VegetationFeatures.GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(10), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(28),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_GRASS_OASIS.makeAndSetHolder(WWVegetationFeatures.GRASS_OASIS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(19), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(35),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		TALL_GRASS.makeAndSetHolder(features.getOrThrow(VegetationFeatures.TALL_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(3), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		TALL_GRASS_LIGHT.makeAndSetHolder(features.getOrThrow(VegetationFeatures.TALL_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(5), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_TALL_GRASS_DENSE.makeAndSetHolder(features.getOrThrow(VegetationFeatures.TALL_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_TALL_GRASS_FLOWER_FIELD.makeAndSetHolder(features.getOrThrow(VegetationFeatures.TALL_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				List.of(
					NoiseThresholdCountPlacement.of(-0.8D, 0, 7),
					RarityFilter.onAverageOnceEvery(16),
					InSquarePlacement.spread(),
					PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
					BiomeFilter.biome()
				),
				CountPlacement.of(36),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_TALL_GRASS_SWAMP.makeAndSetHolder(WWVegetationFeatures.TALL_GRASS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(18),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_GRASS_WATER.makeAndSetHolder(WWVegetationFeatures.TALL_GRASS_AND_GRASS_WATER,
			FrozenLibPlacementUtil.postModifiers(
				List.of(
					RarityFilter.onAverageOnceEvery(2),
					CountPlacement.of(3),
					InSquarePlacement.spread(),
					PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
					BiomeFilter.biome()
				),
				CountPlacement.of(17),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_FERN_AND_GRASS.makeAndSetHolder(WWVegetationFeatures.FERN_AND_GRASS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(20), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(32),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_FERN_AND_GRASS_LIGHT.makeAndSetHolder(WWVegetationFeatures.FERN_AND_GRASS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(10), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(28),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_LARGE_FERN_AND_GRASS.makeAndSetHolder(WWVegetationFeatures.LARGE_FERN_AND_TALL_GRASS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(36),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_LARGE_FERN_AND_GRASS_RARE.makeAndSetHolder(WWVegetationFeatures.LARGE_FERN_AND_TALL_GRASS,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(36),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_FERN_DENSE.makeAndSetHolder(features.getOrThrow(VegetationFeatures.LARGE_FERN),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			PlacementUtils.isEmpty()
		);

		PATCH_FERN_SWAMP.makeAndSetHolder(WWVegetationFeatures.FERN_SWAMP,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(24),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_CLOVER.makeAndSetHolder(WWVegetationFeatures.CLOVER,
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(6, 3),
			PlacementUtils.isEmpty()
		);

		PATCH_CLOVER_SPARSE.makeAndSetHolder(WWVegetationFeatures.CLOVER,
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(32),
				OffsetPlacement.ofTriangle(6, 3),
				PlacementUtils.isEmpty()
			)
		);

		// DRY GRASS
		PATCH_DRY_GRASS_BADLANDS.makeAndSetHolder(features.getOrThrow(VegetationFeatures.DRY_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(6), PlacementUtils.HEIGHTMAP),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_DRY_GRASS_DESERT.makeAndSetHolder(features.getOrThrow(VegetationFeatures.DRY_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(3), PlacementUtils.HEIGHTMAP),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_DRY_GRASS_BEACH.makeAndSetHolder(features.getOrThrow(VegetationFeatures.DRY_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP),
				CountPlacement.of(20),
				OffsetPlacement.ofTriangle(7, 3),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.matchesTag(Direction.DOWN, BlockTags.SAND)
					)
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
		PATCH_DRY_GRASS_BETA_BEACH.makeAndSetHolder(features.getOrThrow(VegetationFeatures.DRY_GRASS),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(
					RarityFilter.onAverageOnceEvery(2),
					PlacementUtils.HEIGHTMAP,
					BlockPredicateFilter.forPredicate(sandNearby)
				),
				CountPlacement.of(28),
				OffsetPlacement.ofTriangle(7, 3),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.matchesTag(Direction.DOWN, BlockTags.SAND)
					)
				)
			)
		);

		// FROZEN GRASS
		PATCH_FROZEN_GRASS_BONEMEAL.makeAndSetHolder(WWVegetationFeatures.SINGLE_PIECE_OF_FROZEN_GRASS, PlacementUtils.isEmpty());

		PATCH_FROZEN_GRASS_PLAIN.makeAndSetHolder(WWVegetationFeatures.FROZEN_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 1, 3),
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(32),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		registerPatch(
			context,
			PATCH_FROZEN_GRASS_FOREST,
			WWVegetationFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(5),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			32, 7, 3
		);

		registerPatch(
			context,
			PATCH_FROZEN_GRASS_NORMAL,
			WWVegetationFeatures.FROZEN_GRASS,
			RarityFilter.onAverageOnceEvery(6),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			32, 7, 3
		);

		registerPatch(
			context,
			PATCH_FROZEN_GRASS_TAIGA,
			WWVegetationFeatures.FROZEN_GRASS_TAIGA,
			RarityFilter.onAverageOnceEvery(4),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			32, 7, 3
		);

		registerPatch(
			context,
			PATCH_FROZEN_GRASS_TAIGA_2,
			WWVegetationFeatures.FROZEN_GRASS_TAIGA,
			RarityFilter.onAverageOnceEvery(3),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			32, 7, 3
		);

		PATCH_FROZEN_TALL_GRASS.makeAndSetHolder(WWVegetationFeatures.FROZEN_TALL_GRASS,
			RarityFilter.onAverageOnceEvery(10),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		PATCH_FROZEN_TALL_GRASS_2.makeAndSetHolder(WWVegetationFeatures.FROZEN_TALL_GRASS,
			NoiseThresholdCountPlacement.of(-0.8D, 0, 3),
			RarityFilter.onAverageOnceEvery(36),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(96),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		registerPatch(
			context,
			PATCH_FROZEN_BUSH,
			WWVegetationFeatures.FROZEN_BUSH,
			RarityFilter.onAverageOnceEvery(7),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			24, 7, 3
		);

		registerPatch(
			context,
			PATCH_FROZEN_LARGE_FERN,
			WWVegetationFeatures.FROZEN_LARGE_FERN,
			RarityFilter.onAverageOnceEvery(9),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			96, 7, 3
		);

		// FIREFLY BUSH
		PATCH_FIREFLY_BUSH_NEAR_WATER.makeAndSetHolder(features.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP),
				VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
				CountPlacement.of(20),
				OffsetPlacement.ofTriangle(4, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP.makeAndSetHolder(features.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(3), PlacementUtils.HEIGHTMAP),
				VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
				CountPlacement.of(20),
				OffsetPlacement.ofTriangle(4, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_FIREFLY_BUSH_SWAMP.makeAndSetHolder(features.getOrThrow(VegetationFeatures.FIREFLY_BUSH),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(8), PlacementUtils.HEIGHTMAP),
				VegetationFeatures.nearWaterPredicate(Blocks.FIREFLY_BUSH),
				CountPlacement.of(20),
				OffsetPlacement.ofTriangle(4, 3),
				PlacementUtils.isEmpty()
			)
		);

		// SHRUB
		final PlacementFilter shrubConfigFilter = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SHRUB));
		final PlacementFilter shrubPlacementFilter = BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				BlockPredicate.replaceable(),
				BlockPredicate.noFluid(),
				BlockPredicate.matchesTag(Direction.DOWN, WWFeatureBlockTags.SHRUB_MAY_PLACE_ON_FEATURE_NO_SAND)
			)
		);
		final PlacementFilter shrubSurvivalFilter = PlacementUtils.filteredByBlockSurvival(WWBlocks.SHRUB.get());

		PATCH_SHRUB.makeAndSetHolder(WWVegetationFeatures.SHRUB_GENERIC,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(12),
				OffsetPlacement.ofTriangle(6, 2),
				shrubPlacementFilter
			)
		);

		PATCH_SHRUB_SPARSE.makeAndSetHolder(WWVegetationFeatures.SHRUB_SPARSE,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(4),
				OffsetPlacement.ofTriangle(6, 2),
				shrubPlacementFilter
			)
		);

		PATCH_SHRUB_FLOWER_FIELD.makeAndSetHolder(WWVegetationFeatures.SHRUB_FLOWER_FIELD,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(6), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(18),
				OffsetPlacement.ofTriangle(6, 2),
				shrubPlacementFilter
			)
		);

		PATCH_SHRUB_JUNGLE.makeAndSetHolder(WWVegetationFeatures.SHRUB_JUNGLE,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(22),
				OffsetPlacement.ofTriangle(6, 2),
				shrubPlacementFilter
			)
		);

		PATCH_SHRUB_OASIS.makeAndSetHolder(WWVegetationFeatures.SHRUB_OASIS,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(22),
				OffsetPlacement.ofTriangle(6, 2),
				shrubPlacementFilter
			)
		);

		PATCH_SHRUB_DESERT.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(4),
				OffsetPlacement.ofTriangle(6, 2),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_SHRUB_ARID.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(4), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(4),
				OffsetPlacement.ofTriangle(6, 2),
				PlacementUtils.isEmpty()
			)
		);

		final PlacementFilter badlandsShrubOnSandFilter = BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				BlockPredicate.wouldSurvive(Blocks.CACTUS)
			)
		);
		PATCH_SHRUB_BADLANDS_SAND.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(7), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(8),
				OffsetPlacement.ofTriangle(6, 2),
				badlandsShrubOnSandFilter
			)
		);

		PATCH_SHRUB_BADLANDS_SAND_RARE.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(9), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(8),
				OffsetPlacement.ofTriangle(6, 2),
				badlandsShrubOnSandFilter
			)
		);

		final PlacementFilter badlandsShrubOnTerracottaFilter = BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				BlockPredicate.matchesTag(Direction.DOWN, BlockTags.BADLANDS_TERRACOTTA)
			)
		);
		PATCH_SHRUB_BADLANDS_TERRACOTTA.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(9), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(6),
				OffsetPlacement.ofTriangle(6, 2),
				badlandsShrubOnTerracottaFilter
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(7), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(10),
				OffsetPlacement.ofTriangle(6, 2),
				badlandsShrubOnTerracottaFilter
			)
		);

		PATCH_SHRUB_WOODED_BADLANDS_DIRT.makeAndSetHolder(WWVegetationFeatures.SHRUB_DESERT,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(5), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, shrubSurvivalFilter),
				CountPlacement.of(15),
				OffsetPlacement.ofTriangle(6, 2),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.matchesTag(Direction.DOWN, BlockTags.DIRT)
					)
				)
			)
		);

		// MUSHROOM
		final PlacementFilter mushroomConfigFilter = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_MUSHROOM));
		final PlacementFilter paleMushroomConfigFilter = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALE_MUSHROOM));

		BROWN_MUSHROOM.makeAndSetHolder(features.getOrThrow(VegetationFeatures.BROWN_MUSHROOM),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				mushroomConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		RED_MUSHROOM.makeAndSetHolder(features.getOrThrow(VegetationFeatures.RED_MUSHROOM),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				mushroomConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PALE_MUSHROOM.makeAndSetHolder(WWVegetationFeatures.PALE_MUSHROOM,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				paleMushroomConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		HUGE_PALE_MUSHROOM.makeAndSetHolder(WWTreeFeatures.HUGE_PALE_MUSHROOM,
			FrozenLibPlacementUtil.modifiersWithFilter(paleMushroomConfigFilter, RarityFilter.onAverageOnceEvery(3), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		PATCH_MUSHROOM_DARK_FOREST.makeAndSetHolder(WWVegetationFeatures.MUSHROOMS_DARK_FOREST,
			mushroomConfigFilter,
			RarityFilter.onAverageOnceEvery(4),
			CountPlacement.of(2),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(50),
			OffsetPlacement.ofTriangle(4, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		HUGE_BROWN_MUSHROOM.makeAndSetHolder(features.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(90), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_RED_MUSHROOM.makeAndSetHolder(features.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(90), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_MUSHROOM.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(6), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_MUSHROOM_SWAMP.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(5), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_MUSHROOM_RARE.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(20), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_MUSHROOM_MIXED_FOREST.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(75), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		HUGE_MUSHROOM_RAINFOREST.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MUSHROOM_ISLAND_VEGETATION),
			FrozenLibPlacementUtil.modifiersWithFilter(mushroomConfigFilter, RarityFilter.onAverageOnceEvery(8), PlacementUtils.HEIGHTMAP_WORLD_SURFACE)
		);

		PATCH_MYCELIUM_GROWTH.makeAndSetHolder(WWVegetationFeatures.MYCELIUM_GROWTH,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				mushroomConfigFilter,
				FrozenLibPlacementUtil.modifiers(CountPlacement.of(3), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(28),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		// CACTUS
		final PlacementFilter cactusConfigFilter = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_CACTUS));
		final PlacementFilter cactusSurvivalFilter = BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				BlockPredicate.wouldSurvive(Blocks.CACTUS)
			)
		);

		PATCH_CACTUS_TALL.makeAndSetHolder(WWVegetationFeatures.CACTUS_TALL,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(8), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(8),
				OffsetPlacement.ofTriangle(7, 3),
				cactusSurvivalFilter
			)
		);

		PATCH_CACTUS_OASIS.makeAndSetHolder(WWVegetationFeatures.CACTUS_OASIS,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(8),
				OffsetPlacement.ofTriangle(7, 3),
				cactusSurvivalFilter
			)
		);

		PATCH_CACTUS_ARID.makeAndSetHolder(features.getOrThrow(VegetationFeatures.CACTUS),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(7), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(10),
				OffsetPlacement.ofTriangle(7, 3),
				cactusSurvivalFilter
			)
		);

		PATCH_CACTUS_TALL_BADLANDS.makeAndSetHolder(WWVegetationFeatures.CACTUS_TALL_BADLANDS,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(7), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(12),
				OffsetPlacement.ofTriangle(7, 3),
				cactusSurvivalFilter
			)
		);

		PATCH_PRICKLY_PEAR.makeAndSetHolder(WWVegetationFeatures.PRICKLY_PEAR,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(7), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(15),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		PATCH_PRICKLY_PEAR_RARE.makeAndSetHolder(WWVegetationFeatures.PRICKLY_PEAR,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				cactusConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(9), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(15),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		// TUMBLEWEED
		PATCH_TUMBLEWEED.makeAndSetHolder(WWVegetationFeatures.TUMBLEWEED,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TUMBLEWEED)),
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(9), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(12),
				OffsetPlacement.ofTriangle(7, 3),
				PlacementUtils.isEmpty()
			)
		);

		// POLLEN
		POLLEN.makeAndSetHolder(WWVegetationFeatures.POLLEN,
			FrozenLibPlacementUtil.modifiersWithFilter(
				configPredicates.getOrThrow(WWConfigPredicates.GENERATE_POLLEN),
				CountPlacement.of(2),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE
			)
		);

		// MOSS
		MOSS_CARPET.makeAndSetHolder(WWVegetationFeatures.MOSS_CARPET,
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_MOSS)),
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(25),
				OffsetPlacement.ofTriangle(6, 2),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING)
					)
				)
			)
		);

		// BERRY
		PATCH_BERRY_FOREST.makeAndSetHolder(features.getOrThrow(VegetationFeatures.BERRY_BUSH),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				shrubConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(28), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.ONLY_IN_AIR_PREDICATE,
						BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
					)
				)
			)
		);

		// MELON
		PATCH_MELON.makeAndSetHolder(features.getOrThrow(VegetationFeatures.MELON),
			FrozenLibPlacementUtil.postModifiers(
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(64), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(64),
				OffsetPlacement.ofTriangle(7, 3),
				BlockPredicateFilter.forPredicate(
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
					)
				)
			)
		);

		final PlacementFilter pumpkinConfigFilter = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PUMPKIN));
		final PlacementFilter pumpkinPlacementFilter = BlockPredicateFilter.forPredicate(
			BlockPredicate.allOf(
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
			)
		);
		PATCH_PUMPKIN_COMMON.makeAndSetHolder(features.getOrThrow(VegetationFeatures.PUMPKIN),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				pumpkinConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(12), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				pumpkinPlacementFilter
			)
		);

		PATCH_PUMPKIN_UNCOMMON.makeAndSetHolder(features.getOrThrow(VegetationFeatures.PUMPKIN),
			FrozenLibPlacementUtil.postModifiersWithPreFilter(
				pumpkinConfigFilter,
				FrozenLibPlacementUtil.modifiers(RarityFilter.onAverageOnceEvery(24), PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				CountPlacement.of(96),
				OffsetPlacement.ofTriangle(7, 3),
				pumpkinPlacementFilter
			)
		);

		// MISC
		TERMITE_MOUND.makeAndSetHolder(WWVegetationFeatures.TERMITE_MOUND,
			ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TERMITE)),
			RarityFilter.onAverageOnceEvery(45),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
			BiomeFilter.biome()
		);
	}

	private static void registerLeafLitterPatch(
		FrozenLibPlacedFeature placement,
		Holder<Feature> feature,
		PlacementModifier count,
		int patchCount,
		int xzRange, int yRange,
		boolean requireOnGrass
	) {
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

		final PlacementFilter endFilter = requireOnGrass
			? BlockPredicateFilter.forPredicate(
				BlockPredicate.allOf(
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.GRASS_BLOCK)
				)
			)
			: BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE);
		placement.makeAndSetHolder(
			feature,
			postModifiers(
				FrozenLibPlacementUtil.modifiers(count, PlacementUtils.HEIGHTMAP_WORLD_SURFACE),
				leafLitterPlacementPredicate,
				CountPlacement.of(patchCount),
				OffsetPlacement.ofTriangle(xzRange, yRange),
				endFilter
			)
		);
	}

	private static void registerPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		Holder<Feature> feature,
		PlacementModifier count,
		PlacementModifier heightmap,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		int patchCount,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		placement.makeAndSetHolder(
			feature,
			postModifiers(
				baseModifiers(context, count, heightmap, configFilter, modifiers),
				CountPlacement.of(patchCount),
				OffsetPlacement.ofTriangle(xzRange, yRange),
				BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
			)
		);
	}

	private static void registerPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementModifier heightmap,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		int patchCount,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		registerPatch(context, placement, feature.getHolder(), count, heightmap, configFilter, patchCount, xzRange, yRange, modifiers);
	}

	private static void registerPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementModifier heightmap,
		int patchCount,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		registerPatch(context, placement, feature, count, heightmap, null, patchCount, xzRange, yRange, modifiers);
	}

	private static List<PlacementModifier> baseModifiers(
		BootstrapContext<PlacedFeature> context,
		PlacementModifier count,
		PlacementModifier heightmap,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		PlacementModifier... modifiers
	) {
		final List<PlacementModifier> modifiersWithHeightmap = new ArrayList<>(List.of(modifiers));
		modifiersWithHeightmap.add(heightmap);

		final List<PlacementModifier> baseModifiers = FrozenLibPlacementUtil.modifiers(count, modifiersWithHeightmap);

		if (configFilter != null) {
			final List<PlacementModifier> modifiersWithConfigFilter = new ArrayList<>(baseModifiers);
			modifiersWithConfigFilter.addFirst(ConfigPredicate.placementFilter(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(configFilter)));
			return modifiersWithConfigFilter;
		}

		return baseModifiers;
	}
}
