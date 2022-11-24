package net.frozenblock.wilderwild.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWild;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends EntityRenderer<Tumbleweed> {
	private final BlockRenderDispatcher blockRenderer;

	public TumbleweedRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.blockRenderer = context.getBlockRenderDispatcher();
	}

	private static final double yOffset = 0.155F;
	private static final Quaternion one80Quat = Vector3f.YP.rotationDegrees(180.0F);
	private static final float pi = (float) Math.PI;
	private static final RenderType TUMBLEWEED_LAYER = RenderType.entityCutout(WilderWild.id("textures/block/tumbleweed.png"));

	public static int getOverlay(float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(false));
	}

	@Override
	public void render(@NotNull Tumbleweed entity, float entityYaw, float partialTicks, PoseStack matrixStack, @NotNull MultiBufferSource buffer, int packedLight) {
		matrixStack.pushPose();
		matrixStack.translate(0.0D, 0.5D, 0.0D);
		matrixStack.translate(0, yOffset, 0);
		matrixStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
		matrixStack.mulPose(one80Quat);

		PoseStack.Pose entry = matrixStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		VertexConsumer vertexConsumer = buffer.getBuffer(TUMBLEWEED_LAYER);
		int overlay = getOverlay(0);

		vertexConsumer
				.vertex(matrix4f, -0.5F, -0.5F, 0.0F)
				.color(255, 255, 255, 255)
				.uv(0, 1)
				.overlayCoords(overlay)
				.uv2(packedLight)
				.normal(matrix3f, 0.0F, 1.0F, 0.0F)
				.endVertex();
		vertexConsumer
				.vertex(matrix4f, 0.5F, -0.5F, 0.0F)
				.color(255, 255, 255, 255)
				.uv(1, 1)
				.overlayCoords(overlay)
				.uv2(packedLight)
				.normal(matrix3f, 0.0F, 1.0F, 0.0F)
				.endVertex();
		vertexConsumer
				.vertex(matrix4f, 0.5F, 0.5F, 0.0F)
				.color(255, 255, 255, 255)
				.uv(1, 0)
				.overlayCoords(overlay)
				.uv2(packedLight)
				.normal(matrix3f, 0.0F, 1.0F, 0.0F)
				.endVertex();
		vertexConsumer
				.vertex(matrix4f, -0.5F, 0.5F, 0.0F)
				.color(255, 255, 255, 255)
				.uv(0, 0)
				.overlayCoords(overlay)
				.uv2(packedLight)
				.normal(matrix3f, 0.0F, 1.0F, 0.0F)
				.endVertex();
		/*
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(-90.0F));
		matrixStack.translate(-0.5D, -0.5D, 0.5D);
		matrixStack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
		 */
		matrixStack.popPose();
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
	}

	@Override
	public ResourceLocation getTextureLocation(@NotNull Tumbleweed entity) {
		return TextureAtlas.LOCATION_BLOCKS;
	}
}

