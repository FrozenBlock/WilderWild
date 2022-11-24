package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
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
    public static final TagKey<Biome> BIRCH_FOREST = bind("birch_forest");
    public static final TagKey<Biome> DARK_FOREST = bind("dark_forest");
    public static final TagKey<Biome> MEADOW = bind("meadow");
	public static final TagKey<Biome> FOREST_GRASS = bind("forest_grass");

    private static TagKey<Biome> bind(String path) {
        return TagKey.create(Registries.BIOME, WilderSharedConstants.id(path));
    }
}
