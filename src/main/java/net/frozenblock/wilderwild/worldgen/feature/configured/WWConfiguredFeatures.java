/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.feature.configured;

import java.util.List;
import net.frozenblock.lib.worldgen.feature.api.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.api.FrozenFeatures;
import net.frozenblock.lib.worldgen.feature.api.features.config.ColumnWithDiskFeatureConfig;
import net.frozenblock.lib.worldgen.feature.api.features.config.ComboFeatureConfig;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.state.properties.FlowerColor;
import net.frozenblock.wilderwild.registry.WWBlockStateProperties;
import net.frozenblock.wilderwild.registry.WWBlocks;
import net.frozenblock.wilderwild.registry.WWFeatures;
import net.frozenblock.wilderwild.tag.WWBlockTags;
import net.frozenblock.wilderwild.worldgen.feature.WWFeatureUtils;
import net.frozenblock.wilderwild.worldgen.feature.placed.WWTreePlaced;
import net.frozenblock.wilderwild.worldgen.impl.features.config.AlgaeFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.CattailFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.ShelfFungiFeatureConfig;
import net.frozenblock.wilderwild.worldgen.impl.features.config.SpongeBudFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.BiasedToBottomInt;
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
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import org.jetbrains.annotations.NotNull;

public final class WWConfiguredFeatures {
	// FALLEN TREES
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_TREES_MIXED = WWFeatureUtils.register("fallen_trees_mixed");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_MIXED = WWFeatureUtils.register("mossy_fallen_trees_mixed");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MOSSY_FALLEN_TREES_OAK_AND_BIRCH = WWFeatureUtils.register("mossy_fallen_trees_oak_and_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_SPRUCE = WWFeatureUtils.register("fallen_birch_and_spruce");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_BIRCH = WWFeatureUtils.register("fallen_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_CHERRY = WWFeatureUtils.register("fallen_cherry");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_SPRUCE = WWFeatureUtils.register("fallen_spruce");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CLEAN_FALLEN_SPRUCE = WWFeatureUtils.register("clean_fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SWAMP_TREES = WWFeatureUtils.register("fallen_swamp_trees");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> DECORATED_LARGE_FALLEN_SPRUCE = WWFeatureUtils.register("decorated_large_fallen_spruce");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> CLEAN_LARGE_FALLEN_SPRUCE = WWFeatureUtils.register("clean_large_fallen_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_SPRUCE_AND_OAK = WWFeatureUtils.register("fallen_spruce_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_BIRCH_AND_OAK = WWFeatureUtils.register("fallen_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_CYPRESS_AND_OAK = WWFeatureUtils.register("fallen_cypress_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_ACACIA_AND_OAK = WWFeatureUtils.register("fallen_acacia_and_oak");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_LARGE_JUNGLE = WWFeatureUtils.register("fallen_large_jungle");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_PALM_AND_JUNGLE_AND_OAK = WWFeatureUtils.register("fallen_palm_and_jungle_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> FALLEN_OAK_AND_BIRCH_DARK_FOREST = WWFeatureUtils.register("fallen_oak_and_birch_dark_forest");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_MANGROVE = WWFeatureUtils.register("fallen_mangrove");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_DARK_OAKS = WWFeatureUtils.register("fallen_dark_oaks");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> FALLEN_MAPLE = WWFeatureUtils.register("fallen_maple");

	// TREES
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_PLAINS = WWFeatureUtils.register("trees_plains");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FIELD = WWFeatureUtils.register("trees_flower_field");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_AND_OAK = WWFeatureUtils.register("trees_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_AND_OAK_CALM = WWFeatureUtils.register("trees_birch_and_oak_calm");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_DYING_FOREST = WWFeatureUtils.register("trees_dying_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SNOWY_DYING_FOREST = WWFeatureUtils.register("trees_snowy_dying_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_DYING_MIXED_FOREST = WWFeatureUtils.register("trees_dying_mixed_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SNOWY_DYING_MIXED_FOREST = WWFeatureUtils.register("trees_snowy_dying_mixed_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_SEMI_BIRCH_AND_OAK = WWFeatureUtils.register("trees_semi_birch_and_oak");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH = WWFeatureUtils.register("trees_birch");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_BIRCH_TALL = WWFeatureUtils.register("trees_birch_tall");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_FLOWER_FOREST = WWFeatureUtils.register("trees_flower_forest");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MIXED_TREES = WWFeatureUtils.register("mixed_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TEMPERATE_RAINFOREST_TREES = WWFeatureUtils.register("temperate_rainforest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> RAINFOREST_TREES = WWFeatureUtils.register("rainforest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_TAIGA_TREES = WWFeatureUtils.register("birch_taiga_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_BIRCH_TAIGA_TREES = WWFeatureUtils.register("old_growth_birch_taiga_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIRCH_JUNGLE_TREES = WWFeatureUtils.register("birch_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SPARSE_BIRCH_JUNGLE_TREES = WWFeatureUtils.register("sparse_birch_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_FOREST_VEGETATION = WWFeatureUtils.register("dark_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> OLD_GROWTH_DARK_FOREST_VEGETATION = WWFeatureUtils.register("old_growth_dark_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_BIRCH_FOREST_VEGETATION = WWFeatureUtils.register("dark_birch_forest_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> DARK_TAIGA_VEGETATION = WWFeatureUtils.register("dark_taiga_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_TAIGA = WWFeatureUtils.register("trees_taiga");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SHORT_TREES_TAIGA = WWFeatureUtils.register("short_trees_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE = WWFeatureUtils.register("short_mega_spruce_configured");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHORT_MEGA_SPRUCE_ON_SNOW = WWFeatureUtils.register("short_mega_spruce_on_snow_configured");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_PINE_TAIGA = WWFeatureUtils.register("trees_old_growth_pine_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SPRUCE_TAIGA = WWFeatureUtils.register("trees_old_growth_spruce_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = WWFeatureUtils.register("trees_old_growth_snowy_pine_taiga");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_GROVE = WWFeatureUtils.register("trees_grove");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> TREES_WINDSWEPT_HILLS = WWFeatureUtils.register("trees_windswept_hills");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MEADOW_TREES = WWFeatureUtils.register("meadow_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SAVANNA_TREES = WWFeatureUtils.register("savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> WINDSWEPT_SAVANNA_TREES = WWFeatureUtils.register("windswept_savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_SAVANNA_TREES = WWFeatureUtils.register("arid_savanna_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PARCHED_FOREST_TREES = WWFeatureUtils.register("parched_forest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ARID_FOREST_TREES = WWFeatureUtils.register("arid_forest_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES = WWFeatureUtils.register("cypress_wetlands_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_SAPLING = WWFeatureUtils.register("cypress_wetlands_trees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CYPRESS_WETLANDS_TREES_WATER = WWFeatureUtils.register("cypress_wetlands_trees_water");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> WOODED_BADLANDS_TREES = WWFeatureUtils.register("wooded_badlands_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SWAMP_TREES_SURFACE = WWFeatureUtils.register("swamp_trees_surface");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SWAMP_TREES_WATER_SHALLOW = WWFeatureUtils.register("swamp_trees_water_shallow");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SWAMP_TREES_WATER = WWFeatureUtils.register("swamp_trees_water");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BIG_COARSE_SHRUBS = WWFeatureUtils.register("big_coarse_shrubs");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SHRUBS = WWFeatureUtils.register("shrubs");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS = WWFeatureUtils.register("palms");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_JUNGLE = WWFeatureUtils.register("palms_jungle");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> PALMS_OASIS = WWFeatureUtils.register("palms_oasis");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> BAMBOO_JUNGLE_TREES = WWFeatureUtils.register("bamboo_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> JUNGLE_TREES = WWFeatureUtils.register("jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SPARSE_JUNGLE_TREES = WWFeatureUtils.register("sparse_jungle_trees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MANGROVE_VEGETATION = WWFeatureUtils.register("mangrove_vegetation");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> CHERRIES = WWFeatureUtils.register("cherries");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> YELLOW_MAPLES = WWFeatureUtils.register("yellow_maples");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> ORANGE_MAPLES = WWFeatureUtils.register("orange_maples");
	public static final FrozenConfiguredFeature<ComboFeatureConfig, ConfiguredFeature<ComboFeatureConfig, ?>> RED_MAPLES = WWFeatureUtils.register("red_maples");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MAPLES = WWFeatureUtils.register("maples");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> YELLOW_MAPLES_NO_BEES = WWFeatureUtils.register("yellow_maples_no_bees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ORANGE_MAPLES_NO_BEES = WWFeatureUtils.register("orange_maples_no_bees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> RED_MAPLES_NO_BEES = WWFeatureUtils.register("red_maples_no_bees");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> YELLOW_MAPLES_BEES_SAPLING = WWFeatureUtils.register("yellow_maples_bees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> ORANGE_MAPLES_BEES_SAPLING = WWFeatureUtils.register("orange_maples_bees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> RED_MAPLES_BEES_SAPLING = WWFeatureUtils.register("red_maples_bees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MAPLES_BEES_SAPLING = WWFeatureUtils.register("maples_bees_sapling");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> MAPLES_NO_BEES = WWFeatureUtils.register("maples_no_bees");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_BIRCHES = WWFeatureUtils.register("snapped_birches");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_OAKS = WWFeatureUtils.register("snapped_oaks");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_OAK = WWFeatureUtils.register("snapped_birch_and_oak");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_SPRUCES = WWFeatureUtils.register("snapped_spruces");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_LARGE_SPRUCES = WWFeatureUtils.register("snapped_large_spruces");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_SPRUCES_ON_SNOW = WWFeatureUtils.register("snapped_spruces_on_snow");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_LARGE_SPRUCES_ON_SNOW = WWFeatureUtils.register("snapped_large_spruces_on_snow");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_OAK_AND_SPRUCE = WWFeatureUtils.register("snapped_birch_and_oak_and_spruce");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_SPRUCE = WWFeatureUtils.register("snapped_birch_and_spruce");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_CYPRESSES = WWFeatureUtils.register("snapped_cypresses");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_JUNGLES = WWFeatureUtils.register("snapped_jungles");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_LARGE_JUNGLES = WWFeatureUtils.register("snapped_large_jungles");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_BIRCH_AND_JUNGLE = WWFeatureUtils.register("snapped_birch_and_jungle");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_ACACIAS = WWFeatureUtils.register("snapped_acacias");
	public static final FrozenConfiguredFeature<RandomFeatureConfiguration, ConfiguredFeature<RandomFeatureConfiguration, ?>> SNAPPED_ACACIA_AND_OAK = WWFeatureUtils.register("snapped_acacia_and_oak");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_CHERRY = WWFeatureUtils.register("snapped_cherry");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_DARK_OAKS = WWFeatureUtils.register("snapped_dark_oaks");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> SNAPPED_MAPLE = WWFeatureUtils.register("snapped_maple");

	// FLOWERS
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SEEDING_DANDELION = WWFeatureUtils.register("seeding_dandelion");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> CARNATION = WWFeatureUtils.register("carnation");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MARIGOLD = WWFeatureUtils.register("marigold");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MARIGOLD_SPARSE = WWFeatureUtils.register("marigold_sparse");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PINK_TULIP_UNCOMMON = WWFeatureUtils.register("pink_tulip_uncommon");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> ALLIUM_UNCOMMON = WWFeatureUtils.register("allium_uncommon");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DATURA = WWFeatureUtils.register("datura");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> ROSE_BUSH = WWFeatureUtils.register("rose_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PEONY = WWFeatureUtils.register("peony");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> LILAC = WWFeatureUtils.register("lilac");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_GENERIC = WWFeatureUtils.register("flower_generic");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_PLAINS = WWFeatureUtils.register("flower_plains");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_TUNDRA = WWFeatureUtils.register("flower_tundra");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_BIRCH = WWFeatureUtils.register("flower_birch");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_MEADOW = WWFeatureUtils.register("flower_meadow");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MILKWEED = WWFeatureUtils.register("milkweed");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW = WWFeatureUtils.register("glory_of_the_snow");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GLORY_OF_THE_SNOW_JUNGLE = WWFeatureUtils.register("glory_of_the_snow_jungle");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_FLOWER_FIELD = WWFeatureUtils.register("flower_flower_field");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MOSS_CARPET = WWFeatureUtils.register("moss_carpet");

	public static final SimpleWeightedRandomList<BlockState> FLOWERS_CHERRY_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.POPPY.defaultBlockState(), 9)
		.add(Blocks.PINK_TULIP.defaultBlockState(), 5)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_CYPRESS_WETLANDS = WWFeatureUtils.register("flowers_cypress_wetlands");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_CYPRESS_WETLANDS = WWFeatureUtils.register("tall_flowers_cypress_wetlands");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_TEMPERATE_RAINFOREST = WWFeatureUtils.register("flowers_temperate_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_TEMPERATE_RAINFOREST = WWFeatureUtils.register("tall_flowers_temperate_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("flowers_temperate_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_TEMPERATE_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flowers_temperate_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MUSHROOMS_DARK_FOREST = WWFeatureUtils.register("mushroom_dark_forest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_RAINFOREST = WWFeatureUtils.register("flowers_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_RAINFOREST = WWFeatureUtils.register("tall_flowers_rainforest");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_RAINFOREST_VANILLA = WWFeatureUtils.register("flowers_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_RAINFOREST_VANILLA = WWFeatureUtils.register("tall_flowers_rainforest_vanilla");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_JUNGLE = WWFeatureUtils.register("flowers_jungle");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_FLOWERS_JUNGLE = WWFeatureUtils.register("tall_flowers_jungle");
	public static final FrozenConfiguredFeature<SimpleRandomFeatureConfiguration, ConfiguredFeature<SimpleRandomFeatureConfiguration, ?>> TALL_FLOWER_FLOWER_FIELD = WWFeatureUtils.register("tall_flower_flower_field");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_CHERRY = WWFeatureUtils.register("flowers_cherry");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_SUNFLOWER_PLAINS = WWFeatureUtils.register("flowers_sunflower_plains");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_BIRCH_CLEARING = WWFeatureUtils.register("flowers_birch_clearing");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWERS_FOREST_CLEARING = WWFeatureUtils.register("flowers_forest_clearing");

	// VEGETATION
	public static final SimpleWeightedRandomList<BlockState> OASIS_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 2)
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> OASIS_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 8)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> DEAD_BUSH_AND_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 5)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> BUSH_AND_DEAD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.DEAD_BUSH.defaultBlockState(), 2)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final SimpleWeightedRandomList<BlockState> JUNGLE_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> SPARSE_JUNGLE_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 3)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_GRASS = WWFeatureUtils.register("oasis_grass");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> OASIS_BUSH = WWFeatureUtils.register("oasis_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DEAD_BUSH_AND_BUSH = WWFeatureUtils.register("dead_bush_and_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BUSH_AND_DEAD_BUSH = WWFeatureUtils.register("bush_and_dead_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> JUNGLE_BUSH = WWFeatureUtils.register("jungle_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SPARSE_BUSH = WWFeatureUtils.register("sparse_bush");

	public static final SimpleWeightedRandomList<BlockState> FLOWER_FIELD_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 2)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 5)
		.build();

	public static final SimpleWeightedRandomList<BlockState> BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 5)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 2)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FLOWER_FIELD_BUSH = WWFeatureUtils.register("flower_field_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GENERIC_BUSH = WWFeatureUtils.register("bush");

	public static final SimpleWeightedRandomList<BlockState> DESERT_BUSH_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 0), 1)
		.add(WWBlocks.BUSH.defaultBlockState().setValue(BlockStateProperties.AGE_2, 1), 4)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> DESERT_BUSH = WWFeatureUtils.register("desert_bush");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_SAND = WWFeatureUtils.register("badlands_bush_sand");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> BADLANDS_BUSH_TERRACOTTA = WWFeatureUtils.register("badlands_bush_terracotta");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> WOODED_BADLANDS_BUSH_TERRACOTTA = WWFeatureUtils.register("wooded_badlands_bush_terracotta");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> WOODED_BADLANDS_BUSH_DIRT = WWFeatureUtils.register("wooded_badlands_bush_dirt");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_OASIS = WWFeatureUtils.register("patch_cactus_oasis");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL = WWFeatureUtils.register("patch_cactus_tall");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_CACTUS_TALL_BADLANDS = WWFeatureUtils.register("patch_cactus_tall_badlands");

	public static final SimpleWeightedRandomList<BlockState> PRICKLY_PEAR_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 5)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 3)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 2)
		.add(WWBlocks.PRICKLY_PEAR_CACTUS.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 4)
		.add(Blocks.CACTUS.defaultBlockState(), 2)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PRICKLY_PEAR = WWFeatureUtils.register("prickly_pear");

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 3)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 3)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS = WWFeatureUtils.register("large_fern_and_grass");

	public static final SimpleWeightedRandomList<BlockState> LARGE_FERN_AND_GRASS_POOL_2 = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 5)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> LARGE_FERN_AND_GRASS_2 = WWFeatureUtils.register("large_fern_and_grass_2");

	public static final SimpleWeightedRandomList<BlockState> FERN_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 3)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();

	public static final SimpleWeightedRandomList<BlockState> GRASS_AND_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 11)
		.add(Blocks.FERN.defaultBlockState(), 1)
		.build();
	public static final SimpleWeightedRandomList<BlockState> TALL_GRASS_AND_GRASS_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.TALL_GRASS.defaultBlockState(), 1)
		.add(Blocks.SHORT_GRASS.defaultBlockState(), 4)
		.build();
	public static final SimpleWeightedRandomList<BlockState> SWAMP_FERN_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(Blocks.FERN.defaultBlockState(), 4)
		.add(Blocks.LARGE_FERN.defaultBlockState(), 1)
		.build();


	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TALL_GRASS_AND_GRASS_WATER = WWFeatureUtils.register("tall_grass_and_grass_water");

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SWAMP_TALL_GRASS = WWFeatureUtils.register("swamp_tall_grass");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> SWAMP_FERN = WWFeatureUtils.register("swamp_fern");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> FERN_AND_GRASS = WWFeatureUtils.register("fern_and_grass");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> GRASS_AND_FERN = WWFeatureUtils.register("grass_and_fern");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> MYCELIUM_GROWTH = WWFeatureUtils.register("mycelium_growth");
	public static final FrozenConfiguredFeature<MultifaceGrowthConfiguration, ConfiguredFeature<MultifaceGrowthConfiguration, ?>> POLLEN = WWFeatureUtils.register("pollen");
	public static final FrozenConfiguredFeature<ShelfFungiFeatureConfig, ConfiguredFeature<ShelfFungiFeatureConfig, ?>> CRIMSON_SHELF_FUNGI = WWFeatureUtils.register("crimson_shelf_fungi");
	public static final FrozenConfiguredFeature<ShelfFungiFeatureConfig, ConfiguredFeature<ShelfFungiFeatureConfig, ?>> WARPED_SHELF_FUNGI = WWFeatureUtils.register("warped_shelf_fungi");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL = WWFeatureUtils.register("cattail");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_SMALL = WWFeatureUtils.register("cattail_small");
	public static final FrozenConfiguredFeature<CattailFeatureConfig, ConfiguredFeature<CattailFeatureConfig, ?>> CATTAIL_MUD = WWFeatureUtils.register("cattail_mud");
	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> PATCH_FLOWERING_WATERLILY = WWFeatureUtils.register("patch_flowering_waterlily");
	public static final FrozenConfiguredFeature<AlgaeFeatureConfig, ConfiguredFeature<AlgaeFeatureConfig, ?>> PATCH_ALGAE = WWFeatureUtils.register("patch_algae");
	public static final FrozenConfiguredFeature<AlgaeFeatureConfig, ConfiguredFeature<AlgaeFeatureConfig, ?>> PATCH_ALGAE_SMALL = WWFeatureUtils.register("patch_algae_small");
	public static final FrozenConfiguredFeature<ColumnWithDiskFeatureConfig, ConfiguredFeature<ColumnWithDiskFeatureConfig, ?>> TERMITE_MOUND = WWFeatureUtils.register("termite_mound_baobab");

	public static final SimpleWeightedRandomList<BlockState> TUMBLEWEED_PLANT_POOL = SimpleWeightedRandomList.<BlockState>builder()
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 3), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 2), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 1), 1)
		.add(WWBlocks.TUMBLEWEED_PLANT.defaultBlockState().setValue(BlockStateProperties.AGE_3, 0), 1)
		.build();

	public static final FrozenConfiguredFeature<RandomPatchConfiguration, ConfiguredFeature<RandomPatchConfiguration, ?>> TUMBLEWEED = WWFeatureUtils.register("tumbleweed");
	public static final FrozenConfiguredFeature<SpongeBudFeatureConfig, ConfiguredFeature<SpongeBudFeatureConfig, ?>> SPONGE_BUD = WWFeatureUtils.register("sponge_bud");

	private WWConfiguredFeatures() {
		throw new UnsupportedOperationException("WilderConfiguredFeatures contains only static declarations.");
	}

	public static void registerConfiguredFeatures(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> entries) {
		var configuredFeatures = entries.lookup(Registries.CONFIGURED_FEATURE);
		var placedFeatures = entries.lookup(Registries.PLACED_FEATURE);

		WWConstants.logWithModId("Registering WilderConfiguredFeatures for", true);

		FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.3F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_MIXED.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_SPRUCE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.1F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		MOSSY_FALLEN_TREES_OAK_AND_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_BIRCH_CHECKED.getHolder(), 0.15F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.6F),
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder()))
		);

		FALLEN_CHERRY.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_CHERRY_CHECKED.getHolder(), 0.6F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_CHERRY_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.FALLEN_CHERRY_CHECKED.getHolder()
			)
		);

		FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		CLEAN_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.CLEAN_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		FALLEN_SWAMP_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.getHolder(), 0.75F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_WILLOW_CHECKED.getHolder(), 0.2F)
				),
				WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder()
			)
		);

		DECORATED_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.DECORATED_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		CLEAN_LARGE_FALLEN_SPRUCE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.CLEAN_LARGE_FALLEN_SPRUCE_CHECKED.getHolder()))
		);

		FALLEN_SPRUCE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_SPRUCE_CHECKED.getHolder(), 0.55F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_CYPRESS_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_OAK_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.FALLEN_CYPRESS_CHECKED.getHolder()
			)
		);

		FALLEN_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FALLEN_ACACIA_CHECKED.getHolder(), 0.7F)),
				WWTreePlaced.FALLEN_OAK_NO_MOSS_CHECKED.getHolder()
			)
		);

		FALLEN_LARGE_JUNGLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_FALLEN_JUNGLE_CHECKED.getHolder()))
		);

		FALLEN_PALM_AND_JUNGLE_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_PALM_CHECKED.getHolder(), 0.135F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.FALLEN_JUNGLE_CHECKED.getHolder()
			)
		);

		FALLEN_OAK_AND_BIRCH_DARK_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FALLEN_BIRCH_CHECKED.getHolder(), 0.135F),
					new WeightedPlacedFeature(WWTreePlaced.MOSSY_FALLEN_OAK_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.FALLEN_OAK_CHECKED.getHolder()
			)
		);

		FALLEN_MANGROVE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_MANGROVE_CHECKED.getHolder()))
		);

		FALLEN_DARK_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_FALLEN_DARK_OAK_CHECKED.getHolder()))
		);

		FALLEN_MAPLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.FALLEN_MAPLE_CHECKED.getHolder()))
		);

		TREES_PLAINS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.FANCY_OAK_BEES_0004.getHolder()), 0.04F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.FANCY_DYING_OAK_BEES_0004.getHolder()), 0.02F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.SHORT_OAK.getHolder()), 0.1F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.SHRUB.getHolder()), 0.35F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.BIG_SHRUB.getHolder()), 0.6F)
				),
				PlacementUtils.inlinePlaced(WWTreeConfigured.BIG_SHRUB.getHolder())
			)
		);

		TREES_FLOWER_FIELD.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.FANCY_OAK_BEES_025.getHolder()), 0.2F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.FANCY_DYING_OAK_BEES_025.getHolder()), 0.09F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.BIRCH_BEES_025.getHolder()), 0.1F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.MEDIUM_BIRCH_BEES_025.getHolder()), 0.1F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.BIG_SHRUB.getHolder()), 0.5F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.SHRUB.getHolder()), 0.3F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(WWTreeConfigured.SHORT_OAK.getHolder()), 0.169F)
				),
				PlacementUtils.inlinePlaced(WWTreeConfigured.OAK_BEES_0004.getHolder())
			)
		);

		TREES_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.22F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.05F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.155F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_AND_OAK_CALM.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.355F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.05F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.055F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_MEDIUM_BIRCH.getHolder(), 0.07F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_BIRCH.getHolder(), 0.07F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.015F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.06F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.35F),
					new WeightedPlacedFeature(WWTreePlaced.OAK_CHECKED.getHolder(), 0.033F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SNOWY_DYING_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.DEAD_BIRCH.getHolder(), 0.22F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_MEDIUM_BIRCH.getHolder(), 0.32F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.483F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.33F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_BIRCH.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_MEDIUM_BIRCH.getHolder(), 0.07F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.015F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.06F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.35F),
					new WeightedPlacedFeature(WWTreePlaced.OAK_CHECKED.getHolder(), 0.033F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SNOWY_DYING_MIXED_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.13F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_BIRCH.getHolder(), 0.22F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_MEDIUM_BIRCH.getHolder(), 0.32F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_DEAD_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_SEMI_DEAD_OAK_CHECKED.getHolder(), 0.0433F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_FANCY_DEAD_OAK_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.085F),
					new WeightedPlacedFeature(WWTreePlaced.DEAD_OAK_CHECKED.getHolder(), 0.483F)
				),
				WWTreePlaced.DEAD_OAK_BRANCHES_CHECKED.getHolder()
			)
		);

		TREES_SEMI_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.06F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.13F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.14F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH_BEES_0004.getHolder(), 0.025F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.012F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.035F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.BIRCH_BEES_0004.getHolder(), 0.05F)
				),
				WWTreePlaced.MEDIUM_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_BIRCH_TALL.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.002F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.001F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.BIRCH_BEES_0004.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.177F)
				),
				WWTreePlaced.SUPER_BIRCH_BEES_0004.getHolder()
			)
		);

		TREES_FLOWER_FOREST.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH_BEES_0004.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.035F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.05F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.063F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.205F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.095F)
				),
				WWTreePlaced.OAK_BEES_0004.getHolder()
			)
		);

		MIXED_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.33F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.29F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.12F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.23F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.325F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		TEMPERATE_RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.042F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.021F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.041F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.05F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.09F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.72F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		RAINFOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.OAK_CHECKED.getHolder(), 0.085F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_BEES_0004.getHolder(), 0.12F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES_0004.getHolder(), 0.27F),
					new WeightedPlacedFeature(WWTreePlaced.OLD_DYING_FANCY_OAK_BEES_0004.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.022F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.052F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.120F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.098F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.BIRCH_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.19F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.155F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F)
				),
				WWTreePlaced.SHORT_BIRCH.getHolder()
			)
		);

		OLD_GROWTH_BIRCH_TAIGA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
				new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.39F),
				new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
				new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.02F),
				new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.155F),
				new WeightedPlacedFeature(WWTreePlaced.DYING_SUPER_BIRCH.getHolder(), 0.37F),
				new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
				new WeightedPlacedFeature(WWTreePlaced.BIRCH_CHECKED.getHolder(), 0.355F),
				new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.1F),
				new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.1F)),
				WWTreePlaced.BIRCH_CHECKED.getHolder()
			)
		);

		BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.049F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.069F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH_BEES_0004.getHolder(), 0.049F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.079F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.119F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.getHolder(), 0.165F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		SPARSE_BIRCH_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.07F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.055F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.089F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH_BEES_0004.getHolder(), 0.049F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.059F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.069F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.05F),
					new WeightedPlacedFeature(WWTreePlaced.DARK_OAK_CHECKED.getHolder(), 0.55F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.015F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.32F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.027F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.012F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.185F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		OLD_GROWTH_DARK_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.045F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.07F),
					new WeightedPlacedFeature(WWTreePlaced.DARK_OAK_CHECKED.getHolder(), 0.55F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.255F),
					new WeightedPlacedFeature(WWTreePlaced.BIRCH_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.04F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.6F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.522F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_TALL_DARK_OAK_CHECKED.getHolder(), 0.018F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.0766F),
					new WeightedPlacedFeature(WWTreePlaced.COBWEB_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.035F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.222F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.095F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.24F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_BIRCH_FOREST_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_BROWN_MUSHROOM)), 0.025F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(TreeFeatures.HUGE_RED_MUSHROOM)), 0.035F),
					new WeightedPlacedFeature(WWTreePlaced.DARK_OAK_CHECKED.getHolder(), 0.235F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.35F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.015F),
					new WeightedPlacedFeature(WWTreePlaced.MEDIUM_BIRCH.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEDIUM_BIRCH.getHolder(), 0.015F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.095F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.027F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.012F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.15F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		DARK_TAIGA_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.155F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.086F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.19F),
					new WeightedPlacedFeature(WWTreePlaced.DARK_OAK_CHECKED.getHolder(), 0.235F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_DARK_OAK_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.12F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.004F),
					new WeightedPlacedFeature(WWTreePlaced.BIRCH_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.005F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DARK_OAK_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.08F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_TALL_DARK_OAK_CHECKED.getHolder(), 0.024F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_TALL_DARK_OAK_CHECKED.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.031F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.015F)
				),
				WWTreePlaced.DARK_OAK_CHECKED.getHolder()
			)
		);

		TREES_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_TREES_TAIGA.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder()))
		);

		SHORT_MEGA_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.43333334F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.125F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_CHECKED.getHolder(), 0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_CHECKED.getHolder()
			)
		);

		SHORT_MEGA_SPRUCE_ON_SNOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.43333334F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_FUNGUS_SPRUCE_ON_SNOW.getHolder(), 0.125F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_MEGA_DYING_SPRUCE_ON_SNOW.getHolder(), 0.125F)
				),
				WWTreePlaced.SHORT_MEGA_SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_OLD_GROWTH_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.025641026F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.028F),
					new WeightedPlacedFeature(WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.30769232F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.045F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SPRUCE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.33333334F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F)
				),
				WWTreePlaced.SPRUCE_CHECKED.getHolder()
			)
		);

		TREES_OLD_GROWTH_SNOWY_PINE_TAIGA.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder(), 0.33333334F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FUNGUS_PINE_CHECKED.getHolder(), 0.075F),
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_SHORT_CHECKED.getHolder(), 0.0255F),
					new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_CHECKED.getHolder(), 0.18333334F),
					new WeightedPlacedFeature(WWTreePlaced.MEGA_FUNGUS_SPRUCE_CHECKED.getHolder(), 0.255F)
				),
				WWTreePlaced.MEGA_FUNGUS_PINE_CHECKED.getHolder()
			)
		);

		TREES_GROVE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FUNGUS_PINE_ON_SNOW.getHolder(), 0.33333334F)),
				WWTreePlaced.SPRUCE_ON_SNOW.getHolder()
			)
		);

		TREES_WINDSWEPT_HILLS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SPRUCE_CHECKED.getHolder(), 0.666F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.1F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		MEADOW_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_BEES.getHolder(), 0.5F)),
				WWTreePlaced.SUPER_BIRCH_BEES.getHolder()
			)
		);

		SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					new WeightedPlacedFeature(WWTreePlaced.BAOBAB.getHolder(), 0.062F),
					new WeightedPlacedFeature(WWTreePlaced.BAOBAB_TALL.getHolder(), 0.035F)
				),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		WINDSWEPT_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F)),
				WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder()
			)
		);

		ARID_SAVANNA_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.8F),
					new WeightedPlacedFeature(WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder(), 0.08F),
					new WeightedPlacedFeature(WWTreePlaced.BAOBAB.getHolder(), 0.065F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.getHolder(), 0.052F),
					new WeightedPlacedFeature(WWTreePlaced.BAOBAB_TALL.getHolder(), 0.02F)
				),
				placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED)
			)
		);

		PARCHED_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.59F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.186F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.02F),
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.155F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.ACACIA_CHECKED), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.01F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_BIRCH.getHolder(), 0.155F)
				),
				WWTreePlaced.OAK_CHECKED.getHolder()
			)
		);

		ARID_FOREST_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.DYING_OAK_CHECKED.getHolder(), 0.7085F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_FANCY_OAK_CHECKED.getHolder(), 0.175F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_SHORT_BIRCH.getHolder(), 0.38F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_BIRCH.getHolder(), 0.2325F)
				),
				WWTreePlaced.DYING_OAK_CHECKED.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CYPRESS.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_CYPRESS.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F),
					new WeightedPlacedFeature(WWTreePlaced.OAK_CHECKED.getHolder(), 0.1F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CYPRESS.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_CYPRESS.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.81F)
				),
				WWTreePlaced.FUNGUS_CYPRESS.getHolder()
			)
		);

		CYPRESS_WETLANDS_TREES_WATER.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SWAMP_CYPRESS.getHolder(), 0.85F)),
				WWTreePlaced.SWAMP_CYPRESS.getHolder()
			)
		);

		WOODED_BADLANDS_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.OAK_NO_FUNGI_CHECKED.getHolder(), 0.095F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_COARSE_GRASS_CHECKED.getHolder(), 0.4F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_OAK_CHECKED.getHolder(), 0.67F),
					new WeightedPlacedFeature(WWTreePlaced.JUNIPER.getHolder(), 0.2F)
				),
				WWTreePlaced.JUNIPER.getHolder()
			)
		);

		SWAMP_TREES_SURFACE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.WILLOW_CHECKED.getHolder(), 0.75F),
					new WeightedPlacedFeature(WWTreePlaced.WILLOW_TALL_CHECKED.getHolder(), 0.2F)
				),
				WWTreePlaced.SWAMP_OAK_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_WATER_SHALLOW.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.WILLOW_TALL_CHECKED.getHolder(), 0.75F)
				),
				WWTreePlaced.WILLOW_CHECKED.getHolder()
			)
		);

		SWAMP_TREES_WATER.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(
				HolderSet.direct(WWTreePlaced.WILLOW_TALLER_CHECKED.getHolder())
			)
		);

		BIG_COARSE_SHRUBS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_COARSE_CHECKED.getHolder(), 1F)),
				WWTreePlaced.BIG_SHRUB_COARSE_CHECKED.getHolder())
		);

		SHRUBS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SHRUB_CHECKED.getHolder(), 0.3F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_CHECKED.getHolder(), 0.6F)
				),
				WWTreePlaced.BIG_SHRUB_CHECKED.getHolder()
			)
		);

		PALMS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		PALMS_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINE_PALM_CHECKED_DIRT.getHolder(), 0.7F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED_DIRT.getHolder(), 0.4F)
				),
				WWTreePlaced.PALM_CHECKED_DIRT.getHolder()
			)
		);

		PALMS_OASIS.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_PALM_CHECKED.getHolder(), 0.5F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_WINDMILL_PALM_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(WWTreePlaced.SMALL_WINDMILL_PALM_CHECKED.getHolder(), 0.37F)
				),
				WWTreePlaced.PALM_CHECKED.getHolder()
			)
		);

		BAMBOO_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.05F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.getHolder(), 0.7F)
				),
				PlacementUtils.inlinePlaced(configuredFeatures.getOrThrow(VegetationFeatures.PATCH_GRASS_JUNGLE))
			)
		);

		SPARSE_JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		JUNGLE_TREES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.FANCY_OAK_CHECKED.getHolder(), 0.1F),
					new WeightedPlacedFeature(placedFeatures.getOrThrow(TreePlacements.JUNGLE_BUSH), 0.5F),
					new WeightedPlacedFeature(WWTreePlaced.MEGA_JUNGLE_TREE_CHECKED.getHolder(), 0.33333334F)
				),
				WWTreePlaced.JUNGLE_TREE_CHECKED.getHolder()
			)
		);

		MANGROVE_VEGETATION.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.TALL_MANGROVE_CHECKED.getHolder(), 0.85F)
				),
				WWTreePlaced.MANGROVE_CHECKED.getHolder()
			)
		);

		CHERRIES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.CHERRY_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_CHERRY_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_CHERRY_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_CHERRY_CHECKED.getHolder(), 0.0785F)
				),
				WWTreePlaced.CHERRY_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES.makeAndSetHolder(
			FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.YELLOW_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		ORANGE_MAPLES.makeAndSetHolder(
			FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.ORANGE_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		RED_MAPLES.makeAndSetHolder(
			FrozenFeatures.COMBO_FEATURE,
			new ComboFeatureConfig(
				List.of(
					PlacementUtils.inlinePlaced(
						Feature.RANDOM_SELECTOR,
						new RandomFeatureConfiguration(
							List.of(
								new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
								new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
								new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
								new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
								new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
								new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
							),
							WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder()
						)
					),
					PlacementUtils.inlinePlaced(WWMiscConfigured.RED_MAPLE_LEAF_LITTER.getHolder())
				)
			)
		);

		MAPLES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		RED_MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_BEES_CHECKED.getHolder(), 0.37F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.RED_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		MAPLES_BEES_SAPLING.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES_BEES_SAPLING.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES_BEES_SAPLING.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES_BEES_SAPLING.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_BEES_CHECKED.getHolder()
			)
		);

		YELLOW_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_YELLOW_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_YELLOW_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_YELLOW_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_YELLOW_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_YELLOW_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder()
			)
		);

		ORANGE_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_ORANGE_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_ORANGE_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_ORANGE_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_ORANGE_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_ORANGE_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.ORANGE_MAPLE_CHECKED.getHolder()
			)
		);

		RED_MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.RED_MAPLE_CHECKED.getHolder(), 0.025F),
					new WeightedPlacedFeature(WWTreePlaced.ROUND_RED_MAPLE_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_RED_MAPLE_CHECKED.getHolder(), 0.25F),
					new WeightedPlacedFeature(WWTreePlaced.DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.TALL_DYING_RED_MAPLE_CHECKED.getHolder(), 0.0785F),
					new WeightedPlacedFeature(WWTreePlaced.SHORT_RED_MAPLE_CHECKED.getHolder(), 0.2F),
					new WeightedPlacedFeature(WWTreePlaced.BIG_SHRUB_RED_MAPLE_CHECKED.getHolder(), 0.4F)
				),
				WWTreePlaced.RED_MAPLE_CHECKED.getHolder()
			)
		);

		MAPLES_NO_BEES.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(YELLOW_MAPLES_NO_BEES.getHolder()), 0.4F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(ORANGE_MAPLES_NO_BEES.getHolder()), 0.55F),
					new WeightedPlacedFeature(PlacementUtils.inlinePlaced(RED_MAPLES_NO_BEES.getHolder()), 0.9F)
				),
				WWTreePlaced.YELLOW_MAPLE_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCHES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder()))
		);

		SNAPPED_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.3F)),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_SPRUCES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()))
		);

		SNAPPED_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_SPRUCE_ON_SNOW.getHolder()))
		);

		SNAPPED_LARGE_SPRUCES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_SPRUCE_CHECKED.getHolder()))
		);

		SNAPPED_LARGE_SPRUCES_ON_SNOW.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_SPRUCE_ON_SNOW_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_OAK_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(
					new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.15F),
					new WeightedPlacedFeature(WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder(), 0.25F)
				),
				WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder()
			)
		);

		SNAPPED_BIRCH_AND_SPRUCE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.5F)),
				WWTreePlaced.SNAPPED_SPRUCE_CHECKED.getHolder()
			)
		);

		SNAPPED_CYPRESSES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_CYPRESS_CHECKED.getHolder()))
		);

		SNAPPED_JUNGLES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()))
		);

		SNAPPED_LARGE_JUNGLES.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_JUNGLE_CHECKED.getHolder()))
		);

		SNAPPED_BIRCH_AND_JUNGLE.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_BIRCH_CHECKED.getHolder(), 0.35F)),
				WWTreePlaced.SNAPPED_JUNGLE_CHECKED.getHolder()
			)
		);

		SNAPPED_ACACIAS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()))
		);

		SNAPPED_ACACIA_AND_OAK.makeAndSetHolder(Feature.RANDOM_SELECTOR,
			new RandomFeatureConfiguration(
				List.of(new WeightedPlacedFeature(WWTreePlaced.SNAPPED_OAK_CHECKED.getHolder(), 0.3F)),
				WWTreePlaced.SNAPPED_ACACIA_CHECKED.getHolder()
			)
		);

		SNAPPED_CHERRY.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_CHERRY_CHECKED.getHolder()))
		);


		SNAPPED_DARK_OAKS.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.LARGE_SNAPPED_DARK_OAK_CHECKED.getHolder()))
		);

		SNAPPED_MAPLE.makeAndSetHolder(Feature.SIMPLE_RANDOM_SELECTOR,
			new SimpleRandomFeatureConfiguration(HolderSet.direct(WWTreePlaced.SNAPPED_MAPLE_CHECKED.getHolder()))
		);

		// FLOWERS

		SEEDING_DANDELION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.SEEDING_DANDELION))
				)
			)
		);

		CARNATION.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.CARNATION))
				)
			)
		);

		MARIGOLD.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MARIGOLD))
				)
			)
		);

		MARIGOLD_SPARSE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				24,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MARIGOLD))
				)
			)
		);

		PINK_TULIP_UNCOMMON.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PINK_TULIP))
				)
			)
		);

		ALLIUM_UNCOMMON.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ALLIUM))
				)
			)
		);


		DATURA.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				48,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.DATURA))
				)
			)
		);

		ROSE_BUSH.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.ROSE_BUSH))
				)
			)
		);

		PEONY.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				32,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.PEONY))
				)
			)
		);

		LILAC.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				40,
				PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.LILAC))
				)
			)
		);

		FLOWER_GENERIC.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.048833334F,
							List.of(
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.048833334F,
							List.of(
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.WHITE_TULIP.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWER_TUNDRA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.048833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWER_BIRCH.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				48,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.048833334F,
							List.of(
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.WHITE_TULIP.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState()
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
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.007833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState()
							)
						)
					)
				)
			)
		);

		MILKWEED.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				20,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED))
				)
			)
		);

		Holder<PlacedFeature> gloryOfTheSnowNoise = PlacementUtils.onlyWhenEmpty(
			Feature.SIMPLE_BLOCK,
			new SimpleBlockConfiguration(
				new NoiseProvider(
					5050L,
					new NormalNoise.NoiseParameters(0, 1D),
					0.048833334F,
					List.of(
						WWBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(WWBlockStateProperties.FLOWER_COLOR, FlowerColor.WHITE),
						WWBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(WWBlockStateProperties.FLOWER_COLOR, FlowerColor.PINK),
						WWBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(WWBlockStateProperties.FLOWER_COLOR, FlowerColor.PURPLE),
						WWBlocks.GLORY_OF_THE_SNOW.defaultBlockState().setValue(WWBlockStateProperties.FLOWER_COLOR, FlowerColor.BLUE)
					)
				)
			)
		);

		GLORY_OF_THE_SNOW.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				gloryOfTheSnowNoise
			)
		);

		GLORY_OF_THE_SNOW_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				8,
				gloryOfTheSnowNoise
			)
		);

		FLOWER_FLOWER_FIELD.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				86,
				6,
				2,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(2345L,
							new NormalNoise.NoiseParameters(0, 1.0F), 0.020833334F,
							List.of(
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
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								WWBlocks.PASQUEFLOWER.defaultBlockState()
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
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.043833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.WHITE_TULIP.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.AZURE_BLUET.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_CYPRESS_WETLANDS.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.043833334F,
							List.of(
								WWBlocks.DATURA.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.ROSE_BUSH.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.LILAC.defaultBlockState(),
								Blocks.PEONY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.023833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_TEMPERATE_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.023833334F,
							List.of(
								WWBlocks.DATURA.defaultBlockState(),
								Blocks.ROSE_BUSH.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.LILAC.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.023833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_TEMPERATE_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.023833334F,
							List.of(
								Blocks.ROSE_BUSH.defaultBlockState(),
								Blocks.LILAC.defaultBlockState()
							)
						)
					)
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
			new RandomPatchConfiguration(
				24,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.034833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.BLUE_ORCHID.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_RAINFOREST.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.034833334F,
							List.of(
								WWBlocks.DATURA.defaultBlockState(),
								Blocks.ROSE_BUSH.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.LILAC.defaultBlockState(),
								Blocks.PEONY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				36,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.034833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState(),
								Blocks.BLUE_ORCHID.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_RAINFOREST_VANILLA.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.034833334F,
							List.of(
								Blocks.ROSE_BUSH.defaultBlockState(),
								Blocks.LILAC.defaultBlockState(),
								Blocks.PEONY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.054833334F,
							List.of(
								Blocks.LILY_OF_THE_VALLEY.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.OXEYE_DAISY.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.BLUE_ORCHID.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState()
							)
						)
					)
				)
			)
		);

		TALL_FLOWERS_JUNGLE.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				10,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.054833334F,
							List.of(
								WWBlocks.DATURA.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.ROSE_BUSH.defaultBlockState(),
								Blocks.LILAC.defaultBlockState(),
								Blocks.PEONY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_SUNFLOWER_PLAINS.makeAndSetHolder(Feature.FLOWER,
			FeatureUtils.simpleRandomPatchConfiguration(
				20,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.054833334F,
							List.of(
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								WWBlocks.MARIGOLD.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.POPPY.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_BIRCH_CLEARING.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.054833334F,
							List.of(
								Blocks.WHITE_TULIP.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState()
							)
						)
					)
				)
			)
		);

		FLOWERS_FOREST_CLEARING.makeAndSetHolder(Feature.FLOWER,
			new RandomPatchConfiguration(
				12,
				8,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(
						new NoiseProvider(
							5050L,
							new NormalNoise.NoiseParameters(0, 1D),
							0.054833334F,
							List.of(
								Blocks.WHITE_TULIP.defaultBlockState(),
								WWBlocks.SEEDING_DANDELION.defaultBlockState(),
								Blocks.SUNFLOWER.defaultBlockState(),
								Blocks.DANDELION.defaultBlockState(),
								Blocks.ORANGE_TULIP.defaultBlockState(),
								Blocks.RED_TULIP.defaultBlockState(),
								Blocks.POPPY.defaultBlockState(),
								Blocks.PINK_TULIP.defaultBlockState(),
								WWBlocks.MILKWEED.defaultBlockState(),
								Blocks.ALLIUM.defaultBlockState(),
								Blocks.LILAC.defaultBlockState(),
								WWBlocks.CARNATION.defaultBlockState(),
								Blocks.PEONY.defaultBlockState(),
								Blocks.CORNFLOWER.defaultBlockState()
							)
						)
					)
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
							new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MILKWEED)),
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
				24,
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
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(OASIS_BUSH_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						// TODO: Maybe remove sand again
						BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.BUSH_MAY_PLACE_ON)
					)
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
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(JUNGLE_BUSH_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.BUSH_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
				)
			)
		);

		SPARSE_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				4,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SPARSE_JUNGLE_BUSH_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.BUSH_MAY_PLACE_ON_FEATURE_NO_SAND)
					)

				)
			)
		);

		FLOWER_FIELD_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				18,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(FLOWER_FIELD_BUSH_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.BUSH_MAY_PLACE_ON_FEATURE_NO_SAND)
					)

				)
			)
		);

		GENERIC_BUSH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				12,
				PlacementUtils.filtered(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(BUSH_POOL)),
					BlockPredicate.allOf(
						BlockPredicate.replaceable(),
						BlockPredicate.noFluid(),
						BlockPredicate.matchesTag(Direction.DOWN.getNormal(), WWBlockTags.BUSH_MAY_PLACE_ON_FEATURE_NO_SAND)
					)
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
							BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.BADLANDS_TERRACOTTA)
						)
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
							BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.BADLANDS_TERRACOTTA)
						)
					)
				)
			)
		);

		WOODED_BADLANDS_BUSH_DIRT.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				15,
				PlacementUtils.inlinePlaced(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(DESERT_BUSH_POOL)),
					BlockPredicateFilter.forPredicate(
						BlockPredicate.allOf(
							BlockPredicate.ONLY_IN_AIR_PREDICATE,
							BlockPredicate.matchesTag(Direction.DOWN.getNormal(), BlockTags.DIRT)
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

		SWAMP_TALL_GRASS.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				18,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(Blocks.TALL_GRASS))
				)
			)
		);

		SWAMP_FERN.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				24,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(new WeightedStateProvider(SWAMP_FERN_POOL))
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

		MYCELIUM_GROWTH.makeAndSetHolder(Feature.RANDOM_PATCH,
			FeatureUtils.simpleRandomPatchConfiguration(
				28,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.MYCELIUM_GROWTH))
				)
			)
		);

		POLLEN.makeAndSetHolder(Feature.MULTIFACE_GROWTH,
			new MultifaceGrowthConfiguration(
				WWBlocks.POLLEN,
				10,
				true,
				true,
				true,
				0.5F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.POLLEN_FEATURE_PLACEABLE
				)
			)
		);

		CRIMSON_SHELF_FUNGI.makeAndSetHolder(WWFeatures.SHELF_FUNGI_FEATURE,
			new ShelfFungiFeatureConfig(
				WWBlocks.CRIMSON_SHELF_FUNGI,
				12,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER
				)
			)
		);

		WARPED_SHELF_FUNGI.makeAndSetHolder(WWFeatures.SHELF_FUNGI_FEATURE,
			new ShelfFungiFeatureConfig(
				WWBlocks.WARPED_SHELF_FUNGI,
				12,
				true,
				true,
				true,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.SHELF_FUNGI_FEATURE_PLACEABLE_NETHER
				)
			)
		);

		CATTAIL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 18),
				true,
				WWBlockTags.CATTAIL_FEATURE_PLACEABLE
			)
		);

		CATTAIL_SMALL.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-5, 5),
				UniformInt.of(6, 12),
				true,
				WWBlockTags.CATTAIL_FEATURE_PLACEABLE
			)
		);

		CATTAIL_MUD.makeAndSetHolder(WWFeatures.CATTAIL_FEATURE,
			new CattailFeatureConfig(
				UniformInt.of(-7, 7),
				UniformInt.of(12, 18),
				false,
				WWBlockTags.CATTAIL_FEATURE_MUD_PLACEABLE
			)
		);

		PATCH_FLOWERING_WATERLILY.makeAndSetHolder(Feature.RANDOM_PATCH,
			new RandomPatchConfiguration(
				10,
				7,
				3,
				PlacementUtils.onlyWhenEmpty(
					Feature.SIMPLE_BLOCK,
					new SimpleBlockConfiguration(BlockStateProvider.simple(WWBlocks.FLOWERING_LILY_PAD))
				)
			)
		);

		PATCH_ALGAE.makeAndSetHolder(WWFeatures.ALGAE_FEATURE,
			new AlgaeFeatureConfig(UniformInt.of(4, 10))
		);

		PATCH_ALGAE_SMALL.makeAndSetHolder(WWFeatures.ALGAE_FEATURE,
			new AlgaeFeatureConfig(UniformInt.of(2, 6))
		);

		TERMITE_MOUND.makeAndSetHolder(FrozenFeatures.COLUMN_WITH_DISK_FEATURE,
			new ColumnWithDiskFeatureConfig(
				WWBlocks.TERMITE_MOUND.defaultBlockState().setValue(WWBlockStateProperties.NATURAL, true),
				UniformInt.of(4, 9),
				UniformInt.of(3, 7),
				0.75F,
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.TERMITE_DISK_REPLACEABLE
				),
				new HolderSet.Named<>(
					BuiltInRegistries.BLOCK.holderOwner(),
					WWBlockTags.TERMITE_DISK_BLOCKS
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

		SPONGE_BUD.makeAndSetHolder(WWFeatures.SPONGE_BUD_FEATURE,
			new SpongeBudFeatureConfig(
				20,
				true,
				true,
				true,
				WWBlockTags.SMALL_SPONGE_GROWS_ON
			)
		);
	}
}
