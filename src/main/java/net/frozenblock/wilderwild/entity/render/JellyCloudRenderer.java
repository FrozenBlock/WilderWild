package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.JellyCloud;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class JellyCloudRenderer extends EntityRenderer<JellyCloud> {

    //CREDIT TO magistermaks ON GITHUB!!

    public JellyCloudRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    private static final double yOffset = 0.155F;
    private static final Quaternion one80Quat = Vector3f.YP.rotationDegrees(180.0F);
    private static final float pi = (float) Math.PI;

    @Override
    public void render(JellyCloud entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        float age = entity.getAge() + tickDelta;
        float scale = age < JellyCloud.STOPS_GROWING_TIME ? (age) / 35F : 1F;

        matrices.pushPose();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(one80Quat);

        PoseStack.Pose entry = matrices.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(WilderWild.id("textures/entity/jelly_cloud/" + (age / JellyCloud.TEXTURE_INCREASE_PERCENT) + ".png")));

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 1)
                .overlayCoords(0)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 1)
                .overlayCoords(0)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 0)
                .overlayCoords(0)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 0)
                .overlayCoords(0)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(JellyCloud entity) {
        return WilderWild.id("textures/entity/jelly_cloud/0.png");
    }

}
