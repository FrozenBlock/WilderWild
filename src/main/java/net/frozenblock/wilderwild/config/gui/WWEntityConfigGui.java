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

package net.frozenblock.wilderwild.config.gui;

import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.config.clothconfig.FrozenClothConfig;
import static net.frozenblock.wilderwild.WWConstants.tooltip;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import static net.frozenblock.wilderwild.config.gui.WWConfigGuiHelper.*;
import net.frozenblock.wilderwild.registry.WWEntityTypes;
import net.minecraft.world.entity.EntityTypes;

@Environment(EnvType.CLIENT)
public final class WWEntityConfigGui {

	private WWEntityConfigGui() {
		throw new UnsupportedOperationException("WWEntityConfigGui contains only static declarations.");
	}

	public static void setupEntries(ConfigCategory category, ConfigEntryBuilder builder) {
		category.addEntry(booleanEntry(builder, "unpassable_rail", WWEntityConfig.UNPASSABLE_RAIL));

		// LIGHTNING
		var lightningScorchesSand = booleanEntry(builder, "lightning_scorches_sand", WWEntityConfig.LIGHTNING_SCORCHES_SAND);
		var lightningBlockParticles = booleanEntry(builder, "lightning_block_particles", WWEntityConfig.LIGHTNING_BLOCK_PARTICLES);
		var lightningSmokeParticles = booleanEntry(builder, "lightning_smoke_particles", WWEntityConfig.LIGHTNING_SMOKE_PARTICLES);

		FrozenClothConfig.createSubCategory(builder, category, EntityTypes.LIGHTNING_BOLT.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.LIGHTNING_BOLT.getDescription()),
			lightningScorchesSand, lightningBlockParticles, lightningSmokeParticles
		);

		// ALLAY
		var keyframeAllayDance = booleanEntry(builder, "keyframe_allay_dance", WWEntityConfig.ALLAY_KEYFRAME_DANCE);

