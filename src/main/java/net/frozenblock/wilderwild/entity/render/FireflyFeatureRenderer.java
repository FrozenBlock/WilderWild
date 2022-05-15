package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class FireflyFeatureRenderer extends EyesFeatureRenderer<FireflyEntity, FireflyEntityModel<FireflyEntity>> {
    public RenderLayer GLOW;

    public FireflyFeatureRenderer(FeatureRendererContext<FireflyEntity, FireflyEntityModel<FireflyEntity>> featureRendererContext) {
        super(featureRendererContext);
        GLOW = RenderLayer.getEyes(new Identifier(WilderWild.MOD_ID, "textures/entity/firefly/firefly_on.png"));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, FireflyEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.GLOW);
            this.getContextModel().render(matrices, vertexConsumer, 15728640, OverlayTexture.DEFAULT_UV, calcGlow(entity), calcGlow(entity), calcGlow(entity), 1.0f);
    }

    private static float calcGlow(FireflyEntity firefly) {
        //help i dont know this stuff please fix
        float d = (float) ((float) Math.cos((1*Math.PI)/(20)));
        return MathHelper.clamp(d,0,1);
    }

    public RenderLayer getEyesTexture() {
        return this.GLOW;
    }
}
