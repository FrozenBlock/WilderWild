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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.entity.render.renderer.FireflyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class DisplayLanternBlockEntityRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T> {
	private final ItemRenderer itemRenderer;

	public DisplayLanternBlockEntityRenderer(@NotNull Context ctx) {
		ctx.bakeLayer(WWModelLayers.DISPLAY_LANTERN);
		this.itemRenderer = ctx.getItemRenderer();
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return LayerDefinition.create(new MeshDefinition(), 64, 64);
	}

	@Override
	public void render(@NotNull T lantern, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay) {
		ItemStack stack = lantern.getItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5F, lantern.clientHanging ? 0.25F : 0.125F, 0.5F);
			poseStack.scale(0.7F, 0.7F, 0.7F);
			poseStack.mulPose(Axis.YP.rotation((lantern.age + partialTick) / 20F));
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, lantern.getLevel(), 1);
			poseStack.popPose();
		} else {
			for (DisplayLanternBlockEntity.Occupant entity : lantern.getFireflies()) {
				double ageDelta = entity.age + partialTick;
				FireflyRenderer.renderFirefly(
					poseStack,
					buffer,
					light,
					entity.getCustomName().toLowerCase().contains("nectar"),
					overlay,
					entity.age,
					partialTick,
					entity.flickers,
					entity.getColor(),
					1F,
					(float) entity.pos.x,
					lantern.clientHanging ? 0.38F : 0.225F + (float) Math.sin(ageDelta * 0.03F) * 0.15F,
					(float) entity.pos.z, Minecraft.getInstance().gameRenderer.getMainCamera().rotation()
				);
			}
		}
	}

}
