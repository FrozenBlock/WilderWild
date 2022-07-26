package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    private final ModelPart bone;
    private final ModelPart front;
    private final ModelPart middle;
    private final ModelPart back;

    public AncientHornProjectileModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucentEmissive);
        this.bone = root.getChild("bone");
        this.front = bone.getChild("front");
        this.middle = bone.getChild("middle");
        this.back = bone.getChild("back");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();

        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData front = bone.addChild("front", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -4.0F));

        ModelPartData middle = bone.addChild("middle", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData back = bone.addChild("back", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 4.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta, AncientHornProjectile entity, float scale) {
        matrices.scale(scale, scale, scale);

        this.bone.yaw = (float) (90 * (Math.PI / 180));
        this.bone.pitch = (float) (90 * (Math.PI / 180));
        this.bone.pivotX = 4;
        this.bone.pivotY = 0;
        this.bone.pivotZ = 0;

        float aliveDelta = entity.aliveTicks + tickDelta;

        float pulse2Extra = 8.0F / 1.5F;
        float pulse3Extra = 8.0F / 3.0F;

        float pulse = (float) ((Math.sin((aliveDelta * Math.PI) / 5) / 6) + 1);
        float pulse2 = (float) ((Math.sin(((aliveDelta + pulse2Extra) * Math.PI) / 5) / 6) + 1);
        float pulse3 = (float) ((Math.sin(((aliveDelta + pulse3Extra) * Math.PI) / 5) / 6) + 1);

        this.front.xScale = pulse;
        this.front.yScale = pulse;
        this.front.pivotZ = pulse3 * 2.0F - 6.0F;

        this.middle.xScale = pulse2;
        this.middle.yScale = pulse2;
        this.middle.pivotZ = pulse * 2.0F - 2.0F;

        this.back.xScale = pulse3;
        this.back.yScale = pulse3;
        this.back.pivotZ = pulse2 * 2.0F + 2.0F;

        this.bone.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
