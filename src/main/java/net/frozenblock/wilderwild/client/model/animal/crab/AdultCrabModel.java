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
import net.frozenblock.wilderwild.client.animation.definitions.CrabAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class AdultCrabModel extends CrabModel {
	private static final float LEG_OFFSET = 4F;
	private static final float LEG_OFFSET_MOJANG = 4.5F;

	public AdultCrabModel(ModelPart root) {
		super(root, CrabAnimation.CRAB_EMERGE_WAIT, CrabAnimation.CRAB_DIG, CrabAnimation.CRAB_EMERGE);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0F, 23.9F, -2F));
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
		final PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0F, 22.9F, -1F));
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

		return LayerDefinition.create(mesh, 32, 32);
	}

	public static LayerDefinition createMojangBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0F, 23.9F, -2F));
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
		final PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0F, 22.9F, -1F));
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
}
