package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;

public class ModMenuInteractionHandler {


    public static boolean modifyDesertPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifyDesertPlacement;
        }
        return true;
    }

    public static boolean modifyBadlandsPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifyBadlandsPlacement;
        }
        return true;
    }

    public static boolean modifyJunglePlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifyJunglePlacement;
        }
        return true;
    }

    public static boolean modifySwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifySwampPlacement;
        }
        return true;
    }

    public static boolean modifyMangroveSwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifyMangroveSwampPlacement;
        }
        return true;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.modifyWindsweptSavannaPlacement;
        }
        return true;
    }

    public static boolean mcLiveSensorTendrils() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.mcLiveSensorTendrils;
        }
        return false;
    }

    public static boolean betaBeaches() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.betaBeaches;
        }
        return true;
    }

    public static boolean wardenEmergesFromEgg() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.wardenEmergesFromEgg;
        }
        return true;
    }

    public static boolean customWardenTendrils() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.customWardenTendrils;
        }
        return true;
    }

    public static boolean wardenSwimAnimation() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.wardenSwimAnimation;
        }
        return true;
    }

    public static boolean shriekerGargling() {
        if (WilderWild.hasModMenu()) {
            return WilderWildClient.config.shriekerGargling;
        }
        return true;
    }

}
