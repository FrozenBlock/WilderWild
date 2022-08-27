package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;

// Made with Blockbench 4.3.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class JellyFishModel extends EntityModel<Entity> {
    private final ModelPart body;
    private final ModelPart tentacle1;
    private final ModelPart tentacle2;
    private final ModelPart tentacle3;
    private final ModelPart tentacle4;
    private final ModelPart tentacle5;
    private final ModelPart tentacle6;
    private final ModelPart tentacle7;
    private final ModelPart tentacle8;

    public JellyFishModel(ModelPart root) {
        super(RenderType::entityTranslucentEmissive);
        this.body = root.getChild("bone");
        this.tentacle1 = body.getChild("tentacle1");
        this.tentacle2 = body.getChild("tentacle2");
        this.tentacle3 = body.getChild("tentacle3");
        this.tentacle4 = body.getChild("tentacle4");
        this.tentacle5 = body.getChild("tentacle5");
        this.tentacle6 = body.getChild("tentacle6");
        this.tentacle7 = body.getChild("tentacle7");
        this.tentacle8 = body.getChild("tentacle8");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition tentacle1 = body.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -3.0F));
        PartDefinition tentacle2 = body.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 2.0F, -2.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition tentacle3 = body.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition tentacle4 = body.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 2.0F, 2.5F, 0.0F, -2.3562F, 0.0F));
        PartDefinition tentacle5 = body.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition tentacle6 = body.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        PartDefinition tentacle7 = body.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition tentacle8 = body.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.0F, -2.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }
    @Override
    public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {

    }
}