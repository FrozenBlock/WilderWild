package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMushroomGeneration {
    public static void generateMushroom() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED.getResourceKey());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_MUSHROOM_PLACED.getResourceKey());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_BROWN_MUSHROOM_PLACED.getResourceKey());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP.getResourceKey());
    }
}
