package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WildClientMod;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.block.entity.BellBlockEntity;
import net.minecraft.block.entity.SculkSensorBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;


@Environment(EnvType.CLIENT)
public class SculkSensorTendrilRenderer implements BlockEntityRenderer<SculkSensorBlockEntity> {
    private static SpriteIdentifier TENDRIL_TEXTURE = null;
    private final ModelPart ne;
    private final ModelPart nw;
    private final ModelPart se;
    private final ModelPart sw;

    public SculkSensorTendrilRenderer(BlockEntityRendererFactory.Context ctx, ModelPart root) {
        this.sw = root.getChild("sw");
        this.se = root.getChild("se");
        this.nw = root.getChild("nw");
        this.ne = root.getChild("ne");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("ne", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(-5.0F,24.0F,-5.0F));
        modelPartData.addChild("nw", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(5.0F,24.0F,-5.0F));
        modelPartData.addChild("se", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(-5.0F,24.0F,5.0F));
        modelPartData.addChild("sw", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(5.0F,24.0F,5.0F));
        return TexturedModelData.of(modelData,64,64);
    }

    public void setAngles(SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float tickDelta = animationProgress - (float)entity.age;
        setTendrilPitches(entity, animationProgress, tickDelta);
        this.ne.yaw = 0.7854F;
        this.nw.yaw = -0.7854F;
        this.se.yaw = -0.7854F;
        this.sw.yaw = 0.7854F;
    }
    private void setTendrilPitches(SculkSensorTendrilEntity tendril, float animationProgress, float tickDelta) {
        float r = (float) (Math.PI / 180);
        float f = tendril.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * (25 * r));
        float g = tendril.getTendrilPitch(tickDelta) * (float)(-Math.sin((double)animationProgress * 2.25D) * (25 * r));

        this.ne.pitch = f;
        this.nw.pitch = g;
        this.se.pitch = g;
        this.sw.pitch = f;

    }


    private VertexConsumerProvider vertexConsumerProvider;
    VertexConsumer vertexConsumer = TENDRIL_TEXTURE.getVertexConsumer(vertexConsumerProvider, RenderLayer::getEntityCutout);

    @Override
    public void render(SculkSensorBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        ne.render(matrices, (VertexConsumer) vertices, light, overlay);
        nw.render(matrices, (VertexConsumer) vertices, light, overlay);
        se.render(matrices, (VertexConsumer) vertices, light, overlay);
        sw.render(matrices, (VertexConsumer) vertices, light, overlay);
    }

    @Override
    public boolean rendersOutsideBoundingBox(SculkSensorBlockEntity blockEntity) {
        return BlockEntityRenderer.super.rendersOutsideBoundingBox(blockEntity);
    }

    @Override
    public int getRenderDistance() {
        return BlockEntityRenderer.super.getRenderDistance();
    }

    @Override
    public boolean isInRenderDistance(SculkSensorBlockEntity blockEntity, Vec3d pos) {
        return BlockEntityRenderer.super.isInRenderDistance(blockEntity, pos);
    }
    static {
        TENDRIL_TEXTURE = new SpriteIdentifier(new Identifier(WilderWild.MOD_ID), new Identifier("textures/entity/sculk_sensor_tendrils/inactive.png"));
    }
}