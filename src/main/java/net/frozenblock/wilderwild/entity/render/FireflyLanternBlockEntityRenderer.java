package net.frozenblock.wilderwild.entity.render;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.entity.FireflyLanternBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;

@Environment(EnvType.CLIENT)
public class FireflyLanternBlockEntityRenderer<T extends FireflyLanternBlockEntity> implements BlockEntityRenderer<T> {

    //TODO: Make four different Firefly ModelParts, move them up & down with Sin and Cos. I'll (AViewFromTheTop) handle the rest.

    private static final float pi = (float) Math.PI;
    private static final Quaternion one80Quat = Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F);

    private static final Identifier TEXTURE = WilderWild.id("textures/entity/firefly/firefly_off.png");
    private static final RenderLayer LAYER = RenderLayer.getEntityCutout(TEXTURE);

    private static final RenderLayer NECTAR_LAYER = RenderLayer.getEntityCutout(WilderWild.id("textures/entity/firefly/nectar.png"));
    private static final RenderLayer NECTAR_FLAP_LAYER = RenderLayer.getEntityCutout(WilderWild.id("textures/entity/firefly/nectar_wings_down.png"));
    private static final RenderLayer NECTAR_OVERLAY = RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/nectar_overlay.png"), true);

    public FireflyLanternBlockEntityRenderer(Context ctx) {
        ModelPart root = ctx.getLayerModelPart(WilderWildClient.FIREFLY_LANTERN);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(T lantern, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        Quaternion cam = MinecraftClient.getInstance().gameRenderer.getCamera().getRotation();
        if (cam != null) {
            for (FireflyLanternBlockEntity.FireflyInLantern entity : lantern.getFireflies()) {
                boolean nectar = entity.getCustomName().toLowerCase().contains("nectar");
                int age = entity.getAge();
                boolean flickers = entity.getFlickers();
                double ageDelta = age + tickDelta;

                matrices.push();
                matrices.translate(entity.pos.x, entity.pos.y + (Math.sin(((ageDelta) * 0.03)) * 0.15), entity.pos.z);
                matrices.multiply(cam);
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
                        .texture(0, 0)
                        .overlay(overlay)
                        .light(light)
                        .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                        .next();

                if (!nectar) {
                    vertexConsumer = vertexConsumers.getBuffer(layers.get(entity.getColor()));
                } else {
                    vertexConsumer = vertexConsumers.getBuffer(NECTAR_OVERLAY);
                }

                int color = flickers ? (int) ((255 * (Math.cos((ageDelta * pi) * 0.025))) + 127.5) : (int) Math.max((255 * (Math.cos((ageDelta * pi) * 0.05))), 0);

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
                        .texture(0, 0)
                        .overlay(overlay)
                        .light(light)
                        .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                        .next();

                matrices.pop();
            }
        }
    }

    public static Object2ObjectMap<String, RenderLayer> layers = new Object2ObjectLinkedOpenHashMap<>() {{
        put("on", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_on.png"), true));
        put("red", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_red.png"), true));
        put("orange", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_orange.png"), true));
        put("yellow", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_yellow.png"), true));
        put("lime", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_lime.png"), true));
        put("green", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_green.png"), true));
        put("cyan", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_cyan.png"), true));
        put("light_blue", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_light_blue.png"), true));
        put("blue", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_blue.png"), true));
        put("pink", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_pink.png"), true));
        put("magenta", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_magenta.png"), true));
        put("purple", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_purple.png"), true));
        put("black", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_black.png"), true));
        put("gray", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_gray.png"), true));
        put("light_gray", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_light_gray.png"), true));
        put("white", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_white.png"), true));
        put("brown", RenderLayer.getEntityTranslucentEmissive(WilderWild.id("textures/entity/firefly/firefly_brown.png"), true));
    }};

}