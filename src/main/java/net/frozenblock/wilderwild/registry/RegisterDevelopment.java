package net.frozenblock.wilderwild.registry;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.registry.Registry;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;ry.Registry;

public class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new FabricItemSettings());

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
    }

    private static void registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }
}
