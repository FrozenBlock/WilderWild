package net.frozenblock.wilderwild.entity.render;

import com.google.common.collect.Lists;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.FireflyEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

import java.util.List;

public class FireflyEntityRenderer extends EntityRenderer<FireflyEntity> {

    //CREDIT TO magistermaks ON GITHUB!!

    public FireflyEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    private final Identifier TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/firefly/firefly_off.png");
    private final Identifier OVERLAY_TEXTURE = new Identifier(WilderWild.MOD_ID, "textures/entity/firefly/firefly_on.png");
    private final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);
    private final RenderLayer OVERLAY = RenderLayer.getEntityTranslucentEmissive(OVERLAY_TEXTURE); //how do i get this to work

    @Override
    public void render(FireflyEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(1, 1, 1); // you may need to adjust this
        matrixStack.translate(0, -0.1, 0);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));

        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(LAYER);

        int j = getLight(i);

        vertex(vertexConsumer, matrix4f, matrix3f, j, 0.0F, 0, 0, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, j, 1.0F, 0, 1, 1);
        vertex(vertexConsumer, matrix4f, matrix3f, j, 1.0F, 1, 1, 0);
        vertex(vertexConsumer, matrix4f, matrix3f, j, 0.0F, 1, 0, 0);

        matrixStack.pop();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public Identifier getTexture(FireflyEntity entity) {
        return TEXTURE;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int i, float f, int j, int k, int l) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(k, l)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(i)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
    }

    protected int getLight(int i) {
        return 240;
    }

}
