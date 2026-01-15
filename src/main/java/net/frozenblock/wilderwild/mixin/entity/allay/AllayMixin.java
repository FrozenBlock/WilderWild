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

package net.frozenblock.wilderwild.mixin.entity.allay;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.client.animation.definitions.impl.WilderAllay;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

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

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/animal/allay/Allay;isDancing()Z"
		)
	)
	private boolean wilderWild$keyframeDance(boolean original) {
		if (original) {
			this.wilderWild$dancingAnimationState().startIfStopped((int) this.dancingAnimationTicks);
		} else {
			this.wilderWild$dancingAnimationState().stop();
		}
		return original;
	}

}
