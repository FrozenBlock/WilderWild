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
import net.frozenblock.wilderwild.client.model.ScorchedModel;
import net.frozenblock.wilderwild.client.renderer.entity.layers.ScorchedGlowingLayer;
import net.frozenblock.wilderwild.client.renderer.entity.state.ScorchedRenderState;
import net.frozenblock.wilderwild.entity.Scorched;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class ScorchedRenderer extends MobRenderer<Scorched, ScorchedRenderState, ScorchedModel> {
	private static final Identifier SCORCHED_LOCATION = WWConstants.id("textures/entity/scorched/scorched.png");
	private static final float SCALE = 0.9F;

	public ScorchedRenderer(EntityRendererProvider.Context context) {
		this(context, WWModelLayers.SCORCHED);
	}

	public ScorchedRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new ScorchedModel(context.bakeLayer(layer)), 0.8F);
		this.addLayer(new ScorchedGlowingLayer(this));
		this.shadowRadius *= SCALE;
	}

	@Override
	public void scale(@NotNull ScorchedRenderState renderState, @NotNull PoseStack poseStack) {
		super.scale(renderState, poseStack);
		poseStack.scale(SCALE, SCALE, SCALE);
	}

	@Override
	public @NotNull Identifier getTextureLocation(@NotNull ScorchedRenderState renderState) {
		return SCORCHED_LOCATION;
	}

	@Override
	@NotNull
	public ScorchedRenderState createRenderState() {
		return new ScorchedRenderState();
	}

	@Override
	public void extractRenderState(@NotNull Scorched entity, @NotNull ScorchedRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.lavaAnimProgress = entity.getLavaAnimProgress(partialTick);
	}
}

