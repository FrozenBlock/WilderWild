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

public final class WilderGrassGeneration {

    public static void generateGrass() {

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_NEW_RARE_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.RARE_GRASS_PLACED.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS.unwrapKey().orElseThrow());

		BiomeModifications.addFeature(BiomeSelectors.tag(WilderBiomeTags.HAS_LARGE_FERN_AND_GRASS_RARE),
				GenerationStep.Decoration.VEGETAL_DECORATION, WilderPlacedFeatures.LARGE_FERN_AND_GRASS_RARE.unwrapKey().orElseThrow());

    }
}
