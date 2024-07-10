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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.entity.api.rendering.FrozenRenderType;
import net.frozenblock.wilderwild.WilderConstants;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornVibration;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer<T extends AncientHornVibration> extends EntityRenderer<T> {
	private static final ResourceLocation TEXTURE = WilderConstants.id("textures/entity/ancient_horn_projectile.png");
	private final AncientHornProjectileModel model;

	public AncientHornProjectileRenderer(@NotNull EntityRendererProvider.Context context) {
		super(context);
		this.model = new AncientHornProjectileModel(context.bakeLayer(WilderWildClient.ANCIENT_HORN_PROJECTILE_LAYER));
	}

	@Override
	public void render(@NotNull T projectile, float yaw, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light) {
		poseStack.pushPose();
		poseStack.mulPose(Axis.YP.rotationDegrees((projectile.yRotO + partialTick * (projectile.getYRot() - projectile.yRotO)) - 90F));
		poseStack.mulPose(Axis.ZP.rotationDegrees((projectile.xRotO + partialTick * (projectile.getXRot() - projectile.xRotO)) + 90F));
		VertexConsumer vertexConsumer = buffer.getBuffer(FrozenRenderType.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(getTextureLocation(projectile), false));

		float multiplier = projectile.getBoundingBoxMultiplier(partialTick);
		float scale = (multiplier * 0.5F) + 1F;
		float alpha = 1F - (multiplier / 15F);
		float correctedAlpha = Math.max(alpha, 0.01F);
		int color = FastColor.ARGB32.colorFromFloat(correctedAlpha, 1F, 1F, 1F);

		poseStack.scale(scale, scale, scale);

		this.model.partialTick = partialTick;
		this.model.projectile = projectile;
		this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, color);

		poseStack.popPose();
		super.render(projectile, yaw, partialTick, poseStack, buffer, light);
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T entity) {
		return TEXTURE;
	}

	@Override
	protected int getBlockLightLevel(@NotNull T entity, @NotNull BlockPos blockPos) {
		return 15;
	}
}
