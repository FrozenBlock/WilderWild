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

package net.frozenblock.wilderwild.client.model.monster.skeleton;

import net.frozenblock.wilderwild.client.renderer.entity.state.SunkenRenderState;
import net.frozenblock.wilderwild.entity.monster.skeleton.SunkenVariant;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.monster.skeleton.SkeletonModel;

@ClientOnly
public class SunkenModel extends SkeletonModel<SunkenRenderState> {
	private final ModelPart headCoral;

	public SunkenModel(ModelPart root) {
		super(root);
		this.headCoral = this.head.getChild("coral");
	}

	public static LayerDefinition createBodyLayer() {
		final MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0F);
		final PartDefinition root = mesh.getRoot();
		createSunkenMesh(root);
		return LayerDefinition.create(mesh, 64, 64);
	}

	protected static void createSunkenMesh(PartDefinition root) {
		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(16, 16)
				.addBox(-4F, 0F, -2F, 8F, 12F, 4F)
				.texOffs(19, 32)
				.addBox(-5F, 0F, -2F, 10F, 14F, 0F),
			PartPose.ZERO
		);

		final PartDefinition rightArm = root.addOrReplaceChild(
			"right_arm",
			CubeListBuilder.create()
				.texOffs(40, 16)
				.addBox(-1F, -2F, -1F, 2F, 12F, 2F),
			PartPose.offset(-5F, 2F, 0F)
		);

		final PartDefinition leftArm = root.addOrReplaceChild(
			"left_arm",
			CubeListBuilder.create()
				.texOffs(40, 16)
				.mirror()
				.addBox(-1F, -2F, -1F, 2F, 12F, 2F)
				.mirror(false),
			PartPose.offset(5F, 2F, 0F)
		);

		final PartDefinition rightLeg = root.addOrReplaceChild(
			"right_leg",
			CubeListBuilder.create()
				.texOffs(0, 16)
				.addBox(-1F, 0F, -1F, 2F, 12F, 2F),
			PartPose.offset(-2F, 12F, 0F)
		);

		final PartDefinition leftLeg = root.addOrReplaceChild(
			"left_leg",
			CubeListBuilder.create().texOffs(0, 16)
				.mirror()
				.addBox(-1F, 0F, -1F, 2F, 12F, 2F)
				.mirror(false),
			PartPose.offset(2F, 12F, 0F)
		);

		final PartDefinition head = root.getChild("head");
		final PartDefinition coral = head.addOrReplaceChild("coral", CubeListBuilder.create(), PartPose.offset(0F, -4F, 0F));

		final PartDefinition coralHat = coral.addOrReplaceChild(
			"coral_hat",
			CubeListBuilder.create()
				.texOffs(0, 33)
				.addBox(-4F, -6F, 0F, 9F, 8F, 0F),
			PartPose.offset(3F, -4F, -4F)
		);
		final PartDefinition coralShelf = coral.addOrReplaceChild(
			"coral_shelf",
			CubeListBuilder.create()
				.texOffs(46, 26)
				.addBox(-3F, 0F, -2F, 6F, 0F, 4F),
			PartPose.offset(2F, 1F, 4F)
		);
	}

	@Override
	public void setupAnim(SunkenRenderState state) {
		super.setupAnim(state);
		this.headCoral.visible = state.modelType == SunkenVariant.ModelType.CORAL;
	}
}
