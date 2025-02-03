/*
 * Copyright 2023-2025 FrozenBlock
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
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatures;
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
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.jetbrains.annotations.NotNull;

public final class WWCaveConfigured {
	// MESOGLEA CAVES
	public static final FrozenLibConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> STONE_POOL = register("stone_pool");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_BLUE_MESOGLEA_COLUMN = register("downwards_blue_mesoglea_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_PURPLE_MESOGLEA_COLUMN = register("downwards_purple_mesoglea_column");
	public static final FrozenLibConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> BLUE_MESOGLEA_PATH = register("blue_mesoglea_path");
	public static final FrozenLibConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> PURPLE_MESOGLEA_PATH = register("purple_mesoglea_path");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_PURPLE = WWFeatureUtils.register("mesoglea_cluster_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_BLUE = WWFeatureUtils.register("mesoglea_cluster_blue");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_WITH_DRIPLEAVES = WWFeatureUtils.register("blue_mesoglea_with_dripleaves");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_POOL = WWFeatureUtils.register("blue_mesoglea_pool");
	public static final FrozenLibConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> BLUE_MESOGLEA = WWFeatureUtils.register("blue_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = WWFeatureUtils.register("upside_down_blue_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_DRIPLEAVES = WWFeatureUtils.register("purple_mesoglea_with_dripleaves");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_POOL = WWFeatureUtils.register("purple_mesoglea_pool");
	public static final FrozenLibConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> PURPLE_MESOGLEA = WWFeatureUtils.register("purple_mesoglea");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = WWFeatureUtils.register("upside_down_purple_mesoglea");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_BLUE = WWFeatureUtils.register("nematocyst_blue");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_PURPLE = WWFeatureUtils.register("nematocyst_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_PURPLE = WWFeatureUtils.register("large_mesoglea_purple");
	public static final FrozenLibConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_BLUE = WWFeatureUtils.register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> MAGMA_LAVA_POOL = register("magma_lava_pool");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> MAGMA_AND_GABBRO_PATH = register("magma_and_gabbro_path");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> MAGMA_DISK = register("magma_disk");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> OBSIDIAN_DISK = register("obsidian_disk");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> MAGMA_COLUMN = register("magma_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_MAGMA_COLUMN = register("downwards_magma_column");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> MAGMA_PILE = register("magma_pile");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FIRE_PATCH_MAGMA = register("fire_patch_magma");
	public static final FrozenLibConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_GABBRO = register("ore_gabbro");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> GABBRO_DISK = register("gabbro_disk");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> DOWNWARDS_GABBRO_COLUMN = register("downwards_gabbro_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> GABBRO_COLUMN = register("gabbro_column");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> GABBRO_PILE = register("gabbro_pile");
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
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_PATH = register("packed_ice_path");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> PACKED_ICE_DISK = register("packed_ice_disk");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_COLUMN = register("packed_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_PACKED_ICE_COLUMN = register("downwards_packed_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> PACKED_ICE_BIG_COLUMN = register("packed_ice_big_column");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> ICE_DISK = register("ice_disk");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> ICE_COLUMN = register("ice_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> SMALL_ICE_COLUMN = register("small_ice_column");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> DOWNWARDS_ICE_COLUMN = register("downwards_ice_column");
	public static final FrozenLibConfiguredFeature<ColumnFeatureConfig, ConfiguredFeature<ColumnFeatureConfig, ?>> SMALL_DOWNWARDS_ICE_COLUMN = register("small_downwards_ice_column");
	public static final FrozenLibConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> ICE_PILE = register("ice_pile");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SNOW_DISK = register("snow_disk");
	public static final FrozenLibConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> POWDER_SNOW_DISK = register("powder_snow_disk");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> HANGING_ICICLES = WWFeatureUtils.register("hanging_icicles");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ICE_COLUMNS = WWFeatureUtils.register("ice_columns");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> ICICLE_PATCH = WWFeatureUtils.register("icicle_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> ICE_PATCH_CEILING = WWFeatureUtils.register("ice_patch_ceiling");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> ICE_COLUMN_PATCH = WWFeatureUtils.register("ice_column_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> ICE_PATCH = WWFeatureUtils.register("ice_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DIORITE_PATCH = WWFeatureUtils.register("diorite_patch");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DIORITE_PATCH_CEILING = WWFeatureUtils.register("diorite_patch_ceiling");

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

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(4, 12),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA,
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA,
					Blocks.WATER
				)
			)
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(4, 12),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA,
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA,
					Blocks.WATER
				)
			)
		);

		DOWNWARDS_BLUE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(3, 10),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA,
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA,
					Blocks.WATER
				)
			)
		);

		DOWNWARDS_PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
				UniformInt.of(3, 10),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					WWBlocks.BLUE_PEARLESCENT_MESOGLEA,
					WWBlocks.PURPLE_PEARLESCENT_MESOGLEA,
					Blocks.WATER
				)
			)
		);

		BLUE_MESOGLEA_PATH.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
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
					BuiltInRegistries.BLOCK,
					WWBlockTags.MESOGLEA_PATH_REPLACEABLE
				),
				1F
			)
		);

		PURPLE_MESOGLEA_PATH.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
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
					BuiltInRegistries.BLOCK,
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
					BuiltInRegistries.BLOCK,
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
					BuiltInRegistries.BLOCK,
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

		MAGMA_AND_GABBRO_PATH.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
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
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
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
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
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

		MAGMA_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_SCHEDULE_TICK_FEATURE,
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

		OBSIDIAN_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
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

		MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.MAGMA_BLOCK.defaultBlockState(),
				UniformInt.of(1, 2),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.MAGMA_BLOCK,
					Blocks.LAVA,
					Blocks.WATER
				)
			)
		);

		DOWNWARDS_MAGMA_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.MAGMA_BLOCK.defaultBlockState(),
				UniformInt.of(1, 4),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.MAGMA_BLOCK,
					Blocks.LAVA,
					Blocks.WATER
				)
			)
		);

		MAGMA_PILE.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
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

		GABBRO_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
				BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
				UniformInt.of(6, 12),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				BlockTags.BASE_STONE_OVERWORLD,
				BlockTags.BASE_STONE_OVERWORLD,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GABBRO.defaultBlockState(),
				UniformInt.of(1, 6),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.LAVA,
					Blocks.WATER
				)
			)
		);

		GABBRO_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GABBRO.defaultBlockState(),
				UniformInt.of(1, 6),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.LAVA,
					Blocks.WATER
				)
			)
		);

		GABBRO_PILE.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
				BlockStateProvider.simple(WWBlocks.GABBRO.defaultBlockState()),
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

		DOWNWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GEYSER.defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN),
				UniformInt.of(2, 4),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.LAVA,
					Blocks.WATER
				)
			)
		);

		UPWARDS_GEYSER_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				WWBlocks.GEYSER.defaultBlockState(),
				UniformInt.of(3, 5),
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.LAVA,
					Blocks.WATER
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

		PACKED_ICE_PATH.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							5,
							4,
							0.0325D,
							0.55D,
							0.7D,
							true,
							true,
							true,
							true,
							WWBlockTags.ICE_FEATURE_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							5,
							4,
							0.0325D,
							-0.7D,
							-0.55D,
							true,
							true,
							true,
							true,
							WWBlockTags.ICE_FEATURE_REPLACEABLE,
							1F
						)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.NOISE_PATH_TAG_FEATURE,
						new PathTagFeatureConfig(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							5,
							4,
							0.0325D,
							0.15D,
							0.3D,
							true,
							true,
							true,
							true,
							WWBlockTags.ICE_FEATURE_REPLACEABLE,
							1F
						)
					)
				)
			)
		);

		PACKED_ICE_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.PACKED_ICE),
				BlockStateProvider.simple(Blocks.PACKED_ICE),
				UniformInt.of(4, 8),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(2, 9),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(2, 6),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(2, 9),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.PACKED_ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		ICE_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.ICE),
				BlockStateProvider.simple(Blocks.ICE),
				UniformInt.of(2, 5),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(2, 7),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		SMALL_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.UPWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.ICE.defaultBlockState(),
				UniformInt.of(0, 4),
				HolderSet.direct()
			)
		);

		DOWNWARDS_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(2, 6),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					),
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
						new ColumnFeatureConfig(
							Blocks.ICE.defaultBlockState(),
							UniformInt.of(0, 4),
							HolderSet.direct()
						),
						RandomOffsetPlacement.horizontal(UniformInt.of(-1, 1)),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12)
					)
				)
			)
		);

		SMALL_DOWNWARDS_ICE_COLUMN.makeAndSetHolder(FrozenLibFeatures.DOWNWARDS_COLUMN_FEATURE,
			new ColumnFeatureConfig(
				Blocks.ICE.defaultBlockState(),
				UniformInt.of(0, 4),
				HolderSet.direct()
			)
		);

		ICE_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(Blocks.ICE)
			)
		);

		SNOW_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.SNOW_BLOCK),
				BlockStateProvider.simple(Blocks.SNOW_BLOCK),
				UniformInt.of(2, 5),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		POWDER_SNOW_DISK.makeAndSetHolder(FrozenLibFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(Blocks.POWDER_SNOW),
				BlockStateProvider.simple(Blocks.POWDER_SNOW),
				UniformInt.of(3, 8),
				0.8F,
				0.7F,
				0.325F,
				0.675F,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		HANGING_ICICLES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(DOWNWARDS_PACKED_ICE_COLUMN.getHolder()),
						0.6F
					)
				),
				PlacementUtils.inlinePlaced(DOWNWARDS_ICE_COLUMN.getHolder())
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
					)
				),
				PlacementUtils.inlinePlaced(ICE_COLUMN.getHolder())
			)
		);

		ICICLE_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(Blocks.ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.add(Blocks.BLUE_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(HANGING_ICICLES.getHolder()),
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
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(Blocks.ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.add(Blocks.BLUE_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(SMALL_DOWNWARDS_ICE_COLUMN.getHolder()),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		ICE_COLUMN_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(Blocks.ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.add(Blocks.BLUE_ICE.defaultBlockState(), 3)
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

		ICE_PATCH.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.ICE_FEATURE_REPLACEABLE,
				new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
					.add(Blocks.ICE.defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.add(Blocks.BLUE_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(SMALL_ICE_COLUMN.getHolder()),
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
