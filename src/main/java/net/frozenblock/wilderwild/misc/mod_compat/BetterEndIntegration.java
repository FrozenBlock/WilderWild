package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;

public class BetterEndIntegration extends ModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    @Override
    public void init() {
        addBlock(id("pythadendron_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("lacugrove_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("dragon_tree_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("tenanea_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("helix_tree_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(id("lucernia_leaves"), LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
    }
}
