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

package net.frozenblock.wilderwild.mixin.client.allay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderAllay;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.minecraft.client.renderer.entity.AllayRenderer;
import net.minecraft.client.renderer.entity.state.AllayRenderState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(AllayRenderer.class)
public class AllayRendererMixin {

	@ModifyExpressionValue(
		method = "extractRenderState(Lnet/minecraft/world/entity/animal/allay/Allay;Lnet/minecraft/client/renderer/entity/state/AllayRenderState;F)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/allay/Allay;isDancing()Z",
			ordinal = 0
		),
		require = 0
	)
	private boolean wilderWild$alterDanceCheck(boolean original) {
		return original && !WWEntityConfig.Client.ALLAY_KEYFRAME_DANCE;
	}

	@Inject(
		method = "extractRenderState(Lnet/minecraft/world/entity/animal/allay/Allay;Lnet/minecraft/client/renderer/entity/state/AllayRenderState;F)V",
		at = @At("TAIL")
	)
	private void extractAnimation(Allay allay, AllayRenderState renderState, float partialTick, CallbackInfo ci) {
		WilderAllay wilderAllay = (WilderAllay) allay;
		WilderAllay wilderRenderState = (WilderAllay) renderState;

		wilderRenderState.wilderWild$getDancingAnimationState().copyFrom(wilderAllay.wilderWild$getDancingAnimationState());
	}
}
