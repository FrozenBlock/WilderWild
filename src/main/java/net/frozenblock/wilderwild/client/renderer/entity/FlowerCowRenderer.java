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
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.entity.layers.FlowerCowFlowerLayer;
import net.frozenblock.wilderwild.client.renderer.entity.state.FlowerCowRenderState;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;

@Environment(EnvType.CLIENT)
public class FlowerCowRenderer extends AgeableMobRenderer<FlowerCow, FlowerCowRenderState, CowModel> {

	public FlowerCowRenderer(Context context) {
		super(context, new CowModel(context.bakeLayer(WWModelLayers.MOOBLOOM)), new CowModel(context.bakeLayer(WWModelLayers.MOOBLOOM_BABY)), 0.7F);
		this.addLayer(new FlowerCowFlowerLayer(this, context.getBlockRenderDispatcher()));
	}

	@Override
	protected AABB getBoundingBoxForCulling(FlowerCow flowerCow) {
		final AABB boundingBox = super.getBoundingBoxForCulling(flowerCow);
		if (flowerCow.getVariantForRendering().isDoubleBlock()) return boundingBox.setMaxY(boundingBox.maxY + boundingBox.getYsize() * 0.75F);
		return boundingBox;
	}

	@Override
	public FlowerCowRenderState createRenderState() {
		return new FlowerCowRenderState();
	}

	@Override
	public void extractRenderState(FlowerCow flowerCow, FlowerCowRenderState renderState, float partialTick) {
		super.extractRenderState(flowerCow, renderState, partialTick);
		renderState.flowers = flowerCow.getFlowersLeft();

		final MoobloomVariant variant = flowerCow.getVariantForRendering();
		renderState.texture = variant.resourceTexture().texturePath();
		renderState.flowerBlockState = variant.flowerBlockState();
		renderState.topFlowerBlockState = variant.isDoubleBlock()
			? renderState.flowerBlockState.trySetValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER)
			: null;
	}

	@Override
	public Identifier getTextureLocation(FlowerCowRenderState renderState) {
		return renderState.texture;
	}
}

