/*
 * Copyright 2023-2024 FrozenBlock
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
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class FireflyRenderer extends EntityRenderer<Firefly> {
	//CREDIT TO magistermaks ON GITHUB!!

	public static final Object2ObjectMap<ResourceLocation, RenderType> LAYERS = new Object2ObjectLinkedOpenHashMap<>() {{
		Object2ObjectMap<ResourceLocation, ResourceLocation> colors = new Object2ObjectLinkedOpenHashMap<>();
		WilderRegistry.FIREFLY_COLOR.forEach(color -> colors.put(color.key(), color.texture()));
		colors.forEach((colorKey, texture) -> put(colorKey, RenderType.entityTranslucentEmissive(texture)));
	}};
	private static final ResourceLocation TEXTURE = WilderConstants.id("textures/entity/firefly/firefly_off.png");
	private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);
	private static final RenderType NECTAR_LAYER = RenderType.entityCutout(WilderConstants.id("textures/entity/firefly/nectar.png"));
	private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(WilderConstants.id("textures/entity/firefly/nectar_wings_down.png"));
	private static final RenderType NECTAR_OVERLAY = RenderType.entityTranslucentEmissive(WilderConstants.id("textures/entity/firefly/nectar_overlay.png"), true);
	private static final float Y_OFFSET = 0.155F;
	private static final Quaternionf QUAT_180 = Axis.YP.rotationDegrees(180F);

	public FireflyRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	public static void renderFirefly(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, boolean nectar, int overlay, int age, float tickDelta, boolean flickers, FireflyColor color, float scale, float xOffset, float yOffset, float zOffset, Quaternionf rotation) {
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		poseStack.translate(xOffset, yOffset, zOffset);
		poseStack.mulPose(rotation);
		poseStack.mulPose(QUAT_180);

		PoseStack.Pose entry = poseStack.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		Supplier<RenderType> nectarLayer = () -> age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER;
		VertexConsumer vertexConsumer = buffer.getBuffer(nectar ? nectarLayer.get() : LAYER);

		vertexConsumer
			.vertex(matrix4f, -0.5F, -0.5F, 0F)
			.color(255, 255, 255, 255)
			.uv(0, 1)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, -0.5F, 0F)
			.color(255, 255, 255, 255)
			.uv(1, 1)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, 0.5F, 0F)
			.color(255, 255, 255, 255)
			.uv(1, 0)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, -0.5F, 0.5F, 0F)
			.color(255, 255, 255, 255)
			.uv(0, 0)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();

		if (color != null && LAYERS.get(color.key()) != null) {
			RenderType layer = nectar ? NECTAR_OVERLAY : LAYERS.get(color.key());
			vertexConsumer = buffer.getBuffer(layer);
		} else {
			vertexConsumer = buffer.getBuffer(LAYERS.get(FireflyColor.ON.key()));
		}

		int calcColor = flickers ?
			(int) (((age + tickDelta) * Mth.PI) * -4D) :
			(int) Math.max((255D * (Math.cos(((age + tickDelta) * Mth.PI) * 0.05D))), 0D);

		vertexConsumer
			.vertex(matrix4f, -0.5F, -0.5F, 0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(0, 1)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, -0.5F, 0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(1, 1)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, 0.5F, 0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(1, 0)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, -0.5F, 0.5F, 0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(0, 0)
			.overlayCoords(overlay)
			.uv2(packedLight)
			.normal(matrix3f, 0F, 1F, 0F)
			.endVertex();


		poseStack.popPose();
	}

	public static int getOverlay(@NotNull Firefly entity, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
	}

	@Override
	public void render(@NotNull Firefly entity, float yaw, float tickDelta, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
		boolean nectar = false;

		Component component = entity.getCustomName();
		if (component != null) {
			nectar = component.getString().toLowerCase().contains("nectar");
		}

		float prevScale = entity.getPrevAnimScale();
		float scale = prevScale + (tickDelta * (entity.getAnimScale() - prevScale));

		int overlay = getOverlay(entity, 0);

		int age = entity.getFlickerAge();
		boolean flickers = entity.flickers();


		poseStack.pushPose();
		float f = entity.getScale();
		poseStack.scale(f, f, f);
		renderFirefly(poseStack, buffer, light, nectar, overlay, age, tickDelta, flickers, entity.getColor(), scale, 0F, Y_OFFSET, 0F, this.entityRenderDispatcher.cameraOrientation());

		if (this.shouldShowName(entity)) {
			this.renderNameTag(entity, entity.getDisplayName(), poseStack, buffer, light);
		}
		poseStack.popPose();
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Firefly entity) {
		return TEXTURE;
	}

}
