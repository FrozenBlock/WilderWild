package net.frozenblock.wilderwild.world.feature;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.ClampedIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.world.gen.feature.OceanPlacedFeatures.seagrassModifiers;
import static net.minecraft.world.gen.feature.VegetationPlacedFeatures.*;

public class WilderPlacedFeatures {
    //FALLEN TREES

    public static final RegistryEntry<PlacedFeature> FALLEN_TREES_MIXED_PLACED = register("fallen_trees_mixed_placed",
            WilderConfiguredFeatures.FALLEN_TREES_MIXED, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = register("fallen_oak_and_spruce_placed",
            WilderConfiguredFeatures.FALLEN_SPRUCE_AND_OAK, RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_FALLEN_OAK_AND_BIRCH_PLACED = register("new_fallen_oak_and_birch_placed",
            WilderConfiguredFeatures.NEW_FALLEN_BIRCH_AND_OAK, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = register("new_fallen_oak_and_cypress_placed",
            WilderConfiguredFeatures.NEW_FALLEN_CYPRESS_AND_OAK, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_FALLEN_BIRCH_PLACED = register("new_fallen_birch_placed",
            WilderTreeConfigured.NEW_FALLEN_BIRCH_TREE, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> FALLEN_SPRUCE_PLACED = register("fallen_spruce_placed",
            WilderTreeConfigured.FALLEN_SPRUCE_TREE, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    //TREES

    public static final RegistryEntry<PlacedFeature> NEW_TREES_PLAINS = register("new_trees_plains", WilderConfiguredFeatures.NEW_TREES_PLAINS,
            PlacedFeatures.createCountExtraModifier(0, 0.05F, 1), SquarePlacementModifier.of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.OAK_SAPLING.getDefaultState(), BlockPos.ORIGIN)), BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_TREES_BIRCH_AND_OAK = register("new_trees_birch_and_oak",
            WilderConfiguredFeatures.NEW_TREES_BIRCH_AND_OAK, modifiers(PlacedFeatures.createCountExtraModifier(12, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_FLOWER_FOREST = register("new_trees_flower_forest",
            WilderConfiguredFeatures.NEW_TREES_FLOWER_FOREST, modifiers(PlacedFeatures.createCountExtraModifier(8, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> MIXED_TREES = register("mixed_trees",
            WilderConfiguredFeatures.MIXED_TREES, modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_DARK_FOREST_VEGETATION = register("new_dark_forest_vegetation",
            WilderConfiguredFeatures.NEW_DARK_FOREST_VEGETATION, CountPlacementModifier.of(16), SquarePlacementModifier.of(), NOT_IN_SURFACE_WATER_MODIFIER, PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_BIRCH_PLACED = register("new_birch_placed",
            WilderTreeConfigured.NEW_BIRCH_BEES_0004, modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1), Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<PlacedFeature> NEW_TALL_BIRCH_PLACED = register("new_tall_birch_placed",
            WilderTreeConfigured.NEW_SUPER_BIRCH_BEES_0004, modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1), Blocks.BIRCH_SAPLING));

    public static final RegistryEntry<PlacedFeature> NEW_SPRUCE_PLACED = register("new_spruce_placed",
            WilderConfiguredFeatures.NEW_TREES_TAIGA, modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_SHORT_SPRUCE_PLACED = register("new_short_spruce_placed",
            WilderConfiguredFeatures.NEW_SHORT_TREES_TAIGA, modifiers(PlacedFeatures.createCountExtraModifier(5, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_OLD_GROWTH_PINE_TAIGA = register("new_trees_old_growth_pine_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA, modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA = register("new_trees_old_growth_spruce_taiga",
            WilderConfiguredFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA, modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_SNOWY = register("new_trees_snowy",
            WilderTreeConfigured.NEW_SPRUCE, modifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1F, 1), Blocks.SPRUCE_SAPLING));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_GROVE = register("new_trees_grove",
            WilderConfiguredFeatures.NEW_TREES_GROVE, modifiers(PlacedFeatures.createCountExtraModifier(10, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_WINDSWEPT_HILLS = register("new_trees_windswept_hills",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, modifiers(PlacedFeatures.createCountExtraModifier(0, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_WINDSWEPT_FOREST = register("new_trees_windswept_forest",
            WilderConfiguredFeatures.NEW_TREES_WINDSWEPT_HILLS, modifiers(PlacedFeatures.createCountExtraModifier(3, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_MEADOW = register("new_trees_meadow",
            WilderConfiguredFeatures.NEW_MEADOW_TREES, modifiers(RarityFilterPlacementModifier.of(100)));

    public static final RegistryEntry<PlacedFeature> WINDSWEPT_SAVANNA_TREES = register("windswept_savanna_trees",
            WilderConfiguredFeatures.WINDSWEPT_SAVANNA_TREES, modifiers(PlacedFeatures.createCountExtraModifier(2, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> SAVANNA_TREES = register("savanna_trees",
            WilderConfiguredFeatures.SAVANNA_TREES, modifiers(PlacedFeatures.createCountExtraModifier(1, 0.1F, 1)));

    public static final RegistryEntry<PlacedFeature> NEW_TREES_SWAMP = register("new_trees_swamp", WilderTreeConfigured.NEW_SWAMP_TREE,
            PlacedFeatures.createCountExtraModifier(2, 0.1F, 1), SquarePlacementModifier.of(), SurfaceWaterDepthFilterPlacementModifier.of(4), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(Blocks.MANGROVE_PROPAGULE.getDefaultState(), BlockPos.ORIGIN)));

    public static final RegistryEntry<PlacedFeature> CYPRESS_WETLANDS_TREES = register("cypress_wetlands_trees",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES, CountPlacementModifier.of(28), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.getDefaultState(), BlockPos.ORIGIN)));

    public static final RegistryEntry<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = register("cypress_wetlands_trees_water",
            WilderConfiguredFeatures.CYPRESS_WETLANDS_TREES_WATER, CountPlacementModifier.of(20), SurfaceWaterDepthFilterPlacementModifier.of(5), PlacedFeatures.OCEAN_FLOOR_HEIGHTMAP, BiomePlacementModifier.of(), BlockFilterPlacementModifier.of(BlockPredicate.wouldSurvive(RegisterBlocks.CYPRESS_SAPLING.getDefaultState(), BlockPos.ORIGIN)));

    //MUSHROOMS
    public static final RegistryEntry<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = register("brown_shelf_fungus_placed",
            WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(16), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG, 0, 128), BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> RED_SHELF_FUNGUS_PLACED = register("red_shelf_fungus_placed",
            WilderConfiguredFeatures.RED_SHELF_FUNGUS_CONFIGURED, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(16), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG, 0, 128), BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_BROWN_MUSHROOM_PLACED = register("new_brown_mushroom_placed",
            VegetationConfiguredFeatures.PATCH_BROWN_MUSHROOM, modifiers(10));

    public static final RegistryEntry<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = register("huge_red_mushroom_placed",
            TreeConfiguredFeatures.HUGE_RED_MUSHROOM, RarityFilterPlacementModifier.of(90), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> HUGE_MUSHROOMS_SWAMP = register("huge_mushrooms_swamp",
            VegetationConfiguredFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_MUSHROOM_PLACED = register("new_mushroom_placed",
            VegetationConfiguredFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> MIXED_MUSHROOMS_PLACED = register("mixed_mushroom_placed",
            VegetationConfiguredFeatures.MUSHROOM_ISLAND_VEGETATION, RarityFilterPlacementModifier.of(75), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    //GRASS AND FERNS
    public static final RegistryEntry<PlacedFeature> NEW_GRASS_PLACED = register("new_grass_placed",
            VegetationConfiguredFeatures.PATCH_GRASS_JUNGLE, modifiers(20));

    public static final RegistryEntry<PlacedFeature> NEW_RARE_GRASS_PLACED = register("new_rare_grass_placed",
            VegetationConfiguredFeatures.PATCH_GRASS_JUNGLE, modifiers(8));

    public static final RegistryEntry<PlacedFeature> NEW_TALL_GRASS = register("new_tall_grass",
            VegetationConfiguredFeatures.PATCH_TALL_GRASS, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> DENSE_TALL_GRASS_PLACED = register("dense_tall_grass_placed",
            VegetationConfiguredFeatures.PATCH_TALL_GRASS, modifiers(1));

    public static final RegistryEntry<PlacedFeature> DENSE_FERN_PLACED = register("dense_fern_placed",
            VegetationConfiguredFeatures.PATCH_LARGE_FERN, modifiers(1));

    public static final RegistryEntry<PlacedFeature> SEAGRASS_CYPRESS = register("seagrass_cypress",
            OceanConfiguredFeatures.SEAGRASS_MID, seagrassModifiers(56));

    //FLOWERS
    public static final RegistryEntry<PlacedFeature> SEEDING_DANDELION = register("seeding_dandelion",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> SEEDING_DANDELION_MIXED = register("seeding_dandelion_mixed",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> SEEDING_DANDELION_CYPRESS = register("seeding_dandelion_cypress",
            WilderConfiguredFeatures.SEEDING_DANDELION, RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> CARNATION = register("carnation",
            WilderConfiguredFeatures.CARNATION, RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> DATURA_BIRCH = register("datura_birch",
            WilderConfiguredFeatures.DATURA, RarityFilterPlacementModifier.of(8), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> NEW_FLOWER_PLAIN = register("new_flower_plains",
            WilderConfiguredFeatures.NEW_FLOWER_PLAIN, NoiseThresholdCountPlacementModifier.of(-0.8, 15, 4), RarityFilterPlacementModifier.of(32), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> DENSE_FLOWER_PLACED = register("dense_flower_placed",
            VegetationConfiguredFeatures.FLOWER_DEFAULT, modifiers(1));

    public static final RegistryEntry<PlacedFeature> FLOWER_FOREST_FLOWERS = register(
            "flower_forest_flowers",
            VegetationConfiguredFeatures.FOREST_FLOWERS,
            RarityFilterPlacementModifier.of(7),
            SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP,
            CountPlacementModifier.of(ClampedIntProvider.create(UniformIntProvider.create(-1, 3), 0, 3)),
            BiomePlacementModifier.of()
    );

    public static final RegistryEntry<PlacedFeature> MILKWEED = register("milkweed",
            WilderConfiguredFeatures.MILKWEED, RarityFilterPlacementModifier.of(12), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> MILKWEED_CYPRESS = register("milkweed_cypress",
            WilderConfiguredFeatures.MILKWEED, RarityFilterPlacementModifier.of(12), SquarePlacementModifier.of(),
            PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> GLORY_OF_THE_SNOW = register("glory_of_the_snow",
            WilderConfiguredFeatures.GLORY_OF_THE_SNOW, RarityFilterPlacementModifier.of(11), CountPlacementModifier.of(2), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SquarePlacementModifier.of(), BiomePlacementModifier.of());

    //VEGETATION
    public static final RegistryEntry<PlacedFeature> POLLEN_PLACED = register("pollen",
            WilderConfiguredFeatures.POLLEN_CONFIGURED, RarityFilterPlacementModifier.of(1), CountPlacementModifier.of(2), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.MOTION_BLOCKING, 0, 128), BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> PATCH_CATTAIL =
            register("cattail", WilderConfiguredFeatures.CATTAIL,
                    RarityFilterPlacementModifier.of(4), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> PATCH_FLOWERED_WATERLILY = register("patch_flowered_waterlily",
            WilderConfiguredFeatures.PATCH_FLOWERED_WATERLILY, modifiers(1));

    public static final RegistryEntry<PlacedFeature> PATCH_ALGAE =
            register("patch_algae", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> PATCH_ALGAE_5 =
            register("patch_algae_5", WilderConfiguredFeatures.PATCH_ALGAE,
                    RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> PATCH_BERRY_FOREST =
            register("patch_berry_forest", VegetationConfiguredFeatures.PATCH_BERRY_BUSH, RarityFilterPlacementModifier.of(28), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> TERMITE_PLACED = register("termite_placed",
            WilderConfiguredFeatures.TERMITE_CONFIGURED, RarityFilterPlacementModifier.of(45), CountPlacementModifier.of(1), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.WORLD_SURFACE_WG, 0, 128), BiomePlacementModifier.of());

    /*public static final RegistryEntry<PlacedFeature> PATCH_CYPRESS_ROOTS = register("patch_cypress_roots",
            WilderConfiguredFeatures.PATCH_CYPRESS_ROOTS, modifiers(1));*/

    public static RegistryEntry<PlacedFeature> register(
            @NotNull String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull List<PlacementModifier> modifiers
    ) {
        return BuiltinRegistries.add(BuiltinRegistries.PLACED_FEATURE, WilderWild.id(id), new PlacedFeature(RegistryEntry.upcast(registryEntry), List.copyOf(modifiers)));
    }

    public static RegistryEntry<PlacedFeature> register(
            @NotNull String id, RegistryEntry<? extends ConfiguredFeature<?, ?>> registryEntry, @NotNull PlacementModifier... modifiers
    ) {
        return register(id, registryEntry, List.of(modifiers));
    }

}
