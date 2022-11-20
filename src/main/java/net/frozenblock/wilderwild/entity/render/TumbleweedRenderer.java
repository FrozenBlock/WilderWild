package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.TntMinecartRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends EntityRenderer<Tumbleweed> {
	private final BlockRenderDispatcher blockRenderer;

	public TumbleweedRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	public void render(Tumbleweed entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
		matrixStack.pushPose();
		matrixStack.translate(0.0D, 0.5D, 0.0D);

		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStack.translate(-0.5D, -0.5D, 0.5D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	public ResourceLocation getTextureLocation(Tumbleweed entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}

