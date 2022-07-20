package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.MiscConfiguredFeatures;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class WilderMiscPlaced {
    public static final RegistryEntry<PlacedFeature> FOREST_ROCK_TAIGA = WilderPlacedFeatures.register("forest_rock_taiga", MiscConfiguredFeatures.FOREST_ROCK, RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> DISK_MUD = WilderPlacedFeatures.register("disk_mud", WilderMiscConfigured.DISK_MUD, CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> MUD_PATH = WilderPlacedFeatures.register("mud_path", WilderMiscConfigured.MUD_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> COARSE_PATH = WilderPlacedFeatures.register("coarse_dirt_path", WilderMiscConfigured.COARSE_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> COARSE_PATH_5 = WilderPlacedFeatures.register("coarse_dirt_path_5", WilderMiscConfigured.COARSE_PATH, RarityFilterPlacementModifier.of(5), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> MOSS_PATH = WilderPlacedFeatures.register("moss_path", WilderMiscConfigured.MOSS_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> SAND_PATH = WilderPlacedFeatures.register("sand_path", WilderMiscConfigured.SAND_PATH, RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> SANDY_DIRT_PATH_1 = WilderPlacedFeatures.register("sandy_dirt_path_1", WilderMiscConfigured.SANDY_DIRT_PATH_1, RarityFilterPlacementModifier.of(6), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> SANDY_DIRT_PATH_2 = WilderPlacedFeatures.register("sandy_dirt_path_2", WilderMiscConfigured.SANDY_DIRT_PATH_2, RarityFilterPlacementModifier.of(9), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> UNDER_WATER_SAND_PATH = WilderPlacedFeatures.register("under_water_sand_path", WilderMiscConfigured.UNDER_WATER_SAND_PATH, SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> UNDER_WATER_GRAVEL_PATH = WilderPlacedFeatures.register("under_water_gravel_path", WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());
    public static final RegistryEntry<PlacedFeature> UNDER_WATER_CLAY_PATH = WilderPlacedFeatures.register("under_water_clay_path", WilderMiscConfigured.UNDER_WATER_CLAY_PATH, SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, BiomePlacementModifier.of());

    public static final RegistryEntry<PlacedFeature> ORE_SANDY_DIRT = WilderPlacedFeatures.register("ore_sandy_dirt", WilderMiscConfigured.ORE_SANDY_DIRT, modifiersWithCount(5,HeightRangePlacementModifier.uniform(YOffset.fixed(32), YOffset.fixed(250))));

    public WilderMiscPlaced() {
    }
    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }
    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

}
