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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.entity.client.api.renderer.entity.AbstractBlockLikeMobRenderer;
import net.frozenblock.lib.renderer.model.FrozenLibModelLayers;
import net.frozenblock.lib.renderer.model.NoOpModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.TumbleweedRenderState;
import net.frozenblock.wilderwild.entity.block.Tumbleweed;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;

@ClientOnly
public class TumbleweedRenderer extends AbstractBlockLikeMobRenderer<Tumbleweed, TumbleweedRenderState, NoOpModel<TumbleweedRenderState>> {
	private static final float ITEM_GROUND_SCALE = 0.5F;
	private static final float ITEM_GROUND_Y_OFFSET = 2F / 16F;
	private static final float ITEM_Y_OFFSET = 0.333F + ITEM_GROUND_Y_OFFSET;
	private final ItemModelResolver itemModelResolver;

	public TumbleweedRenderer(Context context) {
		super(context, new NoOpModel<>(context.bakeLayer(FrozenLibModelLayers.NO_MODEL)));
		this.itemModelResolver = context.getItemModelResolver();
	}

	@Override
	public void submitExtras(TumbleweedRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
		if (state.item.isEmpty()) return;

		poseStack.pushPose();
		poseStack.translate(0.5F, ITEM_Y_OFFSET, 0.5F);
		poseStack.scale(ITEM_GROUND_SCALE, ITEM_GROUND_SCALE, ITEM_GROUND_SCALE);
		state.item.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
		poseStack.popPose();
	}

	@Override
	public TumbleweedRenderState createRenderState() {
		return new TumbleweedRenderState();
	}

	@Override
	public void extractRenderState(Tumbleweed tumbleweed, TumbleweedRenderState state, float partialTicks) {
		super.extractRenderState(tumbleweed, state, partialTicks);
		state.hasRedOverlay = false;
		this.itemModelResolver.updateForLiving(state.item, tumbleweed.getVisibleItem(), ItemDisplayContext.FIXED, tumbleweed);
	}
}
