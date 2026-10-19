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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.features.WWFlowerFeatures;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.Nullable;

public final class WWFlowerPlacements {

	public static final FrozenLibPlacedFeature FLOWER_DEFAULT_WITH_SEEDING_DANDELION = register("flower_default_with_seeding_dandelion");

	// PATCH
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DEFAULT_64_LARGE = register("patch_flower_default_64_large");

	public static final FrozenLibPlacedFeature PATCH_ALLIUM_42 = register("patch_allium_42");

	public static final FrozenLibPlacedFeature PATCH_AZURE_BLUET_52_LARGE = register("patch_azure_bluet_52_large");

	public static final FrozenLibPlacedFeature PATCH_BLUE_ORCHID_36 = register("patch_blue_orchid_36");
	public static final FrozenLibPlacedFeature PATCH_BLUE_ORCHID_48 = register("patch_blue_orchid_48");

	public static final FrozenLibPlacedFeature PATCH_CARNATION_38 = register("patch_carnation_38");
	public static final FrozenLibPlacedFeature PATCH_CARNATION_42 = register("patch_carnation_42");
	public static final FrozenLibPlacedFeature PATCH_CARNATION_52 = register("patch_carnation_52");

	public static final FrozenLibPlacedFeature PATCH_DANDELION_72_LARGE = register("patch_dandelion_72_large");

	public static final FrozenLibPlacedFeature PATCH_LILY_OF_THE_VALLEY_38 = register("patch_lily_of_the_valley_38");
	public static final FrozenLibPlacedFeature PATCH_LILY_OF_THE_VALLEY_42 = register("patch_lily_of_the_valley_42");
	public static final FrozenLibPlacedFeature PATCH_LILY_OF_THE_VALLEY_48 = register("patch_lily_of_the_valley_48");

	public static final FrozenLibPlacedFeature PATCH_POPPY_32_LARGE = register("patch_poppy_32_large");
	public static final FrozenLibPlacedFeature PATCH_POPPY_38_LARGE = register("patch_poppy_38_large");
	public static final FrozenLibPlacedFeature PATCH_POPPY_48_LARGE = register("patch_poppy_48_large");

	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_32 = register("patch_marigold_32");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_42 = register("patch_marigold_42");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_46 = register("patch_marigold_46");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_52 = register("patch_marigold_52");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_32_LARGE = register("patch_marigold_32_large");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_38_LARGE = register("patch_marigold_38_large");

	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_32 = register("patch_seeding_dandelion_32");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_38 = register("patch_seeding_dandelion_38");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_42 = register("patch_seeding_dandelion_42");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_48 = register("patch_seeding_dandelion_48");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_52 = register("patch_seeding_dandelion_52");
	public static final FrozenLibPlacedFeature PATCH_SEEDING_DANDELION_72_LARGE = register("patch_seeding_dandelion_72_large");

	public static final FrozenLibPlacedFeature PATCH_HIBISCUS_32 = register("patch_hibiscus_32");
	public static final FrozenLibPlacedFeature PATCH_HIBISCUS_72 = register("patch_hibiscus_72");

	public static final FrozenLibPlacedFeature PATCH_POPPY_AND_PASQUEFLOWER_36 = register("patch_poppy_and_pasqueflower_36");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_AND_PASQUEFLOWER_36 = register("patch_marigold_and_pasqueflower_36");
	public static final FrozenLibPlacedFeature PATCH_MARIGOLD_AND_SEEDING_DANDELION_36 = register("patch_marigold_and_seeding_dandelion_36");
	public static final FrozenLibPlacedFeature PATCH_PASQUEFLOWER_AND_POPPY_36 = register("patch_pasqueflower_and_poppy_36");
	public static final FrozenLibPlacedFeature PATCH_AZURE_BLUET_AND_SEEDING_DANDELION_64_LARGE = register("patch_azure_bluet_and_seeding_dandelion_64_large");
	public static final FrozenLibPlacedFeature PATCH_POPPY_AND_MARIGOLD_64_LARGE = register("patch_poppy_and_marigold_64_large");

	public static final FrozenLibPlacedFeature PATCH_DATURA_32 = register("patch_datura_32");
	public static final FrozenLibPlacedFeature PATCH_DATURA_38 = register("patch_datura_38");
	public static final FrozenLibPlacedFeature PATCH_DATURA_42 = register("patch_datura_42");

