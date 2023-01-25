package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import net.frozenblock.wilderwild.misc.config.ClothConfigInteractionHandler;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class EdenRingIntegration extends ModIntegration {
    public EdenRingIntegration() {
        super("edenring");
    }

    @Override
    public void init() {
        addBlock(id("auritis_leaves"), LEAVES, ClothConfigInteractionHandler::leafSounds);
    }
}
