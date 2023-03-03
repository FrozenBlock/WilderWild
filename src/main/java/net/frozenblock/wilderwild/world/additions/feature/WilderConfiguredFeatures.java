/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.additions.feature;

import net.frozenblock.wilderwild.misc.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterProperties;
<<<<<<< HEAD
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
=======
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import net.frozenblock.wilderwild.world.generation.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.generation.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.generation.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
>>>>>>> dev
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import java.util.List;

public final class WilderConfiguredFeatures {
	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

<<<<<<< HEAD
	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2).add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 3).add(Blocks.LARGE_FERN.defaultBlockState(), 3).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_TREES_MIXED = key("fallen_trees_mixed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_FALLEN_TREES_MIXED = key("mossy_fallen_trees_mixed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_FALLEN_TREES_OAK_AND_BIRCH = key("mossy_fallen_trees_oak_and_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH = key("fallen_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE = key("fallen_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_SPRUCE_AND_OAK = key("fallen_spruce_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_OAK = key("fallen_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_CYPRESS_AND_OAK = key("fallen_cypress_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FALLEN_BIRCH_AND_SPRUCE = key("fallen_birch_and_spruce");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PLAINS = key("trees_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH = key("trees_birch");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_BIRCH_TALL = key("trees_birch_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FOREST = key("trees_flower_forest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MIXED_TREES = key("mixed_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TEMPERATE_RAINFOREST_TREES = key("temperate_rainforest_trees");

	public static final ResourceKey<ConfiguredFeature<?, ?>> RAINFOREST_TREES = key("rainforest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TAIGA_TREES = key("birch_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_TAIGA = key("trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_TREES_TAIGA = key("short_trees_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_SPRUCE = key("short_mega_spruce_configured");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_MEGA_SPRUCE_ON_SNOW = key("short_mega_spruce_on_snow_configured");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_GROVE = key("trees_grove");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MEADOW_TREES = key("meadow_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SAVANNA_TREES = key("savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_SAVANNA_TREES = key("arid_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PARCHED_FOREST_TREES = key("parched_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> ARID_FOREST_TREES = key("arid_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_SAPLING = key("cypress_wetlands_trees_sapling");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WOODED_BADLANDS_TREES = key("wooded_badlands_trees", );
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIG_SHRUBS = key("big_shrubs");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS = key("palms");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS_JUNGLE = key("palms_jungle");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PALMS_OASIS = key("palms_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SEEDING_DANDELION = key("seeding_dandelion");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CARNATION = key("carnation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DATURA = key("datura");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PLAINS = key("flower_plains");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MILKWEED = key("milkweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> GLORY_OF_THE_SNOW = key("glory_of_the_snow");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_FLOWER_FIELD = key("flower_field_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = key("old_growth_snowy_pine_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_ARID_SAVANNA = key("arid_savanna_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_PARCHED_FOREST = key("parched_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_ARID_FOREST = key("arid_forest_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_FLOWER_FIELD = key("flower_field_flowers");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MOSS_CARPET = key("moss_carpet");

	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_TEMPERATE_RAINFOREST = key("flowers_temperate_rainforest");

	public static final ResourceKey<ConfiguredFeature<?, ?>> MUSHROOMS_DARK_FOREST = key("mushroom_dark_forest");

	public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_RAINFOREST = key("flowers_rainforest");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_FLOWER_FLOWER_FIELD = key("flower_field_tall_flowers");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_BUSH = key("desert_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_SAND = key("badlands_bush_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_TERRACOTTA = key("badlands_bush_terracotta");
	public static final ResourceKey<ConfiguredFeature<?, ?>> WOODED_BADLANDS_BUSH_TERRACOTTA = key("wooded_badlands_bush_terracotta");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_OASIS = key("patch_cactus_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL = key("patch_cactus_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL_BADLANDS = key("patch_cactus_tall_badlands");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_BUSH = key("oasis_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_BUSH_AND_BUSH = key("dead_bush_and_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BUSH_AND_DEAD_BUSH = key("bush_and_dead_bush");

	public static final SimpleWeightedRandomList<BlockState> FLOWER_FIELD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 2).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 5).build();
	public static final SimpleWeightedRandomList<BlockState> DESERT_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 4).build();
	public static final ResourceKey<ConfiguredFeature<?, ?>> TREES_SEMI_BIRCH_AND_OAK = key("trees_semi_birch_and_oak");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_GROWTH_BIRCH_TAIGA_TREES = key("old_growth_birch_taiga_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_JUNGLE_TREES = key("birch_jungle_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SPARSE_BIRCH_JUNGLE_TREES = key("sparse_birch_jungle_trees");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OLD_GROWTH_DARK_FOREST_VEGETATION = key("old_growth_dark_forest_vegetation");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_BIRCH_FOREST_VEGETATION = key("dark_birch_forest_vegetation");

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_TEMPERATE_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.MILKWEED.defaultBlockState(), 3)
			.add(RegisterBlocks.DATURA.defaultBlockState(), 5)
			.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 5)
			.add(Blocks.LILAC.defaultBlockState(), 6)
			.add(Blocks.DANDELION.defaultBlockState(), 10)
			.add(Blocks.BLUE_ORCHID.defaultBlockState(), 8)
			.add(Blocks.POPPY.defaultBlockState(), 10).build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder()
			.add(RegisterBlocks.MILKWEED.defaultBlockState(), 5)
			.add(RegisterBlocks.DATURA.defaultBlockState(), 7)
			.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 7)
			.add(Blocks.LILAC.defaultBlockState(), 3)
			.add(Blocks.DANDELION.defaultBlockState(), 4)
			.add(Blocks.BLUE_ORCHID.defaultBlockState(), 3)
			.add(Blocks.POPPY.defaultBlockState(), 4)
			.add(RegisterBlocks.CARNATION.defaultBlockState(), 8).build();

	//VEGETATION
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 2).add(Blocks.GRASS.defaultBlockState(), 5).build();
	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 8).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> DEAD_BUSH_AND_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 5).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();
	public static final SimpleWeightedRandomList<BlockState> BUSH_AND_DEAD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.DEAD_BUSH.defaultBlockState(), 2).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 0), 1).add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_1, 1), 2).build();

	public static final SimpleWeightedRandomList<BlockState> PRICKLY_PEAR_POOL = SimpleWeightedRandomList.<BlockState>builder()
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
			.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 4)
			.add(Blocks.CACTUS.defaultBlockState(), 2).build();
	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.TALL_GRASS.defaultBlockState(), 5).add(Blocks.LARGE_FERN.defaultBlockState(), 1).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS_2 = key("large_fern_and_grass_2");
	public static final SimpleWeightedRandomList<BlockState> GRASS_AND_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder().add(Blocks.GRASS.defaultBlockState(), 3).add(Blocks.FERN.defaultBlockState(), 1).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> FERN_AND_GRASS =
			key("fern_and_grass", Feature.RANDOM_PATCH,
					new RandomPatchConfiguration(32, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL)))));

    public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL =
            key("cattail", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL_06 =
			key("cattail_06", WilderWild.CATTAIL_FEATURE, new ProbabilityFeatureConfiguration(0.6F));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERED_WATERLILY =
            key("patch_flowered_waterlily", Feature.RANDOM_PATCH,
                    new RandomPatchConfiguration(10, 7, 3, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD)))));

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_ALGAE =
            key("patch_algae", WilderWild.ALGAE_FEATURE, new ProbabilityFeatureConfiguration(0.8F));

    public static final ResourceKey<ConfiguredFeature<?, ?>> TERMITE_CONFIGURED =
            key("termite_mound_baobab", FrozenFeatures.COLUMN_WITH_DISK_FEATURE, new ColumnWithDiskFeatureConfig(RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true), UniformInt.of(4, 9), UniformInt.of(3, 7), UniformInt.of(1, 3), HolderSet.direct(Block::builtInRegistryHolder, Blocks.GRASS_BLOCK, Blocks.STONE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE), HolderSet.direct(Block::builtInRegistryHolder, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.PACKED_MUD)));

