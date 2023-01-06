package net.frozenblock.wilderwild.world.additions.feature;

import java.util.List;
import com.google.common.collect.ImmutableList;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WilderPlacedFeatures {
	private WilderPlacedFeatures() {
		throw new UnsupportedOperationException("ResourceKey<PlacedFeatures contains only static declarations.");
	}

    //FALLEN TREES
    public static final ResourceKey<PlacedFeature> FALLEN_TREES_MIXED_PLACED = key("fallen_trees_mixed_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_SPRUCE_PLACED = key("fallen_oak_and_spruce_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_BIRCH_PLACED = key("fallen_oak_and_birch_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_OAK_AND_CYPRESS_PLACED = key("fallen_oak_and_cypress_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_PLACED = key("fallen_birch_placed");

    public static final ResourceKey<PlacedFeature> FALLEN_SPRUCE_PLACED = key("fallen_spruce_placed");

    //TREES
    public static final ResourceKey<PlacedFeature> TREES_PLAINS = key("trees_plains");

    public static final ResourceKey<PlacedFeature> TREES_BIRCH_AND_OAK = key("trees_birch_and_oak");

    public static final ResourceKey<PlacedFeature> TREES_FLOWER_FOREST = key("trees_flower_forest");

    public static final ResourceKey<PlacedFeature> DARK_FOREST_VEGETATION = key("dark_forest_vegetation");

    public static final ResourceKey<PlacedFeature> TREES_BIRCH_PLACED = key("trees_birch");

    public static final ResourceKey<PlacedFeature> BIRCH_TALL_PLACED = key("birch_tall");

    public static final ResourceKey<PlacedFeature> SPRUCE_PLACED = key("spruce_placed");

    public static final ResourceKey<PlacedFeature> SHORT_SPRUCE_PLACED = key("short_spruce_placed");

    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_PINE_TAIGA = key("trees_old_growth_pine_taiga");

    public static final ResourceKey<PlacedFeature> TREES_OLD_GROWTH_SPRUCE_TAIGA = key("trees_old_growth_spruce_taiga");

    public static final ResourceKey<PlacedFeature> TREES_SNOWY = key("trees_snowy");

    public static final ResourceKey<PlacedFeature> TREES_GROVE = key("trees_grove");

    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_HILLS = key("trees_windswept_hills");

    public static final ResourceKey<PlacedFeature> TREES_WINDSWEPT_FOREST = key("trees_windswept_forest");

    public static final ResourceKey<PlacedFeature> TREES_MEADOW = key("trees_meadow");

    public static final ResourceKey<PlacedFeature> WINDSWEPT_SAVANNA_TREES = key("windswept_savanna_trees");

    public static final ResourceKey<PlacedFeature> SAVANNA_TREES = key("savanna_trees");

    public static final ResourceKey<PlacedFeature> TREES_SWAMP = key("trees_swamp");

    public static final ResourceKey<PlacedFeature> MIXED_TREES = key("mixed_trees");

    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES = key("cypress_wetlands_trees");

    public static final ResourceKey<PlacedFeature> CYPRESS_WETLANDS_TREES_WATER = key("cypress_wetlands_trees_water");

    //MUSHROOMS
    public static final ResourceKey<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = key("brown_shelf_fungus_placed");
	public static final Holder<PlacedFeature> BIG_SHRUB = PlacementUtils.register("big_shrub",
			WilderConfiguredFeatures.BIG_SHRUBS, treePlacement(RarityFilter.onAverageOnceEvery(5)));

	public static final Holder<PlacedFeature> PALM = PlacementUtils.register("palm_placed",
			WilderConfiguredFeatures.PALMS, treePlacement(RarityFilter.onAverageOnceEvery(4)));

	public static final Holder<PlacedFeature> PALMS_OASIS = PlacementUtils.register("palms_oasis",
			WilderConfiguredFeatures.PALMS_OASIS, treePlacement(RarityFilter.onAverageOnceEvery(3)));

	public static final Holder<PlacedFeature> PALM_RARE = PlacementUtils.register("palm_rare",
			WilderConfiguredFeatures.PALMS_OASIS, treePlacement(RarityFilter.onAverageOnceEvery(52)));
	//MUSHROOMS
    public static final Holder<PlacedFeature> BROWN_SHELF_FUNGUS_PLACED = register("brown_shelf_fungus_placed",
            WilderConfiguredFeatures.BROWN_SHELF_FUNGUS_CONFIGURED, RarityFilter.onAverageOnceEvery(1), CountPlacement.of(11), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.WORLD_SURFACE_WG, 0, 128), BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> RED_SHELF_FUNGUS_PLACED = key("red_shelf_fungus_placed");

    public static final ResourceKey<PlacedFeature> BROWN_MUSHROOM_PLACED = key("brown_mushroom_placed");

    public static final ResourceKey<PlacedFeature> HUGE_RED_MUSHROOM_PLACED = key("huge_red_mushroom_placed");

    public static final ResourceKey<PlacedFeature> HUGE_MUSHROOMS_SWAMP = key("huge_mushrooms_swamp");

    public static final ResourceKey<PlacedFeature> MUSHROOM_PLACED = key("mushroom_placed");

    public static final ResourceKey<PlacedFeature> MIXED_MUSHROOMS_PLACED = key("mixed_mushroom_placed");

    //GRASS AND FERNS
	public static final Holder<PlacedFeature> OASIS_GRASS_PLACED = register("oasis_grass_placed",
			WilderConfiguredFeatures.OASIS_GRASS, worldSurfaceSquaredWithCount(19));

	public static final Holder<PlacedFeature> OASIS_BUSH_PLACED = register("oasis_bush_placed",
			WilderConfiguredFeatures.OASIS_BUSH, worldSurfaceSquaredWithCount(2));

	public static final Holder<PlacedFeature> DESERT_BUSH_PLACED = register("desert_bush_placed",
			WilderConfiguredFeatures.DESERT_BUSH, RarityFilter.onAverageOnceEvery(7), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

	public static final Holder<PlacedFeature> OASIS_CACTUS_PLACED = register("oasis_cactus_placed",
			WilderConfiguredFeatures.PATCH_CACTUS_OASIS, RarityFilter.onAverageOnceEvery(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> TALL_CACTUS_PLACED = register("tall_cactus_placed",
			WilderConfiguredFeatures.PATCH_CACTUS_TALL, RarityFilter.onAverageOnceEvery(8), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());

	public static final Holder<PlacedFeature> GRASS_PLACED = register("grass_placed",
            VegetationFeatures.PATCH_GRASS_JUNGLE, worldSurfaceSquaredWithCount(20));

    public static final ResourceKey<PlacedFeature> RARE_GRASS_PLACED = key("rare_grass_placed");

    public static final ResourceKey<PlacedFeature> TALL_GRASS = key("tall_grass");

    public static final ResourceKey<PlacedFeature> DENSE_TALL_GRASS_PLACED = key("dense_tall_grass_placed");

    public static final ResourceKey<PlacedFeature> DENSE_FERN_PLACED = key("dense_fern_placed");

    public static final ResourceKey<PlacedFeature> SEAGRASS_CYPRESS = key("seagrass_cypress");

    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS = key("large_fern_and_grass");
    public static final ResourceKey<PlacedFeature> LARGE_FERN_AND_GRASS_RARE = key("large_fern_and_grass_rare");


    //FLOWERS
    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION = key("seeding_dandelion");

    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_MIXED = key("seeding_dandelion_mixed");

    public static final ResourceKey<PlacedFeature> SEEDING_DANDELION_CYPRESS = key("seeding_dandelion_cypress");

    public static final ResourceKey<PlacedFeature> CARNATION = key("carnation");

    public static final ResourceKey<PlacedFeature> DATURA_BIRCH = key("datura_birch");

    public static final ResourceKey<PlacedFeature> FLOWER_PLAINS = key("flower_plains");

    public static final ResourceKey<PlacedFeature> DENSE_FLOWER_PLACED = key("dense_flower_placed");

    public static final ResourceKey<PlacedFeature> FLOWER_FOREST_FLOWERS = key(
            "flower_forest_flowers"
    );

    public static final ResourceKey<PlacedFeature> MILKWEED = key("milkweed");

    public static final ResourceKey<PlacedFeature> MILKWEED_CYPRESS = key("milkweed_cypress");

    public static final ResourceKey<PlacedFeature> GLORY_OF_THE_SNOW = key("glory_of_the_snow");

    //VEGETATION
    public static final ResourceKey<PlacedFeature> POLLEN = key("pollen");

    public static final ResourceKey<PlacedFeature> PATCH_CATTAIL = key("cattail");

    public static final ResourceKey<PlacedFeature> PATCH_FLOWERED_WATERLILY = key("patch_flowered_waterlily");

    public static final ResourceKey<PlacedFeature> PATCH_ALGAE = key("patch_algae");

    public static final ResourceKey<PlacedFeature> PATCH_ALGAE_5 = key("patch_algae_5");
	public static final Holder<PlacedFeature> TUMBLEWEED = register("tumbleweed",
			WilderConfiguredFeatures.TUMBLEWEED, RarityFilter.onAverageOnceEvery(9), CountPlacement.of(1), PlacementUtils.HEIGHTMAP_WORLD_SURFACE, BiomeFilter.biome());

    public static final Holder<PlacedFeature> JELLYFISH_CAVES_BLUE_MESOGLEA = register(
            "blue_mesoglea",
            WilderConfiguredFeatures.JELLYFISH_CAVES_BLUE_MESOGLEA,
            CountPlacement.of(9),
            InSquarePlacement.spread(),
            PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
            EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 1),
            RandomOffsetPlacement.vertical(ConstantInt.of(1)),
            BiomeFilter.biome());

    public static final ResourceKey<PlacedFeature> PATCH_BERRY_FOREST = key("patch_berry_forest");

    public static final ResourceKey<PlacedFeature> TERMITE = key("termite");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_BLUE_MESOGLEA = key("blue_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_BLUE_MESOGLEA = key("upside_down_blue_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_PURPLE_MESOGLEA = key("purple_mesoglea");

    public static final ResourceKey<PlacedFeature> JELLYFISH_CAVES_UPSIDE_DOWN_PURPLE_MESOGLEA = key(
            "upside_down_purple_mesoglea");

    public static final ResourceKey<PlacedFeature> NEMATOCYST = key("nematocyst");
    public static final ResourceKey<PlacedFeature> NEMATOCYST_PURPLE = key("nematocyst_purple");

	public static ResourceKey<PlacedFeature> key(String path) {
		return ResourceKey.create(Registries.PLACED_FEATURE, WilderSharedConstants.id(path));
	}
}
