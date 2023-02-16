package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderGrassGeneration {

    public static void init() {
        if (ClothConfigInteractionHandler.wildGrass()) {

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_NEW_RARE_GRASS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_GRASS_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS.unwrapKey().orElseThrow());

        }
    }
}
