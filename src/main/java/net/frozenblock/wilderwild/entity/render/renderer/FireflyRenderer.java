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
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import java.util.function.Supplier;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.variant.FireflyColor;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.frozenblock.wilderwild.registry.WilderRegistry;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

public class FireflyRenderer extends EntityRenderer<Firefly> {
	//CREDIT TO magistermaks ON GITHUB!!

	private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/firefly/firefly_off.png");
	private static final RenderType LAYER = RenderType.entityCutout(TEXTURE);
	private static final RenderType NECTAR_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar.png"));
	private static final RenderType NECTAR_FLAP_LAYER = RenderType.entityCutout(WilderSharedConstants.id("textures/entity/firefly/nectar_wings_down.png"));
	private static final RenderType NECTAR_OVERLAY = RenderType.entityTranslucentEmissive(WilderSharedConstants.id("textures/entity/firefly/nectar_overlay.png"), true);
	private static final float Y_OFFSET = 0.155F;
	private static final Quaternionf ONE_HUNDRED_EIGHTY_QUAT = Axis.YP.rotationDegrees(180.0F);
	private static final float PI = (float) Math.PI;
	public static final Object2ObjectMap<ResourceLocation, RenderType> LAYERS = new Object2ObjectLinkedOpenHashMap<>() {{
		Object2ObjectMap<ResourceLocation, ResourceLocation> colors = new Object2ObjectLinkedOpenHashMap<>();
		WilderRegistry.FIREFLY_COLOR.forEach(color -> colors.put(color.key(), color.texture()));
		colors.forEach((colorKey, texture) -> put(colorKey, RenderType.entityTranslucentEmissive(texture)));
	}};

	public FireflyRenderer(EntityRendererProvider.Context ctx) {
		super(ctx);
	}

	public static void renderFirefly(@NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, boolean nectar, int overlay, int age, boolean flickers, FireflyColor color, double piAgeDelta, float scale, float xOffset, float yOffset, float zOffset, Quaternionf rotation) {
		matrices.pushPose();
		matrices.scale(scale, scale, scale);
		matrices.translate(xOffset, yOffset, zOffset);
		matrices.mulPose(rotation);
		matrices.mulPose(ONE_HUNDRED_EIGHTY_QUAT);

		PoseStack.Pose entry = matrices.last();
		Matrix4f matrix4f = entry.pose();
		Matrix3f matrix3f = entry.normal();
		Supplier<RenderType> nectarLayer = () -> age % 2 == 0 ? NECTAR_LAYER : NECTAR_FLAP_LAYER;
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(nectar ? nectarLayer.get() : LAYER);

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

		if (color != null && LAYERS.get(color.key()) != null) {
			RenderType layer = nectar ? NECTAR_OVERLAY : LAYERS.get(color.key());
			vertexConsumer = vertexConsumers.getBuffer(layer);
		} else {
			vertexConsumer = vertexConsumers.getBuffer(LAYERS.get(FireflyColor.ON.key()));
		}

		int calcColor = flickers ? (int) ((255 * (Math.cos((piAgeDelta) * 0.025))) + 127.5) : (int) Math.max((255 * (Math.cos((piAgeDelta) * 0.05))), 0);

		vertexConsumer
			.vertex(matrix4f, -0.5F, -0.5F, 0.0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(0, 1)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, -0.5F, 0.0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(1, 1)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, 0.5F, 0.5F, 0.0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(1, 0)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();
		vertexConsumer
			.vertex(matrix4f, -0.5F, 0.5F, 0.0F)
			.color(calcColor, calcColor, calcColor, calcColor)
			.uv(0, 0)
			.overlayCoords(overlay)
			.uv2(light)
			.normal(matrix3f, 0.0F, 1.0F, 0.0F)
			.endVertex();

		matrices.popPose();
	}

	public static int getOverlay(@NotNull Firefly entity, float whiteOverlayProgress) {
		return OverlayTexture.pack(OverlayTexture.u(whiteOverlayProgress), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
	}

	@Override
	public void render(@NotNull Firefly entity, float yaw, float tickDelta, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
		boolean nectar = false;

		Component component = entity.getCustomName();
		if (component != null) {
			nectar = component.getString().toLowerCase().contains("nectar");
		}

		float prevScale = entity.getPrevScale();
		float scale = prevScale + (tickDelta * (entity.getScale() - prevScale));

		int overlay = getOverlay(entity, 0);

		int age = entity.getFlickerAge();
		float ageDelta = age + tickDelta;
		boolean flickers = entity.flickers();


		renderFirefly(matrices, vertexConsumers, light, nectar, overlay, age, flickers, entity.getColor(), (ageDelta) * PI, scale, 0F, Y_OFFSET, 0F, this.entityRenderDispatcher.cameraOrientation());

		if (this.shouldShowName(entity)) {
			this.renderNameTag(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
		}
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Firefly entity) {
		return TEXTURE;
	}

}
