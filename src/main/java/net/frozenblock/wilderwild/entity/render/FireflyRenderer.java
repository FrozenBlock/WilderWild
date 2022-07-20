package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FireflyRenderer extends EntityRenderer<Firefly> {

    //CREDIT TO magistermaks ON GITHUB!!

    public FireflyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    private static final ResourceLocation TEXTURE = WilderWild.id("textures/entity/firefly/firefly_off.png");
    private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);

    private static final ResourceLocation NECTAR = WilderWild.id("textures/entity/firefly/nectar.png");
    private static final RenderType NECTAR_LAYER = RenderType.entityCutout(NECTAR);
    private static final ResourceLocation NECTAR_FLAP = WilderWild.id("textures/entity/firefly/nectar_wings_down.png");
    private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(NECTAR_FLAP);

    private static final double yOffset = 0.155F;

    @Override
    public void render(Firefly entity, float yaw, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
        boolean nectar = false;
        if (entity.hasCustomName()) {
            nectar = Objects.requireNonNull(entity.getCustomName()).getString().toLowerCase().contains("nectar");
        }
        if (this.shouldShowName(entity)) {
            this.renderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
        int age = entity.getFlickerAge();
        boolean flickers = entity.flickers();
        float scale = entity.getScale() == 1.5F ? 1.5F : entity.getScale() - (tickDelta * 0.001875F); //0.0375
        matrices.pushPose();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        PoseStack.Pose entry = matrices.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(nectar ? age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER : LAYER);

        int overlay = getOverlay(entity, 0);

        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0, overlay);
        vertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0, overlay);

        matrices.popPose();

        //OVERLAY
        matrices.pushPose();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        entry = matrices.last();
        matrix4f = entry.pose();
        matrix3f = entry.normal();

        if (!nectar) {
            vertexConsumer = vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_" + entity.getColor() + ".png")));
        } else { //NECTAR OVERLAY
            vertexConsumer = vertexConsumers.getBuffer(RenderType.entityTranslucentEmissive(WilderWild.id("textures/entity/firefly/nectar_overlay.png")));
        }

        vertexPulsate(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1, age, flickers, tickDelta, overlay);
        vertexPulsate(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1, age, flickers, tickDelta, overlay);
        vertexPulsate(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0, age, flickers, tickDelta, overlay);
        vertexPulsate(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0, age, flickers, tickDelta, overlay);

        matrices.popPose();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(@NotNull Firefly entity) {
        return TEXTURE;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int overlay) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(k, l)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    private static void vertexPulsate(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int age, boolean flickers, float tickDelta, int overlay) {
        int colors = !flickers ? (int) ((int) Math.max((255 * (Math.cos(((age + tickDelta) * Math.PI) / 20))), 0)) : (int) ((int) (255 * (Math.cos(((age + tickDelta) * Math.PI) / 40))) + 127.5);
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(colors, colors, colors, colors)
                .uv(k, l)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
    }

    public static int getOverlay(Firefly entity, float whiteOverlayProgress) {
        return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

}
