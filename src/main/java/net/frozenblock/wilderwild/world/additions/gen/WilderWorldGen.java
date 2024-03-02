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

package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.frozenblock.wilderwild.world.generation.conditionsource.BetaBeachConditionSource;
import net.frozenblock.wilderwild.world.generation.treedecorators.WilderTreeDecorators;
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

		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WilderSharedConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());
	}

	private static void configureBuiltInBiomes() {
		BiomeModifications.create(WilderSharedConstants.id("remove_fallen_trees"))
			.add(ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(RegisterWorldgen.CYPRESS_WETLANDS),
				(context) -> {
					if (!WorldgenConfig.get().fallenTrees) {
						context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
					}
				}
			)
			.add(ModificationPhase.REMOVALS,
				BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
				(context) -> {
					if (!WorldgenConfig.get().fallenTrees) {
						context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
					}
				}
			);
		BiomeModifications.create(WilderSharedConstants.id("rainforest_flowers"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(RegisterWorldgen.RAINFOREST),
				context -> {
					if (WorldgenConfig.get().flowerGeneration) {
						context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getKey());
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_RAINFOREST.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("temperate_rainforest_flowers"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(RegisterWorldgen.TEMPERATE_RAINFOREST),
				context -> {
					if (WorldgenConfig.get().flowerGeneration) {
						context.getGenerationSettings().removeFeature(WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey());
					}
				});
	}

	private static void replaceFeatures() {
		BiomeModifications.create(WilderSharedConstants.id("add_new_snow"))
			.add(ModificationPhase.POST_PROCESSING,
				BiomeSelectors.all(),
				context -> {
					if (context.getGenerationSettings().removeFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)) {
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
						if (WorldgenConfig.get().snowBelowTrees) {
							context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_BLANKET.getKey());
						}
						if (WorldgenConfig.get().surfaceTransitions) {
							context.getGenerationSettings().addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SNOW_AND_ICE_TRANSITION.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_forest_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.FOREST_GRASS),
				context -> {
					if (WorldgenConfig.get().grassGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLACED.getKey());
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_plains_grass"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.PLAINS_GRASS),
				context -> {
					if (WorldgenConfig.get().grassGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.PATCH_GRASS_PLAIN);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GRASS_PLAINS_PLACED.getKey());
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS_PLAINS.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_cherry_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.CHERRY_TREES),
				(context) -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_CHERRY);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CHERRY_TREES.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_forest_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK_ORIGINAL.getKey());
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_birch_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.BIRCH_TALL);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIRCH_TALL.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK);
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_FLOWER_FOREST);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_FLOWER_FOREST.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_plains_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.NON_FROZEN_PLAINS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_PLAINS);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_PLAINS.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_badlands_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_BADLANDS);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_TREES.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_swamp_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.SWAMP_TREES),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SWAMP);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SWAMP.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_taiga_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.SHORT_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_TAIGA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPRUCE_PLACED.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.TALL_PINE_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.TALL_SPRUCE_TAIGA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA1.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_grove_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.GROVE),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_GROVE);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_GROVE.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_savanna_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.NORMAL_SAVANNA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SAVANNA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SAVANNA_TREES.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_SAVANNA),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_snowy_plains_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.SNOWY_PLAINS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_SNOWY);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_SNOWY.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_windswept_hills_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_HILLS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_HILLS.getKey());
					}
				})
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.WINDSWEPT_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_WINDSWEPT_FOREST.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_dark_forest_vegetation"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.DARK_FOREST),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.DARK_FOREST_VEGETATION);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DARK_FOREST_VEGETATION.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_meadow_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.MEADOW),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_MEADOW);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_MEADOW.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("replace_water_trees"))
			.add(ModificationPhase.REPLACEMENTS,
				BiomeSelectors.tag(WilderBiomeTags.HAS_WATER_SHRUBS),
				context -> {
					if (WorldgenConfig.get().treeGeneration) {
						context.getGenerationSettings().removeFeature(VegetationPlacements.TREES_WATER);
						context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS_WATER.getKey());
					}
				});

	}

	private static void generatePollen() {
		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_POLLEN),
			GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.POLLEN_PLACED.getKey());
	}

}
