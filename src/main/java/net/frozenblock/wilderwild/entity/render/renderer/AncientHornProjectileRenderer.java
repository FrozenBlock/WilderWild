/*
 * Copyright 2022-2023 FrozenBlock
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
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.AncientHornProjectile;
import net.frozenblock.wilderwild.entity.render.model.AncientHornProjectileModel;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class AncientHornProjectileRenderer<T extends AncientHornProjectile> extends EntityRenderer<T> {
	private static final ResourceLocation TEXTURE = WilderSharedConstants.id("textures/entity/ancient_horn_projectile.png");
	private final AncientHornProjectileModel model;

	public AncientHornProjectileRenderer(@NotNull EntityRendererProvider.Context context) {
		super(context);
		this.model = new AncientHornProjectileModel(context.bakeLayer(WilderWildClient.ANCIENT_HORN_PROJECTILE_LAYER));
	}

	@Override
	public void render(@NotNull T projectile, float yaw, float partialTick, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light) {
		matrices.pushPose();
		matrices.mulPose(Axis.YP.rotationDegrees((projectile.yRotO + partialTick * (projectile.getYRot() - projectile.yRotO)) - 90.0F));
		matrices.mulPose(Axis.ZP.rotationDegrees((projectile.xRotO + partialTick * (projectile.getXRot() - projectile.xRotO)) + 90.0F));
		VertexConsumer vertexConsumer = vertexConsumers.getBuffer(FrozenRenderType.ENTITY_TRANSLUCENT_EMISSIVE_FIXED.apply(TEXTURE, false));

		float multiplier = projectile.getBoundingBoxMultiplier();
		float scale = multiplier + 1F;
		float alpha = 1.0F - (multiplier / 15F);
		float correctedAlpha = Math.max(alpha, 0.01F);

		matrices.scale(scale, scale, scale);

		this.model.partialTick = partialTick;
		this.model.projectile = projectile;
		this.model.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, correctedAlpha);

		matrices.popPose();
		super.render(projectile, yaw, partialTick, matrices, vertexConsumers, light);
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
