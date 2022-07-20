package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.world.level.biome.Biomes;

public class WilderSpawns {

    public static void addFirefliesSwamps() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 12, 2, 4);
    }

    public static void addFirefliesOthers() {
        //TODO: Decide which biomes fireflies should spawn in, if any at all (probably not honestly)
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.SPARSE_JUNGLE, Biomes.BAMBOO_JUNGLE),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 5, 1, 2);
    }

}