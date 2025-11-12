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

import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.registry.WWBiomes;
import net.frozenblock.wilderwild.registry.WWSounds;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.attribute.AmbientAdditionsSettings;
import net.minecraft.world.attribute.AmbientMoodSettings;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biomes;

public final class WWBiomeMusicAndAmbience {

	private WWBiomeMusicAndAmbience() {
		throw new UnsupportedOperationException("WWBiomeMusicAndAmbience contains only static declarations.");
	}

	public static void playMusic() {
		WWConstants.logWithModId("Adding Music And Ambience for", true);

		// Configurable Music
		BiomeModifications.create(WWConstants.id("modify_music_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FOREST_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderForestMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_FORESTS);
			});

		BiomeModifications.create(WWConstants.id("modify_music_flower_forests")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FLOWER_FOREST_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderForestMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_FLOWER_FORESTS);
			});

		BiomeModifications.create(WWConstants.id("modify_music_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_LUSH_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderLushCavesMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_LUSH_CAVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DRIPSTONE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderDripstoneCavesMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_DRIPSTONE_CAVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_cherry_groves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_CHERRY_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderCherryGroveMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_CHERRY_GROVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_groves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_GROVE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderGroveMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_GROVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_JUNGLES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_bamboo_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_BAMBOO_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_BAMBOO_JUNGLES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_sparse_jungles")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SPARSE_JUNGLE_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderJungleMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_SPARSE_JUNGLES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_badlands")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_BADLANDS_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderBadlandsMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_BADLANDS);
			});

		BiomeModifications.create(WWConstants.id("modify_music_deserts")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DESERT_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderDesertMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_DESERTS);
			});


		BiomeModifications.create(WWConstants.id("modify_music_snowy")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SNOWY_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderSnowyMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_SNOWY);
			});

		BiomeModifications.create(WWConstants.id("modify_music_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_OCEANS);
			});

		BiomeModifications.create(WWConstants.id("modify_music_frozen_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FROZEN_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_FROZEN_OCEANS);
			});

		BiomeModifications.create(WWConstants.id("modify_music_warm_oceans")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_WARM_OCEAN_MUSIC),
			(selectionContext, modificationContext) -> {
				if (!WWAmbienceAndMiscConfig.get().music.wilderOceanMusic) return;
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_WILD_WARM_OCEANS);
			});

		// Normal Music
		BiomeModifications.create(WWConstants.id("modify_music_maple")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MAPLE_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_MAPLE_FOREST);
			});

		BiomeModifications.create(WWConstants.id("modify_music_dying")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_DYING_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_DYING_FOREST);
			});

		BiomeModifications.create(WWConstants.id("modify_music_snowy_dying")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_SNOWY_DYING_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_SNOWY_DYING_FOREST);
			});

		BiomeModifications.create(WWConstants.id("modify_music_frozen")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_FROZEN_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_FROZEN_CAVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_magmatic")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MAGMATIC_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_MAGMATIC_CAVES);
			});

		BiomeModifications.create(WWConstants.id("modify_music_mesoglea")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.tag(WWBiomeTags.HAS_MESOGLEA_MUSIC),
			(selectionContext, modificationContext) -> {
				setBackgroundMusic(modificationContext, WWSounds.MUSIC_OVERWORLD_MESOGLEA_CAVES);
			});

		// Ambience
		BiomeModifications.create(WWConstants.id("modify_ambience_deep_dark")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DEEP_DARK),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.deepDarkAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_DEEP_DARK_LOOP, WWSounds.AMBIENT_DEEP_DARK_ADDITIONS, 0.005D);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_dripstone_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.DRIPSTONE_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.dripstoneCavesAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_DRIPSTONE_CAVES_LOOP, WWSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS, 0.01D);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_lush_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(Biomes.LUSH_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.lushCavesAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_LUSH_CAVES_LOOP, WWSounds.AMBIENT_LUSH_CAVES_ADDITIONS, 0.01D);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_frozen_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.FROZEN_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.frozenCavesAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_FROZEN_CAVES_LOOP, WWSounds.AMBIENT_FROZEN_CAVES_ADDITIONS, 0.003D);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_mesoglea_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MESOGLEA_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.mesogleaCavesAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_MESOGLEA_CAVES_LOOP, WWSounds.AMBIENT_MESOGLEA_CAVES_ADDITIONS, 0.005D);
				}
			});

		BiomeModifications.create(WWConstants.id("modify_ambience_magmatic_caves")).add(
			ModificationPhase.REPLACEMENTS,
			BiomeSelectors.includeByKey(WWBiomes.MAGMATIC_CAVES),
			(selectionContext, modificationContext) -> {
				if (WWAmbienceAndMiscConfig.get().biomeAmbience.magmaticCavesAmbience) {
					setBiomeAmbience(modificationContext, WWSounds.AMBIENT_MAGMATIC_CAVES_LOOP, WWSounds.AMBIENT_MAGMATIC_CAVES_ADDITIONS, 0.005D);
				}
			});
	}

	private static void setBackgroundMusic(BiomeModificationContext context, Holder.Reference<SoundEvent> music) {
		context.getAttributes().set(EnvironmentAttributes.BACKGROUND_MUSIC, new BackgroundMusic(music));
	}

	private static void setBiomeAmbience(BiomeModificationContext context, Holder.Reference<SoundEvent> loop, Holder.Reference<SoundEvent> additions, double additionsChance) {
		final BiomeModificationContext.AttributesContext attributes = context.getAttributes();
		attributes.set(
			EnvironmentAttributes.AMBIENT_SOUNDS,
			new AmbientSounds(
				Optional.of(loop),
				Optional.of(AmbientMoodSettings.LEGACY_CAVE_SETTINGS),
				List.of(new AmbientAdditionsSettings(additions, additionsChance))
			)
		);
	}

}
