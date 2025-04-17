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
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
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
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;

public final class WWAquaticConfigured {
	public static final FrozenLibConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL = WWFeatureUtils.register("cattail");
	public static final FrozenLibConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_SMALL = WWFeatureUtils.register("cattail_small");
	public static final FrozenLibConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_MUD = WWFeatureUtils.register("cattail_mud");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERING_WATERLILY = WWFeatureUtils.register("patch_flowering_waterlily");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig, ConfiguredFeature<WaterCoverFeatureConfig, ?>> PATCH_ALGAE = WWFeatureUtils.register("patch_algae");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig, ConfiguredFeature<WaterCoverFeatureConfig, ?>> PATCH_ALGAE_SMALL = WWFeatureUtils.register("patch_algae_small");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfig, ConfiguredFeature<WaterCoverFeatureConfig, ?>> PATCH_PLANKTON = WWFeatureUtils.register("patch_plankton");
	public static final FrozenLibConfiguredFeature<ProbabilityFeatureConfiguration, ConfiguredFeature<ProbabilityFeatureConfiguration, ?>> SEAGRASS_MEADOW = WWFeatureUtils.register("seagrass_meadow");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARNACLES_DENSE = WWFeatureUtils.register("patch_barnacles_dense");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARNACLES_STRUCTURE = WWFeatureUtils.register("patch_barnacles_structure");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_BARNACLES = WWFeatureUtils.register("patch_barnacles");
	public static final FrozenLibConfiguredFeature<SpongeBudFeatureConfig, ConfiguredFeature<SpongeBudFeatureConfig, ?>> SPONGE_BUD = WWFeatureUtils.register("sponge_bud");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_SEA_ANEMONE = WWFeatureUtils.register("patch_sea_anemone");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_SEA_WHIP = WWFeatureUtils.register("patch_sea_whip");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_SEA_WHIP_SPARSE = WWFeatureUtils.register("patch_sea_whip_sparse");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_TUBE_WORMS = WWFeatureUtils.register("patch_tube_worms");

	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> HYDROTHERMAL_VENT = WWFeatureUtils.register("hydrothermal_vent");
	public static final FrozenLibConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> HYDROTHERMAL_VENT_TUBE_WORMS = WWFeatureUtils.register("hydrothermal_vent_tube_worms");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfig, ConfiguredFeature<NoisePathFeatureConfig, ?>> OCEAN_MOSS = register("ocean_moss");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration, ConfiguredFeature<SimpleBlockConfiguration, ?>> AUBURN_MOSS_VEGETATION_UNDERWATER = register("auburn_moss_vegetation_underwater");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> AUBURN_MOSS_PATCH_UNDERWATER = register("auburn_moss_patch_underwater");
	public static final FrozenLibConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> AUBURN_MOSS_UNDERWATER = register("auburn_moss_underwater");
	public static final FrozenLibConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> AUBURN_CREEPING_MOSS_PATCH_UNDERWATER = register("auburn_creeping_moss_patch_underwater");
	public static final FrozenLibConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER = register("auburn_moss_patch_bonemeal_underwater");

	private WWAquaticConfigured() {
		throw new UnsupportedOperationException("WWAquaticConfigured contains only static declarations.");
	}

