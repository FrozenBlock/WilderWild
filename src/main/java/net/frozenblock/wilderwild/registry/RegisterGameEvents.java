package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.level.gameevent.GameEvent;

public final class RegisterGameEvents {
	private RegisterGameEvents() {
		throw new UnsupportedOperationException("RegisterGameEvents contains only static declarations.");
	}

    public static final GameEvent JAW_ACTIVATE = new GameEvent("jaw_activate", 8);
    public static final GameEvent SCULK_ECHOER_ECHO = new GameEvent("sculk_echoer_echo", 16);
    public static final GameEvent SCULK_ECHOER_LOUD_ECHO = new GameEvent("sculk_echoer_loud_echo", 16);
    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);


    public static void registerEvents() {
        WilderSharedConstants.logWild("Registering GameEvents for", WilderSharedConstants.UNSTABLE_LOGGING);
		Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("jaw_activate"), JAW_ACTIVATE);
		Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("sculk_echoer_echo"), SCULK_ECHOER_ECHO);
		Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("sculk_echoer_loud_echo"), SCULK_ECHOER_LOUD_ECHO);
        Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);
    }
}
