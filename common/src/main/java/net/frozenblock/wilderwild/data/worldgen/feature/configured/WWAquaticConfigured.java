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
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibConfiguredFeature;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeatures;
import net.frozenblock.lib.levelgen.feature.api.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoisePathFeatureConfiguration;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.AuburnCreepingMossBlock;
import net.frozenblock.wilderwild.block.AuburnMossCarpetBlock;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.levelgen.feature.configuration.CattailFeatureConfiguration;
import net.frozenblock.wilderwild.levelgen.feature.configuration.SpongeBudFeatureConfiguration;
import net.frozenblock.wilderwild.levelgen.feature.configuration.WaterCoverFeatureConfiguration;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
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
import net.minecraft.world.level.levelgen.feature.configurations.CompositeFeatureConfiguration;
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
	public static final FrozenLibConfiguredFeature<CattailFeatureConfiguration> CATTAIL = register("cattail");
	public static final FrozenLibConfiguredFeature<CattailFeatureConfiguration> CATTAIL_SMALL = register("cattail_small");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> FLOWERING_WATERLILY = register("flowering_waterlily");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfiguration> PATCH_ALGAE = register("patch_algae");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfiguration> PATCH_ALGAE_SMALL = register("patch_algae_small");
	public static final FrozenLibConfiguredFeature<WaterCoverFeatureConfiguration> PATCH_PLANKTON = register("patch_plankton");
	public static final FrozenLibConfiguredFeature<ProbabilityFeatureConfiguration> SEAGRASS_MEADOW = register("seagrass_meadow");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> PATCH_BARNACLES_STRUCTURE = register("patch_barnacles_structure");
	public static final FrozenLibConfiguredFeature<MultifaceGrowthConfiguration> PATCH_BARNACLES = register("patch_barnacles");
	public static final FrozenLibConfiguredFeature<SpongeBudFeatureConfiguration> SPONGE_BUD = register("sponge_bud");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SEA_ANEMONE = register("sea_anemone");
	public static final FrozenLibConfiguredFeature<SimpleBlockConfiguration> SEA_WHIP = register("sea_whip");
	public static final FrozenLibConfiguredFeature<NoneFeatureConfiguration> TUBE_WORMS = register("tube_worms");

	public static final FrozenLibConfiguredFeature<CompositeFeatureConfiguration> HYDROTHERMAL_VENT = register("hydrothermal_vent");
	public static final FrozenLibConfiguredFeature<NoisePathFeatureConfiguration> OCEAN_MOSS = register("ocean_moss");
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

		CATTAIL.makeAndSetHolder(WWFeatures.CATTAIL,
			new CattailFeatureConfiguration(
				6,
				UniformInt.of(24, 32),
				blocks.getOrThrow(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			)
		);

		CATTAIL_SMALL.makeAndSetHolder(WWFeatures.CATTAIL,
			new CattailFeatureConfiguration(
				4,
				UniformInt.of(10, 20),
				blocks.getOrThrow(WWBlockTags.CATTAIL_FEATURE_PLACEABLE)
			)
		);

		FLOWERING_WATERLILY.makeAndSetHolder(Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FLOWERING_LILY_PAD.get()))
		);

		PATCH_ALGAE.makeAndSetHolder(WWFeatures.WATER_COVER,
			new WaterCoverFeatureConfiguration(BlockStateProvider.simple(WWBlocks.ALGAE.get()), UniformInt.of(4, 10))
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WWFeatures.WATER_COVER,
			new WaterCoverFeatureConfiguration(BlockStateProvider.simple(WWBlocks.ALGAE.get()), UniformInt.of(2, 6))
		);

		PATCH_PLANKTON.makeAndSetHolder(WWFeatures.WATER_COVER,
			new WaterCoverFeatureConfiguration(BlockStateProvider.simple(WWBlocks.PLANKTON.get()), UniformInt.of(2, 4))
		);

		SEAGRASS_MEADOW.makeAndSetHolder(Feature.SEAGRASS,
			new ProbabilityFeatureConfiguration(0.025F)
		);

		PATCH_BARNACLES_STRUCTURE.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.BARNACLES.get(),
				6,
				true,
				true,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.BARNACLES_FEATURE_PLACEABLE_STRUCTURE)
			)
		);

		PATCH_BARNACLES.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.BARNACLES.get(),
				10,
				true,
				false,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.BARNACLES_FEATURE_PLACEABLE)
			)
		);

		SPONGE_BUD.makeAndSetHolder(WWFeatures.SPONGE_BUD,
			new SpongeBudFeatureConfiguration(
				20,
				true,
				true,
				true,
				blocks.getOrThrow(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			)
		);

		SEA_ANEMONE.makeAndSetHolder(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.SEA_ANEMONE.get())));

		SEA_WHIP.makeAndSetHolder(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.SEA_WHIP.get())));

		TUBE_WORMS.makeAndSetHolder(WWFeatures.TUBE_WORMS, NoneFeatureConfiguration.INSTANCE);

		HYDROTHERMAL_VENT.makeAndSetHolder(Feature.SEQUENCE,
			new CompositeFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH,
						new VegetationPatchConfiguration(
							blocks.getOrThrow(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE),
							BlockStateProvider.simple(WWBlocks.GABBRO.get()),
							PlacementUtils.inlinePlaced(
								WWFeatures.HYDROTHERMAL_VENT,
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
					),
					PlacementUtils.inlinePlaced(
						WWFeatures.TUBE_WORMS,
						NoneFeatureConfiguration.INSTANCE,
						ConfigPredicate.equalTo(WWWorldgenConfig.TUBE_WORMS_GENERATION, true).asPlacementFilter(),
						CountPlacement.of(33),
						RandomOffsetPlacement.ofTriangle(5, 4)
					)
				)
			)
		);

		OCEAN_MOSS.makeAndSetHolder(FrozenLibFeatures.NOISE_PATH,
			new NoisePathFeatureConfiguration(
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
							WWBlocks.AUBURN_CREEPING_MOSS.get().defaultBlockState()
								.setValue(MultifaceBlock.getFaceProperty(Direction.DOWN), true)
								.setValue(AuburnCreepingMossBlock.WATERLOGGED, true),
							3
						)
						.add(WWBlocks.AUBURN_MOSS_CARPET.get().defaultBlockState().setValue(AuburnMossCarpetBlock.WATERLOGGED, true), 1)
						.add(WWBlocks.SEA_WHIP.get().defaultBlockState(), 1)
				)
			)
		);

		AUBURN_MOSS_PATCH_UNDERWATER.makeAndSetHolder(FrozenLibFeatures.UNDERWATER_VEGETATION_PATCH_WITH_EDGE_DECORATION,
			new VegetationPatchConfiguration(
				blocks.getOrThrow(WWBlockTags.AUBURN_MOSS_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.AUBURN_MOSS_BLOCK.get()),
				AUBURN_MOSS_VEGETATION_UNDERWATER.asInlinePlaced(),
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
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.5F,
						CountPlacement.of(4),
						RandomOffsetPlacement.ofTriangle(6, 3)
					),
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.35F,
						CountPlacement.of(5),
						RandomOffsetPlacement.ofTriangle(6, 4)
					),
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.5F,
						CountPlacement.of(3),
						RandomOffsetPlacement.ofTriangle(3, 2)
					)
				),
				AUBURN_MOSS_PATCH_UNDERWATER.asInlinePlaced()
			)
		);

		AUBURN_CREEPING_MOSS_PATCH_UNDERWATER.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.AUBURN_CREEPING_MOSS.get(),
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
				BlockStateProvider.simple(WWBlocks.AUBURN_MOSS_BLOCK.get()),
				AUBURN_MOSS_VEGETATION_UNDERWATER.asInlinePlaced(),
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
