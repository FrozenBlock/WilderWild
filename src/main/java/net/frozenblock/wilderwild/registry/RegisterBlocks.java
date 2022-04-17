package net.frozenblock.wilderwild.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.DaturaBlock;
import net.frozenblock.wilderwild.block.SculkEchoerBlock;
import net.frozenblock.wilderwild.block.SculkJawBlock;
import net.minecraft.block.*;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RegisterBlocks {
    // HOLLOW LOGS
    public static final Block HOLLOWED_OAK_LOG = registerBlock("hollowed_oak_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_SPRUCE_LOG = registerBlock("hollowed_spruce_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_ACACIA_LOG = registerBlock("hollowed_acacia_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_JUNGLE_LOG = registerBlock("hollowed_jungle_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_BIRCH_LOG = registerBlock("hollowed_birch_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_DARK_OAK_LOG = registerBlock("hollowed_dark_oak_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    public static final Block HOLLOWED_MANGROVE_LOG = registerBlock("hollowed_mangrove_log",
            new PillarBlock(FabricBlockSettings.of(Material.WOOD).strength(1f).requiresTool()), ItemGroup.BUILDING_BLOCKS);

    // SCULK
    public static final Block SCULK_ECHOER = registerBlock("sculk_echoer",
            new SculkEchoerBlock(AbstractBlock.Settings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK), 8),
            ItemGroup.REDSTONE);
    public static final Block SCULK_JAW = registerBlock("sculk_jaw",
            new SculkJawBlock(AbstractBlock.Settings.of(Material.SCULK).strength(0.6F).sounds(BlockSoundGroup.SCULK)),
            ItemGroup.REDSTONE);
    public static final Block SCULK_BONE = registerBlock("sculk_bone",
            new PillarBlock(AbstractBlock.Settings.of(Material.STONE,
                    MapColor.PALE_YELLOW).requiresTool().strength(2.0F).sounds(BlockSoundGroup.SCULK_CATALYST)),ItemGroup.DECORATIONS);

    // FLOWERS
    public static final Block DATURA = registerBlock("datura",
            new TallFlowerBlock(FabricBlockSettings.copy(Blocks.ROSE_BUSH).strength(0.0f).nonOpaque()), ItemGroup.DECORATIONS);
    public static final Block POTTED_CARNATION = registerBlockWithoutBlockItem("potted_carnation",
            new FlowerPotBlock(RegisterBlocks.CARNATION,
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

    public static void RegisterBlocks() {
        WilderWild.LOGGER.info("Registering Blocks for " + WilderWild.MOD_ID);
    }
}