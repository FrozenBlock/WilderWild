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
import net.frozenblock.wilderwild.config.defaults.DefaultEntityConfig;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class EntityConfig {

	private static final Config<EntityConfig> INSTANCE = ConfigRegistry.register(
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

	public boolean unpassableRail = DefaultEntityConfig.UNPASSABLE_RAIL;

	public static EntityConfig get() {
		return INSTANCE.config();
	}

	public static Config<EntityConfig> getConfigInstance() {
		return INSTANCE;
	}

	public static class AllayConfig {
		public boolean keyframeAllayDance = DefaultEntityConfig.AllayConfig.KEYFRAME_ALLAY_DANCE;
	}

	public static class EnderManConfig {
		public boolean angerLoopSound = DefaultEntityConfig.EnderManConfig.ANGER_LOOP_SOUND;
		public boolean movingStareSound = DefaultEntityConfig.EnderManConfig.MOVING_STARE_SOUND;
	}

	public static class FireflyConfig {
		public int fireflySpawnCap = DefaultEntityConfig.FireflyConfig.FIREFLY_SPAWN_CAP;
	}

	public static class JellyfishConfig {
		public int jellyfishSpawnCap = DefaultEntityConfig.JellyfishConfig.JELLYFISH_SPAWN_CAP;
		public int jellyfishTentacles = DefaultEntityConfig.JellyfishConfig.JELLYFISH_TENTACLES;
	}

	public static class CrabConfig {
		public int crabSpawnCap = DefaultEntityConfig.CrabConfig.CRAB_SPAWN_CAP;
	}

	public static class TumbleweedConfig {
		public int tumbleweedSpawnCap = DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_SPAWN_CAP;
		public boolean leashedTumbleweed = DefaultEntityConfig.TumbleweedConfig.LEASHED_TUMBLEWEED;
		public boolean tumbleweedDestroysCrops = DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_DESTROYS_CROPS;
		public boolean tumbleweedRotatesToLookDirection = DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION;
	}

	public static class WardenConfig {
		public boolean wardenAttacksImmediately = DefaultEntityConfig.WardenConfig.WARDEN_ATTACKS_IMMEDIATELY;
		public boolean wardenCustomTendrils = DefaultEntityConfig.WardenConfig.WARDEN_CUSTOM_TENDRILS;
		public boolean wardenBedrockSniff = DefaultEntityConfig.WardenConfig.WARDEN_BEDROCK_SNIFF;
		public boolean wardenDyingAnimation = DefaultEntityConfig.WardenConfig.WARDEN_DYING_ANIMATION;
		public boolean wardenEmergesFromCommand = DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_COMMAND;
		public boolean wardenEmergesFromEgg = DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_EGG;
		public boolean wardenSwimAnimation = DefaultEntityConfig.WardenConfig.WARDEN_SWIM_ANIMATION;
	}
}
