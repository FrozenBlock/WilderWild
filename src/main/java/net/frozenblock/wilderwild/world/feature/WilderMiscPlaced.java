package net.frozenblock.wilderwild.world.feature;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.MiscOverworldFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
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

	public static final ResourceKey<PlacedFeature> DISK_MUD = key("disk_mud");
    public static final ResourceKey<PlacedFeature> MUD_PATH = key("mud_path");
    public static final ResourceKey<PlacedFeature> COARSE_PATH = key("coarse_dirt_path");
    public static final ResourceKey<PlacedFeature> COARSE_PATH_5 = key("coarse_dirt_path_5");
    public static final ResourceKey<PlacedFeature> MOSS_PATH = key("moss_path");
    public static final ResourceKey<PlacedFeature> SAND_PATH = key("sand_path");
    public static final ResourceKey<PlacedFeature> PACKED_MUD_PATH = key("packed_mud_path");

    public static final ResourceKey<PlacedFeature> UNDER_WATER_SAND_PATH = key("under_water_sand_path");
    public static final ResourceKey<PlacedFeature> UNDER_WATER_GRAVEL_PATH = key("under_water_gravel_path");
    public static final ResourceKey<PlacedFeature> UNDER_WATER_CLAY_PATH = key("under_water_clay_path");

    public static final ResourceKey<PlacedFeature> UNDER_WATER_CLAY_PATH_BEACH = key("under_water_clay_path_beach");

    public static final ResourceKey<PlacedFeature> ORE_PACKED_MUD = key("ore_packed_mud");
    public static final ResourceKey<PlacedFeature> ORE_CALCITE = key("ore_calcite");
    public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);

    public static final ResourceKey<PlacedFeature> MESOGLEA_PILLAR = key("blue_mesoglea_pillar");
    public static final ResourceKey<PlacedFeature> PURPLE_MESOGLEA_PILLAR = key("purple_mesoglea_pillar");

    //IDS
	public static final ResourceKey<PlacedFeature> FOREST_ROCK_TAIGA = key("forest_rock_taiga");
	public static final ResourceKey<PlacedFeature> EXTRA_GLOW_LICHEN = key("extra_glow_lichen");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_POOL = key("deepslate_pool");
	public static final ResourceKey<PlacedFeature> STONE_POOL = key("stone_pool");

	public static final ResourceKey<PlacedFeature> JELLYFISH_DEEPSLATE_POOL = key("jellyfish_deepslate_pool");
	public static final ResourceKey<PlacedFeature> JELLYFISH_STONE_POOL = key("jellyfish_stone_pool");

	public static void bootstrap(FabricWorldgenProvider.Entries entries) {
		WilderWild.logWild("ResourceKey<PlacedFeatureing WilderMiscPlaced for", true);
		register(entries, DISK_MUD, WilderMiscConfigured.DISK_MUD, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomeFilter.biome());
		register(entries, MUD_PATH, WilderMiscConfigured.MUD_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, COARSE_PATH, WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, COARSE_PATH_5, WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, MOSS_PATH, WilderMiscConfigured.MOSS_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, SAND_PATH, WilderMiscConfigured.SAND_PATH, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, PACKED_MUD_PATH, WilderMiscConfigured.PACKED_MUD_PATH, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, UNDER_WATER_SAND_PATH, WilderMiscConfigured.UNDER_WATER_SAND_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		register(entries, UNDER_WATER_GRAVEL_PATH, WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		register(entries, UNDER_WATER_CLAY_PATH, WilderMiscConfigured.UNDER_WATER_CLAY_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
		register(entries, UNDER_WATER_CLAY_PATH_BEACH, WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
		register(entries, ORE_PACKED_MUD, WilderMiscConfigured.ORE_PACKED_MUD, modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250))));
		register(entries, ORE_CALCITE, WilderMiscConfigured.ORE_CALCITE, modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64))));
		register(entries, MESOGLEA_PILLAR, WilderMiscConfigured.UPWARDS_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		register(entries, PURPLE_MESOGLEA_PILLAR, WilderMiscConfigured.PURPLE_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		register(entries, FOREST_ROCK_TAIGA, MiscOverworldFeatures.FOREST_ROCK, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
		register(entries, EXTRA_GLOW_LICHEN, CaveFeatures.GLOW_LICHEN, CountPlacement.of(UniformInt.of(104, 157)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
		register(entries, DEEPSLATE_POOL, WilderMiscConfigured.DEEPSLATE_POOL, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(5)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		register(entries, STONE_POOL, WilderMiscConfigured.STONE_POOL, RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.aboveBottom(108)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		register(entries, JELLYFISH_DEEPSLATE_POOL, WilderMiscConfigured.DEEPSLATE_POOL, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(67)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		register(entries, JELLYFISH_STONE_POOL, WilderMiscConfigured.STONE_POOL, CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(68), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}

	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
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
