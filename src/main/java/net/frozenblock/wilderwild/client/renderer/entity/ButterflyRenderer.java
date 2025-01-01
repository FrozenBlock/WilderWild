/*
 * Copyright 2023-2024 FrozenBlock
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

import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.ButterflyModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.ButterflyRenderState;
import net.frozenblock.wilderwild.entity.Butterfly;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ButterflyRenderer extends MobRenderer<Butterfly, ButterflyRenderState, ButterflyModel> {

	public ButterflyRenderer(EntityRendererProvider.Context context) {
		super(context, new ButterflyModel(context.bakeLayer(WWModelLayers.BUTTERFLY)), 0.25F);
	}

	@Override
	public @NotNull ButterflyRenderState createRenderState() {
		return new ButterflyRenderState();
	}

	@Override
	public void extractRenderState(Butterfly butterfly, ButterflyRenderState renderState, float partialTick) {
		super.extractRenderState(butterfly, renderState, partialTick);
		renderState.downProgress = butterfly.getDownProgress(partialTick);
		renderState.groundProgress = butterfly.getDownProgress(partialTick);
		renderState.flyingXRot = butterfly.getFlyingXRot(partialTick);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull ButterflyRenderState renderState) {
		return renderState.texture;
	}
}

