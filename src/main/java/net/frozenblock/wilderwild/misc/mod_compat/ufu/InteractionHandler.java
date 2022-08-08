package net.frozenblock.wilderwild.misc.mod_compat.ufu;

import io.github.silverandro.ufu.UpdateFixerUpper;
import net.frozenblock.wilderwild.WilderWild;

public class InteractionHandler {

    public static void addToUFU() {
        UpdateFixerUpper.fixerMap.putAll(WilderWild.DataFixMap);
    }

}
