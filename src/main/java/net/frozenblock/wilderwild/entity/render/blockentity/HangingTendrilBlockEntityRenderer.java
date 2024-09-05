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

package net.frozenblock.wilderwild.entity.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.api.entity.BillboardBlockEntityRenderer;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.client.rendering.WWModelLayers;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HangingTendrilBlockEntityRenderer<T extends HangingTendrilBlockEntity> extends BillboardBlockEntityRenderer<T> {

	public HangingTendrilBlockEntityRenderer(@NotNull Context ctx) {
		super(ctx);
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return BillboardBlockEntityRenderer.getTexturedModelData();
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		if (WWBlockConfig.get().billboardTendrils) {
			super.render(entity, partialTick, poseStack, buffer, light, overlay);
		}
	}

	@Override
	@NotNull
	public ResourceLocation getTexture(@NotNull T entity) {
		return entity.texture;
	}

	@Override
	@NotNull
	public ModelPart getRoot(@NotNull Context ctx) {
		return ctx.bakeLayer(WWModelLayers.HANGING_TENDRIL);
	}
}
