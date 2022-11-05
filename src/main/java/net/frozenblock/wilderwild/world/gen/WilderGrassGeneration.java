package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderGrassGeneration {

    public static void init() {
        if (ClothConfigInteractionHandler.wildGrass()) {

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_RARE_GRASS_PLACED.getResourceKey());

            // broken
            /*BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.getResourceKey());*/
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.getResourceKey());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE.getResourceKey());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS.getResourceKey());

        }
    }
}
