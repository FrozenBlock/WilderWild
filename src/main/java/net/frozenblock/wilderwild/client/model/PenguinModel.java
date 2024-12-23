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

package net.frozenblock.wilderwild.client.model;

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
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class PenguinModel<T extends Penguin> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart right_flipper;
	private final ModelPart left_flipper;
	private final ModelPart feet;
	private final ModelPart left_foot;
	private final ModelPart right_foot;

	public PenguinModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.right_flipper = this.body.getChild("right_flipper");
		this.left_flipper = this.body.getChild("left_flipper");
		this.feet = root.getChild("feet");
		this.left_foot = this.feet.getChild("left_foot");
		this.right_foot = this.feet.getChild("right_foot");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.5F, -11F, -5F, 9F, 11F, 7F, new CubeDeformation(0F))
			.texOffs(28, 18)
				.addBox(-4.5F, 0F, -5F, 9F, 1F, 7F, new CubeDeformation(0F)),
			PartPose.offset(0F, 23F, 1F)
		);

		PartDefinition head = body.addOrReplaceChild(
			"head",
			CubeListBuilder.create()
				.texOffs(0, 18)
				.addBox(-3.5F, -5F, -4F, 7F, 5F, 7F, new CubeDeformation(0F))
			.texOffs(0, 42)
				.addBox(-4.5F, -6F, -4F, 9F, 3F, 7F, new CubeDeformation(0.005F))
			.texOffs(32, 0)
				.addBox(-0.5F, -3F, -6F, 1F, 2F, 2F, new CubeDeformation(0F)),
			PartPose.offset(0F, -11F, -1F)
		);

		PartDefinition right_flipper = body.addOrReplaceChild(
			"right_flipper",
			CubeListBuilder.create()
				.texOffs(16, 30)
				.addBox(-1F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(-4.5F, -11F, -1.5F)
		);
		PartDefinition left_flipper = body.addOrReplaceChild(
			"left_flipper",
			CubeListBuilder.create()
				.texOffs(24, 30)
				.addBox(0F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(4.5F, -11F, -1.5F)
		);

		PartDefinition feet = partdefinition.addOrReplaceChild(
			"feet", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F)
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

	private static void animateFoot(@NotNull ModelPart foot, float limbSwing, float limbSwingAmount, float animOffset) {
		float fastAngle = limbSwing * 0.3331F + animOffset;
		float angleSin = Math.sin(-fastAngle);

		float angleSinSwingAmount = angleSin * limbSwingAmount;
		float legZ = angleSinSwingAmount * 2F;

		float earlyAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.3331F));
		float earlyAngleSinSwingAmount = earlyAngleSin * limbSwingAmount;
		float onlyPositiveEarlyAngleSinSwingAmount = Math.max(earlyAngleSinSwingAmount, 0F);
		float legY = onlyPositiveEarlyAngleSinSwingAmount * 1F;

		foot.y -= legY;
		foot.z += legZ;

		float earlierAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.6662F));
		float earlierAngleSinSwingAmount = earlierAngleSin * limbSwingAmount;
		float earlierLegY = onlyPositiveEarlyAngleSinSwingAmount * 5F;
		float additionalFoot = Math.min(earlierLegY, 1F) * earlierAngleSinSwingAmount;
		//foot.xRot -= Math.sin(fastAngle) * limbSwingAmount * 0.5F;
		foot.xRot += additionalFoot * 0.5F;

		float laterAngleSin = Math.sin(-fastAngle + (Mth.PI * 0.3331F));
		float laterAngleSinSwingAmount = laterAngleSin * limbSwingAmount;
		float onlyPositiveLaterAngleSinSwingAmount = Math.max(laterAngleSinSwingAmount, 0F);
		float laterLegY = onlyPositiveLaterAngleSinSwingAmount * 5F;
		float additionalLateFoot = Math.min(laterLegY, 1F) * laterAngleSinSwingAmount;
		foot.y -= additionalLateFoot;
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yRot += netHeadYaw * Mth.DEG_TO_RAD;
		this.head.xRot += headPitch * Mth.DEG_TO_RAD;

		limbSwing *= 2.65F;
		limbSwingAmount = Math.min(limbSwingAmount * 1.5F, 1F);

		// FEET
		animateFoot(this.left_foot, limbSwing, limbSwingAmount, Mth.PI);
		animateFoot(this.right_foot, limbSwing, limbSwingAmount, 0F);

		// BODY
		float fastAngleBody = limbSwing * 0.3331F;
		float angleSinBody = Math.sin(-fastAngleBody);
		float angleSinSwingAmountBody = (angleSinBody * limbSwingAmount) * 0.175F;
		this.body.zRot += angleSinSwingAmountBody;

		float leftFlipperZRot = (55F * Mth.DEG_TO_RAD * limbSwingAmount) + (angleSinSwingAmountBody);
		this.left_flipper.zRot -= leftFlipperZRot;
		float rightFlipperZRot = (55F * Mth.DEG_TO_RAD * limbSwingAmount) + (angleSinSwingAmountBody);
		this.right_flipper.zRot += rightFlipperZRot;

		// HEAD
		float fastAngleNeckBase = limbSwing * 0.3331F;
		float angleSinNeckBase = Math.sin(-fastAngleNeckBase);
		float angleSinSwingAmountNeckBase = (angleSinNeckBase * limbSwingAmount) * 0.175F;
		this.head.zRot -= angleSinSwingAmountNeckBase;


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
