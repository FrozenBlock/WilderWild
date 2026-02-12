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

package net.frozenblock.wilderwild.mixin.entity.axolotl;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = Axolotl.class)
public class AxolotlMixin {

	@WrapOperation(
		method = "tickAnimations",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/WalkAnimationState;isMoving()Z"
		)
	)
	private boolean wilderWild$alterWalkMovementThresholdInWater(WalkAnimationState instance, Operation<Boolean> original) {
		return Axolotl.class.cast(this).isInWater() ? instance.speed() > 0.075F : original.call(instance);
	}

}
