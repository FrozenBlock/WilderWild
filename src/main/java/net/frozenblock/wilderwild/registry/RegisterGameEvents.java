package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.core.Registry;
import net.minecraft.world.level.gameevent.GameEvent;

public final class RegisterGameEvents {
	private RegisterGameEvents() {
		throw new UnsupportedOperationException("RegisterGameEvents contains only static declarations.");
	}

    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);


    public static void registerEvents() {
        WilderSharedConstants.logWild("Registering GameEvents for", WilderSharedConstants.UNSTABLE_LOGGING);
        Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, WilderSharedConstants.id("hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);
    }
}
