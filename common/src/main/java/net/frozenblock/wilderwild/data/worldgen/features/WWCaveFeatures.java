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
import java.util.function.BiFunction;
import java.util.function.Function;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.blockpredicates.SearchInAreaBlockPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.NoisePathFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandBlockPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.noise_path.config.NoiseBandPlacement;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleLavaVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleVegetationPatchFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.patch.FlexibleWaterloggedVegetationPatchFeature;
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
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
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
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.LargeSpeleothemFeature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.NoOpFeature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.SequenceFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.SimpleRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.SpringFeature;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.feature.WeightedRandomSelectorFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.OffsetPlacement;
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
	public static final FrozenLibFeature PUDDLE_LAVA_GABBRO = register("puddle_lava_gabbro");
	public static final FrozenLibFeature SPRING_LAVA_MAGMA_OR_GABBRO = register("spring_lava_magmatic_caves");
	public static final FrozenLibFeature LAKE_LAVA_GABBRO = register("lake_lava_gabbro");
	public static final FrozenLibFeature PATCH_MAGMA = WWFeatureUtils.register("patch_magma");
	public static final FrozenLibFeature PATCH_MAGMA_CEILING = WWFeatureUtils.register("patch_magma_ceiling");
	public static final FrozenLibFeature PATCH_GABBRO = register("patch_gabbro");
	public static final FrozenLibFeature PATCH_GABBRO_CEILING = register("patch_gabbro_ceiling");
	public static final FrozenLibFeature GEOTHERMAL_VENT_UP = register("geothermal_vent_up");
	public static final FrozenLibFeature GEOTHERMAL_VENT_DOWN = register("geothermal_vent_down");
	public static final FrozenLibFeature GEOTHERMAL_VENT_NORTH_SOUTH = register("geothermal_vent_north_south");
	public static final FrozenLibFeature GEOTHERMAL_VENT_EAST_WEST = register("geothermal_vent_east_west");
	public static final FrozenLibFeature GEOTHERMAL_VENT_STACK = register("geothermal_vent_stack");
	public static final FrozenLibFeature GEOTHERMAL_VENT_STACK_CEILING = register("geothermal_vent_stack_ceiling");
	public static final FrozenLibFeature GEOTHERMAL_VENT_STACK_LAVA_LAKE = register("geothermal_vent_stack_lava_lake");

	// ICE CAVES
	public static final FrozenLibFeature PATCH_BRITTLE_ICE = register("patch_brittle_ice");
	public static final FrozenLibFeature PATCH_BRITTLE_ICE_CEILING = register("patch_brittle_ice_ceiling");

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
			new FlexibleWaterloggedVegetationPatchFeature(
				blocks.getOrThrow(BlockTags.LUSH_GROUND_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.STONE),
				WWMiscConfigured.EMPTY.asInlinePlaced(),
				CaveSurface.FLOOR,
				ConstantInt.of(4),
				0.8F,
				2,
				0.000F,
				UniformInt.of(12, 15),
				0.7F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
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
		PUDDLE_LAVA_GABBRO.makeAndSetHolder(
			new FlexibleLavaVegetationPatchFeature(
				blocks.getOrThrow(WWFeatureBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
				PlacementUtils.inlinePlaced(
					new BlockColumnFeature(
						List.of(
							BlockColumnFeature.layer(
								UniformInt.of(1, 4),
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
				UniformInt.of(3, 6),
				0.7F,
				FlexibleVegetationPatchFeature.FlexibleComponents.CIRCULAR
			)
		);

		SPRING_LAVA_MAGMA_OR_GABBRO.makeAndSetHolder(
			new SpringFeature(
				Fluids.LAVA.defaultFluidState(),
				false,
				4,
				1,
				HolderSet.direct(
					Block::builtInRegistryHolder,
					Blocks.MAGMA_BLOCK,
					WWBlocks.GABBRO.get()
				)
			)
		);

		LAKE_LAVA_GABBRO.makeAndSetHolder(
			new SequenceFeature(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						new LakeFeature(
							BlockStateProvider.holderOf(Blocks.LAVA),
							BlockStateProvider.holderOf(WWBlocks.GABBRO.get()),
							BlockPredicate.alwaysTrue(),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.FEATURES_CANNOT_REPLACE)),
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
						)
					),
					PlacementUtils.inlinePlaced(
						new WeightedRandomSelectorFeature(
							WeightedList.<Holder<PlacedFeature>>builder()
								.add(
									GEOTHERMAL_VENT_STACK_LAVA_LAKE.asInlinePlaced(
										OffsetPlacement.vertical(ConstantInt.of(-1)),
										EnvironmentScanPlacement.scanningFor(
											Direction.DOWN,
											BlockPredicate.matchesBlocks(WWBlocks.GABBRO.get()),
											BlockPredicate.anyOf(
												BlockPredicate.ONLY_IN_AIR_PREDICATE,
												BlockPredicate.matchesBlocks(Blocks.LAVA)
											),
											6
										),
										OffsetPlacement.vertical(ConstantInt.of(1))
									),
									2
								)
								.add(PlacementUtils.inlinePlaced(new NoOpFeature()))
								.build()
						)
					)
				)
			)
		);

		registerMagmaPatch(context, PATCH_MAGMA, false);
		registerMagmaPatch(context, PATCH_MAGMA_CEILING, true);

		registerGabbroPatch(context, PATCH_GABBRO, false);
		registerGabbroPatch(context, PATCH_GABBRO_CEILING, true);

		final Function<Direction, SimpleBlockFeature> geothermalVentFeature = direction -> new SimpleBlockFeature(
			BlockStateProvider.holderOf(WWBlocks.GEOTHERMAL_VENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction))
		);
		GEOTHERMAL_VENT_UP.makeAndSetHolder(geothermalVentFeature.apply(Direction.UP));
		GEOTHERMAL_VENT_DOWN.makeAndSetHolder(geothermalVentFeature.apply(Direction.DOWN));
		registerHorizontalGeothermalVent(context, GEOTHERMAL_VENT_NORTH_SOUTH, Direction.Axis.Z);
		registerHorizontalGeothermalVent(context, GEOTHERMAL_VENT_EAST_WEST, Direction.Axis.X);

		registerGeothermalVentStack(context, GEOTHERMAL_VENT_STACK, 0, 2, false);
		registerGeothermalVentStack(context, GEOTHERMAL_VENT_STACK_CEILING, 1, 3, true);
		registerGeothermalVentStack(context, GEOTHERMAL_VENT_STACK_LAVA_LAKE, 0, 1, false);

		// ICE CAVES
		registerBrittleIcePatch(context, PATCH_BRITTLE_ICE, false);
		registerBrittleIcePatch(context, PATCH_BRITTLE_ICE_CEILING, true);
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

	private static void registerMagmaPatch(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		boolean ceiling
	) {
		feature.makeAndSetHolder(
			new VegetationPatchFeature(
				context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.MAGMA_REPLACEABLE),
				BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK),
				ceiling
					? PlacementUtils.inlinePlaced(
						new BlockColumnFeature(
							List.of(
								BlockColumnFeature.layer(BiasedToBottomInt.of(1, 2), BlockStateProvider.holderOf(Blocks.MAGMA_BLOCK))
							),
							Direction.DOWN,
							BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE,
							false
						)
					)
					: PlacementUtils.inlinePlaced(new NoOpFeature()),
				ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.4F,
				2,
				ceiling ? 0.08F : 0F,
				BiasedToBottomInt.of(2, 3),
				0.65F
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
				PlacementUtils.inlinePlaced(new NoOpFeature()),
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
		int minHeight, int maxHeight,
		boolean ceiling
	) {
		feature.makeAndSetHolder(
			new FlexibleVegetationPatchFeature(
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
						UniformInt.of(minHeight, maxHeight),
						context.lookup(Registries.BLOCK).getOrThrow(WWFeatureBlockTags.GEOTHERMAL_VENT_STACK_REPLACEABLE)
					)
				),
				ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
				ConstantInt.of(2),
				0.375F,
				4,
				0.2F,
				ceiling ? UniformInt.of(2, 4) : UniformInt.of(2, 3),
				0.45F,
				new FlexibleVegetationPatchFeature.FlexibleComponents(
					Optional.of(
						BlockPredicate.anyOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.matchesBlocks(Blocks.LAVA)
						)
					),
					false,
					false,
					true
				)
			)
		);
	}

	private static void registerBrittleIcePatch(
		BootstrapContext<Feature> context,
		FrozenLibFeature feature,
		boolean ceiling
	) {
		feature.makeAndSetHolder(
			new VegetationPatchFeature(
				context.lookup(Registries.BLOCK).getOrThrow(BlockTags.ICE_CREVASSE_REPLACEABLES),
				BlockStateProvider.holderOf(Blocks.BRITTLE_ICE),
				PlacementUtils.inlinePlaced(ceiling
					? new BlockColumnFeature(
						List.of(
							BlockColumnFeature.layer(BiasedToBottomInt.of(0, 4), BlockStateProvider.holderOf(Blocks.BRITTLE_ICE))
						),
					Direction.DOWN,
					BlockPredicate.ONLY_IN_AIR_PREDICATE,
					false
					) : new NoOpFeature()
				),
				ceiling ? CaveSurface.CEILING : CaveSurface.FLOOR,
				ceiling ? UniformInt.of(2, 3) : UniformInt.of(2, 5),
				ceiling ? 0.4F : 0.25F,
				4,
				0.035F,
				UniformInt.of(2, 4),
				ceiling ? 0.6F : 0.4F
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("cave/" + id));
	}

	private WWCaveFeatures() {}
}
