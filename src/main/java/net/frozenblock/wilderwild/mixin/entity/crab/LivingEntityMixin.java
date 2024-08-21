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

package net.frozenblock.wilderwild.mixin.entity.crab;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.entity.Crab;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@ModifyExpressionValue(
		method = "travel",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"
		)
	)
	public boolean wilderWild$crabTravel(boolean original) {
		if (LivingEntity.class.cast(this) instanceof Crab crab) {
			return crab.isClimbing();
		}
		return original;
	}

	@ModifyExpressionValue(
		method = "handleRelativeFrictionAndCalculateMovement",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"
		)
	)
	public boolean wilderWild$crabHandleRelativeFrictionAndCalculateMovement(boolean original) {
		if (LivingEntity.class.cast(this) instanceof Crab crab) {
			return crab.isClimbing();
		}
		return original;
	}

}
