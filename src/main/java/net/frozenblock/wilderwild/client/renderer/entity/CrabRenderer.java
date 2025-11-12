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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.CrabModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.CrabRenderState;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class CrabRenderer extends MobRenderer<Crab, CrabRenderState, CrabModel> {
	private static final Identifier CRAB_DITTO_LOCATION = WWConstants.id("textures/entity/crab/crab_ditto.png");

	private final CrabModel normalModel = this.getModel();
	private final CrabModel mojangModel;

	public CrabRenderer(EntityRendererProvider.Context context) {
		this(context, WWModelLayers.CRAB);
	}

	public CrabRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new CrabModel(context.bakeLayer(layer)), 0.3F);
		this.mojangModel = new CrabModel(context.bakeLayer(WWModelLayers.CRAB_MOJANG));
	}

	@Override
	protected void setupRotations(@NotNull CrabRenderState renderState, @NotNull PoseStack poseStack, float f, float g) {
		poseStack.translate(0F, 0.17F * renderState.ageScale, 0F);
		poseStack.mulPose(Axis.YP.rotationDegrees(-90F));
		super.setupRotations(renderState, poseStack, f, g);
	}

	@Override
	protected void scale(@NotNull CrabRenderState renderState, PoseStack poseStack) {
		super.scale(renderState, poseStack);
		poseStack.scale(renderState.ageScale, renderState.ageScale, renderState.ageScale);
	}

	@Override
	public void submit(
		@NotNull CrabRenderState renderState,
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		@NotNull CameraRenderState cameraRenderState
	) {
		this.model = (!WWConstants.MOJANG_CRABS || renderState.isDitto) ? this.normalModel : this.mojangModel;
		super.submit(renderState, poseStack, submitNodeCollector, cameraRenderState);
	}

	@Override
	protected float getFlipDegrees() {
		return 180F;
	}

	@Override
	@NotNull
	public Identifier getTextureLocation(@NotNull CrabRenderState renderState) {
		if (renderState.isDitto) return CRAB_DITTO_LOCATION;
		return WWConstants.MOJANG_CRABS ? renderState.mojangTexture : renderState.texture;
	}

	@Override
	@NotNull
	public CrabRenderState createRenderState() {
		return new CrabRenderState();
	}

	@Override
	public void extractRenderState(@NotNull Crab entity, @NotNull CrabRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.climbXRot = Mth.lerp(partialTick, entity.prevClimbAnimX, entity.climbAnimX) * 85F;
		renderState.attackTime = entity.getAttackAnim(partialTick);
		renderState.isDitto = entity.isDitto();
		renderState.hidingAnimationState.copyFrom(entity.hidingAnimationState);
		renderState.diggingAnimationState.copyFrom(entity.diggingAnimationState);
		renderState.emergingAnimationState.copyFrom(entity.emergingAnimationState);
		renderState.texture = entity.getVariantForRendering().resourceTexture().texturePath();
		renderState.mojangTexture = entity.getVariantForRendering().mojangResourceTexture().texturePath();
	}
}

