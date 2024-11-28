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

public class OstrichInbredModel<T extends Ostrich> extends HierarchicalModel<T> {
	private static final float NECK_DELAY = 0F;
	private static final float OLD_NECK_DELAY = 0.0416375F;
	private static final float NECK_BASE_SWING = 0.175F * 0.5F;
	private static final float NECK_SWING = 0.175F * 0.65F;
	private static final float RAD_5 = 5F * Mth.DEG_TO_RAD;
	private static final float RAD_025 = Mth.DEG_TO_RAD * 0.25F;
	private static final float RAD_065 = Mth.DEG_TO_RAD * 0.65F;

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
	private float partialTick;
	private float scale;
	private float yOffset;
	public OstrichInbredModel(@NotNull ModelPart root) {
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

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 8.0F, 4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 16.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(86, 53).mirror().addBox(-3.5F, -8.5F, 1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2553F, 0.8126F, -0.1873F));

		PartDefinition right_leg_r2 = right_leg.addOrReplaceChild("right_leg_r2", CubeListBuilder.create().texOffs(86, 53).mirror().addBox(-1.0F, -7.5F, -2.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition right_leg_r3 = right_leg.addOrReplaceChild("right_leg_r3", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-6.0F, -17.0F, -4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.9603F, 0.884F, 2.852F));

		PartDefinition right_leg_r4 = right_leg.addOrReplaceChild("right_leg_r4", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(3.75F, -17.0F, 1.5F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2669F, -1.1473F, -0.1372F));

		PartDefinition right_leg_r5 = right_leg.addOrReplaceChild("right_leg_r5", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1731F, 0.0227F, -0.1289F));

