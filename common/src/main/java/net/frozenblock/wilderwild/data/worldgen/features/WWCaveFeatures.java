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
import java.util.function.BiFunction;
import java.util.function.Function;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.CircularLavaVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.CircularWaterloggedVegetationPatchLessBordersFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.NoisePathFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.math.api.EasyNoiseSampler;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.GeothermalVentBlock;
import net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils;
import net.frozenblock.wilderwild.data.worldgen.feature.configured.WWMiscConfigured;
import net.frozenblock.wilderwild.levelgen.feature.VentStackFeature;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.ReplaceablePredicate;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LargeSpeleothemFeature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;

public final class WWCaveFeatures {
	// MESOGLEA CAVES
	public static final FrozenLibFeature ORE_CALCITE = register("ore_calcite");
	public static final FrozenLibFeature POOL_STONE = register("pool_stone");
	public static final FrozenLibFeature PATHS_MESOGLEA = register("path_mesoglea");
	public static final FrozenLibFeature PATCH_MESOGLEA = register("patch_mesoglea");
	public static final FrozenLibFeature PATCH_MESOGLEA_CEILING = register("patch_mesoglea_ceiling");
	public static final FrozenLibFeature MEDIUM_MESOGLEA = register("medium_mesoglea");
	public static final FrozenLibFeature LARGE_MESOGLEA = register("large_mesoglea");
	public static final FrozenLibFeature NEMATOCYST = WWFeatureUtils.register("nematocyst");

	// MAGMATIC CAVES
	public static final FrozenLibFeature POOL_LAVA_GABBRO = register("pool_lava_gabbro");
	public static final FrozenLibFeature PATCH_MAGMA_CEILING = WWFeatureUtils.register("patch_magma_ceiling");

	public static final FrozenLibFeature PATCH_GABBRO = register("patch_gabbro");
	public static final FrozenLibFeature PATCH_GABBRO_CEILING = register("patch_gabbro_ceiling");

