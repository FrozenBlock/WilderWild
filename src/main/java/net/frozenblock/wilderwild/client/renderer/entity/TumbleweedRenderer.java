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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.TumbleweedModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.TumbleweedRenderState;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends MobRenderer<Tumbleweed, TumbleweedRenderState, TumbleweedModel> {
	private static final ResourceLocation TUMBLEWEED_LOCATION = WWConstants.id("textures/entity/tumbleweed/tumbleweed.png");
	private static final ResourceLocation CANNONBALL_LOCATION = WWConstants.id("textures/entity/tumbleweed/cannonball.png");
	private final ItemModelResolver itemModelResolver;

	public TumbleweedRenderer(@NotNull Context context) {
		super(context, new TumbleweedModel(context.bakeLayer(WWModelLayers.TUMBLEWEED)), 0.6F);
		this.itemModelResolver = context.getItemModelResolver();
	}

	@Override
	public void submit(
		@NotNull TumbleweedRenderState renderState,
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		@NotNull CameraRenderState cameraRenderState
	) {
		super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);

		if (renderState.item.isEmpty()) return;

		poseStack.pushPose();
		poseStack.translate(renderState.itemX, 0.4375D, renderState.itemZ);
		Quaternionf quaternionf = new Quaternionf().rotationXYZ(
			renderState.pitch * Mth.DEG_TO_RAD,
			0F,
			renderState.roll * Mth.DEG_TO_RAD
		);
		poseStack.mulPose(quaternionf);
		renderState.item.submit(poseStack, submitNodeCollector, 1, OverlayTexture.NO_OVERLAY, renderState.outlineColor);
		poseStack.popPose();
	}

	@Override
	protected void setupRotations(@NotNull TumbleweedRenderState renderState, @NotNull PoseStack poseStack, float rotationYaw, float scale) {
		poseStack.translate(0D, -1.3D, 0D);
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) poseStack.mulPose(Axis.YP.rotationDegrees(180F - rotationYaw));
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull TumbleweedRenderState renderState) {
		return renderState.isCannonball ? CANNONBALL_LOCATION : TUMBLEWEED_LOCATION;
	}

	@Override
	@NotNull
	public TumbleweedRenderState createRenderState() {
		return new TumbleweedRenderState();
	}

	@Override
	public void extractRenderState(@NotNull Tumbleweed entity, @NotNull TumbleweedRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.tumbleRot = Mth.lerp(partialTick, entity.prevTumble, entity.tumble) * Mth.DEG_TO_RAD;
		renderState.itemX = entity.itemX;
		renderState.itemZ = entity.itemZ;
		renderState.pitch = -Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * Mth.DEG_TO_RAD;
		renderState.roll = Mth.lerp(partialTick, entity.prevRoll, entity.roll) * Mth.DEG_TO_RAD;
		renderState.isCannonball = entity.isCannonball();
		this.itemModelResolver.updateForLiving(renderState.item, entity.getVisibleItem(), ItemDisplayContext.GROUND, entity);
		renderState.level = entity.level();
	}
}
