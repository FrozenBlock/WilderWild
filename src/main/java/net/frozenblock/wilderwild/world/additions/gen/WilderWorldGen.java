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
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.treedecorators.WilderTreeDecorators;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
	private WilderWorldGen() {
		throw new UnsupportedOperationException("WilderWorldGen contains only static declarations.");
	}

    public static void generateWildWorldGen() {
        replaceFeatures();
        WilderVegetationGeneration.generateFlower();
		WilderVegetationGeneration.generateBush();
		WilderVegetationGeneration.generateCacti();
		WilderVegetationGeneration.generateAlgae();
		WilderVegetationGeneration.generateGrass();
        WilderMiscGeneration.generateMisc();

        WilderTreeDecorators.generateTreeDecorators();
        WilderTreesGeneration.generateTrees();
		WilderVegetationGeneration.generateMushroom();

        WilderBiomeSettings.init();

        generatePollen();
    }

    private static void replaceFeatures() {
		BiomeModifications.create(WilderSharedConstants.id("add_new_snow"))
				.add(ModificationPhase.POST_PROCESSING,
						BiomeSelectors.all(),
						context -> {
							if (context.getGenerationSettings().removeBuiltInFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER.value())) {
								context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER.value());
								if (WilderSharedConstants.config().snowBelowTrees()) {
									context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_BLANKET.getHolder().value());
								}
								context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_AND_ICE_TRANSITION.getHolder().value());
							}
						});

        BiomeModifications.create(WilderSharedConstants.id("replace_forest_grass"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.FOREST_GRASS),
						context -> {
					if (WilderSharedConstants.config().wildGrass()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.PATCH_GRASS_FOREST.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLACED.getHolder().value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS.getHolder().value());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("rainforest_flowers"))
				.add(ModificationPhase.REPLACEMENTS,
						BiomeSelectors.includeByKey(RegisterWorldgen.RAINFOREST),
						context -> {
					if (WilderSharedConstants.config().wildGrass()) {
						context.getGenerationSettings().removeBuiltInFeature(WilderPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getHolder().value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_RAINFOREST.getHolder().value());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("temperate_rainforest_flowers"))
				.add(ModificationPhase.REPLACEMENTS,
						BiomeSelectors.includeByKey(RegisterWorldgen.TEMPERATE_RAINFOREST),
						context -> {
							if (WilderSharedConstants.config().wildGrass()) {
								context.getGenerationSettings().removeBuiltInFeature(WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getHolder().value());
								context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST.getHolder().value());
							}
						});

        BiomeModifications.create(WilderSharedConstants.id("replace_birch_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.BIRCH_TALL.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_TALL.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BIRCH_AND_OAK.value());
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_FLOWER_FOREST.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FOREST.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_PLAINS.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_PLAINS.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_badlands_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
						context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_BADLANDS.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_TREES.getHolder().value());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_swamp_trees"))
				.add(ModificationPhase.REPLACEMENTS,
						BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
						context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SWAMP.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SWAMP.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_taiga_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SHORT_TAIGA),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_TAIGA.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPRUCE_PLACED.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_PINE_TAIGA),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.TALL_SPRUCE_TAIGA),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA1.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_grove_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.GROVE),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_GROVE.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_GROVE.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_savanna_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.NORMAL_SAVANNA),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SAVANNA.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_SAVANNA),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_snowy_plains_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.SNOWY_PLAINS),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_SNOWY.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SNOWY.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_windswept_hills_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_HILLS),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_HILLS.getHolder().value());
					}
				})
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_FOREST),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_FOREST.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_dark_forest_vegetation"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.DARK_FOREST),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.DARK_FOREST_VEGETATION.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_FOREST_VEGETATION.getHolder().value());
					}
				});

        BiomeModifications.create(WilderSharedConstants.id("replace_meadow_trees"))
                .add(ModificationPhase.REPLACEMENTS,
                        BiomeSelectors.tag(WilderBiomeTags.MEADOW),
                        context -> {
					if (WilderSharedConstants.config().wildTrees()) {
						context.getGenerationSettings().removeBuiltInFeature(VegetationPlacements.TREES_MEADOW.value());
						context.getGenerationSettings().addBuiltInFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_MEADOW.getHolder().value());
					}
				});

    }

    private static void generatePollen() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_POLLEN),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.getKey());
    }

}

