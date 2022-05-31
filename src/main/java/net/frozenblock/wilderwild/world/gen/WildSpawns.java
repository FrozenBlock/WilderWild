package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.minecraft.world.biome.BiomeKeys;

public class WildSpawns {

    public static void addFirefliesSwamps() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                WilderWild.FIREFLIES, RegisterEntities.FIREFLY, 8, 2, 4);
    }

}