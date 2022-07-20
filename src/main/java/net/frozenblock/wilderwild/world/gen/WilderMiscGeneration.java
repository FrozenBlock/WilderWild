package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderMiscPlaced;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WilderMiscGeneration {
    public static void generateMisc() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.WINDSWEPT_FOREST),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA, Biomes.DESERT),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SANDY_DIRT_PATH.unwrapKey().get());
    }
}
