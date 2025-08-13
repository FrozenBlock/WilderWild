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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class FireflyRenderer extends MobRenderer<Firefly, FireflyRenderState, NoOpModel<FireflyRenderState>> {
	private static final ResourceLocation TEXTURE = WWConstants.id("textures/entity/firefly/firefly_off.png");
	private static final ResourceLocation TEXTURE_RIGHT= WWConstants.id("textures/entity/firefly/firefly_off_right.png");
	private static final RenderType LAYER = RenderType.entityTranslucent(TEXTURE);
	private static final RenderType LAYER_RIGHT = RenderType.entityTranslucent(TEXTURE_RIGHT);

	private static final float Y_OFFSET = 0.155F;
	private static final Quaternionf QUAT_180 = Axis.YP.rotationDegrees(180F);

	public FireflyRenderer(EntityRendererProvider.Context context) {
		super(context, new NoOpModel<>(context.bakeLayer(WWModelLayers.FIREFLY)), 0.15F);
	}

	private static void renderFirefly(
		@NotNull PoseStack poseStack,
		@NotNull SubmitNodeCollector submitNodeCollector,
		int packedLight,
		int overlay,
		float calcColor,
		@NotNull ResourceLocation colorTexture
	) {
		submitNodeCollector.submitCustomGeometry(
			poseStack,
			LAYER,
			(pose, vertexConsumer) -> render(pose, vertexConsumer, 1F - calcColor, packedLight, overlay)
		);
		if (WWConstants.ORIGINAL_FIREFLIES) {
			submitNodeCollector.submitCustomGeometry(
				poseStack,
				LAYER_RIGHT,
				(pose, vertexConsumer) -> render(pose, vertexConsumer, 1F, packedLight, overlay)
			);
		}
		submitNodeCollector.submitCustomGeometry(
			poseStack,
			RenderType.entityTranslucentEmissive(colorTexture),
			(pose, vertexConsumer) -> render(pose, vertexConsumer, calcColor, packedLight, overlay)
		);
	}

	public static void renderFirefly(
		@NotNull PoseStack poseStack,
		@NotNull MultiBufferSource bufferSource,
		int packedLight,
		int overlay,
		float calcColor,
		@NotNull ResourceLocation colorTexture,
		float scale,
		float xOffset,
		float yOffset,
		float zOffset,
		Quaternionf rotation
	) {
		setupPoseStack(poseStack, scale, xOffset, yOffset, zOffset, rotation);
		PoseStack.Pose pose = poseStack.last();
		render(pose, bufferSource.getBuffer(LAYER), 1F, packedLight, overlay);
		if (WWConstants.ORIGINAL_FIREFLIES) render(pose, bufferSource.getBuffer(LAYER_RIGHT), 1F, packedLight, overlay);
		render(pose, bufferSource.getBuffer(RenderType.entityTranslucentEmissive(colorTexture)), calcColor, packedLight, overlay);
		poseStack.popPose();
	}

	@Override
	public void submit(@NotNull FireflyRenderState renderState, @NotNull PoseStack poseStack, SubmitNodeCollector submitNodeCollector) {
		setupPoseStack(poseStack, renderState.scale * renderState.animScale, 0F, Y_OFFSET, 0F, this.entityRenderDispatcher.cameraOrientation());
		renderFirefly(
			poseStack,
			submitNodeCollector,
			renderState.lightCoords,
			LivingEntityRenderer.getOverlayCoords(renderState, 0F),
			renderState.calcColor,
			this.getTextureLocation(renderState)
		);
		poseStack.popPose();

		if (renderState.nameTag != null) this.submitNameTag(renderState, poseStack, submitNodeCollector);
	}

	@Override
	public @NotNull ResourceLocation getTextureLocation(@NotNull FireflyRenderState renderState) {
		return renderState.color.assetInfo().texturePath();
	}

	@Override
	@NotNull
	public FireflyRenderState createRenderState() {
		return new FireflyRenderState();
	}

	@Override
	public void extractRenderState(Firefly entity, FireflyRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.animScale = Mth.lerp(partialTick, entity.getPrevAnimScale(), entity.getAnimScale());
		renderState.color = entity.getColorForRendering();
		renderState.calcColor = (((entity.getFlickerAge() + partialTick) * Mth.PI) * -4F) / 255F;
	}

	private static void setupPoseStack(@NotNull PoseStack poseStack, float scale, float xOffset, float yOffset, float zOffset, Quaternionf rotation) {
		poseStack.pushPose();
		poseStack.scale(scale, scale, scale);
		poseStack.translate(xOffset, yOffset, zOffset);
		poseStack.mulPose(rotation);
		poseStack.mulPose(QUAT_180);
	}

	//CREDIT TO magistermaks ON GITHUB!!
	private static void render(PoseStack.Pose pose, @NotNull VertexConsumer vertexConsumer, float color, int packedLight, int overlay) {
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
