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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import java.util.function.Function;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.LivingEntityEmissiveLayer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ARGB;
import org.jetbrains.annotations.NotNull;

public class StellaWardenLayer extends LivingEntityEmissiveLayer<WardenRenderState, WardenModel> {
	public StellaWardenLayer(
		@NotNull RenderLayerParent<WardenRenderState, WardenModel> context,
		@NotNull ResourceLocation texture,
		@NotNull AlphaFunction<WardenRenderState> animationAngleAdjuster,
		@NotNull DrawSelector<WardenRenderState, WardenModel> modelPartVisibility,
		Function<ResourceLocation, RenderType> function
	) {
		super(context, texture, animationAngleAdjuster, modelPartVisibility, function);
	}

	@Override
	public void render(
		@NotNull PoseStack poseStack, @NotNull MultiBufferSource vertexConsumerProvider, int light, @NotNull WardenRenderState renderState, float partialTick, float color
	) {
		// TODO: make WilderWarden actually not error here
		if (!renderState.isInvisible && ((WilderWarden) renderState).wilderWild$isStella()) {
			this.onlyDrawSelectedParts(renderState);
			VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.bufferProvider.apply(this.texture));
			float alpha = this.alphaFunction.apply(renderState, partialTick);
			this.getParentModel()
				.renderToBuffer(
					poseStack,
					vertexConsumer,
					light,
					LivingEntityRenderer.getOverlayCoords(renderState, 0F),
					ARGB.colorFromFloat(alpha, 1F, 1F, 1F)
				);
			this.resetDrawForAllParts();
		}
	}

	private void onlyDrawSelectedParts(WardenRenderState renderState) {
		List<ModelPart> list = this.drawSelector.getPartsToDraw(this.getParentModel(), renderState);
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = true);
		list.forEach(part -> part.skipDraw = false);
	}

	private void resetDrawForAllParts() {
		this.getParentModel().root().getAllParts().forEach(part -> part.skipDraw = false);
	}
}
