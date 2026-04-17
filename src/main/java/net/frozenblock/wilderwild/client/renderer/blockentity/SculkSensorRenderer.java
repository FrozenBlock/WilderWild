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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.block.entity.impl.SculkSensorInterface;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.object.sculksensor.SculkSensorModel;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.SculkSensorRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.level.block.entity.SculkSensorBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SculkSensorRenderer<T extends SculkSensorBlockEntity> implements BlockEntityRenderer<T, SculkSensorRenderState> {
	private static final RenderType ACTIVE_SENSOR_LAYER = RenderTypes.entityCutout(WWConstants.id("textures/entity/sculk_sensor/active.png"));
	private final SculkSensorModel model;

	public SculkSensorRenderer(Context context) {
		this.model = new SculkSensorModel(context.bakeLayer(WWModelLayers.SCULK_SENSOR));
	}

	@Override
	public void submit(
		SculkSensorRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState camera
	) {
		if (!renderState.active) return;

		poseStack.rotateAround(Axis.YP.rotationDegrees(renderState.blockYRot), 0.5F, 0.5F, 0.5F);
		collector.submitModel(
			this.model,
			renderState,
			poseStack,
			ACTIVE_SENSOR_LAYER,
			renderState.lightCoords,
			OverlayTexture.NO_OVERLAY,
			-1,
			null,
			0,
			renderState.breakProgress
		);
	}

	@Override
	public SculkSensorRenderState createRenderState() {
		return new SculkSensorRenderState();
	}

	@Override
	public void extractRenderState(
		T sculkSensor,
		SculkSensorRenderState renderState,
		float partialTicks,
		Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress
	) {
		BlockEntityRenderer.super.extractRenderState(sculkSensor, renderState, partialTicks, cameraPos, breakProgress);

		if (!WWConstants.MC_LIVE_TENDRILS || !(sculkSensor instanceof SculkSensorInterface sculkSensorInterface) || !sculkSensorInterface.wilderWild$isActive()) {
			renderState.active = false;
			return;
		}

		renderState.ageInTicks = sculkSensorInterface.wilderWild$getAgeInTicks(partialTicks);
		renderState.active = true;
		renderState.blockYRot = -sculkSensorInterface.wilderWild$getFacing().toYRot();
		renderState.tendrilAnimation = sculkSensorInterface.wilderWild$getTendrilAnimation(partialTicks);
	}
}
