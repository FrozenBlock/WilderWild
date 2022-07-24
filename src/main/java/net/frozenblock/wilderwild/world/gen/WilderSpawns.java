package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.minecraft.world.biome.BiomeKeys;

public class WilderSpawns {

    public static void addFirefliesByTags() {
        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_DURING_DAY),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);

        BiomeModifications.addSpawn(BiomeSelectors.tag(WilderBiomeTags.FIREFLY_SPAWNABLE_CAVE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 6, 2, 4);
    }

    public static void addFirefliesOthers() {
        //TODO: Decide which biomes fireflies should spawn in, if any at all (probably not honestly)
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.BAMBOO_JUNGLE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

}