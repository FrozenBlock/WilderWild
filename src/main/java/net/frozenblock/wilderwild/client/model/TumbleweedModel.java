/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
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
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedModel extends EntityModel<TumbleweedRenderState> {
	private final ModelPart body;
	private final ModelPart tumbleweed;

	public TumbleweedModel(@NotNull ModelPart root) {
		super(root, RenderType::entityCutoutNoCull);
		this.body = root.getChild("body");
		this.tumbleweed = this.body.getChild("tumbleweed");
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();

		PartDefinition body = meshdefinition.getRoot().addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(0F, -3.8F, 0F)
		);

		body.addOrReplaceChild(
			"tumbleweed",
			CubeListBuilder.create()
				.texOffs(0, 28).addBox(-6F, -6F, -6F, 12F, 12F, 12F)
				.texOffs(0, 0).addBox(-7F, -7F, -7F, 14F, 14F, 14F),
			PartPose.ZERO
		);

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(TumbleweedRenderState renderState) {
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) {
			this.tumbleweed.xRot = renderState.tumbleRot;
		} else {
			this.tumbleweed.xRot = -renderState.pitch;
			this.tumbleweed.zRot = renderState.roll;
		}
	}
}