	public static final FrozenLibPlacedFeature PATCH_LILAC_38 = register("patch_lilac_38");
	public static final FrozenLibPlacedFeature PATCH_LILAC_38_LARGE = register("patch_lilac_38_large");
	public static final FrozenLibPlacedFeature PATCH_LILAC_48_LARGE = register("patch_lilac_48_large");
	public static final FrozenLibPlacedFeature PATCH_LILAC_52_LARGE = register("patch_lilac_52_large");
	public static final FrozenLibPlacedFeature PATCH_LILAC_64_LARGE = register("patch_lilac_64_large");

	public static final FrozenLibPlacedFeature PATCH_MILKWEED_42 = register("patch_milkweed_42");
	public static final FrozenLibPlacedFeature PATCH_MILKWEED_64 = register("patch_milkweed_64");
	public static final FrozenLibPlacedFeature PATCH_MILKWEED_48_LARGE = register("patch_milkweed_48_large");
	public static final FrozenLibPlacedFeature PATCH_MILKWEED_52_LARGE = register("patch_milkweed_52_large");

	public static final FrozenLibPlacedFeature PATCH_PEONY_38 = register("patch_peony_38");
	public static final FrozenLibPlacedFeature PATCH_PEONY_42_LARGE = register("patch_peony_42_large");
	public static final FrozenLibPlacedFeature PATCH_PEONY_48_LARGE = register("patch_peony_48_large");
	public static final FrozenLibPlacedFeature PATCH_PEONY_52_LARGE = register("patch_peony_52_large");

	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_38 = register("patch_rose_bush_38");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_42 = register("patch_rose_bush_42");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_64 = register("patch_rose_bush_64");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_42_LARGE = register("patch_rose_bush_42_large");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_48_LARGE = register("patch_rose_bush_48_large");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_52_LARGE = register("patch_rose_bush_52_large");
	public static final FrozenLibPlacedFeature PATCH_ROSE_BUSH_64_LARGE = register("patch_rose_bush_64_large");

	// PLAINS
	public static final FrozenLibPlacedFeature FLOWER_DEFAULT_PLAINS = register("flower_default_plains");
	public static final FrozenLibPlacedFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenLibPlacedFeature FLOWER_DEFAULT_SNOWY_PLAINS = register("flower_default_snowy_plains");
	public static final FrozenLibPlacedFeature WILDFLOWERS_SUNFLOWER_PLAINS = register("wildflowers_sunflower_plains");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_SUNFLOWER_PLAINS = register("patch_flower_sunflower_plains");
	public static final FrozenLibPlacedFeature FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_FLOWER_FIELD = register("patch_flower_flower_field");

