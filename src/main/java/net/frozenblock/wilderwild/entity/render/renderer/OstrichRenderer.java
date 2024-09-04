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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.entity.render.model.OstrichInbredModel;
import net.frozenblock.wilderwild.entity.render.model.OstrichModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class OstrichRenderer extends MobRenderer<Ostrich, OstrichRenderState, EntityModel<OstrichRenderState>> {
	private static final ResourceLocation OSTRICH_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich.png");
	private static final ResourceLocation OSTRICH_SADDLE_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich_saddle.png");

	private boolean isInbred = false;
	private final EntityModel<OstrichRenderState> normalModel = this.getModel();
	private final EntityModel<OstrichRenderState> inbredModel;

	public OstrichRenderer(EntityRendererProvider.Context context) {
		super(context, new OstrichModel<>(context.bakeLayer(WWModelLayers.OSTRICH)), 0.75F);
		this.inbredModel = new OstrichInbredModel(context.bakeLayer(WWModelLayers.OSTRICH_INBRED));
		this.addLayer(new SaddleLayer<>(this, new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)), OSTRICH_SADDLE_LOCATION));
	}

	@Override
	public void render(OstrichRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		boolean isOstrichInbred = renderState.isInbred;
		if (this.isInbred != isOstrichInbred) {
			this.model = !isOstrichInbred ? this.normalModel : this.inbredModel;
			this.isInbred = isOstrichInbred;
		}
		super.render(renderState, poseStack, multiBufferSource, packedLight);
	}


	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull OstrichRenderState renderState) {
		return OSTRICH_LOCATION;
	}

	@Override
	@NotNull
	public OstrichRenderState createRenderState() {
		return new OstrichRenderState();
	}

	@Override
	public void extractRenderState(Ostrich entity, OstrichRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.walkAnimationPos *= 1.65F;
		renderState.walkAnimationSpeed = Math.min(renderState.walkAnimationSpeed * 1.5F, 1F);

		renderState.isInbred = entity.isInbred();
		renderState.beakAnimProgress = entity.getBeakAnimProgress(partialTick);
		renderState.targetStraightProgress = entity.getTargetStraightProgress(partialTick);
	}
}

