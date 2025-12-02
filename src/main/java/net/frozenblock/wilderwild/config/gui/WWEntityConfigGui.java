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
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.api.instance.Config;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.text;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWEntityConfig;

@Environment(EnvType.CLIENT)
public final class WWEntityConfigGui {

	private WWEntityConfigGui() {
		throw new UnsupportedOperationException("WWEntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		final var config = WWEntityConfig.get(true);
		final var modifiedConfig = WWEntityConfig.getWithSync();
		final Class<? extends WWEntityConfig> clazz = config.getClass();
		final Config<?> configInstance = WWEntityConfig.INSTANCE;
		final var defaultConfig = WWEntityConfig.INSTANCE.defaultInstance();
		final var lightning = config.lightning;
		final var modifiedLightning = modifiedConfig.lightning;
		final var allay = config.allay;
		final var modifiedAllay = modifiedConfig.allay;
		final var enderMan = config.enderMan;
		final var modifiedEnderMan = modifiedConfig.enderMan;
		final var firefly = config.firefly;
		final var modifiedFirefly = modifiedConfig.firefly;
		final var butterfly = config.butterfly;
		final var modifiedButterfly = modifiedConfig.butterfly;
		final var jellyfish = config.jellyfish;
		final var modifiedJellyfish = modifiedConfig.jellyfish;
		final var crab = config.crab;
		final var modifiedCrab = modifiedConfig.crab;
		final var ostrich = config.ostrich;
		final var modifiedOstrich = modifiedConfig.ostrich;
		final var scorched = config.scorched;
		final var modifiedScorched = modifiedConfig.scorched;
		final var moobloom = config.moobloom;
		final var modifiedMoobloom = modifiedConfig.moobloom;
		final var penguin = config.penguin;
		final var modifiedPenguin = modifiedConfig.penguin;
		final var tumbleweed = config.tumbleweed;
		final var modifiedTumbleweed = modifiedConfig.tumbleweed;
		final var warden = config.warden;
		final var modifiedWarden = modifiedConfig.warden;
		final var villager = config.villager;
		final var modifiedVillager = modifiedConfig.villager;

		var unpassableRail = category.addEntry(
			FrozenClothConfig.syncedEntry(
				builder.startBooleanToggle(text("unpassable_rail"), modifiedConfig.unpassableRail)
					.setDefaultValue(defaultConfig.unpassableRail)
					.setSaveConsumer(newValue -> config.unpassableRail = newValue)
					.setTooltip(tooltip("unpassable_rail"))
					.requireRestart()
					.build(),
				clazz,
				"unpassableRail",
				configInstance
			)
		);

		var lightningScorchesSand = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("lightning_scorches_sand"), modifiedLightning.lightningScorchesSand)
				.setDefaultValue(defaultConfig.lightning.lightningScorchesSand)
				.setSaveConsumer(newValue -> lightning.lightningScorchesSand = newValue)
				.setTooltip(tooltip("lightning_scorches_sand"))
				.build(),
			lightning.getClass(),
			"lightningScorchesSand",
			configInstance
		);

