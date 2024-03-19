/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.features.feature;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenFeatures;
import net.frozenblock.lib.worldgen.feature.api.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.wilderwild.block.impl.FlowerColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterFeatures;
import net.frozenblock.wilderwild.registry.RegisterProperties;
import net.frozenblock.wilderwild.tag.WilderBlockTags;
import static net.frozenblock.wilderwild.world.features.feature.WilderFeatureUtils.register;
import net.frozenblock.wilderwild.world.impl.features.config.AlgaeFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.CattailFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.LargeMesogleaConfig;
import net.frozenblock.wilderwild.world.impl.features.config.ShelfFungusFeatureConfig;
import net.frozenblock.wilderwild.world.impl.features.config.SmallSpongeFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomBooleanFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

public final class WilderConfiguredFeatures {

	//FALLEN TREES
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED = register("fallen_trees_mixed");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_MIXED = register("mossy_fallen_trees_mixed");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_OAK_AND_BIRCH = register("mossy_fallen_trees_oak_and_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_SPRUCE = register("fallen_birch_and_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH = register("fallen_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_CHERRY = register("fallen_cherry");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE = register("fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CLEAN_FALLEN_SPRUCE = register("clean_fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_OAK_SWAMP = register("fallen_oak_swamp");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DECORATED_LARGE_FALLEN_SPRUCE = register("decorated_large_fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CLEAN_LARGE_FALLEN_SPRUCE = register("clean_large_fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE_AND_OAK = register("fallen_spruce_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_OAK = register("fallen_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_CYPRESS_AND_OAK = register("fallen_cypress_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_ACACIA_AND_OAK = register("fallen_acacia_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_LARGE_JUNGLE = register("fallen_large_jungle");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_PALM_AND_JUNGLE_AND_OAK = register("fallen_palm_and_jungle_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_OAK_AND_BIRCH_DARK_FOREST = register("fallen_oak_and_birch_dark_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_MANGROVE = register("fallen_mangrove");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_DARK_OAKS = register("fallen_dark_oaks");

	//TREES
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_PLAINS = register("trees_plains");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FIELD = register("trees_flower_field");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_AND_OAK = register("trees_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_DYING_FOREST = register("trees_dying_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SNOWY_DYING_FOREST = register("trees_snowy_dying_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_DYING_MIXED_FOREST = register("trees_dying_mixed_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SNOWY_DYING_MIXED_FOREST = register("trees_snowy_dying_mixed_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SEMI_BIRCH_AND_OAK = register("trees_semi_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH = register("trees_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_TALL = register("trees_birch_tall");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FOREST = register("trees_flower_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MIXED_TREES = register("mixed_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TEMPERATE_RAINFOREST_TREES = register("temperate_rainforest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> RAINFOREST_TREES = register("rainforest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_TAIGA_TREES = register("birch_taiga_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_BIRCH_TAIGA_TREES = register("old_growth_birch_taiga_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_JUNGLE_TREES = register("birch_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SPARSE_BIRCH_JUNGLE_TREES = register("sparse_birch_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_FOREST_VEGETATION = register("dark_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_BIRCH_FOREST_VEGETATION = register("dark_birch_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_TAIGA_VEGETATION = register("dark_taiga_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_TAIGA = register("trees_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_TREES_TAIGA = register("short_trees_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE = register("short_mega_spruce_configured");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE_ON_SNOW = register("short_mega_spruce_on_snow_configured");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_PINE_TAIGA = register("trees_old_growth_pine_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = register("trees_old_growth_spruce_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_GROVE = register("trees_grove");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_WINDSWEPT_HILLS = register("trees_windswept_hills");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MEADOW_TREES = register("meadow_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SAVANNA_TREES = register("savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_SAVANNA_TREES = register("arid_savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PARCHED_FOREST_TREES = register("parched_forest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_FOREST_TREES = register("arid_forest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_SAPLING = register("cypress_wetlands_trees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> WOODED_BADLANDS_TREES = register("wooded_badlands_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIG_COARSE_SHRUBS = register("big_coarse_shrubs");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHRUBS = register("shrubs");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS = register("palms");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_JUNGLE = register("palms_jungle");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_OASIS = register("palms_oasis");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CHERRIES = register("cherries");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCHES = register("snapped_birches");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_OAKS = register("snapped_oaks");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_OAK = register("snapped_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_SPRUCES = register("snapped_spruces");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_LARGE_SPRUCES = register("snapped_large_spruces");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_SPRUCES_ON_SNOW = register("snapped_spruces_on_snow");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_LARGE_SPRUCES_ON_SNOW = register("snapped_large_spruces_on_snow");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = register("snapped_birch_and_oak_and_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_SPRUCE = register("snapped_birch_and_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_CYPRESSES = register("snapped_cypresses");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_JUNGLES = register("snapped_jungles");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_LARGE_JUNGLES = register("snapped_large_jungles");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_JUNGLE = register("snapped_birch_and_jungle");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_ACACIAS = register("snapped_acacias");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_ACACIA_AND_OAK = register("snapped_acacia_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_CHERRY = register("snapped_cherry");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_DARK_OAKS = register("snapped_dark_oaks");
	//FLOWERS
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SEEDING_DANDELION = register("seeding_dandelion");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> CARNATION = register("carnation");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DATURA = register("datura");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLAINS = register("flower_plain");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_MEADOW = register("flower_meadow");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MILKWEED = register("milkweed");
	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 3)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 3)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 1)
		.build();
	public static final SimpleWeightedRandomList<BlockState> GLORY_OF_THE_SNOW_JUNGLE_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.BLUE), 1)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PURPLE), 1)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.PINK), 2)
		.add(RegisterBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(RegisterProperties.FLOWER_COLOR, FlowerColor.WHITE), 2)
		.build();
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW = register("glory_of_the_snow");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW_JUNGLE = register("glory_of_the_snow_jungle");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_FLOWER_FIELD = register("flower_flower_field");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MOSS_CARPET = register("moss_carpet");
	public static final SimpleWeightedRandomList<BlockState> FLOWERS_CYPRESS_WETLANDS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 3)
		.add(RegisterBlocks.DATURA.defaultBlockState(), 2)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 1)
		.add(Blocks.LILAC.defaultBlockState(), 3)
		.add(Blocks.DANDELION.defaultBlockState(), 1)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 2)
		.add(Blocks.POPPY.defaultBlockState(), 1)
		.add(RegisterBlocks.CARNATION.defaultBlockState(), 2)
		.add(Blocks.ORANGE_TULIP.defaultBlockState(), 1)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 1)
		.add(Blocks.RED_TULIP.defaultBlockState(), 1)
		.add(Blocks.WHITE_TULIP.defaultBlockState(), 1)
		.add(Blocks.OXEYE_DAISY.defaultBlockState(), 3)
		.add(Blocks.ALLIUM.defaultBlockState(), 3)
		.add(Blocks.ROSE_BUSH.defaultBlockState(), 3)
		.add(Blocks.AZURE_BLUET.defaultBlockState(), 3)
		.add(Blocks.LILY_OF_THE_VALLEY.defaultBlockState(), 3)
		.add(Blocks.PEONY.defaultBlockState(), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_TEMPERATE_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 3)
		.add(RegisterBlocks.DATURA.defaultBlockState(), 5)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 5)
		.add(Blocks.LILAC.defaultBlockState(), 6)
		.add(Blocks.DANDELION.defaultBlockState(), 10)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 8)
		.add(Blocks.POPPY.defaultBlockState(), 10)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_TEMPERATE_RAINFOREST_VANILLA_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.LILAC.defaultBlockState(), 6)
		.add(Blocks.DANDELION.defaultBlockState(), 10)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 8)
		.add(Blocks.POPPY.defaultBlockState(), 10)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_RAINFOREST_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 5)
		.add(RegisterBlocks.DATURA.defaultBlockState(), 7)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 7)
		.add(Blocks.LILAC.defaultBlockState(), 3)
		.add(Blocks.DANDELION.defaultBlockState(), 4)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 3)
		.add(Blocks.POPPY.defaultBlockState(), 4)
		.add(RegisterBlocks.CARNATION.defaultBlockState(), 8)
		.add(Blocks.ALLIUM.defaultBlockState(), 2)
		.add(Blocks.PEONY.defaultBlockState(), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_RAINFOREST_VANILLA_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.LILAC.defaultBlockState(), 3)
		.add(Blocks.DANDELION.defaultBlockState(), 4)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 3)
		.add(Blocks.POPPY.defaultBlockState(), 4)
		.add(Blocks.ALLIUM.defaultBlockState(), 2)
		.add(Blocks.PEONY.defaultBlockState(), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_JUNGLE_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 5)
		.add(RegisterBlocks.DATURA.defaultBlockState(), 1)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 4)
		.add(Blocks.LILAC.defaultBlockState(), 3)
		.add(Blocks.DANDELION.defaultBlockState(), 6)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 3)
		.add(Blocks.POPPY.defaultBlockState(), 4)
		.add(RegisterBlocks.CARNATION.defaultBlockState(), 3)
		.add(Blocks.ORANGE_TULIP.defaultBlockState(), 1)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 1)
		.add(Blocks.RED_TULIP.defaultBlockState(), 1)
		.add(Blocks.WHITE_TULIP.defaultBlockState(), 1)
		.add(Blocks.ALLIUM.defaultBlockState(), 3)
		.add(Blocks.PEONY.defaultBlockState(), 3)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_SUNFLOWER_PLAINS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.SUNFLOWER.defaultBlockState(), 12)
		.add(Blocks.DANDELION.defaultBlockState(), 50)
		.add(Blocks.ORANGE_TULIP.defaultBlockState(), 12)
		.add(Blocks.AZURE_BLUET.defaultBlockState(), 1)
		.add(Blocks.OXEYE_DAISY.defaultBlockState(), 1)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_CHERRY_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.DATURA.defaultBlockState(), 2)
		.add(Blocks.LILAC.defaultBlockState(), 7)
		.add(Blocks.POPPY.defaultBlockState(), 9)
		.add(Blocks.ROSE_BUSH.defaultBlockState(), 3)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 5)
		.add(Blocks.PEONY.defaultBlockState(), 1)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_BIRCH_CLEARING_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 1)
		.add(RegisterBlocks.DATURA.defaultBlockState(), 1)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 4)
		.add(Blocks.LILAC.defaultBlockState(), 1)
		.add(Blocks.BLUE_ORCHID.defaultBlockState(), 2)
		.add(Blocks.CORNFLOWER.defaultBlockState(), 6)
		.add(RegisterBlocks.CARNATION.defaultBlockState(), 3)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 3)
		.add(Blocks.WHITE_TULIP.defaultBlockState(), 3)
		.add(Blocks.ALLIUM.defaultBlockState(), 2)
		.add(Blocks.PEONY.defaultBlockState(), 1)
		.build();

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_FOREST_CLEARING_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.MILKWEED.defaultBlockState(), 1)
		.add(Blocks.SUNFLOWER.defaultBlockState(), 1)
		.add(RegisterBlocks.SEEDING_DANDELION.defaultBlockState(), 3)
		.add(Blocks.CORNFLOWER.defaultBlockState(), 2)
		.add(RegisterBlocks.CARNATION.defaultBlockState(), 3)
		.add(Blocks.DANDELION.defaultBlockState(), 3)
		.add(Blocks.POPPY.defaultBlockState(), 5)
		.add(Blocks.ORANGE_TULIP.defaultBlockState(), 2)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 1)
		.add(Blocks.RED_TULIP.defaultBlockState(), 2)
		.add(Blocks.WHITE_TULIP.defaultBlockState(), 2)
		.add(Blocks.ALLIUM.defaultBlockState(), 1)
		.add(Blocks.PEONY.defaultBlockState(), 1)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_CYPRESS_WETLANDS = register("flowers_cypress_wetlands");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_TEMPERATE_RAINFOREST = register("flowers_temperate_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_TEMPERATE_RAINFOREST_VANILLA = register("flowers_temperate_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MUSHROOMS_DARK_FOREST = register("mushroom_dark_forest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_RAINFOREST = register("flowers_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_RAINFOREST_VANILLA = register("flowers_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_JUNGLE = register("flowers_jungle");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> TALL_FLOWER_FLOWER_FIELD = register("tall_flower_flower_field");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_CHERRY = register("flowers_cherry");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_SUNFLOWER_PLAINS = register("flowers_sunflower_plains");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_BIRCH_CLEARING = register("flowers_birch_clearing");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_FOREST_CLEARING = register("flowers_forest_clearing");


	//VEGETATION
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
		.add(Blocks.GRASS.defaultBlockState(), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> DEAD_BUSH_AND_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 5)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> BUSH_AND_DEAD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 2)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> JUNGLE_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> SPARSE_JUNGLE_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 3)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_GRASS = register("oasis_grass");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_BUSH = register("oasis_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DEAD_BUSH_AND_BUSH = register("dead_bush_and_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BUSH_AND_DEAD_BUSH = register("bush_and_dead_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> JUNGLE_BUSH = register("jungle_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SPARSE_BUSH = register("sparse_bush");

	public static final SimpleWeightedRandomList<BlockState> FLOWER_FIELD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_FIELD_BUSH = register("flower_field_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GENERIC_BUSH = register("bush");
	public static final SimpleWeightedRandomList<BlockState> DESERT_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(RegisterBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 4)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DESERT_BUSH = register("desert_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_SAND = register("badlands_bush_sand");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_TERRACOTTA = register("badlands_bush_terracotta");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> WOODED_BADLANDS_BUSH_TERRACOTTA = register("wooded_badlands_bush_terracotta");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_OASIS = register("patch_cactus_oasis");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL = register("patch_cactus_tall");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL_BADLANDS = register("patch_cactus_tall_badlands");

	public static final SimpleWeightedRandomList<BlockState> PRICKLY_PEAR_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
		.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
		.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
		.add(RegisterBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 4)
		.add(Blocks.CACTUS.defaultBlockState(), 2)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PRICKLY_PEAR = register("prickly_pear");

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS = register("large_fern_and_grass");

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS_2 = register("large_fern_and_grass_2");

	public static final SimpleWeightedRandomList<BlockState> FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.GRASS.defaultBlockState(), 3)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();

	public static final SimpleWeightedRandomList<BlockState> GRASS_AND_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.GRASS.defaultBlockState(), 11)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();
	public static final SimpleWeightedRandomList<BlockState> TALL_GRASS_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 1)
		.add(Blocks.GRASS.defaultBlockState(), 4)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_GRASS_AND_GRASS_WATER = register("tall_grass_and_grass_water");

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FERN_AND_GRASS = register("fern_and_grass");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GRASS_AND_FERN = register("grass_and_fern");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> POLLEN_CONFIGURED = register("pollen");
	public static final FrozenConfiguredFeature<ShelfFungusFeatureConfig, ConfiguredFeature<ShelfFungusFeatureConfig, ?>> BROWN_SHELF_FUNGUS_CONFIGURED = register("brown_shelf_fungus");
	public static final FrozenConfiguredFeature<ShelfFungusFeatureConfig, ConfiguredFeature<ShelfFungusFeatureConfig, ?>> RED_SHELF_FUNGUS_CONFIGURED = register("red_shelf_fungus");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL = register("cattail");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_SMALL = register("cattail_small");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_MUD = register("cattail_mud");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_MUD_SMALL = register("cattail_mud_small");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERING_WATERLILY = register("patch_flowering_waterlily");
	public static final FrozenConfiguredFeature<AlgaeFeatureConfig, ConfiguredFeature<AlgaeFeatureConfig, ?>> PATCH_ALGAE = register("patch_algae");
	public static final FrozenConfiguredFeature<AlgaeFeatureConfig, ConfiguredFeature<AlgaeFeatureConfig, ?>> PATCH_ALGAE_SMALL = register("patch_algae_small");
	public static final FrozenConfiguredFeature<ColumnWithDiskFeatureConfig, ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_CONFIGURED = register("termite_mound_baobab");

	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1)
		.add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1)
		.add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1)
		.add(RegisterBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TUMBLEWEED = register("tumbleweed");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_PURPLE = register("mesoglea_cluster_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> MESOGLEA_CLUSTER_BLUE = register("mesoglea_cluster_blue");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA = register("mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> BLUE_MESOGLEA_POOL = register("mesoglea_pool");
	public static final FrozenConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> JELLYFISH_CAVES_BLUE_MESOGLEA = register("jellyfish_caves_blue_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_BLUE_MESOGLEA = register("upside_down_blue_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA = register("mesoglea_with_dripleaves");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> PURPLE_MESOGLEA_POOL = register("purple_mesoglea_pool");
	public static final FrozenConfiguredFeature<RandomBooleanFeatureConfiguration, ConfiguredFeature<RandomBooleanFeatureConfiguration, ?>> JELLYFISH_CAVES_PURPLE_MESOGLEA = register("jellyfish_caves_purple_mesoglea");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_PURPLE_MESOGLEA = register("upside_down_purple_mesoglea");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_BLUE = register("nematocyst_blue");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> NEMATOCYST_PURPLE = register("nematocyst_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_PURPLE = register("large_mesoglea_purple");
	public static final FrozenConfiguredFeature<LargeMesogleaConfig, ConfiguredFeature<LargeMesogleaConfig, ?>> LARGE_MESOGLEA_BLUE = register("large_mesoglea_blue");
	public static final FrozenConfiguredFeature<SmallSpongeFeatureConfig, ConfiguredFeature<SmallSpongeFeatureConfig, ?>> SMALL_SPONGE = register("small_sponges");
	public static final FrozenConfiguredFeature<VegetationPatchConfiguration, ConfiguredFeature<VegetationPatchConfiguration, ?>> UPSIDE_DOWN_MAGMA = register("upside_down_magma");

	private WilderConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

	public static void registerConfiguredFeatures(@NotNull BootstapContext<ConfiguredFeature<?, ?>> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WilderSharedConstants.logWithModId("Registering WilderConfiguredFeatures for", true);

		FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.4F)),
				new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.3F)), WilderTreePlaced.FALLEN_OAK_CHECKED.getHolder()));

		MOSSY_FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED.getHolder(), 0.15F)),
				new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.1F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()));

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.15F)),
				new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.15F)), WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()));

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.6F)),
				new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.4F)), WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()));

		FALLEN_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder()));

		FALLEN_CHERRY.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of((new WeightedPlacedFeature(WilderTreePlaced.FALLEN_CHERRY_CHECKED.getHolder(), 0.6F)),
				new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.4F)), WilderTreePlaced.FALLEN_CHERRY_CHECKED.getHolder()));

		FALLEN_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()));

		CLEAN_FALLEN_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.CLEAN_FALLEN_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.CLEAN_FALLEN_SPRUCE_CHECKED.getHolder()));

		FALLEN_OAK_SWAMP.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_STRAIGHT_OAK_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.MOSSY_FALLEN_STRAIGHT_OAK_CHECKED.getHolder()));

		DECORATED_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()
			)
		);

		CLEAN_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()
			)
		);

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.55F)),
				WilderTreePlaced.FALLEN_OAK_CHECKED.getHolder()));

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.35F)),
				WilderTreePlaced.FALLEN_OAK_CHECKED.getHolder()));

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_OAK_CHECKED.getHolder(), 0.35F)),
				WilderTreePlaced.FALLEN_CYPRESS_CHECKED.getHolder()));

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FALLEN_ACACIA_CHECKED.getHolder(), 0.7F)),
				WilderTreePlaced.FALLEN_OAK_NO_MOSS_CHECKED.getHolder()));

		FALLEN_LARGE_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_FALLEN_JUNGLE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.FALLEN_PALM_CHECKED.getHolder(), 0.135F),
				new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)),
				WilderTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()));

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.135F),
				new WeightedPlacedFeature(WilderTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)),
				WilderTreePlaced.FALLEN_OAK_CHECKED.getHolder()));

		FALLEN_MANGROVE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.FALLEN_MANGROVE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.FALLEN_MANGROVE_CHECKED.getHolder()));

		FALLEN_DARK_OAKS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_FALLEN_DARK_OAK_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_FALLEN_DARK_OAK_CHECKED.getHolder()
			)
		);

		TREES_PLAINS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_0004.getHolder()), 0.04F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_0004.getHolder()), 0.02F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK.getHolder()), 0.1F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHRUB.getHolder()), 0.35F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIG_SHRUB.getHolder()), 0.6F)),
				PlacementUtils.inlinePlaced(WilderTreeConfigured.BIG_SHRUB.getHolder())));

		TREES_FLOWER_FIELD.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_OAK_BEES_025.getHolder()), 0.2F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.FANCY_DYING_OAK_BEES_025.getHolder()), 0.09F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIRCH_BEES_025.getHolder()), 0.1F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.BIG_SHRUB.getHolder()), 0.5F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHRUB.getHolder()), 0.3F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WilderTreeConfigured.SHORT_OAK.getHolder()), 0.169F)),
				PlacementUtils.inlinePlaced(WilderTreeConfigured.OAK_BEES_0004.getHolder())
			)
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.26F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.055F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.155F)),
				WilderTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_BIRCH.getHolder(), 0.07F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.015F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.06F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.35F),
				new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.033F)), WilderTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_BIRCH.getHolder(), 0.52F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.483F)), WilderTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.13F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_BIRCH.getHolder(), 0.07F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.015F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.06F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.35F),
				new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.033F)), WilderTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.13F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_BIRCH.getHolder(), 0.52F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
				new WeightedPlacedFeature(WilderTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.483F)), WilderTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.06F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.13F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.14F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.SUPER_BIRCH.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_BEES_0004.getHolder(), 0.025F)), WilderTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.065F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.012F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.035F)), WilderTreePlaced.BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.002F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.0001F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.032F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_BEES_0004.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.017F)), WilderTreePlaced.SUPER_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.035F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.05F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.063F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.205F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.095F)), WilderTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		MIXED_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.13F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.27F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.23F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.325F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.042F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.061F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.05F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.025F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.09F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.4F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.72F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder(), 0.6F)), WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.085F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.12F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.27F),
				new WeightedPlacedFeature(WilderTreePlaced.OLD_DYING_FANCY_OAK_BEES_0004.getHolder(), 0.15F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.072F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.120F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.098F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.37F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.21F)), WilderTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.155F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.37F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.455F)), WilderTreePlaced.BIRCH_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.155F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.37F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.355F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.1F)), WilderTreePlaced.SUPER_BIRCH.getHolder()
			)
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.049F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.069F),
				new WeightedPlacedFeature(WilderTreePlaced.SUPER_BIRCH.getHolder(), 0.049F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.049F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.079F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.119F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.MEGA_JUNGLE_TREE_CHECKED), 0.165F)), placedFeatures.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED)
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.FANCY_OAK_CHECKED), 0.07F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.055F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.089F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.027F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.059F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.069F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)),
				placedFeatures.getOrThrow(TreePlacements.JUNGLE_TREE_CHECKED)
			)
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED), 0.55F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.015F),
				new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.32F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.027F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.012F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.185F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED), 0.55F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.255F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.04F),
				new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.6F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.522F),
				new WeightedPlacedFeature(WilderTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.getHolder(), 0.018F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.0766F),
				new WeightedPlacedFeature(WilderTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.035F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.222F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.095F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.24F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
				new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED), 0.235F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.35F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.015F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.4F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.015F),
				new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.15F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.095F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.027F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.012F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.15F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.155F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.19F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED), 0.235F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.12F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.004F),
				new WeightedPlacedFeature(WilderTreePlaced.BIRCH_CHECKED.getHolder(), 0.1F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.005F),
				new WeightedPlacedFeature(WilderTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.2F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.08F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.024F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.031F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.015F)), placedFeatures.getOrThrow(TreePlacements.DARK_OAK_CHECKED)
			)
		);

		TREES_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F)), WilderTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_TREES_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.33333334F)),
				WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.43333334F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.125F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED.getHolder(), 0.125F)
				),
				WilderTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.43333334F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.125F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW.getHolder(), 0.125F)
				),
				WilderTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.025641026F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.028F),
				new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.30769232F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.045F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.33333334F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)), WilderTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F),
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.0255F),
				new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.18333334F),
				new WeightedPlacedFeature(WilderTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.255F)), WilderTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TREES_GROVE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FUNGUS_PINE_ON_SNOW.getHolder(), 0.33333334F)), WilderTreePlaced.SPRUCE_ON_SNOW.getHolder())
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SPRUCE_CHECKED.getHolder(), 0.666F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.1F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		MEADOW_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_BEES.getHolder(), 0.5F)), WilderTreePlaced.SUPER_BIRCH_BEES.getHolder())
		);

		SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
				new WeightedPlacedFeature(WilderTreePlaced.BAOBAB.getHolder(), 0.062F),
				new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL.getHolder(), 0.035F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)), WilderTreePlaced.OAK_CHECKED.getHolder())
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
				new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.08F),
				new WeightedPlacedFeature(WilderTreePlaced.BAOBAB.getHolder(), 0.065F),
				new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED.getHolder(), 0.052F),
				new WeightedPlacedFeature(WilderTreePlaced.BAOBAB_TALL.getHolder(), 0.02F)), placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.59F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.186F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WilderTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.155F),
				new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.37F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_BIRCH.getHolder(), 0.155F)), WilderTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		ARID_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.7085F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.175F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.38F),
				new WeightedPlacedFeature(WilderTreePlaced.DYING_BIRCH.getHolder(), 0.2325F)), WilderTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(
				new WeightedPlacedFeature(WilderTreePlaced.CYPRESS.getHolder(), 0.37F),
				new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS.getHolder(), 0.25F),
				new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F),
				new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.1F)), WilderTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.CYPRESS.getHolder(), 0.4F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_CYPRESS.getHolder(), 0.15F),
					new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F)
				),
				WilderTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SWAMP_CYPRESS.getHolder(), 0.85F)),
				WilderTreePlaced.SWAMP_CYPRESS.getHolder()
			)
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.OAK_CHECKED.getHolder(), 0.095F),
					new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_COARSE_GRASS_CHECKED.getHolder(), 0.4F),
					new WeightedPlacedFeature(WilderTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.67F),
					new WeightedPlacedFeature(WilderTreePlaced.JUNIPER.getHolder(), 0.2F)
				),
				WilderTreePlaced.JUNIPER.getHolder()
			)
		);

		BIG_COARSE_SHRUBS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_COARSE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.BIG_SHRUB_COARSE_CHECKED.getHolder())
		);

		SHRUBS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.SHRUB_CHECKED.getHolder(), 0.3F),
					new WeightedPlacedFeature(WilderTreePlaced.BIG_SHRUB_CHECKED.getHolder(), 0.6F)
				),
				WilderTreePlaced.BIG_SHRUB_CHECKED.getHolder()
			)
		);

		PALMS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.4F)
				),
				WilderTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		PALMS_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.25F),
					new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.7F),
					new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED_DIRT.getHolder(), 0.4F)
				),
				WilderTreePlaced.PALM_CHECKED_DIRT.getHolder()
			)
		);

		PALMS_OASIS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.5F),
					new WeightedPlacedFeature(WilderTreePlaced.TALL_WINE_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WilderTreePlaced.SMALL_WINE_PALM_CHECKED.getHolder(), 0.37F)
				),
				WilderTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		CHERRIES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.CHERRY_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WilderTreePlaced.DYING_CHERRY_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WilderTreePlaced.TALL_CHERRY_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WilderTreePlaced.TALL_DYING_CHERRY_CHECKED.getHolder(), 0.0785F)
				),
				WilderTreePlaced.CHERRY_BEES_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCHES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder()
			)
		);

		SNAPPED_OAKS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_OAK_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.3F)),
				WilderTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_SPRUCES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_SPRUCE_ON_SNOW.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_SPRUCE_ON_SNOW.getHolder()
			)
		);

		SNAPPED_LARGE_SPRUCES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_SNAPPED_SPRUCE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_LARGE_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder(), 0.25F)
				),
				WilderTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.5F)),
				WilderTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_CYPRESSES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_CYPRESS_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_CYPRESS_CHECKED.getHolder()
			)
		);

		SNAPPED_JUNGLES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_LARGE_JUNGLES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_SNAPPED_JUNGLE_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.35F)),
				WilderTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_ACACIAS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()
			)
		);

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_OAK_CHECKED.getHolder(), 0.3F)),
				WilderTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()
			)
		);

		SNAPPED_CHERRY.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.SNAPPED_CHERRY_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.SNAPPED_CHERRY_CHECKED.getHolder()
			)
		);

		SNAPPED_DARK_OAKS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WilderTreePlaced.LARGE_SNAPPED_DARK_OAK_CHECKED.getHolder(), 1.0F)),
				WilderTreePlaced.LARGE_SNAPPED_DARK_OAK_CHECKED.getHolder()
			)
		);

		// FLOWERS

		SEEDING_DANDELION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.SEEDING_DANDELION))
				)
			)
		);

		CARNATION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.CARNATION))
				)
			)
		);

		DATURA.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				64,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.DATURA))
				)
			)
		);

		FLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				22,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1.0),
							0.020833334F,
							List.of(
								RegisterBlocks.SEEDING_DANDELION.defaultBlockState(),
								RegisterBlocks.CARNATION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.WHITE_TULIP.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWER_MEADOW.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				20,
				8,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new DualNoiseProvider(
							new InclusiveRange<>(1, 3),
							new NormalNoise.NoiseParameters(-10, 1.0),
							1.0f,
							2345L,
							new NormalNoise.NoiseParameters(-3, 1.0),
							1.0f,
							List.of(
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								RegisterBlocks.CARNATION.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								RegisterBlocks.SEEDING_DANDELION.defaultBlockState()
							)
						)
					)
				)
			)
		);

		MILKWEED.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED))
				)
			)
		);

		GLORY_OF_THE_SNOW.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				64,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_POOL))
				)
			)
		);

		GLORY_OF_THE_SNOW_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(GLORY_OF_THE_SNOW_JUNGLE_POOL))
				)
			)
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				100,
				8,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1.0),
							0.020833334F,
							List.of(
								RegisterBlocks.SEEDING_DANDELION.defaultBlockState(),
								RegisterBlocks.CARNATION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.WHITE_TULIP.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
							)
						)
					)
				)
			)
		);


		MOSS_CARPET.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				25,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.MOSS_CARPET)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		FLOWERS_CYPRESS_WETLANDS.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				38,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_CYPRESS_WETLANDS_POOL))
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_TEMPERATE_RAINFOREST_POOL))
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_TEMPERATE_RAINFOREST_VANILLA_POOL))
				)
			)
		);

		MUSHROOMS_DARK_FOREST.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				50,
				4,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5234L,
							new NormalNoise.NoiseParameters(0, 1.0),
							0.020833334F,
							List.of(
								Blocks.RED_MUSHROOM.defaultBlockState(),
								Blocks.BROWN_MUSHROOM.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_RAINFOREST_POOL))
				)
			)
		);

		FLOWERS_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_RAINFOREST_VANILLA_POOL))
				)
			)
		);

		FLOWERS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_JUNGLE_POOL))
				)
			)
		);

		FLOWERS_SUNFLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				52,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_SUNFLOWER_PLAINS_POOL))
				)
			)
		);

		FLOWERS_BIRCH_CLEARING.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_BIRCH_CLEARING_POOL))
				)
			)
		);

		FLOWERS_FOREST_CLEARING.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_FOREST_CLEARING_POOL))
				)
			)
		);

		TALL_FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.MILKWEED)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH)),
							List.of(),
							9
						)
					),
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_PATCH,
						FeatureUtils.simplePatchConfiguration(
							Feature.SIMPLE_BLOCK,
							new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY)),
							List.of(),
							9
						)
					)
				)
			)
		);

		FLOWERS_CHERRY.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				36,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWERS_CHERRY_POOL))
				)
			)
		);

		OASIS_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				35,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_GRASS_POOL))
				)
			)
		);

		OASIS_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				23,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_BUSH_POOL))
				)
			)
		);

		DEAD_BUSH_AND_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DEAD_BUSH_AND_BUSH_POOL))
				)
			)
		);

		BUSH_AND_DEAD_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(BUSH_AND_DEAD_BUSH_POOL))
				)
			)
		);

		JUNGLE_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(JUNGLE_BUSH_POOL))
				)
			)
		);

		SPARSE_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SPARSE_JUNGLE_BUSH_POOL))
				)
			)
		);

		FLOWER_FIELD_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWER_FIELD_BUSH_POOL))
				)
			)
		);

		GENERIC_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(BUSH_POOL))
				)
			)
		);

		DESERT_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL))
				)
			)
		);

		BADLANDS_BUSH_SAND.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(DESERT_BUSH_POOL)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		BADLANDS_BUSH_TERRACOTTA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				6,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new WeightedStateProvider(DESERT_BUSH_POOL)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND)))
					)
				)
			)
		);

		WOODED_BADLANDS_BUSH_TERRACOTTA.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.not(BlockPredicate.matchesTag(BlockTags.SAND))
						)
					)
				)
			)
		);

		PATCH_CACTUS_OASIS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				10,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
					BlockColumnConfiguration.simple(
						BiasedToBottomInt.of(3, 5),
						BlockStateProvider.simple(Blocks.CACTUS)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		PATCH_CACTUS_TALL.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
					BlockColumnConfiguration.simple(
						BiasedToBottomInt.of(4, 5),
						BlockStateProvider.simple(Blocks.CACTUS)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		PATCH_CACTUS_TALL_BADLANDS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.inlinePlaced(
					Feature.BLOCK_COLUMN,
					BlockColumnConfiguration.simple(
						BiasedToBottomInt.of(2, 6),
						BlockStateProvider.simple(Blocks.CACTUS)
					),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.wouldSurvive(Blocks.CACTUS.defaultBlockState(), BlockPos.ZERO)
						)
					)
				)
			)
		);

		PRICKLY_PEAR.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				20,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(PRICKLY_PEAR_POOL))
				)
			)
		);

		LARGE_FERN_AND_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				36,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL))
				)
			)
		);

		LARGE_FERN_AND_GRASS_2.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				36,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(LARGE_FERN_AND_GRASS_POOL_2))
				)
			)
		);

		TALL_GRASS_AND_GRASS_WATER.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				16,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(TALL_GRASS_AND_GRASS_POOL))
				)
			)
		);

		FERN_AND_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				32,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FERN_AND_GRASS_POOL))
				)
			)
		);

		GRASS_AND_FERN.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				32,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(GRASS_AND_FERN_POOL))
				)
			)
		);

		POLLEN_CONFIGURED.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				RegisterBlocks.POLLEN,
				20,
				true,
				true,
				true,
				0.5F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.POLLEN_FEATURE_PLACEABLE
				)
			)
		);

		BROWN_SHELF_FUNGUS_CONFIGURED.makeAndSetHolder(RegisterFeatures.SHELF_FUNGUS_FEATURE,
			new ShelfFungusFeatureConfig(
				RegisterBlocks.BROWN_SHELF_FUNGUS,
				20,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.SHELF_FUNGUS_FEATURE_PLACEABLE
				)
			)
		);

		RED_SHELF_FUNGUS_CONFIGURED.makeAndSetHolder(RegisterFeatures.SHELF_FUNGUS_FEATURE,
			new ShelfFungusFeatureConfig(
				RegisterBlocks.RED_SHELF_FUNGUS,
				20,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.SHELF_FUNGUS_FEATURE_PLACEABLE
				)
			)
		);

		CATTAIL.makeAndSetHolder(RegisterFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 21),
				true,
				WilderBlockTags.CATTAIL_PLACEABLE
			)
		);

		CATTAIL_SMALL.makeAndSetHolder(RegisterFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-5, 5),
				UniformInt.of(6, 18),
				true,
				WilderBlockTags.CATTAIL_PLACEABLE
			)
		);

		CATTAIL_MUD.makeAndSetHolder(RegisterFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 21),
				false,
				WilderBlockTags.CATTAIL_MUD_PLACEABLE
			)
		);

		CATTAIL_MUD_SMALL.makeAndSetHolder(RegisterFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-5, 5),
				UniformInt.of(6, 18),
				false,
				WilderBlockTags.CATTAIL_MUD_PLACEABLE
			)
		);

		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				10,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(RegisterBlocks.FLOWERING_LILY_PAD))
				)
			)
		);

		PATCH_ALGAE.makeAndSetHolder(RegisterFeatures.ALGAE_FEATURE,
			new AlgaeFeatureConfig(UniformInt.of(4, 10))
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(RegisterFeatures.ALGAE_FEATURE,
			new AlgaeFeatureConfig(UniformInt.of(2, 6))
		);

		TERMITE_CONFIGURED.makeAndSetHolder(FrozenFeatures.COLUMN_WITH_DISK_FEATURE,
			new ColumnWithDiskFeatureConfig(
				RegisterBlocks.TERMITE_MOUND.defaultBlockState().setValue(RegisterProperties.NATURAL, true),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				UniformInt.of(1, 3),
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.TERMITE_DISC_REPLACEABLE
				),
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.TERMITE_DISC_BLOCKS
				)
			)
		);

		TUMBLEWEED.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				5,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(TUMBLEWEED_PLANT_POOL))
				)
			)
		);

		MESOGLEA_CLUSTER_PURPLE.makeAndSetHolder(RegisterFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		MESOGLEA_CLUSTER_BLUE.makeAndSetHolder(RegisterFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 10),
				BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				UniformFloat.of(0.2F, 0.75F),
				0.15F,
				UniformFloat.of(0.1F, 0.25F),
				UniformFloat.of(0.16F, 0.4F),
				UniformFloat.of(0.0F, 0.25F),
				5,
				0.2F
			)
		);

		BLUE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				2,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		BLUE_MESOGLEA_POOL.makeAndSetHolder(Feature.WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				5,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		JELLYFISH_CAVES_BLUE_MESOGLEA.makeAndSetHolder(Feature.RANDOM_BOOLEAN_SELECTOR,
			new RandomBooleanFeatureConfiguration(
				PlacementUtils.inlinePlaced(BLUE_MESOGLEA.getHolder()),
				PlacementUtils.inlinePlaced(BLUE_MESOGLEA_POOL.getHolder())
			)
		);

		UPSIDE_DOWN_BLUE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_BLUE_MESOGLEA_COLUMN.getHolder()),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		PURPLE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				2,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		PURPLE_MESOGLEA_POOL.makeAndSetHolder(Feature.WATERLOGGED_VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(CaveFeatures.DRIPLEAF)),
				CaveSurface.FLOOR,
				ConstantInt.of(3),
				0.8F,
				5,
				0.04F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		JELLYFISH_CAVES_PURPLE_MESOGLEA.makeAndSetHolder(Feature.RANDOM_BOOLEAN_SELECTOR,
			new RandomBooleanFeatureConfiguration(
				PlacementUtils.inlinePlaced(PURPLE_MESOGLEA.getHolder()),
				PlacementUtils.inlinePlaced(PURPLE_MESOGLEA_POOL.getHolder())
			)
		);

		UPSIDE_DOWN_PURPLE_MESOGLEA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				BlockTags.LUSH_GROUND_REPLACEABLE,
				BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_PURPLE_MESOGLEA_COLUMN.getHolder()),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(4, 14),
				0.7F
			)
		);

		NEMATOCYST_BLUE.makeAndSetHolder(RegisterFeatures.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
				RegisterBlocks.BLUE_PEARLESCENT_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.BLUE_NEMATOCYST_FEATURE_PLACEABLE
				)
			)
		);

		NEMATOCYST_PURPLE.makeAndSetHolder(RegisterFeatures.NEMATOCYST_FEATURE,
			new MultifaceGrowthConfiguration(
				RegisterBlocks.PURPLE_PEARLESCENT_NEMATOCYST,
				20,
				true,
				true,
				true,
				0.98F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WilderBlockTags.PURPLE_NEMATOCYST_FEATURE_PLACEABLE
				)
			)
		);

		LARGE_MESOGLEA_PURPLE.makeAndSetHolder(RegisterFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(RegisterBlocks.PURPLE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				UniformFloat.of(0.2F, 2.0F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1.0F),
				UniformFloat.of(0.0F, 0.3F),
				4,
				0.2F
			)
		);

		LARGE_MESOGLEA_BLUE.makeAndSetHolder(RegisterFeatures.LARGE_MESOGLEA_FEATURE,
			new LargeMesogleaConfig(
				30,
				UniformInt.of(3, 19),
				BlockStateProvider.simple(RegisterBlocks.BLUE_PEARLESCENT_MESOGLEA.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, true)),
				UniformFloat.of(0.2F, 2.0F),
				0.33F,
				UniformFloat.of(0.1F, 0.9F),
				UniformFloat.of(0.4F, 1.0F),
				UniformFloat.of(0.0F, 0.3F),
				4,
				0.2F
			)
		);

		SMALL_SPONGE.makeAndSetHolder(RegisterFeatures.SMALL_SPONGE_FEATURE,
			new SmallSpongeFeatureConfig(
				RegisterBlocks.SMALL_SPONGE,
				20,
				true,
				true,
				true,
				WilderBlockTags.SMALL_SPONGE_GROWS_ON
			)
		);

		UPSIDE_DOWN_MAGMA.makeAndSetHolder(Feature.VEGETATION_PATCH,
			new VegetationPatchConfiguration(
				WilderBlockTags.MAGMA_REPLACEABLE,
				BlockStateProvider.simple(Blocks.MAGMA_BLOCK.defaultBlockState()),
				PlacementUtils.inlinePlaced(WilderMiscConfigured.DOWNWARDS_MAGMA_COLUMN.getHolder()),
				CaveSurface.CEILING,
				ConstantInt.of(3),
				0.8F,
				2,
				0.08F,
				UniformInt.of(2, 6),
				0.7F
			)
		);
	}
}
