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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWAmbienceAndMiscConfig {

	public static final Config<WWAmbienceAndMiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWAmbienceAndMiscConfig.class,
			WWPreLoadConstants.configPath("misc", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WWAmbienceAndMiscConfig syncInstance) {
				var config = this.config();
				CLOUD_MOVEMENT = config.wind.cloudMovement;
				WIND_PARTICLES = config.wind.windParticles;
				WIND_PARTICLE_FREQUENCY = config.wind.windParticleFrequency;
				WIND_PARTICLE_SPAWN_ATTEMPTS = config.wind.windParticleSpawnAttempts;
				WIND_DISTURBANCE_PARTICLES = config.wind.windDisturbanceParticles;
				WIND_DISTURBANCE_PARTICLE_FREQUENCY = config.wind.windDisturbanceParticleFrequency;
				WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS = config.wind.windDisturbanceParticleSpawnAttempts;
				PARTICLE_WIND_MOVEMENT = config.wind.particleWindMovement;
				FIREWORK_WIND_MOVEMENT = config.wind.fireworkWindMovement;

				Client.OAK_LEAF_FREQUENCY = config.leafParticles.oakFrequency / 100D;
				Client.SPRUCE_LEAF_FREQUENCY = config.leafParticles.spruceFrequency / 100D;
				Client.BIRCH_LEAF_FREQUENCY = config.leafParticles.birchFrequency / 100D;
				Client.JUNGLE_LEAF_FREQUENCY = config.leafParticles.jungleFrequency / 100D;
				Client.ACACIA_LEAF_FREQUENCY = config.leafParticles.acaciaFrequency / 100D;
				Client.DARK_OAK_LEAF_FREQUENCY = config.leafParticles.darkOakFrequency / 100D;
				Client.MANGROVE_LEAF_FREQUENCY = config.leafParticles.mangroveFrequency / 100D;
				Client.CHERRY_LEAF_FREQUENCY = config.leafParticles.cherryFrequency / 100D;
				Client.AZALEA_LEAF_FREQUENCY = config.leafParticles.azaleaFrequency / 100D;
				Client.FLOWERING_AZALEA_LEAF_FREQUENCY = config.leafParticles.floweringAzaleaFrequency / 100D;
				Client.BAOBAB_LEAF_FREQUENCY = config.leafParticles.baobabFrequency / 100D;
				Client.CYPRESS_LEAF_FREQUENCY = config.leafParticles.cypressFrequency / 100D;
				Client.PALM_FROND_FREQUENCY = config.leafParticles.palmFrequency / 100D;
				Client.MAPLE_LEAF_FREQUENCY = config.leafParticles.mapleFrequency / 100D;
				Client.WILLOW_LEAF_FREQUENCY = config.leafParticles.willowFrequency / 100D;

				Client.DISTORTED_DYING_FOREST_MUSIC = config.music.distortedDyingForestMusic;
			}
		}
	);

	public static double getWindParticleFrequency() {
		return ((double) WIND_PARTICLE_FREQUENCY) * 0.01D;
	}

	public static double getWindDisturbanceParticleFrequency() {
		return ((double) WIND_DISTURBANCE_PARTICLE_FREQUENCY) * 0.01D;
	}

	public static double getParticleWindIntensity() {
		return ((double) PARTICLE_WIND_MOVEMENT) * 0.01D;
	}

	public static double getFireworkWindIntensity() {
		return ((double) FIREWORK_WIND_MOVEMENT) * 0.01D;
	}

	public static volatile boolean CLOUD_MOVEMENT = true;
	public static volatile boolean WIND_PARTICLES = true;
	public static volatile int WIND_PARTICLE_FREQUENCY = 50;
	public static volatile int WIND_PARTICLE_SPAWN_ATTEMPTS = 1;
	public static volatile boolean WIND_DISTURBANCE_PARTICLES = true;
	public static volatile int WIND_DISTURBANCE_PARTICLE_FREQUENCY = 90;
	public static volatile int WIND_DISTURBANCE_PARTICLE_SPAWN_ATTEMPTS = 100;
	public static volatile int PARTICLE_WIND_MOVEMENT = 100;
	public static volatile int FIREWORK_WIND_MOVEMENT = 100;

	public static class Client {
		public static volatile double OAK_LEAF_FREQUENCY = 1D;
		public static volatile double SPRUCE_LEAF_FREQUENCY = 1D;
		public static volatile double BIRCH_LEAF_FREQUENCY = 1D;
		public static volatile double JUNGLE_LEAF_FREQUENCY = 1D;
		public static volatile double ACACIA_LEAF_FREQUENCY = 1D;
		public static volatile double DARK_OAK_LEAF_FREQUENCY = 1D;
		public static volatile double MANGROVE_LEAF_FREQUENCY = 1D;
		public static volatile double CHERRY_LEAF_FREQUENCY = 1D;
		public static volatile double AZALEA_LEAF_FREQUENCY = 1D;
		public static volatile double FLOWERING_AZALEA_LEAF_FREQUENCY = 1D;
		public static volatile double BAOBAB_LEAF_FREQUENCY = 1D;
		public static volatile double CYPRESS_LEAF_FREQUENCY = 1D;
		public static volatile double PALM_FROND_FREQUENCY = 1D;
		public static volatile double MAPLE_LEAF_FREQUENCY = 1D;
		public static volatile double WILLOW_LEAF_FREQUENCY = 1D;

		public static volatile boolean DISTORTED_DYING_FOREST_MUSIC = true;
	}

	@CollapsibleObject
	public final WaterColorConfig waterColors = new WaterColorConfig();

	@CollapsibleObject
	public final Wind wind = new Wind();

	@CollapsibleObject
	public final LeafParticles leafParticles = new LeafParticles();

	@EntrySyncData("modifyAdvancements")
	public boolean modifyAdvancements = true;

	@CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@CollapsibleObject
	public MusicConfig music = new MusicConfig();

	@CollapsibleObject
	public VegetationColorConfig vegetationColors = new VegetationColorConfig();

	public static WWAmbienceAndMiscConfig get() {
		return get(false);
	}

	public static WWAmbienceAndMiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWAmbienceAndMiscConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class BiomeAmbienceConfig {
		@EntrySyncData(value = "deepDarkAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean deepDarkAmbience = true;

		@EntrySyncData(value = "deepDarkFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean deepDarkFog = true;

		@EntrySyncData(value = "dripstoneCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean dripstoneCavesAmbience = true;

		@EntrySyncData(value = "lushCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean lushCavesAmbience = true;

		@EntrySyncData(value = "frozenCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean frozenCavesAmbience = true;

		@EntrySyncData(value = "frozenCavesFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean frozenCavesFog = false;

		@EntrySyncData(value = "mesogleaCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean mesogleaCavesAmbience = true;

		@EntrySyncData(value = "mesogleaCavesFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean mesogleaCavesFog = true;

		@EntrySyncData(value = "magmaticCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesAmbience = true;

		@EntrySyncData(value = "magmaticCavesFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesFog = true;

		@EntrySyncData(value = "magmaticCavesParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesParticles = true;
	}

	public static class MusicConfig {
		@EntrySyncData(value = "wilderForestMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderForestMusic = true;

		@EntrySyncData(value = "wilderLushCavesMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderLushCavesMusic = true;

		@EntrySyncData(value = "wilderDripstoneCavesMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderDripstoneCavesMusic = true;

		@EntrySyncData(value = "wilderCherryGroveMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderCherryGroveMusic = true;

		@EntrySyncData(value = "wilderGroveMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderGroveMusic = true;

		@EntrySyncData(value = "wilderJungleMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderJungleMusic = true;

		@EntrySyncData(value = "wilderSnowyMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderSnowyMusic = true;

		@EntrySyncData(value = "wilderOceanMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderOceanMusic = true;

		@EntrySyncData(value = "ancientCityMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean ancientCityMusic = true;

		@EntrySyncData(value = "distortedDyingForestMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean distortedDyingForestMusic = true;
	}

	public static class VegetationColorConfig {
		@EntrySyncData(value = "badlandsFoliage", behavior = SyncBehavior.UNSYNCABLE)
		public boolean badlandsFoliage = true;
	}

	public static class WaterColorConfig {
		@EntrySyncData(value = "modifyLukewarmWater", behavior = SyncBehavior.UNSYNCABLE)
		public boolean modifyLukewarmWater = true;

		@EntrySyncData(value = "modifyHotWater", behavior = SyncBehavior.UNSYNCABLE)
		public boolean modifyHotWater = true;

		@EntrySyncData(value = "modifySnowywater", behavior = SyncBehavior.UNSYNCABLE)
		public boolean modifySnowyWater = true;

		@EntrySyncData(value = "modifyFrozenWater", behavior = SyncBehavior.UNSYNCABLE)
		public boolean modifyFrozenWater = true;
	}

	public static class Wind {
		@EntrySyncData(value = "cloudMovement", behavior = SyncBehavior.UNSYNCABLE)
		public boolean cloudMovement = true;

		@EntrySyncData(value = "windParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean windParticles = true;

		@EntrySyncData(value = "windParticleFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int windParticleFrequency = 50;

		@EntrySyncData(value = "windParticleSpawnAttempts", behavior = SyncBehavior.UNSYNCABLE)
		public int windParticleSpawnAttempts = 1;

		@EntrySyncData(value = "windDisturbanceParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean windDisturbanceParticles = true;

		@EntrySyncData(value = "windDisturbanceParticleFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int windDisturbanceParticleFrequency = 90;

		@EntrySyncData(value = "windDisturbanceParticleSpawnAttempts", behavior = SyncBehavior.UNSYNCABLE)
		public int windDisturbanceParticleSpawnAttempts = 100;

		@EntrySyncData(value = "particleWindMovement", behavior = SyncBehavior.UNSYNCABLE)
		public int particleWindMovement = 100;

		@EntrySyncData("fireworkWindMovement")
		public int fireworkWindMovement = 100;
	}

	public static class LeafParticles {
		@EntrySyncData(value = "oakFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int oakFrequency = 50;

		@EntrySyncData(value = "spruceFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int spruceFrequency = 50;

		@EntrySyncData(value = "birchFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int birchFrequency = 50;

		@EntrySyncData(value = "jungleFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int jungleFrequency = 50;

		@EntrySyncData(value = "acaciaFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int acaciaFrequency = 50;

		@EntrySyncData(value = "darkOakFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int darkOakFrequency = 50;

		@EntrySyncData(value = "mangroveFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int mangroveFrequency = 50;

		@EntrySyncData(value = "cherryFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int cherryFrequency = 50;

		@EntrySyncData(value = "azaleaFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int azaleaFrequency = 50;

		@EntrySyncData(value = "floweringAzaleaFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int floweringAzaleaFrequency = 50;

		@EntrySyncData(value = "baobabFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int baobabFrequency = 50;

		@EntrySyncData(value = "cypressFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int cypressFrequency = 50;

		@EntrySyncData(value = "palmFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int palmFrequency = 50;

		@EntrySyncData(value = "mapleFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int mapleFrequency = 100;

		@EntrySyncData(value = "willowFrequency", behavior = SyncBehavior.UNSYNCABLE)
		public int willowFrequency = 50;
	}
}
