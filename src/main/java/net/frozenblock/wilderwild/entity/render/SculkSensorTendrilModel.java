package net.frozenblock.wilderwild.entity.render;

import net.frozenblock.wilderwild.entity.SculkSensorTendrilEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SculkSensorTendrilModel<T extends SculkSensorTendrilEntity> extends EntityModel<SculkSensorTendrilEntity> {
    private final ModelPart bone;
    private final ModelPart ne;
    private final ModelPart nw;
    private final ModelPart se;
    private final ModelPart sw;

    public SculkSensorTendrilModel(ModelPart root) {
        this.bone = root.getChild("bone");
        this.ne = this.bone.getChild("nw");
        this.nw = this.bone.getChild("ne");
        this.sw = this.bone.getChild("sw");
        this.se = this.bone.getChild("se");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData1 = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData1.addChild("ne", ModelPartBuilder.create().uv(0,8).cuboid(-4.0F, 8.0F, 0.0F, 8.0F, 8.0F, 0.0F), ModelTransform.pivot(-5.0F,12.0F,-5.0F));
        modelPartData1.addChild("nw", ModelPartBuilder.create().uv(0,0).cuboid(0.0F, 8.0F, -4.0F, 0.0F, 8.0F, 8.0F), ModelTransform.pivot(5.0F,12.0F,-5.0F));
        modelPartData1.addChild("se", ModelPartBuilder.create().uv(0,8).cuboid(0.0F, 8.0F, -4.0F, 0.0F, 8.0F, 8.0F), ModelTransform.pivot(-5.0F,12.0F,5.0F));
        modelPartData1.addChild("sw", ModelPartBuilder.create().uv(0,0).cuboid(-4.0F, 8.0F, 0.0F, 8.0F, 8.0F, 0.0F), ModelTransform.pivot(5.0F,12.0F,5.0F));
        return TexturedModelData.of(modelData,64,64);
    }
    public void setAngles(SculkSensorTendrilEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        float tickDelta = animationProgress - (float)entity.age;
        float r = (float) (Math.PI / 180);
        this.ne.yaw = 45F * r;
        this.nw.yaw = 45F * r;
        this.se.yaw = 45F * r;
        this.sw.yaw = 45F * r;
        setTendrilPitches(entity, animationProgress, tickDelta);
    }

    private void setTendrilPitches(SculkSensorTendrilEntity tendril, float animationProgress, float tickDelta) {
        float f = tendril.getTendrilPitch(tickDelta) * (float)(Math.cos((double)animationProgress * 2.25D) * 3.141592653589793D * 0.10000000149011612D);
        this.ne.pitch = f;
        this.nw.roll = f;
        this.se.pitch = f;
        this.sw.roll = f;
    }


    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        ne.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        nw.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        se.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        sw.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
