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

package net.frozenblock.wilderwild.registry;

import java.util.List;
import java.util.Optional;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.predicates.ConfigPredicate;
import net.frozenblock.lib.levelgen.biome.api.attribute.BiomeEnvironmentAttributeModification;
import net.frozenblock.lib.platform.api.registry.DeferredHolder;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.config.WWAmbienceAndMiscConfig;
import net.frozenblock.wilderwild.tag.WWBiomeTags;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.Musics;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.attribute.AmbientAdditionsSettings;
import net.minecraft.world.attribute.AmbientMoodSettings;
import net.minecraft.world.attribute.AmbientParticle;
import net.minecraft.world.attribute.AmbientSounds;
import net.minecraft.world.attribute.BackgroundMusic;
import net.minecraft.world.attribute.EnvironmentAttributeMap;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public final class WWBiomeEnvironmentAttributeModifications {

	public static void bootstrap(BootstrapContext<BiomeEnvironmentAttributeModification> context) {
		// FOG
		registerBlackFog(context, Biomes.DEEP_DARK, WWAmbienceAndMiscConfig.DEEP_DARK_FOG);
		registerBlackFog(context, Biomes.DRIPSTONE_CAVES, WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_FOG);
		registerBlackFog(context, Biomes.LUSH_CAVES, WWAmbienceAndMiscConfig.LUSH_CAVES_FOG);
		registerBlackFog(context, Biomes.SULFUR_CAVES, WWAmbienceAndMiscConfig.SULFUR_CAVES_FOG);
		registerBlackFog(context, WWBiomes.FROZEN_CAVES, WWAmbienceAndMiscConfig.FROZEN_CAVES_FOG);
		registerBlackFog(context, WWBiomes.MESOGLEA_CAVES, WWAmbienceAndMiscConfig.MESOGLEA_CAVES_FOG);
		registerBlackFog(context, WWBiomes.MAGMATIC_CAVES, WWAmbienceAndMiscConfig.MAGMATIC_CAVES_FOG);

		// PARTICLES
		BiomeEnvironmentAttributeModification.register(
			context,
			WWConstants.id("particle/magmatic_caves"),
			WWBiomes.MAGMATIC_CAVES,
			EnvironmentAttributeMap.builder().set(
				EnvironmentAttributes.AMBIENT_PARTICLES,
				List.of(new AmbientParticle(ParticleTypes.LARGE_SMOKE, 0.00123F))
			).build(),
			WWAmbienceAndMiscConfig.MAGMATIC_CAVES_PARTICLES.equalTo(true)
		);

		// AMBIENCE
		registerAmbience(
			context,
			Biomes.DEEP_DARK,
			WWSounds.AMBIENT_DEEP_DARK_LOOP,
			WWSounds.AMBIENT_DEEP_DARK_ADDITIONS,
			0.005D,
			WWAmbienceAndMiscConfig.DEEP_DARK_AMBIENCE
		);
		registerAmbience(
			context,
			Biomes.DRIPSTONE_CAVES,
			WWSounds.AMBIENT_DRIPSTONE_CAVES_LOOP,
			WWSounds.AMBIENT_DRIPSTONE_CAVES_ADDITIONS,
			0.01D,
			WWAmbienceAndMiscConfig.DRIPSTONE_CAVES_AMBIENCE
		);
		registerAmbience(
			context,
			Biomes.LUSH_CAVES,
			WWSounds.AMBIENT_LUSH_CAVES_LOOP,
			WWSounds.AMBIENT_LUSH_CAVES_ADDITIONS,
			0.01D,
			WWAmbienceAndMiscConfig.LUSH_CAVES_AMBIENCE
		);
		registerAmbience(
			context,
			Biomes.SULFUR_CAVES,
			WWSounds.AMBIENT_SULFUR_CAVES_LOOP,
			WWSounds.AMBIENT_SULFUR_CAVES_ADDITIONS,
			0.015D,
			WWAmbienceAndMiscConfig.SULFUR_CAVES_AMBIENCE
		);
		registerAmbience(
			context,
			WWBiomes.FROZEN_CAVES,
			WWSounds.AMBIENT_FROZEN_CAVES_LOOP,
			WWSounds.AMBIENT_FROZEN_CAVES_ADDITIONS,
			0.003D,
			WWAmbienceAndMiscConfig.FROZEN_CAVES_AMBIENCE
		);
		registerAmbience(
			context,
			WWBiomes.MESOGLEA_CAVES,
			WWSounds.AMBIENT_MESOGLEA_CAVES_LOOP,
			WWSounds.AMBIENT_MESOGLEA_CAVES_ADDITIONS,
			0.005D,
			WWAmbienceAndMiscConfig.MESOGLEA_CAVES_AMBIENCE
		);
		registerAmbience(
			context,
			WWBiomes.MAGMATIC_CAVES,
			WWSounds.AMBIENT_MAGMATIC_CAVES_LOOP,
			WWSounds.AMBIENT_MAGMATIC_CAVES_ADDITIONS,
			0.005D,
			WWAmbienceAndMiscConfig.MAGMATIC_CAVES_AMBIENCE
		);

		// MUSIC
		registerMusic(
			context,
			"forest",
			WWBiomeTags.HAS_FOREST_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_FORESTS,
			WWAmbienceAndMiscConfig.WILDER_FOREST_MUSIC
		);
		registerMusic(
			context,
			"flower_forest",
			WWBiomeTags.HAS_FLOWER_FOREST_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_FLOWER_FORESTS,
			WWAmbienceAndMiscConfig.WILDER_FOREST_MUSIC
		);
		registerMusic(
			context,
			"lush_caves",
			WWBiomeTags.HAS_LUSH_CAVES_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_LUSH_CAVES,
			WWAmbienceAndMiscConfig.WILDER_LUSH_CAVES_MUSIC
		);
		registerMusic(
			context,
			"dripstone_caves",
			WWBiomeTags.HAS_DRIPSTONE_CAVES_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_DRIPSTONE_CAVES,
			WWAmbienceAndMiscConfig.WILDER_DRIPSTONE_CAVES_MUSIC
		);
		registerMusic(
			context,
			"cherry_grove",
			WWBiomeTags.HAS_CHERRY_GROVE_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_CHERRY_GROVES,
			WWAmbienceAndMiscConfig.WILDER_CHERRY_GROVE_MUSIC
		);
		registerMusic(
			context,
			"taiga",
			WWBiomeTags.HAS_TAIGA_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_TAIGAS,
			WWAmbienceAndMiscConfig.WILDER_TAIGA_MUSIC
		);
		registerMusic(
			context,
			"old_growth_taiga",
			WWBiomeTags.HAS_OLD_GROWTH_TAIGA_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_OLD_GROWTH_TAIGAS,
			WWAmbienceAndMiscConfig.WILDER_TAIGA_MUSIC
		);
		registerMusic(
			context,
			"grove",
			WWBiomeTags.HAS_GROVE_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_GROVES,
			WWAmbienceAndMiscConfig.WILDER_GROVE_MUSIC
		);
		registerMusic(
			context,
			"jungle",
			WWBiomeTags.HAS_JUNGLE_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_JUNGLES,
			WWAmbienceAndMiscConfig.WILDER_JUNGLE_MUSIC
		);
		registerMusic(
			context,
			"bamboo_jungle",
			WWBiomeTags.HAS_BAMBOO_JUNGLE_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_BAMBOO_JUNGLES,
			WWAmbienceAndMiscConfig.WILDER_JUNGLE_MUSIC
		);
		registerMusic(
			context,
			"sparse_jungle",
			WWBiomeTags.HAS_SPARSE_JUNGLE_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_SPARSE_JUNGLES,
			WWAmbienceAndMiscConfig.WILDER_JUNGLE_MUSIC
		);
		registerMusic(
			context,
			"badlands",
			WWBiomeTags.HAS_BADLANDS_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_BADLANDS,
			WWAmbienceAndMiscConfig.WILDER_BADLANDS_MUSIC
		);
		registerMusic(
			context,
			"desert",
			WWBiomeTags.HAS_DESERT_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_DESERTS,
			WWAmbienceAndMiscConfig.WILDER_DESERT_MUSIC
		);
		registerMusic(
			context,
			"snowy",
			WWBiomeTags.HAS_SNOWY_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_SNOWY,
			WWAmbienceAndMiscConfig.WILDER_SNOWY_MUSIC
		);
		registerMusic(
			context,
			"ocean",
			WWBiomeTags.HAS_OCEAN_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_OCEANS,
			WWAmbienceAndMiscConfig.WILDER_OCEAN_MUSIC,
			true
		);
		registerMusic(
			context,
			"frozen_ocean",
			WWBiomeTags.HAS_FROZEN_OCEAN_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_FROZEN_OCEANS,
			WWAmbienceAndMiscConfig.WILDER_OCEAN_MUSIC,
			true
		);
		registerMusic(
			context,
			"warm_ocean",
			WWBiomeTags.HAS_WARM_OCEAN_MUSIC,
			WWSounds.MUSIC_OVERWORLD_WILD_WARM_OCEANS,
			WWAmbienceAndMiscConfig.WILDER_OCEAN_MUSIC,
			true
		);
		registerMusic(
			context,
			"maple_forest",
			WWBiomeTags.HAS_MAPLE_FOREST_MUSIC,
			WWSounds.MUSIC_OVERWORLD_MAPLE_FOREST,
			ConfigPredicate.alwaysTrue()
		);
		registerMusic(
			context,
			"dying_forest",
			WWBiomeTags.HAS_DYING_FOREST_MUSIC,
			WWSounds.MUSIC_OVERWORLD_DYING_FOREST,
			ConfigPredicate.alwaysTrue()
		);
		registerMusic(
			context,
			"snowy_dying_forest",
			WWBiomeTags.HAS_SNOWY_DYING_FOREST_MUSIC,
			WWSounds.MUSIC_OVERWORLD_SNOWY_DYING_FOREST,
			ConfigPredicate.alwaysTrue()
		);
		registerMusic(
			context,
			"frozen_caves",
			WWBiomeTags.HAS_FROZEN_CAVES_MUSIC,
			WWSounds.MUSIC_OVERWORLD_FROZEN_CAVES,
			ConfigPredicate.alwaysTrue()
		);
		registerMusic(
			context,
			"mesoglea_caves",
			WWBiomeTags.HAS_MESOGLEA_CAVES_MUSIC,
			WWSounds.MUSIC_OVERWORLD_MESOGLEA_CAVES,
			ConfigPredicate.alwaysTrue()
		);
		registerMusic(
			context,
			"magmatic_caves",
			WWBiomeTags.HAS_MAGMATIC_CAVES_MUSIC,
			WWSounds.MUSIC_OVERWORLD_MAGMATIC_CAVES,
			ConfigPredicate.alwaysTrue()
		);
	}

	private static void registerBlackFog(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		ResourceKey<Biome> biome,
		ConfigEntry<Boolean> entry
	) {
		BiomeEnvironmentAttributeModification.register(
			context,
			WWConstants.id("fog/" + biome.identifier().getPath()),
			biome,
			EnvironmentAttributeMap.builder().set(EnvironmentAttributes.FOG_COLOR, 0).build(),
			entry.equalTo(true)
		);
	}

	private static void registerAmbience(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		ResourceKey<Biome> biome,
		DeferredHolder<SoundEvent, SoundEvent> loop,
		DeferredHolder<SoundEvent, SoundEvent> additions,
		double additionsChance,
		ConfigEntry<Boolean> entry
	) {
		BiomeEnvironmentAttributeModification.register(
			context,
			WWConstants.id("ambience/" + biome.identifier().getPath()),
			biome,
			EnvironmentAttributeMap.builder().set(
				EnvironmentAttributes.AMBIENT_SOUNDS,
				new AmbientSounds(
					Optional.of(loop.asHolder()),
					Optional.of(AmbientMoodSettings.LEGACY_CAVE_SETTINGS),
					List.of(new AmbientAdditionsSettings(additions.asHolder(), additionsChance))
				)
			).build(),
			entry.equalTo(true)
		);
	}

	private static void registerMusic(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		String name,
		TagKey<Biome> biomes,
		DeferredHolder<SoundEvent, SoundEvent> music,
		ConfigPredicate mergeWhen,
		boolean hasUnderWater
	) {
		BackgroundMusic backgroundMusic = new BackgroundMusic(music.asHolder());
		if (hasUnderWater) backgroundMusic = backgroundMusic.withUnderwater(Musics.UNDER_WATER);

		BiomeEnvironmentAttributeModification.register(
			context,
			WWConstants.id("music/" + name),
			biomes,
			EnvironmentAttributeMap.builder().set(EnvironmentAttributes.BACKGROUND_MUSIC, backgroundMusic).build(),
			mergeWhen
		);
	}

	private static void registerMusic(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		String name,
		TagKey<Biome> biomes,
		DeferredHolder<SoundEvent, SoundEvent> music,
		ConfigPredicate mergeWhen
	) {
		registerMusic(context, name, biomes, music, mergeWhen, false);
	}

	private static void registerMusic(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		String name,
		TagKey<Biome> biomes,
		DeferredHolder<SoundEvent, SoundEvent> music,
		ConfigEntry<Boolean> entry,
		boolean hasUnderWater
	) {
		registerMusic(context, name, biomes, music, entry.equalTo(true), hasUnderWater);
	}

	private static void registerMusic(
		BootstrapContext<BiomeEnvironmentAttributeModification> context,
		String name,
		TagKey<Biome> biomes,
		DeferredHolder<SoundEvent, SoundEvent> music,
		ConfigEntry<Boolean> entry
	) {
		registerMusic(context, name, biomes, music, entry, false);
	}
}
