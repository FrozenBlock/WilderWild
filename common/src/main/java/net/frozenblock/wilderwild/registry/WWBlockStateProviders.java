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

package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.BaobabNutBlock;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RandomizedIntStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import java.util.List;

public final class WWBlockStateProviders {
	public static final ResourceKey<BlockStateProvider> FLOWER_HIBISCUS = bind("flower/hibiscus");

	public static final ResourceKey<BlockStateProvider> FLOWER_DEFAULT_WITH_SEEDING_DANDELION = bind("flower/default_with_seeding_dandelion");
	public static final ResourceKey<BlockStateProvider> FLOWER_DEFAULT_PLAINS = bind("flower/default_plains");
	public static final ResourceKey<BlockStateProvider> FLOWER_PLAINS = bind("flower/plains");
	public static final ResourceKey<BlockStateProvider> FLOWER_DEFAULT_SNOWY_PLAINS = bind("flower/default_snowy_plains");
	public static final ResourceKey<BlockStateProvider> SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS = bind("flower/seeding_dandelion_and_dandelion_sunflower_plains");
	public static final ResourceKey<BlockStateProvider> FLOWER_SUNFLOWER_PLAINS = bind("flower/sunflower_plains");
	public static final ResourceKey<BlockStateProvider> FLOWER_SUNFLOWER_PLAINS_PINK = bind("flower/sunflower_plains_pink");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_RED = bind("flower/flower_field_red");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_RED_TALL = bind("flower/flower_field_red_tall");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_PINK = bind("flower/flower_field_pink");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_PINK_TALL_1 = bind("flower/flower_field_pink_tall_1");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_PINK_TALL_2 = bind("flower/flower_field_pink_tall_2");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_ORANGE = bind("flower/flower_field_orange");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_ORANGE_TALL = bind("flower/flower_field_orange_tall");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_YELLOW = bind("flower/flower_field_yellow");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_WHITE = bind("flower/flower_field_white");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_BLUE = bind("flower/flower_field_blue");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_PURPLE = bind("flower/flower_field_purple");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FIELD_PURPLE_TALL = bind("flower/flower_field_purple_tall");

	public static final ResourceKey<BlockStateProvider> FLOWER_FOREST = bind("flower/forest");
	public static final ResourceKey<BlockStateProvider> FLOWER_RAINFOREST = bind("flower/rainforest");
	public static final ResourceKey<BlockStateProvider> FLOWER_TEMPERATE_RAINFOREST = bind("flower/temperate_rainforest");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FOREST_1 = bind("flower/flower_forest_1");
	public static final ResourceKey<BlockStateProvider> FLOWER_FLOWER_FOREST_2 = bind("flower/flower_forest_2");

	public static final ResourceKey<BlockStateProvider> FLOWER_DARK_FOREST = bind("flower/dark_forest");
	public static final ResourceKey<BlockStateProvider> FLOWER_DARK_TAIGA = bind("flower/dark_taiga");

	public static final ResourceKey<BlockStateProvider> FLOWER_JUNGLE = bind("flower/jungle");

	public static final ResourceKey<BlockStateProvider> FLOWER_CYPRESS_WETLANDS = bind("flower/cypress_wetlands");

	public static final ResourceKey<BlockStateProvider> FLOWER_CHERRY_GROVE_1 = bind("flower/cherry_grove_1");
	public static final ResourceKey<BlockStateProvider> FLOWER_CHERRY_GROVE_2 = bind("flower/cherry_grove_2");

	public static final ResourceKey<BlockStateProvider> FLOWER_MAPLE_FOREST = bind("flower/maple_forest");
	public static final ResourceKey<BlockStateProvider> FLOWER_POPPY_AND_PASQUEFLOWER = bind("flower/poppy_and_pasqueflower");
	public static final ResourceKey<BlockStateProvider> FLOWER_MARIGOLD_AND_PASQUEFLOWER = bind("flower/marigold_and_pasqueflower");
	public static final ResourceKey<BlockStateProvider> FLOWER_MARIGOLD_AND_SEEDING_DANDELION = bind("flower/marigold_and_seeding_dandelion");
	public static final ResourceKey<BlockStateProvider> FLOWER_PASQUEFLOWER_AND_POPPY = bind("flower/pasqueflower_and_poppy");
	public static final ResourceKey<BlockStateProvider> FLOWER_AZURE_BLUET_AND_SEEDING_DANDELION = bind("flower/azure_bluet_and_seeding_dandelion");
	public static final ResourceKey<BlockStateProvider> FLOWER_POPPY_AND_MARIGOLD = bind("flower/poppy_and_marigold");

