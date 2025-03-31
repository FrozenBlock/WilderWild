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
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.model.CrabModel;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CrabRenderer<T extends Crab> extends MobRenderer<T, CrabModel<T>> {
	private static final ResourceLocation CRAB_DITTO_LOCATION = WWConstants.id("textures/entity/crab/crab_ditto.png");

	public CrabRenderer(EntityRendererProvider.Context context) {
		this(context, WWModelLayers.CRAB);
	}

	public CrabRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new CrabModel<>(context.bakeLayer(layer)), 0.3F);
	}

	@Override
	protected void setupRotations(@NotNull T crab, @NotNull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks, float scale) {
		poseStack.translate(0D, crab.isBaby() ? -0.1D : 0D, 0D);
		super.setupRotations(crab, poseStack, ageInTicks, rotationYaw, partialTicks, scale);
	}

	@Override
	protected float getFlipDegrees(@NotNull T livingEntity) {
		return 180F;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T entity) {
		return !entity.isDitto() ? entity.getVariantForRendering().texture() : CRAB_DITTO_LOCATION;
	}

}

