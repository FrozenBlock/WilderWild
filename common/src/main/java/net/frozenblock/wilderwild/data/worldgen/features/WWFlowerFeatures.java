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

package net.frozenblock.wilderwild.data.worldgen.features;

import java.util.Comparator;
import java.util.Map;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ConfigSelectorFeature;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.placement.WWFlowerPlacements;
import net.frozenblock.wilderwild.registry.WWBlockStateProviders;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OverlayFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.WeightedRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWFlowerFeatures {
	public static final FrozenLibFeature ALLIUM = register("allium");
	public static final FrozenLibFeature AZURE_BLUET = register("azure_bluet");
	public static final FrozenLibFeature BLUE_ORCHID = register("blue_orchid");
	public static final FrozenLibFeature CARNATION = register("carnation");
	public static final FrozenLibFeature DANDELION = register("dandelion");
	public static final FrozenLibFeature EYEBLOSSOM = register("eyeblossom");
	public static final FrozenLibFeature LILY_OF_THE_VALLEY = register("lily_of_the_valley");
	public static final FrozenLibFeature MARIGOLD = register("marigold");
	public static final FrozenLibFeature POPPY = register("poppy");
	public static final FrozenLibFeature SEEDING_DANDELION = register("seeding_dandelion");

	public static final FrozenLibFeature HIBISCUS = register("hibiscus");
	public static final FrozenLibFeature POPPY_AND_PASQUEFLOWER = register("poppy_and_pasqueflower");
	public static final FrozenLibFeature MARIGOLD_AND_PASQUEFLOWER = register("marigold_and_pasqueflower");
	public static final FrozenLibFeature MARIGOLD_AND_SEEDING_DANDELION = register("marigold_and_seeding_dandelion");
	public static final FrozenLibFeature PASQUEFLOWER_AND_POPPY = register("pasqueflower_and_poppy");
	public static final FrozenLibFeature AZURE_BLUET_AND_SEEDING_DANDELION = register("azure_bluet_and_seeding_dandelion");
	public static final FrozenLibFeature POPPY_AND_MARIGOLD = register("poppy_and_marigold");

	public static final FrozenLibFeature DATURA = register("datura");
	public static final FrozenLibFeature LILAC = register("lilac");
	public static final FrozenLibFeature MILKWEED = register("milkweed");
	public static final FrozenLibFeature PEONY = register("peony");
	public static final FrozenLibFeature ROSE_BUSH = register("rose_bush");

	public static final FrozenLibFeature LANTANAS = register("lantanas");
	public static final FrozenLibFeature PHLOX = register("phlox");
	public static final FrozenLibFeature WILDFLOWERS = register("wildflowers");

	public static final FrozenLibFeature FLOWER_DEFAULT_WITH_SEEDING_DANDELION = register("flower_default_with_seeding_dandelion");

	// PLAINS
	public static final FrozenLibFeature FLOWER_DEFAULT_PLAINS = register("flower_default_plains");
	public static final FrozenLibFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenLibFeature FLOWER_DEFAULT_SNOWY_PLAINS = register("flower_default_snowy_plains");

	public static final FrozenLibFeature SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS = register("seeding_dandelion_and_dandelion_sunflower_plains");
	public static final FrozenLibFeature FLOWER_SUNFLOWER_PLAINS = register("flower_sunflower_plains");
	public static final FrozenLibFeature FLOWER_SUNFLOWER_PLAINS_PINK = register("flower_sunflower_plains_pink");
	public static final FrozenLibFeature PATCH_FLOWER_SUNFLOWER_PLAINS = register("patch_flower_sunflower_plains");

	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_RED = register("flower_flower_field_red");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_RED_TALL = register("flower_flower_field_red_tall");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_PINK = register("flower_flower_field_pink");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_PINK_TALL_1 = register("flower_flower_field_pink_tall_1");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_PINK_TALL_2 = register("flower_flower_field_pink_tall_2");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_ORANGE = register("flower_flower_field_orange");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_ORANGE_TALL = register("flower_flower_field_orange_tall");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_YELLOW = register("flower_flower_field_yellow");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_WHITE = register("flower_flower_field_white");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_BLUE = register("flower_flower_field_blue");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_PURPLE = register("flower_flower_field_purple");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD_PURPLE_TALL = register("flower_flower_field_purple_tall");
	public static final FrozenLibFeature FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenLibFeature PATCH_FLOWER_FLOWER_FIELD = register("patch_flower_flower_field");

	// FOREST
	public static final FrozenLibFeature FLOWER_FOREST = register("flower_forest");
	public static final FrozenLibFeature PATCH_FLOWER_FOREST = register("patch_flower_forest");
	public static final FrozenLibFeature PATCH_FLOWER_SPARSE_FOREST = register("patch_flower_sparse_forest");
	public static final FrozenLibFeature FLOWER_RAINFOREST = register("flower_rainforest");
	public static final FrozenLibFeature PATCH_FLOWER_RAINFOREST = register("patch_flower_rainforest");
	public static final FrozenLibFeature FLOWER_FLOWER_FOREST_1 = register("flower_flower_forest_1");
	public static final FrozenLibFeature FLOWER_FLOWER_FOREST_2 = register("flower_flower_forest_2");
	public static final FrozenLibFeature PATCH_FLOWER_FLOWER_FOREST = register("patch_flower_flower_forest");
	public static final FrozenLibFeature PATCH_FLOWER_DYING_FOREST = register("patch_flower_dying_forest");
	public static final FrozenLibFeature PATCH_FLOWER_DYING_MIXED_FOREST = register("patch_flower_dying_mixed_forest");

	// BIRCH FOREST
	public static final FrozenLibFeature PATCH_FLOWER_BIRCH_FOREST = register("patch_flower_birch_forest");
	public static final FrozenLibFeature PATCH_FLOWER_SEMI_BIRCH_FOREST = register("patch_flower_semi_birch_forest");
	public static final FrozenLibFeature PATCH_FLOWER_BIRCH_JUNGLE = register("patch_flower_birch_jungle");
	public static final FrozenLibFeature PATCH_FLOWER_BIRCH_TAIGA = register("patch_flower_birch_taiga");

	// DARK FOREST
	public static final FrozenLibFeature PATCH_PHLOX_AND_LANTANAS_DARK_FOREST = register("patch_phlox_and_lantanas_dark_forest");
	public static final FrozenLibFeature FLOWER_DARK_FOREST = register("flower_dark_forest");
	public static final FrozenLibFeature PATCH_FLOWER_DARK_FOREST = register("patch_flower_dark_forest");
	public static final FrozenLibFeature PATCH_FLOWER_DARK_BIRCH_FOREST = register("patch_flower_dark_birch_forest");
	public static final FrozenLibFeature FLOWER_DARK_TAIGA = register("flower_dark_taiga");
	public static final FrozenLibFeature PATCH_FLOWER_DARK_TAIGA = register("patch_flower_dark_taiga");

	// JUNGLE
	public static final FrozenLibFeature FLOWER_JUNGLE = register("flower_jungle");
	public static final FrozenLibFeature PATCH_FLOWER_JUNGLE = register("patch_flower_jungle");

	// SWAMP
	public static final FrozenLibFeature PATCH_FLOWER_MANGROVE_SWAMP = register("patch_flower_mangrove_swamp");
	public static final FrozenLibFeature FLOWER_CYPRESS_WETLANDS = register("flower_cypress_wetlands");

	// CHERRY GROVE
	public static final FrozenLibFeature FLOWER_CHERRY_GROVE_1 = register("flower_cherry_grove_1");
	public static final FrozenLibFeature FLOWER_CHERRY_GROVE_2 = register("flower_cherry_grove_2");
	public static final FrozenLibFeature PATCH_FLOWER_CHERRY_GROVE = register("patch_flower_cherry_grove");

	// AUTUMNAL
	public static final FrozenLibFeature FLOWER_MAPLE_FOREST = register("flower_maple_forest");
	public static final FrozenLibFeature PATCH_FLOWER_MAPLE_FOREST = register("patch_flower_maple_forest");
	public static final FrozenLibFeature PATCH_FLOWER_TUNDRA = register("patch_flower_tundra");
	public static final FrozenLibFeature PATCH_FLOWER_DAPPLED_FOREST = register("patch_flower_dappled_forest");

	// MEADOW
	public static final FrozenLibFeature PATCH_WILDFLOWERS_AND_PHLOX_MEADOW = register("patch_wildflowers_and_phlox_meadow");
	public static final FrozenLibFeature FLOWER_MEADOW = register("flower_meadow");

	// TAIGA
	public static final FrozenLibFeature FLOWER_TEMPERATE_RAINFOREST = register("flower_temperate_rainforest");
	public static final FrozenLibFeature PATCH_FLOWER_TEMPERATE_RAINFOREST = register("patch_flower_temperate_rainforest");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);

		final Holder<ConfigPredicate> generateFlower = configPredicates.getOrThrow(WWConfigPredicates.GENERATE_FLOWER);

		registerSimpleBlock(ALLIUM, Blocks.ALLIUM);
		registerSimpleBlock(AZURE_BLUET, Blocks.AZURE_BLUET);
		registerSimpleBlock(BLUE_ORCHID, Blocks.BLUE_ORCHID);
		registerSimpleBlock(CARNATION, WWBlocks.CARNATION.get());
		registerSimpleBlock(DANDELION, Blocks.DANDELION);
		EYEBLOSSOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.CLOSED_EYEBLOSSOM), true));
		registerSimpleBlock(LILY_OF_THE_VALLEY, Blocks.LILY_OF_THE_VALLEY);
		registerSimpleBlock(MARIGOLD, WWBlocks.MARIGOLD.get());
		registerSimpleBlock(POPPY, Blocks.POPPY);
		registerSimpleBlock(SEEDING_DANDELION, WWBlocks.SEEDING_DANDELION.get());

		registerSimpleBlock(HIBISCUS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_HIBISCUS));
		registerSimpleBlock(POPPY_AND_PASQUEFLOWER, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_POPPY_AND_PASQUEFLOWER));
		registerSimpleBlock(MARIGOLD_AND_PASQUEFLOWER, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_MARIGOLD_AND_PASQUEFLOWER));
		registerSimpleBlock(MARIGOLD_AND_SEEDING_DANDELION, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_MARIGOLD_AND_SEEDING_DANDELION));
		registerSimpleBlock(PASQUEFLOWER_AND_POPPY, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_PASQUEFLOWER_AND_POPPY));
		registerSimpleBlock(AZURE_BLUET_AND_SEEDING_DANDELION, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_AZURE_BLUET_AND_SEEDING_DANDELION));
		registerSimpleBlock(POPPY_AND_MARIGOLD, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_POPPY_AND_MARIGOLD));

		registerSimpleBlock(DATURA, WWBlocks.DATURA.get());
		registerSimpleBlock(LILAC, Blocks.LILAC);
		registerSimpleBlock(MILKWEED, WWBlocks.MILKWEED.get());
		registerSimpleBlock(PEONY, Blocks.PEONY);
		registerSimpleBlock(ROSE_BUSH, Blocks.ROSE_BUSH);

		registerSimpleBlock(LANTANAS, new FlowerBedStateProvider(WWBlocks.LANTANAS.get()));
		registerSimpleBlock(PHLOX, new FlowerBedStateProvider(WWBlocks.PHLOX.get()));
		registerSimpleBlock(WILDFLOWERS, new FlowerBedStateProvider(Blocks.WILDFLOWERS));

		FLOWER_DEFAULT_WITH_SEEDING_DANDELION.makeAndSetHolder(
			new ConfigSelectorFeature(
				generateFlower,
				PlacementUtils.inlinePlaced(
					new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DEFAULT_WITH_SEEDING_DANDELION))
				),
				PlacementUtils.inlinePlaced(features.getOrThrow(VegetationFeatures.FLOWER_DEFAULT))
			)
		);

		// PLAINS
		registerSimpleBlock(FLOWER_DEFAULT_PLAINS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DEFAULT_PLAINS));
		registerSimpleBlock(FLOWER_PLAINS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_PLAINS));
		registerSimpleBlock(FLOWER_DEFAULT_SNOWY_PLAINS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DEFAULT_SNOWY_PLAINS));

		registerSimpleBlock(SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS, blockStateProviders.getOrThrow(WWBlockStateProviders.SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS));
		registerSimpleBlock(FLOWER_SUNFLOWER_PLAINS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_SUNFLOWER_PLAINS));
		registerSimpleBlock(FLOWER_SUNFLOWER_PLAINS_PINK, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_SUNFLOWER_PLAINS_PINK));
		registerWeightedSelector(PATCH_FLOWER_SUNFLOWER_PLAINS,
			Map.of(
				flowerPatchInlinePlaced(
					SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS.getHolder(),
					32, 6, 2
				), 12,
				flowerPatchInlinePlaced(
					FLOWER_SUNFLOWER_PLAINS.getHolder(),
					42, 6, 2
				), 6,
				flowerPatchInlinePlaced(
					FLOWER_SUNFLOWER_PLAINS_PINK.getHolder(),
					42, 6, 2
				), 2
			)
		);

		registerSimpleBlock(FLOWER_FLOWER_FIELD_RED, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_RED));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_RED_TALL, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_RED_TALL));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_PINK, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_PINK_TALL_1, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK_TALL_1));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_PINK_TALL_2, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK_TALL_2));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_ORANGE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_ORANGE));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_ORANGE_TALL, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_ORANGE_TALL));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_YELLOW, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_YELLOW));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_WHITE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_WHITE));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_BLUE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_BLUE));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_PURPLE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PURPLE));
		registerSimpleBlock(FLOWER_FLOWER_FIELD_PURPLE_TALL, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PURPLE_TALL));

		final int flowerFieldColors = 6;
		final float flowerFieldTotalWidth = 1.5F;
		final float flowerFieldColorWidth = flowerFieldTotalWidth / flowerFieldColors;
		FLOWER_FLOWER_FIELD.makeAndSetHolder(
			new OverlayFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								FLOWER_FLOWER_FIELD_RED.asInlinePlaced(),
								FLOWER_FLOWER_FIELD_RED_TALL.asInlinePlaced()
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F, 1, 0)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								FLOWER_FLOWER_FIELD_PINK.asInlinePlaced(),
								FLOWER_FLOWER_FIELD_PINK_TALL_1.asInlinePlaced(),
								FLOWER_FLOWER_FIELD_PINK_TALL_2.asInlinePlaced()
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F + flowerFieldColorWidth, 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F, 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								FLOWER_FLOWER_FIELD_ORANGE.asInlinePlaced(),
								FLOWER_FLOWER_FIELD_ORANGE_TALL.asInlinePlaced()
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 2), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + flowerFieldColorWidth, 0, 1)
					),
					FLOWER_FLOWER_FIELD_YELLOW.asInlinePlaced(
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 3), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 2), 0, 1)
					),
					FLOWER_FLOWER_FIELD_WHITE.asInlinePlaced(
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 4), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 3), 0, 1)
					),
					FLOWER_FLOWER_FIELD_BLUE.asInlinePlaced(
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 5), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 4), 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								FLOWER_FLOWER_FIELD_PURPLE.asInlinePlaced(),
								FLOWER_FLOWER_FIELD_PURPLE_TALL.asInlinePlaced()
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 6), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 5), 0, 1)
					)
				)
			)
		);
		registerWeightedSelector(PATCH_FLOWER_FLOWER_FIELD,
			Map.of(
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_48.getHolder(), 4,
				WWFlowerPlacements.PATCH_MILKWEED_52_LARGE.getHolder(), 1
			)
		);

		// FOREST
		registerSimpleBlock(FLOWER_FOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FOREST));
		registerWeightedSelector(PATCH_FLOWER_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_42.getHolder(), 2,
				WWFlowerPlacements.PATCH_CARNATION_42.getHolder(), 2,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_32.getHolder(), 1,
				WWFlowerPlacements.PATCH_MILKWEED_52_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_64_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_ROSE_BUSH_64_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_PEONY_52_LARGE.getHolder(), 3
			)
		);
		registerRandomSelector(PATCH_FLOWER_SPARSE_FOREST,
			WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_38.getHolder(),
			WWFlowerPlacements.PATCH_CARNATION_38.getHolder(),
			WWFlowerPlacements.PATCH_SEEDING_DANDELION_32.getHolder(),
			WWFlowerPlacements.PATCH_MILKWEED_48_LARGE.getHolder(),
			WWFlowerPlacements.PATCH_LILAC_52_LARGE.getHolder(),
			WWFlowerPlacements.PATCH_ROSE_BUSH_52_LARGE.getHolder(),
			WWFlowerPlacements.PATCH_PEONY_52_LARGE.getHolder()
		);
		registerSimpleBlock(FLOWER_RAINFOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_RAINFOREST));
		PATCH_FLOWER_RAINFOREST.makeAndSetHolder(
			new ConfigSelectorFeature(
				generateFlower,
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_BLUE_ORCHID_36.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_CARNATION_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 2)
							.add(WWFlowerPlacements.PATCH_ROSE_BUSH_64.getHolder(), 2)
							.build()
					)
				),
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_BLUE_ORCHID_36.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_ROSE_BUSH_64.getHolder(), 2)
							.build()
					)
				)
			)
		);
		registerSimpleBlock(FLOWER_FLOWER_FOREST_1, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FOREST_1));
		registerSimpleBlock(FLOWER_FLOWER_FOREST_2, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FOREST_2));
		registerWeightedSelector(PATCH_FLOWER_FLOWER_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_48.getHolder(), 2,
				WWFlowerPlacements.PATCH_MILKWEED_52_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_LILAC_64_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_ROSE_BUSH_64_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_PEONY_52_LARGE.getHolder(), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DYING_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_MARIGOLD_46.getHolder(), 5,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_38.getHolder(), 3
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DYING_MIXED_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_MARIGOLD_32.getHolder(), 1,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_48.getHolder(), 2
			)
		);

		// BIRCH FOREST
		registerWeightedSelector(PATCH_FLOWER_BIRCH_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_DANDELION_72_LARGE.getHolder(), 6,
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_48.getHolder(), 3,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_72_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_DATURA_38.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_48_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_PEONY_42_LARGE.getHolder(), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_SEMI_BIRCH_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_DANDELION_72_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_48.getHolder(), 2,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_72_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_48_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_PEONY_42_LARGE.getHolder(), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_BIRCH_JUNGLE,
			Map.of(
				WWFlowerPlacements.PATCH_HIBISCUS_72.getHolder(), 3,
				WWFlowerPlacements.PATCH_FLOWER_DEFAULT_64_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_48.getHolder(), 2,
				WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 3,
				WWFlowerPlacements.PATCH_DATURA_38.getHolder(), 2,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_BIRCH_TAIGA,
			Map.of(
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_72_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_DATURA_38.getHolder(), 3,
				WWFlowerPlacements.PATCH_LILAC_48_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_PEONY_42_LARGE.getHolder(), 1
			)
		);

		// DARK FOREST
		registerWeightedSelector(PATCH_PHLOX_AND_LANTANAS_DARK_FOREST,
			Map.of(
				flowerPatchInlinePlaced(PHLOX.getHolder(), 42, 6, 2), 4,
				flowerPatchInlinePlaced(LANTANAS.getHolder(), 42, 6, 2), 3
			)
		);
		registerSimpleBlock(FLOWER_DARK_FOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DARK_FOREST));
		registerWeightedSelector(PATCH_FLOWER_DARK_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_CARNATION_52.getHolder(), 5,
				WWFlowerPlacements.PATCH_MARIGOLD_52.getHolder(), 5,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_52.getHolder(), 4,
				WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 4,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_PEONY_48_LARGE.getHolder(), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DARK_BIRCH_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_CARNATION_42.getHolder(), 3,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_42.getHolder(), 3,
				WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 3,
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_48.getHolder(), 3,
				WWFlowerPlacements.PATCH_DATURA_38.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_48_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_PEONY_48_LARGE.getHolder(), 2
			)
		);
		registerSimpleBlock(FLOWER_DARK_TAIGA, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DARK_TAIGA));
		registerWeightedSelector(PATCH_FLOWER_DARK_TAIGA,
			Map.of(
				WWFlowerPlacements.PATCH_MARIGOLD_52.getHolder(), 3,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_52.getHolder(), 3,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_PEONY_48_LARGE.getHolder(), 1
			)
		);

		// JUNGLE
		registerSimpleBlock(FLOWER_JUNGLE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_JUNGLE));
		registerWeightedSelector(PATCH_FLOWER_JUNGLE,
			Map.of(
				WWFlowerPlacements.PATCH_HIBISCUS_72.getHolder(), 6,
				WWFlowerPlacements.PATCH_FLOWER_DEFAULT_64_LARGE.getHolder(), 1,
				flowerPatchInlinePlaced(FLOWER_JUNGLE.getHolder(), 64, 7, 3), 3,
				WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_48.getHolder(), 2,
				WWFlowerPlacements.PATCH_BLUE_ORCHID_48.getHolder(), 1,
				WWFlowerPlacements.PATCH_MILKWEED_64.getHolder(), 4,
				WWFlowerPlacements.PATCH_ROSE_BUSH_48_LARGE.getHolder(), 2
			)
		);

		// SWAMP
		registerRandomSelector(PATCH_FLOWER_MANGROVE_SWAMP,
			WWFlowerPlacements.PATCH_HIBISCUS_32.getHolder(),
			WWFlowerPlacements.PATCH_CARNATION_38.getHolder()
		);
		registerSimpleBlock(FLOWER_CYPRESS_WETLANDS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CYPRESS_WETLANDS));

		// CHERRY GROVE
		registerSimpleBlock(FLOWER_CHERRY_GROVE_1, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CHERRY_GROVE_1));
		registerSimpleBlock(FLOWER_CHERRY_GROVE_2, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CHERRY_GROVE_2));
		registerWeightedSelector(PATCH_FLOWER_CHERRY_GROVE,
			Map.of(
				flowerPatchInlinePlaced(FLOWER_CHERRY_GROVE_1.getHolder(), 42, 6, 2), 4,
				flowerPatchInlinePlaced(FLOWER_CHERRY_GROVE_2.getHolder(), 42, 6, 2), 4,
				WWFlowerPlacements.PATCH_DATURA_38.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_38.getHolder(), 2,
				WWFlowerPlacements.PATCH_ROSE_BUSH_38.getHolder(), 3,
				WWFlowerPlacements.PATCH_PEONY_38.getHolder(), 2
			)
		);

		// AUTUMNAL
		registerSimpleBlock(FLOWER_MAPLE_FOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_MAPLE_FOREST));
		registerWeightedSelector(PATCH_FLOWER_MAPLE_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_ALLIUM_42.getHolder(), 4,
				WWFlowerPlacements.PATCH_MARIGOLD_42.getHolder(), 1,
				WWFlowerPlacements.PATCH_POPPY_48_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_42.getHolder(), 3,
				WWFlowerPlacements.PATCH_DATURA_32.getHolder(), 2,
				WWFlowerPlacements.PATCH_LILAC_38_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_ROSE_BUSH_42_LARGE.getHolder(), 3
			)
		);
		registerWeightedSelector(PATCH_FLOWER_TUNDRA,
			Map.of(
				WWFlowerPlacements.PATCH_POPPY_AND_PASQUEFLOWER_36.getHolder(), 2,
				WWFlowerPlacements.PATCH_MARIGOLD_AND_PASQUEFLOWER_36.getHolder(), 3,
				WWFlowerPlacements.PATCH_MARIGOLD_AND_SEEDING_DANDELION_36.getHolder(), 2,
				WWFlowerPlacements.PATCH_PASQUEFLOWER_AND_POPPY_36.getHolder(), 3,
				WWFlowerPlacements.PATCH_AZURE_BLUET_AND_SEEDING_DANDELION_64_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_AZURE_BLUET_52_LARGE.getHolder(), 4,
				WWFlowerPlacements.PATCH_POPPY_38_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_MARIGOLD_38_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_SEEDING_DANDELION_42.getHolder(), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DAPPLED_FOREST,
			Map.of(
				WWFlowerPlacements.PATCH_POPPY_AND_PASQUEFLOWER_36.getHolder(), 3,
				WWFlowerPlacements.PATCH_AZURE_BLUET_AND_SEEDING_DANDELION_64_LARGE.getHolder(), 2,
				WWFlowerPlacements.PATCH_POPPY_AND_MARIGOLD_64_LARGE.getHolder(), 3,
				WWFlowerPlacements.PATCH_AZURE_BLUET_52_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_ALLIUM_42.getHolder(), 1,
				WWFlowerPlacements.PATCH_POPPY_32_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_MARIGOLD_32_LARGE.getHolder(), 1,
				WWFlowerPlacements.PATCH_ROSE_BUSH_42.getHolder(), 3
			)
		);

		// MEADOW
		registerWeightedSelector(PATCH_WILDFLOWERS_AND_PHLOX_MEADOW,
			Map.of(
				flowerPatchInlinePlaced(WILDFLOWERS.getHolder(), 52, 6, 2), 2,
				flowerPatchInlinePlaced(PHLOX.getHolder(), 52, 6, 2), 1
			)
		);
		registerSimpleBlock(FLOWER_MEADOW, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_MEADOW));

		// TAIGA
		registerSimpleBlock(FLOWER_TEMPERATE_RAINFOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_TEMPERATE_RAINFOREST));
		PATCH_FLOWER_TEMPERATE_RAINFOREST.makeAndSetHolder(
			new ConfigSelectorFeature(
				generateFlower,
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_DATURA_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_MILKWEED_42.getHolder(), 1)
							.add(WWFlowerPlacements.PATCH_ROSE_BUSH_64.getHolder(), 1)
							.build()
					)
				),
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(WWFlowerPlacements.PATCH_LILY_OF_THE_VALLEY_42.getHolder(), 2)
							.add(WWFlowerPlacements.PATCH_ROSE_BUSH_64.getHolder(), 1)
							.build()
					)
				)
			)
		);
	}

	private static void registerSimpleBlock(FrozenLibFeature feature, Holder<BlockStateProvider> blockStateProvider) {
		feature.makeAndSetHolder(new SimpleBlockFeature(blockStateProvider));
	}

	private static void registerSimpleBlock(FrozenLibFeature feature, BlockStateProvider blockStateProvider) {
		registerSimpleBlock(feature, Holder.direct(blockStateProvider));
	}

	private static void registerSimpleBlock(FrozenLibFeature feature, Block block) {
		registerSimpleBlock(feature, BlockStateProvider.of(block));
	}

	private static void registerRandomSelector(FrozenLibFeature feature, Holder<PlacedFeature>... placements) {
		feature.makeAndSetHolder(new SimpleRandomSelectorFeature(HolderSet.direct(placements)));
	}

	private static void registerWeightedSelector(FrozenLibFeature feature, Map<Holder<PlacedFeature>, Integer> placements) {
		feature.makeAndSetHolder(
			new WeightedRandomSelectorFeature(
				WeightedList.of(
					placements.entrySet().stream()
						.map((entry) -> new Weighted<>(entry.getKey(), entry.getValue()))
						.sorted(Comparator.comparing(Weighted::weight))
						.toList()
				)
			)
		);
	}

	private static Holder<PlacedFeature> flowerPatchInlinePlaced(Holder<Feature> feature, int count, int xzRange, int yRange) {
		return PlacementUtils.inlinePlaced(
			feature,
			CountPlacement.of(count),
			OffsetPlacement.ofTriangle(xzRange, yRange),
			BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("flower/" + id));
	}

	private WWFlowerFeatures() {}
}
