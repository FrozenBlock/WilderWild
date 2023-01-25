package net.frozenblock.wilderwild.misc.config;

public final class ClothConfigCloserInteractionHandler {

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
	private static final EntityConfig.WardenConfig WARDEN = ENTITY.warden;
	private static final EntityConfig.FireflyConfig FIREFLY = ENTITY.firefly;
	private static final EntityConfig.JellyfishConfig JELLYFISH = ENTITY.jellyfish;
	private static final EntityConfig.TumbleweedConfig TUMBLEWEED = ENTITY.tumbleweed;

	// BLOCK

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

	public static boolean enderPearlLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.enderPearlLandingSounds;
	}

	public static boolean potionLandingSounds() {
		return PROJECTILE_LANDING_SOUNDS.potionLandingSounds;
	}

	public static int stoneChestTimer() {
		return STONE_CHEST.stoneChestTimer;
	}

	public static boolean shriekerGargling() {
		return BLOCK.shriekerGargling;
	}

	public static boolean soulFireSounds() {
		return BLOCK.soulFireSounds;
	}

	// ENTITY

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

	public static boolean wardenAttacksImmediately() {
		return WARDEN.wardenAttacksImmediately;
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

	public static boolean wardenBedrockSniff() {
		return WARDEN.wardenBedrockSniff;
	}

	// ITEM

	public static boolean hornCanSummonWarden() {
		return ANCIENT_HORN.ancientHornCanSummonWarden;
	}

	public static int hornLifespan() {
		return ANCIENT_HORN.ancientHornLifespan;
	}

	public static boolean billboardTendrils() {
		return BLOCK.billboardTendrils;
	}

	public static boolean cloudMovement() {
		return MISC.cloudMovement;
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

	// WORLDGEN

	public static boolean betaBeaches() {
		return WilderWildConfig.get().worldgen.betaBeaches;
	}

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

	public static boolean generateCypressWetlands() {
		return BIOME_GENERATION.generateCypressWetlands;
	}

	public static boolean generateMixedForest() {
		return BIOME_GENERATION.generateMixedForest;
	}

	public static boolean generateJellyfishCaves() {
		return BIOME_GENERATION.generateJellyfishCaves;
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

	public static boolean cypressWitchHuts() {
		return WORLDGEN.cypressWitchHuts;
	}

	public static boolean generateOasis() {
		return BIOME_GENERATION.generateOasis;
	}

	public static boolean generateWarmRiver() {
		return BIOME_GENERATION.generateWarmRiver;
	}

	public static boolean generateBirchTaiga() {
		return BIOME_GENERATION.generateBirchTaiga;
	}

	public static boolean generateFlowerField() {
		return BIOME_GENERATION.generateFlowerField;
	}

	public static boolean generateAridSavanna() {
		return BIOME_GENERATION.generateAridSavanna;
	}

	public static boolean generateParchedForest() {
		return BIOME_GENERATION.generateParchedForest;
	}

	public static boolean generateAridForest() {
		return BIOME_GENERATION.generateAridForest;
	}

	public static boolean generateOldGrowthSnowyTaiga() {
		return BIOME_GENERATION.generateOldGrowthSnowyTaiga;
	}

	// MISC

	public static boolean deepDarkAmbience() {
		return BIOME_AMBIENCE.deepDarkAmbience;
	}

	public static boolean dripstoneCavesAmbience() {
		return BIOME_AMBIENCE.dripstoneCavesAmbience;
	}

	public static boolean lushCavesAmbience() {
		return BIOME_AMBIENCE.lushCavesAmbience;
	}

	public static boolean birchForestMusic() {
		return BIOME_MUSIC.birchForestMusic;
	}

	public static boolean flowerForestMusic() {
		return BIOME_MUSIC.flowerForestMusic;
	}

	public static boolean leashedTumbleweed() {
		return TUMBLEWEED.leashedTumbleweed;
	}

}
