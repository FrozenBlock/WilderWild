/*
 * Copyright 2023-2024 FrozenBlock
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

import net.frozenblock.wilderwild.entity.render.renderer.state.OstrichRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class OstrichModel extends EntityModel<OstrichRenderState> {
	private static final float NECK_DELAY = 0F;
	private static final float OLD_NECK_DELAY = 0.0416375F;
	private static final float NECK_BASE_SWING = 0.175F * 0.5F;
	private static final float NECK_SWING = 0.175F * 0.65F;
	private static final float RAD_5 = 5F * Mth.DEG_TO_RAD;
	private static final float RAD_025 = Mth.DEG_TO_RAD * 0.25F;
	private static final float RAD_065 = Mth.DEG_TO_RAD * 0.65F;

	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;
	private final ModelPart body;
	private final ModelPart saddle;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart neck_base;
	private final ModelPart neck;
	private final ModelPart beak;
	private final ModelPart tail;
	private float scale;
	private float yOffset;

	public OstrichModel(@NotNull ModelPart root) {
		super(root);

		this.legs = root.getChild("legs");

		this.left_leg = this.legs.getChild("left_leg");
		this.left_foot = this.left_leg.getChild("left_foot");
		this.right_leg = this.legs.getChild("right_leg");
		this.right_foot = this.right_leg.getChild("right_foot");

		this.body = root.getChild("body");
		this.saddle = this.body.getChild("saddle");
		this.left_wing = this.body.getChild("left_wing");
		this.right_wing = this.body.getChild("right_wing");

		this.neck_base = this.body.getChild("neck_base");
		this.neck = this.neck_base.getChild("neck");
		this.beak = this.neck.getChild("beak");

		this.tail = this.body.getChild("tail");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F));
		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(86, 44).addBox(-1F, -18F, -1F, 2F, 18F, 2F), PartPose.offset(-3F, 16F, 0F));
		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(88, 58).addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F)), PartPose.offset(0F, 0F, -1F));
		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1F, -18F, -1F, 2F, 18F, 2F).mirror(false), PartPose.offset(3F, 16.0F, 0F));
		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(88, 58).mirror().addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F)).mirror(false), PartPose.offset(0F, 0F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 31).addBox(-6F, -12F, -7F, 12F, 14F, 19F)
			.texOffs(24, 31).addBox(-6F, 0F, -7F, 12F, 0F, 19F), PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F));
		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(0, 0).addBox(-6F, -12F, -7F, 12F, 12F, 19F, new CubeDeformation(0.5F)), PartPose.ZERO);
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(98, 22).addBox(-1.0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F))
			.texOffs(98, 44).addBox(-1F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F)), PartPose.offset(-6F, -4F, 5F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(98, 22).mirror().addBox(0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F)).mirror(false)
			.texOffs(98, 44).mirror().addBox(0F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offset(6F, -4F, 5F));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create().texOffs(81, 9).addBox(-4F, -5F, 0F, 8F, 8F, 6F), PartPose.offset(0F, -4F, 12F));
		PartDefinition neck = neck_base.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(65, 0).addBox(-2F, -21F, 0F, 4F, 21F, 4F), PartPose.offset(0F, 1F, 4F));
		PartDefinition beak = neck.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(81, 1).addBox(-2F, -1F, 0F, 4F, 2F, 3F), PartPose.offset(0F, -18F, 4F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(62, 46).addBox(-3F, 0F, -6F, 6F, 12F, 6F)
			.texOffs(62, 40).addBox(-3F, 10F, -6F, 6F, 0F, 6F), PartPose.offset(0F, -7F, -7F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	private static void animateLeg(@NotNull ModelPart leg, @NotNull ModelPart foot, float limbSwing, float limbSwingAmount, float animOffset) {
		float fastAngle = limbSwing * 0.3331F + animOffset;
		float angleSin = Math.sin(-fastAngle);

		float angleSinSwingAmount = angleSin * limbSwingAmount;
		float legZ = angleSinSwingAmount * 11F;

		float earlyAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.3331F));
		float earlyAngleSinSwingAmount = earlyAngleSin * limbSwingAmount;
		float onlyPositiveEarlyAngleSinSwingAmount = Math.max(earlyAngleSinSwingAmount, 0F);
		float legY = onlyPositiveEarlyAngleSinSwingAmount * 6F;

		leg.xRot -= Math.sin(fastAngle) * limbSwingAmount * 0.5F;
		leg.y -= legY;
		leg.z += legZ;

		float earlierAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.6662F));
		float earlierAngleSinSwingAmount = earlierAngleSin * limbSwingAmount;
		float earlierLegY = onlyPositiveEarlyAngleSinSwingAmount * 5F;
		float additionalFoot = Math.min(earlierLegY, 1F) * earlierAngleSinSwingAmount;
		foot.xRot -= leg.xRot;
		foot.xRot -= additionalFoot;

		float laterAngleSin = Math.sin(-fastAngle + (Mth.PI * 0.3331F));
		float laterAngleSinSwingAmount = laterAngleSin * limbSwingAmount;
		float onlyPositiveLaterAngleSinSwingAmount = Math.max(laterAngleSinSwingAmount, 0F);
		float laterLegY = onlyPositiveLaterAngleSinSwingAmount * 5F;
		float additionalLateFoot = Math.min(laterLegY, 1F) * laterAngleSinSwingAmount;
		foot.y -= additionalLateFoot;
	}

	@Override
	public void setupAnim(OstrichRenderState renderState) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		this.scale = renderState.ageScale;

		if (renderState.isBaby) {
			this.neck.xScale = 1.5F;
			this.neck.yScale = 1.5F;
			this.neck.zScale = 1.5F;
			this.yOffset = 0.75F;
		} else {
			this.yOffset = 0F;
		}

		this.saddle.visible = renderState.isSaddled;
		float walkPos = renderState.walkAnimationPos;
		float walkSpeed = renderState.walkAnimationSpeed;

		// LEGS
		animateLeg(this.left_leg, this.left_foot, walkPos, walkSpeed, Mth.PI);
		animateLeg(this.right_leg, this.right_foot, walkPos, walkSpeed, 0F);

		// BODY
		float fastAngleBody = walkPos * 0.3331F;
		float angleSinBody = Math.sin(-fastAngleBody);
		float angleSinSwingAmountBody = (angleSinBody * walkSpeed) * 0.175F;
		this.body.zRot += angleSinSwingAmountBody;

		// NECK
		float beakAnimProgress = renderState.beakAnimProgress;
		float rotation = beakAnimProgress * -Mth.PI;

		this.neck_base.xRot = rotation * 0.3F;
		this.neck.xRot = rotation * 0.7F;

		float yRot = renderState.yRot;
		float xRot = renderState.xRot;
		xRot = Mth.lerp(renderState.targetStraightProgress, renderState.xRot, Math.min(xRot, 0F));
		xRot = Mth.lerp(beakAnimProgress, xRot, 0F);

		float fastAngleNeckBase = walkPos * 0.3331F + NECK_DELAY;
		float angleSinNeckBase = Math.sin(-fastAngleNeckBase);
		float angleSinSwingAmountNeckBase = (angleSinNeckBase * walkSpeed) * NECK_BASE_SWING;
		this.neck_base.zRot += angleSinSwingAmountNeckBase;

		float fastAngleNeck = walkPos * 0.3331F + NECK_DELAY;
		float angleSinNeck = Math.sin(-fastAngleNeck);
		float angleSinSwingAmountNeck = (angleSinNeck * walkSpeed) * NECK_SWING;
		this.neck.zRot += angleSinSwingAmountNeck;

		this.neck.xRot += (walkSpeed * RAD_5);

		this.neck_base.xRot -= xRot * RAD_025;
		this.neck_base.yRot += yRot * RAD_025;
		this.neck.xRot -= xRot * RAD_065;
		this.neck.yRot += yRot * RAD_065;
	}

	/*@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		poseStack.pushPose();
		poseStack.translate(0, this.yOffset, 0);
		poseStack.scale(this.scale, this.scale, this.scale);
		this.root().render(poseStack, buffer, packedLight, packedOverlay, color);
		poseStack.popPose();
	}*/
}
