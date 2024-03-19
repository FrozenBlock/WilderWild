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
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.MOD_ID;
import static net.frozenblock.wilderwild.misc.WilderSharedConstants.configPath;

public final class EntityConfig {

	public static final Config<EntityConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			EntityConfig.class,
			configPath("entity", true),
			JsonType.JSON5,
			null,
			null
		)
	);
	@CollapsibleObject
	public final LightningConfig lightning = new LightningConfig();

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
	public final OstrichConfig ostrich = new OstrichConfig();

	@CollapsibleObject
	public final ScorchedConfig scorched = new ScorchedConfig();

	@CollapsibleObject
	public final TumbleweedConfig tumbleweed = new TumbleweedConfig();

	@CollapsibleObject
	public final WardenConfig warden = new WardenConfig();

	@EntrySyncData("unpassableRail")
	public boolean unpassableRail = false;

	public static EntityConfig get() {
		return get(false);
	}

	public static EntityConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static EntityConfig getWithSync() {
		return INSTANCE.configWithSync();
	}

	public static class LightningConfig {
		@EntrySyncData("lightningScorchesSand")
		public boolean lightningScorchesSand = true;

		@EntrySyncData(value = "lightningBlockParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean lightningBlockParticles = true;

		@EntrySyncData(value = "lightningSmokeParticles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean lightningSmokeParticles = true;
	}

	public static class AllayConfig {
		@EntrySyncData(value = "keyframeAllayDance", behavior = SyncBehavior.UNSYNCABLE)
		public boolean keyframeAllayDance = true;
	}

	public static class EnderManConfig {
		@EntrySyncData("angerLoopSound")
		public boolean angerLoopSound = true;

		@EntrySyncData(value = "movingStareSound", behavior = SyncBehavior.UNSYNCABLE)
		public boolean movingStareSound = true;
	}

	public static class FireflyConfig {
		@EntrySyncData("spawnFireflies")
		public boolean spawnFireflies = true;

		@EntrySyncData("fireflySpawnCap")
		public int fireflySpawnCap = 56;
	}

	public static class JellyfishConfig {
		@EntrySyncData("spawnJellyfish")
		public boolean spawnJellyfish = true;
		@EntrySyncData("jellyfishSpawnCap")
		public int jellyfishSpawnCap = 30;

		@EntrySyncData(value = "jellyfishTentacles", behavior = SyncBehavior.UNSYNCABLE)
		public int jellyfishTentacles = 8;
	}

	public static class CrabConfig {
		@EntrySyncData("spawnCrabs")
		public boolean spawnCrabs = true;

		@EntrySyncData("crabSpawnCap")
		public int crabSpawnCap = 25;

		@EntrySyncData("reachAffectsAttack")
		public boolean reachAffectsAttack = false;
	}

	public static class OstrichConfig {
		@EntrySyncData("spawnOstriches")
		public boolean spawnOstriches = true;

		@EntrySyncData("allowAttack")
		public boolean allowAttack = true;
	}

	public static class ScorchedConfig {
		@EntrySyncData("spawnScorched")
		public boolean spawnScorched = true;
	}

	public static class TumbleweedConfig {
		@EntrySyncData("spawnTumbleweed")
		public boolean spawnTumbleweed = true;

		@EntrySyncData("tumbleweedSpawnCap")
		public int tumbleweedSpawnCap = 10;

		@EntrySyncData("leashedTumbleweed")
		public boolean leashedTumbleweed = false;

		@EntrySyncData("tumbleweedDestroysCrops")
		public boolean tumbleweedDestroysCrops = true;

		@EntrySyncData(value = "tumbleweedRotatesToLookDirection", behavior = SyncBehavior.UNSYNCABLE)
		public boolean tumbleweedRotatesToLookDirection = false;
	}

	public static class WardenConfig {
		@EntrySyncData("wardenAttacksImmediately")
		public boolean wardenAttacksImmediately = true;

		@EntrySyncData("wardenSwims")
		public boolean wardenSwims = true;

		@EntrySyncData(value = "wardenSwimAnimation", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wardenSwimAnimation = true;

		@EntrySyncData(value = "wardenCustomTendrils", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wardenCustomTendrils = true;

		@EntrySyncData(value = "wardenImprovedDig", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wardenImprovedDig = true;

		@EntrySyncData(value = "wardenImprovedEmerge", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wardenImprovedEmerge = true;

		@EntrySyncData(value = "wardenBedrockSniff", behavior = SyncBehavior.UNSYNCABLE)
		public boolean wardenBedrockSniff = true;

		@EntrySyncData("wardenDeathAnimation")
		public boolean wardenDeathAnimation = true;

		@EntrySyncData("wardenEmergesFromCommand")
		public boolean wardenEmergesFromCommand = false;

		@EntrySyncData("wardenEmergesFromEgg")
		public boolean wardenEmergesFromEgg = false;

		public boolean swimAndAnimationConfigEnabled() {
			return this.wardenSwims && this.wardenSwimAnimation;
		}
	}
}
