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

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.wilderwild.config.AmbienceAndMiscConfig;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.RegisterSounds;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WilderMusic {
	private WilderMusic() {
		throw new UnsupportedOperationException("WilderMusic contains only static declarations.");
	}

	public static void playMusic() {
		WilderSharedConstants.logWithModId("Adding Music And Ambience To Biomes for", true);

		// Music
		BiomeModifications.create(WilderSharedConstants.id("modify_music_birch_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(ConventionalBiomeTags.BIRCH_FOREST),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_BIRCH_FORESTS));
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_music_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(ConventionalBiomeTags.FOREST),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(RegisterSounds.MUSIC_OVERWORLD_WILD_BIRCH_FORESTS));
				}
			});

		// Ambience
		BiomeModifications.create(WilderSharedConstants.id("modify_ambience_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.deepDarkAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(RegisterSounds.AMBIENT_DEEP_DARK_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D));
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_ambience_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.dripstoneCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D));
				}
			});

		BiomeModifications.create(WilderSharedConstants.id("modify_ambience_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.LUSH_CAVES),
			(selectionContext, modificationContext) -> {
				if (AmbienceAndMiscConfig.get().biomeAmbience.lushCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(RegisterSounds.AMBIENT_LUSH_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(RegisterSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D));
				}
			});
	}
}
