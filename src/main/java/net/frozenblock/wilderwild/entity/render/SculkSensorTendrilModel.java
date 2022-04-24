package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SculkSensorTendrilModel<T extends SculkSensorTendrilEntity> extends EntityModel<SculkSensorTendrilEntity> {
    private final ModelPart bone;
    private final ModelPart right_tendril;
    private final ModelPart left_tendril;

    public SculkSensorTendrilModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.left_tendril = this.bone.getChild("left_tendril");
        this.right_tendril = this.bone.getChild("right_tendril");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData1.addChild("right_tendril", ModelPartBuilder.create().uv(0,32).cuboid(-16.0F, -12.5F, 0.0F, 16.0F, 16.0F, 0.002F), ModelTransform.pivot(-8.0F,-12.5F,0.0F));
        modelPartData1.addChild("left_tendril", ModelPartBuilder.create().uv(0,0).cuboid(0.0F, -12.5F, 0.0F, 16.0F, 16.0F, 0.002F), ModelTransform.pivot(8.0F,-12.5F,0.0F));
        return TexturedModelData.of(modelData,64,64);
    }
    public void setAngles(SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
       //code
    }


    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        left_tendril.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        right_tendril.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
