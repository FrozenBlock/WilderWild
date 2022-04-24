package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SculkSensorTendrilModel<T extends SculkSensorTendrilEntity> extends EntityModel<SculkSensorTendrilEntity> {
    private final ModelPart ne;
    private final ModelPart nw;
    private final ModelPart se;
    private final ModelPart sw;

    public SculkSensorTendrilModel(ModelPart root) {
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

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ne.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        nw.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        se.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        sw.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
