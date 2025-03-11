/*
 * Copyright 2023-2025 FrozenBlock
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

	public static void generateAlgae() {
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

	public static void generateBarnacles() {
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

	public static void generateCattails() {
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

	public static void generateSeaAnemone() {
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

}
