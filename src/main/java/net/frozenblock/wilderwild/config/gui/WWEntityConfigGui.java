/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public final class WWEntityConfigGui {
	private WWEntityConfigGui() {
		throw new UnsupportedOperationException("EntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(@NotNull ConfigCategory category, @NotNull ConfigEntryBuilder entryBuilder) {
		var config = WWEntityConfig.get(true);
		var modifiedConfig = WWEntityConfig.getWithSync();
		Class<? extends WWEntityConfig> clazz = config.getClass();
		Config<?> configInstance = WWEntityConfig.INSTANCE;
		var defaultConfig = WWEntityConfig.INSTANCE.defaultInstance();
		var lightning = config.lightning;
		var modifiedLightning = modifiedConfig.lightning;
		var allay = config.allay;
		var modifiedAllay = modifiedConfig.allay;
		var enderMan = config.enderMan;
		var modifiedEnderMan = modifiedConfig.enderMan;
		var firefly = config.firefly;
		var modifiedFirefly = modifiedConfig.firefly;
		var butterfly = config.butterfly;
		var modifiedButterfly = modifiedConfig.butterfly;
		var jellyfish = config.jellyfish;
		var modifiedJellyfish = modifiedConfig.jellyfish;
		var crab = config.crab;
		var modifiedCrab = modifiedConfig.crab;
		var ostrich = config.ostrich;
		var modifiedOstrich = modifiedConfig.ostrich;
		var scorched = config.scorched;
		var modifiedScorched = modifiedConfig.scorched;
		var moobloom = config.moobloom;
		var modifiedMoobloom = modifiedConfig.moobloom;
		var penguin = config.penguin;
		var modifiedPenguin = modifiedConfig.penguin;
		var tumbleweed = config.tumbleweed;
		var modifiedTumbleweed = modifiedConfig.tumbleweed;
		var warden = config.warden;
		var modifiedWarden = modifiedConfig.warden;

		var unpassableRail = category.addEntry(
			FrozenClothConfig.syncedEntry(
				entryBuilder.startBooleanToggle(text("unpassable_rail"), modifiedConfig.unpassableRail)
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
			entryBuilder.startBooleanToggle(text("lightning_scorches_sand"), modifiedLightning.lightningScorchesSand)
				.setDefaultValue(defaultConfig.lightning.lightningScorchesSand)
				.setSaveConsumer(newValue -> lightning.lightningScorchesSand = newValue)
				.setTooltip(tooltip("lightning_scorches_sand"))
				.build(),
			lightning.getClass(),
			"lightningScorchesSand",
			configInstance
		);

		var lightningBlockParticles = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("lightning_block_particles"), modifiedLightning.lightningBlockParticles)
				.setDefaultValue(defaultConfig.lightning.lightningBlockParticles)
				.setSaveConsumer(newValue -> lightning.lightningBlockParticles = newValue)
				.setTooltip(tooltip("lightning_block_particles"))
				.build(),
			lightning.getClass(),
			"lightningBlockParticles",
			configInstance
		);

		var lightningSmokeParticles = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("lightning_smoke_particles"), modifiedLightning.lightningSmokeParticles)
				.setDefaultValue(defaultConfig.lightning.lightningSmokeParticles)
				.setSaveConsumer(newValue -> lightning.lightningSmokeParticles = newValue)
				.setTooltip(tooltip("lightning_smoke_particles"))
				.build(),
			lightning.getClass(),
			"lightningSmokeParticles",
			configInstance
		);

		var lightningCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("lightning"),
			false,
			tooltip("lightning"),
			lightningScorchesSand, lightningBlockParticles, lightningSmokeParticles
		);

		var keyframeAllayDance = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("keyframe_allay_dance"), modifiedAllay.keyframeAllayDance)
				.setDefaultValue(defaultConfig.allay.keyframeAllayDance)
				.setSaveConsumer(newValue -> allay.keyframeAllayDance = newValue)
				.setTooltip(tooltip("keyframe_allay_dance"))
				.build(),
			allay.getClass(),
			"keyframeAllayDance",
			configInstance
		);

		var allayCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("allay"),
			false,
			tooltip("allay"),
			keyframeAllayDance
		);

		var angerLoopSound = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("anger_loop_sound"), modifiedEnderMan.angerLoopSound)
				.setDefaultValue(defaultConfig.enderMan.angerLoopSound)
				.setSaveConsumer(newValue -> enderMan.angerLoopSound = newValue)
				.setTooltip(tooltip("anger_loop_sound"))
				.build(),
			enderMan.getClass(),
			"angerLoopSound",
			configInstance
		);

		var movingStareSound = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("moving_stare_sound"), modifiedEnderMan.movingStareSound)
				.setDefaultValue(defaultConfig.enderMan.movingStareSound)
				.setSaveConsumer(newValue -> enderMan.movingStareSound = newValue)
				.setTooltip(tooltip("moving_stare_sound"))
				.build(),
			enderMan.getClass(),
			"movingStareSound",
			configInstance
		);

		var enderManCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("enderman"),
			false,
			tooltip("enderman"),
			angerLoopSound, movingStareSound
		);

		var spawnFireflies = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_fireflies"), modifiedFirefly.spawnFireflies)
				.setDefaultValue(defaultConfig.firefly.spawnFireflies)
				.setSaveConsumer(newValue -> firefly.spawnFireflies = newValue)
				.setTooltip(tooltip("spawn_fireflies"))
				.build(),
			firefly.getClass(),
			"spawnFireflies",
			configInstance
		);

		var fireflySpawnCap = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("firefly_spawn_cap"), modifiedFirefly.fireflySpawnCap, 1, 100)
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
			entryBuilder.startBooleanToggle(text("firefly_swarm"), modifiedFirefly.fireflySwarm)
				.setDefaultValue(defaultConfig.firefly.fireflySwarm)
				.setSaveConsumer(newValue -> firefly.fireflySwarm = newValue)
				.setTooltip(tooltip("firefly_swarm"))
				.build(),
			firefly.getClass(),
			"fireflySwarm",
			configInstance
		);

		var fireflyCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("firefly"),
			false,
			tooltip("firefly"),
			spawnFireflies, fireflySpawnCap, fireflySwarm
		);

		var spawnButterflies = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_butterflies"), modifiedButterfly.spawnButterflies)
				.setDefaultValue(defaultConfig.butterfly.spawnButterflies)
				.setSaveConsumer(newValue -> butterfly.spawnButterflies = newValue)
				.setTooltip(tooltip("spawn_butterflies"))
				.build(),
			butterfly.getClass(),
			"spawnButterflies",
			configInstance
		);

		var butterflySpawnCap = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("butterfly_spawn_cap"), modifiedButterfly.butterflySpawnCap, 1, 100)
				.setDefaultValue(defaultConfig.butterfly.butterflySpawnCap)
				.setSaveConsumer(newValue -> butterfly.butterflySpawnCap = newValue)
				.setTooltip(tooltip("butterfly_spawn_cap"))
				.requireRestart()
				.build(),
			butterfly.getClass(),
			"butterflySpawnCap",
			configInstance
		);

		var butterflyCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("butterfly"),
			false,
			tooltip("butterfly"),
			spawnButterflies, butterflySpawnCap
		);

		var spawnJellyfish = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_jellyfish"), modifiedJellyfish.spawnJellyfish)
				.setDefaultValue(defaultConfig.jellyfish.spawnJellyfish)
				.setSaveConsumer(newValue -> jellyfish.spawnJellyfish = newValue)
				.setTooltip(tooltip("spawn_jellyfish"))
				.build(),
			jellyfish.getClass(),
			"spawnJellyfish",
			configInstance
		);

		var jellyfishSpawnCap = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("jellyfish_spawn_cap"), modifiedJellyfish.jellyfishSpawnCap, 1, 100)
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
			entryBuilder.startBooleanToggle(text("jellyfish_hiding"), modifiedJellyfish.jellyfishHiding)
				.setDefaultValue(defaultConfig.jellyfish.jellyfishHiding)
				.setSaveConsumer(newValue -> jellyfish.jellyfishHiding = newValue)
				.setTooltip(tooltip("jellyfish_hiding"))
				.build(),
			jellyfish.getClass(),
			"jellyfishHiding",
			configInstance
		);

		var jellyfishTentacles = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("jellyfish_tentacles"), modifiedJellyfish.jellyfishTentacles, 0, 100)
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
			entryBuilder.startBooleanToggle(text("plane_tentacles"), modifiedJellyfish.planeTentacles)
				.setDefaultValue(defaultConfig.jellyfish.planeTentacles)
				.setSaveConsumer(newValue -> jellyfish.planeTentacles = newValue)
				.setTooltip(tooltip("plane_tentacles"))
				.build(),
			jellyfish.getClass(),
			"planeTentacles",
			configInstance
		);

		var jellyfishCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("jellyfish"),
			false,
			tooltip("jellyfish"),
			spawnJellyfish, jellyfishSpawnCap, jellyfishHiding, jellyfishTentacles, planeTentacles
		);

		var spawnCrabs = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_crabs"), modifiedCrab.spawnCrabs)
				.setDefaultValue(defaultConfig.crab.spawnCrabs)
				.setSaveConsumer(newValue -> crab.spawnCrabs = newValue)
				.setTooltip(tooltip("spawn_crabs"))
				.build(),
			crab.getClass(),
			"spawnCrabs",
			configInstance
		);

		var crabSpawnCap = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("crab_spawn_cap"), modifiedCrab.crabSpawnCap, 1, 100)
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
			entryBuilder.startBooleanToggle(text("reach_affects_attack"), modifiedCrab.reachAffectsAttack)
				.setDefaultValue(defaultConfig.crab.reachAffectsAttack)
				.setSaveConsumer(newValue -> crab.reachAffectsAttack = newValue)
				.setTooltip(tooltip("reach_affects_attack"))
				.requireRestart()
				.build(),
			crab.getClass(),
			"reachAffectsAttack",
			configInstance
		);

		var crabCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("crab"),
			false,
			tooltip("crab"),
			spawnCrabs, crabSpawnCap, reachAffectsAttack
		);

		var spawnOstriches = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_ostriches"), modifiedOstrich.spawnOstriches)
				.setDefaultValue(defaultConfig.ostrich.spawnOstriches)
				.setSaveConsumer(newValue -> ostrich.spawnOstriches = newValue)
				.setTooltip(tooltip("spawn_ostriches"))
				.build(),
			ostrich.getClass(),
			"spawnOstriches",
			configInstance
		);

		var ostrichAttack = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("allow_ostrich_attack"), modifiedOstrich.allowAttack)
				.setDefaultValue(defaultConfig.ostrich.allowAttack)
				.setSaveConsumer(newValue -> ostrich.allowAttack = newValue)
				.setTooltip(tooltip("allow_ostrich_attack"))
				.build(),
			ostrich.getClass(),
			"allowAttack",
			configInstance
		);

		var ostrichCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("ostrich"),
			false,
			tooltip("ostrich"),
			spawnOstriches, ostrichAttack
		);

		var spawnScorched = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_scorched"), modifiedScorched.spawnScorched)
				.setDefaultValue(defaultConfig.scorched.spawnScorched)
				.setSaveConsumer(newValue -> scorched.spawnScorched = newValue)
				.setTooltip(tooltip("spawn_scorched"))
				.build(),
			scorched.getClass(),
			"spawnScorched",
			configInstance
		);

		var scorchedInTrialChambers = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("scorched_in_trial_chambers"), modifiedScorched.scorchedInTrialChambers)
				.setDefaultValue(defaultConfig.scorched.scorchedInTrialChambers)
				.setSaveConsumer(newValue -> scorched.scorchedInTrialChambers = newValue)
				.setTooltip(tooltip("scorched_in_trial_chambers"))
				.requireRestart()
				.build(),
			scorched.getClass(),
			"scorchedInTrialChambers",
			configInstance
		);

		var scorchedCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("scorched"),
			false,
			tooltip("scorched"),
			spawnScorched, scorchedInTrialChambers
		);

		var spawnMooblooms = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_mooblooms"), modifiedMoobloom.spawnMooblooms)
				.setDefaultValue(defaultConfig.moobloom.spawnMooblooms)
				.setSaveConsumer(newValue -> moobloom.spawnMooblooms = newValue)
				.setTooltip(tooltip("spawn_mooblooms"))
				.build(),
			moobloom.getClass(),
			"spawnMooblooms",
			configInstance
		);

		var moobloomCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("moobloom"),
			false,
			tooltip("moobloom"),
			spawnMooblooms
		);

		var spawnPenguins = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_penguins"), modifiedPenguin.spawnPenguins)
				.setDefaultValue(defaultConfig.penguin.spawnPenguins)
				.setSaveConsumer(newValue -> penguin.spawnPenguins = newValue)
				.setTooltip(tooltip("spawn_penguins"))
				.build(),
			penguin.getClass(),
			"spawnPenguins",
			configInstance
		);

		var penguinCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("penguin"),
			false,
			tooltip("penguin"),
			spawnPenguins
		);

		var spawnTumbleweed = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("spawn_tumbleweed"), modifiedTumbleweed.spawnTumbleweed)
				.setDefaultValue(defaultConfig.tumbleweed.spawnTumbleweed)
				.setSaveConsumer(newValue -> tumbleweed.spawnTumbleweed = newValue)
				.setTooltip(tooltip("spawn_tumbleweed"))
				.build(),
			tumbleweed.getClass(),
			"spawnTumbleweed",
			configInstance
		);

		var tumbleweedSpawnCap = FrozenClothConfig.syncedEntry(
			entryBuilder.startIntSlider(text("tumbleweed_spawn_cap"), modifiedTumbleweed.tumbleweedSpawnCap, 1, 100)
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
			entryBuilder.startBooleanToggle(text("leashed_tumbleweed"), modifiedTumbleweed.leashedTumbleweed)
				.setDefaultValue(defaultConfig.tumbleweed.leashedTumbleweed)
				.setSaveConsumer(newValue -> tumbleweed.leashedTumbleweed = newValue)
				.setTooltip(tooltip("leashed_tumbleweed"))
				.build(),
			tumbleweed.getClass(),
			"leashedTumbleweed",
			configInstance
		);

		var tumbleweedDestroysCrops = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("tumbleweed_destroys_crops"), modifiedTumbleweed.tumbleweedDestroysCrops)
				.setDefaultValue(defaultConfig.tumbleweed.tumbleweedDestroysCrops)
				.setSaveConsumer(newValue -> tumbleweed.tumbleweedDestroysCrops = newValue)
				.setTooltip(tooltip("tumbleweed_destroys_crops"))
				.build(),
			tumbleweed.getClass(),
			"tumbleweedDestroysCrops",
			configInstance
		);

		var tumbleweedRotatesToLookDirection = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("tumbleweed_rotates_to_look_direction"), modifiedTumbleweed.tumbleweedRotatesToLookDirection)
				.setDefaultValue(defaultConfig.tumbleweed.tumbleweedRotatesToLookDirection)
				.setSaveConsumer(newValue -> tumbleweed.tumbleweedRotatesToLookDirection = newValue)
				.setTooltip(tooltip("tumbleweed_rotates_to_look_direction"))
				.build(),
			tumbleweed.getClass(),
			"tumbleweedRotatesToLookDirection",
			configInstance
		);

		var tumbleweedCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("tumbleweed"),
			false,
			tooltip("tumbleweed"),
			spawnTumbleweed, tumbleweedSpawnCap, leashedTumbleweed, tumbleweedDestroysCrops, tumbleweedRotatesToLookDirection
		);

		var wardenAttacksImmediately = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_attacks_immediately"), modifiedWarden.wardenAttacksImmediately)
				.setDefaultValue(defaultConfig.warden.wardenAttacksImmediately)
				.setSaveConsumer(newValue -> warden.wardenAttacksImmediately = newValue)
				.setTooltip(tooltip("warden_attacks_immediately"))
				.build(),
			warden.getClass(),
			"wardenAttacksImmediately",
			configInstance
		);

		var wardenSwims = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_swims"), modifiedWarden.wardenSwims)
				.setDefaultValue(defaultConfig.warden.wardenSwims)
				.setSaveConsumer(newValue -> warden.wardenSwims = newValue)
				.setTooltip(tooltip("warden_swims"))
				.build(),
			warden.getClass(),
			"wardenSwims",
			configInstance
		);

		var wardenSwimAnimation = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_swim_animation"), modifiedWarden.wardenSwimAnimation)
				.setDefaultValue(defaultConfig.warden.wardenSwimAnimation)
				.setSaveConsumer(newValue -> warden.wardenSwimAnimation = newValue)
				.setTooltip(tooltip("warden_swim_animation"))
				.build(),
			warden.getClass(),
			"wardenSwimAnimation",
			configInstance
		);

		var wardenDeathAnimation = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_death_animation"), modifiedWarden.wardenDeathAnimation)
				.setDefaultValue(defaultConfig.warden.wardenDeathAnimation)
				.setSaveConsumer(newValue -> warden.wardenDeathAnimation = newValue)
				.setTooltip(tooltip("warden_death_animation"))
				.build(),
			warden.getClass(),
			"wardenDeathAnimation",
			configInstance
		);

		var wardenImprovedEmerge = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_improved_emerge"), modifiedWarden.wardenImprovedEmerge)
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
			entryBuilder.startBooleanToggle(text("warden_emerges_from_command"), modifiedWarden.wardenEmergesFromCommand)
				.setDefaultValue(defaultConfig.warden.wardenEmergesFromCommand)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromCommand = newValue)
				.setTooltip(tooltip("warden_emerges_from_command"))
				.build(),
			warden.getClass(),
			"wardenEmergesFromCommand",
			configInstance
		);

		var wardenEmergesFromEgg = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_emerges_from_egg"), modifiedWarden.wardenEmergesFromEgg)
				.setDefaultValue(defaultConfig.warden.wardenEmergesFromEgg)
				.setSaveConsumer(newValue -> warden.wardenEmergesFromEgg = newValue)
				.setTooltip(tooltip("warden_emerges_from_egg"))
				.build(),
			warden.getClass(),
			"wardenEmergesFromEgg",
			configInstance
		);

		var wardenImprovedDig = FrozenClothConfig.syncedEntry(
			entryBuilder.startBooleanToggle(text("warden_improved_dig"), modifiedWarden.wardenImprovedDig)
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
			entryBuilder.startBooleanToggle(text("warden_bedrock_sniff"), modifiedWarden.wardenBedrockSniff)
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
			entryBuilder.startBooleanToggle(text("warden_custom_tendrils"), modifiedWarden.wardenCustomTendrils)
				.setDefaultValue(defaultConfig.warden.wardenCustomTendrils)
				.setSaveConsumer(newValue -> warden.wardenCustomTendrils = newValue)
				.setYesNoTextSupplier(bool -> text("improved." + bool))
				.setTooltip(tooltip("warden_custom_tendrils"))
				.build(),
			warden.getClass(),
			"wardenCustomTendrils",
			configInstance
		);

		var wardenCategory = FrozenClothConfig.createSubCategory(entryBuilder, category, text("warden"),
			false,
			tooltip("warden"),
			wardenAttacksImmediately, wardenSwims, wardenSwimAnimation, wardenDeathAnimation, wardenImprovedEmerge, wardenEmergesFromCommand, wardenEmergesFromEgg, wardenImprovedDig, wardenBedrockSniff, wardenCustomTendrils
		);
	}
}
