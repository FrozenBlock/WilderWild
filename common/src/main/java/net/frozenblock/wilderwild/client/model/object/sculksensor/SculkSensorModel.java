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

package net.frozenblock.wilderwild.client.model.object.sculksensor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.SculkSensorRenderState;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class SculkSensorModel extends Model<SculkSensorRenderState> {
	private static final float TENDRIL_ANGLE = 45F * Mth.DEG_TO_RAD;
	private static final float TENDRIL_ANGLE_SOUTH = 225F * Mth.DEG_TO_RAD;
	private final ModelPart tendril1;
	private final ModelPart tendril2;
	private final ModelPart tendril3;
	private final ModelPart tendril4;

	public SculkSensorModel(ModelPart root) {
		super(root, RenderTypes::entityCutoutCull);
		this.tendril1 = root.getChild("tendril1");
		this.tendril2 = root.getChild("tendril2");
		this.tendril3 = root.getChild("tendril3");
		this.tendril4 = root.getChild("tendril4");
	}

	public static LayerDefinition createModelLayer() {
		final MeshDefinition mesh = new MeshDefinition();
		final PartDefinition root = mesh.getRoot();
		makeTendril(root, "tendril1", false, 3F, 3F, -TENDRIL_ANGLE);
		makeTendril(root, "tendril2", true, 13F, 3F, TENDRIL_ANGLE);
		makeTendril(root, "tendril3", false, 13F, 13F, -TENDRIL_ANGLE_SOUTH);
		makeTendril(root, "tendril4", true, 3F, 13F,  TENDRIL_ANGLE_SOUTH);
		return LayerDefinition.create(mesh, 16, 16);
	}

	private static void makeTendril(
		PartDefinition root, String name, boolean mirror, float xPos, float zPos, float rot
	) {
		final CubeListBuilder cubeListBuilder = CubeListBuilder.create().texOffs(0, 0);
		if (mirror) cubeListBuilder.mirror();
		cubeListBuilder.addBox(-4F, -8F, 0F, 8F, 8F, 0.001F);

		root.addOrReplaceChild(
			name,
			cubeListBuilder,
			PartPose.offsetAndRotation(xPos, 8F, zPos, 0F, rot, Mth.PI)
		);
	}

	@Override
	public void setupAnim(SculkSensorRenderState renderState) {
		super.setupAnim(renderState);
		final float tendrilXRot = renderState.tendrilAnimation * (float) (Math.cos(renderState.ageInTicks * 2.25F) * Math.PI * 0.1F);
		this.tendril1.xRot = tendrilXRot;
		this.tendril2.xRot = tendrilXRot;
		this.tendril3.xRot = tendrilXRot;
		this.tendril4.xRot = tendrilXRot;
	}
}
