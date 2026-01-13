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
import net.frozenblock.wilderwild.client.animation.definitions.BabyCrabAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class BabyCrabModel extends CrabModel {
	private static final float LEG_OFFSET = 1.5F;
	private static final float LEG_OFFSET_Z = 0.5F;
	private static final float LEG_ROT_X = 0.2618F;
	private static final float LEG_ROT_Z = 1.0472F;

	public BabyCrabModel(ModelPart root) {
		super(root, BabyCrabAnimation.BABY_CRAB_EMERGE_WAIT, BabyCrabAnimation.BABY_CRAB_DIG, BabyCrabAnimation.BABY_CRAB_EMERGE);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0F, 24.4F, -0.5F));
		body.addOrReplaceChild(
			"torso",
			CubeListBuilder.create()
				.texOffs(0, 0).addBox(-1.5F, -1.5F, -1F, 3F, 2F, 3F),
			PartPose.ZERO
		);

		final PartDefinition mainClaw = body.addOrReplaceChild(
			"main_claw",
			CubeListBuilder.create()
				.texOffs(0, 7)
				.addBox(0F, 0F, 0F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(1.75F, -0.5F, 1.75F, 0F, -0.7854F, -0.3491F)
		);
		mainClaw.addOrReplaceChild("claw_top", CubeListBuilder.create(), PartPose.ZERO);
		mainClaw.addOrReplaceChild("claw_bottom", CubeListBuilder.create(), PartPose.ZERO);

		body.addOrReplaceChild(
			"left_claw",
			CubeListBuilder.create()
				.texOffs(5, 7)
				.addBox(0F, 0F, 0F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(-1.75F, -0.5F, 1.75F, 0F, 0.7854F, 0.3491F)
		);

		// LEGS
		final PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0F, 24.9F, 0F));
		legs.addOrReplaceChild(
			"front_left_leg",
			CubeListBuilder.create()
				.texOffs(1, 5)
				.addBox(0F, 0F, 0F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET, 0F, LEG_OFFSET_Z, LEG_ROT_X, 0F, LEG_ROT_Z)
		);
		legs.addOrReplaceChild(
			"front_right_leg",
			CubeListBuilder.create()
				.texOffs(0, 4)
				.addBox(0F, 0F, 0F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET, 0F, LEG_OFFSET_Z, LEG_ROT_X, 0F, -LEG_ROT_Z)
		);
		legs.addOrReplaceChild(
			"middle_left_leg",
			CubeListBuilder.create().texOffs(5, 5)
				.addBox(0F, 0F, -0.5F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET, 0F, 0F, 0F, 0F, LEG_ROT_Z)
		);
		legs.addOrReplaceChild(
			"middle_right_leg",
			CubeListBuilder.create().texOffs(4, 4)
				.addBox(0F, 0F, -0.5F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET, 0F, 0F, 0F, 0F, -LEG_ROT_Z)
		);
		legs.addOrReplaceChild(
			"back_left_leg",
			CubeListBuilder.create().texOffs(9, 5)
				.addBox(0F, 0F, -1F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(-LEG_OFFSET, 0F, -LEG_OFFSET_Z, -LEG_ROT_X, 0F, LEG_ROT_Z)
		);
		legs.addOrReplaceChild(
			"back_right_leg",
			CubeListBuilder.create().texOffs(8, 4)
				.addBox(0F, 0F, -1F, 0F, 1F, 1F),
			PartPose.offsetAndRotation(LEG_OFFSET, 0F, -LEG_OFFSET_Z, -LEG_ROT_X, 0F, -LEG_ROT_Z)
		);

		return LayerDefinition.create(mesh, 16, 16);
	}
}