	public static final ResourceKey<BlockStateProvider> FLOWER_MEADOW = bind("flower/flower_meadow");

	public static final ResourceKey<BlockStateProvider> BAOBAB_NUT = bind("baobab_nut");
	public static final ResourceKey<BlockStateProvider> COCONUT = bind("coconut");
	public static final ResourceKey<BlockStateProvider> SOIL_BENEATH_WATERLOGGABLE_TREE = bind("soil_beneath_waterloggable_tree");
	public static final ResourceKey<BlockStateProvider> SOIL_BENEATH_DESERT_TREE = bind("soil_beneath_desert_tree");

	public static void bootstrap(BootstrapContext<BlockStateProvider> context) {
		context.register(
			FLOWER_HIBISCUS,
			new NoiseProvider(
				1234L,
				NormalNoise.createParity(0, 1D),
				0.088833334F,
				List.of(
					WWBlocks.RED_HIBISCUS.get().defaultBlockState(),
					WWBlocks.RED_HIBISCUS.get().defaultBlockState(),
					WWBlocks.YELLOW_HIBISCUS.get().defaultBlockState(),
					WWBlocks.WHITE_HIBISCUS.get().defaultBlockState(),
					WWBlocks.PINK_HIBISCUS.get().defaultBlockState(),
					WWBlocks.PURPLE_HIBISCUS.get().defaultBlockState(),
					WWBlocks.PURPLE_HIBISCUS.get().defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_DEFAULT_WITH_SEEDING_DANDELION,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.POPPY.defaultBlockState(), 2)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_DEFAULT_PLAINS,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.DANDELION.defaultBlockState(), 5)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
					.add(Blocks.POPPY.defaultBlockState(), 2)
			)
		);

