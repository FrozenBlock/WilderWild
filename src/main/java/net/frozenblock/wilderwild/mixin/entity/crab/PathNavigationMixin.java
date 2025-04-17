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
