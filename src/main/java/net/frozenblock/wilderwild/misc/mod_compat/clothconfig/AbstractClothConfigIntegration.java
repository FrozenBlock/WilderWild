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
import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig;
import net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig;

public class AbstractClothConfigIntegration extends ModIntegration {
	public AbstractClothConfigIntegration() {
		super("cloth-config");
	}

	@Override
	public void init() {}

	public boolean mcLiveSensorTendrils() {
		return DefaultBlockConfig.MC_LIVE_SENSOR_TENDRILS;
	}

	public boolean billboardTendrils() {
		return DefaultBlockConfig.BILLBOARD_TENDRILS;
	}

	public boolean cactusSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.CACTUS_SOUNDS;
	}

	public boolean claySounds() {
		return DefaultBlockConfig.BlockSoundsConfig.CLAY_SOUNDS;
	}

	public boolean coarseDirtSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.COARSE_DIRT_SOUNDS;
	}

	public boolean cobwebSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.COBWEB_SOUNDS;
	}

	public boolean deadBushSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.DEAD_BUSH_SOUNDS;
	}

	public boolean flowerSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.FLOWER_SOUNDS;
	}

	public boolean gravelSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.GRAVEL_SOUNDS;
	}

	public boolean frostedIceSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.FROSTED_ICE_SOUNDS;
	}

	public boolean leafSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.LEAF_SOUNDS;
	}

	public boolean lilyPadSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.LILY_PAD_SOUNDS;
	}

	public boolean mushroomBlockSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS;
	}

	public boolean podzolSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.PODZOL_SOUNDS;
	}

	public boolean reinforcedDeepslateSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS;
	}

	public boolean sugarCaneSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.SUGAR_CANE_SOUNDS;
	}

	public boolean witherRoseSounds() {
		return DefaultBlockConfig.BlockSoundsConfig.WITHER_ROSE_SOUNDS;
	}

	public boolean snowballLandingSounds() {
		return true;
	}

	public boolean eggLandingSounds() {
		return true;
	}

	public boolean enderPearlLandingSounds() {
		return true;
	}

	public boolean potionLandingSounds() {
		return true;
	}

	public int stoneChestTimer() {
		return DefaultBlockConfig.StoneChestConfig.STONE_CHEST_TIMER;
	}

	public boolean shriekerGargling() {
		return DefaultBlockConfig.SHRIEKER_GARGLING;
	}

	public boolean soulFireSounds() {
		return DefaultBlockConfig.SOUL_FIRE_SOUNDS;
	}

	// ENTITY

	public boolean unpassableRail() {
		return DefaultEntityConfig.UNPASSABLE_RAIL;
	}

	public boolean keyframeAllayDance() {
		return DefaultEntityConfig.AllayConfig.KEYFRAME_ALLAY_DANCE;
	}

	public boolean angerLoopSound() {
		return DefaultEntityConfig.EnderManConfig.ANGER_LOOP_SOUND;
	}

	public boolean movingStareSound() {
		return DefaultEntityConfig.EnderManConfig.MOVING_STARE_SOUND;
	}

	public int fireflySpawnCap() {
		return DefaultEntityConfig.FireflyConfig.FIREFLY_SPAWN_CAP;
	}

	public int jellyfishSpawnCap() {
		return DefaultEntityConfig.JellyfishConfig.JELLYFISH_SPAWN_CAP;
	}

	public int tumbleweedSpawnCap() {
		return DefaultEntityConfig.TumbleweedConfig.TUMBLEWEED_SPAWN_CAP;
	}

	public boolean leashedTumbleweed() {
		return DefaultEntityConfig.TumbleweedConfig.LEASHED_TUMBLEWEED;
	}

	public boolean wardenAttacksImmediately() {
		return DefaultEntityConfig.WardenConfig.WARDEN_ATTACKS_IMMEDIATELY;
	}

	public boolean wardenCustomTendrils() {
		return DefaultEntityConfig.WardenConfig.WARDEN_CUSTOM_TENDRILS;
	}

	public boolean wardenDyingAnimation() {
		return DefaultEntityConfig.WardenConfig.WARDEN_DYING_ANIMATION;
	}

	public boolean wardenEmergesFromCommand() {
		return DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_COMMAND;
	}

	public boolean wardenEmergesFromEgg() {
		return DefaultEntityConfig.WardenConfig.WARDEN_EMERGES_FROM_EGG;
	}

	public boolean wardenSwimAnimation() {
		return DefaultEntityConfig.WardenConfig.WARDEN_SWIM_ANIMATION;
	}

	public boolean wardenBedrockSniff() {
		return DefaultEntityConfig.WardenConfig.WARDEN_BEDROCK_SNIFF;
	}

	// ITEM

	public boolean hornCanSummonWarden() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
	}

	public int hornLifespan() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_LIFESPAN;
	}

	public int hornMobDamage() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
	}

	public int hornPlayerDamage() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
	}

	public boolean hornShattersGlass() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
	}

	public float hornSizeMultiplier() {
		return DefaultItemConfig.AncientHornConfig.ANCIENT_HORN_SIZE_MULTIPLIER;
	}

	public boolean projectileBreakParticles() {
		return DefaultItemConfig.PROJECTILE_BREAK_PARTICLES;
	}

	public boolean itemCooldownsSave() {
		return FrozenLibConfigValues.DefaultFrozenLibConfigValues.SAVE_ITEM_COOLDOWNS;
	}

	// WORLDGEN

	public boolean betaBeaches() {
		return DefaultWorldgenConfig.BETA_BEACHES;
	}

	public boolean modifyJunglePlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
	}

	public boolean modifySwampPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_SWAMP_PLACEMENT;
	}

	public boolean modifyMangroveSwampPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
	}

	public boolean modifyWindsweptSavannaPlacement() {
		return DefaultWorldgenConfig.BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
	}

	public boolean generateCypressWetlands() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_CYPRESS_WETLANDS;
	}

	public boolean generateJellyfishCaves() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_JELLYFISH_CAVES;
	}

	public boolean generateMixedForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_MIXED_FOREST;
	}

	public boolean generateOasis() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OASIS;
	}

	public boolean generateWarmRiver() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_WARM_RIVER;
	}

	public boolean generateBirchTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_TAIGA;
	}

	public boolean generateOldGrowthBirchTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_BIRCH_TAIGA;
	}

	public boolean generateFlowerField() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_FLOWER_FIELD;
	}

	public boolean generateAridSavanna() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_SAVANNA;
	}

	public boolean generateParchedForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_PARCHED_FOREST;
	}

	public boolean generateAridForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_ARID_FOREST;
	}

	public boolean generateOldGrowthSnowyTaiga() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA;
	}

	public boolean generateBirchJungle() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_BIRCH_JUNGLE;
	}

	public boolean generateSparseBirchJungle() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_SPARSE_BIRCH_JUNGLE;
	}

	public boolean generateOldGrowthDarkForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_OLD_GROWTH_DARK_FOREST;
	}

	public boolean generateDarkBirchForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_DARK_BIRCH_FOREST;
	}

	public boolean generateSemiBirchForest() {
		return DefaultWorldgenConfig.BiomeGeneration.GENERATE_SEMI_BIRCH_FOREST;
	}

	public boolean dyingTrees() {
		return DefaultWorldgenConfig.DYING_TREES;
	}

	public boolean fallenLogs() {
		return DefaultWorldgenConfig.FALLEN_LOGS;
	}

	public boolean wildTrees() {
		return DefaultWorldgenConfig.WILDER_WILD_TREE_GEN;
	}

	public boolean wildGrass() {
		return DefaultWorldgenConfig.WILDER_WILD_GRASS_GEN;
	}

	public boolean newWitchHuts() {
		return DefaultWorldgenConfig.NEW_WITCH_HUTS;
	}

	// MISC

	public boolean deepDarkAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
	}

	public boolean dripstoneCavesAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
	}

	public boolean lushCavesAmbience() {
		return DefaultMiscConfig.BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
	}

	public boolean wilderForestMusic() {
		return DefaultMiscConfig.BiomeMusicConfig.WILDER_FOREST_MUSIC;
	}

	public boolean cloudMovement() {
		return DefaultMiscConfig.CLOUD_MOVEMENT;
	}
}
