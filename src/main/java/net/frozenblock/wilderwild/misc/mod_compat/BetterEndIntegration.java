package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import org.betterx.betterend.registry.EndBlocks;

import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LEAVES;

public class BetterEndIntegration extends ModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    @Override
    public void init() {
        addBlock(EndBlocks.PYTHADENDRON_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(EndBlocks.LACUGROVE_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(EndBlocks.DRAGON_TREE_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(EndBlocks.TENANEA_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(EndBlocks.HELIX_TREE_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(EndBlocks.LUCERNIA_LEAVES, LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
