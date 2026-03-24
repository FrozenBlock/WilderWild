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
import net.frozenblock.wilderwild.worldgen.impl.conditionsource.SnowUnderMountainConditionSource;
import net.frozenblock.wilderwild.worldgen.impl.treedecorators.WWTreeDecorators;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerType;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import java.util.Map;

public final class WWWorldgen {

	private WWWorldgen() {
		throw new UnsupportedOperationException("WWWorldgen contains only static declarations.");
	}

	public static void generateWildWorldGen() {
		configureBuiltInBiomes();
		replaceFeatures();
		WWVegetationGeneration.generateFlower();
		WWVegetationGeneration.generateShrub();
		WWVegetationGeneration.generateCacti();
		WWAquaticGeneration.generateAquaticFeatures();
		WWVegetationGeneration.generateGrass();
		WWMiscGeneration.generateMisc();

		WWTreeDecorators.init();
		WWTreeGeneration.generateTrees();
		WWVegetationGeneration.generateMushroom();
		WWVegetationGeneration.generatePumpkin();

		WWBiomeSettings.init();

		generatePollen();

		final Map<ResourceKey<Biome>, ResourceKey<VillagerType>> villagerTypeMap = VillagerType.BY_BIOME;
		villagerTypeMap.put(WWBiomes.CYPRESS_WETLANDS, VillagerType.SWAMP);
		villagerTypeMap.put(WWBiomes.OASIS, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.FROZEN_CAVES, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.ARID_FOREST, VillagerType.DESERT);
		villagerTypeMap.put(WWBiomes.ARID_SAVANNA, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.PARCHED_FOREST, VillagerType.SAVANNA);
		villagerTypeMap.put(WWBiomes.BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.SPARSE_BIRCH_JUNGLE, VillagerType.JUNGLE);
		villagerTypeMap.put(WWBiomes.BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.TEMPERATE_RAINFOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DARK_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.DYING_MIXED_FOREST, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_MIXED_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.SNOWY_DYING_FOREST, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.OLD_GROWTH_BIRCH_TAIGA, VillagerType.TAIGA);
		villagerTypeMap.put(WWBiomes.SNOWY_OLD_GROWTH_PINE_TAIGA, VillagerType.SNOW);
		villagerTypeMap.put(WWBiomes.FLOWER_FIELD, VillagerType.PLAINS);

		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WWConstants.id("beta_beach_condition_source"), BetaBeachConditionSource.CODEC.codec());
		Registry.register(BuiltInRegistries.MATERIAL_CONDITION, WWConstants.id("snow_under_mountain_condition_source"), SnowUnderMountainConditionSource.CODEC.codec());
	}

	private static void configureBuiltInBiomes() {
		BiomeModifications.create(WWConstants.id("remove_fallen_trees")).add(
			ModificationPhase.REMOVALS,
			BiomeSelectors.includeByKey(WWBiomes.CYPRESS_WETLANDS),
			context -> {
				if (WWWorldgenConfig.FALLEN_TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(WWPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());
		}).add(
			ModificationPhase.REMOVALS,
			BiomeSelectors.includeByKey(WWBiomes.MIXED_FOREST),
			context -> {
				if (WWWorldgenConfig.FALLEN_TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(WWPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());
			});

		BiomeModifications.create(WWConstants.id("flowers_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.RAINFOREST),
			context -> {
				if (!WWWorldgenConfig.FLOWER_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(WWPlacedFeatures.FLOWER_RAINFOREST_VANILLA.getKey());
				generationSettings.removeFeature(WWPlacedFeatures.TALL_FLOWER_RAINFOREST_VANILLA.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FLOWER_RAINFOREST.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_FLOWER_RAINFOREST.getKey());
			});

		BiomeModifications.create(WWConstants.id("flowers_temperate_rainforest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.TEMPERATE_RAINFOREST),
			context -> {
				if (!WWWorldgenConfig.FLOWER_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(WWPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
				generationSettings.removeFeature(WWPlacedFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST_VANILLA.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.FLOWER_TEMPERATE_RAINFOREST.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_FLOWER_TEMPERATE_RAINFOREST.getKey());
			});
	}

	private static void replaceFeatures() {
		BiomeModifications.create(WWConstants.id("add_new_snow")).add(
			ModificationPhase.POST_PROCESSING,
			BiomeSelectors.all(),
			context -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				if (!generationSettings.removeFeature(MiscOverworldPlacements.FREEZE_TOP_LAYER)) return;

				generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, MiscOverworldPlacements.FREEZE_TOP_LAYER);
				if (WWWorldgenConfig.SNOW_BELOW_TREES.get()) {
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_BLANKET.getKey());
				}
				if (WWWorldgenConfig.SNOW_TRANSITION_GENERATION.get()) {
					generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SNOW_AND_ICE_TRANSITION.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_forest_grass")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.FOREST_GRASS),
			context -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FERN_AND_GRASS.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_plains_grass")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.PLAINS_GRASS),
			context -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_PLAIN);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_PLAINS.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS_PLAINS.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_cherry_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.CHERRY_TREES),
			(context) -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_CHERRY);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.CHERRY_TREES.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_forest_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_AND_OAK_ORIGINAL.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_AND_OAK.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_birch_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.BIRCH_FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.OLD_GROWTH_BIRCH_FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.BIRCH_TALL);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_BIRCH_TALL.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.FLOWER_FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_BIRCH_AND_OAK_LEAF_LITTER);
				generationSettings.removeFeature(VegetationPlacements.TREES_FLOWER_FOREST);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_FLOWER_FOREST.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_plains_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.NON_FROZEN_PLAINS),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_PLAINS);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_PLAINS.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_badlands_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_BADLANDS);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.WOODED_BADLANDS_TREES.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_swamp_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SWAMP_TREES),
			context -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				if (WWWorldgenConfig.WILLOW_TREE_GENERATION.get()) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SWAMP);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_SURFACE_WILLOW.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_WATER_SHALLOW.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP_WATER.getKey());
				} else if (WWWorldgenConfig.TREE_GENERATION.get()) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SWAMP);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SWAMP.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("replace_taiga_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SHORT_TAIGA),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_TAIGA);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SPRUCE_PLACED.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.SHORT_TAIGA_SNOWY),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_TAIGA);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SPRUCE_PLACED_NO_LITTER.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.TALL_PINE_TAIGA),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_PINE_TAIGA);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_OLD_GROWTH_PINE_TAIGA.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.TALL_SPRUCE_TAIGA),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_OLD_GROWTH_SPRUCE_TAIGA);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_OLD_GROWTH_SPRUCE_TAIGA1.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_grove_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.GROVE),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_GROVE);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_GROVE.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_savanna_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.NORMAL_SAVANNA),
			context -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				if (WWWorldgenConfig.TREE_GENERATION.get()) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SAVANNA);
					if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES_BAOBAB.getKey());
					} else {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES.getKey());
					}
				} else if (WWWorldgenConfig.BAOBAB_TREE_GENERATION.get()) {
					generationSettings.removeFeature(VegetationPlacements.TREES_SAVANNA);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.SAVANNA_TREES_BAOBAB_VANILLA.getKey());
				}
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_SAVANNA),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_SAVANNA);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.WINDSWEPT_SAVANNA_TREES.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_bamboo_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.BAMBOO_JUNGLE_TREES),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.BAMBOO_VEGETATION);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.BAMBOO_VEGETATION.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_sparse_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.SPARSE_JUNGLE_TREES),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_SPARSE_JUNGLE);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SPARSE_JUNGLE.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_jungle_trees")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.tag(WWBiomeTags.JUNGLE_TREES),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_JUNGLE);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_JUNGLE.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_mangrove_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.MANGROVE_TREES),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_MANGROVE);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_MANGROVE.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_snowy_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_TREES_SNOWY),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_SNOWY);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_SNOWY.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_windswept_hills_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_HILLS),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_HILLS);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_WINDSWEPT_HILLS.getKey());
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.WINDSWEPT_FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_WINDSWEPT_FOREST);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_WINDSWEPT_FOREST.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_dark_forest_vegetation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.DARK_FOREST),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.DARK_FOREST_VEGETATION);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.DARK_FOREST_VEGETATION.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_pale_garden_vegetation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.PALE_GARDEN),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.PALE_GARDEN_VEGETATION);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_PALE_GARDEN.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_meadow_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.MEADOW),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_MEADOW);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TREES_MEADOW.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_water_trees")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_WATER_SHRUBS),
			context -> {
				if (!WWWorldgenConfig.TREE_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.TREES_WATER);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.BIG_BUSHES_WATER.getKey());
			});

	}

	private static void generatePollen() {
		BiomeModifications.create(WWConstants.id("pollen_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.POLLEN_GENERATION.get() || !biomeSelectionContext.hasTag(WWBiomeTags.HAS_POLLEN)) return;
				context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.POLLEN.getKey());
			});
	}

}
