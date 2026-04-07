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

package net.frozenblock.wilderwild.worldgen.features.configured;

import java.util.List;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.worldgen.feature.api.block_predicate.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.worldgen.feature.api.feature.config.ComboFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.worldgen.feature.api.feature.noise_path.config.NoisePathFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.AuburnCreepingMossBlock;
import net.frozenblock.wilderwild.block.AuburnMossCarpetBlock;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils;
import static net.frozenblock.wilderwild.worldgen.features.WWFeatureUtils.register;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.CattailFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.SpongeBudFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.feature.config.WaterCoverFeatureConfig;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;

public final class WWAquaticConfigured {
	public static final FrozenLibConfiguredFeature<CattailFeatureConfig> CATTAIL = WWFeatureUtils.register("cattail");
	public static final FrozenLibConfiguredFeature<CattailFeatureConfig> CATTAIL_SMALL = WWFeatureUtils.register("cattail_small");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWERING_WATERLILY = WWFeatureUtils.register("flowering_waterlily");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig> PATCH_ALGAE = WWFeatureUtils.register("patch_algae");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig> PATCH_ALGAE_SMALL = WWFeatureUtils.register("patch_algae_small");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig> PATCH_PLANKTON = WWFeatureUtils.register("patch_plankton");
	public static final FrozenLibConfiguredFeature<ProbabilityFeatureConfiguration> SEAGRASS_MEADOW = WWFeatureUtils.register("seagrass_meadow");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> PATCH_BARNACLES_STRUCTURE = WWFeatureUtils.register("patch_barnacles_structure");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> PATCH_BARNACLES = WWFeatureUtils.register("patch_barnacles");
	public static final FrozenLibConfiguredFeature<SpongeBudFeatureConfig> SPONGE_BUD = WWFeatureUtils.register("sponge_bud");
	public static final FrozenLibConfiguredFeature<BlockStateConfiguration> SEA_ANEMONE = WWFeatureUtils.register("sea_anemone");
	public static final FrozenLibConfiguredFeature<NoneFeatureConfiguration> SEA_WHIP = WWFeatureUtils.register("sea_whip");
	public static final FrozenLibConfiguredFeature<NoneFeatureConfiguration> TUBE_WORMS = WWFeatureUtils.register("tube_worms");

	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> HYDROTHERMAL_VENT = WWFeatureUtils.register("hydrothermal_vent");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig> HYDROTHERMAL_VENT_TUBE_WORMS = WWFeatureUtils.register("hydrothermal_vent_tube_worms");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfig> OCEAN_MOSS = register("ocean_moss");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> AUBURN_MOSS_VEGETATION_UNDERWATER = register("auburn_moss_vegetation_underwater");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> AUBURN_MOSS_PATCH_UNDERWATER = register("auburn_moss_patch_underwater");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration> AUBURN_MOSS_UNDERWATER = register("auburn_moss_underwater");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> AUBURN_CREEPING_MOSS_PATCH_UNDERWATER = register("auburn_creeping_moss_patch_underwater");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration> AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER = register("auburn_moss_patch_bonemeal_underwater");

	private WWAquaticConfigured() {
		throw new UnsupportedOperationException("WWAquaticConfigured contains only static declarations.");
	}

	public static void registerAquaticConfigured(BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		WWConstants.logWithModId("Registering WWAquaticConfigured for", true);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		CATTAIL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				6,
				UniformInt.of(24, 32),
				blocks.getOrThrow(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			)
		);

