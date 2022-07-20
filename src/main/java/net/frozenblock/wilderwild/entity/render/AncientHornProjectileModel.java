package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    private final ModelPart bone;
    private final ModelPart front;
    private final ModelPart middle;
    private final ModelPart back;

    public AncientHornProjectileModel(ModelPart root) {
        super(RenderType::entityTranslucentEmissive);
        this.bone = root.getChild("bone");
        this.front = bone.getChild("front");
        this.middle = bone.getChild("middle");
        this.back = bone.getChild("back");
    }

    public static LayerDefinition getTexturedModelData() {

        MeshDefinition modelData = new MeshDefinition();

        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition front = bone.addOrReplaceChild("front", CubeListBuilder.create().texOffs(0, 32).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -4.0F));

        PartDefinition middle = bone.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 16).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition back = bone.addOrReplaceChild("back", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 4.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta, AncientHornProjectile entity) {
        matrices.scale(1.0F, 1.0F, 1.0F);

        this.bone.yRot = (float) (90 * (Math.PI / 180));
        this.bone.xRot = (float) (90 * (Math.PI / 180));
        this.bone.x = 4;
        this.bone.y = 0;
        this.bone.z = 0;

        float aliveDelta = entity.aliveTicks + tickDelta;

        float pulse2Extra = 8.0F / 1.5F;
        float pulse3Extra = 8.0F / 3.0F;

        float pulse = (float) ((Math.sin((aliveDelta * Math.PI) / 5) / 6) + 1);
        float pulse2 = (float) ((Math.sin(((aliveDelta + pulse2Extra) * Math.PI) / 5) / 6) + 1);
        float pulse3 = (float) ((Math.sin(((aliveDelta + pulse3Extra) * Math.PI) / 5) / 6) + 1);

        this.front.xScale = pulse;
        this.front.yScale = pulse;
        this.front.z = pulse3 * 2.0F - 6.0F;

        this.middle.xScale = pulse2;
        this.middle.yScale = pulse2;
        this.middle.z = pulse * 2.0F - 2.0F;

        this.back.xScale = pulse3;
        this.back.yScale = pulse3;
        this.back.z = pulse2 * 2.0F + 2.0F;

        this.bone.render(matrices, vertices, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {

    }
}
