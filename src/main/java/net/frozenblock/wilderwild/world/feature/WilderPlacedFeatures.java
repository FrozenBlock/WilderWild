package net.frozenblock.wilderwild.world.feature;

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

    public static final Holder<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = register("fallen_oak_and_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED = register("fallen_oak_and_birch_placed",
            WilderConfiguredFeatures.FALLEN_BIRCH_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = register("fallen_oak_and_cypress_placed",
            WilderConfiguredFeatures.FALLEN_CYPRESS_AND_OAK, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_BIRCH_PLACED = register("fallen_birch_placed",
            WilderConfiguredFeatures.FALLEN_BIRCH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> FALLEN_SPRUCE_PLACED = register("fallen_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //TREES
    public static final Holder<PlacedFeature> TREES_PLAINS = register("trees_plains", WilderConfiguredFeatures.TREES_PLAINS,
            PlacementUtils.countExtra(0, 0.05F, 1), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)), BiomeFilter.biome());

    public static final Holder<PlacedFeature> TREES_BIRCH_AND_OAK = register("trees_birch_and_oak",
            WilderConfiguredFeatures.TREES_BIRCH_AND_OAK, treePlacement(PlacementUtils.countExtra(12, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_FLOWER_FOREST = register("trees_flower_forest",
            WilderConfiguredFeatures.TREES_FLOWER_FOREST, treePlacement(PlacementUtils.countExtra(8, 0.1F, 1)));

    public static final Holder<PlacedFeature> DARK_FOREST_VEGETATION = register("dark_forest_vegetation",
            WilderConfiguredFeatures.DARK_FOREST_VEGETATION, CountPlacement.of(16), InSquarePlacement.spread(), TREE_THRESHOLD, PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome());

    public static final Holder<PlacedFeature> TREES_BIRCH = register("trees_birch",
            WilderConfiguredFeatures.TREES_BIRCH, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> BIRCH_TALL = register("birch_tall",
            WilderConfiguredFeatures.TREES_BIRCH_TALL, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> SPRUCE_PLACED = register("spruce_placed",
            WilderConfiguredFeatures.TREES_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> SHORT_SPRUCE_PLACED = register("short_spruce_placed",
            WilderConfiguredFeatures.SHORT_TREES_TAIGA, treePlacement(PlacementUtils.countExtra(5, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_OLD_GROWTH_PINE_TAIGA1 = register("trees_old_growth_pine_taiga",
            WilderConfiguredFeatures.TREES_OLD_GROWTH_PINE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_OLD_GROWTH_SPRUCE_TAIGA1 = register("trees_old_growth_spruce_taiga",
            WilderConfiguredFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_SNOWY = register("trees_snowy",
            WilderTreeConfigured.SPRUCE, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));

    public static final Holder<PlacedFeature> TREES_GROVE = register("trees_grove",
            WilderConfiguredFeatures.TREES_GROVE, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_WINDSWEPT_HILLS = register("trees_windswept_hills",
            WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(0, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_WINDSWEPT_FOREST = register("trees_windswept_forest",
            WilderConfiguredFeatures.TREES_WINDSWEPT_HILLS, treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_MEADOW = register("trees_meadow",
            WilderConfiguredFeatures.MEADOW_TREES, treePlacement(RarityFilter.onAverageOnceEvery(100)));

    public static final Holder<PlacedFeature> WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees",
            WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(2, 0.1F, 1)));

    public static final Holder<PlacedFeature> SAVANNA_TREES = register("savanna_trees",
            WilderConfiguredFeatures.SAVANNA_TREES, treePlacement(PlacementUtils.countExtra(1, 0.1F, 1)));

    public static final Holder<PlacedFeature> TREES_SWAMP = register("trees_swamp", WilderTreeConfigured.SWAMP_TREE,
            PlacementUtils.countExtra(2, 0.1F, 1), InSquarePlacement.spread(), SurfaceWaterDepthFilter.forMaxDepth(4), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.defaultBlockState(), BlockPos.ZERO)));

    public static final Holder<PlacedFeature> MIXED_TREES = register("mixed_trees",
            WilderConfiguredFeatures.MIXED_TREES, treePlacement(PlacementUtils.countExtra(10, 0.1F, 1)));

    public static final Holder<PlacedFeature> CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES, CountPlacement.of(28), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

    public static final Holder<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER, CountPlacement.of(20), SurfaceWaterDepthFilter.forMaxDepth(5), PlacementUtils.HEIGHTMAP_OCEAN_FLOOR, BiomeFilter.biome(), BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.defaultBlockState(), BlockPos.ZERO)));

    //MUSHROOMS
    public static final Holder<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = register("brown_shelf_fungus_placed",
            WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final Holder<PlacedFeature> RED_SHELF_FUNGUS_PLACED = register("red_shelf_fungus_placed",
            WilderConfiguredFeatures.RED_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final Holder<PlacedFeature> BROWN_MUSHROOM_PLACED = register("brown_mushroom_placed",
            VegetationFeatures.PATCH_BROWN_MUSHROOM, worldSurfaceSquaredWithCount(10));

    public static final Holder<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = register("huge_red_mushroom_placed",
            TreeFeatures.HUGE_RED_MUSHROOM, RarityFilter.onAverageOnceEvery(90), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> MUSHROOM_PLACED = register("mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(4), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    public static final Holder<PlacedFeature> MIXED_MUSHROOMS_PLACED = register("mixed_mushroom_placed",
            VegetationFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilter.onAverageOnceEvery(75), InSquarePlacement.spread(),
            PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

    //GRASS AND FERNS
    public static final Holder<PlacedFeature> GRASS_PLACED = register("grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(20));

    public static final Holder<PlacedFeature> RARE_GRASS_PLACED = register("rare_grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(8));

    public static final Holder<PlacedFeature> TALL_GRASS = register("tall_grass",
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

    public static final Holder<PlacedFeature> FLOWER_PLAINS = register("flower_plains",
            WilderConfiguredFeatures.FLOWER_PLAINS, NoiseThresholdCountPlacement.of(-0.8, 15, 4), RarityFilter.onAverageOnceEvery(32), InSquarePlacement.spread(),
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

	public static final Holder<PlacedFeature> NEMATOCYST = PlacementUtils.register(
			"nematocyst",
			WilderConfiguredFeatures.NEMATOCYST,
			CountPlacement.of(ConstantInt.of(64)),
			InSquarePlacement.spread(),
			PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
			BiomeFilter.biome()
	);

	public static final Holder<PlacedFeature> NEMATOCYST_PURPLE = PlacementUtils.register(
			"nematocyst_purple",
			WilderConfiguredFeatures.NEMATOCYST_PURPLE,
			CountPlacement.of(ConstantInt.of(64)),
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
