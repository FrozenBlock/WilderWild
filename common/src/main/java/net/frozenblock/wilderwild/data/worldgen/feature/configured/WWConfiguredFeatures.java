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
import net.frozenblock.lib.levelgen.feature.api.FrozenLibFeature;
import net.frozenblock.lib.levelgen.feature.api.feature.ColumnWithDiskFeature;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.FlowerBedStateProvider;
import net.frozenblock.lib.levelgen.feature.api.stateproviders.LeafLitterStateProvider;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.ShrubBlock;
import static net.frozenblock.wilderwild.data.worldgen.feature.WWFeatureUtils.register;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.BlockColumnFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.MultifaceGrowthFeature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public final class WWConfiguredFeatures {
	private static final NormalNoise SINGLE_OCTAVE_NOISE = NormalNoise.builder()
		.setBaseOctave(0)
		.setBaseAmplitude(1D)
		.setNormalize(false)
		.setOctaveCount(1)
		.setAmplitudeModifier(0, 1D)
		.build();

	// LEAF LITTERS
	public static final FrozenLibFeature DARK_OAK_LEAF_LITTER_SINGLE = register("dark_oak_leaf_litter_single");
	public static final FrozenLibFeature PALE_OAK_LEAF_LITTER_SINGLE = register("pale_oak_leaf_litter_single");
	public static final FrozenLibFeature SPRUCE_LEAF_LITTER_SINGLE = register("spruce_leaf_litter_single");

	// FLOWERS (lol no??)
	public static final FrozenLibFeature CLOVER = register("clover");
	public static final FrozenLibFeature PALE_MUSHROOM = register("pale_mushroom");
	public static final FrozenLibFeature MUSHROOMS_DARK_FOREST = register("mushroom_dark_forest");

	// VEGETATION
	public static final FrozenLibFeature MOSS_CARPET = register("moss_carpet");
	public static final FrozenLibFeature FROZEN_BUSH = register("frozen_bush");
	public static final FrozenLibFeature TAIGA_FROZEN_GRASS = register("taiga_frozen_grass");
	public static final FrozenLibFeature FROZEN_GRASS = register("frozen_grass");
	public static final FrozenLibFeature FROZEN_LARGE_FERN = register("frozen_large_fern");
	public static final FrozenLibFeature FROZEN_TALL_GRASS = register("frozen_tall_grass");
	public static final FrozenLibFeature SINGLE_PIECE_OF_FROZEN_GRASS = register("single_piece_of_frozen_grass");
	public static final FrozenLibFeature GRASS_OASIS = register("grass_oasis");
	public static final FrozenLibFeature SHRUB_OASIS = register("shrub_oasis");
	public static final FrozenLibFeature SHRUB_JUNGLE = register("shrub_jungle");
	public static final FrozenLibFeature SHRUB_SPARSE = register("shrub_sparse");
	public static final FrozenLibFeature SHRUB_FLOWER_FIELD = register("shrub_flower_field");
	public static final FrozenLibFeature SHRUB_GENERIC = register("shrub_generic");
	public static final FrozenLibFeature SHRUB_DESERT = register("shrub_desert");
	public static final FrozenLibFeature CACTUS_OASIS = register("cactus_oasis");
	public static final FrozenLibFeature CACTUS_TALL = register("cactus_tall");
	public static final FrozenLibFeature CACTUS_TALL_BADLANDS = register("cactus_tall_badlands");
	public static final FrozenLibFeature PRICKLY_PEAR = register("prickly_pear");
	public static final FrozenLibFeature LARGE_FERN_AND_GRASS = register("large_fern_and_grass");
	public static final FrozenLibFeature LARGE_FERN_AND_GRASS_2 = register("large_fern_and_grass_2");
	public static final FrozenLibFeature TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");
	public static final FrozenLibFeature TALL_GRASS_SWAMP = register("tall_grass_swamp");
	public static final FrozenLibFeature FERN_SWAMP = register("fern_swamp");
	public static final FrozenLibFeature FERN_AND_GRASS = register("fern_and_grass");
	public static final FrozenLibFeature GRASS_AND_FERN = register("grass_and_fern");
	public static final FrozenLibFeature MYCELIUM_GROWTH = register("mycelium_growth");
	public static final FrozenLibFeature POLLEN = register("pollen");
	public static final FrozenLibFeature TERMITE_MOUND = register("termite_mound");
	public static final FrozenLibFeature TUMBLEWEED = register("tumbleweed");

	public static void registerConfiguredFeatures(BootstrapContext<Feature> entries) {
		WWConstants.logWithModId("Registering WWConfiguredFeatures for", true);
		final HolderGetter<Feature> features = entries.lookup(Registries.FEATURE);
		final HolderGetter<PlacedFeature> placedFeatures = entries.lookup(Registries.PLACED_FEATURE);
		final HolderGetter<Block> blocks = entries.lookup(Registries.BLOCK);

		// LEAF LITTERS
		DARK_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.DARK_OAK_LEAF_LITTER.get(), 3))
		);

		PALE_OAK_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.PALE_OAK_LEAF_LITTER.get(), 3))
		);

		SPRUCE_LEAF_LITTER_SINGLE.makeAndSetHolder(
			new SimpleBlockFeature(new LeafLitterStateProvider(WWBlocks.SPRUCE_LEAF_LITTER.get(), 3))
		);

		// FLOWERS (BRUH)
		CLOVER.makeAndSetHolder(new SimpleBlockFeature(new FlowerBedStateProvider(WWBlocks.CLOVERS.get())));

		PALE_MUSHROOM.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.PALE_MUSHROOM.get())));

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(
			new SimpleBlockFeature(
				new NoiseProvider(
					5234L,
					SINGLE_OCTAVE_NOISE,
					0.020833334F,
					List.of(
						Blocks.RED_MUSHROOM.defaultBlockState(),
						Blocks.BROWN_MUSHROOM.defaultBlockState()
					)
				)
			)
		);


		// VEGETATION
		MOSS_CARPET.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.MOSS_CARPET)));

		FROZEN_BUSH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_BUSH.get())));

		TAIGA_FROZEN_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_SHORT_GRASS.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_FERN.get().defaultBlockState(), 4)
						.build()
				)
			)
		);

		FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));

		FROZEN_LARGE_FERN.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_LARGE_FERN.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_FERN.get().defaultBlockState(), 2)
						.build()
				)
			)
		);

		FROZEN_TALL_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.FROZEN_TALL_GRASS.get().defaultBlockState(), 1)
						.add(WWBlocks.FROZEN_SHORT_GRASS.get().defaultBlockState(), 2)
						.build()
				)
			)
		);

		SINGLE_PIECE_OF_FROZEN_GRASS.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.FROZEN_SHORT_GRASS.get())));

		GRASS_OASIS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
						.build()
				)
			)
		);

		SHRUB_OASIS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_JUNGLE.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 5)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 5)
						.build()
				)
			)
		);

		SHRUB_SPARSE.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_FLOWER_FIELD.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 4)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 4)
						.build()
				)
			)
		);

		SHRUB_GENERIC.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 6)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 2)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 2)
						.build()
				)
			)
		);

		SHRUB_DESERT.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 0), 1)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 1), 3)
						.add(WWBlocks.SHRUB.get().defaultBlockState().setValue(ShrubBlock.AGE, 2), 3)
						.build()
				)
			)
		);

		CACTUS_OASIS.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(3, 5), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 3).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(4, 5), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		CACTUS_TALL_BADLANDS.makeAndSetHolder(
			new BlockColumnFeature(
				List.of(
					BlockColumnFeature.layer(BiasedToBottomInt.of(2, 6), BlockStateProvider.holderOf(Blocks.CACTUS)),
					BlockColumnFeature.layer(
						new WeightedListInt(WeightedList.<IntProvider>builder().add(ConstantInt.of(0), 4).add(ConstantInt.of(1), 1).build()),
						BlockStateProvider.holderOf(Blocks.CACTUS_FLOWER)
					)
				),
				Direction.UP,
				BlockPredicate.ONLY_IN_AIR_PREDICATE,
				false
			)
		);

		PRICKLY_PEAR.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
					.add(WWBlocks.PRICKLY_PEAR.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 2)
					.add(Blocks.CACTUS.defaultBlockState(), 3)
					.build()
				)
			)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
						.build()
				)
			)
		);

		LARGE_FERN_AND_GRASS_2.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.TALL_GRASS.defaultBlockState(), 1)
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 4)
						.build()
				)
			)
		);

		TALL_GRASS_SWAMP.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(Blocks.TALL_GRASS)));

		FERN_SWAMP.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.FERN.defaultBlockState(), 4)
						.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		FERN_AND_GRASS.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
						.add(Blocks.FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		GRASS_AND_FERN.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(Blocks.SHORT_GRASS.defaultBlockState(), 11)
						.add(Blocks.FERN.defaultBlockState(), 1)
						.build()
				)
			)
		);

		MYCELIUM_GROWTH.makeAndSetHolder(new SimpleBlockFeature(BlockStateProvider.holderOf(WWBlocks.MYCELIUM_GROWTH.get())));

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

		TUMBLEWEED.makeAndSetHolder(
			new SimpleBlockFeature(
				new WeightedStateProvider(
					WeightedList.<BlockState>builder()
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1)
						.add(WWBlocks.TUMBLEWEED_PLANT.get().defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1)
						.build()
				)
			)
		);
	}
}
