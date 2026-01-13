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

package net.frozenblock.wilderwild.client.model.animal.ostrich;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.AbstractOstrichRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class BabyOstrichModel<T extends AbstractOstrichRenderState> extends OstrichModel<T> {

	public BabyOstrichModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0F, 22F, 0.5F));

		final PartDefinition leftLeg = legs.addOrReplaceChild(
			"left_leg",
			CubeListBuilder.create()
				.texOffs(17, 0)
				.addBox(-0.5F, 0F, 0F, 1F, 2F, 0F),
			PartPose.offset(1F, 0F, 0F)
		);
		leftLeg.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(16, 3)
				.addBox(-0.5F, 0F, -1F, 1F, 0F, 1F, new CubeDeformation(0.005F)),
			PartPose.offset(0F, 2F, 0F)
		);

		final PartDefinition rightLeg = legs.addOrReplaceChild(
			"right_leg",
			CubeListBuilder.create()
				.texOffs(14, 0)
				.addBox(-0.5F, 0F, 0F, 1F, 2F, 0F),
			PartPose.offset(-1F, 0F, 0F)
		);
		rightLeg.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(13, 3)
				.addBox(-0.5F, 0F, -1F, 1F, 0F, 1F, new CubeDeformation(0.005F)),
			PartPose.offset(0F, 2F, 0F)
		);

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-2F, -4F, -2.5F, 4F, 4F, 5F),
			PartPose.offset(0F, 22F, 0.5F)
		);
		body.addOrReplaceChild(
			"saddle",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-2F, -4F, -2.5F, 4F, 4F, 5F, new CubeDeformation(0.5F)),
			PartPose.ZERO
		);

		body.addOrReplaceChild(
			"left_wing",
			CubeListBuilder.create()
				.texOffs(10, 14)
				.addBox(0F, 0F, -1F, 2F, 0F, 3F),
			PartPose.offset(2F, -2F, 0.5F)
		);
		body.addOrReplaceChild(
			"right_wing",
			CubeListBuilder.create()
				.texOffs(6, 14)
				.addBox(-2F, 0F, -1F, 2F, 0F, 3F),
			PartPose.offset(-2F, -2F, 0.5F)
		);

		final PartDefinition neckBase = body.addOrReplaceChild("neck_base", CubeListBuilder.create(), PartPose.offset(0F, -2F, -2.5F));
		final PartDefinition neck = neckBase.addOrReplaceChild(
			"neck",
			CubeListBuilder.create()
				.texOffs(0, 9)
				.addBox(-1F, -5F, -2F, 2F, 6F, 2F)
				.texOffs(9, 11)
				.addBox(-1F, -4F, -3F, 2F, 1F, 1F),
			PartPose.ZERO
		);
		neck.addOrReplaceChild(
			"beak",
			CubeListBuilder.create()
				.texOffs(9, 11)
				.addBox(-1F, -4F, -3F, 2F, 1F, 1F),
			PartPose.ZERO
		);

		body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(mesh, 32, 32);
	}
}
