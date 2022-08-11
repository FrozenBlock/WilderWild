package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.misc.SculkSensorTickInterface;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class SculkSensorBlockEntityRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart base;
    private final ModelPart ne;
    private final ModelPart se;

    private static final float pi = (float) Math.PI;
    private static final float merp25 = 25 * (pi / 180);

    private static final RenderLayer SENSOR_LAYER = RenderLayer.getEntityCutout(WilderWild.id("textures/entity/sculk_sensor/inactive.png"));
    private static final RenderLayer ACTIVE_SENSOR_LAYER = RenderLayer.getEntityCutout(WilderWild.id("textures/entity/sculk_sensor/active.png"));

    public SculkSensorBlockEntityRenderer(Context ctx) {
        ModelPart root = ctx.getLayerModelPart(WilderWildClient.SCULK_SENSOR);
        this.base = root.getChild("base");
        this.se = root.getChild("se");
        this.ne = root.getChild("ne");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("base", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), ModelTransform.of(8.0F, 0.0F, 8.0F, 0, 0.0F, pi));
        modelPartData.addChild("ne", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F, 8.0F, 3.0F, 0, -0.7854F, pi));
        modelPartData.addChild("se", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F, 8.0F, 13.0F, 0, 0.7854F, pi));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (WilderWild.RENDER_TENDRILS) {
            SculkSensorTickInterface tickInterface = ((SculkSensorTickInterface) entity);
            if (tickInterface.isActive()) {
                int prevTicks = tickInterface.getPrevAnimTicks();
                float pitch = (prevTicks + tickDelta * (tickInterface.getAnimTicks() - prevTicks)) * 0.1F;
                float animProg = (tickInterface.getAge() + tickDelta) * 2.25F;
                this.ne.pitch = pitch * ((float) Math.cos(animProg) * merp25);
                this.se.pitch = pitch * (-(float) Math.sin(animProg) * merp25);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ACTIVE_SENSOR_LAYER);
                this.base.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.translate(0.625, 0, 0.625);
                this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.translate(0, 0, -1.25);
                this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            } else {
                this.ne.pitch = 0;
                this.se.pitch = 0;
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SENSOR_LAYER);
                this.base.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.translate(0.625, 0, 0.625);
                this.ne.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
                matrices.translate(0, 0, -1.25);
                this.se.render(matrices, vertexConsumer, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

}