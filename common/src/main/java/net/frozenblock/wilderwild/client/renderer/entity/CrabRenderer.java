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
import java.util.Optional;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.animal.crab.AdultCrabModel;
import net.frozenblock.wilderwild.client.model.animal.crab.BabyCrabModel;
import net.frozenblock.wilderwild.client.model.animal.crab.CrabModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.frozenblock.wilderwild.entity.animal.crab.Crab;
import net.frozenblock.wilderwild.entity.animal.crab.CrabVariant;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

@ClientOnly
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
	protected void setupRotations(CrabRenderState state, PoseStack poseStack, float bodyRot, float entityScale) {
		poseStack.translate(0F, 0.17F * state.ageScale, 0F);
		poseStack.rotate(Axis.YP.rotationDegrees(-90F));
		super.setupRotations(state, poseStack, bodyRot, entityScale);
	}

	@Override
	protected void scale(CrabRenderState state, PoseStack poseStack) {
		super.scale(state, poseStack);
		if (state.isDitto && state.isBaby) poseStack.scale(state.ageScale, state.ageScale, state.ageScale);
	}

	@Override
	public void submit(
		CrabRenderState state,
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		CameraRenderState camera
	) {
		this.model = state.isBaby && !state.isDitto ? this.babyModel : (!WWConstants.MOJANG_CRABS || state.isDitto) ? this.normalModel : this.mojangModel;
		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	@Override
	protected float getFlipDegrees() {
		return 180F;
	}

	@Override
	public Identifier getTextureLocation(CrabRenderState state) {
		if (state.isDitto) return CRAB_DITTO_LOCATION;
		return WWConstants.MOJANG_CRABS ? state.mojangTexture : state.texture;
	}

	@Override
	public CrabRenderState createRenderState() {
		return new CrabRenderState();
	}

	@Override
	public void extractRenderState(Crab crab, CrabRenderState state, float partialTicks) {
		super.extractRenderState(crab, state, partialTicks);
		state.climbXRot = Mth.lerp(partialTicks, crab.prevClimbAnimX, crab.climbAnimX) * 85F;
		state.attackTime = crab.getSwingAnimation(partialTicks);
		state.isDitto = crab.isDitto();
		state.hidingAnimationState.copyFrom(crab.hidingAnimationState);
		state.diggingAnimationState.copyFrom(crab.diggingAnimationState);
		state.emergingAnimationState.copyFrom(crab.emergingAnimationState);

		final Optional<Holder<CrabVariant>> optionalVariant = Optional.ofNullable(crab.wilderWild$getVariant());
		if (optionalVariant.isEmpty()) return;

		final CrabVariant variant = optionalVariant.get().value();
		state.texture = (state.isBaby ? variant.babyTexture() : variant.texture()).texturePath();
		state.mojangTexture = (state.isBaby ? variant.babyMojangTexture() : variant.mojangTexture()).texturePath();
	}
}

