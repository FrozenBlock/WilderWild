/*
 * Copyright 2025 FrozenBlock
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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.features.configured;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.worldgen.feature.api.block_predicate.SearchInAreaBlockPredicate;
import net.frozenblock.lib.worldgen.feature.api.block_predicate.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.worldgen.feature.api.block_predicate.TouchingBlockPredicate;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ColumnFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ComboFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.disk.config.BallBlockPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.disk.config.BallFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.disk.config.BallOuterRingBlockPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoisePathFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils.register;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.IcicleClusterConfig;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.IcicleConfig;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.LargeMesogleaConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

public final class WWCaveConfigured {
	// MESOGLEA CAVES
	public static final FrozenLibConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> STONE_POOL = register("stone_pool");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_BLUE_MESOGLEA_COLUMN = register("downwards_blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_PURPLE_MESOGLEA_COLUMN = register("downwards_purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfig, ConfiguredFeature<NoisePathFeatureConfig, ?>> MESOGLEA_PATHS = register("mesoglea_paths");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_PURPLE = WWFeatureUtils.register("mesoglea_cluster_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_BLUE = WWFeatureUtils.register("mesoglea_cluster_blue");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_WITH_DRIPLEAVES = WWFeatureUtils.register("blue_mesoglea_with_dripleaves");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_POOL = WWFeatureUtils.register("blue_mesoglea_pool");
	public static final FrozenLibConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> BLUE_MESOGLEA = WWFeatureUtils.register("blue_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DOWNWARD_BLUE_MESOGLEA = WWFeatureUtils.register("downwards_blue_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_DRIPLEAVES = WWFeatureUtils.register("purple_mesoglea_with_dripleaves");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_POOL = WWFeatureUtils.register("purple_mesoglea_pool");
	public static final FrozenLibConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> PURPLE_MESOGLEA = WWFeatureUtils.register("purple_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DOWNWARD_PURPLE_MESOGLEA = WWFeatureUtils.register("downwards_purple_mesoglea");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_BLUE = WWFeatureUtils.register("nematocyst_blue");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_PURPLE = WWFeatureUtils.register("nematocyst_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_PURPLE = WWFeatureUtils.register("large_mesoglea_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_BLUE = WWFeatureUtils.register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> MAGMA_LAVA_POOL = register("magma_lava_pool");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfig, ConfiguredFeature<NoisePathFeatureConfig, ?>> MAGMA_AND_GABBRO_PATH = register("magma_and_gabbro_path");
	public static final FrozenLibConfiguredFeature<BallFeatureConfig, ConfiguredFeature<BallFeatureConfig, ?>> MAGMA_DISK = register("magma_disk");
	public static final FrozenLibConfiguredFeature<BallFeatureConfig, ConfiguredFeature<BallFeatureConfig, ?>> OBSIDIAN_DISK = register("obsidian_disk");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> MAGMA_COLUMN = register("magma_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_MAGMA_COLUMN = register("downwards_magma_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> MAGMA_PILE = register("magma_pile");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_PATCH_MAGMA = register("fire_patch_magma");
	public static final FrozenLibConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_GABBRO = register("ore_gabbro");
	public static final FrozenLibConfiguredFeature<BallFeatureConfig, ConfiguredFeature<BallFeatureConfig, ?>> GABBRO_DISK = register("gabbro_disk");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_GABBRO_COLUMN = register("downwards_gabbro_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> GABBRO_COLUMN = register("gabbro_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> GABBRO_PILE = register("gabbro_pile");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_UP = register("geyser_up");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_DOWN = register("geyser_down");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_NORTH = register("geyser_north");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_EAST = register("geyser_east");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_SOUTH = register("geyser_south");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_WEST = register("geyser_west");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_GEYSER_COLUMN = register("downwards_geyser_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> UPWARDS_GEYSER_COLUMN = register("geyser_column");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_MAGMA = WWFeatureUtils.register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenLibConfiguredFeature<IcicleClusterConfig, ConfiguredFeature<IcicleClusterConfig, ?>> ICICLE_CLUSTER = register("icicle_cluster");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CAVE_ICICLE = register("cave_icicle");
	public static final FrozenLibConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> ICICLE = register("icicle");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfig, ConfiguredFeature<NoisePathFeatureConfig, ?>> ICE_PATHS = register("ice_paths");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_COLUMN = register("packed_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_PACKED_ICE_COLUMN = register("downwards_packed_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_BIG_COLUMN = register("packed_ice_big_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> FRAGILE_ICE_BIG_COLUMN = register("fragile_ice_big_column");
	public static final FrozenLibConfiguredFeature<BallFeatureConfig, ConfiguredFeature<BallFeatureConfig, ?>> FRAGILE_ICE_DISK = register("fragile_ice_disk");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> FRAGILE_ICE_COLUMN = register("fragile_ice_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> SMALL_FRAGILE_ICE_COLUMN = register("small_fragile_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_FRAGILE_ICE_COLUMN = register("downwards_fragile_ice_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> SMALL_DOWNWARDS_FRAGILE_ICE_COLUMN = register("small_downwards_fragile_ice_column");
	public static final FrozenLibConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> FRAGILE_ICE_PILE = register("fragile_ice_pile");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> HANGING_ICE = WWFeatureUtils.register("hanging_ice");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ICE_COLUMNS = WWFeatureUtils.register("ice_columns");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> HANGING_PACKED_ICE = WWFeatureUtils.register("hanging_packed_ice");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> ICE_PATCH_CEILING = WWFeatureUtils.register("ice_patch_ceiling");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> FRAGILE_ICE_COLUMN_PATCH = WWFeatureUtils.register("fragile_ice_column_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> FRAGILE_ICE_PATCH = WWFeatureUtils.register("fragile_ice_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DIORITE_PATCH = WWFeatureUtils.register("diorite_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DIORITE_PATCH_CEILING = WWFeatureUtils.register("diorite_patch_ceiling");

	private WWCaveConfigured() {
		throw new UnsupportedOperationException("WWCaveConfigured contains only static declarations.");
	}

	public static void registerCaveConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		WWConstants.logWithModId("Registering WWCaveConfigured for", true);
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

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
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(Blocks.STONE),
				PlacementUtils.inlinePlaced(WWMiscConfigured.EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.000F,
				UniformInt.of(12, 15),
				0.7F
			)
		);

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.BLUE_PEARLESCENT_MESOGLEA)
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA)
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		DOWNWARDS_BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.BLUE_PEARLESCENT_MESOGLEA)
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		DOWNWARDS_PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA)
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		MESOGLEA_PATHS.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
			new NoisePathFeatureConfig(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.025D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_PATH_REPLACEABLE))
							.within(0.5125D, 0.5875D)
							.searchingBlockPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_PATH_REPLACEABLE))
							.within(-0.5875D, -0.5125D)
							.searchingBlockPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build()
					).build(),
				12
			)
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		BLUE_MESOGLEA_WITH_DRIPLEAVES.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				2,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		BLUE_MESOGLEA_POOL.makeAndSetHolder(Feature.WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				5,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		BLUE_MESOGLEA.makeAndSetHolder(Feature.RANDOM_BOOLEAN_SELECTOR,
			new RandomBooleanFeatureConfiguration(
				PlacementUtils.inlinePlaced(BLUE_MESOGLEA_WITH_DRIPLEAVES.getHolder()),
				PlacementUtils.inlinePlaced(BLUE_MESOGLEA_POOL.getHolder())
			)
		);

		DOWNWARD_BLUE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(DOWNWARDS_BLUE_MESOGLEA_COLUMN.getHolder()),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		PURPLE_MESOGLEA_DRIPLEAVES.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				2,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		PURPLE_MESOGLEA_POOL.makeAndSetHolder(Feature.WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				5,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		PURPLE_MESOGLEA.makeAndSetHolder(Feature.RANDOM_BOOLEAN_SELECTOR,
			new RandomBooleanFeatureConfiguration(
				PlacementUtils.inlinePlaced(PURPLE_MESOGLEA_DRIPLEAVES.getHolder()),
				PlacementUtils.inlinePlaced(PURPLE_MESOGLEA_POOL.getHolder())
			)
		);

		DOWNWARD_PURPLE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				PlacementUtils.inlinePlaced(DOWNWARDS_PURPLE_MESOGLEA_COLUMN.getHolder()),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		NEMATOCYST_BLUE.makeAndSetHolder(WWFeatures.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
				WWBlocks.BLUE_PEARLESCENT_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE
				)
			)
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(WWFeatures.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
				WWBlocks.PURPLE_PEARLESCENT_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE
				)
			)
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 2.0F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1.0F),
				UniformFloat.of(0.0F, 0.3F),
				4,
				0.2F
			)
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState()),
				UniformFloat.of(0.2F, 2.0F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1.0F),
				UniformFloat.of(0.0F, 0.3F),
				4,
				0.2F
			)
		);

		// MAGMATIC CAVES

		MAGMA_LAVA_POOL.makeAndSetHolder(FrozenLibFeatures.CIRCULAR_LAVA_VEGETATION_PATCH_LESS_BORDERS,
			new VegetationPatchConfiguration(
				WWBlockTags.MAGMA_REPLACEABLE,
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				PlacementUtils.inlinePlaced(MAGMA_COLUMN.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.08F,
				UniformInt.of(3, 10),
				0.7F
			)
		);

		MAGMA_AND_GABBRO_PATH.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
			new NoisePathFeatureConfig(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.0325D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MAGMA_BLOCK))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.275D, -0.15D)
							.searchingBlockPredicate(
								BlockPredicate.allOf(
									TouchingBlockPredicate.exposedTo(
										BlockPredicate.allOf(
											BlockPredicate.replaceable(),
											BlockPredicate.not(
												BlockPredicate.matchesBlocks(Blocks.WATER)
											)
										)
									),
									BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(3))
								)
							).scheduleTickOnPlacement()
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.31D, -0.115D)
							.searchingBlockPredicate(SearchInAreaBlockPredicate.hasAirOrWaterOrLavaWithin(2))
							.build()

					).build(),
				8
			)
		);

		MAGMA_DISK.makeAndSetHolder(FrozenLibFeatures.BALL_FEATURE,
			new BallFeatureConfig(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(Blocks.OBSIDIAN))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MAGMA_BLOCK))
							.placementChance(0.7F)
							.chanceToChooseInInnerRing(0.7F)
							.outerRingStartPercentage(0.75F)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.searchingBlockPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(2, 6)
			)
		);

		OBSIDIAN_DISK.makeAndSetHolder(FrozenLibFeatures.BALL_FEATURE,
			new BallFeatureConfig(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(Blocks.OBSIDIAN))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(Blocks.OBSIDIAN))
							.placementChance(0.7F)
							.outerRingStartPercentage(0.5F)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.searchingBlockPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(2, 4)
			)
		);

		MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK)
				),
				UniformInt.of(1, 2),
				Direction.UP,
				true
			)
		);

		DOWNWARDS_MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
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

		MAGMA_PILE.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				ImmutableList.of(
					PlacementUtils.inlinePlaced(
						Feature.BLOCK_PILE,
						new BlockPileConfiguration(
							BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState())
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.BALL_FEATURE,
						new BallFeatureConfig(
							new BallBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MAGMA_BLOCK))
								.placementChance(0.8F)
								.fadeStartPercentage(0.675F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(Blocks.OBSIDIAN))
										.placementChance(0.7F)
										.outerRingStartPercentage(0.5F)
										.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
										.build()
								).build(),
							Optional.empty(),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		FIRE_PATCH_MAGMA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simplePatchConfiguration(
				FrozenLibFeatures.SIMPLE_BLOCK_SCHEDULE_TICK_FEATURE, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FIRE)), List.of(Blocks.MAGMA_BLOCK)
			)
		);

		ORE_GABBRO.makeAndSetHolder(Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				WWBlocks.GABBRO.defaultBlockState(),
				64
			)
		);

		GABBRO_DISK.makeAndSetHolder(FrozenLibFeatures.BALL_FEATURE,
			new BallFeatureConfig(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
							.placementChance(0.7F)
							.outerRingStartPercentage(0.75F)
							.replacementBlockPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
							.searchingBlockPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(6, 12)
			)
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
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

		GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
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

		GABBRO_PILE.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				ImmutableList.of(
					PlacementUtils.inlinePlaced(
						Feature.BLOCK_PILE,
						new BlockPileConfiguration(
							BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState())
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.BALL_FEATURE,
						new BallFeatureConfig(
							new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
								.placementChance(0.8F)
								.fadeStartPercentage(0.675F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
										.placementChance(0.7F)
										.outerRingStartPercentage(0.75F)
										.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
										.build()
								).build(),
							Optional.empty(),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		GEYSER_UP.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER)
			)
		);

		GEYSER_DOWN.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN))
			)
		);

		GEYSER_NORTH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.NORTH))
			)
		);

		GEYSER_EAST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.EAST))
			)
		);

		GEYSER_SOUTH.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.SOUTH))
			)
		);

		GEYSER_WEST.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.WEST))
			)
		);

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN)),
				BlockPredicate.replaceable(),
				UniformInt.of(2, 4),
				Direction.DOWN,
				true
			)
		);

		UPWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.GEYSER),
				BlockPredicate.replaceable(),
				UniformInt.of(3, 5),
				Direction.UP,
				true
			)
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.MAGMA_REPLACEABLE,
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				PlacementUtils.inlinePlaced(DOWNWARDS_MAGMA_COLUMN.getHolder()),
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

		ICICLE_CLUSTER.makeAndSetHolder(WWFeatures.ICICLE_CLUSTER_FEATURE,
			new IcicleClusterConfig(
				12,
				UniformInt.of(2, 5),
				UniformInt.of(2, 6),
				1,
				3,
				UniformInt.of(2, 5),
				UniformFloat.of(0.3F, 0.7F),
				0.1F,
				3,
				8
			)
		);

		CAVE_ICICLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWFeatures.ICICLE_FEATURE,
						new IcicleConfig(0.2F, 0.7F, 0.5F, 0.5F, true),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						WWFeatures.ICICLE_FEATURE,
						new IcicleConfig(0.2F, 0.7F, 0.5F, 0.5F, true),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		ICICLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWFeatures.ICICLE_FEATURE,
						new IcicleConfig(0.3F, 0.7F, 0.5F, 0.5F, false),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						WWFeatures.ICICLE_FEATURE,
						new IcicleConfig(0.3F, 0.7F, 0.5F, 0.5F, false),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		BlockStateProvider icePathProvider = new NoiseProvider(
			153,
			new NormalNoise.NoiseParameters(0, 1F),
			0.020833334F,
			List.of(
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.BLUE_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.PACKED_ICE.defaultBlockState()
			)
		);

		BlockPredicate iceSearchPredicate = SearchInAreaBlockPredicate.hasAirOrWaterWithin(2);

		ICE_PATHS.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
			new NoisePathFeatureConfig(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.0325D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(icePathProvider)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.within(-1D, -0.7D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE))
							.within(-0.7D, -0.6D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(icePathProvider)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.within(-0.6D, 0.2D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE))
							.within(0.2D, 0.25D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(icePathProvider)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.within(0.25D, 0.6D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE))
							.within(0.6D, 0.7D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build(),
						new NoiseBandBlockPlacement.Builder(icePathProvider)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.within(0.7D, 1D)
							.searchingBlockPredicate(iceSearchPredicate)
							.build()
					).build(),
				8
			)
		);

		FRAGILE_ICE_DISK.makeAndSetHolder(FrozenLibFeatures.BALL_FEATURE,
			new BallFeatureConfig(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE))
							.placementChance(0.7F)
							.outerRingStartPercentage(0.5F)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.searchingBlockPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(4, 8)
			)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_BIG_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 7),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		SMALL_FRAGILE_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
				BlockPredicate.replaceable(),
				UniformInt.of(0, 4),
				Direction.UP,
				false
			)
		);

		DOWNWARDS_FRAGILE_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.COLUMN_FEATURE,
						new ColumnFeatureConfig(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						RandomOffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		SMALL_DOWNWARDS_FRAGILE_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COLUMN_FEATURE,
			new ColumnFeatureConfig(
				BlockStateProvider.simple(WWBlocks.FRAGILE_ICE),
				BlockPredicate.replaceable(),
				UniformInt.of(0, 4),
				Direction.DOWN,
				true
			)
		);

		FRAGILE_ICE_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(WWBlocks.FRAGILE_ICE)
			)
		);

		HANGING_ICE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(DOWNWARDS_PACKED_ICE_COLUMN.getHolder()),
						0.6F
					)
				),
				PlacementUtils.inlinePlaced(DOWNWARDS_FRAGILE_ICE_COLUMN.getHolder())
			)
		);

		ICE_COLUMNS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(PACKED_ICE_COLUMN.getHolder()),
						0.3F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(PACKED_ICE_BIG_COLUMN.getHolder()),
						0.3F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(FRAGILE_ICE_BIG_COLUMN.getHolder()),
						0.35F
					)
				),
				PlacementUtils.inlinePlaced(FRAGILE_ICE_COLUMN.getHolder())
			)
		);

		HANGING_PACKED_ICE.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.CAVE_ICE_REPLACEABLE,
				SimpleStateProvider.simple(Blocks.PACKED_ICE),
				PlacementUtils.inlinePlaced(HANGING_ICE.getHolder()),
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
				WWBlockTags.CAVE_ICE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 8)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(SMALL_DOWNWARDS_FRAGILE_ICE_COLUMN.getHolder()),
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
				WWBlockTags.CAVE_ICE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				PlacementUtils.inlinePlaced(ICE_COLUMNS.getHolder()),
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
				WWBlockTags.CAVE_ICE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				PlacementUtils.inlinePlaced(SMALL_FRAGILE_ICE_COLUMN.getHolder()),
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
				WWBlockTags.DIORITE_ICE_REPLACEABLE,
				BlockStateProvider.simple(Blocks.DIORITE),
				PlacementUtils.inlinePlaced(WWMiscConfigured.EMPTY.getHolder()),
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
				WWBlockTags.DIORITE_ICE_REPLACEABLE,
				BlockStateProvider.simple(Blocks.DIORITE),
				PlacementUtils.inlinePlaced(WWMiscConfigured.EMPTY.getHolder()),
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
