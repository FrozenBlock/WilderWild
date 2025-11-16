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

package net.frozenblock.wilderwild.mixin.entity.allay;

import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderAllay;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Allay.class)
public class AllayMixin implements WilderAllay {

	@Unique
	private final AnimationState wilderWild$dancingAnimationState = new AnimationState();

	@Shadow
	private float dancingAnimationTicks;

	@Unique
	@Override
	public AnimationState wilderWild$dancingAnimationState() {
		return this.wilderWild$dancingAnimationState;
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/allay/Allay;isSpinning()Z",
			shift = At.Shift.BEFORE
		)
	)
	private void wilderWild$animateDance(CallbackInfo info) {
		this.wilderWild$dancingAnimationState().startIfStopped((int) this.dancingAnimationTicks);
		this.wilderWild$dancingAnimationState().stop();
	}

	@Inject(
		method = "tick",
		at = @At(
			value = "CONSTANT",
			args = "floatValue=0",
			ordinal = 0
		),
		slice = @Slice(
			from = @At(
				value = "FIELD",
				target = "Lnet/minecraft/world/entity/animal/allay/Allay;dancingAnimationTicks:F",
				ordinal = 2
			)
		)
	)
	private void wilderWild$stopDance(CallbackInfo info) {
		this.wilderWild$dancingAnimationState().stop();
	}

}
