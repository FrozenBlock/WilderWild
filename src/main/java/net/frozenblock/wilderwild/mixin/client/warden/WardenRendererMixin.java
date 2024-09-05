/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.entity.impl.SwimmingWardenInterface;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenState;
import net.frozenblock.wilderwild.entity.render.animation.WilderWarden;
import net.minecraft.client.renderer.entity.WardenRenderer;
import net.minecraft.client.renderer.entity.state.WardenRenderState;
import net.minecraft.world.entity.monster.warden.Warden;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WardenRenderer.class)
public class WardenRendererMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/warden/Warden;Lnet/minecraft/client/renderer/entity/state/WardenRenderState;F)V", at = @At("TAIL"))
	private void extractWilderWarden(Warden warden, WardenRenderState renderState, float partialTick, CallbackInfo ci) {
		WilderWarden wilderWarden = (WilderWarden) warden;
		WilderWarden wilderRenderState = (WilderWarden) renderState;

		wilderRenderState.wilderWild$getDyingAnimationState().copyFrom(wilderWarden.wilderWild$getDyingAnimationState());
		wilderRenderState.wilderWild$getSwimmingDyingAnimationState().copyFrom(wilderWarden.wilderWild$getSwimmingDyingAnimationState());
		wilderRenderState.wilderWild$getKirbyDeathAnimationState().copyFrom(wilderWarden.wilderWild$getKirbyDeathAnimationState());
		wilderRenderState.wilderWild$setDeathTicks(wilderWarden.wilderWild$getDeathTicks());
		wilderRenderState.wilderWild$setIsStella(wilderWarden.wilderWild$isStella());

		// check if disabled in mixins config
		if (warden instanceof SwimmingWardenInterface swimmingWarden) {
			SwimmingWardenState swimmingWardenState = (SwimmingWardenState) wilderRenderState;

			swimmingWardenState.wilderWild$setSwimAmount(warden.getSwimAmount(partialTick));
			swimmingWardenState.wilderWild$setWadingProgress(swimmingWarden.wilderWild$getWadingProgress(partialTick));
		}
	}
}
