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
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskCarpetFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagBiomeFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathSwapUnderWaterFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathTagFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import static net.frozenblock.wilderwild.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.worldgen.impl.features.config.SnowAndIceDiskFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WWMiscConfigured {

	public static final FrozenConfiguredFeature<NoneFeatureConfiguration, ConfiguredFeature<NoneFeatureConfiguration, ?>> EMPTY = register("empty");

	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> COARSE_DIRT_PATH_RARE = register("coarse_dirt_path_rare");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> GRAVEL_PATH_RARE = register("gravel_path_rare");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> STONE_PATH_RARE = register("stone_path_rare");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> COARSE_DIRT_PATH_CLEARING = register("coarse_dirt_path_clearing");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> GRAVEL_PATH_CLEARING = register("gravel_path_clearing");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> ROOTED_DIRT_PATH_CLEARING = register("rooted_dirt_path_clearing");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> STONE_DISK_AND_PILE = register("stone_disk_and_pile");

	// SWAMP
	public static final FrozenConfiguredFeature<DiskConfiguration, ConfiguredFeature<DiskConfiguration, ?>> DISK_MUD = register("disk_mud");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> MUD_PATH = register("mud_path");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> MUD_TRANSITION_DISK = register("mud_transition_disk");

	// TAIGA
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> COARSE_PATH = register("coarse_dirt_path");

	// CYPRESS WETLANDS
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_SAND_PATH = register("under_water_sand_path");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH = register("under_water_gravel_path");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_CLAY_PATH = register("under_water_clay_path");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_CLAY_PATH_BEACH = register("under_water_clay_path_beach");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH_RIVER = register("under_water_gravel_path_river");

	// BEACH AND RIVER
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> STONE_TRANSITION_DISK = register("stone_transition_disk");
	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> SMALL_SAND_TRANSITION_DISK = register("small_sand_transition_disk");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> BETA_BEACH_SAND_TRANSITION_DISK = register("beta_beach_sand_transition_disk");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SMALL_GRAVEL_TRANSITION_DISK = register("small_gravel_transition_disk");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> RIVER_POOL = register("river_pool");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> SMALL_RIVER_POOL = register("small_river_pool");

	// SAVANNA
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> PACKED_MUD_PATH = register("packed_mud_path");

	// JUNGLE
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> MOSS_PATH = register("moss_path");

	// DESERT
	public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WWBlockTags.PACKED_MUD_REPLACEABLE);
	public static final FrozenConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_PACKED_MUD = register("ore_packed_mud");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> SANDSTONE_PATH = register("sandstone_path");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_SAND_DISK = register("scorched_sand");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_SAND_DISK_HUGE = register("scorched_sand_huge");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_SAND_DISK_LIGHTNING = register("scorched_sand_lightning");
	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> SAND_TRANSITION_DISK = register("sand_transition");

	// BADLANDS
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> COARSE_DIRT_PATH_SMALL = register("coarse_dirt_path_small");
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> PACKED_MUD_PATH_BADLANDS = register("packed_mud_path_badlands");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_RED_SAND_DISK = register("scorched_red_sand");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_RED_SAND_DISK_HUGE = register("scorched_red_sand_huge");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SCORCHED_RED_SAND_DISK_LIGHTNING = register("scorched_red_sand_lightning");
	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> RED_SAND_TRANSITION_DISK = register("red_sand_transition");

	// OASIS
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> SAND_POOL = register("sand_pool");
	public static final FrozenConfiguredFeature<LakeFeature.Configuration, ConfiguredFeature<LakeFeature.Configuration, ?>> MESSY_SAND_POOL = register("messy_sand_pool");
	public static final FrozenConfiguredFeature<PathSwapUnderWaterFeatureConfig, ConfiguredFeature<PathSwapUnderWaterFeatureConfig, ?>> GRASS_PATH = register("grass_path");
	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> MOSS_PATH_OASIS = register("moss_path_oasis");

	// ARID SAVANNA
	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> ARID_COARSE_PATH = register("arid_coarse_dirt_path");

	// OLD GROWTH SNOWY TAIGA
	public static final FrozenConfiguredFeature<BlockStateConfiguration, ConfiguredFeature<BlockStateConfiguration, ?>> SNOW = register("snow");

	// TEMPERATE RAINFOREST & RAINFOREST
	public static final FrozenConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> MOSS_PILE = register("moss_pile");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BASIN_PODZOL = register("basin_podzol");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BASIN_MOSS = register("basin_moss");
	public static final FrozenConfiguredFeature<LakeFeature.Configuration, ConfiguredFeature<LakeFeature.Configuration, ?>> MOSS_LAKE = register("moss_lake");

	// MANGROVE SWAMP
	public static final FrozenConfiguredFeature<BlockPileConfiguration, ConfiguredFeature<BlockPileConfiguration, ?>> MUD_PILE = register("mud_pile");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BASIN_MUD = register("basin_mud");
	public static final FrozenConfiguredFeature<LakeFeature.Configuration, ConfiguredFeature<LakeFeature.Configuration, ?>> MUD_LAKE = register("mud_lake");

	// DYING FOREST
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> COARSE_DIRT_DISK_AND_PILE = register("coarse_dirt_disk_and_pile");
	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> COARSE_TRANSITION_DISK = register("coarse_dirt_transition_disk");

	// MAPLE GROVE
	public static final FrozenConfiguredFeature<FadingDiskCarpetFeatureConfig, ConfiguredFeature<FadingDiskCarpetFeatureConfig, ?>> YELLOW_MAPLE_LEAF_LITTER = register("yellow_maple_leaf_litter");
	public static final FrozenConfiguredFeature<FadingDiskCarpetFeatureConfig, ConfiguredFeature<FadingDiskCarpetFeatureConfig, ?>> ORANGE_MAPLE_LEAF_LITTER = register("orange_maple_leaf_litter");
	public static final FrozenConfiguredFeature<FadingDiskCarpetFeatureConfig, ConfiguredFeature<FadingDiskCarpetFeatureConfig, ?>> RED_MAPLE_LEAF_LITTER = register("red_maple_leaf_litter");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MAPLE_LEAF_LITTER = register("maple_leaf_litter");

	// SNOW
	public static final FrozenConfiguredFeature<NoneFeatureConfiguration, ConfiguredFeature<NoneFeatureConfiguration, ?>> SNOW_BLANKET = register("snow_blanket");
	public static final FrozenConfiguredFeature<SnowAndIceDiskFeatureConfig, ConfiguredFeature<SnowAndIceDiskFeatureConfig, ?>> SNOW_AND_ICE_TRANSITION_DISK = register("snow_and_freeze_transition_disk");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SNOW_CARPET_RANDOM = register("snow_carpet_random");

	private WWMiscConfigured() {
		throw new UnsupportedOperationException("WilderMiscConfigured contains only static declarations.");
	}

	public static void registerMiscConfigured() {
		WWConstants.logWithModId("Registering WilderMiscConfigured for", true);

		EMPTY.makeAndSetHolder(Feature.NO_OP,
			NoneFeatureConfiguration.INSTANCE
		);

		COARSE_DIRT_PATH_RARE.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.COARSE_DIRT),
				6,
				3,
				0.12,
				-0.2,
				0.3,
				false,
				false,
				false,
				false,
				WWBlockTags.COARSE_PATH_REPLACEABLE,
				0.25F
			)
		);

		GRAVEL_PATH_RARE.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.GRAVEL),
				6,
				4,
				0.12,
				-0.2,
				0.3,
				false,
				false,
				false,
				false,
				WWBlockTags.GRAVEL_PATH_REPLACEABLE,
				0.21F
			)
		);

		STONE_PATH_RARE.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.STONE),
				6,
				2,
				0.12,
				-0.2,
				0.3,
				false,
				false,
				false,
				false,
				WWBlockTags.STONE_PATH_REPLACEABLE,
				0.215F
			)
		);

		COARSE_DIRT_PATH_CLEARING.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.COARSE_DIRT),
				3,
				3,
				0.07,
				-0.075,
				0.175,
				false,
				false,
				false,
				false,
				WWBlockTags.COARSE_CLEARING_REPLACEABLE,
				0.7F
			)
		);

		GRAVEL_PATH_CLEARING.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.GRAVEL),
				3,
				3,
				0.07,
				-0.075,
				0.175,
				false,
				false,
				false,
				false,
				WWBlockTags.GRAVEL_CLEARING_REPLACEABLE,
				0.7F
			)
		);

		ROOTED_DIRT_PATH_CLEARING.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.ROOTED_DIRT),
				3,
				3,
				0.07,
				-0.035,
				0.135,
				false,
				false,
				false,
				false,
				WWBlockTags.ROOTED_DIRT_PATH_REPLACEABLE,
				0.5F
			)
		);

		STONE_DISK_AND_PILE.makeAndSetHolder(FrozenFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.STONE.defaultBlockState()),
				BlockStateProvider.simple(Blocks.STONE.defaultBlockState()),
				UniformInt.of(2, 4),
				0.95F,
				0.925F,
				0.65F,
				0.8F,
				WWBlockTags.STONE_TRANSITION_REPLACEABLE,
				WWBlockTags.STONE_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		DISK_MUD.makeAndSetHolder(Feature.DISK,
			new DiskConfiguration(
				new RuleBasedBlockStateProvider(
					BlockStateProvider.simple(Blocks.MUD),
					List.of(
						new RuleBasedBlockStateProvider.Rule(
							BlockPredicate.not(
								BlockPredicate.anyOf(
									BlockPredicate.solid(Direction.UP.getNormal()),
									BlockPredicate.matchesFluids(Direction.UP.getNormal(), Fluids.WATER)
								)
							),
							BlockStateProvider.simple(Blocks.MUD)
						)
					)
				),
				BlockPredicate.matchesBlocks(
					List.of(
						Blocks.DIRT,
						Blocks.GRASS_BLOCK
					)
				),
				UniformInt.of(2, 6),
				2
			)
		);

		MUD_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.MUD),
				11,
				4,
				0.1,
				0.23,
				1,
				false,
				false,
				false,
				false,
				WWBlockTags.MUD_PATH_REPLACEABLE,
				0.75F
			)
		);

		MUD_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.MUD),
				BlockStateProvider.simple(Blocks.MUD),
				UniformInt.of(3, 5),
				0.65F,
				0.5F,
				0.5F,
				0.5F,
				WWBlockTags.MUD_TRANSITION_REPLACEABLE,
				WWBlockTags.MUD_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		COARSE_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.COARSE_DIRT),
				11,
				3,
				0.12,
				-0.2,
				0.3,
				false,
				false,
				false,
				false,
				WWBlockTags.COARSE_PATH_REPLACEABLE,
				0.65F
			)
		);

		UNDER_WATER_SAND_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_UNDER_WATER_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.SAND),
				16,
				4,
				0.05,
				0.2,
				0.54,
				true,
				true,
				false,
				false,
				WWBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE,
				0.925F
			)
		);

		UNDER_WATER_GRAVEL_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_UNDER_WATER_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.GRAVEL),
				16,
				1,
				0.07,
				-0.7,
				-0.3,
				true,
				true,
				false,
				false,
				WWBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE,
				0.9F
			)
		);

		UNDER_WATER_CLAY_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_UNDER_WATER_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.CLAY),
				16,
				3,
				0.07,
				0.5,
				0.85,
				true,
				true,
				false,
				false,
				WWBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE,
				0.9F
			)
		);

		UNDER_WATER_CLAY_PATH_BEACH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_UNDER_WATER_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.CLAY),
				14,
				2,
				0.10,
				0.5,
				0.85,
				true,
				true,
				false,
				false,
				WWBlockTags.BEACH_CLAY_PATH_REPLACEABLE,
				0.915F
			)
		);

		UNDER_WATER_GRAVEL_PATH_RIVER.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_UNDER_WATER_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.GRAVEL),
				14,
				2,
				0.10,
				0.5,
				0.85,
				true,
				true,
				false,
				false,
				WWBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE,
				0.915F
			)
		);

		STONE_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.STONE),
				BlockStateProvider.simple(Blocks.STONE),
				UniformInt.of(6, 7),
				0.65F,
				0.5F,
				0.5F,
				0.5F,
				WWBlockTags.STONE_TRANSITION_REPLACEABLE,
				WWBlockTags.STONE_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SMALL_SAND_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_EXCEPT_IN_BIOME_FEATURE,
			new FadingDiskTagBiomeFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.SAND),
				BlockStateProvider.simple(Blocks.SAND),
				UniformInt.of(6, 7),
				0.65F,
				0.5F,
				0.5F,
				0.5F,
				WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
				WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG,
				WWBiomeTags.HAS_SMALL_SAND_TRANSITION
			)
		);

		BETA_BEACH_SAND_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.SAND),
				BlockStateProvider.simple(Blocks.SAND),
				UniformInt.of(3, 5),
				0.65F,
				0.5F,
				0.5F,
				0.5F,
				WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
				WWBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SMALL_GRAVEL_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.GRAVEL),
				BlockStateProvider.simple(Blocks.GRAVEL),
				UniformInt.of(3, 5),
				0.65F,
				0.5F,
				0.5F,
				0.5F,
				WWBlockTags.GRAVEL_TRANSITION_REPLACEABLE,
				WWBlockTags.GRAVEL_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		RIVER_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH_LESS_BORDERS,
			new VegetationPatchConfiguration(
				WWBlockTags.RIVER_POOL_REPLACEABLE,
				BlockStateProvider.simple(Blocks.GRASS_BLOCK),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0.8F,
				1,
				0.000F,
				UniformInt.of(4, 8),
				0.7F
			)
		);
		SMALL_RIVER_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH_LESS_BORDERS,
			new VegetationPatchConfiguration(
				WWBlockTags.RIVER_POOL_REPLACEABLE,
				BlockStateProvider.simple(Blocks.GRASS_BLOCK),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0.8F,
				1,
				0.000F,
				UniformInt.of(1, 2),
				0.7F
			)
		);

		PACKED_MUD_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.PACKED_MUD),
				9,
				1,
				0.12,
				0.20,
				1,
				true,
				true,
				false,
				false,
				WWBlockTags.PACKED_MUD_PATH_REPLACEABLE,
				0.675F
			)
		);

		MOSS_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.MOSS_BLOCK),
				9,
				1,
				0.15,
				0.18,
				1,
				true,
				true,
				false,
				false,
				WWBlockTags.MOSS_PATH_REPLACEABLE,
				0.7F
			)
		);

		ORE_PACKED_MUD.makeAndSetHolder(Feature.ORE,
			new OreConfiguration(
				PACKED_MUD_REPLACEABLE,
				Blocks.PACKED_MUD.defaultBlockState(),
				40
			)
		);

		SANDSTONE_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.SANDSTONE),
				10,
				2,
				0.2,
				0.4,
				1,
				true,
				true,
				false,
				false,
				WWBlockTags.SANDSTONE_PATH_REPLACEABLE,
				0.65F
			)
		);

		SCORCHED_SAND_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState()),
				UniformInt.of(2, 8),
				0.95F,
				0.925F,
				0.65F,
				0.8F,
				WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SCORCHED_SAND_DISK_HUGE.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState()),
				UniformInt.of(11, 15),
				0.95F,
				0.875F,
				0.65F,
				0.8F,
				WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SCORCHED_SAND_DISK_LIGHTNING.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_SAND.defaultBlockState()),
				UniformInt.of(1, 3),
				0.85F,
				0.925F,
				0.55F,
				0.8F,
				WWBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SAND_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_EXCEPT_IN_BIOME_FEATURE,
			new FadingDiskTagBiomeFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.SAND),
				BlockStateProvider.simple(Blocks.SAND),
				UniformInt.of(7, 12),
				0.65F,
				0.875F,
				0.65F,
				0.5F,
				WWBlockTags.SAND_TRANSITION_REPLACEABLE,
				WWBlockTags.SAND_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG,
				WWBiomeTags.HAS_SAND_TRANSITION
			)
		);

		COARSE_DIRT_PATH_SMALL.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.COARSE_DIRT),
				8,
				2,
				0.15,
				0.2,
				1,
				true,
				true,
				false,
				false,
				WWBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE,
				0.715F
			)
		);

		PACKED_MUD_PATH_BADLANDS.makeAndSetHolder(FrozenFeatures.NOISE_PATH_TAG_FEATURE,
			new PathTagFeatureConfig(
				BlockStateProvider.simple(Blocks.PACKED_MUD),
				4,
				3,
				0.7,
				0.2,
				1,
				true,
				true,
				false,
				false,
				WWBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE,
				0.9F
			)
		);

		SCORCHED_RED_SAND_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState()),
				UniformInt.of(2, 8),
				0.95F,
				0.925F,
				0.65F,
				0.8F,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SCORCHED_RED_SAND_DISK_HUGE.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState()),
				UniformInt.of(11, 15),
				0.95F,
				0.875F,
				0.65F,
				0.8F,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		SCORCHED_RED_SAND_DISK_LIGHTNING.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				false,
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(WWBlockStateProperties.CRACKED, true)),
				BlockStateProvider.simple(WWBlocks.SCORCHED_RED_SAND.defaultBlockState()),
				UniformInt.of(1, 3),
				0.85F,
				0.925F,
				0.55F,
				0.8F,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE,
				WWBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		RED_SAND_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_EXCEPT_IN_BIOME_FEATURE,
			new FadingDiskTagBiomeFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.RED_SAND),
				BlockStateProvider.simple(Blocks.RED_SAND),
				UniformInt.of(7, 12),
				0.65F,
				0.875F,
				0.65F,
				0.5F,
				WWBlockTags.RED_SAND_TRANSITION_REPLACEABLE,
				WWBlockTags.RED_SAND_TRANSITION_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG,
				WWBiomeTags.HAS_RED_SAND_TRANSITION
			)
		);

		// OASIS

		SAND_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.SAND_POOL_REPLACEABLE,
				BlockStateProvider.simple(Blocks.SAND),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(5),
				0.8F,
				1,
				0.000F,
				UniformInt.of(8, 14),
				0.7F
			)
		);

		MESSY_SAND_POOL.makeAndSetHolder(Feature.LAKE,
			new LakeFeature.Configuration(
				BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
				BlockStateProvider.simple(Blocks.SAND.defaultBlockState())
			)
		);

		GRASS_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_SWAP_UNDER_WATER_FEATURE,
			new PathSwapUnderWaterFeatureConfig(
				BlockStateProvider.simple(Blocks.GRASS_BLOCK),
				BlockStateProvider.simple(Blocks.DIRT),
				11,
				4,
				0.15,
				0.4,
				1.0,
				false,
				false,
				false,
				false,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.OASIS_PATH_REPLACEABLE
				),
				0.8F
			)
		);

		MOSS_PATH_OASIS.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
			new PathFeatureConfig(
				BlockStateProvider.simple(Blocks.MOSS_BLOCK),
				9,
				2,
				0.10,
				0.12,
				1,
				true,
				true,
				false,
				false,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.OASIS_PATH_REPLACEABLE
				),
				0.725F
			)
		);

		// ARID SAVANNA

		ARID_COARSE_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
			new PathFeatureConfig(
				BlockStateProvider.simple(Blocks.COARSE_DIRT),
				12,
				3,
				0.15,
				-0.15,
				0.55,
				false,
				false,
				false,
				false,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					BlockTags.DIRT
				),
				0.825F
			)
		);

		SNOW.makeAndSetHolder(Feature.FOREST_ROCK,
			new BlockStateConfiguration(
				Blocks.SNOW_BLOCK.defaultBlockState()
			)
		);

		MOSS_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(Blocks.MOSS_BLOCK)
			)
		);

		BASIN_PODZOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.BASIN_REPLACEABLE,
				BlockStateProvider.simple(Blocks.PODZOL),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
				UniformInt.of(1, 3),
				0.7F
			)
		);

		BASIN_MOSS.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.BASIN_REPLACEABLE,
				BlockStateProvider.simple(Blocks.MOSS_BLOCK),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
				UniformInt.of(1, 3),
				0.7F
			)
		);

		MOSS_LAKE.makeAndSetHolder(Feature.LAKE,
			new LakeFeature.Configuration(
				BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
				BlockStateProvider.simple(Blocks.MOSS_BLOCK.defaultBlockState())
			)
		);

		MUD_PILE.makeAndSetHolder(Feature.BLOCK_PILE,
			new BlockPileConfiguration(
				BlockStateProvider.simple(Blocks.MUD)
			)
		);

		BASIN_MUD.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.BASIN_REPLACEABLE,
				BlockStateProvider.simple(Blocks.MUD),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
				UniformInt.of(1, 3),
				0.7F
			)
		);

		MUD_LAKE.makeAndSetHolder(Feature.LAKE,
			new LakeFeature.Configuration(
				BlockStateProvider.simple(Blocks.WATER.defaultBlockState()),
				BlockStateProvider.simple(Blocks.MUD.defaultBlockState())
			)
		);

		COARSE_DIRT_DISK_AND_PILE.makeAndSetHolder(FrozenFeatures.FADING_DISK_WITH_PILE_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.COARSE_DIRT.defaultBlockState()),
				BlockStateProvider.simple(Blocks.COARSE_DIRT.defaultBlockState()),
				UniformInt.of(2, 4),
				0.95F,
				0.925F,
				0.65F,
				0.8F,
				WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE,
				WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		COARSE_TRANSITION_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_TAG_FEATURE,
			new FadingDiskTagFeatureConfig(
				true,
				BlockStateProvider.simple(Blocks.COARSE_DIRT.defaultBlockState()),
				BlockStateProvider.simple(Blocks.COARSE_DIRT.defaultBlockState()),
				UniformInt.of(2, 4),
				0.95F,
				0.925F,
				0.65F,
				0.5F,
				WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE,
				WWBlockTags.COARSE_DIRT_DISK_REPLACEABLE,
				Heightmap.Types.OCEAN_FLOOR_WG
			)
		);

		YELLOW_MAPLE_LEAF_LITTER.makeAndSetHolder(FrozenFeatures.FADING_DISK_CARPET_FEATURE,
			new FadingDiskCarpetFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.YELLOW_MAPLE_LEAF_LITTER.defaultBlockState()),
				BlockStateProvider.simple(WWBlocks.YELLOW_MAPLE_LEAF_LITTER.defaultBlockState()),
				UniformInt.of(2, 4),
				0.75F,
				0.5F,
				0.65F,
				0.5F,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
			)
		);

		ORANGE_MAPLE_LEAF_LITTER.makeAndSetHolder(FrozenFeatures.FADING_DISK_CARPET_FEATURE,
			new FadingDiskCarpetFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.ORANGE_MAPLE_LEAF_LITTER.defaultBlockState()),
				BlockStateProvider.simple(WWBlocks.ORANGE_MAPLE_LEAF_LITTER.defaultBlockState()),
				UniformInt.of(2, 4),
				0.75F,
				0.5F,
				0.65F,
				0.5F,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
			)
		);

		RED_MAPLE_LEAF_LITTER.makeAndSetHolder(FrozenFeatures.FADING_DISK_CARPET_FEATURE,
			new FadingDiskCarpetFeatureConfig(
				true,
				BlockStateProvider.simple(WWBlocks.RED_MAPLE_LEAF_LITTER.defaultBlockState()),
				BlockStateProvider.simple(WWBlocks.RED_MAPLE_LEAF_LITTER.defaultBlockState()),
				UniformInt.of(2, 4),
				0.75F,
				0.5F,
				0.65F,
				0.5F,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES
			)
		);

		MAPLE_LEAF_LITTER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLE_LEAF_LITTER.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLE_LEAF_LITTER.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLE_LEAF_LITTER.getHolder()), 0.9F)
				),
				PlacementUtils.inlinePlaced(YELLOW_MAPLE_LEAF_LITTER.getHolder())
			)
		);

		SNOW_BLANKET.makeAndSetHolder(WWFeatures.SNOW_BLANKET_FEATURE, NoneFeatureConfiguration.INSTANCE);

		SNOW_AND_ICE_TRANSITION_DISK.makeAndSetHolder(WWFeatures.SNOW_AND_FREEZE_DISK_FEATURE,
			new SnowAndIceDiskFeatureConfig(
				UniformInt.of(6, 7),
				UniformInt.of(2, 4),
				0.65F,
				0.5F
			)
		);

		SNOW_CARPET_RANDOM.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				64,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.SNOW))
				)
			)
		);
	}
}
