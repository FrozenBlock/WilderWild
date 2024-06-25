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

import net.frozenblock.wilderwild.entity.ai.crab.CrabNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PathNavigation.class)
public class PathNavigationMixin {

	@Inject(
		method = "doStuckDetection",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/navigation/PathNavigation;stop()V"
		)
	)
	public void wilderWild$onStuckNavigationStop(CallbackInfo info) {
		PathNavigation navigation = PathNavigation.class.cast(this);
		if (navigation instanceof CrabNavigation crabNavigation) {
			crabNavigation.pathToPosition = null;
		}
	}

	@Inject(
		method = "doStuckDetection",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/ai/navigation/PathNavigation;timeoutPath()V"
		)
	)
	public void wilderWild$onStuckNavigationTimeout(CallbackInfo info) {
		PathNavigation navigation = PathNavigation.class.cast(this);
		if (navigation instanceof CrabNavigation crabNavigation) {
			crabNavigation.pathToPosition = null;
		}
	}

}
