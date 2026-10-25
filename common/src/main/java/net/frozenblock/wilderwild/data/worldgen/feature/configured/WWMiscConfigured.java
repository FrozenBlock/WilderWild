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
import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.TouchingBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.BallFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallOuterRingBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.NoisePathFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleWaterloggedVegetationPatchFeature;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.data.worldgen.features.WWTreeVegetationFeatures;
import net.frozenblock.wilderwild.data.worldgen.features.WWVegetationFeatures;
import net.frozenblock.wilderwild.levelgen.feature.SnowAndFreezeDiskFeature;
import net.frozenblock.wilderwild.levelgen.feature.SnowBlanketFeature;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWConfigPredicates;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
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
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.OverlayFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WWMiscConfigured {
	public static final FrozenLibFeature EMPTY = register("empty");

	public static final FrozenLibFeature COARSE_DIRT_PATH_RARE = register("coarse_dirt_path_rare");
	public static final FrozenLibFeature GRAVEL_PATH_RARE = register("gravel_path_rare");
	public static final FrozenLibFeature STONE_PATH_RARE = register("stone_path_rare");
	public static final FrozenLibFeature COARSE_DIRT_PATH_CLEARING = register("coarse_dirt_path_clearing");
	public static final FrozenLibFeature GRAVEL_PATH_CLEARING = register("gravel_path_clearing");
	public static final FrozenLibFeature ROOTED_DIRT_PATH_CLEARING = register("rooted_dirt_path_clearing");
	public static final FrozenLibFeature STONE_DISK_AND_PILE = register("stone_disk_and_pile");

	// SWAMP
	public static final FrozenLibFeature DISK_MUD = register("disk_mud");
	public static final FrozenLibFeature MUD_PATH = register("mud_path");
	public static final FrozenLibFeature MUD_TRANSITION_DISK = register("mud_transition_disk");

	// TAIGA
	public static final FrozenLibFeature COARSE_PATH = register("coarse_dirt_path");

	// CYPRESS WETLANDS
	public static final FrozenLibFeature UNDER_WATER_SAND_PATH = register("under_water_sand_path");
	public static final FrozenLibFeature UNDER_WATER_GRAVEL_PATH = register("under_water_gravel_path");
	public static final FrozenLibFeature UNDER_WATER_CLAY_PATH = register("under_water_clay_path");
	public static final FrozenLibFeature UNDER_WATER_CLAY_PATH_BEACH = register("under_water_clay_path_beach");
	public static final FrozenLibFeature UNDER_WATER_GRAVEL_PATH_RIVER = register("under_water_gravel_path_river");

	// BEACH AND RIVER
	public static final FrozenLibFeature STONE_TRANSITION_DISK = register("stone_transition_disk");
	public static final FrozenLibFeature SMALL_SAND_TRANSITION_DISK = register("small_sand_transition_disk");
	public static final FrozenLibFeature BETA_BEACH_SAND_TRANSITION_DISK = register("beta_beach_sand_transition_disk");
	public static final FrozenLibFeature SMALL_GRAVEL_TRANSITION_DISK = register("small_gravel_transition_disk");

	// SAVANNA
	public static final FrozenLibFeature PACKED_MUD_PATH = register("packed_mud_path");

	// DESERT
	public static final RuleTest PACKED_MUD_REPLACEABLE = new TagMatchTest(WWFeatureBlockTags.PACKED_MUD_REPLACEABLE);
	public static final FrozenLibFeature ORE_PACKED_MUD = register("ore_packed_mud");
	public static final FrozenLibFeature SANDSTONE_PATH = register("sandstone_path");
	public static final FrozenLibFeature SCORCHED_SAND_DISK = register("scorched_sand");
	public static final FrozenLibFeature SCORCHED_SAND_DISK_HUGE = register("scorched_sand_huge");
	public static final FrozenLibFeature SCORCHED_SAND_DISK_LIGHTNING = register("scorched_sand_lightning");
	public static final FrozenLibFeature SAND_TRANSITION_DISK = register("sand_transition");

	// OASIS
	public static final FrozenLibFeature LAKE_OASIS = register("lake_oasis");

	// BADLANDS
	public static final FrozenLibFeature COARSE_DIRT_PATH_SMALL = register("coarse_dirt_path_small");
	public static final FrozenLibFeature PACKED_MUD_PATH_BADLANDS = register("packed_mud_path_badlands");
	public static final FrozenLibFeature SCORCHED_RED_SAND_DISK = register("scorched_red_sand");
	public static final FrozenLibFeature SCORCHED_RED_SAND_DISK_HUGE = register("scorched_red_sand_huge");
	public static final FrozenLibFeature SCORCHED_RED_SAND_DISK_LIGHTNING = register("scorched_red_sand_lightning");
	public static final FrozenLibFeature RED_SAND_TRANSITION_DISK = register("red_sand_transition");

	// OLD GROWTH SNOWY TAIGA
	public static final FrozenLibFeature SNOW = register("snow");

	// TEMPERATE RAINFOREST & RAINFOREST
	public static final FrozenLibFeature MOSS_PILE = register("moss_pile");
	public static final FrozenLibFeature BASIN_PODZOL = register("basin_podzol");
	public static final FrozenLibFeature BASIN_MOSS = register("basin_moss");
	public static final FrozenLibFeature MOSS_LAKE = register("moss_lake");

	// PALE GARDEN
	public static final FrozenLibFeature PALE_MOSS_PILE = register("pale_moss_pile");
	public static final FrozenLibFeature GRAVEL_AND_PALE_MOSS_PATH = register("gravel_and_pale_moss_path");

	// MANGROVE SWAMP
	public static final FrozenLibFeature MUD_PILE = register("mud_pile");
	public static final FrozenLibFeature BASIN_MUD = register("basin_mud");
	public static final FrozenLibFeature MUD_LAKE = register("mud_lake");

	// DYING FOREST
	public static final FrozenLibFeature COARSE_DIRT_DISK_AND_PILE = register("coarse_dirt_disk_and_pile");
	public static final FrozenLibFeature COARSE_TRANSITION_DISK = register("coarse_dirt_transition_disk");

	// SNOW
	public static final FrozenLibFeature SNOW_BLANKET = register("snow_blanket");
	public static final FrozenLibFeature SNOW_AND_ICE_TRANSITION_DISK = register("snow_and_freeze_transition_disk");

	// ICE
	public static final FrozenLibFeature BRITTLE_ICE_DISK_SURFACE = register("brittle_ice_disk_surface");

	public static void registerMiscConfigured(BootstrapContext<Feature> context) {
		WWConstants.logWithModId("Registering WWMiscConfigured for", true);

		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);
		EMPTY.makeAndSetHolder(new NoOpFeature());

		COARSE_DIRT_PATH_RARE.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.12D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
							.within(-0.2D, 0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.COARSE_PATH_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.25F)
							.build()
					).build(),
				6
			)
		);

		GRAVEL_PATH_RARE.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.12D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.within(-0.2D, 0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_PATH_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.placementChance(0.21F)
							.build()
					).build(),
				6
			)
		);

		STONE_PATH_RARE.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.12D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.STONE))
							.within(-0.2D, 0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_PATH_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.placementChance(0.215F)
							.build()
					).build(),
				6
			)
		);

		COARSE_DIRT_PATH_CLEARING.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.07D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
							.within(-0.075D, 0.175D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.COARSE_CLEARING_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.7F)
							.build()
					).build(),
				3
			)
		);

		GRAVEL_PATH_CLEARING.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.07D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.within(-0.075D, 0.175D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_CLEARING_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirWithin(2))
							.placementChance(0.7F)
							.build()
					).build(),
				3
			)
		);

		ROOTED_DIRT_PATH_CLEARING.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.07D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.ROOTED_DIRT))
							.within(-0.035D, 0.135D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.ROOTED_DIRT_PATH_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.placementChance(0.5F)
							.build()
					).build(),
				3
			)
		);

		STONE_DISK_AND_PILE.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.STONE.defaultBlockState()))),
					PlacementUtils.inlinePlaced(
						new BallFeature(
							new BallBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.STONE))
								.placementChance(0.95F)
								.fadeStartPercentage(0.8F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_TRANSITION_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.STONE))
										.placementChance(0.7F)
										.outerRingStartPercentage(0.7F)
										.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.STONE_TRANSITION_REPLACEABLE))
										.build()
								).build(),
							Optional.of(Heightmap.Types.OCEAN_FLOOR),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		DISK_MUD.makeAndSetHolder(
			new DiskFeature(
				Holder.direct(
					new RuleBasedStateProvider(
						BlockStateProvider.holderOf(Blocks.MUD),
						List.of(
							new RuleBasedStateProvider.Rule(
								BlockPredicate.not(
									BlockPredicate.anyOf(
										BlockPredicate.solid(Direction.UP),
										BlockPredicate.matchesFluids(Direction.UP, Fluids.WATER)
									)
								),
								BlockStateProvider.holderOf(Blocks.MUD)
							)
						)
					)
				),
				BlockPredicate.matchesBlocks(Blocks.DIRT, Blocks.GRASS_BLOCK),
				UniformInt.of(2, 6),
				2
			)
		);

		MUD_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.1D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.MUD))
							.within(0.23D, 1D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.MUD_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.placementChance(0.75F)
							.build()
					).build(),
				11
			)
		);

		MUD_TRANSITION_DISK.makeAndSetHolder(
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
				UniformInt.of(3, 5)
			)
		);

		COARSE_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.12D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
							.within(-0.2D, 0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.COARSE_PATH_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.65F)
							.build()
					).build(),
				11
			)
		);

		UNDER_WATER_SAND_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.05D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SAND))
							.within(0.2D, 0.54D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.UNDER_WATER_SAND_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasWaterWithin(2))
							.placementChance(0.925F)
							.build()
					).build(),
				12
			)
		);

		UNDER_WATER_GRAVEL_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.07D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.within(-0.7D, -0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.UNDER_WATER_GRAVEL_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasWaterWithin(2))
							.placementChance(0.9F)
							.build()
					).build(),
				12
			)
		);

		UNDER_WATER_CLAY_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.07D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.CLAY))
							.within(0.5D, 0.85D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.UNDER_WATER_CLAY_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasWaterWithin(2))
							.placementChance(0.9F)
							.build()
					).build(),
				12
			)
		);

		UNDER_WATER_CLAY_PATH_BEACH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.1D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.CLAY))
							.within(0.5D, 0.85D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.BEACH_CLAY_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasWaterWithin(2))
							.placementChance(0.915F)
							.build()
					).build(),
				12
			)
		);

		UNDER_WATER_GRAVEL_PATH_RIVER.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.1D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.within(0.5D, 0.85D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.RIVER_GRAVEL_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasWaterWithin(2))
							.placementChance(0.915F)
							.build()
					).build(),
				12
			)
		);

		STONE_TRANSITION_DISK.makeAndSetHolder(
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

		SMALL_SAND_TRANSITION_DISK.makeAndSetHolder(
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
						context.lookup(Registries.BIOME).getOrThrow(WWBiomeTags.HAS_SMALL_SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(6, 7)
			)
		);

		BETA_BEACH_SAND_TRANSITION_DISK.makeAndSetHolder(
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

		SMALL_GRAVEL_TRANSITION_DISK.makeAndSetHolder(
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

		PACKED_MUD_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.12D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.PACKED_MUD))
							.within(0.2D, 1D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.PACKED_MUD_PATH_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.placementChance(0.675F)
							.build()
					).build(),
				9
			)
		);

		ORE_PACKED_MUD.makeAndSetHolder(
			new OreFeature(
				PACKED_MUD_REPLACEABLE,
				Blocks.PACKED_MUD.defaultBlockState(),
				40
			)
		);

		SANDSTONE_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.2D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.SANDSTONE))
							.within(0.4D, 1D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SANDSTONE_PATH_REPLACEABLE))
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterOrLavaWithin(2))
							.placementChance(0.65F)
							.build()
					).build(),
				10
			)
		);

		SCORCHED_SAND_DISK.makeAndSetHolder(
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

		SCORCHED_SAND_DISK_HUGE.makeAndSetHolder(
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

		SCORCHED_SAND_DISK_LIGHTNING.makeAndSetHolder(
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

		SAND_TRANSITION_DISK.makeAndSetHolder(
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
						context.lookup(Registries.BIOME).getOrThrow(WWBiomeTags.HAS_SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(6, 9)
			)
		);

		LAKE_OASIS.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new LakeFeature(
							BlockStateProvider.holderOf(Blocks.WATER.defaultBlockState()),
							Holder.direct(
								RuleBasedStateProvider.builder(BlockStateProvider.of(Blocks.SANDSTONE.defaultBlockState()))
									.ifTrueThenProvide(
										ConfigPredicate.blockPredicate(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_MOSS)),
										BlockStateProvider.of(Blocks.MOSS_BLOCK.defaultBlockState())
									)
									.build()
							),
							BlockPredicate.alwaysTrue(),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
						)
					),
					PlacementUtils.inlinePlaced(
						new OverlayFeature(
							HolderSet.direct(
								PlacementUtils.inlinePlaced(
									WWTreeVegetationFeatures.PALM_OASIS.getHolder(),
									ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_PALM)),
									CountPlacement.of(UniformInt.of(6, 14)),
									OffsetPlacement.horizontal(TrapezoidInt.of(-8, 8, 0)),
									OffsetPlacement.vertical(UniformInt.of(-1, 2)),
									PlacementUtils.isEmpty()
								),
								PlacementUtils.inlinePlaced(
									WWVegetationFeatures.CACTUS_OASIS.getHolder(),
									CountPlacement.of(148),
									OffsetPlacement.horizontal(TrapezoidInt.of(-8, 8, 0)),
									OffsetPlacement.vertical(UniformInt.of(-1, 2)),
									BlockPredicateFilter.forPredicate(
										BlockPredicate.allOf(
											BlockPredicate.ONLY_IN_AIR_PREDICATE,
											BlockPredicate.wouldSurvive(Blocks.CACTUS)
										)
									)
								),
								PlacementUtils.inlinePlaced(
									WWVegetationFeatures.SHRUB_OASIS.getHolder(),
									ConfigPredicate.placementFilter(configPredicates.getOrThrow(WWConfigPredicates.GENERATE_SHRUB)),
									CountPlacement.of(196),
									OffsetPlacement.horizontal(TrapezoidInt.of(-8, 8, 0)),
									OffsetPlacement.vertical(UniformInt.of(-1, 2)),
									BlockPredicateFilter.forPredicate(
										BlockPredicate.allOf(
											BlockPredicate.replaceable(),
											BlockPredicate.noFluid(),
											BlockPredicate.wouldSurvive(WWBlocks.SHRUB.get())
										)
									)
								),
								PlacementUtils.inlinePlaced(
									WWVegetationFeatures.GRASS_OASIS.getHolder(),
									CountPlacement.of(196),
									OffsetPlacement.horizontal(TrapezoidInt.of(-8, 8, 0)),
									OffsetPlacement.vertical(UniformInt.of(-1, 2)),
									BlockPredicateFilter.forPredicate(
										BlockPredicate.allOf(
											BlockPredicate.ONLY_IN_AIR_PREDICATE,
											BlockPredicate.wouldSurvive(Blocks.SHORT_GRASS)
										)
									)
								)
							)
						)
					)
				)
			)
		);

		COARSE_DIRT_PATH_SMALL.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.CHECKED)
					.noiseScale(0.15D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.COARSE_DIRT))
							.within(0.2D, 1D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.SMALL_COARSE_DIRT_PATH_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.715F)
							.build()
					).build(),
				8
			)
		);

		PACKED_MUD_PATH_BADLANDS.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LEGACY_THREAD_SAFE)
					.noiseScale(0.7D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacement(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.PACKED_MUD))
							.within(0.2D, 1D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.PACKED_MUD_PATH_BADLANDS_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.placementChance(0.9F)
							.build()
					).build(),
				4
			)
		);

		SCORCHED_RED_SAND_DISK.makeAndSetHolder(
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

		SCORCHED_RED_SAND_DISK_HUGE.makeAndSetHolder(
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

		SCORCHED_RED_SAND_DISK_LIGHTNING.makeAndSetHolder(
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

		RED_SAND_TRANSITION_DISK.makeAndSetHolder(
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
						context.lookup(Registries.BIOME).getOrThrow(WWBiomeTags.HAS_RED_SAND_TRANSITION)
					)
					.build(),
				Optional.of(Heightmap.Types.OCEAN_FLOOR),
				UniformInt.of(6, 9)
			)
		);

		SNOW.makeAndSetHolder(
			new BlockBlobFeature(
				Blocks.SNOW_BLOCK.defaultBlockState(),
				// TODO 26.1 check if tag is correct
				BlockPredicate.matchesTag(BlockTags.FOREST_ROCK_CAN_PLACE_ON)
			)
		);

		MOSS_PILE.makeAndSetHolder(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.MOSS_BLOCK)));

		BASIN_PODZOL.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.PODZOL),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
				UniformInt.of(1, 3),
				0.35F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
			)
		);

		BASIN_MOSS.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MOSS_BLOCK),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
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

		MUD_PILE.makeAndSetHolder(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.MUD)));

		BASIN_MUD.makeAndSetHolder(
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.BASIN_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MUD),
				PlacementUtils.inlinePlaced(EMPTY.getHolder()),
				CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.8F,
				1,
				0.000F,
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

		PALE_MOSS_PILE.makeAndSetHolder(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.PALE_MOSS_BLOCK)));

		GRAVEL_AND_PALE_MOSS_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.1D)
					.heightmapType(Heightmap.Types.OCEAN_FLOOR)
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.GRAVEL))
							.within(-0.2D, 0.3D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.35F)
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.PALE_MOSS_BLOCK))
							.within(-0.21D, 0.31D)
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.GRAVEL_AND_PALE_MOSS_PATH_REPLACEABLE))
							.searchingPredicate(BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(1)))
							.placementChance(0.25F)
							.build()
					)
					.build(),
				9
			)
		);

		COARSE_DIRT_DISK_AND_PILE.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(new BlockPileFeature(BlockStateProvider.holderOf(Blocks.COARSE_DIRT.defaultBlockState()))),
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

		COARSE_TRANSITION_DISK.makeAndSetHolder(
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
		);

		SNOW_BLANKET.makeAndSetHolder(SnowBlanketFeature.INSTANCE);

		SNOW_AND_ICE_TRANSITION_DISK.makeAndSetHolder(
			new SnowAndFreezeDiskFeature(
				UniformInt.of(6, 7),
				UniformInt.of(2, 4),
				0.65F,
				0.5F
			)
		);

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


	}
}
