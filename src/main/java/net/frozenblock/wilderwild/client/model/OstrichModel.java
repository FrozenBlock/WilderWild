/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can modify it under
 * the terms of version 1 of the FrozenBlock Modding Oasis License
 * as published by FrozenBlock Modding Oasis.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * FrozenBlock Modding Oasis License for more details.
 *
 * You should have received a copy of the FrozenBlock Modding Oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client.model;

import java.util.Set;
import net.frozenblock.wilderwild.client.renderer.entity.state.AbstractOstrichRenderState;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.joml.Math;

public class OstrichModel<T extends AbstractOstrichRenderState> extends EntityModel<T> {
	public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 10F, 4F, Set.of("neck"));
	private static final float NECK_DELAY = 0F;
	private static final float OLD_NECK_DELAY = 0.0416375F;
	private static final float NECK_BASE_SWING = 0.175F * 0.5F;
	private static final float NECK_SWING = 0.175F * 0.65F;
	private static final float RAD_5 = 5F * Mth.DEG_TO_RAD;
	private static final float RAD_025 = Mth.DEG_TO_RAD * 0.25F;
	private static final float RAD_065 = Mth.DEG_TO_RAD * 0.65F;

	private final ModelPart legs;
	private final ModelPart leftLeg;
	private final ModelPart leftFoot;
	private final ModelPart rightLeg;
	private final ModelPart rightFoot;
	private final ModelPart body;
	private final ModelPart saddle;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart neckBase;
	private final ModelPart neck;
	private final ModelPart beak;
	private final ModelPart tail;

	public OstrichModel(ModelPart root) {
		super(root);

		this.legs = root.getChild("legs");

		this.leftLeg = this.legs.getChild("left_leg");
		this.leftFoot = this.leftLeg.getChild("left_foot");
		this.rightLeg = this.legs.getChild("right_leg");
		this.rightFoot = this.rightLeg.getChild("right_foot");

		this.body = root.getChild("body");
		this.saddle = this.body.getChild("saddle");
		this.leftWing = this.body.getChild("left_wing");
		this.rightWing = this.body.getChild("right_wing");

		this.neckBase = this.body.getChild("neck_base");
		this.neck = this.neckBase.getChild("neck");
		this.beak = this.neck.getChild("beak");

		this.tail = this.body.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition legs = root.addOrReplaceChild(
			"legs",
			CubeListBuilder.create(),
			PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F)
		);

		final PartDefinition leftLeg = legs.addOrReplaceChild(
			"left_leg",
			CubeListBuilder.create()
				.texOffs(86, 44)
				.addBox(-1F, -18F, -1F, 2F, 18F, 2F),
			PartPose.offset(-3F, 16F, 0F)
		);
		leftLeg.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(88, 58)
				.addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F)),
			PartPose.offset(0F, 0F, -1F)
		);

		final PartDefinition rightLeg = legs.addOrReplaceChild(
			"right_leg",
			CubeListBuilder.create()
				.texOffs(86, 44)
				.mirror()
				.addBox(-1F, -18F, -1F, 2F, 18F, 2F)
				.mirror(false),
			PartPose.offset(3F, 16F, 0F)
		);
		rightLeg.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(88, 58)
				.mirror()
				.addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F))
				.mirror(false),
			PartPose.offset(0F, 0F, -1F)
		);

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(0, 31)
				.addBox(-6F, -12F, -7F, 12F, 14F, 19F)
			.texOffs(24, 31)
				.addBox(-6F, 0F, -7F, 12F, 0F, 19F), PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F));
		body.addOrReplaceChild(
			"saddle",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-6F, -12F, -7F, 12F, 12F, 19F, new CubeDeformation(0.5F)),
			PartPose.ZERO
		);
		body.addOrReplaceChild(
			"left_wing",
			CubeListBuilder.create()
				.texOffs(98, 22)
				.addBox(-1.0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F))
				.texOffs(98, 44)
				.addBox(-1F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F)),
			PartPose.offset(-6F, -4F, 5F)
		);
		body.addOrReplaceChild(
			"right_wing",
			CubeListBuilder.create()
				.texOffs(98, 22)
				.mirror()
				.addBox(0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F))
				.mirror(false)
				.texOffs(98, 44)
				.mirror()
				.addBox(0F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F))
				.mirror(false),
			PartPose.offset(6F, -4F, 5F)
		);

		final PartDefinition neckBase = body.addOrReplaceChild(
			"neck_base",
			CubeListBuilder.create()
				.texOffs(81, 9)
				.addBox(-4F, -5F, 0F, 8F, 8F, 6F),
			PartPose.offset(0F, -4F, 12F)
		);
		final PartDefinition neck = neckBase.addOrReplaceChild(
			"neck",
			CubeListBuilder.create()
				.texOffs(65, 0)
				.addBox(-2F, -21F, 0F, 4F, 21F, 4F),
			PartPose.offset(0F, 1F, 4F)
		);
		neck.addOrReplaceChild(
			"beak",
			CubeListBuilder.create()
				.texOffs(81, 1)
				.addBox(-2F, -1F, 0F, 4F, 2F, 3F),
			PartPose.offset(0F, -18F, 4F)
		);

		body.addOrReplaceChild(
			"tail",
			CubeListBuilder.create()
				.texOffs(62, 46)
				.addBox(-3F, 0F, -6F, 6F, 12F, 6F)
				.texOffs(62, 40)
				.addBox(-3F, 10F, -6F, 6F, 0F, 6F),
			PartPose.offset(0F, -7F, -7F)
		);

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	public static LayerDefinition createBabyBodyLayer() {
		return createBodyLayer().apply(BABY_TRANSFORMER);
	}

	private static void animateLeg(ModelPart leg, ModelPart foot, float limbSwing, float limbSwingAmount, float animOffset) {
		final float fastAngle = limbSwing * 0.3331F + animOffset;
		final float angleSin = Math.sin(-fastAngle);

		final float angleSinSwingAmount = angleSin * limbSwingAmount;
		final float legZ = angleSinSwingAmount * 11F;

		final float earlyAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.3331F));
		final float earlyAngleSinSwingAmount = earlyAngleSin * limbSwingAmount;
		final float onlyPositiveEarlyAngleSinSwingAmount = Math.max(earlyAngleSinSwingAmount, 0F);
		final float legY = onlyPositiveEarlyAngleSinSwingAmount * 6F;

		leg.xRot -= Math.sin(fastAngle) * limbSwingAmount * 0.5F;
		leg.y -= legY;
		leg.z += legZ;

		final float earlierAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.6662F));
		final float earlierAngleSinSwingAmount = earlierAngleSin * limbSwingAmount;
		final float earlierLegY = onlyPositiveEarlyAngleSinSwingAmount * 5F;
		final float additionalFoot = Math.min(earlierLegY, 1F) * earlierAngleSinSwingAmount;
		foot.xRot -= leg.xRot;
		foot.xRot -= additionalFoot;

		final float laterAngleSin = Math.sin(-fastAngle + (Mth.PI * 0.3331F));
		final float laterAngleSinSwingAmount = laterAngleSin * limbSwingAmount;
		final float onlyPositiveLaterAngleSinSwingAmount = Math.max(laterAngleSinSwingAmount, 0F);
		final float laterLegY = onlyPositiveLaterAngleSinSwingAmount * 5F;
		final float additionalLateFoot = Math.min(laterLegY, 1F) * laterAngleSinSwingAmount;
		foot.y -= additionalLateFoot;
	}

	@Override
	public void setupAnim(T renderState) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		if (renderState.isBaby) {
			this.neck.xScale = 1.5F;
			this.neck.yScale = 1.5F;
			this.neck.zScale = 1.5F;
		}

		this.saddle.visible = !renderState.saddle.isEmpty();
		float walkPos = renderState.walkAnimationPos;
		float walkSpeed = renderState.walkAnimationSpeed;

		// LEGS
		animateLeg(this.leftLeg, this.leftFoot, walkPos, walkSpeed, Mth.PI);
		animateLeg(this.rightLeg, this.rightFoot, walkPos, walkSpeed, 0F);

		// BODY
		final float fastAngleBody = walkPos * 0.3331F;
		final float angleSinBody = Math.sin(-fastAngleBody);
		final float angleSinSwingAmountBody = (angleSinBody * walkSpeed) * 0.175F;
		this.body.zRot += angleSinSwingAmountBody;

		// NECK
		final float beakAnimProgress = renderState.beakAnimProgress;
		final float rotation = beakAnimProgress * -Mth.PI;

		this.neckBase.xRot = rotation * 0.3F;
		this.neck.xRot = rotation * 0.7F;

		float yRot = renderState.yRot;
		float xRot = renderState.xRot;
		xRot = Mth.lerp(renderState.targetStraightProgress, renderState.xRot, Math.min(xRot, 0F));
		xRot = Mth.lerp(beakAnimProgress, xRot, 0F);

		final float fastAngleNeckBase = walkPos * 0.3331F + NECK_DELAY;
		final float angleSinNeckBase = Math.sin(-fastAngleNeckBase);
		final float angleSinSwingAmountNeckBase = (angleSinNeckBase * walkSpeed) * NECK_BASE_SWING;
		this.neckBase.zRot += angleSinSwingAmountNeckBase;

		final float fastAngleNeck = walkPos * 0.3331F + NECK_DELAY;
		final float angleSinNeck = Math.sin(-fastAngleNeck);
		final float angleSinSwingAmountNeck = (angleSinNeck * walkSpeed) * NECK_SWING;
		this.neck.zRot += angleSinSwingAmountNeck;

		this.neck.xRot += (walkSpeed * RAD_5);

		this.neckBase.xRot -= xRot * RAD_025;
		this.neckBase.yRot += yRot * RAD_025;
		this.neck.xRot -= xRot * RAD_065;
		this.neck.yRot += yRot * RAD_065;
	}
}
