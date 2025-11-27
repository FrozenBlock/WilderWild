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
import net.frozenblock.wilderwild.client.animation.definitions.PenguinAnimation;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
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

public class PenguinModel<T extends PenguinRenderState> extends EntityModel<PenguinRenderState> {
	public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 0F, 0F, 1F, 2.5F, 36F, Set.of());
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart rightFlipper;
	private final ModelPart leftFlipper;
	private final ModelPart feet;
	private final ModelPart rightFoot;
	private final ModelPart leftFoot;
	private final KeyframeAnimation layDownAnimation;
	private final KeyframeAnimation standUpAnimation;
	private final KeyframeAnimation callAnimation;

	public PenguinModel(ModelPart root) {
		super(root);
		this.body = root.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.rightFlipper = this.torso.getChild("right_flipper");
		this.leftFlipper = this.torso.getChild("left_flipper");
		this.feet = this.body.getChild("feet");
		this.rightFoot = this.feet.getChild("right_foot");
		this.leftFoot = this.feet.getChild("left_foot");

		this.layDownAnimation = PenguinAnimation.PENGUIN_LAY_DOWN.bake(root);
		this.standUpAnimation = PenguinAnimation.PENGUIN_STAND_UP.bake(root);
		this.callAnimation = PenguinAnimation.PENGUIN_CALL.bake(root);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, 17F, -0.5F)
		);

		final PartDefinition torso = body.addOrReplaceChild(
			"torso", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.5F, -5F, -3.5F, 9F, 11F, 7F, new CubeDeformation(0F))
			.texOffs(28, 18)
				.addBox(-4.5F, 6F, -3.5F, 9F, 1F, 7F, new CubeDeformation(0F)),
			PartPose.offset(0F, 0F, 0F)
		);
		torso.addOrReplaceChild(
			"head", CubeListBuilder.create()
				.texOffs(0, 18)
				.addBox(-3.5F, -5F, -3.5F, 7F, 5F, 7F, new CubeDeformation(0F))
			.texOffs(0, 42)
				.addBox(-4.5F, -6F, -3.5F, 9F, 3F, 7F, new CubeDeformation(0.005F))
			.texOffs(32, 0)
				.addBox(-0.5F, -3F, -5.5F, 1F, 2F, 2F, new CubeDeformation(0F)),
			PartPose.offset(0F, -5F, 0F)
		);

		 torso.addOrReplaceChild(
			"right_flipper", CubeListBuilder.create()
				.texOffs(16, 30).addBox(-1F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(-4.5F, -5F, 0F)
		);
		torso.addOrReplaceChild(
			"left_flipper", CubeListBuilder.create()
				.texOffs(24, 30).addBox(0F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(4.5F, -5F, 0F)
		);

		final PartDefinition feet = body.addOrReplaceChild(
			"feet",
			CubeListBuilder.create(),
			PartPose.offset(0F, 7F, 0.5F)
		);
		feet.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(-2.5F, 0F, 1F)
		);
		feet.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(2.5F, 0F, 1F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public static LayerDefinition createBabyBodyLayer() {
		return createBodyLayer().apply(BABY_TRANSFORMER);
	}

	@Override
	public void setupAnim(PenguinRenderState renderState) {
		super.setupAnim(renderState);
		final float movementDelta = renderState.movementDelta;
		final float notMovingDelta = 1F - movementDelta;
		final float swimAmount = renderState.swimAmount;
		final float notSwimmingAmount = 1F - swimAmount;
		final float wadeProgress = renderState.wadeProgress;
		final float notWadingProgress = 1F - wadeProgress;
		final float slideProgress = renderState.slideProgress;
		final float notSlidingProgress = 1F - slideProgress;
		final float idleWadeProgress = Math.max(swimAmount, wadeProgress);

		this.head.yRot += renderState.yRot * Mth.DEG_TO_RAD * notSwimmingAmount * notSlidingProgress;
		this.head.xRot += renderState.xRot * Mth.DEG_TO_RAD * notSwimmingAmount * notSlidingProgress;

		final float limbSwing = renderState.walkAnimationPos * 2.65F;
		final float limbSwingAmount = Math.min(renderState.walkAnimationSpeed * 1.5F, 1F);
		this.layDownAnimation.apply(renderState.layDownAnimationState, renderState.ageInTicks);
		this.standUpAnimation.apply(renderState.standUpAnimationState, renderState.ageInTicks);
		this.callAnimation.apply(renderState.callAnimationState, renderState.ageInTicks);
		this.animateWalk(limbSwing, limbSwingAmount * notSwimmingAmount * notWadingProgress * notSlidingProgress);
		this.animateSlide(limbSwing * 2.5F, Math.min(limbSwingAmount * 2F, 1F), movementDelta, slideProgress * notSwimmingAmount * notWadingProgress);
		this.animateWade(renderState.ageInTicks, idleWadeProgress * notMovingDelta * notSlidingProgress);
		this.animateWadeMove(limbSwing,  Math.min(limbSwingAmount * wadeProgress * notSwimmingAmount * movementDelta * notSlidingProgress * 1.75F, 1F));
		this.animateSwim(limbSwing, limbSwingAmount, renderState.xRot, swimAmount * notSlidingProgress);
	}

	// Original Molang animation made by DaDolphin!
	private void animateWalk(float limbSwing, float limbSwingAmount) {
		limbSwing *= 0.001F;
		limbSwing *= 1.35F;
		final float walkDelta = Math.min(limbSwingAmount * 2F, 1F);
		this.body.y -= 0.25F * limbSwingAmount;
		limbSwingAmount *= Mth.DEG_TO_RAD * 2F;

		final float walkSpeed = limbSwing * 90F;
		final float walk35 = walkSpeed * 3.5F;
		final float walk7 = walk35 * 2F;

		// TORSO & HEAD
		this.torso.y -= Mth.sin(walk7 - 80F) * limbSwingAmount * 0.1F;
		this.torso.xRot -= Mth.sin(walk7 - 80F) * limbSwingAmount * 0.3F;
		this.torso.yRot -= Mth.sin(walk35) * limbSwingAmount * 11F;
		this.torso.zRot -= Mth.sin(walk35 - 80F) * limbSwingAmount * 7F;

		this.head.yRot += Mth.sin(walk35) * limbSwingAmount * 9F;
		this.head.zRot += Mth.sin(walk35 - 80F) * limbSwingAmount * 4F;

		// FLIPPERS
		this.leftFlipper.xRot += Mth.sin(walk35 - 150F) * limbSwingAmount * 2F;
		this.leftFlipper.yRot += Mth.sin(walk35 - 80F) * limbSwingAmount * 3F;
		this.leftFlipper.zRot += (-20F + Mth.sin(walk35 - 80F) * 5F) * limbSwingAmount;

		this.rightFlipper.xRot += Mth.sin(walk35 - 10F) * limbSwingAmount * 2F;
		this.rightFlipper.yRot += Mth.sin(walk35 - 260F) * limbSwingAmount * 3F;
		this.rightFlipper.zRot += (20F - Mth.sin(walk35 - 140F) * 3F) * limbSwingAmount;

		// FEET
		this.leftFoot.y += Mth.clamp(Mth.sin(walk35 - 80F), -1F, 0F) * walkDelta * 0.75F;
		this.leftFoot.z -= Mth.sin(walk35 - 160F) * walkDelta;
		this.leftFoot.xRot -= Math.clamp(Mth.sin(walk35 - 110F) * 5F, 0F, 5F) * Mth.DEG_TO_RAD * walkDelta;

		this.rightFoot.y += Mth.clamp(-Mth.sin(walk35 - 80F), -1F, 0F) * walkDelta * 0.75F;
		this.rightFoot.z += Mth.sin(walk35 - 160F) * walkDelta;
		this.rightFoot.xRot -= Math.clamp(-Mth.sin(walk35 - 110F) * 5F, 0F, 5F) * Mth.DEG_TO_RAD * walkDelta;
	}

	// Original Molang animation made by DaDolphin!
	private void animateWade(float ageInTicks, float wadeAmount) {
		ageInTicks *= 0.001F;
		final float wadeDegToRad = wadeAmount * Mth.DEG_TO_RAD;
		final float animProgress = ageInTicks * 90F * 0.75F;

		this.body.xRot += (-90F + Mth.sin(animProgress - 40F)) * wadeDegToRad;
		this.body.zRot -= (Mth.sin(animProgress - 120F) * 0.5F) * wadeDegToRad;
		this.body.x -= (Mth.sin(animProgress - 80F) * 0.2F) * wadeAmount;
		this.body.y += (Mth.sin(animProgress) * 0.4F) * wadeAmount;
		this.body.z += (3F - Mth.sin(animProgress - 180F) * 0.2F) * wadeAmount;

		this.torso.xRot += 90F * wadeDegToRad;
		this.torso.y += 3.5F * wadeAmount;

		this.head.xRot += (2.5F - Mth.sin(animProgress - 40F)) * wadeDegToRad;
		this.head.zRot -= Mth.sin(animProgress - 120F) * wadeDegToRad;
		this.head.y += 0.2F * wadeAmount;
		this.head.z -= 0.1F * wadeAmount;

		this.leftFlipper.xRot += 2.4687F * wadeDegToRad;
		this.leftFlipper.yRot += (-0.4911F - Mth.sin(animProgress - 40F) * 10F) * wadeDegToRad;
		this.leftFlipper.zRot += (-17.372F - Mth.sin(animProgress + 40F) * 5F) * wadeDegToRad;

		this.rightFlipper.xRot -= 4.2618F * wadeDegToRad;
		this.rightFlipper.yRot += (-0.9452F - Mth.sin(animProgress - 90F) * 10) * wadeDegToRad;
		this.rightFlipper.zRot += (14.6625F + Mth.sin(animProgress + 30F) * 5F) * wadeDegToRad;

		this.feet.xRot += 92.1786F * wadeDegToRad;
		this.feet.y -= 4.25F * wadeAmount;
		this.feet.z += 5.75F * wadeAmount;

		float footAnimProgress = ageInTicks * 90F * 6F;
		this.leftFoot.xRot += (36.0192F - Mth.sin(footAnimProgress) * 15F) * wadeDegToRad;
		this.leftFoot.yRot -= 11.5188F * wadeDegToRad;
		this.leftFoot.zRot -= 12.8719F * wadeDegToRad;
		this.leftFoot.y -= (0.5F - Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;

		this.rightFoot.xRot += (42.7455F + Mth.sin(footAnimProgress - 40F) * 15F) * wadeDegToRad;
		this.rightFoot.yRot += 5.7415F * wadeDegToRad;
		this.rightFoot.zRot += 15.1165F * wadeDegToRad;
		this.rightFoot.y -= (0.5F + Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;
	}

	// Original Molang animation made by DaDolphin!
	private void animateWadeMove(float limbSwing, float wadeAmount) {
		limbSwing *= 0.001F;
		final float wadeDegToRad = wadeAmount * Mth.DEG_TO_RAD;
		final float animProgress = limbSwing * 90F;

		this.body.xRot += (-72.5F + Mth.sin(animProgress - 40F)) * wadeDegToRad;
		this.body.zRot -= (Mth.sin(animProgress - 120F) * 0.5F) * wadeDegToRad;
		this.body.x -= (Mth.sin(animProgress - 80F) * 0.2F) * wadeAmount;
		this.body.y += (Mth.sin(animProgress) * 0.4F) * wadeAmount;
		this.body.z += (3F - Mth.sin(animProgress - 180F) * 0.2F) * wadeAmount;

		this.torso.xRot += 90F * wadeDegToRad;
		this.torso.y += 3.5F * wadeAmount;

		this.head.xRot += (-10F - Mth.sin(animProgress - 40F) * 2F) * wadeDegToRad;
		this.head.zRot -= (Mth.sin((animProgress * 2F) - 120F) * 1.5F) * wadeDegToRad;
		this.head.y += 0.2F * wadeAmount;
		this.head.z -= 0.1F * wadeAmount;

		this.leftFlipper.xRot += (-30.8475F + Mth.sin((animProgress * 2F) + 40F) * 15F) * wadeDegToRad;
		this.leftFlipper.yRot += (-5.7559F - Mth.sin((animProgress * 2F) - 40F) * 20F) * wadeDegToRad;
		this.leftFlipper.zRot += (-31.4462F - Mth.sin(animProgress + 40F) * 5F) * wadeDegToRad;

		this.rightFlipper.xRot += (-30.8475F - Mth.sin((animProgress * 2F) + 30F) * 15F) * wadeDegToRad;
		this.rightFlipper.yRot += (5.7559F - Mth.sin((animProgress * 2F) - 70F) * 20F) * wadeDegToRad;
		this.rightFlipper.zRot += (31.4462F + Mth.sin(animProgress + 30F) * 5F) * wadeDegToRad;

		this.feet.xRot += 92.1786F * wadeDegToRad;
		this.feet.y -= 4.25F * wadeAmount;
		this.feet.z += 5.75F * wadeAmount;

		final float footAnimProgress = limbSwing * 90F * 6F;
		this.leftFoot.xRot += (35.7474F - Mth.sin(footAnimProgress) * 15F) * wadeDegToRad;
		this.leftFoot.yRot -= 9.4931F * wadeDegToRad;
		this.leftFoot.zRot -= 11.3817F * wadeDegToRad;
		this.leftFoot.x -= 0.25F * wadeAmount;
		this.leftFoot.y -= (0.5F - Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;

		this.rightFoot.xRot += (42.7455F + Mth.sin(footAnimProgress - 40F) * 15F) * wadeDegToRad;
		this.rightFoot.yRot += 5.7415F * wadeDegToRad;
		this.rightFoot.zRot += 15.1165F * wadeDegToRad;
		this.rightFoot.x += 0.25F * wadeAmount;
		this.rightFoot.y -= (0.5F + Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;
	}

	// Original Molang animation made by DaDolphin!
	private void animateSlide(float limbSwing, float limbSwingAmount, float movementDelta, float slideAmount) {
		limbSwing *= 0.001F;
		final float animProgress = limbSwing * 90F;
		final float slideToRad = Mth.DEG_TO_RAD * slideAmount;
		final float slideRadSwing = limbSwingAmount * slideToRad;
		final float movementDeltaSlideToRad = Mth.DEG_TO_RAD * movementDelta;

		this.body.xRot += Mth.sin((animProgress * 2F) - 210F) * slideToRad;
		this.body.zRot += Mth.sin(animProgress - 210F) * slideToRad;
		this.body.z -= Mth.sin((animProgress * 2F) - 180F) * slideAmount;

		//this.head.xRot -= 90F * slideToRad;
		this.head.xRot -= (Mth.sin((animProgress * 2F) - 120F) * 1.5F) * slideRadSwing;
		this.head.yRot -= Mth.sin((animProgress * 2F) - 40F) * slideRadSwing;
		this.head.zRot -= Mth.sin(animProgress - 180F) * slideRadSwing;

		this.leftFlipper.xRot += (-13.6109F + Mth.clamp(-Mth.sin((animProgress * 2F) - 90F) * 15F, 0F, 15F)) * movementDeltaSlideToRad;
		this.leftFlipper.yRot -= (Mth.sin((animProgress * 2F) - 40F) * 20F) * movementDeltaSlideToRad;
		this.leftFlipper.zRot += (-42.5F - (Mth.sin(animProgress * 2F)) * 20F) * movementDeltaSlideToRad;

		this.rightFlipper.xRot += (-13.6109F + Mth.clamp(-Mth.sin((animProgress * 2F) - 90F) * 15F, 0F, 15F)) * movementDeltaSlideToRad;
		this.rightFlipper.yRot += (Mth.sin((animProgress * 2F) - 40F) * 20F) * movementDeltaSlideToRad;
		this.rightFlipper.zRot += (42.5F + (Mth.sin(animProgress * 2F)) * 20F) * movementDeltaSlideToRad;

		this.leftFoot.xRot -= (Mth.sin((animProgress * 2F) - 270F) * 2F) * slideRadSwing;
		this.rightFoot.xRot -= (Mth.sin((animProgress * 2F) - 240F) * 2F) * slideRadSwing;
	}

	// AViewFromTheTop/Lunade
	private void animateSwim(float limbSwing, float limbSwingAmount, float headPitch, float swimAmount) {
		final float swimLimbAmount = limbSwingAmount * swimAmount;

		final float flipperZRot = Mth.clamp(Mth.cos(limbSwing * 0.2F) * swimLimbAmount * swimAmount + (Mth.HALF_PI * 0.35F * swimAmount), 0F, Mth.PI);
		this.leftFlipper.zRot -= flipperZRot;
		this.rightFlipper.zRot += flipperZRot;

		this.body.xRot = Mth.rotLerp(swimLimbAmount, this.body.xRot, ((headPitch + 90F) * Mth.DEG_TO_RAD));

		final float headRot = -Mth.HALF_PI * swimLimbAmount;
		final float headY = -3F * swimLimbAmount;
		final float headZ = -2F * swimLimbAmount;
		this.head.xRot += headRot;
		this.head.y += headY;
		this.head.z += headZ;

		final float swimRotScale = swimLimbAmount * 10F * Mth.DEG_TO_RAD;
		final float swimXRot = Mth.cos(limbSwing * 0.175F) * swimRotScale;
		this.torso.xRot += swimXRot;
		this.head.xRot -= swimXRot;
		final float swimXRotDelayed = Mth.cos((limbSwing - 5F) * 0.175F) * swimRotScale;
		this.feet.xRot += swimXRotDelayed + (Mth.HALF_PI * swimLimbAmount);
	}

}
