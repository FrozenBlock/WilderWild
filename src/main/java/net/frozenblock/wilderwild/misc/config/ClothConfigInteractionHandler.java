package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.lib.FrozenBools;
import net.frozenblock.wilderwild.WilderWild;

public final class ClothConfigInteractionHandler {

    public static boolean betaBeaches() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.betaBeaches();
        }
        return true;
    }

    /*
        public static boolean modifyDesertPlacement() {
            if (FrozenBools.hasCloth) {
                return ClothConfigCloserInteractionHandler.modifyDesertPlacement();
            }
            return true;
        }

        public static boolean modifyBadlandsPlacement() {
            if (FrozenBools.hasCloth) {
                return ClothConfigCloserInteractionHandler.modifyBadlandsPlacement();
            }
            return true;
        }
    */
    public static boolean modifyJunglePlacement() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyJunglePlacement();
        }
        return true;
    }

    public static boolean modifySwampPlacement() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifySwampPlacement();
        }
        return true;
    }

    public static boolean modifyMangroveSwampPlacement() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyMangroveSwampPlacement();
        }
        return true;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyWindsweptSavannaPlacement();
        }
        return true;
    }

    public static boolean dyingTrees() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.dyingTrees();
        }
        return true;
    }

    public static boolean fallenLogs() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.fallenLogs();
        }
        return true;
    }

    public static boolean wildTrees() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wildTrees();
        }
        return true;
    }

    public static boolean wildGrass() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wildGrass();
        }
        return true;
    }

    public static boolean hornShattersGlass() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.hornShattersGlass();
        }
        return false;
    }

    public static boolean projectileBreakParticles() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.projectileBreakParticles();
        }
        return false;
    }

    public static boolean hornCanSummonWarden() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.hornCanSummonWarden();
        }
        return false;
    }

    public static boolean mcLiveSensorTendrils() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.mcLiveSensorTendrils();
        }
        return false;
    }

	public static int stoneChestTimer() {
		if (FrozenBools.hasCloth) {
			return ClothConfigCloserInteractionHandler.stoneChestTimer();
		}
		return 100;
	}

    public static boolean unpassableRail() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.unpassableRail();
        }
        return true;
    }

	public static boolean keyframeAllayDance() {
		if (FrozenBools.hasCloth) {
			return ClothConfigCloserInteractionHandler.keyframeAllayDance();
		}
		return true;
	}

    public static boolean wardenCustomTendrils() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenCustomTendrils();
        }
        return true;
    }

    public static boolean wardenDyingAnimation() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenDyingAnimation();
        }
        return true;
    }

    public static boolean wardenEmergesFromEgg() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenEmergesFromEgg();
        }
        return true;
    }

    public static boolean wardenSwimAnimation() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenSwimAnimation();
        }
        return true;
    }

    public static boolean shriekerGargling() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.shriekerGargling();
        }
        return false;
    }

    public static boolean soulFireSounds() {
        if (FrozenBools.hasCloth) {
            return ClothConfigCloserInteractionHandler.soulFireSounds();
        }
        return true;
    }

}
