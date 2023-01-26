package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class EdenRingIntegration extends ModIntegration {
    public EdenRingIntegration() {
        super("edenring");
    }

    @Override
    public void init() {
        addBlock(id("auritis_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
    }
}
