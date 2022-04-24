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
        modelPartData1.addChild("right_tendril", ModelPartBuilder.create().uv(0,0).cuboid(0.0F, 0.0F, 0.0F, 6.0F, 7.0F, 0.002F), ModelTransform.pivot(-4.0F,-1F,0.0F));
        modelPartData1.addChild("left_tendril", ModelPartBuilder.create().uv(0,0).cuboid(0.0F, 0.0F, 0.0F, 6.0F, 7.0F, 0.002F), ModelTransform.pivot(4.0F,-1.0F,0.0F));
        return TexturedModelData.of(modelData,64,64);
    }
    public void setAngles(SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float tickDelta = animationProgress - (float)entity.age;
       setTendrilPitches(entity, animationProgress, tickDelta);
    }

    private void setTendrilPitches(SculkSensorTendrilEntity tendril, float animationProgress, float tickDelta) {
        float f = tendril.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        this.left_tendril.pitch = f;
        this.right_tendril.pitch = -f;
    }


    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        left_tendril.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        right_tendril.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
