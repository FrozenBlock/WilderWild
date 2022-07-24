package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class WilderBiomeTags {
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = of("firefly_spawnable_during_day");
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_CAVE = of("firefly_spawnable_cave");

    private WilderBiomeTags() {
    }

    private static TagKey<Biome> of(String path) {
        return TagKey.of(Registry.BIOME_KEY, WilderWild.id(path));
    }
}
