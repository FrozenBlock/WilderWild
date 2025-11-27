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

package net.frozenblock.wilderwild.mixin.entity.villager;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.frozenblock.wilderwild.config.WWEntityConfig;
import net.frozenblock.wilderwild.registry.WWItems;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VillagerTrades.class)
public class VillagerTradesMixin {

	@ModifyExpressionValue(
		method = "method_16929",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/item/Items;JUNGLE_BOAT:Lnet/minecraft/world/item/Item;",
			ordinal = 0
		)
	)
	private static Item wilderWild$desertVillagerSellsPalmBoat(Item original) {
		if (WWEntityConfig.get().villager.fishermanDesertPalmBoat) return WWItems.PALM_BOAT;
		return original;
	}

}
