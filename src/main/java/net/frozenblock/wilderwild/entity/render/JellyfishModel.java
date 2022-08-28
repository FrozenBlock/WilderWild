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
        PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition tentacleRot = bone.addOrReplaceChild("tentacleRot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
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

    public void render(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j) {
        this.bone.render(poseStack, vertexConsumer, i, j, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    public void animate(@NotNull T jelly, float limbSwing, float tickDelta) {
        this.body.xRot = -Mth.lerp(tickDelta, jelly.xRot1, jelly.xBodyRot) * pi180;
        this.body.zRot = -Mth.lerp(tickDelta, jelly.zRot1, jelly.zBodyRot) * pi180;
        this.tentacleRot.xRot = -Mth.lerp(tickDelta, jelly.xRot6, jelly.xRot5) * pi180;
        this.tentacleRot.zRot = -Mth.lerp(tickDelta, jelly.zRot6, jelly.zRot5) * pi180;

        float animation = limbSwing * 2;

        float sin = -Mth.sin(animation);
        float cosTentacle = (-Mth.sin(animation + 5) * 20 - 7.5F) * pi180;
        //cardinal tentacles
        this.tentacle1.xRot = cosTentacle;
        this.tentacle3.xRot = cosTentacle;
        this.tentacle5.xRot = cosTentacle;
        this.tentacle7.xRot = cosTentacle;

        //intermediate tentacles
        this.tentacle2.xRot = cosTentacle;
        this.tentacle4.xRot = cosTentacle;
        this.tentacle6.xRot = cosTentacle;
        this.tentacle8.xRot = cosTentacle;

        float xZScale = 1F + (-sin * 0.25F);
        float ySin = 1.25F + (sin * 0.75F);

        this.body.xScale = xZScale;
        this.body.zScale = xZScale;

        this.body.yScale = ySin;
        this.body.y = 3.5F -(ySin * 3.5F);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float f, float g, float h, float i, float j) {

    }
}