package net.frozenblock.wilderwild.misc;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;

import java.util.ArrayList;
import java.util.List;

import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.CLAY_BLOCK;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.COARSEDIRT;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.FLOWER;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.GRAVELSOUNDS;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.ICE_BLOCKS;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LILYPAD;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.MUSHROOM;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.MUSHROOM_BLOCK;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.REINFORCEDDEEPSLATE;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.SUGARCANE;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.WEB;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.BAOBAB_LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.CYPRESS_LEAVES;
import static net.minecraft.world.level.block.Blocks.ACACIA_LEAVES;
import static net.minecraft.world.level.block.Blocks.ALLIUM;
import static net.minecraft.world.level.block.Blocks.AZURE_BLUET;
import static net.minecraft.world.level.block.Blocks.BIRCH_LEAVES;
import static net.minecraft.world.level.block.Blocks.BLUE_ORCHID;
import static net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM;
import static net.minecraft.world.level.block.Blocks.BROWN_MUSHROOM_BLOCK;
import static net.minecraft.world.level.block.Blocks.CACTUS;
import static net.minecraft.world.level.block.Blocks.CLAY;
import static net.minecraft.world.level.block.Blocks.COARSE_DIRT;
import static net.minecraft.world.level.block.Blocks.COBWEB;
import static net.minecraft.world.level.block.Blocks.CORNFLOWER;
import static net.minecraft.world.level.block.Blocks.DANDELION;
import static net.minecraft.world.level.block.Blocks.DARK_OAK_LEAVES;
import static net.minecraft.world.level.block.Blocks.DEAD_BUSH;
import static net.minecraft.world.level.block.Blocks.FROSTED_ICE;
import static net.minecraft.world.level.block.Blocks.GRAVEL;
import static net.minecraft.world.level.block.Blocks.JUNGLE_LEAVES;
import static net.minecraft.world.level.block.Blocks.LILY_OF_THE_VALLEY;
import static net.minecraft.world.level.block.Blocks.LILY_PAD;
import static net.minecraft.world.level.block.Blocks.MANGROVE_LEAVES;
import static net.minecraft.world.level.block.Blocks.MUSHROOM_STEM;
import static net.minecraft.world.level.block.Blocks.OAK_LEAVES;
import static net.minecraft.world.level.block.Blocks.ORANGE_TULIP;
import static net.minecraft.world.level.block.Blocks.OXEYE_DAISY;
import static net.minecraft.world.level.block.Blocks.PINK_TULIP;
import static net.minecraft.world.level.block.Blocks.PODZOL;
import static net.minecraft.world.level.block.Blocks.POPPY;
import static net.minecraft.world.level.block.Blocks.RED_MUSHROOM;
import static net.minecraft.world.level.block.Blocks.RED_MUSHROOM_BLOCK;
import static net.minecraft.world.level.block.Blocks.RED_TULIP;
import static net.minecraft.world.level.block.Blocks.REINFORCED_DEEPSLATE;
import static net.minecraft.world.level.block.Blocks.SPRUCE_LEAVES;
import static net.minecraft.world.level.block.Blocks.SUGAR_CANE;
import static net.minecraft.world.level.block.Blocks.WHITE_TULIP;
import static net.minecraft.world.level.block.Blocks.WITHER_ROSE;


public class BlockSoundGroupOverwrites {

    public static void init() {
        addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH);
        addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS);
        addBlock(CACTUS, SoundType.SWEET_BERRY_BUSH);
        addBlock(PODZOL, SoundType.ROOTED_DIRT);

        addBlockTag(BlockTags.LEAVES, LEAVES);
        addBlocks(new Block[] {ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES,
                JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES,
                BAOBAB_LEAVES, CYPRESS_LEAVES}, LEAVES);
        addBlocks(
                new Block[] {DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET,
                        RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP,
                        OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY}, FLOWER);
        addBlocks(new Block[] {RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM);
        addBlocks(new Block[] {RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK,
                MUSHROOM_STEM}, MUSHROOM_BLOCK);
        addBlocks(new Block[] {FROSTED_ICE}, ICE_BLOCKS);
        addBlock(COBWEB, WEB);
        addBlock(CLAY, CLAY_BLOCK);
        addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE);
        addBlock(LILY_PAD, LILYPAD);
        addBlock(SUGAR_CANE, SUGARCANE);
        addBlock(COARSE_DIRT, COARSEDIRT);
        addBlock(GRAVEL, GRAVELSOUNDS);

        if (FabricLoader.getInstance().isModLoaded("betternether")) {
            addBlock("betternether", "willow_leaves", LEAVES);
            addBlock("betternether", "rubeous_leaves", LEAVES);
            addBlock("betternether", "anchor_tree_leaves", LEAVES);
            addBlock("betternether", "nether_sakura_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().isModLoaded("betterend")) {
            addBlock("betterend", "pythadendron_leaves", LEAVES);
            addBlock("betterend", "lacugrove_leaves", LEAVES);
            addBlock("betterend", "dragon_tree_leaves", LEAVES);
            addBlock("betterend", "tenanea_leaves", LEAVES);
            addBlock("betterend", "helix_tree_leaves", LEAVES);
            addBlock("betterend", "lucernia_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().isModLoaded("blockus")) {
            addBlock("blockus", "white_oak_leaves", LEAVES);
            addBlock("blockus", "legacy_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().isModLoaded("edenring")) {
            addBlock("edenring", "auritis_leaves", LEAVES);
        }

        if (FabricLoader.getInstance().isModLoaded("techreborn")) {
            addBlock("techreborn", "rubber_leaves", LEAVES);
        }
    }

    /**
     * You can add any block by either adding its registry (Blocks.STONE) or its ID ("stone").
     * If you want to add a modded block, make sure to put the nameSpaceID (wilderwild) in the first field, then the ID and soundGroup.
     * Or you could just be normal and add the block itself instead of the ID.
     * You can also add a LIST of blocks (IDs not allowed,) by using new Block[]{block1, block2}.
     */

    public static void addBlock(String id, SoundType sounds) {
        ids.add(new ResourceLocation(id));
        soundGroups.add(sounds);
    }

    public static void addBlock(String nameSpace, String id, SoundType sounds) {
        ids.add(new ResourceLocation(nameSpace, id));
        soundGroups.add(sounds);
    }

    public static void addBlock(Block block, SoundType sounds) {
        ids.add(Registry.BLOCK.getKey(block));
        soundGroups.add(sounds);
    }

    public static void addBlocks(Block[] blocks, SoundType sounds) {
        for (Block block : blocks) {
            ids.add(Registry.BLOCK.getKey(block));
            soundGroups.add(sounds);
        }
    }

    public static void addBlockTag(TagKey<Block> tag, SoundType sounds) {
        for (Holder<Block> block : Registry.BLOCK.getTagOrEmpty(tag)) {
            ids.add(block.unwrapKey().orElseThrow().location());
            soundGroups.add(sounds);
        }
    }

    public static void addNamespace(String nameSpace, SoundType sounds) {
        namespaces.add(nameSpace);
        namespaceSoundGroups.add(sounds);
    }

    public static final List<ResourceLocation> ids = new ArrayList<>();
    public static final List<SoundType> soundGroups = new ArrayList<>();
    public static final List<String> namespaces = new ArrayList<>();
    public static final List<SoundType> namespaceSoundGroups =
            new ArrayList<>();
}
