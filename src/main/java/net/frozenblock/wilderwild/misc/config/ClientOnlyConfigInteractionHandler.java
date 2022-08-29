package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;

public class ClientOnlyConfigInteractionHandler {

    public static ConfigScreenFactory<?> buildScreen() {
        return WilderWildConfig::buildScreen;
    }

}
