package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.lib.FrozenBools;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultBlockConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultEntityConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultItemConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultMiscConfig.*;
import static net.frozenblock.wilderwild.misc.config.defaultconfig.DefaultWorldgenConfig.*;

public final class ClothConfigInteractionHandler {

	public static boolean betaBeaches() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.betaBeaches() : BETA_BEACHES;
	}

	public static boolean modifyJunglePlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyJunglePlacement() : BiomePlacement.MODIFY_JUNGLE_PLACEMENT;
	}

	public static boolean modifySwampPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifySwampPlacement() : BiomePlacement.MODIFY_SWAMP_PLACEMENT;
	}

	public static boolean modifyMangroveSwampPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyMangroveSwampPlacement() : BiomePlacement.MODIFY_MANGROVE_SWAMP_PLACEMENT;
	}

	public static boolean modifyWindsweptSavannaPlacement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.modifyWindsweptSavannaPlacement() : BiomePlacement.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT;
	}

	public static boolean generateCypressWetlands() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateCypressWetlands() : BiomeGeneration.GENERATE_CYPRESS_WETLANDS;
	}

	public static boolean generateJellyfishCaves() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateJellyfishCaves() : BiomeGeneration.GENERATE_JELLYFISH_CAVES;
	}

	public static boolean generateMixedForest() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateMixedForest() : BiomeGeneration.GENERATE_MIXED_FOREST;
	}

	public static boolean generateOasis() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateOasis() : BiomeGeneration.GENERATE_OASIS;
	}

	public static boolean generateWarmRiver() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateWarmRiver() : BiomeGeneration.GENERATE_WARM_RIVER;
	}

	public static boolean generateBirchTaiga() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateBirchTaiga() : BiomeGeneration.GENERATE_BIRCH_TAIGA;
	}

	public static boolean generateFlowerField() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateFlowerField() : BiomeGeneration.GENERATE_FLOWER_FIELD;
	}

	public static boolean generateAridSavanna() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateAridSavanna() : BiomeGeneration.GENERATE_ARID_SAVANNA;
	}

	public static boolean generateParchedForest() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateParchedForest() : BiomeGeneration.GENERATE_ARID_SAVANNA;
	}

	public static boolean generateAridForest() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateAridForest() : BiomeGeneration.GENERATE_ARID_FOREST;
	}
	public static boolean generateOldGrowthSnowyTaiga() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.generateOldGrowthSnowyTaiga() : BiomeGeneration.GENERATE_OLD_GROWTH_SNOWY_TAIGA;
	}

	public static boolean dyingTrees() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.dyingTrees() : DYING_TREES;
	}

	public static boolean fallenLogs() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.fallenLogs() : FALLEN_LOGS;
	}

	public static boolean wildTrees() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wildTrees() : WILDER_WILD_TREE_GEN;
	}

	public static boolean wildGrass() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wildGrass() : WILDER_WILD_GRASS_GEN;
	}

	public static boolean hornCanSummonWarden() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornCanSummonWarden() : AncientHornConfig.ANCIENT_HORN_CAN_SUMMON_WARDEN;
	}

	public static int hornLifespan() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornLifespan() : AncientHornConfig.ANCIENT_HORN_LIFESPAN;
	}

	public static int hornMobDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornMobDamage() : AncientHornConfig.ANCIENT_HORN_MOB_DAMAGE;
	}

	public static int hornPlayerDamage() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornPlayerDamage() : AncientHornConfig.ANCIENT_HORN_PLAYER_DAMAGE;
	}

	public static boolean hornShattersGlass() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.hornShattersGlass() : AncientHornConfig.ANCIENT_HORN_SHATTERS_GLASS;
	}

	public static boolean projectileBreakParticles() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.projectileBreakParticles() : PROJECTILE_BREAK_PARTICLES;
	}

	public static boolean mcLiveSensorTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.mcLiveSensorTendrils() : MC_LIVE_SENSOR_TENDRILS;
	}

	public static boolean cactusSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.cactusSounds() : BlockSoundsConfig.CACTUS_SOUNDS;
	}

	public static boolean claySounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.claySounds() : BlockSoundsConfig.CLAY_SOUNDS;
	}

	public static boolean coarseDirtSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.coarseDirtSounds() : BlockSoundsConfig.COARSE_DIRT_SOUNDS;
	}

	public static boolean cobwebSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.cobwebSounds() : BlockSoundsConfig.COBWEB_SOUNDS;
	}

	public static boolean deadBushSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.deadBushSounds() : BlockSoundsConfig.DEAD_BUSH_SOUNDS;
	}

	public static boolean flowerSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.flowerSounds() : BlockSoundsConfig.FLOWER_SOUNDS;
	}

	public static boolean gravelSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.gravelSounds() : BlockSoundsConfig.GRAVEL_SOUNDS;
	}

	public static boolean frostedIceSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.frostedIceSounds() : BlockSoundsConfig.FROSTED_ICE_SOUNDS;
	}

	public static boolean leafSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.leafSounds() : BlockSoundsConfig.LEAF_SOUNDS;
	}

	public static boolean lilyPadSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.lilyPadSounds() : BlockSoundsConfig.LILY_PAD_SOUNDS;
	}

	public static boolean mushroomBlockSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.mushroomBlockSounds() : BlockSoundsConfig.MUSHROOM_BLOCK_SOUNDS;
	}

	public static boolean podzolSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.podzolSounds() : BlockSoundsConfig.PODZOL_SOUNDS;
	}

	public static boolean reinforcedDeepslateSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.reinforcedDeepslateSounds() : BlockSoundsConfig.REINFORCED_DEEPSLATE_SOUNDS;
	}

	public static boolean sugarCaneSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.sugarCaneSounds() : BlockSoundsConfig.SUGAR_CANE_SOUNDS;
	}

	public static boolean witherRoseSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.witherRoseSounds() : BlockSoundsConfig.WITHER_ROSE_SOUNDS;
	}

	public static boolean snowballLandingSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.snowballLandingSounds() : ProjectileLandingSoundsConfig.SNOWBALL_LANDING_SOUNDS;
	}

	public static boolean eggLandingSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.eggLandingSounds() : ProjectileLandingSoundsConfig.EGG_LANDING_SOUNDS;
	}

	public static boolean enderPearlLandingSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.enderPearlLandingSounds() : ProjectileLandingSoundsConfig.ENDER_PEARL_LANDING_SOUNDS;
	}

	public static boolean potionLandingSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.potionLandingSounds() : ProjectileLandingSoundsConfig.POTION_LANDING_SOUNDS;
	}

	public static int stoneChestTimer() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.stoneChestTimer() : StoneChestConfig.STONE_CHEST_TIMER;
	}

	public static boolean unpassableRail() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.unpassableRail() : UNPASSABLE_RAIL;
	}

	public static boolean keyframeAllayDance() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.keyframeAllayDance() : AllayConfig.KEYFRAME_ALLAY_DANCE;
	}

	public static boolean angerLoopSound() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.angerLoopSound() : EnderManConfig.ANGER_LOOP_SOUND;
	}

	public static boolean movingStareSound() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.movingStareSound() : EnderManConfig.MOVING_STARE_SOUND;
	}

	public static int fireflySpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.fireflySpawnCap() : FireflyConfig.FIREFLY_SPAWN_CAP;
	}

	public static int jellyfishSpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.jellyfishSpawnCap() : JellyfishConfig.JELLYFISH_SPAWN_CAP;
	}

	public static int tumbleweedSpawnCap() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.tumbleweedSpawnCap() : TumbleweedConfig.TUMBLEWEED_SPAWN_CAP;
	}

	public static boolean leashedTumbleweed() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.leashedTumbleweed() : TumbleweedConfig.LEASHED_TUMBLEWEED;
	}

	public static boolean wardenAttacksImmediately() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenAttacksImmediately() : WardenConfig.WARDEN_ATTACKS_IMMEDIATELY;
	}

	public static boolean wardenCustomTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenCustomTendrils() : WardenConfig.WARDEN_CUSTOM_TENDRILS;
	}

	public static boolean wardenDyingAnimation() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenDyingAnimation() : WardenConfig.WARDEN_DYING_ANIMATION;
	}

	public static boolean wardenEmergesFromCommand() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenEmergesFromCommand() : WardenConfig.WARDEN_EMERGES_FROM_COMMAND;
	}

	public static boolean wardenEmergesFromEgg() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenEmergesFromEgg() : WardenConfig.WARDEN_EMERGES_FROM_EGG;
	}

	public static boolean wardenSwimAnimation() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenSwimAnimation() : WardenConfig.WARDEN_SWIM_ANIMATION;
	}

	public static boolean wardenBedrockSniff() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.wardenBedrockSniff() : WardenConfig.WARDEN_BEDROCK_SNIFF;
	}

	public static boolean shriekerGargling() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.shriekerGargling() : SHRIEKER_GARGLING;
	}

	public static boolean soulFireSounds() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.soulFireSounds() : SOUL_FIRE_SOUNDS;
	}

	public static boolean billboardTendrils() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.billboardTendrils() : BILLBOARD_TENDRILS;
	}

	public static boolean cloudMovement() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.cloudMovement() : CLOUD_MOVEMENT;
	}

	public static boolean deepDarkAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.deepDarkAmbience() : BiomeAmbienceConfig.DEEP_DARK_AMBIENCE;
	}

	public static boolean dripstoneCavesAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.dripstoneCavesAmbience() : BiomeAmbienceConfig.DRIPSTONE_CAVES_AMBIENCE;
	}

	public static boolean lushCavesAmbience() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.lushCavesAmbience() : BiomeAmbienceConfig.LUSH_CAVES_AMBIENCE;
	}

	public static boolean birchForestMusic() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.birchForestMusic() : BiomeMusicConfig.BIRCH_FOREST_MUSIC;
	}

	public static boolean flowerForestMusic() {
		return FrozenBools.HAS_CLOTH_CONFIG ? ClothConfigCloserInteractionHandler.flowerForestMusic() : BiomeMusicConfig.FLOWER_FOREST_MUSIC;
	}
}
