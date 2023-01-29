package net.frozenblock.wilderwild.misc.mod_compat;

import net.frozenblock.lib.integration.api.ModIntegration;
import static net.frozenblock.lib.sound.api.block_sound_group.BlockSoundGroupOverwrites.addBlock;
import static net.frozenblock.wilderwild.registry.RegisterBlockSoundTypes.LEAVES;
import org.betterx.betternether.registry.NetherBlocks;

public class BetterNetherIntegration extends ModIntegration {
    public BetterNetherIntegration() {
        super("betternether");
    }

    @Override
    public void init() {
        addBlock(NetherBlocks.WILLOW_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(NetherBlocks.RUBEUS_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
        addBlock(NetherBlocks.ANCHOR_TREE_LEAVES, LEAVES, WilderModIntegrations.CLOTH_CONFIG_INTEGRATION.getIntegration()::leafSounds);
    }
}
