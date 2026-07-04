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

package net.frozenblock.wilderwild.client.model.animal.jellyfish;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class BabyJellyfishModel extends JellyfishModel {

	public BabyJellyfishModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition bone = root.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0F, 8F, 0F));
		bone.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(2, 0)
				.addBox(-1.5F, -1.5F, -1.5F, 3F, 2F, 3F, new CubeDeformation(0.25F))
				.texOffs(2, 5)
				.addBox(-1.5F, -1.5F, -1.5F, 3F, 2F, 3F),
			PartPose.ZERO
		);

		final PartDefinition tentacleBase = bone.addOrReplaceChild("tentacleBase", CubeListBuilder.create(), PartPose.ZERO);
		makeTentacles(tentacleBase, JELLYFISH_TENTACLES);

		final PartDefinition armBase = bone.addOrReplaceChild("armBase", CubeListBuilder.create(), PartPose.ZERO);
		armBase.addOrReplaceChild("arms1", CubeListBuilder.create(), PartPose.ZERO);
		armBase.addOrReplaceChild("arms2", CubeListBuilder.create(), PartPose.ZERO);

		return LayerDefinition.create(mesh, 16, 16);
	}

	private static void makeTentacles(PartDefinition partDefinition, int amount) {
		final CubeListBuilder empty = CubeListBuilder.create();
		final CubeListBuilder tentacle = CubeListBuilder.create().texOffs(0, 8).addBox(-0.5F, 0F, 1F, 1F, 2F, 0F, new CubeDeformation(0.001F));
		for (int i = 0; i < amount; ++i) {
			partDefinition.addOrReplaceChild(createTentacleName(i, false), empty, PartPose.ZERO);
			float rot = i * Mth.PI * 2F /  amount;
			partDefinition.addOrReplaceChild(
				createTentacleName(i, true),
				tentacle,
				PartPose.offsetAndRotation(
					Mth.cos(rot) * 0.65F,
					0F,
					Mth.sin(rot) * 0.65F,
					0F,
					i * Mth.PI * -2F / amount + Mth.HALF_PI,
					0F
				)
			);
		}
	}
}
