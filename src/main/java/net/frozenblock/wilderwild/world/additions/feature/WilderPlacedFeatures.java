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

import java.util.List;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import static net.minecraft.data.worldgen.placement.AquaticPlacements.seagrassPlacement;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.*;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceWaterDepthFilter;
import org.jetbrains.annotations.NotNull;

public final class WilderPlacedFeatures {
	private WilderPlacedFeatures() {
		throw new UnsupportedOperationException("WilderPlacedFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final Holder<PlacedFeature> FALLEN_TREES_MIXED_PLACED = register("fallen_trees_mixed_placed",
            WilderConfiguredFeatures.FALLEN_TREES_MIXED, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_BIRCH_AND_SPRUCE_PLACED = register("fallen_birch_and_spruce_placed",
            WilderConfiguredFeatures.FALLEN_BIRCH_AND_SPRUCE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = register("fallen_oak_and_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> NEW_FALLEN_OAK_AND_BIRCH_PLACED = register("fallen_oak_and_birch_placed",
            WilderConfiguredFeatures.NEW_FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = register("fallen_oak_and_cypress_placed",
            WilderConfiguredFeatures.NEW_FALLEN_CYPRESS_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> NEW_FALLEN_BIRCH_PLACED = register("fallen_birch_placed",
            WilderConfiguredFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_SPRUCE_PLACED = register("fallen_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED_2 = register("fallen_oak_and_birch_placed_2",
			WilderConfiguredFeatures.NEW_FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(10), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //TREES
    public static final Holder<PlacedFeature> NEW_TREES_PLAINS = register("trees_plains", WilderConfiguredFeatures.NEW_TREES_PLAINS,
            PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());

	public static final Holder<PlacedFeature> TREES_FLOWER_FIELD = register("trees_flower_field", WilderConfiguredFeatures.TREES_FLOWER_FIELD,
			PlacementUtils.countExtra(0, 0.1F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());

	public static final Holder<PlacedFeature> NEW_TREES_BIRCH_AND_OAK = register("trees_birch_and_oak",
            WilderConfiguredFeatures.NEW_TREES_BIRCH_AND_OAK, treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_FLOWER_FOREST = register("trees_flower_forest",
            WilderConfiguredFeatures.NEW_TREES_FLOWER_FOREST, treePlacement(PlacementUtils.countExtra(8, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_DARK_FOREST_VEGETATION = register("dark_forest_vegetation",
            WilderConfiguredFeatures.NEW_DARK_FOREST_VEGETATION, CountPlacement.of(16), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome());

	public static final Holder<PlacedFeature> OLD_GROWTH_DARK_FOREST_VEGETATION = register("old_growth_dark_forest_vegetation",
			WilderConfiguredFeatures.OLD_GROWTH_DARK_FOREST_VEGETATION, CountPlacement.of(14), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome());

	public static final Holder<PlacedFeature> NEW_BIRCH_PLACED = register("trees_birch",
            WilderConfiguredFeatures.NEW_TREES_BIRCH, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TALL_BIRCH_PLACED = register("birch_tall",
            WilderConfiguredFeatures.NEW_TREES_BIRCH_TALL, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_SPRUCE_PLACED = register("spruce_placed",
            WilderConfiguredFeatures.NEW_TREES_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_SHORT_SPRUCE_PLACED = register("short_spruce_placed",
            WilderConfiguredFeatures.NEW_SHORT_TREES_TAIGA, treePlacement(PlacementUtils.countExtra(5, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_OLD_GROWTH_PINE_TAIGA = register("trees_old_growth_pine_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA = register("trees_old_growth_spruce_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

	public static final Holder<PlacedFeature> TREES_OLD_GROWTH_SNOWY_PINE_TAIGA = register("trees_old_growth_snowy_pine_taiga",
			WilderConfiguredFeatures.TREES_OLD_GROWTH_SNOWY_PINE_TAIGA, treePlacement(PlacementUtils.countExtra(8, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_SNOWY = register("trees_snowy",
            WilderTreeConfigured.NEW_SPRUCE, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));

    public static final Holder<PlacedFeature> NEW_TREES_GROVE = register("trees_grove",
            WilderConfiguredFeatures.NEW_TREES_GROVE, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_WINDSWEPT_HILLS = register("trees_windswept_hills",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_WINDSWEPT_FOREST = register("trees_windswept_forest",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));

    public static final Holder<PlacedFeature> NEW_TREES_MEADOW = register("trees_meadow",
            WilderConfiguredFeatures.NEW_MEADOW_TREES, treePlacement(RarityFilter.onAverageOnceEvery(100)));

    public static final Holder<PlacedFeature> WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees",
            WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

    public static final Holder<PlacedFeature> SAVANNA_TREES = register("savanna_trees",
            WilderConfiguredFeatures.SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

	public static final Holder<PlacedFeature> ARID_SAVANNA_TREES = register("arid_savanna_trees",
			WilderConfiguredFeatures.ARID_SAVANNA_TREES, treePlacement(RarityFilter.onAverageOnceEvery(12)));

    public static final Holder<PlacedFeature> NEW_TREES_SWAMP = register("trees_swamp", WilderTreeConfigured.NEW_SWAMP_TREE,
            PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(4), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockPos.ZERO)));

    public static final Holder<PlacedFeature> MIXED_TREES = register("mixed_trees",
            WilderConfiguredFeatures.MIXED_TREES, treePlacement(PlacementUtils.countExtra(14, 0.1F, 1)));

    public static final Holder<PlacedFeature> BIRCH_TAIGA_TREES = register("birch_taiga_trees",
            WilderConfiguredFeatures.BIRCH_TAIGA_TREES, treePlacement(CountPlacement.of(3)));

	public static final Holder<PlacedFeature> OLD_GROWTH_BIRCH_TAIGA_TREES = register("old_growth_birch_taiga_trees",
			WilderConfiguredFeatures.OLD_GROWTH_BIRCH_TAIGA_TREES, treePlacement(CountPlacement.of(3)));

	public static final Holder<PlacedFeature> PARCHED_FOREST_TREES = register("parched_forest_trees",
			WilderConfiguredFeatures.PARCHED_FOREST_TREES, treePlacement(CountPlacement.of(3)));

	public static final Holder<PlacedFeature> ARID_FOREST_TREES = register("arid_forest_trees",
			WilderConfiguredFeatures.ARID_FOREST_TREES, treePlacement(CountPlacement.of(3)));

	public static final Holder<PlacedFeature> BIRCH_JUNGLE_TREES = register("birch_jungle_trees",
			WilderConfiguredFeatures.BIRCH_JUNGLE_TREES, treePlacement(CountPlacement.of(50)));

	public static final Holder<PlacedFeature> SPARSE_BIRCH_JUNGLE_TREES = register("sparse_birch_jungle_trees",
			WilderConfiguredFeatures.SPARSE_BIRCH_JUNGLE_TREES, VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.1f, 1)));

    public static final Holder<PlacedFeature> CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES, CountPlacement.of(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

    public static final Holder<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER, CountPlacement.of(20), SurfaceWaterDepthFilter.forMaxDepth(5), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

	public static final Holder<PlacedFeature> BIG_SHRUB = PlacementUtils.register("big_shrub",
			WilderConfiguredFeatures.BIG_SHRUBS, treePlacement(RarityFilter.onAverageOnceEvery(5)));

	public static final Holder<PlacedFeature> PALM = PlacementUtils.register("palm_placed",
			WilderConfiguredFeatures.PALMS, treePlacement(RarityFilter.onAverageOnceEvery(4)));

	public static final Holder<PlacedFeature> PALM_JUNGLE = PlacementUtils.register("palm_jungle",
			WilderConfiguredFeatures.PALMS_JUNGLE, treePlacement(PlacementUtils.countExtra(6, 0.5F, 2)));

	public static final Holder<PlacedFeature> PALMS_OASIS = PlacementUtils.register("palms_oasis",
			WilderConfiguredFeatures.PALMS_OASIS, treePlacement(RarityFilter.onAverageOnceEvery(3)));

	public static final Holder<PlacedFeature> PALM_RARE = PlacementUtils.register("palm_rare",
			WilderConfiguredFeatures.PALMS_OASIS, treePlacement(RarityFilter.onAverageOnceEvery(52)));
	//MUSHROOMS
    public static final Holder<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = register("brown_shelf_fungus_placed",
            WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final Holder<PlacedFeature> RED_SHELF_FUNGUS_PLACED = register("red_shelf_fungus_placed",
            WilderConfiguredFeatures.RED_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final Holder<PlacedFeature> NEW_BROWN_MUSHROOM_PLACED = register("brown_mushroom_placed",
            VegetationFeatures.PATCH_BROWN_MUSHROOM, worldSurfaceSquaredWithCount(10));

    public static final Holder<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = register("huge_red_mushroom_placed",
            TreeFeatures.HUGE_RED_MUSHROOM, RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> NEW_MUSHROOM_PLACED = register("mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> MIXED_MUSHROOMS_PLACED = register("mixed_mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(75), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //GRASS AND FERNS
	public static final Holder<PlacedFeature> OASIS_GRASS_PLACED = register("oasis_grass_placed",
			WilderConfiguredFeatures.OASIS_GRASS, worldSurfaceSquaredWithCount(19));

	public static final Holder<PlacedFeature> OASIS_BUSH_PLACED = register("oasis_bush_placed",
			WilderConfiguredFeatures.OASIS_BUSH, worldSurfaceSquaredWithCount(2));

	public static final Holder<PlacedFeature> FLOWER_FIELD_BUSH_PLACED = register("flower_field_bush_placed",
			WilderConfiguredFeatures.FLOWER_FIELD_BUSH, RarityFilter.onAverageOnceEvery(6), InSquarePlacement.spread(),
			PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> DESERT_BUSH_PLACED = register("desert_bush_placed",
			WilderConfiguredFeatures.DESERT_BUSH, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	public static final Holder<PlacedFeature> ARID_BUSH_PLACED = register("arid_bush_placed",
			WilderConfiguredFeatures.DESERT_BUSH, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	public static final Holder<PlacedFeature> OASIS_CACTUS_PLACED = register("oasis_cactus_placed",
			WilderConfiguredFeatures.PATCH_CACTUS_OASIS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> TALL_CACTUS_PLACED = register("tall_cactus_placed",
			WilderConfiguredFeatures.PATCH_CACTUS_TALL, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> ARID_CACTUS_PLACED = register("arid_cactus_placed",
			VegetationFeatures.PATCH_CACTUS, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> NEW_GRASS_PLACED = register("grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(20));

    public static final Holder<PlacedFeature> NEW_RARE_GRASS_PLACED = register("rare_grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(8));

    public static final Holder<PlacedFeature> NEW_TALL_GRASS = register("tall_grass",
            VegetationFeatures.PATCH_TALL_GRASS, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> DENSE_TALL_GRASS_PLACED = register("dense_tall_grass_placed",
            VegetationFeatures.PATCH_TALL_GRASS, worldSurfaceSquaredWithCount(1));

    public static final Holder<PlacedFeature> DENSE_FERN_PLACED = register("dense_fern_placed",
            VegetationFeatures.PATCH_LARGE_FERN, worldSurfaceSquaredWithCount(1));

    public static final Holder<PlacedFeature> SEAGRASS_CYPRESS = register("seagrass_cypress",
            AquaticFeatures.SEAGRASS_MID, seagrassPlacement(56));

    public static final Holder<PlacedFeature> LARGE_FERN_AND_GRASS = register("large_fern_and_grass",
            WilderConfiguredFeatures.LARGE_FERN_AND_GRASS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> LARGE_FERN_AND_GRASS_RARE = register("large_fern_and_grass_rare",
            WilderConfiguredFeatures.LARGE_FERN_AND_GRASS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> FLOWER_FIELD_GRASS_PLACED = register("flower_field_grass_placed",
			VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(15));

	public static final Holder<PlacedFeature> PATCH_TALL_GRASS_FF = PlacementUtils.register("patch_tall_grass_ff", WilderConfiguredFeatures.LARGE_FERN_AND_GRASS_2, NoiseThresholdCountPlacement.of(-0.8, 0, 7), RarityFilter.onAverageOnceEvery(16), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());


	//FLOWERS
    public static final Holder<PlacedFeature> SEEDING_DANDELION = register("seeding_dandelion",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> SEEDING_DANDELION_MIXED = register("seeding_dandelion_mixed",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> SEEDING_DANDELION_CYPRESS = register("seeding_dandelion_cypress",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> CARNATION = register("carnation",
            WilderConfiguredFeatures.CARNATION, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> DATURA_BIRCH = register("datura_birch",
            WilderConfiguredFeatures.DATURA, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> NEW_FLOWER_PLAIN = register("flower_plains",
            WilderConfiguredFeatures.NEW_FLOWER_PLAIN, NoiseThresholdCountPlacement.of(-0.8, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> DENSE_FLOWER_PLACED = register("dense_flower_placed",
            VegetationFeatures.FLOWER_DEFAULT, worldSurfaceSquaredWithCount(1));

    public static final Holder<PlacedFeature> FLOWER_FOREST_FLOWERS = register(
            "flower_forest_flowers",
            VegetationFeatures.FOREST_FLOWERS,
            RarityFilter.onAverageOnceEvery(7),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
            BiomeFilter.biome()
    );

    public static final Holder<PlacedFeature> MILKWEED = register("milkweed",
            WilderConfiguredFeatures.MILKWEED, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> MILKWEED_CYPRESS = register("milkweed_cypress",
            WilderConfiguredFeatures.MILKWEED, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> GLORY_OF_THE_SNOW = register("glory_of_the_snow",
            WilderConfiguredFeatures.GLORY_OF_THE_SNOW, RarityFilter.onAverageOnceEvery(11), CountPlacement.of(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome());

	public static final Holder<PlacedFeature> FLOWER_FLOWER_FIELD = register("flower_flower_field", WilderConfiguredFeatures.FLOWER_FLOWER_FIELD,
			CountPlacement.of(3), RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature>  TALL_FLOWER_FIELD_FLOWERS = register("tall_flower_field_flowers", WilderConfiguredFeatures.TALL_FLOWER_FLOWER_FIELD,
			RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 4), 0, 4)), BiomeFilter.biome());

	//VEGETATION
    public static final Holder<PlacedFeature> POLLEN_PLACED = register("pollen",
            WilderConfiguredFeatures.POLLEN_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(2), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING, 0, 128), BiomeFilter.biome());

    public static final Holder<PlacedFeature> PATCH_CATTAIL =
            register("cattail", WilderConfiguredFeatures.CATTAIL,
                    RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> PATCH_FLOWERED_WATERLILY = register("patch_flowered_waterlily",
            WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY, worldSurfaceSquaredWithCount(1));

    public static final Holder<PlacedFeature> PATCH_ALGAE =
            register("patch_algae", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> PATCH_ALGAE_5 =
            register("patch_algae_5", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> PATCH_BERRY_FOREST =
            register("patch_berry_forest", VegetationFeatures.PATCH_BERRY_BUSH, RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<PlacedFeature> TERMITE_PLACED = register("termite_placed",
            WilderConfiguredFeatures.TERMITE_CONFIGURED, RarityFilter.onAverageOnceEvery(45), CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

	public static final Holder<PlacedFeature> TUMBLEWEED = register("tumbleweed",
			WilderConfiguredFeatures.TUMBLEWEED, RarityFilter.onAverageOnceEvery(9), CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<PlacedFeature> JELLYFISH_CAVES_BLUE_MESOGLEA = register(
            "blue_mesoglea",
            WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = register(
            "upside_down_blue_mesoglea",
            WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> JELLYFISH_CAVES_PURPLE_MESOGLEA = register(
            "purple_mesoglea",
            WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final Holder<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = register(
            "upside_down_purple_mesoglea",
            WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

	public static final Holder<PlacedFeature> NEMATOCYST_BLUE = PlacementUtils.register(
			"nematocyst_blue",
			WilderConfiguredFeatures.NEMATOCYST_BLUE,
			CountPlacement.of(ConstantInt.of(90)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
	);

	public static final Holder<PlacedFeature> NEMATOCYST_PURPLE = PlacementUtils.register(
			"nematocyst_purple",
			WilderConfiguredFeatures.NEMATOCYST_PURPLE,
			CountPlacement.of(ConstantInt.of(90)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
	);

	public static final Holder<PlacedFeature> MESOGLEA_CLUSTER_PURPLE = PlacementUtils.register(
			"mesoglea_cluster_purple",
			WilderConfiguredFeatures.MESOGLEA_CLUSTER_PURPLE,
			CountPlacement.of(UniformInt.of(9, 15)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

	public static final Holder<PlacedFeature> MESOGLEA_CLUSTER_BLUE = PlacementUtils.register(
			"mesoglea_cluster_blue",
			WilderConfiguredFeatures.MESOGLEA_CLUSTER_BLUE,
			CountPlacement.of(UniformInt.of(6, 13)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

	public static final Holder<PlacedFeature> LARGE_MESOGLEA_PURPLE = PlacementUtils.register(
			"large_mesoglea_purple",
			WilderConfiguredFeatures.LARGE_MESOGLEA_PURPLE,
			CountPlacement.of(UniformInt.of(1, 3)), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

	public static final Holder<PlacedFeature> LARGE_MESOGLEA_BLUE = PlacementUtils.register(
			"large_mesoglea_blue",
			WilderConfiguredFeatures.LARGE_MESOGLEA_BLUE,
			CountPlacement.of(UniformInt.of(1, 3)), RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

	public static final Holder<PlacedFeature> SMALL_SPONGES = PlacementUtils.register(
			"small_sponges",
			WilderConfiguredFeatures.SMALL_SPONGE,
			CountPlacement.of(ConstantInt.of(82)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
	);

	public static final Holder<PlacedFeature> SMALL_SPONGES_RARE = PlacementUtils.register(
			"small_sponges_rare",
			WilderConfiguredFeatures.SMALL_SPONGE,
			CountPlacement.of(ConstantInt.of(42)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
	);

	public static void init() {
    }

    public static Holder<PlacedFeature> register(@NotNull String id, Holder<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull List<PlacementModifier> modifiers) {
        return BuiltinRegistries.register(BuiltinRegistries.PLACED_FEATURE, WilderSharedConstants.id(id), new PlacedFeature(Holder.hackyErase(registryEntry), List.copyOf(modifiers)));
    }

    public static Holder<PlacedFeature> register(@NotNull String id, Holder<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull PlacementModifier... modifiers) {
        return register(id, registryEntry, List.of(modifiers));
    }

}
