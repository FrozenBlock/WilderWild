package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;
import org.betterx.betterend.registry.EndBlocks;

public class BetterEndIntegration extends ModIntegration {
    public BetterEndIntegration() {
        super("betterend");
    }

    @Override
    public void init() {
        addBlock(EndBlocks.PYTHADENDRON_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(EndBlocks.LACUGROVE_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(EndBlocks.DRAGON_TREE_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(EndBlocks.TENANEA_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(EndBlocks.HELIX_TREE_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(EndBlocks.LUCERNIA_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
    }
}
