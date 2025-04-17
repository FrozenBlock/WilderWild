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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TumbleweedModel<T extends Tumbleweed> extends HierarchicalModel<T> {
	private final ModelPart root;
	private final ModelPart tumbleweed;
	private float partialTick;
	private float prevPitch;
	private float pitch;
	private float prevRoll;
	private float roll;
	private float prevTumble;
	private float tumble;

	public TumbleweedModel(@NotNull ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
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
	public void prepareMobModel(@NotNull T entity, float limbSwing, float limbSwingAmount, float partialTick) {
		super.prepareMobModel(entity, limbSwing, limbSwingAmount, partialTick);
		this.partialTick = partialTick;
		this.prevPitch = entity.prevPitch;
		this.pitch = entity.pitch;
		this.prevRoll = entity.prevRoll;
		this.roll = entity.roll;
		this.prevTumble = entity.prevTumble;
		this.tumble = entity.tumble;
	}

	@Override
	public void setupAnim(@NotNull T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) {
			this.root.xRot = (Mth.lerp(this.partialTick, this.prevTumble, this.tumble)) * Mth.DEG_TO_RAD;
		} else {
			this.root.xRot = 0F;
		}
	}

	@Override
	public void renderToBuffer(@NotNull PoseStack poseStack, @NotNull VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		poseStack.pushPose();
		poseStack.translate(0D, 1.3D, 0D);
		if (!WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) {
			poseStack.pushPose();
			Quaternionf quaternionf = new Quaternionf().rotationXYZ(
				Mth.lerp(this.partialTick, this.prevPitch, this.pitch) * Mth.DEG_TO_RAD,
				0F,
				Mth.lerp(this.partialTick, this.prevRoll, this.roll) * Mth.DEG_TO_RAD
			);
			poseStack.mulPose(quaternionf);
			this.root.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
			poseStack.popPose();
		} else {
			this.root.render(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, color);
		}
		poseStack.popPose();
	}

	@Override
	@NotNull
	public ModelPart root() {
		return this.root;
	}
}
