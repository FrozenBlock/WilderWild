package net.frozenblock.wilderwild.misc.config;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;

public class ClothConfigInteractionHandler {


    public static boolean modifyDesertPlacement() {
        return WilderWildClient.config.worldgen.modifyDesertPlacement;
    }

    public static boolean modifyBadlandsPlacement() {
        return WilderWildClient.config.worldgen.modifyBadlandsPlacement;
    }

    public static boolean modifyJunglePlacement() {
        return WilderWildClient.config.worldgen.modifyJunglePlacement;
    }

    public static boolean modifySwampPlacement() {
        return WilderWildClient.config.worldgen.modifySwampPlacement;
    }

    public static boolean modifyMangroveSwampPlacement() {
        return WilderWildClient.config.worldgen.modifyMangroveSwampPlacement;
    }

    public static boolean modifyWindsweptSavannaPlacement() {
        return WilderWildClient.config.worldgen.modifyWindsweptSavannaPlacement;
    }

    public static boolean mcLiveSensorTendrils() {
        return WilderWildClient.config.block.mcLiveSensorTendrils;
    }

    public static boolean betaBeaches() {
        return WilderWildClient.config.worldgen.betaBeaches;
    }

    public static boolean wardenEmergesFromEgg() {
        return WilderWildClient.config.entity.wardenEmergesFromEgg;
    }

    public static boolean customWardenTendrils() {
        return WilderWildClient.config.entity.customWardenTendrils;
    }

    public static boolean wardenSwimAnimation() {
        return WilderWildClient.config.entity.wardenSwimAnimation;
    }

    public static boolean shriekerGargling() {
        return WilderWildClient.config.block.shriekerGargling;
    }

}