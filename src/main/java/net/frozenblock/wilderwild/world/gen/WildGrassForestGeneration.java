package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WildPlacedFeature;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.gen.GenerationStep;

public class WildGrassForestGeneration {
    public static void generateGrassForest() {
        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeature.NEW_GRASS_PLACED.getKey().get());
    }
}