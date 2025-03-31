/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.frozenblock.wilderwild.worldgen.features.placed.WWCavePlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWMiscPlaced;
import net.frozenblock.wilderwild.worldgen.features.placed.WWPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WWMiscGeneration {

	public static void generateMisc() {
		BiomeModifications.create(WWConstants.id("nether_geysers"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().netherGeyserGen) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_NETHER_GEYSER)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.NETHER_GEYSER.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_NETHER_LAVA_GEYSER)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WWCavePlaced.NETHER_LAVA_GEYSER.getKey());
						}
					}
				});

		BiomeModifications.create(WWConstants.id("termite_generation"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().termiteGen) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TERMITE_MOUND)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWPlacedFeatures.TERMITE_MOUND.getKey());
						}
					}
				});

		BiomeModifications.create(WWConstants.id("surface_decoration"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					WWWorldgenConfig.SurfaceDecoration surfaceDecoration = WWWorldgenConfig.get().surfaceDecoration;

					if (surfaceDecoration.coarseDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RARE_COARSE)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_PATH_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.MIXED_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_PATH_5.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.ARID_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.ARID_COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.ARID_SAVANNA)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.ARID_COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_PATH_10.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(WWBiomes.OLD_GROWTH_BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_PATH_10.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.COARSE_DIRT_DISK_AND_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.COARSE_DIRT_DISK_AND_PILE_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_PATH_SMALL)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_DIRT_PATH_SMALL.getKey());
						}
					}

					if (surfaceDecoration.mudDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_DECORATIVE_MUD)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.DISK_MUD.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.MUD_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MUD_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.MUD_PILE.getKey());
						}
					}

					if (surfaceDecoration.packedMudDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PACKED_MUD_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.PACKED_MUD_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PACKED_MUD_ORE)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.ORE_PACKED_MUD.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.PACKED_MUD_PATH_BADLANDS.getKey());
						}
					}

					if (surfaceDecoration.gravelDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RARE_GRAVEL)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.GRAVEL_PATH_RARE.getKey());
						}
					}

					if (surfaceDecoration.stoneDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RARE_STONE)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.STONE_PATH_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_STONE_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, WWMiscPlaced.STONE_DISK_AND_PILE.getKey());
						}
					}

					if (surfaceDecoration.mossDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.MOSS_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_CARPET)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWPlacedFeatures.MOSS_CARPET.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.MOSS_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PALE_MOSS_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.PALE_MOSS_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_GRAVEL_AND_PALE_MOSS_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.GRAVEL_AND_PALE_MOSS_PATH.getKey());
						}
					}

					if (surfaceDecoration.auburnMoss) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CREEPING_AUBURN_MOSS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.AUBURN_CREEPING_MOSS.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_AUBURN_MOSS)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.AUBURN_MOSS.getKey());
						}
					}

					if (surfaceDecoration.scorchedSandDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SCORCHED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SCORCHED_SAND.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SCORCHED_SAND_HUGE.getKey());
						}
					}

					if (surfaceDecoration.scorchedRedSandDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SCORCHED_RED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SCORCHED_RED_SAND.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SCORCHED_RED_SAND_HUGE.getKey());
						}
					}

					if (surfaceDecoration.sandstoneDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SANDSTONE_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.SANDSTONE_PATH.getKey());
						}
					}

					if (surfaceDecoration.clayDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_CLAY_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.getKey());
						}
					}

					if (surfaceDecoration.clearingDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.COARSE_PATH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_GRAVEL_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.GRAVEL_PATH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_ROOTED_DIRT_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.ROOTED_DIRT_PATH_CLEARING.getKey());
						}
					}

					if (surfaceDecoration.taigaBoulders) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_TAIGA_FOREST_ROCK)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.FOREST_ROCK_TAIGA.getKey());
						}
					}

					if (surfaceDecoration.snowPiles) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SNOW_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWMiscPlaced.SNOW_PILE.getKey());
						}
					}

					if (surfaceDecoration.fragileIceDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SURFACE_FRAGILE_ICE)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.FRAGILE_ICE_DISK_SURFACE.getKey());
						}
					}

					if (surfaceDecoration.icicleDecoration) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SURFACE_ICICLES)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWCavePlaced.ICICLES_SURFACE_WG.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WWCavePlaced.ICICLES_SURFACE.getKey());
						}
					}

					if (surfaceDecoration.lakes) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MUD_LAKE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.MUD_LAKE.getKey());
						}
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_LAKE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.MOSS_LAKE.getKey());
						}
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_LAKE_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.MOSS_LAKE_RARE.getKey());
						}
					}

					if (surfaceDecoration.basins) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MOSS_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.BASIN_MOSS.getKey());
						}
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_PODZOL_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.BASIN_PODZOL.getKey());
						}
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MUD_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.BASIN_MUD.getKey());
						}
					}
			});

		BiomeModifications.create(WWConstants.id("surface_transitions"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
					WWWorldgenConfig.TransitionGeneration transitionGeneration = WWWorldgenConfig.get().transitionGeneration;

					if (transitionGeneration.sandTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SMALL_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.SMALL_SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.BETA_BEACH_SAND_TRANSITION.getKey());
						}
					}

					if (transitionGeneration.redSandTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_RED_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.RED_SAND_TRANSITION.getKey());
						}
					}

					if (transitionGeneration.coarseTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_COARSE_DIRT_TRANSITION_DISK)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WWMiscPlaced.COARSE_TRANSITION_DISK.getKey());
						}
					}

					if (transitionGeneration.gravelTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_GRAVEL_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.SMALL_GRAVEL_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.BETA_BEACH_GRAVEL_TRANSITION.getKey());
						}
					}

					if (transitionGeneration.mudTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_MUD_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.MUD_TRANSITION.getKey());
						}
					}

					if (transitionGeneration.stoneTransitions) {
						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_STONE_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WWMiscPlaced.STONE_TRANSITION.getKey());
						}
					}
			});

		BiomeModifications.create(WWConstants.id("river_pools"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WWWorldgenConfig.get().aquaticGeneration.riverPool) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WWBiomeTags.HAS_WATER_POOLS)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.RIVER_POOL.getKey());
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WWMiscPlaced.SMALL_RIVER_POOL.getKey());
						}
					}
				});
	}

}
