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
import java.util.function.Function;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.TouchingBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.feature.CircularLavaVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.CircularWaterloggedVegetationPatchLessBordersFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.BallFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.disk.config.BallOuterRingBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.NoisePathFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.levelgen.feature.LargeMesogleaFeature;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.TrapezoidInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.RandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemFeature;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WWCaveConfigured {
	// MESOGLEA CAVES
	public static final FrozenLibFeature ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibFeature STONE_POOL = register("stone_pool");
	public static final FrozenLibFeature BLUE_MESOGLEA_COLUMN = register("blue_mesoglea_column");
	public static final FrozenLibFeature PURPLE_MESOGLEA_COLUMN = register("purple_mesoglea_column");
	public static final FrozenLibFeature DOWNWARDS_BLUE_MESOGLEA_COLUMN = register("downwards_blue_mesoglea_column");
	public static final FrozenLibFeature DOWNWARDS_PURPLE_MESOGLEA_COLUMN = register("downwards_purple_mesoglea_column");
	public static final FrozenLibFeature MESOGLEA_PATHS = register("mesoglea_paths");
	public static final FrozenLibFeature MESOGLEA_CLUSTER_PURPLE = WWFeatureUtils.register("mesoglea_cluster_purple");
	public static final FrozenLibFeature MESOGLEA_CLUSTER_BLUE = WWFeatureUtils.register("mesoglea_cluster_blue");
	public static final FrozenLibFeature DOWNWARD_BLUE_MESOGLEA = WWFeatureUtils.register("downwards_blue_mesoglea");
	public static final FrozenLibFeature DOWNWARD_PURPLE_MESOGLEA = WWFeatureUtils.register("downwards_purple_mesoglea");
	public static final FrozenLibFeature NEMATOCYST_BLUE = WWFeatureUtils.register("nematocyst_blue");
	public static final FrozenLibFeature NEMATOCYST_PURPLE = WWFeatureUtils.register("nematocyst_purple");
	public static final FrozenLibFeature LARGE_MESOGLEA_PURPLE = WWFeatureUtils.register("large_mesoglea_purple");
	public static final FrozenLibFeature LARGE_MESOGLEA_BLUE = WWFeatureUtils.register("large_mesoglea_blue");

	// MAGMATIC CAVES
	public static final FrozenLibFeature GABBRO_LAVA_POOL = register("gabbro_lava_pool");
	public static final FrozenLibFeature LAVA_POOL_MAGMA_COLUMN = register("lava_pool_magma_column");
	public static final FrozenLibFeature GABBRO_MAGMA_PATH = register("gabbro_magma_path");
	public static final FrozenLibFeature DOWNWARDS_MAGMA_COLUMN = register("downwards_magma_column");
	public static final FrozenLibFeature ORE_GABBRO = register("ore_gabbro");
	public static final FrozenLibFeature GABBRO_DISK = register("gabbro_disk");
	public static final FrozenLibFeature DOWNWARDS_GABBRO_COLUMN = register("downwards_gabbro_column");
	public static final FrozenLibFeature GABBRO_COLUMN = register("gabbro_column");
	public static final FrozenLibFeature GABBRO_PILE = register("gabbro_pile");
	public static final FrozenLibFeature GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibFeature GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibFeature GEOTHERMAL_VENT_NORTH = register("geothermal_vent_north");
	public static final FrozenLibFeature GEOTHERMAL_VENT_EAST = register("geothermal_vent_east");
	public static final FrozenLibFeature GEOTHERMAL_VENT_SOUTH = register("geothermal_vent_south");
	public static final FrozenLibFeature GEOTHERMAL_VENT_WEST = register("geothermal_vent_west");
	public static final FrozenLibFeature DOWNWARDS_GEOTHERMAL_VENT_COLUMN = register("downwards_geothermal_vent_column");
	public static final FrozenLibFeature GEOTHERMAL_VENT_COLUMN = register("geothermal_vent_column");
	public static final FrozenLibFeature UPSIDE_DOWN_MAGMA = WWFeatureUtils.register("upside_down_magma");

	// FROZEN CAVES
	public static final FrozenLibFeature ICICLE_CLUSTER = register("icicle_cluster");
	public static final FrozenLibFeature CAVE_ICICLE = register("cave_icicle");
	public static final FrozenLibFeature ICICLE = register("icicle");
	public static final FrozenLibFeature PACKED_ICE_COLUMN = register("packed_ice_column");
	public static final FrozenLibFeature DOWNWARDS_PACKED_ICE_COLUMN = register("downwards_packed_ice_column");
	public static final FrozenLibFeature PACKED_ICE_BIG_COLUMN = register("packed_ice_big_column");
	public static final FrozenLibFeature FRAGILE_ICE_BIG_COLUMN = register("fragile_ice_big_column");
	public static final FrozenLibFeature FRAGILE_ICE_DISK = register("fragile_ice_disk");
	public static final FrozenLibFeature FRAGILE_ICE_COLUMN = register("fragile_ice_column");
	public static final FrozenLibFeature SMALL_FRAGILE_ICE_COLUMN = register("small_fragile_ice_column");
	public static final FrozenLibFeature DOWNWARDS_FRAGILE_ICE_COLUMN = register("downwards_fragile_ice_column");
	public static final FrozenLibFeature FRAGILE_ICE_PILE = register("fragile_ice_pile");
	public static final FrozenLibFeature HANGING_ICE = WWFeatureUtils.register("hanging_ice");
	public static final FrozenLibFeature ICE_COLUMNS = WWFeatureUtils.register("ice_columns");
	public static final FrozenLibFeature HANGING_PACKED_ICE = WWFeatureUtils.register("hanging_packed_ice");
	public static final FrozenLibFeature ICE_PATCH_CEILING = WWFeatureUtils.register("ice_patch_ceiling");
	public static final FrozenLibFeature FRAGILE_ICE_COLUMN_PATCH = WWFeatureUtils.register("fragile_ice_column_patch");
	public static final FrozenLibFeature FRAGILE_ICE_PATCH = WWFeatureUtils.register("fragile_ice_patch");
	public static final FrozenLibFeature DIORITE_PATCH = WWFeatureUtils.register("diorite_patch");
	public static final FrozenLibFeature DIORITE_PATCH_CEILING = WWFeatureUtils.register("diorite_patch_ceiling");

	public static void registerCaveConfigured(BootstrapContext<Feature> entries) {
		WWConstants.logWithModId("Registering WWCaveConfigured for", true);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(
			new OreFeature(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				Blocks.CALCITE.defaultBlockState(),
				64
			)
		);

		STONE_POOL.makeAndSetHolder(
			new CircularWaterloggedVegetationPatchLessBordersFeature(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(Blocks.STONE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.000F,
				UniformInt.of(12, 15),
				0.7F
			)
		);

		BLUE_MESOGLEA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get())
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get())
				),
				UniformInt.of(4, 12),
				Direction.UP,
				true
			)
		);

		DOWNWARDS_BLUE_MESOGLEA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get())
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		DOWNWARDS_PURPLE_MESOGLEA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get())
				),
				UniformInt.of(3, 10),
				Direction.DOWN,
				true
			)
		);

		MESOGLEA_PATHS.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.025D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_REPLACEABLE))
							.within(0.5125D, 0.5875D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_REPLACEABLE))
							.within(-0.5875D, -0.5125D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build()
					).build(),
				12
			)
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(
			new LargeMesogleaFeature(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(
			new LargeMesogleaFeature(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		DOWNWARD_BLUE_MESOGLEA.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
				DOWNWARDS_BLUE_MESOGLEA_COLUMN.asInlinePlaced(),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		DOWNWARD_PURPLE_MESOGLEA.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
				DOWNWARDS_PURPLE_MESOGLEA_COLUMN.asInlinePlaced(),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		// TODO: see if nematocyst feature works
		NEMATOCYST_BLUE.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.PEARLESCENT_BLUE_NEMATOCYST.get(),
				20,
				true,
				true,
				true,
				0.98F,
				blocks.getOrThrow(WWBlockTags.PEARLESCENT_BLUE_NEMATOCYST_FEATURE_PLACEABLE)
			)
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST.get(),
				20,
				true,
				true,
				true,
				0.98F,
				blocks.getOrThrow(WWBlockTags.PEARLESCENT_PURPLE_NEMATOCYST_FEATURE_PLACEABLE)
			)
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(
			new LargeMesogleaFeature(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get().defaultBlockState()),
				UniformFloat.of(0.2F, 2F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1F),
				UniformFloat.of(0F, 0.3F),
				4,
				0.2F
			)
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(
			new LargeMesogleaFeature(
				blocks.getOrThrow(WWBlockTags.MESOGLEA_REPLACEABLE),
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get().defaultBlockState()),
				UniformFloat.of(0.2F, 2F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1F),
				UniformFloat.of(0F, 0.3F),
				4,
				0.2F
			)
		);

		// MAGMATIC CAVES
		GABBRO_LAVA_POOL.makeAndSetHolder(
			new CircularLavaVegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.simple(WWBlocks.GABBRO.get()),
				LAVA_POOL_MAGMA_COLUMN.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.08F,
				UniformInt.of(3, 10),
				0.7F
			)
		);

		LAVA_POOL_MAGMA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				BlockPredicate.matchesFluids(Fluids.LAVA),
				UniformInt.of(1, 8),
				Direction.UP,
				true
			)
		);

		GABBRO_MAGMA_PATH.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.XORO)
					.noiseScale(0.0325D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(Blocks.MAGMA_BLOCK))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.26D, -0.16D)
							.searchingPredicate(
								BlockPredicate.allOf(
									TouchingBlockPredicate.exposedTo(
										BlockPredicate.allOf(
											BlockPredicate.replaceable(),
											BlockPredicate.not(BlockPredicate.matchesBlocks(Blocks.WATER))
										)
									),
									BlockPredicate.not(SearchInDirectionBlockPredicate.hasWaterAbove(3))
								)
							)
							.scheduleTickOnPlacement()
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
							.within(-0.46D, -0.005D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterOrLavaWithin(2))
							.build()
					).build(),
				8
			)
		);

		DOWNWARDS_MAGMA_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(Blocks.MAGMA_BLOCK)
				),
				UniformInt.of(1, 4),
				Direction.DOWN,
				true
			)
		);

		ORE_GABBRO.makeAndSetHolder(
			new OreFeature(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				WWBlocks.GABBRO.get().defaultBlockState(),
				64
			)
		);

		GABBRO_DISK.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO))
					.placementChance(0.9F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO.get()))
							.placementChance(0.75F)
							.outerRingStartPercentage(0.75F)
							.replacementPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(8, 10)
			)
		);

		DOWNWARDS_GABBRO_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.GABBRO.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get())
				),
				UniformInt.of(1, 6),
				Direction.DOWN,
				true
			)
		);

		GABBRO_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.GABBRO.get()),
				BlockPredicate.anyOf(
					BlockPredicate.replaceable(),
					BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get())
				),
				UniformInt.of(1, 6),
				Direction.UP,
				true
			)
		);

		GABBRO_PILE.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(new BlockPileFeature(BlockStateProvider.simple(WWBlocks.GABBRO.get()))),
					PlacementUtils.inlinePlaced(
						new BallFeature(
							new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO.get()))
								.placementChance(0.9F)
								.fadeStartPercentage(0.675F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.GABBRO.get()))
										.placementChance(0.75F)
										.outerRingStartPercentage(0.75F)
										.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
										.build()
								).build(),
							Optional.empty(),
							UniformInt.of(2, 4)
						)
					)
				)
			)
		);

		final Function<Direction, SimpleBlockFeature> geothermalVentFeature = direction -> new SimpleBlockFeature(
			BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction))
		);
		GEOTHERMAL_VENT_UP.makeAndSetHolder(geothermalVentFeature.apply(Direction.UP));
		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(geothermalVentFeature.apply(Direction.DOWN));
		GEOTHERMAL_VENT_NORTH.makeAndSetHolder(geothermalVentFeature.apply(Direction.NORTH));
		GEOTHERMAL_VENT_EAST.makeAndSetHolder(geothermalVentFeature.apply(Direction.EAST));
		GEOTHERMAL_VENT_SOUTH.makeAndSetHolder(geothermalVentFeature.apply(Direction.SOUTH));
		GEOTHERMAL_VENT_WEST.makeAndSetHolder(geothermalVentFeature.apply(Direction.WEST));

		DOWNWARDS_GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN)),
				BlockPredicate.replaceable(),
				UniformInt.of(2, 4),
				Direction.DOWN,
				true
			)
		);

		GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.GEOTHERMAL_VENT.get()),
				BlockPredicate.replaceable(),
				UniformInt.of(3, 5),
				Direction.UP,
				true
			)
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				DOWNWARDS_MAGMA_COLUMN.asInlinePlaced(),
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
		ICICLE_CLUSTER.makeAndSetHolder(
			new SpeleothemClusterFeature(
				WWBlocks.FRAGILE_ICE.get().defaultBlockState(),
				WWBlocks.ICICLE.get().defaultBlockState(),
				blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
				12,
				UniformInt.of(2, 5),
				UniformInt.of(2, 6),
				1,
				3,
				UniformInt.of(2, 5),
				UniformFloat.of(0.3F, 0.7F),
				ConstantFloat.of(0F),
				0.1F,
				3,
				8
			)
		);

		CAVE_ICICLE.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new SpeleothemFeature(
							WWBlocks.FRAGILE_ICE.get().defaultBlockState(),
							WWBlocks.ICICLE.get().defaultBlockState(),
							blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
							0.2F,
							0.7F,
							0.5F,
							0.5F
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new SpeleothemFeature(
							WWBlocks.FRAGILE_ICE.get().defaultBlockState(),
							WWBlocks.ICICLE.get().defaultBlockState(),
							blocks.getOrThrow(WWBlockTags.ICICLE_REPLACEABLE),
							0.2F,
							0.7F,
							0.5F,
							0.5F
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		ICICLE.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new SpeleothemFeature(
							WWBlocks.FRAGILE_ICE.get().defaultBlockState(),
							WWBlocks.ICICLE.get().defaultBlockState(),
							HolderSet.empty(),
							0.3F,
							0.7F,
							0.5F,
							0.3F
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new SpeleothemFeature(
							WWBlocks.FRAGILE_ICE.get().defaultBlockState(),
							WWBlocks.ICICLE.get().defaultBlockState(),
							HolderSet.empty(),
							0.3F,
							0.7F,
							0.5F,
							0.3F
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		FRAGILE_ICE_DISK.makeAndSetHolder(
			new BallFeature(
				new BallBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()))
					.placementChance(0.8F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()))
							.placementChance(0.7F)
							.outerRingStartPercentage(0.5F)
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.CAVE_ICE_REPLACEABLE))
							.searchingPredicate(TouchingBlockPredicate.exposed())
							.build()
					).build(),
				Optional.empty(),
				UniformInt.of(4, 8)
			)
		);

		PACKED_ICE_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						OffsetPlacement.horizontal(TrapezoidInt.triangle(1)),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(3),
						OffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		DOWNWARDS_PACKED_ICE_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						CountPlacement.of(3),
						OffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		PACKED_ICE_BIG_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(Blocks.PACKED_ICE),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(5),
						OffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_BIG_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 9),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(5),
						OffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		FRAGILE_ICE_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 7),
							Direction.UP,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.UP,
							true
						),
						CountPlacement.of(3),
						OffsetPlacement.ofTriangle(1, 1),
						EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(1))
					)
				)
			)
		);

		SMALL_FRAGILE_ICE_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
				BlockPredicate.replaceable(),
				UniformInt.of(0, 4),
				Direction.UP,
				false
			)
		);

		DOWNWARDS_FRAGILE_ICE_COLUMN.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(2, 6),
							Direction.DOWN,
							true
						),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					),
					PlacementUtils.inlinePlaced(
						new ColumnFeature(
							BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
							BlockPredicate.replaceable(),
							UniformInt.of(0, 4),
							Direction.DOWN,
							true
						),
						CountPlacement.of(3),
						EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12),
						OffsetPlacement.vertical(ConstantInt.of(-1))
					)
				)
			)
		);

		FRAGILE_ICE_PILE.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get())));

		HANGING_ICE.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(DOWNWARDS_PACKED_ICE_COLUMN.asWeightedPlacedFeature(0.6F)),
				DOWNWARDS_FRAGILE_ICE_COLUMN.asInlinePlaced()
			)
		);

		ICE_COLUMNS.makeAndSetHolder(
			new RandomSelectorFeature(
				List.of(
					PACKED_ICE_COLUMN.asWeightedPlacedFeature(0.3F),
					PACKED_ICE_BIG_COLUMN.asWeightedPlacedFeature(0.3F),
					FRAGILE_ICE_BIG_COLUMN.asWeightedPlacedFeature(0.35F)
				),
				FRAGILE_ICE_COLUMN.asInlinePlaced()
			)
		);

		HANGING_PACKED_ICE.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				SimpleStateProvider.simple(Blocks.PACKED_ICE),
				HANGING_ICE.asInlinePlaced(),
				CaveSurface.CEILING,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.15F,
				UniformInt.of(3, 6),
				0.6F
			)
		);

		ICE_PATCH_CEILING.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.get().defaultBlockState(), 8)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 3)
					.build()
				),
				PlacementUtils.inlinePlaced(
					new ColumnFeature(
						BlockStateProvider.simple(WWBlocks.FRAGILE_ICE.get()),
						BlockPredicate.replaceable(),
						UniformInt.of(0, 4),
						Direction.DOWN,
						true
					)
				),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		FRAGILE_ICE_COLUMN_PATCH.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.get().defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				ICE_COLUMNS.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.1F,
				UniformInt.of(3, 6),
				0.6F
			)
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_ICE_REPLACEABLE),
				new WeightedStateProvider(WeightedList.<BlockState>builder()
					.add(WWBlocks.FRAGILE_ICE.get().defaultBlockState(), 5)
					.add(Blocks.PACKED_ICE.defaultBlockState(), 8)
					.build()
				),
				SMALL_FRAGILE_ICE_COLUMN.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		DIORITE_PATCH.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.DIORITE_ICE_REPLACEABLE),
				BlockStateProvider.simple(Blocks.DIORITE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.FLOOR,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.05F,
				UniformInt.of(2, 6),
				0.65F
			)
		);

		DIORITE_PATCH_CEILING.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.DIORITE_ICE_REPLACEABLE),
				BlockStateProvider.simple(Blocks.DIORITE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
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
