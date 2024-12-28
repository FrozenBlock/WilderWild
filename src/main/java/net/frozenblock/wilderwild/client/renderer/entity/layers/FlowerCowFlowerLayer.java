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
	private final BlockRenderDispatcher blockRenderer;

	public FlowerCowFlowerLayer(RenderLayerParent<T, CowModel<T>> renderLayerParent, BlockRenderDispatcher blockRenderDispatcher) {
		super(renderLayerParent);
		this.blockRenderer = blockRenderDispatcher;
	}

	public void render(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, @NotNull T flowerCow, float f, float g, float h, float j, float k, float l) {
		if (!flowerCow.isBaby()) {
			Minecraft minecraft = Minecraft.getInstance();
			boolean bl = minecraft.shouldEntityAppearGlowing(flowerCow) && flowerCow.isInvisible();
			if (!flowerCow.isInvisible() || bl) {
				BlockState blockState = flowerCow.getVariantForRendering().getFlowerBlockState();
				int m = LivingEntityRenderer.getOverlayCoords(flowerCow, 0F);
				BakedModel bakedModel = this.blockRenderer.getBlockModel(blockState);
				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48F));
				poseStack.scale(-1F, -1F, 1F);
				poseStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
				poseStack.popPose();
				poseStack.pushPose();
				poseStack.translate(0.2F, -0.35F, 0.5F);
				poseStack.mulPose(Axis.YP.rotationDegrees(42F));
				poseStack.translate(0.1F, 0F, -0.6F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-48F));
				poseStack.scale(-1F, -1F, 1F);
				poseStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
				poseStack.popPose();
				poseStack.pushPose();
				this.getParentModel().getHead().translateAndRotate(poseStack);
				poseStack.translate(0F, -0.7F, -0.2F);
				poseStack.mulPose(Axis.YP.rotationDegrees(-78F));
				poseStack.scale(-1F, -1F, 1F);
				poseStack.translate(-0.5F, -0.5F, -0.5F);
				this.renderFlowerBlock(poseStack, multiBufferSource, i, bl, blockState, m, bakedModel);
				poseStack.popPose();
			}
		}
	}

	private void renderFlowerBlock(
		PoseStack poseStack, MultiBufferSource multiBufferSource, int i, boolean bl, BlockState blockState, int j, BakedModel bakedModel
	) {
		if (bl) {
			this.blockRenderer
				.getModelRenderer()
				.renderModel(
					poseStack.last(), multiBufferSource.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), blockState, bakedModel, 0F, 0F, 0F, i, j
				);
		} else {
			this.blockRenderer.renderSingleBlock(blockState, poseStack, multiBufferSource, i, j);
		}
	}
}
