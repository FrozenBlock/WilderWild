package net.frozenblock.wilderwild.tag;

import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class WildBiomeTags {
    public static final TagKey<Biome> IS_BIRCH = of("is_birch");
    private WildBiomeTags() {
    }

    private static TagKey<Biome> of(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(id));
    }
}
