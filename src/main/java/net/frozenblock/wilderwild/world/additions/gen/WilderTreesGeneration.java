package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {
    public static void generateTrees() {

        if (WilderSharedConstants.CONFIG().dyingTrees()) {

        }
        if (WilderSharedConstants.CONFIG().fallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.BIRCH_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_BIRCH_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.unwrapKey().orElseThrow());
            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST, Biomes.FLOWER_FOREST, RegisterWorldgen.PARCHED_FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_FALLEN_OAK_AND_BIRCH_PLACED.unwrapKey().orElseThrow());
        }
        if (WilderSharedConstants.CONFIG().wildTrees()) {
			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.JUNGLE),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_JUNGLE.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DESERT, RegisterWorldgen.ARID_SAVANNA),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_RARE.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_BIRCH_AND_OAK.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.TAIGA, Biomes.SNOWY_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.WINDSWEPT_FOREST, Biomes.WINDSWEPT_HILLS),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SHORT_SPRUCE_PLACED.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BADLANDS, Biomes.WOODED_BADLANDS, Biomes.ERODED_BADLANDS),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIG_SHRUB.unwrapKey().orElseThrow());
        }
    }
}
