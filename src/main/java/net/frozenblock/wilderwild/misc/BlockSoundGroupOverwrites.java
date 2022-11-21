package net.frozenblock.wilderwild.misc;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.*;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.BAOBAB_LEAVES;
import static net.frozenblock.wilderwild.registry.RegisterBlocks.CYPRESS_LEAVES;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import static net.minecraft.world.level.block.Blocks.*;
import net.minecraft.world.level.block.SoundType;


public class BlockSoundGroupOverwrites {

	public static final List<ResourceLocation> IDS = new ArrayList<>();
	public static final List<SoundType> SOUND_GROUPS = new ArrayList<>();
	public static final List<String> NAMESPACES = new ArrayList<>();
	public static final List<SoundType> NAMESPACE_SOUND_GROUPS = new ArrayList<>();

    public static void init() {
		if (ClothConfigInteractionHandler.cactusSounds()) {
			addBlock(CACTUS, SoundType.SWEET_BERRY_BUSH);
		}
		if (ClothConfigInteractionHandler.claySounds()) {
			addBlock(CLAY, CLAY_BLOCK);
		}
		if (ClothConfigInteractionHandler.coarseDirtSounds()) {
			addBlock(COARSE_DIRT, COARSEDIRT);
		}
		if (ClothConfigInteractionHandler.cobwebSounds()) {
			addBlock(COBWEB, WEB);
		}
		if (ClothConfigInteractionHandler.deadBushSounds()) {
			addBlock(DEAD_BUSH, SoundType.NETHER_SPROUTS);
		}
		if (ClothConfigInteractionHandler.flowerSounds()) {
			addBlocks(new Block[]{DANDELION, POPPY, BLUE_ORCHID, ALLIUM, AZURE_BLUET, RED_TULIP, ORANGE_TULIP, WHITE_TULIP, PINK_TULIP, OXEYE_DAISY, CORNFLOWER, LILY_OF_THE_VALLEY}, FLOWER);
		}
		if (ClothConfigInteractionHandler.frostedIceSounds()) {
			addBlocks(new Block[]{FROSTED_ICE}, ICE_BLOCKS);
		}
		if (ClothConfigInteractionHandler.gravelSounds()) {
			addBlock(GRAVEL, GRAVELSOUNDS);
		}
		if (ClothConfigInteractionHandler.leafSounds()) {
			addBlockTag(BlockTags.LEAVES, LEAVES);
			addBlocks(new Block[]{ACACIA_LEAVES, BIRCH_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, MANGROVE_LEAVES, OAK_LEAVES, SPRUCE_LEAVES, BAOBAB_LEAVES, CYPRESS_LEAVES}, LEAVES);
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
		if (ClothConfigInteractionHandler.lilyPadSounds()) {
			addBlock(LILY_PAD, LILYPAD);
		}
		if (ClothConfigInteractionHandler.mushroomBlockSounds()) {
			addBlocks(new Block[]{RED_MUSHROOM, BROWN_MUSHROOM}, MUSHROOM);
			addBlocks(new Block[]{RED_MUSHROOM_BLOCK, BROWN_MUSHROOM_BLOCK, MUSHROOM_STEM}, MUSHROOM_BLOCK);
		}
		if (ClothConfigInteractionHandler.podzolSounds()) {
			addBlock(PODZOL, SoundType.ROOTED_DIRT);
		}
		if (ClothConfigInteractionHandler.reinforcedDeepslateSounds()) {
			addBlock(REINFORCED_DEEPSLATE, REINFORCEDDEEPSLATE);
		}
		if (ClothConfigInteractionHandler.sugarCaneSounds()) {
			addBlock(SUGAR_CANE, SUGARCANE);
		}
		if (ClothConfigInteractionHandler.witherRoseSounds()) {
			addBlock(WITHER_ROSE, SoundType.SWEET_BERRY_BUSH);
		}
    }

    /**
     * You can add any block by either adding its registry (Blocks.STONE) or its ID ("stone").
     * If you want to add a modded block, make sure to put the nameSpaceID (wilderwild) in the first field, then the ID and soundGroup.
     * Or you could just be normal and add the block itself instead of the ID.
     * You can also add a LIST of blocks (IDs not allowed,) by using new Block[]{block1, block2}.
     */

    public static void addBlock(String id, SoundType sounds) {
        IDS.add(new ResourceLocation(id));
        SOUND_GROUPS.add(sounds);
    }

    public static void addBlock(String nameSpace, String id, SoundType sounds) {
        IDS.add(new ResourceLocation(nameSpace, id));
        SOUND_GROUPS.add(sounds);
    }

    public static void addBlock(Block block, SoundType sounds) {
        IDS.add(Registry.BLOCK.getKey(block));
        SOUND_GROUPS.add(sounds);
    }

    public static void addBlocks(Block[] blocks, SoundType sounds) {
        for (Block block : blocks) {
            IDS.add(Registry.BLOCK.getKey(block));
            SOUND_GROUPS.add(sounds);
        }
    }

    public static void addBlockTag(TagKey<Block> tag, SoundType sounds) {
        for (Holder<Block> block : Registry.BLOCK.getTagOrEmpty(tag)) {
            IDS.add(block.unwrapKey().orElseThrow().location());
            SOUND_GROUPS.add(sounds);
        }
    }

    public static void addNamespace(String nameSpace, SoundType sounds) {
        NAMESPACES.add(nameSpace);
        NAMESPACE_SOUND_GROUPS.add(sounds);
    }
}
