package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;

public final class WilderSpawns {

    public static void addFireflies() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

}