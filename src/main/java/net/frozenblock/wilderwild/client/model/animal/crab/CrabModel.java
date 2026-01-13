/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.client.model.animal.crab;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Math;

@Environment(EnvType.CLIENT)
public class CrabModel extends EntityModel<CrabRenderState> {
	private static final float DOUBLE_PI = Mth.PI * 2F;
	private static final float RAD_50 = 50F * Mth.DEG_TO_RAD;
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

	public CrabModel(ModelPart root, AnimationDefinition hidingAnimation, AnimationDefinition diggingAnimation, AnimationDefinition emergingAnimation) {
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

		this.hidingAnimation = hidingAnimation.bake(root);
		this.diggingAnimation = diggingAnimation.bake(root);
		this.emergingAnimation = emergingAnimation.bake(root);
	}

	private static void bobClaw(ModelPart modelPart, float ageInTicks, float multiplier) {
		modelPart.zRot += multiplier * (Mth.cos(ageInTicks * 0.09F) * 0.05F + 0.05F);
		modelPart.yRot += multiplier * (Mth.sin(ageInTicks * 0.067F) * 0.05F);
	}

	@Override
	public void setupAnim(CrabRenderState renderState) {
		super.setupAnim(renderState);

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
		final float climbProgress = climbRotRadians / Mth.HALF_PI;
		this.body.zRot += legRoll + climbRotRadians;
		this.legs.zRot += climbRotRadians;
		if (renderState.isBaby && !renderState.isDitto) {
			this.body.x -= climbProgress * 1.5F;
			this.legs.x -= climbProgress * 1.5F;
		}

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
