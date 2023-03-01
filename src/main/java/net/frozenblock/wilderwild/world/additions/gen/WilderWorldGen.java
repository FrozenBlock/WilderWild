/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.treedecorators.WilderTreeDecorators;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
    public static void generateWildWorldGen() {
		configureBuiltInBiomes();
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

	private static void configureBuiltInBiomes() {
		BiomeModifications.create(WilderSharedConstants.id("remove_fallen_trees"))
				.add(ModificationPhase.REMOVALS,
						BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
						(context) -> {
							if (!WilderSharedConstants.config().fallenLogs()) {
								context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED);
							}
						}
				)
				.add(ModificationPhase.REMOVALS,
						BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
						(context) -> {
							if (!WilderSharedConstants.config().fallenLogs()) {
								context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED);
							}
						}
				);
	}

    private static void replaceFeatures() {
        BiomeModifications.create(WilderSharedConstants.id("replace_forest_grass"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.FOREST_GRASS),
                        (context) -> {
                            if (WilderSharedConstants.config().wildGrass()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLACED);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_birch_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_PLACED);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.BIRCH_TALL);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_TALL_PLACED);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_FLOWER_FOREST);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FOREST);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_PLAINS);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_PLAINS);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_swamp_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SWAMP);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SWAMP);
                            }
                        });
		BiomeModifications.create(WilderSharedConstants.id("replace_badlands_trees"))
				.add(ModificationPhase.REPLACEMENTS,
						BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
						context -> {
							if (WilderSharedConstants.config().wildTrees()) {
								context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SWAMP);
								context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SWAMP);
							}
						});

        BiomeModifications.create(WilderSharedConstants.id("replace_taiga_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SHORT_TAIGA),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_TAIGA);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPRUCE_PLACED);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_PINE_TAIGA),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_SPRUCE_TAIGA),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_grove_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.GROVE),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_GROVE);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_GROVE);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_savanna_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NORMAL_SAVANNA),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SAVANNA);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_SAVANNA),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_snowy_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SNOWY_PLAINS),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SNOWY);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SNOWY);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_windswept_hills_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_HILLS),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_HILLS);
                            }
                        })
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_FOREST),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_FOREST);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_dark_forest_vegetation"))
				.add(ModificationPhase.REPLACEMENTS,
						BiomeSelectors.tag(WilderBiomeTags.DARK_FOREST),
						context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.DARK_FOREST_VEGETATION);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_FOREST_VEGETATION);
                            }
                        });

        BiomeModifications.create(WilderSharedConstants.id("replace_meadow_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.MEADOW),
                        context -> {
                            if (WilderSharedConstants.config().wildTrees()) {
                                context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_MEADOW);
                                context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_MEADOW);
                            }
                        });

    }

    private static void generatePollen() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_POLLEN),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN);
    }
}
