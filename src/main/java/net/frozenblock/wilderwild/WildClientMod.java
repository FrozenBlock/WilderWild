package net.frozenblock.wilderwild;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.minecraft.client.render.RenderLayer;

public class WildClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.SCULK_ECHOER, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_ACACIA_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_BIRCH_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RenderLayer.getCutout());

    }
}