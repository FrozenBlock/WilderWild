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

import java.util.Optional;
import java.util.function.Function;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInDirectionBlockPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.TouchingBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
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
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockPileFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemClusterFeature;
import net.minecraft.world.level.levelgen.feature.SpeleothemUtils;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
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

	// ICE CAVES
	public static final FrozenLibFeature DECORATIVE_ICICLE_CLUSTER = register("decorative_icicle_cluster");
	public static final FrozenLibFeature FRAGILE_ICE_PATCH_CEILING = WWFeatureUtils.register("fragile_ice_patch_ceiling");
	public static final FrozenLibFeature FRAGILE_ICE_PATCH = WWFeatureUtils.register("fragile_ice_patch");

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
				BlockStateProvider.holderOf(Blocks.STONE),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
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
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWBlockTags.MESOGLEA_REPLACEABLE))
							.within(0.5125D, 0.5875D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()))
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get().defaultBlockState()),
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
				BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get().defaultBlockState()),
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
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
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
				BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
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
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK))
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
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))
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
				BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
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
				new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))
					.placementChance(0.9F)
					.fadeStartPercentage(0.675F)
					.replacementBlockPredicate(BlockPredicate.matchesTag(BlockTags.BASE_STONE_OVERWORLD))
					.searchingBlockPredicate(TouchingBlockPredicate.exposed())
					.outerRingBlockPlacement(
						new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))
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
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
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
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
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
					PlacementUtils.inlinePlaced(new BlockPileFeature(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))),
					PlacementUtils.inlinePlaced(
						new BallFeature(
							new BallBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))
								.placementChance(0.9F)
								.fadeStartPercentage(0.675F)
								.replacementBlockPredicate(BlockPredicate.matchesTag(WWBlockTags.MAGMA_REPLACEABLE))
								.outerRingBlockPlacement(
									new BallOuterRingBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.GABBRO.get()))
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
			BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction))
		);
		GEOTHERMAL_VENT_UP.makeAndSetHolder(geothermalVentFeature.apply(Direction.UP));
		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(geothermalVentFeature.apply(Direction.DOWN));
		GEOTHERMAL_VENT_NORTH.makeAndSetHolder(geothermalVentFeature.apply(Direction.NORTH));
		GEOTHERMAL_VENT_EAST.makeAndSetHolder(geothermalVentFeature.apply(Direction.EAST));
		GEOTHERMAL_VENT_SOUTH.makeAndSetHolder(geothermalVentFeature.apply(Direction.SOUTH));
		GEOTHERMAL_VENT_WEST.makeAndSetHolder(geothermalVentFeature.apply(Direction.WEST));

		DOWNWARDS_GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, Direction.DOWN)),
				BlockPredicate.replaceable(),
				UniformInt.of(2, 4),
				Direction.DOWN,
				true
			)
		);

		GEOTHERMAL_VENT_COLUMN.makeAndSetHolder(
			new ColumnFeature(
				BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get()),
				BlockPredicate.replaceable(),
				UniformInt.of(3, 5),
				Direction.UP,
				true
			)
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK.defaultBlockState()),
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

		// ICE CAVES
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

		FRAGILE_ICE_PATCH_CEILING.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
				PlacementUtils.inlinePlaced(
					new ColumnFeature(
						BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
						BlockPredicate.replaceable(),
						UniformInt.of(0, 4),
						Direction.DOWN,
						true
					)
				),
				CaveSurface.CEILING,
				UniformInt.of(2, 3),
				0.4F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.6F
			)
		);

		FRAGILE_ICE_PATCH.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWBlockTags.CAVE_FRAGILE_ICE_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.FRAGILE_ICE.get()),
				PlacementUtils.inlinePlaced(new NoOpFeature()),
				CaveSurface.FLOOR,
				UniformInt.of(2, 5),
				0.25F,
				4,
				0.035F,
				UniformInt.of(4, 10),
				0.4F
			)
		);
	}
}
