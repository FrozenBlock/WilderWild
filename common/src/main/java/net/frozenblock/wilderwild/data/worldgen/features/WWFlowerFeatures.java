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

import java.util.Map;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ConfigSelectorFeature;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
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
	public static final FrozenLibFeature SEEDING_DANDELION = register("seeding_dandelion");
	public static final FrozenLibFeature MILKWEED = register("milkweed");
	public static final FrozenLibFeature LANTANAS = register("lantanas");
	public static final FrozenLibFeature PHLOX = register("phlox");
	public static final FrozenLibFeature WILDFLOWERS = register("wildflowers");
	public static final FrozenLibFeature HIBISCUS = register("hibiscus");
	public static final FrozenLibFeature EYEBLOSSOM = register("eyeblossom");

	public static final FrozenLibFeature FLOWER_DEFAULT_WITH_SEEDING_DANDELION = register("flower_default_with_seeding_dandelion");

	// PLAINS
	public static final FrozenLibFeature FLOWER_DEFAULT_PLAINS = register("flower_default_plains");
	public static final FrozenLibFeature FLOWER_PLAINS = register("flower_plains");
	public static final FrozenLibFeature FLOWER_DEFAULT_SNOWY_PLAINS = register("flower_default_snowy_plains");
	public static final FrozenLibFeature PATCH_FLOWER_SUNFLOWER_PLAINS = register("patch_flower_sunflower_plains");
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

		registerSimpleBlock(SEEDING_DANDELION, WWBlocks.SEEDING_DANDELION.get());
		registerSimpleBlock(MILKWEED, new FlowerBedStateProvider(WWBlocks.MILKWEED.get()));
		registerSimpleBlock(LANTANAS, new FlowerBedStateProvider(WWBlocks.LANTANAS.get()));
		registerSimpleBlock(PHLOX, new FlowerBedStateProvider(WWBlocks.PHLOX.get()));
		registerSimpleBlock(WILDFLOWERS, new FlowerBedStateProvider(Blocks.WILDFLOWERS));
		registerSimpleBlock(HIBISCUS, blockStateProviders.getOrThrow(WWBlockStateProviders.HIBISCUS));
		EYEBLOSSOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.CLOSED_EYEBLOSSOM), true));

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
		registerWeightedSelector(PATCH_FLOWER_SUNFLOWER_PLAINS,
			Map.of(
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS))),
					32, 6, 2
				), 12,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_SUNFLOWER_PLAINS))),
					42, 6, 2
				), 6,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_SUNFLOWER_PLAINS_PINK))),
					42, 6, 2
				), 2
			)
		);

		final int flowerFieldColors = 6;
		final float flowerFieldTotalWidth = 1.5F;
		final float flowerFieldColorWidth = flowerFieldTotalWidth / flowerFieldColors;
		FLOWER_FLOWER_FIELD.makeAndSetHolder(
			new OverlayFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_RED))),
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_RED_TALL)))
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F, 1, 0)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK))),
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK_TALL_1))),
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PINK_TALL_2)))
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F + flowerFieldColorWidth, 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F, 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_ORANGE))),
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_ORANGE_TALL)))
							)
						),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 2), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + flowerFieldColorWidth, 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_YELLOW)),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 3), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 2), 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_WHITE)),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 4), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 3), 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_BLUE)),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 5), 1, 0),
						NoiseThresholdCountPlacement.of(-0.75F + (flowerFieldColorWidth * 4), 0, 1)
					),
					PlacementUtils.inlinePlaced(
						new SimpleRandomSelectorFeature(
							HolderSet.direct(
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PURPLE))),
								PlacementUtils.inlinePlaced(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FIELD_PURPLE_TALL)))
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
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 48, 6, 2), 4,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 52, 7, 3), 1
			)
		);

		// FOREST
		registerSimpleBlock(FLOWER_FOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FOREST));
		registerWeightedSelector(PATCH_FLOWER_FOREST,
			Map.of(
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 42, 6, 2), 2,
				flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 42, 6, 2), 2,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 32, 6, 2), 1,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 52, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 64, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.PEONY, 52, 7, 3), 3
			)
		);
		registerRandomSelector(PATCH_FLOWER_SPARSE_FOREST,
			flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 38, 6, 2),
			flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 38, 6, 2),
			flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 30, 6, 2),
			flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 48, 7, 3),
			flowerPatchInlinePlaced(Blocks.LILAC, 52, 7, 3),
			flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 52, 7, 3),
			flowerPatchInlinePlaced(Blocks.PEONY, 52, 7, 3)
		);
		registerSimpleBlock(FLOWER_RAINFOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_RAINFOREST));
		PATCH_FLOWER_RAINFOREST.makeAndSetHolder(
			new ConfigSelectorFeature(
				generateFlower,
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(Blocks.BLUE_ORCHID, 36, 6, 2), 1)
							.add(flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 42, 6, 2), 2)
							.add(flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 6, 2), 2)
							.build()
					)
				),
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(Blocks.BLUE_ORCHID, 36, 6, 2), 1)
							.add(flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 6, 2), 2)
							.build()
					)
				)
			)
		);
		registerSimpleBlock(FLOWER_FLOWER_FOREST_1, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FOREST_1));
		registerSimpleBlock(FLOWER_FLOWER_FOREST_2, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_FLOWER_FOREST_2));
		registerWeightedSelector(PATCH_FLOWER_FLOWER_FOREST,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 48, 6, 2), 2,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 52, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.LILAC, 64, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.PEONY, 52, 7, 3), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DYING_FOREST,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 46, 6, 2), 5,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 38, 6, 2), 3
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DYING_MIXED_FOREST,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 32, 6, 2), 1,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 48, 6, 2), 2
			)
		);

		// BIRCH FOREST
		registerWeightedSelector(PATCH_FLOWER_BIRCH_FOREST,
			Map.of(
				flowerPatchInlinePlaced(Blocks.DANDELION, 72, 7, 3), 6,
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 48, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 72, 7, 4), 3,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 48, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.PEONY, 42, 7, 3), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_SEMI_BIRCH_FOREST,
			Map.of(
				flowerPatchInlinePlaced(Blocks.DANDELION, 72, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 48, 6, 2), 2,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 72, 7, 4), 3,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 48, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.PEONY, 42, 7, 3), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_BIRCH_JUNGLE,
			Map.of(
				flowerPatchInlinePlaced(HIBISCUS.getHolder(), 72, 6, 2), 3,
				flowerPatchInlinePlaced(features.getOrThrow(VegetationFeatures.FLOWER_DEFAULT), 64, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 48, 6, 2), 2,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 42, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_BIRCH_TAIGA,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 72, 7, 3), 3,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 38, 6, 2), 3,
				flowerPatchInlinePlaced(Blocks.LILAC, 48, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.PEONY, 42, 7, 3), 1
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
				flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 52, 6, 2), 5,
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 52, 6, 2), 5,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 52, 6, 2), 4,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 42, 6, 2), 4,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.PEONY, 48, 7, 3), 2
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DARK_BIRCH_FOREST,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 42, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 42, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 42, 6, 2), 3,
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 48, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 48, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.PEONY, 48, 7, 3), 2
			)
		);
		registerSimpleBlock(FLOWER_DARK_TAIGA, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_DARK_TAIGA));
		registerWeightedSelector(PATCH_FLOWER_DARK_TAIGA,
			Map.of(
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 52, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 52, 6, 2), 3,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.PEONY, 48, 7, 3), 1
			)
		);

		// JUNGLE
		registerSimpleBlock(FLOWER_JUNGLE, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_JUNGLE));
		registerWeightedSelector(PATCH_FLOWER_JUNGLE,
			Map.of(
				flowerPatchInlinePlaced(HIBISCUS.getHolder(), 72, 6, 2), 6,
				flowerPatchInlinePlaced(features.getOrThrow(VegetationFeatures.FLOWER_DEFAULT), 64, 7, 3), 1,
				flowerPatchInlinePlaced(FLOWER_JUNGLE.getHolder(), 64, 7, 3), 3,
				flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 48, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.BLUE_ORCHID, 48, 6, 2), 1,
				flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 64, 6, 2), 4,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 48, 7, 3), 2
			)
		);

		// SWAMP
		registerRandomSelector(PATCH_FLOWER_MANGROVE_SWAMP,
			flowerPatchInlinePlaced(HIBISCUS.getHolder(), 32, 6, 2),
			flowerPatchInlinePlaced(WWBlocks.CARNATION.get(), 38, 6, 2)
		);
		registerSimpleBlock(FLOWER_CYPRESS_WETLANDS, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CYPRESS_WETLANDS));

		// CHERRY GROVE
		registerWeightedSelector(PATCH_FLOWER_CHERRY_GROVE,
			Map.of(
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CHERRY_GROVE_1))),
					42, 6, 2
				), 4,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_CHERRY_GROVE_2))),
					42, 6, 2
				), 4,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 38, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 38, 6, 2), 3,
				flowerPatchInlinePlaced(Blocks.PEONY, 38, 6, 2), 2
			)
		);

		// AUTUMNAL
		registerSimpleBlock(FLOWER_MAPLE_FOREST, blockStateProviders.getOrThrow(WWBlockStateProviders.FLOWER_MAPLE_FOREST));
		registerWeightedSelector(PATCH_FLOWER_MAPLE_FOREST,
			Map.of(
				flowerPatchInlinePlaced(Blocks.ALLIUM, 42, 6, 2), 4,
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 42, 6, 2), 1,
				flowerPatchInlinePlaced(Blocks.POPPY, 48, 7, 3), 3,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 42, 6, 2), 3,
				flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 32, 6, 2), 2,
				flowerPatchInlinePlaced(Blocks.LILAC, 38, 7, 3), 2,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 42, 7, 3), 3
			)
		);
		registerWeightedSelector(PATCH_FLOWER_TUNDRA,
			Map.of(
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.POPPY_AND_PASQUEFLOWER))),
					36, 6, 2
				), 2,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.MARIGOLD_AND_PASQUEFLOWER))),
					36, 6, 2
				), 3,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.MARIGOLD_AND_SEEDING_DANDELION))),
					36, 6, 2
				), 2,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.PASQUEFLOWER_AND_POPPY))),
					36, 6, 2
				), 3,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.AZURE_BLUET_AND_SEEDING_DANDELION))),
					64, 7, 3
				), 2,

				flowerPatchInlinePlaced(Blocks.AZURE_BLUET, 52, 7, 3), 4,
				flowerPatchInlinePlaced(Blocks.POPPY, 38, 7, 3), 1,
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 38, 7, 3), 1,
				flowerPatchInlinePlaced(WWBlocks.SEEDING_DANDELION.get(), 42, 6, 2), 1
			)
		);
		registerWeightedSelector(PATCH_FLOWER_DAPPLED_FOREST,
			Map.of(
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.POPPY_AND_PASQUEFLOWER))),
					36, 6, 2
				), 3,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.AZURE_BLUET_AND_SEEDING_DANDELION))),
					64, 7, 3
				), 2,
				flowerPatchInlinePlaced(
					Holder.direct(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.POPPY_AND_MARIGOLD))),
					64, 7, 3
				), 3,

				flowerPatchInlinePlaced(Blocks.AZURE_BLUET, 52, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.ALLIUM, 42, 6, 2), 1,
				flowerPatchInlinePlaced(Blocks.POPPY, 32, 7, 3), 1,
				flowerPatchInlinePlaced(WWBlocks.MARIGOLD.get(), 32, 7, 3), 1,
				flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 42, 6, 2), 3
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
							.add(flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(WWBlocks.DATURA.get(), 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(WWBlocks.MILKWEED.get(), 42, 6, 2), 1)
							.add(flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 6, 2), 1)
							.build()
					)
				),
				PlacementUtils.inlinePlaced(
					new WeightedRandomSelectorFeature(
						WeightedList.<Holder<PlacedFeature>>builder()
							.add(flowerPatchInlinePlaced(Blocks.LILY_OF_THE_VALLEY, 42, 6, 2), 2)
							.add(flowerPatchInlinePlaced(Blocks.ROSE_BUSH, 64, 6, 2), 1)
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

	private static Holder<PlacedFeature> flowerPatchInlinePlaced(Block block, int count, int xzRange, int yRange) {
		return flowerPatchInlinePlaced(Holder.direct(new SimpleBlockFeature(BlockStateProvider.of(block))), count, xzRange, yRange);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("flower/" + id));
	}

	private WWFlowerFeatures() {}
}
