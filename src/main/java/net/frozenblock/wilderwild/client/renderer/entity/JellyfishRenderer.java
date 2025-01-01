/*
 * Copyright 2023-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.JellyfishModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.JellyfishRenderState;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishRenderState, JellyfishModel> {
	private static final ResourceLocation WHITE_TEXTURE = WWConstants.id("textures/entity/jellyfish/jellyfish_white.png");

	public JellyfishRenderer(@NotNull Context context) {
		super(context, new JellyfishModel(context.bakeLayer(WWModelLayers.JELLYFISH)), 0.3F);
	}

	@Override
	protected void setupRotations(@NotNull JellyfishRenderState renderState, @NotNull PoseStack poseStack, float rotationYaw, float g) {
		super.setupRotations(renderState, poseStack, rotationYaw, g);
		poseStack.translate(0F, -1F * renderState.ageScale, 0F);
	}

	@Override
	protected int getModelTint(@NotNull JellyfishRenderState renderState) {
		return !renderState.isRGB ? super.getModelTint(renderState)
			: ARGB.color(
			(int) (Mth.clamp(Math.abs((renderState.windTime % 6) - 3) - 1, 0, 1) * 255),
			(int) (Mth.clamp(Math.abs(((renderState.windTime - 2) % 6) - 3) - 1, 0, 1) * 255),
			(int) (Mth.clamp(Math.abs(((renderState.windTime - 4) % 6) - 3) - 1, 0, 1) * 255)
		);
	}

	@Override
	protected void scale(JellyfishRenderState renderState, PoseStack poseStack) {
		super.scale(renderState, poseStack);
		poseStack.scale(0.8F, 0.8F, 0.8F);
		poseStack.scale(renderState.jellyScale, renderState.jellyScale, renderState.jellyScale);
	}

	@Override
	protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
		return 15;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull JellyfishRenderState renderState) {
		if (renderState.isRGB) return WHITE_TEXTURE;
		return renderState.variant.texture();
	}

	@Override
	@NotNull
	public JellyfishRenderState createRenderState() {
		return new JellyfishRenderState();
	}

	@Override
	public void extractRenderState(Jellyfish entity, JellyfishRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);

		renderState.tickCount = entity.tickCount;
		renderState.isRGB = entity.isRGB();
		renderState.variant = entity.getVariantForRendering();
		renderState.windTime = (ClientWindManager.time + partialTick) * 0.05F;

		renderState.jellyXRot = -(entity.xRot1 + partialTick * (entity.xBodyRot - entity.xRot1)) * Mth.DEG_TO_RAD;
		renderState.tentXRot = -(entity.xRot6 + partialTick * (entity.xRot5 - entity.xRot6)) * Mth.DEG_TO_RAD;
		renderState.jellyScale = (entity.prevScale + partialTick * (entity.scale - entity.prevScale)) * entity.getAgeScale();
	}
}
