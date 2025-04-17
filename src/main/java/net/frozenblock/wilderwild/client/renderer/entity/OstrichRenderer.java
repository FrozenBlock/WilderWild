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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.OstrichInbredModel;
import net.frozenblock.wilderwild.client.model.OstrichModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.OstrichRenderState;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

@Environment(EnvType.CLIENT)
public class OstrichRenderer extends AgeableMobRenderer<Ostrich, OstrichRenderState, EntityModel<OstrichRenderState>> {
	private static final ResourceLocation OSTRICH_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich.png");
	private static final ResourceLocation OSTRICH_SADDLE_LOCATION = WWConstants.id("textures/entity/ostrich/ostrich_saddle.png");

	private final EntityModel<OstrichRenderState> normalModel;
	private final EntityModel<OstrichRenderState> normalBabyModel;
	private final EntityModel<OstrichRenderState> inbredModel;
	private final EntityModel<OstrichRenderState> inbredBabyModel;

	public OstrichRenderer(EntityRendererProvider.Context context) {
		super(context, new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH)), new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY)), 0.75F);

		this.normalModel = this.adultModel;
		this.normalBabyModel = this.babyModel;

		this.inbredModel = new OstrichInbredModel(context.bakeLayer(WWModelLayers.OSTRICH_INBRED));
		this.inbredBabyModel = new OstrichInbredModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY_INBRED));

		this.addLayer(
			new SaddleLayer<>(
				this,
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)),
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY_SADDLE)),
				OSTRICH_SADDLE_LOCATION
			)
		);
	}

	@Override
	public void render(@NotNull OstrichRenderState renderState, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight) {
		if (renderState.isInbred) {
			this.adultModel = this.inbredModel;
			this.babyModel = this.inbredBabyModel;
		} else {
			this.adultModel = this.normalModel;
			this.babyModel = this.normalBabyModel;
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
		renderState.isSaddled = entity.isSaddled();
	}
}

