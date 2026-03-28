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

import net.frozenblock.lib.config.v2.config.ConfigData;
import net.frozenblock.lib.config.v2.config.ConfigSettings;
import net.frozenblock.lib.config.v2.entry.ConfigEntry;
import net.frozenblock.lib.config.v2.entry.EntryType;
import net.frozenblock.lib.config.v2.registry.ID;
import net.frozenblock.wilderwild.WWConstants;
import static net.frozenblock.wilderwild.WWConstants.text;

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
	public static final ConfigEntry<Integer> FIREFLY_SPAWN_CAP = CONFIG.entryBuilder("firefly/fireflySpawnCap", EntryType.INT, 56).requireRestart().build();
	public static final ConfigEntry<Boolean> FIREFLY_SWARM = CONFIG.entry("firefly/fireflySwarm", EntryType.BOOL, true);

	// BUTTERFLY
	public static final ConfigEntry<Boolean> SPAWN_BUTTERFLIES = CONFIG.entry("butterfly/spawnButterflies", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> BUTTERFLY_SPAWN_CAP = CONFIG.entryBuilder("butterfly/butterflySpawnCap", EntryType.INT, 10).requireRestart().build();

	// JELLYFISH
	public static final ConfigEntry<Boolean> SPAWN_JELLYFISH = CONFIG.entry("jellyfish/spawnJellyfish", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> JELLYFISH_SPAWN_CAP = CONFIG.entryBuilder("jellyfish/jellyfishSpawnCap", EntryType.INT, 30).requireRestart().build();
	public static final ConfigEntry<Boolean> JELLYFISH_HIDING = CONFIG.entry("jellyfish/jellyfishHiding", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> JELLYFISH_TENTACLES = CONFIG.unsyncableEntryBuilder("jellyfish/jellyfishTentacles", EntryType.INT, 8).requireRestart().build();
	public static final ConfigEntry<Boolean> JELLYFISH_PLANE_TENTACLES = CONFIG.unsyncableEntry("jellyfish/planeTentacles", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> JELLYFISH_ORAL_ARM = CONFIG.unsyncableEntry("jellyfish/oralArm", EntryType.BOOL, true);

	// CRAB
	public static final ConfigEntry<Boolean> SPAWN_CRABS = CONFIG.entry("crab/spawnCrabs", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> CRAB_SPAWN_CAP = CONFIG.entryBuilder("crab/crabSpawnCap", EntryType.INT, 6).requireRestart().build();
	public static final ConfigEntry<Boolean> CRAB_REACH_AFFECTS_ATTACK = CONFIG.entryBuilder("crab/reachAffectsAttack", EntryType.BOOL, false).requireRestart().build();
	public static final ConfigEntry<Boolean> CRAB_CLAW_GIVES_REACH = CONFIG.entryBuilder("crab/crabClawGivesReach", EntryType.BOOL, false).requireRestart().build();

	// OSTRICH
	public static final ConfigEntry<Boolean> SPAWN_OSTRICHES = CONFIG.entry("ostrich/spawnOstriches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SPAWN_ZOMBIE_OSTRICHES = CONFIG.entry("ostrich/spawnZombieOstriches", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> OSTRICH_ALLOW_ATTACK = CONFIG.entry("ostrich/allowAttack", EntryType.BOOL, true);

	// SCORCHED
	public static final ConfigEntry<Boolean> SPAWN_SCORCHED = CONFIG.entry("scorched/spawnScorched", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> SCORCHED_IN_TRIAL_CHAMBERS = CONFIG.entryBuilder("scorched/scorchedInTrialChambers", EntryType.BOOL, true).requireRestart().build();

	// MOOBLOOM
	public static final ConfigEntry<Boolean> SPAWN_MOOBLOOMS = CONFIG.entry("moobloom/spawnMooblooms", EntryType.BOOL, true);

	// MOOBLOOM
	public static final ConfigEntry<Boolean> SPAWN_PENGUINS = CONFIG.entry("penguin/spawnPenguins", EntryType.BOOL, true);

	// TUMBLEWEED
	public static final ConfigEntry<Boolean> SPAWN_TUMBLEWEED = CONFIG.entry("tumbleweed/spawnTumbleweed", EntryType.BOOL, true);
	public static final ConfigEntry<Integer> TUMBLEWEED_SPAWN_CAP = CONFIG.entryBuilder("tumbleweed/tumbleweedSpawnCap", EntryType.INT, 10).requireRestart().build();
	public static final ConfigEntry<Boolean> LEASHED_TUMBLEWEED = CONFIG.entry("tumbleweed/leashedTumbleweed", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> TUMBLEWEED_DESTROYS_CROPS = CONFIG.entry("tumbleweed/tumbleweedDestroysCrops", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION = CONFIG.unsyncableEntry("tumbleweed/tumbleweedRotatesToLookDirection", EntryType.BOOL, false);

	// WARDEN
	public static final ConfigEntry<Boolean> WARDEN_ATTACKS_IMMEDIATELY = CONFIG.entry("warden/wardenAttacksImmediately", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_SWIMS = CONFIG.entry("warden/wardenSwims", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_SWIM_ANIMATION = CONFIG.unsyncableEntry("warden/wardenSwimAnimation", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_IMPROVED_TENDRIL_ANIMATION = CONFIG.unsyncableEntry("warden/wardenCustomTendrils", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_IMPROVED_DIG_ANIMATION = CONFIG.unsyncableEntry("warden/wardenImprovedDig", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_IMPROVED_EMERGE_ANIMATION = CONFIG.unsyncableEntryBuilder("warden/wardenImprovedEmerge", EntryType.BOOL, true)
		.textSupplier(bool -> text("improved." + bool))
		.build();
	public static final ConfigEntry<Boolean> WARDEN_BEDROCK_SNIFF_ANIMATION = CONFIG.unsyncableEntryBuilder("warden/wardenBedrockSniff", EntryType.BOOL, true)
		.textSupplier(bool -> text("warden_bedrock_sniff." + bool))
		.build();
	public static final ConfigEntry<Boolean> WARDEN_DEATH_ANIMATION = CONFIG.entry("warden/wardenDeathAnimation", EntryType.BOOL, true);
	public static final ConfigEntry<Boolean> WARDEN_EMERGES_FROM_COMMAND = CONFIG.entry("warden/wardenEmergesFromCommand", EntryType.BOOL, false);
	public static final ConfigEntry<Boolean> WARDEN_EMERGES_FROM_EGG = CONFIG.entry("warden/wardenEmergesFromEgg", EntryType.BOOL, false);

	// VILLAGER
	// TODO: possible non-data-driven way to do trades?
	public static final ConfigEntry<Boolean> WANDERING_WILLOW_TRADE = CONFIG.entryBuilder("villager/wanderingWillowTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_CYPRESS_TRADE = CONFIG.entryBuilder("villager/wanderingCypressTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_BAOBAB_TRADE = CONFIG.entryBuilder("villager/wanderingBaobabTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_PALM_TRADE = CONFIG.entryBuilder("villager/wanderingPalmTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_MAPLE_TRADE = CONFIG.entryBuilder("villager/wanderingMapleTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_CARNATION_TRADE = CONFIG.entryBuilder("villager/wanderingCarnationTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_HIBISCUS_TRADE = CONFIG.entryBuilder("villager/wanderingHibiscusTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_SEEDING_DANDELION_TRADE = CONFIG.entryBuilder("villager/wanderingSeedingDandelionTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_MARIGOLD_TRADE = CONFIG.entryBuilder("villager/wanderingMarigoldTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_PASQUEFLOWER_TRADE = CONFIG.entryBuilder("villager/wanderingPasqueflowerTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_PRICKLY_PEAR_TRADE = CONFIG.entryBuilder("villager/wanderingPricklyPearTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_TUMBLEWEED_TRADE = CONFIG.entryBuilder("villager/wanderingTumbleweedTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_ICICLE_TRADE = CONFIG.entryBuilder("villager/wanderingIcicleTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_BARNACLES_TRADE = CONFIG.entryBuilder("villager/wanderingBarnaclesTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_SEA_ANEMONE_TRADE = CONFIG.entryBuilder("villager/wanderingSeaAnemoneTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_SEA_WHIP_TRADE = CONFIG.entryBuilder("villager/wanderingSeaWhipTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_ALGAE_TRADE = CONFIG.entryBuilder("villager/wanderingAlgaeTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_PLANKTON_TRADE = CONFIG.entryBuilder("villager/wanderingPlanktonTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_AUBURN_MOSS_TRADE = CONFIG.entryBuilder("villager/wanderingAuburnMossTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> WANDERING_GEYSER_TRADE = CONFIG.entryBuilder("villager/wanderingGeyserTrade", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FISHERMAN_DESERT_PALM_BOAT = CONFIG.entryBuilder("villager/fishermanDesertPalmBoat", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FISHERMAN_CRAB_FOR_EMERALDS = CONFIG.entryBuilder("villager/fishermanCrabForEmeralds", EntryType.BOOL, true).requireRestart().build();
	public static final ConfigEntry<Boolean> FISHERMAN_JELLYFISH_FOR_EMERALDS = CONFIG.entryBuilder("villager/fishermanJellyfishForEmeralds", EntryType.BOOL, true).requireRestart().build();
}
