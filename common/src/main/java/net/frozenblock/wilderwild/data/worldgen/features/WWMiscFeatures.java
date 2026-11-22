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

import java.util.Optional;
import lombok.experimental.UtilityClass;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.TouchingBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnWithDiskFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.BallFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallOuterRingBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleWaterloggedVegetationPatchFeature;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.levelgen.feature.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.levelgen.feature.SnowBlanketFeature;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.frozenblock.wilderwild.tag.WWMiscFeatureBiomeTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockBlobFeature;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.DiskFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.OverlayFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@UtilityClass
public final class WWMiscFeatures {
	// TERMITE MOUND
	public static final FrozenLibFeature TERMITE_MOUND = register("termite_mound");

	// COARSE DIRT
	public static final FrozenLibFeature COARSE_DIRT_BLOB_AND_PATCH = register("coarse_dirt_blob_and_patch");

	// PODZOL
	public static final FrozenLibFeature PODZOL_BASIN = register("podzol_basin");

	// MUD
	public static final FrozenLibFeature MUD_BASIN = register("mud_basin");
	public static final FrozenLibFeature MUD_LAKE = register("mud_lake");
	public static final FrozenLibFeature MUD_TRANSITION = register("mud_transition");

	// CLAY
	public static final FrozenLibFeature DISK_CLAY_LARGE = register("disk_clay_large");

	// MOSS
	public static final FrozenLibFeature MOSS_BLOB = register("moss_blob");
	public static final FrozenLibFeature MOSS_BASIN = register("moss_basin");
	public static final FrozenLibFeature MOSS_LAKE = register("moss_lake");

	// PALE MOSS
	public static final FrozenLibFeature PALE_MOSS_BLOB = register("pale_moss_blob");

	// GRAVEL
	public static final FrozenLibFeature GRAVEL_TRANSITION = register("gravel_transition");

	// STONE
	public static final FrozenLibFeature STONE_TRANSITION = register("stone_transition");

	// SAND
	public static final FrozenLibFeature SCORCHED_SAND = register("scorched_sand");
	public static final FrozenLibFeature SCORCHED_SAND_HUGE = register("scorched_sand_huge");
	public static final FrozenLibFeature SCORCHED_SAND_LIGHTNING = register("scorched_sand_lightning");
	public static final FrozenLibFeature SAND_TRANSITION = register("sand_transition");
	public static final FrozenLibFeature SMALL_SAND_TRANSITION = register("small_sand_transition");
	public static final FrozenLibFeature BETA_BEACH_SAND_TRANSITION = register("beta_beach_sand_transition");

	// OASIS
	public static final FrozenLibFeature LAKE_OASIS = register("lake_oasis");
	public static final FrozenLibFeature PATCH_PALM_LAKE_OASIS = register("patch_palm_lake_oasis");
	public static final FrozenLibFeature PATCH_CACTUS_LAKE_OASIS = register("patch_cactus_lake_oasis");
	public static final FrozenLibFeature PATCH_SHRUB_LAKE_OASIS = register("patch_shrub_lake_oasis");
	public static final FrozenLibFeature PATCH_GRASS_LAKE_OASIS = register("patch_grass_lake_oasis");

	// RED SAND
	public static final FrozenLibFeature SCORCHED_RED_SAND = register("scorched_red_sand");
	public static final FrozenLibFeature SCORCHED_RED_SAND_HUGE = register("scorched_red_sand_huge");
	public static final FrozenLibFeature SCORCHED_RED_SAND_LIGHTNING = register("scorched_red_sand_lightning");
	public static final FrozenLibFeature RED_SAND_TRANSITION = register("red_sand_transition");

	// SNOW
	public static final FrozenLibFeature SNOW_BLOB = register("snow_blob");
	public static final FrozenLibFeature SNOW_BLANKET = register("snow_blanket");
	public static final FrozenLibFeature SNOW_AND_ICE_TRANSITION = register("snow_and_freeze_transition");

