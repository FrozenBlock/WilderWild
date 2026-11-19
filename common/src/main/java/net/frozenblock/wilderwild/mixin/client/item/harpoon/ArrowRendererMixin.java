/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.item.harpoon;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.frozenblock.lib.renderer.rendertype.FrozenLibRenderTypes;
import net.frozenblock.wilderwild.client.renderer.entity.state.HarpoonRenderState;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@ClientOnly
@Mixin(ArrowRenderer.class)
public class ArrowRendererMixin {

	@WrapOperation(
		method = "submit(Lnet/minecraft/client/renderer/entity/state/ArrowRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/SubmitNodeCollector;submitModel(Lnet/minecraft/client/model/Model;Ljava/lang/Object;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/resources/Identifier;III)V"
		)
	)
	public void wilderWild$submitFoilHarpoon(
		SubmitNodeCollector instance,
		Model model,
		Object state,
		PoseStack poseStack,
		Identifier texture,
		int lightCoords,
		int overlayCoords,
		int outlineColor,
		Operation<Void> original
	) {
		if (state instanceof HarpoonRenderState harpoonState && harpoonState.isFoil) {
			instance.submitModel(
				model,
				state,
				poseStack,
				FrozenLibRenderTypes.entityCutoutGlint(texture),
				lightCoords,
				overlayCoords,
				outlineColor
			);
			return;
		}

		original.call(instance, model, state, poseStack, texture, lightCoords, overlayCoords, outlineColor);
	}
}
