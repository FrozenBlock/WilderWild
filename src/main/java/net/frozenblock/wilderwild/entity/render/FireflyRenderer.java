package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class FireflyRenderer extends EntityRenderer<Firefly> {

    //CREDIT TO magistermaks ON GITHUB!!

    public FireflyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    private static final Identifier TEXTURE = WilderWild.id("textures/entity/firefly/firefly_off.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

    private static final Identifier NECTAR = WilderWild.id("textures/entity/firefly/nectar.png");
    private static final RenderLayer NECTAR_LAYER = RenderLayer.getEntityCutout(NECTAR);
    private static final Identifier NECTAR_FLAP = WilderWild.id("textures/entity/firefly/nectar_wings_down.png");
    private static final RenderLayer NECTAR_FLAP_LAYER = RenderLayer.getEntityCutout(NECTAR_FLAP);

    private static final double yOffset = 0.155F;
    private static final Quaternion one80Quat = Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F);

    @Override
    public void render(Firefly entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        boolean nectar = false;

        if (entity.hasCustomName()) {
            nectar = entity.getCustomName().getString().toLowerCase().contains("nectar");
        }

        int age = entity.getFlickerAge();
        boolean flickers = entity.flickers();
        float scale = entity.getScale() == 1.5F ? 1.5F : entity.getScale() - (tickDelta * 0.001875F);

        Quaternion rotation = this.dispatcher.getRotation();
        int overlay = getOverlay(entity, 0);

        matrices.push();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.multiply(rotation);
        matrices.multiply(one80Quat);

        MatrixStack.Entry entry = matrices.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(nectar ? age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER : LAYER);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(0, 1)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(1, 1)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(1, 0)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(1, 0)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();

        matrices.pop();

        //OVERLAY
        matrices.push();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.multiply(rotation);
        matrices.multiply(one80Quat);

        entry = matrices.peek();
        matrix4f = entry.getPositionMatrix();
        matrix3f = entry.getNormalMatrix();

        if (!nectar) {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_" + entity.getColor() + ".png")));
        } else {
            vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/nectar_overlay.png")));
        }

        int color = flickers ? (int) ((255 * (Math.cos(((age + tickDelta) * pi) / 40))) + 127.5) : (int) Math.max((255 * (Math.cos(((age + tickDelta) * pi) / 20))), 0);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .texture(0, 1)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .texture(1, 1)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .texture(1, 0)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .texture(1, 0)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();

        matrices.pop();

        if (this.hasLabel(entity)) {
            this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
    }

    @Override
    public Identifier getTexture(Firefly entity) {
        return TEXTURE;
    }

    private static final float pi = (float)Math.PI;

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int overlay) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .texture(k, l)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
    }

    private static void vertexPulsate(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int colors, int overlay) {
        vertexConsumer
                .vertex(matrix4f, f - 0.5F, j - 0.5F, 0.0F)
                .color(colors, colors, colors, colors)
                .texture(k, l)
                .overlay(overlay)
                .light(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .next();
    }

    public static int getOverlay(Firefly entity, float whiteOverlayProgress) {
        return OverlayTexture.packUv(OverlayTexture.getU(whiteOverlayProgress), OverlayTexture.getV(entity.hurtTime > 0 || entity.deathTime > 0));
    }

}
