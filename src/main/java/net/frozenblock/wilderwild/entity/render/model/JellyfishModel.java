/*
 * Copyright 2023 FrozenBlock
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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Arrays;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.config.EntityConfig;
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
	private static final int JELLYFISH_TENTACLES = EntityConfig.get().jellyfish.jellyfishTentacles;
	private static final float pi180 = Mth.PI / 180F;
	private static final float eightPi = -8F * pi180;
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart tentacleBase;
	private final ModelPart[] tentacles = new ModelPart[JELLYFISH_TENTACLES];
	public float xRot;
	public float tentXRot;

	public float red;
	public float green;
	public float blue;

	public float scale = 1F;

	public JellyfishModel(@NotNull ModelPart root) {
		super(FrozenRenderType::entityTranslucentEmissiveFixed);
		this.root = root;
		ModelPart bone = root.getChild("bone");
		this.body = bone.getChild("body");
		this.tentacleBase = bone.getChild("tentacleBase");
		Arrays.setAll(this.tentacles, i -> tentacleBase.getChild(createTentacleName(i)));
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshDefinition = new MeshDefinition();
		PartDefinition partDefinition = meshDefinition.getRoot();
		PartDefinition bone = partDefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 0.0F));
		bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -2.0F, -4.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(4, 13).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition tentacleBase = bone.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, 0.0F));
		makeTentacles(tentacleBase, JELLYFISH_TENTACLES);
		return LayerDefinition.create(meshDefinition, 64, 64);
	}

	private static void makeTentacles(PartDefinition partDefinition, int amount) {
		CubeListBuilder tentacle = CubeListBuilder.create().texOffs(0, 13).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F));
		for (int i = 0; i < amount; ++i) {
			double rot = (double) i * Math.PI * 2.0 / (double) amount;
			partDefinition.addOrReplaceChild(createTentacleName(i), tentacle, PartPose.offsetAndRotation(
					(float) Math.cos(rot) * 2.5F,
					0.0F,
					(float) Math.sin(rot) * 2.5F,
					0.0F,
					(float) (i * Math.PI * -2.0 / (double) amount + 1.5707963267948966),
					0.0F
				)
			);
		}
	}

	@NotNull
	private static String createTentacleName(int i) {
		return "tentacle" + i;
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

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		poseStack.scale(this.scale, this.scale, this.scale);
		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(this.xRot));
		this.body.render(poseStack, buffer, packedLight, packedOverlay, this.red, this.green, this.blue, alpha);
		poseStack.popPose();

		poseStack.pushPose();
		poseStack.mulPose(Axis.XP.rotationDegrees(this.tentXRot));
		this.tentacleBase.render(poseStack, buffer, packedLight, packedOverlay, this.red, this.green, this.blue, alpha);
		poseStack.popPose();
	}

	@Override
	public void prepareMobModel(@NotNull T jelly, float limbSwing, float limbSwimgAmount, float partialTick) {
		this.xRot = -(jelly.xRot1 + partialTick * (jelly.xBodyRot - jelly.xRot1));
		this.tentXRot = -(jelly.xRot6 + partialTick * (jelly.xRot5 - jelly.xRot6));
		this.scale = (jelly.prevScale + partialTick * (jelly.scale - jelly.prevScale)) * jelly.getScale();
	}

	@Override
	public void setupAnim(@NotNull T jellyfish, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float animation = limbSwing * 2;
		float movementDelta = Math.min(limbSwingAmount * 26.6666667F, 1.0F);

		//SQUASH & STRETCH
		float sin = (float) -Math.sin(animation);
		float sinIdle = (float) (Math.sin(ageInTicks * 0.1F) * 0.2F);
		float squashStretch = 1F + (-sin * 0.25F);
		float squash = Mth.lerp(movementDelta, sinIdle + 1, squashStretch);

		this.body.xScale = squash;
		this.body.zScale = squash;
		float yScaleSin = -sinIdle + 1;
		this.body.yScale = yScaleSin + movementDelta * ((1.25F + (sin * 0.75F)) - yScaleSin); //this.body.yScale = MathHelper.lerp(movementDelta, -sinIdle + 1, 1.25F + (sin * 0.75F));

		this.body.y = movementDelta * (3.5F - (squashStretch * 3.5F)); //this.body.y = MathHelper.lerp(movementDelta, 0, 3.5F - (squashStretch * 3.5F));
		float sinPivotY = (-sinIdle * 2.0F) + 1.8F;
		this.tentacleBase.y = sinPivotY + movementDelta * (((6F - (squashStretch * 5F)) * 2) - sinPivotY); //this.tentacleBase.y = MathHelper.lerp(movementDelta, (-sinIdle * 2.0F) + 1.8F, (6F - (squashStretch * 5F)) * 2);

		float tentRot = -fasterRotLerp(movementDelta, (float) (-Math.sin((ageInTicks - 10) * 0.1F) * 0.2F) + eightPi, (float) (-Math.sin(animation + 5) * 20 - 7.5F) * pi180);
		for (ModelPart modelPart : this.tentacles) {
			PartPose initialPose = modelPart.getInitialPose();
			modelPart.x = initialPose.x * squash;
			modelPart.z = initialPose.z * squash;
			modelPart.xRot = tentRot;
		}
	}

	@Override
	@NotNull
	public ModelPart root() {
		return this.root;
	}
}
