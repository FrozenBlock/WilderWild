package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.block.entity.NewSculkSensorBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkSensorBlock;
import net.minecraft.block.enums.SculkSensorPhase;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Environment(EnvType.CLIENT)
public class SculkSensorBlockEntityRenderer<T extends NewSculkSensorBlockEntity> implements BlockEntityRenderer<T> {
    private final ModelPart base;
    private final ModelPart ne;
    private final ModelPart se;

    private static final float pi = (float) Math.PI;
    private static final float merp25 = 25 * ((float)(Math.PI / 180));

    RenderLayer SENSOR_LAYER = RenderLayer.getEntityCutout(new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/inactive.png"));
    RenderLayer ACTIVE_SENSOR_LAYER = RenderLayer.getEntityCutout(new Identifier(WilderWild.MOD_ID, "textures/entity/sculk_sensor_tendrils/active_overlay.png"));

    public SculkSensorBlockEntityRenderer(Context ctx) {
        ModelPart root = ctx.getLayerModelPart(WildClientMod.SCULK_SENSOR);
        this.base = root.getChild("base");
        this.se = root.getChild("se");
        this.ne = root.getChild("ne");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("base", ModelPartBuilder.create().uv(0,0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), ModelTransform.of(8.0F,0.0F,8.0F,0,0.0F, pi));
        modelPartData.addChild("ne", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F,8.0F,3.0F,0,-0.7854F, pi));
        modelPartData.addChild("se", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.of(3.0F,8.0F,13.0F,0,0.7854F, pi));
        return TexturedModelData.of(modelData,64,64);
    }

    public void render(T entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (WildClientMod.RENDER_TENDRILS) {
            BlockState blockState = entity.getWorld()!=null ? entity.getCachedState() : Blocks.SCULK_SENSOR.getDefaultState();
            if (blockState.getBlock() instanceof SculkSensorBlock) {
                boolean active = blockState.get(Properties.SCULK_SENSOR_PHASE) == SculkSensorPhase.ACTIVE;
                setTendrilPitches(entity.age + tickDelta, tickDelta, entity, active);
                VertexConsumer vertexConsumer = vertexConsumers.getBuffer(active ? ACTIVE_SENSOR_LAYER : SENSOR_LAYER);
                matrices.push();
                this.render(matrices, vertexConsumer, light, overlay);
                matrices.pop();
            }
        }
    }

    private void setTendrilPitches(float animationProgress, float tickDelta, NewSculkSensorBlockEntity entity, boolean active) {
        if (active) {
            float pitch = MathHelper.lerp(tickDelta, (float) entity.prevAnimTicks, (float) entity.animTicks) / 10.0F;
            this.ne.pitch = pitch * (float) (Math.cos(animationProgress * 2.25D) * merp25);
            this.se.pitch = pitch * (float) (-Math.sin(animationProgress * 2.25D) * merp25);
        } else {
            this.ne.pitch = 0;
            this.se.pitch = 0;
        }
    }

    private void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay) {
        this.base.render(matrices, vertices, light, overlay);
        this.ne.render(matrices, vertices, light, overlay);
        this.se.render(matrices, vertices, light, overlay);
        matrices.translate(0.625, 0, 0.625);
        this.ne.render(matrices, vertices, light, overlay);
        matrices.translate(0, 0, -1.25);
        this.se.render(matrices, vertices, light, overlay);
    }
}
