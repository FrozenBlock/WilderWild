/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.levelgen.modification;

import net.frozenblock.lib.levelgen.biome.api.BiomeSelectors;
import net.frozenblock.lib.levelgen.biome.api.modifications.BiomeModificationContext;
import net.frozenblock.lib.levelgen.biome.api.modifications.BiomeModifications;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.lib.levelgen.feature.api.FrozenLibPlacedFeature;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.placement.WWFlowerPlacements;
import net.frozenblock.wilderwild.tag.WWFlowerBiomeTags;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWFlowerGeneration {

	public static void setup() {
		BiomeModifications.create(WWConstants.id("flower_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (WWWorldgenConfig.FLOWER_GENERATION.get()) {
					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.PLAINS_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FLOWER_PLAINS);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_DEFAULT_PLAINS, WWFlowerPlacements.FLOWER_PLAINS);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SNOWY_PLAINS_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FLOWER_PLAINS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_DEFAULT_SNOWY_PLAINS);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SUNFLOWER_PLAINS_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FLOWER_PLAINS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.WILDFLOWERS_SUNFLOWER_PLAINS, WWFlowerPlacements.PATCH_FLOWER_SUNFLOWER_PLAINS);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.FLOWER_FIELD_FLOWERS)) {
						removeFeatures(
							generationSettings,
							VegetationPlacements.FLOWER_FOREST_FLOWERS,
							VegetationPlacements.FLOWER_FLOWER_FOREST,
							VegetationPlacements.FOREST_FLOWERS,
							VegetationPlacements.FLOWER_DEFAULT
						);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_FLOWER_FIELD, WWFlowerPlacements.PATCH_FLOWER_FLOWER_FIELD);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_FOREST, WWFlowerPlacements.PATCH_FLOWER_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SPARSE_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_SPARSE_FOREST, WWFlowerPlacements.PATCH_FLOWER_SPARSE_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.FLOWER_FOREST_FLOWERS)) {
						removeFeatures(
							generationSettings,
							VegetationPlacements.FLOWER_FOREST_FLOWERS,
							VegetationPlacements.FLOWER_FLOWER_FOREST,
							VegetationPlacements.FOREST_FLOWERS,
							VegetationPlacements.FLOWER_DEFAULT
						);
						addVegetalDecoration(
							generationSettings,
							WWFlowerPlacements.FLOWER_FLOWER_FOREST_1,
							WWFlowerPlacements.FLOWER_FLOWER_FOREST_2,
							WWFlowerPlacements.PATCH_FLOWER_FLOWER_FOREST
						);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.WILDFLOWERS_BIRCH_FOREST)) {
						removeFeatures(generationSettings, VegetationPlacements.WILDFLOWERS_BIRCH_FOREST);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.WILDFLOWERS_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.WILDFLOWERS_SEMI_BIRCH_FOREST)) {
						removeFeatures(generationSettings, VegetationPlacements.WILDFLOWERS_BIRCH_FOREST);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.WILDFLOWERS_SEMI_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.BIRCH_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.OLD_GROWTH_BIRCH_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_OLD_GROWTH_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SEMI_BIRCH_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_SEMI_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.BIRCH_TAIGA_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_BIRCH_TAIGA);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DARK_BIRCH_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_FOREST, WWFlowerPlacements.PATCH_FLOWER_DARK_BIRCH_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.BIRCH_JUNGLE_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT, VegetationPlacements.FLOWER_WARM);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_BIRCH_JUNGLE);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SPARSE_BIRCH_JUNGLE_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT, VegetationPlacements.FLOWER_WARM);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_SPARSE_BIRCH_JUNGLE);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DARK_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(
							generationSettings,
							WWFlowerPlacements.PATCH_PHLOX_AND_LANTANAS_DARK_FOREST,
							WWFlowerPlacements.FLOWER_DARK_FOREST,
							WWFlowerPlacements.PATCH_FLOWER_DARK_FOREST
						);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DARK_TAIGA_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(
							generationSettings,
							WWFlowerPlacements.PHLOX_DARK_TAIGA,
							WWFlowerPlacements.FLOWER_DARK_TAIGA,
							WWFlowerPlacements.PATCH_FLOWER_DARK_TAIGA
						);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.PALE_GARDEN_FLOWERS)) {
						// FLOWER_PALE_GARDEN is left untouched as it's literally just a rare single Eyeblossom
						removeFeatures(generationSettings, VegetationPlacements.PALE_GARDEN_FLOWERS);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_PALE_GARDEN);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.MAPLE_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_MAPLE_FOREST, WWFlowerPlacements.PATCH_FLOWER_MAPLE_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.TUNDRA_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_TUNDRA);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DAPPLED_FOREST_FLOWERS)) {
						removeFeatures(generationSettings, VegetationPlacements.FOREST_FLOWERS, VegetationPlacements.FLOWER_DEFAULT);
						addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_DAPPLED_FOREST);
					}

					if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.MEADOW_FLOWERS)) {
						removeFeatures(
							generationSettings,
							VegetationPlacements.WILDFLOWERS_MEADOW,
							VegetationPlacements.WILDFLOWERS_BIRCH_FOREST,
							VegetationPlacements.FLOWER_MEADOW
						);
						addVegetalDecoration(
							generationSettings,
							WWFlowerPlacements.PATCH_WILDFLOWERS_AND_PHLOX_MEADOW,
							WWFlowerPlacements.FLOWER_MEADOW
						);
					}
				}
			}
		).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.MIXED_FOREST_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.SEEDING_DANDELION_MIXED_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DYING_FOREST_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_DYING_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.DYING_MIXED_FOREST_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_DYING_MIXED_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SNOWY_DYING_FOREST_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.SEEDING_DANDELION_SNOWY_DYING_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.JUNGLE_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_JUNGLE, WWFlowerPlacements.PATCH_FLOWER_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SPARSE_JUNGLE_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.FLOWER_SPARSE_JUNGLE, WWFlowerPlacements.PATCH_FLOWER_SPARSE_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SAVANNA_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.LANTANAS_SAVANNA);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.MILKWEED_SWAMP)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.MILKWEED_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.SWAMP_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.PHLOX_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.MANGROVE_SWAMP_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_MANGROVE_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.CHERRY_GROVE_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.PATCH_FLOWER_CHERRY_GROVE);
				}

				if (biomeSelectionContext.hasTag(WWFlowerBiomeTags.WINDSWEPT_FLOWERS)) {
					addVegetalDecoration(generationSettings, WWFlowerPlacements.SEEDING_DANDELION_WINDSWEPT);
				}
			}
		);
	}

	@SafeVarargs
	private static void removeFeatures(BiomeModificationContext.GenerationSettingsContext generationSettings, ResourceKey<PlacedFeature>... placements) {
		for (ResourceKey<PlacedFeature> placement : placements) generationSettings.removeFeature(placement);
	}

	@SafeVarargs
	private static void addVegetalDecoration(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placement.getKey());
	}

	private WWFlowerGeneration() {}
}
