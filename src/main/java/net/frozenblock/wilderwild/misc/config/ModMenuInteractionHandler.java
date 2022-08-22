package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;

public class ModMenuInteractionHandler {


    public static boolean modDesertPlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modDesertPlacement();
        }
        return true;
    }

    public static boolean modBadlandsPlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modBadlandsPlacement();
        }
        return true;
    }

    public static boolean modJunglePlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modJunglePlacement();
        }
        return true;
    }

    public static boolean modSwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modSwampPlacement();
        }
        return true;
    }

    public static boolean modMangroveSwampPlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modMangroveSwampPlacement();
        }
        return true;
    }

    public static boolean modWindsweptSavannaPlacement() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.modWindsweptSavannaPlacement();
        }
        return true;
    }

    public static boolean tendrilsEnabled() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.tendrilsEnabled();
        }
        return false;
    }

    public static boolean betaBeaches() {
        if (WilderWild.hasModMenu()) {
            return MMDistantInteractions.betaBeaches();
        }
        return true;
    }

}
