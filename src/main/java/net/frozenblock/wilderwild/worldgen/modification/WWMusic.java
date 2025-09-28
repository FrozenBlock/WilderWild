/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.worldgen.modification;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.sounds.Musics;
import net.minecraft.world.level.biome.AmbientAdditionsSettings;
import net.minecraft.world.level.biome.Biomes;

public final class WWMusic {
	private WWMusic() {
		throw new UnsupportedOperationException("WWMusic contains only static declarations.");
	}

	public static void playMusic() {
		WWConstants.logWithModId("Adding Music And Ambience for", true);

		// Configurable Music
		BiomeModifications.create(WWConstants.id("modify_music_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FOREST_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderForestMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_FORESTS));
			});

		BiomeModifications.create(WWConstants.id("modify_music_flower_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FLOWER_FOREST_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderForestMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_FLOWER_FORESTS));
			});

		BiomeModifications.create(WWConstants.id("modify_music_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_LUSH_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderLushCavesMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_LUSH_CAVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DRIPSTONE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderDripstoneCavesMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_DRIPSTONE_CAVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_cherry_groves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_CHERRY_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderCherryGroveMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_CHERRY_GROVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_groves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_GROVE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderGroveMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_GROVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_JUNGLES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_bamboo_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_BAMBOO_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_BAMBOO_JUNGLES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_sparse_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SPARSE_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_SPARSE_JUNGLES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_BADLANDS_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderBadlandsMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_BADLANDS));
			});

		BiomeModifications.create(WWConstants.id("modify_music_deserts")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DESERT_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderDesertMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_DESERTS));
			});


		BiomeModifications.create(WWConstants.id("modify_music_snowy")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SNOWY_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderSnowyMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_SNOWY));
			});

		BiomeModifications.create(WWConstants.id("modify_music_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_OCEANS));
			});

		BiomeModifications.create(WWConstants.id("modify_music_frozen_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FROZEN_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_FROZEN_OCEANS));
			});

		BiomeModifications.create(WWConstants.id("modify_music_warm_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_WARM_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_WILD_WARM_OCEANS));
			});

		// Normal Music
		BiomeModifications.create(WWConstants.id("modify_music_maple")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MAPLE_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_MAPLE_FOREST));
			});

		BiomeModifications.create(WWConstants.id("modify_music_dying")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DYING_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_DYING_FOREST));
			});

		BiomeModifications.create(WWConstants.id("modify_music_snowy_dying")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SNOWY_DYING_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_SNOWY_DYING_FOREST));
			});

		BiomeModifications.create(WWConstants.id("modify_music_frozen")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FROZEN_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_FROZEN_CAVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_magmatic")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MAGMATIC_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_MAGMATIC_CAVES));
			});

		BiomeModifications.create(WWConstants.id("modify_music_mesoglea")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MESOGLEA_MUSIC),
			(selectionContext, modificationContext) -> {
				modificationContext.getEffects().setMusic(Musics.createGameMusic(WWSounds.MUSIC_OVERWORLD_MESOGLEA_CAVES));
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
			BiomeSelectors.includeByKey(WWBiomes.FROZEN_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.frozenCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_FROZEN_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_FROZEN_CAVES_ADDITIONS, 0.003D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_mesoglea_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MESOGLEA_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.mesogleaCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_MESOGLEA_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_MESOGLEA_CAVES_ADDITIONS, 0.005D));
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesAmbience) {
					BiomeModificationContext.EffectsContext context = modificationContext.getEffects();
					context.setAmbientSound(WWSounds.AMBIENT_MAGMATIC_CAVES_LOOP);
					context.setAdditionsSound(new AmbientAdditionsSettings(WWSounds.AMBIENT_MAGMATIC_CAVES_ADDITIONS, 0.005D));
				}
			});
	}
}
