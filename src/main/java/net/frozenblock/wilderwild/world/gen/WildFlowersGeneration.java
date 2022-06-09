package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WildPlacedFeatures;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class WildFlowersGeneration {
    public static void generateFlower() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.FLOWER_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.CARNATION_BIRCH.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.DATURA_BIRCH.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST, BiomeKeys.FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST, BiomeKeys.FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WildPlacedFeatures.RED_SHELF_FUNGUS_PLACED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.PATCH_FLOWERED_WATERLILY.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.PATCH_CATTAIL.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.MEADOW, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.TAIGA, RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.WHITE_DANDELION.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.MEADOW, BiomeKeys.SWAMP, BiomeKeys.SPARSE_JUNGLE, RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WildPlacedFeatures.MILKWEED.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST, RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FOREST_FLOWERS.getKey().get());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.FLOWER_FOREST_FLOWERS.getKey().get());
    }
}