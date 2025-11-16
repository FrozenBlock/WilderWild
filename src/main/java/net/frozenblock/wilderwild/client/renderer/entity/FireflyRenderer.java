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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.NoOpModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.FireflyRenderState;
import net.frozenblock.wilderwild.entity.Firefly;
import net.frozenblock.wilderwild.entity.variant.firefly.FireflyColor;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class FireflyRenderer extends MobRenderer<Firefly, FireflyRenderState, NoOpModel<FireflyRenderState>> {
	private static final Identifier TEXTURE = WWConstants.id("textures/entity/firefly/firefly_base.png");
	private static final RenderType LAYER = RenderTypes.entityTranslucent(TEXTURE);

	private static final float Y_OFFSET = 0.155F;
	private static final Quaternionf QUAT_180 = Axis.YP.rotationDegrees(180F);

	public FireflyRenderer(Context context) {
		super(context, new NoOpModel<>(context.bakeLayer(WWModelLayers.FIREFLY)), 0.15F);
	}

	public static void submitFireflyWithoutRenderState(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		Quaternionf cameraOrientation,
		FireflyColor color,
		float calcColor,
		float scale,
		float xOffset,
		float yOffset,
		float zOffset,
		int light,
		int overlay
	) {
		setupPoseStack(poseStack, scale, xOffset, yOffset, zOffset, cameraOrientation);

		collector
			.order(0)
			.submitCustomGeometry(
				poseStack,
				LAYER,
				(pose, vertexConsumer) -> renderFireflyBase(pose, vertexConsumer, light, overlay)
			);

		collector
			.order(1)
			.submitCustomGeometry(
				poseStack,
				RenderTypes.entityTranslucentEmissive(color.resourceTexture().texturePath()),
				(pose, vertexConsumer) -> renderFireflyColor(pose, vertexConsumer, light, overlay, calcColor)
			);

		poseStack.popPose();
	}

	public static void submitFirefly(
		PoseStack poseStack,
		SubmitNodeCollector collector,
		Quaternionf cameraOrientation,
		FireflyRenderState renderState
	) {
		setupPoseStack(poseStack, renderState.scale * renderState.animScale, 0F, Y_OFFSET, 0F, cameraOrientation);

		final int light = renderState.lightCoords;
		final int overlay = getOverlayCoords(renderState, 0F);
		collector
			.order(0)
			.submitCustomGeometry(
				poseStack,
				LAYER,
				(pose, vertexConsumer) -> renderFireflyBase(pose, vertexConsumer, light, overlay)
			);

		collector
			.order(1)
			.submitCustomGeometry(
				poseStack,
				RenderTypes.entityTranslucentEmissive(renderState.color.resourceTexture().texturePath()),
				(pose, vertexConsumer) -> renderFireflyColor(pose, vertexConsumer, light, overlay, renderState.calcColor)
			);

		poseStack.popPose();
	}

	public static void renderFireflyBase(PoseStack.Pose pose, VertexConsumer vertexConsumer, int packedLight, int overlay) {
		render(pose, vertexConsumer, 1F, packedLight, overlay);
	}

	public static void renderFireflyColor(PoseStack.Pose pose, VertexConsumer vertexConsumer, int packedLight, int overlay, float calcColor) {
		render(pose, vertexConsumer, calcColor, packedLight, overlay);
	}

	@Override
	public void submit(
		FireflyRenderState renderState,
		PoseStack poseStack,
		SubmitNodeCollector collector,
		CameraRenderState cameraRenderState
	) {
		Quaternionf cameraOrientation = cameraRenderState.orientation;
		submitFirefly(poseStack, collector, cameraOrientation, renderState);

		if (renderState.nameTag != null) this.submitNameTag(renderState, poseStack, collector, cameraRenderState);
	}

	@Override
	public Identifier getTextureLocation(FireflyRenderState renderState) {
		return renderState.color.resourceTexture().texturePath();
	}

	@Override
	public FireflyRenderState createRenderState() {
		return new FireflyRenderState();
	}

	@Override
	public void extractRenderState(Firefly firefly, FireflyRenderState renderState, float partialTick) {
		super.extractRenderState(firefly, renderState, partialTick);
		renderState.animScale = Mth.lerp(partialTick, firefly.getPrevAnimScale(), firefly.getAnimScale());
		renderState.color = firefly.getColorForRendering();
		renderState.calcColor = (((firefly.getFlickerAge() + partialTick) * Mth.PI) * -4F) / 255F;
	}

	private static void setupPoseStack(PoseStack poseStack, float scale, float xOffset, float yOffset, float zOffset, Quaternionf rotation) {
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		poseStack.translate(xOffset, yOffset, zOffset);
		poseStack.mulPose(rotation);
		poseStack.mulPose(QUAT_180);
	}

	//CREDIT TO magistermaks ON GITHUB!!
	private static void render(PoseStack.Pose pose, VertexConsumer vertexConsumer, float color, int packedLight, int overlay) {
		vertexConsumer
			.addVertex(pose, -0.5F, -0.5F, 0F)
			.setColor(color, color, color, color)
			.setUv(0, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, -0.5F, 0F)
			.setColor(color, color, color, color)
			.setUv(1, 1)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, 0.5F, 0.5F, 0F)
			.setColor(color, color, color, color)
			.setUv(1, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
		vertexConsumer
			.addVertex(pose, -0.5F, 0.5F, 0F)
			.setColor(color, color, color, color)
			.setUv(0, 0)
			.setOverlay(overlay)
			.setLight(packedLight)
			.setNormal(pose, 0F, 1F, 0F);
	}
}
