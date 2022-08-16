package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tag.BiomeTags;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.GenerationStep;

public final class WilderFlowersGeneration {
    public static void generateFlower() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST, BiomeKeys.FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, BiomeKeys.TAIGA, BiomeKeys.SNOWY_TAIGA),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.DARK_FOREST, BiomeKeys.FOREST, BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.BIRCH_FOREST, BiomeKeys.PLAINS, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS),
                GenerationStep.Feature.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.SWAMP, BiomeKeys.MANGROVE_SWAMP, RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_5.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.OLD_GROWTH_BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.FOREST, BiomeKeys.MEADOW, BiomeKeys.WINDSWEPT_HILLS, BiomeKeys.WINDSWEPT_FOREST, BiomeKeys.WINDSWEPT_GRAVELLY_HILLS, BiomeKeys.TAIGA),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.BIRCH_FOREST, BiomeKeys.FLOWER_FOREST, BiomeKeys.SUNFLOWER_PLAINS, BiomeKeys.PLAINS, BiomeKeys.FOREST, BiomeKeys.MEADOW, BiomeKeys.SWAMP, BiomeKeys.SPARSE_JUNGLE),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.PLAINS),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FLOWER_PLAIN.getKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST, RegisterWorldgen.MIXED_FOREST),
                GenerationStep.Feature.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.getKey().orElseThrow());

    }
}