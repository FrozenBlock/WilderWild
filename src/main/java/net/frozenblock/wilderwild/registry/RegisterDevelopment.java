package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;

public class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new FabricItemSettings());
    public static final Block DEV_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.STONE));

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
        Registry.register(Registry.BLOCK, WilderWild.id("test_1"), DEV_BLOCK);
        Registry.register(Registry.ITEM, WilderWild.id("test_1"), DEV_BLOCK.asItem());
    }
}
