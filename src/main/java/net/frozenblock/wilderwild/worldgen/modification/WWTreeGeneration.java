/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.worldgen.features.placed.WWTreePlaced;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWTreeGeneration {

	public static void generateTrees() {
		BiomeModifications.create(WWConstants.id("snapped_tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().treeGeneration.snappedTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_OAK_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_OAK_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_BIRCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_SPRUCE_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_SNAPPED_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_SPRUCE_COMMON_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_OAK_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_CYPRESS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_CYPRESS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_LARGE_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_BIRCH_AND_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_ACACIA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_ACACIA_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_ACACIA_AND_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_CHERRY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_CHERRY_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_DARK_OAK_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_DARK_OAK_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_PALE_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_PALE_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_MAPLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_MAPLE_PLACED.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SNAPPED_MAPLE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_CRIMSON_FUNGI)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWTreePlaced.SNAPPED_CRIMSON_FUNGI.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNAPPED_WARPED_FUNGI)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWTreePlaced.SNAPPED_WARPED_FUNGI.getKey());
						}
					}
				});

		BiomeModifications.create(WWConstants.id("fallen_tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					WWWorldgenConfig.TreeGeneration treeGeneration = WWWorldgenConfig.get().treeGeneration;
					if (treeGeneration.fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_BIRCH_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.CLEAN_FALLEN_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.DECORATED_FALLEN_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CLEAN_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.CLEAN_FALLEN_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.MOSSY_FALLEN_TREES_MIXED_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_CHERRY_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_CHERRY_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_ACACIA_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_ACACIA_AND_OAK_PLACED.getKey());
						}

						if (treeGeneration.palm) {
							if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_PALM)) {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_PALM_PLACED.getKey());
							}

							if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_PALM_RARE)) {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_PALM_PLACED_RARE.getKey());
							}

							if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)) {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.getKey());
							}
						} else {
							if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)) {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_JUNGLE_AND_OAK_PLACED.getKey());
							}
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.LARGE_FALLEN_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_FALLEN_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.LARGE_FALLEN_JUNGLE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.FALLEN_DARK_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_FALLEN_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.FALLEN_DARK_OAK_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_PALE_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWPlacedFeatures.FALLEN_PALE_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_SWAMP_TREES)) {
							if (treeGeneration.willow) {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_SWAMP_TREES_WILLOW.getKey());
							} else {
								generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_SWAMP_TREES.getKey());
							}
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_MANGROVE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_MANGROVE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_MAPLE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_MAPLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.MIXED_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.FLOWER_FIELD)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED_2.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_CRIMSON_FUNGI)) {
							generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WWTreePlaced.FALLEN_CRIMSON_FUNGI.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FALLEN_WARPED_FUNGI)) {
							generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WWTreePlaced.FALLEN_WARPED_FUNGI.getKey());
						}
					}
				});

		BiomeModifications.create(WWConstants.id("palms")).add(
			ModificationPhase.POST_PROCESSING,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (WWWorldgenConfig.get().treeGeneration.palm) {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					if (biomeSelectionContext.getBiomeKey().equals(Biomes.SPARSE_JUNGLE)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PALM.getKey());
					}

					if (biomeSelectionContext.getBiomeKey().equals(Biomes.JUNGLE)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PALM_JUNGLE.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PALMS)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PALM_RARE.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_WARM_BEACH_PALMS)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PALMS_WARM_BEACH.getKey());
					}

					if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.OASIS)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PALMS_OASIS.getKey());
					}

					if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.ARID_SAVANNA)) {
						generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.ARID_SAVANNA_TREES.getKey());
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.ARID_SAVANNA_TREES_PALM.getKey());
					}
				}
			});

		BiomeModifications.create(WWConstants.id("tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().treeGeneration.treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SHORT_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHORT_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BIG_COARSE_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.BIG_SHRUB.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FOREST_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHRUBS_FOREST.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHRUBS.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHORT_MEGA_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.getKey());
						}
					}
				});
	}
}
