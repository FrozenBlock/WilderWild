package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;

public class RegisterGameEvents {

    public static final GameEvent JAW_ACTIVATE = new GameEvent("jaw_activate", 8);
    public static final GameEvent SCULK_ECHOER_ECHO = new GameEvent("sculk_echoer_echo", 16);
    public static final GameEvent SCULK_ECHOER_LOUD_ECHO = new GameEvent("sculk_echoer_loud_echo", 16);
    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);


    public static void RegisterEvents() {
        WilderWild.logWild("Registering GameEvents for", WilderWild.UNSTABLE_LOGGING);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "jaw_activate"), JAW_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_echoer_echo"), SCULK_ECHOER_ECHO);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_echoer_loud_echo"), SCULK_ECHOER_LOUD_ECHO);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);
    }
}
