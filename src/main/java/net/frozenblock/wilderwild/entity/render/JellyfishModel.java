package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishModel<T extends Jellyfish> extends HierarchicalModel<T> {

    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tentacleBase;
    private final ModelPart tentacle1;
    private final ModelPart tentacle2;
    private final ModelPart tentacle3;
    private final ModelPart tentacle4;
    private final ModelPart tentacle5;
    private final ModelPart tentacle6;
    private final ModelPart tentacle7;
    private final ModelPart tentacle8;

    public JellyfishModel(ModelPart root) {
        super(WilderWildClient::entityTranslucentEmissiveFixed);
        this.root = root;
        ModelPart bone = root.getChild("bone");
        this.body = bone.getChild("body");
        this.tentacleBase = bone.getChild("tentacleBase");
        this.tentacle1 = this.tentacleBase.getChild("tentacle1");
        this.tentacle2 = this.tentacleBase.getChild("tentacle2");
        this.tentacle3 = this.tentacleBase.getChild("tentacle3");
        this.tentacle4 = this.tentacleBase.getChild("tentacle4");
        this.tentacle5 = this.tentacleBase.getChild("tentacle5");
        this.tentacle6 = this.tentacleBase.getChild("tentacle6");
        this.tentacle7 = this.tentacleBase.getChild("tentacle7");
        this.tentacle8 = this.tentacleBase.getChild("tentacle8");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition tentacleBase = bone.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        PartDefinition tentacle1 = tentacleBase.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        PartDefinition tentacle2 = tentacleBase.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, -2.5F, 0.0F, -0.7854F, 0.0F));
        PartDefinition tentacle3 = tentacleBase.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        PartDefinition tentacle4 = tentacleBase.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 2.5F, 0.0F, -2.3562F, 0.0F));
        PartDefinition tentacle5 = tentacleBase.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        PartDefinition tentacle6 = tentacleBase.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        PartDefinition tentacle7 = tentacleBase.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition tentacle8 = tentacleBase.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -2.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public float xRot;
    public float zRot;
    public float tentXRot;
    public float tentZRot;

    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int i, int j, float var5, float var6, float var7, float var8) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(this.xRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(this.zRot));
        this.body.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();

        poseStack.pushPose();
        poseStack.mulPose(Vector3f.XP.rotationDegrees(this.tentXRot));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(this.tentZRot));
        this.tentacleBase.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
        poseStack.popPose();
    }

    private static final float pi180 = Mth.PI / 180;
    private static final float eightPi = -8 * pi180;

    @Override
    public void prepareMobModel(T jelly, float f, float g, float h) {
        this.xRot = -(jelly.xRot1 + h * (jelly.xBodyRot - jelly.xRot1));
        this.zRot = -(jelly.zRot1 + h * (jelly.zBodyRot - jelly.zRot1));
        this.tentXRot = -(jelly.xRot6 + h * (jelly.xRot5 - jelly.xRot6));
        this.tentZRot = -(jelly.zRot6 + h * (jelly.zRot5 - jelly.zRot6));
    }

    @Override
    public void setupAnim(@NotNull T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float animation = limbSwing * 2;
        float movementDelta = Math.min(limbSwingAmount * 26.6666667F, 1.0F);
        float tentRot = Mth.rotLerp(movementDelta, (float) (-Math.sin((ageInTicks - 10) * 0.1F) * 0.2F) + eightPi, (float) (-Math.sin(animation + 5) * 20 - 7.5F) * pi180);

        this.tentacle1.xRot = tentRot;
        this.tentacle3.xRot = tentRot;
        this.tentacle5.xRot = tentRot;
        this.tentacle7.xRot = tentRot;
        this.tentacle2.xRot = tentRot;
        this.tentacle4.xRot = tentRot;
        this.tentacle6.xRot = tentRot;
        this.tentacle8.xRot = tentRot;

        //SQUASH & STRETCH
        float sin = (float) -Math.sin(animation);
        float sinIdle = (float) (Math.sin(ageInTicks * 0.1F) * 0.2F);
        float squashStretch = 1F + (-sin * 0.25F);
        float squash = Mth.lerp(movementDelta, sinIdle + 1, squashStretch);

        this.body.xScale = squash;
        this.body.zScale = squash;
        this.body.yScale = Mth.lerp(movementDelta, -sinIdle + 1, 1.25F + (sin * 0.75F));

        this.body.y = Mth.lerp(movementDelta, 0, 3.5F - (squashStretch * 3.5F));
        this.tentacleBase.y = Mth.lerp(movementDelta, (-sinIdle * 2.0F) + 1.8F, (6F - (squashStretch * 5F)) * 2);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
