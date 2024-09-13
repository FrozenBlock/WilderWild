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

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.registry.WWWorldgen;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WWMusic {
	private WWMusic() {
		throw new UnsupportedOperationException("WWMusic contains only static declarations.");
	}

	public static void playMusic() {
		WWConstants.logWithModId("Adding Music And Ambience To Biomes for", true);

		// Music
		BiomeModifications.create(WWConstants.id("modify_music_birch_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(ConventionalBiomeTags.BIRCH_FOREST),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_music_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(ConventionalBiomeTags.FOREST),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeMusic.wilderForestMusic) {
					modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_FORESTS));
				}
			});

		// Ambience
		BiomeModifications.create(WWConstants.id("modify_ambience_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.deepDarkAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_DEEP_DARK_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.dripstoneCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_DRIPSTONE_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.LUSH_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.lushCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_LUSH_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_frozen_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWWorldgen.FROZEN_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.frozenCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_FROZEN_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_FROZEN_CAVES_ADDITIONS, 0.003D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_jellyfish_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWWorldgen.JELLYFISH_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.jellyfishCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_JELLYFISH_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_JELLYFISH_CAVES_ADDITIONS, 0.005D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWWorldgen.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_MAGMATIC_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_MAGMATIC_CAVES_ADDITIONS, 0.005D));
				}
			});
	}
}
