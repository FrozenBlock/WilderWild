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
import net.frozenblock.wilderwild.entity.FlowerCow;
import net.frozenblock.wilderwild.entity.variant.moobloom.MoobloomVariant;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class FlowerCowFlowerLayer<T extends FlowerCow> extends RenderLayer<T, CowModel<T>> {
	private static final float FLOWER_SCALE = 0.75F;
	private static final float DOUBLE_BLOCK_SCALE = 0.75F;

	private final BlockRenderDispatcher blockRenderer;

	public FlowerCowFlowerLayer(RenderLayerParent<T, CowModel<T>> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;
	}

	@Override
	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int light, @NotNull T flowerCow, float f, float g, float h, float j, float k, float l) {
		if (flowerCow.isBaby()) return;

		final int flowersLeft = flowerCow.getFlowersLeft();
		if (flowersLeft <= 0) return;

		final Minecraft minecraft = Minecraft.getInstance();
		final boolean invisible = flowerCow.isInvisible();
		final boolean invisibleAndGlowing = minecraft.shouldEntityAppearGlowing(flowerCow) && invisible;
		if (invisible && !invisibleAndGlowing) return;

		final MoobloomVariant variant =  flowerCow.getVariantForRendering();
		final BlockState state = variant.getFlowerBlockState();
		final BakedModel model = this.blockRenderer.getBlockModel(state);

		final boolean isDoubleBlock = variant.isDoubleBlock();
		final BlockState topState = state.trySetValue(BlockStateProperties.DOUBLE_BLOCK_HALF, DoubleBlockHalf.UPPER);
		final BakedModel topModel = this.blockRenderer.getBlockModel(topState);

		final float modelScale = !isDoubleBlock ? 1F : DOUBLE_BLOCK_SCALE;
		final int overlay = LivingEntityRenderer.getOverlayCoords(flowerCow, 0F);

		// BACK MIDDLE
		poseStack.pushPose();
		preparePose(poseStack, 0F, -0.35F, 0.5F, 0F);
		this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, state, overlay, model, modelScale, false);
		if (isDoubleBlock) this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, topState, overlay, topModel, modelScale, true);
		poseStack.popPose();

		if (flowersLeft >= 2) {
			// MIDDLE LEFT
			poseStack.pushPose();
			preparePose(poseStack, 0.2F, -0.35F, 0F, -32F);
			this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}

		if (flowersLeft >= 3) {
			// MIDDLE RIGHT
			poseStack.pushPose();
			preparePose(poseStack, -0.2F, -0.35F, -0.15F, 112F);
			this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}

		if (flowersLeft >= 4) {
			// HEAD
			poseStack.pushPose();
			this.getParentModel().getHead().translateAndRotate(poseStack);
			preparePose(poseStack, 0.1F, -0.7F, -0.2F, -78F);
			this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, state, overlay, model, modelScale, false);
			if (isDoubleBlock) this.renderFlowerBlock(poseStack, multiBufferSource, light, invisibleAndGlowing, topState, overlay, topModel, modelScale, true);
			poseStack.popPose();
		}
	}

	private static void preparePose(PoseStack poseStack, float xOffset, float yOffset, float zOffset, float rotation) {
		poseStack.translate(xOffset, yOffset, zOffset);
		if (rotation != 0F) poseStack.mulPose(Axis.YP.rotationDegrees(rotation));
		poseStack.scale(-FLOWER_SCALE, -FLOWER_SCALE, FLOWER_SCALE);
		poseStack.translate(-0.5F, -FLOWER_SCALE, -0.5F);
	}

	private void renderFlowerBlock(
		PoseStack poseStack,
		MultiBufferSource multiBufferSource,
		int light,
		boolean glowing,
		BlockState state,
		int overlay,
		BakedModel bakedModel,
		float scale,
		boolean isTop
	) {
		final boolean pushAndPop = isTop || scale != 1F;
		if (pushAndPop) poseStack.pushPose();
		if (scale != 1F) poseStack.scale(scale, scale, scale);
		if (isTop) poseStack.translate(0F, 1F, 0F);

		if (glowing) {
			this.blockRenderer.getModelRenderer()
				.renderModel(
					poseStack.last(),
					multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)),
					state,
					bakedModel,
					0F,
					0F,
					0F,
					light,
					overlay
				);
		} else {
			this.blockRenderer.renderSingleBlock(state, poseStack, multiBufferSource, light, overlay);
		}

		if (pushAndPop) poseStack.popPose();
	}
}
