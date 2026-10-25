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
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreePlacements;
import net.frozenblock.wilderwild.data.worldgen.placement.WWTreeVegetationPlacements;
import net.frozenblock.wilderwild.tag.WWTreeFeatureBiomeTags;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWTreeGeneration {

	public static void setup() {
		setupPalmTrees();
		setupSavannaTrees();
		setupTrees();
		setupFallenTrees();
		setupSnappedTrees();
	}

	public static void setupPalmTrees() {
		BiomeModifications.create(WWConstants.id("palm_tree_generation")).add(
			ModificationPhase.POST_PROCESSING,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.PALM)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.PALM);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.PALM_JUNGLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.PALM_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.PALM_RARE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.PALM_RARE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.PALM_WARM_BEACH)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.PALMS_WARM_BEACH);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.PALM_OASIS)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.PALMS_OASIS);
				}
			});
	}

	public static void setupSavannaTrees() {
		BiomeModifications.create(WWConstants.id("replace_savanna_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWTreeFeatureBiomeTags.TREE_SAVANNA),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (WWWorldgenConfig.TREE_GENERATION.get()) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_SAVANNA);
					if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
						addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SAVANNA_BAOBAB);
					} else {
						addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SAVANNA);
					}
				} else if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_SAVANNA);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SAVANNA_BAOBAB_VANILLA);
				}
			});

		BiomeModifications.create(WWConstants.id("replace_windswept_savanna_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWTreeFeatureBiomeTags.TREE_WINDSWEPT_SAVANNA),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				removeFeatures(generationSettings, VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
				addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_WINDSWEPT_SAVANNA);
			});
	}

	public static void setupTrees() {
		BiomeModifications.create(WWConstants.id("tree_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_PLAINS)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_PLAINS);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_PLAINS);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_SNOWY)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_SNOWY);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SNOWY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIRCH_AND_OAK, WWTreeVegetationPlacements.BIRCH_AND_OAK_ORIGINAL);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_FLOWER_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER, VegetationPlacements.TREES_FLOWER_FOREST);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_FLOWER_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_BIRCH_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_BIRCH);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_BIRCH);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_OLD_GROWTH_BIRCH_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.BIRCH_TALL);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_BIRCH_TALL);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_TAIGA)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_TAIGA);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_TAIGA);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_TAIGA_SNOWY)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_TAIGA);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_TAIGA_NO_LITTER);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_TALL_PINE_TAIGA)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_OLD_GROWTH_PINE_TAIGA);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_TALL_SPRUCE_TAIGA)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_OLD_GROWTH_SPRUCE_TAIGA);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_JUNGLE)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_JUNGLE);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_SPARSE_JUNGLE)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_SPARSE_JUNGLE);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SPARSE_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_BAMBOO_JUNGLE)) {
					removeFeatures(generationSettings, VegetationPlacements.BAMBOO_VEGETATION);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_BAMBOO_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_DARK_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.DARK_FOREST_VEGETATION);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.DARK_FOREST_VEGETATION);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_PALE_GARDEN)) {
					removeFeatures(generationSettings, VegetationPlacements.PALE_GARDEN_VEGETATION);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_PALE_GARDEN);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_WOODED_BADLANDS)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_BADLANDS);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_WOODED_BADLANDS);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_SWAMP)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_SWAMP);

					if (WWWorldgenConfig.WILLOW_TREE_GENERATION.get()) {
						addVegetalDecoration(
							generationSettings,
							WWTreeVegetationPlacements.TREE_SWAMP_SURFACE_WILLOW,
							WWTreeVegetationPlacements.TREE_SWAMP_WATER_SHALLOW,
							WWTreeVegetationPlacements.TREE_SWAMP_WATER
						);
					} else {
						addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_SWAMP);
					}
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_MANGROVE_SWAMP)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_MANGROVE);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.MANGROVE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_CHERRY_GROVE)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_CHERRY);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.CHERRY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_DAPPLED_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_DAPPLED_FOREST);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.POPLAR);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_MEADOW)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_MEADOW);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_MEADOW);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_GROVE)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_GROVE);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_GROVE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_WINDSWEPT_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_WINDSWEPT_FOREST);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_WINDSWEPT_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_WINDSWEPT_HILLS)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_WINDSWEPT_HILLS);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.TREE_WINDSWEPT_HILLS);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.TREE_WATER)) {
					removeFeatures(generationSettings, VegetationPlacements.TREES_WATER);
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIG_BUSH_WATER);
				}
			}
		).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SHORT_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SHORT_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SHORT_SPRUCE_SPARSE_LEAF_LITTER)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SHORT_SPRUCE_SPARSE_LEAF_LITTER);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SHORT_MEGA_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SHORT_MEGA_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SHORT_MEGA_SPRUCE_ON_SNOW)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SHORT_MEGA_SPRUCE_ON_SNOW);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.BIG_BUSH_FOREST)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIG_BUSH_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.BIG_BUSH_FOREST_COMMON)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIG_BUSH_FOREST_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.BIG_BUSH)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIG_BUSH);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.BIG_BUSH_ON_SAND)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.BIG_BUSH_ON_SAND);
				}
			});
	}

	public static void setupFallenTrees() {
		BiomeModifications.create(WWConstants.id("fallen_tree_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.FALLEN_TREE_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_MOSSY)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_MOSSY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_DARK_FOREST)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_DARK_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_BIRCH)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_BIRCH);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_OAK_SPARSE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_OAK_SPARSE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_BIRCH_AND_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_BIRCH_AND_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_AND_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_AND_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_OAK_AND_BIRCH_AND_SPRUCE_MOSSY)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_OAK_AND_BIRCH_AND_SPRUCE_MOSSY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_SPRUCE_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_SPRUCE_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_SPRUCE_CLEAN)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_SPRUCE_CLEAN);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_COMMON)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_CLEAN)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_CLEAN);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_SPRUCE_CLEAN_COMMON)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_SPRUCE_CLEAN_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_DARK_OAK)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.FALLEN_DARK_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_DARK_OAK_COMMON)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.FALLEN_DARK_OAK_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_PALE_OAK)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.FALLEN_PALE_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_JUNGLE)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.LARGE_FALLEN_JUNGLE_COMMON)) {
					addLocalModification(generationSettings, WWTreeVegetationPlacements.LARGE_FALLEN_JUNGLE_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_ACACIA_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_ACACIA_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_SWAMP)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_WILLOW_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_MANGROVE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_MANGROVE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_CYPRESS_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_CYPRESS_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_PALM)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_PALM);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_PALM_DESERT)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_PALM_DESERT);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_PALM_RARE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_PALM_RARE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_PALM_AND_JUNGLE_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_PALM_AND_JUNGLE_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_CHERRY)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_CHERRY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_MAPLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_MAPLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_POPLAR)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.FALLEN_POPLAR);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_CRIMSON_FUNGI)) {
					generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WWTreePlacements.FALLEN_CRIMSON.getKey());
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.FALLEN_WARPED_FUNGI)) {
					generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, WWTreePlacements.FALLEN_WARPED.getKey());
				}
			});
	}

	public static void setupSnappedTrees() {
		BiomeModifications.create(WWConstants.id("snapped_tree_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_OAK, WWTreeVegetationPlacements.SNAPPED_OAK_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_BIRCH)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_BIRCH, WWTreeVegetationPlacements.SNAPPED_BIRCH_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_SPRUCE, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_SPRUCE_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE)) {
					addVegetalDecoration(
						generationSettings,
						WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE,
						WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING
					);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_BIRCH_AND_JUNGLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_BIRCH_AND_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_SPRUCE, WWTreeVegetationPlacements.SNAPPED_SPRUCE_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_SPRUCE_ON_SNOW)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_SPRUCE_ON_SNOW, WWTreeVegetationPlacements.SNAPPED_SPRUCE_ON_SNOW_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE_COMMON)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_COMMON, WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_LARGE_SPRUCE_ON_SNOW)) {
					addVegetalDecoration(
						generationSettings,
						WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_ON_SNOW,
						WWTreeVegetationPlacements.SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING
					);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_DARK_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_DARK_OAK, WWTreeVegetationPlacements.SNAPPED_DARK_OAK_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_PALE_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_PALE_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_JUNGLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_LARGE_JUNGLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_LARGE_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_ACACIA)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_ACACIA);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_ACACIA_AND_OAK)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_ACACIA_AND_OAK);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_CYPRESS)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_CYPRESS);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_CHERRY)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_CHERRY);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_MAPLE)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_MAPLE, WWTreeVegetationPlacements.SNAPPED_MAPLE_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_POPLAR)) {
					addVegetalDecoration(generationSettings, WWTreeVegetationPlacements.SNAPPED_POPLAR, WWTreeVegetationPlacements.SNAPPED_POPLAR_CLEARING);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_CRIMSON_FUNGI)) {
					addVegetalDecoration(generationSettings, WWTreePlacements.SNAPPED_CRIMSON);
				}

				if (biomeSelectionContext.hasTag(WWTreeFeatureBiomeTags.SNAPPED_WARPED_FUNGI)) {
					addVegetalDecoration(generationSettings, WWTreePlacements.SNAPPED_WARPED);
				}
			});
	}

	@SafeVarargs
	private static void removeFeatures(BiomeModificationContext.GenerationSettingsContext generationSettings, ResourceKey<PlacedFeature>... placements) {
		for (ResourceKey<PlacedFeature> placement : placements) generationSettings.removeFeature(placement);
	}

	@SafeVarargs
	private static void addLocalModification(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, placement.getKey());
	}

	@SafeVarargs
	private static void addVegetalDecoration(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placement.getKey());
	}

	private WWTreeGeneration() {}
}
