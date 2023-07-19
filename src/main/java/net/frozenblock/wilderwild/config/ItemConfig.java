/*
 * Copyright 2022-2023 FrozenBlock
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
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.config.defaults.DefaultItemConfig;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class ItemConfig {

	private static final Config<ItemConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			ItemConfig.class,
			configPath("item", true),
			true
		)
	);

	@CollapsibleObject
	public final AncientHornConfig ancientHorn = new AncientHornConfig();

	@CollapsibleObject
	public final ProjectileLandingSoundsConfig projectileLandingSounds = new ProjectileLandingSoundsConfig();

	public boolean projectileBreakParticles = DefaultItemConfig.PROJECTILE_BREAK_PARTICLES;

	public boolean restrictInstrumentSound = DefaultItemConfig.RESTRICT_INSTRUMENT_SOUND;

	public static ItemConfig get() {
		return INSTANCE.config();
	}

	public static Config<ItemConfig> getConfigInstance() {
		return INSTANCE;
	}

	public static class AncientHornConfig {
		public boolean ancientHornCanSummonWarden = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
		public int ancientHornLifespan = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN;
		public int ancientHornMobDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
		public int ancientHornPlayerDamage = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
		public boolean ancientHornShattersGlass = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
		public float ancientHornSizeMultiplier = DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SIZE_MULTIPLIER;
	}

	public static class ProjectileLandingSoundsConfig {
		public boolean snowballLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.SNOWBALL_LANDING_SOUNDS;
		public boolean eggLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.EGG_LANDING_SOUNDS;
		public boolean enderPearlLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.ENDER_PEARL_LANDING_SOUNDS;
		public boolean potionLandingSounds = DefaultItemConfig.ProjectileLandingSoundsConfig.POTION_LANDING_SOUNDS;
	}
}
