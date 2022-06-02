package net.frozenblock.wilderwild.world.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.MiscConfiguredFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.placementmodifier.*;

public class WildMiscPlaced {
    public static final RegistryEntry<PlacedFeature> FOREST_ROCK_TAIGA;
    public static final RegistryEntry<PlacedFeature> DISK_MUD;
    public static final RegistryEntry<PlacedFeature> MUD_PATH;
    public static final RegistryEntry<PlacedFeature> COARSE_PATH;
    public static final RegistryEntry<PlacedFeature> MOSS_PATH;

    public WildMiscPlaced() {
    }
    static {
        FOREST_ROCK_TAIGA = WildPlacedFeatures.register("forest_rock_taiga", MiscConfiguredFeatures.FOREST_ROCK, new PlacementModifier[]{RarityFilterPlacementModifier.of(7), SquarePlacementModifier.of(), PlacedFeatures.MOTION_BLOCKING_HEIGHTMAP, BiomePlacementModifier.of()});
        DISK_MUD = WildPlacedFeatures.register("disk_mud", WildMiscConfigured.DISK_MUD, new PlacementModifier[]{CountPlacementModifier.of(1), SquarePlacementModifier.of(), PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BlockFilterPlacementModifier.of(BlockPredicate.matchingBlocks(new Block[]{Blocks.GRASS_BLOCK, Blocks.DIRT})), BiomePlacementModifier.of()});
        MUD_PATH = WildPlacedFeatures.register("mud_path", WildMiscConfigured.MUD_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        COARSE_PATH = WildPlacedFeatures.register("coarse_dirt_path", WildMiscConfigured.COARSE_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
        MOSS_PATH = WildPlacedFeatures.register("moss_path", WildMiscConfigured.MOSS_PATH, RarityFilterPlacementModifier.of(3), SquarePlacementModifier.of(), PlacedFeatures.WORLD_SURFACE_WG_HEIGHTMAP, BiomePlacementModifier.of());
    }
}
