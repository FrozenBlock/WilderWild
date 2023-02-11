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
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderMiscPlaced;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMiscGeneration {
    public static void generateMisc() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.DISK_MUD.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DECORATIVE_MUD),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MUD_PATH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TAIGA_FOREST_ROCK),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.FOREST_ROCK_TAIGA.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_PATH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSS_PATH),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.MOSS_PATH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_PATH),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_SAVANNA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TERMITE_PLACED.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_ORE),
                GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.ORE_PACKED_MUD.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.MULTI_LAYER_SAND_BEACHES),
                GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.SAND_BEACHES),
				GenerationStep.Decoration.UNDERGROUND_ORES, WilderMiscPlaced.UNDER_WATER_CLAY_PATH_BEACH.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.STONE_POOL.unwrapKey().orElseThrow());

        BiomeModifications.addFeature(FrozenBiomeSelectors.foundInOverworldExcept(WilderBiomeTags.NO_POOLS),
                GenerationStep.Decoration.UNDERGROUND_STRUCTURES, WilderMiscPlaced.DEEPSLATE_POOL.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COARSE_DIRT_PATH_SMALL),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.COARSE_DIRT_PATH_SMALL.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PACKED_MUD_PATH_BADLANDS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.PACKED_MUD_PATH_BADLANDS.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_PLANT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TUMBLEWEED.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SANDSTONE_PATH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderMiscPlaced.SANDSTONE_PATH.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SMALL_SPONGE_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SMALL_SPONGES_RARE.unwrapKey().orElseThrow());

    }
}
