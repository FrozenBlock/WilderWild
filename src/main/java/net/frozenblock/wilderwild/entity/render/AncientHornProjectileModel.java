package net.frozenblock.wilderwild.entity.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.AncientHornProjectileEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart body2;
    private final ModelPart body3;

    public float merp = (float) (90 * (Math.PI / 180));
    public float merp2 = (float) (-90 * (Math.PI / 180));
    public double pulse2Extra = 8 / 1.5;
    public double pulse3Extra = 8F / 3F;

    public AncientHornProjectileModel(ModelPart root) {
        super(RenderLayer::getEntityTranslucentEmissive);
        this.root = root;
        this.body = root.getChild("body");
        this.body2 = root.getChild("body2");
        this.body3 = root.getChild("body3");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData modelPartData2 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 32).cuboid(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.001F), ModelTransform.NONE);
        ModelPartData modelPartData3 = modelPartData.addChild("body2", ModelPartBuilder.create().uv(0, 16).cuboid(-8.0F, -8.0F, 4.0F, 16.0F, 16.0F, 0.001F), ModelTransform.NONE);
        ModelPartData modelPartData4 = modelPartData.addChild("body3", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, 8.0F, 16.0F, 16.0F, 0.001F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta, AncientHornProjectileEntity entity) {
        matrices.scale(1.5F, 1.5F, 1.5F);
        this.root.yaw = merp;
        this.root.pitch = merp2;

        float aliveDelta = entity.aliveTicks + tickDelta;
        float pulse = (float) ((Math.sin((aliveDelta * Math.PI) / 5) / 6) + 0.5);
        this.body.xScale = pulse;
        this.body.yScale = pulse;

        float pulse2 = (float) ((Math.sin(((aliveDelta + pulse2Extra) * Math.PI) / 5) / 6) + 0.5);
        this.body2.xScale = pulse2;
        this.body2.yScale = pulse2;

        float pulse3 = (float) ((Math.sin(((aliveDelta + pulse3Extra) * Math.PI) / 5) / 6) + 0.5);
        this.body3.xScale = pulse3;
        this.body3.yScale = pulse3;

        this.root.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
