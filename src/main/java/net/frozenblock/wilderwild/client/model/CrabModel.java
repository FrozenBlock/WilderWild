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

import net.frozenblock.wilderwild.client.animation.definitions.CrabAnimation;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.joml.Math;

public class CrabModel extends EntityModel<CrabRenderState> {
	private static final float DOUBLE_PI = Mth.PI * 2F;
	private static final float RAD_50 = 50F * Mth.DEG_TO_RAD;
	private static final float LEG_OFFSET = 4F;
	private static final float LEG_OFFSET_MOJANG = 4.5F;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart mainClaw;
	private final ModelPart mainClawTop;
	private final ModelPart mainClawBottom;
	private final ModelPart leftClaw;
	private final ModelPart legs;
	private final ModelPart backRightLeg;
	private final ModelPart middleRightLeg;
	private final ModelPart frontRightLeg;
	private final ModelPart backLeftLeg;
	private final ModelPart middleLeftLeg;
	private final ModelPart frontLeftLeg;
	private final KeyframeAnimation hidingAnimation;
	private final KeyframeAnimation diggingAnimation;
	private final KeyframeAnimation emergingAnimation;

	public CrabModel(ModelPart root) {
		super(root);

		this.body = root.getChild("body");
		this.torso = body.getChild("torso");
		this.mainClaw = this.body.getChild("main_claw");
		this.mainClawTop = this.mainClaw.getChild("claw_top");
		this.mainClawBottom = this.mainClaw.getChild("claw_bottom");

		this.leftClaw = this.body.getChild("left_claw");

		this.legs = root.getChild("legs");
		this.backRightLeg = this.legs.getChild("back_right_leg");
		this.middleRightLeg = this.legs.getChild("middle_right_leg");
		this.frontRightLeg = this.legs.getChild("front_right_leg");
		this.backLeftLeg = this.legs.getChild("back_left_leg");
		this.middleLeftLeg = this.legs.getChild("middle_left_leg");
		this.frontLeftLeg = this.legs.getChild("front_left_leg");

		this.hidingAnimation = CrabAnimation.CRAB_EMERGE_WAIT.bake(root);
		this.diggingAnimation = CrabAnimation.CRAB_DIG.bake(root);
		this.emergingAnimation = CrabAnimation.CRAB_EMERGE.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, 0F, -2F)
		);

		body.addOrReplaceChild(
			"torso",
			CubeListBuilder.create()
				.texOffs(0, 2)
				.addBox(-4F, -2F, -2F, 8F, 3F, 7F)
				.texOffs(7, 0)
				.addBox(-4F, -4F, 5F, 8F, 2F, 0F),
			PartPose.ZERO
		);

		body.addOrReplaceChild(
			"left_claw",
			CubeListBuilder.create()
				.texOffs(16, 12)
				.addBox(1F, -1F, -1F, 3F, 2F, 2F),
			PartPose.offsetAndRotation(-6F, -1F, 4.25F, 0F, -0.6981F, 0.2618F)
		);

		final PartDefinition mainClaw = body.addOrReplaceChild(
			"main_claw", CubeListBuilder.create(),
			PartPose.offsetAndRotation(6F, -1.5F, 4.25F, 0F, 0.6981F, -0.5236F)
		);
		mainClaw.addOrReplaceChild(
			"claw_top",
			CubeListBuilder.create()
				.texOffs(0, 12)
				.addBox(-6F, -1.5F, -1F, 6F, 2F, 2F),
			PartPose.ZERO
		);
		mainClaw.addOrReplaceChild(
			"claw_bottom",
			CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(-6F, 0.5F, -1F, 6F, 1F, 2F),
			PartPose.ZERO
		);

		//LEGS
		final PartDefinition legs = root.addOrReplaceChild(
			"legs",
			CubeListBuilder.create(),
			PartPose.offset(0F, -1F, -1F)
		);

		final CubeListBuilder leg = CubeListBuilder.create().texOffs(28, 12).addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F);
		legs.addOrReplaceChild(
			"back_right_leg",
			leg,
			PartPose.offsetAndRotation(LEG_OFFSET, 1F, -2.25F, -0.4876F, -0.0741F, -0.7109F)
		);
		legs.addOrReplaceChild(
			"back_left_leg",
			leg,
			PartPose.offsetAndRotation(-LEG_OFFSET, 1F, -2.25F, -0.4876F, 0.0741F, 0.7109F)
		);
		legs.addOrReplaceChild(
			"middle_right_leg",
			leg,
			PartPose.offsetAndRotation(LEG_OFFSET, 1F, -0.25F, -0.0949F, -0.0741F, -0.7109F)
		);
		legs.addOrReplaceChild(
			"middle_left_leg",
			leg,
			PartPose.offsetAndRotation(-LEG_OFFSET, 1F, -0.25F, -0.0949F, 0.0741F, 0.7109F)
		);
		legs.addOrReplaceChild(
			"front_right_leg",
			leg,
			PartPose.offsetAndRotation(LEG_OFFSET, 1F, 1.75F, 0.3414F, -0.0741F, -0.7109F)
		);
		legs.addOrReplaceChild(
			"front_left_leg",
			leg,
			PartPose.offsetAndRotation(-LEG_OFFSET, 1F, 1.75F, 0.3414F, 0.0741F, 0.7109F)
		);

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	public static LayerDefinition createMojangBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, 0F, -2F)
		);

		body.addOrReplaceChild(
			"torso",
			CubeListBuilder.create()
				.texOffs(7, 0)
				.addBox(-3.5F, -6F, 5F, 9F, 2F, 0F)
				.texOffs(0, 2)
				.addBox(-3.5F, -4F, -2F, 9F, 5F, 7F),
			PartPose.offset(-1F, 0F, 0F)
		);

		body.addOrReplaceChild(
			"left_claw",
			CubeListBuilder.create()
				.texOffs(20, 14)
				.addBox(1F, -1F, -1F, 3F, 2F, 2F),
			PartPose.offsetAndRotation(-6F, -1F, 4.25F, 0F, -0.6981F, 0.2618F)
		);

		final PartDefinition mainClaw = body.addOrReplaceChild(
			"main_claw",
			CubeListBuilder.create(),
			PartPose.offsetAndRotation(7.25F, -2F, 4F, 0F, 0.6981F, -0.5236F)
		);
		mainClaw.addOrReplaceChild(
			"claw_top",
			CubeListBuilder.create()
				.texOffs(0, 14)
				.addBox(-8F, -3F, -1F, 8F, 3F, 2F),
			PartPose.offset(0F, 0.5F, 0F)
		);
		mainClaw.addOrReplaceChild(
			"claw_bottom",
			CubeListBuilder.create()
				.texOffs(0, 19)
				.addBox(-8F, 0F, -1F, 8F, 1F, 2F),
			PartPose.offset(0F, 0.5F, 0F)
		);

		// LEGS
		final PartDefinition legs = root.addOrReplaceChild(
			"legs",
			CubeListBuilder.create(),
			PartPose.offset(0F, -1F, -1F)
		);
		legs.addOrReplaceChild(
			"front_left_leg",
			CubeListBuilder.create()
				.texOffs(0, 27)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET_MOJANG, 1.5F, 1.75F, 0.3414F, 0.0741F, 0.7109F)
		);
		legs.addOrReplaceChild(
			"front_right_leg",
			CubeListBuilder.create()
				.texOffs(0, 22)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET_MOJANG, 1.5F, 1.75F, 0.3414F, -0.0741F, -0.7109F)
		);
		legs.addOrReplaceChild(
			"middle_left_leg",
			CubeListBuilder.create()
				.texOffs(4, 27)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET_MOJANG, 1.5F, -0.25F, -0.0949F, 0.0741F, 0.7109F)
		);
		legs.addOrReplaceChild(
			"middle_right_leg",
			CubeListBuilder.create()
				.texOffs(4, 22)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET_MOJANG, 1.5F, -0.25F, -0.0949F, -0.0741F, -0.7109F)
		);
		legs.addOrReplaceChild(
			"back_left_leg",
			CubeListBuilder.create()
				.texOffs(8, 27)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET_MOJANG, 1.5F, -2.75F, -0.4876F, 0.0741F, 0.7109F)
		);
		legs.addOrReplaceChild(
			"back_right_leg",
			CubeListBuilder.create()
				.texOffs(8, 22)
				.addBox(-0.5F, 0F, -0.5F, 1F, 4F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET_MOJANG, 1.5F, -2.75F, -0.4876F, -0.0741F, -0.7109F)
		);

		return LayerDefinition.create(meshdefinition, 48, 48);
	}

	private static void bobClaw(ModelPart modelPart, float ageInTicks, float multiplier) {
		modelPart.zRot += multiplier * (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
		modelPart.yRot += multiplier * (Mth.sin(ageInTicks * 0.067F) * 0.05F);
	}

	@Override
	public void setupAnim(CrabRenderState renderState) {
		super.setupAnim(renderState);
		this.root.y = 23.9F;

		this.hidingAnimation.apply(renderState.hidingAnimationState, renderState.ageInTicks);
		this.diggingAnimation.apply(renderState.diggingAnimationState, renderState.ageInTicks);
		this.emergingAnimation.apply(renderState.emergingAnimationState, renderState.ageInTicks);

		final float walkPos = renderState.walkAnimationPos * 4.75F;
		final float walkSpeed = renderState.walkAnimationSpeed;
		bobClaw(this.mainClaw, renderState.ageInTicks, 2F);
		bobClaw(this.leftClaw, renderState.ageInTicks, -2F);

		final float movementDelta = Math.min(walkSpeed * 4F, 1F);
		final float fastAngle = walkPos * 0.3331F;
		final float limbSwing5 = Math.min(1F, walkSpeed * 5) * 0.5F;
		final float fastAngleSin = Mth.sin(fastAngle);
		final float walkA = Mth.lerp(movementDelta, 0F, ((1F - fastAngleSin) * limbSwing5) - 0.5F);
		final float walkB = Mth.lerp(movementDelta, 0F, ((1F + fastAngleSin) * limbSwing5) - 0.5F);

		final float legRoll = fastAngleSin * 0.4F * walkSpeed;
		final float lerpedWalkA = Mth.lerp(walkA, -legRoll, RAD_50);
		final float lerpedWalkB = Mth.lerp(walkB, legRoll, RAD_50);
		this.backRightLeg.zRot += lerpedWalkA;
		this.middleRightLeg.zRot += lerpedWalkB;
		this.frontRightLeg.zRot += lerpedWalkA;

		this.backLeftLeg.zRot -= lerpedWalkB;
		this.middleLeftLeg.zRot -= lerpedWalkA;
		this.frontLeftLeg.zRot -= lerpedWalkB;

		this.backRightLeg.y -= walkA;
		this.middleRightLeg.y -= walkB;
		this.frontRightLeg.y -= walkA;

		this.backLeftLeg.y -= walkB;
		this.middleLeftLeg.y -= walkA;
		this.frontLeftLeg.y -= walkB;

		final float fastAngleDelayed = (walkPos + Mth.PI) * 0.3331F;
		final float fastAngleDelayedSin = Mth.sin(fastAngleDelayed);
		final float walkADelayed = Mth.lerp(movementDelta, 0F, (((1F - fastAngleDelayedSin) * limbSwing5) - 0.5F));
		final float walkBDelayed = Mth.lerp(movementDelta, 0F, (((1F + fastAngleDelayedSin) * limbSwing5) - 0.5F));
		this.backRightLeg.x += walkBDelayed;
		this.middleRightLeg.x += walkADelayed;
		this.frontRightLeg.x += walkBDelayed;

		this.backLeftLeg.x -= walkADelayed;
		this.middleLeftLeg.x -= walkBDelayed;
		this.frontLeftLeg.x -= walkADelayed;

		final float climbRotRadians = renderState.climbXRot * Mth.DEG_TO_RAD;
		this.body.zRot += legRoll + climbRotRadians;
		this.legs.zRot += climbRotRadians;

		//Attack Anim
		this.body.yRot = Mth.sin(Mth.sqrt(renderState.attackTime) * (DOUBLE_PI)) * -0.2F;
		final float attackSin = Mth.sin(renderState.attackTime * Mth.PI);
		this.mainClaw.x += attackSin * 1.5F;
		this.mainClaw.xRot += attackSin * -80F * Mth.DEG_TO_RAD;
		this.mainClaw.yRot += attackSin * 100F * Mth.DEG_TO_RAD;
		this.mainClaw.zRot += attackSin * 20F * Mth.DEG_TO_RAD;
		this.mainClawTop.zRot += attackSin * 45F * Mth.DEG_TO_RAD;
		this.mainClawBottom.zRot -= this.mainClawTop.zRot;
	}
}
