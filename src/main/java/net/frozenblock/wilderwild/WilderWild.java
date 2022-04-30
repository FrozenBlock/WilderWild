package net.frozenblock.wilderwild;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.dev_only.CameraItem;
import net.frozenblock.wilderwild.mixin.worldgen.TrunkPlacerTypeInvoker;
import net.frozenblock.wilderwild.registry.*;
import net.frozenblock.wilderwild.world.feature.WildConfiguredFeatures;
import net.frozenblock.wilderwild.world.feature.WildTreeConfigured;
import net.frozenblock.wilderwild.world.feature.WildTreePlaced;
import net.frozenblock.wilderwild.world.gen.WildWorldGen;
import net.frozenblock.wilderwild.world.gen.trunk.StraightTrunkWithLogs;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.trunk.TrunkPlacerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WilderWild implements ModInitializer {
    public static final String MOD_ID = "wilderwild";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameEvent JAW_ACTIVATE = new GameEvent("jaw_activate", 8);
    public static final GameEvent SCULK_ECHOER_ECHO = new GameEvent("sculk_echoer_echo", 16);
    public static final GameEvent SCULK_SENSOR_ACTIVATE = new GameEvent("sculk_sensor_activate", 16);
    public static final GameEvent SCULK_SENSOR_HICCUP = new GameEvent("sculk_sensor_hiccup", 16);
    public static final GameEvent TENDRIL_EXTRACT_XP = new GameEvent("hanging_tendril_extract_xp", 16);

    public static final TrunkPlacerType<StraightTrunkWithLogs> STRAIGHT_TRUNK_WITH_LOGS_PLACER_TYPE = TrunkPlacerTypeInvoker.callRegister("straight_trunk_logs_placer", StraightTrunkWithLogs.CODEC);

    @Override
    public void onInitialize() {
        RegisterBlocks.RegisterBlocks();
        RegisterItems.RegisterItems();
        RegisterParticles.RegisterParticles();
        WildConfiguredFeatures.registerConfiguredFeatures();
        WildTreeConfigured.registerTreeConfigured();
        WildTreePlaced.registerTreePlaced();
        WildWorldGen.generateWildWorldGen();
        RegisterBlockEntityType.init();
        RegisterEntities.init();

        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "jaw_activate"), JAW_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_echoer_echo"), SCULK_ECHOER_ECHO);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_sensor_activate"), SCULK_SENSOR_ACTIVATE);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "sculk_sensor_hiccup"), SCULK_SENSOR_HICCUP);
        Registry.register(Registry.GAME_EVENT, new Identifier(WilderWild.MOD_ID, "hanging_tendril_extract_xp"), TENDRIL_EXTRACT_XP);

        //if (FabricLoader.getInstance().isDevelopmentEnvironment()) { /* DEV-ONLY */
            Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, "camera"), CAMERA_ITEM);
        //}
    }

    public static final Identifier HORN_PROJECTILE_PACKET_ID = new Identifier(WilderWild.MOD_ID, "ancient_horn_projectile_packet");

    /* DEV-ONLY */
    public static final CameraItem CAMERA_ITEM = new CameraItem(new FabricItemSettings().group(ItemGroup.TOOLS));

}
