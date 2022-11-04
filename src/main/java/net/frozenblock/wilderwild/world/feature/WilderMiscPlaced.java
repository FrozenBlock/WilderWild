package net.frozenblock.wilderwild.world.feature;

import java.util.List;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;

public final class WilderMiscPlaced {
	private WilderMiscPlaced() {
		throw new UnsupportedOperationException("WilderMiscPlaced contains only static declarations.");
	}

    public static final WilderPlacedFeature FOREST_ROCK_TAIGA = WilderPlacementUtils.wilderPlacedFeature("forest_rock_taiga", MiscOverworldFeatures.FOREST_ROCK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
    public static final WilderPlacedFeature DISK_MUD = WilderPlacementUtils.wilderPlacedFeature("disk_mud", WilderMiscConfigured.DISK_MUD, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomeFilter.biome());
    public static final WilderPlacedFeature MUD_PATH = WilderPlacementUtils.wilderPlacedFeature("mud_path", WilderMiscConfigured.MUD_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final WilderPlacedFeature COARSE_PATH = WilderPlacementUtils.wilderPlacedFeature("coarse_dirt_path", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final WilderPlacedFeature COARSE_PATH_5 = WilderPlacementUtils.wilderPlacedFeature("coarse_dirt_path_5", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final WilderPlacedFeature MOSS_PATH = WilderPlacementUtils.wilderPlacedFeature("moss_path", WilderMiscConfigured.MOSS_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final WilderPlacedFeature SAND_PATH = WilderPlacementUtils.wilderPlacedFeature("sand_path", WilderMiscConfigured.SAND_PATH, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final WilderPlacedFeature PACKED_MUD_PATH = WilderPlacementUtils.wilderPlacedFeature("packed_mud_path", WilderMiscConfigured.PACKED_MUD_PATH, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final WilderPlacedFeature UNDER_WATER_SAND_PATH = WilderPlacementUtils.wilderPlacedFeature("under_water_sand_path", WilderMiscConfigured.UNDER_WATER_SAND_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final WilderPlacedFeature UNDER_WATER_GRAVEL_PATH = WilderPlacementUtils.wilderPlacedFeature("under_water_gravel_path", WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final WilderPlacedFeature UNDER_WATER_CLAY_PATH = WilderPlacementUtils.wilderPlacedFeature("under_water_clay_path", WilderMiscConfigured.UNDER_WATER_CLAY_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());

    public static final WilderPlacedFeature UNDER_WATER_CLAY_PATH_BEACH = WilderPlacementUtils.wilderPlacedFeature("under_water_clay_path_beach", WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final WilderPlacedFeature ORE_PACKED_MUD = WilderPlacementUtils.wilderPlacedFeature("ore_packed_mud", WilderMiscConfigured.ORE_PACKED_MUD, modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250))));
    public static final WilderPlacedFeature ORE_CALCITE = WilderPlacementUtils.wilderPlacedFeature("ore_calcite", WilderMiscConfigured.ORE_CALCITE, modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64))));
    public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);
    public static final WilderPlacedFeature JELLYFISH_DEEPSLATE_POOL = WilderPlacementUtils.wilderPlacedFeature("jellyfish_deepslate_pool", WilderMiscConfigured.DEEPSLATE_POOL, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(67)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final WilderPlacedFeature JELLYFISH_STONE_POOL = WilderPlacementUtils.wilderPlacedFeature("jellyfish_stone_pool", WilderMiscConfigured.STONE_POOL, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(68), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final WilderPlacedFeature MESOGLEA_PILLAR = WilderPlacementUtils.wilderPlacedFeature("blue_mesoglea_pillar", WilderMiscConfigured.UPWARDS_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final WilderPlacedFeature PURPLE_MESOGLEA_PILLAR = WilderPlacementUtils.wilderPlacedFeature("purple_mesoglea_pillar", WilderMiscConfigured.PURPLE_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());

    //General Caves
    public static final WilderPlacedFeature EXTRA_GLOW_LICHEN = WilderPlacementUtils.wilderPlacedFeature("extra_glow_lichen", CaveFeatures.GLOW_LICHEN, CountPlacement.of(UniformInt.of(104, 157)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
    public static final WilderPlacedFeature DEEPSLATE_POOL = WilderPlacementUtils.wilderPlacedFeature("deepslate_pool", WilderMiscConfigured.DEEPSLATE_POOL, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(5)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final WilderPlacedFeature STONE_POOL = WilderPlacementUtils.wilderPlacedFeature("stone_pool", WilderMiscConfigured.STONE_POOL, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.aboveBottom(108)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());

	public static void wilderPlacedFeatureMiscPlaced() {
		WilderWild.logWild("wilderPlacedFeatureing WilderMiscPlaced for", true);
	}

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

}
