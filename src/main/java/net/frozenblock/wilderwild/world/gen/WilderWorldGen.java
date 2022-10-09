package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.gen.treedecorators.WilderTreeDecorators;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
    public static void generateWildWorldGen() {
        replaceFeatures();
        WilderFlowersGeneration.generateFlower();
        WilderGrassGeneration.init();
        WilderMiscGeneration.generateMisc();

        WilderTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
        WilderMushroomGeneration.generateMushroom();

        WilderBiomeSettings.init();

        generatePollen();
    }

    private static void replaceFeatures() {
        BiomeModifications.create(WilderWild.id("replace_forest_grass"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(BiomeTags.IS_FOREST),
                        (context) -> {
                            if (ClothConfigInteractionHandler.wildGrass()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_GRASS_FOREST.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.value());
                            }
                        });

        BiomeModifications.create(WilderWild.id("replace_birch_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (ClothConfigInteractionHandler.wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_BIRCH_PLACED.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (ClothConfigInteractionHandler.wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.BIRCH_TALL.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_BIRCH_PLACED.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
                        context -> {
                            if (ClothConfigInteractionHandler.wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH_AND_OAK.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_FLOWER_FOREST.value());
                            }
                        });

        BiomeModifications.create(WilderWild.id("replace_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
                        context -> {
                            if (ClothConfigInteractionHandler.wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_PLAINS.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_PLAINS.value());
                            }
                        });

        BiomeModifications.create(WilderWild.id("replace_swamp_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
                        context -> {
                            if (ClothConfigInteractionHandler.wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SWAMP.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SWAMP.value());
                            }
                        });
    }

    private static void generatePollen() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.unwrapKey().orElseThrow());
    }
}

