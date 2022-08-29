package net.frozenblock.wilderwild.misc.config;

public class ClothConfigCloserInteractionHandler {


    public static boolean modifyDesertPlacement() {
        return WilderWildConfig.get().worldgen.modifyDesertPlacement;
    }

    public static boolean modifyBadlandsPlacement() {
        return WilderWildConfig.get().worldgen.modifyBadlandsPlacement;
    }

    public static boolean modifyJunglePlacement() {
        return WilderWildConfig.get().worldgen.modifyJunglePlacement;
    }

    public static boolean modifySwampPlacement() {
        return WilderWildConfig.get().worldgen.modifySwampPlacement;
    }

    public static boolean modifyMangroveSwampPlacement() {
        return WilderWildConfig.get().worldgen.modifyMangroveSwampPlacement;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        return WilderWildConfig.get().worldgen.modifyWindsweptSavannaPlacement;
    }

    public static boolean mcLiveSensorTendrils() {
        return WilderWildConfig.get().block.mcLiveSensorTendrils;
    }

    public static boolean betaBeaches() {
        return WilderWildConfig.get().worldgen.betaBeaches;
    }

    public static boolean wardenEmergesFromEgg() {
        return WilderWildConfig.get().entity.wardenEmergesFromEgg;
    }

    public static boolean customWardenTendrils() {
        return WilderWildConfig.get().entity.customWardenTendrils;
    }

    public static boolean wardenSwimAnimation() {
        return WilderWildConfig.get().entity.wardenSwimAnimation;
    }

    public static boolean shriekerGargling() {
        return WilderWildConfig.get().block.shriekerGargling;
    }

}