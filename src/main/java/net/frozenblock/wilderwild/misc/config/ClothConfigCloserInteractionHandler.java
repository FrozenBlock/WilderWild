package net.frozenblock.wilderwild.misc.config;

public final class ClothConfigCloserInteractionHandler {

	private static final BlockConfig BLOCK = WilderWildConfig.get().block;
	private static final EntityConfig ENTITY = WilderWildConfig.get().entity;
	private static final ItemConfig ITEM = WilderWildConfig.get().item;
	private static final WorldgenConfig WORLDGEN = WilderWildConfig.get().worldgen;

	private static final EntityConfig.AllayConfig ALLAY = ENTITY.allay;
	private static final ItemConfig.AncientHornConfig ANCIENT_HORN = ITEM.ancientHorn;
	private static final WorldgenConfig.BiomePlacement BIOME_PLACEMENT = WORLDGEN.biomePlacement;
	private static final BlockConfig.BlockSoundsConfig BLOCK_SOUNDS = BLOCK.blockSounds;
	private static final EntityConfig.EnderManConfig ENDER_MAN = ENTITY.enderMan;
	private static final ItemConfig.ProjectileLandingSoundsConfig PROJECTILE_LANDING_SOUNDS = WilderWildConfig.get().item.projectileLandingSounds;
	private static final BlockConfig.StoneChestConfig STONE_CHEST = BLOCK.stoneChest;
	private static final EntityConfig.WardenConfig WARDEN = ENTITY.warden;
	private static final EntityConfig.FireflyConfig FIREFLY = ENTITY.firefly;
	private static final EntityConfig.JellyfishConfig JELLYFISH = ENTITY.jellyfish;
	private static final EntityConfig.TumbleweedConfig TUMBLEWEED = ENTITY.tumbleweed;

	public static boolean betaBeaches() {
		return WilderWildConfig.get().worldgen.betaBeaches;
	}

	/*
		public static boolean modifyDesertPlacement() {
			return BIOME_PLACEMENT.modifyDesertPlacement;
		}

		public static boolean modifyBadlandsPlacement() {
			return BIOME_PLACEMENT.modifyBadlandsPlacement;
		}
	*/
	public static boolean modifyJunglePlacement() {
		return BIOME_PLACEMENT.modifyJunglePlacement;
	}

	public static boolean modifySwampPlacement() {
		return BIOME_PLACEMENT.modifySwampPlacement;
	}

	public static boolean modifyMangroveSwampPlacement() {
		return BIOME_PLACEMENT.modifyMangroveSwampPlacement;
	}

	public static boolean modifyWindsweptSavannaPlacement() {
		return BIOME_PLACEMENT.modifyWindsweptSavannaPlacement;
	}

	public static boolean dyingTrees() {
		return WORLDGEN.dyingTrees;
	}

	public static boolean fallenLogs() {
		return WORLDGEN.fallenLogs;
	}

	public static boolean wildTrees() {
		return WORLDGEN.wilderWildTreeGen;
	}

	public static boolean wildGrass() {
		return WORLDGEN.wilderWildGrassGen;
	}

	public static boolean hornCanSummonWarden() {
		return ANCIENT_HORN.ancientHornCanSummonWarden;
	}

	public static int hornLifespan() {
		return ANCIENT_HORN.ancientHornLifespan;
	}

	public static int hornMobDamage() {
		return ANCIENT_HORN.ancientHornMobDamage;
	}

	public static int hornPlayerDamage() {
		return ANCIENT_HORN.ancientHornPlayerDamage;
	}

	public static boolean hornShattersGlass() {
		return ANCIENT_HORN.ancientHornShattersGlass;
	}

	public static boolean projectileBreakParticles() {
		return ITEM.projectileBreakParticles;
	}

	public static boolean mcLiveSensorTendrils() {
		return BLOCK.mcLiveSensorTendrils;
	}

	public static boolean cactusSounds() {
		return BLOCK_SOUNDS.cactusSounds;
	}

	public static boolean claySounds() {
		return BLOCK_SOUNDS.claySounds;
	}

	public static boolean coarseDirtSounds() {
		return BLOCK_SOUNDS.coarseDirtSounds;
	}

	public static boolean cobwebSounds() {
		return BLOCK_SOUNDS.cobwebSounds;
	}

	public static boolean deadBushSounds() {
		return BLOCK_SOUNDS.deadBushSounds;
	}

	public static boolean flowerSounds() {
		return BLOCK_SOUNDS.flowerSounds;
	}

	public static boolean gravelSounds() {
		return BLOCK_SOUNDS.gravelSounds;
	}

	public static boolean frostedIceSounds() {
		return BLOCK_SOUNDS.frostedIceSounds;
	}

	public static boolean leafSounds() {
		return BLOCK_SOUNDS.leafSounds;
	}

	public static boolean lilyPadSounds() {
		return BLOCK_SOUNDS.lilyPadSounds;
	}

	public static boolean mushroomBlockSounds() {
		return BLOCK_SOUNDS.mushroomBlockSounds;
	}

	public static boolean podzolSounds() {
		return BLOCK_SOUNDS.podzolSounds;
	}

	public static boolean reinforcedDeepslateSounds() {
		return BLOCK_SOUNDS.reinforcedDeepslateSounds;
	}

	public static boolean sugarCaneSounds() {
		return BLOCK_SOUNDS.sugarCaneSounds;
	}

	public static boolean witherRoseSounds() {
		return BLOCK_SOUNDS.witherRoseSounds;
	}

	public static boolean snowballLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.snowballLandingSounds;
	}

	public static boolean eggLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.eggLandingSounds;
	}

	public static boolean enderpearlLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.enderpearlLandingSounds;
	}

	public static boolean potionLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.potionLandingSounds;
	}

	public static int stoneChestTimer() {
		return STONE_CHEST.stoneChestTimer;
	}

	public static boolean unpassableRail() {
		return ENTITY.unpassableRail;
	}

	public static int fireflySpawnCap() {
		return FIREFLY.fireflySpawnCap;
	}

	public static int jellyfishSpawnCap() {
		return JELLYFISH.jellyfishSpawnCap;
	}

	public static int tumbleweedSpawnCap() {
		return TUMBLEWEED.tumbleweedSpawnCap;
	}

	public static boolean keyframeAllayDance() {
		return ALLAY.keyframeAllayDance;
	}

	public static boolean angerLoopSound() {
		return ENDER_MAN.angerLoopSound;
	}

	public static boolean movingStareSound() {
		return ENDER_MAN.movingStareSound;
	}

	public static boolean wardenAttacksInstantly() {
		return WARDEN.wardenAttacksInstantly;
	}

	public static boolean wardenCustomTendrils() {
		return WARDEN.wardenCustomTendrils;
	}

	public static boolean wardenDyingAnimation() {
		return WARDEN.wardenDyingAnimation;
	}

	public static boolean wardenEmergesFromCommand() {
		return WARDEN.wardenEmergesFromCommand;
	}

	public static boolean wardenEmergesFromEgg() {
		return WARDEN.wardenEmergesFromEgg;
	}

	public static boolean wardenSwimAnimation() {
		return WARDEN.wardenSwimAnimation;
	}

	public static boolean shriekerGargling() {
		return BLOCK.shriekerGargling;
	}

	public static boolean soulFireSounds() {
		return BLOCK.soulFireSounds;
	}

	public static boolean billboardTendrils() {
		return BLOCK.billboardTendrils;
	}

	public static boolean leashedTumbleweed() {
		return TUMBLEWEED.canBeLeashed;
	}

}
