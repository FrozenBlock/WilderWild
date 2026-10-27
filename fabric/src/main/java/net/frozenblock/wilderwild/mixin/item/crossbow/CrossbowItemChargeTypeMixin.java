/*
 * Copyright 2026 FrozenBlock
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

package net.frozenblock.wilderwild.mixin.item.crossbow;

import net.frozenblock.wilderwild.WWConstants;
import net.frozenblock.wilderwild.item.impl.WWCrossbowChargeType;
import net.minecraft.world.item.CrossbowItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(CrossbowItem.ChargeType.class)
public enum CrossbowItemChargeTypeMixin { // In common mixins.json
	WILDERWILD_HARPOON(WWConstants.safeString("harpoon"));

	static {
		WWCrossbowChargeType.WILDERWILD_HARPOON = CrossbowItem.ChargeType.class.cast(WILDERWILD_HARPOON);
	}

	@Shadow
	CrossbowItemChargeTypeMixin(String name) {}
}
