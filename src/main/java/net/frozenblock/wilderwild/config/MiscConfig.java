/*
 * Copyright 2023 FrozenBlock
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
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class MiscConfig {

	public static final Config<MiscConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			MiscConfig.class,
			configPath("misc", true),
			JsonType.JSON5
		)
	);

	public boolean cloudMovement = true;

	public int particleWindMovement = 100;

	@CollapsibleObject
	public BiomeAmbienceConfig biomeAmbience = new BiomeAmbienceConfig();

	@CollapsibleObject
	public BiomeMusicConfig biomeMusic = new BiomeMusicConfig();

	public static MiscConfig get() {
		return get(false);
	}

	public static MiscConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public double getParticleWindIntensity() {
		return ((double) this.particleWindMovement) * 0.01;
	}

	public static class BiomeAmbienceConfig {
		public boolean deepDarkAmbience = true;
		public boolean dripstoneCavesAmbience = true;
		public boolean lushCavesAmbience = true;
	}

	public static class BiomeMusicConfig {
		public boolean wilderForestMusic = true;
	}
}
