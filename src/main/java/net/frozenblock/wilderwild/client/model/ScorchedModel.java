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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.Scorched;
import net.minecraft.client.model.SpiderModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class ScorchedModel<T extends Scorched> extends SpiderModel<T> {

	private final ModelPart body0;
	private final ModelPart body1;

	public ScorchedModel(ModelPart root) {
		super(root);
		this.body0 = root.getChild("body0");
		this.body1 = root.getChild("body1");
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		float partialTick = ageInTicks - entity.tickCount;
		float lavaProgress = entity.getLavaAnimProgress(partialTick);
		float lavaProgToRad = lavaProgress * Mth.DEG_TO_RAD;

		float yPos = 1F;
		float headProgress = Mth.clamp(((headPitch + 25F) / 45F) * 2F, 0F, 1F);
		this.head.y += yPos * 3F * lavaProgress;
		this.head.xRot -= (float) (12.5F + (Math.sin(Mth.PI + ageInTicks * 0.0375F) * 6.25)) * lavaProgToRad * headProgress;
		this.head.zRot += (float) (Math.sin(Mth.PI -ageInTicks * 0.05F) * 7.5F) * lavaProgToRad * headProgress;

		this.body0.y += yPos * 1.5F * lavaProgress;
		this.body0.zRot += (float) (Math.sin(Mth.HALF_PI -ageInTicks * 0.05F) * 7.5F) * lavaProgToRad;

		this.body1.xRot += (float) (12.5F + (Math.sin(ageInTicks * 0.0375F) * 6.25)) * lavaProgToRad;
		this.body1.zRot += (float) (Math.sin(-ageInTicks * 0.05F) * 7.5F) * lavaProgToRad;

		float easyLimbSwing = limbSwing * 0.6662F * 0.75F;
		float yRotA = (0.7853982F) - (Mth.cos(easyLimbSwing) * 0.8F * limbSwingAmount);
		float zRotA = (-0.7853982F * 0.5F) + (Mth.sin(-easyLimbSwing) * 0.8F * limbSwingAmount);
		this.rightHindLeg.yRot = Mth.lerp(lavaProgress, this.rightHindLeg.yRot, yRotA);
		this.rightHindLeg.zRot = Mth.lerp(lavaProgress, this.rightHindLeg.zRot, zRotA);
		this.rightHindLeg.y -= yPos * lavaProgress;
		this.leftHindLeg.yRot = Mth.lerp(lavaProgress, this.leftHindLeg.yRot, -yRotA);
		this.leftHindLeg.zRot = Mth.lerp(lavaProgress, this.leftHindLeg.zRot, -zRotA);
		this.leftHindLeg.y -= yPos * lavaProgress;

		float yRotB = (0.3926991F) - (Mth.cos((easyLimbSwing) + (Mth.HALF_PI * 0.5F)) * 0.8F * limbSwingAmount);
		float zRotB = (-0.58119464F * 0.5F) + (Mth.sin((-easyLimbSwing) + (Mth.HALF_PI * 0.5F)) * 0.8F * limbSwingAmount);
		this.rightMiddleHindLeg.yRot = Mth.lerp(lavaProgress, this.rightMiddleHindLeg.yRot, yRotB);
		this.rightMiddleHindLeg.zRot = Mth.lerp(lavaProgress, this.rightMiddleHindLeg.zRot, zRotB);
		this.rightMiddleHindLeg.y -= yPos * lavaProgress;
		this.leftMiddleHindLeg.yRot = Mth.lerp(lavaProgress, this.leftMiddleHindLeg.yRot, -yRotB);
		this.leftMiddleHindLeg.zRot = Mth.lerp(lavaProgress, this.leftMiddleHindLeg.zRot, -zRotB);
		this.leftMiddleHindLeg.y -= yPos * lavaProgress;

		float yRotC = (-0.3926991F) - (Mth.cos((-easyLimbSwing) + Mth.HALF_PI) * 0.8F * limbSwingAmount);
		float zRotC = (-0.58119464F * 0.5F) + (Mth.sin((easyLimbSwing) + Mth.HALF_PI) * 0.8F * limbSwingAmount);
		this.rightMiddleFrontLeg.yRot = Mth.lerp(lavaProgress, this.rightMiddleFrontLeg.yRot, yRotC);
		this.rightMiddleFrontLeg.zRot = Mth.lerp(lavaProgress, this.rightMiddleFrontLeg.zRot, zRotC);
		this.rightMiddleFrontLeg.y -= yPos * lavaProgress;
		this.leftMiddleFrontLeg.yRot = Mth.lerp(lavaProgress, this.leftMiddleFrontLeg.yRot, -yRotC);
		this.leftMiddleFrontLeg.zRot = Mth.lerp(lavaProgress, this.leftMiddleFrontLeg.zRot, -zRotC);
		this.leftMiddleFrontLeg.y -= yPos * lavaProgress;

		float yRotD = (-0.7853982F) - (Mth.cos((-easyLimbSwing) + Mth.HALF_PI + (Mth.HALF_PI * 0.5F)) * 0.8F * limbSwingAmount);
		float zRotD = (-0.7853982F * 0.5F) + (Mth.sin((easyLimbSwing) + Mth.HALF_PI + (Mth.HALF_PI * 0.5F)) * 0.8F * limbSwingAmount);
		this.rightFrontLeg.yRot = Mth.lerp(lavaProgress, this.rightFrontLeg.yRot, yRotD);
		this.rightFrontLeg.zRot = Mth.lerp(lavaProgress, this.rightFrontLeg.zRot, zRotD);
		this.rightFrontLeg.y -= yPos * lavaProgress;
		this.leftFrontLeg.yRot = Mth.lerp(lavaProgress, this.leftFrontLeg.yRot, -yRotD);
		this.leftFrontLeg.zRot = Mth.lerp(lavaProgress, this.leftFrontLeg.zRot, -zRotD);
		this.leftFrontLeg.y -= yPos * lavaProgress;
	}

}
