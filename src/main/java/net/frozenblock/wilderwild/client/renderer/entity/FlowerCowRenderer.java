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

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.entity.layers.FlowerCowFlowerLayer;
import net.frozenblock.wilderwild.client.renderer.entity.state.FlowerCowRenderState;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FlowerCowRenderer extends AgeableMobRenderer<FlowerCow, FlowerCowRenderState, CowModel> {

	public FlowerCowRenderer(EntityRendererProvider.Context context) {
		super(context, new CowModel(context.bakeLayer(WWModelLayers.MOOBLOOM)), new CowModel(context.bakeLayer(WWModelLayers.MOOBLOOM_BABY)), 0.7F);
		this.addLayer(new FlowerCowFlowerLayer(this, context.getBlockRenderDispatcher()));
	}

	@Override
	public @NotNull FlowerCowRenderState createRenderState() {
		return new FlowerCowRenderState();
	}

	@Override
	public void extractRenderState(FlowerCow flowerCow, FlowerCowRenderState renderState, float partialTick) {
		super.extractRenderState(flowerCow, renderState, partialTick);
		renderState.flowers = flowerCow.getFlowersLeft();

		MoobloomVariant variant = flowerCow.getVariantForRendering();
		renderState.texture = variant.assetInfo().texturePath();
		renderState.flowerBlockState = variant.getFlowerBlockState();
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull FlowerCowRenderState renderState) {
		return renderState.texture;
	}
}

