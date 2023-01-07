package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
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
	// SWAMP
	public static final ResourceKey<PlacedFeature> DISK_MUD = key("disk_mud");
	public static final ResourceKey<PlacedFeature> MUD_PATH = key("mud_path");
	// TAIGA
	public static final ResourceKey<PlacedFeature> COARSE_PATH = key("coarse_dirt_path");
	public static final ResourceKey<PlacedFeature> COARSE_PATH_5 = key("coarse_dirt_path_5");
	public static final ResourceKey<PlacedFeature> FOREST_ROCK_TAIGA = key("forest_rock_taiga");
	// CYPRESS WETLANDS
	public static final ResourceKey<PlacedFeature> UNDER_WATER_SAND_PATH = key("under_water_sand_path");
	public static final ResourceKey<PlacedFeature> UNDER_WATER_GRAVEL_PATH = key("under_water_gravel_path");
	public static final ResourceKey<PlacedFeature> UNDER_WATER_CLAY_PATH = key("under_water_clay_path");
	// BEACH AND RIVER
	public static final ResourceKey<PlacedFeature> UNDER_WATER_CLAY_PATH_BEACH = key("under_water_clay_path_beach");
	public static final ResourceKey<PlacedFeature> UNDER_WATER_GRAVEL_PATH_RIVER = key("under_water_gravel_path_river");
	// SAVANNA
	public static final ResourceKey<PlacedFeature> PACKED_MUD_PATH = key("packed_mud_path");
	// JUNGLE
	public static final ResourceKey<PlacedFeature> MOSS_PATH = key("moss_path");
	// DESERT
	public static final ResourceKey<PlacedFeature> ORE_PACKED_MUD = key("ore_packed_mud");
	public static final ResourceKey<PlacedFeature> SANDSTONE_PATH = key("sandstone_path");
	// BADLANDS
	public static final ResourceKey<PlacedFeature> COARSE_DIRT_PATH_SMALL = key("coarse_dirt_path_small");
	public static final ResourceKey<PlacedFeature> PACKED_MUD_PATH_BADLANDS = key("packed_mud_path_badlands");
	// JELLYFISH CAVES
	public static final ResourceKey<PlacedFeature> EXTRA_GLOW_LICHEN = key("extra_glow_lichen");
	public static final ResourceKey<PlacedFeature> STONE_POOL = key("stone_pool");
	public static final ResourceKey<PlacedFeature> DEEPSLATE_POOL = key("deepslate_pool");
	public static final ResourceKey<PlacedFeature> ORE_CALCITE = key("ore_calcite");
	public static final BlockPredicate ONLY_IN_WATER_PREDICATE = BlockPredicate.matchesBlocks(Blocks.WATER);
	public static final ResourceKey<PlacedFeature> JELLYFISH_DEEPSLATE_POOL = key("jellyfish_deepslate_pool");
	public static final ResourceKey<PlacedFeature> JELLYFISH_STONE_POOL = key("jellyfish_stone_pool");
	public static final ResourceKey<PlacedFeature> MESOGLEA_PILLAR = key("blue_mesoglea_pillar");
	public static final ResourceKey<PlacedFeature> PURPLE_MESOGLEA_PILLAR = key("purple_mesoglea_pillar");
	// OASIS
	public static final ResourceKey<PlacedFeature> SAND_POOL = key("sand_pool");
	public static final ResourceKey<PlacedFeature> GRASS_PATH = key("grass_path");
	public static final ResourceKey<PlacedFeature> MOSS_PATH_OASIS = key("moss_path_oasis");

	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, WilderSharedConstants.id(path));
	}
}
