package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class WilderBiomeTags {
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = bind("firefly_spawnable_during_day");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_CAVE = bind("firefly_spawnable_cave");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE = bind("firefly_spawnable");
    public static final TagKey<Biome> ABANDONED_CABIN_HAS_STRUCTURE = bind("has_structure/abandoned_cabin");
    public static final TagKey<Biome> HAS_GRAVEL_BEACH = bind("has_gravel_beach");
    public static final TagKey<Biome> HAS_JELLYFISH = bind("has_jellyfish");
    public static final TagKey<Biome> HAS_SAND_BEACH = bind("has_sand_beach");
    public static final TagKey<Biome> HAS_SAND_BEACH_OTHER = bind("has_sand_beach_other");
    public static final TagKey<Biome> PEARLESCENT_JELLYFISH = bind("pearlescent_jellyfish");
    public static final TagKey<Biome> NO_POOLS = bind("no_pools");

    private WilderBiomeTags() {
    }

    private static TagKey<Biome> bind(String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, WilderWild.id(path));
    }
}