	// ICE
	public static final FrozenLibFeature BRITTLE_ICE_DISK_SURFACE = register("brittle_ice_disk_surface");
	public static final FrozenLibFeature DECORATIVE_ICICLE_CLUSTER = register("decorative_icicle_cluster");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// TERMITE MOUND
		TERMITE_MOUND.makeAndSetHolder(
			new ColumnWithDiskFeature(
				BlockStateProvider.holderOf(WWBlocks.TERMITE_MOUND.get().defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true)),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				blocks.getOrThrow(WWFeatureBlockTags.TERMITE_MOUND_DISK_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.COARSE_DIRT)
			)
		);

		// COARSE DIRT
		COARSE_DIRT_BLOB_AND_PATCH.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new BlockBlobFeature(
							Blocks.COARSE_DIRT.defaultBlockState(),
							BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)
						)
					),
					PlacementUtils.inlinePlaced(
						new BallFeature(
							new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
								.placementChance(0.95F)
								.fadeStartPercentage(0.5F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.COARSE_DIRT_DISK_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
										.placementChance(0.875F)
										.outerRingStartPercentage(0.7F)
										.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.COARSE_DIRT_DISK_REPLACEABLE))
										.build()
								).build(),
							Optional.of(Heightmap.Types.OCEAN_FLOOR),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		// PODZOL
		PODZOL_BASIN.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.PODZOL),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0F,
				UniformInt.of(1, 3),
				0.35F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
			)
		);

		// MUD
		MUD_BASIN.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MUD),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0F,
				UniformInt.of(1, 3),
				0.35F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
			)
		);

		MUD_LAKE.makeAndSetHolder(
			new LakeFeature(
				BlockStateProvider.holderOf(Blocks.WATER.defaultBlockState()),
				BlockStateProvider.holderOf(Blocks.MUD.defaultBlockState()),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
			)
		);

		MUD_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.MUD))
					.placementChance(0.65F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.MUD_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.MUD))
							.placementChance(0.5F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.MUD_TRANSITION_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(3, 4)
			)
		);

		// CLAY
		DISK_CLAY_LARGE.makeAndSetHolder(
			new DiskFeature(
				BlockStateProvider.holderOf(Blocks.CLAY),
				BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK),
				UniformInt.of(2, 4),
				2
			)
		);

		// MOSS
		MOSS_BLOB.makeAndSetHolder(
			new BlockBlobFeature(
				Blocks.MOSS_BLOCK.defaultBlockState(),
				BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)
			)
		);

		MOSS_BASIN.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MOSS_BLOCK),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0F,
				UniformInt.of(1, 3),
				0.35F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
			)
		);

		MOSS_LAKE.makeAndSetHolder(
			new LakeFeature(
				BlockStateProvider.holderOf(Blocks.WATER.defaultBlockState()),
				BlockStateProvider.holderOf(Blocks.MOSS_BLOCK.defaultBlockState()),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SUBSTRATE_OVERWORLD)),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
				BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
			)
		);

		// PALE MOSS
		PALE_MOSS_BLOB.makeAndSetHolder(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.PALE_MOSS_BLOCK)));

		// GRAVEL
		GRAVEL_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
					.placementChance(0.65F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.placementChance(0.5F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_TRANSITION_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(4, 5)
			)
		);

		// STONE
		STONE_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.STONE))
					.placementChance(0.65F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.STONE))
							.placementChance(0.5F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_TRANSITION_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(6, 7)
			)
		);

		// SAND
		SCORCHED_SAND.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.7F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(2, 8)
			)
		);

		SCORCHED_SAND_HUGE.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.7F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(11, 15)
			)
		);

		SCORCHED_SAND_LIGHTNING.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.6F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SCORCHED_SAND_FEATURE_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(1, 3)
			)
		);

		SAND_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
					.placementChance(0.65F)
					.fadeStartPercentage(0.5F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SAND_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
							.placementChance(0.6F)
							.outerRingStartPercentage(0.4F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SAND_TRANSITION_REPLACEABLE))
							.build()
					).excludedBiomes(
						context.lookup(Registries.BIOME).getOrThrow(WWMiscFeatureBiomeTags.SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(4, 6)
			)
		);

		SMALL_SAND_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
					.placementChance(0.65F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
							.placementChance(0.75F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE))
							.build()
					).excludedBiomes(
						context.lookup(Registries.BIOME).getOrThrow(WWMiscFeatureBiomeTags.SMALL_SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(6, 7)
			)
		);

		BETA_BEACH_SAND_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
					.placementChance(0.65F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
							.placementChance(0.5F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SMALL_SAND_TRANSITION_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(4, 6)
			)
		);

		// OASIS
		LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new LakeFeature(
							BlockStateProvider.holderOf(Blocks.WATER.defaultBlockState()),
							BlockStateProvider.holderOf(Blocks.CLAY.defaultBlockState()),
							BlockPredicate.alwaysTrue(),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
						)
					),
					PlacementUtils.inlinePlaced(
						new OverlayFeature(
							HolderSet.direct(
								PATCH_PALM_LAKE_OASIS.asInlinePlaced(
									CountPlacement.of(UniformInt.of(3, 6)),
									OffsetPlacement.ofTriangle(3, 1)
								),
								PATCH_CACTUS_LAKE_OASIS.asInlinePlaced(
									CountPlacement.of(UniformInt.of(2, 5)),
									OffsetPlacement.ofTriangle(3, 1)
								),
								PATCH_SHRUB_LAKE_OASIS.asInlinePlaced(
									CountPlacement.of(UniformInt.of(2, 8)),
									OffsetPlacement.ofTriangle(3, 1)
								),
								PATCH_GRASS_LAKE_OASIS.asInlinePlaced(
									CountPlacement.of(UniformInt.of(3, 9)),
									OffsetPlacement.ofTriangle(4, 1)
								)
							)
						)
					)
				)
			)
		);

		PATCH_PALM_LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWTreeVegetationFeatures.PALM_DESERT.getHolder(),
						ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALM)),
						CountPlacement.of(UniformInt.of(1, 3)),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(
							BlockPredicate.allOf(
								BlockPredicate.ONLY_IN_AIR_PREDICATE,
								BlockPredicate.wouldSurvive(WWBlocks.COCONUT.get())
							)
						)
					)
				)
			)
		);

		PATCH_CACTUS_LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWVegetationFeatures.CACTUS_OASIS.getHolder(),
						CountPlacement.of(32),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(
							BlockPredicate.allOf(
								BlockPredicate.ONLY_IN_AIR_PREDICATE,
								BlockPredicate.wouldSurvive(Blocks.CACTUS)
							)
						)
					)
				)
			)
		);

		PATCH_SHRUB_LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWVegetationFeatures.SHRUB_DESERT.getHolder(),
						ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SHRUB)),
						CountPlacement.of(32),
						OffsetPlacement.ofTriangle(6, 2),
						BlockPredicateFilter.forPredicate(
							BlockPredicate.allOf(
								BlockPredicate.replaceable(),
								BlockPredicate.noFluid(),
								BlockPredicate.wouldSurvive(WWBlocks.SHRUB.get())
							)
						)
					)
				)
			)
		);

		PATCH_GRASS_LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						WWVegetationFeatures.GRASS_OASIS.getHolder(),
						CountPlacement.of(72),
						OffsetPlacement.ofTriangle(7, 2),
						BlockPredicateFilter.forPredicate(
							BlockPredicate.allOf(
								BlockPredicate.ONLY_IN_AIR_PREDICATE,
								BlockPredicate.wouldSurvive(Blocks.SHORT_GRASS)
							)
						)
					)
				)
			)
		);

		// RED SAND
		SCORCHED_RED_SAND.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.7F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(2, 8)
			)
		);

		SCORCHED_RED_SAND_HUGE.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.7F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE))
							.build()
					).build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(11, 15)
			)
		);

		SCORCHED_RED_SAND_LIGHTNING.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
					.placementChance(0.95F)
					.fadeStartPercentage(0.8F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_INNER_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.SCORCHED_RED_SAND.get()))
							.placementChance(0.895F)
							.outerRingStartPercentage(0.6F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SCORCHED_SAND_FEATURE_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(1, 3)
			)
		);

		RED_SAND_TRANSITION.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.RED_SAND))
					.placementChance(0.65F)
					.fadeStartPercentage(0.5F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SAND_TRANSITION_REPLACEABLE))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.RED_SAND))
							.placementChance(0.6F)
							.outerRingStartPercentage(0.4F)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RED_SAND_TRANSITION_REPLACEABLE))
							.build()
					).excludedBiomes(
						context.lookup(Registries.BIOME).getOrThrow(WWMiscFeatureBiomeTags.RED_SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(4, 6)
			)
		);

		// SNOW
		SNOW_BLOB.makeAndSetHolder(
			new BlockBlobFeature(
				Blocks.SNOW_BLOCK.defaultBlockState(),
				BlockPredicate.matchesTag(BlockTags.FOREST_ROCK_CAN_PLACE_ON)
			)
		);

		SNOW_BLANKET.makeAndSetHolder(SnowBlanketFeature.INSTANCE);

		SNOW_AND_ICE_TRANSITION.makeAndSetHolder(
			new SnowAndFreezeDiskFeature(
				UniformInt.of(6, 7),
				UniformInt.of(2, 4),
				0.65F,
				0.5F
			)
		);

		// ICE
		BRITTLE_ICE_DISK_SURFACE.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.BRITTLE_ICE))
					.placementChance(0.85F)
					.fadeStartPercentage(0.75F)
					.replacementBlockPredicate(
						BlockPredicate.anyOf(
							BlockPredicate.replaceable(),
							BlockPredicate.matchesBlocks(Blocks.ICE)
						)
					)
					.searchingBlockPredicate(BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.WATER))
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.BRITTLE_ICE))
							.placementChance(0.75F)
							.outerRingStartPercentage(0.675F)
							.replacementPredicate(
								BlockPredicate.anyOf(
									BlockPredicate.replaceable(),
									BlockPredicate.matchesBlocks(Blocks.ICE)
								)
							)
							.searchingPredicate(BlockPredicate.matchesBlocks(Direction.DOWN, Blocks.WATER))
							.build()
					).build(),
				Optional.of(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
				UniformInt.of(3, 4)
			)
		);

		DECORATIVE_ICICLE_CLUSTER.makeAndSetHolder(
			new SpeleothemClusterFeature(
				Blocks.PACKED_ICE.defaultBlockState(),
				Blocks.ICICLE.defaultBlockState(),
				HolderSet.empty(),
				12,
				BiasedToBottomInt.of(1, 2),
				ConstantInt.of(3),
				1,
				2,
				BiasedToBottomInt.of(1, 2),
				UniformFloat.of(0.1F, 0.25F),
				ConstantFloat.ZERO,
				0.1F,
				2,
				5,
				new SpeleothemClusterFeature.PlacementOptions(
					SpeleothemClusterFeature.PlacementMode.FLOOR_AND_CEILING, SpeleothemUtils.BaseBlockTransformer.SET_ATTACHED, false
				)
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("misc/" + id));
	}
}
