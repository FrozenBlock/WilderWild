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
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
import net.frozenblock.wilderwild.client.WWModelLayers;
import net.frozenblock.wilderwild.client.renderer.entity.FireflyRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class DisplayLanternRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T> {
	private final ItemRenderer itemRenderer;

	public DisplayLanternRenderer(@NotNull Context ctx) {
		ctx.bakeLayer(WWModelLayers.DISPLAY_LANTERN);
		this.itemRenderer = ctx.getItemRenderer();
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		return LayerDefinition.create(new MeshDefinition(), 64, 64);
	}

	@Override
	public void render(@NotNull T lantern, float partialTick, @NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int light, int overlay, Vec3 cameraPos) {
		ItemStack stack = lantern.getItem();
		if (!stack.isEmpty()) {
			poseStack.pushPose();
			poseStack.translate(0.5F, lantern.isHanging() ? 0.25F : 0.125F, 0.5F);
			poseStack.scale(0.7F, 0.7F, 0.7F);
			poseStack.mulPose(Axis.YP.rotation((lantern.age + partialTick) / 20F));
			this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, poseStack, buffer, lantern.getLevel(), 1);
			poseStack.popPose();
		} else {
			for (DisplayLanternBlockEntity.Occupant occupant : lantern.getFireflies()) {
				if (occupant.canRender()) {
					double ageDelta = occupant.age + partialTick;
					float calcColor = (float) (((ageDelta) * Mth.PI) * -4F) / 255F;

					FireflyRenderer.renderFirefly(
						poseStack,
						buffer,
						light,
						overlay,
						calcColor,
						occupant.getColorForRendering().assetInfo().texturePath(),
						1F,
						(float) occupant.pos.x,
						(lantern.isHanging() ? 0.38F : 0.225F) + (float) Math.sin(ageDelta * 0.03F) * 0.15F,
						(float) occupant.pos.z,
						Minecraft.getInstance().gameRenderer.getMainCamera().rotation()
					);
				}
			}
		}
	}

}
