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
import net.frozenblock.wilderwild.data.worldgen.placement.WWAquaticPlacements;
import net.frozenblock.wilderwild.tag.WWAquaticFeatureBiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWAquaticGeneration {

	public static void setup() {
		setupSurfaceDecorations();
		setupVegetation();
		setupMisc();
	}

	private static void setupSurfaceDecorations() {
		BiomeModifications.create(WWConstants.id("aquatic_surface_decoration_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				// FLOWERING WATER LILY
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.FLOWERING_WATER_LILY)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_FLOWERING_WATER_LILY);
				}

				// CATTAIL
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.CATTAIL)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_CATTAIL);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.CATTAIL_COMMON)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_CATTAIL_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.CATTAIL_UNCOMMON)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_CATTAIL_UNCOMMON);
				}

				// ALGAE
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.ALGAE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_ALGAE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.ALGAE_SMALL)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_ALGAE_SMALL);
				}

				// PLANKTON
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.PLANKTON)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_PLANKTON);
				}

				// POOL
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.WATER_POOLS)) {
					addLakes(generationSettings, WWAquaticPlacements.RIVER_POOL);
					addLakes(generationSettings, WWAquaticPlacements.SMALL_RIVER_POOL);
				}
			});
	}

	private static void setupVegetation() {
		BiomeModifications.create(WWConstants.id("aquatic_vegetation_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				// SEAGRASS
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEAGRASS_MEADOW)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.SEAGRASS_MEADOW);
				}

				// BARNACLES
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.BARNACLES)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_BARNACLES);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.BARNACLES_COMMON)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_BARNACLES_COMMON);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.BARNACLES_SPARSE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_BARNACLES_SPARSE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.BARNACLES_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_BARNACLES_RARE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.BARNACLES_STRUCTURE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_BARNACLES_STRUCTURE);
				}

				// SEA ANEMONE
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_ANEMONE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_ANEMONE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_ANEMONE_SPARSE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_ANEMONE_SPARSE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_ANEMONE_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_ANEMONE_RARE);
				}

				// SEA WHIP
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_WHIP)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_WHIP);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_WHIP_SPARSE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_WHIP_SPARSE);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SEA_WHIP_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_SEA_WHIP_RARE);
				}

				// SPONGE BUD
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SPONGE_BUD)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.SPONGE_BUDS);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.SPONGE_BUD_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.SPONGE_BUDS_RARE);
				}

				// TUBE WORMS
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.TUBE_WORMS)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_TUBE_WORMS);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.TUBE_WORMS_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.PATCH_TUBE_WORMS_RARE);
				}

				// MOSS
				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.AUBURN_CREEPING_MOSS_UNDERWATER)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.AUBURN_CREEPING_MOSS_UNDERWATER);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.AUBURN_MOSS_UNDERWATER)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.AUBURN_MOSS_UNDERWATER);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.AUBURN_MOSS_UNDERWATER_RARE)) {
					addVegetalDecoration(generationSettings, WWAquaticPlacements.AUBURN_MOSS_UNDERWATER_RARE);
				}
			});
	}

	private static void setupMisc() {
		BiomeModifications.create(WWConstants.id("aquatic_misc_generation")).add(
			ModificationPhase.ADDITIONS,
			BiomeSelectors.all(),
			(biomeSelectionContext, context) -> {
				final BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.HYDROTHERMAL_VENT)) {
					addFluidSprings(generationSettings, WWAquaticPlacements.HYDROTHERMAL_VENT);
				}

				if (biomeSelectionContext.hasTag(WWAquaticFeatureBiomeTags.HYDROTHERMAL_VENT_RARE)) {
					addFluidSprings(generationSettings, WWAquaticPlacements.HYDROTHERMAL_VENT_RARE);
				}
			});
	}

	@SafeVarargs
	private static void addLakes(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.LAKES, placement.getKey());
	}

	@SafeVarargs
	private static void addFluidSprings(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.FLUID_SPRINGS, placement.getKey());
	}

	@SafeVarargs
	private static void addVegetalDecoration(BiomeModificationContext.GenerationSettingsContext generationSettings, FrozenLibPlacedFeature... placements) {
		for (FrozenLibPlacedFeature placement : placements) generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, placement.getKey());
	}


	private WWAquaticGeneration() {}
}
