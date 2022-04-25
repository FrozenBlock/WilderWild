package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SculkSensorTendrilsFeature extends EyesFeatureRenderer<SculkSensorTendrilEntity, SculkSensorTendrilModel<SculkSensorTendrilEntity>> {

    public RenderLayer TENDRILS = RenderLayer.getEyes(new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/active.png"));

    public SculkSensorTendrilsFeature(FeatureRendererContext<SculkSensorTendrilEntity, SculkSensorTendrilModel<SculkSensorTendrilEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        //if (entity.shouldRender) { /* REPLACE WITH CLIENT SETTINGS CHECK */
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.TENDRILS);
            this.getContextModel().render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, calculateColors(entity), calculateColors(entity), calculateColors(entity), 1.0f);
        //}
    }

    private static float calculateColors(SculkSensorTendrilEntity tendrils) {
        if (tendrils.activeTicksLeft>0) { return 1F; }
        return 0F;
    }

    @Override
    public RenderLayer getEyesTexture() {
        return TENDRILS;
    }

}