	// FOREST
	public static final FrozenLibPlacedFeature FLOWER_FOREST = register("flower_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_FOREST = register("patch_flower_forest");
	public static final FrozenLibPlacedFeature FLOWER_SPARSE_FOREST = register("flower_sparse_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_SPARSE_FOREST = register("patch_flower_sparse_forest");
	public static final FrozenLibPlacedFeature SEEDING_DANDELION_MIXED_FOREST = register("seeding_dandelion_mixed_forest");
	public static final FrozenLibPlacedFeature FLOWER_RAINFOREST = register("flower_rainforest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_RAINFOREST = register("patch_flower_rainforest");
	public static final FrozenLibPlacedFeature FLOWER_FLOWER_FOREST_1 = register("flower_flower_forest_1");
	public static final FrozenLibPlacedFeature FLOWER_FLOWER_FOREST_2 = register("flower_flower_forest_2");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_FLOWER_FOREST = register("patch_flower_flower_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DYING_FOREST = register("patch_flower_dying_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DYING_MIXED_FOREST = register("patch_flower_dying_mixed_forest");
	public static final FrozenLibPlacedFeature SEEDING_DANDELION_SNOWY_DYING_FOREST = register("seeding_dandelion_snowy_dying_forest");

	// BIRCH FOREST
	public static final FrozenLibPlacedFeature WILDFLOWERS_BIRCH_FOREST = register("wildflowers_birch_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_BIRCH_FOREST = register("patch_flower_birch_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_OLD_GROWTH_BIRCH_FOREST = register("patch_flower_old_growth_birch_forest");
	public static final FrozenLibPlacedFeature WILDFLOWERS_SEMI_BIRCH_FOREST = register("wildflowers_semi_birch_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_SEMI_BIRCH_FOREST = register("patch_flower_semi_birch_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_BIRCH_JUNGLE = register("patch_flower_birch_jungle");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_SPARSE_BIRCH_JUNGLE = register("patch_flower_sparse_birch_jungle");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_BIRCH_TAIGA = register("patch_flower_birch_taiga");

	// DARK FOREST
	public static final FrozenLibPlacedFeature PATCH_PHLOX_AND_LANTANAS_DARK_FOREST = register("patch_phlox_and_lantanas_dark_forest");
	public static final FrozenLibPlacedFeature FLOWER_DARK_FOREST = register("flower_dark_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DARK_FOREST = register("patch_flower_dark_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DARK_BIRCH_FOREST = register("patch_flower_dark_birch_forest");
	public static final FrozenLibPlacedFeature PHLOX_DARK_TAIGA = register("phlox_dark_taiga");
	public static final FrozenLibPlacedFeature FLOWER_DARK_TAIGA = register("flower_dark_taiga");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DARK_TAIGA = register("patch_flower_dark_taiga");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_PALE_GARDEN = register("patch_flower_pale_garden");

	// JUNGLE
	public static final FrozenLibPlacedFeature FLOWER_JUNGLE = register("flower_jungle");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_JUNGLE = register("patch_flower_jungle");
	public static final FrozenLibPlacedFeature FLOWER_SPARSE_JUNGLE = register("flower_sparse_jungle");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_SPARSE_JUNGLE = register("patch_flower_sparse_jungle");

	// SAVANNA
	public static final FrozenLibPlacedFeature LANTANAS_SAVANNA = register("lantanas_savanna");

	// SWAMP
	public static final FrozenLibPlacedFeature MILKWEED_SWAMP = register("milkweed_swamp");
	public static final FrozenLibPlacedFeature PHLOX_SWAMP = register("phlox_swamp");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_MANGROVE_SWAMP = register("patch_flower_mangrove_swamp");
	public static final FrozenLibPlacedFeature FLOWER_CYPRESS_WETLANDS = register("flower_cypress_wetlands");

	// CHERRY GROVE
	public static final FrozenLibPlacedFeature PATCH_FLOWER_CHERRY_GROVE = register("patch_flower_cherry_grove");

	// AUTUMNAL
	public static final FrozenLibPlacedFeature FLOWER_MAPLE_FOREST = register("flower_maple_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_MAPLE_FOREST = register("patch_flower_maple_forest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_TUNDRA = register("patch_flower_tundra");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_DAPPLED_FOREST = register("patch_flower_dappled_forest");

	// MEADOW
	public static final FrozenLibPlacedFeature PATCH_WILDFLOWERS_AND_PHLOX_MEADOW = register("patch_wildflowers_and_phlox_meadow");
	public static final FrozenLibPlacedFeature FLOWER_MEADOW = register("flower_meadow");

	// TAIGA
	public static final FrozenLibPlacedFeature SEEDING_DANDELION_WINDSWEPT = register("seeding_dandelion_windswept");
	public static final FrozenLibPlacedFeature FLOWER_TEMPERATE_RAINFOREST = register("flower_temperate_rainforest");
	public static final FrozenLibPlacedFeature PATCH_FLOWER_TEMPERATE_RAINFOREST = register("patch_flower_temperate_rainforest");

	public static void bootstrap(BootstrapContext<PlacedFeature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		FLOWER_DEFAULT_WITH_SEEDING_DANDELION.makeAndSetHolder(
			WWFlowerFeatures.FLOWER_DEFAULT_WITH_SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(32),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP,
			BiomeFilter.biome(),
			CountPlacement.of(64),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);

		// PATCH
		registerFlowerPatchForFeature(PATCH_FLOWER_DEFAULT_64_LARGE, features.getOrThrow(VegetationFeatures.FLOWER_DEFAULT), 64, 7, 3);

		registerFlowerPatchForFeature(PATCH_ALLIUM_42, WWFlowerFeatures.ALLIUM.getHolder(), 42, 6, 2);

		registerFlowerPatchForFeature(PATCH_AZURE_BLUET_52_LARGE, WWFlowerFeatures.AZURE_BLUET.getHolder(), 52, 7, 3);

		registerFlowerPatchForFeature(PATCH_BLUE_ORCHID_36, WWFlowerFeatures.BLUE_ORCHID.getHolder(), 36, 6, 2);
		registerFlowerPatchForFeature(PATCH_BLUE_ORCHID_48, WWFlowerFeatures.BLUE_ORCHID.getHolder(), 48, 6, 2);

		registerFlowerPatchForFeature(PATCH_CARNATION_38, WWFlowerFeatures.CARNATION.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_CARNATION_42, WWFlowerFeatures.CARNATION.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_CARNATION_52, WWFlowerFeatures.CARNATION.getHolder(), 52, 6, 2);

		registerFlowerPatchForFeature(PATCH_DANDELION_72_LARGE, WWFlowerFeatures.DANDELION.getHolder(), 72, 7, 3);

		registerFlowerPatchForFeature(PATCH_LILY_OF_THE_VALLEY_38, WWFlowerFeatures.LILY_OF_THE_VALLEY.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_LILY_OF_THE_VALLEY_42, WWFlowerFeatures.LILY_OF_THE_VALLEY.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_LILY_OF_THE_VALLEY_48, WWFlowerFeatures.LILY_OF_THE_VALLEY.getHolder(), 48, 6, 2);

		registerFlowerPatchForFeature(PATCH_POPPY_32_LARGE, WWFlowerFeatures.DANDELION.getHolder(), 32, 7, 3);
		registerFlowerPatchForFeature(PATCH_POPPY_38_LARGE, WWFlowerFeatures.DANDELION.getHolder(), 38, 7, 3);
		registerFlowerPatchForFeature(PATCH_POPPY_48_LARGE, WWFlowerFeatures.DANDELION.getHolder(), 48, 7, 3);

		registerFlowerPatchForFeature(PATCH_MARIGOLD_32, WWFlowerFeatures.MARIGOLD.getHolder(), 32, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_42, WWFlowerFeatures.MARIGOLD.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_46, WWFlowerFeatures.MARIGOLD.getHolder(), 46, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_52, WWFlowerFeatures.MARIGOLD.getHolder(), 52, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_32_LARGE, WWFlowerFeatures.MARIGOLD.getHolder(), 32, 7, 3);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_38_LARGE, WWFlowerFeatures.MARIGOLD.getHolder(), 38, 7, 3);

		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_32, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 32, 6, 2);
		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_38, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_42, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_48, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 48, 6, 2);
		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_52, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 52, 6, 2);
		registerFlowerPatchForFeature(PATCH_SEEDING_DANDELION_72_LARGE, WWFlowerFeatures.SEEDING_DANDELION.getHolder(), 72, 7, 3);

		registerFlowerPatchForFeature(PATCH_HIBISCUS_32, WWFlowerFeatures.HIBISCUS.getHolder(), 32, 6, 2);
		registerFlowerPatchForFeature(PATCH_HIBISCUS_72, WWFlowerFeatures.HIBISCUS.getHolder(), 72, 6, 2);

		registerFlowerPatchForFeature(PATCH_POPPY_AND_PASQUEFLOWER_36, WWFlowerFeatures.POPPY_AND_PASQUEFLOWER.getHolder(), 36, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_AND_PASQUEFLOWER_36, WWFlowerFeatures.MARIGOLD_AND_PASQUEFLOWER.getHolder(), 36, 6, 2);
		registerFlowerPatchForFeature(PATCH_MARIGOLD_AND_SEEDING_DANDELION_36, WWFlowerFeatures.MARIGOLD_AND_SEEDING_DANDELION.getHolder(), 36, 6, 2);
		registerFlowerPatchForFeature(PATCH_PASQUEFLOWER_AND_POPPY_36, WWFlowerFeatures.PASQUEFLOWER_AND_POPPY.getHolder(), 36, 6, 2);
		registerFlowerPatchForFeature(PATCH_AZURE_BLUET_AND_SEEDING_DANDELION_64_LARGE, WWFlowerFeatures.AZURE_BLUET_AND_SEEDING_DANDELION.getHolder(), 64, 7, 3);
		registerFlowerPatchForFeature(PATCH_POPPY_AND_MARIGOLD_64_LARGE, WWFlowerFeatures.POPPY_AND_MARIGOLD.getHolder(), 64, 7, 3);

		registerFlowerPatchForFeature(PATCH_DATURA_32, WWFlowerFeatures.DATURA.getHolder(), 32, 6, 2);
		registerFlowerPatchForFeature(PATCH_DATURA_38, WWFlowerFeatures.DATURA.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_DATURA_42, WWFlowerFeatures.DATURA.getHolder(), 42, 6, 2);

		registerFlowerPatchForFeature(PATCH_LILAC_38, WWFlowerFeatures.LILAC.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_LILAC_38_LARGE, WWFlowerFeatures.LILAC.getHolder(), 38, 7, 3);
		registerFlowerPatchForFeature(PATCH_LILAC_48_LARGE, WWFlowerFeatures.LILAC.getHolder(), 48, 7, 3);
		registerFlowerPatchForFeature(PATCH_LILAC_52_LARGE, WWFlowerFeatures.LILAC.getHolder(), 52, 7, 3);
		registerFlowerPatchForFeature(PATCH_LILAC_64_LARGE, WWFlowerFeatures.LILAC.getHolder(), 64, 7, 3);

		registerFlowerPatchForFeature(PATCH_MILKWEED_42, WWFlowerFeatures.MILKWEED.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_MILKWEED_64, WWFlowerFeatures.MILKWEED.getHolder(), 64, 6, 2);
		registerFlowerPatchForFeature(PATCH_MILKWEED_48_LARGE, WWFlowerFeatures.MILKWEED.getHolder(), 48, 7, 3);
		registerFlowerPatchForFeature(PATCH_MILKWEED_52_LARGE, WWFlowerFeatures.MILKWEED.getHolder(), 52, 7, 3);

		registerFlowerPatchForFeature(PATCH_PEONY_38, WWFlowerFeatures.PEONY.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_PEONY_42_LARGE, WWFlowerFeatures.PEONY.getHolder(), 42, 7, 3);
		registerFlowerPatchForFeature(PATCH_PEONY_48_LARGE, WWFlowerFeatures.PEONY.getHolder(), 48, 7, 3);
		registerFlowerPatchForFeature(PATCH_PEONY_52_LARGE, WWFlowerFeatures.PEONY.getHolder(), 52, 7, 3);

		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_38, WWFlowerFeatures.ROSE_BUSH.getHolder(), 38, 6, 2);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_42, WWFlowerFeatures.ROSE_BUSH.getHolder(), 42, 6, 2);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_64, WWFlowerFeatures.ROSE_BUSH.getHolder(), 64, 6, 2);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_42_LARGE, WWFlowerFeatures.ROSE_BUSH.getHolder(), 42, 7, 3);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_48_LARGE, WWFlowerFeatures.ROSE_BUSH.getHolder(), 48, 7, 3);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_52_LARGE, WWFlowerFeatures.ROSE_BUSH.getHolder(), 52, 7, 3);
		registerFlowerPatchForFeature(PATCH_ROSE_BUSH_64_LARGE, WWFlowerFeatures.ROSE_BUSH.getHolder(), 64, 7, 3);


		// PLAINS
		FLOWER_DEFAULT_PLAINS.makeAndSetHolder(
			WWFlowerFeatures.FLOWER_DEFAULT_PLAINS,
			NoiseThresholdCountPlacement.of(-0.8, 1, 4),
			RarityFilter.onAverageOnceEvery(32),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(16),
			OffsetPlacement.ofTriangle(4, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
		FLOWER_PLAINS.makeAndSetHolder(
			WWFlowerFeatures.FLOWER_PLAINS,
			NoiseThresholdCountPlacement.of(-0.8D, 15, 3),
			RarityFilter.onAverageOnceEvery(32),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(82),
			OffsetPlacement.ofTriangle(7, 3),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
		FLOWER_DEFAULT_SNOWY_PLAINS.makeAndSetHolder(
			WWFlowerFeatures.FLOWER_DEFAULT_SNOWY_PLAINS,
			RarityFilter.onAverageOnceEvery(28),
			InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
			BiomeFilter.biome(),
			CountPlacement.of(42),
			OffsetPlacement.ofTriangle(6, 2),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
		registerFlowerPatch(
			context,
			WILDFLOWERS_SUNFLOWER_PLAINS,
			WWFlowerFeatures.WILDFLOWERS,
			NoiseThresholdCountPlacement.of(-0.2, 0, 2),
			58, 6, 2,
			RarityFilter.onAverageOnceEvery(2),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 3), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_SUNFLOWER_PLAINS,
			WWFlowerFeatures.PATCH_FLOWER_SUNFLOWER_PLAINS,
			NoiseThresholdCountPlacement.of(-0.3, 3, 1),
			RarityFilter.onAverageOnceEvery(8)
		);
		registerFlowerPatch(
			context,
			FLOWER_FLOWER_FIELD,
			WWFlowerFeatures.FLOWER_FLOWER_FIELD,
			CountPlacement.of(ClampedInt.of(UniformInt.of(1, 3), 1, 2)),
			64, 6, 2,
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_FLOWER_FIELD,
			WWFlowerFeatures.PATCH_FLOWER_FLOWER_FIELD,
			CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 1), 0, 1)),
			RarityFilter.onAverageOnceEvery(3)
		);

		// FOREST
		registerFlowerPatch(
			context,
			FLOWER_FOREST,
			WWFlowerFeatures.FLOWER_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			52, 7, 3,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 5), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_FOREST,
			RarityFilter.onAverageOnceEvery(8),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowerPatch(
			context,
			FLOWER_SPARSE_FOREST,
			WWFlowerFeatures.FLOWER_FOREST,
			RarityFilter.onAverageOnceEvery(9),
			52, 7, 3,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_SPARSE_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_SPARSE_FOREST,
			RarityFilter.onAverageOnceEvery(9),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowerPatch(
			context,
			SEEDING_DANDELION_MIXED_FOREST,
			WWFlowerFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(9),
			WWConfigPredicates.GENERATE_FLOWER,
			48, 6, 2
		);
		registerFlowerPatch(
			context,
			FLOWER_RAINFOREST,
			WWFlowerFeatures.FLOWER_RAINFOREST,
			RarityFilter.onAverageOnceEvery(7),
			48, 6, 2,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_RAINFOREST,
			WWFlowerFeatures.PATCH_FLOWER_RAINFOREST,
			RarityFilter.onAverageOnceEvery(8),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowerPatch(
			context,
			FLOWER_FLOWER_FOREST_1,
			WWFlowerFeatures.FLOWER_FLOWER_FOREST_1,
			CountPlacement.of(ClampedInt.of(UniformInt.of(2, 4), 2, 3)),
			72, 6, 2,
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowerPatch(
			context,
			FLOWER_FLOWER_FOREST_2,
			WWFlowerFeatures.FLOWER_FLOWER_FOREST_2,
			CountPlacement.of(1),
			64, 6, 2,
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_FLOWER_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_FLOWER_FOREST,
			RarityFilter.onAverageOnceEvery(4)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DYING_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_DYING_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			WWConfigPredicates.GENERATE_FLOWER,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 5), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DYING_MIXED_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_DYING_MIXED_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			WWConfigPredicates.GENERATE_FLOWER,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 5), 0, 1))
		);
		registerFlowerPatch(
			context,
			SEEDING_DANDELION_SNOWY_DYING_FOREST,
			WWFlowerFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(10),
			WWConfigPredicates.GENERATE_FLOWER,
			48, 6, 2
		);

		// BIRCH FOREST
		registerFlowerPatch(
			context,
			WILDFLOWERS_BIRCH_FOREST,
			WWFlowerFeatures.WILDFLOWERS,
			CountPlacement.of(3),
			64, 6, 2,
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_BIRCH_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_BIRCH_FOREST,
			RarityFilter.onAverageOnceEvery(5),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 5), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_OLD_GROWTH_BIRCH_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_BIRCH_FOREST,
			RarityFilter.onAverageOnceEvery(4)
		);
		registerFlowerPatch(
			context,
			WILDFLOWERS_SEMI_BIRCH_FOREST,
			WWFlowerFeatures.WILDFLOWERS,
			CountPlacement.of(UniformInt.of(1, 2)),
			58, 6, 2,
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_SEMI_BIRCH_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_SEMI_BIRCH_FOREST,
			RarityFilter.onAverageOnceEvery(6)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_BIRCH_JUNGLE,
			WWFlowerFeatures.PATCH_FLOWER_BIRCH_JUNGLE,
			RarityFilter.onAverageOnceEvery(3)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_SPARSE_BIRCH_JUNGLE,
			WWFlowerFeatures.PATCH_FLOWER_BIRCH_JUNGLE,
			RarityFilter.onAverageOnceEvery(5)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_BIRCH_TAIGA,
			WWFlowerFeatures.PATCH_FLOWER_BIRCH_TAIGA,
			RarityFilter.onAverageOnceEvery(6)
		);

		// DARK FOREST
		registerFlowers(
			context,
			PATCH_PHLOX_AND_LANTANAS_DARK_FOREST,
			WWFlowerFeatures.PATCH_PHLOX_AND_LANTANAS_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(5)
		);
		registerFlowerPatch(
			context,
			FLOWER_DARK_FOREST,
			WWFlowerFeatures.FLOWER_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			52, 7, 3,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 3), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DARK_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_DARK_FOREST,
			RarityFilter.onAverageOnceEvery(4)
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DARK_BIRCH_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_DARK_BIRCH_FOREST,
			RarityFilter.onAverageOnceEvery(7)
		);
		registerFlowerPatch(
			context,
			PHLOX_DARK_TAIGA,
			WWFlowerFeatures.PHLOX,
			RarityFilter.onAverageOnceEvery(8),
			48, 6, 2,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowerPatch(
			context,
			FLOWER_DARK_TAIGA,
			WWFlowerFeatures.FLOWER_DARK_TAIGA,
			RarityFilter.onAverageOnceEvery(14),
			42, 7, 3,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 3), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DARK_TAIGA,
			WWFlowerFeatures.PATCH_FLOWER_DARK_TAIGA,
			RarityFilter.onAverageOnceEvery(6)
		);
		registerFlowerPatch(
			context,
			PATCH_FLOWER_PALE_GARDEN,
			WWFlowerFeatures.EYEBLOSSOM,
			RarityFilter.onAverageOnceEvery(5),
			24, 7, 3
		);

		// JUNGLE
		registerFlowers(
			context,
			FLOWER_JUNGLE,
			WWFlowerFeatures.FLOWER_JUNGLE,
			RarityFilter.onAverageOnceEvery(3),
			WWConfigPredicates.GENERATE_FLOWER
		);
		registerFlowers(
			context,
			PATCH_FLOWER_JUNGLE,
			WWFlowerFeatures.PATCH_FLOWER_JUNGLE,
			RarityFilter.onAverageOnceEvery(3),
			WWConfigPredicates.GENERATE_FLOWER
		);
		registerFlowers(
			context,
			FLOWER_SPARSE_JUNGLE,
			WWFlowerFeatures.FLOWER_JUNGLE,
			RarityFilter.onAverageOnceEvery(6),
			WWConfigPredicates.GENERATE_FLOWER
		);
		registerFlowers(
			context,
			PATCH_FLOWER_SPARSE_JUNGLE,
			WWFlowerFeatures.PATCH_FLOWER_JUNGLE,
			RarityFilter.onAverageOnceEvery(6),
			WWConfigPredicates.GENERATE_FLOWER
		);

		// SAVANNA
		registerFlowerPatch(
			context,
			LANTANAS_SAVANNA,
			WWFlowerFeatures.LANTANAS,
			RarityFilter.onAverageOnceEvery(10),
			WWConfigPredicates.GENERATE_FLOWER,
			30, 6, 2
		);

		// SWAMP
		registerFlowerPatch(
			context,
			MILKWEED_SWAMP,
			WWFlowerFeatures.MILKWEED,
			RarityFilter.onAverageOnceEvery(8),
			WWConfigPredicates.GENERATE_FLOWER,
			38, 7, 3
		);
		registerFlowerPatch(
			context,
			PHLOX_SWAMP,
			WWFlowerFeatures.PHLOX,
			RarityFilter.onAverageOnceEvery(8),
			WWConfigPredicates.GENERATE_FLOWER,
			42, 6, 2
		);
		registerFlowers(
			context,
			PATCH_FLOWER_MANGROVE_SWAMP,
			WWFlowerFeatures.PATCH_FLOWER_MANGROVE_SWAMP,
			RarityFilter.onAverageOnceEvery(5),
			WWConfigPredicates.GENERATE_FLOWER
		);
		registerFlowerPatch(
			context,
			FLOWER_CYPRESS_WETLANDS,
			WWFlowerFeatures.FLOWER_CYPRESS_WETLANDS,
			RarityFilter.onAverageOnceEvery(3),
			52, 6, 2,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 3), 0, 1))
		);

		// CHERRY GROVE
		registerFlowers(
			context,
			PATCH_FLOWER_CHERRY_GROVE,
			WWFlowerFeatures.PATCH_FLOWER_CHERRY_GROVE,
			RarityFilter.onAverageOnceEvery(5),
			WWConfigPredicates.GENERATE_FLOWER
		);

		// AUTUMNAL
		registerFlowerPatch(
			context,
			FLOWER_MAPLE_FOREST,
			WWFlowerFeatures.FLOWER_MAPLE_FOREST,
			RarityFilter.onAverageOnceEvery(10),
			72, 7, 3,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 3), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_MAPLE_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_MAPLE_FOREST,
			RarityFilter.onAverageOnceEvery(6),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_TUNDRA,
			WWFlowerFeatures.PATCH_FLOWER_TUNDRA,
			NoiseThresholdCountPlacement.of(-0.7D, 2, 1),
			RarityFilter.onAverageOnceEvery(6),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_DAPPLED_FOREST,
			WWFlowerFeatures.PATCH_FLOWER_DAPPLED_FOREST,
			RarityFilter.onAverageOnceEvery(6),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);

		// MEADOW
		registerFlowers(
			context,
			PATCH_WILDFLOWERS_AND_PHLOX_MEADOW,
			WWFlowerFeatures.PATCH_WILDFLOWERS_AND_PHLOX_MEADOW,
			CountPlacement.of(UniformInt.of(1, 2)),
			RarityFilter.onAverageOnceEvery(2)
		);
		registerFlowerPatch(
			context,
			FLOWER_MEADOW,
			WWFlowerFeatures.FLOWER_MEADOW,
			CountPlacement.of(BiasedToBottomInt.of(1, 2)),
			96, 6, 2
		);

		// TAIGA
		registerFlowerPatch(
			context,
			SEEDING_DANDELION_WINDSWEPT,
			WWFlowerFeatures.SEEDING_DANDELION,
			RarityFilter.onAverageOnceEvery(9),
			WWConfigPredicates.GENERATE_FLOWER,
			58, 7, 3
		);
		registerFlowerPatch(
			context,
			FLOWER_TEMPERATE_RAINFOREST,
			WWFlowerFeatures.FLOWER_TEMPERATE_RAINFOREST,
			RarityFilter.onAverageOnceEvery(7),
			32, 6, 2,
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
		registerFlowers(
			context,
			PATCH_FLOWER_TEMPERATE_RAINFOREST,
			WWFlowerFeatures.PATCH_FLOWER_TEMPERATE_RAINFOREST,
			RarityFilter.onAverageOnceEvery(8),
			CountPlacement.of(ClampedInt.of(UniformInt.of(0, 4), 0, 1))
		);
	}

	private static void registerFlowers(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		PlacementModifier... modifiers
	) {
		placement.makeAndSetHolder(feature, baseModifiers(context, count, configFilter, modifiers));
	}

	private static void registerFlowers(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		PlacementModifier... modifiers
	) {
		registerFlowers(context, placement, feature, count, null, modifiers);
	}

	private static void registerFlowerPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		int patchCount,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		placement.makeAndSetHolder(
			feature,
			postModifiers(
				baseModifiers(context, count, configFilter, modifiers),
				CountPlacement.of(patchCount),
				OffsetPlacement.ofTriangle(xzRange, yRange),
				BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
			)
		);
	}

	private static void registerFlowerPatch(
		BootstrapContext<PlacedFeature> context,
		FrozenLibPlacedFeature placement,
		FrozenLibFeature feature,
		PlacementModifier count,
		int patchCount,
		int xzRange, int yRange,
		PlacementModifier... modifiers
	) {
		registerFlowerPatch(context, placement, feature, count, null, patchCount, xzRange, yRange, modifiers);
	}

	private static List<PlacementModifier> baseModifiers(
		BootstrapContext<PlacedFeature> context,
		PlacementModifier count,
		@Nullable ResourceKey<ConfigPredicate> configFilter,
		PlacementModifier... modifiers
	) {
		final List<PlacementModifier> modifiersWithHeightmap = new ArrayList<>(List.of(modifiers));
		modifiersWithHeightmap.add(PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

		final List<PlacementModifier> baseModifiers = FrozenLibPlacementUtil.modifiers(count, modifiersWithHeightmap.toArray(new PlacementModifier[0]));

		if (configFilter != null) {
			final List<PlacementModifier> modifiersWithConfigFilter = new ArrayList<>(baseModifiers);
			modifiersWithConfigFilter.addFirst(ConfigPredicate.placementFilter(context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER).getOrThrow(configFilter)));
			return modifiersWithConfigFilter;
		}

		return baseModifiers;
	}

	private static void registerFlowerPatchForFeature(
		FrozenLibPlacedFeature placement,
		Holder<Feature> feature,
		int patchCount,
		int xzRange, int yRange
	) {
		placement.makeAndSetHolder(
			feature,
			CountPlacement.of(patchCount),
			OffsetPlacement.ofTriangle(xzRange, yRange),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
	}

	public static FrozenLibPlacedFeature register(String name) {
		return new FrozenLibPlacedFeature(WWConstants.id("flower/" + name));
	}

	private WWFlowerPlacements() {}
}
