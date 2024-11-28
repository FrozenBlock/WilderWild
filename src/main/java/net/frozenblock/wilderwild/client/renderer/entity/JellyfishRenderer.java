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

package net.frozenblock.wilderwild.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.wind.api.ClientWindManager;
import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.Jellyfish;
import net.frozenblock.wilderwild.client.model.JellyfishModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class JellyfishRenderer extends MobRenderer<Jellyfish, JellyfishModel<Jellyfish>> {
	private static final ResourceLocation WHITE_TEXTURE = WWConstants.id("textures/entity/jellyfish/white.png");

	public JellyfishRenderer(@NotNull Context context) {
		super(context, new JellyfishModel<>(context.bakeLayer(WWModelLayers.JELLYFISH)), 0.3F);
	}

	@Override
	public void setupRotations(@NotNull Jellyfish jelly, @NotNull PoseStack poseStack, float ageInTicks, float bodyYaw, float tickDelta, float scale) {
		super.setupRotations(jelly, poseStack, ageInTicks, bodyYaw, tickDelta, scale);

		poseStack.translate(0F, -1F * jelly.getAgeScale(), 0F);
		poseStack.scale(0.8F, 0.8F, 0.8F);
		float jellyScale = (jelly.prevScale + tickDelta * (jelly.scale - jelly.prevScale)) * jelly.getAgeScale();
		poseStack.scale(jellyScale, jellyScale, jellyScale);
		JellyfishModel<Jellyfish> model = this.getModel();

		if (jelly.isRGB()) {
			float time = (ClientWindManager.time + tickDelta) * 0.05F;
			model.red = Mth.clamp(Math.abs((time % 6) - 3) - 1, 0, 1);
			model.green = Mth.clamp(Math.abs(((time - 2) % 6) - 3) - 1, 0, 1);
			model.blue = Mth.clamp(Math.abs(((time - 4) % 6) - 3) - 1, 0, 1);
		} else {
			model.red = 1;
			model.green = 1;
			model.blue = 1;
		}
	}

	@Override
	protected int getBlockLightLevel(@NotNull Jellyfish jellyfish, @NotNull BlockPos blockPos) {
		return 15;
	}

	@Override
	@NotNull
	public ResourceLocation getTextureLocation(@NotNull Jellyfish jellyfish) {
		if (jellyfish.isRGB()) return WHITE_TEXTURE;
		return jellyfish.getVariant().texture();
	}
}
