package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.BAOBAB_LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.CYPRESS_LEAVES;
import static net.minecraft.block.Blocks.*;


public class BlockSoundGroupOverwrites {

    public static void init() {
        addBlock(WITHER_ROSE, BlockSoundGroup.SWEET_BERRY_BUSH);
        addBlock(DEAD_BUSH, BlockSoundGroup.NETHER_SPROUTS);
        addBlock(CACTUS, BlockSoundGroup.SWEET_BERRY_BUSH);
        addBlock(PODZOL, BlockSoundGroup.ROOTED_DIRT);

        addBlockTag(BlockTags.LEAVES, LEAVES);
        addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES}, LEAVES);
        addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY}, FLOWER);
        addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM);
        addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK);
        addBlocks(new Block[]{FROSTED_ICE}, ICE_BLOCKS);
        addBlock(COBWEB, WEB);
        addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE);
        addBlock(LILY_PAD, LILYPAD);
        addBlock(SUGAR_CANE, SUGARCANE);
        addBlock(COARSE_DIRT, COARSEDIRT);

        if (FabricLoader.getInstance().getModContainer("betternether").isPresent()) {
            addBlock("betternether", "willow_leaves", LEAVES);
            addBlock("betternether", "rubeous_leaves", LEAVES);
            addBlock("betternether", "anchor_tree_leaves", LEAVES);
            addBlock("betternether", "nether_sakura_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().getModContainer("betterend").isPresent()) {
            addBlock("betterend", "pythadendron_leaves", LEAVES);
            addBlock("betterend", "lacugrove_leaves", LEAVES);
            addBlock("betterend", "dragon_tree_leaves", LEAVES);
            addBlock("betterend", "tenanea_leaves", LEAVES);
            addBlock("betterend", "helix_tree_leaves", LEAVES);
            addBlock("betterend", "lucernia_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().getModContainer("blockus").isPresent()) {
            addBlock("blockus", "white_oak_leaves", LEAVES);
            addBlock("blockus", "legacy_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().getModContainer("edenring").isPresent()) {
            addBlock("edenring", "auritis_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().getModContainer("techreborn").isPresent()) {
            addBlock("techreborn", "rubber_leaves", LEAVES);
        }
    }

    /**
     * You can add any block by either adding its registry (Blocks.STONE) or its ID ("stone").
     * If you want to add a modded block, make sure to put the nameSpaceID (wilderwild) in the first field, then the ID and soundGroup.
     * Or you could just be normal and add the block itself instead of the ID.
     * You can also add a LIST of blocks (IDs not allowed,) by using new Block[]{block1, block2}.
     */

    public static void addBlock(String id, BlockSoundGroup sounds) {
        ids.add(new Identifier(id));
        soundGroups.add(sounds);
    }

    public static void addBlock(String nameSpace, String id, BlockSoundGroup sounds) {
        ids.add(new Identifier(nameSpace, id));
        soundGroups.add(sounds);
    }

    public static void addBlock(Block block, BlockSoundGroup sounds) {
        ids.add(Registry.BLOCK.getId(block));
        soundGroups.add(sounds);
    }

    public static void addBlocks(Block[] blocks, BlockSoundGroup sounds) {
        for (Block block : blocks) {
            ids.add(Registry.BLOCK.getId(block));
            soundGroups.add(sounds);
        }
    }

    public static void addBlockTag(TagKey<Block> tag, BlockSoundGroup sounds) {
        for (RegistryEntry<Block> block : Registry.BLOCK.iterateEntries(tag)) {
            ids.add(Registry.BLOCK.getId(block.value()));
            soundGroups.add(sounds);
        }
    }

    public static void addNamespace(String nameSpace, BlockSoundGroup sounds) {
        namespaces.add(nameSpace);
        namespaceSoundGroups.add(sounds);
    }

    public static final List<Identifier> ids = new ArrayList<>();
    public static final List<BlockSoundGroup> soundGroups = new ArrayList<>();
    public static final List<String> namespaces = new ArrayList<>();
    public static final List<BlockSoundGroup> namespaceSoundGroups = new ArrayList<>();
}
