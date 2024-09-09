/*
 * Copyright 2023-2024 FrozenBlock
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

package net.frozenblock.wilderwild.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.AncientHornVibration;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.frozenblock.wilderwild.entity.render.renderer.state.AncientHornVibrationRenderState;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer extends EntityRenderer<AncientHornVibration, AncientHornVibrationRenderState> {
	private static final ResourceLocation TEXTURE = WWConstants.id("textures/entity/ancient_horn_projectile.png");
	private final AncientHornProjectileModel model;

	public AncientHornProjectileRenderer(@NotNull EntityRendererProvider.Context context) {
		super(context);
		this.model = new AncientHornProjectileModel(context.bakeLayer(WWModelLayers.ANCIENT_HORN_PROJECTILE_LAYER));
	}

	@Override
	public void extractRenderState(AncientHornVibration ancientHornVibration, AncientHornVibrationRenderState renderState, float partialTick) {
		super.extractRenderState(ancientHornVibration, renderState, partialTick);
		renderState.partialTick = partialTick;
		renderState.boundingBoxMultiplier = ancientHornVibration.getBoundingBoxMultiplier(partialTick);
		renderState.prevYRot = ancientHornVibration.yRotO;
		renderState.yRot = ancientHornVibration.getYRot();
		renderState.prevXRot = ancientHornVibration.xRotO;
		renderState.xRot = ancientHornVibration.getXRot();
	}

	@Override
	public void render(@NotNull AncientHornVibrationRenderState renderState, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
		poseStack.pushPose();
		poseStack.mulPose(
			Axis.YP.rotationDegrees(
				(
					renderState.prevYRot + renderState.partialTick * (renderState.yRot - renderState.prevYRot)
				) - 90F
			)
		);
		poseStack.mulPose(
			Axis.ZP.rotationDegrees(
				(
					renderState.prevXRot + renderState.partialTick * (renderState.xRot - renderState.prevXRot)
					+ 90F
				)
			)
		);
		VertexConsumer vertexConsumer = buffer.getBuffer(FrozenRenderType.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false));

		float multiplier = renderState.boundingBoxMultiplier;
		float scale = (multiplier * 0.5F) + 1F;
		float alpha = 1F - (multiplier / 15F);
		float correctedAlpha = Math.max(alpha, 0.01F);
		int color = ARGB.colorFromFloat(correctedAlpha, 1F, 1F, 1F);

		poseStack.scale(scale, scale, scale);
		this.model.animateHornVibration(renderState);
		this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);
		poseStack.popPose();

		super.render(renderState, poseStack, buffer, light);
	}

	@Override
	protected int getBlockLightLevel(AncientHornVibration entity, BlockPos blockPos) {
		return 15;
	}

	@Override
	public @NotNull AncientHornVibrationRenderState createRenderState() {
		return new AncientHornVibrationRenderState();
	}
}
