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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.entity.render.model.JellyfishModel;
import net.frozenblock.wilderwild.entity.render.renderer.state.JellyfishRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishRenderState, JellyfishModel> {
	private static final ResourceLocation WHITE_TEXTURE = WWConstants.id("textures/entity/jellyfish/white.png");

	public JellyfishRenderer(@NotNull Context context) {
		super(context, new JellyfishModel(context.bakeLayer(WWModelLayers.JELLYFISH)), 0.3F);
	}

	@Override
	protected void setupRotations(JellyfishRenderState renderState, PoseStack poseStack, float rotationYaw, float g) {
		poseStack.mulPose(Axis.YP.rotationDegrees(180F - rotationYaw));
		poseStack.translate(0, renderState.isBaby ? -1.1 : -1, 0);
		poseStack.scale(0.8F, 0.8F, 0.8F);
		JellyfishModel model = this.getModel();

		if (this.isShaking(renderState)) {
			poseStack.mulPose(Axis.YP.rotationDegrees((float) (Math.cos((double) renderState.tickCount * 3.25D) * 3.141592653589793D * 0.4000000059604645D)));
		}

		if (renderState.isUpsideDown) {
			poseStack.translate(0F, renderState.boundingBoxHeight + 0.1F, 0F);
			poseStack.mulPose(Axis.ZP.rotationDegrees(180F));
		}

		if (renderState.isRGB) {
			float time = renderState.windTime;
			model.red = Mth.clamp(Math.abs((time % 6) - 3) - 1, 0, 1);
			model.green = Mth.clamp(Math.abs(((time - 2) % 6) - 3) - 1, 0, 1);
			model.blue = Mth.clamp(Math.abs(((time - 4) % 6) - 3) - 1, 0, 1);
		} else {
			model.red = 1;
			model.green = 1;
			model.blue = 1;
		}
	}

	@Override
	protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
		return 15;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull JellyfishRenderState jellyfish) {
		if (jellyfish.isRGB) {
			return WHITE_TEXTURE;
		}
		return jellyfish.variant.texture();
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
		renderState.variant = entity.getVariant();
		renderState.windTime = (ClientWindManager.time + partialTick) * 0.05F;

		renderState.xRot = -(entity.xRot1 + partialTick * (entity.xBodyRot - entity.xRot1));
		renderState.tentXRot = -(entity.xRot6 + partialTick * (entity.xRot5 - entity.xRot6));
		renderState.scale = (entity.prevScale + partialTick * (entity.scale - entity.prevScale)) * entity.getAgeScale();
	}
}
