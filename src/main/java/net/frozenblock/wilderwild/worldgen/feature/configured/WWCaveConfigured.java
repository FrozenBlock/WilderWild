/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.worldgen.feature.configured;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenFeatures;
import net.frozenblock.lib.worldgen.feature.api.features.config.ColumnFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.ComboFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathTagFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.feature.WWFeatureUtils;
import static net.frozenblock.wilderwild.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.worldgen.impl.features.config.LargeMesogleaConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.jetbrains.annotations.NotNull;

public final class WWCaveConfigured {
	// MESOGLEA CAVES
	public static final FrozenConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = register("ore_calcite");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> STONE_POOL = register("stone_pool");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_BLUE_MESOGLEA_COLUMN = register("downwards_blue_mesoglea_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_PURPLE_MESOGLEA_COLUMN = register("downwards_purple_mesoglea_column");
	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> BLUE_MESOGLEA_PATH = register("blue_mesoglea_path");
	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> PURPLE_MESOGLEA_PATH = register("purple_mesoglea_path");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_PURPLE = WWFeatureUtils.register("mesoglea_cluster_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_BLUE = WWFeatureUtils.register("mesoglea_cluster_blue");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_WITH_DRIPLEAVES = WWFeatureUtils.register("blue_mesoglea_with_dripleaves");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_POOL = WWFeatureUtils.register("blue_mesoglea_pool");
	public static final FrozenConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> BLUE_MESOGLEA = WWFeatureUtils.register("blue_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = WWFeatureUtils.register("upside_down_blue_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_DRIPLEAVES = WWFeatureUtils.register("purple_mesoglea_with_dripleaves");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_POOL = WWFeatureUtils.register("purple_mesoglea_pool");
	public static final FrozenConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> PURPLE_MESOGLEA = WWFeatureUtils.register("purple_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = WWFeatureUtils.register("upside_down_purple_mesoglea");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_BLUE = WWFeatureUtils.register("nematocyst_blue");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_PURPLE = WWFeatureUtils.register("nematocyst_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_PURPLE = WWFeatureUtils.register("large_mesoglea_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_BLUE = WWFeatureUtils.register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> MAGMA_LAVA_POOL = register("magma_lava_pool");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> MAGMA_AND_BASALT_PATH = register("magma_and_basalt_path");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> MAGMA_DISK = register("magma_disk");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> OBSIDIAN_DISK = register("obsidian_disk");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> MAGMA_COLUMN = register("magma_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_MAGMA_COLUMN = register("downwards_magma_column");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> MAGMA_PILE = register("magma_pile");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_PATCH_MAGMA = register("fire_patch_magma");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> BASALT_PILE = register("basalt_pile");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_BASALT_COLUMN = register("downwards_basalt_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> BASALT_COLUMN = register("basalt_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfiguration, ConfiguredFeature<ColumnFeatureConfiguration, ?>> BASALT_SPIKE = register("basalt_spike");
	public static final FrozenConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> GEYSER_PILE = register("geyser_pile");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_UP = register("geyser_up");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_DOWN = register("geyser_down");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_NORTH = register("geyser_north");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_EAST = register("geyser_east");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_SOUTH = register("geyser_south");
	public static final FrozenConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> GEYSER_WEST = register("geyser_west");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_GEYSER_COLUMN = register("downwards_geyser_column");
	public static final FrozenConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> UPWARDS_GEYSER_COLUMN = register("geyser_column");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_MAGMA = WWFeatureUtils.register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_PATH = register("packed_ice_path");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> PACKED_ICE_DISK = register("packed_ice_disk");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_COLUMN = register("packed_ice_column");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_PACKED_ICE_COLUMN = register("downwards_packed_ice_column");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_BIG_COLUMN = register("packed_ice_big_column");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> ICE_DISK = register("ice_disk");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> ICE_COLUMN = register("ice_column");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_ICE_COLUMN = register("downwards_ice_column");
	public static final FrozenConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> ICE_PILE = register("ice_pile");
	public static final FrozenConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_ICE = register("ore_ice");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SNOW_DISK = register("snow_disk");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> POWDER_SNOW_DISK = register("powder_snow_disk");

	private WWCaveConfigured() {
		throw new UnsupportedOperationException("WilderCaveConfigured contains only static declarations.");
	}

	public static void registerCaveConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		WWConstants.logWithModId("Registering WilderCaveConfigured for", true);
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

		STONE_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH_LESS_BORDERS,
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

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(4, 12),
				HolderSet.direct(
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(4, 12),
				HolderSet.direct(
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		DOWNWARDS_BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(3, 10),
				HolderSet.direct(
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		DOWNWARDS_PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(3, 10),
				HolderSet.direct(
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		BLUE_MESOGLEA_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
			new PathFeatureConfig(
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				14,
				1,
				0.025,
				0.5125,
				0.5875,
				true,
				true,
				true,
				false,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.MESOGLEA_PATH_REPLACEABLE
				),
				1F
			)
		);

		PURPLE_MESOGLEA_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
			new PathFeatureConfig(
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				14,
				1,
				0.025,
				-0.5875,
				-0.5125,
				true,
				true,
				true,
				false,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.MESOGLEA_PATH_REPLACEABLE
				),
				1F
			)
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(WWFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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

		UPSIDE_DOWN_BLUE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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

		UPSIDE_DOWN_PURPLE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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
				BlockStateProvider.simple(WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
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

		MAGMA_LAVA_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_LAVA_VEGETATION_PATCH_LESS_BORDERS,
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

		MAGMA_AND_BASALT_PATH.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
							14,
							4,
							0.0325D,
							-0.275D,
							-0.15D,
							true,
							true,
							true,
							true,
							WWBlockTags.MAGMA_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.BASALT.defaultBlockState()),
							14,
							4,
							0.0325D,
							-0.31D,
							-0.275D,
							true,
							true,
							true,
							true,
							WWBlockTags.MAGMA_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.BASALT.defaultBlockState()),
							14,
							4,
							0.0325D,
							-0.15D,
							-0.115D,
							true,
							true,
							true,
							true,
							WWBlockTags.MAGMA_REPLACEABLE,
							1F
						)
					)
				)
			)
		);

		MAGMA_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_SCHEDULE_TICK_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.OBSIDIAN.defaultBlockState()),
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				UniformInt.of(2, 6),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.MAGMA_REPLACEABLE,
				WWBlockTags.MAGMA_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		OBSIDIAN_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_SCHEDULE_TICK_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.OBSIDIAN.defaultBlockState()),
				BlockStateProvider.simple(Blocks.OBSIDIAN.defaultBlockState()),
				UniformInt.of(2, 4),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.MAGMA_REPLACEABLE,
				WWBlockTags.MAGMA_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		MAGMA_COLUMN.makeAndSetHolder(FrozenFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.MAGMA_BLOCK.defaultBlockState(),
				UniformInt.of(1, 2),
				HolderSet.direct(
					Blocks.MAGMA_BLOCK.builtInRegistryHolder(),
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		DOWNWARDS_MAGMA_COLUMN.makeAndSetHolder(FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.MAGMA_BLOCK.defaultBlockState(),
				UniformInt.of(1, 4),
				HolderSet.direct(
					Blocks.MAGMA_BLOCK.builtInRegistryHolder(),
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		MAGMA_PILE.makeAndSetHolder(FrozenFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				UniformInt.of(2, 4),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.MAGMA_REPLACEABLE,
				WWBlockTags.MAGMA_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		FIRE_PATCH_MAGMA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simplePatchConfiguration(
				FrozenFeatures.SIMPLE_BLOCK_SCHEDULE_TICK_FEATURE, new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.FIRE)), List.of(Blocks.MAGMA_BLOCK)
			)
		);

		DOWNWARDS_BASALT_COLUMN.makeAndSetHolder(FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.BASALT.defaultBlockState(),
				UniformInt.of(1, 6),
				HolderSet.direct(
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		BASALT_COLUMN.makeAndSetHolder(FrozenFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.BASALT.defaultBlockState(),
				UniformInt.of(1, 6),
				HolderSet.direct(
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		BASALT_SPIKE.makeAndSetHolder(Feature.BASALT_COLUMNS,
			new ColumnFeatureConfiguration(
				UniformInt.of(0, 2),
				UniformInt.of(2, 4)
			)
		);

		BASALT_PILE.makeAndSetHolder(FrozenFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.BASALT.defaultBlockState()),
				BlockStateProvider.simple(Blocks.BASALT.defaultBlockState()),
				UniformInt.of(2, 4),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.MAGMA_REPLACEABLE,
				WWBlockTags.MAGMA_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		GEYSER_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(WWBlocks.GEYSER)
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

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN),
				UniformInt.of(2, 4),
				HolderSet.direct(
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
			)
		);

		UPWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GEYSER.defaultBlockState(),
				UniformInt.of(3, 5),
				HolderSet.direct(
					Blocks.LAVA.builtInRegistryHolder(),
					Blocks.WATER.builtInRegistryHolder()
				)
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

		PACKED_ICE_PATH.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							14,
							4,
							0.0325D,
							0.55D,
							0.7D,
							true,
							true,
							true,
							true,
							WWBlockTags.PACKED_ICE_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							14,
							4,
							0.0325D,
							-0.7D,
							-0.55D,
							true,
							true,
							true,
							true,
							WWBlockTags.PACKED_ICE_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							14,
							4,
							0.0325D,
							0.15D,
							0.3D,
							true,
							true,
							true,
							true,
							WWBlockTags.PACKED_ICE_REPLACEABLE,
							1F
						)
					)
				)
			)
		);

		PACKED_ICE_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.PACKED_ICE),
				BlockStateProvider.simple(Blocks.PACKED_ICE),
				UniformInt.of(4, 8),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(5, 13),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 12),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 10),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 8),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(4, 9),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 8),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 8),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 7),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(8, 15),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(5, 13),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(2, 14),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 14),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 14),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 12),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		ICE_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.ICE),
				BlockStateProvider.simple(Blocks.ICE),
				UniformInt.of(2, 5),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		ICE_COLUMN.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(3, 7),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 5),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 6),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 5),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		DOWNWARDS_ICE_COLUMN.makeAndSetHolder(FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(2, 6),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 5),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(0, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		ICE_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(Blocks.ICE)
			)
		);

		ORE_ICE.makeAndSetHolder(Feature.ORE,
			new OreConfiguration(
				new TagMatchTest(WWBlockTags.PACKED_ICE_REPLACEABLE),
				Blocks.ICE.defaultBlockState(),
				32
			)
		);

		SNOW_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.SNOW_BLOCK),
				BlockStateProvider.simple(Blocks.SNOW_BLOCK),
				UniformInt.of(2, 5),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		POWDER_SNOW_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.POWDER_SNOW),
				BlockStateProvider.simple(Blocks.POWDER_SNOW),
				UniformInt.of(3, 8),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				WWBlockTags.PACKED_ICE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);
	}
}
