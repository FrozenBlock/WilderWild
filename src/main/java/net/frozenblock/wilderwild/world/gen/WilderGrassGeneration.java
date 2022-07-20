package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public class WilderGrassGeneration {
    public static void generateGrassForest() {
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.unwrapKey().get());

    }

    public static void generateGrassTaiga() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.unwrapKey().get());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.unwrapKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_RARE_GRASS_PLACED.unwrapKey().get());
    }
}