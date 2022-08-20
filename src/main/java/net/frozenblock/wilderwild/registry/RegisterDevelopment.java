package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.misc.Camera;
import net.frozenblock.wilderwild.misc.LootTableWhacker.LootTableWhacker;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;

public final class RegisterDevelopment {

    public static final Camera CAMERA = new Camera(new FabricItemSettings());
    public static final LootTableWhacker LOOT_TABLE_WHACKER = new LootTableWhacker(new FabricItemSettings());

    public static void init() {
        Registry.register(Registry.ITEM, WilderWild.id("camera"), CAMERA);
        Registry.register(Registry.ITEM, WilderWild.id("loot_table_whacker"), LOOT_TABLE_WHACKER);
    }

    private static void registerBlock(String name, Block block, CreativeModeTab group) {
        registerBlockItem(name, block, group);
        Registry.register(Registry.BLOCK, WilderWild.id(name), block);
    }

    private static void registerBlockItem(String name, Block block, CreativeModeTab group) {
        Registry.register(Registry.ITEM, WilderWild.id(name),
                new BlockItem(block, new FabricItemSettings().tab(group)));
    }
}
