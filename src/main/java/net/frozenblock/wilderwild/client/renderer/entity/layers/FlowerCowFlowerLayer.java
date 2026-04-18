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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.renderer.entity.state.FlowerCowRenderState;
import net.minecraft.client.model.animal.cow.CowModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;

@Environment(EnvType.CLIENT)
public class FlowerCowFlowerLayer extends RenderLayer<FlowerCowRenderState, CowModel> {
	private static final float FLOWER_SCALE = 0.75F;
	private static final float DOUBLE_BLOCK_SCALE = 0.75F;

	public FlowerCowFlowerLayer(RenderLayerParent<FlowerCowRenderState, CowModel> renderLayerParent) {
		super(renderLayerParent);
	}

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, FlowerCowRenderState renderState, float yRot, float xRot) {
		if (renderState.isBaby) return;

		final int flowersLeft = renderState.flowers;
		if (flowersLeft <= 0) return;

		final boolean invisible = renderState.isInvisible;
		final boolean appearsGlowingWithInvisibility = renderState.appearsGlowing() && invisible;
		if (invisible && !appearsGlowingWithInvisibility) return;

		final boolean isDoubleBlock = !renderState.topFlowerModel.isEmpty();
		final float modelScale = !isDoubleBlock ? 1F : DOUBLE_BLOCK_SCALE;
		final int overlayCoords = LivingEntityRenderer.getOverlayCoords(renderState, 0F);

		// BACK MIDDLE
		poseStack.pushPose();
		preparePose(poseStack, 0F, -0.35F, 0.5F, 0F);
		this.submitFlowerBlock(
			poseStack,
			collector,
			lightCoords,
			appearsGlowingWithInvisibility,
			renderState.outlineColor,
			renderState.flowerModel,
			overlayCoords,
			modelScale,
			false
		);
		if (isDoubleBlock) {
			this.submitFlowerBlock(
				poseStack,
				collector,
				lightCoords,
				appearsGlowingWithInvisibility,
				renderState.outlineColor,
				renderState.topFlowerModel,
				overlayCoords,
				modelScale,
				true
			);
		}
		poseStack.popPose();

		if (flowersLeft >= 2) {
			// MIDDLE LEFT
			poseStack.pushPose();
			preparePose(poseStack, 0.2F, -0.35F, 0F, -32F);
			this.submitFlowerBlock(
				poseStack,
				collector,
				lightCoords,
				appearsGlowingWithInvisibility,
				renderState.outlineColor,
				renderState.flowerModel,
				overlayCoords,
				modelScale,
				false
			);
			if (isDoubleBlock) {
				this.submitFlowerBlock(
					poseStack,
					collector,
					lightCoords,
					appearsGlowingWithInvisibility,
					renderState.outlineColor,
					renderState.topFlowerModel,
					overlayCoords,
					modelScale,
					true
				);
			}
			poseStack.popPose();
		}

		if (flowersLeft >= 3) {
			// MIDDLE RIGHT
			poseStack.pushPose();
			preparePose(poseStack, -0.2F, -0.35F, -0.15F, 112F);
			this.submitFlowerBlock(
				poseStack,
				collector,
				lightCoords,
				appearsGlowingWithInvisibility,
				renderState.outlineColor,
				renderState.flowerModel,
				overlayCoords,
				modelScale,
				false
			);
			if (isDoubleBlock) {
				this.submitFlowerBlock(
					poseStack,
					collector,
					lightCoords,
					appearsGlowingWithInvisibility,
					renderState.outlineColor,
					renderState.topFlowerModel,
					overlayCoords,
					modelScale,
					true
				);
			}
			poseStack.popPose();
		}

		if (flowersLeft >= 4) {
			// HEAD
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			preparePose(poseStack, 0.1F, -0.7F, -0.2F, -78F);
			this.submitFlowerBlock(
				poseStack,
				collector,
				lightCoords,
				appearsGlowingWithInvisibility,
				renderState.outlineColor,
				renderState.flowerModel,
				overlayCoords,
				modelScale,
				false
			);
			if (isDoubleBlock) {
				this.submitFlowerBlock(
					poseStack,
					collector,
					lightCoords,
					appearsGlowingWithInvisibility,
					renderState.outlineColor,
					renderState.topFlowerModel,
					overlayCoords,
					modelScale,
					true
				);
			}
			poseStack.popPose();
		}
	}

	private void submitFlowerBlock(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int lightCoords,
		boolean appearsGlowingWithInvisibility,
		int outlineColor,
		BlockModelRenderState flowerModel,
		int overlayCoords,
		float scale,
		boolean isTop
	) {
		final boolean pushAndPop = isTop || scale != 1F;
		if (pushAndPop) poseStack.pushPose();
		if (scale != 1F) poseStack.scale(scale, scale, scale);
		if (isTop) poseStack.translate(0F, 1F, 0F);

		if (appearsGlowingWithInvisibility) {
			flowerModel.submitOnlyOutline(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
		} else {
			flowerModel.submit(poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
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
