/*
 * Copyright 2025 FrozenBlock
 * This file is part of Wilder Wild.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see <https://www.gnu.org/licenses/>.
 */

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class FireflyRenderer extends EntityRenderer<Firefly> {
	private static final ResourceLocation TEXTURE = WWConstants.id("textures/entity/firefly/firefly_off.png");
	private static final RenderType LAYER = RenderType.entityTranslucent(TEXTURE);

	private static final float Y_OFFSET = 0.155F;
	private static final Quaternionf QUAT_180 = Axis.YP.rotationDegrees(180F);

	public FireflyRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	//CREDIT TO magistermaks ON GITHUB!!
	public static void renderFirefly(
		@NotNull PoseStack poseStack,
		@NotNull MultiBufferSource buffer,
		int packedLight,
		int overlay,
		int age,
		float tickDelta,
		@NotNull FireflyColor color,
		float scale,
		float xOffset,
		float yOffset,
		float zOffset,
		Quaternionf rotation
	) {
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		poseStack.translate(xOffset, yOffset, zOffset);
		poseStack.mulPose(rotation);
		poseStack.mulPose(QUAT_180);

		PoseStack.Pose pose = poseStack.last();
		VertexConsumer vertexConsumer = buffer.getBuffer(LAYER);

		vertexConsumer
			.addVertex(pose, -0.5F, -0.5F, 0F)
			.setColor(1F, 1F, 1F, 1F)
			.setUv(0, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, -0.5F, 0F)
			.setColor(1F, 1F, 1F, 1F)
			.setUv(1, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, 0.5F, 0F)
			.setColor(1F, 1F, 1F, 1F)
			.setUv(1, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, -0.5F, 0.5F, 0F)
			.setColor(1F, 1F, 1F, 1F)
			.setUv(0, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);

		RenderType colorRenderType = RenderType.entityTranslucentEmissive(color.texture());
		vertexConsumer = buffer.getBuffer(colorRenderType);

		float calcColor = (((age + tickDelta) * Mth.PI) * -4F) / 255F;

		vertexConsumer
			.addVertex(pose, -0.5F, -0.5F, 0F)
			.setColor(calcColor, calcColor, calcColor, calcColor)
			.setUv(0, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, -0.5F, 0F)
			.setColor(calcColor, calcColor, calcColor, calcColor)
			.setUv(1, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, 0.5F, 0F)
			.setColor(calcColor, calcColor, calcColor, calcColor)
			.setUv(1, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, -0.5F, 0.5F, 0F)
			.setColor(calcColor, calcColor, calcColor, calcColor)
			.setUv(0, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);

		poseStack.popPose();
	}

	@Override
	public void render(@NotNull Firefly entity, float yaw, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
		float prevScale = entity.getPrevAnimScale();
		float scale = prevScale + (tickDelta * (entity.getAnimScale() - prevScale));

		int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0);

		int age = entity.getFlickerAge();

		poseStack.pushPose();
		float f = entity.getScale();
		poseStack.scale(f, f, f);
		renderFirefly(poseStack, buffer, light, overlay, age, tickDelta, entity.getColorForRendering(), scale, 0F, Y_OFFSET, 0F, this.entityRenderDispatcher.cameraOrientation());

		if (this.shouldShowName(entity)) {
			this.renderNameTag(entity, entity.getDisplayName(), poseStack, buffer, light, tickDelta);
		}
		poseStack.popPose();
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Firefly entity) {
		return TEXTURE;
	}

}
