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

package net.frozenblock.wilderwild.mixin.client.warden;

import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderWarden;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenInterface;
import net.frozenblock.wilderwild.entity.impl.SwimmingWardenState;
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
	private void extractWilderWarden(Warden warden, WardenRenderState renderState, float partialTick, CallbackInfo info) {
		if (!(warden instanceof WilderWarden wilderWarden) || !(renderState instanceof WilderWarden wilderRenderState)) return;
		wilderRenderState.wilderWild$dyingAnimationState().copyFrom(wilderWarden.wilderWild$dyingAnimationState());
		wilderRenderState.wilderWild$swimmingDyingAnimationState().copyFrom(wilderWarden.wilderWild$swimmingDyingAnimationState());
		wilderRenderState.wilderWild$kirbyDeathAnimationState().copyFrom(wilderWarden.wilderWild$kirbyDeathAnimationState());
		wilderRenderState.wilderWild$setDeathTicks(wilderWarden.wilderWild$getDeathTicks());
		wilderRenderState.wilderWild$setStella(wilderWarden.wilderWild$isStella());

		if (!(warden instanceof SwimmingWardenInterface swimmingWarden) || !(renderState instanceof SwimmingWardenState swimmingRenderState)) return;
		swimmingRenderState.wilderWild$setSwimAmount(warden.getSwimAmount(partialTick));
		swimmingRenderState.wilderWild$setWadingProgress(swimmingWarden.wilderWild$getWadingProgress(partialTick));
	}
}
