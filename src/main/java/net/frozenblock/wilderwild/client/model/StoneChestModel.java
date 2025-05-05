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
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class StoneChestModel extends Model {
	private static final String BASE = "bottom";
	private static final String LID = "lid";
	private final ModelPart lid;

	public StoneChestModel(ModelPart modelPart) {
		super(modelPart, RenderType::entitySolid);
		this.lid = modelPart.getChild(LID);
	}

	@NotNull
	public static LayerDefinition createSingleBodyLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1F, 0F, 1F, 14F, 12F, 14F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1F, 0F, 0F, 14F, 3F, 14F), PartPose.offset(0F, 11F, 1F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static LayerDefinition createDoubleBodyRightLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(1F, 0F, 1F, 15F, 12F, 14F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(1F, 0F, 0F, 15F, 3F, 14F), PartPose.offset(0F, 11F, 1F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	@NotNull
	public static LayerDefinition createDoubleBodyLeftLayer() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		modelPartData.addOrReplaceChild(BASE, CubeListBuilder.create().texOffs(0, 17).addBox(0F, 0F, 1F, 15F, 12F, 14F), PartPose.ZERO);
		modelPartData.addOrReplaceChild(LID, CubeListBuilder.create().texOffs(0, 0).addBox(0F, 0F, 0F, 15F, 3F, 14F), PartPose.offset(0F, 11F, 1F));
		return LayerDefinition.create(modelData, 64, 64);
	}

	public void setupAnim(float liftProgress) {
		this.lid.xRot = -(liftProgress * Mth.HALF_PI);
	}
}