	public static void registerAquaticConfigured() {
		WWConstants.logWithModId("Registering WWAquaticConfigured for", true);

		CATTAIL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 18),
				true,
				WWBlockTags.CATTAIL_FEATURE_PLACEABLE
			)
		);

		CATTAIL_SMALL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-5, 5),
				UniformInt.of(6, 12),
				true,
				WWBlockTags.CATTAIL_FEATURE_PLACEABLE
			)
		);

		CATTAIL_MUD.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 18),
				false,
				WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE
			)
		);

		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				10,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FLOWERING_LILY_PAD))
				)
			)
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

		PATCH_BARNACLES_DENSE.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				30,
				6,
				3,
				PlacementUtils.inlinePlaced(
					Feature.MULTIFACE_GROWTH,
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
					),
					BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
				)
			)
		);

		PATCH_BARNACLES_STRUCTURE.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				42,
				8,
				8,
				PlacementUtils.inlinePlaced(
					Feature.MULTIFACE_GROWTH,
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
					),
					BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
				)
			)
		);

		PATCH_BARNACLES.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				18,
				6,
				3,
				PlacementUtils.inlinePlaced(
					Feature.MULTIFACE_GROWTH,
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
					),
					BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.WATER))
				)
			)
		);

		SPONGE_BUD.makeAndSetHolder(WWFeatures.SPONGE_BUD_FEATURE,
			new SpongeBudFeatureConfig(
				20,
				true,
				true,
				true,
				WWBlockTags.SMALL_SPONGE_GROWS_ON
			)
		);

		PATCH_SEA_ANEMONE.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				12,
				6,
				3,
				PlacementUtils.inlinePlaced(
					WWFeatures.SEA_ANEMONE_FEATURE,
					new BlockStateConfiguration(WWBlocks.SEA_ANEMONE.defaultBlockState())
				)
			)
		);

		PATCH_SEA_WHIP.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				7,
				4,
				3,
				PlacementUtils.inlinePlaced(
					WWFeatures.SEA_WHIP_FEATURE,
					NoneFeatureConfiguration.INSTANCE
				)
			)
		);

		PATCH_SEA_WHIP_SPARSE.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				4,
				6,
				3,
				PlacementUtils.inlinePlaced(
					WWFeatures.SEA_WHIP_FEATURE,
					NoneFeatureConfiguration.INSTANCE
				)
			)
		);

		PATCH_TUBE_WORMS.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				12,
				3,
				4,
				PlacementUtils.inlinePlaced(
					WWFeatures.TUBE_WORMS_FEATURE,
					NoneFeatureConfiguration.INSTANCE
				)
			)
		);

		HYDROTHERMAL_VENT.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE,
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
						Feature.RANDOM_PATCH,
						new RandomPatchConfiguration(
							33,
							5,
							4,
							PlacementUtils.inlinePlaced(
								WWFeatures.TUBE_WORMS_FEATURE,
								NoneFeatureConfiguration.INSTANCE
							)
						)
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
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.OCEAN_MOSS_REPLACEABLE))
							.searchingBlockPredicate(SearchInDirectionBlockPredicate.hasWaterAbove(1))
							.placementChance(0.915F)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MOSS_BLOCK))
							.within(-0.9D, -0.4D)
							.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.OCEAN_MOSS_REPLACEABLE))
							.searchingBlockPredicate(SearchInDirectionBlockPredicate.hasWaterAbove(1))
							.placementChance(0.915F)
							.build()
					).build(),
				12
			)
		);

		AUBURN_MOSS_VEGETATION_UNDERWATER.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new WeightedStateProvider(
					SimpleWeightedRandomList.<BlockState>builder()
						.add(WWBlocks.AUBURN_CREEPING_MOSS.defaultBlockState()
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
				WWBlockTags.RED_MOSS_REPLACEABLE,
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
							Feature.RANDOM_PATCH,
							new RandomPatchConfiguration(
								4,
								6,
								3,
								PlacementUtils.inlinePlaced(AUBURN_MOSS_PATCH_UNDERWATER.getHolder())
							)
						),
						0.5F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							Feature.RANDOM_PATCH,
							new RandomPatchConfiguration(
								5,
								6,
								4,
								PlacementUtils.inlinePlaced(AUBURN_MOSS_PATCH_UNDERWATER.getHolder())
							)
						),
						0.35F
					),
					new WeightedPlacedFeature(
						PlacementUtils.inlinePlaced(
							Feature.RANDOM_PATCH,
							new RandomPatchConfiguration(
								3,
								3,
								2,
								PlacementUtils.inlinePlaced(AUBURN_MOSS_PATCH_UNDERWATER.getHolder())
							)
						),
						0.5F
					)
				),
				PlacementUtils.inlinePlaced(AUBURN_MOSS_PATCH_UNDERWATER.getHolder())
			)
		);

		AUBURN_CREEPING_MOSS_PATCH_UNDERWATER.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				38,
				6,
				4,
				PlacementUtils.inlinePlaced(
					Feature.MULTIFACE_GROWTH,
					new MultifaceGrowthConfiguration(
						WWBlocks.AUBURN_CREEPING_MOSS,
						10,
						true,
						true,
						true,
						0.7F,
						new HolderSet.Named<>(
							BuiltInRegistries.BLOCK,
							WWBlockTags.RED_CREEPING_MOSS_FEATURE_PLACEABLE
						)
					),
					BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE)
				)
			)
		);

		AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH_WITH_EDGE_DECORATION,
			new VegetationPatchConfiguration(
				WWBlockTags.RED_MOSS_REPLACEABLE,
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
