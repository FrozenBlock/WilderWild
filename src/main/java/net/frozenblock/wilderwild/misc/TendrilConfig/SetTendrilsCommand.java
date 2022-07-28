package net.frozenblock.wilderwild.misc.TendrilConfig;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.frozenblock.wilderwild.WilderWild;

import java.io.FileNotFoundException;

import static com.mojang.brigadier.arguments.BoolArgumentType.bool;
import static com.mojang.brigadier.arguments.BoolArgumentType.getBool;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class SetTendrilsCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(literal("sensorTendrils")
                        .then(argument("sensorTendrils", bool()).executes(ctx -> {
                    try {
                        return setTendrils(getBool(ctx, "sensorTendrils"));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    return 0;
                })));
    }

    private static int setTendrils(boolean bool) throws FileNotFoundException {
        SensorConfig.config.setTendrils(!bool);
        SensorConfig.rewriteConfig();
        WilderWild.RENDER_TENDRILS = bool;
        return 1;
    }

}