		PartDefinition right_leg_r6 = right_leg.addOrReplaceChild("right_leg_r6", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1731F, -0.0227F, 0.1289F));

		PartDefinition right_leg_r7 = right_leg.addOrReplaceChild("right_leg_r7", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -17.5F, 4.5F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_leg_r8 = right_leg.addOrReplaceChild("right_leg_r8", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition right_foot_r1 = right_foot.addOrReplaceChild("right_foot_r1", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, -0.0873F, -0.829F, 0.0F));

		PartDefinition right_foot_r2 = right_foot.addOrReplaceChild("right_foot_r2", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, 0.0873F, -0.7854F, 0.0F));

		PartDefinition right_foot_r3 = right_foot.addOrReplaceChild("right_foot_r3", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, 0.0F, -0.8727F, 0.0F));

		PartDefinition right_foot_r4 = right_foot.addOrReplaceChild("right_foot_r4", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, -0.6545F, -0.829F, 0.0F));

		PartDefinition right_foot_r5 = right_foot.addOrReplaceChild("right_foot_r5", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.0873F, -0.1309F, 0.0F));

		PartDefinition right_foot_r6 = right_foot.addOrReplaceChild("right_foot_r6", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0873F, -0.0873F, 0.0F));

		PartDefinition right_foot_r7 = right_foot.addOrReplaceChild("right_foot_r7", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_foot_r8 = right_foot.addOrReplaceChild("right_foot_r8", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.6545F, -0.1309F, 0.0F));

		PartDefinition right_foot_r9 = right_foot.addOrReplaceChild("right_foot_r9", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.3054F, 0.0F));

		PartDefinition right_foot_r10 = right_foot.addOrReplaceChild("right_foot_r10", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.3054F, 0.0F));

		PartDefinition right_foot_r11 = right_foot.addOrReplaceChild("right_foot_r11", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.3491F, 0.0F));

		PartDefinition right_foot_r12 = right_foot.addOrReplaceChild("right_foot_r12", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 16.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition right_leg_r9 = left_leg.addOrReplaceChild("right_leg_r9", CubeListBuilder.create().texOffs(86, 53).mirror().addBox(-3.5F, -8.5F, 1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2553F, 0.8126F, -0.1873F));

		PartDefinition right_leg_r10 = left_leg.addOrReplaceChild("right_leg_r10", CubeListBuilder.create().texOffs(86, 53).mirror().addBox(-1.0F, -7.5F, -2.25F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7418F, 0.0F, 0.0F));

		PartDefinition right_leg_r11 = left_leg.addOrReplaceChild("right_leg_r11", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-6.0F, -17.0F, -4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.9603F, 0.884F, 2.852F));

		PartDefinition right_leg_r12 = left_leg.addOrReplaceChild("right_leg_r12", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(3.75F, -17.0F, 1.5F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2669F, -1.1473F, -0.1372F));

		PartDefinition right_leg_r13 = left_leg.addOrReplaceChild("right_leg_r13", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1731F, 0.0227F, -0.1289F));

		PartDefinition right_leg_r14 = left_leg.addOrReplaceChild("right_leg_r14", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1731F, -0.0227F, 0.1289F));

		PartDefinition right_leg_r15 = left_leg.addOrReplaceChild("right_leg_r15", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -17.5F, 4.5F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition right_leg_r16 = left_leg.addOrReplaceChild("right_leg_r16", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1.0F, -16.5F, 4.25F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

		PartDefinition right_foot_r13 = left_foot.addOrReplaceChild("right_foot_r13", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, -0.0873F, -0.829F, 0.0F));

		PartDefinition right_foot_r14 = left_foot.addOrReplaceChild("right_foot_r14", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, 0.0873F, -0.7854F, 0.0F));

		PartDefinition right_foot_r15 = left_foot.addOrReplaceChild("right_foot_r15", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, 0.0F, -0.8727F, 0.0F));

		PartDefinition right_foot_r16 = left_foot.addOrReplaceChild("right_foot_r16", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, -0.75F, -0.6545F, -0.829F, 0.0F));

		PartDefinition right_foot_r17 = left_foot.addOrReplaceChild("right_foot_r17", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.0873F, -0.1309F, 0.0F));

		PartDefinition right_foot_r18 = left_foot.addOrReplaceChild("right_foot_r18", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0873F, -0.0873F, 0.0F));

		PartDefinition right_foot_r19 = left_foot.addOrReplaceChild("right_foot_r19", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_foot_r20 = left_foot.addOrReplaceChild("right_foot_r20", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, -0.6545F, -0.1309F, 0.0F));

		PartDefinition right_foot_r21 = left_foot.addOrReplaceChild("right_foot_r21", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -3.0F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.3054F, 0.0F));

		PartDefinition right_foot_r22 = left_foot.addOrReplaceChild("right_foot_r22", CubeListBuilder.create().texOffs(93, 59).mirror().addBox(0.0F, -0.5F, 3.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.3054F, 0.0F));

		PartDefinition right_foot_r23 = left_foot.addOrReplaceChild("right_foot_r23", CubeListBuilder.create().texOffs(92, 60).mirror().addBox(0.0F, 0.0F, 2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.3491F, 0.0F));

		PartDefinition right_foot_r24 = left_foot.addOrReplaceChild("right_foot_r24", CubeListBuilder.create().texOffs(92, 62).mirror().addBox(0.0F, 0.0F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.005F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(9, 40).addBox(-6.0F, -12.0F, -2.0F, 12.0F, 14.0F, 10.0F, new CubeDeformation(0.0F))
			.texOffs(18, 49).addBox(-6.0F, -10.0F, -4.0F, 12.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 49).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 49).addBox(-4.0F, -8.0F, -6.0F, 8.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 49).addBox(-3.0F, -7.0F, -7.0F, 6.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(18, 49).addBox(-6.0F, -11.0F, -3.0F, 12.0F, 13.0F, 1.0F, new CubeDeformation(0.0F))
			.texOffs(13, 44).addBox(-5.0F, -11.0F, 3.0F, 10.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(11, 42).addBox(-4.0F, -10.0F, 2.0F, 8.0F, 10.0F, 8.0F, new CubeDeformation(0.0F))
			.texOffs(10, 41).addBox(-3.0F, -9.0F, 2.0F, 6.0F, 8.0F, 9.0F, new CubeDeformation(0.0F))
			.texOffs(8, 39).addBox(-2.0F, -8.0F, 1.0F, 4.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
			.texOffs(31, 38).addBox(-6.0F, 0.0F, -4.0F, 12.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 8.0F, 4.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(-6.0F, -4.0F, 5.0F));

		PartDefinition left_wing_inner_r1 = left_wing.addOrReplaceChild("left_wing_inner_r1", CubeListBuilder.create().texOffs(108, 54).addBox(-9.25F, 1.0F, -7.75F, 1.0F, 0.0F, 4.0F, new CubeDeformation(-0.001F))
			.texOffs(108, 32).addBox(-9.25F, -4.0F, -7.75F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6109F, 0.0F));

		PartDefinition left_wing_inner_r2 = left_wing.addOrReplaceChild("left_wing_inner_r2", CubeListBuilder.create().texOffs(102, 48).addBox(-1.0F, -4.0F, -10.0F, 1.0F, 0.0F, 10.0F, new CubeDeformation(-0.001F))
			.texOffs(102, 48).addBox(-1.0F, 1.0F, -10.0F, 1.0F, 0.0F, 10.0F, new CubeDeformation(-0.001F))
			.texOffs(98, 26).addBox(-1.0F, -4.0F, -10.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 6.0F));

		PartDefinition left_wing_inner_r3 = right_wing.addOrReplaceChild("left_wing_inner_r3", CubeListBuilder.create().texOffs(108, 54).mirror().addBox(7.75F, 1.0F, -8.75F, 1.0F, 0.0F, 4.0F, new CubeDeformation(-0.001F)).mirror(false)
			.texOffs(108, 32).mirror().addBox(7.75F, -4.0F, -8.75F, 1.0F, 8.0F, 4.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, 0.6109F, 0.0F));

		PartDefinition left_wing_inner_r4 = right_wing.addOrReplaceChild("left_wing_inner_r4", CubeListBuilder.create().texOffs(102, 48).mirror().addBox(-1.0F, -1.0F, -10.0F, 1.0F, 0.0F, 10.0F, new CubeDeformation(-0.001F)).mirror(false)
			.texOffs(102, 48).mirror().addBox(-1.0F, -4.0F, -10.0F, 1.0F, 0.0F, 10.0F, new CubeDeformation(-0.001F)).mirror(false)
			.texOffs(98, 26).mirror().addBox(-1.0F, -4.0F, -10.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(7.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 12.0F));

		PartDefinition neck_base_r1 = neck_base.addOrReplaceChild("neck_base_r1", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.789F));

		PartDefinition neck_base_r2 = neck_base.addOrReplaceChild("neck_base_r2", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.1817F));

		PartDefinition neck_base_r3 = neck_base.addOrReplaceChild("neck_base_r3", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -2.4871F));

		PartDefinition neck_base_r4 = neck_base.addOrReplaceChild("neck_base_r4", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.0107F));

		PartDefinition neck_base_r5 = neck_base.addOrReplaceChild("neck_base_r5", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.7925F));

		PartDefinition neck_base_r6 = neck_base.addOrReplaceChild("neck_base_r6", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.2253F));

		PartDefinition neck_base_r7 = neck_base.addOrReplaceChild("neck_base_r7", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.7017F));

		PartDefinition neck_base_r8 = neck_base.addOrReplaceChild("neck_base_r8", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.1781F));

		PartDefinition neck_base_r9 = neck_base.addOrReplaceChild("neck_base_r9", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

		PartDefinition neck_base_r10 = neck_base.addOrReplaceChild("neck_base_r10", CubeListBuilder.create().texOffs(81, 9).addBox(-4.0F, -5.0F, 0.0F, 8.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

		PartDefinition neck = neck_base.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(65, 0).addBox(-2.0F, -21.0F, 0.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 4.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(65, 10).addBox(-2.0F, -5.25F, 8.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 12).addBox(-2.0F, 2.0F, 10.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.7418F, 0.0F, 0.0F));

		PartDefinition neck_r2 = neck.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(65, 18).addBox(-2.0F, -3.0F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 16).addBox(-2.0F, -9.75F, 2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition neck_r3 = neck.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(65, 18).addBox(-2.0F, -3.5F, -1.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 20).addBox(-2.0F, -4.0F, -1.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 19).addBox(-2.0F, -4.75F, -0.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 18).addBox(-2.0F, -5.5F, 0.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.4835F, 0.0F, 0.0F));

		PartDefinition neck_r4 = neck.addOrReplaceChild("neck_r4", CubeListBuilder.create().texOffs(65, 17).addBox(-2.0F, -6.5F, -1.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.789F, 0.0F, 0.0F));

		PartDefinition neck_r5 = neck.addOrReplaceChild("neck_r5", CubeListBuilder.create().texOffs(65, 16).addBox(-2.0F, -7.25F, -1.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.9635F, 0.0F, 0.0F));

		PartDefinition neck_r6 = neck.addOrReplaceChild("neck_r6", CubeListBuilder.create().texOffs(65, 16).addBox(-2.0F, -7.75F, -2.25F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.0508F, 0.0F, 0.0F));

		PartDefinition neck_r7 = neck.addOrReplaceChild("neck_r7", CubeListBuilder.create().texOffs(65, 16).addBox(-2.0F, -8.25F, -3.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -2.2689F, 0.0F, 0.0F));

		PartDefinition neck_r8 = neck.addOrReplaceChild("neck_r8", CubeListBuilder.create().texOffs(65, 16).addBox(-2.0F, -9.75F, -0.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.8762F, 0.0F, 0.0F));

		PartDefinition neck_r9 = neck.addOrReplaceChild("neck_r9", CubeListBuilder.create().texOffs(65, 16).addBox(-2.0F, -9.75F, 4.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.309F, 0.0F, 0.0F));

		PartDefinition neck_r10 = neck.addOrReplaceChild("neck_r10", CubeListBuilder.create().texOffs(65, 15).addBox(-2.0F, -5.5F, 9.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition neck_r11 = neck.addOrReplaceChild("neck_r11", CubeListBuilder.create().texOffs(65, 15).addBox(-2.0F, -4.25F, 10.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition neck_r12 = neck.addOrReplaceChild("neck_r12", CubeListBuilder.create().texOffs(65, 15).addBox(-2.0F, -1.5F, 10.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r13 = neck.addOrReplaceChild("neck_r13", CubeListBuilder.create().texOffs(65, 14).addBox(-2.0F, -1.0F, 10.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition neck_r14 = neck.addOrReplaceChild("neck_r14", CubeListBuilder.create().texOffs(65, 14).addBox(-2.0F, -0.5F, 10.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 4).addBox(-2.0F, -15.75F, 3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition neck_r15 = neck.addOrReplaceChild("neck_r15", CubeListBuilder.create().texOffs(65, 13).addBox(-2.0F, 0.0F, 10.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition neck_r16 = neck.addOrReplaceChild("neck_r16", CubeListBuilder.create().texOffs(65, 13).addBox(-2.0F, 0.0F, 10.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 6).addBox(-2.0F, -12.0F, 6.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition neck_r17 = neck.addOrReplaceChild("neck_r17", CubeListBuilder.create().texOffs(65, 13).addBox(-2.0F, 1.0F, 10.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
			.texOffs(65, 8).addBox(-2.0F, -8.5F, 7.75F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition neck_r18 = neck.addOrReplaceChild("neck_r18", CubeListBuilder.create().texOffs(65, 12).addBox(-2.0F, 4.0F, 9.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

		PartDefinition neck_r19 = neck.addOrReplaceChild("neck_r19", CubeListBuilder.create().texOffs(65, 12).addBox(-2.0F, 4.25F, 9.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition neck_r20 = neck.addOrReplaceChild("neck_r20", CubeListBuilder.create().texOffs(65, 11).addBox(-2.0F, 7.75F, 6.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5272F, 0.0F, 0.0F));

		PartDefinition neck_r21 = neck.addOrReplaceChild("neck_r21", CubeListBuilder.create().texOffs(65, 11).addBox(-2.0F, 5.75F, 7.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4399F, 0.0F, 0.0F));

		PartDefinition neck_r22 = neck.addOrReplaceChild("neck_r22", CubeListBuilder.create().texOffs(65, 11).addBox(-2.0F, 0.75F, 9.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0908F, 0.0F, 0.0F));

		PartDefinition neck_r23 = neck.addOrReplaceChild("neck_r23", CubeListBuilder.create().texOffs(65, 11).addBox(-2.0F, -2.75F, 9.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.8727F, 0.0F, 0.0F));

		PartDefinition neck_r24 = neck.addOrReplaceChild("neck_r24", CubeListBuilder.create().texOffs(65, 7).addBox(-2.0F, -10.25F, 7.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

		PartDefinition neck_r25 = neck.addOrReplaceChild("neck_r25", CubeListBuilder.create().texOffs(65, 5).addBox(-2.0F, -13.5F, 5.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition neck_r26 = neck.addOrReplaceChild("neck_r26", CubeListBuilder.create().texOffs(65, 3).addBox(-2.0F, -17.0F, 1.5F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r27 = neck.addOrReplaceChild("neck_r27", CubeListBuilder.create().texOffs(67, 0).addBox(0.0F, -21.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.2618F, 0.0F));

		PartDefinition neck_r28 = neck.addOrReplaceChild("neck_r28", CubeListBuilder.create().texOffs(65, 0).addBox(-2.0F, -21.0F, 0.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

		PartDefinition beak = neck.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(81, 1).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -18.0F, 4.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(62, 46).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
			.texOffs(62, 40).addBox(-3.0F, 10.0F, -6.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.0F, -7.0F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	private static void animateLeg(@NotNull ModelPart leg, @NotNull ModelPart foot, float limbSwing, float limbSwingAmount, float animOffset) {
		float fastAngle = limbSwing * 0.3331F + animOffset;
		float angleSin = Math.sin(-fastAngle);

		float angleSinSwingAmount = angleSin * limbSwingAmount;
		float legZ = angleSinSwingAmount * 11F;

		float earlyAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.3331F));
		float earlyAngleSinSwingAmount = earlyAngleSin * limbSwingAmount;
		float onlyPositiveEarlyAngleSinSwingAmount = Math.max(earlyAngleSinSwingAmount, 0F);
		float legY = onlyPositiveEarlyAngleSinSwingAmount * 6F;

		leg.xRot -= Math.sin(fastAngle) * limbSwingAmount * 0.6F;
		leg.y -= legY * 1.35F;
		leg.z += legZ * 1.35F;

		float earlierAngleSin = Math.sin(-fastAngle - (Mth.PI * 0.6662F));
		float earlierAngleSinSwingAmount = earlierAngleSin * limbSwingAmount;
		float earlierLegY = onlyPositiveEarlyAngleSinSwingAmount * 5F;
		float additionalFoot = Math.min(earlierLegY, 1F) * earlierAngleSinSwingAmount;
		foot.xRot -= leg.xRot;
		foot.xRot -= additionalFoot;

		float laterAngleSin = Math.sin(-fastAngle + (Mth.PI * 0.3331F));
		float laterAngleSinSwingAmount = laterAngleSin * limbSwingAmount;
		float onlyPositiveLaterAngleSinSwingAmount = Math.max(laterAngleSinSwingAmount, 0F);
		float laterLegY = onlyPositiveLaterAngleSinSwingAmount * 5F;
		float additionalLateFoot = Math.min(laterLegY, 1F) * laterAngleSinSwingAmount;
		foot.y -= additionalLateFoot;
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		limbSwing *= 1.65F;
		limbSwingAmount = Math.min(limbSwingAmount * 1.5F, 1F);

		// LEGS
		animateLeg(this.left_leg, this.left_foot, limbSwing, limbSwingAmount, Mth.PI);
		animateLeg(this.right_leg, this.right_foot, limbSwing, limbSwingAmount, 0F);

		// BODY
		float fastAngleBody = limbSwing * 0.3331F;
		float angleSinBody = Math.sin(-fastAngleBody);
		float angleSinSwingAmountBody = (angleSinBody * limbSwingAmount) * 0.175F;
		this.body.zRot += angleSinSwingAmountBody;

		// NECK
		float beakAnimProgress = entity.getBeakAnimProgress(partialTick);
		float rotation = beakAnimProgress * -Mth.PI;

		this.neck_base.xRot = rotation * 0.3F;
		this.neck.xRot = rotation * 0.7F;

		headPitch = Mth.lerp(entity.getTargetStraightProgress(this.partialTick), headPitch, Math.min(headPitch, 0F));
		headPitch = Mth.lerp(beakAnimProgress, headPitch, 0F);

		float fastAngleNeckBase = limbSwing * 0.3331F + NECK_DELAY;
		float angleSinNeckBase = Math.sin(-fastAngleNeckBase);
		float angleSinSwingAmountNeckBase = (angleSinNeckBase * limbSwingAmount) * NECK_BASE_SWING;
		this.neck_base.zRot += angleSinSwingAmountNeckBase;

		float fastAngleNeck = limbSwing * 0.3331F + NECK_DELAY;
		float angleSinNeck = Math.sin(-fastAngleNeck);
		float angleSinSwingAmountNeck = (angleSinNeck * limbSwingAmount) * NECK_SWING;
		this.neck.zRot += angleSinSwingAmountNeck;

		this.neck.xRot += (limbSwingAmount * RAD_5);

		this.neck_base.xRot -= headPitch * RAD_025;
		this.neck_base.yRot += netHeadYaw * RAD_025;
		this.neck.xRot -= headPitch * RAD_065;
		this.neck.yRot += netHeadYaw * RAD_065;
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
		this.scale = entity.getAgeScale();

		if (entity.isBaby()) {
			this.neck.xScale = 1.5F;
			this.neck.yScale = 1.5F;
			this.neck.zScale = 1.5F;
			this.yOffset = 0.75F;
		} else {
			this.yOffset = 0F;
		}
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
		poseStack.pushPose();
		poseStack.translate(0, this.yOffset, 0);
		poseStack.scale(this.scale, this.scale, this.scale);
		this.root().render(poseStack, buffer, packedLight, packedOverlay, color);
		poseStack.popPose();
	}

	@NotNull
	@Override
	public ModelPart root() {
		return this.root;
	}

}
