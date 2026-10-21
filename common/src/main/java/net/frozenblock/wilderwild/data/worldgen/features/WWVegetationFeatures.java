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
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnWithDiskFeature;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.LeafLitterStateProvider;
import net.frozenblock.lib.registry.FrozenLibRegistries;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlockStateProviders;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.tag.WWFeatureBlockTags;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWVegetationFeatures {
	// LEAF LITTER
	public static final FrozenLibFeature SPRUCE_LEAF_LITTER_SINGLE = register("spruce_leaf_litter_single");
	public static final FrozenLibFeature DARK_OAK_LEAF_LITTER_SINGLE = register("dark_oak_leaf_litter_single");
	public static final FrozenLibFeature PALE_OAK_LEAF_LITTER_SINGLE = register("pale_oak_leaf_litter_single");

	// GRASS
	public static final FrozenLibFeature GRASS_AND_FERN = register("grass_and_fern");
	public static final FrozenLibFeature FERN_AND_GRASS = register("fern_and_grass");
	public static final FrozenLibFeature GRASS_OASIS = register("grass_oasis");
	public static final FrozenLibFeature TALL_GRASS = register("tall_grass");
	public static final FrozenLibFeature TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");
	public static final FrozenLibFeature TALL_GRASS_AND_LARGE_FERN = register("tall_grass_and_large_fern");
	public static final FrozenLibFeature LARGE_FERN_AND_TALL_GRASS = register("large_fern_and_tall_grass");
	public static final FrozenLibFeature FERN_SWAMP = register("fern_swamp");
	public static final FrozenLibFeature CLOVER = register("clover");

	// FROZEN GRASS
	public static final FrozenLibFeature SINGLE_PIECE_OF_FROZEN_GRASS = register("single_piece_of_frozen_grass");
	public static final FrozenLibFeature FROZEN_GRASS = register("frozen_grass");
	public static final FrozenLibFeature FROZEN_TALL_GRASS = register("frozen_tall_grass");
	public static final FrozenLibFeature FROZEN_GRASS_TAIGA = register("frozen_grass_taiga");
	public static final FrozenLibFeature FROZEN_BUSH = register("frozen_bush");
	public static final FrozenLibFeature FROZEN_LARGE_FERN = register("frozen_large_fern");

	// SHRUB
	public static final FrozenLibFeature SHRUB_GENERIC = register("shrub_generic");
	public static final FrozenLibFeature SHRUB_SPARSE = register("shrub_sparse");
	public static final FrozenLibFeature SHRUB_FLOWER_FIELD = register("shrub_flower_field");
	public static final FrozenLibFeature SHRUB_JUNGLE = register("shrub_jungle");
	public static final FrozenLibFeature SHRUB_OASIS = register("shrub_oasis");
	public static final FrozenLibFeature SHRUB_DESERT = register("shrub_desert");

	// MUSHROOM
	public static final FrozenLibFeature MUSHROOMS_DARK_FOREST = register("mushroom_dark_forest");
	public static final FrozenLibFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibFeature MYCELIUM_GROWTH = register("mycelium_growth");

	// CACTUS
	public static final FrozenLibFeature CACTUS_OASIS = register("cactus_oasis");
	public static final FrozenLibFeature CACTUS_TALL = register("cactus_tall");
	public static final FrozenLibFeature CACTUS_TALL_BADLANDS = register("cactus_tall_badlands");
	public static final FrozenLibFeature PRICKLY_PEAR = register("prickly_pear");

	// TUMBLEWEED
	public static final FrozenLibFeature TUMBLEWEED = register("tumbleweed");

	// POLLEN
	public static final FrozenLibFeature POLLEN = register("pollen");

	// MOSS
	public static final FrozenLibFeature MOSS_CARPET = register("moss_carpet");

	// MISC (move to misc features later)
	public static final FrozenLibFeature TERMITE_MOUND = register("termite_mound");

	public static void bootstrap(BootstrapContext<Feature> context) {
		final HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<ConfigPredicate> configPredicates = context.lookup(FrozenLibRegistries.CONFIG_PREDICATE_PROVIDER);
		final HolderGetter<BlockStateProvider> blockStateProviders = context.lookup(Registries.BLOCK_STATE_PROVIDER);
		final HolderGetter<Block> blocks = context.lookup(Registries.BLOCK);

		// LEAF LITTER
		registerSingleLeafLitter(SPRUCE_LEAF_LITTER_SINGLE, WWBlocks.SPRUCE_LEAF_LITTER.get());
		registerSingleLeafLitter(DARK_OAK_LEAF_LITTER_SINGLE, WWBlocks.DARK_OAK_LEAF_LITTER.get());
		registerSingleLeafLitter(PALE_OAK_LEAF_LITTER_SINGLE, WWBlocks.PALE_OAK_LEAF_LITTER.get());

		// GRASS
		GRASS_AND_FERN.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.GRASS_AND_FERN)));
		FERN_AND_GRASS.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FERN_AND_GRASS)));
		GRASS_OASIS.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.GRASS_OASIS)));
		TALL_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.TALL_GRASS)));
		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.TALL_GRASS_AND_GRASS_WATER)));
		TALL_GRASS_AND_LARGE_FERN.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.TALL_GRASS_AND_LARGE_FERN)));
		LARGE_FERN_AND_TALL_GRASS.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.LARGE_FERN_AND_TALL_GRASS)));
		FERN_SWAMP.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FERN_SWAMP)));
		CLOVER.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(WWBlocks.CLOVERS.get())));

		// FROZEN GRASS
		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));
		FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));
		FROZEN_TALL_GRASS.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FROZEN_TALL_GRASS)));
		FROZEN_GRASS_TAIGA.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FROZEN_GRASS_TAIGA)));
		FROZEN_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_BUSH.get())));
		FROZEN_LARGE_FERN.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.FROZEN_LARGE_FERN)));

		// SHRUB
		SHRUB_GENERIC.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_GENERIC)));
		SHRUB_SPARSE.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_SPARSE)));
		SHRUB_FLOWER_FIELD.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_FLOWER_FIELD)));
		SHRUB_JUNGLE.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_JUNGLE)));
		SHRUB_OASIS.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_OASIS)));
		SHRUB_DESERT.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.SHRUB_DESERT)));

		// MUSHROOM
		MUSHROOMS_DARK_FOREST.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.MUSHROOM_DARK_FOREST)));
		PALE_MUSHROOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.PALE_MUSHROOM.get())));
		MYCELIUM_GROWTH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MYCELIUM_GROWTH.get())));

		// CACTUS
		registerCactus(CACTUS_OASIS, BiasedToBottomInt.of(3, 5), 4);
		registerCactus(CACTUS_TALL, BiasedToBottomInt.of(4, 5), 5);
		registerCactus(CACTUS_TALL_BADLANDS, BiasedToBottomInt.of(2, 6), 5);
		PRICKLY_PEAR.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.PRICKLY_PEAR)));

		// TUMBLEWEED
		TUMBLEWEED.makeAndSetHolder(new SimpleBlockFeature(blockStateProviders.getOrThrow(WWBlockStateProviders.TUMBLEWEED)));

		// POLLEN
		POLLEN.makeAndSetHolder(
			new MultifaceGrowthFeature(
				WWBlocks.POLLEN.get(),
				10,
				true,
				true,
				true,
				0.5F,
				blocks.getOrThrow(WWFeatureBlockTags.POLLEN_PLACEABLE)
			)
		);

		// MOSS
		MOSS_CARPET.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.MOSS_CARPET)));

		// MISC (move to misc features later)
		TERMITE_MOUND.makeAndSetHolder(
			new ColumnWithDiskFeature(
				BlockStateProvider.holderOf(WWBlocks.TERMITE_MOUND.get().defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true)),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				blocks.getOrThrow(WWFeatureBlockTags.TERMITE_DISK_REPLACEABLE),
				Holder.direct(
					new WeightedStateProvider(
						WeightedList.<BlockState>builder()
							.add(Blocks.COARSE_DIRT.defaultBlockState(), 2)
							.add(Blocks.PACKED_MUD.defaultBlockState(), 1)
							.build()
					)
				)
			)
		);
	}

	private static void registerSingleLeafLitter(FrozenLibFeature feature, Block leafLitterBlock) {
		feature.makeAndSetHolder(new SimpleBlockFeature(new LeafLitterStateProvider(leafLitterBlock, 3)));
	}

	private static void registerCactus(FrozenLibFeature feature, IntProvider height, int placeFlowerOnceEvery) {
		if (placeFlowerOnceEvery <= 1) throw new IllegalArgumentException("placeFlowerOnceEvery must be greater than 1!");

		feature.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(height, BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(
							WeightedList.<IntProvider>builder()
								.add(ConstantInt.of(0), placeFlowerOnceEvery - 1)
								.add(ConstantInt.of(1), 1).build()
						),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);
	}

	private static FrozenLibFeature register(String id) {
		return new FrozenLibFeature(WWConstants.id("vegetation/" + id));
	}

	private WWVegetationFeatures() {}
}
