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
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.CrabModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CrabRenderer extends MobRenderer<Crab, CrabRenderState, CrabModel> {
	private static final ResourceLocation CRAB_DITTO_LOCATION = WWConstants.id("textures/entity/crab/crab_ditto.png");

	public CrabRenderer(EntityRendererProvider.Context context) {
		this(context, WWModelLayers.CRAB);
	}

	public CrabRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new CrabModel(context.bakeLayer(layer)), 0.3F);
	}

	@Override
	protected void setupRotations(@NotNull CrabRenderState renderState, @NotNull PoseStack poseStack, float f, float g) {
		poseStack.translate(0F, 0.17F * renderState.ageScale, 0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
		super.setupRotations(renderState, poseStack, f, g);
	}

	@Override
	protected void scale(CrabRenderState renderState, PoseStack poseStack) {
		super.scale(renderState, poseStack);
		poseStack.scale(renderState.ageScale, renderState.ageScale, renderState.ageScale);
	}

	@Override
	protected float getFlipDegrees() {
		return 180F;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull CrabRenderState renderState) {
		return !renderState.isDitto ? renderState.texture : CRAB_DITTO_LOCATION;
	}

	@Override
	@NotNull
	public CrabRenderState createRenderState() {
		return new CrabRenderState();
	}

	@Override
	public void extractRenderState(Crab entity, CrabRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.climbXRot = Mth.lerp(partialTick, entity.prevClimbAnimX, entity.climbAnimX) * 85F;
		renderState.attackTime = entity.getAttackAnim(partialTick);
		renderState.isDitto = entity.isDitto();
		renderState.hidingAnimationState.copyFrom(entity.hidingAnimationState);
		renderState.diggingAnimationState.copyFrom(entity.diggingAnimationState);
		renderState.emergingAnimationState.copyFrom(entity.emergingAnimationState);
		renderState.texture = entity.getVariantForRendering().assetInfo().texturePath();
	}
}

