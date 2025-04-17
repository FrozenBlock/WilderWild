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
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.frozenblock.wilderwild.worldgen.features.placed.WWAquaticPlaced;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWAquaticGeneration {
	private WWAquaticGeneration() {
		throw new UnsupportedOperationException("WWAquaticGeneration contains only static declarations.");
	}

	public static void generateAquaticFeatures() {
		generateAlgae();
		generatePlankton();
		generateSeaWhips();
		generateSeagrass();
		generateSpongeBuds();
		generateBarnacles();
		generateCattails();
		generateSeaAnemones();
		generateTubeWorms();
		generateHydrothermalVent();
		generateMoss();
	}

	private static void generateAlgae() {
		BiomeModifications.create(WWConstants.id("algae_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.algae) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_ALGAE_SMALL)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_ALGAE_SMALL.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_ALGAE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_ALGAE.getKey());
						}
					}
				});
	}

	private static void generatePlankton() {
		BiomeModifications.create(WWConstants.id("plankton_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.plankton) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PLANKTON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_PLANKTON.getKey());
						}
					}
				});
	}

	private static void generateSeagrass() {
		BiomeModifications.create(WWConstants.id("seagrass_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.seagrass) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MEADOW_SEAGRASS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.SEAGRASS_MEADOW.getKey());
						}
					}
				});
	}

	private static void generateSpongeBuds() {
		BiomeModifications.create(WWConstants.id("sponge_bud_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.spongeBud) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SPONGE_BUD)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.SPONGE_BUDS.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SPONGE_BUD_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.SPONGE_BUDS_RARE.getKey());
						}
					}
				});
	}

	private static void generateBarnacles() {
		BiomeModifications.create(WWConstants.id("barnacle_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.barnacle) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BARNACLES_COMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.BARNACLES_COMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BARNACLES_STRUCTURE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.BARNACLES_STRUCTURE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BARNACLES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.BARNACLES.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BARNACLES_SPARSE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.BARNACLES_SPARSE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BARNACLES_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.BARNACLES_RARE.getKey());
						}
					}
				});
	}

	private static void generateCattails() {
		BiomeModifications.create(WWConstants.id("cattail_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.cattail) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CATTAIL)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_CATTAIL.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CATTAIL_UNCOMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_CATTAIL_UNCOMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CATTAIL_COMMON)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_CATTAIL_COMMON.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CATTAIL_MUD)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_CATTAIL_MUD.getKey());
						}
					}
				});
	}

	private static void generateSeaAnemones() {
		BiomeModifications.create(WWConstants.id("sea_anemone_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.seaAnemone) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_ANEMONE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_ANEMONE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_ANEMONE_SPARSE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_ANEMONE_SPARSE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_ANEMONE_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_ANEMONE_RARE.getKey());
						}
					}
				});
	}

	private static void generateSeaWhips() {
		BiomeModifications.create(WWConstants.id("sea_whip_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.seaWhip) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_WHIP)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_WHIP.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_WHIP_SPARSE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_WHIP_SPARSE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SEA_WHIP_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_SEA_WHIP_RARE.getKey());
						}
					}
				});
	}

	private static void generateTubeWorms() {
		BiomeModifications.create(WWConstants.id("tube_worm_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.tubeWorm) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TUBE_WORMS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_TUBE_WORMS.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TUBE_WORMS_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.PATCH_TUBE_WORMS_RARE.getKey());
						}
					}
				});
	}

	private static void generateHydrothermalVent() {
		BiomeModifications.create(WWConstants.id("hydrothermal_vent_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.hydrothermalVent) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						boolean useTubeWorms = WWWorldgenConfig.get().aquaticGeneration.tubeWorm;

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HYDROTHERMAL_VENT)) {
							generationSettings.addFeature(
								GenerationStep.Decoration.FLUID_SPRINGS,
								useTubeWorms ? WWAquaticPlaced.HYDROTHERMAL_VENT_TUBE_WORMS.getKey() : WWAquaticPlaced.HYDROTHERMAL_VENT.getKey()
							);
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_HYDROTHERMAL_VENT_RARE)) {
							generationSettings.addFeature(
								GenerationStep.Decoration.FLUID_SPRINGS,
								useTubeWorms ? WWAquaticPlaced.HYDROTHERMAL_VENT_TUBE_WORMS_RARE.getKey() : WWAquaticPlaced.HYDROTHERMAL_VENT_RARE.getKey()
							);
						}
					}
				});
	}

	private static void generateMoss() {
		BiomeModifications.create(WWConstants.id("ocean_moss_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

					if (WWWorldgenConfig.get().aquaticGeneration.oceanMossGeneration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_OCEAN_MOSS)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWAquaticPlaced.OCEAN_MOSS.getKey());
						}
					}

					if (WWWorldgenConfig.get().aquaticGeneration.oceanAuburnMossGeneration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CREEPING_AUBURN_MOSS_UNDERWATER)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.AUBURN_CREEPING_MOSS_UNDERWATER.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_AUBURN_MOSS_UNDERWATER)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.AUBURN_MOSS_UNDERWATER.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_AUBURN_MOSS_UNDERWATER_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWAquaticPlaced.AUBURN_MOSS_UNDERWATER_RARE.getKey());
						}
					}
				});
	}

}
