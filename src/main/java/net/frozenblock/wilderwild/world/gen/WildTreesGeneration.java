package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WildPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class WildTreesGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_BIRCH_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_FALLEN_BIRCH_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TALL_BIRCH_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_BIRCH_AND_OAK.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_FALLEN_OAK_AND_BIRCH_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_SWAMP.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_PLAINS.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_FLOWER_FOREST.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_SPRUCE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_SHORT_SPRUCE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.FALLEN_SPRUCE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.GROVE),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_GROVE.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_PINE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_WINDSWEPT_FOREST.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_WINDSWEPT_HILLS.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_SNOWY.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_DARK_FOREST_VEGETATION.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_TREES_MEADOW.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.SAVANNA_TREES.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU, BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.TERMITE_BAOBAB_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.MIXED_TREES.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey().get());
    }
}
