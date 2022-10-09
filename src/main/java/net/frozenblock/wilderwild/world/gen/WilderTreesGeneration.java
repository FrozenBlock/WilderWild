package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {
    public static void generateTrees() {

        if (ClothConfigInteractionHandler.dyingTrees()) {

        }
        if (ClothConfigInteractionHandler.fallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_BIRCH_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_OAK_AND_BIRCH_PLACED.unwrapKey().orElseThrow());
        }
        if (ClothConfigInteractionHandler.wildTrees()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_BIRCH_AND_OAK.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SPRUCE_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SHORT_SPRUCE_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.GROVE),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_GROVE.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_PINE_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_FOREST.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_GRAVELLY_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_HILLS.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SNOWY_PLAINS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SNOWY.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_DARK_FOREST_VEGETATION.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.MEADOW),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_MEADOW.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_SAVANNA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.unwrapKey().orElseThrow());
        }

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.unwrapKey().orElseThrow());

    }
}
