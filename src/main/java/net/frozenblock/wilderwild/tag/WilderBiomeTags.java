package net.frozenblock.wilderwild.tag;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public class WilderBiomeTags {
    public static final TagKey<Biome> FIREFLY_SPAWNABLE_DURING_DAY = of("firefly_spawnable_during_day");
    public static final TagKey<Biome> SLIMES_SPAWN_ON_FLOATING_MOSS = of("slimes_spawn_on_floating_moss");

    private WilderBiomeTags() {
    }

    private static TagKey<Biome> of(String path) {
        return TagKey.create(Registry.BIOME_REGISTRY, WilderWild.id(path));
    }
}
