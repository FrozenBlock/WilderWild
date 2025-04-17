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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
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

