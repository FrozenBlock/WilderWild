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

package net.frozenblock.wilderwild.data.worldgen.features;

import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleWaterloggedVegetationPatchFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.levelgen.feature.CattailFeature;
import net.frozenblock.wilderwild.levelgen.feature.SpongeBudFeature;
import net.frozenblock.wilderwild.levelgen.feature.TubeWormsFeature;
import net.frozenblock.wilderwild.levelgen.feature.VentStackFeature;
import net.frozenblock.wilderwild.registry.WWBlockStateProviders;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;

public final class WWAquaticFeatures {
	public static final FrozenLibFeature RIVER_POOL = register("river_pool");
	public static final FrozenLibFeature SMALL_RIVER_POOL = register("small_river_pool");
	public static final FrozenLibFeature CATTAIL = register("cattail");
	public static final FrozenLibFeature FLOWERING_WATER_LILY = register("flowering_water_lily");
	public static final FrozenLibFeature ALGAE = register("algae");
	public static final FrozenLibFeature PLANKTON = register("plankton");
	public static final FrozenLibFeature SEAGRASS_VERY_SHORT = register("seagrass_very_short");
	public static final FrozenLibFeature BARNACLES = register("barnacles");
	public static final FrozenLibFeature BARNACLES_STRUCTURE = register("barnacles_structure");
	public static final FrozenLibFeature SPONGE_BUD = register("sponge_bud");
	public static final FrozenLibFeature SEA_ANEMONE = register("sea_anemone");
	public static final FrozenLibFeature SEA_WHIP = register("sea_whip");
	public static final FrozenLibFeature TUBE_WORMS = register("tube_worms");
	public static final FrozenLibFeature HYDROTHERMAL_VENT = register("hydrothermal_vent");
	public static final FrozenLibFeature AUBURN_MOSS_VEGETATION_UNDERWATER = register("auburn_moss_vegetation_underwater");
	public static final FrozenLibFeature AUBURN_MOSS_PATCH_UNDERWATER = register("auburn_moss_patch_underwater");
	public static final FrozenLibFeature PATCH_AUBURN_MOSS_PATCH_UNDERWATER = register("patch_auburn_moss_patch_underwater");
	public static final FrozenLibFeature AUBURN_CREEPING_MOSS_PATCH_UNDERWATER = register("auburn_creeping_moss_patch_underwater");
	public static final FrozenLibFeature AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER = register("auburn_moss_patch_bonemeal_underwater");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		registerRiverPool(RIVER_POOL, 4, 8, blocks.getOrThrow(WWFeatureBlockTags.RIVER_POOL_REPLACEABLE));
		registerRiverPool(SMALL_RIVER_POOL, 1, 2, blocks.getOrThrow(WWFeatureBlockTags.RIVER_POOL_REPLACEABLE));

		CATTAIL.makeAndSetHolder(CattailFeature.INSTANCE);

		FLOWERING_WATER_LILY.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FLOWERING_LILY_PAD.get())));

		ALGAE.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.ALGAE.get())));

		PLANKTON.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.PLANKTON.get())));

		SEAGRASS_VERY_SHORT.makeAndSetHolder(AquaticFeatures.seagrass(2));

		registerBarnacles(BARNACLES, 10, blocks.getOrThrow(WWFeatureBlockTags.BARNACLES_PLACEABLE));
		registerBarnacles(BARNACLES_STRUCTURE, 6, blocks.getOrThrow(WWFeatureBlockTags.BARNACLES_PLACEABLE_UNNATURAL));

		SPONGE_BUD.makeAndSetHolder(
			new SpongeBudFeature(
				20,
				true,
				true,
				true,
				blocks.getOrThrow(WWFeatureBlockTags.SPONGE_BUD_PLACEABLE)
			)
		);

		SEA_ANEMONE.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.SEA_ANEMONE.get())));

		SEA_WHIP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.SEA_WHIP.get())));

		TUBE_WORMS.makeAndSetHolder(TubeWormsFeature.INSTANCE);

		final PlacementFilter generateTubeWorms = ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_TUBE_WORMS));
		HYDROTHERMAL_VENT.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new FlexibleVegetationPatchFeature(
							blocks.getOrThrow(WWFeatureBlockTags.HYDROTHERMAL_VENT_REPLACEABLE),
							BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
							PlacementUtils.inlinePlaced(
								new VentStackFeature(
									BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get()),
									BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
									BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
									CaveSurface.FLOOR,
									UniformInt.of(0, 3),
									blocks.getOrThrow(WWFeatureBlockTags.HYDROTHERMAL_VENT_REPLACEABLE)
								)
							),
							CaveSurface.FLOOR,
							ConstantInt.of(2),
							0.375F,
							6,
							0.25F,
							UniformInt.of(1, 2),
							0.5F,
							new FlexibleVegetationPatchFeature.FlexibleComponents(
								Optional.of(BlockPredicate.matchesBlocks(Blocks.WATER)),
								false,
								false,
								true
							)
						)
					),
					TUBE_WORMS.asInlinePlaced(
						generateTubeWorms,
						CountPlacement.of(33),
						OffsetPlacement.ofTriangle(5, 4)
					)
				)
			)
		);

		AUBURN_MOSS_VEGETATION_UNDERWATER.makeAndSetHolder(
			new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.AUBURN_MOSS_VEGETATION_UNDERWATER))
		);

		AUBURN_MOSS_PATCH_UNDERWATER.makeAndSetHolder(
			new FlexibleVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.AUBURN_MOSS_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.AUBURN_MOSS_BLOCK.get()),
				AUBURN_MOSS_VEGETATION_UNDERWATER.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0F,
				5,
				0.475F,
				UniformInt.of(1, 2),
				0.55F,
				new FlexibleVegetationPatchFeature.FlexibleComponents(
					Optional.of(BlockPredicate.matchesBlocks(Blocks.WATER)),
					true,
					false,
					true
				)
			)
		);

		PATCH_AUBURN_MOSS_PATCH_UNDERWATER.makeAndSetHolder(
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
				blocks.getOrThrow(WWFeatureBlockTags.AUBURN_CREEPING_MOSS_PLACEABLE)
			)
		);

		AUBURN_MOSS_PATCH_BONEMEAL_UNDERWATER.makeAndSetHolder(
			new FlexibleVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.AUBURN_MOSS_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.AUBURN_MOSS_BLOCK.get()),
				AUBURN_MOSS_VEGETATION_UNDERWATER.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0F,
				5,
				0.475F,
				UniformInt.of(1, 2),
				0.75F,
				new FlexibleVegetationPatchFeature.FlexibleComponents(
					Optional.of(BlockPredicate.matchesBlocks(Blocks.WATER)),
					true,
					false,
					true
				)
			)
		);
	}

	private static void registerRiverPool(FrozenLibFeature feature, int minSize, int maxSize, HolderSet<Block> replaceable) {
		feature.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				replaceable,
				BlockStateProvider.holderOf(Blocks.GRASS_BLOCK),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				ConstantInt.of(1),
				0.8F,
				1,
				0F,
				UniformInt.of(minSize, maxSize),
				0.35F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR_NO_BASE_BLOCK
			)
		);
	}

	private static void registerBarnacles(FrozenLibFeature feature, int searchRange, HolderSet<Block> placeable) {
		feature.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.BARNACLES.get(),
				searchRange,
				true,
				false,
				true,
				0.7F,
				placeable
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("aquatic/" + id));
	}

	private WWAquaticFeatures() {}
}
