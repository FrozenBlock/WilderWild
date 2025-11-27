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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.PenguinModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import org.joml.Math;

@Environment(EnvType.CLIENT)
public class PenguinRenderer<T extends Penguin> extends AgeableMobRenderer<Penguin, PenguinRenderState, PenguinModel<PenguinRenderState>> {
	private static final Identifier TEXTURE = WWConstants.id("textures/entity/penguin/penguin.png");
	private static final Identifier LINUX = WWConstants.id("textures/entity/penguin/penguin_linux.png");

	public PenguinRenderer(Context context) {
		super(
			context,
			new PenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN)),
			new PenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN_BABY)),
			0.5F
		);
	}

	@Override
	public PenguinRenderState createRenderState() {
		return new PenguinRenderState();
	}

	@Override
	public void extractRenderState(Penguin penguin, PenguinRenderState renderState, float partialTick) {
		super.extractRenderState(penguin, renderState, partialTick);
		renderState.movementDelta = Math.min(renderState.walkAnimationSpeed * 5F, 1F);
		renderState.swimAmount = penguin.getSwimAmount(partialTick);
		renderState.wadeProgress = penguin.getWadeProgress(partialTick);
		renderState.slideProgress = penguin.getSlideProgress(partialTick);
		renderState.isLinux = penguin.isLinux();
		renderState.layDownAnimationState = penguin.layDownAnimationState;
		renderState.standUpAnimationState = penguin.standUpAnimationState;
		renderState.callAnimationState = penguin.callAnimationState;
	}

	@Override
	public Identifier getTextureLocation(PenguinRenderState livingEntityRenderState) {
		return livingEntityRenderState.isLinux ? LINUX : TEXTURE;
	}
}

