/*
 * Copyright 2023-2025 FrozenBlock
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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WallClimberNavigation.class)
public abstract class WallClimberNavigationMixin extends PathNavigation {

	public WallClimberNavigationMixin(Mob mob, Level level) {
		super(mob, level);
	}

	@ModifyExpressionValue(
		method = "tick",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Mob;getBbWidth()F"
		)
	)
	public float wilderWild$tick(float original) {
		if (this.mob instanceof Crab crab) {
			return original * (crab.isBaby() ? 4F : 2F);
		}
		return original;
	}

}
