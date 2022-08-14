package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;

public class MMDistantInteractions {

    public static void loadConfig() {
        WilderWildConfigManager.initializeConfig();
    }

    public static boolean modDesertPlacement() {
        if (WilderWild.hasModMenu()) {
            return WilderWildConfig.MODIFY_DESERT_PLACEMENT.getValue();
        }
        return true;
    }

    public static boolean modBadlandsPlacement() {
        return WilderWildConfig.MODIFY_BADLANDS_PLACEMENT.getValue();
    }

    public static boolean modJunglePlacement() {
        return WilderWildConfig.MODIFY_JUNGLE_PLACEMENT.getValue();
    }

    public static boolean modSwampPlacement() {
        return WilderWildConfig.MODIFY_SWAMP_PLACEMENT.getValue();
    }

    public static boolean modMangroveSwampPlacement() {
        return WilderWildConfig.MODIFY_MANGROVE_SWAMP_PLACEMENT.getValue();
    }

    public static boolean modWindsweptSavannaPlacement() {
        return WilderWildConfig.MODIFY_WINDSWEPT_SAVANNA_PLACEMENT.getValue();
    }

    public static boolean tendrilsEnabled() {
        return WilderWildConfig.MC_LIVE_SENSOR_TENDRILS.getValue();
    }

}
