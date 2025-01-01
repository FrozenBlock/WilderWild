/*
 * Copyright 2024-2025 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.datafix.boat;

import net.minecraft.util.datafix.fixes.BoatSplitFix;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BoatSplitFix.class)
public class BoatSplitFixMixin {

	@Inject(
		method = "mapVariantToNormalBoat",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$mapVariantToNormalBoat(String string, CallbackInfoReturnable<String> info) {
		if (string.equals("wilderwildbaobab")) {
			info.setReturnValue("wilderwild:baobab_boat");
		} else if (string.equals("wilderwildcypress")) {
			info.setReturnValue("wilderwild:cypress_boat");
		} else if (string.equals("wilderwildpalm")) {
			info.setReturnValue("wilderwild:palm_boat");
		} else if (string.equals("wilderwildmaple")) {
			info.setReturnValue("wilderwild:maple_boat");
		}
	}

	@Inject(
		method = "mapVariantToChestBoat",
		at = @At("HEAD"),
		cancellable = true
	)
	private static void wilderWild$mapVariantToChestBoat(String string, CallbackInfoReturnable<String> info) {
		if (string.equals("wilderwildbaobab")) {
			info.setReturnValue("wilderwild:baobab_chest_boat");
		} else if (string.equals("wilderwildcypress")) {
			info.setReturnValue("wilderwild:cypress_chest_boat");
		} else if (string.equals("wilderwildpalm")) {
			info.setReturnValue("wilderwild:palm_chest_boat");
		} else if (string.equals("wilderwildmaple")) {
			info.setReturnValue("wilderwild:maple_chest_boat");
		}
	}

}
