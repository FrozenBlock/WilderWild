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

public final class EntityConfig {

	public static final Config<EntityConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			EntityConfig.class,
			configPath("entity", true),
			JsonType.JSON5
		)
	);

	@CollapsibleObject
	public final AllayConfig allay = new AllayConfig();

	@CollapsibleObject
	public final EnderManConfig enderMan = new EnderManConfig();

	@CollapsibleObject
	public final FireflyConfig firefly = new FireflyConfig();

	@CollapsibleObject
	public final JellyfishConfig jellyfish = new JellyfishConfig();

	@CollapsibleObject
	public final CrabConfig crab = new CrabConfig();

	@CollapsibleObject
	public final TumbleweedConfig tumbleweed = new TumbleweedConfig();

	@CollapsibleObject
	public final WardenConfig warden = new WardenConfig();

	public boolean unpassableRail = false;

	public static EntityConfig get() {
		return get(false);
	}

	public static EntityConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static class AllayConfig {
		public boolean keyframeAllayDance = true;
	}

	public static class EnderManConfig {
		public boolean angerLoopSound = true;
		public boolean movingStareSound = true;
	}

	public static class FireflyConfig {
		public int fireflySpawnCap = 56;
	}

	public static class JellyfishConfig {
		public int jellyfishSpawnCap = 30;
		public int jellyfishTentacles = 8;
	}

	public static class CrabConfig {
		public int crabSpawnCap = DefaultEntityConfig.CrabConfig.CRAB_SPAWN_CAP;
	}

	public static class TumbleweedConfig {
		public int tumbleweedSpawnCap = 10;
		public boolean leashedTumbleweed = false;
		public boolean tumbleweedDestroysCrops = true;
		public boolean tumbleweedRotatesToLookDirection = false;
	}

	public static class WardenConfig {
		public boolean wardenAttacksImmediately = true;
		public boolean wardenCustomTendrils = true;
		public boolean wardenBedrockSniff = true;
		public boolean wardenDyingAnimation = true;
		public boolean wardenEmergesFromCommand = false;
		public boolean wardenEmergesFromEgg = false;
		public boolean wardenSwimAnimation = true;
	}
}
