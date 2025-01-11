/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.model;

import net.frozenblock.wilderwild.client.animation.definitions.PenguinAnimation;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Pose;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class PenguinModel<T extends Penguin> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart right_flipper;
	private final ModelPart left_flipper;
	private final ModelPart feet;
	private final ModelPart left_foot;
	private final ModelPart right_foot;

	public PenguinModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.torso = this.body.getChild("torso");
		this.head = this.torso.getChild("head");
		this.right_flipper = this.torso.getChild("right_flipper");
		this.left_flipper = this.torso.getChild("left_flipper");
		this.feet = this.body.getChild("feet");
		this.left_foot = this.feet.getChild("left_foot");
		this.right_foot = this.feet.getChild("right_foot");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, 17F, -0.5F)
		);

		PartDefinition torso = body.addOrReplaceChild(
			"torso", CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.5F, -5F, -3.5F, 9F, 11F, 7F, new CubeDeformation(0F))
			.texOffs(28, 18)
				.addBox(-4.5F, 6F, -3.5F, 9F, 1F, 7F, new CubeDeformation(0F)),
			PartPose.offset(0F, 0F, 0F)
		);
		PartDefinition head = torso.addOrReplaceChild(
			"head", CubeListBuilder.create()
				.texOffs(0, 18)
				.addBox(-3.5F, -5F, -3.5F, 7F, 5F, 7F, new CubeDeformation(0F))
			.texOffs(0, 42)
				.addBox(-4.5F, -6F, -3.5F, 9F, 3F, 7F, new CubeDeformation(0.005F))
			.texOffs(32, 0)
				.addBox(-0.5F, -3F, -5.5F, 1F, 2F, 2F, new CubeDeformation(0F)),
			PartPose.offset(0F, -5F, 0F)
		);
		PartDefinition right_flipper = torso.addOrReplaceChild(
			"right_flipper", CubeListBuilder.create()
				.texOffs(16, 30).addBox(-1F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(-4.5F, -5F, 0F)
		);
		PartDefinition left_flipper = torso.addOrReplaceChild(
			"left_flipper", CubeListBuilder.create()
				.texOffs(24, 30).addBox(0F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(4.5F, -5F, 0F)
		);

		PartDefinition feet = body.addOrReplaceChild(
			"feet",
			CubeListBuilder.create(),
			PartPose.offset(0F, 7F, 0.5F)
		);
		PartDefinition left_foot = feet.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(2.5F, 0F, 1F)
		);
		PartDefinition right_foot = feet.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(-2.5F, 0F, 1F)
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float partialTick = ageInTicks - entity.tickCount;
		float movementDelta = Math.min(limbSwingAmount * 5F, 1F);
		float notMovingDelta = 1F - movementDelta;
		float swimAmount = entity.getSwimAmount(partialTick);
		float notSwimmingAmount = 1F - swimAmount;
		float wadeProgress = entity.getWadeProgress(partialTick);
		float notWadingProgress = 1F - wadeProgress;
		float slideProgress = entity.getPose() == Pose.SLIDING ? 1F : 0F;
		float notSlidingProgress = 1F - slideProgress;

		this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD * notSwimmingAmount;
		this.head.xRot += headPitch * Mth.DEG_TO_RAD * notSwimmingAmount * notSlidingProgress;

		limbSwing *= 2.65F;
		limbSwingAmount = Math.min(limbSwingAmount * 1.5F, 1F);
		this.animate(entity.layDownAnimationState, PenguinAnimation.PENGUIN_LAY_DOWN, ageInTicks);
		this.animate(entity.standUpAnimationState, PenguinAnimation.PENGUIN_STAND_UP, ageInTicks);
		this.animateWalk(limbSwing, limbSwingAmount * notSwimmingAmount * notWadingProgress * notSlidingProgress);
		this.animateSlide(limbSwing * 2.5F, Math.min(limbSwingAmount * 2F, 1F), slideProgress * notSwimmingAmount * notWadingProgress);
		//this.animateSlide(limbSwing * 2F, limbSwingAmount, slideProgress * notSwimmingAmount * notWadingProgress);
		this.animateWade(ageInTicks, wadeProgress * notMovingDelta * notSlidingProgress);
		this.animateWadeMove(limbSwing, limbSwingAmount * wadeProgress * notSwimmingAmount * movementDelta * notSlidingProgress);
		//this.animateSwimIdle(ageInTicks, wadeProgress * notMovingDelta);
		this.animateSwim(limbSwing, limbSwingAmount, headPitch, swimAmount * notSlidingProgress);
	}

	// Original Molang animation made by DaDolphin!
	private void animateWalk(float limbSwing, float limbSwingAmount) {
		limbSwing *= 0.001F;
		limbSwing *= 1.35F;
		float walkDelta = Math.min(limbSwingAmount * 2F, 1F);
		this.body.y -= 0.25F * limbSwingAmount;
		limbSwingAmount *= Mth.DEG_TO_RAD * 2F;

		float walkSpeed = limbSwing * 90F;
		float walk35 = walkSpeed * 3.5F;
		float walk7 = walk35 * 2F;

		// TORSO & HEAD
		this.torso.y -= Mth.sin(walk7 - 80F) * limbSwingAmount * 0.1F;
		this.torso.xRot -= Mth.sin(walk7 - 80F) * limbSwingAmount * 0.3F;
		this.torso.yRot -= Mth.sin(walk35) * limbSwingAmount * 11F;
		this.torso.zRot -= Mth.sin(walk35 - 80F) * limbSwingAmount * 7F;

		this.head.yRot += Mth.sin(walk35) * limbSwingAmount * 9F;
		this.head.zRot += Mth.sin(walk35 - 80F) * limbSwingAmount * 4F;

		// FLIPPERS
		this.left_flipper.xRot += Mth.sin(walk35 - 150F) * limbSwingAmount * 2F;
		this.left_flipper.yRot += Mth.sin(walk35 - 80F) * limbSwingAmount * 3F;
		this.left_flipper.zRot += (-20F + Mth.sin(walk35 - 80F) * 5F) * limbSwingAmount;

		this.right_flipper.xRot += Mth.sin(walk35 - 10F) * limbSwingAmount * 2F;
		this.right_flipper.yRot += Mth.sin(walk35 - 260F) * limbSwingAmount * 3F;
		this.right_flipper.zRot += (20F - Mth.sin(walk35 - 140F) * 3F) * limbSwingAmount;

		// FEET
		this.left_foot.y += Mth.clamp(Mth.sin(walk35 - 80F), -1F, 0F) * walkDelta * 0.75F;
		this.left_foot.z -= Mth.sin(walk35 - 160F) * walkDelta;
		this.left_foot.xRot -= Math.clamp(Mth.sin(walk35 - 110F) * 5F, 0F, 5F) * Mth.DEG_TO_RAD * walkDelta;

		this.right_foot.y += Mth.clamp(-Mth.sin(walk35 - 80F), -1F, 0F) * walkDelta * 0.75F;
		this.right_foot.z += Mth.sin(walk35 - 160F) * walkDelta;
		this.right_foot.xRot -= Math.clamp(-Mth.sin(walk35 - 110F) * 5F, 0F, 5F) * Mth.DEG_TO_RAD * walkDelta;
	}

	// Original Molang animation made by DaDolphin!
	private void animateWade(float ageInTicks, float wadeAmount) {
		ageInTicks *= 0.001F;
		float wadeDegToRad = wadeAmount * Mth.DEG_TO_RAD;
		float animProgress = ageInTicks * 90F * 0.75F;

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

		this.left_flipper.xRot += 2.4687F * wadeDegToRad;
		this.left_flipper.yRot += (-0.4911F - Mth.sin(animProgress - 40F) * 10F) * wadeDegToRad;
		this.left_flipper.zRot += (-17.372F - Mth.sin(animProgress + 40F) * 5F) * wadeDegToRad;

		this.right_flipper.xRot -= 4.2618F * wadeDegToRad;
		this.right_flipper.yRot += (-0.9452F - Mth.sin(animProgress - 90F) * 10) * wadeDegToRad;
		this.right_flipper.zRot += (14.6625F + Mth.sin(animProgress + 30F) * 5F) * wadeDegToRad;

		this.feet.xRot += 92.1786F * wadeDegToRad;
		this.feet.y -= 4.25F * wadeAmount;
		this.feet.z += 5.75F * wadeAmount;

		float footAnimProgress = ageInTicks * 90F * 6F;
		this.left_foot.xRot += (36.0192F - Mth.sin(footAnimProgress) * 15F) * wadeDegToRad;
		this.left_foot.yRot -= 11.5188F * wadeDegToRad;
		this.left_foot.zRot -= 12.8719F * wadeDegToRad;
		this.left_foot.y -= (0.5F - Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;

		this.right_foot.xRot += (42.7455F + Mth.sin(footAnimProgress - 40F) * 15F) * wadeDegToRad;
		this.right_foot.yRot += 5.7415F * wadeDegToRad;
		this.right_foot.zRot += 15.1165F * wadeDegToRad;
		this.right_foot.y -= (0.5F + Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;
	}

	// Original Molang animation made by DaDolphin!
	private void animateWadeMove(float limbSwing, float wadeAmount) {
		limbSwing *= 0.001F;
		float wadeDegToRad = wadeAmount * Mth.DEG_TO_RAD;
		float animProgress = limbSwing * 90F;

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

		this.left_flipper.xRot += (-30.8475F + Mth.sin((animProgress * 2F) + 40F) * 15F) * wadeDegToRad;
		this.left_flipper.yRot += (-5.7559F - Mth.sin((animProgress * 2F) - 40F) * 20F) * wadeDegToRad;
		this.left_flipper.zRot += (-31.4462F - Mth.sin(animProgress + 40F) * 5F) * wadeDegToRad;

		this.right_flipper.xRot += (-30.8475F - Mth.sin((animProgress * 2F) + 30F) * 15F) * wadeDegToRad;
		this.right_flipper.yRot += (5.7559F - Mth.sin((animProgress * 2F) - 70F) * 20F) * wadeDegToRad;
		this.right_flipper.zRot += (31.4462F + Mth.sin(animProgress + 30F) * 5F) * wadeDegToRad;

		this.feet.xRot += 92.1786F * wadeDegToRad;
		this.feet.y -= 4.25F * wadeAmount;
		this.feet.z += 5.75F * wadeAmount;

		float footAnimProgress = limbSwing * 90F * 6F;
		this.left_foot.xRot += (35.7474F - Mth.sin(footAnimProgress) * 15F) * wadeDegToRad;
		this.left_foot.yRot -= 9.4931F * wadeDegToRad;
		this.left_foot.zRot -= 11.3817F * wadeDegToRad;
		this.left_foot.x -= 0.25F * wadeAmount;
		this.left_foot.y -= (0.5F - Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;

		this.right_foot.xRot += (42.7455F + Mth.sin(footAnimProgress - 40F) * 15F) * wadeDegToRad;
		this.right_foot.yRot += 5.7415F * wadeDegToRad;
		this.right_foot.zRot += 15.1165F * wadeDegToRad;
		this.right_foot.x += 0.25F * wadeAmount;
		this.right_foot.y -= (0.5F + Mth.sin(footAnimProgress - 80F) * 0.5F) * wadeAmount;
	}

	// Original Molang animation made by DaDolphin!
	private void animateSlide(float limbSwing, float limbSwingAmount, float slideAmount) {
		limbSwing *= 0.001F;
		float animProgress = limbSwing * 90F;
		float slideToRad = Mth.DEG_TO_RAD * slideAmount;
		float slideRadSwing = limbSwingAmount * slideToRad;

		this.body.xRot += Mth.sin((animProgress * 2F) - 210F) * slideToRad;
		this.body.zRot += Mth.sin(animProgress - 210F) * slideToRad;
		this.body.z -= Mth.sin((animProgress * 2F) - 180F) * slideAmount;

		//this.head.xRot -= 90F * slideToRad;
		this.head.xRot -= (Mth.sin((animProgress * 2F) - 120F) * 1.5F) * slideRadSwing;
		this.head.yRot -= Mth.sin((animProgress * 2F) - 40F) * slideRadSwing;
		this.head.zRot -= Mth.sin(animProgress - 180F) * slideRadSwing;

		this.left_flipper.xRot += (-13.6109F + Mth.clamp(-Mth.sin((animProgress * 2F) - 90F) * 15F, 0F, 15F)) * slideRadSwing;
		this.left_flipper.yRot -= (Mth.sin((animProgress * 2F) - 40F) * 20F) * slideRadSwing;
		this.left_flipper.zRot += (-42.5F - (Mth.sin(animProgress * 2F)) * 20F) * slideRadSwing;

		this.right_flipper.xRot += (-13.6109F + Mth.clamp(-Mth.sin((animProgress * 2F) - 90F) * 15F, 0F, 15F)) * slideRadSwing;
		this.right_flipper.yRot += (Mth.sin((animProgress * 2F) - 40F) * 20F) * slideRadSwing;
		this.right_flipper.zRot += (42.5F + (Mth.sin(animProgress * 2F)) * 20F) * slideRadSwing;

		this.left_foot.xRot -= (Mth.sin((animProgress * 2F) - 270F) * 2F) * slideRadSwing;
		this.right_foot.xRot -= (Mth.sin((animProgress * 2F) - 240F) * 2F) * slideRadSwing;
	}

	private void animateSwim(float limbSwing, float limbSwingAmount, float headPitch, float swimAmount) {
		// DaDolphin
		/*
		limbSwing *= 0.001F;
		headPitch *= swimAmount;
		float animProgress = limbSwing * 90F;
		float swimLimbAmount = limbSwingAmount * swimAmount;
		float swimLimbToRad = swimLimbAmount * Mth.DEG_TO_RAD;

		this.body.xRot += headPitch * swimLimbToRad;
		this.body.xRot -= (Mth.sin((animProgress * 2F) - 80F) * 3F) * swimLimbToRad;
		this.body.yRot -= (Mth.sin((animProgress * 2F) - 180F)) * swimLimbToRad;
		this.body.zRot += (Mth.sin((animProgress * 3F) - 50F) * 2F) * swimLimbToRad;
		this.body.x -= (Mth.sin(animProgress) * 0.3F) * swimLimbAmount;
		this.body.y += (Mth.sin(animProgress * 2F)) * swimLimbAmount;
		this.body.z += (Mth.sin((animProgress * 3F) - 50F)) * swimLimbAmount;

		this.torso.xRot += 90F * swimLimbToRad;
		this.torso.y += 3.5F * swimLimbAmount;

		this.head.xRot += (-90F + Mth.sin((animProgress * 2F) - 240F)) * swimLimbToRad;
		this.head.yRot -= (Mth.sin((animProgress * 3F) - 50F) * 1.5F) * swimLimbToRad;
		this.head.y -= 3F * swimLimbAmount;
		this.head.z -= 1.63F * swimLimbAmount;

		this.left_flipper.xRot -= 0.4104F * swimLimbToRad;
		this.left_flipper.yRot += (-0.0179F - Mth.sin((animProgress * 3F) - 70F) * 5F) * swimLimbToRad;
		this.left_flipper.zRot += (-22.5168F - Mth.sin((animProgress * 3F) - 20F) * 15F) * swimLimbToRad;

		this.right_flipper.xRot -= 0.3343F * swimLimbToRad;
		this.right_flipper.yRot += (0.0146F + Mth.sin((animProgress * 3F) - 50F) * 5F) * swimLimbToRad;
		this.right_flipper.zRot += (22.5111F + Mth.sin(animProgress * 3F) * 15F) * swimLimbToRad;

		this.feet.xRot += 92.1786F * swimLimbToRad;
		this.feet.y -= 4.25F * swimLimbAmount;
		this.feet.z += 5.75F * swimLimbAmount;

		this.left_foot.xRot += (22.5F - Mth.sin((animProgress * 3F) - 80F) * 20F) * swimLimbToRad;

		this.right_foot.xRot += (27.5F + Mth.sin((animProgress * 3F) - 80F) * 20F) * swimLimbToRad;
		 */

		// AViewFromTheTop/Lunade
		float swimLimbAmount = limbSwingAmount * swimAmount;
		float swimLimbToRad = swimLimbAmount * Mth.DEG_TO_RAD;

		float flipperZRot = Mth.clamp(Mth.cos(limbSwing * 0.2F) * swimLimbAmount * swimAmount + (Mth.HALF_PI * 0.35F * swimAmount), 0F, Mth.PI);
		this.left_flipper.zRot -= flipperZRot;
		this.right_flipper.zRot += flipperZRot;

		this.body.xRot = Mth.rotLerp(swimLimbAmount, this.body.xRot, ((headPitch + 90F) * Mth.DEG_TO_RAD));

		float headRot = -Mth.HALF_PI * swimLimbAmount;
		float headY = -3F * swimLimbAmount;
		float headZ = -2F * swimLimbAmount;
		this.head.xRot += headRot;
		this.head.y += headY;
		this.head.z += headZ;

		float swimRotScale = swimLimbAmount * 10F * Mth.DEG_TO_RAD;
		float swimXRot = Mth.cos(limbSwing * 0.175F) * swimRotScale;
		this.torso.xRot += swimXRot;
		this.head.xRot -= swimXRot;
		float swimXRotDelayed = Mth.cos((limbSwing - 5F) * 0.175F) * swimRotScale;
		this.feet.xRot += swimXRotDelayed;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
	}

	@NotNull
	@Override
	public ModelPart root() {
		return this.root;
	}

}
