package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderFlowersGeneration {
    public static void generateFlower() {

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CARNATION),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DATURA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_GLORY_OF_THE_SNOW),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FLOWER_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BROWN_SHELF_FUNGUS),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RED_SHELF_FUNGUS),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FLOWERING_WATER_LILY),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_5);

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SEEDING_DANDELION),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MILKWEED),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_PLAINS);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BERRY_PATCH),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.unwrapKey().orElseThrow());

    }
}
