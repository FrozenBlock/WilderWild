package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.CameraItem;
import net.minecraft.util.registry.Registry;

public class RegisterDevelopment {

    public static final CameraItem CAMERA = new CameraItem(new FabricItemSettings());

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
    }
}
