package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishModel<T extends Jellyfish> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart bone;
    private final ModelPart body;
    private final ModelPart tentacleRot;
    private final ModelPart tentacle1;
    private final ModelPart tentacle2;
    private final ModelPart tentacle3;
    private final ModelPart tentacle4;
    private final ModelPart tentacle5;
    private final ModelPart tentacle6;
    private final ModelPart tentacle7;
    private final ModelPart tentacle8;

    public JellyfishModel(ModelPart root) {
        super(RenderType::entityTranslucentEmissive);
        this.root = root;
        this.bone = root.getChild("bone");
        this.body = this.bone.getChild("body");
        this.tentacleRot = this.bone.getChild("tentacleRot");
        this.tentacle1 = this.tentacleRot.getChild("tentacle1");
        this.tentacle2 = this.tentacleRot.getChild("tentacle2");
        this.tentacle3 = this.tentacleRot.getChild("tentacle3");
        this.tentacle4 = this.tentacleRot.getChild("tentacle4");
        this.tentacle5 = this.tentacleRot.getChild("tentacle5");
        this.tentacle6 = this.tentacleRot.getChild("tentacle6");
        this.tentacle7 = this.tentacleRot.getChild("tentacle7");
        this.tentacle8 = this.tentacleRot.getChild("tentacle8");
    }

    private static String createTentacleName(int i) {
        return "tentacle" + (i + 1);
    }

    private static final float pi180 = Mth.PI / 180;

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition tentacleRot = bone.addOrReplaceChild("tentacleRot", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition tentacle1 = tentacleRot.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        PartDefinition tentacle2 = tentacleRot.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, -2.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition tentacle3 = tentacleRot.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition tentacle4 = tentacleRot.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 2.5F, 0.0F, -2.3562F, 0.0F));
        PartDefinition tentacle5 = tentacleRot.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition tentacle6 = tentacleRot.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        PartDefinition tentacle7 = tentacleRot.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition tentacle8 = tentacleRot.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -2.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public float xRot;
    public float zRot;
    public float tentXRot;
    public float tentZRot;
    public float lightProg;

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.zRot));
        this.body.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.tentXRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.tentZRot));
        this.tentacleRot.render(poseStack, vertexConsumer, 1, j, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    public void renderDeez(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.zRot));
        this.body.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, this.lightProg);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.tentXRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.tentZRot));
        this.tentacleRot.render(poseStack, vertexConsumer, 1, j, 1.0F, 1.0F, 1.0F, this.lightProg);
        poseStack.popPose();
    }
    
    private float lerp(float delta, float start, float end) {
        return Mth.lerp(delta, start, end);
    }
    
    private float lerpAngleDegrees(float delta, float start, float end) {
        return Mth.rotLerp(delta, start, end);
    }

    @Override
    public void setupAnim(@NotNull T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float animation = limbSwing * 2;

        float sin = -Mth.sin(animation);
        float sinTentacle = (-Mth.sin(animation + 5) * 20 - 7.5F) * pi180;
        float sinIdle = Mth.sin(ageInTicks) * 0.2;
        
        //CARDINAL TENTACLES
        this.tentacle1.xRot = sinTentacle;
        this.tentacle3.xRot = sinTentacle;
        this.tentacle5.xRot = sinTentacle;
        this.tentacle7.xRot = sinTentacle;

        //INTERMEDIATE TENTACLES
        this.tentacle2.xRot = sinTentacle;
        this.tentacle4.xRot = sinTentacle;
        this.tentacle6.xRot = sinTentacle;
        this.tentacle8.xRot = sinTentacle;
        
        //SQUASH & STRETCH
        float squashStretch = 1F + (-sin * 0.25F);

        this.body.xScale = lerp(animation, sinIdle + 1, squashStretch);
        this.body.zScale = lerp(animation, sinIdle + 1, squashStretch);
        this.body.yScale = lerp(animation, -sinIdle + 1, 1.25F + (sin * 0.75F);

        this.body.y = lerp(animation, 0, 3.5F -(squashStretch * 3.5F));
        this.tentacleRot.y = lerpAngleDegrees(animation, 0, (6F -(squashStretch * 5F)) * 2));
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
