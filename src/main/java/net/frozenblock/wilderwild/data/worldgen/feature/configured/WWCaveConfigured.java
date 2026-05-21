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

package net.frozenblock.wilderwild.data.worldgen.feature.configured;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.levelgen.feature.api.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.blockpredicates.TouchingBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.feature.config.ColumnFeatureConfiguration;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallFeatureConfiguration;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallOuterRingBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoisePathFeatureConfiguration;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.levelgen.feature.configuration.LargeMesogleaConfiguration;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.CompositeFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpeleothemClusterConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpeleothemConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WWCaveConfigured {
	// MESOGLEA CAVES
	public static final FrozenLibConfiguredFeature<OreConfiguration> ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> STONE_POOL = register("stone_pool");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> DOWNWARDS_BLUE_MESOGLEA_COLUMN = register("downwards_blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> DOWNWARDS_PURPLE_MESOGLEA_COLUMN = register("downwards_purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfiguration> MESOGLEA_PATHS = register("mesoglea_paths");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfiguration> MESOGLEA_CLUSTER_PURPLE = WWFeatureUtils.register("mesoglea_cluster_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfiguration> MESOGLEA_CLUSTER_BLUE = WWFeatureUtils.register("mesoglea_cluster_blue");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> DOWNWARD_BLUE_MESOGLEA = WWFeatureUtils.register("downwards_blue_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> DOWNWARD_PURPLE_MESOGLEA = WWFeatureUtils.register("downwards_purple_mesoglea");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> NEMATOCYST_BLUE = WWFeatureUtils.register("nematocyst_blue");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> NEMATOCYST_PURPLE = WWFeatureUtils.register("nematocyst_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfiguration> LARGE_MESOGLEA_PURPLE = WWFeatureUtils.register("large_mesoglea_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfiguration> LARGE_MESOGLEA_BLUE = WWFeatureUtils.register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> GABBRO_LAVA_POOL = register("gabbro_lava_pool");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> LAVA_POOL_MAGMA_COLUMN = register("lava_pool_magma_column");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfiguration> GABBRO_MAGMA_PATH = register("gabbro_magma_path");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> DOWNWARDS_MAGMA_COLUMN = register("downwards_magma_column");
	public static final FrozenLibConfiguredFeature<OreConfiguration> ORE_GABBRO = register("ore_gabbro");
	public static final FrozenLibConfiguredFeature<BallFeatureConfiguration> GABBRO_DISK = register("gabbro_disk");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> DOWNWARDS_GABBRO_COLUMN = register("downwards_gabbro_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> GABBRO_COLUMN = register("gabbro_column");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> GABBRO_PILE = register("gabbro_pile");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_NORTH = register("geothermal_vent_north");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_EAST = register("geothermal_vent_east");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_SOUTH = register("geothermal_vent_south");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> GEOTHERMAL_VENT_WEST = register("geothermal_vent_west");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> DOWNWARDS_GEOTHERMAL_VENT_COLUMN = register("downwards_geothermal_vent_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> GEOTHERMAL_VENT_COLUMN = register("geothermal_vent_column");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> UPSIDE_DOWN_MAGMA = WWFeatureUtils.register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenLibConfiguredFeature<SpeleothemClusterConfiguration> ICICLE_CLUSTER = register("icicle_cluster");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> CAVE_ICICLE = register("cave_icicle");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> ICICLE = register("icicle");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> PACKED_ICE_COLUMN = register("packed_ice_column");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> DOWNWARDS_PACKED_ICE_COLUMN = register("downwards_packed_ice_column");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> PACKED_ICE_BIG_COLUMN = register("packed_ice_big_column");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> FRAGILE_ICE_BIG_COLUMN = register("fragile_ice_big_column");
	public static final FrozenLibConfiguredFeature<BallFeatureConfiguration> FRAGILE_ICE_DISK = register("fragile_ice_disk");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> FRAGILE_ICE_COLUMN = register("fragile_ice_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfiguration> SMALL_FRAGILE_ICE_COLUMN = register("small_fragile_ice_column");
	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> DOWNWARDS_FRAGILE_ICE_COLUMN = register("downwards_fragile_ice_column");
	public static final FrozenLibConfiguredFeature<BlockPileConfiguration> FRAGILE_ICE_PILE = register("fragile_ice_pile");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> HANGING_ICE = WWFeatureUtils.register("hanging_ice");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> ICE_COLUMNS = WWFeatureUtils.register("ice_columns");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> HANGING_PACKED_ICE = WWFeatureUtils.register("hanging_packed_ice");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> ICE_PATCH_CEILING = WWFeatureUtils.register("ice_patch_ceiling");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> FRAGILE_ICE_COLUMN_PATCH = WWFeatureUtils.register("fragile_ice_column_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> FRAGILE_ICE_PATCH = WWFeatureUtils.register("fragile_ice_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> DIORITE_PATCH = WWFeatureUtils.register("diorite_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> DIORITE_PATCH_CEILING = WWFeatureUtils.register("diorite_patch_ceiling");

	public static void registerCaveConfigured(BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		WWConstants.logWithModId("Registering WWCaveConfigured for", true);
		final HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				Blocks.CALCITE.defaultBlockState(),
				64
			)
		);

		STONE_POOL.makeAndSetHolder(FrozenLibFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH_LESS_BORDERS,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(Blocks.STONE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.000F,
				UniformInt.of(12, 15),
				0.7F
			)
		);

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_BLUE_MESOGLEA)
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA)
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		DOWNWARDS_BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_BLUE_MESOGLEA)
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		DOWNWARDS_PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA)
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		MESOGLEA_PATHS.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH,
			new NoisePathFeatureConfiguration(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.025D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_REPLACEABLE))
							.within(0.5125D, 0.5875D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_REPLACEABLE))
							.within(-0.5875D, -0.5125D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build()
					).build(),
				12
			)
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA,
			new LargeMesogleaConfiguration(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA,
			new LargeMesogleaConfiguration(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		DOWNWARD_BLUE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA),
				DOWNWARDS_BLUE_MESOGLEA_COLUMN.asInlinePlaced(),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		DOWNWARD_PURPLE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA),
				DOWNWARDS_PURPLE_MESOGLEA_COLUMN.asInlinePlaced(),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		NEMATOCYST_BLUE.makeAndSetHolder(WWFeatures.NEMATOCYST,
			new MultifaceGrowthConfiguration(
				WWBlocks.PEARLESCENT_BLUE_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				blocks.getOrThrow(WWBlockTags.PEARLESCENT_BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			)
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(WWFeatures.NEMATOCYST,
			new MultifaceGrowthConfiguration(
				WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				blocks.getOrThrow(WWBlockTags.PEARLESCENT_PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			)
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA,
			new LargeMesogleaConfiguration(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 2F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1F),
				UniformFloat.of(0F, 0.3F),
				4,
				0.2F
			)
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA,
			new LargeMesogleaConfiguration(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 2F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1F),
				UniformFloat.of(0F, 0.3F),
				4,
				0.2F
			)
		);

		// MAGMATIC CAVES
		GABBRO_LAVA_POOL.makeAndSetHolder(FrozenLibFeatures.CIRCULAR_LAVA_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.GABBRO),
				LAVA_POOL_MAGMA_COLUMN.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.08F,
				UniformInt.of(3, 10),
				0.7F
			)
		);

		LAVA_POOL_MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				BlockPredicate.matchesFluids(Fluids.LAVA),
				UniformInt.of(1, 8),
				Direction.UP,
				true
			)
		);

		GABBRO_MAGMA_PATH.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH,
			new NoisePathFeatureConfiguration(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.0325D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MAGMA_BLOCK))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.26D, -0.16D)
							.searchingPredicate(
								BlockPredicate.allOf(
									TouchingBlockPredicate.exposedTo(
										BlockPredicate.allOf(
											BlockPredicate.replaceable(),
											BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.WATER))
										)
									),
									BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(3))
								)
							)
							.scheduleTickOnPlacement()
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.46D, -0.005D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterOrLavaWithin(2))
							.build()
					).build(),
				8
			)
		);

		DOWNWARDS_MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK)
				),
				UniformInt.of(1, 4),
				Direction.DOWN,
				true
			)
		);

		ORE_GABBRO.makeAndSetHolder(Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				WWBlocks.GABBRO.defaultBlockState(),
				64
			)
		);

		GABBRO_DISK.makeAndSetHolder(FrozenLibFeatures.BALL,
			new BallFeatureConfiguration(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
					.placementChance(0.9F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
							.placementChance(0.75F)
							.outerRingStartPercentage(0.75F)
							.replacementPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(8, 10)
			)
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.GABBRO),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO)
				),
				UniformInt.of(1, 6),
				Direction.DOWN,
				true
			)
		);

		GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.GABBRO),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO)
				),
				UniformInt.of(1, 6),
				Direction.UP,
				true
			)
		);

		GABBRO_PILE.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.BLOCK_PILE,
						new BlockPileConfiguration(BlockStateProvider.simple(WWBlocks.GABBRO))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.BALL,
						new BallFeatureConfiguration(
							new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
								.placementChance(0.9F)
								.fadeStartPercentage(0.675F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
										.placementChance(0.75F)
										.outerRingStartPercentage(0.75F)
										.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
										.build()
								).build(),
							Optional.empty(),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		final Function<Direction, SimpleBlockConfiguration> geothermalVentConfig = direction -> new SimpleBlockConfiguration(
			BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT.defaultBlockState().setValue(BlockStateProperties.FACING, direction))
		);
		GEOTHERMAL_VENT_UP.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.UP));
		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.DOWN));
		GEOTHERMAL_VENT_NORTH.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.NORTH));
		GEOTHERMAL_VENT_EAST.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.EAST));
		GEOTHERMAL_VENT_SOUTH.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.SOUTH));
		GEOTHERMAL_VENT_WEST.makeAndSetHolder(Feature.SIMPLE_BLOCK, geothermalVentConfig.apply(Direction.WEST));

		DOWNWARDS_GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN)),
				BlockPredicate.replaceable(),
				UniformInt.of(2, 4),
				Direction.DOWN,
				true
			)
		);

		GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT),
				BlockPredicate.replaceable(),
				UniformInt.of(3, 5),
				Direction.UP,
				true
			)
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				DOWNWARDS_MAGMA_COLUMN.asInlinePlaced(),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(2, 6),
				0.7F
			)
		);

		// FROZEN CAVES
		ICICLE_CLUSTER.makeAndSetHolder(Feature.SPELEOTHEM_CLUSTER,
			new SpeleothemClusterConfiguration(
				WWBlocks.FRAGILE_ICE.defaultBlockState(),
				WWBlocks.ICICLE.defaultBlockState(),
				blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
				12,
				UniformInt.of(2, 5),
				UniformInt.of(2, 6),
				1,
				3,
				UniformInt.of(2, 5),
				UniformFloat.of(0.3F, 0.7F),
				ConstantFloat.of(0F),
				0.1F,
				3,
				8
			)
		);

		CAVE_ICICLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.SPELEOTHEM,
						new SpeleothemConfiguration(
							WWBlocks.FRAGILE_ICE.defaultBlockState(),
							WWBlocks.ICICLE.defaultBlockState(),
							blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
							0.2F,
							0.7F,
							0.5F,
							0.5F
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						Feature.SPELEOTHEM,
						new SpeleothemConfiguration(
							WWBlocks.FRAGILE_ICE.defaultBlockState(),
							WWBlocks.ICICLE.defaultBlockState(),
							blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
							0.2F,
							0.7F,
							0.5F,
							0.5F
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		ICICLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.SPELEOTHEM,
						new SpeleothemConfiguration(
							WWBlocks.FRAGILE_ICE.defaultBlockState(),
							WWBlocks.ICICLE.defaultBlockState(),
							HolderSet.empty(),
							0.3F,
							0.7F,
							0.5F,
							0.3F
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						Feature.SPELEOTHEM,
						new SpeleothemConfiguration(
							WWBlocks.FRAGILE_ICE.defaultBlockState(),
							WWBlocks.ICICLE.defaultBlockState(),
							HolderSet.empty(),
							0.3F,
							0.7F,
							0.5F,
							0.3F
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		FRAGILE_ICE_DISK.makeAndSetHolder(FrozenLibFeatures.BALL,
			new BallFeatureConfiguration(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
							.placementChance(0.7F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(4, 8)
			)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(TrapezoidInt.triangle(1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(3),
						RandomOffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						CountPlacement.of(3),
						RandomOffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(5),
						RandomOffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_BIG_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(5),
						RandomOffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 7),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(3),
						RandomOffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		SMALL_FRAGILE_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN,
			new ColumnFeatureConfiguration(
				BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
				BlockPredicate.replaceable(),
				UniformInt.of(0, 4),
				Direction.UP,
				false
			)
		);

		DOWNWARDS_FRAGILE_ICE_COLUMN.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN,
						new ColumnFeatureConfiguration(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						CountPlacement.of(3),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		FRAGILE_ICE_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
		);

		HANGING_ICE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(DOWNWARDS_PACKED_ICE_COLUMN.asWeightedPlacedFeature(0.6F)),
				DOWNWARDS_FRAGILE_ICE_COLUMN.asInlinePlaced()
			)
		);

		ICE_COLUMNS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					PACKED_ICE_COLUMN.asWeightedPlacedFeature(0.3F),
					PACKED_ICE_BIG_COLUMN.asWeightedPlacedFeature(0.3F),
					FRAGILE_ICE_BIG_COLUMN.asWeightedPlacedFeature(0.35F)
				),
				FRAGILE_ICE_COLUMN.asInlinePlaced()
			)
		);

		HANGING_PACKED_ICE.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				SimpleStateProvider.simple(Blocks.PACKED_ICE),
				HANGING_ICE.asInlinePlaced(),
				CaveSurface.CEILING,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.15F,
				UniformInt.of(3, 6),
				0.6F
			)
		);

		ICE_PATCH_CEILING.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 8)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(
					FrozenLibFeatures.COLUMN,
					new ColumnFeatureConfiguration(
						BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
						BlockPredicate.replaceable(),
						UniformInt.of(0, 4),
						Direction.DOWN,
						true
					)
				),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		FRAGILE_ICE_COLUMN_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				ICE_COLUMNS.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.1F,
				UniformInt.of(3, 6),
				0.6F
			)
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				SMALL_FRAGILE_ICE_COLUMN.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		DIORITE_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.DIORITE_ICE_REPLACEABLE),
				BlockStateProvider.simple(Blocks.DIORITE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.05F,
				UniformInt.of(2, 6),
				0.65F
			)
		);

		DIORITE_PATCH_CEILING.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.DIORITE_ICE_REPLACEABLE),
				BlockStateProvider.simple(Blocks.DIORITE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.CEILING,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.05F,
				UniformInt.of(2, 6),
				0.65F
			)
		);
	}
}
