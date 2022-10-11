package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.render.FrozenRenderType;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileModel extends Model {
    private final ModelPart bone;
    private final ModelPart front;
    private final ModelPart middle;
    private final ModelPart back;

    public AncientHornProjectileModel(ModelPart root) {
        super(FrozenRenderType::entityTranslucentEmissiveFixed);
        this.bone = root.getChild("bone");
        this.front = bone.getChild("front");
        this.middle = bone.getChild("middle");
        this.back = bone.getChild("back");
    }

    private static final float pi = (float) Math.PI;
    private static final float bonePitchYaw = 1.57079632f;
    private static final float pulse2Extra = 8 / 1.5F;
    private static final float pulse3Extra = 8 / 3;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone",
                CubeListBuilder.create(),
                PartPose.offsetAndRotation(4, 0, 0, bonePitchYaw, bonePitchYaw,
                        0));
        PartDefinition front = bone.addOrReplaceChild("front",
                CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-8, -8, 0, 16, 16, 0, new CubeDeformation(0)),
                PartPose.offset(0, 0, -4));
        PartDefinition middle = bone.addOrReplaceChild("middle",
                CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-8, -8, 0, 16, 16, 0, new CubeDeformation(0)),
                PartPose.offset(0, 0, 0));
        PartDefinition back = bone.addOrReplaceChild("back",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-8, -8, 0, 16, 16, 0, new CubeDeformation(0)),
                PartPose.offset(0, 0, 4));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void render(PoseStack matrices, VertexConsumer vertices, int light,
                       int overlay, float red, float green, float blue,
                       float alpha, float tickDelta,
                       AncientHornProjectile entity) {
        matrices.scale(1, 1, 1);
        float aliveDelta = entity.getAliveTicks() + tickDelta;

        float pulse =
                (((float) Math.sin((aliveDelta * pi) * 0.2f) * 0.16666667f) +
                        1);
        float pulse2 =
                (((float) Math.sin(((aliveDelta + pulse2Extra) * pi) * 0.2f) *
                        0.16666667f) + 1);
        float pulse3 =
                (((float) Math.sin(((aliveDelta + pulse3Extra) * pi) * 0.2f) *
                        0.16666667f) + 1);

        this.front.xScale = pulse;
        this.front.yScale = pulse;
        this.front.z = pulse3 * 2 - 6;

        this.middle.xScale = pulse2;
        this.middle.yScale = pulse2;
        this.middle.z = pulse * 2 - 2;

        this.back.xScale = pulse3;
        this.back.yScale = pulse3;
        this.back.z = pulse2 * 2 + 2;

        this.bone.render(matrices, vertices, light, overlay, 1, 1, 1, 1);
    }

    @Override
    public void renderToBuffer(@NotNull PoseStack poseStack,
                               @NotNull VertexConsumer buffer, int packedLight,
                               int packedOverlay, float red, float green,
                               float blue, float alpha) {

    }
}
