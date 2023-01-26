package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class BetterNetherIntegration extends ModIntegration {
    public BetterNetherIntegration() {
        super("betternether");
    }

    @Override
    public void init() {
        addBlock(id("willow_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("rubeous_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("anchor_tree_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("nether_sakura_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
    }
}
