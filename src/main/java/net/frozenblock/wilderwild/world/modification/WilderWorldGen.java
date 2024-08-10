/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.world.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.placed.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.feature.placed.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.impl.treedecorators.WilderTreeDecorators;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderWorldGen {
	private WilderWorldGen() {
		throw new UnsupportedOperationException("WilderWorldGen contains only static declarations.");
	}

	public static void generateWildWorldGen() {
		configureBuiltInBiomes();
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

		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WilderConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());
	}

	private static void configureBuiltInBiomes() {
		BiomeModifications.create(WilderConstants.id("remove_fallen_trees")).add(
				ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
				(context) -> {
					if (!WorldgenConfig.get().fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
					}
				}
			)
			.add(
				ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
				(context) -> {
					if (!WorldgenConfig.get().fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
					}
				}
			);

		BiomeModifications.create(WilderConstants.id("flowers_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.RAINFOREST),
			context -> {
				if (WorldgenConfig.get().flowerGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(WilderPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_RAINFOREST.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("flowers_temperate_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(RegisterWorldgen.TEMPERATE_RAINFOREST),
			context -> {
				if (WorldgenConfig.get().flowerGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey());
				}
			});
	}

	private static void replaceFeatures() {
		BiomeModifications.create(WilderConstants.id("add_new_snow"))
			.add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.all(),
				context -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					if (generationSettings.removeFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
						if (WorldgenConfig.get().snowBelowTrees) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_BLANKET.getKey());
						}
						if (WorldgenConfig.get().surfaceTransitions) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_AND_ICE_TRANSITION.getKey());
						}
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_forest_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.FOREST_GRASS),
				context -> {
					if (WorldgenConfig.get().grassGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLACED.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_plains_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.PLAINS_GRASS),
				context -> {
					if (WorldgenConfig.get().grassGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_PLAIN);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLAINS_PLACED.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS_PLAINS.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_cherry_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.CHERRY_TREES),
				(context) -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_CHERRY);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CHERRY_TREES.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_forest_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK_ORIGINAL.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_birch_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.BIRCH_TALL);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_TALL.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						generationSettings.removeFeature(VegetationPlacements.TREES_FLOWER_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FOREST.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_plains_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_PLAINS);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_PLAINS.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_badlands_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_BADLANDS);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_TREES.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_swamp_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_SWAMP);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SWAMP.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_taiga_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.SHORT_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPRUCE_PLACED.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.TALL_PINE_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.TALL_SPRUCE_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA1.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_grove_trees")).add
			(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.GROVE),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_GROVE);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_GROVE.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_savanna_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.NORMAL_SAVANNA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_SAVANNA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_SAVANNA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_snowy_plains_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.SNOWY_PLAINS),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_SNOWY);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SNOWY.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_windswept_hills_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_HILLS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_HILLS.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_FOREST.getKey());
					}
				});

		BiomeModifications.create(WilderConstants.id("replace_dark_forest_vegetation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.DARK_FOREST),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.DARK_FOREST_VEGETATION);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_FOREST_VEGETATION.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_meadow_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.MEADOW),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_MEADOW);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_MEADOW.getKey());
				}
			});

		BiomeModifications.create(WilderConstants.id("replace_water_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WilderBiomeTags.HAS_WATER_SHRUBS),
			context -> {
				if (WorldgenConfig.get().treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_WATER);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS_WATER.getKey());
				}
			});

	}

	private static void generatePollen() {
		BiomeModifications.create(WilderConstants.id("pollen_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_POLLEN)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.getKey());
					}
				});
	}

}
