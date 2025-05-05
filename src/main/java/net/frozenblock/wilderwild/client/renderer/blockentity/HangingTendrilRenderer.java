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

package net.frozenblock.wilderwild.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.lib.block.api.entity.BillboardBlockEntityRenderer;
import net.frozenblock.wilderwild.block.entity.HangingTendrilBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.config.WWBlockConfig;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class HangingTendrilRenderer<T extends HangingTendrilBlockEntity> extends BillboardBlockEntityRenderer<T> {

	public HangingTendrilRenderer(@NotNull Context ctx) {
		super(ctx);
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return BillboardBlockEntityRenderer.getTexturedModelData();
	}

	@Override
	public void render(@NotNull T entity, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay, Vec3 cameraPos) {
		if (WWBlockConfig.Client.BILLBOARD_TENDRILS) {
			super.render(entity, partialTick, poseStack, buffer, light, overlay, cameraPos);
		}
	}

	@Override
	@NotNull
	public ResourceLocation getTexture(@NotNull T entity) {
		return entity.getClientTexture();
	}

	@Override
	@NotNull
	public ModelPart getRoot(@NotNull Context ctx) {
		return ctx.bakeLayer(WWModelLayers.HANGING_TENDRIL);
	}
}
