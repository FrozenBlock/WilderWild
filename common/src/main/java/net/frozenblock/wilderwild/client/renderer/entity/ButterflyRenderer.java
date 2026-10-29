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

import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.ambient.ButterflyModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.ButterflyRenderState;
import net.frozenblock.wilderwild.entity.ambient.butterfly.Butterfly;
import net.frozenblock.wilderwild.entity.ambient.butterfly.ButterflyVariant;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

@ClientOnly
public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyRenderState, ButterflyModel> {

	public ButterflyRenderer(Context context) {
		super(context, new ButterflyModel(context.bakeLayer(WWModelLayers.BUTTERFLY)), 0.25F);
	}

	@Override
	public ButterflyRenderState createRenderState() {
		return new ButterflyRenderState();
	}

	@Override
	public void extractRenderState(Butterfly butterfly, ButterflyRenderState state, float partialTicks) {
		super.extractRenderState(butterfly, state, partialTicks);
		state.downProgress = butterfly.getDownProgress(partialTicks);
		state.groundProgress = butterfly.getGroundProgress(partialTicks);
		state.flyingXRot = butterfly.getFlyingXRot(partialTicks);

		final ButterflyVariant variant = butterfly.wilderWild$getVariantOr(null);
		if (variant == null) return;

		state.texture = variant.resourceTexture().texturePath();
	}

	@Override
	public Identifier getTextureLocation(ButterflyRenderState state) {
		return state.texture;
	}
}

