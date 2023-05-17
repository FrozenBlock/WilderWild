/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenFeatures;
import net.frozenblock.lib.worldgen.feature.api.features.FadingDiskTagExceptInBiomeFeature;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagBiomeFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.FadingDiskTagFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathSwapUnderWaterFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PathTagFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.PillarFeatureConfig;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import static net.frozenblock.wilderwild.world.additions.feature.WilderFeatureUtils.register;
import net.frozenblock.wilderwild.world.generation.features.config.SnowAndIceDiskFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import static net.minecraft.data.worldgen.features.OreFeatures.NATURAL_STONE;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DiskConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WilderMiscConfigured {
	private WilderMiscConfigured() {
		throw new UnsupportedOperationException("WilderMiscConfigured contains only static declarations.");
	}
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

	// BEACH AND RIVER

	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_CLAY_PATH_BEACH = register("under_water_clay_path_beach");

	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> UNDER_WATER_GRAVEL_PATH_RIVER = register("under_water_gravel_path_river");

	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> STONE_TRANSITION_DISK = register("stone_transition_disk");

	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> SMALL_SAND_TRANSITION_DISK = register("small_sand_transition_disk");

	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> BETA_BEACH_SAND_TRANSITION_DISK = register("beta_beach_sand_transition_disk");

	public static final FrozenConfiguredFeature<FadingDiskTagFeatureConfig, ConfiguredFeature<FadingDiskTagFeatureConfig, ?>> SMALL_GRAVEL_TRANSITION_DISK = register("small_gravel_transition_disk");

	// SAVANNA
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> PACKED_MUD_PATH = register("packed_mud_path");

	// JUNGLE
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> MOSS_PATH = register("moss_path");

	// DESERT
	public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WilderBlockTags.PACKED_MUD_REPLACEABLE);

	public static final FrozenConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_PACKED_MUD = register("ore_packed_mud");

	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> SANDSTONE_PATH = register("sandstone_path");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_SAND_DISK = register("scorched_sand");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_SAND_DISK_HUGE = register("scorched_sand_huge");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_SAND_DISK_LIGHTNING = register("scorched_sand_lightning");

	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> SAND_TRANSITION_DISK = register("sand_transition");

	// BADLANDS
	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> COARSE_DIRT_PATH_SMALL = register("coarse_dirt_path_small");

	public static final FrozenConfiguredFeature<PathTagFeatureConfig, ConfiguredFeature<PathTagFeatureConfig, ?>> PACKED_MUD_PATH_BADLANDS = register("packed_mud_path_badlands");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_RED_SAND_DISK = register("scorched_red_sand");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_RED_SAND_DISK_HUGE = register("scorched_red_sand_huge");

	public static final FrozenConfiguredFeature<FadingDiskFeatureConfig, ConfiguredFeature<FadingDiskFeatureConfig, ?>> SCORCHED_RED_SAND_DISK_LIGHTNING = register("scorched_red_sand_lightning");

	public static final FrozenConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ConfiguredFeature<FadingDiskTagBiomeFeatureConfig, ?>> RED_SAND_TRANSITION_DISK = register("red_sand_transition");

	// JELLYFISH CAVES
	public static final FrozenConfiguredFeature<OreConfiguration, ConfiguredFeature<OreConfiguration, ?>> ORE_CALCITE = register("ore_calcite");

	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> BLANK_SHUT_UP = register("blank_shut_up");

	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> STONE_POOL = register("stone_pool");

	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> DEEPSLATE_POOL = register("deepslate_pool");

	public static final FrozenConfiguredFeature<PillarFeatureConfig, ConfiguredFeature<PillarFeatureConfig, ?>> UPWARDS_MESOGLEA_PILLAR = register("blue_mesoglea_pillar");

	public static final FrozenConfiguredFeature<PillarFeatureConfig, ConfiguredFeature<PillarFeatureConfig, ?>> PURPLE_MESOGLEA_PILLAR = register("purple_mesoglea_pillar");

	public static final FrozenConfiguredFeature<PillarFeatureConfig, ConfiguredFeature<PillarFeatureConfig, ?>> DOWNWARDS_MESOGLEA_PILLAR = register("downwards_blue_mesoglea_pillar");

	public static final FrozenConfiguredFeature<PillarFeatureConfig, ConfiguredFeature<PillarFeatureConfig, ?>> DOWNWARDS_PURPLE_MESOGLEA_PILLAR = register("downwards_purple_mesoglea_pillar");

	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> BLUE_MESOGLEA_PATH = register("blue_mesoglea_path");

	public static final FrozenConfiguredFeature<PathFeatureConfig, ConfiguredFeature<PathFeatureConfig, ?>> PURPLE_MESOGLEA_PATH = register("purple_mesoglea_path");

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

	//SNOW
	public static final FrozenConfiguredFeature<NoneFeatureConfiguration, ConfiguredFeature<NoneFeatureConfiguration, ?>> SNOW_BLANKET = register("snow_blanket");

	public static final FrozenConfiguredFeature<SnowAndIceDiskFeatureConfig, ConfiguredFeature<SnowAndIceDiskFeatureConfig, ?>> SNOW_AND_ICE_TRANSITION_DISK = register("snow_and_freeze_transition_disk");

	static {
		registerMiscConfigured();
	}

	public static void init() {
    }

	public static void registerMiscConfigured() {

		WilderSharedConstants.logWild("Registering WilderMiscConfigured for", true);

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
						WilderBlockTags.MUD_PATH_REPLACEABLE
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
						WilderBlockTags.MUD_TRANSITION_REPLACEABLE,
						WilderBlockTags.MUD_TRANSITION_REPLACEABLE,
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
						WilderBlockTags.COARSE_PATH_REPLACEABLE
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
						WilderBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE
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
						WilderBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE
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
						WilderBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE
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
						WilderBlockTags.BEACH_CLAY_PATH_REPLACEABLE
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
						WilderBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE
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
						WilderBlockTags.STONE_TRANSITION_REPLACEABLE,
						WilderBlockTags.STONE_TRANSITION_REPLACEABLE,
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
						WilderBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
						WilderBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
						Heightmap.Types.OCEAN_FLOOR_WG,
						WilderBiomeTags.HAS_SMALL_SAND_TRANSITION
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
						WilderBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
						WilderBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE,
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
						WilderBlockTags.GRAVEL_TRANSITION_REPLACEABLE,
						WilderBlockTags.GRAVEL_TRANSITION_REPLACEABLE,
						Heightmap.Types.OCEAN_FLOOR_WG
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
						WilderBlockTags.PACKED_MUD_PATH_REPLACEABLE
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
						WilderBlockTags.MOSS_PATH_REPLACEABLE
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
						WilderBlockTags.SANDSTONE_PATH_REPLACEABLE
				)
		);

		SCORCHED_SAND_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						true,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState()),
						UniformInt.of(2, 8),
						0.95F,
						0.925F,
						0.65F,
						0.8F,
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder()
						),
						Heightmap.Types.OCEAN_FLOOR_WG
				)
		);

		SCORCHED_SAND_DISK_HUGE.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						true,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState()),
						UniformInt.of(12, 24),
						0.95F,
						0.875F,
						0.65F,
						0.8F,
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder()
						),
						Heightmap.Types.OCEAN_FLOOR_WG
				)
		);

		SCORCHED_SAND_DISK_LIGHTNING.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						false,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_SAND.defaultBlockState()),
						UniformInt.of(1, 3),
						0.85F,
						0.925F,
						0.55F,
						0.8F,
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder()
						),
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
						WilderBlockTags.SAND_TRANSITION_REPLACEABLE,
						WilderBlockTags.SAND_TRANSITION_REPLACEABLE,
						Heightmap.Types.OCEAN_FLOOR_WG,
						WilderBiomeTags.HAS_SAND_TRANSITION
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
						WilderBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE
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
						WilderBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE
				)
		);

		SCORCHED_RED_SAND_DISK.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						true,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState()),
						UniformInt.of(2, 8),
						0.95F,
						0.925F,
						0.65F,
						0.8F,
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_RED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder()
						),
						Heightmap.Types.OCEAN_FLOOR_WG
				)
		);

		SCORCHED_RED_SAND_DISK_HUGE.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						true,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState()),
						UniformInt.of(12, 24),
						0.95F,
						0.875F,
						0.65F,
						0.8F,
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_RED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder()
						),
						Heightmap.Types.OCEAN_FLOOR_WG
				)
		);

		SCORCHED_RED_SAND_DISK_LIGHTNING.makeAndSetHolder(FrozenFeatures.FADING_DISK_FEATURE,
				new FadingDiskFeatureConfig(
						false,
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState().setValue(RegisterProperties.CRACKEDNESS, true)),
						BlockStateProvider.simple(RegisterBlocks.SCORCHED_RED_SAND.defaultBlockState()),
						UniformInt.of(1, 3),
						0.85F,
						0.925F,
						0.55F,
						0.8F,
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder(),
								RegisterBlocks.SCORCHED_RED_SAND.builtInRegistryHolder()
						),
						HolderSet.direct(
								Blocks.RED_SAND.builtInRegistryHolder()
						),
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
						WilderBlockTags.RED_SAND_TRANSITION_REPLACEABLE,
						WilderBlockTags.RED_SAND_TRANSITION_REPLACEABLE,
						Heightmap.Types.OCEAN_FLOOR_WG,
						WilderBiomeTags.HAS_RED_SAND_TRANSITION
				)
		);

		ORE_CALCITE.makeAndSetHolder(Feature.ORE,
				new OreConfiguration(
						NATURAL_STONE,
						Blocks.CALCITE.defaultBlockState(),
						64
				)
		);

		BLANK_SHUT_UP.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
				new SimpleRandomFeatureConfiguration(
						HolderSet.direct(
								PlacementUtils.inlinePlaced(
										Feature.SIMPLE_BLOCK,
										new SimpleBlockConfiguration(
												new SimpleStateProvider(Blocks.WATER.defaultBlockState())
										)
								)
						)
				)
		);

		STONE_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
				new VegetationPatchConfiguration(
						BlockTags.LUSH_GROUND_REPLACEABLE,
						BlockStateProvider.simple(Blocks.STONE),
						PlacementUtils.inlinePlaced(BLANK_SHUT_UP.getHolder()),
						CaveSurface.FLOOR,
						ConstantInt.of(4),
						0.8F,
						2,
						0.000F,
						UniformInt.of(12, 15),
						0.7F
				)
		);

		DEEPSLATE_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
				new VegetationPatchConfiguration(
						BlockTags.LUSH_GROUND_REPLACEABLE,
						BlockStateProvider.simple(Blocks.DEEPSLATE),
						PlacementUtils.inlinePlaced(BLANK_SHUT_UP.getHolder()),
						CaveSurface.FLOOR,
						ConstantInt.of(4),
						0.8F,
						2,
						0.000F,
						UniformInt.of(12, 15),
						0.7F
				)
		);

		UPWARDS_MESOGLEA_PILLAR.makeAndSetHolder(FrozenFeatures.UPWARDS_PILLAR_FEATURE,
				new PillarFeatureConfig(
						RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
						UniformInt.of(4, 12),
						HolderSet.direct(
								RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								Blocks.WATER.builtInRegistryHolder()
						)
				)
		);

		PURPLE_MESOGLEA_PILLAR.makeAndSetHolder(FrozenFeatures.UPWARDS_PILLAR_FEATURE,
				new PillarFeatureConfig(
						RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
						UniformInt.of(4, 12),
						HolderSet.direct(
								RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								Blocks.WATER.builtInRegistryHolder()
						)
				)
		);

		DOWNWARDS_MESOGLEA_PILLAR.makeAndSetHolder(FrozenFeatures.DOWNWARDS_PILLAR_FEATURE,
				new PillarFeatureConfig(
						RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
						UniformInt.of(3, 10),
						HolderSet.direct(
								RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								Blocks.WATER.builtInRegistryHolder()
						)
				)
		);

		DOWNWARDS_PURPLE_MESOGLEA_PILLAR.makeAndSetHolder(FrozenFeatures.DOWNWARDS_PILLAR_FEATURE,
				new PillarFeatureConfig(
						RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true),
						UniformInt.of(3, 10),
						HolderSet.direct(
								RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.builtInRegistryHolder(),
								Blocks.WATER.builtInRegistryHolder()
						)
				)
		);

		BLUE_MESOGLEA_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
				new PathFeatureConfig(
						BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
						14,
						1,
						0.025,
						0.5125,
						0.5875,
						true,
						true,
						true,
						false,
						HolderSet.direct(
								Block::builtInRegistryHolder,
								Blocks.CLAY,
								Blocks.STONE,
								Blocks.ANDESITE,
								Blocks.DIORITE,
								Blocks.GRANITE,
								Blocks.DRIPSTONE_BLOCK,
								Blocks.CALCITE,
								Blocks.TUFF,
								Blocks.DEEPSLATE
						)
				)
		);

		PURPLE_MESOGLEA_PATH.makeAndSetHolder(FrozenFeatures.NOISE_PATH_FEATURE,
				new PathFeatureConfig(
						BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
						14,
						1,
						0.025,
						-0.5875,
						-0.5125,
						true,
						true,
						true,
						false,
						HolderSet.direct(
								Block::builtInRegistryHolder,
								Blocks.CLAY,
								Blocks.STONE,
								Blocks.ANDESITE,
								Blocks.DIORITE,
								Blocks.GRANITE,
								Blocks.DRIPSTONE_BLOCK,
								Blocks.CALCITE,
								Blocks.TUFF,
								Blocks.DEEPSLATE
						)
				)
		);

		// OASIS

		SAND_POOL.makeAndSetHolder(FrozenFeatures.CIRCULAR_WATERLOGGED_VEGETATION_PATCH,
				new VegetationPatchConfiguration(
						WilderBlockTags.SAND_POOL_REPLACEABLE,
						BlockStateProvider.simple(Blocks.SAND),
						PlacementUtils.inlinePlaced(BLANK_SHUT_UP.getHolder()),
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
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder(),
								Blocks.SANDSTONE.builtInRegistryHolder()
						)
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
						HolderSet.direct(
								Blocks.SAND.builtInRegistryHolder()
						)
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
						HolderSet.direct(
								Blocks.DIRT.builtInRegistryHolder(),
								Blocks.GRASS_BLOCK.builtInRegistryHolder()
						)
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
						WilderBlockTags.BASIN_REPLACEABLE,
						BlockStateProvider.simple(Blocks.PODZOL),
						PlacementUtils.inlinePlaced(BLANK_SHUT_UP.getHolder()),
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
						WilderBlockTags.BASIN_REPLACEABLE,
						BlockStateProvider.simple(Blocks.MOSS_BLOCK),
						PlacementUtils.inlinePlaced(BLANK_SHUT_UP.getHolder()),
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

		SNOW_BLANKET.makeAndSetHolder(WilderWild.SNOW_BLANKET_FEATURE, NoneFeatureConfiguration.INSTANCE);

		SNOW_AND_ICE_TRANSITION_DISK.makeAndSetHolder(WilderWild.SNOW_AND_FREEZE_DISK_FEATURE,
				new SnowAndIceDiskFeatureConfig(
						UniformInt.of(6, 7),
						UniformInt.of(2, 4),
						0.65F,
						0.5F
				)
		);
	}
}
