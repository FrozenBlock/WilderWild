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

package net.frozenblock.wilderwild.mixin.server.general;

import net.frozenblock.wilderwild.config.EntityConfig;
import net.frozenblock.wilderwild.entity.render.animations.WilderAllay;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.allay.Allay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Allay.class)
public class AllayMixin implements WilderAllay {

	@Unique
	private final AnimationState wilderWild$dancingAnimationState = new AnimationState();
	@Shadow
	private float dancingAnimationTicks;

	@Unique
	@Override
	public AnimationState wilderWild$getDancingAnimationState() {
		return this.wilderWild$dancingAnimationState;
	}

	@Inject(method = "tick", at = @At(value = "TAIL"))
	private void wilderWild$tickDancing(CallbackInfo info) {
		Allay allay = Allay.class.cast(this);
		if (allay.level().isClientSide && EntityConfig.get().allay.keyframeAllayDance) {
			if (allay.isDancing()) {
				this.wilderWild$getDancingAnimationState().startIfStopped((int) this.dancingAnimationTicks);
			} else {
				this.wilderWild$getDancingAnimationState().stop();
			}
		}
	}

}
