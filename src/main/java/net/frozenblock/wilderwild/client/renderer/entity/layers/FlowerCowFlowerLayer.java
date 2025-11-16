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
import net.minecraft.client.model.CowModel;
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

	private final BlockRenderDispatcher blockRenderer;

	public FlowerCowFlowerLayer(RenderLayerParent<FlowerCowRenderState, CowModel> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;
	}

	@Override
	public void submit(
		PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, FlowerCowRenderState renderState, float f, float g
	) {
		if (renderState.isBaby) return;

		final boolean glowingAndInvisible = renderState.appearsGlowing() && renderState.isInvisible;
		if (!(!renderState.isInvisible || glowingAndInvisible)) return;

		final int flowersLeft = renderState.flowers;
		final int outlineColor = renderState.outlineColor;
		final int overlay = LivingEntityRenderer.getOverlayCoords(renderState, 0F);
		final BlockState state = renderState.flowerBlockState;
		final BlockStateModel model = this.blockRenderer.getBlockModel(state);

		// BACK MIDDLE
		if (flowersLeft >= 1) {
			poseStack.pushPose();
			poseStack.translate(0F, -0.35F, 0.5F);
			poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
			poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
			this.submitFlowerBlock(poseStack, submitNodeCollector, light, glowingAndInvisible, outlineColor, state, overlay, model);
			poseStack.popPose();
		}

		if (flowersLeft >= 2) {
			// MIDDLE LEFT
			poseStack.pushPose();
			poseStack.translate(0.2F, -0.35F, 0F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-32));
			poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
			poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
			this.submitFlowerBlock(poseStack, submitNodeCollector, light, glowingAndInvisible, outlineColor, state, overlay, model);
			poseStack.popPose();
		}

		if (flowersLeft >= 3) {
			// MIDDLE RIGHT
			poseStack.pushPose();
			poseStack.translate(-0.2F, -0.35F, -0.15F);
			poseStack.mulPose(Axis.YP.rotationDegrees(112));
			poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
			poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
			this.submitFlowerBlock(poseStack, submitNodeCollector, light, glowingAndInvisible, outlineColor, state, overlay, model);
			poseStack.popPose();
		}

		if (flowersLeft >= 4) {
			// HEAD
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			poseStack.translate(0.1F, -0.7F, -0.2F);
			poseStack.mulPose(Axis.YP.rotationDegrees(-78F));
			poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
			poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
			this.submitFlowerBlock(poseStack, submitNodeCollector, light, glowingAndInvisible, outlineColor, state, overlay, model);
			poseStack.popPose();
		}
	}

	private void submitFlowerBlock(
		PoseStack poseStack,
		SubmitNodeCollector submitNodeCollector,
		int light,
		boolean glowingAndInvisible,
		int outlineColor,
		BlockState state,
		int overlay,
		BlockStateModel model
	) {
		if (glowingAndInvisible) {
			submitNodeCollector.submitBlockModel(poseStack, RenderTypes.outline(TextureAtlas.LOCATION_BLOCKS), model, 0F, 0F, 0F, light, overlay, outlineColor);
			return;
		}
		submitNodeCollector.submitBlock(poseStack, state, light, overlay, outlineColor);
	}
}
