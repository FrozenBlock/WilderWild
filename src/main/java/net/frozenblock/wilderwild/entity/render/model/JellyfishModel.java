/*
 * Copyright 2022-2023 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.entity.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
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
    private final List<ModelPart> tentacles;

    public JellyfishModel(ModelPart root) {
        super(FrozenRenderType::entityTranslucentEmissiveFixed);
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
        this.tentacles = List.of(this.tentacle1, this.tentacle2, this.tentacle3, this.tentacle4, this.tentacle5, this.tentacle6, this.tentacle7, this.tentacle8);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();
        PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
        bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(4, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tentacleBase = bone.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle1", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -3.0F));
        tentacleBase.addOrReplaceChild("tentacle2", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, -2.5F, 0.0F, -0.7854F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle3", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle4", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5F, 0.0F, 2.5F, 0.0F, -2.3562F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle5", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle6", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, 2.5F, 0.0F, 2.3562F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle7", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));
        tentacleBase.addOrReplaceChild("tentacle8", CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -2.5F, 0.0F, 0.7854F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    public float xRot;
    public float tentXRot;

    public float red;
    public float green;
    public float blue;

	public float scale = 1F;

    @Override
    public void renderToBuffer(PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(scale, scale, scale);
		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XP.rotationDegrees(this.xRot));
		this.body.render(poseStack, buffer, packedLight, packedOverlay, this.red, this.green, this.blue, alpha);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Vector3f.XP.rotationDegrees(this.tentXRot));
		this.tentacleBase.render(poseStack, buffer, packedLight, packedOverlay, this.red, this.green, this.blue, alpha);
		poseStack.popPose();
    }

    private static final float pi180 = Mth.PI / 180;
    private static final float eightPi = -8 * pi180;

    @Override
    public void prepareMobModel(T jelly, float limbSwing, float limbSwimgAmount, float partialTick) {
        this.xRot = -(jelly.xRot1 + partialTick * (jelly.xBodyRot - jelly.xRot1));
        this.tentXRot = -(jelly.xRot6 + partialTick * (jelly.xRot5 - jelly.xRot6));
		this.scale = jelly.prevScale + partialTick * (jelly.scale - jelly.prevScale);
    }

    @Override
    public void setupAnim(@NotNull T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float animation = limbSwing * 2;
		float movementDelta = Math.min(limbSwingAmount * 26.6666667F, 1.0F);
		float tentRot = fasterRotLerp(movementDelta, (float) (-Math.sin((ageInTicks - 10) * 0.1F) * 0.2F) + eightPi, (float) (-Math.sin(animation + 5) * 20 - 7.5F) * pi180);

		this.tentacle1.xRot = tentRot;
		this.tentacle2.xRot = tentRot;
		this.tentacle3.xRot = tentRot;
		this.tentacle4.xRot = tentRot;
		this.tentacle5.xRot = tentRot;
		this.tentacle6.xRot = tentRot;
		this.tentacle7.xRot = tentRot;
		this.tentacle8.xRot = tentRot;

        //SQUASH & STRETCH
		float sin = (float) -Math.sin(animation);
		float sinIdle = (float) (Math.sin(ageInTicks * 0.1F) * 0.2F);
		float squashStretch = 1F + (-sin * 0.25F);
		float squash = Mth.lerp(movementDelta, sinIdle + 1, squashStretch);

		this.body.xScale = squash;
		this.body.zScale = squash;
		float yScaleSin = -sinIdle + 1;
		//this.body.yScale = MathHelper.lerp(movementDelta, -sinIdle + 1, 1.25F + (sin * 0.75F));
		this.body.yScale = yScaleSin + movementDelta * ((1.25F + (sin * 0.75F)) - yScaleSin);

		this.body.y = movementDelta * (3.5F - (squashStretch * 3.5F));
		//this.body.y = MathHelper.lerp(movementDelta, 0, 3.5F - (squashStretch * 3.5F));
		float sinPivotY = (-sinIdle * 2.0F) + 1.8F;
		this.tentacleBase.y = sinPivotY + movementDelta * (((6F - (squashStretch * 5F)) * 2) - sinPivotY);
		//this.tentacleBase.y = MathHelper.lerp(movementDelta, (-sinIdle * 2.0F) + 1.8F, (6F - (squashStretch * 5F)) * 2);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    public List<ModelPart> getTentacles() {
        return this.tentacles;
    }

    private static float fasterRotLerp(float f, float g, float h) {
        float angleCalc = (h - g) % 360F;
        if (angleCalc >= 180.0F) {
            angleCalc -= 360.0F;
        }

        if (angleCalc < -180.0F) {
            angleCalc += 360.0F;
        }

        return g + f * angleCalc;
    }
}
