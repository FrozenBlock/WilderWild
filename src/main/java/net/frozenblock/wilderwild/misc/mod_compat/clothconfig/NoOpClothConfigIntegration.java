/*
 * Copyright 2022-2023 FrozenBlock
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

package net.frozenblock.wilderwild.misc.mod_compat.clothconfig;

import net.frozenblock.lib.config.frozenlib_config.getter.FrozenLibConfigValues;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;

public class NoOpClothConfigIntegration extends AbstractClothConfigIntegration {
	public NoOpClothConfigIntegration() {
		super();
	}

	// BLOCK

	@Override
	public boolean mcLiveSensorTendrils() {
		return DefaultBlockConfig.MC_LIVE_SENSOR_TENDRILS;
	}

	@Override
	public boolean billboardTendrils() {
		return DefaultBlockConfig.BILLBOARD_TENDRILS;
	}

	@Override
	public boolean mesogleaLiquid() {
		return DefaultBlockConfig.MESOGLEA_LIQUID;
	}

	@Override
	public boolean pollenParticles() {
		return DefaultBlockConfig.POLLEN_PARTICLES;
	}

	@Override
	public boolean cactusPlacement() {
		return DefaultBlockConfig.CACTUS_PLACEMENT;
	}

	@Override
	public boolean cactusSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS;
	}

	@Override
	public boolean claySounds() {
		return DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS;
	}

	@Override
	public boolean coarseDirtSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS;
	}

	@Override
	public boolean cobwebSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS;
	}

	@Override
	public boolean deadBushSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS;
	}

	@Override
	public boolean flowerSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS;
	}

	@Override
	public boolean gravelSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS;
	}

	@Override
	public boolean frostedIceSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS;
	}

	@Override
	public boolean leafSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS;
	}

	@Override
	public boolean saplingSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.SAPLING_SOUNDS;
	}

	@Override
	public boolean lilyPadSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS;
	}

	@Override
	public boolean mushroomBlockSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS;
	}

	@Override
	public boolean podzolSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS;
	}

	@Override
	public boolean reinforcedDeepslateSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS;
	}

	@Override
	public boolean sandstoneSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.SANDSTONE_SOUNDS;
	}

	@Override
	public boolean sugarCaneSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS;
	}

	@Override
	public boolean witherRoseSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS;
	}

	@Override
	public boolean snowballLandingSounds() {
		return DefaultItemConfig.ProjectileLandingSoundsConfig.SNOWBALL_LANDING_SOUNDS;
	}

	@Override
	public boolean eggLandingSounds() {
		return DefaultItemConfig.ProjectileLandingSoundsConfig.EGG_LANDING_SOUNDS;
	}

	@Override
	public boolean enderPearlLandingSounds() {
		return DefaultItemConfig.ProjectileLandingSoundsConfig.ENDER_PEARL_LANDING_SOUNDS;
	}

	@Override
	public boolean potionLandingSounds() {
		return DefaultItemConfig.ProjectileLandingSoundsConfig.POTION_LANDING_SOUNDS;
	}

	@Override
	public int stoneChestTimer() {
		return DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER;
	}

	@Override
	public boolean termitesOnlyEatNaturalBlocks() {
		return DefaultBlockConfig.TermiteConfig.ONLY_EAT_NATURAL_BLOCKS;
	}

	@Override
	public boolean shriekerGargling() {
		return DefaultBlockConfig.SHRIEKER_GARGLING;
	}

	@Override
	public boolean soulFireSounds() {
		return DefaultBlockConfig.SOUL_FIRE_SOUNDS;
	}

	// ENTITY

	@Override
	public boolean unpassableRail() {
		return DefaultEntityConfig.UNPASSABLE_RAIL;
	}

	@Override
	public boolean keyframeAllayDance() {
		return DefaultEntityConfig.AllayConfig.KEYFRAME_ALLAY_DANCE;
	}

	@Override
	public boolean angerLoopSound() {
		return DefaultEntityConfig.EnderManConfig.ANGER_LOOP_SOUND;
	}

	@Override
	public boolean movingStareSound() {
		return DefaultEntityConfig.EnderManConfig.MOVING_STARE_SOUND;
	}

	@Override
	public int fireflySpawnCap() {
		return DefaultEntityConfig.FireflyConfig.FIREFLY_SPAWN_CAP;
	}

	@Override
	public int jellyfishSpawnCap() {
		return DefaultEntityConfig.JellyfishConfig.JELLYFISH_SPAWN_CAP;
	}

	@Override
	public int tumbleweedSpawnCap() {
		return DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_SPAWN_CAP;
	}

	@Override
	public boolean leashedTumbleweed() {
		return DefaultEntityConfig.TumbleweedConfig.LEASHED_TUMBLEWEED;
	}

	@Override
	public boolean wardenAttacksImmediately() {
		return DefaultEntityConfig.WardenConfig.WARDEN_ATTACKS_IMMEDIATELY;
	}

	@Override
	public boolean wardenCustomTendrils() {
		return DefaultEntityConfig.WardenConfig.WARDEN_CUSTOM_TENDRILS;
	}

	@Override
	public boolean wardenDyingAnimation() {
		return DefaultEntityConfig.WardenConfig.WARDEN_DYING_ANIMATION;
	}

	@Override
	public boolean wardenEmergesFromCommand() {
		return DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_COMMAND;
	}

	@Override
	public boolean wardenEmergesFromEgg() {
		return DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_EGG;
	}

	@Override
	public boolean wardenSwimAnimation() {
		return DefaultEntityConfig.WardenConfig.WARDEN_SWIM_ANIMATION;
	}

	@Override
	public boolean wardenBedrockSniff() {
		return DefaultEntityConfig.WardenConfig.WARDEN_BEDROCK_SNIFF;
	}

	// ITEM

	@Override
	public boolean hornCanSummonWarden() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
	}

	@Override
	public int hornLifespan() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN;
	}

	@Override
	public int hornMobDamage() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
	}

	@Override
	public int hornPlayerDamage() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
	}

	@Override
	public boolean hornShattersGlass() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
	}

	@Override
	public float hornSizeMultiplier() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SIZE_MULTIPLIER;
	}

	@Override
	public boolean projectileBreakParticles() {
		return DefaultItemConfig.PROJECTILE_BREAK_PARTICLES;
	}

	@Override
	public boolean itemCooldownsSave() {
		return FrozenLibConfigValues.DefaultFrozenLibConfigValues.SAVE_ITEM_COOLDOWNS;
	}

	@Override
	public boolean restrictInstrumentSound() {
		return DefaultItemConfig.RESTRICT_INSTRUMENT_SOUND;
	}

	// WORLDGEN

	@Override
	public boolean betaBeaches() {
		return DefaultWorldgenConfig.BETA_BEACHES;
	}

	@Override
	public boolean modifyJunglePlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
	}

	@Override
	public boolean modifySwampPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT;
	}

	@Override
	public boolean modifyMangroveSwampPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
	}

	@Override
	public boolean modifyWindsweptSavannaPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
	}

	@Override
	public boolean generateCypressWetlands() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_CYPRESS_WETLANDS;
	}

	@Override
	public boolean generateJellyfishCaves() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_JELLYFISH_CAVES;
	}

	@Override
	public boolean generateMixedForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_MIXED_FOREST;
	}

	@Override
	public boolean generateOasis() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OASIS;
	}

	@Override
	public boolean generateWarmRiver() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_RIVER;
	}

	@Override
	public boolean generateBirchTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA;
	}

	@Override
	public boolean generateOldGrowthBirchTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_BIRCH_TAIGA;
	}

	@Override
	public boolean generateFlowerField() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_FLOWER_FIELD;
	}

	@Override
	public boolean generateAridSavanna() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_SAVANNA;
	}

	@Override
	public boolean generateParchedForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_PARCHED_FOREST;
	}

	@Override
	public boolean generateAridForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_FOREST;
	}

	@Override
	public boolean generateOldGrowthSnowyTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA;
	}

	@Override
	public boolean generateBirchJungle() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_JUNGLE;
	}

	@Override
	public boolean generateSparseBirchJungle() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_SPARSE_BIRCH_JUNGLE;
	}

	@Override
	public boolean generateOldGrowthDarkForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_DARK_FOREST;
	}

	@Override
	public boolean generateDarkBirchForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_BIRCH_FOREST;
	}

	@Override
	public boolean generateSemiBirchForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_SEMI_BIRCH_FOREST;
	}

	@Override
	public boolean generateTemperateRainforest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_TEMPERATE_RAINFOREST;
	}

	@Override
	public boolean generateRainforest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_RAINFOREST;
	}

	@Override
	public boolean generateDarkTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_TAIGA;
	}

	@Override
	public boolean dyingTrees() {
		return DefaultWorldgenConfig.DYING_TREES;
	}

	@Override
	public boolean fallenLogs() {
		return DefaultWorldgenConfig.FALLEN_LOGS;
	}

	@Override
	public boolean snappedLogs() {
		return DefaultWorldgenConfig.SNAPPED_LOGS;
	}

	@Override
	public boolean wildTrees() {
		return DefaultWorldgenConfig.WILDER_WILD_TREE_GEN;
	}

	@Override
	public boolean wildGrass() {
		return DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN;
	}

	@Override
	public boolean wildFlowers() {
		return DefaultWorldgenConfig.WILDER_WILD_FLOWER_GEN;
	}

	@Override
	public boolean wildBushes() {
		return DefaultWorldgenConfig.WILDER_WILD_BUSH_GEN;
	}

	@Override
	public boolean wildCacti() {
		return DefaultWorldgenConfig.WILDER_WILD_CACTUS_GEN;
	}

	@Override
	public boolean wildMushrooms() {
		return DefaultWorldgenConfig.WILDER_WILD_MUSHROOM_GEN;
	}

	@Override
	public boolean tumbleweed() {
		return DefaultWorldgenConfig.TUMBLEWEED_GEN;
	}

	@Override
	public boolean surfaceDecoration() {
		return DefaultWorldgenConfig.SURFACE_DECORATION;
	}

	@Override
	public boolean algae() {
		return DefaultWorldgenConfig.ALGAE_GEN;
	}

	@Override
	public boolean termiteGen() {
		return DefaultWorldgenConfig.TERMITE_GEN;
	}

	@Override
	public boolean snowBelowTrees() {
		return DefaultWorldgenConfig.SNOW_BELOW_TREES;
	}

	@Override
	public boolean surfaceTransitions() {
		return DefaultWorldgenConfig.SURFACE_TRANSITIONS;
	}

	@Override
	public boolean newWitchHuts() {
		return DefaultWorldgenConfig.NEW_WITCH_HUTS;
	}

	// MISC

	@Override
	public boolean cloudMovement() {
		return DefaultMiscConfig.CLOUD_MOVEMENT;
	}

	@Override
	public float particleWindMovement() {
		return DefaultMiscConfig.PARTICLE_WIND_MOVEMENT / 100F;
	}

	@Override
	public boolean deepDarkAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
	}

	@Override
	public boolean dripstoneCavesAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
	}

	@Override
	public boolean lushCavesAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
	}

	@Override
	public boolean wilderForestMusic() {
		return DefaultMiscConfig.BiomeMusicConfig.WILDER_FOREST_MUSIC;
	}

}
