package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.world.biome.BiomeKeys;

public class WildSpawns {

    public static void addFirefliesSwamps() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);
    }

    public static void addFirefliesOthers() {
        //TODO: Decide which biomes fireflies should spawn in, if any at all (probably not honestly)
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.JUNGLE, BiomeKeys.SPARSE_JUNGLE, BiomeKeys.TAIGA),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 7, 2, 3);
    }

}