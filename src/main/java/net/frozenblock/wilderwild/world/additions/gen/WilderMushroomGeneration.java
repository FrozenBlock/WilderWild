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
import net.minecraft.world.level.levelgen.GenerationStep;

public final class WilderMushroomGeneration {
    public static void generateMushroom() {
        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_HUGE_RED_MUSHROOM),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_RED_MUSHROOM_PLACED);

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_HUGE_BROWN_MUSHROOM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_BROWN_MUSHROOM_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_BIG_MUSHROOMS),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.MUSHROOM_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_BROWN_MUSHROOM),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.BROWN_MUSHROOM_PLACED);

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_COMMON_RED_MUSHROOM),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RED_MUSHROOM_PLACED);

        BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_SWAMP_MUSHROOM),
                GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.HUGE_MUSHROOMS_SWAMP);
    }
}