		FrozenClothConfig.createSubCategory(builder, category, EntityTypes.ALLAY.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.ALLAY.getDescription()),
			keyframeAllayDance
		);

		// ENDERMAN
		var angerLoopSound = booleanEntry(builder, "anger_loop_sound", WWEntityConfig.ENDERMAN_ANGER_LOOP_SOUND);
		var movingStareSound = booleanEntry(builder, "moving_stare_sound", WWEntityConfig.ENDERMAN_MOVING_STARE_SOUND);

		FrozenClothConfig.createSubCategory(builder, category, EntityTypes.ENDERMAN.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.ENDERMAN.getDescription()),
			angerLoopSound, movingStareSound
		);

		// FIREFLY
		var spawnFireflyParticles = booleanEntry(builder, "spawn_firefly_particles", WWEntityConfig.SPAWN_FIREFLY_PARTICLES);
		var spawnFireflies = entitySpawnEntry(builder, WWEntityTypes.FIREFLY, WWEntityConfig.SPAWN_FIREFLIES);
		var firefliesNeedBush = booleanEntry(builder, "fireflies_need_bush", WWEntityConfig.FIREFLIES_NEED_BUSH);
		var fireflySpawnCap = entitySpawnCapEntry(builder, WWEntityTypes.FIREFLY, WWEntityConfig.FIREFLY_SPAWN_CAP, 1, 100);
		var fireflySwarm = booleanEntry(builder, "firefly_swarm", WWEntityConfig.FIREFLY_SWARM);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.FIREFLY.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.FIREFLY.getDescription()),
			spawnFireflyParticles, spawnFireflies, firefliesNeedBush, fireflySpawnCap, fireflySwarm
		);

		// BUTTERFLY
		var spawnButterflies = entitySpawnEntry(builder, WWEntityTypes.BUTTERFLY, WWEntityConfig.SPAWN_BUTTERFLIES);
		var butterflySpawnCap = entitySpawnCapEntry(builder, WWEntityTypes.BUTTERFLY, WWEntityConfig.BUTTERFLY_SPAWN_CAP, 1, 100);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.BUTTERFLY.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.BUTTERFLY.getDescription()),
			spawnButterflies, butterflySpawnCap
		);

		// JELLYFISH
		var spawnJellyfish = entitySpawnEntry(builder, WWEntityTypes.JELLYFISH, WWEntityConfig.SPAWN_JELLYFISH);
		var jellyfishSpawnCap = entitySpawnCapEntry(builder, WWEntityTypes.JELLYFISH, WWEntityConfig.JELLYFISH_SPAWN_CAP, 1, 100);
		var jellyfishHiding = booleanEntry(builder, "jellyfish_hiding", WWEntityConfig.JELLYFISH_HIDING);
		var jellyfishTentacles = intSliderEntry(builder, "jellyfish_tentacles", WWEntityConfig.JELLYFISH_TENTACLES, 0, 100);
		var planeTentacles = booleanEntry(builder, "plane_tentacles", WWEntityConfig.JELLYFISH_PLANE_TENTACLES);
		var oralArm = booleanEntry(builder, "oral_arm", WWEntityConfig.JELLYFISH_ORAL_ARM);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.JELLYFISH.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.JELLYFISH.getDescription()),
			spawnJellyfish, jellyfishSpawnCap, jellyfishHiding, jellyfishTentacles, planeTentacles, oralArm
		);

		// CRAB
		var spawnCrabs = entitySpawnEntry(builder, WWEntityTypes.CRAB, WWEntityConfig.SPAWN_CRABS);
		var crabSpawnCap = entitySpawnCapEntry(builder, WWEntityTypes.CRAB, WWEntityConfig.CRAB_SPAWN_CAP, 1, 100);
		var reachAffectsAttack = booleanEntry(builder, "reach_affects_attack", WWEntityConfig.CRAB_REACH_AFFECTS_ATTACK);
		var crabClawGivesReach = booleanEntry(builder, "crab_claw_gives_reach", WWEntityConfig.CRAB_CLAW_GIVES_REACH);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.CRAB.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.CRAB.getDescription()),
			spawnCrabs, crabSpawnCap, reachAffectsAttack, crabClawGivesReach
		);

		// OSTRICH
		var spawnOstriches = entitySpawnEntry(builder, WWEntityTypes.OSTRICH, WWEntityConfig.SPAWN_OSTRICHES);
		var spawnZombieOstriches = entitySpawnEntry(builder, WWEntityTypes.ZOMBIE_OSTRICH, WWEntityConfig.SPAWN_ZOMBIE_OSTRICHES);
		var ostrichAttack = booleanEntry(builder, "allow_ostrich_attack", WWEntityConfig.OSTRICH_ALLOW_ATTACK);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.OSTRICH.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.OSTRICH.getDescription()),
			spawnOstriches, spawnZombieOstriches, ostrichAttack
		);

		// SCORCHED
		var spawnScorched = entitySpawnEntry(builder, WWEntityTypes.SCORCHED, WWEntityConfig.SPAWN_SCORCHED);
		var scorchedInTrialChambers = booleanEntry(builder, "scorched_in_trial_chambers", WWEntityConfig.SCORCHED_IN_TRIAL_CHAMBERS);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.SCORCHED.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.SCORCHED.getDescription()),
			spawnScorched, scorchedInTrialChambers
		);

		// MOOBLOOM
		var spawnMooblooms = entitySpawnEntry(builder, WWEntityTypes.MOOBLOOM, WWEntityConfig.SPAWN_MOOBLOOMS);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.MOOBLOOM.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.MOOBLOOM.getDescription()),
			spawnMooblooms
		);

		// PENGUIN
		var spawnPenguins = entitySpawnEntry(builder, WWEntityTypes.PENGUIN, WWEntityConfig.SPAWN_PENGUINS);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.PENGUIN.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.PENGUIN.getDescription()),
			spawnPenguins
		);

		// TUMBLEWEED
		var spawnTumbleweed = entitySpawnEntry(builder, WWEntityTypes.TUMBLEWEED, WWEntityConfig.SPAWN_TUMBLEWEED);
		var tumbleweedSpawnCap = entitySpawnCapEntry(builder, WWEntityTypes.TUMBLEWEED, WWEntityConfig.TUMBLEWEED_SPAWN_CAP, 1, 100);
		var leashedTumbleweed = booleanEntry(builder, "leashed_tumbleweed", WWEntityConfig.LEASHED_TUMBLEWEED);
		var tumbleweedDestroysCrops = booleanEntry(builder, "tumbleweed_destroys_crops", WWEntityConfig.TUMBLEWEED_DESTROYS_CROPS);
		var tumbleweedRotatesToLookDirection = booleanEntry(builder, "tumbleweed_rotates_to_look_direction", WWEntityConfig.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION);

		FrozenClothConfig.createSubCategory(builder, category, WWEntityTypes.TUMBLEWEED.getDescription(),
			false,
			tooltip("entity_category", WWEntityTypes.TUMBLEWEED.getDescription()),
			spawnTumbleweed, tumbleweedSpawnCap, leashedTumbleweed, tumbleweedDestroysCrops, tumbleweedRotatesToLookDirection
		);

		// WARDEN
		var wardenAttacksImmediately = booleanEntry(builder, "warden_attacks_immediately", WWEntityConfig.WARDEN_ATTACKS_IMMEDIATELY);
		var wardenSwims = booleanEntry(builder, "warden_swims", WWEntityConfig.WARDEN_SWIMS);
		var wardenSwimAnimation = booleanEntry(builder, "warden_swim_animation", WWEntityConfig.WARDEN_SWIM_ANIMATION);
		var wardenDeathAnimation = booleanEntry(builder, "warden_death_animation", WWEntityConfig.WARDEN_DEATH_ANIMATION);
		var wardenImprovedEmerge = booleanEntry(builder, "warden_improved_emerge", WWEntityConfig.WARDEN_IMPROVED_EMERGE_ANIMATION);
		var wardenEmergesFromCommand = booleanEntry(builder, "warden_emerges_from_command", WWEntityConfig.WARDEN_EMERGES_FROM_COMMAND);
		var wardenEmergesFromEgg = booleanEntry(builder, "warden_emerges_from_egg", WWEntityConfig.WARDEN_EMERGES_FROM_EGG);
		var wardenImprovedDig = booleanEntry(builder, "warden_improved_dig", WWEntityConfig.WARDEN_IMPROVED_DIG_ANIMATION);
		var wardenBedrockSniff = booleanEntry(builder, "warden_bedrock_sniff", WWEntityConfig.WARDEN_BEDROCK_SNIFF_ANIMATION);
		var wardenCustomTendrils = booleanEntry(builder, "warden_custom_tendrils", WWEntityConfig.WARDEN_IMPROVED_TENDRIL_ANIMATION);

		FrozenClothConfig.createSubCategory(builder, category, EntityTypes.WARDEN.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.WARDEN.getDescription()),
			wardenAttacksImmediately, wardenSwims, wardenSwimAnimation, wardenDeathAnimation,
			wardenImprovedEmerge, wardenImprovedDig, wardenBedrockSniff, wardenCustomTendrils,
			wardenEmergesFromCommand, wardenEmergesFromEgg
		);

		// VILLAGER
		var willowTrade = booleanEntry(builder, "willow_trade", WWEntityConfig.WANDERING_WILLOW_TRADE);
		var cypressTrade = booleanEntry(builder, "cypress_trade", WWEntityConfig.WANDERING_CYPRESS_TRADE);
		var baobabTrade = booleanEntry(builder, "baobab_trade", WWEntityConfig.WANDERING_BAOBAB_TRADE);
		var palmTrade = booleanEntry(builder, "palm_trade", WWEntityConfig.WANDERING_PALM_TRADE);
		var mapleTrade = booleanEntry(builder, "maple_trade", WWEntityConfig.WANDERING_MAPLE_TRADE);
		var carnationTrade = booleanEntry(builder, "carnation_trade", WWEntityConfig.WANDERING_CARNATION_TRADE);
		var hibiscusTrade = booleanEntry(builder, "hibiscus_trade", WWEntityConfig.WANDERING_HIBISCUS_TRADE);
		var seedingDandelionTrade = booleanEntry(builder, "seeding_dandelion_trade", WWEntityConfig.WANDERING_SEEDING_DANDELION_TRADE);
		var marigoldTrade = booleanEntry(builder, "marigold_trade", WWEntityConfig.WANDERING_MARIGOLD_TRADE);
		var pasqueflowerTrade = booleanEntry(builder, "pasqueflower_trade", WWEntityConfig.WANDERING_PASQUEFLOWER_TRADE);
		var pricklyPearTrade = booleanEntry(builder, "prickly_pear_trade", WWEntityConfig.WANDERING_PRICKLY_PEAR_TRADE);
		var tumbleweedTrade = booleanEntry(builder, "tumbleweed_trade", WWEntityConfig.WANDERING_TUMBLEWEED_TRADE);
		var icicleTrade = booleanEntry(builder, "icicle_trade", WWEntityConfig.WANDERING_ICICLE_TRADE);
		var barnaclesTrade = booleanEntry(builder, "barnacles_trade", WWEntityConfig.WANDERING_BARNACLES_TRADE);
		var seaAnemoneTrade = booleanEntry(builder, "sea_anemone_trade", WWEntityConfig.WANDERING_SEA_ANEMONE_TRADE);
		var seaWhipTrade = booleanEntry(builder, "sea_whip_trade", WWEntityConfig.WANDERING_SEA_WHIP_TRADE);
		var algaeTrade = booleanEntry(builder, "algae_trade", WWEntityConfig.WANDERING_ALGAE_TRADE);
		var planktonTrade = booleanEntry(builder, "plankton_trade", WWEntityConfig.WANDERING_PLANKTON_TRADE);
		var auburnMossTrade = booleanEntry(builder, "auburn_moss_trade", WWEntityConfig.WANDERING_AUBURN_MOSS_TRADE);
		var geyserTrade = booleanEntry(builder, "geyser_trade", WWEntityConfig.WANDERING_GEYSER_TRADE);

		var desertPalmBoat = booleanEntry(builder, "desert_palm_boat", WWEntityConfig.FISHERMAN_DESERT_PALM_BOAT);
		var crabForEmeralds = booleanEntry(builder, "crab_for_emeralds", WWEntityConfig.FISHERMAN_CRAB_FOR_EMERALDS);
		var jellyfishForEmeralds = booleanEntry(builder, "jellyfish_for_emeralds", WWEntityConfig.FISHERMAN_JELLYFISH_FOR_EMERALDS);

		/*
		FrozenClothConfig.createSubCategory(builder, category, EntityTypes.VILLAGER.getDescription(),
			false,
			tooltip("entity_category", EntityTypes.VILLAGER.getDescription()),
			willowTrade, cypressTrade, baobabTrade, palmTrade, mapleTrade,
			carnationTrade, hibiscusTrade, seedingDandelionTrade, marigoldTrade, pasqueflowerTrade,
			pricklyPearTrade, tumbleweedTrade,
			icicleTrade,
			barnaclesTrade, seaAnemoneTrade, seaWhipTrade, algaeTrade, planktonTrade, auburnMossTrade,
			geyserTrade,
			desertPalmBoat, crabForEmeralds, jellyfishForEmeralds
		);
		 */
	}
}
