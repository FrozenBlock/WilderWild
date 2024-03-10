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

package net.frozenblock.wilderwild.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.config.MiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.frozenblock.wilderwild.registry.RegisterWorldgen;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WilderMusic {
	private WilderMusic() {
		throw new UnsupportedOperationException("WilderMusic contains only static declarations.");
	}

	public static void playMusic() {
		WilderSharedConstants.logWithModId("Adding Music And Ambience To Biomes for", true);

		/** MUSIC */
		BiomeModifications.create(WilderSharedConstants.id("modify_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.BIRCH_FOREST),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_old_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.OLD_GROWTH_BIRCH_FOREST),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_flower_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.FLOWER_FOREST),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_mixed_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(RegisterWorldgen.MIXED_FOREST),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_birch_taiga_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(RegisterWorldgen.BIRCH_TAIGA),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_old_growth_birch_taiga_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(RegisterWorldgen.OLD_GROWTH_BIRCH_TAIGA),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_semi_birch_forest_music")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(RegisterWorldgen.SEMI_BIRCH_FOREST),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});

		/** AMBIENCE */
		// Deep Dark
		BiomeModifications.create(WilderSharedConstants.id("modify_deep_dark_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.deepDarkAmbience) {
					modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DEEP_DARK_LOOP);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_deep_dark_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.deepDarkAmbience) {
					modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D));
				}
			});

		// Dripstone Caves
		BiomeModifications.create(WilderSharedConstants.id("modify_dripstone_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.dripstoneCavesAmbience) {
					modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_LOOP);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_dripstone_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.DRIPSTONE_CAVES),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.dripstoneCavesAmbience) {
					modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D));
				}
			});

		// Lush Caves
		BiomeModifications.create(WilderSharedConstants.id("modify_lush_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.lushCavesAmbience) {
					modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_LUSH_CAVES_LOOP);
				}
			});
		BiomeModifications.create(WilderSharedConstants.id("modify_lush_caves_additions")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.LUSH_CAVES),
			(selectionContext, modificationContext) -> {
				if (MiscConfig.get().biomeAmbience.lushCavesAmbience) {
					modificationContext.getEffects().setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D));
				}
			});

		// Generic Caves
        /*BiomeModifications.create(WilderSharedConstants.id("modify_caves_ambience")).add(ModificationPhase.REPLACEMENTS, (context) -> context.getBiomeKey().equals(Biomes.SOMETHING),
                (selectionContext, modificationContext) -> modificationContext.getEffects().setAmbientSound(RegisterSounds.AMBIENT_GENERIC_CAVES_LOOP));*/
	}
}
