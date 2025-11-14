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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import it.unimi.dsi.fastutil.HashCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.DisplayLanternRenderState;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class DisplayLanternRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T, DisplayLanternRenderState> {
	private final ItemModelResolver itemModelResolver;

	public DisplayLanternRenderer(@NotNull Context ctx) {
		ctx.bakeLayer(WWModelLayers.DISPLAY_LANTERN);
		this.itemModelResolver = ctx.itemModelResolver();
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return LayerDefinition.create(new MeshDefinition(), 16, 16);
	}

	@Override
	public void submit(
		@NotNull DisplayLanternRenderState renderState,
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		@NotNull CameraRenderState cameraRenderState
	) {
		if (!renderState.item.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5F, renderState.isHanging ? 0.25F : 0.125F, 0.5F);
			poseStack.scale(0.7F, 0.7F, 0.7F);
			poseStack.mulPose(Axis.YP.rotation(renderState.age / 20F));

			renderState.item.submit(poseStack, submitNodeCollector, renderState.lightCoords, OverlayTexture.NO_OVERLAY, 0);

			poseStack.popPose();
		}
		for (DisplayLanternRenderState.Occupant occupant : renderState.occupants) {
			FireflyRenderer.submitFireflyWithoutRenderState(
				poseStack,
				submitNodeCollector,
				cameraRenderState.orientation,
				occupant.color,
				occupant.calcColor,
				1F,
				occupant.x,
				occupant.y,
				occupant.z,
				renderState.lightCoords,
				OverlayTexture.NO_OVERLAY
			);
		}
	}

	@Override
	public @NotNull DisplayLanternRenderState createRenderState() {
		return new DisplayLanternRenderState();
	}

	@Override
	public void extractRenderState(
		@NotNull T displayLantern,
		@NotNull DisplayLanternRenderState renderState,
		float partialTicks,
		@NotNull Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay
	) {
		BlockEntityRenderer.super.extractRenderState(displayLantern, renderState, partialTicks, cameraPos, crumblingOverlay);
		renderState.age = displayLantern.age + partialTicks;
		renderState.isHanging = displayLantern.isHanging();
		this.itemModelResolver.updateForTopItem(
			renderState.item,
			displayLantern.getItem(),
			ItemDisplayContext.GROUND,
			displayLantern.level(), displayLantern,
			HashCommon.long2int(displayLantern.getBlockPos().asLong())
		);
		renderState.extractOccupants(displayLantern, partialTicks);
	}

}
