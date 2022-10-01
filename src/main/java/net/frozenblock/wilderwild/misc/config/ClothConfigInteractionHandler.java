package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;

public final class ClothConfigInteractionHandler {

    public static boolean betaBeaches() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.betaBeaches();
        }
        return true;
    }

    /*
        public static boolean modifyDesertPlacement() {
            if (WilderWild.hasCloth) {
                return ClothConfigCloserInteractionHandler.modifyDesertPlacement();
            }
            return true;
        }

        public static boolean modifyBadlandsPlacement() {
            if (WilderWild.hasCloth) {
                return ClothConfigCloserInteractionHandler.modifyBadlandsPlacement();
            }
            return true;
        }
    */
    public static boolean modifyJunglePlacement() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyJunglePlacement();
        }
        return true;
    }

    public static boolean modifySwampPlacement() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifySwampPlacement();
        }
        return true;
    }

    public static boolean modifyMangroveSwampPlacement() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyMangroveSwampPlacement();
        }
        return true;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.modifyWindsweptSavannaPlacement();
        }
        return true;
    }

    public static boolean dyingTrees() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.dyingTrees();
        }
        return true;
    }

    public static boolean fallenLogs() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.fallenLogs();
        }
        return true;
    }

    public static boolean wildTrees() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wildTrees();
        }
        return true;
    }

    public static boolean wildGrass() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wildGrass();
        }
        return true;
    }

    public static boolean hornShattersGlass() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.hornShattersGlass();
        }
        return false;
    }

    public static boolean projectileBreakParticles() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.projectileBreakParticles();
        }
        return false;
    }

    public static boolean hornCanSummonWarden() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.hornCanSummonWarden();
        }
        return false;
    }

    public static boolean mcLiveSensorTendrils() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.mcLiveSensorTendrils();
        }
        return false;
    }

    public static boolean unpassableRail() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.unpassableRail();
        }
        return true;
    }

    public static boolean wardenCustomTendrils() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenCustomTendrils();
        }
        return true;
    }

    public static boolean wardenDyingAnimation() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenDyingAnimation();
        }
        return true;
    }

    public static boolean wardenEmergesFromEgg() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenEmergesFromEgg();
        }
        return true;
    }

    public static boolean wardenSwimAnimation() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.wardenSwimAnimation();
        }
        return true;
    }

    public static boolean shriekerGargling() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.shriekerGargling();
        }
        return false;
    }

    public static boolean soulFireSounds() {
        if (WilderWild.hasCloth) {
            return ClothConfigCloserInteractionHandler.soulFireSounds();
        }
        return true;
    }

}