>>>>>>> dev
	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder().add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1).add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1).build();

	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL = key("patch_cactus_tall");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> POLLEN = key("pollen");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_SHELF_FUNGUS = key("brown_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> RED_SHELF_FUNGUS = key("red_shelf_fungus");
	public static final ResourceKey<ConfiguredFeature<?, ?>> CATTAIL = key("cattail");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_ALGAE = key("patch_algae");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TERMITE = key("termite_mound_baobab");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_GRASS = key("oasis_grass");
	public static final ResourceKey<ConfiguredFeature<?, ?>> OASIS_BUSH = key("oasis_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BUSH_FLOWER_FIELD = key("bush_flower_field");
	public static final ResourceKey<ConfiguredFeature<?, ?>> DESERT_BUSH = key("desert_bush");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_OASIS = key("patch_cactus_oasis");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_SAND = key("badlands_bush_sand");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BADLANDS_BUSH_TERRACOTTA = key("badlands_bush_terracotta");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CACTUS_TALL_BADLANDS = key("patch_cactus_tall_badlands");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PRICKLY_PEAR = key("prickly_pear");
	public static final ResourceKey<ConfiguredFeature<?, ?>> TUMBLEWEED = key("tumbleweed");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA = key("mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> BLUE_MESOGLEA_POOL = key("mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = key("jellyfish_caves_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA = key("mesoglea_with_dripleaves");
	public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_MESOGLEA_POOL = key("purple_mesoglea_pool");
	public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("jellyfish_caves_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = key("upside_down_purple_mesoglea");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST = key("nematocyst");
	public static final ResourceKey<ConfiguredFeature<?, ?>> NEMATOCYST_PURPLE = key("nematocyst_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MESOGLEA_CLUSTER_PURPLE = key("mesoglea_cluster_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> MESOGLEA_CLUSTER_BLUE = key("mesoglea_cluster_blue");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MESOGLEA_PURPLE = key("large_mesoglea_purple");
	public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MESOGLEA_BLUE = key("large_mesoglea_blue");
	public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_SPONGE = key("small_sponges");

	public static ResourceKey<ConfiguredFeature<?, ?>> key(String path) {
		return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(WilderSharedConstants.MOD_ID, path));
	}
}
