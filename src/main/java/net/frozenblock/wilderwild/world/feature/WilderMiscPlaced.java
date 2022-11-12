package net.frozenblock.wilderwild.world.feature;

import java.util.Arrays;
import java.util.List;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricWorldgenProvider;
import net.frozenblock.lib.worldgen.feature.api.FrozenPlacementUtils;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

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

	private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    public static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, WilderSharedConstants.id(path));
	}
}
