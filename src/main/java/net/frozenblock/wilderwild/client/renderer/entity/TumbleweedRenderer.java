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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.TumbleweedModel;
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
			Quaternionf quaternionf = new Quaternionf().rotationXYZ(
				Mth.lerp(partialTick, entity.prevPitch, entity.pitch) * Mth.DEG_TO_RAD,
				0F,
				Mth.lerp(partialTick, entity.prevRoll, entity.roll) * Mth.DEG_TO_RAD
			);
			poseStack.mulPose(quaternionf);
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, entity.level(), 1);
			poseStack.popPose();
		}
	}

	@Override
	protected void setupRotations(@NotNull Tumbleweed entityLiving, @NotNull PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTick, float scale) {
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
