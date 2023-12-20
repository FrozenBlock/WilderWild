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

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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
	private static final float LEG_ROT = 40F * PI_180;

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

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -16.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -5.0F, 0.0F, 0.0F, 0.0F, -3.1416F));
		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(34, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -16.0F, -1.0F, 0.0F, 0.0F, -3.1416F));
		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 16.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -5.0F, 0.0F));
		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(34, 12).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 16.0F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(18, 33).addBox(-6.0F, -12.0F, -7.0F, 12.0F, 12.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 8.0F, -2.0F));
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -13.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.0F, -4.0F, 4.0F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -4.0F, -13.0F, 1.0F, 8.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -4.0F, 4.0F, 0.0F, 0.0F, (float) Math.PI));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create().texOffs(35, 19).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, 12.0F));
		PartDefinition neck = neck_base.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(64, 27).addBox(-2.0F, -21.0F, 0.0F, 4.0F, 21.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));
		PartDefinition beak = neck.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(65, 22).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 4.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 80, 64);
	}

	private float partialTick;

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float movementDelta = Math.min(limbSwingAmount * 4F, 1.0F);
		limbSwing *= 2.75F;
		float fastAngle = limbSwing * 0.3331F;
		float limbSwing5 = Math.min(1F, limbSwingAmount * 5) * 0.5F;
		float fastAngleSin = Math.sin(fastAngle);
		float walkA = Mth.lerp(movementDelta, 0F, ((1F - fastAngleSin) * limbSwing5) - 0.5F);
		float walkB = Mth.lerp(movementDelta, 0F, ((1F + fastAngleSin) * limbSwing5) - 0.5F);

		float legRot = Math.sin(fastAngle) * 0.4F * limbSwingAmount;
		float lerpedWalkA = Mth.lerp(walkA, -legRot, LEG_ROT);
		float lerpedWalkB = Mth.lerp(walkB, legRot, LEG_ROT);

		this.left_leg.xRot += lerpedWalkA;
		this.left_leg.y -= walkA * 5F;
		this.left_leg.z -= walkA * 5F;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
		float beakAnimProgress = entity.getBeakAnimProgress(partialTick);
		float rotation = beakAnimProgress * 180F * -PI_180;

		this.neck_base.xRot = Mth.clamp(rotation, -22.5F * PI_180, 22.5F * PI_180);
		this.neck.xRot = rotation - this.neck_base.xRot;
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.legs.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		this.body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@NotNull
	@Override
	public ModelPart root() {
		return this.root;
	}
}