		CATTAIL_SMALL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				4,
				UniformInt.of(10, 20),
				blocks.getOrThrow(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			)
		);

		FLOWERING_WATERLILY.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FLOWERING_LILY_PAD))
		);

		PATCH_ALGAE.makeAndSetHolder(WWFeatures.WATER_COVER_FEATURE,
			new WaterCoverFeatureConfig(BlockStateProvider.simple(WWBlocks.ALGAE), UniformInt.of(4, 10))
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WWFeatures.WATER_COVER_FEATURE,
			new WaterCoverFeatureConfig(BlockStateProvider.simple(WWBlocks.ALGAE), UniformInt.of(2, 6))
		);

		PATCH_PLANKTON.makeAndSetHolder(WWFeatures.WATER_COVER_FEATURE,
			new WaterCoverFeatureConfig(BlockStateProvider.simple(WWBlocks.PLANKTON), UniformInt.of(2, 4))
		);

		SEAGRASS_MEADOW.makeAndSetHolder(Feature.SEAGRASS,
			new ProbabilityFeatureConfiguration(0.025F)
		);

		PATCH_BARNACLES_STRUCTURE.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.BARNACLES,
				6,
				true,
				true,
				true,
				0.7F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.BARNACLES_FEATURE_PLACEABLE_STRUCTURE
				)
			)
		);

		PATCH_BARNACLES.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.BARNACLES,
				10,
				true,
				false,
				true,
				0.7F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK,
					WWBlockTags.BARNACLES_FEATURE_PLACEABLE
				)
			)
		);

		SPONGE_BUD.makeAndSetHolder(WWFeatures.SPONGE_BUD_FEATURE,
			new SpongeBudFeatureConfig(
				20,
				true,
				true,
				true,
				blocks.getOrThrow(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			)
		);

		SEA_ANEMONE.makeAndSetHolder(WWFeatures.SEA_ANEMONE_FEATURE,
			new BlockStateConfiguration(WWBlocks.SEA_ANEMONE.defaultBlockState())
		);

		SEA_WHIP.makeAndSetHolder(WWFeatures.SEA_WHIP_FEATURE,
			NoneFeatureConfiguration.INSTANCE
		);

		TUBE_WORMS.makeAndSetHolder(WWFeatures.TUBE_WORMS_FEATURE,
			NoneFeatureConfiguration.INSTANCE
		);

		HYDROTHERMAL_VENT.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.GABBRO),
				PlacementUtils.inlinePlaced(
					WWFeatures.HYDROTHERMAL_VENT_FEATURE,
					NoneFeatureConfiguration.INSTANCE
				),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.375F,
				6,
				0.25F,
				UniformInt.of(1, 2),
				0.5F
			)
		);

		HYDROTHERMAL_VENT_TUBE_WORMS.makeAndSetHolder(FrozenLibFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(HYDROTHERMAL_VENT.getHolder()),
					PlacementUtils.inlinePlaced(
						WWFeatures.TUBE_WORMS_FEATURE,
						NoneFeatureConfiguration.INSTANCE,
						CountPlacement.of(33),
						RandomOffsetPlacement.ofTriangle(5, 4)
					)
				)
			)
		);

		OCEAN_MOSS.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH_FEATURE,
			new NoisePathFeatureConfig(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.1D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR_WG)
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MOSS_BLOCK))
							.within(0.4D, 0.9D)
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.OCEAN_MOSS_REPLACEABLE))
							.searchingPredicate(SearchInDirectionBlockPredicate.hasWaterAbove(1))
							.placementChance(0.915F)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MOSS_BLOCK))
							.within(-0.9D, -0.4D)
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.OCEAN_MOSS_REPLACEABLE))
							.searchingPredicate(SearchInDirectionBlockPredicate.hasWaterAbove(1))
							.placementChance(0.915F)
							.build()
					).build(),
				12
			)
		);

		AUBURN_MOSS_VEGETATION_UNDERWATER.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(
							WWBlocks.AUBURN_CREEPING_MOSS.defaultBlockState()
								.setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)
								.setValue(AuburnCreepingMossBlock.WATERLOGGED, true),
							3
						)
						.add(WWBlocks.AUBURN_MOSS_CARPET.defaultBlockState().setValue(AuburnMossCarpetBlock.WATERLOGGED, true), 1)
						.add(WWBlocks.SEA_WHIP.defaultBlockState(), 1)
				)
			)
		);

		AUBURN_MOSS_PATCH_UNDERWATER.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH_WITH_EDGE_DECORATION,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.AUBURN_MOSS_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.AUBURN_MOSS_BLOCK),
				PlacementUtils.inlinePlaced(AUBURN_MOSS_VEGETATION_UNDERWATER.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0F,
				5,
				0.475F,
				UniformInt.of(1, 2),
				0.55F
			)
		);

		AUBURN_MOSS_UNDERWATER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							AUBURN_MOSS_PATCH_UNDERWATER.getHolder(),
							CountPlacement.of(4),
							RandomOffsetPlacement.ofTriangle(6, 3)
						),
						0.5F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							AUBURN_MOSS_PATCH_UNDERWATER.getHolder(),
							CountPlacement.of(5),
							RandomOffsetPlacement.ofTriangle(6, 4)
						),
						0.35F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							AUBURN_MOSS_PATCH_UNDERWATER.getHolder(),
							CountPlacement.of(3),
							RandomOffsetPlacement.ofTriangle(3, 2)
						),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(AUBURN_MOSS_PATCH_UNDERWATER.getHolder())
			)
		);

		AUBURN_CREEPING_MOSS_PATCH_UNDERWATER.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.AUBURN_CREEPING_MOSS,
				10,
				true,
				true,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.AUBURN_CREEPING_MOSS_FEATURE_PLACEABLE)
			)
		);

		AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH_WITH_EDGE_DECORATION,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.AUBURN_MOSS_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.AUBURN_MOSS_BLOCK),
				PlacementUtils.inlinePlaced(AUBURN_MOSS_VEGETATION_UNDERWATER.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0F,
				5,
				0.475F,
				UniformInt.of(1, 2),
				0.75F
			)
		);
	}
}
