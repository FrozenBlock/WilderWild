package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.FrozenPlacedFeatureBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import static net.minecraft.data.worldgen.placement.AquaticPlacements.seagrassPlacement;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import static net.minecraft.data.worldgen.placement.VegetationPlacements.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
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
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public final class FrozenPlacedFeatures {
	private FrozenPlacedFeatures() {
		throw new UnsupportedOperationException("FrozenPlacedFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final FrozenPlacedFeature FALLEN_TREES_MIXED_PLACED = placedFeature("fallen_trees_mixed_placed",
            WilderConfiguredFeatures.FALLEN_TREES_MIXED, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature FALLEN_OAK_AND_SPRUCE_PLACED = placedFeature("fallen_oak_and_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_FALLEN_OAK_AND_BIRCH_PLACED = placedFeature("fallen_oak_and_birch_placed",
            WilderConfiguredFeatures.NEW_FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature FALLEN_OAK_AND_CYPRESS_PLACED = placedFeature("fallen_oak_and_cypress_placed",
            WilderConfiguredFeatures.NEW_FALLEN_CYPRESS_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_FALLEN_BIRCH_PLACED = placedFeature("fallen_birch_placed",
            WilderConfiguredFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature FALLEN_SPRUCE_PLACED = placedFeature("fallen_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //TREES
    public static final FrozenPlacedFeature NEW_TREES_PLAINS = placedFeature("trees_plains", WilderConfiguredFeatures.NEW_TREES_PLAINS,
            PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_TREES_BIRCH_AND_OAK = placedFeature("trees_birch_and_oak",
            WilderConfiguredFeatures.NEW_TREES_BIRCH_AND_OAK, treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_FLOWER_FOREST = placedFeature("trees_flower_forest",
            WilderConfiguredFeatures.NEW_TREES_FLOWER_FOREST, treePlacement(PlacementUtils.countExtra(8, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_DARK_FOREST_VEGETATION = placedFeature("dark_forest_vegetation",
            WilderConfiguredFeatures.NEW_DARK_FOREST_VEGETATION, CountPlacement.of(16), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_BIRCH_PLACED = placedFeature("trees_birch",
            WilderConfiguredFeatures.NEW_TREES_BIRCH, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TALL_BIRCH_PLACED = placedFeature("birch_tall",
            WilderConfiguredFeatures.NEW_TREES_BIRCH_TALL, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_SPRUCE_PLACED = placedFeature("spruce_placed",
            WilderConfiguredFeatures.NEW_TREES_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_SHORT_SPRUCE_PLACED = placedFeature("short_spruce_placed",
            WilderConfiguredFeatures.NEW_SHORT_TREES_TAIGA, treePlacement(PlacementUtils.countExtra(5, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_OLD_GROWTH_PINE_TAIGA = placedFeature("trees_old_growth_pine_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA = placedFeature("trees_old_growth_spruce_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_SNOWY = placedFeature("trees_snowy",
            WilderTreeConfigured.NEW_SPRUCE, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));

    public static final FrozenPlacedFeature NEW_TREES_GROVE = placedFeature("trees_grove",
            WilderConfiguredFeatures.NEW_TREES_GROVE, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_WINDSWEPT_HILLS = placedFeature("trees_windswept_hills",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_WINDSWEPT_FOREST = placedFeature("trees_windswept_forest",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_MEADOW = placedFeature("trees_meadow",
            WilderConfiguredFeatures.NEW_MEADOW_TREES, treePlacement(RarityFilter.onAverageOnceEvery(100)));

    public static final FrozenPlacedFeature WINDSWEPT_SAVANNA_TREES = placedFeature("windswept_savanna_trees",
            WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

    public static final FrozenPlacedFeature SAVANNA_TREES = placedFeature("savanna_trees",
            WilderConfiguredFeatures.SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

    public static final FrozenPlacedFeature NEW_TREES_SWAMP = placedFeature("trees_swamp", WilderTreeConfigured.NEW_SWAMP_TREE,
            PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(4), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockPos.ZERO)));

    public static final FrozenPlacedFeature MIXED_TREES = placedFeature("mixed_trees",
            WilderConfiguredFeatures.MIXED_TREES, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final FrozenPlacedFeature CYPRESS_WETLANDS_TREES = placedFeature("cypress_wetlands_trees",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES, CountPlacement.of(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(FrozenPlacedFeatureBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

    public static final FrozenPlacedFeature CYPRESS_WETLANDS_TREES_WATER = placedFeature("cypress_wetlands_trees_water",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER, CountPlacement.of(20), SurfaceWaterDepthFilter.forMaxDepth(5), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(FrozenPlacedFeatureBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

    //MUSHROOMS
    public static final FrozenPlacedFeature BROWN_SHELF_FUNGUS_PLACED = placedFeature("brown_shelf_fungus_placed",
            WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final FrozenPlacedFeature RED_SHELF_FUNGUS_PLACED = placedFeature("red_shelf_fungus_placed",
            WilderConfiguredFeatures.RED_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_BROWN_MUSHROOM_PLACED = placedFeature("brown_mushroom_placed",
            VegetationFeatures.PATCH_BROWN_MUSHROOM, worldSurfaceSquaredWithCount(10));

    public static final FrozenPlacedFeature HUGE_RED_MUSHROOM_PLACED = placedFeature("huge_red_mushroom_placed",
            TreeFeatures.HUGE_RED_MUSHROOM, RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature HUGE_MUSHROOMS_SWAMP = placedFeature("huge_mushrooms_swamp",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_MUSHROOM_PLACED = placedFeature("mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature MIXED_MUSHROOMS_PLACED = placedFeature("mixed_mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(75), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //GRASS AND FERNS
    public static final FrozenPlacedFeature NEW_GRASS_PLACED = placedFeature("grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(20));

    public static final FrozenPlacedFeature NEW_RARE_GRASS_PLACED = placedFeature("rare_grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(8));

    public static final FrozenPlacedFeature NEW_TALL_GRASS = placedFeature("tall_grass",
            VegetationFeatures.PATCH_TALL_GRASS, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature DENSE_TALL_GRASS_PLACED = placedFeature("dense_tall_grass_placed",
            VegetationFeatures.PATCH_TALL_GRASS, worldSurfaceSquaredWithCount(1));

    public static final FrozenPlacedFeature DENSE_FERN_PLACED = placedFeature("dense_fern_placed",
            VegetationFeatures.PATCH_LARGE_FERN, worldSurfaceSquaredWithCount(1));

    public static final FrozenPlacedFeature SEAGRASS_CYPRESS = placedFeature("seagrass_cypress",
            AquaticFeatures.SEAGRASS_MID, List.of(InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, CountPlacement.of(56), BiomeFilter.biome()));

    public static final FrozenPlacedFeature LARGE_FERN_AND_GRASS = placedFeature("large_fern_and_grass",
            WilderConfiguredFeatures.LARGE_FERN_AND_GRASS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final FrozenPlacedFeature LARGE_FERN_AND_GRASS_RARE = placedFeature("large_fern_and_grass_rare",
            WilderConfiguredFeatures.LARGE_FERN_AND_GRASS, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());


    //FLOWERS
    public static final FrozenPlacedFeature SEEDING_DANDELION = placedFeature("seeding_dandelion",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature SEEDING_DANDELION_MIXED = placedFeature("seeding_dandelion_mixed",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature SEEDING_DANDELION_CYPRESS = placedFeature("seeding_dandelion_cypress",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature CARNATION = placedFeature("carnation",
            WilderConfiguredFeatures.CARNATION, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature DATURA_BIRCH = placedFeature("datura_birch",
            WilderConfiguredFeatures.DATURA, RarityFilter.onAverageOnceEvery(9), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature NEW_FLOWER_PLAIN = placedFeature("flower_plains",
            WilderConfiguredFeatures.NEW_FLOWER_PLAIN, NoiseThresholdCountPlacement.of(-0.8, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature DENSE_FLOWER_PLACED = placedFeature("dense_flower_placed",
            VegetationFeatures.FLOWER_DEFAULT, worldSurfaceSquaredWithCount(1));

    public static final FrozenPlacedFeature FLOWER_FOREST_FLOWERS = placedFeature(
            "flower_forest_flowers",
            VegetationFeatures.FOREST_FLOWERS,
            RarityFilter.onAverageOnceEvery(7),
            InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP,
            CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
            BiomeFilter.biome()
    );

    public static final FrozenPlacedFeature MILKWEED = placedFeature("milkweed",
            WilderConfiguredFeatures.MILKWEED, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature MILKWEED_CYPRESS = placedFeature("milkweed_cypress",
            WilderConfiguredFeatures.MILKWEED, RarityFilter.onAverageOnceEvery(12), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature GLORY_OF_THE_SNOW = placedFeature("glory_of_the_snow",
            WilderConfiguredFeatures.GLORY_OF_THE_SNOW, RarityFilter.onAverageOnceEvery(11), CountPlacement.of(2), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), BiomeFilter.biome());

    //VEGETATION
    public static final FrozenPlacedFeature POLLEN_PLACED = placedFeature("pollen",
            WilderConfiguredFeatures.POLLEN_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(2), PlacementUtils.HEIGHTMAP, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING, 0, 128), BiomeFilter.biome());

    public static final FrozenPlacedFeature PATCH_CATTAIL =
            placedFeature("cattail", WilderConfiguredFeatures.CATTAIL,
                    RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature PATCH_FLOWERED_WATERLILY = placedFeature("patch_flowered_waterlily",
            WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY, worldSurfaceSquaredWithCount(1));

    public static final FrozenPlacedFeature PATCH_ALGAE =
            placedFeature("patch_algae", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature PATCH_ALGAE_5 =
            placedFeature("patch_algae_5", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final FrozenPlacedFeature PATCH_BERRY_FOREST =
            placedFeature("patch_berry_forest", VegetationFeatures.PATCH_BERRY_BUSH, RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final FrozenPlacedFeature TERMITE_PLACED = placedFeature("termite_placed",
            WilderConfiguredFeatures.TERMITE_CONFIGURED, RarityFilter.onAverageOnceEvery(45), CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final FrozenPlacedFeature JELLYFISH_CAVES_BLUE_MESOGLEA = placedFeature(
            "blue_mesoglea",
            WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final FrozenPlacedFeature JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = placedFeature(
            "upside_down_blue_mesoglea",
            WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final FrozenPlacedFeature JELLYFISH_CAVES_PURPLE_MESOGLEA = placedFeature(
            "purple_mesoglea",
            WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final FrozenPlacedFeature JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = placedFeature(
            "upside_down_purple_mesoglea",
            WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final FrozenPlacedFeature PATCH_NEMATOCYST_UP = placedFeature("patch_nematocyst_up", WilderConfiguredFeatures.PATCH_NEMATOCYST_UP,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final FrozenPlacedFeature PATCH_NEMATOCYST_DOWN = placedFeature("patch_nematocyst_down", WilderConfiguredFeatures.PATCH_NEMATOCYST_DOWN,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final FrozenPlacedFeature PATCH_NEMATOCYST_NORTH = placedFeature("patch_nematocyst_north", WilderConfiguredFeatures.PATCH_NEMATOCYST_NORTH,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final FrozenPlacedFeature PATCH_NEMATOCYST_SOUTH = placedFeature("patch_nematocyst_south", WilderConfiguredFeatures.PATCH_NEMATOCYST_SOUTH,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final FrozenPlacedFeature PATCH_NEMATOCYST_EAST = placedFeature("patch_nematocyst_east", WilderConfiguredFeatures.PATCH_NEMATOCYST_EAST,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
    public static final FrozenPlacedFeature PATCH_NEMATOCYST_WEST = placedFeature("patch_nematocyst_west", WilderConfiguredFeatures.PATCH_NEMATOCYST_WEST,
            CountPlacement.of(ConstantInt.of(9)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());

    public static void FrozenPlacedFeaturePlacedFeatures() {
    }

	public static void bootstap(BootstapContext<PlacedFeature> bootstapContext) throws IllegalAccessException {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		for (Field field : Arrays.stream(WilderConfiguredFeatures.class.getDeclaredFields()).sorted().toList()) {
			Object whatIsThis = field.get(WilderConfiguredFeatures.class);
			if (whatIsThis instanceof FrozenPlacedFeature feature) {
				FrozenPlacementUtils.register(bootstapContext, feature.resourceKey, holderGetter.getOrThrow(feature.featureKey), feature.placementModifiers);
			}
		}
	}

	private static FrozenPlacedFeature placedFeature(String id, FrozenConfiguredFeature feature, PlacementModifier... placementModifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), feature.resourceKey, placementModifiers);
	}

	private static FrozenPlacedFeature placedFeature(String id, FrozenConfiguredFeature feature, List<PlacementModifier> modifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), feature.resourceKey, (PlacementModifier[]) modifiers.toArray());
	}

	private static FrozenPlacedFeature placedFeature(String id, ResourceKey<ConfiguredFeature<?, ?>> featureKey, PlacementModifier... placementModifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), featureKey, placementModifiers);
	}

	private static FrozenPlacedFeature placedFeature(String id, ResourceKey<ConfiguredFeature<?, ?>> featureKey, List<PlacementModifier> modifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), featureKey, (PlacementModifier[]) modifiers.toArray());
	}
}
