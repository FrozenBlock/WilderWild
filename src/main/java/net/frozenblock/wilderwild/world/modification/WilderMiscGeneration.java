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
import net.frozenblock.lib.worldgen.biome.api.modifications.FrozenBiomeSelectors;
import net.frozenblock.wilderwild.WilderSharedConstants;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.features.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.features.feature.WilderPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMiscGeneration {

	public static void generateMisc() {
		BiomeModifications.create(WilderSharedConstants.id("nether_geysers"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().netherGeyserGen) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_NETHER_GEYSER)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.NETHER_GEYSER.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_NETHER_LAVA_GEYSER)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.NETHER_LAVA_GEYSER.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("termite_transitions"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().termiteGen) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_TERMITE_MOUND)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("surface_decoration"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().surfaceDecoration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_COARSE)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_GRAVEL)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRAVEL_PATH_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RARE_STONE)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.STONE_PATH_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DECORATIVE_MUD)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_DECORATIVE_MUD)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_TAIGA_FOREST_ROCK)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COARSE_DIRT_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PACKED_MUD_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.PACKED_MUD_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PACKED_MUD_ORE)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_CLAY_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COARSE_DIRT_PATH_SMALL)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_DIRT_PATH_SMALL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.PACKED_MUD_PATH_BADLANDS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SCORCHED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SCORCHED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND_HUGE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SCORCHED_RED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SCORCHED_RED_SAND)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND_HUGE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SANDSTONE_PATH)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SANDSTONE_PATH.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SMALL_SPONGE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SMALL_SPONGE_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_LAKE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MOSS_LAKE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_LAKE_RARE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MOSS_LAKE_RARE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_MOSS.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_PODZOL_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_PODZOL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_CARPET)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSS_CARPET.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MOSS_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.MOSS_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MUD_PILE)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.MUD_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MUD_BASIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_MUD.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MUD_LAKE)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.MUD_LAKE.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.MIXED_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.ARID_FOREST)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ARID_COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.ARID_SAVANNA)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ARID_COARSE_PATH.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_10.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_10.getKey());
						}

						if (biomeSelectionContext.getBiomeKey().equals(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.PILE_SNOW.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COARSE_DIRT_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.COARSE_PATH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_GRAVEL_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRAVEL_PATH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_ROOTED_DIRT_CLEARING)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ROOTED_DIRT_PATH_CLEARING.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_WATER_POOLS)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.RIVER_POOL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_WATER_POOLS)) {
							generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.SMALL_RIVER_POOL.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.COARSE_DIRT_DISK_AND_PILE.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_COMMON_PUMPKIN)) {
							generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_PUMPKIN_COMMON.getKey());
						}
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("stone_pool_caves"))
			.add(ModificationPhase.ADDITIONS,
				FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().surfaceDecoration) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
						generationSettings.addFeature(GenerationStep.Decoration.LAKES, WilderMiscPlaced.STONE_POOL.getKey());
					}
				});

		BiomeModifications.create(WilderSharedConstants.id("surface_transitions"))
			.add(ModificationPhase.ADDITIONS,
				BiomeSelectors.all(),
				(biomeSelectionContext, context) -> {
					if (WorldgenConfig.get().surfaceTransitions) {
						BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SMALL_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SMALL_SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_RED_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.RED_SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_STONE_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.STONE_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.BETA_BEACH_SAND_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.BETA_BEACH_GRAVEL_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_GRAVEL_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SMALL_GRAVEL_TRANSITION.getKey());
						}

						if (biomeSelectionContext.hasTag(WilderBiomeTags.HAS_MUD_TRANSITION)) {
							generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.MUD_TRANSITION.getKey());
						}
					}
				});
	}

}
