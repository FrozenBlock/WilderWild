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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.FlowerCowRenderState;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class FlowerCowFlowerLayer extends RenderLayer<FlowerCowRenderState, CowModel> {
	private static final float FLOWER_SCALE = 0.75F;
	private static final float DOUBLE_BLOCK_SCALE = 0.75F;

	private final BlockRenderDispatcher blockRenderer;

	public FlowerCowFlowerLayer(RenderLayerParent<FlowerCowRenderState, CowModel> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector collector, int light, FlowerCowRenderState renderState, float f, float g) {
		if (renderState.isBaby) return;

		final int flowersLeft = renderState.flowers;
		if (flowersLeft <= 0) return;

		final boolean invisible = renderState.isInvisible;
		final boolean invisibleAndGlowing = renderState.appearsGlowing() && invisible;
		if (invisible && !invisibleAndGlowing) return;

		final BlockState state = renderState.flowerBlockState;
		final BlockStateModel model = this.blockRenderer.getBlockModel(state);

		final boolean isDoubleBlock = renderState.topFlowerBlockState != null;
		final BlockState topState = renderState.topFlowerBlockState;
		final BlockStateModel topModel = isDoubleBlock ? this.blockRenderer.getBlockModel(topState) : null;

		final float modelScale = !isDoubleBlock ? 1F : DOUBLE_BLOCK_SCALE;
		final int overlay = LivingEntityRenderer.getOverlayCoords(renderState, 0F);
		final int outlineColor = renderState.outlineColor;

		// BACK MIDDLE
		poseStack.pushPose();
		preparePose(poseStack, 0F, -0.35F, 0.5F, 0F);
		this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, state, overlay, model, modelScale, false);
		if (isDoubleBlock) this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, topState, overlay, topModel, modelScale, true);
		poseStack.popPose();

		if (flowersLeft >= 2) {
			// MIDDLE LEFT
			poseStack.pushPose();
			preparePose(poseStack, 0.2F, -0.35F, 0F, -32F);
			this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}

		if (flowersLeft >= 3) {
			// MIDDLE RIGHT
			poseStack.pushPose();
			preparePose(poseStack, -0.2F, -0.35F, -0.15F, 112F);
			this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}

		if (flowersLeft >= 4) {
			// HEAD
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			preparePose(poseStack, 0.1F, -0.7F, -0.2F, -78F);
			this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.submitFlowerBlock(poseStack, collector, light, invisibleAndGlowing, outlineColor, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}
	}

	private void submitFlowerBlock(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		int light,
		boolean glowingAndInvisible,
		int outlineColor,
		BlockState state,
		int overlay,
		BlockStateModel model,
		float scale,
		boolean isTop
	) {
		final boolean pushAndPop = isTop || scale != 1F;
		if (pushAndPop) poseStack.pushPose();
		if (scale != 1F) poseStack.scale(scale, scale, scale);
		if (isTop) poseStack.translate(0F, 1F, 0F);

		if (glowingAndInvisible) {
			collector.submitBlockModel(poseStack, RenderTypes.outline(TextureAtlas.LOCATION_BLOCKS), model, 0F, 0F, 0F, light, overlay, outlineColor);
		} else {
			collector.submitBlock(poseStack, state, light, overlay, outlineColor);
		}

		if (pushAndPop) poseStack.popPose();
	}

	private static void preparePose(PoseStack poseStack, float xOffset, float yOffset, float zOffset, float rotation) {
		poseStack.translate(xOffset, yOffset, zOffset);
		if (rotation != 0F) poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
		poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
	}
}
