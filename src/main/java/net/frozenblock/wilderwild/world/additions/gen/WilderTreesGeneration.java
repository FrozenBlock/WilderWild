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
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {
    public static void generateTrees() {

        if (WilderSharedConstants.config().dyingTrees()) {

        }
        if (WilderSharedConstants.config().fallenLogs()) {
            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_BIRCH_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(BiomeTags.IS_TAIGA),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_MIXED_PLACED.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.unwrapKey().orElseThrow());
        }
        if (WilderSharedConstants.config().wildTrees()) {
			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.JUNGLE),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_JUNGLE.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PALMS),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_RARE.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.FOREST),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TREES_BIRCH_AND_OAK.unwrapKey().orElseThrow());

            BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_SPRUCE),
                    GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_SPRUCE_PLACED.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIG_SHRUB),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIG_SHRUB.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_PLACED.unwrapKey().orElseThrow());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY),
					GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.unwrapKey().orElseThrow());
        }
    }
}
