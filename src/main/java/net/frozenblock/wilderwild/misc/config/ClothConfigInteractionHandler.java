package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig.*;

public final class ClothConfigInteractionHandler {

	// BLOCK



	// ENTITY



	// ITEM



	// WORLDGEN



	// MISC



	public static boolean betaBeaches() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.betaBeaches() : betaBeaches;
	}

	/*public static boolean modifyDesertPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyDesertPlacement() : modifyDesertPlacement;
	}

	public static boolean modifyBadlandsPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyBadlandsPlacement() :modifyBadlandsPlacement;
	}*/

	public static boolean modifyJunglePlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyJunglePlacement() : BiomePlacement.modifyJunglePlacement;
	}

	public static boolean modifySwampPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifySwampPlacement() : BiomePlacement.modifySwampPlacement;
	}

	public static boolean modifyMangroveSwampPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyMangroveSwampPlacement() : BiomePlacement.modifyMangroveSwampPlacement;
	}

	public static boolean modifyWindsweptSavannaPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyWindsweptSavannaPlacement() : BiomePlacement.modifyWindsweptSavannaPlacement;
	}

	public static boolean dyingTrees() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.dyingTrees() : dyingTrees;
	}

	public static boolean fallenLogs() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.fallenLogs() : fallenLogs;
	}

	public static boolean wildTrees() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wildTrees() : wilderWildTreeGen;
	}

	public static boolean wildGrass() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wildGrass() : wilderWildGrassGen;
	}

	public static boolean hornCanSummonWarden() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornCanSummonWarden() : AncientHornConfig.ancientHornCanSummonWarden;
	}

	public static int hornLifespan() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornLifespan() : AncientHornConfig.ancientHornLifespan;
	}

	public static int hornMobDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornMobDamage() : AncientHornConfig.ancientHornMobDamage;
	}

	public static int hornPlayerDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornPlayerDamage() : AncientHornConfig.ancientHornPlayerDamage;
	}

	public static boolean hornShattersGlass() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornShattersGlass() : AncientHornConfig.ancientHornShattersGlass;
	}

	public static boolean projectileBreakParticles() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.projectileBreakParticles() : projectileBreakParticles;
	}

	public static boolean mcLiveSensorTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.mcLiveSensorTendrils() : mcLiveSensorTendrils;
	}

	public static boolean cactusSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.cactusSounds() : BlockSoundsConfig.cactusSounds;
	}

	public static boolean claySounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.claySounds() : BlockSoundsConfig.claySounds;
	}

	public static boolean coarseDirtSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.coarseDirtSounds() : BlockSoundsConfig.coarseDirtSounds;
	}

	public static boolean cobwebSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.cobwebSounds() : BlockSoundsConfig.cobwebSounds;
	}

	public static boolean deadBushSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.deadBushSounds() : BlockSoundsConfig.deadBushSounds;
	}

	public static boolean flowerSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.flowerSounds() : BlockSoundsConfig.flowerSounds;
	}

	public static boolean gravelSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.gravelSounds() : BlockSoundsConfig.gravelSounds;
	}

	public static boolean frostedIceSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.frostedIceSounds() : BlockSoundsConfig.frostedIceSounds;
	}

	public static boolean leafSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.leafSounds() : BlockSoundsConfig.leafSounds;
	}

	public static boolean lilyPadSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.lilyPadSounds() : BlockSoundsConfig.lilyPadSounds;
	}

	public static boolean mushroomBlockSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.mushroomBlockSounds() : BlockSoundsConfig.mushroomBlockSounds;
	}

	public static boolean podzolSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.podzolSounds() : BlockSoundsConfig.podzolSounds;
	}

	public static boolean reinforcedDeepslateSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.reinforcedDeepslateSounds() : BlockSoundsConfig.reinforcedDeepslateSounds;
	}

	public static boolean sugarCaneSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.sugarCaneSounds() : BlockSoundsConfig.sugarCaneSounds;
	}

	public static boolean witherRoseSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.witherRoseSounds() : BlockSoundsConfig.witherRoseSounds;
	}

	public static int stoneChestTimer() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.stoneChestTimer() : StoneChestConfig.stoneChestTimer;
	}

	public static boolean unpassableRail() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.unpassableRail() : unpassableRail;
	}

	public static boolean keyframeAllayDance() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.keyframeAllayDance() : AllayConfig.keyframeAllayDance;
	}

	public static boolean angerLoopSound() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.angerLoopSound() : EnderManConfig.angerLoopSound;
	}

	public static boolean movingStareSound() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.movingStareSound() : EnderManConfig.movingStareSound;
	}

	public static int fireflySpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.fireflySpawnCap() : FireflyConfig.fireflySpawnCap;
	}

	public static int jellyfishSpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.jellyfishSpawnCap() : JellyfishConfig.jellyfishSpawnCap;
	}

	public static boolean wardenAttacksInstantly() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenAttacksInstantly() : WardenConfig.wardenAttacksInstantly;
	}

	public static boolean wardenCustomTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenCustomTendrils() : WardenConfig.wardenCustomTendrils;
	}

	public static boolean wardenDyingAnimation() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenDyingAnimation() : WardenConfig.wardenDyingAnimation;
	}

	public static boolean wardenEmergesFromCommand() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenEmergesFromCommand() : WardenConfig.wardenEmergesFromCommand;
	}

	public static boolean wardenEmergesFromEgg() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenEmergesFromEgg() : WardenConfig.wardenEmergesFromEgg;
	}

	public static boolean wardenSwimAnimation() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenSwimAnimation() : WardenConfig.wardenSwimAnimation;
	}

	public static boolean wardenBedrockSniff() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenBedrockSniff() : WardenConfig.wardenBedrockSniff;
	}

	public static boolean shriekerGargling() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.shriekerGargling() : shriekerGargling;
	}

	public static boolean soulFireSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.soulFireSounds() : soulFireSounds;
	}

	public static boolean deepDarkAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.deepDarkAmbience() : BiomeAmbienceConfig.deepDarkAmbience;
	}

	public static boolean dripstoneCavesAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.dripstoneCavesAmbience() : BiomeAmbienceConfig.dripstoneCavesAmbience;
	}

	public static boolean lushCavesAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.lushCavesAmbience() : BiomeAmbienceConfig.lushCavesAmbience;
	}

	public static boolean birchForestMusic() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.birchForestMusic() : BiomeMusicConfig.birchForestMusic;
	}

	public static boolean flowerForestMusic() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.flowerForestMusic() : BiomeMusicConfig.flowerForestMusic;
	}
}
