package net.frozenblock.wilderwild.misc;

import net.minecraft.datafixer.DataFixerPhase;

public class WilderConstants {

    public static DataFixerPhase dataFixerPhase = DataFixerPhase.UNINITIALIZED_UNOPTIMIZED;

    public static void enableDataFixerOptimization() {
        dataFixerPhase = switch(dataFixerPhase) {
            case INITIALIZED_UNOPTIMIZED -> throw new IllegalStateException("Tried to enable Wilder Wild's datafixer optimization after unoptimized initialization");
            case INITIALIZED_OPTIMIZED -> DataFixerPhase.INITIALIZED_OPTIMIZED;
            default -> DataFixerPhase.UNINITIALIZED_OPTIMIZED;
        };
    }
}
