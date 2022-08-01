package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;

public class ModMenuInteractionHandler {

    public static boolean modDesertPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_DESERT_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modBadlandsPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_BADLANDS_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modJunglePlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_JUNGLE_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modSwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_SWAMP_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modMangroveSwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_MANGROVE_SWAMP_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modWindsweptSavannaPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean tendrilsEnabled() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MC_LIVE_SENSOR_TENDRILS.getValue();
        }
        return true;
    }

}
