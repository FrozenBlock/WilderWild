/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FlowerCowFlowerLayer<T extends FlowerCow> extends RenderLayer<T, CowModel<T>> {
	private static final float FLOWER_SCALE = 0.75F;

	private final BlockRenderDispatcher blockRenderer;

	public FlowerCowFlowerLayer(RenderLayerParent<T, CowModel<T>> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, @NotNull T flowerCow, float f, float g, float h, float j, float k, float l) {
		if (!flowerCow.isBaby()) {
			Minecraft minecraft = Minecraft.getInstance();
			boolean bl = minecraft.shouldEntityAppearGlowing(flowerCow) && flowerCow.isInvisible();
			if (!flowerCow.isInvisible() || bl) {
				BlockState blockState = flowerCow.getVariantForRendering().getFlowerBlockState();
				int m = LivingEntityRenderer.getOverlayCoords(flowerCow, 0F);
				BakedModel bakedModel = this.blockRenderer.getBlockModel(blockState);
				int flowersLeft = flowerCow.getFlowersLeft();

				// BACK MIDDLE
				if (flowersLeft >= 1) {
					poseStack.pushPose();
					poseStack.translate(0F, -0.35F, 0.5F);
					poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
					poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
					this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
					poseStack.popPose();
				}

				if (flowersLeft >= 2) {
					// MIDDLE LEFT
					poseStack.pushPose();
					poseStack.translate(0.2F, -0.35F, 0F);
					poseStack.mulPose(Axis.YP.rotationDegrees(-32));
					poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
					poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
					this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
					poseStack.popPose();
				}

				if (flowersLeft >= 3) {
					// MIDDLE RIGHT
					poseStack.pushPose();
					poseStack.translate(-0.2F, -0.35F, -0.15F);
					poseStack.mulPose(Axis.YP.rotationDegrees(112));
					poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
					poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
					this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
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
					this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
					poseStack.popPose();
				}
			}
		}
	}

	private void renderFlowerBlock(
		PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean bl, BlockState blockState, int j, BakedModel bakedModel
	) {
		if (bl) {
			this.blockRenderer.getModelRenderer()
				.renderModel(
					poseStack.last(),
					multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)),
					blockState,
					bakedModel,
					0F,
					0F,
					0F,
					i,
					j
				);
		} else {
			this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, i, j);
		}
	}
}
