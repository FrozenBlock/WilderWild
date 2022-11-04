package net.frozenblock.wilderwild.world.feature;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import net.frozenblock.lib.worldgen.feature.FrozenConfiguredFeature;
import net.frozenblock.lib.worldgen.feature.FrozenPlacedFeature;
import net.frozenblock.lib.worldgen.feature.util.FrozenConfiguredFeatureUtils;
import net.frozenblock.lib.worldgen.feature.util.FrozenPlacementUtils;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
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

	public static final FrozenPlacedFeature DISK_MUD = feature("disk_mud", WilderMiscConfigured.DISK_MUD, CountPlacement.of(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlocks(Blocks.GRASS_BLOCK, Blocks.DIRT)), BiomeFilter.biome());
    public static final FrozenPlacedFeature MUD_PATH = feature("mud_path", WilderMiscConfigured.MUD_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final FrozenPlacedFeature COARSE_PATH = feature("coarse_dirt_path", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final FrozenPlacedFeature COARSE_PATH_5 = feature("coarse_dirt_path_5", WilderMiscConfigured.COARSE_PATH, RarityFilter.onAverageOnceEvery(5), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final FrozenPlacedFeature MOSS_PATH = feature("moss_path", WilderMiscConfigured.MOSS_PATH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final FrozenPlacedFeature SAND_PATH = feature("sand_path", WilderMiscConfigured.SAND_PATH, RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());
    public static final FrozenPlacedFeature PACKED_MUD_PATH = feature("packed_mud_path", WilderMiscConfigured.PACKED_MUD_PATH, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final FrozenPlacedFeature UNDER_WATER_SAND_PATH = feature("under_water_sand_path", WilderMiscConfigured.UNDER_WATER_SAND_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final FrozenPlacedFeature UNDER_WATER_GRAVEL_PATH = feature("under_water_gravel_path", WilderMiscConfigured.UNDER_WATER_GRAVEL_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());
    public static final FrozenPlacedFeature UNDER_WATER_CLAY_PATH = feature("under_water_clay_path", WilderMiscConfigured.UNDER_WATER_CLAY_PATH, InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_TOP_SOLID, BiomeFilter.biome());

    public static final FrozenPlacedFeature UNDER_WATER_CLAY_PATH_BEACH = feature("under_water_clay_path_beach", WilderMiscConfigured.UNDER_WATER_CLAY_PATH_BEACH, RarityFilter.onAverageOnceEvery(3), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final FrozenPlacedFeature ORE_PACKED_MUD = feature("ore_packed_mud", WilderMiscConfigured.ORE_PACKED_MUD, modifiersWithCount(5, HeightRangePlacement.uniform(VerticalAnchor.absolute(42), VerticalAnchor.absolute(250))));
    public static final FrozenPlacedFeature ORE_CALCITE = feature("ore_calcite", WilderMiscConfigured.ORE_CALCITE, modifiersWithCount(2, HeightRangePlacement.uniform(VerticalAnchor.absolute(-54), VerticalAnchor.absolute(64))));
    public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);

    public static final FrozenPlacedFeature MESOGLEA_PILLAR = feature("blue_mesoglea_pillar", WilderMiscConfigured.UPWARDS_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
    public static final FrozenPlacedFeature PURPLE_MESOGLEA_PILLAR = feature("purple_mesoglea_pillar", WilderMiscConfigured.PURPLE_MESOGLEA_PILLAR, CountPlacement.of(7), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), ONLY_IN_WATER_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());

    //IDS
	public static final ResourceKey<PlacedFeature> FOREST_ROCK_TAIGA = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "forest_rock_taiga");
	public static final ResourceKey<PlacedFeature> EXTRA_GLOW_LICHEN = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "extra_glow_lichen");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_POOL = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "deepslate_pool");
	public static final ResourceKey<PlacedFeature> STONE_POOL = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "stone_pool");

	public static final ResourceKey<PlacedFeature> JELLYFISH_DEEPSLATE_POOL = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "jellyfish_deepslate_pool");
	public static final ResourceKey<PlacedFeature> JELLYFISH_STONE_POOL = FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, "jellyfish_stone_pool");

	public static void FrozenPlacedFeatureMiscPlaced() {
		WilderWild.logWild("FrozenPlacedFeatureing WilderMiscPlaced for", true);
	}

	public static void bootstap(BootstapContext<PlacedFeature> bootstapContext) throws IllegalAccessException {
		HolderGetter<ConfiguredFeature<?, ?>> holderGetter = bootstapContext.lookup(Registry.CONFIGURED_FEATURE_REGISTRY);
		for (Field field : Arrays.stream(WilderMiscPlaced.class.getDeclaredFields()).sorted().toList()) {
			Object whatIsThis = field.get(WilderMiscPlaced.class);
			if (whatIsThis instanceof FrozenPlacedFeature feature) {
				FrozenPlacementUtils.register(bootstapContext, feature.resourceKey, holderGetter.getOrThrow(feature.featureKey), feature.placementModifiers);
			}
		}
		FrozenPlacementUtils.register(bootstapContext, EXTRA_GLOW_LICHEN, holderGetter.getOrThrow(CaveFeatures.GLOW_LICHEN), CountPlacement.of(UniformInt.of(104, 157)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
		FrozenPlacementUtils.register(bootstapContext, FOREST_ROCK_TAIGA, holderGetter.getOrThrow(MiscOverworldFeatures.FOREST_ROCK), RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

		FrozenPlacementUtils.register(bootstapContext, DEEPSLATE_POOL, holderGetter.getOrThrow(WilderMiscConfigured.DEEPSLATE_POOL), RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(5)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		FrozenPlacementUtils.register(bootstapContext, STONE_POOL, holderGetter.getOrThrow(WilderMiscConfigured.STONE_POOL), RarityFilter.onAverageOnceEvery(13), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.absolute(5), VerticalAnchor.aboveBottom(108)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		FrozenPlacementUtils.register(bootstapContext, JELLYFISH_DEEPSLATE_POOL, holderGetter.getOrThrow(WilderMiscConfigured.DEEPSLATE_POOL), CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(67)), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
		FrozenPlacementUtils.register(bootstapContext, JELLYFISH_STONE_POOL, holderGetter.getOrThrow(WilderMiscConfigured.STONE_POOL), CountPlacement.of(30), InSquarePlacement.spread(), HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(68), VerticalAnchor.top()), EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
	}

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

	public static FrozenPlacedFeature feature(String id, FrozenConfiguredFeature feature, PlacementModifier... placementModifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), feature.resourceKey, placementModifiers);
	}

	public static FrozenPlacedFeature feature(String id, FrozenConfiguredFeature feature, List<PlacementModifier> modifiers) {
		return new FrozenPlacedFeature(FrozenPlacementUtils.createKey(WilderSharedConstants.MOD_ID, id), feature.resourceKey, (PlacementModifier[]) modifiers.toArray());
	}

}
