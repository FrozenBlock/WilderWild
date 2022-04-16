package net.frozenblock.wilderwild.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    public static final Block SCULK_ECHOER = registerBlock("sculk_echoer", new SculkEchoerBlock(AbstractBlock.Settings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK)), ItemGroup.REDSTONE);

    public static final Block SCULK_JAW = registerBlock("sculk_jaw", new SculkJawBlock(AbstractBlock.Settings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK)), ItemGroup.REDSTONE);

    public static final Block DARTURA_FLOWER = registerBlock("dartura_flower",
            new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);

    public static final Block POTTED_CARNATION = registerBlockWithoutBlockItem("potted_carnation",
            new FlowerPotBlock(ModBlocks.CARNATION,
                    FabricBlockSettings.copy(Blocks.POTTED_DANDELION).nonOpaque()), ItemGroup.DECORATIONS);

    public static final Block CARNATION = registerBlock("carnation",
            new FlowerBlock(StatusEffects.FIRE_RESISTANCE, 12,
                    FabricBlockSettings.copy(Blocks.DANDELION).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);

    private static Block registerBlockWithoutBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(WilderWild.MOD_ID, name), block);
    }

    private static BlockItem registerBlockItem(String name, Block block, ItemGroup group) {
        return Registry.register(Registry.ITEM, new Identifier(WilderWild.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void registerModBlocks() {
        WilderWild.LOGGER.info("Registering ModBlocks for " + WilderWild.MOD_ID);
    }
}
