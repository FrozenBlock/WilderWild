/*
 * Copyright 2024 FrozenBlock
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

import net.frozenblock.wilderwild.entity.Butterfly;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ButterflyModel<T extends Butterfly> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wings;
	private final ModelPart left_wing;
	private final ModelPart right_wing;
	private final ModelPart antennae;
	private final ModelPart left_antenna;
	private final ModelPart right_antenna;

	public ButterflyModel(@NotNull ModelPart root) {
		this.root = root;
		this.body = root.getChild("body");
		this.wings = this.body.getChild("wings");
		this.left_wing = this.wings.getChild("left_wing");
		this.right_wing = this.wings.getChild("right_wing");
		this.antennae = this.body.getChild("antennae");
		this.left_antenna = this.antennae.getChild("left_antenna");
		this.right_antenna = this.antennae.getChild("right_antenna");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(6, 9)
				.addBox(-0.5F, -1F, -2F, 1F, 1F, 4F, new CubeDeformation(0F)), PartPose.offset(0F, 24F, 0F)
		);

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
		PartDefinition left_wing = wings.addOrReplaceChild(
			"left_wing",
			CubeListBuilder.create()
				.texOffs(0, -8)
				.addBox(0F, -4F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);
		PartDefinition right_wing = wings.addOrReplaceChild(
			"right_wing",
			CubeListBuilder.create()
				.texOffs(0, -4)
				.addBox(0F, 0F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(-0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);

		PartDefinition antennae = body.addOrReplaceChild("antennae", CubeListBuilder.create(), PartPose.offset(0F, -1F, -2F));
		PartDefinition left_antenna = antennae.addOrReplaceChild(
			"left_antenna", CubeListBuilder.create()
				.texOffs(0, 7)
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F)), PartPose.offset(0.5F, 0F, 0F)
		);
		PartDefinition right_antenna = antennae.addOrReplaceChild(
			"right_antenna", CubeListBuilder.create()
				.texOffs(0, 9)
				.mirror()
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F)).mirror(false), PartPose.offset(-0.5F, 0F, 0F)
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	private static final float FLAP_SPEED = 1.375F;
	private static final float FLAP_HEIGHT = Mth.PI * 0.35F;
	private static final float FLAP_ROW_OFFSET = -3F;
	private static final float FLAP_ROW_WIDTH = Mth.PI * 0.025F;
	private static final float FLAP_TILT_OFFSET = -6F;
	private static final float FLAP_TILT_WIDTH = Mth.PI * 0.055F;

	private static final float BODY_ROT_SPEED = 0.675F;
	private static final float BODY_ROT_HEIGHT = Mth.PI * 0.025F;

	private static final float BODY_X_ROT_SPEED = 1.5F;
	private static final float BODY_X_ROT_HEIGHT = Mth.PI * 0.015F;

	private static final float BODY_Y_ROT_SPEED = 2F;
	private static final float BODY_Y_ROT_HEIGHT = Mth.PI * 0.015F;

	private static final float BODY_HEIGHT_SPEED = 0.375F;
	private static final float BODY_HEIGHT = Mth.PI * 0.075F;
	private static final float BODY_HEIGHT_OFFSET = -4.5F;

	private static final float IDLE_WING_X_ROT = 15F * Mth.DEG_TO_RAD;

	@Override
	public void setupAnim(@NotNull Butterfly butterfly, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);
		float partialTick = ageInTicks - butterfly.tickCount;

		float animation = (ageInTicks * 0.75F) + (limbSwing * 0.85F);
		float movementDelta = 1F - butterfly.getGroundProgress(partialTick);
		float lengthMultiplier = Math.max(movementDelta * 0.75F, 0.4F);
		float flapLengthMultiplier = lengthMultiplier * (1F - (butterfly.getDownProgress(partialTick) * 0.5F));

		float verticalFlap = Mth.lerp(movementDelta, -Mth.HALF_PI * 1.025F, (Mth.cos(animation * FLAP_SPEED) * FLAP_HEIGHT * flapLengthMultiplier) + (-20F * Mth.DEG_TO_RAD));
		this.left_wing.zRot += verticalFlap;
		this.right_wing.zRot -= verticalFlap;

		float xRot = Mth.lerp(movementDelta, IDLE_WING_X_ROT, Mth.cos((animation + FLAP_ROW_OFFSET) * FLAP_SPEED) * FLAP_ROW_WIDTH * flapLengthMultiplier);
		this.left_wing.xRot -= xRot;
		this.right_wing.xRot += xRot;

		float yRot = Mth.lerp(movementDelta, 0F, Mth.cos((animation + FLAP_TILT_OFFSET) * FLAP_SPEED) * FLAP_TILT_WIDTH * flapLengthMultiplier);
		this.left_wing.yRot -= yRot;
		this.right_wing.yRot -= yRot;

		float walkAnimation = limbSwing * 9F;
		float bodyXRot = Mth.lerp(movementDelta, Mth.cos(walkAnimation * BODY_X_ROT_SPEED) * BODY_X_ROT_HEIGHT, 0F);
		this.body.xRot += bodyXRot;

		float bodyYRot = Mth.lerp(movementDelta, Mth.cos(walkAnimation * BODY_Y_ROT_SPEED) * BODY_Y_ROT_HEIGHT, 0F);
		this.body.yRot += bodyYRot;

		this.body.xRot -= butterfly.getFlyingXRot(partialTick) * Mth.HALF_PI * 0.5F;

		float bodyY = Mth.lerp(movementDelta, 0F, Mth.cos((animation + BODY_HEIGHT_OFFSET) * BODY_HEIGHT_SPEED) * BODY_HEIGHT * lengthMultiplier);
		this.body.y -= bodyY;
	}

	@Override
	public @NotNull ModelPart root() {
		return this.root;
	}
}
