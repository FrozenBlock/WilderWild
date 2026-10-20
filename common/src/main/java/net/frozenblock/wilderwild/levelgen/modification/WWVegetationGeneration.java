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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWWorldgenConfig;
import net.frozenblock.wilderwild.data.worldgen.feature.placed.WWPlacedFeatures;
import net.frozenblock.wilderwild.data.worldgen.placement.WWAquaticPlacements;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWVegetationGeneration {

	public static void generateFlower() {
		// TODO: ???
		BiomeModifications.create(WWConstants.id("flower_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.FLOWER_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FLOWERING_WATER_LILY)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlacements.PATCH_FLOWERING_WATERLILY.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BERRY_PATCH)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_BERRY_FOREST.getKey());
				}

			});
	}

	public static void generateShrub() {
		BiomeModifications.create(WWConstants.id("shrub_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.SHRUB_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_JUNGLE_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_JUNGLE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SPARSE_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_SPARSE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BADLANDS_SAND_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_BADLANDS_SAND.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BADLANDS_TERRACOTTA_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_BADLANDS_TERRACOTTA.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_WOODED_BADLANDS_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_WOODED_BADLANDS_DIRT.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BADLANDS_RARE_SAND_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_BADLANDS_SAND_RARE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DESERT_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_DESERT.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_OASIS_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_OASIS.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_ARID_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_BUSH_ARID.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FLOWER_FIELD_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB_FLOWER_FIELD.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RAINFOREST_SHRUB)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SHRUB.getKey());
				}
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.SHRUB_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_BUSH)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_BUSH);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FROZEN_BUSH.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("firefly_bush_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.FIREFLY_BUSH_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FIREFLY_BUSH)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP);
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP);

					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FIREFLY_BUSH_NEAR_WATER.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FIREFLY_BUSH_SWAMP)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP);
					generationSettings.removeFeature(VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP);

					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP.getKey());
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FIREFLY_BUSH_SWAMP.getKey());
				}
			});
	}

	public static void generateCacti() {
		BiomeModifications.create(WWConstants.id("cactus_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.CACTUS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TALL_CACTUS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_CACTUS_TALL.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PRICKLY_PEAR)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_PRICKLY_PEAR.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RARE_PRICKLY_PEAR)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_PRICKLY_PEAR_RARE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TALL_BADLANDS_CACTUS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_CACTUS_TALL_BADLANDS.getKey());
				}
			});
	}

	public static void generateGrass() {
		BiomeModifications.create(WWConstants.id("tumbleweed_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.TUMBLEWEED_GENERATION.get()) return;
				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TUMBLEWEED_PLANT)) {
					context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_TUMBLEWEED.getKey());
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

		BiomeModifications.create(WWConstants.id("replace_forest_grass_light")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.FOREST_GRASS_LIGHT),
			context -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FERN_AND_GRASS_LIGHT.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS_LIGHT.getKey());
			});

		BiomeModifications.create(WWConstants.id("replace_forest_grass_birch")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.FOREST_GRASS_BIRCH),
			context -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
				generationSettings.removeFeature(VegetationPlacements.PATCH_GRASS_FOREST);
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_BIRCH.getKey());
				generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.TALL_GRASS.getKey());
			});

		BiomeModifications.create(WWConstants.id("grass_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CLOVERS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_CLOVER.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CLOVERS_SPARSE)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_CLOVER_SPARSE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_NEW_RARE_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_RARE_GRASS.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_LARGE_FERN_AND_GRASS.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_LARGE_FERN_AND_GRASS_RARE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_TALL_GRASS_FLOWER_FIELD.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SWAMP_FERN)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FERN_SWAMP.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DENSE_FERN)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FERN_DENSE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DENSE_TALL_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_TALL_GRASS_DENSE.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SWAMP_TALL_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_TALL_GRASS_SWAMP.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_WATER_GRASS)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_TALL_GRASS_AND_GRASS_WATER.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MYCELIUM_GROWTH)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_MYCELIUM_GROWTH.getKey());
				}
		}).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_FOREST_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_FOREST);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_FROZEN_FOREST.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_NORMAL_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_FROZEN_NORMAL.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_PLAIN_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_BADLANDS);
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_PLAIN);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_FROZEN_PLAIN.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_TAIGA_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_FROZEN_TAIGA.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_TAIGA_2_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_TAIGA_2);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_GRASS_FROZEN_TAIGA_2.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_PLAIN_TALL_GRASS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_TALL_GRASS_2);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FROZEN_TALL_GRASS_2.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_FROZEN_LARGE_FERNS)) {
					generationSettings.removeFeature(GenerationStep.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_LARGE_FERN);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_FROZEN_LARGE_FERN.getKey());
				}
			});

		BiomeModifications.create(WWConstants.id("dry_grass_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.DRY_GRASS_GENERATION.get()) return;
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DRY_GRASS_BADLANDS)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_DRY_GRASS_BADLANDS);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_DRY_GRASS_BADLANDS.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DRY_GRASS_DESERT)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_DRY_GRASS_DESERT);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_DRY_GRASS_DESERT.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DRY_GRASS_BEACH)) {
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_DRY_GRASS_BEACH.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DRY_GRASS_BETA_BEACH)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_DRY_GRASS_DESERT);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_DRY_GRASS_BETA_BEACH.getKey());
				}
		});

		BiomeModifications.create(WWConstants.id("leaf_litter_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (WWWorldgenConfig.OAK_LITTER_GENERATION.get()) {
					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_LEAF_LITTER)) {
						generationSettings.removeFeature(VegetationPlacements.PATCH_LEAF_LITTER);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_LEAF_LITTER.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_UNCOMMON_LEAF_LITTER)) {
						generationSettings.removeFeature(VegetationPlacements.PATCH_LEAF_LITTER);
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_UNCOMMON_LEAF_LITTER.getKey());
					}
				}

				if (WWWorldgenConfig.DARK_OAK_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWBiomeTags.HAS_DARK_OAK_LEAF_LITTER)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_LEAF_LITTER);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_DARK_OAK_LEAF_LITTER.getKey());
				}

				if (WWWorldgenConfig.PALE_OAK_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWBiomeTags.HAS_PALE_OAK_LEAF_LITTER)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_LEAF_LITTER);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_PALE_OAK_LEAF_LITTER.getKey());
				}

				if (WWWorldgenConfig.SPRUCE_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWBiomeTags.HAS_SPRUCE_LEAF_LITTER)) {
					generationSettings.removeFeature(VegetationPlacements.PATCH_LEAF_LITTER);
					generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_SPRUCE_LEAF_LITTER.getKey());
				}
		});
	}

	public static void generateMushroom() {
		BiomeModifications.create(WWConstants.id("mushroom_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (WWWorldgenConfig.MUSHROOM_GENERATION.get()) {
					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HUGE_RED_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.HUGE_RED_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HUGE_BROWN_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.HUGE_BROWN_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HUGE_MUSHROOMS)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.HUGE_MUSHROOMS.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HUGE_MUSHROOMS_RARE)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.HUGE_MUSHROOMS_RARE.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_BROWN_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.BROWN_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_RED_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.RED_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SWAMP_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.HUGE_MUSHROOMS_SWAMP.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BIG_MUSHROOM_PATCH)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.MUSHROOMS_DARK_FOREST.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RAINFOREST_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.RAINFOREST_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MIXED_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.MIXED_MUSHROOM.getKey());
					}
				}

				if (WWWorldgenConfig.PALE_MUSHROOM_GENERATION.get()) {
					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HUGE_PALE_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.HUGE_PALE_MUSHROOM.getKey());
					}

					if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PALE_MUSHROOM)) {
						generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWPlacedFeatures.PALE_MUSHROOM.getKey());
					}
				}
			});
	}

	public static void generatePumpkin() {
		BiomeModifications.create(WWConstants.id("pumpkin_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.PUMPKIN_GENERATION.get()) return;

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COMMON_PUMPKIN)) {
					context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_PUMPKIN_COMMON.getKey());
				}

				if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_UNCOMMON_PUMPKIN)) {
					context.getGenerationSettings().addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.PATCH_PUMPKIN_UNCOMMON.getKey());
				}
			});
	}

}
