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

package net.frozenblock.wilderwild.config;

import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;

public final class WWAmbienceAndMiscConfig {
	private static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(WWConstants.id("misc")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> MODIFY_ADVANCEMENTS = CONFIG.entryBuilder("modifyAdvancements", EntryType.BOOL, true).requireRestart().build();

	// BIOME AMBIENCE
	public static final ConfigEntry<Boolean> DEEP_DARK_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/deepDarkAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DEEP_DARK_FOG = CONFIG.unsyncableEntryBuilder("biomeAmbience/deepDarkFog", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DRIPSTONE_CAVES_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/dripstoneCavesAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> LUSH_CAVES_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/lushCavesAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FROZEN_CAVES_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/frozenCavesAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FROZEN_CAVES_FOG = CONFIG.unsyncableEntryBuilder("biomeAmbience/frozenCavesFog", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> MESOGLEA_CAVES_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/mesogleaCavesAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MESOGLEA_CAVES_FOG = CONFIG.unsyncableEntryBuilder("biomeAmbience/mesogleaCavesFog", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MAGMATIC_CAVES_AMBIENCE = CONFIG.unsyncableEntryBuilder("biomeAmbience/magmaticCavesAmbience", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MAGMATIC_CAVES_FOG = CONFIG.unsyncableEntryBuilder("biomeAmbience/magmaticCavesFog", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> MAGMATIC_CAVES_PARTICLES = CONFIG.unsyncableEntryBuilder("biomeAmbience/magmaticCavesParticles", EntryType.BOOL, true).requireRestart().build();

	// WATER COLORS
	public static final ConfigEntry<Boolean> MODIFY_LUKEWARM_WATER = CONFIG.unsyncableEntryBuilder("waterColors/modifyLukewarmWater", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> MODIFY_HOT_WATER = CONFIG.unsyncableEntryBuilder("waterColors/modifyHotWater", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> MODIFY_SNOWY_WATER = CONFIG.unsyncableEntryBuilder("waterColors/modifySnowyWater", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
	public static final ConfigEntry<Boolean> MODIFY_FROZEN_WATER = CONFIG.unsyncableEntryBuilder("waterColors/modifyFrozenWater", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();

	// WIND
	public static final ConfigEntry<Boolean> CLOUD_MOVEMENT = CONFIG.unsyncableEntry("wind/cloudMovement", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WIND_PARTICLES = CONFIG.unsyncableEntry("wind/windParticles", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> WIND_PARTICLE_FREQUENCY = CONFIG.unsyncableEntry("wind/windParticleFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> WIND_PARTICLE_SPAWN_ATTEMPTS = CONFIG.unsyncableEntry("wind/windParticleSpawnAttempts", EntryType.INT, 1);
	public static final ConfigEntry<Boolean> WIND_DISTURBANCE_PARTICLES = CONFIG.unsyncableEntry("wind/windDisturbanceParticles", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> WIND_DISTURBANCE_PARTICLE_FREQUENCY = CONFIG.unsyncableEntry("wind/windDisturbanceParticleFrequency", EntryType.INT, 90);
	public static final ConfigEntry<Integer> WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS = CONFIG.unsyncableEntry("wind/windDisturbanceParticleSpawnAttempts", EntryType.INT, 100);
	public static final ConfigEntry<Boolean> WIND_CLUSTERS = CONFIG.unsyncableEntry("wind/windClusters", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> WIND_CLUSTER_FREQUENCY = CONFIG.unsyncableEntry("wind/windClusterFrequency", EntryType.INT, 20);
	public static final ConfigEntry<Integer> WIND_CLUSTER_MAX_SPAWN_ATTEMPTS = CONFIG.unsyncableEntry("wind/windClusterMaxSpawnAttempts", EntryType.INT, 6);
	public static final ConfigEntry<Integer> PARTICLE_WIND_MOVEMENT = CONFIG.unsyncableEntry("wind/particleWindMovement", EntryType.INT, 100);
	public static final ConfigEntry<Integer> FIREWORK_WIND_MOVEMENT = CONFIG.entry("wind/fireworkWindMovement", EntryType.INT, 100);

	// LEAF PARTICLES
	public static final ConfigEntry<Boolean> USE_WILDER_WILD_FALLING_LEAVES = CONFIG.unsyncableEntry("leafParticles/useWilderWildFallingLeaves", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LEAF_WALKING_PARTICLES = CONFIG.entry("leafParticles/leafWalkingParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> BREAKING_LEAF_PARTICLES = CONFIG.unsyncableEntry("leafParticles/breakingLeafParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> LEAF_LITTER_WALKING_PARTICLES = CONFIG.entry("leafParticles/leafLitterWalkingParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> BREAKING_LEAF_LITTER_PARTICLES = CONFIG.unsyncableEntry("leafParticles/windParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> LEAF_EXPLOSION_PARTICLES = CONFIG.unsyncableEntry("leafParticles/leaf_explosion_particles", EntryType.BOOL, false);
	public static final ConfigEntry<Integer> LEAF_EXPLOSION_VELOCITY = CONFIG.unsyncableEntry("leafParticles/leafExplosionVelocity", EntryType.INT, 75);
	public static final ConfigEntry<Integer> OAK_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/oakFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> SPRUCE_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/spruceFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> BIRCH_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/birchFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> JUNGLE_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/jungleFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> ACACIA_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/acaciaFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> DARK_OAK_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/darkOakFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> PALE_OAK_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/paleOakFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> MANGROVE_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/mangroveFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> CHERRY_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/cherryFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> AZALEA_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/azaleaFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> BAOBAB_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/baobabFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> CYPRESS_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/cypressFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> PALM_FROND_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/palmFrequency", EntryType.INT, 50);
	public static final ConfigEntry<Integer> MAPLE_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/mapleFrequency", EntryType.INT, 100);
	public static final ConfigEntry<Integer> WILLOW_LEAF_FREQUENCY = CONFIG.unsyncableEntry("leafParticles/willowFrequency", EntryType.INT, 50);

	// MUSIC
	public static final ConfigEntry<Boolean> WILDER_FOREST_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderForestMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_LUSH_CAVES_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderLushCavesMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_DRIPSTONE_CAVES_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderDripstoneCavesMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_CHERRY_GROVE_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderCherryGroveMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_GROVE_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderGroveMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_JUNGLE_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderJungleMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_BADLANDS_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderBadlandsMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_DESERT_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderDesertMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_SNOWY_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderSnowyMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_OCEAN_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderOceanMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WILDER_EXTRA_MUSIC = CONFIG.unsyncableEntryBuilder("music/wilderExtraMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DAN_MUSIC = CONFIG.unsyncableEntryBuilder("music/danMusic", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> ANCIENT_CITY_MUSIC = CONFIG.unsyncableEntryBuilder("music/ancientCityMusic", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> DISTORTED_DYING_FOREST_MUSIC = CONFIG.unsyncableEntry("music/distortedDyingForestMusic", EntryType.BOOL, true);

	// VEGETATION COLORS
	public static final ConfigEntry<Boolean> BADLANDS_FOLIAGE_COLOR = CONFIG.unsyncableEntryBuilder("vegetationColors/badlandsFoliage", EntryType.BOOL, true)
		.requireRestart()
		.textSupplier(bool -> WWConstants.text("new." + bool))
		.build();
}
