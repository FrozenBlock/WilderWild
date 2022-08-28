package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWildClient;

/**
 * @deprecated Use WilderWildClient.config instead
 */
@Deprecated
public class ClothConfigInteractionHandler {


    public static boolean modifyDesertPlacement() {
        return WilderWildClient.config.modifyDesertPlacement;
    }

    public static boolean modifyBadlandsPlacement() {
        return WilderWildClient.config.modifyBadlandsPlacement;
    }

    public static boolean modifyJunglePlacement() {
        return WilderWildClient.config.modifyJunglePlacement;
    }

    public static boolean modifySwampPlacement() {
        return WilderWildClient.config.modifySwampPlacement;
    }

    public static boolean modifyMangroveSwampPlacement() {
        return WilderWildClient.config.modifyMangroveSwampPlacement;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        return WilderWildClient.config.modifyWindsweptSavannaPlacement;
    }

    public static boolean mcLiveSensorTendrils() {
        return WilderWildClient.config.mcLiveSensorTendrils;
    }

    public static boolean betaBeaches() {
        return WilderWildClient.config.betaBeaches;
    }

    public static boolean wardenEmergesFromEgg() {
        return WilderWildClient.config.wardenEmergesFromEgg;
    }

    public static boolean customWardenTendrils() {
        return WilderWildClient.config.customWardenTendrils;
    }

    public static boolean wardenSwimAnimation() {
        return WilderWildClient.config.wardenSwimAnimation;
    }

    public static boolean shriekerGargling() {
        return WilderWildClient.config.shriekerGargling;
    }

}