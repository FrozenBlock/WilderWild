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

package net.frozenblock.wilderwild.world.additions.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.frozenblock.wilderwild.config.WorldgenConfig;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderTreesGeneration {

	public static void generateTrees() {
		if (WorldgenConfig.get().snappedTrees) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_OAK_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_CYPRESS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_CYPRESS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_JUNGLE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_JUNGLE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_LARGE_JUNGLE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_JUNGLE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_JUNGLE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_JUNGLE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_ACACIA),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_ACACIA_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_ACACIA_AND_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_ACACIA_AND_OAK_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_CHERRY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_CHERRY_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_DARK_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_DARK_OAK_PLACED.getKey());

			// CLEARINGS
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_OAK_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_SPRUCE_SNOWY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_LARGE_SPRUCE_SNOWY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_LARGE_SPRUCE_ON_SNOW_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK_AND_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_AND_SPRUCE_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_OAK_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_BIRCH_AND_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_BIRCH_AND_SPRUCE_CLEARING_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_DARK_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_DARK_OAK_CLEARING_PLACED.getKey());

			// NETHER
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_CRIMSON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_CRIMSON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SNAPPED_WARPED),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SNAPPED_WARPED.getKey());
		}

		if (WorldgenConfig.get().fallenTrees) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_BIRCH_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_SPRUCE_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CLEAN_FALLEN_SPRUCE_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CLEAN_FALLEN_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_SPRUCE_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_BIRCH_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSSY_FALLEN_MIXED_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_MIXED_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MOSSY_FALLEN_OAK_AND_BIRCH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MOSSY_FALLEN_TREES_OAK_AND_BIRCH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_CHERRY_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_CHERRY_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_ACACIA_AND_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_ACACIA_AND_OAK_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_PALM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_PALM_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_PLACED_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_PALM_AND_JUNGLE_AND_OAK),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_PALM_AND_JUNGLE_AND_OAK_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_BIRCH_AND_OAK_DARK_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_OAK_DARK_FOREST_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_SWAMP_OAK_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_PLACED_SWAMP.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_MANGROVE_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_MANGROVE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_OAK_AND_CYPRESS_TREES),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_CYPRESS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.MIXED_FOREST),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_TREES_MIXED_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.BIRCH_TAIGA, RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_BIRCH_AND_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(RegisterWorldgen.FLOWER_FIELD),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_OAK_AND_BIRCH_PLACED_2.getKey());

			// NETHER

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_CRIMSON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_CRIMSON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FALLEN_WARPED),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FALLEN_WARPED.getKey());
		}

		if (WorldgenConfig.get().treeGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.SPARSE_JUNGLE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM.getKey());

			BiomeModifications.addFeature(BiomeSelectors.includeByKey(Biomes.JUNGLE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_JUNGLE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PALMS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALM_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_WARM_BEACH_PALMS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PALMS_WARM_BEACH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIG_COARSE_SHRUB),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BIG_SHRUB.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FOREST_SHRUB),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS_FOREST.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHRUB),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHRUBS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SHORT_MEGA_SPRUCE_SNOWY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SHORT_MEGA_SPRUCE_ON_SNOW_PLACED.getKey());
		}
	}
}
