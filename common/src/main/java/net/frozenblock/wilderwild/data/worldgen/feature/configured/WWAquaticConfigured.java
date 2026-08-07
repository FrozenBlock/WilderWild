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
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.feature.UnderwaterVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.UnderwaterVegetationPatchWithEdgeDecorationFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.NoisePathFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.AuburnCreepingMossBlock;
import net.frozenblock.wilderwild.block.AuburnMossCarpetBlock;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.levelgen.feature.CattailFeature;
import net.frozenblock.wilderwild.levelgen.feature.HydrothermalVentFeature;
import net.frozenblock.wilderwild.levelgen.feature.SpongeBudFeature;
import net.frozenblock.wilderwild.levelgen.feature.TubeWormsFeature;
import net.frozenblock.wilderwild.levelgen.feature.WaterCoverFeature;
import net.frozenblock.wilderwild.registry.WWBlocks;
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
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.frozenblock.wilderwild.levelgen.feature.SeagrassFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;

public final class WWAquaticConfigured {
	public static final FrozenLibFeature CATTAIL = register("cattail");
	public static final FrozenLibFeature FLOWERING_WATERLILY = register("flowering_waterlily");
	public static final FrozenLibFeature PATCH_ALGAE = register("patch_algae");
	public static final FrozenLibFeature PATCH_ALGAE_SMALL = register("patch_algae_small");
	public static final FrozenLibFeature PATCH_PLANKTON = register("patch_plankton");
	public static final FrozenLibFeature SEAGRASS_MEADOW = register("seagrass_meadow");
	public static final FrozenLibFeature PATCH_BARNACLES_STRUCTURE = register("patch_barnacles_structure");
	public static final FrozenLibFeature PATCH_BARNACLES = register("patch_barnacles");
	public static final FrozenLibFeature SPONGE_BUD = register("sponge_bud");
	public static final FrozenLibFeature SEA_ANEMONE = register("sea_anemone");
	public static final FrozenLibFeature SEA_WHIP = register("sea_whip");
	public static final FrozenLibFeature TUBE_WORMS = register("tube_worms");

	public static final FrozenLibFeature HYDROTHERMAL_VENT = register("hydrothermal_vent");
	public static final FrozenLibFeature OCEAN_MOSS = register("ocean_moss");
	public static final FrozenLibFeature AUBURN_MOSS_VEGETATION_UNDERWATER = register("auburn_moss_vegetation_underwater");
	public static final FrozenLibFeature AUBURN_MOSS_PATCH_UNDERWATER = register("auburn_moss_patch_underwater");
	public static final FrozenLibFeature AUBURN_MOSS_UNDERWATER = register("auburn_moss_underwater");
	public static final FrozenLibFeature AUBURN_CREEPING_MOSS_PATCH_UNDERWATER = register("auburn_creeping_moss_patch_underwater");
	public static final FrozenLibFeature AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER = register("auburn_moss_patch_bonemeal_underwater");

	public static void registerAquaticConfigured(BootstrapContext<Feature> entries) {
		WWConstants.logWithModId("Registering WWAquaticConfigured for", true);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		CATTAIL.makeAndSetHolder(CattailFeature.INSTANCE);

		FLOWERING_WATERLILY.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.FLOWERING_LILY_PAD.get())));

		PATCH_ALGAE.makeAndSetHolder(new WaterCoverFeature(BlockStateProvider.simple(WWBlocks.ALGAE.get()), UniformInt.of(4, 10)));

		PATCH_ALGAE_SMALL.makeAndSetHolder(new WaterCoverFeature(BlockStateProvider.simple(WWBlocks.ALGAE.get()), UniformInt.of(2, 6)));

		PATCH_PLANKTON.makeAndSetHolder(new WaterCoverFeature(BlockStateProvider.simple(WWBlocks.PLANKTON.get()), UniformInt.of(2, 4)));

		SEAGRASS_MEADOW.makeAndSetHolder(new SeagrassFeature(0.025F));

		PATCH_BARNACLES_STRUCTURE.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.BARNACLES.get(),
				6,
				true,
				true,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.BARNACLES_FEATURE_PLACEABLE_STRUCTURE)
			)
		);

		PATCH_BARNACLES.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.BARNACLES.get(),
				10,
				true,
				false,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.BARNACLES_FEATURE_PLACEABLE)
			)
		);

		SPONGE_BUD.makeAndSetHolder(
			new SpongeBudFeature(
				20,
				true,
				true,
				true,
				blocks.getOrThrow(WWBlockTags.SMALL_SPONGE_GROWS_ON)
			)
		);

		SEA_ANEMONE.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.SEA_ANEMONE.get())));

		SEA_WHIP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.SEA_WHIP.get())));

		TUBE_WORMS.makeAndSetHolder(TubeWormsFeature.INSTANCE);

		HYDROTHERMAL_VENT.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new UnderwaterVegetationPatchFeature(
							blocks.getOrThrow(WWBlockTags.HYDROTHERMAL_VENT_REPLACEABLE),
							BlockStateProvider.simple(WWBlocks.GABBRO.get()),
							PlacementUtils.inlinePlaced(HydrothermalVentFeature.INSTANCE),
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
						TubeWormsFeature.INSTANCE,
						ConfigPredicate.equalTo(WWWorldgenConfig.TUBE_WORMS_GENERATION, true).asPlacementFilter(),
						CountPlacement.of(33),
						OffsetPlacement.ofTriangle(5, 4)
					)
				)
			)
		);

		OCEAN_MOSS.makeAndSetHolder(
			new NoisePathFeature(
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

		AUBURN_MOSS_VEGETATION_UNDERWATER.makeAndSetHolder(
			new SimpleBlockFeature(
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

		AUBURN_MOSS_PATCH_UNDERWATER.makeAndSetHolder(
			new UnderwaterVegetationPatchWithEdgeDecorationFeature(
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

		AUBURN_MOSS_UNDERWATER.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.5F,
						CountPlacement.of(4),
						OffsetPlacement.ofTriangle(6, 3)
					),
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.35F,
						CountPlacement.of(5),
						OffsetPlacement.ofTriangle(6, 4)
					),
					AUBURN_MOSS_PATCH_UNDERWATER.asWeightedPlacedFeature(
						0.5F,
						CountPlacement.of(3),
						OffsetPlacement.ofTriangle(3, 2)
					)
				),
				AUBURN_MOSS_PATCH_UNDERWATER.asInlinePlaced()
			)
		);

		AUBURN_CREEPING_MOSS_PATCH_UNDERWATER.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.AUBURN_CREEPING_MOSS.get(),
				10,
				true,
				true,
				true,
				0.7F,
				blocks.getOrThrow(WWBlockTags.AUBURN_CREEPING_MOSS_FEATURE_PLACEABLE)
			)
		);

		AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER.makeAndSetHolder(
			new UnderwaterVegetationPatchWithEdgeDecorationFeature(
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
