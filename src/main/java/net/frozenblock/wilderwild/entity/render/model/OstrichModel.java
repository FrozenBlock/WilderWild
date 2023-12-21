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

import net.frozenblock.wilderwild.entity.Ostrich;
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

public class OstrichModel<T extends Ostrich> extends HierarchicalModel<T> {
	private static final float PI_180 = Mth.PI / 180F;

	private final ModelPart root;
	private final ModelPart legs;
	private final ModelPart left_leg;
	private final ModelPart left_foot;
	private final ModelPart right_leg;
	private final ModelPart right_foot;
	private final ModelPart body;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart neck_base;
	private final ModelPart neck;
	private final ModelPart beak;
	private final ModelPart tail;

	public OstrichModel(@NotNull ModelPart root) {
		this.root = root;

		this.legs = root.getChild("legs");

		this.left_leg = this.legs.getChild("left_leg");
		this.left_foot = this.left_leg.getChild("left_foot");
		this.right_leg = this.legs.getChild("right_leg");
		this.right_foot = this.right_leg.getChild("right_foot");

		this.body = root.getChild("body");
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

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 13.0F, -2.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -18.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 11.0F, 0.0F));
		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(34, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));
		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(-1.0F, -18.0F, -1.0F, 2.0F, 18.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 11.0F, 0.0F));
		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(34, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 33).addBox(-6.0F, -12.0F, -7.0F, 12.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -2.0F));
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -13.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -4.0F, 4.0F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(11.0F, -4.0F, -13.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -4.0F, 4.0F));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create().texOffs(35, 19).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 12.0F));
		PartDefinition neck = neck_base.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(64, 27).addBox(-2.0F, -21.0F, 0.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));
		PartDefinition beak = neck.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(65, 22).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 4.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 80, 64);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		limbSwing *= 1.5F;
		limbSwingAmount = Math.min(limbSwingAmount * 1.5F, 1F);

		// LEGS
		animateLeg(this.left_leg, this.left_foot, limbSwing, limbSwingAmount, 0F);
		animateLeg(this.right_leg, this.right_foot, limbSwing, limbSwingAmount, (float) Math.PI);

		// BODY
		float fastAngleBody = limbSwing * 0.3331F;
		float angleSinBody = Math.sin(-fastAngleBody);
		float angleSinSwingAmountBody = (angleSinBody * limbSwingAmount) * 0.175F;
		this.body.zRot += angleSinSwingAmountBody;

		// NECK
		float beakAnimProgress = entity.getBeakAnimProgress(partialTick);
		float rotation = beakAnimProgress * 180F * -PI_180;

		this.neck_base.xRot = rotation * 0.3F;
		this.neck.xRot = rotation * 0.7F;

		headPitch = Mth.lerp(entity.getTargetPassengerProgress(this.partialTick), headPitch, Math.min(headPitch, 0F));
		headPitch = Mth.lerp(beakAnimProgress, headPitch, 0F);

		float fastAngleNeckBase = limbSwing * 0.3331F + 0.0416375F;
		float angleSinNeckBase = Math.sin(-fastAngleNeckBase);
		float angleSinSwingAmountNeckBase = (angleSinNeckBase * limbSwingAmount) * 0.175F * 0.5F;
		this.neck_base.zRot -= angleSinSwingAmountNeckBase;

		float fastAngleNeck = limbSwing * 0.3331F + 0.0466375F;
		float angleSinNeck = Math.sin(-fastAngleNeck);
		float angleSinSwingAmountNeck = (angleSinNeck * limbSwingAmount) * 0.175F * 0.5F;
		this.neck.zRot -= angleSinSwingAmountNeck;

		this.neck.xRot += (limbSwingAmount * 5F * PI_180);

		this.neck_base.xRot -= headPitch * PI_180 * 0.25F;
		this.neck_base.yRot += netHeadYaw * PI_180 * 0.25F;
		this.neck.xRot -= headPitch * PI_180 * 0.65F;
		this.neck.yRot += netHeadYaw * PI_180 * 0.65F;
	}

	private float partialTick;

	private static void animateLeg(@NotNull ModelPart leg, @NotNull ModelPart foot, float limbSwing, float limbSwingAmount, float animOffset) {
		float fastAngle = limbSwing * 0.3331F + animOffset;
		float angleSin = Math.sin(-fastAngle);

		float angleSinSwingAmount = angleSin * limbSwingAmount;
		float legZ = angleSinSwingAmount * 10F;

		float earlyAngleSin = Math.sin(-fastAngle - ((float) Math.PI * 0.3331F));
		float earlyAngleSinSwingAmount = earlyAngleSin * limbSwingAmount;
		float onlyPositiveEarlyAngleSinSwingAmount = Math.max(earlyAngleSinSwingAmount, 0F);
		float legY = onlyPositiveEarlyAngleSinSwingAmount * 5F;

		leg.xRot -= Math.sin(fastAngle) * limbSwingAmount * 0.5F;
		leg.y -= legY;
		leg.z += legZ;

		float earlierAngleSin = Math.sin(-fastAngle - ((float) Math.PI * 0.6662F));
		float earlierAngleSinSwingAmount = earlierAngleSin * limbSwingAmount;
		float earlierLegY = onlyPositiveEarlyAngleSinSwingAmount * 5F;
		float additionalFoot = Math.min(earlierLegY, 1F) * earlierAngleSinSwingAmount;
		foot.xRot -= leg.xRot;
		foot.xRot -= additionalFoot;

		float laterAngleSin = Math.sin(-fastAngle + ((float) Math.PI * 0.3331F));
		float laterAngleSinSwingAmount = laterAngleSin * limbSwingAmount;
		float onlyPositiveLaterAngleSinSwingAmount = Math.max(laterAngleSinSwingAmount, 0F);
		float laterLegY = onlyPositiveLaterAngleSinSwingAmount * 5F;
		float additionalLateFoot = Math.min(laterLegY, 1F) * laterAngleSinSwingAmount;
		foot.y -= additionalLateFoot;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
	}

	@NotNull
	@Override
	public ModelPart root() {
		return this.root;
	}
}
