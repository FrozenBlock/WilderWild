/*
 * Copyright 2024 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.client.wind.fallingleaves;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.frozenblock.wilderwild.WilderConstants;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import randommcsomethin.fallingleaves.config.ConfigDefaults;

@Pseudo
@Environment(EnvType.CLIENT)
@Mixin(ConfigDefaults.class)
public class ConfigDefaultsMixin {

	@Inject(
		method = "isConifer",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidnt(ResourceLocation blockId, CallbackInfoReturnable<Boolean> info) {
		if (
			blockId.toString().equals(WilderConstants.string("cypress_leaves"))
			|| blockId.toString().equals(WilderConstants.string("palm_fronds"))
		) {
			info.setReturnValue(true);
		}
	}

	@Inject(
		method = "spawnRateFactor",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$forceAddCompatBecauseTheyDidntAgain(ResourceLocation blockId, CallbackInfoReturnable<Double> info) {
		if (blockId.toString().equals(WilderConstants.string("palm_fronds"))) {
			info.setReturnValue(0D);
		}
	}

}
