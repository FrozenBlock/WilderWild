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

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biomes;

public final class WilderBiomeSettings {

	static void init() {
		BiomeModifications.create(WilderSharedConstants.id("replace_deep_dark_fog")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(modificationContext) -> modificationContext.getEffects().setFogColor(0));

		BiomeModifications.create(WilderSharedConstants.id("replace_badlands_foliage_color")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(BiomeTags.IS_BADLANDS),
			(modificationContext) -> modificationContext.getEffects().setFoliageColor(11445290));

		WilderMusic.playMusic();
		WilderWaterColors.stirWater();
		WilderSpawns.addFireflies();
		WilderSpawns.addJellyfish();
		WilderSpawns.addCrabs();
		WilderSpawns.addOstriches();
		WilderSpawns.addTumbleweed();
		WilderSpawns.addRabbits();
	}
}
