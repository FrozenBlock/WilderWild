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
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.features.feature.WilderPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {

	public static void generateTrees() {
		BiomeModifications.create(WilderSharedConstants.id("snapped_tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().snappedTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_SNAPPED_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_CYPRESS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_CYPRESS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_ACACIA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_ACACIA_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_ACACIA_AND_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_CHERRY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_CHERRY_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_DARK_OAK_PLACED.getKey());
						}

						// CLEARINGS
						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_OAK_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_SNAPPED_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SNAPPED_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_DARK_OAK_CLEARING_PLACED.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("fallen_tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().fallenTrees) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_BIRCH_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CLEAN_FALLEN_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.DECORATED_FALLEN_LARGE_SPRUCE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.DECORATED_FALLEN_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_CLEAN_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.CLEAN_FALLEN_LARGE_SPRUCE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CLEAN_FALLEN_LARGE_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.CLEAN_FALLEN_LARGE_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_MIXED_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_CHERRY_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_CHERRY_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_ACACIA_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_ACACIA_AND_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_PALM)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_PALM_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_PLACED_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_FALLEN_JUNGLE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_FALLEN_LARGE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.LARGE_FALLEN_JUNGLE_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.FALLEN_DARK_OAK_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_FALLEN_DARK_OAK)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderPlacedFeatures.FALLEN_DARK_OAK_COMMON_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_SWAMP_OAK_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_PLACED_SWAMP.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_MANGROVE_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_MANGROVE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.MIXED_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.FLOWER_FIELD)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED_2.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("tree_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().treeGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.getBiomeKey().equals(Biomes.SPARSE_JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(Biomes.JUNGLE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_JUNGLE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PALMS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_WARM_BEACH_PALMS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_WARM_BEACH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SHORT_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BIG_COARSE_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIG_SHRUB.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FOREST_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS_FOREST.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SHRUB)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.getKey());
						}
					}
				});
	}
}
