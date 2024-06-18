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
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.feature.WilderPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderVegetationGeneration {

	public static void generateFlower() {
		BiomeModifications.create(WilderSharedConstants.id("flower_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().flowerGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CARNATION)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DATURA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.CHERRY_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_CHERRY.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_GLORY_OF_THE_SNOW)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.FLOWER_SPARSE_JUNGLE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_JUNGLE_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.FLOWER_JUNGLE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW_SPARSE_JUNGLE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_JUNGLE_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW_JUNGLE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FLOWERING_WATER_LILY)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERING_WATERLILY.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL_UNCOMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_UNCOMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL_UNCOMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD_UNCOMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL_COMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_COMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CATTAIL_COMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD_COMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SEEDING_DANDELION)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_SEEDING_DANDELION)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.COMMON_SEEDING_DANDELION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_SEEDING_DANDELION)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_SEEDING_DANDELION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MILKWEED)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PLAINS_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_PLAINS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BERRY_PATCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FIELD_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FLOWER_FIELD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CYPRESS_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_FLOWERS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_MILKWEED)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SUNFLOWER_PLAINS_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_SUNFLOWER_PLAINS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.MEADOW)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_MEADOW.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BIRCH_CLEARING_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_BIRCH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FOREST_CLEARING_FLOWERS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_CLEARING.getKey());
						}
					}
				});
	}

	public static void generateBush() {
		BiomeModifications.create(WilderSharedConstants.id("bush_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().bushGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GENERIC_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_JUNGLE_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.JUNGLE_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SPARSE_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SPARSE_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BADLANDS_SAND_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_SAND_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BADLANDS_TERRACOTTA_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_WOODED_BADLANDS_TERRACOTTA_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BADLANDS_RARE_SAND_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_RARE_SAND_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DESERT_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DESERT_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_OASIS_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_ARID_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FLOWER_FIELD_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_BUSH_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RAINFOREST_BUSH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GENERIC_BUSH_PLACED.getKey());
						}
					}
			});
	}

	public static void generateCacti() {
		BiomeModifications.create(WilderSharedConstants.id("cactus_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().cactusGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_TALL_CACTUS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_CACTUS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PRICKLY_PEAR)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_PRICKLY_PEAR)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_TALL_BADLANDS_CACTUS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_TALL_CACTUS_PLACED.getKey());
						}
					}
				});
	}

	public static void generateAlgae() {
		BiomeModifications.create(WilderSharedConstants.id("algae_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().algae) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_ALGAE_SMALL)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_SMALL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_ALGAE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE.getKey());
						}
					}
				});
	}

	public static void generateGrass() {
		BiomeModifications.create(WilderSharedConstants.id("tumbleweed_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().tumbleweed) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_TUMBLEWEED_PLANT)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TUMBLEWEED.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("grass_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().grassGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_NEW_RARE_GRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_GRASS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_TALL_GRASS_FLOWER_FIELD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DENSE_FERN)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DENSE_TALL_GRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_WATER_GRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS_AND_GRASS_WATER.getKey());
						}
					}
				});
	}

	public static void generateMushroom() {
		BiomeModifications.create(WilderSharedConstants.id("mushroom_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().mushroomGeneration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_HUGE_RED_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_HUGE_BROWN_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_BROWN_MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BIG_MUSHROOMS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_BROWN_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_RED_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SWAMP_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BIG_MUSHROOM_PATCH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.DARK_FOREST_MUSHROOM_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BROWN_SHELF_FUNGUS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RED_SHELF_FUNGUS)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RAINFOREST_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RAINFOREST_MUSHROOMS_PLACED.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MIXED_MUSHROOM)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED.getKey());
						}
					}
				});
	}

}
