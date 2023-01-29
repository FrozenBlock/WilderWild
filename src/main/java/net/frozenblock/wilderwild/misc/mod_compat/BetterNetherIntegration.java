package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import org.betterx.betternether.registry.NetherBlocks;

import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.*;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LEAVES;

public class BetterNetherIntegration extends ModIntegration {
    public BetterNetherIntegration() {
        super("betternether");
    }

    @Override
    public void init() {
        addBlock(NetherBlocks.WILLOW_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(NetherBlocks.RUBEUS_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(NetherBlocks.ANCHOR_TREE_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(NetherBlocks.NETHER_SAKURA_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
