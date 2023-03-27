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
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderFlowersGeneration {

    public static void generateFlower() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CARNATION),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DATURA),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_GLORY_OF_THE_SNOW),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.FLOWER_SPARSE_JUNGLE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_JUNGLE_FLOWERS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.FLOWER_JUNGLE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SPARSE_JUNGLE_FLOWERS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW_SPARSE_JUNGLE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_JUNGLE_FLOWERS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.GLORY_OF_THE_SNOW_JUNGLE.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BROWN_SHELF_FUNGUS),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RED_SHELF_FUNGUS),
                GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FLOWERING_WATER_LILY),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL_COMMON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_COMMON.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_5.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SEEDING_DANDELION),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MILKWEED),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.getKey());

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_PLAINS.getKey());

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BERRY_PATCH),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DESERT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_CACTUS_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.DESERT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DESERT_BUSH_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BADLANDS, Biomes.WOODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.ERODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR_RARE.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BADLANDS, Biomes.ERODED_BADLANDS, Biomes.WOODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_TALL_CACTUS_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BADLANDS, Biomes.WOODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_SAND_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.WOODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.ERODED_BADLANDS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_RARE_SAND_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FIELD_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FLOWER_FIELD.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_JUNGLE_BUSH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.JUNGLE_BUSH_PLACED.getKey());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SPARSE_JUNGLE_BUSH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.SPARSE_JUNGLE_BUSH_PLACED.getKey());
	}
}
