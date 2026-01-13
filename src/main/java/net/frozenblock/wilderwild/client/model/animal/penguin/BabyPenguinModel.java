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

package net.frozenblock.wilderwild.client.model.animal.penguin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.animation.definitions.BabyPenguinAnimation;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class BabyPenguinModel<T extends PenguinRenderState> extends PenguinModel<T> {

	public BabyPenguinModel(ModelPart root) {
		super(root, BabyPenguinAnimation.BABY_PENGUIN_LAY_DOWN, BabyPenguinAnimation.BABY_PENGUIN_STAND_UP, BabyPenguinAnimation.BABY_PENGUIN_CALL);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.5F, 21.5F, 0F));

		final PartDefinition torso = body.addOrReplaceChild(
			"torso",
			CubeListBuilder.create()
				.texOffs(0, 7)
				.addBox(-2.5F, -1.5F, -2F, 5F, 3F, 4F)
				.texOffs(0, 14)
				.addBox(-2.5F, 1.5F, -2F, 5F, 1F, 4F),
			PartPose.ZERO
		);
		torso.addOrReplaceChild(
			"head",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-2.5F, -3F, -2.5F, 5F, 3F, 4F)
				.texOffs(18, 0)
				.addBox(-0.5F, -2F, -3.5F, 1F, 1F, 1F),
			PartPose.offset(0F, -1.5F, 0.5F)
		);

		torso.addOrReplaceChild(
			"left_flipper",
			CubeListBuilder.create()
				.texOffs(14, 19)
				.addBox(0F, 0F, -1F, 0F, 3F, 2F, new CubeDeformation(0.005F)),
			PartPose.offsetAndRotation(2.5F, -1.5F, 0F, 0F, 0F, -0.2618F)
		);
		torso.addOrReplaceChild(
			"right_flipper",
			CubeListBuilder.create()
				.texOffs(10, 19)
				.addBox(0F, 0F, -1F, 0F, 3F, 2F, new CubeDeformation(0.005F)),
			PartPose.offsetAndRotation(-2.5F, -1.5F, 0F, 0F, 0F, 0.2618F)
		);

		final PartDefinition feet = body.addOrReplaceChild("feet", CubeListBuilder.create(), PartPose.offset(0F, 2.5F, 0.5F));
		feet.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(-3, 21)
				.addBox(-0.5F, 0F, -3F, 1F, 0F, 3F, new CubeDeformation(0.005F)),
			PartPose.offset(1F, 0F, 0.5F)
		);
		feet.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(-3, 21)
				.addBox(-0.5F, 0F, -3F, 1F, 0F, 3F, new CubeDeformation(0.005F)),
			PartPose.offset(-1F, 0F, 0.5F)
		);

		return LayerDefinition.create(mesh, 32, 32);
	}
}
