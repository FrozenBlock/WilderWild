package net.frozenblock.wilderwild.misc.config;

public final class ClothConfigCloserInteractionHandler {

	private static final BlockConfig.BlockSoundsConfig BLOCK_SOUNDS = WilderWildConfig.get().block.blockSounds;

    public static boolean betaBeaches() {
        return WilderWildConfig.get().worldgen.betaBeaches;
    }

    /*
        public static boolean modifyDesertPlacement() {
            return WilderWildConfig.get().worldgen.biomePlacement.modifyDesertPlacement;
        }

        public static boolean modifyBadlandsPlacement() {
            return WilderWildConfig.get().worldgen.biomePlacement.modifyBadlandsPlacement;
        }
    */
    public static boolean modifyJunglePlacement() {
        return WilderWildConfig.get().worldgen.biomePlacement.modifyJunglePlacement;
    }

    public static boolean modifySwampPlacement() {
        return WilderWildConfig.get().worldgen.biomePlacement.modifySwampPlacement;
    }

    public static boolean modifyMangroveSwampPlacement() {
        return WilderWildConfig.get().worldgen.biomePlacement.modifyMangroveSwampPlacement;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        return WilderWildConfig.get().worldgen.biomePlacement.modifyWindsweptSavannaPlacement;
    }

    public static boolean dyingTrees() {
        return WilderWildConfig.get().worldgen.dyingTrees;
    }

    public static boolean fallenLogs() {
        return WilderWildConfig.get().worldgen.fallenLogs;
    }

    public static boolean wildTrees() {
        return WilderWildConfig.get().worldgen.wilderWildTreeGen;
    }

    public static boolean wildGrass() {
        return WilderWildConfig.get().worldgen.wilderWildGrassGen;
    }

    public static boolean hornShattersGlass() {
        return WilderWildConfig.get().item.ancientHorn.ancientHornShattersGlass;
    }

    public static boolean hornCanSummonWarden() {
        return WilderWildConfig.get().item.ancientHorn.ancientHornCanSummonWarden;
    }

    public static boolean projectileBreakParticles() {
        return WilderWildConfig.get().item.projectileBreakParticles;
    }

    public static boolean mcLiveSensorTendrils() {
        return WilderWildConfig.get().block.mcLiveSensorTendrils;
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

	public static int stoneChestTimer() {
		return WilderWildConfig.get().block.stoneChest.stoneChestTimer;
	}

    public static boolean unpassableRail() {
        return WilderWildConfig.get().entity.unpassableRail;
    }

	public static boolean keyframeAllayDance() {
		return WilderWildConfig.get().entity.allay.keyframeAllayDance;
	}

    public static boolean wardenCustomTendrils() {
        return WilderWildConfig.get().entity.warden.wardenCustomTendrils;
    }

    public static boolean wardenDyingAnimation() {
        return WilderWildConfig.get().entity.warden.wardenDyingAnimation;
    }

    public static boolean wardenEmergesFromEgg() {
        return WilderWildConfig.get().entity.warden.wardenEmergesFromEgg;
    }

    public static boolean wardenSwimAnimation() {
        return WilderWildConfig.get().entity.warden.wardenSwimAnimation;
    }

    public static boolean shriekerGargling() {
        return WilderWildConfig.get().block.shriekerGargling;
    }

    public static boolean soulFireSounds() {
        return WilderWildConfig.get().block.soulFireSounds;
    }

}