	public static final FrozenLibFeature GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibFeature GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibFeature GEOTHERMAL_VENT_NORTH_SOUTH = register("geothermal_vent_north_south");
	public static final FrozenLibFeature GEOTHERMAL_VENT_EAST_WEST = register("geothermal_vent_east_west");
	public static final FrozenLibFeature GEOTHERMAL_VENT_STACK = register("geothermal_vent_stack");
	public static final FrozenLibFeature GEOTHERMAL_VENT_STACK_CEILING = register("geothermal_vent_stack_ceiling");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// MESOGLEA CAVES
		ORE_CALCITE.makeAndSetHolder(
			new OreFeature(
				new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD),
				Blocks.CALCITE.defaultBlockState(),
				64
			)
		);

		POOL_STONE.makeAndSetHolder(
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

		PATHS_MESOGLEA.makeAndSetHolder(
			new NoisePathFeature(
				new NoiseBandPlacement.Builder(EasyNoiseSampler.NoiseType.LOCAL)
					.noiseScale(0.025D)
					.calculateNoiseWithY()
					.scaleYNoise()
					.noiseBandBlockPlacements(
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.MESOGLEA_AND_NEMATOCYST_BASE_BLOCKS))
							.within(0.5125D, 0.5875D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build(),
						new NoiseBandBlockPlacement.Builder(BlockStateProvider.holderOf(WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get()))
							.replacementPredicate(BlockPredicate.matchesTag(WWFeatureBlockTags.MESOGLEA_AND_NEMATOCYST_BASE_BLOCKS))
							.within(-0.5875D, -0.5125D)
							.searchingPredicate(SearchInAreaBlockPredicate.hasAirOrWaterWithin(2))
							.build()
					).build(),
				12
			)
		);


		registerMesogleaPatch(context, PATCH_MESOGLEA, WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get(), WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get(), false);
		registerMesogleaPatch(context, PATCH_MESOGLEA_CEILING, WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get(), WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get(), true);

		registerLargeMesoglea(context, MEDIUM_MESOGLEA, WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get(), WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get(), false);
		registerLargeMesoglea(context, LARGE_MESOGLEA, WWBlocks.PEARLESCENT_BLUE_MESOGLEA.get(), WWBlocks.PEARLESCENT_PURPLE_MESOGLEA.get(), true);
		registerNematocyst(
			context,
			NEMATOCYST,
			WWBlocks.PEARLESCENT_BLUE_NEMATOCYST.get(), WWBlocks.PEARLESCENT_PURPLE_NEMATOCYST.get(),
			WWFeatureBlockTags.NEMATOCYST_PLACEABLE_PEARLESCENT_BLUE, WWFeatureBlockTags.NEMATOCYST_PLACEABLE_PEARLESCENT_PURPLE
		);

		// MAGMATIC CAVES
		POOL_LAVA_GABBRO.makeAndSetHolder(
			new CircularLavaVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
				PlacementUtils.inlinePlaced(
					new BlockColumnFeature(
						List.of(
							BlockColumnFeature.layer(
								UniformInt.of(1, 8),
								BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK)
							)
						),
						Direction.UP,
						BlockPredicate.matchesFluids(Fluids.LAVA),
						false
					)
				),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.08F,
				UniformInt.of(3, 10),
				0.7F
			)
		);

		PATCH_MAGMA_CEILING.makeAndSetHolder(
			new VegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
				PlacementUtils.inlinePlaced(
					new BlockColumnFeature(
						List.of(
							BlockColumnFeature.layer(
								UniformInt.of(1, 3),
								BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK)
							)
						),
						Direction.DOWN,
						BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
						false
					)
				),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(2, 6),
				0.7F
			)
		);

		registerGabbroPatch(context, PATCH_GABBRO, false);
		registerGabbroPatch(context, PATCH_GABBRO_CEILING, true);

		final Function<Direction, SimpleBlockFeature> geothermalVentFeature = direction -> new SimpleBlockFeature(
			BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction))
		);
		GEOTHERMAL_VENT_UP.makeAndSetHolder(geothermalVentFeature.apply(Direction.UP));
		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(geothermalVentFeature.apply(Direction.DOWN));
		registerHorizontalGeothermalVent(context, GEOTHERMAL_VENT_NORTH_SOUTH, Direction.Axis.Z);
		registerHorizontalGeothermalVent(context, GEOTHERMAL_VENT_EAST_WEST, Direction.Axis.X);

		registerGeothermalVentStack(context, GEOTHERMAL_VENT_STACK, false);
		registerGeothermalVentStack(context, GEOTHERMAL_VENT_STACK_CEILING, true);
	}

	private static void registerMesogleaPatch(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		Block pearlescentBlue, Block pearlescentPurple,
		boolean ceiling
	) {
		final Function<Block, Feature> vegetationPatch = block -> new VegetationPatchFeature(
			context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.MESOGLEA_AND_NEMATOCYST_BASE_BLOCKS),
			BlockStateProvider.holderOf(block),
			PlacementUtils.inlinePlaced(
				new BlockColumnFeature(
					List.of(
						BlockColumnFeature.layer(
							ceiling ? UniformInt.of(2, 5) : UniformInt.of(1, 3),
							BlockStateProvider.holderOf(block)
						)
					),
					ceiling ? Direction.DOWN : Direction.UP,
					BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
					false
				)
			),
			ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
			ceiling ? ConstantInt.of(3) : ConstantInt.of(2),
			ceiling ? 0.7F : 0.45F,
			2,
			0.08F,
			ceiling ? UniformInt.of(4, 14) : UniformInt.of(2, 4),
			ceiling ? 0.7F : 0.5F
		);

		feature.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(vegetationPatch.apply(pearlescentBlue)),
					PlacementUtils.inlinePlaced(vegetationPatch.apply(pearlescentPurple))
				)
			)
		);
	}

	private static void registerLargeMesoglea(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		Block pearlescentBlue, Block pearlescentPurple,
		boolean large
	) {
		final Function<Block, Feature> largeMesoglea = block -> new LargeSpeleothemFeature(
			block.defaultBlockState(),
			context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.MESOGLEA_AND_NEMATOCYST_BASE_BLOCKS),
			30,
			large ? ClampedInt.of(UniformInt.of(3, 19), 3, 16) : ClampedInt.of(UniformInt.of(3, 10), 3, 7),
			large ? UniformFloat.of(0.2F, 2F) : UniformFloat.of(0.2F, 0.75F),
			large ? 0.33F : 0.15F,
			large ? UniformFloat.of(0.1F, 0.9F) : UniformFloat.of(0.1F, 0.25F),
			large ? UniformFloat.of(0.4F, 1F) : UniformFloat.of(0.16F, 0.4F),
			large ? UniformFloat.of(0F, 0.3F) : UniformFloat.of(0F, 0.25F),
			large ? 4 : 5,
			0.2F
		);

		feature.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(largeMesoglea.apply(pearlescentBlue)),
					PlacementUtils.inlinePlaced(largeMesoglea.apply(pearlescentPurple))
				)
			)
		);
	}

	private static void registerNematocyst(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		Block pearlescentBlue, Block pearlescentPurple,
		TagKey<Block> pearlescentBluePlaceable, TagKey<Block> pearlescentPurplePlaceable
	) {
		final BiFunction<Block, TagKey<Block>, Feature> nematocystFeature = (block, placeable) -> new MultifaceGrowthFeature(
			block,
			20,
			true,
			true,
			true,
			0.98F,
			context.lookup(Registries.BLOCK).getOrThrow(placeable)
		);

		feature.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(nematocystFeature.apply(pearlescentBlue, pearlescentBluePlaceable)),
					PlacementUtils.inlinePlaced(nematocystFeature.apply(pearlescentPurple, pearlescentPurplePlaceable))
				)
			)
		);
	}

	private static void registerGabbroPatch(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		boolean ceiling
	) {
		feature.makeAndSetHolder(
			new VegetationPatchFeature(
				context.lookup(Registries.BLOCK).getOrThrow(BlockTags.BASE_STONE_OVERWORLD),
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
				PlacementUtils.inlinePlaced(
					new BlockColumnFeature(
						List.of(
							BlockColumnFeature.layer(
								ceiling ? UniformInt.of(1, 3) : UniformInt.of(1, 2),
								BlockStateProvider.holderOf(WWBlocks.GABBRO.get())
							)
						),
						ceiling ? Direction.DOWN : Direction.UP,
						BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
						false
					)
				),
				ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.5F,
				2,
				0.2F,
				UniformInt.of(2, 4),
				0.45F
			)
		);
	}

	private static void registerHorizontalGeothermalVent(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		Direction.Axis axis
	) {
		final Function<Direction, Holder<PlacedFeature>> inlinePlacedWithDirection = direction -> PlacementUtils.inlinePlaced(
			new SimpleBlockFeature(
				BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction))
			),
			EnvironmentScanPlacement.scanningFor(
				Direction.DOWN,
				BlockPredicate.allOf(
					BlockPredicate.solid(),
					new ReplaceablePredicate(direction.getUnitVec3i()),
					BlockPredicate.matchesBlocks(direction.getOpposite(), WWBlocks.GABBRO.get(), Blocks.MAGMA_BLOCK)
				),
				BlockPredicate.alwaysTrue(),
				12
			)
		);
		feature.makeAndSetHolder(
			new SimpleRandomSelectorFeature(
				HolderSet.direct(
					inlinePlacedWithDirection.apply(axis.getNegative()),
					inlinePlacedWithDirection.apply(axis.getPositive())
				)
			)
		);
	}

	private static void registerGeothermalVentStack(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		boolean ceiling
	) {
		feature.makeAndSetHolder(
			new VegetationPatchFeature(
				context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.GEOTHERMAL_VENT_STACK_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
				PlacementUtils.inlinePlaced(
					new VentStackFeature(
						ceiling
							? BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(GeothermalVentBlock.FACING, Direction.DOWN))
							: BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get()),
						BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
						BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
						ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
						ceiling ? UniformInt.of(1, 4) : UniformInt.of(0, 3),
						context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.GEOTHERMAL_VENT_STACK_REPLACEABLE)
					)
				),
				ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.375F,
				4,
				0.25F,
				ceiling ? UniformInt.of(2, 4) : UniformInt.of(2, 3),
				0.45F
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("cave/" + id));
	}

	private WWCaveFeatures() {}
}
