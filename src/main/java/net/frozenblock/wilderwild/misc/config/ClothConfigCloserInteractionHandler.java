package net.frozenblock.wilderwild.misc.config;

public final class ClothConfigCloserInteractionHandler {

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

    public static boolean unpassableRail() {
        return WilderWildConfig.get().entity.unpassableRail;
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