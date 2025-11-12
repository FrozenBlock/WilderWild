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

import net.frozenblock.wilderwild.client.renderer.entity.state.ButterflyRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ButterflyModel extends EntityModel<ButterflyRenderState> {
	private static final float FLAP_SPEED = 1.375F;
	private static final float FLAP_HEIGHT = Mth.PI * 0.475F;
	private static final float FLAP_ROW_OFFSET = -3F;
	private static final float FLAP_ROW_WIDTH = Mth.PI * 0.025F;
	private static final float FLAP_TILT_OFFSET = -6F;
	private static final float FLAP_TILT_WIDTH = Mth.PI * 0.055F;
	private static final float BODY_X_ROT_SPEED = 1.5F;
	private static final float BODY_X_ROT_HEIGHT = Mth.PI * 0.015F;
	private static final float BODY_Y_ROT_SPEED = 2F;
	private static final float BODY_Y_ROT_HEIGHT = Mth.PI * 0.015F;
	private static final float BODY_HEIGHT_SPEED = 0.375F;
	private static final float BODY_HEIGHT = Mth.PI * 0.075F;
	private static final float BODY_HEIGHT_OFFSET = -4.5F;
	private static final float IDLE_WING_X_ROT = 15F * Mth.DEG_TO_RAD;
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart wings;
	private final ModelPart leftWing;
	private final ModelPart rightWing;
	private final ModelPart antennae;
	private final ModelPart leftAntenna;
	private final ModelPart rightAntenna;

	public ButterflyModel(@NotNull ModelPart root) {
		super(root);
		this.root = root;
		this.body = root.getChild("body");
		this.wings = this.body.getChild("wings");
		this.leftWing = this.wings.getChild("left_wing");
		this.rightWing = this.wings.getChild("right_wing");
		this.antennae = this.body.getChild("antennae");
		this.leftAntenna = this.antennae.getChild("left_antenna");
		this.rightAntenna = this.antennae.getChild("right_antenna");
	}

	public static @NotNull LayerDefinition createBodyLayer() {
		final MeshDefinition meshdefinition = new MeshDefinition();
		final PartDefinition root = meshdefinition.getRoot();

		final PartDefinition body = root.addOrReplaceChild(
			"body",
			CubeListBuilder.create()
				.texOffs(6, 9)
				.addBox(-0.5F, -1F, -2F, 1F, 1F, 4F, new CubeDeformation(0F)),
			PartPose.offset(0F, 24F, 0F)
		);

		final PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0F, 0F, 0F));
		wings.addOrReplaceChild(
			"left_wing",
			CubeListBuilder.create()
				.texOffs(0, -8)
				.addBox(0F, -4F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)),
			PartPose.offsetAndRotation(0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);
		wings.addOrReplaceChild(
			"right_wing",
			CubeListBuilder.create()
				.texOffs(0, -4)
				.addBox(0F, 0F, -4F, 0F, 4F, 8F, new CubeDeformation(0.005F)),
			PartPose.offsetAndRotation(-0.25F, -1F, 0F, 0F, 0F, 1.5708F)
		);

		final PartDefinition antennae = body.addOrReplaceChild("antennae", CubeListBuilder.create(), PartPose.offset(0F, -1F, -2F));
		antennae.addOrReplaceChild(
			"left_antenna", CubeListBuilder.create()
				.texOffs(0, 7)
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F)),
			PartPose.offset(0.5F, 0F, 0F)
		);
		antennae.addOrReplaceChild(
			"right_antenna", CubeListBuilder.create()
				.texOffs(0, 9)
				.mirror()
				.addBox(0F, -2F, -2F, 0F, 2F, 2F, new CubeDeformation(0.005F))
				.mirror(false),
			PartPose.offset(-0.5F, 0F, 0F)
		);

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	@Override
	public void setupAnim(@NotNull ButterflyRenderState renderState) {
		super.setupAnim(renderState);

		final float animation = (renderState.ageInTicks * 0.75F) + (renderState.walkAnimationPos * 0.85F);
		final float movementDelta = Math.max(1F - renderState.groundProgress, 0.1F);
		final float lengthMultiplier = Math.max(movementDelta * 0.75F, 0.4F);
		final float flapLengthMultiplier = lengthMultiplier * (1F - (renderState.downProgress * 0.5F));

		final float verticalFlap = Mth.lerp(movementDelta, -Mth.HALF_PI * 1.025F, (Mth.cos(animation * FLAP_SPEED) * FLAP_HEIGHT * flapLengthMultiplier) + (-20F * Mth.DEG_TO_RAD));
		this.leftWing.zRot += verticalFlap;
		this.rightWing.zRot -= verticalFlap;

		final float xRot = Mth.lerp(movementDelta, IDLE_WING_X_ROT, Mth.cos((animation + FLAP_ROW_OFFSET) * FLAP_SPEED) * FLAP_ROW_WIDTH * flapLengthMultiplier);
		this.leftWing.xRot -= xRot;
		this.rightWing.xRot += xRot;

		final float yRot = Mth.lerp(movementDelta, 0F, Mth.cos((animation + FLAP_TILT_OFFSET) * FLAP_SPEED) * FLAP_TILT_WIDTH * flapLengthMultiplier);
		this.leftWing.yRot -= yRot;
		this.rightWing.yRot -= yRot;

		final float walkAnimation = renderState.walkAnimationPos * 9F;
		final float bodyXRot = Mth.lerp(movementDelta, Mth.cos(walkAnimation * BODY_X_ROT_SPEED) * BODY_X_ROT_HEIGHT, 0F);
		this.body.xRot += bodyXRot;

		final float bodyYRot = Mth.lerp(movementDelta, Mth.cos(walkAnimation * BODY_Y_ROT_SPEED) * BODY_Y_ROT_HEIGHT, 0F);
		this.body.yRot += bodyYRot;

		this.body.xRot -= renderState.flyingXRot * Mth.HALF_PI * 0.5F;

		final float bodyY = Mth.lerp(movementDelta, 0F, Mth.cos((animation + BODY_HEIGHT_OFFSET) * BODY_HEIGHT_SPEED) * BODY_HEIGHT * lengthMultiplier);
		this.body.y -= bodyY;
	}
}
