package net.frozenblock.wilderwild.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;

import java.io.FileNotFoundException;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class EnableDisableTendrilCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("sensorTendrils")
                        .then(literal("enable")
                                .executes(context -> {
                                    try {
                                        MinecraftClient.getInstance().player.sendChatMessage("Game restart required for this feature to work!");
                                        return enable();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                }))
                        .then(literal("disable")
                                .executes(context -> {
                                    try {
                                        MinecraftClient.getInstance().player.sendChatMessage("Game restart required for this feature to work!");
                                        return disable();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    return 0;
                                })));
    }

    private static int disable() throws FileNotFoundException {
        ConfigManager.setEnabled(false);
        return 1;
    }

    private static int enable() throws FileNotFoundException {
        ConfigManager.setEnabled(true);
        return 1;
    }

}
