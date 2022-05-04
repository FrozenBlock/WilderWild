package net.frozenblock.wilderwild.block;

import net.minecraft.block.Block;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;
import static net.minecraft.block.Blocks.*;


public class BlockSoundGroupOverwrites {

    public static void init() {
        addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM);
        addBlock(LILY_PAD, LILYPAD);
        addBlock("stone", BlockSoundGroup.AMETHYST_CLUSTER);
        addBlock("minecraft","grass", BlockSoundGroup.ANVIL);
        addBlock("minecraft","grass_block", BlockSoundGroup.ANVIL);
        addBlock(DIRT, BlockSoundGroup.ANVIL);
        addBlock(DEEPSLATE, BlockSoundGroup.ANVIL);
        addBlock(OAK_LOG, BlockSoundGroup.ANVIL);
    }

    /** You can add any block by either adding its registry (Blocks.STONE) or its ID ("stone").
     * If you want to add a modded block, make sure to put the nameSpaceID (wilderwild) in the first field, then the ID and soundGroup.
     * Or you could just be normal and add the block itself instead of the ID.
     * You can also add a LIST of blocks (IDs not allowed,) by using new Block[]{block1, block2}.*/

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

    public static List<Identifier> ids = new ArrayList<>();
    public static List<BlockSoundGroup> soundGroups = new ArrayList<>();
}
