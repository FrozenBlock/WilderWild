/*
 * Copyright 2025-2026 FrozenBlock
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

package net.frozenblock.wilderwild.references;

import net.frozenblock.wilderwild.WWConstants;
import net.minecraft.core.registries.BuiltInRegistries;

public final class WWRegistryAliases {

	public static void init() {
		BuiltInRegistries.BLOCK.addAlias(WWConstants.id("geyser"), WWConstants.id("geothermal_vent"));
		BuiltInRegistries.ITEM.addAlias(WWConstants.id("geyser"), WWConstants.id("geothermal_vent"));
		BuiltInRegistries.BLOCK_ENTITY_TYPE.addAlias(WWConstants.id("geyser"), WWConstants.id("geothermal_vent"));
	}
}
