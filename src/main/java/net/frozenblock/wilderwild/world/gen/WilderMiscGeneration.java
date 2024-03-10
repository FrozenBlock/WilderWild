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

package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.worldgen.biome.api.modifications.FrozenBiomeSelectors;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.features.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.features.feature.WilderPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMiscGeneration {

	public static void generateMisc() {
		if (WorldgenConfig.get().netherGeyserGen) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_NETHER_GEYSER),
				GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.NETHER_GEYSER.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_NETHER_LAVA_GEYSER),
				GenerationStep.Decoration.UNDERGROUND_DECORATION, WilderMiscPlaced.NETHER_LAVA_GEYSER.getKey());
		}

		if (WorldgenConfig.get().termiteGen) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TERMITE_MOUND),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.getKey());
		}

		if (WorldgenConfig.get().surfaceDecoration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_COARSE),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_GRAVEL),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRAVEL_PATH_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_STONE),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.STONE_PATH_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TAIGA_FOREST_ROCK),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_PATH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_PATH),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.PACKED_MUD_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_ORE),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CLAY_PATH),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.getKey());

			BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.STONE_POOL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH_SMALL),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_DIRT_PATH_SMALL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.PACKED_MUD_PATH_BADLANDS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND_HUGE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_RED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_RED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND_HUGE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SANDSTONE_PATH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SANDSTONE_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_LAKE),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.MOSS_LAKE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_LAKE_RARE),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.MOSS_LAKE_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_BASIN),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_MOSS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PODZOL_BASIN),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_PODZOL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_CARPET),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSS_CARPET.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_PILE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.MOSS_PILE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MUD_PILE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.MUD_PILE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MUD_BASIN),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.BASIN_MUD.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MUD_LAKE),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.MUD_LAKE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_5.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.ARID_FOREST, RegisterWorldgen.ARID_SAVANNA),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ARID_COARSE_PATH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.BIRCH_TAIGA, RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH_10.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.SNOWY_OLD_GROWTH_PINE_TAIGA),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderMiscPlaced.PILE_SNOW.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_CLEARING),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.COARSE_PATH_CLEARING.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_GRAVEL_CLEARING),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.GRAVEL_PATH_CLEARING.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_ROOTED_DIRT_CLEARING),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ROOTED_DIRT_PATH_CLEARING.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_WATER_POOLS),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.RIVER_POOL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_WATER_POOLS),
				GenerationStep.Decoration.LAKES, WilderMiscPlaced.SMALL_RIVER_POOL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PILE_WITH_DISK),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.COARSE_DIRT_DISK_AND_PILE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_PUMPKIN),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_PUMPKIN_COMMON.getKey());
		}

		if (WorldgenConfig.get().surfaceTransitions) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SAND_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SMALL_SAND_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SAND_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SAND_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RED_SAND_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.RED_SAND_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_STONE_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.STONE_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.BETA_BEACH_SAND_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BETA_BEACH_GRAVEL_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.BETA_BEACH_GRAVEL_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_GRAVEL_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.SMALL_GRAVEL_TRANSITION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MUD_TRANSITION),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.MUD_TRANSITION.getKey());
		}
	}

}
