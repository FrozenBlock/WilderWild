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
import net.frozenblock.lib.config.api.annotation.FieldIdentifier;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class ItemConfig {

	public static final Config<ItemConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			ItemConfig.class,
			configPath("item", true),
			JsonType.JSON5,
			null,
			null
		)
	);

	@CollapsibleObject
	public final AncientHornConfig ancientHorn = new AncientHornConfig();

	@CollapsibleObject
	public final ProjectileLandingSoundsConfig projectileLandingSounds = new ProjectileLandingSoundsConfig();

	@FieldIdentifier(identifier = "projectileBreakParticles")
	public boolean projectileBreakParticles = true;

	@FieldIdentifier(identifier = "restrictInstrumentSound")
	public boolean restrictInstrumentSound = true;

	public static ItemConfig get() {
		return get(false);
	}

	public static ItemConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static ItemConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class AncientHornConfig {
		@FieldIdentifier(identifier = "ancientHornCanSummonWarden")
		public boolean ancientHornCanSummonWarden = true;

		@FieldIdentifier(identifier = "ancientHornLifespan")
		public int ancientHornLifespan = AncientHornProjectile.DEFAULT_LIFESPAN;

		@FieldIdentifier(identifier = "ancientHornMobDamage")
		public int ancientHornMobDamage = 22;

		@FieldIdentifier(identifier = "ancientHornPlayerDamage")
		public int ancientHornPlayerDamage = 15;

		@FieldIdentifier(identifier = "ancientHornShattersGlass")
		public boolean ancientHornShattersGlass = false;

		@FieldIdentifier(identifier = "ancientHornSizeMultiplier")
		public float ancientHornSizeMultiplier = 0F;
	}

	public static class ProjectileLandingSoundsConfig {
		@FieldIdentifier(identifier = "snowballLandingSounds")
		public boolean snowballLandingSounds = true;

		@FieldIdentifier(identifier = "eggLandingSounds")
		public boolean eggLandingSounds = true;

		@FieldIdentifier(identifier = "enderPearlLandingSounds")
		public boolean enderPearlLandingSounds = true;

		@FieldIdentifier(identifier = "potionLandingSounds")
		public boolean potionLandingSounds = true;
	}
}
