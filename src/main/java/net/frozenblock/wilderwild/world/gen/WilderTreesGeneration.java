package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public final class WilderTreesGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_BIRCH_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_BIRCH_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_BIRCH_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_BIRCH_AND_OAK.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_OAK_AND_BIRCH_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SWAMP.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_PLAINS.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_FLOWER_FOREST.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SPRUCE_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SHORT_SPRUCE_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA, BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA, BiomeKeys.OLD_GROWTH_PINE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.GROVE),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_GROVE.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_PINE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_SPRUCE_TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_FOREST.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_HILLS.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_HILLS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SNOWY_PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SNOWY.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_DARK_FOREST_VEGETATION.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.MEADOW),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_MEADOW.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SAVANNA, BiomeKeys.SAVANNA_PLATEAU),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.WINDSWEPT_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.getKey().orElseThrow());

    }
}
