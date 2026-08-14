/*
 * Copyright 2025-2026 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.levelgen.modification;

import net.frozenblock.lib.levelgen.biome.api.BiomeSelectors;
import net.frozenblock.lib.levelgen.biome.api.modifications.BiomeModifications;
import net.frozenblock.lib.levelgen.biome.api.modifications.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.minecraft.data.worldgen.biome.BiomeData;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.ARGB;
import net.minecraft.world.level.biome.Biomes;

public final class WWBiomeSettings {

	static void init() {
		BiomeModifications.create(WWConstants.id("foliage_color_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(BiomeTags.IS_BADLANDS),
			context -> {
				if (!WWAmbienceAndMiscConfig.BADLANDS_FOLIAGE_COLOR.get()) return;
				context.getEffects().setFoliageColorOverride(11445290);
			});

		BiomeModifications.create(WWConstants.id("grass_color_dappled_forest")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DAPPLED_FOREST),
			context -> {
				if (!WWAmbienceAndMiscConfig.DAPPLED_FOREST_GRASS_COLOR.get()) return;
				context.getEffects().setGrassColorOverride(ARGB.color(0, 229, 125, 47));
			});

		WWWaterColors.init();
		WWSpawns.addBugs();
		WWSpawns.addJellyfish();
		WWSpawns.addCrabs();
		WWSpawns.addOstriches();
		WWSpawns.addPenguins();
		WWSpawns.addTumbleweed();
		WWSpawns.addRabbits();
		WWSpawns.addMooblooms();
	}
}
