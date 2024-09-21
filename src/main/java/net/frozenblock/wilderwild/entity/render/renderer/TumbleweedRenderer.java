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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.entity.Tumbleweed;
import net.frozenblock.wilderwild.entity.render.model.TumbleweedModel;
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

@Environment(EnvType.CLIENT)
public class TumbleweedRenderer extends MobRenderer<Tumbleweed, TumbleweedModel<Tumbleweed>> {
	private final ItemRenderer itemRenderer;

	public TumbleweedRenderer(@NotNull Context context) {
		super(context, new TumbleweedModel<>(context.bakeLayer(WWModelLayers.TUMBLEWEED)), 0.6F);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	@NotNull
	public Vec3 getRenderOffset(@NotNull Tumbleweed entity, float partialTicks) {
		return new Vec3(0D, 0.2375D, 0D);
	}

	@Override
	public void render(@NotNull Tumbleweed entity, float entityYaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight) {
		super.render(entity, entityYaw, partialTick, poseStack, buffer, packedLight);
		ItemStack stack = entity.getVisibleItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(entity.itemX, 0.4375D, entity.itemZ);
			poseStack.mulPose(Axis.XP.rotation(-Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * Mth.DEG_TO_RAD));
			poseStack.pushPose();
			poseStack.mulPose(Axis.ZP.rotation(Mth.lerp(partialTick, entity.prevRoll, entity.roll) * Mth.DEG_TO_RAD));
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), 1);
			poseStack.popPose();
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(@NotNull Tumbleweed entityLiving, @NotNull PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTick) {
		if (WWEntityConfig.Client.TUMBLEWEED_ROTATES_TO_LOOK_DIRECTION) {
			matrixStack.mulPose(Axis.YP.rotationDegrees(180F - rotationYaw));
		}
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Tumbleweed entity) {
		return WWConstants.id("textures/entity/tumbleweed/tumbleweed.png");
	}

}
