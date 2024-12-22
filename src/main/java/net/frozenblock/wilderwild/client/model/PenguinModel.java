/*
 * Copyright 2023-2024 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.model;

import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class PenguinModel<T extends Penguin> extends HierarchicalModel<T> {

	private final ModelPart root;

	public PenguinModel(@NotNull ModelPart root) {
		this.root = root;
	}

	@NotNull
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition legs = partdefinition.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F));
		PartDefinition left_leg = legs.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(86, 44).addBox(-1F, -18F, -1F, 2F, 18F, 2F), PartPose.offset(-3F, 16F, 0F));
		PartDefinition left_foot = left_leg.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(88, 58).addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F)), PartPose.offset(0F, 0F, -1F));
		PartDefinition right_leg = legs.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(86, 44).mirror().addBox(-1F, -18F, -1F, 2F, 18F, 2F).mirror(false), PartPose.offset(3F, 16.0F, 0F));
		PartDefinition right_foot = right_leg.addOrReplaceChild("right_foot", CubeListBuilder.create().texOffs(88, 58).mirror().addBox(-2F, 0F, 0F, 4F, 0F, 6F, new CubeDeformation(0.005F)).mirror(false), PartPose.offset(0F, 0F, -1.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 31).addBox(-6F, -12F, -7F, 12F, 14F, 19F)
			.texOffs(24, 31).addBox(-6F, 0F, -7F, 12F, 0F, 19F), PartPose.offsetAndRotation(0F, 8F, 4F, 0F, Mth.PI, 0F));
		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(0, 0).addBox(-6F, -12F, -7F, 12F, 12F, 19F, new CubeDeformation(0.5F)), PartPose.ZERO);
		PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(98, 22).addBox(-1.0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F))
			.texOffs(98, 44).addBox(-1F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F)), PartPose.offset(-6F, -4F, 5F));
		PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(98, 22).mirror().addBox(0F, -4F, -14F, 1F, 8F, 14F, new CubeDeformation(-0.001F)).mirror(false)
			.texOffs(98, 44).mirror().addBox(0F, 1F, -14F, 1F, 0F, 14F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offset(6F, -4F, 5F));

		PartDefinition neck_base = body.addOrReplaceChild("neck_base", CubeListBuilder.create().texOffs(81, 9).addBox(-4F, -5F, 0F, 8F, 8F, 6F), PartPose.offset(0F, -4F, 12F));
		PartDefinition neck = neck_base.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(65, 0).addBox(-2F, -21F, 0F, 4F, 21F, 4F), PartPose.offset(0F, 1F, 4F));
		PartDefinition beak = neck.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(81, 1).addBox(-2F, -1F, 0F, 4F, 2F, 3F), PartPose.offset(0F, -18F, 4F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(62, 46).addBox(-3F, 0F, -6F, 6F, 12F, 6F)
			.texOffs(62, 40).addBox(-3F, 10F, -6F, 6F, 0F, 6F), PartPose.offset(0F, -7F, -7F));

		return LayerDefinition.create(meshdefinition, 128, 64);
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		limbSwing *= 1.65F;
		limbSwingAmount = Math.min(limbSwingAmount * 1.5F, 1F);
	}

	@Override
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
	}

	@NotNull
	@Override
	public ModelPart root() {
		return this.root;
	}

}
