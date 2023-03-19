/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.lib.worldgen.biome.api.modifications.FrozenBiomeSelectors;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMiscGeneration {
    public static void generateMisc() {
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
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TERMITE_MOUND),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CLAY_PATH),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.getKey());

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.STONE_POOL.getKey());

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.DEEPSLATE_POOL.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH_SMALL),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_DIRT_PATH_SMALL.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH_BADLANDS.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_PLANT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TUMBLEWEED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_SAND_HUGE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_RED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SCORCHED_RED_SAND),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SCORCHED_RED_SAND_HUGE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SAND_TRANSITION),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.SMALL_SAND_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SAND_TRANSITION),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.SAND_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RED_SAND_TRANSITION),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.RED_SAND_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_STONE_TRANSITION),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.STONE_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BETA_BEACH_SAND_TRANSITION),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.BETA_BEACH_SAND_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_GRAVEL_TRANSITION),
				GenerationStep.Decoration.LOCAL_MODIFICATIONS, WilderMiscPlaced.SMALL_GRAVEL_TRANSITION.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SANDSTONE_PATH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SANDSTONE_PATH.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES_RARE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_LAKE),
				GenerationStep.Decoration.FLUID_SPRINGS, WilderMiscPlaced.MOSS_LAKE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.RAINFOREST),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.BASIN_RAINFOREST.getKey());
    }

}
