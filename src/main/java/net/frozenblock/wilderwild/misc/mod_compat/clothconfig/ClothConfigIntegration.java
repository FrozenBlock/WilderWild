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

import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfig;
import net.frozenblock.lib.config.frozenlib_config.FrozenLibConfigCategory;
import net.frozenblock.wilderwild.misc.config.BlockConfig;
import net.frozenblock.wilderwild.misc.config.EntityConfig;
import net.frozenblock.wilderwild.misc.config.ItemConfig;
import net.frozenblock.wilderwild.misc.config.MiscConfig;
import net.frozenblock.wilderwild.misc.config.WilderWildConfig;
import net.frozenblock.wilderwild.misc.config.WorldgenConfig;

public class ClothConfigIntegration extends AbstractClothConfigIntegration {
	public ClothConfigIntegration() {
		super();
	}

	private static final FrozenLibConfigCategory FROZENLIB = FrozenLibConfig.get().config;
	private static final BlockConfig BLOCK = WilderWildConfig.get().block;
	private static final EntityConfig ENTITY = WilderWildConfig.get().entity;
	private static final ItemConfig ITEM = WilderWildConfig.get().item;
	private static final WorldgenConfig WORLDGEN = WilderWildConfig.get().worldgen;
	private static final MiscConfig MISC = WilderWildConfig.get().misc;

	private static final EntityConfig.AllayConfig ALLAY = ENTITY.allay;
	private static final ItemConfig.AncientHornConfig ANCIENT_HORN = ITEM.ancientHorn;
	private static final MiscConfig.BiomeAmbienceConfig BIOME_AMBIENCE = MISC.biomeAmbience;
	private static final MiscConfig.BiomeMusicConfig BIOME_MUSIC = MISC.biomeMusic;
	private static final WorldgenConfig.BiomeGeneration BIOME_GENERATION = WORLDGEN.biomeGeneration;
	private static final WorldgenConfig.BiomePlacement BIOME_PLACEMENT = WORLDGEN.biomePlacement;
	private static final BlockConfig.BlockSoundsConfig BLOCK_SOUNDS = BLOCK.blockSounds;
	private static final EntityConfig.EnderManConfig ENDER_MAN = ENTITY.enderMan;
	private static final ItemConfig.ProjectileLandingSoundsConfig PROJECTILE_LANDING_SOUNDS = WilderWildConfig.get().item.projectileLandingSounds;
	private static final BlockConfig.StoneChestConfig STONE_CHEST = BLOCK.stoneChest;
	private static final BlockConfig.TermiteConfig TERMITE = BLOCK.termite;
	private static final EntityConfig.WardenConfig WARDEN = ENTITY.warden;
	private static final EntityConfig.FireflyConfig FIREFLY = ENTITY.firefly;
	private static final EntityConfig.JellyfishConfig JELLYFISH = ENTITY.jellyfish;
	private static final EntityConfig.TumbleweedConfig TUMBLEWEED = ENTITY.tumbleweed;

	@Override
	public void init() {}

	// BLOCK

	@Override
	public boolean mcLiveSensorTendrils() {
		return BLOCK.mcLiveSensorTendrils;
	}

	@Override
	public boolean billboardTendrils() {
		return BLOCK.billboardTendrils;
	}

	@Override
	public boolean mesogleaLiquid() {
		return BLOCK.mesogleaLiquid;
	}

	@Override
	public boolean pollenParticles() {
		return BLOCK.pollenParticles;
	}

	@Override
	public boolean cactusPlacement() {
		return BLOCK.cactusPlacement;
	}

	@Override
	public boolean cactusSounds() {
		return BLOCK_SOUNDS.cactusSounds;
	}

	@Override
	public boolean claySounds() {
		return BLOCK_SOUNDS.claySounds;
	}

	@Override
	public boolean coarseDirtSounds() {
		return BLOCK_SOUNDS.coarseDirtSounds;
	}

	@Override
	public boolean cobwebSounds() {
		return BLOCK_SOUNDS.cobwebSounds;
	}

	@Override
	public boolean deadBushSounds() {
		return BLOCK_SOUNDS.deadBushSounds;
	}

	@Override
	public boolean flowerSounds() {
		return BLOCK_SOUNDS.flowerSounds;
	}

	@Override
	public boolean gravelSounds() {
		return BLOCK_SOUNDS.gravelSounds;
	}

	@Override
	public boolean frostedIceSounds() {
		return BLOCK_SOUNDS.frostedIceSounds;
	}

	@Override
	public boolean leafSounds() {
		return BLOCK_SOUNDS.leafSounds;
	}

	@Override
	public boolean saplingSounds() {
		return BLOCK_SOUNDS.saplingSounds;
	}

	@Override
	public boolean lilyPadSounds() {
		return BLOCK_SOUNDS.lilyPadSounds;
	}

	@Override
	public boolean mushroomBlockSounds() {
		return BLOCK_SOUNDS.mushroomBlockSounds;
	}

