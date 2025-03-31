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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.TumbleweedModel;
import net.frozenblock.wilderwild.client.renderer.entity.state.TumbleweedRenderState;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends MobRenderer<Tumbleweed, TumbleweedRenderState, TumbleweedModel> {
	private final ItemRenderer itemRenderer;

	public TumbleweedRenderer(@NotNull Context context) {
		super(context, new TumbleweedModel(context.bakeLayer(WWModelLayers.TUMBLEWEED)), 0.6F);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	@NotNull
	public Vec3 getRenderOffset(TumbleweedRenderState entityRenderState) {
		return new Vec3(0D, 0.2375D, 0D);
	}

	@Override
	public void render(TumbleweedRenderState renderState, PoseStack poseStack, MultiBufferSource buffer, int i) {
		super.render(renderState, poseStack, buffer, i);
		ItemStack stack = renderState.visibleItem;
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(renderState.itemX, 0.4375D, renderState.itemZ);
			Quaternionf quaternionf = new Quaternionf().rotationXYZ(
				renderState.pitch * Mth.DEG_TO_RAD,
				0F,
				renderState.roll * Mth.DEG_TO_RAD
			);
			poseStack.mulPose(quaternionf);
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, i, OverlayTexture.NO_OVERLAY, poseStack, buffer, renderState.level, 1);
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(TumbleweedRenderState livingEntityRenderState, @NotNull PoseStack poseStack, float rotationYaw, float scale) {
		poseStack.translate(0D, -1.3D, 0D);
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) poseStack.mulPose(Axis.YP.rotationDegrees(180F - rotationYaw));
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull TumbleweedRenderState renderState) {
		return WWConstants.id("textures/entity/tumbleweed/tumbleweed.png");
	}

	@Override
	@NotNull
	public TumbleweedRenderState createRenderState() {
		return new TumbleweedRenderState();
	}

	@Override
	public void extractRenderState(Tumbleweed entity, TumbleweedRenderState renderState, float partialTick) {
		super.extractRenderState(entity, renderState, partialTick);
		renderState.tumbleRot = Mth.lerp(partialTick, entity.prevTumble, entity.tumble) * Mth.DEG_TO_RAD;
		renderState.itemX = entity.itemX;
		renderState.itemZ = entity.itemZ;
		renderState.pitch = -Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * Mth.DEG_TO_RAD;
		renderState.roll = Mth.lerp(partialTick, entity.prevRoll, entity.roll) * Mth.DEG_TO_RAD;
		renderState.visibleItem = entity.getVisibleItem();
		renderState.level = entity.level();
	}
}
