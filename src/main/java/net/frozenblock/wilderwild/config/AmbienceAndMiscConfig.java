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

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.WilderSharedConstants.configPath;

public final class AmbienceAndMiscConfig {

	public static final Config<AmbienceAndMiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			AmbienceAndMiscConfig.class,
			configPath("misc", true),
			JsonType.JSON5,
			null,
			null
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(AmbienceAndMiscConfig syncInstance) {
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

	@CollapsibleObject
	public final WaterColorConfig waterColors = new WaterColorConfig();

	@CollapsibleObject
	public final Wind wind = new Wind();

	@EntrySyncData("modifyAdvancements")
	public boolean modifyAdvancements = true;

	@EntrySyncData(value = "titleResourcePackEnabled", behavior = SyncBehavior.UNSYNCABLE)
	public boolean titleResourcePackEnabled = true;

	@CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	@CollapsibleObject
	public VegetationColorConfig vegetationColors = new VegetationColorConfig();

	public static AmbienceAndMiscConfig get() {
		return get(false);
	}

	public static AmbienceAndMiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static AmbienceAndMiscConfig getWithSync() {
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

		@EntrySyncData(value = "jellyfishCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean jellyfishCavesAmbience = true;

		@EntrySyncData(value = "jellyfishCavesFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean jellyfishCavesFog = true;

		@EntrySyncData(value = "magmaticCavesAmbience", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesAmbience = true;

		@EntrySyncData(value = "magmaticCavesFog", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesFog = true;

		@EntrySyncData(value = "magmaticCavesParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean magmaticCavesParticles = true;
	}

	public static class BiomeMusicConfig {
		@EntrySyncData(value = "wilderForestMusic", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wilderForestMusic = true;
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
}
