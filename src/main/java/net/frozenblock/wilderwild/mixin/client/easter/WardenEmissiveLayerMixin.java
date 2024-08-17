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

package net.frozenblock.wilderwild.mixin.client.easter;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.entity.render.animations.WilderWarden;
import net.minecraft.client.model.WardenModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.layers.WardenEmissiveLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WardenEmissiveLayer.class)
public abstract class WardenEmissiveLayerMixin<T extends Warden, M extends WardenModel<T>> extends RenderLayer<T, M> {

	@Shadow
	@Final
	public ResourceLocation texture;

	public WardenEmissiveLayerMixin(RenderLayerParent<T, M> context) {
		super(context);
	}

	@Inject(
		at = @At("HEAD"),
		method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/monster/warden/Warden;FFFFFF)V",
		cancellable = true
	)
	public void wilderWild$cancelIfStella(
		PoseStack matrices, MultiBufferSource vertexConsumers, int i, T warden, float f, float g, float h, float j, float k, float l, CallbackInfo info
	) {
		if (warden instanceof WilderWarden wilderWarden && wilderWarden.wilderWild$isStella()) {
			info.cancel();
		}
	}
}
