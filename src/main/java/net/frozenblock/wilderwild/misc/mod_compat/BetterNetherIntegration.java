package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;

import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.*;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LEAVES;

public class BetterNetherIntegration extends ModIntegration {
    public BetterNetherIntegration() {
        super("betternether");
    }

    @Override
    public void init() {
        addBlock(id("willow_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("rubeous_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("anchor_tree_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
        addBlock(id("nether_sakura_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
