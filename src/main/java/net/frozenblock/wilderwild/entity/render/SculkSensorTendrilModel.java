package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SculkSensorTendrilModel<T extends SculkSensorTendrilEntity> extends EntityModel<SculkSensorTendrilEntity> {
    private final ModelPart northeast;
    private final ModelPart northwest;
    private final ModelPart southeast;
    private final ModelPart southwest;

    public SculkSensorTendrilModel(ModelPart root) {
        this.southwest = root.getChild("southwest");
        this.southeast = root.getChild("southeast");
        this.northwest = root.getChild("northwest");
        this.northeast = root.getChild("northeast");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("northeast", ModelPartBuilder.create().uv(0,8).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(-5.0F,24.0F,-5.0F));
        modelPartData.addChild("northwest", ModelPartBuilder.create().uv(0,8).cuboid(0.0F, -8.0F, -4.0F, 0.002F, 8.0F, 8.0F), ModelTransform.pivot(5.0F,24.0F,-5.0F));
        modelPartData.addChild("southeast", ModelPartBuilder.create().uv(0,8).cuboid(0.0F, -8.0F, -4.0F, 0.002F, 8.0F, 8.0F), ModelTransform.pivot(-5.0F,24.0F,5.0F));
        modelPartData.addChild("southwest", ModelPartBuilder.create().uv(0,8).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 8.0F, 0.002F), ModelTransform.pivot(5.0F,24.0F,5.0F));
        return TexturedModelData.of(modelData,64,64);
    }
    public void setAngles(SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float tickDelta = animationProgress - (float)entity.age;
        setTendrilPitches(entity, animationProgress, tickDelta);
        this.northeast.yaw = 0.7854F;
        this.northwest.yaw = 0.7854F;
        this.southeast.yaw = 0.7854F;
        this.southwest.yaw = 0.7854F;
    }
    private void setTendrilPitches(SculkSensorTendrilEntity tendril, float animationProgress, float tickDelta) {
        float f = tendril.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        this.northeast.roll = f;
        this.northwest.pitch = f;
        this.southeast.pitch = f;
        this.southwest.roll = f;
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        northeast.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        northwest.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        southeast.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        southwest.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
