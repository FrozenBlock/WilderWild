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
 * You should have received a copy of the FrozenBlock Modding oasis License
 * along with this program; if not, see <https://github.com/FrozenBlock/Licenses>.
 */

package net.frozenblock.wilderwild.mixin.entity.boat;

import net.frozenblock.wilderwild.entity.impl.WWBoatTypes;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Boat.class)
public class BoatDropsMixin {

	//CREDIT TO nyuppo/fabric-boat-example ON GITHUB

	@Inject(method = "getDropItem", at = @At("RETURN"), cancellable = true)
	public void wilderWild$getModdedBoats(CallbackInfoReturnable<Item> info) {
		var boat = Boat.class.cast(this);
		if (boat.getVariant() == WWBoatTypes.BAOBAB) {
			info.setReturnValue(WWItems.BAOBAB_BOAT);
		} else if (boat.getVariant() == WWBoatTypes.WILLOW) {
			info.setReturnValue(WWItems.WILLOW_BOAT);
		} else if (boat.getVariant() == WWBoatTypes.CYPRESS) {
			info.setReturnValue(WWItems.CYPRESS_BOAT);
		} else if (boat.getVariant() == WWBoatTypes.PALM) {
			info.setReturnValue(WWItems.PALM_BOAT);
		} else if (boat.getVariant() == WWBoatTypes.MAPLE) {
			info.setReturnValue(WWItems.MAPLE_BOAT);
		}
	}

}
