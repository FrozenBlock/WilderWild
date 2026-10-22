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
import net.frozenblock.wilderwild.data.worldgen.placement.WWVegetationPlacements;
import net.frozenblock.wilderwild.tag.WWVegetationFeatureBiomeTags;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class WWVegetationGeneration {

	public static void setup() {
		setupLeafLitter();
		setupGrass();
		setupDryGrass();
		setupFrozenGrass();
		setupFireflyBush();
		setupShrub();
		setupMushroom();
		setupCactus();
		setupTumbleweed();
		setupPollen();
		setupMoss();
		setupAuburnMoss();
		setupBerry();
		setupPumpkin();
		setupMisc();
	}

	private static void setupLeafLitter() {
		BiomeModifications.create(WWConstants.id("leaf_litter_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (WWWorldgenConfig.OAK_LITTER_GENERATION.get()) {
					if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.LEAF_LITTER)) {
						removeFeatures(generationSettings, VegetationPlacements.PATCH_LEAF_LITTER);
						addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_LEAF_LITTER);
					}

					if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.LEAF_LITTER_UNCOMMON)) {
						removeFeatures(generationSettings, VegetationPlacements.PATCH_LEAF_LITTER);
						addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_LEAF_LITTER_UNCOMMON);
					}
				}

				if (WWWorldgenConfig.DARK_OAK_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.DARK_OAK_LEAF_LITTER)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_LEAF_LITTER);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_DARK_OAK_LEAF_LITTER);
				}

				if (WWWorldgenConfig.PALE_OAK_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PALE_OAK_LEAF_LITTER)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_LEAF_LITTER);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_PALE_OAK_LEAF_LITTER);
				}

				if (WWWorldgenConfig.SPRUCE_LITTER_GENERATION.get() && biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SPRUCE_LEAF_LITTER)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_LEAF_LITTER);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SPRUCE_LEAF_LITTER);
				}
			});
	}

	private static void setupGrass() {
		BiomeModifications.create(WWConstants.id("grass_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.GRASS_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_FOREST);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FERN_AND_GRASS, WWVegetationPlacements.TALL_GRASS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.GRASS_FOREST_LIGHT)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_FOREST);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FERN_AND_GRASS_LIGHT, WWVegetationPlacements.TALL_GRASS_LIGHT);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.GRASS_BIRCH_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_FOREST);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_GRASS_BIRCH, WWVegetationPlacements.TALL_GRASS);
				}
			}
		).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.GRASS_RARE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_GRASS_RARE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.TALL_GRASS_DENSE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_TALL_GRASS_DENSE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.TALL_GRASS_FLOWER_FIELD)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_TALL_GRASS_FLOWER_FIELD);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.TALL_GRASS_SWAMP)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_TALL_GRASS_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.GRASS_WATER)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_GRASS_WATER);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.LARGE_FERN_AND_GRASS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_LARGE_FERN_AND_GRASS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.LARGE_FERN_AND_GRASS_RARE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_LARGE_FERN_AND_GRASS_RARE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FERN_DENSE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FERN_DENSE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FERN_SWAMP)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FERN_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.CLOVERS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_CLOVER);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.CLOVERS_SPARSE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_CLOVER_SPARSE);
				}
			}
		);
	}

	private static void setupDryGrass() {
		BiomeModifications.create(WWConstants.id("dry_grass_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.DRY_GRASS_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.DRY_GRASS_DESERT)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_DRY_GRASS_DESERT);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_DRY_GRASS_DESERT);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.DRY_GRASS_BADLANDS)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_DRY_GRASS_BADLANDS);
					addVegetalDecoration(generationSettings,  WWVegetationPlacements.PATCH_DRY_GRASS_BADLANDS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.DRY_GRASS_BEACH)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_DRY_GRASS_BEACH);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.DRY_GRASS_BETA_BEACH)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_DRY_GRASS_DESERT);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_DRY_GRASS_BETA_BEACH);
				}
			});
	}

	private static void setupFrozenGrass() {
		BiomeModifications.create(WWConstants.id("frozen_grass_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.GRASS_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_GRASS_PLAIN)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_BADLANDS, VegetationPlacements.PATCH_GRASS_PLAIN);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_GRASS_PLAIN);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_GRASS_FOREST)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_FOREST);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_GRASS_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_GRASS_NORMAL)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_NORMAL);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_GRASS_NORMAL);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_GRASS_TAIGA)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_TAIGA);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_GRASS_TAIGA);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_GRASS_TAIGA_2)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_GRASS_TAIGA_2);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_GRASS_TAIGA_2);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_TALL_GRASS_PLAIN)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_TALL_GRASS_2);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_TALL_GRASS_2);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_BUSH)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_BUSH);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_BUSH);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FROZEN_LARGE_FERN)) {
					removeFeatures(generationSettings, VegetationPlacements.PATCH_LARGE_FERN);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FROZEN_LARGE_FERN);
				}
			});
	}

	private static void setupFireflyBush() {
		BiomeModifications.create(WWConstants.id("firefly_bush_generation")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				if (!WWWorldgenConfig.FIREFLY_BUSH_GENERATION.get()) return;

				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FIREFLY_BUSH)) {
					removeFeatures(
						generationSettings,
						VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER,
						VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
						VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP
					);
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.FIREFLY_BUSH_SWAMP)) {
					removeFeatures(
						generationSettings,
						VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER,
						VegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
						VegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP
					);
					addVegetalDecoration(
						generationSettings,
						WWVegetationPlacements.PATCH_FIREFLY_BUSH_NEAR_WATER_SWAMP,
						WWVegetationPlacements.PATCH_FIREFLY_BUSH_SWAMP
					);
				}
			});
	}

	private static void setupShrub() {
		BiomeModifications.create(WWConstants.id("shrub_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_SPARSE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_SPARSE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_FLOWER_FIELD)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_FLOWER_FIELD);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_JUNGLE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_JUNGLE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_OASIS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_OASIS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_DESERT)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_DESERT);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_ARID)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_ARID);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_SAND)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_SAND);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_SAND_RARE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_SAND_RARE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_BADLANDS_TERRACOTTA)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_SHRUB_BADLANDS_TERRACOTTA);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.SHRUB_WOODED_BADLANDS)) {
					addVegetalDecoration(
						generationSettings,
						WWVegetationPlacements.PATCH_SHRUB_WOODED_BADLANDS_TERRACOTTA,
						WWVegetationPlacements.PATCH_SHRUB_WOODED_BADLANDS_DIRT
					);
				}
			});
	}

	private static void setupMushroom() {
		BiomeModifications.create(WWConstants.id("mushroom_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.BROWN_MUSHROOM_COMMON)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.BROWN_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.RED_MUSHROOM_COMMON)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.RED_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PALE_MUSHROOM)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.PALE_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_PALE_MUSHROOM)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.HUGE_PALE_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PATCH_MUSHROOM_DARK_FOREST)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.PATCH_MUSHROOM_DARK_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_BROWN_MUSHROOM)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.HUGE_BROWN_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_RED_MUSHROOM)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.HUGE_RED_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_MUSHROOM)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.HUGE_MUSHROOM);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_SWAMP)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.HUGE_MUSHROOM_SWAMP);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_RARE)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.HUGE_MUSHROOM_RARE);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_MIXED_FOREST)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.HUGE_MUSHROOM_MIXED_FOREST);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HUGE_MUSHROOM_RAINFOREST)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.HUGE_MUSHROOM_RAINFOREST);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.MYCELIUM_GROWTH)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_MYCELIUM_GROWTH);
				}
			});
	}

	private static void setupCactus() {
		BiomeModifications.create(WWConstants.id("cactus_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.CACTUS_TALL)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_CACTUS_TALL);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.CACTUS_TALL_BADLANDS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_CACTUS_TALL_BADLANDS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PRICKLY_PEAR)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_PRICKLY_PEAR);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PRICKLY_PEAR_RARE)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_PRICKLY_PEAR_RARE);
				}
			});
	}

	private static void setupTumbleweed() {
		BiomeModifications.create(WWConstants.id("tumbleweed_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.TUMBLEWEED)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_TUMBLEWEED);
				}
			});
	}

	private static void setupPollen() {
		BiomeModifications.create(WWConstants.id("pollen_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.POLLEN)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.POLLEN);
				}
			});
	}

	private static void setupMoss() {
		BiomeModifications.create(WWConstants.id("moss_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PATH_MOSS)) {
					addTopLayerModification(generationSettings, WWVegetationPlacements.PATH_MOSS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.MOSS_CARPET)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.MOSS_CARPET);
				}
			});
	}

	private static void setupAuburnMoss() {
		BiomeModifications.create(WWConstants.id("auburn_moss_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.AUBURN_MOSS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.AUBURN_MOSS);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.AUBURN_CREEPING_MOSS)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.AUBURN_CREEPING_MOSS);
				}
			});
	}

	private static void setupBerry() {
		BiomeModifications.create(WWConstants.id("berry_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.BERRY_FOREST)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_BERRY_FOREST);
				}
			});
	}

	private static void setupPumpkin() {
		BiomeModifications.create(WWConstants.id("pumpkin_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PUMPKIN_COMMON)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_PUMPKIN_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.PUMPKIN_UNCOMMON)) {
					addVegetalDecoration(generationSettings, WWVegetationPlacements.PATCH_PUMPKIN_UNCOMMON);
				}
			});
	}

	private static void setupMisc() {
		BiomeModifications.create(WWConstants.id("misc_vegetation_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWVegetationFeatureBiomeTags.HAS_TERMITE_MOUND)) {
					generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWVegetationPlacements.TERMITE_MOUND.getKey());
				}
			});
	}

	@SafeVarargs
	private static void removeFeatures(BiomeModificationContext.GenerationSettingsContext generationSettings, ResourceKey<PlacedFeature>... placements) {
		for (ResourceKey<PlacedFeature> placement : placements) generationSettings.removeFeature(placement);
	}

	@SafeVarargs
	private static void addVegetalDecoration(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placement.getKey());
	}

	@SafeVarargs
	private static void addTopLayerModification(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, placement.getKey());
	}

	private WWVegetationGeneration() {}
}