		context.register(
			FLOWER_PLAINS,
			new NoiseThresholdProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.005F,
				-0.6F,
				0.33333334F,
				Blocks.DANDELION.defaultBlockState(),
				List.of(
					Blocks.ORANGE_TULIP.defaultBlockState(),
					Blocks.ORANGE_TULIP.defaultBlockState(),
					Blocks.RED_TULIP.defaultBlockState(),
					Blocks.RED_TULIP.defaultBlockState(),
					Blocks.PINK_TULIP.defaultBlockState(),
					Blocks.PINK_TULIP.defaultBlockState(),
					Blocks.WHITE_TULIP.defaultBlockState(),
					Blocks.WHITE_TULIP.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState()
				),
				List.of(
					Blocks.POPPY.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_DEFAULT_SNOWY_PLAINS,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 4)
					.add(Blocks.POPPY.defaultBlockState(), 3)
			)
		);

		context.register(
			SEEDING_DANDELION_AND_DANDELION_SUNFLOWER_PLAINS,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.DANDELION.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 5)
			)
		);

		context.register(
			FLOWER_SUNFLOWER_PLAINS,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.DANDELION.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
					.add(Blocks.AZURE_BLUET.defaultBlockState(), 2)
					.add(Blocks.OXEYE_DAISY.defaultBlockState(), 2)
			)
		);

		context.register(
			FLOWER_SUNFLOWER_PLAINS_PINK,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.LILAC.defaultBlockState(), 3)
					.add(Blocks.ALLIUM.defaultBlockState(), 6)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_RED,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.RED_TULIP.defaultBlockState(), 3)
					.add(Blocks.POPPY.defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_RED_TALL,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.ROSE_BUSH.defaultBlockState(), 3)
					.add(Blocks.RED_TULIP.defaultBlockState(), 2)
					.add(Blocks.POPPY.defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_PINK,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.PINK_TULIP.defaultBlockState(), 3)
					.add(Blocks.ALLIUM.defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_PINK_TALL_1,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.LILAC.defaultBlockState(), 3)
					.add(Blocks.PINK_TULIP.defaultBlockState(), 2)
					.add(Blocks.ALLIUM.defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_PINK_TALL_2,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.PEONY.defaultBlockState(), 3)
					.add(Blocks.PINK_TULIP.defaultBlockState(), 2)
					.add(Blocks.ALLIUM.defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_ORANGE,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.ORANGE_TULIP.defaultBlockState(), 3)
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_ORANGE_TALL,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.MILKWEED.get().defaultBlockState(), 3)
					.add(Blocks.ORANGE_TULIP.defaultBlockState(), 2)
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_YELLOW,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.DANDELION.defaultBlockState(), 5)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_WHITE,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 1)
					.add(Blocks.WHITE_TULIP.defaultBlockState(), 2)
					.add(Blocks.AZURE_BLUET.defaultBlockState(), 6)
					.add(Blocks.OXEYE_DAISY.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 2)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_BLUE,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.CORNFLOWER.defaultBlockState(), 6)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_PURPLE,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.CARNATION.get().defaultBlockState(), 6)
					.add(Blocks.ALLIUM.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FLOWER_FIELD_PURPLE_TALL,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.LILAC.defaultBlockState(), 3)
					.add(WWBlocks.CARNATION.get().defaultBlockState(), 6)
					.add(Blocks.ALLIUM.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_FOREST,
			new NoiseProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.038333334F,
				List.of(
					Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
					Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_RAINFOREST,
			RuleBasedStateProvider.builder(
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.043333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			).ifTrueThenProvide(
				WWWorldgenConfig.FLOWER_GENERATION.equalTo(true).asBlockPredicate(),
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.043333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			).build()
		);

		context.register(
			FLOWER_TEMPERATE_RAINFOREST,
			RuleBasedStateProvider.builder(
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.028333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState()
					)
				)
			).ifTrueThenProvide(
				WWWorldgenConfig.FLOWER_GENERATION.equalTo(true).asBlockPredicate(),
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.028333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.ALLIUM.defaultBlockState()
					)
				)
			).build()
		);

		context.register(
			FLOWER_FLOWER_FOREST_1,
			new NoiseProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.020833334F,
				List.of(
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.ALLIUM.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.RED_TULIP.defaultBlockState(),
					Blocks.ORANGE_TULIP.defaultBlockState(),
					Blocks.WHITE_TULIP.defaultBlockState(),
					Blocks.PINK_TULIP.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_FLOWER_FOREST_2,
			new NoiseProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.036833334F,
				List.of(
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.ALLIUM.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.RED_TULIP.defaultBlockState(),
					Blocks.ORANGE_TULIP.defaultBlockState(),
					Blocks.WHITE_TULIP.defaultBlockState(),
					Blocks.PINK_TULIP.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_DARK_FOREST,
			new NoiseProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.038333334F,
				List.of(
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_DARK_TAIGA,
			new NoiseProvider(
				2345L,
				NormalNoise.createParity(0, 1D),
				0.038333334F,
				List.of(
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					WWBlocks.MARIGOLD.get().defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_JUNGLE,
			new NoiseProvider(
				1234L,
				NormalNoise.createParity(0, 1D),
				0.054833334F,
				List.of(
					Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					WWBlocks.CARNATION.get().defaultBlockState(),
					Blocks.BLUE_ORCHID.defaultBlockState()
				)
			)
		);

		context.register(
			FLOWER_CYPRESS_WETLANDS,
			RuleBasedStateProvider.builder(
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.043333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			).ifTrueThenProvide(
				WWWorldgenConfig.FLOWER_GENERATION.equalTo(true).asBlockPredicate(),
				new NoiseProvider(
					2345L,
					NormalNoise.createParity(0, 1D),
					0.043333334F,
					List.of(
						Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
						Blocks.DANDELION.defaultBlockState(),
						Blocks.POPPY.defaultBlockState(),
						WWBlocks.CARNATION.get().defaultBlockState(),
						Blocks.BLUE_ORCHID.defaultBlockState()
					)
				)
			).build()
		);

		context.register(
			FLOWER_CHERRY_GROVE_1,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.POPPY.defaultBlockState(), 3)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_CHERRY_GROVE_2,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 3)
					.add(Blocks.AZURE_BLUET.defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_MAPLE_FOREST,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 6)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 2)
			)
		);

		context.register(
			FLOWER_POPPY_AND_PASQUEFLOWER,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.POPPY.defaultBlockState(), 8)
					.add(WWBlocks.PASQUEFLOWER.get().defaultBlockState(), 3)
			)
		);

		context.register(
			FLOWER_MARIGOLD_AND_PASQUEFLOWER,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 8)
					.add(WWBlocks.PASQUEFLOWER.get().defaultBlockState(), 3)
			)
		);

		context.register(
			FLOWER_MARIGOLD_AND_SEEDING_DANDELION,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 8)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 3)
			)
		);

		context.register(
			FLOWER_PASQUEFLOWER_AND_POPPY,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(WWBlocks.PASQUEFLOWER.get().defaultBlockState(), 8)
					.add(Blocks.POPPY.defaultBlockState(), 3)
			)
		);

		context.register(
			FLOWER_AZURE_BLUET_AND_SEEDING_DANDELION,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.AZURE_BLUET.defaultBlockState(), 2)
					.add(WWBlocks.SEEDING_DANDELION.get().defaultBlockState(), 1)
			)
		);

		context.register(
			FLOWER_POPPY_AND_MARIGOLD,
			new WeightedStateProvider(
				WeightedList.<BlockState>builder()
					.add(Blocks.POPPY.defaultBlockState(), 7)
					.add(WWBlocks.MARIGOLD.get().defaultBlockState(), 3)
			)
		);

		context.register(
			FLOWER_MEADOW,
			new DualNoiseProvider(
				new InclusiveRange<>(1, 3),
				NormalNoise.createParity(-9, 1D),
				1F,
				2345L,
				NormalNoise.createParity(-3, 1D),
				1F,
				List.of(
					WWBlocks.MARIGOLD.get().defaultBlockState(),
					Blocks.ALLIUM.defaultBlockState(),
					Blocks.POPPY.defaultBlockState(),
					Blocks.AZURE_BLUET.defaultBlockState(),
					Blocks.DANDELION.defaultBlockState(),
					WWBlocks.PASQUEFLOWER.get().defaultBlockState(),
					Blocks.CORNFLOWER.defaultBlockState(),
					Blocks.OXEYE_DAISY.defaultBlockState(),
					WWBlocks.SEEDING_DANDELION.get().defaultBlockState()
				)
			)
		);

		context.register(
			BAOBAB_NUT,
			new RandomizedIntStateProvider(
				BlockStateProvider.of(WWBlocks.BAOBAB_NUT.get().defaultBlockState().setValue(BlockStateProperties.HANGING, true)),
				BaobabNutBlock.AGE,
				UniformInt.of(0, 2)
			)
		);

		context.register(
			COCONUT,
			BlockStateProvider.of(WWBlocks.COCONUT.get().defaultBlockState().setValue(BlockStateProperties.HANGING, true))
		);

		context.register(
			SOIL_BENEATH_WATERLOGGABLE_TREE,
			RuleBasedStateProvider.ifTrueThenProvide(
				BlockPredicate.not(BlockPredicate.matchesTag(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_WATERLOGGABLE)),
				Blocks.DIRT
			)
		);
		context.register(
			SOIL_BENEATH_DESERT_TREE,
			RuleBasedStateProvider.ifTrueThenProvide(
				BlockPredicate.not(BlockPredicate.matchesTag(WWBlockTags.CANNOT_REPLACE_BELOW_TREE_TRUNK_DESERT)),
				Blocks.DIRT
			)
		);
	}

	private static ResourceKey<BlockStateProvider> bind(String name) {
		return ResourceKey.create(Registries.BLOCK_STATE_PROVIDER, WWConstants.id(name));
	}

	private WWBlockStateProviders() {}
}
