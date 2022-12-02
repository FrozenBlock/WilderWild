package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.misc.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class FireflyRenderer extends EntityRenderer<Firefly> {
    //CREDIT TO magistermaks ON GITHUB!!

    public FireflyRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/firefly/firefly_off.png");
    private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);

    private static final RenderType NECTAR_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar.png"));
    private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar_wings_down.png"));
    private static final RenderType NECTAR_OVERLAY = RenderType.entityTranslucentEmissive(WilderSharedConstants.id("textures/entity/firefly/nectar_overlay.png"), true);

    public static Object2ObjectMap<ResourceLocation, RenderType> layers = new Object2ObjectLinkedOpenHashMap<>() {{
        Object2ObjectMap<ResourceLocation, ResourceLocation> colors = new Object2ObjectLinkedOpenHashMap<>();
        WilderRegistry.FIREFLY_COLOR.forEach(color -> {
            colors.put(color.getKey(), color.getTexture());
        });
        colors.forEach((colorKey, texture) -> {
            put(colorKey, RenderType.entityTranslucentEmissive(texture));
        });
    }};

    private static final double yOffset = 0.155F;
    private static final Quaternion one80Quat = Vector3f.YP.rotationDegrees(180.0F);
    private static final float pi = (float) Math.PI;

    @Override
    public void render(Firefly entity, float yaw, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
        boolean nectar = false;

        if (entity.hasCustomName()) {
            nectar = entity.getCustomName().getString().toLowerCase().contains("nectar");
        }

        int age = entity.getFlickerAge();
        boolean flickers = entity.flickers();
        float prevScale = entity.getPrevScale();
        float scale = prevScale + (tickDelta * (entity.getScale() - prevScale));

        int overlay = getOverlay(entity, 0);

        matrices.pushPose();
        matrices.scale(scale, scale, scale);
        matrices.translate(0, yOffset, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(one80Quat);

        PoseStack.Pose entry = matrices.last();
        Matrix4f matrix4f = entry.pose();
        Matrix3f matrix3f = entry.normal();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(nectar ? age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER : LAYER);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(1, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(255, 255, 255, 255)
                .uv(0, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        if (entity.getColor() != null && layers.get(entity.getColor().getKey()) != null) {
            if (!nectar) {
                vertexConsumer = vertexConsumers.getBuffer(layers.get(entity.getColor().getKey()));
            } else {
                vertexConsumer = vertexConsumers.getBuffer(NECTAR_OVERLAY);
            }
        } else {
            vertexConsumer = vertexConsumers.getBuffer(layers.get(FireflyColor.ON.getKey()));
        }

        int color = flickers ? (int) ((255 * (Math.cos(((age + tickDelta) * pi) * 0.025))) + 127.5) : (int) Math.max((255 * (Math.cos(((age + tickDelta) * pi) * 0.05))), 0);

        vertexConsumer
                .vertex(matrix4f, -0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(0, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, -0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(1, 1)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, 0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(1, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();
        vertexConsumer
                .vertex(matrix4f, -0.5F, 0.5F, 0.0F)
                .color(color, color, color, color)
                .uv(0, 0)
                .overlayCoords(overlay)
                .uv2(light)
                .normal(matrix3f, 0.0F, 1.0F, 0.0F)
                .endVertex();

        matrices.popPose();

        if (this.shouldShowName(entity)) {
            this.renderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
    }

    @Override
    public ResourceLocation getTextureLocation(Firefly entity) {
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

    private static void vertexPulsate(VertexConsumer vertexConsumer, Matrix4f matrix4f, Matrix3f matrix3f, int light, float f, int j, int k, int l, int colors, int overlay) {
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
