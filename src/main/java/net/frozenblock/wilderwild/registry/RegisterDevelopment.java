package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.minecraft.core.Registry;

public class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new FabricItemSettings());

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
    }
}
