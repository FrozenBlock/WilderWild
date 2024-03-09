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
import net.frozenblock.wilderwild.tag.WilderBiomeTags;
import net.frozenblock.wilderwild.world.additions.feature.WilderPlacedFeatures;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderVegetationGeneration {

	public static void generateFlower() {
		if (WorldgenConfig.get().flowerGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CARNATION),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CARNATION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DATURA),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DATURA_BIRCH.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.CHERRY_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_CHERRY.getKey());

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

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FLOWERING_WATER_LILY),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_FLOWERED_WATERLILY.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL_UNCOMMON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_UNCOMMON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL_UNCOMMON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD_UNCOMMON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL_COMMON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_COMMON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CATTAIL_COMMON),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_CATTAIL_MUD_COMMON.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SEEDING_DANDELION),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.SEEDING_DANDELION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_SEEDING_DANDELION),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.COMMON_SEEDING_DANDELION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_SEEDING_DANDELION),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_SEEDING_DANDELION.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MILKWEED),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PLAINS_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_PLAINS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BERRY_PATCH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_BERRY_FOREST.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FIELD_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FLOWER_FIELD.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_CYPRESS_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.CYPRESS_WETLANDS_FLOWERS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_MILKWEED),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MILKWEED_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SUNFLOWER_PLAINS_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_SUNFLOWER_PLAINS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.MEADOW),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_MEADOW.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIRCH_CLEARING_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_BIRCH_CLEARING.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FOREST_CLEARING_FLOWERS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FOREST_CLEARING.getKey());
		}
	}

	public static void generateBush() {
		if (WorldgenConfig.get().bushGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.GENERIC_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_JUNGLE_BUSH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.JUNGLE_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SPARSE_BUSH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.SPARSE_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BADLANDS_SAND_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_SAND_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BADLANDS_TERRACOTTA_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_WOODED_BADLANDS_TERRACOTTA_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.WOODED_BADLANDS_BUSH_TERRACOTTA_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BADLANDS_RARE_SAND_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_BUSH_RARE_SAND_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DESERT_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DESERT_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_OASIS_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.OASIS_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_ARID_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.ARID_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FLOWER_FIELD_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.FLOWER_FIELD_BUSH_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RAINFOREST_BUSH),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DEAD_BUSH_AND_BUSH_PLACED.getKey());
		}
	}

	public static void generateCacti() {
		if (WorldgenConfig.get().cactusGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TALL_CACTUS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_CACTUS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_PRICKLY_PEAR),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RARE_PRICKLY_PEAR),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PRICKLY_PEAR_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TALL_BADLANDS_CACTUS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BADLANDS_TALL_CACTUS_PLACED.getKey());
		}
	}

	public static void generateAlgae() {
		if (WorldgenConfig.get().algae) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_ALGAE_SMALL),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE_SMALL.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_ALGAE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_ALGAE.getKey());
		}
	}

	public static void generateGrass() {
		if (WorldgenConfig.get().tumbleweed) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_TUMBLEWEED_PLANT),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TUMBLEWEED.getKey());
		}

		if (WorldgenConfig.get().grassGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_NEW_RARE_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_GRASS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_FLOWER_FIELD_TALL_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.PATCH_TALL_GRASS_FF.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DENSE_FERN),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_FERN_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_DENSE_TALL_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.DENSE_TALL_GRASS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_WATER_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.TALL_GRASS_AND_GRASS_WATER.getKey());
		}
	}

	public static void generateMushroom() {
		if (WorldgenConfig.get().mushroomGeneration) {
			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_HUGE_RED_MUSHROOM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_HUGE_BROWN_MUSHROOM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_BROWN_MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIG_MUSHROOMS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_BROWN_MUSHROOM),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_RED_MUSHROOM),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SWAMP_MUSHROOM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIG_MUSHROOM_PATCH),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.DARK_FOREST_MUSHROOM_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BROWN_SHELF_FUNGUS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.BROWN_SHELF_FUNGUS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RED_SHELF_FUNGUS),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RED_SHELF_FUNGUS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_RAINFOREST_MUSHROOM),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.RAINFOREST_MUSHROOMS_PLACED.getKey());

			BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_MIXED_MUSHROOM),
				GenerationStep.Decoration.TOP_LAYER_MODIFICATION, WilderPlacedFeatures.MIXED_MUSHROOMS_PLACED.getKey());
		}
	}

}
