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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.ScorchedRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ScorchedModel extends EntityModel<ScorchedRenderState> {
	public final ModelPart head;
	public final ModelPart rightHindLeg;
	public final ModelPart leftHindLeg;
	public final ModelPart rightMiddleHindLeg;
	public final ModelPart leftMiddleHindLeg;
	public final ModelPart rightMiddleFrontLeg;
	public final ModelPart leftMiddleFrontLeg;
	public final ModelPart rightFrontLeg;
	public final ModelPart leftFrontLeg;
	private final ModelPart body0;
	private final ModelPart body1;

	public ScorchedModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.rightHindLeg = root.getChild("right_hind_leg");
		this.leftHindLeg = root.getChild("left_hind_leg");
		this.rightMiddleHindLeg = root.getChild("right_middle_hind_leg");
		this.leftMiddleHindLeg = root.getChild("left_middle_hind_leg");
		this.rightMiddleFrontLeg = root.getChild("right_middle_front_leg");
		this.leftMiddleFrontLeg = root.getChild("left_middle_front_leg");
		this.rightFrontLeg = root.getChild("right_front_leg");
		this.leftFrontLeg = root.getChild("left_front_leg");
		this.body0 = root.getChild("body0");
		this.body1 = root.getChild("body1");
	}

	@Override
	public void setupAnim(ScorchedRenderState renderState) {
		spiderAnim(renderState); // basically super.setupAnim but we can't have a parent class here
		float ageInTicks = renderState.ageInTicks;
		float walkPos = renderState.walkAnimationPos;
		float walkSpeed = renderState.walkAnimationSpeed;
		float lavaProgress = renderState.lavaAnimProgress;
		float lavaProgToRad = lavaProgress * Mth.DEG_TO_RAD;

		float yPos = 1F;
		float headProgress = Mth.clamp(((renderState.xRot + 25F) / 45F) * 2F, 0F, 1F);
		this.head.y += yPos * 3F * lavaProgress;
		this.head.xRot -= (float) (12.5F + (Math.sin(Mth.PI + ageInTicks * 0.0375F) * 6.25)) * lavaProgToRad * headProgress;
		this.head.zRot += (float) (Math.sin(Mth.PI - ageInTicks * 0.05F) * 7.5F) * lavaProgToRad * headProgress;

		this.body0.y += yPos * 1.5F * lavaProgress;
		this.body0.zRot += (float) (Math.sin(Mth.HALF_PI -ageInTicks * 0.05F) * 7.5F) * lavaProgToRad;

		this.body1.xRot += (float) (12.5F + (Math.sin(ageInTicks * 0.0375F) * 6.25)) * lavaProgToRad;
		this.body1.zRot += (float) (Math.sin(-ageInTicks * 0.05F) * 7.5F) * lavaProgToRad;

		float easyLimbSwing = walkPos * 0.6662F * 0.75F;
		float yRotA = (0.7853982F) - (Mth.cos(easyLimbSwing) * 0.8F * walkSpeed);
		float zRotA = (-0.7853982F * 0.5F) + (Mth.sin(-easyLimbSwing) * 0.8F * walkSpeed);
		this.rightHindLeg.yRot = Mth.lerp(lavaProgress, this.rightHindLeg.yRot, yRotA);
		this.rightHindLeg.zRot = Mth.lerp(lavaProgress, this.rightHindLeg.zRot, zRotA);
		this.rightHindLeg.y -= yPos * lavaProgress;
		this.leftHindLeg.yRot = Mth.lerp(lavaProgress, this.leftHindLeg.yRot, -yRotA);
		this.leftHindLeg.zRot = Mth.lerp(lavaProgress, this.leftHindLeg.zRot, -zRotA);
		this.leftHindLeg.y -= yPos * lavaProgress;

		float yRotB = (0.3926991F) - (Mth.cos((easyLimbSwing) + (Mth.HALF_PI * 0.5F)) * 0.8F * walkSpeed);
		float zRotB = (-0.58119464F * 0.5F) + (Mth.sin((-easyLimbSwing) + (Mth.HALF_PI * 0.5F)) * 0.8F * walkSpeed);
		this.rightMiddleHindLeg.yRot = Mth.lerp(lavaProgress, this.rightMiddleHindLeg.yRot, yRotB);
		this.rightMiddleHindLeg.zRot = Mth.lerp(lavaProgress, this.rightMiddleHindLeg.zRot, zRotB);
		this.rightMiddleHindLeg.y -= yPos * lavaProgress;
		this.leftMiddleHindLeg.yRot = Mth.lerp(lavaProgress, this.leftMiddleHindLeg.yRot, -yRotB);
		this.leftMiddleHindLeg.zRot = Mth.lerp(lavaProgress, this.leftMiddleHindLeg.zRot, -zRotB);
		this.leftMiddleHindLeg.y -= yPos * lavaProgress;

		float yRotC = (-0.3926991F) - (Mth.cos((-easyLimbSwing) + Mth.HALF_PI) * 0.8F * walkSpeed);
		float zRotC = (-0.58119464F * 0.5F) + (Mth.sin((easyLimbSwing) + Mth.HALF_PI) * 0.8F * walkSpeed);
		this.rightMiddleFrontLeg.yRot = Mth.lerp(lavaProgress, this.rightMiddleFrontLeg.yRot, yRotC);
		this.rightMiddleFrontLeg.zRot = Mth.lerp(lavaProgress, this.rightMiddleFrontLeg.zRot, zRotC);
		this.rightMiddleFrontLeg.y -= yPos * lavaProgress;
		this.leftMiddleFrontLeg.yRot = Mth.lerp(lavaProgress, this.leftMiddleFrontLeg.yRot, -yRotC);
		this.leftMiddleFrontLeg.zRot = Mth.lerp(lavaProgress, this.leftMiddleFrontLeg.zRot, -zRotC);
		this.leftMiddleFrontLeg.y -= yPos * lavaProgress;

		float yRotD = (-0.7853982F) - (Mth.cos((-easyLimbSwing) + Mth.HALF_PI + (Mth.HALF_PI * 0.5F)) * 0.8F * walkSpeed);
		float zRotD = (-0.7853982F * 0.5F) + (Mth.sin((easyLimbSwing) + Mth.HALF_PI + (Mth.HALF_PI * 0.5F)) * 0.8F * walkSpeed);
		this.rightFrontLeg.yRot = Mth.lerp(lavaProgress, this.rightFrontLeg.yRot, yRotD);
		this.rightFrontLeg.zRot = Mth.lerp(lavaProgress, this.rightFrontLeg.zRot, zRotD);
		this.rightFrontLeg.y -= yPos * lavaProgress;
		this.leftFrontLeg.yRot = Mth.lerp(lavaProgress, this.leftFrontLeg.yRot, -yRotD);
		this.leftFrontLeg.zRot = Mth.lerp(lavaProgress, this.leftFrontLeg.zRot, -zRotD);
		this.leftFrontLeg.y -= yPos * lavaProgress;
	}

	private void spiderAnim(@NotNull LivingEntityRenderState renderState) {
		this.root.getAllParts().forEach(ModelPart::resetPose);
		this.head.yRot = renderState.yRot * Mth.DEG_TO_RAD;
		this.head.xRot = renderState.xRot * Mth.DEG_TO_RAD;
		float f = renderState.walkAnimationPos * 0.6662F;
		float g = renderState.walkAnimationSpeed;
		float h = -(Mth.cos(f * 2F) * 0.4F) * g;
		float i = -(Mth.cos(f * 2F + Mth.PI) * 0.4F) * g;
		float j = -(Mth.cos(f * 2F + (Mth.PI / 2)) * 0.4F) * g;
		float k = -(Mth.cos(f * 2F + (Mth.PI * 3F / 2F)) * 0.4F) * g;
		float l = Math.abs(Mth.sin(f) * 0.4F) * g;
		float m = Math.abs(Mth.sin(f + Mth.PI) * 0.4F) * g;
		float n = Math.abs(Mth.sin(f + (Mth.PI / 2F)) * 0.4F) * g;
		float o = Math.abs(Mth.sin(f + (Mth.PI * 3F / 2F)) * 0.4F) * g;
		this.rightHindLeg.yRot += h;
		this.leftHindLeg.yRot -= h;
		this.rightMiddleHindLeg.yRot += i;
		this.leftMiddleHindLeg.yRot -= i;
		this.rightMiddleFrontLeg.yRot += j;
		this.leftMiddleFrontLeg.yRot -= j;
		this.rightFrontLeg.yRot += k;
		this.leftFrontLeg.yRot -= k;
		this.rightHindLeg.zRot += l;
		this.leftHindLeg.zRot -= l;
		this.rightMiddleHindLeg.zRot += m;
		this.leftMiddleHindLeg.zRot -= m;
		this.rightMiddleFrontLeg.zRot += n;
		this.leftMiddleFrontLeg.zRot -= n;
		this.rightFrontLeg.zRot += o;
		this.leftFrontLeg.zRot -= o;
	}
}