		var lightningBlockParticles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("lightning_block_particles"), modifiedLightning.lightningBlockParticles)
				.setDefaultValue(defaultConfig.lightning.lightningBlockParticles)
				.setSaveConsumer(newValue -> lightning.lightningBlockParticles = newValue)
				.setTooltip(tooltip("lightning_block_particles"))
				.build(),
			lightning.getClass(),
			"lightningBlockParticles",
			configInstance
		);

		var lightningSmokeParticles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("lightning_smoke_particles"), modifiedLightning.lightningSmokeParticles)
				.setDefaultValue(defaultConfig.lightning.lightningSmokeParticles)
				.setSaveConsumer(newValue -> lightning.lightningSmokeParticles = newValue)
				.setTooltip(tooltip("lightning_smoke_particles"))
				.build(),
			lightning.getClass(),
			"lightningSmokeParticles",
			configInstance
		);

		var lightningCategory = FrozenClothConfig.createSubCategory(builder, category, text("lightning"),
			false,
			tooltip("lightning"),
			lightningScorchesSand, lightningBlockParticles, lightningSmokeParticles
		);

		var keyframeAllayDance = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("keyframe_allay_dance"), modifiedAllay.keyframeAllayDance)
				.setDefaultValue(defaultConfig.allay.keyframeAllayDance)
				.setSaveConsumer(newValue -> allay.keyframeAllayDance = newValue)
				.setTooltip(tooltip("keyframe_allay_dance"))
				.build(),
			allay.getClass(),
			"keyframeAllayDance",
			configInstance
		);

		var allayCategory = FrozenClothConfig.createSubCategory(builder, category, text("allay"),
			false,
			tooltip("allay"),
			keyframeAllayDance
		);

		var angerLoopSound = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("anger_loop_sound"), modifiedEnderMan.angerLoopSound)
				.setDefaultValue(defaultConfig.enderMan.angerLoopSound)
				.setSaveConsumer(newValue -> enderMan.angerLoopSound = newValue)
				.setTooltip(tooltip("anger_loop_sound"))
				.build(),
			enderMan.getClass(),
			"angerLoopSound",
			configInstance
		);

		var movingStareSound = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("moving_stare_sound"), modifiedEnderMan.movingStareSound)
				.setDefaultValue(defaultConfig.enderMan.movingStareSound)
				.setSaveConsumer(newValue -> enderMan.movingStareSound = newValue)
				.setTooltip(tooltip("moving_stare_sound"))
				.build(),
			enderMan.getClass(),
			"movingStareSound",
			configInstance
		);

		var enderManCategory = FrozenClothConfig.createSubCategory(builder, category, text("enderman"),
			false,
			tooltip("enderman"),
			angerLoopSound, movingStareSound
		);

		var spawnFireflyParticles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_firefly_particles"), modifiedFirefly.spawnFireflyParticles)
				.setDefaultValue(defaultConfig.firefly.spawnFireflyParticles)
				.setSaveConsumer(newValue -> firefly.spawnFireflyParticles = newValue)
				.setTooltip(tooltip("spawn_firefly_particles"))
				.build(),
			firefly.getClass(),
			"spawnFireflyParticles",
			configInstance
		);

		var spawnFireflies = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_fireflies"), modifiedFirefly.spawnFireflies)
				.setDefaultValue(defaultConfig.firefly.spawnFireflies)
				.setSaveConsumer(newValue -> firefly.spawnFireflies = newValue)
				.setTooltip(tooltip("spawn_fireflies"))
				.build(),
			firefly.getClass(),
			"spawnFireflies",
			configInstance
		);

		var firefliesNeedBush = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("fireflies_need_bush"), modifiedFirefly.firefliesNeedBush)
				.setDefaultValue(defaultConfig.firefly.firefliesNeedBush)
				.setSaveConsumer(newValue -> firefly.firefliesNeedBush = newValue)
				.setTooltip(tooltip("fireflies_need_bush"))
				.requireRestart()
				.build(),
			firefly.getClass(),
			"firefliesNeedBush",
			configInstance
		);

		var fireflySpawnCap = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("firefly_spawn_cap"), modifiedFirefly.fireflySpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.firefly.fireflySpawnCap)
				.setSaveConsumer(newValue -> firefly.fireflySpawnCap = newValue)
				.setTooltip(tooltip("firefly_spawn_cap"))
				.requireRestart()
				.build(),
			firefly.getClass(),
			"fireflySpawnCap",
			configInstance
		);

		var fireflySwarm = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("firefly_swarm"), modifiedFirefly.fireflySwarm)
				.setDefaultValue(defaultConfig.firefly.fireflySwarm)
				.setSaveConsumer(newValue -> firefly.fireflySwarm = newValue)
				.setTooltip(tooltip("firefly_swarm"))
				.build(),
			firefly.getClass(),
			"fireflySwarm",
			configInstance
		);

		var fireflySwarmsBush = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("firefly_swarms_bush"), modifiedFirefly.fireflySwarmsBush)
				.setDefaultValue(defaultConfig.firefly.fireflySwarmsBush)
				.setSaveConsumer(newValue -> firefly.fireflySwarmsBush = newValue)
				.setTooltip(tooltip("firefly_swarms_bush"))
				.build(),
			firefly.getClass(),
			"fireflySwarmsBush",
			configInstance
		);

		var fireflyCategory = FrozenClothConfig.createSubCategory(builder, category, text("firefly"),
			false,
			tooltip("firefly"),
			spawnFireflyParticles, spawnFireflies, firefliesNeedBush, fireflySpawnCap, fireflySwarm, fireflySwarmsBush
		);

		var spawnButterflies = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_butterflies"), modifiedButterfly.spawnButterflies)
				.setDefaultValue(defaultConfig.butterfly.spawnButterflies)
				.setSaveConsumer(newValue -> butterfly.spawnButterflies = newValue)
				.setTooltip(tooltip("spawn_butterflies"))
				.build(),
			butterfly.getClass(),
			"spawnButterflies",
			configInstance
		);

		var butterflySpawnCap = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("butterfly_spawn_cap"), modifiedButterfly.butterflySpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.butterfly.butterflySpawnCap)
				.setSaveConsumer(newValue -> butterfly.butterflySpawnCap = newValue)
				.setTooltip(tooltip("butterfly_spawn_cap"))
				.requireRestart()
				.build(),
			butterfly.getClass(),
			"butterflySpawnCap",
			configInstance
		);

		var butterflyCategory = FrozenClothConfig.createSubCategory(builder, category, text("butterfly"),
			false,
			tooltip("butterfly"),
			spawnButterflies, butterflySpawnCap
		);

		var spawnJellyfish = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_jellyfish"), modifiedJellyfish.spawnJellyfish)
				.setDefaultValue(defaultConfig.jellyfish.spawnJellyfish)
				.setSaveConsumer(newValue -> jellyfish.spawnJellyfish = newValue)
				.setTooltip(tooltip("spawn_jellyfish"))
				.build(),
			jellyfish.getClass(),
			"spawnJellyfish",
			configInstance
		);

		var jellyfishSpawnCap = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("jellyfish_spawn_cap"), modifiedJellyfish.jellyfishSpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.jellyfish.jellyfishSpawnCap)
				.setSaveConsumer(newValue -> jellyfish.jellyfishSpawnCap = newValue)
				.setTooltip(tooltip("jellyfish_spawn_cap"))
				.requireRestart()
				.build(),
			jellyfish.getClass(),
			"jellyfishSpawnCap",
			configInstance
		);

		var jellyfishHiding = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("jellyfish_hiding"), modifiedJellyfish.jellyfishHiding)
				.setDefaultValue(defaultConfig.jellyfish.jellyfishHiding)
				.setSaveConsumer(newValue -> jellyfish.jellyfishHiding = newValue)
				.setTooltip(tooltip("jellyfish_hiding"))
				.build(),
			jellyfish.getClass(),
			"jellyfishHiding",
			configInstance
		);

		var jellyfishTentacles = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("jellyfish_tentacles"), modifiedJellyfish.jellyfishTentacles, 0, 100)
				.setDefaultValue(defaultConfig.jellyfish.jellyfishTentacles)
				.setSaveConsumer(newValue -> jellyfish.jellyfishTentacles = newValue)
				.setTooltip(tooltip("jellyfish_tentacles"))
				.requireRestart()
				.build(),
			jellyfish.getClass(),
			"jellyfishTentacles",
			configInstance
		);

		var planeTentacles = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("plane_tentacles"), modifiedJellyfish.planeTentacles)
				.setDefaultValue(defaultConfig.jellyfish.planeTentacles)
				.setSaveConsumer(newValue -> jellyfish.planeTentacles = newValue)
				.setTooltip(tooltip("plane_tentacles"))
				.build(),
			jellyfish.getClass(),
			"planeTentacles",
			configInstance
		);

		var oralArm = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("oral_arm"), modifiedJellyfish.oralArm)
				.setDefaultValue(defaultConfig.jellyfish.oralArm)
				.setSaveConsumer(newValue -> jellyfish.oralArm = newValue)
				.setTooltip(tooltip("oral_arm"))
				.build(),
			jellyfish.getClass(),
			"oralArm",
			configInstance
		);

		var jellyfishCategory = FrozenClothConfig.createSubCategory(builder, category, text("jellyfish"),
			false,
			tooltip("jellyfish"),
			spawnJellyfish, jellyfishSpawnCap, jellyfishHiding, jellyfishTentacles, planeTentacles, oralArm
		);

		var spawnCrabs = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_crabs"), modifiedCrab.spawnCrabs)
				.setDefaultValue(defaultConfig.crab.spawnCrabs)
				.setSaveConsumer(newValue -> crab.spawnCrabs = newValue)
				.setTooltip(tooltip("spawn_crabs"))
				.build(),
			crab.getClass(),
			"spawnCrabs",
			configInstance
		);

		var crabSpawnCap = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("crab_spawn_cap"), modifiedCrab.crabSpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.crab.crabSpawnCap)
				.setSaveConsumer(newValue -> crab.crabSpawnCap = newValue)
				.setTooltip(tooltip("crab_spawn_cap"))
				.requireRestart()
				.build(),
			crab.getClass(),
			"crabSpawnCap",
			configInstance
		);

		var reachAffectsAttack = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("reach_affects_attack"), modifiedCrab.reachAffectsAttack)
				.setDefaultValue(defaultConfig.crab.reachAffectsAttack)
				.setSaveConsumer(newValue -> crab.reachAffectsAttack = newValue)
				.setTooltip(tooltip("reach_affects_attack"))
				.requireRestart()
				.build(),
			crab.getClass(),
			"reachAffectsAttack",
			configInstance
		);

		var crabClawGivesReach = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("crab_claw_gives_reach"), modifiedCrab.crabClawGivesReach)
				.setDefaultValue(defaultConfig.crab.crabClawGivesReach)
				.setSaveConsumer(newValue -> crab.crabClawGivesReach = newValue)
				.setTooltip(tooltip("crab_claw_gives_reach"))
				.requireRestart()
				.build(),
			crab.getClass(),
			"crabClawGivesReach",
			configInstance
		);

		var crabCategory = FrozenClothConfig.createSubCategory(builder, category, text("crab"),
			false,
			tooltip("crab"),
			spawnCrabs, crabSpawnCap, reachAffectsAttack, crabClawGivesReach
		);

		var spawnOstriches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_ostriches"), modifiedOstrich.spawnOstriches)
				.setDefaultValue(defaultConfig.ostrich.spawnOstriches)
				.setSaveConsumer(newValue -> ostrich.spawnOstriches = newValue)
				.setTooltip(tooltip("spawn_ostriches"))
				.build(),
			ostrich.getClass(),
			"spawnOstriches",
			configInstance
		);

		var spawnZombieOstriches = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_zombie_ostriches"), modifiedOstrich.spawnZombieOstriches)
				.setDefaultValue(defaultConfig.ostrich.spawnZombieOstriches)
				.setSaveConsumer(newValue -> ostrich.spawnZombieOstriches = newValue)
				.setTooltip(tooltip("spawn_zombie_ostriches"))
				.build(),
			ostrich.getClass(),
			"spawnZombieOstriches",
			configInstance
		);

		var ostrichAttack = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("allow_ostrich_attack"), modifiedOstrich.allowAttack)
				.setDefaultValue(defaultConfig.ostrich.allowAttack)
				.setSaveConsumer(newValue -> ostrich.allowAttack = newValue)
				.setTooltip(tooltip("allow_ostrich_attack"))
				.build(),
			ostrich.getClass(),
			"allowAttack",
			configInstance
		);

		var ostrichCategory = FrozenClothConfig.createSubCategory(builder, category, text("ostrich"),
			false,
			tooltip("ostrich"),
			spawnOstriches, spawnZombieOstriches, ostrichAttack
		);

		var spawnScorched = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_scorched"), modifiedScorched.spawnScorched)
				.setDefaultValue(defaultConfig.scorched.spawnScorched)
				.setSaveConsumer(newValue -> scorched.spawnScorched = newValue)
				.setTooltip(tooltip("spawn_scorched"))
				.build(),
			scorched.getClass(),
			"spawnScorched",
			configInstance
		);

		var scorchedInTrialChambers = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("scorched_in_trial_chambers"), modifiedScorched.scorchedInTrialChambers)
				.setDefaultValue(defaultConfig.scorched.scorchedInTrialChambers)
				.setSaveConsumer(newValue -> scorched.scorchedInTrialChambers = newValue)
				.setTooltip(tooltip("scorched_in_trial_chambers"))
				.requireRestart()
				.build(),
			scorched.getClass(),
			"scorchedInTrialChambers",
			configInstance
		);

		var scorchedCategory = FrozenClothConfig.createSubCategory(builder, category, text("scorched"),
			false,
			tooltip("scorched"),
			spawnScorched, scorchedInTrialChambers
		);

		var spawnMooblooms = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_mooblooms"), modifiedMoobloom.spawnMooblooms)
				.setDefaultValue(defaultConfig.moobloom.spawnMooblooms)
				.setSaveConsumer(newValue -> moobloom.spawnMooblooms = newValue)
				.setTooltip(tooltip("spawn_mooblooms"))
				.build(),
			moobloom.getClass(),
			"spawnMooblooms",
			configInstance
		);

		var moobloomCategory = FrozenClothConfig.createSubCategory(builder, category, text("moobloom"),
			false,
			tooltip("moobloom"),
			spawnMooblooms
		);

		var spawnPenguins = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_penguins"), modifiedPenguin.spawnPenguins)
				.setDefaultValue(defaultConfig.penguin.spawnPenguins)
				.setSaveConsumer(newValue -> penguin.spawnPenguins = newValue)
				.setTooltip(tooltip("spawn_penguins"))
				.build(),
			penguin.getClass(),
			"spawnPenguins",
			configInstance
		);

		var penguinCategory = FrozenClothConfig.createSubCategory(builder, category, text("penguin"),
			false,
			tooltip("penguin"),
			spawnPenguins
		);

		var spawnTumbleweed = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("spawn_tumbleweed"), modifiedTumbleweed.spawnTumbleweed)
				.setDefaultValue(defaultConfig.tumbleweed.spawnTumbleweed)
				.setSaveConsumer(newValue -> tumbleweed.spawnTumbleweed = newValue)
				.setTooltip(tooltip("spawn_tumbleweed"))
				.build(),
			tumbleweed.getClass(),
			"spawnTumbleweed",
			configInstance
		);

		var tumbleweedSpawnCap = FrozenClothConfig.syncedEntry(
			builder.startIntSlider(text("tumbleweed_spawn_cap"), modifiedTumbleweed.tumbleweedSpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.tumbleweed.tumbleweedSpawnCap)
				.setSaveConsumer(newValue -> tumbleweed.tumbleweedSpawnCap = newValue)
				.setTooltip(tooltip("tumbleweed_spawn_cap"))
				.requireRestart()
				.build(),
			tumbleweed.getClass(),
			"tumbleweedSpawnCap",
			configInstance
		);

		var leashedTumbleweed = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("leashed_tumbleweed"), modifiedTumbleweed.leashedTumbleweed)
				.setDefaultValue(defaultConfig.tumbleweed.leashedTumbleweed)
				.setSaveConsumer(newValue -> tumbleweed.leashedTumbleweed = newValue)
				.setTooltip(tooltip("leashed_tumbleweed"))
				.build(),
			tumbleweed.getClass(),
			"leashedTumbleweed",
			configInstance
		);

		var tumbleweedDestroysCrops = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tumbleweed_destroys_crops"), modifiedTumbleweed.tumbleweedDestroysCrops)
				.setDefaultValue(defaultConfig.tumbleweed.tumbleweedDestroysCrops)
				.setSaveConsumer(newValue -> tumbleweed.tumbleweedDestroysCrops = newValue)
				.setTooltip(tooltip("tumbleweed_destroys_crops"))
				.build(),
			tumbleweed.getClass(),
			"tumbleweedDestroysCrops",
			configInstance
		);

		var tumbleweedRotatesToLookDirection = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tumbleweed_rotates_to_look_direction"), modifiedTumbleweed.tumbleweedRotatesToLookDirection)
				.setDefaultValue(defaultConfig.tumbleweed.tumbleweedRotatesToLookDirection)
				.setSaveConsumer(newValue -> tumbleweed.tumbleweedRotatesToLookDirection = newValue)
				.setTooltip(tooltip("tumbleweed_rotates_to_look_direction"))
				.build(),
			tumbleweed.getClass(),
			"tumbleweedRotatesToLookDirection",
			configInstance
		);

		var tumbleweedCategory = FrozenClothConfig.createSubCategory(builder, category, text("tumbleweed"),
			false,
			tooltip("tumbleweed"),
			spawnTumbleweed, tumbleweedSpawnCap, leashedTumbleweed, tumbleweedDestroysCrops, tumbleweedRotatesToLookDirection
		);

		var wardenAttacksImmediately = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_attacks_immediately"), modifiedWarden.wardenAttacksImmediately)
				.setDefaultValue(defaultConfig.warden.wardenAttacksImmediately)
				.setSaveConsumer(newValue -> warden.wardenAttacksImmediately = newValue)
				.setTooltip(tooltip("warden_attacks_immediately"))
				.build(),
			warden.getClass(),
			"wardenAttacksImmediately",
			configInstance
		);

		var wardenSwims = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_swims"), modifiedWarden.wardenSwims)
				.setDefaultValue(defaultConfig.warden.wardenSwims)
				.setSaveConsumer(newValue -> warden.wardenSwims = newValue)
				.setTooltip(tooltip("warden_swims"))
				.build(),
			warden.getClass(),
			"wardenSwims",
			configInstance
		);

		var wardenSwimAnimation = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_swim_animation"), modifiedWarden.wardenSwimAnimation)
				.setDefaultValue(defaultConfig.warden.wardenSwimAnimation)
				.setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
				.setTooltip(tooltip("warden_swim_animation"))
				.build(),
			warden.getClass(),
			"wardenSwimAnimation",
			configInstance
		);

		var wardenDeathAnimation = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_death_animation"), modifiedWarden.wardenDeathAnimation)
				.setDefaultValue(defaultConfig.warden.wardenDeathAnimation)
				.setSaveConsumer(newValue -> warden.wardenDeathAnimation = newValue)
				.setTooltip(tooltip("warden_death_animation"))
				.build(),
			warden.getClass(),
			"wardenDeathAnimation",
			configInstance
		);

		var wardenImprovedEmerge = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_improved_emerge"), modifiedWarden.wardenImprovedEmerge)
				.setDefaultValue(defaultConfig.warden.wardenImprovedEmerge)
				.setSaveConsumer(newValue -> warden.wardenImprovedEmerge = newValue)
				.setYesNoTextSupplier(bool -> text("improved." + bool))
				.setTooltip(tooltip("warden_improved_emerge"))
				.build(),
			warden.getClass(),
			"wardenImprovedEmerge",
			configInstance
		);

		var wardenEmergesFromCommand = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_emerges_from_command"), modifiedWarden.wardenEmergesFromCommand)
				.setDefaultValue(defaultConfig.warden.wardenEmergesFromCommand)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromCommand = newValue)
				.setTooltip(tooltip("warden_emerges_from_command"))
				.build(),
			warden.getClass(),
			"wardenEmergesFromCommand",
			configInstance
		);

		var wardenEmergesFromEgg = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_emerges_from_egg"), modifiedWarden.wardenEmergesFromEgg)
				.setDefaultValue(defaultConfig.warden.wardenEmergesFromEgg)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
				.setTooltip(tooltip("warden_emerges_from_egg"))
				.build(),
			warden.getClass(),
			"wardenEmergesFromEgg",
			configInstance
		);

		var wardenImprovedDig = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_improved_dig"), modifiedWarden.wardenImprovedDig)
				.setDefaultValue(defaultConfig.warden.wardenImprovedDig)
				.setSaveConsumer(newValue -> warden.wardenImprovedDig = newValue)
				.setYesNoTextSupplier(bool -> text("improved." + bool))
				.setTooltip(tooltip("warden_improved_dig"))
				.build(),
			warden.getClass(),
			"wardenImprovedDig",
			configInstance
		);

		var wardenBedrockSniff = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_bedrock_sniff"), modifiedWarden.wardenBedrockSniff)
				.setDefaultValue(defaultConfig.warden.wardenBedrockSniff)
				.setSaveConsumer(newValue -> warden.wardenBedrockSniff = newValue)
				.setYesNoTextSupplier(bool -> text("warden_bedrock_sniff." + bool))
				.setTooltip(tooltip("warden_bedrock_sniff"))
				.build(),
			warden.getClass(),
			"wardenBedrockSniff",
			configInstance
		);

		var wardenCustomTendrils = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("warden_custom_tendrils"), modifiedWarden.wardenCustomTendrils)
				.setDefaultValue(defaultConfig.warden.wardenCustomTendrils)
				.setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
				.setYesNoTextSupplier(bool -> text("improved." + bool))
				.setTooltip(tooltip("warden_custom_tendrils"))
				.build(),
			warden.getClass(),
			"wardenCustomTendrils",
			configInstance
		);

		var wardenCategory = FrozenClothConfig.createSubCategory(builder, category, text("warden"),
			false,
			tooltip("warden"),
			wardenAttacksImmediately, wardenSwims, wardenSwimAnimation, wardenDeathAnimation, wardenImprovedEmerge, wardenEmergesFromCommand, wardenEmergesFromEgg, wardenImprovedDig, wardenBedrockSniff, wardenCustomTendrils
		);

		var willowTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("willow_trade"), modifiedVillager.wanderingWillowTrade)
				.setDefaultValue(defaultConfig.villager.wanderingWillowTrade)
				.setSaveConsumer(newValue -> villager.wanderingWillowTrade = newValue)
				.setTooltip(tooltip("willow_trade"))
				.build(),
			villager.getClass(),
			"wanderingWillowTrade",
			configInstance
		);
		var cypressTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("cypress_trade"), modifiedVillager.wanderingCypressTrade)
				.setDefaultValue(defaultConfig.villager.wanderingCypressTrade)
				.setSaveConsumer(newValue -> villager.wanderingCypressTrade = newValue)
				.setTooltip(tooltip("cypress_trade"))
				.build(),
			villager.getClass(),
			"wanderingCypressTrade",
			configInstance
		);
		var baobabTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("baobab_trade"), modifiedVillager.wanderingBaobabTrade)
				.setDefaultValue(defaultConfig.villager.wanderingBaobabTrade)
				.setSaveConsumer(newValue -> villager.wanderingBaobabTrade = newValue)
				.setTooltip(tooltip("baobab_trade"))
				.build(),
			villager.getClass(),
			"wanderingBaobabTrade",
			configInstance
		);
		var palmTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("palm_trade"), modifiedVillager.wanderingPalmTrade)
				.setDefaultValue(defaultConfig.villager.wanderingPalmTrade)
				.setSaveConsumer(newValue -> villager.wanderingPalmTrade = newValue)
				.setTooltip(tooltip("palm_trade"))
				.build(),
			villager.getClass(),
			"wanderingPalmTrade",
			configInstance
		);
		var mapleTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("maple_trade"), modifiedVillager.wanderingMapleTrade)
				.setDefaultValue(defaultConfig.villager.wanderingMapleTrade)
				.setSaveConsumer(newValue -> villager.wanderingMapleTrade = newValue)
				.setTooltip(tooltip("maple_trade"))
				.build(),
			villager.getClass(),
			"wanderingMapleTrade",
			configInstance
		);
		var carnationTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("carnation_trade"), modifiedVillager.wanderingCarnationTrade)
				.setDefaultValue(defaultConfig.villager.wanderingCarnationTrade)
				.setSaveConsumer(newValue -> villager.wanderingCarnationTrade = newValue)
				.setTooltip(tooltip("carnation_trade"))
				.build(),
			villager.getClass(),
			"wanderingCarnationTrade",
			configInstance
		);
		var hibiscusTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("hibiscus_trade"), modifiedVillager.wanderingHibiscusTrade)
				.setDefaultValue(defaultConfig.villager.wanderingHibiscusTrade)
				.setSaveConsumer(newValue -> villager.wanderingHibiscusTrade = newValue)
				.setTooltip(tooltip("hibiscus_trade"))
				.build(),
			villager.getClass(),
			"wanderingHibiscusTrade",
			configInstance
		);
		var seedingDandelionTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("seeding_dandelion_trade"), modifiedVillager.wanderingSeedingDandelionTrade)
				.setDefaultValue(defaultConfig.villager.wanderingSeedingDandelionTrade)
				.setSaveConsumer(newValue -> villager.wanderingSeedingDandelionTrade = newValue)
				.setTooltip(tooltip("seeding_dandelion_trade"))
				.build(),
			villager.getClass(),
			"wanderingSeedingDandelionTrade",
			configInstance
		);
		var marigoldTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("marigold_trade"), modifiedVillager.wanderingMarigoldTrade)
				.setDefaultValue(defaultConfig.villager.wanderingMarigoldTrade)
				.setSaveConsumer(newValue -> villager.wanderingMarigoldTrade = newValue)
				.setTooltip(tooltip("marigold_trade"))
				.build(),
			villager.getClass(),
			"wanderingMarigoldTrade",
			configInstance
		);
		var pasqueflowerTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("pasqueflower_trade"), modifiedVillager.wanderingPasqueflowerTrade)
				.setDefaultValue(defaultConfig.villager.wanderingPasqueflowerTrade)
				.setSaveConsumer(newValue -> villager.wanderingPasqueflowerTrade = newValue)
				.setTooltip(tooltip("pasqueflower_trade"))
				.build(),
			villager.getClass(),
			"wanderingPasqueflowerTrade",
			configInstance
		);
		var pricklyPearTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("prickly_pear_trade"), modifiedVillager.wanderingPricklyPearTrade)
				.setDefaultValue(defaultConfig.villager.wanderingPricklyPearTrade)
				.setSaveConsumer(newValue -> villager.wanderingPricklyPearTrade = newValue)
				.setTooltip(tooltip("prickly_pear_trade"))
				.build(),
			villager.getClass(),
			"wanderingPricklyPearTrade",
			configInstance
		);
		var tumbleweedTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("tumbleweed_trade"), modifiedVillager.wanderingTumbleweedTrade)
				.setDefaultValue(defaultConfig.villager.wanderingTumbleweedTrade)
				.setSaveConsumer(newValue -> villager.wanderingTumbleweedTrade = newValue)
				.setTooltip(tooltip("tumbleweed_trade"))
				.build(),
			villager.getClass(),
			"wanderingTumbleweedTrade",
			configInstance
		);
		var icicleTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("icicle_trade"), modifiedVillager.wanderingIcicleTrade)
				.setDefaultValue(defaultConfig.villager.wanderingIcicleTrade)
				.setSaveConsumer(newValue -> villager.wanderingIcicleTrade = newValue)
				.setTooltip(tooltip("icicle_trade"))
				.build(),
			villager.getClass(),
			"wanderingIcicleTrade",
			configInstance
		);
		var barnaclesTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("barnacles_trade"), modifiedVillager.wanderingBarnaclesTrade)
				.setDefaultValue(defaultConfig.villager.wanderingBarnaclesTrade)
				.setSaveConsumer(newValue -> villager.wanderingBarnaclesTrade = newValue)
				.setTooltip(tooltip("barnacles_trade"))
				.build(),
			villager.getClass(),
			"wanderingBarnaclesTrade",
			configInstance
		);
		var seaAnemoneTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sea_anemone_trade"), modifiedVillager.wanderingSeaAnemoneTrade)
				.setDefaultValue(defaultConfig.villager.wanderingSeaAnemoneTrade)
				.setSaveConsumer(newValue -> villager.wanderingSeaAnemoneTrade = newValue)
				.setTooltip(tooltip("sea_anemone_trade"))
				.build(),
			villager.getClass(),
			"wanderingSeaAnemoneTrade",
			configInstance
		);
		var seaWhipTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("sea_whip_trade"), modifiedVillager.wanderingSeaWhipTrade)
				.setDefaultValue(defaultConfig.villager.wanderingSeaWhipTrade)
				.setSaveConsumer(newValue -> villager.wanderingSeaWhipTrade = newValue)
				.setTooltip(tooltip("sea_whip_trade"))
				.build(),
			villager.getClass(),
			"wanderingSeaWhipTrade",
			configInstance
		);
		var algaeTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("algae_trade"), modifiedVillager.wanderingAlgaeTrade)
				.setDefaultValue(defaultConfig.villager.wanderingAlgaeTrade)
				.setSaveConsumer(newValue -> villager.wanderingAlgaeTrade = newValue)
				.setTooltip(tooltip("algae_trade"))
				.build(),
			villager.getClass(),
			"wanderingAlgaeTrade",
			configInstance
		);
		var planktonTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("plankton_trade"), modifiedVillager.wanderingPlanktonTrade)
				.setDefaultValue(defaultConfig.villager.wanderingPlanktonTrade)
				.setSaveConsumer(newValue -> villager.wanderingPlanktonTrade = newValue)
				.setTooltip(tooltip("plankton_trade"))
				.build(),
			villager.getClass(),
			"wanderingPlanktonTrade",
			configInstance
		);
		var auburnMossTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("auburn_moss_trade"), modifiedVillager.wanderingAuburnMossTrade)
				.setDefaultValue(defaultConfig.villager.wanderingAuburnMossTrade)
				.setSaveConsumer(newValue -> villager.wanderingAuburnMossTrade = newValue)
				.setTooltip(tooltip("auburn_moss_trade"))
				.build(),
			villager.getClass(),
			"wanderingAuburnMossTrade",
			configInstance
		);
		var geyserTrade = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("geyser_trade"), modifiedVillager.wanderingGeyserTrade)
				.setDefaultValue(defaultConfig.villager.wanderingGeyserTrade)
				.setSaveConsumer(newValue -> villager.wanderingGeyserTrade = newValue)
				.setTooltip(tooltip("geyser_trade"))
				.build(),
			villager.getClass(),
			"wanderingGeyserTrade",
			configInstance
		);

		var desertPalmBoat = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("desert_palm_boat"), modifiedVillager.fishermanDesertPalmBoat)
				.setDefaultValue(defaultConfig.villager.fishermanDesertPalmBoat)
				.setSaveConsumer(newValue -> villager.fishermanDesertPalmBoat = newValue)
				.setTooltip(tooltip("desert_palm_boat"))
				.build(),
			villager.getClass(),
			"fishermanDesertPalmBoat",
			configInstance
		);
		var crabForEmeralds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("crab_for_emeralds"), modifiedVillager.fishermanCrabForEmeralds)
				.setDefaultValue(defaultConfig.villager.fishermanCrabForEmeralds)
				.setSaveConsumer(newValue -> villager.fishermanCrabForEmeralds = newValue)
				.setTooltip(tooltip("crab_for_emeralds"))
				.build(),
			villager.getClass(),
			"fishermanCrabForEmeralds",
			configInstance
		);
		var jellyfishForEmeralds = FrozenClothConfig.syncedEntry(
			builder.startBooleanToggle(text("jellyfish_for_emeralds"), modifiedVillager.fishermanJellyfishForEmeralds)
				.setDefaultValue(defaultConfig.villager.fishermanJellyfishForEmeralds)
				.setSaveConsumer(newValue -> villager.fishermanJellyfishForEmeralds = newValue)
				.setTooltip(tooltip("jellyfish_for_emeralds"))
				.build(),
			villager.getClass(),
			"fishermanJellyfishForEmeralds",
			configInstance
		);

		var villagerCategory = FrozenClothConfig.createSubCategory(builder, category, text("villager"),
			false,
			tooltip("villager"),
			willowTrade, cypressTrade, baobabTrade, palmTrade, mapleTrade,
			carnationTrade, hibiscusTrade, seedingDandelionTrade, marigoldTrade, pasqueflowerTrade,
			pricklyPearTrade, tumbleweedTrade,
			icicleTrade,
			barnaclesTrade, seaAnemoneTrade, seaWhipTrade, algaeTrade, planktonTrade, auburnMossTrade,
			geyserTrade,
			desertPalmBoat, crabForEmeralds, jellyfishForEmeralds
		);
	}
}
