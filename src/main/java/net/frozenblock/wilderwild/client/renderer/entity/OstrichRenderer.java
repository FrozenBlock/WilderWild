/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWEquipmentClientInfo;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.OstrichInbredModel;
import net.frozenblock.wilderwild.client.model.OstrichModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.OstrichRenderState;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
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
			new SimpleEquipmentLayer<>(
				this,
				context.getEquipmentRenderer(),
				WWEquipmentClientInfo.OSTRICH_SADDLE,
				ostrichRenderState -> ostrichRenderState.saddle,
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_SADDLE)),
				new OstrichModel(context.bakeLayer(WWModelLayers.OSTRICH_BABY_SADDLE))
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
		renderState.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE);
	}
}

