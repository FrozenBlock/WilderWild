/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.WWTreeDecorators;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWWorldGen {
	private WWWorldGen() {
		throw new UnsupportedOperationException("WWWorldGen contains only static declarations.");
	}

	public static void generateWildWorldGen() {
		configureBuiltInBiomes();
		replaceFeatures();
		WWVegetationGeneration.generateFlower();
		WWVegetationGeneration.generateBush();
		WWVegetationGeneration.generateCacti();
		WWAquaticGeneration.generateAquaticFeatures();
		WWVegetationGeneration.generateGrass();
		WWMiscGeneration.generateMisc();

		WWTreeDecorators.generateTreeDecorators();
		WWTreeGeneration.generateTrees();
		WWVegetationGeneration.generateMushroom();
		WWVegetationGeneration.generatePumpkin();

		WWBiomeSettings.init();

		generatePollen();

		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WWConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());
	}

	private static void configureBuiltInBiomes() {
		BiomeModifications.create(WWConstants.id("remove_fallen_trees")).add(
				ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(WWBiomes.CYPRESS_WETLANDS),
				(context) -> {
					if (!WWWorldgenConfig.get().treeGeneration.fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(WWPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
					}
				}
			).add(
				ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(WWBiomes.MIXED_FOREST),
				(context) -> {
					if (!WWWorldgenConfig.get().treeGeneration.fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(WWPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
					}
				}
			);

		BiomeModifications.create(WWConstants.id("flowers_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.RAINFOREST),
			context -> {
				if (WWWorldgenConfig.get().vegetation.flowerGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(WWPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getKey());
					generationSettings.removeFeature(WWPlacedFeatures.TALL_FLOWER_RAINFOREST_VANILLA.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FLOWER_RAINFOREST.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_FLOWER_RAINFOREST.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("flowers_temperate_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.TEMPERATE_RAINFOREST),
			context -> {
				if (WWWorldgenConfig.get().vegetation.flowerGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(WWPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
					generationSettings.removeFeature(WWPlacedFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST.getKey());
				}
			});
	}

	private static void replaceFeatures() {
		BiomeModifications.create(WWConstants.id("add_new_snow"))
			.add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					if (generationSettings.removeFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
						if (WWWorldgenConfig.get().snowBelowTrees) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_BLANKET.getKey());
						}
						if (WWWorldgenConfig.get().transitionGeneration.snowTransitions) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_AND_ICE_TRANSITION.getKey());
						}
					}
				});

		BiomeModifications.create(WWConstants.id("replace_forest_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.FOREST_GRASS),
				context -> {
					if (WWWorldgenConfig.get().vegetation.grassGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.GRASS_PLACED.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_plains_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.PLAINS_GRASS),
				context -> {
					if (WWWorldgenConfig.get().vegetation.grassGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_PLAIN);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.GRASS_PLAINS_PLACED.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS_PLAINS.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_cherry_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.CHERRY_TREES),
				(context) -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_CHERRY);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.CHERRY_TREES.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_forest_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FOREST),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_AND_OAK_ORIGINAL.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_AND_OAK.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_birch_trees"))
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.BIRCH_TALL);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_TALL.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						generationSettings.removeFeature(VegetationPlacements.TREES_FLOWER_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_FLOWER_FOREST.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_plains_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.NON_FROZEN_PLAINS),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_PLAINS);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_PLAINS.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_badlands_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_BADLANDS);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.WOODED_BADLANDS_TREES.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_swamp_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SWAMP_TREES),
			context -> {
				WWWorldgenConfig.TreeGeneration treeGeneration = WWWorldgenConfig.get().treeGeneration;
				BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (treeGeneration.willow) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SWAMP);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_SURFACE_WILLOW.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_WATER_SHALLOW.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_WATER.getKey());
				} else if (treeGeneration.treeGeneration) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SWAMP);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_taiga_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.SHORT_TAIGA),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SPRUCE_PLACED.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.TALL_PINE_TAIGA),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.TALL_SPRUCE_TAIGA),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA1.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_grove_trees")).add
			(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.GROVE),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_GROVE);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_GROVE.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_savanna_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.NORMAL_SAVANNA),
				context -> {
					WWWorldgenConfig.TreeGeneration treeGeneration = WWWorldgenConfig.get().treeGeneration;
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

					if (treeGeneration.treeGeneration) {
						generationSettings.removeFeature(VegetationPlacements.TREES_SAVANNA);
						if (treeGeneration.baobab) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES_BAOBAB.getKey());
						} else {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES.getKey());
						}
					} else if (treeGeneration.baobab) {
						generationSettings.removeFeature(VegetationPlacements.TREES_SAVANNA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES_BAOBAB_VANILLA.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_SAVANNA),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_bamboo_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.BAMBOO_JUNGLE_TREES),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.BAMBOO_VEGETATION);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.BAMBOO_VEGETATION.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_sparse_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.SPARSE_JUNGLE_TREES),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_SPARSE_JUNGLE);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SPARSE_JUNGLE.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.JUNGLE_TREES),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_JUNGLE);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_JUNGLE.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_mangrove_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.MANGROVE_TREES),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_MANGROVE);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_MANGROVE.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_snowy_plains_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SNOWY_PLAINS),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_SNOWY);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SNOWY.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_windswept_hills_trees")).add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_HILLS),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_WINDSWEPT_HILLS.getKey());
					}
				})
			.add(
				ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_FOREST),
				context -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_WINDSWEPT_FOREST.getKey());
					}
				});

		BiomeModifications.create(WWConstants.id("replace_dark_forest_vegetation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.DARK_FOREST),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.DARK_FOREST_VEGETATION);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.DARK_FOREST_VEGETATION.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_meadow_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.MEADOW),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_MEADOW);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_MEADOW.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_water_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_WATER_SHRUBS),
			context -> {
				if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					generationSettings.removeFeature(VegetationPlacements.TREES_WATER);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHRUBS_WATER.getKey());
				}
			});

	}

	private static void generatePollen() {
		BiomeModifications.create(WWConstants.id("pollen_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

					if (WWWorldgenConfig.GENERATE_POLLEN && biomeSelectionContext.hasTag(WWBiomeTags.HAS_POLLEN)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.POLLEN_PLACED.getKey());
					}
			});
	}

}
