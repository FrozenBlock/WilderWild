package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WildPlacedFeatures;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

import static net.frozenblock.wilderwild.registry.RegisterWorldgen.CYPRESS_FOREST;
import static net.frozenblock.wilderwild.registry.RegisterWorldgen.MIXED_FOREST;

public class WildGrassGeneration {
    public static void generateGrassForest() {
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_GRASS_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_GRASS_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.DENSE_FERN_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.DENSE_TALL_GRASS_PLACED.getKey().get());
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.SEAGRASS_CYPRESS.getKey().get());
    }
    public static void generateGrassTaiga() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, MIXED_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_GRASS_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_RARE_GRASS_PLACED.getKey().get());
    }
}