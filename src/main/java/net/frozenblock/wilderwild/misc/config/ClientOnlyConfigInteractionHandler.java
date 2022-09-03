package net.frozenblock.wilderwild.misc.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screens.Screen;

public class ClientOnlyConfigInteractionHandler {

    public static ConfigScreenFactory<Screen> buildScreen() {
        return parent -> AutoConfig.getConfigScreen(WilderWildConfig.class, parent).get();
    }

}
