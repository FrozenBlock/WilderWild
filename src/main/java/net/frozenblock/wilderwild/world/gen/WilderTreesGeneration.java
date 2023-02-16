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
            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_BIRCH_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_PLACED);

            BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED);

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED);

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED);
        }
        if (ClothConfigInteractionHandler.wildTrees()) {
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK);

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_SPRUCE),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_SPRUCE_PLACED);
        }
    }
}
