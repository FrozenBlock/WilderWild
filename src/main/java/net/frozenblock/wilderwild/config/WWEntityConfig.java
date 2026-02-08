/*
 * Copyright 2025-2026 FrozenBlock
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

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.api.instance.json.JsonConfig;
import net.frozenblock.lib.config.api.instance.json.JsonType;
import net.frozenblock.lib.config.api.registry.ConfigRegistry;
import net.frozenblock.lib.config.api.sync.SyncBehavior;
import net.frozenblock.lib.config.api.sync.annotation.EntrySyncData;
import static net.frozenblock.wilderwild.WWConstants.MOD_ID;
import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.WWPreLoadConstants;

public final class WWEntityConfig {
	public static final ConfigData<?> CONFIG = ConfigData.createAndRegister(ID.of(WWConstants.id("entity")), ConfigSettings.JSON5);

	public static final ConfigEntry<Boolean> UNPASSABLE_RAIL = CONFIG.entryBuilder("unpassableRail", EntryType.BOOL, false).requireRestart().build();

	// LIGHTNING
	public static final ConfigEntry<Boolean> LIGHTNING_SCORCHES_SAND = CONFIG.entry("lightning/lightningScorchesSand", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LIGHTNING_BLOCK_PARTICLES = CONFIG.unsyncableEntry("lightning/lightningBlockParticles", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> LIGHTNING_SMOKE_PARTICLES = CONFIG.unsyncableEntry("lightning/lightningSmokeParticles", EntryType.BOOL, true);

	// ALLAY
	public static final ConfigEntry<Boolean> ALLAY_KEYFRAME_DANCE = CONFIG.entry("allay/keyframeAllayDance", EntryType.BOOL, true);

	// ENDERMAN
	public static final ConfigEntry<Boolean> ENDERMAN_ANGER_LOOP_SOUND = CONFIG.entry("enderMan/angerLoopSound", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> ENDERMAN_MOVING_STARE_SOUND = CONFIG.unsyncableEntry("enderMan/movingStareSound", EntryType.BOOL, true);

	// FIREFLY
	public static final ConfigEntry<Boolean> SPAWN_FIREFLY_PARTICLES = CONFIG.unsyncableEntry("firefly/spawnFireflyParticles", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> SPAWN_FIREFLIES = CONFIG.entry("firefly/spawnFireflies", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FIREFLIES_NEED_BUSH = CONFIG.entry("firefly/firefliesNeedBush", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> FIREFLY_SPAWN_CAP = CONFIG.entry("firefly/fireflySpawnCap", EntryType.INT, 56);
	public static final ConfigEntry<Boolean> FIREFLY_SWARM = CONFIG.entry("firefly/fireflySwarm", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> FIREFLY_SWARMS_BUSH = CONFIG.entry("firefly/fireflySwarmsBush", EntryType.BOOL, true);

	// BUTTERFLY
	public static final ConfigEntry<Boolean> SPAWN_BUTTERFLIES = CONFIG.entry("butterfly/spawnButterflies", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> BUTTERFLY_SPAWN_CAP = CONFIG.entry("butterfly/butterflySpawnCap", EntryType.INT, 10);

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
				final var config = this.config();
				WARDEN_SWIMS = config.warden.wardenSwims;
				REACH_AFFECTS_ATTACK = config.crab.reachAffectsAttack;
				CRAB_CLAW_GIVES_REACH = config.crab.crabClawGivesReach;
				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
					Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION = config.tumbleweed.tumbleweedRotatesToLookDirection;
					Client.WARDEN_SWIM_ANIMATION = config.warden.wardenSwimAnimation;
					Client.WARDEN_CUSTOM_TENDRIL_ANIMATION = config.warden.wardenCustomTendrils;
					Client.WARDEN_IMPROVED_DIG_ANIMATION = config.warden.wardenImprovedDig;
					Client.WARDEN_IMPROVED_EMERGE_ANIMATION = config.warden.wardenImprovedEmerge;
					Client.WARDEN_IMPROVED_SNIFF_ANIMATION = config.warden.wardenBedrockSniff;
					Client.WARDEN_DEATH_ANIMATION = config.warden.wardenDeathAnimation;
					Client.JELLYFISH_PLANE_TENTACLES = config.jellyfish.planeTentacles;
					Client.JELLYFISH_ORAL_ARM = config.jellyfish.oralArm;
				}
			}
		}
	);

	public static volatile boolean WARDEN_SWIMS = true;
	public static volatile boolean REACH_AFFECTS_ATTACK = false;
	public static volatile boolean CRAB_CLAW_GIVES_REACH = false;

	public static final class Client {
		public static volatile boolean TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION = false;
		public static volatile boolean WARDEN_SWIM_ANIMATION = true;
		public static volatile boolean WARDEN_CUSTOM_TENDRIL_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_DIG_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_EMERGE_ANIMATION = true;
		public static volatile boolean WARDEN_IMPROVED_SNIFF_ANIMATION = true;
		public static volatile boolean WARDEN_DEATH_ANIMATION = true;
		public static volatile boolean JELLYFISH_PLANE_TENTACLES = true;
		public static volatile boolean JELLYFISH_ORAL_ARM = true;
	}

	public final JellyfishConfig jellyfish = new JellyfishConfig();

	public final CrabConfig crab = new CrabConfig();

	public final OstrichConfig ostrich = new OstrichConfig();

	public final ScorchedConfig scorched = new ScorchedConfig();

	public final MoobloomConfig moobloom = new MoobloomConfig();

	public final PenguinConfig penguin = new PenguinConfig();

	public final TumbleweedConfig tumbleweed = new TumbleweedConfig();

	public final WardenConfig warden = new WardenConfig();

	public final VillagerConfig villager = new VillagerConfig();

	public static WWEntityConfig get() {
		return get(false);
	}

	public static WWEntityConfig get(boolean real) {
		if (real) return INSTANCE.instance();
		return INSTANCE.config();
	}

	public static WWEntityConfig getWithSync() {
		return INSTANCE.configWithSync();
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
		@EntrySyncData(value = "oralArm", behavior = SyncBehavior.UNSYNCABLE)
		public boolean oralArm = true;
	}

	public static class CrabConfig {
		@EntrySyncData("spawnCrabs")
		public boolean spawnCrabs = true;

		@EntrySyncData("crabSpawnCap")
		public int crabSpawnCap = 6;

		@EntrySyncData("reachAffectsAttack")
		public boolean reachAffectsAttack = false;

		@EntrySyncData("crabClawGivesReach")
		public boolean crabClawGivesReach = false;
	}

	public static class OstrichConfig {
		@EntrySyncData("spawnOstriches")
		public boolean spawnOstriches = true;

		@EntrySyncData("spawnZombieOstriches")
		public boolean spawnZombieOstriches = true;

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

	public static class VillagerConfig {
		@EntrySyncData("wanderingWillowTrade")
		public boolean wanderingWillowTrade = true;
		@EntrySyncData("wanderingCypressTrade")
		public boolean wanderingCypressTrade = true;
		@EntrySyncData("wanderingBaobabTrade")
		public boolean wanderingBaobabTrade = true;
		@EntrySyncData("wanderingPalmTrade")
		public boolean wanderingPalmTrade = true;
		@EntrySyncData("wanderingMapleTrade")
		public boolean wanderingMapleTrade = true;
		@EntrySyncData("wanderingCarnationTrade")
		public boolean wanderingCarnationTrade = true;
		@EntrySyncData("wanderingHibiscusTrade")
		public boolean wanderingHibiscusTrade = true;
		@EntrySyncData("wanderingSeedingDandelionTrade")
		public boolean wanderingSeedingDandelionTrade = true;
		@EntrySyncData("wanderingMarigoldTrade")
		public boolean wanderingMarigoldTrade = true;
		@EntrySyncData("wanderingPasqueflowerTrade")
		public boolean wanderingPasqueflowerTrade = true;
		@EntrySyncData("wanderingTumbleweedTrade")
		public boolean wanderingTumbleweedTrade = true;
		@EntrySyncData("wanderingPricklyPearTrade")
		public boolean wanderingPricklyPearTrade = true;
		@EntrySyncData("wanderingIcicleTrade")
		public boolean wanderingIcicleTrade = true;
		@EntrySyncData("wanderingBarnaclesTrade")
		public boolean wanderingBarnaclesTrade = true;
		@EntrySyncData("wanderingSeaAnemoneTrade")
		public boolean wanderingSeaAnemoneTrade = true;
		@EntrySyncData("wanderingSeaWhipTrade")
		public boolean wanderingSeaWhipTrade = true;
		@EntrySyncData("wanderingAlgaeTrade")
		public boolean wanderingAlgaeTrade = true;
		@EntrySyncData("wanderingPlanktonTrade")
		public boolean wanderingPlanktonTrade = true;
		@EntrySyncData("wanderingAuburnMossTrade")
		public boolean wanderingAuburnMossTrade = true;
		@EntrySyncData("wanderingGeyserTrade")
		public boolean wanderingGeyserTrade = true;

		@EntrySyncData("fishermanDesertPalmBoat")
		public boolean fishermanDesertPalmBoat = true;
		@EntrySyncData("fishermanCrabForEmeralds")
		public boolean fishermanCrabForEmeralds = true;
		@EntrySyncData("fishermanJellyfishForEmeralds")
		public boolean fishermanJellyfishForEmeralds = true;
	}
}
