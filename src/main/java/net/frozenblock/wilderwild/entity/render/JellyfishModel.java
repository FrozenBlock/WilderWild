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

import java.util.Arrays;

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
    private final ModelPart[] tentacles = new ModelPart[8];

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
        Arrays.setAll(this.tentacles, i -> this.tentacleRot.getChild(createTentacleName(i)));
    }

    private static String createTentacleName(int i) {
        return "tentacle" + (i + 1);
    }

    private static final float pi180 = Mth.PI / 180;

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 0.0F));
        PartDefinition tentacleRot = bone.addOrReplaceChild("tentacleRot", CubeListBuilder.create(), PartPose.offset(0.0F, -14.0F, 0.0F));
        PartDefinition tentacle1 = tentacleRot.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, -3.0F));
        PartDefinition tentacle2 = tentacleRot.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 2.0F, -2.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition tentacle3 = tentacleRot.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition tentacle4 = tentacleRot.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 2.0F, 2.5F, 0.0F, -2.3562F, 0.0F));
        PartDefinition tentacle5 = tentacleRot.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition tentacle6 = tentacleRot.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        PartDefinition tentacle7 = tentacleRot.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 2.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition tentacle8 = tentacleRot.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 2.0F, -2.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public float xRot;
    public float zRot;
    public float tentXRot;
    public float tentZRot;

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float lightProg) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.zRot));
        this.body.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, lightProg);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.tentXRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.tentZRot));
        this.tentacleRot.render(poseStack, vertexConsumer, 1, j, 1.0F, 1.0F, 1.0F, lightProg);
        poseStack.popPose();
    }

    public void renderDeez(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float lightProg) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.zRot));
        this.body.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, lightProg);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(-this.tentXRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-this.tentZRot));
        this.tentacleRot.render(poseStack, vertexConsumer, 1, j, 1.0F, 1.0F, 1.0F, lightProg);
        poseStack.popPose();
    }

    private void animateTentacles(T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks) {
        float mult = 15;

        float animation = ageInTicks * 10;
        /*
        float cos = (Mth.cos(animation) * mult - mult) * pi180;
        float cos1 = (Mth.cos(animation + 30) * mult - mult) * pi180;
        float cos2 = (Mth.cos(animation + 60) * mult - mult) * pi180;
        float cos3 = (Mth.cos(animation + 90) * mult - mult) * pi180;

        float sin = (Mth.sin(animation) * mult - mult) * pi180;
        float sin1 = (Mth.sin(animation + 30) * mult - mult) * pi180;
        float sin2 = (Mth.sin(animation + 60) * mult - mult) * pi180;
        float sin3 = (Mth.sin(animation + 90) * mult - mult) * pi180;

        //cardinal tentacles
        this.tentacle1.xRot = cos;
        this.tentacle3.xRot = cos1;
        this.tentacle5.xRot = cos2;
        this.tentacle7.xRot = cos3;

        //intermediate tentacles
        this.tentacle2.xRot = sin;
        this.tentacle4.xRot = sin1;
        this.tentacle6.xRot = sin2;
        this.tentacle8.xRot = sin3;
        */

        float cos = (Mth.cos(animation) * mult - mult) * pi180;
        //cardinal tentacles
        this.tentacle1.xRot = cos;
        this.tentacle3.xRot = cos;
        this.tentacle5.xRot = cos;
        this.tentacle7.xRot = cos;

        //intermediate tentacles
        this.tentacle2.xRot = cos;
        this.tentacle4.xRot = cos;
        this.tentacle6.xRot = cos;
        this.tentacle8.xRot = cos;
    }

    @Override
    public void setupAnim(@NotNull T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.animateTentacles(jellyfish, limbSwing, limbSwingAmount, ageInTicks / 10);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}