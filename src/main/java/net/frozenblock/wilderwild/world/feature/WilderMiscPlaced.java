package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public final class WilderMiscPlaced {
    public static final Holder<PlacedFeature> FOREST_ROCK_TAIGA = WilderPlacedFeatures.register("forest_rock_taiga", MiscOverworldFeatures.FOREST_ROCK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final Holder<PlacedFeature> DISK_MUD = WilderPlacedFeatures.register("disk_mud", WilderMiscConfigured.DISK_MUD, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomeFilter.biome());
    public static final Holder<PlacedFeature> MUD_PATH = WilderPlacedFeatures.register("mud_path", WilderMiscConfigured.MUD_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> COARSE_PATH = WilderPlacedFeatures.register("coarse_dirt_path", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> COARSE_PATH_5 = WilderPlacedFeatures.register("coarse_dirt_path_5", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> MOSS_PATH = WilderPlacedFeatures.register("moss_path", WilderMiscConfigured.MOSS_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> SAND_PATH = WilderPlacedFeatures.register("sand_path", WilderMiscConfigured.SAND_PATH, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final Holder<PlacedFeature> PACKED_MUD_PATH = WilderPlacedFeatures.register("packed_mud_path", WilderMiscConfigured.PACKED_MUD_PATH, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<PlacedFeature> UNDER_WATER_SAND_PATH = WilderPlacedFeatures.register("under_water_sand_path", WilderMiscConfigured.UNDER_WATER_SAND_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final Holder<PlacedFeature> UNDER_WATER_GRAVEL_PATH = WilderPlacedFeatures.register("under_water_gravel_path", WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final Holder<PlacedFeature> UNDER_WATER_CLAY_PATH = WilderPlacedFeatures.register("under_water_clay_path", WilderMiscConfigured.UNDER_WATER_CLAY_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());

    public static final Holder<PlacedFeature> ORE_PACKED_MUD = WilderPlacedFeatures.register("ore_packed_mud", WilderMiscConfigured.ORE_PACKED_MUD, modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250))));

    public WilderMiscPlaced() {
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

}
