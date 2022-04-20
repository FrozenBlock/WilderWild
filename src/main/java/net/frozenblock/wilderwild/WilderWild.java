package net.frozenblock.wilderwild;

import net.fabricmc.api.ModInitializer;
import net.frozenblock.wilderwild.registry.RegisterBlockEntityType;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.frozenblock.wilderwild.world.feature.WildConfiguredFeatures;
import net.frozenblock.wilderwild.world.gen.WildWorldGen;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameEvent JAW_ACTIVATE = new GameEvent("jaw_activate", 16);
    public static final GameEvent SCULK_ECHOER_ECHO = new GameEvent("sculk_echoer_echo", 16);
    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);

    @Override
    public void onInitialize() {
        RegisterBlocks.RegisterBlocks();
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "jaw_activate"), JAW_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_echoer_echo"), SCULK_ECHOER_ECHO);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        RegisterItems.RegisterItems();
        WildWorldGen.generateWildWorldGen();
        WildConfiguredFeatures.registerConfiguredFeatures();
        RegisterBlockEntityType.init();
    }

    /* MIXIN CONTROLS */

}
