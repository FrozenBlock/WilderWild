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

package net.frozenblock.wilderwild.entity.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderWildClient;
import net.frozenblock.wilderwild.block.entity.DisplayLanternBlockEntity;
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
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

@Environment(EnvType.CLIENT)
public class DisplayLanternBlockEntityRenderer<T extends DisplayLanternBlockEntity> implements BlockEntityRenderer<T> {
	private static final float pi = (float) Math.PI;
	private final ItemRenderer itemRenderer;

	public DisplayLanternBlockEntityRenderer(@NotNull Context ctx) {
		ctx.bakeLayer(WilderWildClient.DISPLAY_LANTERN);
		this.itemRenderer = ctx.getItemRenderer();
	}

	@NotNull
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		return LayerDefinition.create(modelData, 64, 64);
	}

	@Override
	public void render(@NotNull T lantern, float partialTick, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
		Optional<ItemStack> stack = lantern.getItem();
		if (!lantern.invEmpty() && stack.isPresent()) {
			matrices.pushPose();
			double extraHeight = lantern.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.25 : 0.125;
			matrices.translate(0.5, extraHeight, 0.5);
			matrices.scale(0.7F, 0.7F, 0.7F);
			float n = (lantern.age + partialTick) / 20;
			matrices.mulPose(Axis.YP.rotation(n));
			this.itemRenderer.renderStatic(stack.get(), ItemDisplayContext.GROUND, light, OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, lantern.getLevel(), 1);
			matrices.popPose();
		} else {
			double extraHeight = lantern.getBlockState().getValue(BlockStateProperties.HANGING) ? 0.38 : 0.225;
			for (DisplayLanternBlockEntity.FireflyInLantern entity : lantern.getFireflies()) {
				boolean nectar = entity.getCustomName().toLowerCase().contains("nectar");
				int age = entity.age;
				double ageDelta = age + partialTick;
				boolean flickers = entity.flickers;
				FireflyRenderer.renderFirefly(matrices, vertexConsumers, light, nectar, overlay, age, flickers, entity.getColor(), (ageDelta) * pi, 1F, (float)entity.pos.x, (float) (extraHeight + Math.sin(ageDelta * 0.03) * 0.15), (float)entity.pos.z, Minecraft.getInstance().gameRenderer.getMainCamera().rotation());
			}
		}
	}

}
