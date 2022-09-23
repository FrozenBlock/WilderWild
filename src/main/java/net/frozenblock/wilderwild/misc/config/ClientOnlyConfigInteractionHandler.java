package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import net.minecraft.client.gui.screens.Screen;

public final class ClientOnlyConfigInteractionHandler {

    public static ConfigScreenFactory<Screen> buildScreen() {
        return WilderWildConfig::buildScreen;
    }

}
