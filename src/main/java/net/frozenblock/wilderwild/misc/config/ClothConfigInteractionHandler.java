package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;

public final class ClothConfigInteractionHandler {

	public static boolean betaBeaches() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.betaBeaches();
	}

	/*public static boolean modifyDesertPlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifyDesertPlacement();
	}

	public static boolean modifyBadlandsPlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifyBadlandsPlacement();
	}*/

	public static boolean modifyJunglePlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifyJunglePlacement();
	}

	public static boolean modifySwampPlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifySwampPlacement();
	}

	public static boolean modifyMangroveSwampPlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifyMangroveSwampPlacement();
	}

	public static boolean modifyWindsweptSavannaPlacement() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.modifyWindsweptSavannaPlacement();
	}

	public static boolean dyingTrees() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.dyingTrees();
	}

	public static boolean fallenLogs() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.fallenLogs();
	}

	public static boolean wildTrees() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.wildTrees();
	}

	public static boolean wildGrass() {
		return !FrozenBools.HAS_CLOTH_CONFIG|| ClothConfigCloserInteractionHandler.wildGrass();
	}

	public static boolean hornCanSummonWarden() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.hornCanSummonWarden();
	}

	public static int hornLifespan() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornLifespan() : AncientHornProjectile.DEFAULT_LIFESPAN;
	}

	public static int hornMobDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornMobDamage() : 15;
	}

	public static int hornPlayerDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornPlayerDamage() : 22;
	}

	public static boolean hornShattersGlass() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.hornShattersGlass();
	}

	public static boolean projectileBreakParticles() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.projectileBreakParticles();
	}

	public static boolean mcLiveSensorTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.mcLiveSensorTendrils();
	}

	public static boolean cactusSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.cactusSounds();
	}

	public static boolean claySounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.claySounds();
	}

	public static boolean coarseDirtSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.coarseDirtSounds();
	}

	public static boolean cobwebSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.cobwebSounds();
	}

	public static boolean deadBushSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.deadBushSounds();
	}

	public static boolean flowerSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.flowerSounds();
	}

	public static boolean gravelSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.gravelSounds();
	}

	public static boolean frostedIceSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.frostedIceSounds();
	}

	public static boolean leafSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.leafSounds();
	}

	public static boolean lilyPadSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.lilyPadSounds();
	}

	public static boolean mushroomBlockSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.mushroomBlockSounds();
	}

	public static boolean podzolSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.podzolSounds();
	}

	public static boolean reinforcedDeepslateSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.reinforcedDeepslateSounds();
	}

	public static boolean sugarCaneSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.sugarCaneSounds();
	}

	public static boolean witherRoseSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.witherRoseSounds();
	}

	public static int stoneChestTimer() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.stoneChestTimer() : 100;
	}

	public static boolean unpassableRail() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.unpassableRail();
	}

	public static boolean keyframeAllayDance() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.keyframeAllayDance();
	}

	public static boolean angerLoopSound() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.angerLoopSound();
	}

	public static boolean movingStareSound() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.movingStareSound();
	}

	public static int fireflySpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.fireflySpawnCap() : 56;
	}

	public static int jellyfishSpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.jellyfishSpawnCap() : 30;
	}

	public static boolean wardenCustomTendrils() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.wardenCustomTendrils();
	}

	public static boolean wardenDyingAnimation() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.wardenDyingAnimation();
	}

	public static boolean wardenEmergesFromCommand() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.wardenEmergesFromCommand();
	}

	public static boolean wardenEmergesFromEgg() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.wardenEmergesFromEgg();
	}

	public static boolean wardenSwimAnimation() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.wardenSwimAnimation();
	}

	public static boolean shriekerGargling() {
		return FrozenBools.HAS_CLOTH_CONFIG && ClothConfigCloserInteractionHandler.shriekerGargling();
	}

	public static boolean soulFireSounds() {
		return !FrozenBools.HAS_CLOTH_CONFIG || ClothConfigCloserInteractionHandler.soulFireSounds();
	}
}
