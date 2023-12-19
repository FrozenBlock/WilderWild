/*
 * Copyright 2023 FrozenBlock
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

package net.frozenblock.wilderwild.entity.render.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.entity.Ostrich;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class OstrichRenderer extends EntityRenderer<Ostrich> {
	//CREDIT TO magistermaks ON GITHUB!!

	private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/firefly/firefly_off.png");
	private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);
	private static final float Y_OFFSET = 1F;
	private static final Quaternionf ONE_HUNDRED_EIGHTY_QUAT = Axis.YP.rotationDegrees(180.0F);
	private static final float PI = (float) Math.PI;

	public OstrichRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	public static void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay, float xOffset, float yOffset, float zOffset, Quaternionf rotation) {
		poseStack.pushPose();
		poseStack.translate(xOffset, yOffset, zOffset);
		poseStack.mulPose(rotation);
		poseStack.mulPose(ONE_HUNDRED_EIGHTY_QUAT);

		PoseStack.Pose entry = poseStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		VertexConsumer vertexConsumer = buffer.getBuffer(LAYER);

		vertexConsumer
			.vertex(matrix4f, -0.5F, -0.5F, 0.0F)
			.color(255, 255, 255, 255)
			.uv(0, 1)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, -0.5F, 0.0F)
			.color(255, 255, 255, 255)
			.uv(1, 1)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, 0.5F, 0.0F)
			.color(255, 255, 255, 255)
			.uv(1, 0)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, -0.5F, 0.5F, 0.0F)
			.color(255, 255, 255, 255)
			.uv(0, 0)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();

		poseStack.popPose();
	}

	public static int getOverlay(@NotNull Ostrich entity, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
	}

	@Override
	public void render(@NotNull Ostrich entity, float yaw, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
		int overlay = getOverlay(entity, 0);
		render(poseStack, buffer, light, overlay, 0F, Y_OFFSET, 0F, this.entityRenderDispatcher.cameraOrientation());
		if (this.shouldShowName(entity)) {
			this.renderNameTag(entity, entity.getDisplayName(), poseStack, buffer, light);
		}
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Ostrich entity) {
		return TEXTURE;
	}

}
