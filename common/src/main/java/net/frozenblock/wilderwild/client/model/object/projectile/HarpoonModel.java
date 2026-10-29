/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.client.model.object.projectile;

import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.object.projectile.ArrowModel;
import net.minecraft.util.Mth;

@ClientOnly
public class HarpoonModel extends ArrowModel {

	public HarpoonModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		root.addOrReplaceChild(
			"back",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(0F, -2.5F, -2.5F,0F, 5F, 5F),
			PartPose.offsetAndRotation(
				0F, 0F, 0F,
				Mth.PI / 4F, 0F, 0F
			).withScale(0.8F)
		);

		final CubeListBuilder cross = CubeListBuilder.create()
			.texOffs(0, 0)
			.addBox(
				-14F, -2F, 0F,
				18F, 4F, 0F,
				CubeDeformation.NONE,
				1F, 0.8F
			);
		root.addOrReplaceChild("cross_1", cross, PartPose.rotation(Mth.PI / 4F, 0F, 0F));
		root.addOrReplaceChild("cross_2", cross, PartPose.rotation((Mth.PI * 3F / 4F), 0F, 0F));

		return LayerDefinition.create(mesh.apply(ARROW_SCALE), 64, 32);
	}
}
