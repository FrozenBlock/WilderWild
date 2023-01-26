package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.misc.mod_compat.WilderModIntegrations;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.treedecorators.WilderTreeDecorators;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
    public static void generateWildWorldGen() {
        replaceFeatures();
        WilderFlowersGeneration.generateFlower();
        WilderGrassGeneration.generateGrass();
        WilderMiscGeneration.generateMisc();

        WilderTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
        WilderMushroomGeneration.generateMushroom();

        WilderBiomeSettings.init();

        generatePollen();
    }

    private static void replaceFeatures() {
        BiomeModifications.create(WilderSharedConstants.id("replace_forest_grass"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.FOREST_GRASS),
                        (context) -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildGrass()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_GRASS_FOREST.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_GRASS_PLACED.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_GRASS.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_birch_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_BIRCH_PLACED.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.BIRCH_TALL.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TALL_BIRCH_PLACED.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH_AND_OAK.value());
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_FLOWER_FOREST.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_FLOWER_FOREST.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_PLAINS.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_PLAINS.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_swamp_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SWAMP.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SWAMP.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_taiga_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SHORT_TAIGA),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_TAIGA.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_SPRUCE_PLACED.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_PINE_TAIGA),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_PINE_TAIGA.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_SPRUCE_TAIGA),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_OLD_GROWTH_SPRUCE_TAIGA.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_grove_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.GROVE),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_GROVE.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_GROVE.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_savanna_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NORMAL_SAVANNA),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SAVANNA.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_SAVANNA),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_snowy_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SNOWY_PLAINS),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SNOWY.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_SNOWY.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_windswept_hills_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_HILLS),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_HILLS.value());
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_FOREST),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_WINDSWEPT_FOREST.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_dark_forest_vegetation"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.DARK_FOREST),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.DARK_FOREST_VEGETATION.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_DARK_FOREST_VEGETATION.value());
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_meadow_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.MEADOW),
                        context -> {
                            if (WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration().wildTrees()) {
                                context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_MEADOW.value());
                                context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.NEW_TREES_MEADOW.value());
                            }
                        });

    }

    private static void generatePollen() {

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.SUNFLOWER_PLAINS, RegisterWorldgen.FLOWER_FIELD),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.unwrapKey().orElseThrow());
    }
}

