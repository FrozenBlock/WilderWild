package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class WilderMiscGeneration {
    public static void generateMisc() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.WINDSWEPT_FOREST),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.JUNGLE, BiomeKeys.BAMBOO_JUNGLE, BiomeKeys.SPARSE_JUNGLE),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_SAVANNA, BiomeKeys.DESERT),
                GenerationStep.Feature.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD.getKey().orElseThrow());
    }
}
