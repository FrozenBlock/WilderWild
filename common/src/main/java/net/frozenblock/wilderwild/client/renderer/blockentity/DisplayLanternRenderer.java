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
import it.unimi.dsi.fastutil.HashCommon;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.client.renderer.blockentity.state.DisplayLanternRenderState;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@ClientOnly
public class DisplayLanternRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T, DisplayLanternRenderState> {
	private static final float ITEM_GROUND_SCALE = 0.5F;
	private static final float ITEM_SCALE = 0.7F;
	private static final float ITEM_DISPLAY_LANTERN_SCALE = ITEM_GROUND_SCALE * ITEM_SCALE;
	private static final float ITEM_GROUND_Y_OFFSET = 2F / 16F;
	private static final float ITEM_Y_OFFSET = 0.125F + ITEM_GROUND_Y_OFFSET;
	private static final float ITEM_HANGING_Y_OFFSET = 0.25F + ITEM_GROUND_Y_OFFSET;
	private final ItemModelResolver itemModelResolver;

	public DisplayLanternRenderer(Context context) {
		this.itemModelResolver = context.itemModelResolver();
	}

	@Override
	public void submit(
		DisplayLanternRenderState state,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState camera
	) {
		if (!state.item.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5F, state.isHanging ? ITEM_HANGING_Y_OFFSET : ITEM_Y_OFFSET, 0.5F);
			poseStack.scale(ITEM_DISPLAY_LANTERN_SCALE, ITEM_DISPLAY_LANTERN_SCALE, ITEM_DISPLAY_LANTERN_SCALE);
			poseStack.rotate(Axis.YP.rotation(state.age / 20F));

			state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

			poseStack.popPose();
		}

		for (DisplayLanternRenderState.Occupant occupant : state.occupants) {
			FireflyRenderer.submitFireflyWithoutRenderState(
				poseStack,
				submitNodeCollector,
				camera.orientation,
				occupant.color(),
				occupant.calcColor(),
				1F,
				occupant.x(),
				occupant.y(),
				occupant.z(),
				state.lightCoords,
				OverlayTexture.NO_OVERLAY
			);
		}
	}

	@Override
	public DisplayLanternRenderState createRenderState() {
		return new DisplayLanternRenderState();
	}

	@Override
	public void extractRenderState(
		T displayLantern,
		DisplayLanternRenderState renderState,
		float partialTicks,
		Vec3 cameraPos,
		@Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress
	) {
		BlockEntityRenderer.super.extractRenderState(displayLantern, renderState, partialTicks, cameraPos, breakProgress);
		renderState.age = displayLantern.age + partialTicks;
		renderState.isHanging = displayLantern.isHanging();
		this.itemModelResolver.updateForTopItem(
			renderState.item,
			displayLantern.getItem(),
			ItemDisplayContext.FIXED,
			displayLantern.level(),
			displayLantern,
			HashCommon.long2int(displayLantern.getBlockPos().asLong())
		);
		renderState.extractOccupants(displayLantern, partialTicks);
	}

}
