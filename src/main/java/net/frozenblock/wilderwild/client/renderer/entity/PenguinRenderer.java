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

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.PenguinModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.frozenblock.wilderwild.entity.Penguin;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Math;

public class PenguinRenderer<T extends Penguin> extends AgeableMobRenderer<Penguin, PenguinRenderState, PenguinModel<PenguinRenderState>> {
	private static final ResourceLocation TEXTURE = WWConstants.id("textures/entity/penguin/penguin.png");

	public PenguinRenderer(EntityRendererProvider.Context context) {
		super(
			context,
			new PenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN)),
			new PenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN_BABY)),
			0.5F
		);
	}

	@Override
	public @NotNull PenguinRenderState createRenderState() {
		return new PenguinRenderState();
	}

	@Override
	public void extractRenderState(Penguin penguin, PenguinRenderState renderState, float partialTick) {
		super.extractRenderState(penguin, renderState, partialTick);
		renderState.movementDelta = Math.min(renderState.walkAnimationSpeed * 5F, 1F);
		renderState.swimAmount = penguin.getSwimAmount(partialTick);
		renderState.wadeProgress = penguin.getWadeProgress(partialTick);
		renderState.slideProgress = penguin.getSlideProgress(partialTick);
		renderState.layDownAnimationState = penguin.layDownAnimationState;
		renderState.standUpAnimationState = penguin.standUpAnimationState;
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(PenguinRenderState livingEntityRenderState) {
		return TEXTURE;
	}
}

