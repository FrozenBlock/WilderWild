package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderFlowersGeneration {
    public static void generateFlower() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST, Biomes.FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.BIRCH_FOREST, Biomes.PLAINS, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.SWAMP, Biomes.MANGROVE_SWAMP, Biomes.TAIGA, Biomes.SNOWY_TAIGA),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DARK_FOREST, Biomes.FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.BIRCH_FOREST, Biomes.PLAINS, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP, RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_5.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.FOREST, Biomes.MEADOW, Biomes.WINDSWEPT_HILLS, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_GRAVELLY_HILLS, Biomes.TAIGA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, Biomes.PLAINS, Biomes.FOREST, Biomes.MEADOW, Biomes.SWAMP, Biomes.SPARSE_JUNGLE),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FLOWER_PLAIN.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST, RegisterWorldgen.MIXED_FOREST),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DESERT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_CACTUS_PLACED.unwrapKey().orElseThrow());

    }
}
