package net.frozenblock.wilderwild.world.feature;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.AquaticFeatures;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
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
import java.util.Arrays;
import java.util.List;

public final class WilderPlacedFeatures {
	private WilderPlacedFeatures() {
		throw new UnsupportedOperationException("ResourceKey<PlacedFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final ResourceKey<PlacedFeature> FALLEN_TREES_MIXED_PLACED = key("fallen_trees_mixed_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = key("fallen_oak_and_spruce_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED = key("fallen_oak_and_birch_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = key("fallen_oak_and_cypress_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_PLACED = key("fallen_birch_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_PLACED = key("fallen_spruce_placed");

    //TREES
    public static final ResourceKey<PlacedFeature> TREES_PLAINS = key("trees_plains");

    public static final ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");

    public static final ResourceKey<PlacedFeature> TREES_FLOWER_FOREST = key("trees_flower_forest");

    public static final ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");

    public static final ResourceKey<PlacedFeature> TREES_BIRCH_PLACED = key("trees_birch");

    public static final ResourceKey<PlacedFeature> BIRCH_TALL_PLACED = key("birch_tall");

    public static final ResourceKey<PlacedFeature> SPRUCE_PLACED = key("spruce_placed");

    public static final ResourceKey<PlacedFeature> SHORT_SPRUCE_PLACED = key("short_spruce_placed");

    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");

    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");

    public static final ResourceKey<PlacedFeature> TREES_SNOWY = key("trees_snowy");

    public static final ResourceKey<PlacedFeature> TREES_GROVE = key("trees_grove");

    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");

    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_FOREST = key("trees_windswept_forest");

    public static final ResourceKey<PlacedFeature> TREES_MEADOW = key("trees_meadow");

    public static final ResourceKey<PlacedFeature> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");

    public static final ResourceKey<PlacedFeature> SAVANNA_TREES = key("savanna_trees");

    public static final ResourceKey<PlacedFeature> TREES_SWAMP = key("trees_swamp");

    public static final ResourceKey<PlacedFeature> MIXED_TREES = key("mixed_trees");

    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");

    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");

    //MUSHROOMS
    public static final ResourceKey<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = key("brown_shelf_fungus_placed");

    public static final ResourceKey<PlacedFeature> RED_SHELF_FUNGUS_PLACED = key("red_shelf_fungus_placed");

    public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_PLACED = key("brown_mushroom_placed");

    public static final ResourceKey<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = key("huge_red_mushroom_placed");

    public static final ResourceKey<PlacedFeature> HUGE_MUSHROOMS_SWAMP = key("huge_mushrooms_swamp");

    public static final ResourceKey<PlacedFeature> MUSHROOM_PLACED = key("mushroom_placed");

    public static final ResourceKey<PlacedFeature> MIXED_MUSHROOMS_PLACED = key("mixed_mushroom_placed");

    //GRASS AND FERNS
    public static final ResourceKey<PlacedFeature> GRASS_PLACED = key("grass_placed");

    public static final ResourceKey<PlacedFeature> RARE_GRASS_PLACED = key("rare_grass_placed");

    public static final ResourceKey<PlacedFeature> TALL_GRASS = key("tall_grass");

    public static final ResourceKey<PlacedFeature> DENSE_TALL_GRASS_PLACED = key("dense_tall_grass_placed");

    public static final ResourceKey<PlacedFeature> DENSE_FERN_PLACED = key("dense_fern_placed");

    public static final ResourceKey<PlacedFeature> SEAGRASS_CYPRESS = key("seagrass_cypress");

    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS_RARE = key("large_fern_and_grass_rare");


    //FLOWERS
    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION = key("seeding_dandelion");

    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_MIXED = key("seeding_dandelion_mixed");

    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_CYPRESS = key("seeding_dandelion_cypress");

    public static final ResourceKey<PlacedFeature> CARNATION = key("carnation");

    public static final ResourceKey<PlacedFeature> DATURA_BIRCH = key("datura_birch");

    public static final ResourceKey<PlacedFeature> FLOWER_PLAINS = key("flower_plains");

    public static final ResourceKey<PlacedFeature> DENSE_FLOWER_PLACED = key("dense_flower_placed");

    public static final ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS = key(
            "flower_forest_flowers"
    );

    public static final ResourceKey<PlacedFeature> MILKWEED = key("milkweed");

    public static final ResourceKey<PlacedFeature> MILKWEED_CYPRESS = key("milkweed_cypress");

    public static final ResourceKey<PlacedFeature> GLORY_OF_THE_SNOW = key("glory_of_the_snow");

    //VEGETATION
    public static final ResourceKey<PlacedFeature> POLLEN = key("pollen");

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL = key("cattail");

    public static final ResourceKey<PlacedFeature> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");

    public static final ResourceKey<PlacedFeature> PATCH_ALGAE = key("patch_algae");

    public static final ResourceKey<PlacedFeature> PATCH_ALGAE_5 = key("patch_algae_5");

    public static final ResourceKey<PlacedFeature> PATCH_BERRY_FOREST = key("patch_berry_forest");

    public static final ResourceKey<PlacedFeature> TERMITE = key("termite");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_BLUE_MESOGLEA = key("blue_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("purple_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = key(
            "upside_down_purple_mesoglea");

    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_UP = key("patch_nematocyst_up");
    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_DOWN = key("patch_nematocyst_down");
    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_NORTH = key("patch_nematocyst_north");
    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_SOUTH = key("patch_nematocyst_south");
    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_EAST = key("patch_nematocyst_east");
    public static final ResourceKey<PlacedFeature> PATCH_NEMATOCYST_WEST = key("patch_nematocyst_west");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		register(entries, FALLEN_TREES_MIXED_PLACED, WilderConfiguredFeatures.FALLEN_TREES_MIXED, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, FALLEN_OAK_AND_SPRUCE_PLACED,
				WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, FALLEN_OAK_AND_BIRCH_PLACED,
				WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, FALLEN_OAK_AND_CYPRESS_PLACED,
				WilderConfiguredFeatures.FALLEN_CYPRESS_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, FALLEN_BIRCH_PLACED,
				WilderConfiguredFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, FALLEN_SPRUCE_PLACED,
				WilderConfiguredFeatures.FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP, BiomeFilter.biome()
		);
		register(entries, TREES_PLAINS,
				WilderConfiguredFeatures.TREES_PLAINS,
				PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(),
				TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, TREES_BIRCH_AND_OAK,
				WilderConfiguredFeatures.TREES_BIRCH_AND_OAK,
				treePlacement(PlacementUtils.countExtra(12, 0.1F, 1))
		);
		register(entries, TREES_FLOWER_FOREST,
				WilderConfiguredFeatures.TREES_FLOWER_FOREST,
				treePlacement(PlacementUtils.countExtra(8, 0.1F, 1))
		);
		register(entries, DARK_FOREST_VEGETATION,
				WilderConfiguredFeatures.DARK_FOREST_VEGETATION,
				CountPlacement.of(16), InSquarePlacement.spread(),
				TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BiomeFilter.biome()
		);
		register(entries, TREES_BIRCH_PLACED,
				WilderConfiguredFeatures.TREES_BIRCH,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, BIRCH_TALL_PLACED,
				WilderConfiguredFeatures.TREES_BIRCH_TALL,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, SPRUCE_PLACED,
				WilderConfiguredFeatures.TREES_TAIGA,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, SHORT_SPRUCE_PLACED,
				WilderConfiguredFeatures.SHORT_TREES_TAIGA,
				treePlacement(PlacementUtils.countExtra(5, 0.1F, 1))
		);
		register(entries, TREES_OLD_GROWTH_PINE_TAIGA,
				WilderConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, TREES_OLD_GROWTH_SPRUCE_TAIGA,
				WilderConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, TREES_SNOWY,
				WilderTreeConfigured.SPRUCE,
				treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING)
		);
		register(entries, TREES_GROVE,
				WilderConfiguredFeatures.TREES_GROVE,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, TREES_WINDSWEPT_HILLS,
				WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS,
				treePlacement(PlacementUtils.countExtra(0, 0.1F, 1))
		);
		register(entries, TREES_WINDSWEPT_FOREST,
				WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS,
				treePlacement(PlacementUtils.countExtra(3, 0.1F, 1))
		);
		register(entries, TREES_MEADOW,
				WilderConfiguredFeatures.MEADOW_TREES,
				treePlacement(RarityFilter.onAverageOnceEvery(100))
		);
		register(entries, WINDSWEPT_SAVANNA_TREES,
				WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES,
				treePlacement(PlacementUtils.countExtra(2, 0.1F, 1))
		);
		register(entries, SAVANNA_TREES,
				WilderConfiguredFeatures.SAVANNA_TREES,
				treePlacement(PlacementUtils.countExtra(1, 0.1F, 1))
		);
		register(entries, TREES_SWAMP,
				WilderTreeConfigured.SWAMP_TREE,
				PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(),
				SurfaceWaterDepthFilter.forMaxDepth(4),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, MIXED_TREES,
				WilderConfiguredFeatures.MIXED_TREES,
				treePlacement(PlacementUtils.countExtra(10, 0.1F, 1))
		);
		register(entries, CYPRESS_WETLANDS_TREES,
				WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES,
				CountPlacement.of(28), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, CYPRESS_WETLANDS_TREES_WATER,
				WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER,
				CountPlacement.of(20), SurfaceWaterDepthFilter.forMaxDepth(5),
				PlacementUtils.HEIGHTMAP_OCEAN_FLOOR,
				BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)),
				BiomeFilter.biome()
		);
		register(entries, BROWN_SHELF_FUNGUS_PLACED,
				WilderConfiguredFeatures.BROWN_SHELF_FUNGUS,
				RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(),
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, RED_SHELF_FUNGUS_PLACED,
				WilderConfiguredFeatures.RED_SHELF_FUNGUS,
				RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(),
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, BROWN_MUSHROOM_PLACED,
				VegetationFeatures.PATCH_BROWN_MUSHROOM,
				worldSurfaceSquaredWithCount(10)
		);
		register(entries, HUGE_RED_MUSHROOM_PLACED,
				TreeFeatures.HUGE_RED_MUSHROOM,
				RarityFilter.onAverageOnceEvery(90),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, HUGE_MUSHROOMS_SWAMP,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(5),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, MUSHROOM_PLACED,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, MIXED_MUSHROOMS_PLACED,
				VegetationFeatures.MUSHROOM_ISLAND_VEGETATION,
				RarityFilter.onAverageOnceEvery(75),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, GRASS_PLACED,
				VegetationFeatures.PATCH_GRASS_JUNGLE,
				worldSurfaceSquaredWithCount(20)
		);
		register(entries, RARE_GRASS_PLACED,
				VegetationFeatures.PATCH_GRASS_JUNGLE,
				worldSurfaceSquaredWithCount(8)
		);
		register(entries, TALL_GRASS,
				VegetationFeatures.PATCH_TALL_GRASS,
				RarityFilter.onAverageOnceEvery(3),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, DENSE_TALL_GRASS_PLACED,
				VegetationFeatures.PATCH_TALL_GRASS,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, DENSE_FERN_PLACED,
				VegetationFeatures.PATCH_LARGE_FERN,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, SEAGRASS_CYPRESS,
				AquaticFeatures.SEAGRASS_MID,
				CountPlacement.of(56),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID,
				BiomeFilter.biome()
		);
		register(entries, LARGE_FERN_AND_GRASS,
				WilderConfiguredFeatures.LARGE_FERN_AND_GRASS,
				RarityFilter.onAverageOnceEvery(2),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, LARGE_FERN_AND_GRASS_RARE,
				WilderConfiguredFeatures.LARGE_FERN_AND_GRASS,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, SEEDING_DANDELION,
				WilderConfiguredFeatures.SEEDING_DANDELION,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, SEEDING_DANDELION_MIXED,
				WilderConfiguredFeatures.SEEDING_DANDELION,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, SEEDING_DANDELION_CYPRESS,
				WilderConfiguredFeatures.SEEDING_DANDELION,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, CARNATION,
				WilderConfiguredFeatures.CARNATION,
				RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, DATURA_BIRCH,
				WilderConfiguredFeatures.DATURA,
				RarityFilter.onAverageOnceEvery(9),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, FLOWER_PLAINS,
				WilderConfiguredFeatures.FLOWER_PLAIN,
				NoiseThresholdCountPlacement.of(-0.8, 15, 4),
				RarityFilter.onAverageOnceEvery(32),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, DENSE_FLOWER_PLACED,
				VegetationFeatures.FLOWER_DEFAULT,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, FLOWER_FOREST_FLOWERS,
				VegetationFeatures.FOREST_FLOWERS,
				CountPlacement.of(ClampedInt.of(UniformInt.of(-1, 3), 0, 3)),
				RarityFilter.onAverageOnceEvery(7),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, MILKWEED,
				WilderConfiguredFeatures.MILKWEED,
				RarityFilter.onAverageOnceEvery(12),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, MILKWEED_CYPRESS,
				WilderConfiguredFeatures.MILKWEED,
				RarityFilter.onAverageOnceEvery(12),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, GLORY_OF_THE_SNOW,
				WilderConfiguredFeatures.GLORY_OF_THE_SNOW,
				CountPlacement.of(2),
				RarityFilter.onAverageOnceEvery(11),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		register(entries, POLLEN,
				WilderConfiguredFeatures.POLLEN,
				CountPlacement.of(2),
				RarityFilter.onAverageOnceEvery(1),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.MOTION_BLOCKING, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, PATCH_CATTAIL,
				WilderConfiguredFeatures.CATTAIL,
				RarityFilter.onAverageOnceEvery(4),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, PATCH_FLOWERED_WATERLILY,
				WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY,
				worldSurfaceSquaredWithCount(1)
		);
		register(entries, PATCH_ALGAE,
				WilderConfiguredFeatures.PATCH_ALGAE,
				RarityFilter.onAverageOnceEvery(3),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, PATCH_ALGAE_5,
				WilderConfiguredFeatures.PATCH_ALGAE,
				RarityFilter.onAverageOnceEvery(5),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP,
				BiomeFilter.biome()
		);
		register(entries, PATCH_BERRY_FOREST,
				VegetationFeatures.PATCH_BERRY_BUSH,
				RarityFilter.onAverageOnceEvery(28), InSquarePlacement.spread(),
				PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				BiomeFilter.biome()
		);
		register(entries, TERMITE,
				WilderConfiguredFeatures.TERMITE,
				CountPlacement.of(1),
				RarityFilter.onAverageOnceEvery(45),
				InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
				SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128),
				BiomeFilter.biome()
		);
		register(entries, JELLYFISH_CAVES_BLUE_MESOGLEA,
				WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA,
				WilderConfiguredFeatures.UPSIDE_DOWN_BLUE_MESOGLEA,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, JELLYFISH_CAVES_PURPLE_MESOGLEA,
				WilderConfiguredFeatures.JELLYFISH_CAVES_PURPLE_MESOGLEA,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA,
				WilderConfiguredFeatures.UPSIDE_DOWN_PURPLE_MESOGLEA,
				CountPlacement.of(9),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
				RandomOffsetPlacement.vertical(ConstantInt.of(1)),
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_UP,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_UP,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_DOWN,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_DOWN,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_NORTH,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_NORTH,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_SOUTH,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_SOUTH,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_EAST,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_EAST,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
		register(entries, PATCH_NEMATOCYST_WEST,
				WilderConfiguredFeatures.PATCH_NEMATOCYST_WEST,
				CountPlacement.of(ConstantInt.of(9)),
				InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
				BiomeFilter.biome()
		);
	}

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, WilderSharedConstants.id(path));
	}

	private static void register(FabricWorldgenProvider.Entries entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, PlacementModifier... modifiers) {
		register(entries, resourceKey, configuredResourceKey, Arrays.asList(modifiers));
	}

	private static void register(FabricWorldgenProvider.Entries entries, ResourceKey<PlacedFeature> resourceKey, ResourceKey<ConfiguredFeature<?, ?>> configuredResourceKey, List<PlacementModifier> modifiers) {
		FrozenPlacementUtils.register(entries, resourceKey, entries.getLookup(Registry.CONFIGURED_FEATURE_REGISTRY).getOrThrow(configuredResourceKey), modifiers);
	}
}