	@Override
	public boolean podzolSounds() {
		return BLOCK_SOUNDS.podzolSounds;
	}

	@Override
	public boolean reinforcedDeepslateSounds() {
		return BLOCK_SOUNDS.reinforcedDeepslateSounds;
	}

	@Override
	public boolean sandstoneSounds() {
		return BLOCK_SOUNDS.sandstoneSounds;
	}

	@Override
	public boolean sugarCaneSounds() {
		return BLOCK_SOUNDS.sugarCaneSounds;
	}

	@Override
	public boolean witherRoseSounds() {
		return BLOCK_SOUNDS.witherRoseSounds;
	}

	@Override
	public boolean snowballLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.snowballLandingSounds;
	}

	@Override
	public boolean eggLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.eggLandingSounds;
	}

	@Override
	public boolean enderPearlLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.enderPearlLandingSounds;
	}

	@Override
	public boolean potionLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.potionLandingSounds;
	}

	@Override
	public int stoneChestTimer() {
		return STONE_CHEST.stoneChestTimer;
	}

	@Override
	public boolean termitesOnlyEatNaturalBlocks() {
		return TERMITE.onlyEatNaturalBlocks;
	}

	@Override
	public boolean shriekerGargling() {
		return BLOCK.shriekerGargling;
	}

	@Override
	public boolean soulFireSounds() {
		return BLOCK.soulFireSounds;
	}

	// ENTITY

	@Override
	public boolean unpassableRail() {
		return ENTITY.unpassableRail;
	}

	@Override
	public boolean keyframeAllayDance() {
		return ALLAY.keyframeAllayDance;
	}

	@Override
	public boolean angerLoopSound() {
		return ENDER_MAN.angerLoopSound;
	}

	@Override
	public boolean movingStareSound() {
		return ENDER_MAN.movingStareSound;
	}

	@Override
	public int fireflySpawnCap() {
		return FIREFLY.fireflySpawnCap;
	}

	@Override
	public int jellyfishSpawnCap() {
		return JELLYFISH.jellyfishSpawnCap;
	}

	@Override
	public int tumbleweedSpawnCap() {
		return TUMBLEWEED.tumbleweedSpawnCap;
	}

	@Override
	public boolean leashedTumbleweed() {
		return TUMBLEWEED.leashedTumbleweed;
	}

	@Override
	public boolean wardenAttacksImmediately() {
		return WARDEN.wardenAttacksImmediately;
	}

	@Override
	public boolean wardenCustomTendrils() {
		return WARDEN.wardenCustomTendrils;
	}

	@Override
	public boolean wardenDyingAnimation() {
		return WARDEN.wardenDyingAnimation;
	}

	@Override
	public boolean wardenEmergesFromCommand() {
		return WARDEN.wardenEmergesFromCommand;
	}

	@Override
	public boolean wardenEmergesFromEgg() {
		return WARDEN.wardenEmergesFromEgg;
	}

	@Override
	public boolean wardenSwimAnimation() {
		return WARDEN.wardenSwimAnimation;
	}

	@Override
	public boolean wardenBedrockSniff() {
		return WARDEN.wardenBedrockSniff;
	}

	// ITEM

	@Override
	public boolean hornCanSummonWarden() {
		return ANCIENT_HORN.ancientHornCanSummonWarden;
	}

	@Override
	public int hornLifespan() {
		return ANCIENT_HORN.ancientHornLifespan;
	}

	@Override
	public int hornMobDamage() {
		return ANCIENT_HORN.ancientHornMobDamage;
	}

	@Override
	public int hornPlayerDamage() {
		return ANCIENT_HORN.ancientHornPlayerDamage;
	}

	@Override
	public boolean hornShattersGlass() {
		return ANCIENT_HORN.ancientHornShattersGlass;
	}

	@Override
	public float hornSizeMultiplier() {
		return ANCIENT_HORN.ancientHornSizeMultiplier;
	}

	@Override
	public boolean projectileBreakParticles() {
		return ITEM.projectileBreakParticles;
	}

	@Override
	public boolean itemCooldownsSave() {
		return FROZENLIB.saveItemCooldowns;
	}

	@Override
	public boolean restrictInstrumentSound() {
		return ITEM.restrictInstrumentSound;
	}

	// WORLDGEN

	@Override
	public boolean betaBeaches() {
		return WORLDGEN.betaBeaches;
	}

	@Override
	public boolean modifyJunglePlacement() {
		return BIOME_PLACEMENT.modifyJunglePlacement;
	}

	@Override
	public boolean modifySwampPlacement() {
		return BIOME_PLACEMENT.modifySwampPlacement;
	}

	@Override
	public boolean modifyMangroveSwampPlacement() {
		return BIOME_PLACEMENT.modifyMangroveSwampPlacement;
	}

	@Override
	public boolean modifyWindsweptSavannaPlacement() {
		return BIOME_PLACEMENT.modifyWindsweptSavannaPlacement;
	}

	@Override
	public boolean generateCypressWetlands() {
		return BIOME_GENERATION.generateCypressWetlands;
	}

	@Override
	public boolean generateJellyfishCaves() {
		return BIOME_GENERATION.generateJellyfishCaves;
	}

	@Override
	public boolean generateMixedForest() {
		return BIOME_GENERATION.generateMixedForest;
	}

	@Override
	public boolean generateOasis() {
		return BIOME_GENERATION.generateOasis;
	}

	@Override
	public boolean generateWarmRiver() {
		return BIOME_GENERATION.generateWarmRiver;
	}

	@Override
	public boolean generateBirchTaiga() {
		return BIOME_GENERATION.generateBirchTaiga;
	}

	@Override
	public boolean generateOldGrowthBirchTaiga() {
		return BIOME_GENERATION.generateOldGrowthBirchTaiga;
	}

	@Override
	public boolean generateFlowerField() {
		return BIOME_GENERATION.generateFlowerField;
	}

	@Override
	public boolean generateAridSavanna() {
		return BIOME_GENERATION.generateAridSavanna;
	}

	@Override
	public boolean generateParchedForest() {
		return BIOME_GENERATION.generateParchedForest;
	}

	@Override
	public boolean generateAridForest() {
		return BIOME_GENERATION.generateAridForest;
	}

	@Override
	public boolean generateOldGrowthSnowyTaiga() {
		return BIOME_GENERATION.generateOldGrowthSnowyTaiga;
	}

	@Override
	public boolean generateBirchJungle() {
		return BIOME_GENERATION.generateBirchJungle;
	}

	@Override
	public boolean generateSparseBirchJungle() {
		return BIOME_GENERATION.generateSparseBirchJungle;
	}

	@Override
	public boolean generateOldGrowthDarkForest() {
		return BIOME_GENERATION.generateOldGrowthDarkForest;
	}

	@Override
	public boolean generateDarkBirchForest() {
		return BIOME_GENERATION.generateDarkBirchForest;
	}

	@Override
	public boolean generateSemiBirchForest() {
		return BIOME_GENERATION.generateSemiBirchForest;
	}

	@Override
	public boolean generateTemperateRainforest() {
		return BIOME_GENERATION.generateTemperateRainforest;
	}

	@Override
	public boolean generateRainforest() {
		return BIOME_GENERATION.generateRainforest;
	}

	@Override
	public boolean generateDarkTaiga() {
		return BIOME_GENERATION.generateDarkTaiga;
	}

	@Override
	public boolean dyingTrees() {
		return WORLDGEN.dyingTrees;
	}

	@Override
	public boolean fallenLogs() {
		return WORLDGEN.fallenLogs;
	}

	@Override
	public boolean snappedLogs() {
		return WORLDGEN.snappedLogs;
	}

	@Override
	public boolean wildTrees() {
		return WORLDGEN.wilderWildTreeGen;
	}

	@Override
	public boolean wildGrass() {
		return WORLDGEN.wilderWildGrassGen;
	}

	@Override
	public boolean wildFlowers() {
		return WORLDGEN.wilderWildFlowerGen;
	}

	@Override
	public boolean wildBushes() {
		return WORLDGEN.wilderWildBushGen;
	}

	@Override
	public boolean wildCacti() {
		return WORLDGEN.wilderWildCactusGen;
	}

	@Override
	public boolean wildMushrooms() {
		return WORLDGEN.wilderWildMushroomGen;
	}

	@Override
	public boolean tumbleweed() {
		return WORLDGEN.tumbleweed;
	}

	@Override
	public boolean surfaceDecoration() {
		return WORLDGEN.surfaceDecoration;
	}

	@Override
	public boolean termiteGen() {
		return WORLDGEN.termite;
	}

	@Override
	public boolean algae() {
		return WORLDGEN.algae;
	}

	@Override
	public boolean snowBelowTrees() {
		return WORLDGEN.snowBelowTrees;
	}

	@Override
	public boolean surfaceTransitions() {
		return WORLDGEN.surfaceTransitions;
	}

	@Override
	public boolean newWitchHuts() {
		return WORLDGEN.newWitchHuts;
	}

	// MISC

	@Override
	public boolean cloudMovement() {
		return MISC.cloudMovement;
	}

	@Override
	public float particleWindMovement() {
		return MISC.particleWindMovement / 100F;
	}

	@Override
	public boolean deepDarkAmbience() {
		return BIOME_AMBIENCE.deepDarkAmbience;
	}

	@Override
	public boolean dripstoneCavesAmbience() {
		return BIOME_AMBIENCE.dripstoneCavesAmbience;
	}

	@Override
	public boolean lushCavesAmbience() {
		return BIOME_AMBIENCE.lushCavesAmbience;
	}

	@Override
	public boolean wilderForestMusic() {
		return BIOME_MUSIC.wilderForestMusic;
	}

}
