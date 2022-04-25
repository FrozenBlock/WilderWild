package net.frozenblock.wilderwild;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.frozenblock.wilderwild.entity.render.SculkSensorTendrilModel;
import net.frozenblock.wilderwild.entity.render.SculkSensorTendrilRenderer;
import net.frozenblock.wilderwild.particle.PollenParticle;
import net.frozenblock.wilderwild.registry.RegisterBlocks;
import net.frozenblock.wilderwild.registry.RegisterEntities;
import net.frozenblock.wilderwild.registry.RegisterParticles;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class WildClientMod implements ClientModInitializer {
    public static final EntityModelLayer SENSOR_TENDRILS_LAYER = new EntityModelLayer(new Identifier(WilderWild.MOD_ID, "sculk_sensor_tendrils"), "main");

    @Override
    public void onInitializeClient() {

        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POTTED_CARNATION, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.DATURA, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.CATTAIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.POLLEN_BLOCK, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.SCULK_ECHOER, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HANGING_TENDRIL, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_ACACIA_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_BIRCH_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_DARK_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_JUNGLE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_MANGROVE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_OAK_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.HOLLOWED_SPRUCE_LOG, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(RegisterBlocks.FLOWERED_LILY_PAD, RenderLayer.getCutout());

        ParticleFactoryRegistry.getInstance().register(RegisterParticles.POLLEN, PollenParticle.PollenFactory::new);

        EntityModelLayerRegistry.registerModelLayer(SENSOR_TENDRILS_LAYER, SculkSensorTendrilModel::getTexturedModelData);

    }
}