package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class WilderBiomeTags {
	private WilderBiomeTags() {
		throw new UnsupportedOperationException("WilderBiomeTags contains only static declarations.");
	}

    public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = bind("firefly_spawnable_during_day");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_CAVE = bind("firefly_spawnable_cave");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE = bind("firefly_spawnable");
    public static final TagKey<Biome> ABANDONED_CABIN_HAS_STRUCTURE = bind("has_structure/abandoned_cabin");
    public static final TagKey<Biome> HAS_JELLYFISH = bind("has_jellyfish");
    public static final TagKey<Biome> PEARLESCENT_JELLYFISH = bind("pearlescent_jellyfish");
    public static final TagKey<Biome> NO_POOLS = bind("no_pools");
    public static final TagKey<Biome> NON_FROZEN_PLAINS = bind("non_frozen_plains");
    public static final TagKey<Biome> SWAMP_TREES = bind("swamp_trees");
    public static final TagKey<Biome> SHORT_TAIGA = bind("short_taiga");
    public static final TagKey<Biome> TALL_PINE_TAIGA = bind("tall_pine_taiga");
    public static final TagKey<Biome> TALL_SPRUCE_TAIGA = bind("tall_spruce_taiga");
    public static final TagKey<Biome> GROVE = bind("grove");
    public static final TagKey<Biome> NORMAL_SAVANNA = bind("normal_savanna");
    public static final TagKey<Biome> WINDSWEPT_SAVANNA = bind("windswept_savanna");
    public static final TagKey<Biome> SNOWY_PLAINS = bind("snowy_plains");
    public static final TagKey<Biome> WINDSWEPT_HILLS = bind("windswept_hills");
    public static final TagKey<Biome> WINDSWEPT_FOREST = bind("windswept_forest");
    public static final TagKey<Biome> HAS_FALLEN_BIRCH_TREES = bind("has_fallen_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_BIRCH_TREES = bind("has_fallen_oak_and_birch_trees");
	public static final TagKey<Biome> HAS_FALLEN_OAK_AND_SPRUCE_TREES = bind("has_fallen_oak_and_spruce_trees");
    public static final TagKey<Biome> DARK_FOREST = bind("dark_forest");
    public static final TagKey<Biome> MEADOW = bind("meadow");
	public static final TagKey<Biome> FOREST_GRASS = bind("forest_grass");
	public static final TagKey<Biome> HAS_DATURA = bind("has_datura");
	public static final TagKey<Biome> HAS_CARNATION = bind("has_carnation");
	public static final TagKey<Biome> HAS_CATTAIL = bind("has_cattail");
	public static final TagKey<Biome> HAS_SEEDING_DANDELION = bind("has_seeding_dandelion");
	public static final TagKey<Biome> HAS_MILKWEED = bind("has_milkweed");
	public static final TagKey<Biome> HAS_SHORT_SPRUCE = bind("has_short_spruce");
	public static final TagKey<Biome> HAS_POLLEN = bind("has_pollen");
	public static final TagKey<Biome> HAS_RED_SHELF_FUNGUS = bind("has_red_shelf_fungus");
	public static final TagKey<Biome> HAS_BROWN_SHELF_FUNGUS = bind("has_brown_shelf_fungus");
	public static final TagKey<Biome> HAS_GLORY_OF_THE_SNOW = bind("has_glory_of_the_snow");
	public static final TagKey<Biome> HAS_FLOWERING_WATER_LILY = bind("has_flowering_water_lily");
	public static final TagKey<Biome> HAS_BERRY_PATCH = bind("has_berry_patch");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS = bind("has_large_fern_and_grass");
	public static final TagKey<Biome> HAS_LARGE_FERN_AND_GRASS_RARE = bind("has_large_fern_and_grass_rare");
	public static final TagKey<Biome> HAS_NEW_RARE_GRASS = bind("has_new_rare_grass");
	public static final TagKey<Biome> HAS_DECORATIVE_MUD = bind("has_decorative_mud");
	public static final TagKey<Biome> HAS_PACKED_MUD_ORE = bind("has_packed_mud_ore");
	public static final TagKey<Biome> HAS_COARSE_DIRT_PATH = bind("has_coarse_dirt_path");
	public static final TagKey<Biome> HAS_TAIGA_FOREST_ROCK = bind("has_taiga_forest_rock");
	public static final TagKey<Biome> HAS_MOSS_PATH = bind("has_moss_path");
	public static final TagKey<Biome> HAS_CLAY_PATH = bind("has_clay_path");

	public static final TagKey<Biome> GRAVEL_BEACH = bind("gravel_beaches");
	public static final TagKey<Biome> SAND_BEACHES = bind("sand_beaches");
	public static final TagKey<Biome> MULTI_LAYER_SAND_BEACHES = bind("multi_layer_sand_beaches");

    private static TagKey<Biome> bind(String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, WilderSharedConstants.id(path));
    }
}
