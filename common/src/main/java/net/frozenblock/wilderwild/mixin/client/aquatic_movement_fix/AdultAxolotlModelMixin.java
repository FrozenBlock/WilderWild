/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.aquatic_movement_fix;

import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.mehvahdjukaar.candlelight.api.ClientOnly;
import net.minecraft.client.model.animal.axolotl.AdultAxolotlModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.AxolotlRenderState;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@ClientOnly
@Mixin(AdultAxolotlModel.class)
public class AdultAxolotlModelMixin {

	@Shadow
	@Final
	private ModelPart head;

	/**
	 * Fixes <a href="https://bugs.mojang.com/browse/MC/issues/MC-226727">MC-226727</a>
	 */
	@Inject(
		method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AxolotlRenderState;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/model/animal/axolotl/AdultAxolotlModel;setupSwimmingAnimation(FFF)V",
			shift = At.Shift.BEFORE
		)
	)
	private void wilderWild$alterWalkMovementThresholdInWater(AxolotlRenderState state, CallbackInfo info) {
		if (!WWEntityConfig.AXOLOTL_HEAD_ROTATION_FIX.get()) return;

		final float notMovingProgress = 1F - state.movingFactor;
		this.head.xRot += state.xRot * Mth.DEG_TO_RAD * notMovingProgress;
	}
}
