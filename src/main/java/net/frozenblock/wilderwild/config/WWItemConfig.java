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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.config;

import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.CollapsibleObject;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWItemConfig {

	public static final Config<WWItemConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWItemConfig.class,
			WWPreLoadConstants.configPath("item", true),
			JsonType.JSON5
		)
	);

	@CollapsibleObject
	public final ProjectileLandingSoundsConfig projectileLandingSounds = new ProjectileLandingSoundsConfig();

	@EntrySyncData("projectileBreakParticles")
	public boolean projectileBreakParticles = true;

	@EntrySyncData("restrictInstrumentSound")
	public boolean restrictInstrumentSound = true;

	public static WWItemConfig get() {
		return get(false);
	}

	public static WWItemConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWItemConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class ProjectileLandingSoundsConfig {
		@EntrySyncData("snowballLandingSounds")
		public boolean snowballLandingSounds = true;

		@EntrySyncData("eggLandingSounds")
		public boolean eggLandingSounds = true;

		@EntrySyncData("enderPearlLandingSounds")
		public boolean enderPearlLandingSounds = true;

		@EntrySyncData("potionLandingSounds")
		public boolean potionLandingSounds = true;
	}
}
