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

package net.frozenblock.wilderwild.mixin.entity.boat;

import net.frozenblock.wilderwild.WilderEnumValues;
import net.frozenblock.wilderwild.registry.RegisterItems;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChestBoat.class)
public final class ChestBoatDropsMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@Inject(method = "getDropItem", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getModdedChestBoats(CallbackInfoReturnable<Item> info) {
		var boat = ChestBoat.class.cast(this);
		if (boat.getVariant() == WilderEnumValues.BAOBAB) {
			info.setReturnValue(RegisterItems.BAOBAB_CHEST_BOAT);
		} else if (boat.getVariant() == WilderEnumValues.CYPRESS) {
			info.setReturnValue(RegisterItems.CYPRESS_CHEST_BOAT);
		} else if (boat.getVariant() == WilderEnumValues.PALM) {
			info.setReturnValue(RegisterItems.PALM_CHEST_BOAT);
		}
	}

}
