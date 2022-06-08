package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WildPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public class WildMushroomGeneration {
    public static void generateMushroom() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FOREST, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.HUGE_RED_MUSHROOM_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_MUSHROOM_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.NEW_BROWN_MUSHROOM_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.HUGE_MUSHROOMS_SWAMP.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.MIXED_MUSHROOMS_PLACED.getKey().get());
    }
}