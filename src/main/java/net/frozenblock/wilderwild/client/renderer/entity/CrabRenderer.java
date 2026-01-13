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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.animal.crab.AdultCrabModel;
import net.frozenblock.wilderwild.client.model.animal.crab.BabyCrabModel;
import net.frozenblock.wilderwild.client.model.animal.crab.CrabModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.variant.crab.CrabVariant;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class CrabRenderer extends MobRenderer<Crab, CrabRenderState, CrabModel> {
	private static final Identifier CRAB_DITTO_LOCATION = WWConstants.id("textures/entity/crab/crab_ditto.png");

	private final CrabModel normalModel = this.getModel();
	private final CrabModel mojangModel;
	private final CrabModel babyModel;

	public CrabRenderer(Context context) {
		this(context, WWModelLayers.CRAB);
	}

	public CrabRenderer(Context context, ModelLayerLocation layer) {
		super(context, new AdultCrabModel(context.bakeLayer(layer)), 0.3F);
		this.mojangModel = new AdultCrabModel(context.bakeLayer(WWModelLayers.CRAB_MOJANG));
		this.babyModel = new BabyCrabModel(context.bakeLayer(WWModelLayers.CRAB_BABY));
	}

	@Override
	protected void setupRotations(CrabRenderState renderState, PoseStack poseStack, float f, float g) {
		poseStack.translate(0F, 0.17F * renderState.ageScale, 0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
		super.setupRotations(renderState, poseStack, f, g);
	}

	@Override
	protected void scale(CrabRenderState renderState, PoseStack poseStack) {
		super.scale(renderState, poseStack);
		if (renderState.isDitto && renderState.isBaby) poseStack.scale(renderState.ageScale, renderState.ageScale, renderState.ageScale);
	}

	@Override
	public void submit(
		CrabRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState cameraState
	) {
		this.model = renderState.isBaby && !renderState.isDitto ? this.babyModel : (!WWConstants.MOJANG_CRABS || renderState.isDitto) ? this.normalModel : this.mojangModel;
		super.submit(renderState, poseStack, collector, cameraState);
	}

	@Override
	protected float getFlipDegrees() {
		return 180F;
	}

	@Override
	public Identifier getTextureLocation(CrabRenderState renderState) {
		if (renderState.isDitto) return CRAB_DITTO_LOCATION;
		return WWConstants.MOJANG_CRABS ? renderState.mojangTexture : renderState.texture;
	}

	@Override
	public CrabRenderState createRenderState() {
		return new CrabRenderState();
	}

	@Override
	public void extractRenderState(Crab crab, CrabRenderState renderState, float partialTick) {
		super.extractRenderState(crab, renderState, partialTick);
		renderState.climbXRot = Mth.lerp(partialTick, crab.prevClimbAnimX, crab.climbAnimX) * 85F;
		renderState.attackTime = crab.getAttackAnim(partialTick);
		renderState.isDitto = crab.isDitto();
		renderState.hidingAnimationState.copyFrom(crab.hidingAnimationState);
		renderState.diggingAnimationState.copyFrom(crab.diggingAnimationState);
		renderState.emergingAnimationState.copyFrom(crab.emergingAnimationState);

		final CrabVariant variant = crab.getVariantForRendering();
		renderState.texture = (renderState.isBaby ? variant.babyTexture() : variant.texture()).texturePath();
		renderState.mojangTexture = (renderState.isBaby ? variant.babyMojangTexture() : variant.mojangTexture()).texturePath();
	}
}

