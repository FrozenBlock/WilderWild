package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;

import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundGroups.LEAVES;

public class TechRebornIntegration extends ModIntegration {
    public TechRebornIntegration() {
        super("techreborn");
    }

    @Override
    public void init() {
        addBlock(id("rubber_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
