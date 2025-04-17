/*
 * Copyright 2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.TumbleweedRenderState;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedModel extends EntityModel<TumbleweedRenderState> {
	private final ModelPart tumbleweed;

	public TumbleweedModel(@NotNull ModelPart root) {
		super(root, RenderType::entityCutoutNoCull);
		this.tumbleweed = root.getChild("tumbleweed");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		meshdefinition.getRoot().addOrReplaceChild("tumbleweed", CubeListBuilder.create()
			.texOffs(0, 28).addBox(-6F, -6F, -6F, 12F, 12F, 12F)
			.texOffs(0, 0).addBox(-7F, -7F, -7F, 14F, 14F, 14F), PartPose.ZERO);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(TumbleweedRenderState renderState) {
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) {
			this.root.xRot = renderState.tumbleRot;
		} else {
			this.root.xRot = renderState.pitch;
			this.root.zRot = renderState.roll;
		}
	}
}
