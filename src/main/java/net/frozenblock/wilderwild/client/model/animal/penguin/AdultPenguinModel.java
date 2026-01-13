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

import java.util.Set;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.animation.definitions.PenguinAnimation;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.minecraft.client.model.BabyModelTransform;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;

@Environment(EnvType.CLIENT)
public class AdultPenguinModel<T extends PenguinRenderState> extends PenguinModel<T> {
	public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 0F, 0F, 1F, 2.5F, 36F, Set.of());

	public AdultPenguinModel(ModelPart root) {
		super(root, PenguinAnimation.PENGUIN_LAY_DOWN, PenguinAnimation.PENGUIN_STAND_UP, PenguinAnimation.PENGUIN_CALL);
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, 17F, -0.5F)
		);

		final PartDefinition torso = body.addOrReplaceChild(
			"torso",
			CubeListBuilder.create()
				.texOffs(0, 0)
				.addBox(-4.5F, -5F, -3.5F, 9F, 11F, 7F, new CubeDeformation(0F))
				.texOffs(28, 18)
				.addBox(-4.5F, 6F, -3.5F, 9F, 1F, 7F, new CubeDeformation(0F)),
			PartPose.offset(0F, 0F, 0F)
		);
		torso.addOrReplaceChild(
			"head",
			CubeListBuilder.create()
				.texOffs(0, 18)
				.addBox(-3.5F, -5F, -3.5F, 7F, 5F, 7F, new CubeDeformation(0F))
				.texOffs(0, 42)
				.addBox(-4.5F, -6F, -3.5F, 9F, 3F, 7F, new CubeDeformation(0.005F))
				.texOffs(32, 0)
				.addBox(-0.5F, -3F, -5.5F, 1F, 2F, 2F, new CubeDeformation(0F)),
			PartPose.offset(0F, -5F, 0F)
		);

		 torso.addOrReplaceChild(
			"right_flipper",
			 CubeListBuilder.create()
				.texOffs(16, 30).addBox(-1F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(-4.5F, -5F, 0F)
		);
		torso.addOrReplaceChild(
			"left_flipper",
			CubeListBuilder.create()
				.texOffs(24, 30).addBox(0F, 0F, -1.5F, 1F, 9F, 3F, new CubeDeformation(0F)),
			PartPose.offset(4.5F, -5F, 0F)
		);

		final PartDefinition feet = body.addOrReplaceChild(
			"feet",
			CubeListBuilder.create(),
			PartPose.offset(0F, 7F, 0.5F)
		);
		feet.addOrReplaceChild(
			"right_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(-2.5F, 0F, 1F)
		);
		feet.addOrReplaceChild(
			"left_foot",
			CubeListBuilder.create()
				.texOffs(-5, 37)
				.addBox(-1.5F, 0F, -5F, 3F, 0F, 5F, new CubeDeformation(0.005F)),
			PartPose.offset(2.5F, 0F, 1F)
		);

		return LayerDefinition.create(mesh, 64, 64);
	}

	public static LayerDefinition createLegacyBabyBodyLayer() {
		return createBodyLayer().apply(BABY_TRANSFORMER);
	}

}
