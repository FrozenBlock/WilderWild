package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class BetterEndIntegration extends ModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    @Override
    public void init() {
        addBlock(id("pythadendron_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("lacugrove_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("dragon_tree_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("tenanea_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("helix_tree_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("lucernia_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
