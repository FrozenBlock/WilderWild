package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class DisplayLanternBlockEntityRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T> {
    private static final float pi = (float) Math.PI;
    private static final Quaternionf one80Quat = Axis.YP.rotationDegrees(180.0F);
    private final ItemRenderer itemRenderer;

    private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/firefly/firefly_off.png");
    private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);

    private static final RenderType NECTAR_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar.png"));
    private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar_wings_down.png"));
    private static final RenderType NECTAR_OVERLAY = RenderType.entityTranslucentEmissive(WilderSharedConstants.id("textures/entity/firefly/nectar_overlay.png"), true);

    public DisplayLanternBlockEntityRenderer(Context ctx) {
        ctx.bakeLayer(WilderWildClient.DISPLAY_LANTERN);
        this.itemRenderer = ctx.getItemRenderer();
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        return LayerDefinition.create(modelData, 64, 64);
    }

	@Override
    public void render(T lantern, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
        Quaternionf cam = Minecraft.getInstance().gameRenderer.getMainCamera().rotation();
        Optional<ItemStack> stack = lantern.getItem();
        if (!lantern.invEmpty() && stack.isPresent()) {
            matrices.pushPose();
            double extraHeight = lantern.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.25 : 0.125;
            matrices.translate(0.5, extraHeight, 0.5);
            matrices.scale(0.7F, 0.7F, 0.7F);
            float n = (lantern.age + tickDelta) / 20;
            matrices.mulPose(Axis.YP.rotation(n));
            this.itemRenderer.renderStatic(stack.get(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, lantern.getLevel(), 1);
            matrices.popPose();
        } else if (cam != null) {
            double extraHeight = lantern.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.38 : 0.225;
            for (DisplayLanternBlockEntity.FireflyInLantern entity : lantern.getFireflies()) {
                boolean nectar = entity.getCustomName().toLowerCase().contains("nectar");
                int age = entity.age;
                boolean flickers = entity.flickers;
                double ageDelta = age + tickDelta;

                matrices.pushPose();
                matrices.translate(entity.pos.x, extraHeight + Math.sin(ageDelta * 0.03) * 0.15, entity.pos.z);
                matrices.mulPose(cam);
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

                if (!nectar) {
                    vertexConsumer = vertexConsumers.getBuffer(layers.get(entity.getColor().getKey()));
                } else {
                    vertexConsumer = vertexConsumers.getBuffer(NECTAR_OVERLAY);
                }

                int color = flickers ? (int) ((255 * (Math.cos((ageDelta * pi) * 0.025))) + 127.5) : (int) Math.max((255 * (Math.cos((ageDelta * pi) * 0.05))), 0);

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
            }
        }
    }

    public static Object2ObjectMap<ResourceLocation, RenderType> layers = FireflyRenderer.layers;

}
