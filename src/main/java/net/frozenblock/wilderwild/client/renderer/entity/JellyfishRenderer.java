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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.client.impl.ClientWindManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.JellyfishModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.JellyfishRenderState;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishRenderState, JellyfishModel> {
	private static final Identifier WHITE_TEXTURE = WWConstants.id("textures/entity/jellyfish/jellyfish_white.png");

	public JellyfishRenderer(Context context) {
		super(context, new JellyfishModel(context.bakeLayer(WWModelLayers.JELLYFISH)), 0.3F);
	}

	@Override
	protected void setupRotations(JellyfishRenderState renderState, PoseStack poseStack, float rotationYaw, float g) {
		super.setupRotations(renderState, poseStack, rotationYaw, g);
		poseStack.translate(0F, -1F * renderState.ageScale, 0F);
	}

	@Override
	protected int getModelTint(JellyfishRenderState renderState) {
		if (renderState.isRGB) return ARGB.color(
			(int) (Mth.clamp(Math.abs((renderState.windTime % 6) - 3) - 1, 0, 1) * 255),
			(int) (Mth.clamp(Math.abs(((renderState.windTime - 2) % 6) - 3) - 1, 0, 1) * 255),
			(int) (Mth.clamp(Math.abs(((renderState.windTime - 4) % 6) - 3) - 1, 0, 1) * 255)
		);
		return super.getModelTint(renderState);
	}

	@Override
	protected void scale(JellyfishRenderState renderState, PoseStack poseStack) {
		super.scale(renderState, poseStack);
		poseStack.scale(0.8F, 0.8F, 0.8F);
		poseStack.scale(renderState.jellyScale, renderState.jellyScale, renderState.jellyScale);
	}

	@Override
	protected int getBlockLightLevel(Jellyfish jellyfish, BlockPos pos) {
		return 15;
	}

	@Override
	public Identifier getTextureLocation(JellyfishRenderState renderState) {
		if (renderState.isRGB) return WHITE_TEXTURE;
		return renderState.variant.resourceTexture().texturePath();
	}

	@Override
	public JellyfishRenderState createRenderState() {
		return new JellyfishRenderState();
	}

	@Override
	public void extractRenderState(Jellyfish jellyfish, JellyfishRenderState renderState, float partialTick) {
		super.extractRenderState(jellyfish, renderState, partialTick);

		renderState.tickCount = jellyfish.tickCount;
		renderState.isRGB = jellyfish.isRGB();
		renderState.variant = jellyfish.getVariantForRendering();
		renderState.windTime = (ClientWindManager.time + partialTick) * 0.05F;

		renderState.jellyXRot = -(jellyfish.xRot1 + partialTick * (jellyfish.xBodyRot - jellyfish.xRot1)) * Mth.DEG_TO_RAD;
		renderState.tentXRot = -(jellyfish.xRot6 + partialTick * (jellyfish.xRot5 - jellyfish.xRot6)) * Mth.DEG_TO_RAD;
		renderState.armXRot = -(jellyfish.xRot9 + partialTick * (jellyfish.xRot8 - jellyfish.xRot9)) * Mth.DEG_TO_RAD;
		renderState.jellyScale = (jellyfish.prevScale + partialTick * (jellyfish.scale - jellyfish.prevScale)) * jellyfish.getAgeScale();
	}
}
