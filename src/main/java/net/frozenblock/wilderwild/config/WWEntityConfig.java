/*
 * Copyright 2023-2025 FrozenBlock
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
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWEntityConfig {

	public static final Config<WWEntityConfig> INSTANCE = ConfigRegistry.register(
		new JsonConfig<>(
			MOD_ID,
			WWEntityConfig.class,
			WWPreLoadConstants.configPath("entity", true),
			JsonType.JSON5
		) {
			@Override
			public void onSave() throws Exception {
				super.onSave();
				this.onSync(null);
			}

			@Override
			public void onSync(WWEntityConfig syncInstance) {
				var config = this.config();
				WARDEN_SWIMS = config.warden.wardenSwims;
				FIREFLY_SWARMS = config.firefly.fireflySwarm;
				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
					Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION = config.tumbleweed.tumbleweedRotatesToLookDirection;
					Client.ALLAY_KEYFRAME_DANCE = config.allay.keyframeAllayDance;
					Client.WARDEN_SWIM_ANIMATION = config.warden.wardenSwimAnimation;
					Client.WARDEN_CUSTOM_TENDRIL_ANIMATION = config.warden.wardenCustomTendrils;
					Client.WARDEN_IMPROVED_DIM_ANIMATION = config.warden.wardenImprovedDig;
					Client.WARDEN_IMPROVED_EMERGE_ANIMATION = config.warden.wardenImprovedEmerge;
					Client.WARDEN_IMPROVED_SNIFF_ANIMATION = config.warden.wardenBedrockSniff;
					Client.WARDEN_DEATH_ANIMATION = config.warden.wardenDeathAnimation;
					Client.JELLYFISH_PLANE_TENTACLES = config.jellyfish.planeTentacles;
				}
			}
		}
	);

	public static volatile boolean WARDEN_SWIMS = true;
	public static volatile boolean FIREFLY_SWARMS = true;

	public static final class Client {
		public static volatile boolean TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION = false;
		public static volatile boolean ALLAY_KEYFRAME_DANCE = false;
		public static volatile boolean WARDEN_SWIM_ANIMATION = true;
		public static volatile boolean WARDEN_CUSTOM_TENDRIL_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_DIM_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_EMERGE_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_SNIFF_ANIMATION = true;
		public static volatile boolean WARDEN_DEATH_ANIMATION = true;
		public static volatile boolean JELLYFISH_PLANE_TENTACLES = true;
	}

	@CollapsibleObject
	public final LightningConfig lightning = new LightningConfig();

	@CollapsibleObject
	public final AllayConfig allay = new AllayConfig();

	@CollapsibleObject
	public final EnderManConfig enderMan = new EnderManConfig();

	@CollapsibleObject
	public final FireflyConfig firefly = new FireflyConfig();

	@CollapsibleObject
	public final ButterflyConfig butterfly = new ButterflyConfig();

	@CollapsibleObject
	public final JellyfishConfig jellyfish = new JellyfishConfig();

	@CollapsibleObject
	public final CrabConfig crab = new CrabConfig();

	@CollapsibleObject
	public final OstrichConfig ostrich = new OstrichConfig();

	@CollapsibleObject
	public final ScorchedConfig scorched = new ScorchedConfig();

	@CollapsibleObject
	public final MoobloomConfig moobloom = new MoobloomConfig();

	@CollapsibleObject
	public final PenguinConfig penguin = new PenguinConfig();

	@CollapsibleObject
	public final TumbleweedConfig tumbleweed = new TumbleweedConfig();

	@CollapsibleObject
	public final WardenConfig warden = new WardenConfig();

	@EntrySyncData("unpassableRail")
	public boolean unpassableRail = false;

	public static WWEntityConfig get() {
		return get(false);
	}

	public static WWEntityConfig get(boolean real) {
		if (real)
			return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWEntityConfig getWithSync() {
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

		@EntrySyncData("fireflySwarm")
		public boolean fireflySwarm = true;
	}

	public static class ButterflyConfig {
		@EntrySyncData("spawnButterflies")
		public boolean spawnButterflies = true;

		@EntrySyncData("butterflySpawnCap")
		public int butterflySpawnCap = 10;
	}

	public static class JellyfishConfig {
		@EntrySyncData("spawnJellyfish")
		public boolean spawnJellyfish = true;
		@EntrySyncData("jellyfishSpawnCap")
		public int jellyfishSpawnCap = 30;
		@EntrySyncData("jellyfishHiding")
		public boolean jellyfishHiding = true;

		@EntrySyncData(value = "jellyfishTentacles", behavior = SyncBehavior.UNSYNCABLE)
		public int jellyfishTentacles = 8;
		@EntrySyncData(value = "planeTentacles", behavior = SyncBehavior.UNSYNCABLE)
		public boolean planeTentacles = true;
	}

	public static class CrabConfig {
		@EntrySyncData("spawnCrabs")
		public boolean spawnCrabs = true;

		@EntrySyncData("crabSpawnCap")
		public int crabSpawnCap = 6;

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

		@EntrySyncData("scorchedInTrialChambers")
		public boolean scorchedInTrialChambers = true;
	}

	public static class MoobloomConfig {
		@EntrySyncData("spawnMooblooms")
		public boolean spawnMooblooms = true;
	}

	public static class PenguinConfig {
		@EntrySyncData("spawnPenguins")
		public boolean spawnPenguins = true;
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
