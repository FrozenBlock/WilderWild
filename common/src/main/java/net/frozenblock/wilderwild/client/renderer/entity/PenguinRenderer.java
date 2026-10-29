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

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.animal.penguin.AdultPenguinModel;
import net.frozenblock.wilderwild.client.model.animal.penguin.BabyPenguinModel;
import net.frozenblock.wilderwild.client.model.animal.penguin.PenguinModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.PenguinRenderState;
import net.frozenblock.wilderwild.entity.animal.penguin.Penguin;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;

@ClientOnly
public class PenguinRenderer<T extends Penguin> extends AgeableMobRenderer<T, PenguinRenderState, PenguinModel<PenguinRenderState>> {
	private static final Identifier TEXTURE = WWConstants.id("textures/entity/penguin/penguin.png");
	private static final Identifier LINUX = WWConstants.id("textures/entity/penguin/penguin_linux.png");
	private static final Identifier TEXTURE_BABY = WWConstants.id("textures/entity/penguin/penguin_baby.png");
	private static final Identifier LINUX_BABY = WWConstants.id("textures/entity/penguin/penguin_linux_baby.png");

	public PenguinRenderer(Context context) {
		super(
			context,
			new AdultPenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN)),
			new BabyPenguinModel<>(context.bakeLayer(WWModelLayers.PENGUIN_BABY)),
			0.5F
		);
	}

	@Override
	public PenguinRenderState createRenderState() {
		return new PenguinRenderState();
	}

	@Override
	public void extractRenderState(T penguin, PenguinRenderState state, float partialTicks) {
		super.extractRenderState(penguin, state, partialTicks);
		state.movementDelta = Math.min(state.walkAnimationSpeed * 5F, 1F);
		state.swimAmount = penguin.getSwimAmount(partialTicks);
		state.wadeProgress = penguin.getWadeProgress(partialTicks);
		state.slideProgress = penguin.getSlideProgress(partialTicks);
		state.isLinux = penguin.isLinux();
		state.layDownAnimationState = penguin.layDownAnimationState;
		state.standUpAnimationState = penguin.standUpAnimationState;
		state.callAnimationState = penguin.callAnimationState;
	}

	@Override
	public Identifier getTextureLocation(PenguinRenderState state) {
		if (state.isBaby) return state.isLinux ? LINUX_BABY : TEXTURE_BABY;
		return state.isLinux ? LINUX : TEXTURE;
	}
}

