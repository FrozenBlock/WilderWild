package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {
    public static void generateTrees() {

        if (ClothConfigInteractionHandler.dyingTrees()) {

        }
        if (ClothConfigInteractionHandler.fallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.BIRCH_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_BIRCH_PLACED.getResourceKey());
            BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.getResourceKey());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getResourceKey());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_OAK_AND_BIRCH_PLACED.getResourceKey());
        }
        if (ClothConfigInteractionHandler.wildTrees()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_BIRCH_AND_OAK.getResourceKey());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SHORT_SPRUCE_PLACED.getResourceKey());
        }
    }
}
