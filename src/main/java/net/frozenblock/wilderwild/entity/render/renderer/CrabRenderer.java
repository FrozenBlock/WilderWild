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
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.entity.Crab;
import net.frozenblock.wilderwild.entity.render.model.CrabModel;
import net.frozenblock.wilderwild.misc.WilderSharedConstants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class CrabRenderer<T extends Crab> extends MobRenderer<T, CrabModel<T>> {
	private static final ResourceLocation CRAB_LOCATION = WilderSharedConstants.id("textures/entity/crab/crab.png");

	public CrabRenderer(EntityRendererProvider.Context context) {
		this(context, WilderWildClient.CRAB);
	}

	public CrabRenderer(EntityRendererProvider.Context context, ModelLayerLocation layer) {
		super(context, new CrabModel<>(context.bakeLayer(layer)), 0.3F);
	}

	@Override
	protected void setupRotations(@NotNull T crab, @NotNull PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
		poseStack.translate(0, crab.isBaby() ? -0.1 : 0, 0);
		float newYaw = Mth.lerp(
			Mth.lerp(partialTicks, crab.prevClimbDirectionAmount, crab.climbDirectionAmount),
			rotationYaw,
			Mth.lerp(partialTicks, crab.prevClimbAnimY, crab.climbAnimY)
		);
		super.setupRotations(crab, poseStack, ageInTicks, rotationYaw, partialTicks);
	}

	@Override
	protected float getFlipDegrees(@NotNull T livingEntity) {
		return 180.0F;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull T entity) {
		return CRAB_LOCATION;
	}

}

